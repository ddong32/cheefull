/*  1:   */ package com.chee.util;
/*  2:   */ 
/*  3:   */ import org.springframework.beans.BeansException;
/*  4:   */ import org.springframework.context.ApplicationContext;
/*  5:   */ import org.springframework.context.ApplicationContextAware;
/*  6:   */ import org.springframework.stereotype.Component;
/*  7:   */ 
/*  8:   */ @Component
/*  9:   */ public class SpringUtil
/* 10:   */   implements ApplicationContextAware
/* 11:   */ {
/* 12:   */   private static ApplicationContext applicationContext;
/* 13:   */   
/* 14:   */   public void setApplicationContext(ApplicationContext applicationContext)
/* 15:   */     throws BeansException
/* 16:   */   {
/* 17:13 */     applicationContext = applicationContext;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public static ApplicationContext getApplicationContext()
/* 21:   */   {
/* 22:17 */     return applicationContext;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public static Object getBean(String name)
/* 26:   */     throws BeansException
/* 27:   */   {
/* 28:21 */     return applicationContext.getBean(name);
/* 29:   */   }
/* 30:   */ }


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.util.SpringUtil
 * JD-Core Version:    0.7.0.1
 */