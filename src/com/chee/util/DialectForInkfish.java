/* 1:  */ package com.chee.util;
/* 2:  */ 
/* 3:  */ import org.hibernate.dialect.MySQL5Dialect;
/* 4:  */ 
/* 5:  */ public class DialectForInkfish
/* 6:  */   extends MySQL5Dialect
/* 7:  */ {
/* 8:  */   public DialectForInkfish()
/* 9:  */   {
/* ::9 */     registerHibernateType(-1, 65535, "text");
/* ;:  */   }
/* <:  */ }


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.util.DialectForInkfish
 * JD-Core Version:    0.7.0.1
 */