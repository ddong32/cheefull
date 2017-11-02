/*   1:    */ package com.chee.dao.impl;
/*   2:    */ 
/*   3:    */ import com.chee.dao.ResourceDao;
/*   4:    */ import com.chee.entity.Resource;
/*   5:    */ import java.util.List;
/*   6:    */ import org.hibernate.Query;
/*   7:    */ import org.hibernate.SQLQuery;
/*   8:    */ import org.hibernate.Session;
/*   9:    */ import org.springframework.stereotype.Repository;
/*  10:    */ 
/*  11:    */ @Repository
/*  12:    */ public class ResourceDaoImpl
/*  13:    */   extends BaseDaoImpl<Resource, Integer>
/*  14:    */   implements ResourceDao
/*  15:    */ {
/*  16:    */   public void delete(Integer id)
/*  17:    */   {
/*  18: 26 */     Resource resource = (Resource)load(id);
/*  19: 27 */     delete(resource);
/*  20:    */   }
/*  21:    */   
/*  22:    */   public void delete(Integer[] ids)
/*  23:    */   {
/*  24: 31 */     for (Integer id : ids) {
/*  25: 32 */       delete(id);
/*  26:    */     }
/*  27:    */   }
/*  28:    */   
/*  29:    */   public Integer save(Resource resource)
/*  30:    */   {
/*  31: 36 */     Integer id = (Integer)super.save(resource);
/*  32: 37 */     Resource parent = resource.getParent();
/*  33: 38 */     if ((parent != null) && (parent.getPath() != null) && (!parent.getPath().equals(""))) {
/*  34: 39 */       resource.setPath(parent.getPath() + "," + id);
/*  35:    */     } else {
/*  36: 41 */       resource.setPath(id.toString());
/*  37:    */     }
/*  38: 43 */     resource.setGrade(Integer.valueOf(resource.getPath().split(",").length));
/*  39: 44 */     super.update(resource);
/*  40: 45 */     return id;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public void update(Resource resource)
/*  44:    */   {
/*  45: 49 */     Resource parent = resource.getParent();
/*  46: 50 */     if (parent != null)
/*  47:    */     {
/*  48: 51 */       String parentPath = parent.getPath();
/*  49: 52 */       resource.setPath(parentPath + "," + resource.getId());
/*  50:    */     }
/*  51:    */     else
/*  52:    */     {
/*  53: 54 */       resource.setPath(resource.getId().toString());
/*  54:    */     }
/*  55: 56 */     resource.setGrade(Integer.valueOf(resource.getPath().split(",").length));
/*  56: 57 */     super.update(resource);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public List<Resource> getChildrenResourceList(Resource resource)
/*  60:    */   {
/*  61: 61 */     String hql = "from Resource resource where resource.parent = ? order by sort asc";
/*  62: 62 */     return getSession().createQuery(hql).setParameter(0, resource).list();
/*  63:    */   }
/*  64:    */   
/*  65:    */   public List<Resource> getRootResourceList()
/*  66:    */   {
/*  67: 66 */     String hql = "from Resource resource where resource.parent is null order by sort asc";
/*  68: 67 */     return getSession().createQuery(hql).list();
/*  69:    */   }
/*  70:    */   
/*  71:    */   public List<Resource> getLevel1MenuList(String username)
/*  72:    */   {
/*  73: 71 */     StringBuffer sb = new StringBuffer();
/*  74: 72 */     sb.append("select t1.* from ppi_resource t1,ppi_role t2,ppi_user t3,ppi_user_role t4,ppi_role_resource t5 ");
/*  75: 73 */     sb.append("where t4.user_id = t3.id ");
/*  76: 74 */     sb.append("and t4.role_id = t2.id ");
/*  77: 75 */     sb.append("and t2.id = t5.role_id ");
/*  78: 76 */     sb.append("and t5.resource_id = t1.id ");
/*  79: 77 */     sb.append("and t1.grade=1 ");
/*  80: 78 */     sb.append("and t3.user_name='" + username + "' ");
/*  81: 79 */     sb.append("order by t1.sort");
/*  82: 80 */     return getSession().createSQLQuery(sb.toString()).addEntity(Resource.class).list();
/*  83:    */   }
/*  84:    */   
/*  85:    */   public List<Resource> getLevel2MenuList(String username)
/*  86:    */   {
/*  87: 84 */     StringBuffer sb = new StringBuffer();
/*  88: 85 */     sb.append("select t1.* from ppi_resource t1,ppi_role t2,ppi_user t3,ppi_user_role t4,ppi_role_resource t5 ");
/*  89: 86 */     sb.append("where t4.user_id = t3.id ");
/*  90: 87 */     sb.append("and t4.role_id = t2.id ");
/*  91: 88 */     sb.append("and t2.id = t5.role_id ");
/*  92: 89 */     sb.append("and t5.resource_id = t1.id ");
/*  93: 90 */     sb.append("and t1.grade=2 ");
/*  94: 91 */     sb.append("and t3.user_name='" + username + "' ");
/*  95: 92 */     sb.append("order by t1.sort");
/*  96: 93 */     return getSession().createSQLQuery(sb.toString()).addEntity(Resource.class).list();
/*  97:    */   }
/*  98:    */   
/*  99:    */   public List<Resource> getLevel3MenuList(String username, Integer parentId)
/* 100:    */   {
/* 101: 97 */     StringBuffer sb = new StringBuffer();
/* 102: 98 */     sb.append("select t1.* from ppi_resource t1,ppi_role t2,ppi_user t3,ppi_user_role t4,ppi_role_resource t5 ");
/* 103: 99 */     sb.append("where t4.user_id = t3.id ");
/* 104:100 */     sb.append("and t4.role_id = t2.id ");
/* 105:101 */     sb.append("and t2.id = t5.role_id ");
/* 106:102 */     sb.append("and t5.resource_id = t1.id ");
/* 107:103 */     sb.append("and t1.grade=3 ");
/* 108:104 */     sb.append("and t3.user_name='" + username + "' ");
/* 109:105 */     sb.append("and t1.parent_id=" + parentId + " ");
/* 110:106 */     sb.append("order by t1.sort");
/* 111:107 */     return getSession().createSQLQuery(sb.toString()).addEntity(Resource.class).list();
/* 112:    */   }
/* 113:    */ }


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.dao.impl.ResourceDaoImpl
 * JD-Core Version:    0.7.0.1
 */