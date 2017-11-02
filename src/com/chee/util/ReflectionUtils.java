/*   1:    */ package com.chee.util;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.Field;
/*   4:    */ import java.lang.reflect.InvocationTargetException;
/*   5:    */ import java.lang.reflect.Method;
/*   6:    */ import java.lang.reflect.ParameterizedType;
/*   7:    */ import java.lang.reflect.Type;
/*   8:    */ import java.util.ArrayList;
/*   9:    */ import java.util.Collection;
/*  10:    */ import java.util.List;
/*  11:    */ import org.apache.commons.beanutils.PropertyUtils;
/*  12:    */ import org.apache.commons.lang.StringUtils;
/*  13:    */ import org.slf4j.Logger;
/*  14:    */ import org.slf4j.LoggerFactory;
/*  15:    */ import org.springframework.util.Assert;
/*  16:    */ 
/*  17:    */ public class ReflectionUtils
/*  18:    */ {
/*  19: 22 */   private static Logger logger = LoggerFactory.getLogger(ReflectionUtils.class);
/*  20:    */   
/*  21:    */   public static Object invokeGetterMethod(Object obj, String propertyName)
/*  22:    */   {
/*  23: 28 */     String getterMethodName = "get" + StringUtils.capitalize(propertyName);
/*  24: 29 */     return invokeMethod(obj, getterMethodName, new Class[0], new Object[0]);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public static void invokeSetterMethod(Object obj, String propertyName, Object value)
/*  28:    */   {
/*  29: 36 */     invokeSetterMethod(obj, propertyName, value, null);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public static void invokeSetterMethod(Object obj, String propertyName, Object value, Class<?> propertyType)
/*  33:    */   {
/*  34: 46 */     Class<?> type = propertyType != null ? propertyType : value.getClass();
/*  35: 47 */     String setterMethodName = "set" + StringUtils.capitalize(propertyName);
/*  36: 48 */     invokeMethod(obj, setterMethodName, new Class[] { type }, new Object[] { value });
/*  37:    */   }
/*  38:    */   
/*  39:    */   public static Object getFieldValue(Object obj, String fieldName)
/*  40:    */   {
/*  41: 55 */     Field field = getAccessibleField(obj, fieldName);
/*  42: 57 */     if (field == null) {
/*  43: 58 */       throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + obj + "]");
/*  44:    */     }
/*  45: 61 */     Object result = null;
/*  46:    */     try
/*  47:    */     {
/*  48: 63 */       result = field.get(obj);
/*  49:    */     }
/*  50:    */     catch (IllegalAccessException e)
/*  51:    */     {
/*  52: 65 */       logger.error("不可能抛出的异常{}", e.getMessage());
/*  53:    */     }
/*  54: 67 */     return result;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public static void setFieldValue(Object obj, String fieldName, Object value)
/*  58:    */   {
/*  59: 74 */     Field field = getAccessibleField(obj, fieldName);
/*  60: 76 */     if (field == null) {
/*  61: 77 */       throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + obj + "]");
/*  62:    */     }
/*  63:    */     try
/*  64:    */     {
/*  65: 81 */       field.set(obj, value);
/*  66:    */     }
/*  67:    */     catch (IllegalAccessException e)
/*  68:    */     {
/*  69: 83 */       logger.error("不可能抛出的异常:{}", e.getMessage());
/*  70:    */     }
/*  71:    */   }
/*  72:    */   
/*  73:    */   public static Field getAccessibleField(Object obj, String fieldName)
/*  74:    */   {
/*  75: 91 */     Assert.notNull(obj, "object不能为空");
/*  76: 92 */     Assert.hasText(fieldName, "fieldName");
/*  77: 93 */     for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
/*  78:    */       try
/*  79:    */       {
/*  80: 95 */         Field field = superClass.getDeclaredField(fieldName);
/*  81: 96 */         field.setAccessible(true);
/*  82: 97 */         return field;
/*  83:    */       }
/*  84:    */       catch (NoSuchFieldException localNoSuchFieldException) {}
/*  85:    */     }
/*  86:102 */     return null;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public static Object invokeMethod(Object obj, String methodName, Class<?>[] parameterTypes, Object[] args)
/*  90:    */   {
/*  91:109 */     Method method = getAccessibleMethod(obj, methodName, parameterTypes);
/*  92:110 */     if (method == null) {
/*  93:111 */       throw new IllegalArgumentException("Could not find method [" + methodName + "] on target [" + obj + "]");
/*  94:    */     }
/*  95:    */     try
/*  96:    */     {
/*  97:115 */       return method.invoke(obj, args);
/*  98:    */     }
/*  99:    */     catch (Exception e)
/* 100:    */     {
/* 101:117 */       throw convertReflectionExceptionToUnchecked(e);
/* 102:    */     }
/* 103:    */   }
/* 104:    */   
/* 105:    */   public static Method getAccessibleMethod(Object obj, String methodName, Class<?>... parameterTypes)
/* 106:    */   {
/* 107:128 */     Assert.notNull(obj, "object不能为空");
/* 108:130 */     for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
/* 109:    */       try
/* 110:    */       {
/* 111:132 */         Method method = superClass.getDeclaredMethod(methodName, parameterTypes);
/* 112:    */         
/* 113:134 */         method.setAccessible(true);
/* 114:    */         
/* 115:136 */         return method;
/* 116:    */       }
/* 117:    */       catch (NoSuchMethodException localNoSuchMethodException) {}
/* 118:    */     }
/* 119:142 */     return null;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public static <T> Class<T> getSuperClassGenricType(Class clazz)
/* 123:    */   {
/* 124:156 */     return getSuperClassGenricType(clazz, 0);
/* 125:    */   }
/* 126:    */   
/* 127:    */   public static Class getSuperClassGenricType(Class clazz, int index)
/* 128:    */   {
/* 129:174 */     Type genType = clazz.getGenericSuperclass();
/* 130:176 */     if (!(genType instanceof ParameterizedType))
/* 131:    */     {
/* 132:177 */       logger.warn(clazz.getSimpleName() + "'s superclass not ParameterizedType");
/* 133:178 */       return Object.class;
/* 134:    */     }
/* 135:181 */     Type[] params = ((ParameterizedType)genType).getActualTypeArguments();
/* 136:183 */     if ((index >= params.length) || (index < 0))
/* 137:    */     {
/* 138:184 */       logger.warn("Index: " + index + ", Size of " + clazz.getSimpleName() + "'s Parameterized Type: " + params.length);
/* 139:185 */       return Object.class;
/* 140:    */     }
/* 141:187 */     if (!(params[index] instanceof Class))
/* 142:    */     {
/* 143:188 */       logger.warn(clazz.getSimpleName() + " not set the actual class on superclass generic parameter");
/* 144:189 */       return Object.class;
/* 145:    */     }
/* 146:192 */     return (Class)params[index];
/* 147:    */   }
/* 148:    */   
/* 149:    */   public static RuntimeException convertReflectionExceptionToUnchecked(Exception e)
/* 150:    */   {
/* 151:199 */     if (((e instanceof IllegalAccessException)) || ((e instanceof IllegalArgumentException)) || ((e instanceof NoSuchMethodException))) {
/* 152:200 */       return new IllegalArgumentException("Reflection Exception.", e);
/* 153:    */     }
/* 154:201 */     if ((e instanceof InvocationTargetException)) {
/* 155:202 */       return new RuntimeException("Reflection Exception.", ((InvocationTargetException)e).getTargetException());
/* 156:    */     }
/* 157:203 */     if ((e instanceof RuntimeException)) {
/* 158:204 */       return (RuntimeException)e;
/* 159:    */     }
/* 160:206 */     return new RuntimeException("Unexpected Checked Exception.", e);
/* 161:    */   }
/* 162:    */   
/* 163:    */   public static String convertElementPropertyToString(Collection collection, String propertyName, String separator)
/* 164:    */   {
/* 165:221 */     List list = convertElementPropertyToList(collection, propertyName);
/* 166:222 */     return StringUtils.join(list, separator);
/* 167:    */   }
/* 168:    */   
/* 169:    */   public static List convertElementPropertyToList(Collection collection, String propertyName)
/* 170:    */   {
/* 171:235 */     List list = new ArrayList();
/* 172:    */     try
/* 173:    */     {
/* 174:238 */       for (Object obj : collection) {
/* 175:239 */         list.add(PropertyUtils.getProperty(obj, propertyName));
/* 176:    */       }
/* 177:    */     }
/* 178:    */     catch (Exception e)
/* 179:    */     {
/* 180:242 */       throw convertReflectionExceptionToUnchecked(e);
/* 181:    */     }
/* 182:245 */     return list;
/* 183:    */   }
/* 184:    */ }


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.util.ReflectionUtils
 * JD-Core Version:    0.7.0.1
 */