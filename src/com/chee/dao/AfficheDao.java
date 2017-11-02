package com.chee.dao;

import com.chee.entity.Affiche;
import java.util.List;

public abstract interface AfficheDao
  extends BaseDao<Affiche, Integer>
{
  public abstract List<Affiche> getMainPageAfficheList();
}


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.dao.AfficheDao
 * JD-Core Version:    0.7.0.1
 */