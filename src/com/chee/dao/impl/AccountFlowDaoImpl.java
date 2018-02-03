package com.chee.dao.impl;

import com.chee.common.Page;
import com.chee.dao.AccountFlowDao;
import com.chee.entity.Account;
import com.chee.entity.AccountFlow;
import com.chee.entity.Bank;
import com.chee.entity.Business;
import com.chee.entity.User;
import com.chee.util.DateUtil;
import com.chee.util.UserUtil;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

@Repository
public class AccountFlowDaoImpl extends BaseDaoImpl<AccountFlow, Integer> implements AccountFlowDao {
    @Resource
    UserUtil userUtil;

    public User getLoginUser() {
        return this.userUtil.getLoginUser();
    }

    public Page<AccountFlow> findWaitAuditDataPage(Page<AccountFlow> page, AccountFlow accountFlow, String type) {
        StringBuffer hqlSb = new StringBuffer();
        hqlSb.append("select a.id,");
        hqlSb.append(" a.stat,");
        hqlSb.append(" a.lsh,");
        hqlSb.append(" a.sksj,");
        hqlSb.append(" a.user.name,");
        hqlSb.append(" a.lrsj,");
        hqlSb.append(" a.skje,");
        hqlSb.append(" a.fkje,");
        hqlSb.append(" b.djm,");
        hqlSb.append(" a.shr,");
        hqlSb.append(" a.shsj,");
        hqlSb.append(" a.bz,");
        hqlSb.append(" a.orderId,");
        if (("payManageChecked".equals(type)) || (type == "payManageChecked") || ("payFinaneChecked".equals(type)) || (type == "payFinaneChecked") || ("takePayment".equals(type))
                || (type == "takePayment")) {
            hqlSb.append(" a.account.id,");
            hqlSb.append(" a.account.cooperator.dwmc,");
        } else {
            hqlSb.append(" a.account.id,");
        }
        hqlSb.append(" a.account.business.ddlx");
        hqlSb.append(" from AccountFlow a left join a.bank b where 1=1 ");

        Map<String, Object> map = new HashMap();
        hqlSb.append(doPropertyFilter(accountFlow, type));
        return findPage(page, hqlSb.toString(), map);
    }

    public List<Integer> findAuditIdList(AccountFlow accountFlow, String type) {
        StringBuffer hqlSb = new StringBuffer("select a.id from AccountFlow a left join a.bank b where 1=1 ");
        hqlSb.append(doPropertyFilter(accountFlow, type));
        return find(hqlSb.toString(), new Object[0]);
    }

    private StringBuffer doPropertyFilter(AccountFlow accountFlow, String type) {
        StringBuffer hqlSb = new StringBuffer();
        Map<String, Object> map = new HashMap();
        if ((accountFlow != null) && (accountFlow.getOrderId() != null) && (!"".equals(accountFlow.getOrderId()))) {
            hqlSb.append("and a.orderId like '" + accountFlow.getOrderId() + "%' ");
        }
        if ((accountFlow != null) && (accountFlow.getProUserIds() != null) && (accountFlow.getProUserIds().size() > 0)) {
            String userId = "";
            for (Integer id : accountFlow.getProUserIds()) {
                userId = userId + id + ",";
            }
            userId = userId.substring(0, userId.lastIndexOf(","));
            hqlSb.append("and a.user.id in (" + userId + ") ");
        }
        if (type != null) {
            if ((accountFlow != null) && (accountFlow.getBeginDate() != null) && (!"".equals(accountFlow.getBeginDate()))) {
                hqlSb.append("and date_format(a.sksj,'%Y-%m-%d') >= '" + accountFlow.getBeginDate() + "' ");
            }
            if ((accountFlow != null) && (accountFlow.getEndDate() != null) && (!"".equals(accountFlow.getEndDate()))) {
                hqlSb.append("and date_format(a.sksj,'%Y-%m-%d') <= '" + accountFlow.getEndDate() + "' ");
            }
            if ((accountFlow != null) && (accountFlow.getAccount() != null) && (accountFlow.getAccount().getBusiness() != null) && (!"".equals(accountFlow.getAccount().getBusiness().getDdlx()))) {
                hqlSb.append(" and a.account.business.ddlx = " + accountFlow.getAccount().getBusiness().getDdlx());
            }
            if ((accountFlow != null) && (accountFlow.getBank() != null) && (accountFlow.getBank().getId() != null) && (!"".equals(accountFlow.getBank().getId()))) {
                hqlSb.append("and a.bank.id = " + accountFlow.getBank().getId());
            }
            if (("takeFinaneChecked".equals(type)) || (type == "takeFinaneChecked")) {
                hqlSb.append("and a.ywlx = '1' ");
                if ((accountFlow != null) && (accountFlow.getStat() != null)) {
                    if (!"".equals(accountFlow.getStat())) {
                        hqlSb.append("and a.stat = '" + accountFlow.getStat() + "' ");
                    }
                } else {
                    hqlSb.append("and a.stat in ('1','3','9') ");
                }
            } else if (("payManageChecked".equals(type)) || (type == "payManageChecked")) {
                hqlSb.append("and a.ywlx = '2' ");
                hqlSb.append("and a.sffs = '1' ");
                if (accountFlow.getStat() != null) {
                    if (!"".equals(accountFlow.getStat())) {
                        hqlSb.append("and a.stat = '" + accountFlow.getStat() + "' ");
                    }
                } else {
                    hqlSb.append("and a.stat = '1' ");
                }
                if ((accountFlow.getAccount() != null) && (accountFlow.getAccount().getBusiness() != null) && (accountFlow.getAccount().getBusiness().getDdlx() != null)
                        && (!"".equals(accountFlow.getAccount().getBusiness().getDdlx()))) {
                    hqlSb.append("and a.account.business.ddlx = '" + accountFlow.getAccount().getBusiness().getDdlx() + "' ");
                }
            } else if (("payManageRechecked".equals(type)) || (type == "payManageRechecked")) {
                hqlSb.append("and a.ywlx = '2' ");
                hqlSb.append("and a.stat in ('2','9') ");
            } else if (("payFinaneChecked".equals(type)) || (type == "payFinaneChecked")) {
                hqlSb.append("and a.ywlx = '2' ");
                hqlSb.append("and a.sffs <> '3' ");
                if ((accountFlow != null) && (accountFlow.getStat() != null) && (!"".equals(accountFlow.getStat()))) {
                    hqlSb.append("and a.stat = '" + accountFlow.getStat() + "' ");
                    if ("9".equals(accountFlow.getStat())) {
                        hqlSb.append("and a.spr is not null ");
                    }
                } else {
                    hqlSb.append("and a.stat in ('2','3') or (a.stat = '9' and a.spr is not null) ");
                }
            } else if (("payFinaneRechecked".equals(type)) || (type == "payFinaneRechecked")) {
                hqlSb.append("and a.ywlx = '2' ");
                hqlSb.append("and a.stat in ('3','9') ");
            } else if (("viewData".equals(type)) || (type == "viewData")) {
                if (getLoginUser().getRoleValues().contains("ROLE_ONE")) {
                    hqlSb.append("and a.user.id = '" + getLoginUser().getId() + "' ");
                }
            } else if (("takePayment".equals(type)) || (type == "takePayment")) {
                hqlSb.append("and a.ywlx = '2' ");
                hqlSb.append("and a.sffs = '3' ");
                if ((accountFlow != null) && (accountFlow.getStat() != null) && (!"".equals(accountFlow.getStat()))) {
                    hqlSb.append("and a.stat = '" + accountFlow.getStat() + "' ");
                    if ("9".equals(accountFlow.getStat())) {
                        hqlSb.append("and a.spr is not null ");
                    }
                } else {
                    hqlSb.append("and a.stat in ('2','3') or (a.stat = '9' and a.spr is not null) ");
                }
            }
        }
        hqlSb.append("order by a.stat, a.orderId");
        return hqlSb;
    }

    public AccountFlow doSearchServices(Integer id) {
        AccountFlow accountFlow = (AccountFlow) get(id);
        return accountFlow;
    }

    public AccountFlow checkRepeatRefer(String accountId) {
        StringBuffer hqlSb = new StringBuffer("select a from AccountFlow a where 1=1 ");
        if ((accountId != null) && (!"".equals(accountId))) {
            hqlSb.append("and a.accountId = '" + accountId + "'");
        }
        return (AccountFlow) findUnique(hqlSb.toString(), new Object[0]);
    }

    public int deleteAccountFlow(Integer accountIds) {
        String hqlSb = "delete from chee_account_flow where account_id = '" + accountIds + "'";
        return getSession().createSQLQuery(hqlSb).executeUpdate();
    }

    public long noGenerate() {
        String hql = "select count(*) from AccountFlow where date_format(lrsj,'%Y-%m-%d') ='" + DateUtil.formatDate(new Date(), "yyyy-MM-dd") + "'";
        return ((Long) getSession().createQuery(hql).uniqueResult()).longValue();
    }
}
