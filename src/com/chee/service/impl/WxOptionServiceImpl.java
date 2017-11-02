/*  1:   */ package com.chee.service.impl;
/*  2:   */ 
/*  3:   */ import com.chee.dao.WxOptionDao;
/*  4:   */ import com.chee.entity.WxOption;
/*  5:   */ import com.chee.service.WxOptionService;
/*  6:   */ import javax.annotation.Resource;
/*  7:   */ import org.springframework.stereotype.Service;
/*  8:   */ 
/*  9:   */ @Service
/* 10:   */ public class WxOptionServiceImpl
/* 11:   */   extends BaseServiceImpl<WxOption, Integer>
/* 12:   */   implements WxOptionService
/* 13:   */ {
/* 14:   */   @Resource
/* 15:   */   WxOptionDao wechatOptionDao;
/* 16:   */   
/* 17:   */   @Resource
/* 18:   */   public void setBaseDao(WxOptionDao wechatOptionDao)
/* 19:   */   {
/* 20:18 */     super.setBaseDao(wechatOptionDao);
/* 21:   */   }
/* 22:   */ }


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.service.impl.WxOptionServiceImpl
 * JD-Core Version:    0.7.0.1
 */