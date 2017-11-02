/*  1:   */ package com.chee.service.impl;
/*  2:   */ 
/*  3:   */ import com.chee.dao.DepartmentDao;
/*  4:   */ import com.chee.entity.Department;
/*  5:   */ import com.chee.service.DepartmentService;
/*  6:   */ import java.util.ArrayList;
/*  7:   */ import java.util.List;
/*  8:   */ import java.util.Set;
/*  9:   */ import javax.annotation.Resource;
/* 10:   */ import org.springframework.stereotype.Service;
/* 11:   */ 
/* 12:   */ @Service
/* 13:   */ public class DepartmentServiceImpl
/* 14:   */   extends BaseServiceImpl<Department, Integer>
/* 15:   */   implements DepartmentService
/* 16:   */ {
/* 17:   */   @Resource
/* 18:   */   private DepartmentDao departmentDao;
/* 19:   */   
/* 20:   */   @Resource
/* 21:   */   public void setBaseDao(DepartmentDao departmentDao)
/* 22:   */   {
/* 23:20 */     super.setBaseDao(departmentDao);
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void update(Department entity)
/* 27:   */   {
/* 28:24 */     this.departmentDao.update(entity);
/* 29:25 */     updateChildren(entity);
/* 30:   */   }
/* 31:   */   
/* 32:   */   private void updateChildren(Department entity)
/* 33:   */   {
/* 34:29 */     for (Department children : entity.getChildren())
/* 35:   */     {
/* 36:30 */       this.departmentDao.update(children);
/* 37:31 */       if (children.getChildren().size() > 0) {
/* 38:32 */         updateChildren(children);
/* 39:   */       }
/* 40:   */     }
/* 41:   */   }
/* 42:   */   
/* 43:   */   public List<String> UnParentOrgCodeById(Integer id)
/* 44:   */   {
/* 45:37 */     return this.departmentDao.UnParentOrgCodeById(id);
/* 46:   */   }
/* 47:   */   
/* 48:   */   public List<Department> getConnectOrgs(Integer id)
/* 49:   */   {
/* 50:41 */     return this.departmentDao.getConnectOrgs(id);
/* 51:   */   }
/* 52:   */   
/* 53:   */   public List<String> getConnectOrgCodes(Integer id)
/* 54:   */   {
/* 55:45 */     List orgCodeList = new ArrayList();
/* 56:46 */     List organizationList = this.departmentDao.getConnectOrgs(id);
/* 57:47 */     if (organizationList != null) {
/* 58:48 */       for (int idx = 0; idx < organizationList.size(); idx++)
/* 59:   */       {
/* 60:49 */         Department orgObj = (Department)organizationList.get(idx);
/* 61:50 */         if ((orgObj != null) && (orgObj.getCode() != null) && (!"".equals(orgObj.getCode()))) {
/* 62:51 */           orgCodeList.add(orgObj.getCode());
/* 63:   */         }
/* 64:   */       }
/* 65:   */     }
/* 66:55 */     return orgCodeList;
/* 67:   */   }
/* 68:   */ }


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.service.impl.DepartmentServiceImpl
 * JD-Core Version:    0.7.0.1
 */