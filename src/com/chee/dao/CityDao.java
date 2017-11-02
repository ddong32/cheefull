package com.chee.dao;

import com.chee.entity.City;
import java.util.List;

public abstract interface CityDao
  extends BaseDao<City, Integer>
{
  public abstract List<City> getCity(Integer paramInteger);
}


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.dao.CityDao
 * JD-Core Version:    0.7.0.1
 */