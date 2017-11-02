package com.chee.service;

import com.chee.entity.Bank;

public abstract interface BankService
  extends BaseService<Bank, Integer>
{
  public abstract int updateBankCush(double paramDouble, Integer paramInteger);
  
  public abstract String doTransfer(Bank paramBank, String paramString1, String paramString2)
    throws Exception;
}


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.service.BankService
 * JD-Core Version:    0.7.0.1
 */