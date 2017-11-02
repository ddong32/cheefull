package com.chee.service;

import com.chee.common.Page;
import com.chee.entity.Business;
import java.util.Date;
import java.util.List;

public abstract interface BusinessService
  extends BaseService<Business, Integer>
{
  public abstract Page<Business> findBusinessPage(Page<Business> paramPage, Business paramBusiness);
  
  public abstract List<Business> findBusinessListTotal(Business paramBusiness);
  
  public abstract int updateBusinessStat(Integer paramInteger, String paramString);
  
  public abstract Business findBusinessRecountJe(String paramString);
  
  public abstract long noGenerate();
  
  public abstract List<String> getcfsjorderid(Date paramDate);
  
  public abstract void updateOrderId(Business paramBusiness)
    throws Exception;
}


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.service.BusinessService
 * JD-Core Version:    0.7.0.1
 */