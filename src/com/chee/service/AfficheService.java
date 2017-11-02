package com.chee.service;

import com.chee.entity.Affiche;
import java.util.List;

public abstract interface AfficheService
  extends BaseService<Affiche, Integer>
{
  public abstract void doBatchDelete(Integer[] paramArrayOfInteger);
  
  public abstract List<Affiche> getMainPageAfficheList();
}


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.service.AfficheService
 * JD-Core Version:    0.7.0.1
 */