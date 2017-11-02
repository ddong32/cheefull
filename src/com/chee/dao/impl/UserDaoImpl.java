/*  1:   */ package com.chee.dao.impl;
/*  2:   */ 
/*  3:   */ import com.chee.dao.UserDao;
/*  4:   */ import com.chee.entity.User;
/*  5:   */ import java.util.List;
/*  6:   */ import org.hibernate.Query;
/*  7:   */ import org.hibernate.Session;
/*  8:   */ import org.springframework.stereotype.Repository;
/*  9:   */ 
/* 10:   */ @Repository
/* 11:   */ public class UserDaoImpl
/* 12:   */   extends BaseDaoImpl<User, Integer>
/* 13:   */   implements UserDao
/* 14:   */ {
/* 15:   */   public List<User> getUserList(String username, String password)
/* 16:   */   {
/* 17:13 */     String hql = " from User where username = ? and password = ?";
/* 18:14 */     return getSession().createQuery(hql).setParameter(0, username).setParameter(1, password).setCacheable(true).list();
/* 19:   */   }
/* 20:   */ }


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.dao.impl.UserDaoImpl
 * JD-Core Version:    0.7.0.1
 */