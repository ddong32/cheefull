/*  1:   */ package com.chee.dao.impl;
/*  2:   */ 
/*  3:   */ import com.chee.dao.SysCodeDao;
/*  4:   */ import com.chee.entity.SysCode;
/*  5:   */ import java.util.List;
/*  6:   */ import org.hibernate.Query;
/*  7:   */ import org.hibernate.Session;
/*  8:   */ import org.springframework.stereotype.Repository;
/*  9:   */ 
/* 10:   */ @Repository
/* 11:   */ public class SysCodeDaoImpl
/* 12:   */   extends BaseDaoImpl<SysCode, Integer>
/* 13:   */   implements SysCodeDao
/* 14:   */ {
/* 15:   */   public List<SysCode> getSysCodeList(int stat, int codeType)
/* 16:   */   {
/* 17:12 */     String hql = " from SysCode where stat = ? and codeType = ? order by codeOrder asc";
/* 18:13 */     return getSession().createQuery(hql).setParameter(0, Integer.valueOf(stat)).setParameter(1, Integer.valueOf(codeType)).setCacheable(true).list();
/* 19:   */   }
/* 20:   */ }


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.dao.impl.SysCodeDaoImpl
 * JD-Core Version:    0.7.0.1
 */