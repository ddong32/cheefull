package com.chee.action.admin;

import com.chee.entity.Business;
import com.chee.entity.BusinessCustomer;
import com.chee.entity.Department;
import com.chee.entity.User;
import com.chee.service.BusinessCustomerService;
import com.chee.service.DepartmentService;
import com.chee.service.UserService;
import com.chee.util.DateUtil;
import com.chee.util.StringUtils;
import com.chee.util.UserUtil;
import com.opensymphony.xwork2.ActionSupport;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Results;

@ParentPackage("admin")
@Results({ @org.apache.struts2.convention.annotation.Result(name = "index", location = "report!list.action", type = "redirect") })
public class ReportAction extends ActionSupport {
    private static final long serialVersionUID = 4984599292597512018L;
    public static Logger logger = Logger.getLogger(ReportAction.class);
    private List<User> userList;
    private User loginUserObj;
    private Business business;
    private List<Department> orgsList;
    private String beginDate;
    private String endDate;
    private String parent_id;
    private String type;
    private String uid;
    private String areaName;
    @Resource
    private UserService userService;
    @Resource
    private DepartmentService departmentService;
    @Resource
    private UserUtil userUtil;
    @Resource
    private BusinessCustomerService businessCustomerService;

    public String businessEfficient() {
        if (StringUtils.isEmpty(getBeginDate())) {
            setBeginDate(DateUtil.afterNDay(new Date(), 0, "yyyy-MM") + "-01");
        }
        if (StringUtils.isEmpty(getEndDate())) {
            setEndDate(DateUtil.afterNDay(new Date(), 0, "yyyy-MM-dd"));
        }
        StringBuilder ids = new StringBuilder();
        for (User user : getUserList()) {
            ids.append("," + user.getId());
        }
        if (!"".equals(ids.toString())) {
            ServletActionContext.getRequest().setAttribute("ids", ids.toString().substring(1));
        }
        return "businessEfficient";
    }

    public String businessAudit() {
        if (StringUtils.isEmpty(getBeginDate())) {
            setBeginDate(DateUtil.afterNDay(new Date(), 0, "yyyy-MM") + "-01");
        }
        if (StringUtils.isEmpty(getEndDate())) {
            setEndDate(DateUtil.afterNDay(new Date(), 0, "yyyy-MM-dd"));
        }
        StringBuilder ids = new StringBuilder();
        for (User user : getUserList()) {
            ids.append("," + user.getId());
        }
        if (!"".equals(ids.toString())) {
            ServletActionContext.getRequest().setAttribute("ids", ids.toString().substring(1));
        }
        return "businessAudit";
    }

    public String businessCustomer() {
        if (StringUtils.isEmpty(getBeginDate())) {
            setBeginDate(DateUtil.afterNDay(new Date(), 0, "yyyy-MM") + "-01");
        }
        if (StringUtils.isEmpty(getEndDate())) {
            setEndDate(DateUtil.afterNDay(new Date(), 0, "yyyy-MM-dd"));
        }
        StringBuilder ids = new StringBuilder();
        for (User user : getUserList()) {
            ids.append("," + user.getId());
        }
        if (!"".equals(ids.toString())) {
            ServletActionContext.getRequest().setAttribute("ids", ids.toString().substring(1));
        }
        return "businessCustomer";
    }

    public String reportBusinessCustomerFrist() {
        BusinessCustomer reportParems = new BusinessCustomer();
        reportParems.setBeginDate(this.beginDate);
        reportParems.setEndDate(this.endDate);
        reportParems.setParent_id(this.parent_id);
        reportParems.setUid(this.uid);
        try {
            if (this.areaName != null) {
                this.areaName = URLDecoder.decode(this.areaName, "utf-8");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        reportParems.setCustomerDwmc(this.areaName);

        List<Map<String, Object>> list = this.businessCustomerService.reportBusinessCustomer(reportParems);
        List<Map<String, Object>> listTotal = this.businessCustomerService.reportBusinessCustomerTotal(reportParems);
        JSONObject jObject = new JSONObject();
        jObject.put("total", Integer.valueOf(list.size()));
        jObject.put("rows", list);
        jObject.put("footer", listTotal);
        return ajaxJson(jObject.toString());
    }

    public String reportBusinessCustomerSecond() {
        BusinessCustomer reportParems = new BusinessCustomer();
        reportParems.setBeginDate(this.beginDate);
        reportParems.setEndDate(this.endDate);
        reportParems.setParent_id(this.parent_id);
        reportParems.setType(this.type);
        reportParems.setUid(this.uid);
        try {
            if (this.areaName != null) {
                this.areaName = URLDecoder.decode(this.areaName, "utf-8");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        reportParems.setCustomerDwmc(this.areaName);
        List<Map<String, Object>> list = this.businessCustomerService.reportBusinessCustomer(reportParems);
        JSONArray jsonArray = JSONArray.fromObject(list);
        return ajaxJson(jsonArray.toString());
    }

    public String businessCustomerRS() {
        if (StringUtils.isEmpty(getBeginDate())) {
            setBeginDate(DateUtil.afterNDay(new Date(), 0, "yyyy-MM") + "-01");
        }
        if (StringUtils.isEmpty(getEndDate())) {
            setEndDate(DateUtil.afterNDay(new Date(), 0, "yyyy-MM-dd"));
        }
        StringBuilder ids = new StringBuilder();
        for (User user : getUserList()) {
            ids.append("," + user.getId());
        }
        if (!"".equals(ids.toString())) {
            ServletActionContext.getRequest().setAttribute("ids", ids.toString().substring(1));
        }
        return "businessCustomerRS";
    }

    public String businessCooperator() {
        if (StringUtils.isEmpty(getBeginDate())) {
            setBeginDate(DateUtil.afterNDay(new Date(), 0, "yyyy-MM") + "-01");
        }
        if (StringUtils.isEmpty(getEndDate())) {
            setEndDate(DateUtil.afterNDay(new Date(), 0, "yyyy-MM-dd"));
        }
        StringBuilder ids = new StringBuilder();
        for (User user : getUserList()) {
            ids.append("," + user.getId());
        }
        if (!"".equals(ids.toString())) {
            ServletActionContext.getRequest().setAttribute("ids", ids.toString().substring(1));
        }
        return "businessCooperator";
    }

    public String deptWork() {
        if (getLoginUser().getRoleValues().contains("ROLE_ADMIN")) {
            this.orgsList = this.departmentService.getAll();
        } else {
            this.orgsList = this.departmentService.getConnectOrgs(getLoginUser().getDepartment().getId());
        }
        return "deptWork";
    }

    public String businessPrint() {
        return "businessPrint";
    }

    public String getComboboxData() {
        JSONObject json = new JSONObject();
        JSONArray array = new JSONArray();
        String data;
        try {
            List<User> dateList = getUserList();
            if (!dateList.isEmpty()) {
                for (User user : dateList) {
                    json.put("id", user.getId());
                    json.put("name", user.getName());
                    array.add(json);
                }
            }
            data = array.toString();
        } catch (Exception e) {
            data = "{}";
        }
        return ajaxJson(data);
    }

    public List<User> getUserList() {
        if (this.userList == null) {
            StringBuffer hqlSb = new StringBuffer(" from User u where 1=1 and username <> 'system'");
            Map map = new HashMap();
            if (!getLoginUser().getRoleValues().contains("ROLE_ADMIN")) {
                if (getLoginUser().getRoleValues().contains("ROLE_MANAGER")) {
                    List orgCodeList = this.departmentService.getConnectOrgCodes(getLoginUser().getDepartment().getId());
                    if (orgCodeList != null) {
                        hqlSb.append(" and ( ");
                        for (int i = 0; i < orgCodeList.size(); i++) {
                            if (i != 0) {
                                hqlSb.append(" or ");
                            }
                            hqlSb.append(" u.department.code =:orgCode_" + i);
                            map.put("orgCode_" + i, orgCodeList.get(i));
                        }
                        hqlSb.append(" ) ");
                    }
                } else {
                    hqlSb.append(" and u.id=:loginUserId ");
                    map.put("loginUserId", getLoginUser().getId());
                }
            }
            this.userList = this.userService.find(hqlSb.toString(), map);
        }
        return this.userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public User getLoginUser() {
        return this.userUtil.getLoginUser();
    }

    public String getUserOrgCodes() {
        String orgCodes = "";
        if ((!getLoginUser().getRoleValues().contains("ROLE_ADMIN")) && (getLoginUser().getRoleValues().contains("ROLE_MANAGER"))) {
            List orgCodeList = this.departmentService.getConnectOrgCodes(getLoginUser().getDepartment().getId());
            if (orgCodeList != null) {
                for (int i = 0; i < orgCodeList.size(); i++) {
                    orgCodes = orgCodes + (String) orgCodeList.get(i) + ",";
                }
                orgCodes = orgCodes.substring(0, orgCodes.length() - 1);
            }
        }
        return orgCodes;
    }

    public User getLoginUserObj() {
        return this.loginUserObj;
    }

    public void setLoginUserObj(User loginUserObj) {
        this.loginUserObj = loginUserObj;
    }

    public List<Department> getOrgsList() {
        return this.orgsList;
    }

    public void setOrgsList(List<Department> orgsList) {
        this.orgsList = orgsList;
    }

    public String getBeginDate() {
        return this.beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return this.endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Business getBusiness() {
        return this.business;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

    public String getParent_id() {
        return this.parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUid() {
        return this.uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAreaName() {
        return this.areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String ajaxJson(String jsonString) {
        return ajax(jsonString, "text/html");
    }

    public String ajax(String content, String type) {
        try {
            HttpServletResponse response = ServletActionContext.getResponse();
            response.setContentType(type + ";charset=UTF-8");
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0L);
            response.getWriter().write(content);
            response.getWriter().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
