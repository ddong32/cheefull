package com.chee.service.impl;

import com.chee.annotation.rmpfLog;
import com.chee.common.Page;
import com.chee.dao.BankRunningDao;
import com.chee.entity.Bank;
import com.chee.entity.BankRunning;
import com.chee.service.BankRunningService;
import com.chee.service.BankService;
import java.io.PrintStream;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class BankRunningServiceImpl extends BaseServiceImpl<BankRunning, Integer> implements BankRunningService {
    @Resource
    BankRunningDao bankRunningDao;
    @Resource
    BankService bankService;

    @Resource
    public void setBaseDao(BankRunningDao bankRunningDao) {
        super.setBaseDao(bankRunningDao);
    }

    public Page<BankRunning> findBankRunningPage(Page<BankRunning> page, BankRunning bankRunning) {
        return this.bankRunningDao.findBankRunningPage(page, bankRunning);
    }

    public BankRunning findBankRunningUnique(BankRunning bankRunning) {
        return this.bankRunningDao.findBankRunningUnique(bankRunning);
    }

    @rmpfLog(desc = "银行流水修改/添加")
    public String saveBankRunning(BankRunning bankRunning, String sessionName, String currentBankId) {
        String operinfo = "";
        if (bankRunning.getId() != null) {
            BankRunning persistent = (BankRunning) get(bankRunning.getId());

            BeanUtils.copyProperties(bankRunning, persistent, new String[] { "id", "gxsj", "lrr", "lrsj", "shbj" });
            if ("1".equals(bankRunning.getSflx())) {
                persistent.setSrje(bankRunning.getJe());
                persistent.setLrje(bankRunning.getJe());
            } else {
                persistent.setZcje(bankRunning.getJe());
                persistent.setLrje(0.0D - bankRunning.getJe());
            }
            persistent.setGxsj(new Date());
            this.bankRunningDao.update(persistent);
        } else {
            if ("1".equals(bankRunning.getSflx())) {
                bankRunning.setSrje(bankRunning.getJe());
                bankRunning.setLrje(bankRunning.getJe());
            } else {
                bankRunning.setZcje(bankRunning.getJe());
                bankRunning.setLrje(0.0D - bankRunning.getJe());
            }
            bankRunning.setLrsj(new Date());
            bankRunning.setLrr(sessionName);
            bankRunning.setShbj("0");
            this.bankRunningDao.save(bankRunning);
        }
        return operinfo;
    }

    public String saveBankRunning1(BankRunning bankRunning, String sessionName, String currentBankId) {
        String operinfo = "";
        double je = 0.0D;
        double lrje = 0.0D;
        double cush = 0.0D;
        if (bankRunning.getId() != null) {
            BankRunning persistent = (BankRunning) get(bankRunning.getId());
            if ((bankRunning.getBank() != null) && (!"".equals(bankRunning.getBank().getId()))
                    && (!"".equals(currentBankId))) {
                if ("1".equals(bankRunning.getSflx())) {
                    je = bankRunning.getJe();
                } else {
                    je = 0.0D - bankRunning.getJe();
                }
                lrje = persistent.getLrje();
                if (bankRunning.getBank().getId() == Integer.valueOf(currentBankId)) {
                    Bank b = new Bank();
                    b = (Bank) this.bankService.get(Integer.valueOf(currentBankId));
                    cush = new Double(b.getCush() - persistent.getLrje() + je).doubleValue();
                    this.bankService.updateBankCush(cush, Integer.valueOf(currentBankId));
                } else {
                    Bank b = new Bank();
                    b = (Bank) this.bankService.get(Integer.valueOf(currentBankId));
                    cush = new Double(b.getCush() - lrje).doubleValue();
                    this.bankService.updateBankCush(cush, Integer.valueOf(currentBankId));

                    b = (Bank) this.bankService.get(bankRunning.getBank().getId());
                    cush = new Double(b.getCush() + je).doubleValue();
                    this.bankService.updateBankCush(cush, bankRunning.getBank().getId());
                }
                BeanUtils.copyProperties(bankRunning, persistent, new String[] { "id", "gxsj", "lrr", "lrsj", "shbj" });
                if ("1".equals(bankRunning.getSflx())) {
                    persistent.setSrje(bankRunning.getJe());
                    persistent.setLrje(bankRunning.getJe());
                } else {
                    persistent.setZcje(bankRunning.getJe());
                    persistent.setLrje(0.0D - bankRunning.getJe());
                }
                persistent.setGxsj(new Date());
                this.bankRunningDao.update(persistent);
            } else {
                operinfo = "用户提交数据有误，Bank：" + bankRunning.getBank() + " Bid" + bankRunning.getBank().getId() + " CBId:"
                        + currentBankId;
            }
        } else if (this.bankRunningDao.findBankRunningUnique(bankRunning) != null) {
            operinfo = "该银行流水已经存在，请重新输入!";
        } else {
            if ("1".equals(bankRunning.getSflx())) {
                bankRunning.setSrje(bankRunning.getJe());
                bankRunning.setLrje(bankRunning.getJe());
            } else {
                bankRunning.setZcje(bankRunning.getJe());
                bankRunning.setLrje(0.0D - bankRunning.getJe());
            }
            Bank b = new Bank();
            if ((bankRunning.getBank() != null) && (!"".equals(bankRunning.getBank().getId()))) {
                System.out.println(bankRunning.getBank().getId());
                b = (Bank) this.bankService.get(bankRunning.getBank().getId());
                cush = new Double(b.getCush() + bankRunning.getLrje()).doubleValue();
                this.bankService.updateBankCush(cush, Integer.valueOf(bankRunning.getBank().getId().intValue()));
            }
            bankRunning.setLrsj(new Date());
            bankRunning.setLrr(sessionName);
            bankRunning.setShbj("0");
            this.bankRunningDao.save(bankRunning);
        }
        return operinfo;
    }

    public List<BankRunning> findAuditBankRunningList(BankRunning bankRunning) {
        return this.bankRunningDao.findAuditBankRunningList(bankRunning);
    }

    public int updateShbj(Integer id, String stat) {
        String sql = "update chee_bank_running t set t.shbj = " + stat + " where t.id=" + id;
        return getSession().createSQLQuery(sql).executeUpdate();
    }

    public int updateOrderId(Integer businessId, String orderIdold, String orderIdnew) {
        String sql = "update chee_bank_running t set t.order_id = '" + orderIdnew + "' where t.order_id = '"
                + orderIdold + "' and t.business_id =" + businessId;
        return getSession().createSQLQuery(sql).executeUpdate();
    }

    public List<BankRunning> findBankRunningListTotal(BankRunning bankRunning) {
        return this.bankRunningDao.findBankRunningListTotal(bankRunning);
    }
}
