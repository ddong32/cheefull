package com.chee.action.admin;

import com.chee.entity.Department;
import com.chee.service.DepartmentService;
import com.chee.util.HibernateUtils;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.Validations;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Resource;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.hibernate.Hibernate;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;

@ParentPackage("admin")
public class DepartmentAction extends BaseAction<Department, Integer> {
    private static final long serialVersionUID = 6254431866456845575L;
    private Department department;
    private Department parent;
    private String addressIdStr;
    private String selfId;
    @Resource
    private DepartmentService departmentService;

    public String input() {
        if ((this.parent != null) && (this.parent.getId() != null)) {
            this.parent = ((Department) this.departmentService.get(this.parent.getId()));
        }
        if ((this.department != null) && (this.department.getId() != null)) {
            this.department = ((Department) this.departmentService.get(this.department.getId()));
        } else {
            this.department = new Department();
            if (!getLoginUser().getRoleValues().contains("ROLE_ADMIN")) {
                this.parent = ((Department) this.departmentService.get(getLoginUser().getDepartment().getId()));
            } else {
                this.parent = new Department();
            }
        }
        return "input";
    }

    public String list() {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Department.class);
        if (!getLoginUser().getRoleValues().contains("ROLE_ADMIN")) {
            detachedCriteria.add(Restrictions.like("path", getLoginUser().getDepartment().getPath() + "%"));
        }
        if (this.department != null) {
            if ((this.department.getName() != null) && (!"".equals(this.department.getName()))) {
                detachedCriteria.add(Restrictions.sqlRestriction("org_name like ? escape '/'", HibernateUtils.escapeSQLLike(this.department.getName()), Hibernate.STRING));
            }
            if ((this.department.getCode() != null) && (!"".equals(this.department.getCode()))) {
                detachedCriteria.add(Restrictions.sqlRestriction("org_code like ? escape '/'", HibernateUtils.escapeSQLLike(this.department.getCode()), Hibernate.STRING));
            }
        }
        this.page.setOrderBy("path");
        this.page.setOrder("asc");
        this.page = this.departmentService.findPage(this.page, detachedCriteria);
        return "list";
    }

    public String delete() {
        this.departmentService.delete(this.department.getId());
        this.redirectionUrl = "department!list.action";
        return "success";
    }

    @Validations(requiredStrings = { @com.opensymphony.xwork2.validator.annotations.RequiredStringValidator(fieldName = "department.name", message = "组织结构名称不允许为空!"),
            @com.opensymphony.xwork2.validator.annotations.RequiredStringValidator(fieldName = "department.code", message = "组织结构编码不允许为空!") }, stringLengthFields = {
            @com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator(fieldName = "department.name", maxLength = "15", message = "组织结构名称长度不允许大于${maxLength}!"),
            @com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator(fieldName = "department.code", maxLength = "10", message = "组织结构编码长度不允许大于${maxLength}!") }, intRangeFields = { @com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator(fieldName = "department.sort", min = "0", message = "组织结构排序必须为零或正整数!") })
    @InputConfig(resultName = "error")
    public String save() {
        if (this.department.getId() != null) {
            Department persistent = (Department) this.departmentService.load(this.department.getId());
            if (!this.departmentService.isUnique("name", persistent.getName(), this.department.getName())) {
                addActionError("组织结构名称已存在!");
                return "error";
            }
            if (!this.departmentService.isUnique("code", persistent.getCode(), this.department.getCode())) {
                addActionError("组织结构编码已存在!");
                return "error";
            }
            BeanUtils.copyProperties(this.department, persistent, new String[] { "id", "createDate", "updateDate", "path", "parent", "children" });
            if (this.parent.getId() != null) {
                persistent.setParent((Department) this.departmentService.get(this.parent.getId()));
            } else {
                persistent.setParent(null);
            }
            persistent.setUpdateDate(new Date());
            this.departmentService.update(persistent);
        } else {
            if (!this.departmentService.isUnique("name", null, this.department.getName())) {
                addActionError("组织结构名称已存在!");
                return "error";
            }
            if (!this.departmentService.isUnique("code", null, this.department.getCode())) {
                addActionError("组织结构编码已存在!");
                return "error";
            }
            if (this.parent.getId() != null) {
                this.department.setParent((Department) this.departmentService.get(this.parent.getId()));
            } else {
                this.department.setParent(null);
            }
            this.department.setCreateDate(new Date());
            this.department.setUpdateDate(new Date());
            this.departmentService.save(this.department);
        }
        this.redirectionUrl = "department!list.action";
        return "success";
    }

    public String ajaxDepartmentTree() {
        if ((this.selfId == null) || (this.selfId.equals(""))) {
            this.selfId = "0";
        }
        List<Department> childrenDepartmentList = new ArrayList();
        if (getLoginUser().getRoleValues().contains("ROLE_ADMIN")) {
            if ((this.department != null) && (this.department.getId() != null)) {
                childrenDepartmentList = this.departmentService.find("from Department where parent = ? and id != ? ", new Object[] { this.department, Integer.valueOf(Integer.parseInt(this.selfId)) });
            } else {
                childrenDepartmentList = this.departmentService.find("from Department where parent is null and id != ? ", new Object[] { Integer.valueOf(Integer.parseInt(this.selfId)) });
            }
        } else if ((this.department != null) && (this.department.getId() != null)) {
            childrenDepartmentList = this.departmentService.find("from Department where parent.id = ? and id != ? and path like ? ",
                    new Object[] { this.department.getId(), Integer.valueOf(Integer.parseInt(this.selfId)), getLoginUser().getDepartment().getPath() + "%" });
        } else {
            childrenDepartmentList = this.departmentService.find("from Department where id = ? and id != ? ",
                    new Object[] { getLoginUser().getDepartment().getId(), Integer.valueOf(Integer.parseInt(this.selfId)) });
        }
        List optionList = new ArrayList();
        for (Department department : childrenDepartmentList) {
            Map map = new HashMap();
            if (department.getChildren().size() > 0) {
                map.put("state", "closed");
            }
            map.put("data", department.getName() + "(" + department.getCode() + ")");
            Map attrMap = new HashMap();
            attrMap.put("id", department.getId().toString());
            attrMap.put("title", department.getName());
            attrMap.put("code", department.getCode());
            attrMap.put("Class", department.getName());
            JSONObject attrObject = JSONObject.fromObject(attrMap);
            map.put("attr", attrObject);
            optionList.add(map);
        }
        JSONArray jsonArray = JSONArray.fromObject(optionList);

        return ajaxJson(jsonArray.toString());
    }

    public String ajaxAddressTree() {
        Set checkSet = new HashSet();
        if ((this.addressIdStr != null) && (!this.addressIdStr.equals(""))) {
            for (String id : this.addressIdStr.split(",")) {
                checkSet.add(Integer.valueOf(Integer.parseInt(id)));
            }
        }
        List addressMapList = new ArrayList();

        JSONArray jsonArray = JSONArray.fromObject(addressMapList);
        return ajaxJson(jsonArray.toString());
    }

    public String ajaxHasAddressTree() {
        List addressMapList = new ArrayList();

        JSONArray jsonArray = JSONArray.fromObject(addressMapList);

        return ajaxJson(jsonArray.toString());
    }

    public Department getParent() {
        return this.parent;
    }

    public void setParent(Department parent) {
        this.parent = parent;
    }

    public Department getDepartment() {
        return this.department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getAddressIdStr() {
        return this.addressIdStr;
    }

    public void setAddressIdStr(String addressIdStr) {
        this.addressIdStr = addressIdStr;
    }

    public String getSelfId() {
        return this.selfId;
    }

    public void setSelfId(String selfId) {
        this.selfId = selfId;
    }
}
