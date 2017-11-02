/*  1:   */ package com.chee.dao.impl;
/*  2:   */ 
/*  3:   */ import com.chee.dao.BankDao;
/*  4:   */ import com.chee.entity.Bank;
/*  5:   */ import org.hibernate.SQLQuery;
/*  6:   */ import org.hibernate.Session;
/*  7:   */ import org.springframework.stereotype.Repository;
/*  8:   */ 
/*  9:   */ @Repository
/* 10:   */ public class BankDaoImpl
/* 11:   */   extends BaseDaoImpl<Bank, Integer>
/* 12:   */   implements BankDao
/* 13:   */ {
/* 14:   */   public int updateBankCush(double cush, Integer id)
/* 15:   */   {
/* 16:10 */     String sql = "update chee_bank t set t.cush = " + cush + " where t.id=" + id;
/* 17:11 */     return getSession().createSQLQuery(sql).executeUpdate();
/* 18:   */   }
/* 19:   */ }


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.dao.impl.BankDaoImpl
 * JD-Core Version:    0.7.0.1
 */