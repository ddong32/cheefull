package com.chee.annotation;

import com.chee.entity.Syslog;
import com.chee.entity.User;
import com.chee.service.LogRecordService;
import com.chee.util.UserUtil;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import javax.annotation.Resource;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

@Component
@Aspect
public class LogAspect {
    @Resource
    private UserUtil userUtil;
    @Resource
    private LogRecordService logRecordService;

    @Pointcut("execution(* com.chee.service..*.*(..)) && !execution(* com.chee.action..*.set*()) && !execution(* com.chee.action..*.get*()) ")
    public void pointCut() {
    }

    @AfterReturning("pointCut() && @annotation(rl)")
    public void addLogSuccess(JoinPoint jp, rmpfLog rl) throws Exception {
        Object[] parames = jp.getArgs();
        String params = parseParames(parames);
        String signature = jp.getSignature().toString();

        String className = jp.getTarget().getClass().toString();
        className = className.substring(className.indexOf("com"));

        String methodName = signature.substring(signature.lastIndexOf(".") + 1, signature.indexOf("("));

        String keyword = parserElement(parames[0], rl.keyword());

        User user = this.userUtil.getLoginUser();
        Syslog syslog = new Syslog();
        syslog.setClassname(className);
        syslog.setMethodname(methodName);
        syslog.setArgument(params);
        syslog.setOperation(rl.desc());

        syslog.setCreatedate(new Date());
        syslog.setLevel("1");
        syslog.setKeyword(keyword);
        if (user != null) {
            syslog.setUserid(user.getId());
            syslog.setUsername(user.getName());
            syslog.setIp(user.getLoginIp());
        }
        this.logRecordService.save(syslog);
    }

    @AfterThrowing(pointcut = "pointCut() && @annotation(rl)", throwing = "ex")
    public void addLog(JoinPoint jp, rmpfLog rl, Throwable ex) throws Exception {
        Object[] parames = jp.getArgs();
        String params = parseParames(parames);
        String signature = jp.getSignature().toString();
        String className = jp.getTarget().getClass().toString();

        className = className.substring(className.indexOf("com"));

        String methodName = signature.substring(signature.lastIndexOf(".") + 1, signature.indexOf("("));

        User user = this.userUtil.getLoginUser();
        Syslog syslog = new Syslog();
        syslog.setClassname(className);
        syslog.setMethodname(methodName);
        syslog.setArgument(params);
        syslog.setOperation(rl.desc());

        syslog.setCreatedate(new Date());
        syslog.setContent(ex.toString());
        syslog.setLevel("0");
        if (user != null) {
            syslog.setUserid(user.getId());
            syslog.setUsername(user.getUsername());
            syslog.setIp(user.getLoginIp());
        }
        this.logRecordService.save(syslog);
    }

    private String getModelName(String packageName) {
        String modelName = "";
        SAXReader reader = new SAXReader();
        try {
            File proj = ResourceUtils.getFile("classpath:project.xml");
            Document doc = reader.read(proj);

            Element root = doc.getRootElement();

            modelName = searchModelName(root, packageName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelName;
    }

    private String searchModelName(Element element, String packageName) {
        String modelName = searchModelNodes(element, packageName);
        if (packageName.lastIndexOf(".") == -1) {
            return modelName;
        }
        if (modelName.equals("")) {
            packageName = packageName.substring(0, packageName.lastIndexOf("."));
            modelName = searchModelName(element, packageName);
        }
        return modelName;
    }

    private String searchModelNodes(Element element, String packageName) {
        String modelName = "";
        Element modules = element.element("modules");
        Iterator<?> it = modules.elementIterator();
        if (!it.hasNext()) {
            return modelName;
        }
        while (it.hasNext()) {
            Element model = (Element) it.next();
            String pack = model.attributeValue("packageName");
            String name = model.elementText("moduleCHPath");
            if (packageName.equals(pack)) {
                modelName = name;
                return modelName;
            }
            if ((modelName != null) && (!modelName.equals(""))) {
                break;
            }
            modelName = searchModelNodes(model, packageName);
        }
        return modelName;
    }

    private String parseParames(Object[] parames) throws Exception {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < parames.length; i++) {
            if ((parames[i] != null) && (!"".equals(parames[i].toString()))) {
                if (((parames[i] instanceof Object[])) || ((parames[i] instanceof Collection))) {
                    JSONArray json = JSONArray.fromObject(parames[i]);
                    if (i == parames.length - 1) {
                        sb.append(json.toString());
                    } else {
                        sb.append(json.toString() + ",");
                    }
                } else if ((parames[i] != null) && (parames[i].toString() != null) && (parames[i].toString().indexOf("id") > -1)) {
                    sb.append(parames[i].toString());
                } else if (i == parames.length - 1) {
                    sb.append(parames[i].toString());
                } else {
                    sb.append(parames[i].toString() + ",");
                }
            }
        }
        String params = sb.toString();
        params = params.replaceAll("(\"\\w+\":\"\",)", "");
        params = params.replaceAll("(,\"\\w+\":\"\")", "");
        return params;
    }

    private String parserElement(Object model, String keyword) {
        String flag = "";
        if ((keyword == null) || ("".equals(keyword))) {
            return flag;
        }
        if ((model != null) && (model.toString() != null) && (model.toString().indexOf(keyword) > -1)) {
            String json = model.toString();

            JsonParser parser = new JsonParser();

            JsonElement element = parser.parse(json);

            JsonObject root = element.getAsJsonObject();

            JsonPrimitive flagJson = root.getAsJsonPrimitive(keyword);
            flag = flagJson.getAsString();
        }
        return flag;
    }

    private Object getProperty(Object owner, String fieldName) throws Exception {
        Class ownerClass = owner.getClass();
        Field field = ownerClass.getField(fieldName);
        Object property = field.get(owner);
        return property;
    }

    private Object invokeMethod(Object owner, String methodName, Object[] args) throws Exception {
        Class ownerClass = owner.getClass();
        Class[] argsClass = new Class[args.length];
        int i = 0;
        for (int j = args.length; i < j; i++) {
            argsClass[i] = args[i].getClass();
        }
        Method method = ownerClass.getMethod(methodName, argsClass);
        return method.invoke(owner, args);
    }

    public String modelReflect(Object model) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        StringBuffer sb = new StringBuffer();
        Field[] field = model.getClass().getDeclaredFields();
        for (int j = 0; j < field.length; j++) {
            String fieldContent = "";
            String name = field[j].getName();
            name = name.substring(0, 1).toUpperCase() + name.substring(1);

            String type = field[j].getGenericType().toString();
            if (type.equals("class java.lang.String")) {
                Method m = model.getClass().getMethod("get" + name, new Class[0]);
                String value = (String) m.invoke(model, new Object[0]);
                if (value != null) {
                    fieldContent = name + "=" + value;
                }
            }
            if (type.equals("class java.lang.Integer")) {
                Method m = model.getClass().getMethod("get" + name, new Class[0]);
                Integer value = (Integer) m.invoke(model, new Object[0]);
                if (value != null) {
                    fieldContent = name + "=" + value;
                }
            }
            if (type.equals("class java.lang.Short")) {
                Method m = model.getClass().getMethod("get" + name, new Class[0]);
                Short value = (Short) m.invoke(model, new Object[0]);
                if (value != null) {
                    fieldContent = name + "=" + value;
                }
            }
            if (type.equals("class java.lang.Double")) {
                Method m = model.getClass().getMethod("get" + name, new Class[0]);
                Double value = (Double) m.invoke(model, new Object[0]);
                if (value != null) {
                    fieldContent = name + "=" + value;
                }
            }
            if (type.equals("class java.lang.Boolean")) {
                Method m = model.getClass().getMethod("get" + name, new Class[0]);
                Boolean value = (Boolean) m.invoke(model, new Object[0]);
                if (value != null) {
                    fieldContent = name + "=" + value;
                }
            }
            if (type.equals("class java.util.Date")) {
                Method m = model.getClass().getMethod("get" + name, new Class[0]);
                Date value = (Date) m.invoke(model, new Object[0]);
                if (value != null) {
                    fieldContent = name + "=" + value.toLocaleString();
                }
            }
            JSONObject json = JSONObject.fromObject(fieldContent);
            if (j == field.length - 1) {
                sb.append(json.toString());
            } else {
                sb.append(json.toString() + ",");
            }
        }
        return null;
    }

    public static String getMethodRemark(JoinPoint joinPoint) throws Exception {
        Class<?> clazz = joinPoint.getTarget().getClass();
        String name = joinPoint.getSignature().getName();
        Object[] parameterTypes = joinPoint.getArgs();
        for (Method method : clazz.getDeclaredMethods()) {
            if ((method.getName().equals(name)) && (method.getParameterTypes().length == parameterTypes.length)) {
                rmpfLog methodLog = (rmpfLog) method.getAnnotation(rmpfLog.class);
                if (methodLog == null) {
                    break;
                }
                return "";
            }
        }
        return "";
    }
}
