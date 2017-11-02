package com.chee.service;

import com.chee.common.Page;
import com.chee.entity.Account;
import java.util.List;

public abstract interface AccountService
  extends BaseService<Account, Integer>
{
  public abstract List<Account> findOrderID(String paramString);
  
  public abstract List<Account> findAccountList(Integer paramInteger, String paramString);
  
  public abstract Page<Account> findBusinessPage(Page<Account> paramPage, Account paramAccount);
  
  public abstract List<Account> findAccountIds(Integer[] paramArrayOfInteger);
  
  public abstract List<Account> findAccountCountJe(String paramString);
  
  public abstract List<Account> findAccountCountYfk(String paramString);
  
  public abstract Page<Account> findOnTrustPage(Page<Account> paramPage, Account paramAccount);
  
  public abstract Page<Account> findOnPaymentPage(Page<Account> paramPage, Account paramAccount);
  
  public abstract List<Account> findAccountUnique(Integer paramInteger);
  
  public abstract void doBatchDelete(Integer[] paramArrayOfInteger);
  
  public abstract String doAccountRefer(Integer[] paramArrayOfInteger, Account paramAccount);
  
  public abstract String doAccountStat(Integer[] paramArrayOfInteger);
  
  public abstract int updateAccountBz(Account paramAccount);
  
  public abstract List<Account> findAccountCountYjs(String paramString, Integer paramInteger);
  
  public abstract int updateOrderId(Integer paramInteger, String paramString1, String paramString2);
  
  public abstract String doAjaxBatchDelete(Integer[] paramArrayOfInteger);
  
  public abstract String doSave(Account paramAccount);
}


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.service.AccountService
 * JD-Core Version:    0.7.0.1
 */