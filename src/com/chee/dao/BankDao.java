package com.chee.dao;

import com.chee.entity.Bank;

public abstract interface BankDao extends BaseDao<Bank, Integer> {
    public abstract int updateBankCush(double paramDouble, Integer paramInteger);
}
