/*  1:   */ package com.chee.dao.impl;
/*  2:   */ 
/*  3:   */ import com.chee.common.Page;
/*  4:   */ import com.chee.dao.BusinessCustomerDao;
/*  5:   */ import com.chee.entity.BusinessCustomer;
/*  6:   */ import java.util.ArrayList;
/*  7:   */ import java.util.List;
/*  8:   */ import org.springframework.stereotype.Repository;
/*  9:   */ 
/* 10:   */ @Repository
/* 11:   */ public class BusinessCustomerDaoImpl
/* 12:   */   extends BaseDaoImpl<BusinessCustomer, Integer>
/* 13:   */   implements BusinessCustomerDao
/* 14:   */ {
/* 15:   */   public Page<BusinessCustomer> findPage(Page<BusinessCustomer> page, BusinessCustomer businessCustomer)
/* 16:   */   {
/* 17:16 */     return null;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public List<BusinessCustomer> findOrderID(String order_id)
/* 21:   */   {
/* 22:19 */     List<BusinessCustomer> l1 = new ArrayList();
/* 23:20 */     StringBuffer hqlSb = new StringBuffer();
/* 24:21 */     hqlSb.append("FROM BusinessCustomer bc WHERE 1=1");
/* 25:22 */     hqlSb.append(" and bc.orderId = '" + order_id + "'");
/* 26:23 */     hqlSb.append(" order by customerDwmc, lrsj ");
/* 27:24 */     l1 = find(hqlSb.toString(), new Object[0]);
/* 28:   */     
/* 29:   */ 
/* 30:   */ 
/* 31:   */ 
/* 32:   */ 
/* 33:   */ 
/* 34:   */ 
/* 35:   */ 
/* 36:   */ 
/* 37:34 */     return l1;
/* 38:   */   }
/* 39:   */ }


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.dao.impl.BusinessCustomerDaoImpl
 * JD-Core Version:    0.7.0.1
 */