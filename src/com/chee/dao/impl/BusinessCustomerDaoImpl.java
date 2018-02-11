package com.chee.dao.impl;

import com.chee.common.Page;
import com.chee.dao.BusinessCustomerDao;
import com.chee.entity.BusinessCustomer;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class BusinessCustomerDaoImpl extends BaseDaoImpl<BusinessCustomer, Integer> implements BusinessCustomerDao {
    public Page<BusinessCustomer> findPage(Page<BusinessCustomer> page, BusinessCustomer businessCustomer) {
        return null;
    }

    public List<BusinessCustomer> findOrderID(String order_id) {
        List<BusinessCustomer> list = new ArrayList<BusinessCustomer>();
        StringBuffer hqlSb = new StringBuffer();
        hqlSb.append("FROM BusinessCustomer bc WHERE 1=1");
        hqlSb.append(" and bc.orderId = '" + order_id + "'");
        hqlSb.append(" order by customer.path, lrsj ");
        list = find(hqlSb.toString(), new Object[0]);
        return list;
    }
}
