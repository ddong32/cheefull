/*  1:   */ package com.chee.service.impl;
/*  2:   */ 
/*  3:   */ import com.chee.common.Page;
/*  4:   */ import com.chee.dao.TravelDao;
/*  5:   */ import com.chee.entity.Travel;
/*  6:   */ import com.chee.service.TravelService;
/*  7:   */ import javax.annotation.Resource;
/*  8:   */ import org.springframework.stereotype.Service;
/*  9:   */ 
/* 10:   */ @Service
/* 11:   */ public class TravelServiceImpl
/* 12:   */   extends BaseServiceImpl<Travel, Integer>
/* 13:   */   implements TravelService
/* 14:   */ {
/* 15:   */   @Resource
/* 16:   */   TravelDao travelDao;
/* 17:   */   
/* 18:   */   @Resource
/* 19:   */   public void setBaseDao(TravelDao travelDao)
/* 20:   */   {
/* 21:18 */     super.setBaseDao(travelDao);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public Page<Travel> findTravelDialogPage(Page<Travel> page, Travel travel)
/* 25:   */   {
/* 26:22 */     return this.travelDao.findTravelDialogPage(page, travel);
/* 27:   */   }
/* 28:   */ }


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.service.impl.TravelServiceImpl
 * JD-Core Version:    0.7.0.1
 */