package com.chee.service;

import com.chee.common.Page;
import com.chee.entity.BusinessCustomer;
import java.util.List;
import java.util.Map;

public abstract interface BusinessCustomerService
  extends BaseService<BusinessCustomer, Integer>
{
  public abstract Page<BusinessCustomer> findPage(Page<BusinessCustomer> paramPage, BusinessCustomer paramBusinessCustomer);
  
  public abstract List<BusinessCustomer> findOrderID(String paramString);
  
  public abstract String doBusinessCustomerSaveOrUpdate(BusinessCustomer paramBusinessCustomer, String paramString);
  
  public abstract String doBatchDelete(Integer[] paramArrayOfInteger, String paramString);
  
  public abstract int updateYjs(double paramDouble, String paramString, Integer paramInteger);
  
  public abstract int updateOrderId(Integer paramInteger, String paramString1, String paramString2);
  
  public abstract List<Map<String, Object>> reportBusinessCustomer(BusinessCustomer paramBusinessCustomer);
  
  public abstract List<Map<String, Object>> reportBusinessCustomerTotal(BusinessCustomer paramBusinessCustomer);
}


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.service.BusinessCustomerService
 * JD-Core Version:    0.7.0.1
 */