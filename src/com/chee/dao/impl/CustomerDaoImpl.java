package com.chee.dao.impl;

import com.chee.common.Page;
import com.chee.dao.CustomerDao;
import com.chee.entity.Customer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerDaoImpl extends BaseDaoImpl<Customer, Integer> implements CustomerDao {
    public Integer save(Customer customer) {
        Integer id = (Integer) super.save(customer);
        Customer parent = customer.getParent();
        if ((parent != null) && (parent.getPath() != null) && (!"".equals(parent.getPath()))) {
            customer.setPath(parent.getPath() + id + ",");
            customer.setPathName(parent.getPathName() + "|" + customer.getAreaName());
        } else {
            customer.setPath(id.toString() + ",");
            customer.setPathName(customer.getAreaName());
        }
        customer.setGrade(Integer.valueOf(customer.getPath().split(",").length));
        super.update(customer);
        return id;
    }

    public void update(Customer customer) {
        Customer parent = customer.getParent();
        if (parent == null) {
            customer.setPath(customer.getId().toString() + ",");
            customer.setPathName(customer.getAreaName());
        } else {
            String parentPath = parent.getPath();
            customer.setPath(parentPath + customer.getId() + ",");
            customer.setPathName(parent.getPathName() + "|" + customer.getAreaName());
        }
        customer.setGrade(Integer.valueOf(customer.getPath().split(",").length));
        super.update(customer);
    }

    public Page<Customer> findPage(Page<Customer> page, Customer customer) {
        StringBuffer hqlSb = new StringBuffer();
        hqlSb.append("select");
        hqlSb.append(" this_.id,");
        hqlSb.append(" this_.szdcs,");
        hqlSb.append(" this_.dwmc,");
        hqlSb.append(" this_.lxr,");
        hqlSb.append(" this_.lxdh,");
        hqlSb.append(" this_.lrsj,");
        hqlSb.append(" this_.czh,");
        hqlSb.append(" this_.dwdz,");
        hqlSb.append(" this_.bz,");
        hqlSb.append(" this_.qq");
        hqlSb.append(" from Customer this_");
        hqlSb.append(" where 1=1 ");

        Map<String, Object> map = new HashMap();
        if (customer != null) {
            if ((customer.getSzdsf() != null) && (!"".equals(customer.getSzdsf()))) {
                hqlSb.append("and this_.szdsf =:szdsf ");
                map.put("szdsf", customer.getSzdsf());
            }
            if ((customer.getSzdcs() != null) && (!"".equals(customer.getSzdcs()))) {
                hqlSb.append("and this_.szdcs =:szdcs ");
                map.put("szdcs", customer.getSzdcs());
            }
            if ((customer.getSzdxf() != null) && (!"".equals(customer.getSzdxf()))) {
                hqlSb.append("and this_.szdxf =:szdxf ");
                map.put("szdxf", customer.getSzdxf());
            }
            if ((customer.getAreaName() != null) && (!"".equals(customer.getAreaName()))) {
                hqlSb.append("and this_.areaName like:dwmc ");
                map.put("dwmc", "%" + customer.getAreaName() + "%");
            }
            if ((customer.getLxr() != null) && (!"".equals(customer.getLxr()))) {
                hqlSb.append("and this_.lxr like:lxr ");
                map.put("lxr", "%" + customer.getLxr() + "%");
            }
        }
        hqlSb.append("order by this_.lrsj desc");
        return findPage(page, hqlSb.toString(), map);
    }

    public List<Customer> getCustomerList(Integer grade) {
        String hql = "";
        if ((grade == null) || (grade.intValue() == 9)) {
            hql = "from Customer order by sort asc";
        } else {
            hql = "from Customer where grade <= " + grade + " order by sort asc";
        }
        return getSession().createQuery(hql).setCacheable(true).list();
    }

    public List<Customer> getPathCustomerList(Set<Integer> pathIdSet) {
        if ((pathIdSet != null) && (pathIdSet.size() > 0)) {
            StringBuffer hqlSb = new StringBuffer(" from Customer where id in (");
            int i = 0;
            for (Integer id : pathIdSet) {
                if (i == 0) {
                    hqlSb.append(id);
                } else {
                    hqlSb.append("," + id);
                }
                i++;
            }
            hqlSb.append(") order by sort asc, areaName asc ");
            return getSession().createQuery(hqlSb.toString()).setCacheable(true).list();
        }
        return null;
    }
}
