package com.chee.action.admin;

import com.chee.entity.Role;
import com.chee.service.ResourceService;
import com.chee.service.RoleService;
import com.chee.util.HibernateUtils;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.Validations;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Results;
import org.hibernate.Hibernate;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;

@ParentPackage("admin")
@Results({ @org.apache.struts2.convention.annotation.Result(name = "index", location = "role!list.action", type = "redirect") })
public class RoleAction extends BaseAction<Role, Integer> {
    private static final long serialVersionUID = -5383463207248344967L;
    private Role role;
    private com.chee.entity.Resource resource;
    private List<com.chee.entity.Resource> allResource;
    private String resourceIdStr;
    @javax.annotation.Resource
    private RoleService roleService;
    @javax.annotation.Resource
    private ResourceService resourceService;

    public String list() {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Role.class);
        if (!getLoginUser().getRoleValues().contains("ROLE_ADMIN")) {
            detachedCriteria.add(Restrictions.eq("isSystem", Boolean.valueOf(false)));
        }
        if ((this.role != null) && (this.role.getName() != null) && (!"".equals(this.role.getName()))) {
            detachedCriteria.add(Restrictions.sqlRestriction("name like ? escape '/'", HibernateUtils.escapeSQLLike(this.role.getName()), Hibernate.STRING));
        }
        this.page = this.roleService.findPage(this.page, detachedCriteria);
        return "list";
    }

    public String delete() {
        Role dbRole = (Role) this.roleService.get(this.role.getId());
        Set userSet = dbRole.getUserSet();
        if ((userSet != null) && (userSet.size() > 0)) {
            addActionError(dbRole.getName() + " 下存在用户，删除失败！");
            return "error";
        }
        this.roleService.delete(this.role.getId());
        this.redirectionUrl = "role!list.action";
        return "success";
    }

    public String input() {
        if ((this.role != null) && (this.role.getId() != null)) {
            this.role = ((Role) this.roleService.get(this.role.getId()));
            this.resourceIdStr = this.role.getResourceIdStr();
        } else {
            this.role = new Role();
        }
        return "input";
    }

    @Validations(requiredStrings = { @com.opensymphony.xwork2.validator.annotations.RequiredStringValidator(fieldName = "role.name", message = "角色名称不允许为空!"),
            @com.opensymphony.xwork2.validator.annotations.RequiredStringValidator(fieldName = "role.value", message = "角色标识不允许为空!") }, stringLengthFields = {
            @com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator(fieldName = "role.name", minLength = "1", maxLength = "15", message = "角色名称长度必须在${minLength}到${maxLength}之间!"),
            @com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator(fieldName = "role.value", minLength = "6", maxLength = "40", message = "角色标识长度必须在${minLength}到${maxLength}之间!"),
            @com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator(fieldName = "role.description", maxLength = "80", message = "角色描述长度不允许大于${maxLength}!") }, regexFields = { @com.opensymphony.xwork2.validator.annotations.RegexFieldValidator(fieldName = "role.value", regexExpression = "^ROLE_.*", message = "角色标识必须以ROLE_开头!") })
    @InputConfig(resultName = "error")
    public String save() throws Exception {
        if ((this.resourceIdStr == null) || ("".equals(this.resourceIdStr))) {
            addActionError("请至少选择一个权限!");
            return "error";
        }
        Role persistent = null;
        Set resourceSet = new HashSet();
        for (String idStr : this.resourceIdStr.split(",")) {
            if ((idStr != null) && (!idStr.equals(""))) {
                com.chee.entity.Resource resource = new com.chee.entity.Resource();
                resource.setId(Integer.valueOf(Integer.parseInt(idStr)));
                resourceSet.add(resource);
            }
        }
        if (this.role.getId() != null) {
            persistent = (Role) this.roleService.get(this.role.getId());
            if (!this.roleService.isUnique("name", persistent.getName(), this.role.getName())) {
                addActionError("角色名称: " + this.role.getName() + " 已经存在!请重新命名!");
                return "error";
            }
            if (!this.roleService.isUnique("value", persistent.getValue(), this.role.getValue())) {
                addActionError("角色标识: " + this.role.getValue() + " 已经存在!请重新命名!");
                return "error";
            }
            BeanUtils.copyProperties(this.role, persistent, new String[] { "id", "createDate", "updateDate", "isSystem", "userSet" });
            persistent.setResourceSet(resourceSet);
            persistent.setUpdateDate(new Date());
            this.roleService.update(persistent);
        } else {
            if (!this.roleService.isUnique("name", null, this.role.getName())) {
                addActionError("角色名称: " + this.role.getName() + " 已经存在!请重新命名!");
                return "error";
            }
            if (!this.roleService.isUnique("value", null, this.role.getValue())) {
                addActionError("角色标识: " + this.role.getValue() + " 已经存在!请重新命名!");
                return "error";
            }
            persistent = this.role;
            persistent.setResourceSet(resourceSet);
            persistent.setIsSystem(Boolean.valueOf(false));
            persistent.setCreateDate(new Date());
            persistent.setUpdateDate(new Date());
            this.roleService.save(persistent);
        }
        this.redirectionUrl = "role!list.action";
        return "success";
    }

    public String ajaxResourceTree() {
        List optionList = getChildrenResource(this.resource);
        JSONArray jsonArray = JSONArray.fromObject(optionList);

        return ajaxJson(jsonArray.toString());
    }

    public List<LinkedHashMap<String, Object>> getChildrenResource(com.chee.entity.Resource resource) {
        List<com.chee.entity.Resource> childrenResourceList = null;
        if ((resource != null) && (resource.getId() != null)) {
            childrenResourceList = this.resourceService.find("from Resource where parent = ? order by sort asc", new Object[] { resource });
        } else {
            childrenResourceList = this.resourceService.find("from Resource where parent is null order by sort asc", new Object[0]);
        }
        List optionList = new ArrayList();
        if ((childrenResourceList != null) && (childrenResourceList.size() > 0)) {
            for (com.chee.entity.Resource children : childrenResourceList) {
                LinkedHashMap map = new LinkedHashMap();
                map.put("data", children.getName());
                LinkedHashMap attrMap = new LinkedHashMap();
                attrMap.put("id", children.getId().toString());
                attrMap.put("title", children.getName() == null ? "" : children.getName());
                if (children.getChildren().size() > 0) {
                    map.put("state", "closed");
                    map.put("children", getChildrenResource(children));
                }
                JSONObject attrObject = JSONObject.fromObject(attrMap);
                map.put("attr", attrObject);
                optionList.add(map);
            }
        }
        return optionList;
    }

    public Role getRole() {
        return this.role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<com.chee.entity.Resource> getAllResource() {
        this.allResource = this.resourceService.getAll();
        return this.allResource;
    }

    public void setAllResource(List<com.chee.entity.Resource> allResource) {
        this.allResource = allResource;
    }

    public com.chee.entity.Resource getResource() {
        return this.resource;
    }

    public void setResource(com.chee.entity.Resource resource) {
        this.resource = resource;
    }

    public String getResourceIdStr() {
        return this.resourceIdStr;
    }

    public void setResourceIdStr(String resourceIdStr) {
        this.resourceIdStr = resourceIdStr;
    }
}
