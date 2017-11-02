package com.chee.service;

import com.chee.entity.City;
import java.util.List;

public abstract interface CityService
  extends BaseService<City, Integer>
{
  public abstract List<City> getCity(Integer paramInteger);
}


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.service.CityService
 * JD-Core Version:    0.7.0.1
 */