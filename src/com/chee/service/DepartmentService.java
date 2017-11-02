package com.chee.service;

import com.chee.entity.Department;
import java.util.List;

public abstract interface DepartmentService
  extends BaseService<Department, Integer>
{
  public abstract List<String> UnParentOrgCodeById(Integer paramInteger);
  
  public abstract List<Department> getConnectOrgs(Integer paramInteger);
  
  public abstract List<String> getConnectOrgCodes(Integer paramInteger);
}


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.service.DepartmentService
 * JD-Core Version:    0.7.0.1
 */