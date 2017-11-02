/*  1:   */ package com.chee.service.impl;
/*  2:   */ 
/*  3:   */ import com.chee.common.Page;
/*  4:   */ import com.chee.dao.CooperatorDao;
/*  5:   */ import com.chee.entity.Cooperator;
/*  6:   */ import com.chee.service.CooperatorService;
/*  7:   */ import javax.annotation.Resource;
/*  8:   */ import org.springframework.stereotype.Service;
/*  9:   */ 
/* 10:   */ @Service
/* 11:   */ public class CooperatorServiceImpl
/* 12:   */   extends BaseServiceImpl<Cooperator, Integer>
/* 13:   */   implements CooperatorService
/* 14:   */ {
/* 15:   */   @Resource
/* 16:   */   CooperatorDao cooperatorDao;
/* 17:   */   
/* 18:   */   @Resource
/* 19:   */   public void setBaseDao(CooperatorDao cooperatorDao)
/* 20:   */   {
/* 21:19 */     super.setBaseDao(cooperatorDao);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public Page<Cooperator> findCooperatorDialogPage(Page<Cooperator> page, Cooperator cooperator)
/* 25:   */   {
/* 26:23 */     return this.cooperatorDao.findCooperatorDialogPage(page, cooperator);
/* 27:   */   }
/* 28:   */ }


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.service.impl.CooperatorServiceImpl
 * JD-Core Version:    0.7.0.1
 */