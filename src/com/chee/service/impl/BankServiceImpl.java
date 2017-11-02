package com.chee.service.impl;

import com.chee.dao.BankDao;
import com.chee.entity.Bank;
import com.chee.entity.BankRunning;
import com.chee.service.BankRunningService;
import com.chee.service.BankService;
import com.chee.util.DateUtil;
import com.chee.util.SequenceUtil;
import java.util.Date;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class BankServiceImpl extends BaseServiceImpl<Bank, Integer> implements BankService {
    @Resource
    BankDao bankDao;
    @Resource
    private BankRunningService bankRunningService;

    @Resource
    public void setBaseDao(BankDao bankDao) {
        super.setBaseDao(bankDao);
    }

    public int updateBankCush(double cush, Integer id) {
        return this.bankDao.updateBankCush(cush, id);
    }

    public String doTransfer(Bank bank, String lrr, String jzsj) throws Exception {
        if ((bank != null) && (bank.getGoid() != null) && (bank.getCoid() != null)) {
            Bank bankGo = (Bank) get(bank.getGoid());
            Bank bankCo = (Bank) get(bank.getCoid());

            bankGo.setCush(bankGo.getCush() - bank.getJe());
            bankCo.setCush(bankCo.getCush() + bank.getJe());
            save(bankGo);
            save(bankCo);

            String orderId = SequenceUtil.ProductBankTransferOrderId();
            BankRunning bankRunningGo = new BankRunning();
            bankRunningGo.setBank(bankGo);
            bankRunningGo.setOrderId(orderId);
            bankRunningGo.setZcje(bank.getJe());
            bankRunningGo.setLrje(0.0D - bank.getJe());
            bankRunningGo.setLrsj(new Date());
            bankRunningGo.setGxsj(new Date());
            bankRunningGo.setLrr(lrr);
            bankRunningGo.setShbj("1");
            bankRunningGo.setSflx("1");
            bankRunningGo.setBz("余额转出");
            bankRunningGo.setJzsj(DateUtil.string2Date(jzsj, "yyyy-MM-dd"));
            this.bankRunningService.save(bankRunningGo);

            BankRunning bankRunningCo = new BankRunning();
            bankRunningCo.setBank(bankCo);
            bankRunningCo.setOrderId(orderId);
            bankRunningCo.setSrje(bank.getJe());
            bankRunningCo.setLrje(bank.getJe());
            bankRunningCo.setLrsj(new Date());
            bankRunningCo.setGxsj(new Date());
            bankRunningCo.setLrr(lrr);
            bankRunningCo.setShbj("1");
            bankRunningCo.setSflx("2");
            bankRunningCo.setBz("余额转出");
            bankRunningCo.setJzsj(DateUtil.string2Date(jzsj, "yyyy-MM-dd"));
            this.bankRunningService.save(bankRunningCo);
            return "success";
        }
        return "error";
    }
}
