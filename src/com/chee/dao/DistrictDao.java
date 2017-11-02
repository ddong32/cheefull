package com.chee.dao;

import com.chee.entity.District;
import java.util.List;

public abstract interface DistrictDao
  extends BaseDao<District, Integer>
{
  public abstract List<District> getDistrict(Integer paramInteger);
}


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.dao.DistrictDao
 * JD-Core Version:    0.7.0.1
 */