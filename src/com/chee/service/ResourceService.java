package com.chee.service;

import com.chee.entity.Resource;
import java.util.List;

public abstract interface ResourceService
  extends BaseService<Resource, Integer>
{
  public abstract List<Resource> getChildrenResourceList(Resource paramResource);
  
  public abstract List<Resource> getRootResourceList();
  
  public abstract List<Resource> getLevel1MenuList(String paramString);
  
  public abstract List<Resource> getLevel2MenuList(String paramString);
  
  public abstract List<Resource> getLevel3MenuList(String paramString, Integer paramInteger);
}


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.service.ResourceService
 * JD-Core Version:    0.7.0.1
 */