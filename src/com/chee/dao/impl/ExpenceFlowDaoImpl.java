/*  1:   */ package com.chee.dao.impl;
/*  2:   */ 
/*  3:   */ import com.chee.dao.ExpenceFlowDao;
/*  4:   */ import com.chee.entity.Expence;
/*  5:   */ import com.chee.entity.ExpenceFlow;
/*  6:   */ import java.util.HashMap;
/*  7:   */ import java.util.List;
/*  8:   */ import java.util.Map;
/*  9:   */ import org.springframework.stereotype.Repository;
/* 10:   */ 
/* 11:   */ @Repository
/* 12:   */ public class ExpenceFlowDaoImpl
/* 13:   */   extends BaseDaoImpl<ExpenceFlow, Integer>
/* 14:   */   implements ExpenceFlowDao
/* 15:   */ {
/* 16:   */   public List<ExpenceFlow> findExpenceFlowList(Expence expence)
/* 17:   */   {
/* 18:16 */     StringBuffer hqlSb = new StringBuffer();
/* 19:17 */     hqlSb.append("from ExpenceFlow a where 1=1 ");
/* 20:   */     
/* 21:19 */     Map<String, Object> map = new HashMap();
/* 22:20 */     if (expence.getId() != null) {
/* 23:21 */       hqlSb.append("and a.expence.id = " + expence.getId());
/* 24:   */     }
/* 25:23 */     hqlSb.append(" order by a.gxsj");
/* 26:24 */     return find(hqlSb.toString(), map);
/* 27:   */   }
/* 28:   */ }


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.dao.impl.ExpenceFlowDaoImpl
 * JD-Core Version:    0.7.0.1
 */