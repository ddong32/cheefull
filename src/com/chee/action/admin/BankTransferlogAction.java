/*  1:   */ package com.chee.action.admin;
/*  2:   */ 
/*  3:   */ import com.chee.entity.BankTransferlog;
/*  4:   */ import com.chee.service.BankTransferlogService;
/*  5:   */ import com.chee.util.DateUtil;
/*  6:   */ import com.chee.util.StringUtils;
/*  7:   */ import java.util.Date;
/*  8:   */ import javax.annotation.Resource;
/*  9:   */ import org.apache.struts2.convention.annotation.ParentPackage;
/* 10:   */ import org.apache.struts2.convention.annotation.Results;
/* 11:   */ import org.hibernate.criterion.DetachedCriteria;
/* 12:   */ 
/* 13:   */ @ParentPackage("admin")
/* 14:   */ @Results({@org.apache.struts2.convention.annotation.Result(name="index", location="bank_transferlog!list.action", type="redirect")})
/* 15:   */ public class BankTransferlogAction
/* 16:   */   extends BaseAction<BankTransferlog, Integer>
/* 17:   */ {
/* 18:   */   private static final long serialVersionUID = 4984599292597512018L;
/* 19:   */   @Resource
/* 20:   */   private BankTransferlogService bankTransferlogService;
/* 21:   */   private BankTransferlog bankTransferlog;
/* 22:   */   
/* 23:   */   public String list()
/* 24:   */   {
/* 25:42 */     DetachedCriteria detachedCriteria = DetachedCriteria.forClass(BankTransferlog.class);
/* 26:43 */     if (this.bankTransferlog == null)
/* 27:   */     {
/* 28:44 */       this.bankTransferlog = new BankTransferlog();
/* 29:45 */       if (StringUtils.isEmpty(this.bankTransferlog.getBeginDate())) {
/* 30:46 */         this.bankTransferlog.setBeginDate(DateUtil.afterNDay(new Date(), -100, "yyyy-MM-dd"));
/* 31:   */       }
/* 32:48 */       if (StringUtils.isEmpty(this.bankTransferlog.getEndDate())) {
/* 33:49 */         this.bankTransferlog.setEndDate(DateUtil.afterNDay(new Date(), 0, "yyyy-MM-dd"));
/* 34:   */       }
/* 35:   */     }
/* 36:53 */     this.page = this.bankTransferlogService.findBankTransferlogPage(this.page, this.bankTransferlog);
/* 37:   */     
/* 38:55 */     return "list";
/* 39:   */   }
/* 40:   */   
/* 41:   */   public BankTransferlog getBankTransferlog()
/* 42:   */   {
/* 43:59 */     return this.bankTransferlog;
/* 44:   */   }
/* 45:   */   
/* 46:   */   public void setBankTransferlog(BankTransferlog bankTransferlog)
/* 47:   */   {
/* 48:63 */     this.bankTransferlog = bankTransferlog;
/* 49:   */   }
/* 50:   */ }


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.action.admin.BankTransferlogAction
 * JD-Core Version:    0.7.0.1
 */