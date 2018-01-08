package com.chee.service;

import com.chee.common.Page;
import com.chee.entity.Customer;
import java.util.List;
import java.util.Set;

public abstract interface CustomerService extends BaseService<Customer, Integer> {
    public abstract Page<Customer> findPage(Page<Customer> paramPage, Customer paramCustomer);

    public abstract List<Customer> getCustomerList(Integer paramInteger);

    public abstract List<Customer> getPathCustomerList(Set<Integer> paramSet);
}