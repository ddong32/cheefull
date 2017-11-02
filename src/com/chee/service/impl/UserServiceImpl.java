/*  1:   */ package com.chee.service.impl;
/*  2:   */ 
/*  3:   */ import com.chee.annotation.rmpfLog;
/*  4:   */ import com.chee.dao.UserDao;
/*  5:   */ import com.chee.entity.User;
/*  6:   */ import com.chee.service.UserService;
/*  7:   */ import java.util.List;
/*  8:   */ import javax.annotation.Resource;
/*  9:   */ import org.springframework.stereotype.Service;
/* 10:   */ 
/* 11:   */ @Service
/* 12:   */ public class UserServiceImpl
/* 13:   */   extends BaseServiceImpl<User, Integer>
/* 14:   */   implements UserService
/* 15:   */ {
/* 16:   */   @Resource
/* 17:   */   UserDao userDao;
/* 18:   */   
/* 19:   */   @Resource
/* 20:   */   public void setBaseDao(UserDao userDao)
/* 21:   */   {
/* 22:19 */     super.setBaseDao(userDao);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public List<User> getUserList(String username, String password)
/* 26:   */   {
/* 27:23 */     return this.userDao.getUserList(username, password);
/* 28:   */   }
/* 29:   */   
/* 30:   */   @rmpfLog(desc="用户信息修改")
/* 31:   */   public void update(User entity)
/* 32:   */   {
/* 33:28 */     this.userDao.update(entity);
/* 34:   */   }
/* 35:   */ }


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.service.impl.UserServiceImpl
 * JD-Core Version:    0.7.0.1
 */