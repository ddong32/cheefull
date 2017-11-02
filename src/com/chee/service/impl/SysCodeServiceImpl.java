/*  1:   */ package com.chee.service.impl;
/*  2:   */ 
/*  3:   */ import com.chee.dao.SysCodeDao;
/*  4:   */ import com.chee.entity.SysCode;
/*  5:   */ import com.chee.service.SysCodeService;
/*  6:   */ import java.util.List;
/*  7:   */ import javax.annotation.Resource;
/*  8:   */ import org.springframework.stereotype.Service;
/*  9:   */ 
/* 10:   */ @Service
/* 11:   */ public class SysCodeServiceImpl
/* 12:   */   extends BaseServiceImpl<SysCode, Integer>
/* 13:   */   implements SysCodeService
/* 14:   */ {
/* 15:   */   @Resource
/* 16:   */   SysCodeDao sysCodeDao;
/* 17:   */   
/* 18:   */   @Resource
/* 19:   */   public void setBaseDao(SysCodeDao sysCodeDao)
/* 20:   */   {
/* 21:18 */     super.setBaseDao(sysCodeDao);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public List<SysCode> getSysCodeList(int stat, int codeType)
/* 25:   */   {
/* 26:22 */     return this.sysCodeDao.getSysCodeList(stat, codeType);
/* 27:   */   }
/* 28:   */ }


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.service.impl.SysCodeServiceImpl
 * JD-Core Version:    0.7.0.1
 */