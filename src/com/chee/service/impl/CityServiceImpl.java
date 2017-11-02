/*  1:   */ package com.chee.service.impl;
/*  2:   */ 
/*  3:   */ import com.chee.dao.CityDao;
/*  4:   */ import com.chee.entity.City;
/*  5:   */ import com.chee.service.CityService;
/*  6:   */ import java.util.List;
/*  7:   */ import javax.annotation.Resource;
/*  8:   */ import org.springframework.stereotype.Service;
/*  9:   */ 
/* 10:   */ @Service
/* 11:   */ public class CityServiceImpl
/* 12:   */   extends BaseServiceImpl<City, Integer>
/* 13:   */   implements CityService
/* 14:   */ {
/* 15:   */   @Resource
/* 16:   */   CityDao citykDao;
/* 17:   */   
/* 18:   */   @Resource
/* 19:   */   public void setBaseDao(CityDao citykDao)
/* 20:   */   {
/* 21:20 */     super.setBaseDao(citykDao);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public List<City> getCity(Integer provinceID)
/* 25:   */   {
/* 26:24 */     return this.citykDao.getCity(provinceID);
/* 27:   */   }
/* 28:   */ }


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.service.impl.CityServiceImpl
 * JD-Core Version:    0.7.0.1
 */