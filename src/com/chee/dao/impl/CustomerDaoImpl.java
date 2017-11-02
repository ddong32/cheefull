/*   1:    */ package com.chee.dao.impl;
/*   2:    */ 
/*   3:    */ import com.chee.common.Page;
/*   4:    */ import com.chee.dao.CustomerDao;
/*   5:    */ import com.chee.entity.Customer;
/*   6:    */ import java.util.HashMap;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.Map;
/*   9:    */ import java.util.Set;
/*  10:    */ import org.hibernate.Query;
/*  11:    */ import org.hibernate.Session;
/*  12:    */ import org.springframework.stereotype.Repository;
/*  13:    */ 
/*  14:    */ @Repository
/*  15:    */ public class CustomerDaoImpl
/*  16:    */   extends BaseDaoImpl<Customer, Integer>
/*  17:    */   implements CustomerDao
/*  18:    */ {
/*  19:    */   public Integer save(Customer customer)
/*  20:    */   {
/*  21: 18 */     Integer id = (Integer)super.save(customer);
/*  22: 19 */     Customer parent = customer.getParent();
/*  23: 20 */     if ((parent != null) && (parent.getPath() != null) && (!"".equals(parent.getPath())))
/*  24:    */     {
/*  25: 21 */       customer.setPath(parent.getPath() + id + ",");
/*  26: 22 */       customer.setPathName(parent.getPathName() + "|" + customer.getAreaName());
/*  27:    */     }
/*  28:    */     else
/*  29:    */     {
/*  30: 24 */       customer.setPath(id.toString() + ",");
/*  31: 25 */       customer.setPathName(customer.getAreaName());
/*  32:    */     }
/*  33: 27 */     customer.setGrade(Integer.valueOf(customer.getPath().split(",").length));
/*  34: 28 */     super.update(customer);
/*  35: 29 */     return id;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public void update(Customer customer)
/*  39:    */   {
/*  40: 33 */     Customer parent = customer.getParent();
/*  41: 35 */     if (parent == null)
/*  42:    */     {
/*  43: 36 */       customer.setPath(customer.getId().toString() + ",");
/*  44: 37 */       customer.setPathName(customer.getAreaName());
/*  45:    */     }
/*  46:    */     else
/*  47:    */     {
/*  48: 39 */       String parentPath = parent.getPath();
/*  49: 40 */       customer.setPath(parentPath + customer.getId() + ",");
/*  50: 41 */       customer.setPathName(parent.getPathName() + "|" + customer.getAreaName());
/*  51:    */     }
/*  52: 43 */     customer.setGrade(Integer.valueOf(customer.getPath().split(",").length));
/*  53: 44 */     super.update(customer);
/*  54:    */   }
/*  55:    */   
/*  56:    */   public Page<Customer> findPage(Page<Customer> page, Customer customer)
/*  57:    */   {
/*  58: 48 */     StringBuffer hqlSb = new StringBuffer();
/*  59: 49 */     hqlSb.append("select");
/*  60: 50 */     hqlSb.append(" this_.id,");
/*  61: 51 */     hqlSb.append(" this_.szdcs,");
/*  62: 52 */     hqlSb.append(" this_.dwmc,");
/*  63: 53 */     hqlSb.append(" this_.lxr,");
/*  64: 54 */     hqlSb.append(" this_.lxdh,");
/*  65: 55 */     hqlSb.append(" this_.lrsj,");
/*  66: 56 */     hqlSb.append(" this_.czh,");
/*  67: 57 */     hqlSb.append(" this_.dwdz,");
/*  68: 58 */     hqlSb.append(" this_.bz,");
/*  69: 59 */     hqlSb.append(" this_.qq");
/*  70: 60 */     hqlSb.append(" from Customer this_");
/*  71: 61 */     hqlSb.append(" where 1=1 ");
/*  72:    */     
/*  73: 63 */     Map<String, Object> map = new HashMap();
/*  74: 65 */     if (customer != null)
/*  75:    */     {
/*  76: 66 */       if ((customer.getSzdsf() != null) && (!"".equals(customer.getSzdsf())))
/*  77:    */       {
/*  78: 67 */         hqlSb.append("and this_.szdsf =:szdsf ");
/*  79: 68 */         map.put("szdsf", customer.getSzdsf());
/*  80:    */       }
/*  81: 70 */       if ((customer.getSzdcs() != null) && (!"".equals(customer.getSzdcs())))
/*  82:    */       {
/*  83: 71 */         hqlSb.append("and this_.szdcs =:szdcs ");
/*  84: 72 */         map.put("szdcs", customer.getSzdcs());
/*  85:    */       }
/*  86: 74 */       if ((customer.getSzdxf() != null) && (!"".equals(customer.getSzdxf())))
/*  87:    */       {
/*  88: 75 */         hqlSb.append("and this_.szdxf =:szdxf ");
/*  89: 76 */         map.put("szdxf", customer.getSzdxf());
/*  90:    */       }
/*  91: 78 */       if ((customer.getAreaName() != null) && (!"".equals(customer.getAreaName())))
/*  92:    */       {
/*  93: 79 */         hqlSb.append("and this_.areaName like:dwmc ");
/*  94: 80 */         map.put("dwmc", "%" + customer.getAreaName() + "%");
/*  95:    */       }
/*  96: 82 */       if ((customer.getLxr() != null) && (!"".equals(customer.getLxr())))
/*  97:    */       {
/*  98: 83 */         hqlSb.append("and this_.lxr like:lxr ");
/*  99: 84 */         map.put("lxr", "%" + customer.getLxr() + "%");
/* 100:    */       }
/* 101:    */     }
/* 102: 87 */     hqlSb.append("order by this_.lrsj desc");
/* 103: 88 */     return findPage(page, hqlSb.toString(), map);
/* 104:    */   }
/* 105:    */   
/* 106:    */   public List<Customer> getCustomerList(Integer grade)
/* 107:    */   {
/* 108: 92 */     String hql = "";
/* 109: 93 */     if ((grade == null) || (grade.intValue() == 9)) {
/* 110: 94 */       hql = "from Customer order by sort asc";
/* 111:    */     } else {
/* 112: 96 */       hql = "from Customer where grade <= " + grade + " order by sort asc";
/* 113:    */     }
/* 114: 98 */     return getSession().createQuery(hql).setCacheable(true).list();
/* 115:    */   }
/* 116:    */   
/* 117:    */   public List<Customer> getPathCustomerList(Set<Integer> pathIdSet)
/* 118:    */   {
/* 119:102 */     if ((pathIdSet != null) && (pathIdSet.size() > 0))
/* 120:    */     {
/* 121:103 */       StringBuffer hqlSb = new StringBuffer(" from Customer where id in (");
/* 122:104 */       int i = 0;
/* 123:105 */       for (Integer id : pathIdSet)
/* 124:    */       {
/* 125:106 */         if (i == 0) {
/* 126:107 */           hqlSb.append(id);
/* 127:    */         } else {
/* 128:109 */           hqlSb.append("," + id);
/* 129:    */         }
/* 130:111 */         i++;
/* 131:    */       }
/* 132:113 */       hqlSb.append(") order by sort asc, areaName asc ");
/* 133:114 */       return getSession().createQuery(hqlSb.toString()).setCacheable(true).list();
/* 134:    */     }
/* 135:116 */     return null;
/* 136:    */   }
/* 137:    */ }


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.dao.impl.CustomerDaoImpl
 * JD-Core Version:    0.7.0.1
 */