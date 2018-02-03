package com.chee.dao.impl;

import com.chee.common.Page;
import com.chee.dao.AccountDao;
import com.chee.entity.Account;
import com.chee.entity.Business;
import com.chee.entity.BusinessCustomer;
import com.chee.entity.Cooperator;
import com.chee.entity.User;
import com.chee.util.UserUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

@Repository
public class AccountDaoImpl extends BaseDaoImpl<Account, Integer> implements AccountDao {
    @Resource
    UserUtil userUtil;

    public User getLoginUser() {
        return this.userUtil.getLoginUser();
    }

    public List<Account> findOrderID(String order_id) {
        StringBuffer hqlSb = new StringBuffer();
        hqlSb.append("FROM Account WHERE 1=1");
        hqlSb.append(" and orderId = '" + order_id + "' ");
        hqlSb.append("order by sflx, businessCustomer, stat desc, lrsj");
        return getSession().createQuery(hqlSb.toString()).list();
    }

    public List<Account> findAccountList(Integer id, String order_id) {
        StringBuffer hqlSb = new StringBuffer();
        hqlSb.append("FROM Account WHERE 1=1");
        hqlSb.append(" and businessCustomer.id =" + id + " and orderId = '" + order_id + "' ");
        return getSession().createQuery(hqlSb.toString()).list();
    }

    public Page<Account> findBusinessPage(Page<Account> page, Account account) {
        StringBuffer hqlSb = new StringBuffer();
        hqlSb.append("select a.id,");
        hqlSb.append(" a.sflx,");
        hqlSb.append(" a.orderId,");
        hqlSb.append(" a.sffs,");
        hqlSb.append(" b.customerDwmc,");
        hqlSb.append(" a.skje,");
        hqlSb.append(" a.fkje,");
        hqlSb.append(" a.lrsj,");
        hqlSb.append(" a.lrr,");
        hqlSb.append(" a.shsj,");
        hqlSb.append(" a.shr,");
        hqlSb.append(" a.stat");
        hqlSb.append(" from Account a left join a.businessCustomer b where 1=1 ");

        Map<String, Object> map = new HashMap();
        if ((account.getBeginDate() != null) && (!"".equals(account.getBeginDate()))) {
            hqlSb.append("and date_format(a.lrsj,'%Y-%m-%d') >= '" + account.getBeginDate() + "'");
        }
        if ((account.getEndDate() != null) && (!"".equals(account.getEndDate()))) {
            hqlSb.append("and date_format(a.lrsj,'%Y-%m-%d') <= '" + account.getEndDate() + "'");
        }
        if ((account.getStat() != null) && (!"".equals(account.getStat()))) {
            hqlSb.append("and a.stat =:stat ");
            map.put("stat", account.getStat());
        }
        hqlSb.append("order by a.lrsj desc ");

        return findPage(page, hqlSb.toString(), map);
    }

    public List<Account> findAccountIds(Integer[] account_ids) {
        StringBuffer hqlSb = new StringBuffer();
        hqlSb.append("FROM Account WHERE 1=1 and id in (:account_ids)");
        Map<String, Object> map = new HashMap();
        map.put("account_ids", account_ids);
        return find(hqlSb.toString(), map);
    }

    public List<Account> findAccountCountJe(String order_id) {
        StringBuffer hqlSb = new StringBuffer();
        hqlSb.append("FROM Account WHERE 1=1");
        hqlSb.append(" and orderId = '" + order_id + "'");
        hqlSb.append(" and stat = '3' ");
        return getSession().createQuery(hqlSb.toString()).list();
    }

    public List<Account> findAccountCountYfk(String order_id) {
        StringBuffer hqlSb = new StringBuffer();
        hqlSb.append("FROM Account WHERE 1=1");
        hqlSb.append(" and orderId = '" + order_id + "'");
        return getSession().createQuery(hqlSb.toString()).list();
    }

    public List<Account> findAccountCountYjs(String order_id, Integer b_c_id) {
        StringBuffer hqlSb = new StringBuffer();
        hqlSb.append("FROM Account WHERE 1=1");
        hqlSb.append(" and stat = 3");
        hqlSb.append(" and orderId = '" + order_id + "'");
        hqlSb.append(" and businessCustomer.id = '" + b_c_id + "'");
        return getSession().createQuery(hqlSb.toString()).list();
    }

    public Page<Account> findOnTrustPage(Page<Account> page, Account account) {
        StringBuffer hqlSb = new StringBuffer();
        hqlSb.append("select");
        hqlSb.append(" a.id,");
        hqlSb.append(" a.sflx,");
        hqlSb.append(" a.orderId,");
        hqlSb.append(" c.customerDwmc,");
        hqlSb.append(" b.travelLine,");
        hqlSb.append(" b.cfsj,");
        hqlSb.append(" a.lrr,");
        hqlSb.append(" a.skje,");
        hqlSb.append(" a.fkje,");
        hqlSb.append(" a.sffs,");
        hqlSb.append(" a.stat,");
        hqlSb.append(" b.id,");
        hqlSb.append(" c.parentId,");
        hqlSb.append(" c.ygs,");
        hqlSb.append(" c.yjs,");
        hqlSb.append(" c.ygs - c.yjs");
        hqlSb.append(" from Account a left join a.business b left join a.businessCustomer c ");
        hqlSb.append(" where 1=1 ");

        Map<String, Object> map = new HashMap();
        if ((account.getBeginDate() != null) && (!"".equals(account.getBeginDate()))) {
            hqlSb.append("and date_format(b.cfsj,'%Y-%m-%d')>='" + account.getBeginDate() + "' ");
        }
        if ((account.getEndDate() != null) && (!"".equals(account.getEndDate()))) {
            hqlSb.append("and date_format(b.cfsj,'%Y-%m-%d') <= '" + account.getEndDate() + "' ");
        }
        if ((account.getStat() != null) && (!"".equals(account.getStat()))) {
            hqlSb.append("and a.stat=:stat ");
            map.put("stat", account.getStat());
        }
        if ((account.getBusinessCustomer() != null) && (account.getBusinessCustomer().getCustomerDwmc() != null) && (!"".equals(account.getBusinessCustomer().getCustomerDwmc()))) {
            hqlSb.append("and c.customerDwmc like:customerDwmc ");
            map.put("customerDwmc", "%" + account.getBusinessCustomer().getCustomerDwmc() + "%");
        }
        if ((account.getBusiness() != null) && (account.getBusiness().getDdzt() != null) && (!"".equals(account.getBusiness().getDdzt()))) {
            hqlSb.append("and b.ddzt =:ddzt ");
            map.put("ddzt", account.getBusiness().getDdzt());
        }
        if (getLoginUser().getRoleValues().contains("ROLE_ONE")) {
            hqlSb.append("and b.lrr =:lrr ");
            map.put("lrr", getLoginUser().getName());
        }
        hqlSb.append("and a.sflx = '1' and a.sffs = '3' order by b.cfsj desc ");

        return findPage(page, hqlSb.toString(), map);
    }

    public Page<Account> findOnPaymentPage(Page<Account> page, Account account) {
        StringBuffer hqlSb = new StringBuffer();
        hqlSb.append("select");
        hqlSb.append(" a.id,");
        hqlSb.append(" a.sflx,");
        hqlSb.append(" a.orderId,");
        hqlSb.append(" c.dwmc,");
        hqlSb.append(" b.travelLine,");
        hqlSb.append(" a.gxsj,");
        hqlSb.append(" a.user.name,");
        hqlSb.append(" a.skje,");
        hqlSb.append(" a.fkje,");
        hqlSb.append(" a.sffs,");
        hqlSb.append(" a.stat,");
        hqlSb.append(" b.id,");
        hqlSb.append(" b.ygs,");
        hqlSb.append(" b.ygf,");
        hqlSb.append(" b.yjs,");
        hqlSb.append(" b.ygs - b.yjs,");
        hqlSb.append(" b.ygs - b.ygf,");
        hqlSb.append(" c.id,");
        hqlSb.append(" b.ddlx");

        hqlSb.append(" from Account a left join a.business b left join a.cooperator c ");
        hqlSb.append(" where 1=1 ");

        Map<String, Object> map = new HashMap();
        if ((account.getBeginDate() != null) && (!"".equals(account.getBeginDate()))) {
            hqlSb.append("and date_format(b.cfsj,'%Y-%m-%d')>='" + account.getBeginDate() + "' ");
        }
        if ((account.getEndDate() != null) && (!"".equals(account.getEndDate()))) {
            hqlSb.append("and date_format(b.cfsj,'%Y-%m-%d') <= '" + account.getEndDate() + "' ");
        }
        if ((account != null) && (account.getOrderId() != null) && (!"".equals(account.getOrderId()))) {
            hqlSb.append("and a.orderId like '" + account.getOrderId() + "%' ");
        }
        if ((account.getStat() != null) && (!"".equals(account.getStat()))) {
            hqlSb.append("and a.stat=:stat ");
            map.put("stat", account.getStat());
        } else {
            hqlSb.append("and a.stat in ('1','2','9') ");
        }
        if ((account.getCooperator() != null) && (account.getCooperator().getDwmc() != null) && (!"".equals(account.getCooperator().getDwmc()))) {
            if (("1".equals(account.getJqchecked())) || (account.getJqchecked() == "1")) {
                hqlSb.append("and c.dwmc=:dwmc ");
                map.put("dwmc", account.getCooperator().getDwmc());
            } else {
                hqlSb.append("and c.dwmc like:dwmc ");
                map.put("dwmc", "%" + account.getCooperator().getDwmc() + "%");
            }
        }
        if ((account.getBusiness() != null) && (account.getBusiness().getDdzt() != null) && (!"".equals(account.getBusiness().getDdzt()))) {
            hqlSb.append("and b.ddzt =:ddzt ");
            map.put("ddzt", account.getBusiness().getDdzt());
        }
        if (getLoginUser().getRoleValues().contains("ROLE_ONE")) {
            hqlSb.append("and a.user.id =:lrr ");
            map.put("lrr", getLoginUser().getId());
        }
        if ((account.getBusiness() != null) && (account.getBusiness().getDdlx() != null) && (!"".equals(account.getBusiness().getDdlx()))) {
            hqlSb.append("and b.ddlx = '" + account.getBusiness().getDdlx() + "' ");
        }
        hqlSb.append("and a.sflx = '2' and a.sffs = '3' order by a.stat, b.cfsj desc ");
        page.setPageSize(1000);
        return findPage(page, hqlSb.toString(), map);
    }

    public List<Account> findAccountUnique(Integer id) {
        StringBuffer hqlSb = new StringBuffer();
        hqlSb.append("FROM Account WHERE 1=1 and id = " + id);
        return getSession().createQuery(hqlSb.toString()).list();
    }

    public int updateAccountBz(Account account) {
        String sql = "update chee_account t set t.bz = '" + account.getBz() + "' where t.id=" + account.getId();
        return getSession().createSQLQuery(sql).executeUpdate();
    }
}
