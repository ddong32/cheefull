package com.chee.dao;

import com.chee.common.Page;
import com.chee.entity.Customer;
import java.util.List;
import java.util.Set;

public abstract interface CustomerDao
  extends BaseDao<Customer, Integer>
{
  public abstract Page<Customer> findPage(Page<Customer> paramPage, Customer paramCustomer);
  
  public abstract List<Customer> getCustomerList(Integer paramInteger);
  
  public abstract List<Customer> getPathCustomerList(Set<Integer> paramSet);
}


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.dao.CustomerDao
 * JD-Core Version:    0.7.0.1
 */