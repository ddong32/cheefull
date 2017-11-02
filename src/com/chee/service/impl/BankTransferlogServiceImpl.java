/*  1:   */ package com.chee.service.impl;
/*  2:   */ 
/*  3:   */ import com.chee.common.Page;
/*  4:   */ import com.chee.dao.BankTransferlogDao;
/*  5:   */ import com.chee.entity.BankTransferlog;
/*  6:   */ import com.chee.service.BankTransferlogService;
/*  7:   */ import javax.annotation.Resource;
/*  8:   */ import org.springframework.stereotype.Service;
/*  9:   */ 
/* 10:   */ @Service
/* 11:   */ public class BankTransferlogServiceImpl
/* 12:   */   extends BaseServiceImpl<BankTransferlog, Integer>
/* 13:   */   implements BankTransferlogService
/* 14:   */ {
/* 15:   */   @Resource
/* 16:   */   BankTransferlogDao bankTransferlogDao;
/* 17:   */   
/* 18:   */   @Resource
/* 19:   */   public void setBaseDao(BankTransferlogDao bankTransferlogDao)
/* 20:   */   {
/* 21:18 */     super.setBaseDao(bankTransferlogDao);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public Page<BankTransferlog> findBankTransferlogPage(Page<BankTransferlog> page, BankTransferlog bankTransferlog)
/* 25:   */   {
/* 26:22 */     return this.bankTransferlogDao.findBankTransferlogPage(page, bankTransferlog);
/* 27:   */   }
/* 28:   */ }


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.service.impl.BankTransferlogServiceImpl
 * JD-Core Version:    0.7.0.1
 */