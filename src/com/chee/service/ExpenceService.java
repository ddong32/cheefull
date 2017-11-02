package com.chee.service;

import com.chee.common.Page;
import com.chee.entity.BankRunning;
import com.chee.entity.Expence;
import com.chee.entity.User;
import java.util.List;

public abstract interface ExpenceService
  extends BaseService<Expence, Integer>
{
  public abstract List<Integer> findAuditIdList(Expence paramExpence);
  
  public abstract Page<Expence> findExpencePage(Page<Expence> paramPage, Expence paramExpence, String paramString);
  
  public abstract Integer save(Expence paramExpence, User paramUser);
  
  public abstract void update(Expence paramExpence);
  
  public abstract Expence getExpenceIds(Integer paramInteger);
  
  public abstract String doPass(Expence paramExpence, User paramUser);
  
  public abstract String doPayy(Expence paramExpence, BankRunning paramBankRunning, User paramUser);
  
  public abstract String doCancel(Integer paramInteger, User paramUser);
}


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.service.ExpenceService
 * JD-Core Version:    0.7.0.1
 */