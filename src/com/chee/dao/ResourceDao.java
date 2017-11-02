package com.chee.dao;

import com.chee.entity.Resource;
import java.util.List;

public abstract interface ResourceDao
  extends BaseDao<Resource, Integer>
{
  public abstract List<Resource> getChildrenResourceList(Resource paramResource);
  
  public abstract List<Resource> getRootResourceList();
  
  public abstract List<Resource> getLevel1MenuList(String paramString);
  
  public abstract List<Resource> getLevel2MenuList(String paramString);
  
  public abstract List<Resource> getLevel3MenuList(String paramString, Integer paramInteger);
}


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.dao.ResourceDao
 * JD-Core Version:    0.7.0.1
 */