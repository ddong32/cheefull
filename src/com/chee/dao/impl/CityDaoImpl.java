/*  1:   */ package com.chee.dao.impl;
/*  2:   */ 
/*  3:   */ import com.chee.dao.CityDao;
/*  4:   */ import com.chee.entity.City;
/*  5:   */ import java.util.List;
/*  6:   */ import org.hibernate.Query;
/*  7:   */ import org.hibernate.Session;
/*  8:   */ import org.springframework.stereotype.Repository;
/*  9:   */ 
/* 10:   */ @Repository
/* 11:   */ public class CityDaoImpl
/* 12:   */   extends BaseDaoImpl<City, Integer>
/* 13:   */   implements CityDao
/* 14:   */ {
/* 15:   */   public List<City> getCity(Integer provinceID)
/* 16:   */   {
/* 17:14 */     String hql = " from City where provinceID = " + provinceID + " order by cityID asc ";
/* 18:15 */     return getSession().createQuery(hql).setCacheable(true).list();
/* 19:   */   }
/* 20:   */ }


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.dao.impl.CityDaoImpl
 * JD-Core Version:    0.7.0.1
 */