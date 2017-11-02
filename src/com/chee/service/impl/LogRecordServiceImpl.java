/*  1:   */ package com.chee.service.impl;
/*  2:   */ 
/*  3:   */ import com.chee.dao.LogRecordDao;
/*  4:   */ import com.chee.entity.Syslog;
/*  5:   */ import com.chee.service.LogRecordService;
/*  6:   */ import javax.annotation.Resource;
/*  7:   */ import org.springframework.stereotype.Service;
/*  8:   */ 
/*  9:   */ @Service
/* 10:   */ public class LogRecordServiceImpl
/* 11:   */   extends BaseServiceImpl<Syslog, Integer>
/* 12:   */   implements LogRecordService
/* 13:   */ {
/* 14:   */   @Resource
/* 15:   */   LogRecordDao logRecordDao;
/* 16:   */   
/* 17:   */   @Resource
/* 18:   */   public void setBaseDao(LogRecordDao logRecordDao)
/* 19:   */   {
/* 20:17 */     super.setBaseDao(logRecordDao);
/* 21:   */   }
/* 22:   */ }


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.service.impl.LogRecordServiceImpl
 * JD-Core Version:    0.7.0.1
 */