package com.chee.dao;

import com.chee.common.Page;
import com.chee.entity.BankRunning;
import java.util.List;

public abstract interface BankRunningDao extends BaseDao<BankRunning, Integer> {
    public abstract Page<BankRunning> findBankRunningPage(Page<BankRunning> paramPage, BankRunning paramBankRunning);

    public abstract BankRunning findBankRunningUnique(BankRunning paramBankRunning);

    public abstract List<BankRunning> findAuditBankRunningList(BankRunning paramBankRunning);

    public abstract List<BankRunning> findBankRunningListTotal(BankRunning paramBankRunning);
}
