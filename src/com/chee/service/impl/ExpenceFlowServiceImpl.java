/*  1:   */ package com.chee.service.impl;
/*  2:   */ 
/*  3:   */ import com.chee.dao.ExpenceFlowDao;
/*  4:   */ import com.chee.entity.Expence;
/*  5:   */ import com.chee.entity.ExpenceFlow;
/*  6:   */ import com.chee.service.ExpenceFlowService;
/*  7:   */ import java.util.List;
/*  8:   */ import javax.annotation.Resource;
/*  9:   */ import org.springframework.stereotype.Service;
/* 10:   */ 
/* 11:   */ @Service
/* 12:   */ public class ExpenceFlowServiceImpl
/* 13:   */   extends BaseServiceImpl<ExpenceFlow, Integer>
/* 14:   */   implements ExpenceFlowService
/* 15:   */ {
/* 16:   */   @Resource
/* 17:   */   private ExpenceFlowDao expenceFlowDao;
/* 18:   */   
/* 19:   */   @Resource
/* 20:   */   public void setBaseDao(ExpenceFlowDao expenceFlowDao)
/* 21:   */   {
/* 22:21 */     super.setBaseDao(expenceFlowDao);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public List<ExpenceFlow> findExpenceFlowList(Expence expence)
/* 26:   */   {
/* 27:25 */     return this.expenceFlowDao.findExpenceFlowList(expence);
/* 28:   */   }
/* 29:   */ }


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.service.impl.ExpenceFlowServiceImpl
 * JD-Core Version:    0.7.0.1
 */