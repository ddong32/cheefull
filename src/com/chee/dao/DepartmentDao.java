package com.chee.dao;

import com.chee.entity.Department;
import java.util.List;

public abstract interface DepartmentDao extends BaseDao<Department, Integer> {
    public abstract List<String> UnParentOrgCodeById(Integer paramInteger);

    public abstract List<Department> getConnectOrgs(Integer paramInteger);
}