package com.chee.action.admin;

import com.chee.annotation.rmpfLog;
import com.chee.common.Config;
import com.chee.entity.Department;
import com.chee.entity.Role;
import com.chee.entity.User;
import com.chee.service.DepartmentService;
import com.chee.service.RoleService;
import com.chee.service.UserService;
import com.chee.util.HibernateUtils;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.Validations;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Results;
import org.hibernate.Hibernate;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.security.AccountExpiredException;
import org.springframework.security.BadCredentialsException;
import org.springframework.security.DisabledException;
import org.springframework.security.LockedException;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@ParentPackage("admin")
@Results({ @org.apache.struts2.convention.annotation.Result(name = "main", location = "main!frame.action", type = "redirect") })
public class UserAction extends BaseAction<User, Integer> {
    private static final long serialVersionUID = 1L;
    private User user;
    private String beginTime;
    private String endTime;
    private List<Role> allRole;
    private List<Integer> roleIds;
    private String loginUsername;
    private String userDeptCode;
    @Resource
    private UserService userService;
    @Resource
    private RoleService roleService;
    @Resource
    private DepartmentService departmentService;
    private String addressId;
    private String addressIdStr;
    private Integer orgId;
    private Integer topOrgId;

    @InputConfig(resultName = "login")
    public String login() {
        HttpSession currentSession = getRequest().getSession(false);
        if (currentSession == null) {
            String ajaxHeader = getRequest().getHeader("X-Requested-With");
            if ("XMLHttpRequest".equals(ajaxHeader)) {
                return "session_timeout";
            }
        }
        setAttribute("systemName", Config.getInstance().getSystemName());
        setAttribute("systemCity", Config.getInstance().getSystemCity());
        Exception springSecurityLastException = (Exception) getSession("SPRING_SECURITY_LAST_EXCEPTION");
        if (springSecurityLastException != null) {
            if ((springSecurityLastException instanceof BadCredentialsException)) {
                this.loginUsername = ((String) getSession("SPRING_SECURITY_LAST_USERNAME")).toLowerCase();
                this.user = ((User) this.userService.get("username", this.loginUsername));
                if (this.user != null) {
                    int loginFailureCount = this.user.getLoginFailureCount().intValue();
                    if (loginFailureCount == 5) {
                        addActionError("第 " + loginFailureCount + " 次密码错误!您的账号已经被锁定!请联系管理员!");
                    } else if (5 - loginFailureCount <= 2) {
                        addActionError("第 " + loginFailureCount + " 次密码错误,若连续" + 5 + "次密码输入错误,您的账号将被锁定!");
                    } else {
                        addActionError("您的用户名或密码错误!");
                    }
                } else {
                    addActionError("您的用户名或密码错误!");
                }
            } else if ((springSecurityLastException instanceof DisabledException)) {
                addActionError("您的账号已被禁用,无法登录!");
            } else if ((springSecurityLastException instanceof LockedException)) {
                addActionError("您的账号已被锁定,无法登录!");
            } else if ((springSecurityLastException instanceof AccountExpiredException)) {
                addActionError("您的账号已过期,无法登录!");
            } else {
                addActionError("出现未知错误,无法登录!");
                System.out.println(springSecurityLastException);
            }
            getSession().remove("SPRING_SECURITY_LAST_USERNAME");
            getSession().remove("SPRING_SECURITY_LAST_EXCEPTION");
        }
        return "login";
    }

    public String list() {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(User.class);
        if ((this.userUtil != null) && (!getLoginUser().getRoleValues().contains("ROLE_ADMIN"))) {
            detachedCriteria.add(Restrictions.eq("isSystem", Boolean.valueOf(false)));
            detachedCriteria.createAlias("department", "department");
            detachedCriteria.add(Restrictions.like("department.path", getLoginUser().getDepartment().getPath() + "%"));
        }
        if (this.user != null) {
            if ((this.user.getUsername() != null) && (!"".equals(this.user.getUsername()))) {
                detachedCriteria.add(Restrictions.sqlRestriction("user_name like ? escape '/'", HibernateUtils.escapeSQLLike(this.user.getUsername()), Hibernate.STRING));
            }
            if ((this.user.getName() != null) && (!"".equals(this.user.getName()))) {
                detachedCriteria.add(Restrictions.sqlRestriction("name like ? escape '/'", HibernateUtils.escapeSQLLike(this.user.getName()), Hibernate.STRING));
            }
            if ((this.user.getJobNumber() != null) && (!"".equals(this.user.getJobNumber()))) {
                detachedCriteria.add(Restrictions.sqlRestriction("job_number like ? escape '/'", HibernateUtils.escapeSQLLike(this.user.getJobNumber()), Hibernate.STRING));
            }
            if ((this.user.getEmail() != null) && (!"".equals(this.user.getEmail()))) {
                detachedCriteria.add(Restrictions.sqlRestriction("email like ? escape '/'", HibernateUtils.escapeSQLLike(this.user.getEmail()), Hibernate.STRING));
            }
            if ((this.user.getUserDepartmentId() != null) && (!"".equals(this.user.getUserDepartmentId()))) {
                detachedCriteria.createAlias("department", "department");
                detachedCriteria.add(Restrictions.eq("department.id", this.user.getUserDepartmentId()));
            }
        }
        this.page.setOrderBy("isAccountEnabled,id");
        this.page.setOrder("desc,asc");
        this.page = this.userService.findPage(this.page, detachedCriteria);
        return "list";
    }

    public String checkSession() {
        Exception springSecurityLastException = (Exception) getSession("SPRING_SECURITY_LAST_EXCEPTION");
        if ((springSecurityLastException == null) && (getSession("SPRING_SECURITY_LAST_USERNAME") != null) && (!"".equals(getSession("SPRING_SECURITY_LAST_USERNAME")))) {
            return ajaxJson(getRequest().getSession().getId());
        }
        return ajaxJson("0");
    }

    public String input() {
        if ((this.user != null) && (this.user.getId() != null)) {
            this.user = ((User) this.userService.load(this.user.getId()));
            this.roleIds = this.user.getRoleIds();
        } else {
            this.user = new User();
        }
        List<Department> list = this.departmentService.getAllDistinct();
        for (Department organization : list) {
            if (organization.getParent() == null) {
                this.topOrgId = organization.getId();
                break;
            }
        }
        return "input";
    }

    public String info() {
        this.user = ((User) this.userService.get(getLoginUser().getId()));
        return "info";
    }

    public String updateUserPassword() {
        User persistent = (User) this.userService.get(this.user.getId());
        if ((this.user.getPassword() != null) && (!this.user.getPassword().equals(""))) {
            if (!persistent.getPassword().equals(DigestUtils.md5Hex(this.user.getPasswordCurrent()))) {
                addActionError("原密码不正解!");
                return "error";
            }
            if (this.user.getPassword().equals(this.user.getPasswordConfirm())) {
                String passwordMd5 = DigestUtils.md5Hex(this.user.getPassword());
                persistent.setPassword(passwordMd5);
            } else {
                addActionError("两次输入密码不一致!");
                return "error";
            }
        }
        this.userService.update(persistent);
        return "success";
    }

    public String update() {
        User persistent = (User) this.userService.get(this.user.getId());
        if ((this.user.getPassword() != null) && (!this.user.getPassword().equals(""))) {
            if (this.user.getPassword().equals(this.user.getPasswordConfirm())) {
                String passwordMd5 = DigestUtils.md5Hex(this.user.getPassword());
                persistent.setPassword(passwordMd5);
            } else {
                addActionError("两次输入密码不一致!");
                return "error";
            }
        }
        persistent.setName(this.user.getName());
        persistent.setSex(this.user.getSex());
        persistent.setJobNumber(this.user.getJobNumber());
        persistent.setQq(this.user.getQq());
        persistent.setLxdz(this.user.getLxdz());
        persistent.setPhone(this.user.getPhone());
        persistent.setQq(this.user.getQq());
        persistent.setEmail(this.user.getEmail());
        persistent.setUpdateDate(new Date());

        persistent.setXx(this.user.getXx());
        persistent.setXz(this.user.getXz());
        persistent.setJjlx(this.user.getJjlx());
        persistent.setRzsj(this.user.getRzsj());
        persistent.setIdCard(this.user.getIdCard());
        this.userService.update(persistent);
        this.redirectionUrl = "user!info.action";
        return "success";
    }

    @rmpfLog(desc = "用户添加/修改")
    @Validations(requiredStrings = { @com.opensymphony.xwork2.validator.annotations.RequiredStringValidator(fieldName = "user.username", message = "用户名不允许为空!"),
            @com.opensymphony.xwork2.validator.annotations.RequiredStringValidator(fieldName = "user.name", message = "姓名不允许为空!") }, requiredFields = {
            @com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator(fieldName = "user.isAccountEnabled", message = "是否启用不允许为空!"),
            @com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator(fieldName = "user.department.id", message = "组织机构不允许为空!") }, stringLengthFields = {
            @com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator(fieldName = "user.username", minLength = "2", maxLength = "20", message = "用户名长度必须在${minLength}到${maxLength}之间!"),
            @com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator(fieldName = "user.name", maxLength = "12", message = "姓名长度不允许大于${maxLength}!"),
            @com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator(fieldName = "user.jobNumber", maxLength = "15", message = "警员编号长度不允许大于${maxLength}!"),
            @com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator(fieldName = "user.mobile", maxLength = "15", message = "手机号码长度不允许大于${maxLength}!"),
            @com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator(fieldName = "user.email", maxLength = "50", message = "邮箱长度不允许大于${maxLength}!") }, regexFields = { @com.opensymphony.xwork2.validator.annotations.RegexFieldValidator(fieldName = "user.username", regexExpression = "^[0-9a-z_A-Z一-龥]+$", message = "用户名只允许包含中文、英文、数字和下划线!") })
    @InputConfig(resultName = "error")
    public String save() {
        if ((this.roleIds == null) || (this.roleIds.size() == 0) || (this.roleIds.get(0) == null)) {
            addActionError("请至少选择一个角色!");
            return "error";
        }
        if ((this.user.getPassword() != null) && (!this.user.getPassword().equals("")) && (!this.user.getPassword().equals(this.user.getPasswordConfirm()))) {
            addActionError("两次输入密码不一致!");
            return "error";
        }
        Set<Role> roleSet = new HashSet();
        for (Integer roleId : this.roleIds) {
            if (roleId != null) {
                Role role = new Role();
                role.setId(roleId);
                roleSet.add(role);
            }
        }
        User persistent = null;
        if ((this.user != null) && (this.user.getId() != null)) {
            System.out.println("update");
            persistent = (User) this.userService.get(this.user.getId());
            if (!this.userService.isUnique("username", persistent.getUsername(), this.user.getUsername())) {
                addActionError("用户名: " + this.user.getUsername() + " 已经存在!请重新命名!");
                return "error";
            }
            if (StringUtils.isNotEmpty(this.user.getPassword())) {
                String passwordMd5 = DigestUtils.md5Hex(this.user.getPassword());
                persistent.setPassword(passwordMd5);
            }
            BeanUtils.copyProperties(this.user, persistent, new String[] { "id", "createDate", "updateDate", "password", "isAccountExpired", "isCredentialsExpired", "loginFailureCount", "lockedDate",
                    "loginDate", "loginIp", "authorities", "roleSet", "isSystem", "violationAddressSet" });
            persistent.setRoleSet(roleSet);
            if (!this.user.getIsAccountLocked().booleanValue()) {
                persistent.setIsAccountLocked(Boolean.valueOf(false));
                persistent.setLoginFailureCount(Integer.valueOf(0));
            }
            persistent.setUpdateDate(new Date());
            this.userService.update(persistent);
        } else {
            System.out.println("save");
            if (!this.userService.isUnique("username", null, this.user.getUsername())) {
                addActionError("用户名: " + this.user.getUsername() + " 已经存在!请重新命名!");
                return "error";
            }
            if ((this.user.getPassword() == null) || (this.user.getPassword().equals(""))) {
                addActionError("密码不能为空!");
                return "error";
            }
            persistent = this.user;
            persistent.setUsername(persistent.getUsername());
            persistent.setLoginFailureCount(Integer.valueOf(0));
            persistent.setIsAccountLocked(Boolean.valueOf(false));
            persistent.setIsAccountExpired(Boolean.valueOf(false));
            persistent.setIsCredentialsExpired(Boolean.valueOf(false));
            persistent.setIsSystem(Boolean.valueOf(false));
            String passwordMd5 = DigestUtils.md5Hex(persistent.getPassword());
            persistent.setPassword(passwordMd5);
            persistent.setRoleSet(roleSet);
            persistent.setCreateDate(new Date());
            persistent.setUpdateDate(new Date());
            this.userService.save(persistent);
        }
        return "success";
    }

    @rmpfLog(desc = "用户删除")
    public String delete() {
        this.userService.delete(this.user.getId());
        this.redirectionUrl = "user!list.action";
        return "success";
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Role> getAllRole() {
        if (this.allRole == null) {
            if (getLoginUser().getRoleValues().contains("ROLE_ADMIN")) {
                this.allRole = this.roleService.getAll();
            } else {
                this.allRole = this.roleService.find("from Role where value != 'ROLE_ADMIN' ", new Object[0]);
            }
        }
        return this.allRole;
    }

    public void setAllRole(List<Role> allRole) {
        this.allRole = allRole;
    }

    public List<Integer> getRoleIds() {
        return this.roleIds;
    }

    public void setRoleIds(List<Integer> roleIds) {
        this.roleIds = roleIds;
    }

    public String getLoginUsername() {
        return this.loginUsername;
    }

    public void setLoginUsername(String loginUsername) {
        this.loginUsername = loginUsername;
    }

    public String getUserDeptCode() {
        return this.userDeptCode;
    }

    public void setUserDeptCode(String userDeptCode) {
        this.userDeptCode = userDeptCode;
    }

    public Integer getOrgId() {
        return this.orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getAddressIdStr() {
        return this.addressIdStr;
    }

    public void setAddressIdStr(String addressIdStr) {
        this.addressIdStr = addressIdStr;
    }

    public String getAddressId() {
        return this.addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getBeginTime() {
        return this.beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return this.endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getTopOrgId() {
        return this.topOrgId;
    }

    public void setTopOrgId(Integer topOrgId) {
        this.topOrgId = topOrgId;
    }
}
