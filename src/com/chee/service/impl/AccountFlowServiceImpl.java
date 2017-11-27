package com.chee.service.impl;

import com.chee.annotation.rmpfLog;
import com.chee.common.Page;
import com.chee.dao.AccountFlowDao;
import com.chee.entity.Account;
import com.chee.entity.AccountFlow;
import com.chee.entity.Bank;
import com.chee.entity.BankRunning;
import com.chee.entity.Business;
import com.chee.service.AccountFlowService;
import com.chee.service.AccountService;
import com.chee.service.BankRunningService;
import com.chee.service.BankService;
import com.chee.service.BusinessCustomerService;
import com.chee.service.BusinessService;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class AccountFlowServiceImpl extends BaseServiceImpl<AccountFlow, Integer> implements AccountFlowService {
    @Resource
    private AccountFlowDao accountFlowDao;
    @Resource
    private AccountService accountService;
    @Resource
    private BusinessService businessService;
    @Resource
    private BankService bankService;
    @Resource
    private BankRunningService bankRunningService;
    @Resource
    private BusinessCustomerService businessCustomerService;

    @Resource
    public void setBaseDao(AccountFlowDao AccountFlowDao) {
        super.setBaseDao(AccountFlowDao);
    }

    public Page<AccountFlow> findWaitAuditDataPage(Page<AccountFlow> page, AccountFlow accountFlow, String type) {
        return this.accountFlowDao.findWaitAuditDataPage(page, accountFlow, type);
    }

    public List<Integer> findAuditIdList(AccountFlow accountFlow, String type) {
        return this.accountFlowDao.findAuditIdList(accountFlow, type);
    }

    public AccountFlow doSearchServices(Integer id) {
        return this.accountFlowDao.doSearchServices(id);
    }

    public AccountFlow checkRepeatRefer(String accountId) {
        return this.accountFlowDao.checkRepeatRefer(accountId);
    }

    @rmpfLog(desc = "计调招回")
    public int deleteAccountFlow(Integer accountIds) {
        return this.accountFlowDao.deleteAccountFlow(accountIds);
    }

    public long noGenerate() {
        return this.accountFlowDao.noGenerate();
    }

    public String updateAccountFlowPayManageApprove(String id, String sessionName) {
        String intStat = "2";
        String result = "";

        AccountFlow flow = (AccountFlow) this.accountFlowDao.get(Integer.valueOf(id));
        if (flow == null) {
            return result = "[id=" + id + "]业务表中未找到该项数据！";
        }
        flow.setShr(sessionName + "-审核");
        flow.setShsj(new Date());
        flow.setStat(intStat);
        this.accountFlowDao.update(flow);

        String account_ids = flow.getAccountId();
        if (account_ids != null) {
            String[] str = account_ids.split(",");
            for (int i = 0; i < str.length; i++) {
                Account account = new Account();
                account = (Account) this.accountService.get(Integer.valueOf(Integer.parseInt(str[i])));
                account.setShr(sessionName);
                account.setShsj(new Date());
                account.setStat(intStat);
                this.accountService.update(account);
            }
        }
        return result;
    }

    public String updateAccountFlowPayFinaneApprove(String id, BankRunning bankRunning, String sessionName) {
        String intStat = "3";
        String result = "";

        AccountFlow flow = (AccountFlow) this.accountFlowDao.get(Integer.valueOf(id));
        if (flow == null) {
            return result = "[id=" + id + "]业务表中未找到该项数据！";
        }
        Bank bank = new Bank();
        bank.setId(bankRunning.getBank().getId());
        flow.setBank(bank);
        flow.setSksj(bankRunning.getJzsj());
        flow.setSpr(sessionName + "-支付");
        flow.setSpsj(new Date());
        flow.setStat(intStat);

        String account_ids = flow.getAccountId();
        if (account_ids != null) {
            String[] str = account_ids.split(",");
            for (int i = 0; i < str.length; i++) {
                Account account = new Account();
                account = (Account) this.accountService.get(Integer.valueOf(Integer.parseInt(str[i])));
                account.setShr(sessionName);
                account.setShsj(new Date());
                account.setStat(intStat);
                account.setBank(bankRunning.getBank());
                account.setSfsj(bankRunning.getJzsj());
                this.accountService.update(account);

                updateBusinessJe(account.getOrderId(), account.getSflx());
            }
        }
        if (bankRunning != null) {
            bankRunning.setZcje(Double.parseDouble(bankRunning.getFkje()));
            bankRunning.setLrje(0.0D - Double.parseDouble(bankRunning.getFkje()));
            bankRunning.setLrsj(new Date());
            bankRunning.setLrr(sessionName);
            bankRunning.setShbj("1");
            bankRunning.setSflx("2");
            bankRunning.setOrderId(flow.getOrderId());
            Integer b_r_id = (Integer) this.bankRunningService.save(bankRunning);

            flow.setBankRunningId(b_r_id);
            this.accountFlowDao.update(flow);

            Bank b = (Bank) this.bankService.get(bankRunning.getBank().getId());
            double cush = new Double(b.getCush() + bankRunning.getLrje()).doubleValue();
            this.bankService.updateBankCush(cush, Integer.valueOf(bankRunning.getBank().getId().intValue()));
        }
        return result;
    }

    public String updateAccountFlowTakeFinaneApprove(String id, String bankRunningId, String sessionName) {
        String intStat = "3";
        String result = "";

        AccountFlow flow = (AccountFlow) this.accountFlowDao.get(Integer.valueOf(id));
        if (flow == null) {
            return result = "[id=" + id + "]业务表中未找到该项数据！";
        }
        flow.setSpr(sessionName + "-审核");
        flow.setSpsj(new Date());
        flow.setStat(intStat);
        flow.setBankRunningId(Integer.valueOf(Integer.parseInt(bankRunningId)));
        this.accountFlowDao.update(flow);

        String account_ids = flow.getAccountId();
        if (account_ids != null) {
            String[] str = account_ids.split(",");
            for (int i = 0; i < str.length; i++) {
                Account account = new Account();
                account = (Account) this.accountService.get(Integer.valueOf(Integer.parseInt(str[i])));
                account.setShr(sessionName);
                account.setShsj(new Date());
                account.setStat(intStat);
                this.accountService.update(account);

                updateBusinessJe(account.getOrderId(), account.getSflx());
                if ((account.getBusinessCustomer() != null) && (account.getBusinessCustomer().getId() != null)) {
                    updateBusinessCustomerYjs(account.getOrderId(), account.getBusinessCustomer().getId());
                }
            }
        }
        BankRunning bankRunning = (BankRunning) this.bankRunningService.get(Integer.valueOf(bankRunningId));

        Bank b = new Bank();
        if ((bankRunning.getBank() != null) && (!"".equals(bankRunning.getBank().getId()))) {
            bankRunning.setShbj("1");
            bankRunning.setGxsj(new Date());
            bankRunning.setOrderId(flow.getOrderId());
            this.bankRunningService.update(bankRunning);

            b = (Bank) this.bankService.get(bankRunning.getBank().getId());
            double cush = new Double(b.getCush() + bankRunning.getLrje()).doubleValue();
            this.bankService.updateBankCush(cush, Integer.valueOf(bankRunning.getBank().getId().intValue()));
        }
        return result;
    }

    private void updateBusinessJe(String order_id, String sflx) {
        double sumSkje = 0.0D;
        double sumFkje = 0.0D;
        List<Account> list = this.accountService.findAccountCountJe(order_id);
        for (Account accountTemp : list) {
            sumSkje += accountTemp.getSkje();
            sumFkje += accountTemp.getFkje();
        }
        Business persistent = (Business) this.businessService.get("orderId", order_id);
        if (persistent != null) {
            persistent.setYjf(sumFkje);
            persistent.setYjs(sumSkje);
            persistent.setGxsj(new Date());
            this.businessService.update(persistent);
        } else {
            System.out.println(order_id + "订单编号查询结果为空！！");
        }
    }

    private void updateBusinessCustomerYjs(String order_id, Integer b_c_id) {
        double sumSkje = 0.0D;
        List<Account> list = this.accountService.findAccountCountYjs(order_id, b_c_id);
        for (Account accountTemp : list) {
            sumSkje += accountTemp.getSkje();
        }
        this.businessCustomerService.updateYjs(sumSkje, order_id, b_c_id);
    }

    @rmpfLog(desc = "财务回退")
    public String deleteRecord(String id, String sessionName) {
        String result = "";
        //通过id获得业务流水信息
        AccountFlow flow = (AccountFlow) this.accountFlowDao.get(Integer.valueOf(id));
        if (flow == null) {
            return result = "[flow_id=" + id + "]业务表中未找到该项数据！";
        }
        String account_ids = flow.getAccountId();
        if (account_ids == null) {
            return result = "[flow_id=" + id + "]account_id为空！";
        }
        if ((flow.getStat() == "3") || ("3".equals(flow.getStat())) && (flow.getBankRunningId() == null)) {
            return result = "[flow_id=" + id + "]bankRunningId为空！";
        }
        Integer bankRunningId = null;
        if (flow.getBankRunningId() != null) {
            bankRunningId = Integer.valueOf(flow.getBankRunningId().intValue());
        }
        
        //1为收款业务；2为付款业务
        if ((flow.getYwlx() == "1") || ("1".equals(flow.getYwlx()))) {
            String[] str = account_ids.split(",");
            for (int i = 0; i < str.length; i++) {
                Account account = new Account();
                account = (Account) this.accountService.get(Integer.valueOf(Integer.parseInt(str[i])));
                account.setShr(sessionName + "-回退");
                account.setShsj(new Date());

                account.setStat("9");
                this.accountService.update(account);
                if ((flow.getStat() == "3") || ("3".equals(flow.getStat())) || (flow.getStat() == "9") || ("9".equals(flow.getStat()))) {
                    updateBusinessJe(account.getOrderId(), account.getSflx());
                    if ((account.getBusinessCustomer() != null) && (account.getBusinessCustomer().getId() != null)) {
                        updateBusinessCustomerYjs(account.getOrderId(), account.getBusinessCustomer().getId());
                    }
                }
            }
        } else {
            String returnStat = "9";

            String[] str = account_ids.split(",");
            for (int i = 0; i < str.length; i++) {
                Account account = new Account();
                account = (Account) this.accountService.get(Integer.valueOf(Integer.parseInt(str[i])));
                account.setShr(sessionName);
                account.setShsj(new Date());
                account.setStat(returnStat);
                account.setBank(null);
                account.setSfsj(null);
                this.accountService.update(account);
                if ((flow.getStat() == "3") || ("3".equals(flow.getStat())) || (flow.getStat() == "9") || ("9".equals(flow.getStat()))) {
                    updateBusinessJe(account.getOrderId(), account.getSflx());
                    if ((account.getBusinessCustomer() != null) && (account.getBusinessCustomer().getId() != null)) {
                        updateBusinessCustomerYjs(account.getOrderId(), account.getBusinessCustomer().getId());
                    }
                }
            }
        }

        if ((flow.getStat() == "3") || ("3".equals(flow.getStat()))) {
            BankRunning bankRunning = (BankRunning) this.bankRunningService.get(bankRunningId);
            if (bankRunning == null) {
                return "银行流水id：" + flow.getBankRunningId();
            }
            if ((flow.getYwlx() == "1") || ("1".equals(flow.getYwlx()))) {
                Bank b = new Bank();
                if ((bankRunning.getBank() != null) && (!"".equals(bankRunning.getBank().getId()))) {
                    bankRunning.setShbj("0");
                    bankRunning.setOrderId("");
                    bankRunning.setGxsj(new Date());
                    this.bankRunningService.update(bankRunning);

                    b = (Bank) this.bankService.get(bankRunning.getBank().getId());
                    double cush = new Double(b.getCush() - bankRunning.getLrje()).doubleValue();
                    this.bankService.updateBankCush(cush, Integer.valueOf(bankRunning.getBank().getId().intValue()));
                }
            } else if ((bankRunning.getBank() != null) && (!"".equals(bankRunning.getBank().getId()))) {
                Bank b = (Bank) this.bankService.get(bankRunning.getBank().getId());
                double cush = new Double(b.getCush() - bankRunning.getLrje()).doubleValue();
                this.bankService.updateBankCush(cush, Integer.valueOf(bankRunning.getBank().getId().intValue()));

                this.bankRunningService.delete(bankRunningId);
            }
            //回退清理已审数据
            flow.setBankRunningId(null);
            flow.setBank(null);
            flow.setSksj(null);
            flow.setSpr(sessionName + "-回退");
            flow.setSpsj(new Date());
        } else {
            flow.setShr(sessionName + "-回退");
            flow.setShsj(new Date());
        }
        flow.setStat("9");
        this.accountFlowDao.update(flow);

        return result;
    }

    public int updateOrderId(String orderIdold, String orderIdnew) {
        String sql = "update chee_account_flow t set t.order_id = '" + orderIdnew + "' where t.order_id = '" + orderIdold + "'";
        return getSession().createSQLQuery(sql).executeUpdate();
    }
}
