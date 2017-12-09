package com.chee.service.impl;

import com.chee.common.Page;
import com.chee.dao.CustomerDao;
import com.chee.entity.Customer;
import com.chee.service.CustomerService;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl extends BaseServiceImpl<Customer, Integer> implements CustomerService {
    @Resource
    CustomerDao customerDao;

    @Resource
    public void setBaseDao(CustomerDao customerDao) {
        super.setBaseDao(customerDao);
    }

    public Page<Customer> findPage(Page<Customer> page, Customer customer) {
        return this.customerDao.findPage(page, customer);
    }

    public List<Customer> getCustomerList(Integer grade) {
        return this.customerDao.getCustomerList(grade);
    }

    public List<Customer> getPathCustomerList(Set<Integer> paramSet) {
        return this.customerDao.getPathCustomerList(paramSet);
    }
}