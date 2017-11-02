/*   1:    */ package com.chee.service.impl;
/*   2:    */ 
/*   3:    */ import com.chee.dao.ResourceDao;
/*   4:    */ import com.chee.service.ResourceService;
/*   5:    */ import com.chee.util.SpringUtil;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Set;
/*   8:    */ import org.springframework.beans.factory.FactoryBean;
/*   9:    */ import org.springframework.security.intercept.web.FilterInvocationDefinitionSource;
/*  10:    */ import org.springframework.security.intercept.web.FilterSecurityInterceptor;
/*  11:    */ import org.springframework.stereotype.Service;
/*  12:    */ 
/*  13:    */ @Service
/*  14:    */ public class ResourceServiceImpl
/*  15:    */   extends BaseServiceImpl<com.chee.entity.Resource, Integer>
/*  16:    */   implements ResourceService
/*  17:    */ {
/*  18:    */   @javax.annotation.Resource
/*  19:    */   ResourceDao resourceDao;
/*  20:    */   
/*  21:    */   @javax.annotation.Resource
/*  22:    */   public void setBaseDao(ResourceDao resourceDao)
/*  23:    */   {
/*  24: 22 */     super.setBaseDao(resourceDao);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public void delete(com.chee.entity.Resource resource)
/*  28:    */   {
/*  29: 26 */     this.resourceDao.delete(resource);
/*  30: 27 */     this.resourceDao.flush();
/*  31: 28 */     flushSpringSecurity();
/*  32:    */   }
/*  33:    */   
/*  34:    */   public void delete(Integer id)
/*  35:    */   {
/*  36: 32 */     com.chee.entity.Resource resource = (com.chee.entity.Resource)this.resourceDao.load(id);
/*  37: 33 */     delete(resource);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public void delete(Integer[] ids)
/*  41:    */   {
/*  42: 37 */     for (Integer id : ids)
/*  43:    */     {
/*  44: 38 */       com.chee.entity.Resource resource = (com.chee.entity.Resource)this.resourceDao.load(id);
/*  45: 39 */       this.resourceDao.delete(resource);
/*  46:    */     }
/*  47: 41 */     this.resourceDao.flush();
/*  48: 42 */     flushSpringSecurity();
/*  49:    */   }
/*  50:    */   
/*  51:    */   public List<com.chee.entity.Resource> getChildrenResourceList(com.chee.entity.Resource resource)
/*  52:    */   {
/*  53: 46 */     return this.resourceDao.getChildrenResourceList(resource);
/*  54:    */   }
/*  55:    */   
/*  56:    */   public List<com.chee.entity.Resource> getRootResourceList()
/*  57:    */   {
/*  58: 50 */     return this.resourceDao.getRootResourceList();
/*  59:    */   }
/*  60:    */   
/*  61:    */   public Integer save(com.chee.entity.Resource resource)
/*  62:    */   {
/*  63: 54 */     Integer id = (Integer)this.resourceDao.save(resource);
/*  64: 55 */     this.resourceDao.flush();
/*  65: 56 */     flushSpringSecurity();
/*  66: 57 */     return id;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void update(com.chee.entity.Resource resource)
/*  70:    */   {
/*  71: 61 */     this.resourceDao.update(resource);
/*  72:    */     
/*  73: 63 */     updateChildren(resource);
/*  74: 64 */     this.resourceDao.flush();
/*  75: 65 */     flushSpringSecurity();
/*  76:    */   }
/*  77:    */   
/*  78:    */   private void updateChildren(com.chee.entity.Resource resource)
/*  79:    */   {
/*  80: 69 */     for (com.chee.entity.Resource children : resource.getChildren())
/*  81:    */     {
/*  82: 70 */       this.resourceDao.update(children);
/*  83: 71 */       if (children.getChildren().size() > 0) {
/*  84: 72 */         updateChildren(children);
/*  85:    */       }
/*  86:    */     }
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void saveOrUpdate(com.chee.entity.Resource resource)
/*  90:    */   {
/*  91: 77 */     this.resourceDao.saveOrUpdate(resource);
/*  92: 78 */     this.resourceDao.flush();
/*  93: 79 */     flushSpringSecurity();
/*  94:    */   }
/*  95:    */   
/*  96:    */   private void flushSpringSecurity()
/*  97:    */   {
/*  98:    */     try
/*  99:    */     {
/* 100: 84 */       FactoryBean factoryBean = (FactoryBean)SpringUtil.getBean("&userSecurityDefinitionSource");
/* 101: 85 */       FilterInvocationDefinitionSource filterInvocationDefinitionSource = (FilterInvocationDefinitionSource)factoryBean.getObject();
/* 102: 86 */       FilterSecurityInterceptor filterSecurityInterceptor = (FilterSecurityInterceptor)SpringUtil.getBean("filterSecurityInterceptor");
/* 103: 87 */       filterSecurityInterceptor.setObjectDefinitionSource(filterInvocationDefinitionSource);
/* 104:    */     }
/* 105:    */     catch (Exception e)
/* 106:    */     {
/* 107: 89 */       e.printStackTrace();
/* 108:    */     }
/* 109:    */   }
/* 110:    */   
/* 111:    */   public List<com.chee.entity.Resource> getLevel1MenuList(String username)
/* 112:    */   {
/* 113: 94 */     return this.resourceDao.getLevel1MenuList(username);
/* 114:    */   }
/* 115:    */   
/* 116:    */   public List<com.chee.entity.Resource> getLevel2MenuList(String username)
/* 117:    */   {
/* 118: 98 */     return this.resourceDao.getLevel2MenuList(username);
/* 119:    */   }
/* 120:    */   
/* 121:    */   public List<com.chee.entity.Resource> getLevel3MenuList(String username, Integer parentId)
/* 122:    */   {
/* 123:102 */     return this.resourceDao.getLevel3MenuList(username, parentId);
/* 124:    */   }
/* 125:    */ }


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.service.impl.ResourceServiceImpl
 * JD-Core Version:    0.7.0.1
 */