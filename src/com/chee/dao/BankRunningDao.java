package com.chee.dao;

import com.chee.common.Page;
import com.chee.entity.BankRunning;
import java.util.List;

public abstract interface BankRunningDao
  extends BaseDao<BankRunning, Integer>
{
  public abstract Page<BankRunning> findBankRunningPage(Page<BankRunning> paramPage, BankRunning paramBankRunning);
  
  public abstract BankRunning findBankRunningUnique(BankRunning paramBankRunning);
  
  public abstract List<BankRunning> findAuditBankRunningList(BankRunning paramBankRunning);
  
  public abstract List<BankRunning> findBankRunningListTotal(BankRunning paramBankRunning);
}


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.dao.BankRunningDao
 * JD-Core Version:    0.7.0.1
 */