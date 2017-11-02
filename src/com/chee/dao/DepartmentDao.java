package com.chee.dao;

import com.chee.entity.Department;
import java.util.List;

public abstract interface DepartmentDao
  extends BaseDao<Department, Integer>
{
  public abstract List<String> UnParentOrgCodeById(Integer paramInteger);
  
  public abstract List<Department> getConnectOrgs(Integer paramInteger);
}


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.dao.DepartmentDao
 * JD-Core Version:    0.7.0.1
 */