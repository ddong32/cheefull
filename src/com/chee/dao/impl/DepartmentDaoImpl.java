/*  1:   */ package com.chee.dao.impl;
/*  2:   */ 
/*  3:   */ import com.chee.dao.DepartmentDao;
/*  4:   */ import com.chee.entity.Department;
/*  5:   */ import java.util.List;
/*  6:   */ import org.hibernate.Query;
/*  7:   */ import org.hibernate.SQLQuery;
/*  8:   */ import org.hibernate.Session;
/*  9:   */ import org.springframework.stereotype.Repository;
/* 10:   */ 
/* 11:   */ @Repository
/* 12:   */ public class DepartmentDaoImpl
/* 13:   */   extends BaseDaoImpl<Department, Integer>
/* 14:   */   implements DepartmentDao
/* 15:   */ {
/* 16:   */   public Integer save(Department department)
/* 17:   */   {
/* 18:11 */     Integer id = (Integer)super.save(department);
/* 19:12 */     Department parent = department.getParent();
/* 20:13 */     if ((parent != null) && (parent.getPath() != null) && (!parent.getPath().equals(""))) {
/* 21:14 */       department.setPath(parent.getPath() + id + ",");
/* 22:   */     } else {
/* 23:16 */       department.setPath(id.toString() + ",");
/* 24:   */     }
/* 25:18 */     department.setGrade(Integer.valueOf(department.getPath().split(",").length));
/* 26:19 */     super.update(department);
/* 27:20 */     return id;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public void update(Department department)
/* 31:   */   {
/* 32:24 */     Department parent = department.getParent();
/* 33:25 */     if (parent != null)
/* 34:   */     {
/* 35:26 */       String parentPath = parent.getPath();
/* 36:27 */       department.setPath(parentPath + department.getId() + ",");
/* 37:   */     }
/* 38:   */     else
/* 39:   */     {
/* 40:29 */       department.setPath(department.getId().toString() + ",");
/* 41:   */     }
/* 42:31 */     department.setGrade(Integer.valueOf(department.getPath().split(",").length));
/* 43:32 */     super.update(department);
/* 44:   */   }
/* 45:   */   
/* 46:   */   public List<String> UnParentOrgCodeById(Integer id)
/* 47:   */   {
/* 48:37 */     String sql = "select org_code from chee_department where FIND_IN_SET(id, getChildidList(" + id + "))";
/* 49:38 */     return getSession().createSQLQuery(sql).list();
/* 50:   */   }
/* 51:   */   
/* 52:   */   public List<Department> getConnectOrgs(Integer id)
/* 53:   */   {
/* 54:43 */     String sql = "select * from chee_department where FIND_IN_SET(id, getChildidList(?))";
/* 55:44 */     return getSession().createSQLQuery(sql).addEntity(Department.class).setParameter(0, id).list();
/* 56:   */   }
/* 57:   */ }


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.dao.impl.DepartmentDaoImpl
 * JD-Core Version:    0.7.0.1
 */