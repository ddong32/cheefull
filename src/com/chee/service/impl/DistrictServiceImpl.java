/*  1:   */ package com.chee.service.impl;
/*  2:   */ 
/*  3:   */ import com.chee.dao.DistrictDao;
/*  4:   */ import com.chee.entity.District;
/*  5:   */ import com.chee.service.DistrictService;
/*  6:   */ import java.util.List;
/*  7:   */ import javax.annotation.Resource;
/*  8:   */ import org.springframework.stereotype.Service;
/*  9:   */ 
/* 10:   */ @Service
/* 11:   */ public class DistrictServiceImpl
/* 12:   */   extends BaseServiceImpl<District, Integer>
/* 13:   */   implements DistrictService
/* 14:   */ {
/* 15:   */   @Resource
/* 16:   */   DistrictDao districtDao;
/* 17:   */   
/* 18:   */   @Resource
/* 19:   */   public void setBaseDao(DistrictDao districtDao)
/* 20:   */   {
/* 21:20 */     super.setBaseDao(districtDao);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public List<District> getDistrict(Integer cityID)
/* 25:   */   {
/* 26:24 */     return this.districtDao.getDistrict(cityID);
/* 27:   */   }
/* 28:   */ }


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.service.impl.DistrictServiceImpl
 * JD-Core Version:    0.7.0.1
 */