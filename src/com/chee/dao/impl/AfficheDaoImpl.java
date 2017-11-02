/*  1:   */ package com.chee.dao.impl;
/*  2:   */ 
/*  3:   */ import com.chee.dao.AfficheDao;
/*  4:   */ import com.chee.entity.Affiche;
/*  5:   */ import java.util.List;
/*  6:   */ import org.hibernate.Query;
/*  7:   */ import org.hibernate.Session;
/*  8:   */ import org.springframework.stereotype.Repository;
/*  9:   */ 
/* 10:   */ @Repository
/* 11:   */ public class AfficheDaoImpl
/* 12:   */   extends BaseDaoImpl<Affiche, Integer>
/* 13:   */   implements AfficheDao
/* 14:   */ {
/* 15:   */   public List<Affiche> getMainPageAfficheList()
/* 16:   */   {
/* 17:13 */     String hql = "from Affiche t1 left join t1.user t2 where stat=1 order by t1.id desc limit 10";
/* 18:14 */     return getSession().createQuery(hql).list();
/* 19:   */   }
/* 20:   */ }


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.dao.impl.AfficheDaoImpl
 * JD-Core Version:    0.7.0.1
 */