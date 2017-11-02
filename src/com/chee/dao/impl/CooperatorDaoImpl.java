/*  1:   */ package com.chee.dao.impl;
/*  2:   */ 
/*  3:   */ import com.chee.common.Page;
/*  4:   */ import com.chee.dao.CooperatorDao;
/*  5:   */ import com.chee.entity.Cooperator;
/*  6:   */ import java.util.HashMap;
/*  7:   */ import java.util.Map;
/*  8:   */ import org.springframework.stereotype.Repository;
/*  9:   */ 
/* 10:   */ @Repository
/* 11:   */ public class CooperatorDaoImpl
/* 12:   */   extends BaseDaoImpl<Cooperator, Integer>
/* 13:   */   implements CooperatorDao
/* 14:   */ {
/* 15:   */   public Page<Cooperator> findCooperatorDialogPage(Page<Cooperator> page, Cooperator cooperator)
/* 16:   */   {
/* 17:16 */     StringBuffer hqlSb = new StringBuffer();
/* 18:17 */     hqlSb.append("select");
/* 19:18 */     hqlSb.append(" this_.id,");
/* 20:19 */     hqlSb.append(" this_.type,");
/* 21:20 */     hqlSb.append(" this_.dwmc,");
/* 22:21 */     hqlSb.append(" this_.lxr,");
/* 23:22 */     hqlSb.append(" this_.lxdh");
/* 24:23 */     hqlSb.append(" from Cooperator this_");
/* 25:24 */     hqlSb.append(" where 1=1 ");
/* 26:   */     
/* 27:26 */     Map<String, Object> map = new HashMap();
/* 28:28 */     if (cooperator != null)
/* 29:   */     {
/* 30:29 */       if ((cooperator.getDwmc() != null) && (!"".equals(cooperator.getDwmc())))
/* 31:   */       {
/* 32:30 */         hqlSb.append("and this_.dwmc like:dwmc ");
/* 33:31 */         map.put("dwmc", "%" + cooperator.getDwmc() + "%");
/* 34:   */       }
/* 35:33 */       if ((cooperator.getLxr() != null) && (!"".equals(cooperator.getLxr())))
/* 36:   */       {
/* 37:34 */         hqlSb.append("and this_.lxr like:lxr ");
/* 38:35 */         map.put("lxr", "%" + cooperator.getLxr() + "%");
/* 39:   */       }
/* 40:   */     }
/* 41:38 */     hqlSb.append("order by this_.sort asc");
/* 42:39 */     page.setPageSize(500);
/* 43:40 */     return findPage(page, hqlSb.toString(), map);
/* 44:   */   }
/* 45:   */ }


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.dao.impl.CooperatorDaoImpl
 * JD-Core Version:    0.7.0.1
 */