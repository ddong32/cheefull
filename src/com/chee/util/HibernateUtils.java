/*  1:   */ package com.chee.util;
/*  2:   */ 
/*  3:   */ import org.apache.commons.lang.StringUtils;
/*  4:   */ 
/*  5:   */ public class HibernateUtils
/*  6:   */ {
/*  7:   */   public static String escapeSQLLike(String likeStr)
/*  8:   */   {
/*  9: 7 */     if (StringUtils.isNotEmpty(likeStr)) {
/* 10: 8 */       return "%" + likeStr.replaceAll("_", "%") + "%";
/* 11:   */     }
/* 12:10 */     return likeStr;
/* 13:   */   }
/* 14:   */ }


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.util.HibernateUtils
 * JD-Core Version:    0.7.0.1
 */