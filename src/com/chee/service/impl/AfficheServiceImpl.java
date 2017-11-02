/*  1:   */ package com.chee.service.impl;
/*  2:   */ 
/*  3:   */ import com.chee.dao.AfficheDao;
/*  4:   */ import com.chee.entity.Affiche;
/*  5:   */ import com.chee.service.AfficheService;
/*  6:   */ import java.util.List;
/*  7:   */ import javax.annotation.Resource;
/*  8:   */ import org.springframework.stereotype.Service;
/*  9:   */ 
/* 10:   */ @Service
/* 11:   */ public class AfficheServiceImpl
/* 12:   */   extends BaseServiceImpl<Affiche, Integer>
/* 13:   */   implements AfficheService
/* 14:   */ {
/* 15:   */   @Resource
/* 16:   */   AfficheDao afficheDao;
/* 17:   */   
/* 18:   */   @Resource
/* 19:   */   public void setBaseDao(AfficheDao afficheDao)
/* 20:   */   {
/* 21:18 */     super.setBaseDao(afficheDao);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void doBatchDelete(Integer[] ids)
/* 25:   */   {
/* 26:22 */     this.afficheDao.delete(ids);
/* 27:   */   }
/* 28:   */   
/* 29:   */   public List<Affiche> getMainPageAfficheList()
/* 30:   */   {
/* 31:26 */     return this.afficheDao.getMainPageAfficheList();
/* 32:   */   }
/* 33:   */ }


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.service.impl.AfficheServiceImpl
 * JD-Core Version:    0.7.0.1
 */