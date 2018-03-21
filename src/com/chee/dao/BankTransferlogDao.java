package com.chee.dao;

import com.chee.common.Page;
import com.chee.entity.BankTransferlog;

public abstract interface BankTransferlogDao extends BaseDao<BankTransferlog, Integer> {
    public abstract Page<BankTransferlog> findBankTransferlogPage(Page<BankTransferlog> paramPage, BankTransferlog paramBankTransferlog);
}
