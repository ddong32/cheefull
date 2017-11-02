package com.chee.dao;

import com.chee.common.Page;
import com.chee.entity.Business;
import java.util.Date;
import java.util.List;

public abstract interface BusinessDao
  extends BaseDao<Business, Integer>
{
  public abstract Page<Business> findBusinessPage(Page<Business> paramPage, Business paramBusiness);
  
  public abstract List<Business> findBusinessListTotal(Business paramBusiness);
  
  public abstract int updateBusinessStat(Integer paramInteger, String paramString);
  
  public abstract Business findBusinessRecountJe(String paramString);
  
  public abstract long noGenerate();
  
  public abstract List<String> getcfsjorderid(Date paramDate);
}


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.dao.BusinessDao
 * JD-Core Version:    0.7.0.1
 */