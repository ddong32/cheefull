package com.chee.service;

import java.util.List;
import java.util.Map;

import com.chee.entity.Bank;

public abstract interface BankService extends BaseService<Bank, Integer> {
    public abstract int updateBankCush(double paramDouble, Integer paramInteger);

    public abstract String doTransfer(Bank paramBank, String paramString1, String paramString2) throws Exception;
    
    public abstract Map<String, Object> getBankRuningMoneyStat() throws Exception;
}
