package com.chee.dao.impl;

import com.chee.dao.BankDao;
import com.chee.entity.Bank;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

@Repository
public class BankDaoImpl extends BaseDaoImpl<Bank, Integer> implements BankDao {
    public int updateBankCush(double cush, Integer id) {
        String sql = "update chee_bank t set t.cush = " + cush + " where t.id=" + id;
        return getSession().createSQLQuery(sql).executeUpdate();
    }
}
