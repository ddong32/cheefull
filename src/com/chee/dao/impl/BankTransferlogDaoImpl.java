/*  1:   */ package com.chee.dao.impl;
/*  2:   */ 
/*  3:   */ import com.chee.common.Page;
/*  4:   */ import com.chee.dao.BankTransferlogDao;
/*  5:   */ import com.chee.entity.BankTransferlog;
/*  6:   */ import java.util.HashMap;
/*  7:   */ import java.util.Map;
/*  8:   */ import org.springframework.stereotype.Repository;
/*  9:   */ 
/* 10:   */ @Repository
/* 11:   */ public class BankTransferlogDaoImpl
/* 12:   */   extends BaseDaoImpl<BankTransferlog, Integer>
/* 13:   */   implements BankTransferlogDao
/* 14:   */ {
/* 15:   */   public Page<BankTransferlog> findBankTransferlogPage(Page<BankTransferlog> page, BankTransferlog bankTransferlog)
/* 16:   */   {
/* 17:15 */     StringBuffer hqlSb = new StringBuffer();
/* 18:16 */     hqlSb.append("select");
/* 19:17 */     hqlSb.append(" this_.czsj,");
/* 20:18 */     hqlSb.append(" this_.foid,");
/* 21:19 */     hqlSb.append(" this_.zcje,");
/* 22:20 */     hqlSb.append(" this_.fo_cush,");
/* 23:21 */     hqlSb.append(" this_.toid,");
/* 24:22 */     hqlSb.append(" this_.to_cush,");
/* 25:23 */     hqlSb.append(" this_.user.name");
/* 26:24 */     hqlSb.append(" from BankTransferlog this_ where 1=1 ");
/* 27:   */     
/* 28:26 */     Map<String, Object> map = new HashMap();
/* 29:28 */     if ((bankTransferlog.getBeginDate() != null) && (!"".equals(bankTransferlog.getBeginDate()))) {
/* 30:29 */       hqlSb.append("and date_format(this_.czsj,'%Y-%m-%d')>='" + bankTransferlog.getBeginDate() + "' ");
/* 31:   */     }
/* 32:31 */     if ((bankTransferlog.getEndDate() != null) && (!"".equals(bankTransferlog.getEndDate()))) {
/* 33:32 */       hqlSb.append("and date_format(this_.czsj,'%Y-%m-%d') <= '" + bankTransferlog.getEndDate() + "' ");
/* 34:   */     }
/* 35:35 */     if ((bankTransferlog.getFoid() != null) && (!"".equals(bankTransferlog.getFoid())))
/* 36:   */     {
/* 37:36 */       hqlSb.append("and this_.foid =:foid ");
/* 38:37 */       map.put("foid", bankTransferlog.getFoid());
/* 39:   */     }
/* 40:39 */     if ((bankTransferlog.getToid() != null) && (!"".equals(bankTransferlog.getToid())))
/* 41:   */     {
/* 42:40 */       hqlSb.append("and this_.toid =:toid ");
/* 43:41 */       map.put("toid", bankTransferlog.getToid());
/* 44:   */     }
/* 45:43 */     if (!"".equals(Double.valueOf(bankTransferlog.getZcje())))
/* 46:   */     {
/* 47:44 */       hqlSb.append("and this_.zcje >=:zcje ");
/* 48:45 */       map.put("zcje", Double.valueOf(bankTransferlog.getZcje()));
/* 49:   */     }
/* 50:48 */     hqlSb.append("order by this_.czsj ");
/* 51:   */     
/* 52:50 */     return findPage(page, hqlSb.toString(), map);
/* 53:   */   }
/* 54:   */ }


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.dao.impl.BankTransferlogDaoImpl
 * JD-Core Version:    0.7.0.1
 */