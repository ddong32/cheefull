package com.chee.dao;

import com.chee.common.Page;
import com.chee.entity.BusinessCustomer;
import java.util.List;

public abstract interface BusinessCustomerDao extends BaseDao<BusinessCustomer, Integer> {
    public abstract Page<BusinessCustomer> findPage(Page<BusinessCustomer> paramPage, BusinessCustomer paramBusinessCustomer);

    public abstract List<BusinessCustomer> findOrderID(String paramString);
}