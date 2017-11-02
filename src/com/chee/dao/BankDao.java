package com.chee.dao;

import com.chee.entity.Bank;

public abstract interface BankDao
  extends BaseDao<Bank, Integer>
{
  public abstract int updateBankCush(double paramDouble, Integer paramInteger);
}


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.dao.BankDao
 * JD-Core Version:    0.7.0.1
 */