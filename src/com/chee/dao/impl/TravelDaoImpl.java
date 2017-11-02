/*  1:   */ package com.chee.dao.impl;
/*  2:   */ 
/*  3:   */ import com.chee.common.Page;
/*  4:   */ import com.chee.dao.TravelDao;
/*  5:   */ import com.chee.entity.Travel;
/*  6:   */ import java.util.HashMap;
/*  7:   */ import java.util.Map;
/*  8:   */ import org.springframework.stereotype.Repository;
/*  9:   */ 
/* 10:   */ @Repository
/* 11:   */ public class TravelDaoImpl
/* 12:   */   extends BaseDaoImpl<Travel, Integer>
/* 13:   */   implements TravelDao
/* 14:   */ {
/* 15:   */   public Page<Travel> findTravelDialogPage(Page<Travel> page, Travel travel)
/* 16:   */   {
/* 17:16 */     StringBuffer hqlSb = new StringBuffer();
/* 18:17 */     hqlSb.append("select");
/* 19:18 */     hqlSb.append(" a.line,");
/* 20:19 */     hqlSb.append(" a.lrr,");
/* 21:20 */     hqlSb.append(" a.lrsj");
/* 22:21 */     hqlSb.append(" from Travel a");
/* 23:22 */     hqlSb.append(" where 1=1 ");
/* 24:   */     
/* 25:24 */     Map<String, Object> map = new HashMap();
/* 26:26 */     if ((travel != null) && 
/* 27:27 */       (travel.getLine() != null) && (!"".equals(travel.getLine())))
/* 28:   */     {
/* 29:28 */       hqlSb.append("and a.line like:line ");
/* 30:29 */       map.put("line", "%" + travel.getLine() + "%");
/* 31:   */     }
/* 32:32 */     hqlSb.append("order by a.lrsj desc");
/* 33:33 */     return findPage(page, hqlSb.toString(), map);
/* 34:   */   }
/* 35:   */ }


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.dao.impl.TravelDaoImpl
 * JD-Core Version:    0.7.0.1
 */