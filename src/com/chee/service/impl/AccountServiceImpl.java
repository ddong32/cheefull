package com.chee.service.impl;

import com.chee.annotation.rmpfLog;
import com.chee.common.Page;
import com.chee.dao.AccountDao;
import com.chee.entity.Account;
import com.chee.entity.AccountFlow;
import com.chee.entity.Bank;
import com.chee.entity.Business;
import com.chee.entity.User;
import com.chee.service.AccountFlowService;
import com.chee.service.AccountService;
import com.chee.service.BusinessService;
import com.chee.util.SequenceUtil;
import com.chee.util.UserUtil;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl extends BaseServiceImpl<Account, Integer> implements AccountService {
    @Resource
    private AccountDao accountDao;
    @Resource
    private AccountFlowService accountFlowService;
    @Resource
    private UserUtil userUtil;
    @Resource
    private SequenceUtil sequenceUtil;
    @Resource
    private BusinessService businessService;

    private User getLoginUser() {
        return this.userUtil.getLoginUser();
    }

    @Resource
    public void setBaseDao(AccountDao AccountDao) {
        super.setBaseDao(this.accountDao);
    }

    public List<Account> findOrderID(String order_id) {
        return this.accountDao.findOrderID(order_id);
    }

    public List<Account> findAccountList(Integer id, String order_id) {
        return this.accountDao.findAccountList(id, order_id);
    }

    public List<Account> findAccountIds(Integer[] account_ids) {
        return this.accountDao.findAccountIds(account_ids);
    }

    public Page<Account> findBusinessPage(Page<Account> page, Account account) {
        return this.accountDao.findBusinessPage(page, account);
    }

    public List<Account> findAccountCountJe(String order_id) {
        return this.accountDao.findAccountCountJe(order_id);
    }

    public List<Account> findAccountCountYfk(String order_id) {
        return this.accountDao.findAccountCountYfk(order_id);
    }

    public List<Account> findAccountCountYjs(String order_id, Integer b_c_id) {
        return this.accountDao.findAccountCountYjs(order_id, b_c_id);
    }

    public Page<Account> findOnTrustPage(Page<Account> page, Account account) {
        return this.accountDao.findOnTrustPage(page, account);
    }

    public Page<Account> findOnPaymentPage(Page<Account> page, Account account) {
        return this.accountDao.findOnPaymentPage(page, account);
    }

    public List<Account> findAccountUnique(Integer id) {
        return this.accountDao.findAccountUnique(id);
    }

    @rmpfLog(desc = "订单管理 - 收付款 - 删除")
    public void doBatchDelete(Integer[] ids) {
        this.accountDao.delete(ids);
        for (Integer id : ids) {
            this.accountFlowService.deleteAccountFlow(id);
        }
    }

    @rmpfLog(desc = "订单管理-提交")
    public String doAccountRefer(Integer[] ids, Account account) {
        String idstr = "";
        String result = "";
        double yjs = 0.0D;
        double yjf = 0.0D;
        if (ids != null) {
            try {
                Account persistent = null;
                Bank bank = null;
                Date sfsj = null;
                if ((account != null) && (account.getBank() != null)) {
                    bank = account.getBank();
                    sfsj = account.getSfsj();
                }
                for (Integer id : ids) {
                    persistent = (Account) load(id);
                    persistent.setGxsj(new Date());
                    persistent.setStat("2");
                    persistent.setBank(bank);
                    persistent.setSfsj(sfsj);
                    this.accountDao.update(persistent);
                    if (idstr == "") {
                        idstr = id + "";
                    } else {
                        idstr = idstr + "," + id;
                    }
                    yjs += persistent.getSkje();
                    yjf += persistent.getFkje();
                }
                if (persistent != null) {
                    AccountFlow accountFlow = this.accountFlowService.checkRepeatRefer(idstr);
                    if (accountFlow != null) {
                        accountFlow.setSkje(yjs);
                        accountFlow.setFkje(yjf);
                        accountFlow.setYwlx(persistent.getSflx());
                        accountFlow.setSksj(persistent.getSfsj());
                        accountFlow.setBank(bank);
                        accountFlow.setSksj(sfsj);
                        accountFlow.setShr("");
                        accountFlow.setStat("2");
                        accountFlow.setOrderId(persistent.getOrderId());
                        accountFlow.setAccount(persistent);
                        accountFlow.setLrr(getLoginUser().getName());
                        accountFlow.setUser(getLoginUser());
                        this.accountFlowService.update(accountFlow);
                    } else {
                        AccountFlow temp = new AccountFlow();
                        temp.setLsh(this.sequenceUtil.ProductLsh());
                        temp.setYwlx(persistent.getSflx());
                        temp.setSkje(yjs);
                        temp.setFkje(yjf);
                        temp.setLrr(getLoginUser().getName());
                        temp.setUser(getLoginUser());
                        temp.setBank(bank);
                        temp.setSksj(sfsj);
                        temp.setLrsj(new Date());
                        temp.setStat("2");
                        temp.setShr("");
                        temp.setAccountId(idstr);
                        temp.setOrderId(persistent.getOrderId());
                        temp.setSffs(persistent.getSffs());
                        temp.setAccount(persistent);
                        this.accountFlowService.save(temp);
                    }
                } else {
                    result = "[" + ids.toString() + "] 中查找收支表有空值，操作失败！";
                }
            } catch (Exception e) {
                System.out.println(e.toString());
                result = e.toString();
            }
        } else {
            result = "没有选中数据,操作失败!";
        }
        return result;
    }

    @rmpfLog(desc = "订单管理-提交")
    public String doAccountStat(Integer[] ids) {
        String result = "";
        Account persistent = null;
        if (ids != null) {
            try {
                for (Integer id : ids) {
                    persistent = (Account) get(id);
                    persistent.setGxsj(new Date());
                    persistent.setStat("1");
                    update(persistent);

                    AccountFlow accountFlow = this.accountFlowService.checkRepeatRefer(id.toString());
                    if (accountFlow != null) {
                        accountFlow.setSkje(persistent.getSkje());
                        accountFlow.setFkje(persistent.getFkje());
                        accountFlow.setSksj(persistent.getSfsj());
                        accountFlow.setLrsj(new Date());
                        accountFlow.setYwlx(persistent.getSflx());
                        accountFlow.setBank(persistent.getBank());
                        accountFlow.setBz(persistent.getBz());

                        accountFlow.setStat("1");
                        accountFlow.setOrderId(persistent.getOrderId());
                        accountFlow.setSffs(persistent.getSffs());
                        accountFlow.setAccount(persistent);
                        this.accountFlowService.update(accountFlow);
                    } else {
                        AccountFlow temp = new AccountFlow();
                        temp.setLsh(this.sequenceUtil.ProductLsh());
                        temp.setYwlx(persistent.getSflx());
                        temp.setSkje(persistent.getSkje());
                        temp.setFkje(persistent.getFkje());
                        temp.setAccountId(persistent.getId().toString());
                        temp.setLrr(getLoginUser().getName());
                        temp.setBank(persistent.getBank());
                        temp.setBz(persistent.getBz());
                        temp.setSksj(persistent.getSfsj());
                        temp.setLrsj(new Date());
                        temp.setStat("1");
                        temp.setShr("");
                        temp.setOrderId(persistent.getOrderId());
                        temp.setSffs(persistent.getSffs());
                        temp.setAccount(persistent);
                        temp.setUser(getLoginUser());
                        this.accountFlowService.save(temp);
                    }
                }
            } catch (Exception e) {
                System.out.println(e.toString());
                result = e.toString();
            }
        } else {
            result = "没有选中数据,操作失败!";
        }
        return result;
    }

    public int updateAccountBz(Account account) {
        return this.accountDao.updateAccountBz(account);
    }

    public int updateOrderId(Integer businessId, String orderIdold, String orderIdnew) {
        String sql = "update chee_account t set t.order_id = '" + orderIdnew + "' where t.order_id = '" + orderIdold + "' and t.business_id =" + businessId;
        return getSession().createSQLQuery(sql).executeUpdate();
    }

    @rmpfLog(desc = "订单帐单-修改", keyword = "orderId")
    public void update(Account entity) {
        this.accountDao.update(entity);
    }

    @rmpfLog(desc = "订单帐单-添加", keyword = "orderId")
    public String doSave(Account account) {
        String msg = "success";

        Account persistent = null;
        if ((account.getBank() != null) && (account.getBank().getId() == null)) {
            account.setBank(null);
        }
        if ((account.getCooperator() != null) && (account.getCooperator().getId() == null)) {
            account.setCooperator(null);
        }
        if ((account.getBusinessCustomer() != null) && (account.getBusinessCustomer().getId() == null)) {
            account.setBusinessCustomer(null);
        }
        if ((account != null) && (account.getId() != null)) {
            persistent = (Account) get(account.getId());
            BeanUtils.copyProperties(account, persistent, new String[] { "id", "shr", "lrr", "lrsj", "gxsj", "stat", "user" });
            persistent.setGxsj(new Date());
            persistent.setShr("");
            if (("3".equals(account.getSffs())) && (account.getSffs() == "3")) {
                persistent.setBank(null);
                persistent.setSfsj(null);
            }
            update(persistent);
        } else {
            account.setUser(getLoginUser());
            account.setLrr(getLoginUser().getName());
            account.setLrsj(new Date());
            account.setStat("0");
            account.setShr("");
            save(account);
        }
        if ("2".equals(account.getSflx())) {
            updateBusinessJe(account.getOrderId());
        }
        return msg;
    }

    @rmpfLog(desc = "订单管理 - 收付款 - 删除")
    public String doAjaxBatchDelete(Integer[] ids) {
        String msg = "";
        try {
            List<Account> list = get(ids);
            doBatchDelete(ids);
            for (Account persistent : list) {
                if ("2".equals(persistent.getSflx())) {
                    updateBusinessJe(persistent.getOrderId());
                }
            }
            msg = "操作完成!";
        } catch (Exception e) {
            msg = e.toString();
        }
        return msg;
    }

    @rmpfLog(desc = "订单管理-收付款-删除-订单已收款更新", keyword = "orderId")
    private void updateBusinessJe(String order_id) {
        double sumFkje = 0.0D;
        List<Account> list = findAccountCountYfk(order_id);
        for (Account accountTemp : list) {
            sumFkje += accountTemp.getFkje();
        }
        Business businessTemp = new Business();
        businessTemp = this.businessService.findBusinessRecountJe(order_id);
        businessTemp.setYgf(sumFkje);
        businessTemp.setGxsj(new Date());
        this.businessService.update(businessTemp);
    }
}
