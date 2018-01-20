package com.chee.service.impl;

import com.chee.common.Page;
import com.chee.dao.BusinessCustomerDao;
import com.chee.entity.Account;
import com.chee.entity.Business;
import com.chee.entity.BusinessCustomer;
import com.chee.entity.Customer;
import com.chee.service.AccountService;
import com.chee.service.BusinessCustomerService;
import com.chee.service.BusinessService;
import com.chee.util.DoubleUtil;
import java.io.PrintStream;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class BusinessCustomerServiceImpl extends BaseServiceImpl<BusinessCustomer, Integer> implements BusinessCustomerService {
    @Resource
    private BusinessCustomerDao businessCustomerDao;
    @Resource
    private BusinessService businessService;
    @Resource
    private AccountService accountService;

    @Resource
    public void setBaseDao(BusinessCustomerDao businessCustomerDao) {
        super.setBaseDao(businessCustomerDao);
    }

    public Page<BusinessCustomer> findPage(Page<BusinessCustomer> page, BusinessCustomer businessCustomer) {
        return this.businessCustomerDao.findPage(page, businessCustomer);
    }

    public List<BusinessCustomer> findOrderID(String order_id) {
        return this.businessCustomerDao.findOrderID(order_id);
    }

    public String doBusinessCustomerSaveOrUpdate(BusinessCustomer businessCustomer, String sessionName) {
        String result = "";
        try {
            BusinessCustomer persistent = null;
            if (businessCustomer.getCustomer().getId() == null) {
                businessCustomer.setCustomer(null);
            }
            if ((businessCustomer != null) && (businessCustomer.getId() != null)) {
                persistent = (BusinessCustomer) this.businessCustomerDao.load(businessCustomer.getId());
                BeanUtils.copyProperties(businessCustomer, persistent, new String[] { "id", "lrr", "lrsj", "gxsj", "yjs" });
                persistent.setGxsj(new Date());
                this.businessCustomerDao.update(persistent);
                updateBusinessInfo(businessCustomer.getOrderId());
            } else {
                businessCustomer.setLrr(sessionName);
                businessCustomer.setLrsj(new Date());
                businessCustomer.setYjs(0.0D);
                this.businessCustomerDao.save(businessCustomer);
                updateBusinessInfo(businessCustomer.getOrderId());
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            result = e.toString();
        }
        return result;
    }

    public String doBatchDelete(Integer[] ids, String orderId) {
        String result = "";
        try {
            String customerDwmc = "";
            if ((ids != null) && (orderId != null) && (!"".equals(orderId))) {
                Integer[] arrayOfInteger;
                int j = (arrayOfInteger = ids).length;
                for (int i = 0; i < j; i++) {
                    int id = arrayOfInteger[i].intValue();
                    List<Account> accountList = this.accountService.findAccountList(Integer.valueOf(id), orderId);
                    for (Account account : accountList) {
                        if (account.getBusinessCustomer() != null) {
                            if (account.getBusinessCustomer().getCustomer() != null) {
                                if (account.getBusinessCustomer().getCustomer().getAreaName() == null) {
                                    customerDwmc = customerDwmc + " " + account.getBusinessCustomer().getCustomer().getAreaName();
                                }
                            }
                        }
                    }
                }
                if ((!"".equals(customerDwmc)) && (customerDwmc != "")) {
                    result = "操作失败：已增加收款项目，" + customerDwmc;
                } else {
                    this.businessCustomerDao.delete(ids);
                    updateBusinessInfo(orderId);
                    result = "操作完成!";
                }
            } else {
                result = "没有选中数据,操作失败!ids=[" + ids + "], orderId=[" + orderId + "]";
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            result = "[BCS_doBatchDelete]：操作失败!ids=[" + ids + "], orderId=[" + orderId + "] " + e.toString();
        }
        return result;
    }

    private void updateBusinessInfo(String orderId) {
        double hjygs = 0.0D;
        int sumAdultNo = 0;
        int sumChildNo = 0;
        int sumEscortNo = 0;
        List<BusinessCustomer> businessCustomerList = this.businessCustomerDao.getList("orderId", orderId);
        for (BusinessCustomer businessCustomer : businessCustomerList) {
            hjygs = DoubleUtil.add(Double.valueOf(hjygs), Double.valueOf(businessCustomer.getYgs())).doubleValue();
            sumAdultNo += Integer.valueOf(businessCustomer.getAdultNo()).intValue();
            sumChildNo += Integer.valueOf(businessCustomer.getChildNo()).intValue();
            sumEscortNo += Integer.valueOf(businessCustomer.getEscortNo()).intValue();
        }
        Business persistent = (Business) this.businessService.get("orderId", orderId);
        persistent.setYgs(hjygs);
        persistent.setAdultNo(Integer.valueOf(sumAdultNo));
        persistent.setChildNo(Integer.valueOf(sumChildNo));
        persistent.setEscortNo(Integer.valueOf(sumEscortNo));
        this.businessService.update(persistent);
    }

    public int updateYjs(double yjs, String order_id, Integer id) {
        String sql = "update chee_business_customer t set t.yjs = " + yjs + " where t.order_id = '" + order_id + "' and t.id=" + id;
        return getSession().createSQLQuery(sql).executeUpdate();
    }

    public int updateOrderId(Integer businessId, String orderIdold, String orderIdnew) {
        String sql = "update chee_business_customer t set t.order_id = '" + orderIdnew + "' where t.order_id = '" + orderIdold + "' and t.business_id=" + businessId;
        return getSession().createSQLQuery(sql).executeUpdate();
    }

    public List<Map<String, Object>> reportBusinessCustomer(BusinessCustomer businessCustomer) {
        String hqlSb = sqlBusinessCustomer(businessCustomer);
        SQLQuery query = getSession().createSQLQuery(hqlSb.toString());
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map<String, Object>> list = query.list();
        return list;
    }

    private String sqlBusinessCustomer(BusinessCustomer businessCustomer) {
        String beginDate = businessCustomer.getBeginDate();
        String endDate = businessCustomer.getEndDate();
        String parent_id = businessCustomer.getParent_id();
        String uid = businessCustomer.getUid();
        String type = businessCustomer.getType();
        String areaName = businessCustomer.getCustomerDwmc();

        StringBuffer hqlSb = new StringBuffer();
        if (type == null) {
            hqlSb.append(
                    "select r.* from (select id, area_name, sum(adult_no) adult_no, sum(child_no) child_no, sum(escort_no) escort_no, sum(ygs) ygs, sum(yjs) yjs, sum(wsk) wsk, '1' type, 'closed' state from (");
            hqlSb.append("  SELECT ");
            hqlSb.append("    substr(c.path, 1, (locate(',', c.path) - 1)) as id, ");
            hqlSb.append("    substr(c.path_name, 1, (locate('|', c.path_name) - 1)) as area_name, ");
            hqlSb.append("    a.adult_no, ");
            hqlSb.append("    a.child_no, ");
            hqlSb.append("    a.escort_no, ");
            hqlSb.append("    a.ygs, ");
            hqlSb.append("    a.yjs, ");
            hqlSb.append("    (a.ygs - a.yjs) wsk,");
            hqlSb.append("    c.sort ");
            hqlSb.append("\tFROM ");
            hqlSb.append("    chee_business_customer a, chee_business b, chee_customer c ");
            hqlSb.append("\tWHERE 1=1 ");
            hqlSb.append("and a.business_id = b.id ");
            hqlSb.append("and a.customer_id = c.id ");
            if ((beginDate != null) && (!"".equals(beginDate))) {
                hqlSb.append(" and date_format(b.cfsj,'%Y-%m-%d') >= '" + beginDate + "' ");
            }
            if ((endDate != null) && (!"".equals(endDate))) {
                hqlSb.append(" and date_format(b.cfsj,'%Y-%m-%d') <= '" + endDate + "' ");
            }
            if ((uid != null) && (!"".equals(uid))) {
                hqlSb.append(" and b.user_id in (" + uid + ") ");
            }
            if ((areaName != null) && (!"".equals(areaName))) {
                hqlSb.append(" and c.path_name like '%" + areaName + "%' ");
            }
            hqlSb.append(") report group by id, area_name) r, chee_customer s where r.id = s.id and s.type=0 order by s.sort");
        } else if ("1".equals(type)) {
            hqlSb.append(
                    "select id, area_name, parent_id, sum(adult_no) adult_no, sum(child_no) child_no, sum(escort_no) escort_no, sum(ygs) ygs, sum(yjs) yjs, sum(wsk) wsk, '2' type, 'closed' state from (");
            hqlSb.append("\tSELECT ");
            hqlSb.append("    substr(substring_index(c.path,',',2), length(substring_index(c.path,',',1))+2) as id, ");
            hqlSb.append("    replace(substring_index(c.path_name,'|',2), substr(c.path_name, 1, (locate('|', c.path_name))), '') as area_name, ");
            hqlSb.append("    substring_index(c.path,',',1) parent_id, ");
            hqlSb.append("    a.adult_no, ");
            hqlSb.append("    a.child_no, ");
            hqlSb.append("    a.escort_no, ");
            hqlSb.append("    a.ygs, ");
            hqlSb.append("    a.yjs, ");
            hqlSb.append("    (a.ygs - a.yjs) wsk,");
            hqlSb.append("    c.sort ");
            hqlSb.append("  FROM ");
            hqlSb.append("    chee_business_customer a, chee_business b, chee_customer c ");
            hqlSb.append("\tWHERE 1=1 ");
            hqlSb.append("and a.business_id = b.id ");
            hqlSb.append("and a.customer_id = c.id ");
            if ((beginDate != null) && (!"".equals(beginDate))) {
                hqlSb.append(" and date_format(b.cfsj,'%Y-%m-%d') >= '" + beginDate + "' ");
            }
            if ((endDate != null) && (!"".equals(endDate))) {
                hqlSb.append(" and date_format(b.cfsj,'%Y-%m-%d') <= '" + endDate + "' ");
            }
            if (!"".equals(parent_id)) {
                hqlSb.append("\tand substring_index(c.path,',',1) = " + parent_id);
            }
            if ((uid != null) && (!"".equals(uid))) {
                hqlSb.append(" and b.user_id in (" + uid + ") ");
            }
            if ((areaName != null) && (!"".equals(areaName))) {
                hqlSb.append(" and c.path_name like '%" + areaName + "%' ");
            }
            hqlSb.append(") report group by id, area_name, parent_id order by area_name");
        } else if ("2".equals(type)) {
            hqlSb.append("SELECT");
            hqlSb.append(" c.id,");
            hqlSb.append(" c.area_name,");
            hqlSb.append(" c.parent_id,");
            hqlSb.append(" a.adult_no,");
            hqlSb.append(" a.child_no,");
            hqlSb.append(" a.escort_no,");
            hqlSb.append(" a.ygs,");
            hqlSb.append(" a.yjs,");
            hqlSb.append(" (a.ygs - a.yjs) wsk,");
            hqlSb.append(" a.lrr,");
            hqlSb.append(" b.order_id,");
            hqlSb.append(" b.travel_line ");
            hqlSb.append("FROM chee_business_customer a, chee_business b, chee_customer c ");
            hqlSb.append(" where 1=1 ");
            hqlSb.append("and a.business_id = b.id ");
            hqlSb.append("and a.customer_id = c.id ");
            if ((beginDate != null) && (!"".equals(beginDate))) {
                hqlSb.append(" and date_format(b.cfsj,'%Y-%m-%d') >= '" + beginDate + "' ");
            }
            if ((endDate != null) && (!"".equals(endDate))) {
                hqlSb.append(" and date_format(b.cfsj,'%Y-%m-%d') <= '" + endDate + "' ");
            }
            if (!"".equals(parent_id)) {
                hqlSb.append("\tand c.parent_id = " + parent_id);
            }
            if ((uid != null) && (!"".equals(uid))) {
                hqlSb.append(" and b.user_id in (" + uid + ") ");
            }
            if ((areaName != null) && (!"".equals(areaName))) {
                hqlSb.append(" and c.path_name like '%" + areaName + "%' ");
            }
            hqlSb.append(" order by c.area_name, b.order_id ");
        }
        return hqlSb.toString();
    }

    public List<Map<String, Object>> reportBusinessCustomerTotal(BusinessCustomer businessCustomer) {
        String beginDate = businessCustomer.getBeginDate();
        String endDate = businessCustomer.getEndDate();
        String parent_id = businessCustomer.getParent_id();
        String uid = businessCustomer.getUid();
        String areaName = businessCustomer.getCustomerDwmc();

        StringBuffer hqlSb = new StringBuffer();
        hqlSb.append("SELECT");
        hqlSb.append(" '合计' area_name,");
        hqlSb.append(" sum(a.adult_no) adult_no,");
        hqlSb.append(" sum(a.child_no) child_no,");
        hqlSb.append(" sum(a.escort_no) escort_no,");
        hqlSb.append(" sum(a.ygs) ygs,");
        hqlSb.append(" sum(a.yjs) yjs,");
        hqlSb.append(" sum(a.ygs - a.yjs) wsk ");
        hqlSb.append("FROM chee_business_customer a, chee_business b, chee_customer c");
        hqlSb.append(" WHERE 1=1 ");
        hqlSb.append(" and a.business_id = b.id");
        hqlSb.append(" and a.customer_id = c.id");
        if ((beginDate != null) && (!"".equals(beginDate))) {
            hqlSb.append(" and date_format(cfsj,'%Y-%m-%d') >= '" + beginDate + "' ");
        }
        if ((endDate != null) && (!"".equals(endDate))) {
            hqlSb.append(" and date_format(cfsj,'%Y-%m-%d') <= '" + endDate + "' ");
        }
        if ((parent_id != null) && (!"".equals(parent_id))) {
            hqlSb.append(" and .parent_id = " + parent_id);
        }
        if ((uid != null) && (!"".equals(uid))) {
            hqlSb.append(" and b.user_id in (" + uid + ") ");
        }
        if ((areaName != null) && (!"".equals(areaName))) {
            hqlSb.append(" and c.path_name like '%" + areaName + "%' ");
        }
        SQLQuery query = getSession().createSQLQuery(hqlSb.toString());
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map<String, Object>> list = query.list();
        return list;
    }
}
