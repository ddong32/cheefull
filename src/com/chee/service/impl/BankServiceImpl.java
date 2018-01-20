package com.chee.service.impl;

import com.chee.dao.BankDao;
import com.chee.entity.Bank;
import com.chee.entity.BankRunning;
import com.chee.entity.BusinessCustomer;
import com.chee.service.BankRunningService;
import com.chee.service.BankService;
import com.chee.util.DateUtil;
import com.chee.util.SequenceUtil;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
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

    //余额转出
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
    
    public Map<String, Object> getBankRuningMoneyStat() throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String hqlSb = sqlString();
        SQLQuery query = getSession().createSQLQuery(hqlSb.toString());
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map<String, Object>> list = query.list();
        
        double sumXtje = 0.0D;
        double sumYhje = 0.0D;
        double sumWsje = 0.0D;
        for (Map<String, Object> map : list) {
            sumXtje += Double.parseDouble(map.get("xtje").toString());
            sumYhje += Double.parseDouble(map.get("yhje").toString());
            sumWsje += Double.parseDouble(map.get("wsje").toString());
        }
        resultMap.put("list", list);
        resultMap.put("sumXtje", sumXtje);
        resultMap.put("sumYhje", sumYhje);
        resultMap.put("sumWsje", sumWsje);

        return resultMap;
    }
    
    private String sqlString() {
        StringBuffer hqlSb = new StringBuffer();
        hqlSb.append("select ccc.id, ccc.djm, ccc.name, ccc.khh, ccc.code, ccc.cush, ccc.xtje, ddd.yhje, (abs(ddd.yhje) - abs(ccc.xtje)) wsje from  ");
        hqlSb.append("(SELECT b.id, b.djm, b.name, b.khh, b.code, b.cush, sum(a.srje - zcje) xtje FROM");
        hqlSb.append("  chee_bank_running a");
        hqlSb.append("  LEFT JOIN");
        hqlSb.append("  chee_bank b ");
        hqlSb.append("on a.bank_id = b.id where a.shbj = 1 GROUP BY bank_id) ccc ");
        hqlSb.append("LEFT JOIN ");
        hqlSb.append("(select aa.bank_id, (aa.srje - zcje) yhje from ");
        hqlSb.append("  (SELECT a.bank_id, sum(a.srje) srje FROM chee_bank_running a GROUP BY bank_id) aa ");
        hqlSb.append("LEFT JOIN");
        hqlSb.append("  (SELECT a.bank_id, sum(a.zcje) zcje FROM chee_bank_running a where shbj =1 GROUP BY bank_id) bb ");
        hqlSb.append("on bb.bank_id = aa.bank_id) ddd on ccc.id = ddd.bank_id");
        return hqlSb.toString();
    }
}
