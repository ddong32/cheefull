/*  1:   */ package com.chee.service.impl;
/*  2:   */ 
/*  3:   */ import com.chee.common.Page;
/*  4:   */ import com.chee.dao.AttachmentDao;
/*  5:   */ import com.chee.entity.Attachment;
/*  6:   */ import com.chee.service.AttachmentService;
/*  7:   */ import javax.annotation.Resource;
/*  8:   */ import org.springframework.stereotype.Service;
/*  9:   */ 
/* 10:   */ @Service
/* 11:   */ public class AttachmentServiceImpl
/* 12:   */   extends BaseServiceImpl<Attachment, Integer>
/* 13:   */   implements AttachmentService
/* 14:   */ {
/* 15:   */   @Resource
/* 16:   */   AttachmentDao attachmentDao;
/* 17:   */   
/* 18:   */   @Resource
/* 19:   */   public void setBaseDao(AttachmentDao attachmentDao)
/* 20:   */   {
/* 21:19 */     super.setBaseDao(attachmentDao);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public Page<Attachment> findAttachmentPage(Page<Attachment> page, Attachment attachment)
/* 25:   */   {
/* 26:23 */     return this.attachmentDao.findAttachmentPage(page, attachment);
/* 27:   */   }
/* 28:   */ }


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.service.impl.AttachmentServiceImpl
 * JD-Core Version:    0.7.0.1
 */