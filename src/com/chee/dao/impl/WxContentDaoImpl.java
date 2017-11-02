/*  1:   */ package com.chee.dao.impl;
/*  2:   */ 
/*  3:   */ import com.chee.common.Page;
/*  4:   */ import com.chee.dao.WxContentDao;
/*  5:   */ import com.chee.entity.WxContent;
/*  6:   */ import java.util.HashMap;
/*  7:   */ import java.util.Map;
/*  8:   */ import org.springframework.stereotype.Repository;
/*  9:   */ 
/* 10:   */ @Repository
/* 11:   */ public class WxContentDaoImpl
/* 12:   */   extends BaseDaoImpl<WxContent, Integer>
/* 13:   */   implements WxContentDao
/* 14:   */ {
/* 15:   */   public Page<WxContent> findContentPage(Page<WxContent> page, WxContent wxContent)
/* 16:   */   {
/* 17:15 */     StringBuilder hqlSb = new StringBuilder();
/* 18:   */     
/* 19:   */ 
/* 20:   */ 
/* 21:   */ 
/* 22:   */ 
/* 23:   */ 
/* 24:   */ 
/* 25:   */ 
/* 26:   */ 
/* 27:   */ 
/* 28:   */ 
/* 29:   */ 
/* 30:   */ 
/* 31:   */ 
/* 32:   */ 
/* 33:   */ 
/* 34:32 */     hqlSb.append("SELECT");
/* 35:33 */     hqlSb.append("\tc.id,");
/* 36:34 */     hqlSb.append("\tc.title,");
/* 37:35 */     hqlSb.append("\tu.name,");
/* 38:36 */     hqlSb.append("\tc.created");
/* 39:37 */     hqlSb.append(" from WxContent c left join c.user u where 1=1 ");
/* 40:   */     
/* 41:39 */     Map<String, Object> map = new HashMap();
/* 42:40 */     if (wxContent != null)
/* 43:   */     {
/* 44:41 */       if ((wxContent.getBeginDate() != null) && (!"".equals(wxContent.getBeginDate()))) {
/* 45:42 */         hqlSb.append("and date_format(a.created,'%Y-%m-%d') >= '" + wxContent.getBeginDate() + "' ");
/* 46:   */       }
/* 47:44 */       if ((wxContent.getEndDate() != null) && (!"".equals(wxContent.getEndDate()))) {
/* 48:45 */         hqlSb.append("and date_format(a.created,'%Y-%m-%d') <= '" + wxContent.getEndDate() + "' ");
/* 49:   */       }
/* 50:47 */       if ((wxContent.getStatus() != null) && (!"".equals(wxContent.getStatus())))
/* 51:   */       {
/* 52:48 */         hqlSb.append("and c.status =:status ");
/* 53:49 */         map.put("status", wxContent.getStatus());
/* 54:   */       }
/* 55:51 */       if ((wxContent.getTitle() != null) && (!"".equals(wxContent.getTitle())))
/* 56:   */       {
/* 57:52 */         hqlSb.append("and c.title like:title ");
/* 58:53 */         map.put("title", "%" + wxContent.getTitle() + "%");
/* 59:   */       }
/* 60:   */     }
/* 61:57 */     hqlSb.append("order by c.created DESC");
/* 62:58 */     return findPage(page, hqlSb.toString(), map);
/* 63:   */   }
/* 64:   */ }


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.dao.impl.WxContentDaoImpl
 * JD-Core Version:    0.7.0.1
 */