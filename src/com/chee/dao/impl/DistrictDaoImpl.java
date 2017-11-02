/*  1:   */ package com.chee.dao.impl;
/*  2:   */ 
/*  3:   */ import com.chee.dao.DistrictDao;
/*  4:   */ import com.chee.entity.District;
/*  5:   */ import java.util.List;
/*  6:   */ import org.hibernate.Query;
/*  7:   */ import org.hibernate.Session;
/*  8:   */ import org.springframework.stereotype.Repository;
/*  9:   */ 
/* 10:   */ @Repository
/* 11:   */ public class DistrictDaoImpl
/* 12:   */   extends BaseDaoImpl<District, Integer>
/* 13:   */   implements DistrictDao
/* 14:   */ {
/* 15:   */   public List<District> getDistrict(Integer cityID)
/* 16:   */   {
/* 17:14 */     String hql = " from District where cityID = " + cityID + " order by districtID asc ";
/* 18:15 */     return getSession().createQuery(hql).setCacheable(true).list();
/* 19:   */   }
/* 20:   */ }


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.dao.impl.DistrictDaoImpl
 * JD-Core Version:    0.7.0.1
 */