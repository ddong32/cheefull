/*  1:   */ package com.chee.service.impl;
/*  2:   */ 
/*  3:   */ import com.chee.dao.RoleDao;
/*  4:   */ import com.chee.entity.Role;
/*  5:   */ import com.chee.service.RoleService;
/*  6:   */ import com.chee.util.SpringUtil;
/*  7:   */ import javax.annotation.Resource;
/*  8:   */ import org.springframework.beans.factory.FactoryBean;
/*  9:   */ import org.springframework.security.intercept.web.FilterInvocationDefinitionSource;
/* 10:   */ import org.springframework.security.intercept.web.FilterSecurityInterceptor;
/* 11:   */ import org.springframework.stereotype.Service;
/* 12:   */ 
/* 13:   */ @Service
/* 14:   */ public class RoleServiceImpl
/* 15:   */   extends BaseServiceImpl<Role, Integer>
/* 16:   */   implements RoleService
/* 17:   */ {
/* 18:   */   @Resource
/* 19:   */   RoleDao roleDao;
/* 20:   */   
/* 21:   */   @Resource
/* 22:   */   public void setBaseDao(RoleDao roleDao)
/* 23:   */   {
/* 24:21 */     super.setBaseDao(roleDao);
/* 25:   */   }
/* 26:   */   
/* 27:   */   public void delete(Role role)
/* 28:   */   {
/* 29:25 */     this.roleDao.delete(role);
/* 30:26 */     this.roleDao.flush();
/* 31:27 */     flushSpringSecurity();
/* 32:   */   }
/* 33:   */   
/* 34:   */   public void delete(Integer id)
/* 35:   */   {
/* 36:31 */     Role role = (Role)this.roleDao.load(id);
/* 37:32 */     delete(role);
/* 38:   */   }
/* 39:   */   
/* 40:   */   public void delete(Integer[] ids)
/* 41:   */   {
/* 42:36 */     for (Integer id : ids)
/* 43:   */     {
/* 44:37 */       Role role = (Role)this.roleDao.load(id);
/* 45:38 */       this.roleDao.delete(role);
/* 46:   */     }
/* 47:40 */     this.roleDao.flush();
/* 48:41 */     flushSpringSecurity();
/* 49:   */   }
/* 50:   */   
/* 51:   */   public Integer save(Role role)
/* 52:   */   {
/* 53:45 */     Integer id = (Integer)this.roleDao.save(role);
/* 54:46 */     this.roleDao.flush();
/* 55:47 */     this.roleDao.clear();
/* 56:48 */     flushSpringSecurity();
/* 57:49 */     return id;
/* 58:   */   }
/* 59:   */   
/* 60:   */   public void update(Role role)
/* 61:   */   {
/* 62:53 */     this.roleDao.update(role);
/* 63:54 */     this.roleDao.flush();
/* 64:55 */     this.roleDao.clear();
/* 65:56 */     flushSpringSecurity();
/* 66:   */   }
/* 67:   */   
/* 68:   */   public void saveOrUpdate(Role role)
/* 69:   */   {
/* 70:60 */     this.roleDao.saveOrUpdate(role);
/* 71:61 */     this.roleDao.flush();
/* 72:62 */     this.roleDao.clear();
/* 73:63 */     flushSpringSecurity();
/* 74:   */   }
/* 75:   */   
/* 76:   */   private void flushSpringSecurity()
/* 77:   */   {
/* 78:   */     try
/* 79:   */     {
/* 80:68 */       FactoryBean factoryBean = (FactoryBean)SpringUtil.getBean("&userSecurityDefinitionSource");
/* 81:69 */       FilterInvocationDefinitionSource filterInvocationDefinitionSource = (FilterInvocationDefinitionSource)factoryBean.getObject();
/* 82:70 */       FilterSecurityInterceptor filterSecurityInterceptor = (FilterSecurityInterceptor)SpringUtil.getBean("filterSecurityInterceptor");
/* 83:71 */       filterSecurityInterceptor.setObjectDefinitionSource(filterInvocationDefinitionSource);
/* 84:   */     }
/* 85:   */     catch (Exception e)
/* 86:   */     {
/* 87:73 */       e.printStackTrace();
/* 88:   */     }
/* 89:   */   }
/* 90:   */ }


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.service.impl.RoleServiceImpl
 * JD-Core Version:    0.7.0.1
 */