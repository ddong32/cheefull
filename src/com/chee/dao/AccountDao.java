package com.chee.dao;

import com.chee.common.Page;
import com.chee.entity.Account;
import java.util.List;

public abstract interface AccountDao extends BaseDao<Account, Integer> {
    public abstract List<Account> findOrderID(String paramString);

    public abstract List<Account> findAccountList(Integer paramInteger, String paramString);

    public abstract Page<Account> findBusinessPage(Page<Account> paramPage, Account paramAccount);

    public abstract List<Account> findAccountIds(Integer[] paramArrayOfInteger);

    public abstract List<Account> findAccountCountJe(String paramString);

    public abstract List<Account> findAccountCountYfk(String paramString);

    public abstract Page<Account> findOnTrustPage(Page<Account> paramPage, Account paramAccount);

    public abstract Page<Account> findOnPaymentPage(Page<Account> paramPage, Account paramAccount);

    public abstract List<Account> findAccountUnique(Integer paramInteger);

    public abstract int updateAccountBz(Account paramAccount);

    public abstract List<Account> findAccountCountYjs(String paramString, Integer paramInteger);
}
