package com.chee.service;

import com.chee.entity.District;
import java.util.List;

public abstract interface DistrictService
  extends BaseService<District, Integer>
{
  public abstract List<District> getDistrict(Integer paramInteger);
}


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.service.DistrictService
 * JD-Core Version:    0.7.0.1
 */