package com.chee.dao;

import com.chee.common.Page;
import com.chee.entity.BusinessCustomer;
import java.util.List;

public abstract interface BusinessCustomerDao
  extends BaseDao<BusinessCustomer, Integer>
{
  public abstract Page<BusinessCustomer> findPage(Page<BusinessCustomer> paramPage, BusinessCustomer paramBusinessCustomer);
  
  public abstract List<BusinessCustomer> findOrderID(String paramString);
}


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.dao.BusinessCustomerDao
 * JD-Core Version:    0.7.0.1
 */