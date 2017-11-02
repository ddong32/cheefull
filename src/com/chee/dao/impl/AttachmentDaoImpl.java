/*  1:   */ package com.chee.dao.impl;
/*  2:   */ 
/*  3:   */ import com.chee.common.Page;
/*  4:   */ import com.chee.dao.AttachmentDao;
/*  5:   */ import com.chee.entity.Attachment;
/*  6:   */ import java.util.HashMap;
/*  7:   */ import java.util.Map;
/*  8:   */ import org.springframework.stereotype.Repository;
/*  9:   */ 
/* 10:   */ @Repository
/* 11:   */ public class AttachmentDaoImpl
/* 12:   */   extends BaseDaoImpl<Attachment, Integer>
/* 13:   */   implements AttachmentDao
/* 14:   */ {
/* 15:   */   public Page<Attachment> findAttachmentPage(Page<Attachment> page, Attachment attachment)
/* 16:   */   {
/* 17:16 */     StringBuilder hqlSb = new StringBuilder();
/* 18:17 */     hqlSb.append("select");
/* 19:18 */     hqlSb.append(" a.id,");
/* 20:19 */     hqlSb.append(" a.title,");
/* 21:20 */     hqlSb.append(" a.user_id,");
/* 22:21 */     hqlSb.append(" a.content_id,");
/* 23:22 */     hqlSb.append(" a.path,");
/* 24:23 */     hqlSb.append(" a.mime_type,");
/* 25:24 */     hqlSb.append(" a.suffix,");
/* 26:25 */     hqlSb.append(" a.mime_type,");
/* 27:26 */     hqlSb.append(" a.type,");
/* 28:27 */     hqlSb.append(" a.flag,");
/* 29:28 */     hqlSb.append(" a.lat,");
/* 30:29 */     hqlSb.append(" a.lng,");
/* 31:30 */     hqlSb.append(" a.order_number,");
/* 32:31 */     hqlSb.append(" a.created");
/* 33:32 */     hqlSb.append(" from Attachment a where 1=1 ");
/* 34:   */     
/* 35:34 */     Map<String, Object> map = new HashMap();
/* 36:35 */     if (attachment != null)
/* 37:   */     {
/* 38:36 */       if ((attachment.getBeginDate() != null) && (!"".equals(attachment.getBeginDate()))) {
/* 39:37 */         hqlSb.append("and date_format(a.created,'%Y-%m-%d') >= '" + attachment.getBeginDate() + "' ");
/* 40:   */       }
/* 41:39 */       if ((attachment.getEndDate() != null) && (!"".equals(attachment.getEndDate()))) {
/* 42:40 */         hqlSb.append("and date_format(a.created,'%Y-%m-%d') <= '" + attachment.getEndDate() + "' ");
/* 43:   */       }
/* 44:42 */       if ((attachment.getUser_id() != null) && (!"".equals(attachment.getUser_id())))
/* 45:   */       {
/* 46:43 */         hqlSb.append("and a.user_id =:user_id ");
/* 47:44 */         map.put("user_id", attachment.getUser_id());
/* 48:   */       }
/* 49:46 */       if ((attachment.getContent_id() != null) && (!"".equals(attachment.getContent_id())))
/* 50:   */       {
/* 51:47 */         hqlSb.append("and a.content_id =:content_id ");
/* 52:48 */         map.put("content_id", attachment.getContent_id());
/* 53:   */       }
/* 54:50 */       if ((attachment.getType() != null) && (!"".equals(attachment.getType())))
/* 55:   */       {
/* 56:51 */         hqlSb.append("and a.type =:type ");
/* 57:52 */         map.put("type", attachment.getType());
/* 58:   */       }
/* 59:54 */       if ((attachment.getFlag() != null) && (!"".equals(attachment.getFlag())))
/* 60:   */       {
/* 61:55 */         hqlSb.append("and a.flag =:flag ");
/* 62:56 */         map.put("flag", attachment.getFlag());
/* 63:   */       }
/* 64:58 */       if ((attachment.getTitle() != null) && (!"".equals(attachment.getTitle())))
/* 65:   */       {
/* 66:59 */         hqlSb.append("and a.title like:title ");
/* 67:60 */         map.put("title", "%" + attachment.getTitle() + "%");
/* 68:   */       }
/* 69:62 */       if ((attachment.getMime() != null) && (!"".equals(attachment.getMime())))
/* 70:   */       {
/* 71:63 */         hqlSb.append("and a.mime_type like:mime_type ");
/* 72:64 */         map.put("mime_type", "%" + attachment.getMime() + "%");
/* 73:   */       }
/* 74:   */     }
/* 75:67 */     hqlSb.append("order by a.created DESC");
/* 76:68 */     return findPage(page, hqlSb.toString(), map);
/* 77:   */   }
/* 78:   */ }


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.dao.impl.AttachmentDaoImpl
 * JD-Core Version:    0.7.0.1
 */