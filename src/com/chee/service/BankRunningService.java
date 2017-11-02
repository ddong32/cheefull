package com.chee.service;

import com.chee.common.Page;
import com.chee.entity.BankRunning;
import java.util.List;

public abstract interface BankRunningService
  extends BaseService<BankRunning, Integer>
{
  public abstract Page<BankRunning> findBankRunningPage(Page<BankRunning> paramPage, BankRunning paramBankRunning);
  
  public abstract BankRunning findBankRunningUnique(BankRunning paramBankRunning);
  
  public abstract String saveBankRunning(BankRunning paramBankRunning, String paramString1, String paramString2);
  
  public abstract List<BankRunning> findAuditBankRunningList(BankRunning paramBankRunning);
  
  public abstract int updateShbj(Integer paramInteger, String paramString);
  
  public abstract int updateOrderId(Integer paramInteger, String paramString1, String paramString2);
  
  public abstract List<BankRunning> findBankRunningListTotal(BankRunning paramBankRunning);
}


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.service.BankRunningService
 * JD-Core Version:    0.7.0.1
 */