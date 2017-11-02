/*   1:    */ package com.chee.dao.impl;
/*   2:    */ 
/*   3:    */ import com.chee.common.Page;
/*   4:    */ import com.chee.dao.AccountFlowDao;
/*   5:    */ import com.chee.entity.Account;
/*   6:    */ import com.chee.entity.AccountFlow;
/*   7:    */ import com.chee.entity.Bank;
/*   8:    */ import com.chee.entity.Business;
/*   9:    */ import com.chee.entity.User;
/*  10:    */ import com.chee.util.DateUtil;
/*  11:    */ import com.chee.util.UserUtil;
/*  12:    */ import java.util.Date;
/*  13:    */ import java.util.HashMap;
/*  14:    */ import java.util.List;
/*  15:    */ import java.util.Map;
/*  16:    */ import javax.annotation.Resource;
/*  17:    */ import org.hibernate.Query;
/*  18:    */ import org.hibernate.SQLQuery;
/*  19:    */ import org.hibernate.Session;
/*  20:    */ import org.springframework.stereotype.Repository;
/*  21:    */ 
/*  22:    */ @Repository
/*  23:    */ public class AccountFlowDaoImpl
/*  24:    */   extends BaseDaoImpl<AccountFlow, Integer>
/*  25:    */   implements AccountFlowDao
/*  26:    */ {
/*  27:    */   @Resource
/*  28:    */   UserUtil userUtil;
/*  29:    */   
/*  30:    */   public User getLoginUser()
/*  31:    */   {
/*  32: 25 */     return this.userUtil.getLoginUser();
/*  33:    */   }
/*  34:    */   
/*  35:    */   public Page<AccountFlow> findWaitAuditDataPage(Page<AccountFlow> page, AccountFlow accountFlow, String type)
/*  36:    */   {
/*  37: 29 */     StringBuffer hqlSb = new StringBuffer();
/*  38: 30 */     hqlSb.append("select a.id,");
/*  39: 31 */     hqlSb.append(" a.stat,");
/*  40: 32 */     hqlSb.append(" a.lsh,");
/*  41: 33 */     hqlSb.append(" a.sksj,");
/*  42: 34 */     hqlSb.append(" a.user.name,");
/*  43: 35 */     hqlSb.append(" a.lrsj,");
/*  44: 36 */     hqlSb.append(" a.skje,");
/*  45: 37 */     hqlSb.append(" a.fkje,");
/*  46: 38 */     hqlSb.append(" b.djm,");
/*  47: 39 */     hqlSb.append(" a.shr,");
/*  48: 40 */     hqlSb.append(" a.shsj,");
/*  49: 41 */     hqlSb.append(" a.bz,");
/*  50: 42 */     hqlSb.append(" a.orderId,");
/*  51: 43 */     if (("payManageChecked".equals(type)) || (type == "payManageChecked") || 
/*  52: 44 */       ("payFinaneChecked".equals(type)) || (type == "payFinaneChecked") || 
/*  53: 45 */       ("takePayment".equals(type)) || (type == "takePayment"))
/*  54:    */     {
/*  55: 46 */       hqlSb.append(" a.account.id,");
/*  56: 47 */       hqlSb.append(" a.account.cooperator.dwmc,");
/*  57:    */     }
/*  58:    */     else
/*  59:    */     {
/*  60: 49 */       hqlSb.append(" a.account.id,");
/*  61:    */     }
/*  62: 51 */     hqlSb.append(" a.account.business.ddlx");
/*  63: 52 */     hqlSb.append(" from AccountFlow a left join a.bank b where 1=1 ");
/*  64:    */     
/*  65: 54 */     Map<String, Object> map = new HashMap();
/*  66: 55 */     hqlSb.append(doPropertyFilter(accountFlow, type));
/*  67: 56 */     return findPage(page, hqlSb.toString(), map);
/*  68:    */   }
/*  69:    */   
/*  70:    */   public List<Integer> findAuditIdList(AccountFlow accountFlow, String type)
/*  71:    */   {
/*  72: 61 */     StringBuffer hqlSb = new StringBuffer("select a.id from AccountFlow a left join a.bank b where 1=1 ");
/*  73: 62 */     hqlSb.append(doPropertyFilter(accountFlow, type));
/*  74: 63 */     return find(hqlSb.toString(), new Object[0]);
/*  75:    */   }
/*  76:    */   
/*  77:    */   private StringBuffer doPropertyFilter(AccountFlow accountFlow, String type)
/*  78:    */   {
/*  79: 67 */     StringBuffer hqlSb = new StringBuffer();
/*  80: 68 */     Map<String, Object> map = new HashMap();
/*  81: 69 */     if ((accountFlow != null) && (accountFlow.getOrderId() != null) && (!"".equals(accountFlow.getOrderId()))) {
/*  82: 70 */       hqlSb.append("and a.orderId like '" + accountFlow.getOrderId() + "%' ");
/*  83:    */     }
/*  84: 72 */     if ((accountFlow != null) && (accountFlow.getProUserIds() != null) && (accountFlow.getProUserIds().size() > 0))
/*  85:    */     {
/*  86: 73 */       String userId = "";
/*  87: 74 */       for (Integer id : accountFlow.getProUserIds()) {
/*  88: 75 */         userId = userId + id + ",";
/*  89:    */       }
/*  90: 77 */       userId = userId.substring(0, userId.lastIndexOf(","));
/*  91: 78 */       hqlSb.append("and a.user.id in (" + userId + ") ");
/*  92:    */     }
/*  93: 80 */     if (type != null)
/*  94:    */     {
/*  95: 81 */       if ((accountFlow != null) && (accountFlow.getBeginDate() != null) && (!"".equals(accountFlow.getBeginDate()))) {
/*  96: 82 */         hqlSb.append("and date_format(a.sksj,'%Y-%m-%d') >= '" + accountFlow.getBeginDate() + "' ");
/*  97:    */       }
/*  98: 84 */       if ((accountFlow != null) && (accountFlow.getEndDate() != null) && (!"".equals(accountFlow.getEndDate()))) {
/*  99: 85 */         hqlSb.append("and date_format(a.sksj,'%Y-%m-%d') <= '" + accountFlow.getEndDate() + "' ");
/* 100:    */       }
/* 101: 87 */       if ((accountFlow != null) && (accountFlow.getBank() != null) && (accountFlow.getBank().getId() != null) && (!"".equals(accountFlow.getBank().getId()))) {
/* 102: 88 */         hqlSb.append("and a.bank.id = " + accountFlow.getBank().getId());
/* 103:    */       }
/* 104: 90 */       if ((accountFlow != null) && (accountFlow.getBank() != null) && (accountFlow.getBank().getId() != null) && (!"".equals(accountFlow.getBank().getId()))) {
/* 105: 91 */         hqlSb.append("and a.bank.id = " + accountFlow.getBank().getId());
/* 106:    */       }
/* 107: 93 */       if (("takeFinaneChecked".equals(type)) || (type == "takeFinaneChecked"))
/* 108:    */       {
/* 109: 94 */         hqlSb.append("and a.ywlx = '1' ");
/* 110: 95 */         if ((accountFlow != null) && (accountFlow.getStat() != null))
/* 111:    */         {
/* 112: 96 */           if (!"".equals(accountFlow.getStat())) {
/* 113: 97 */             hqlSb.append("and a.stat = '" + accountFlow.getStat() + "' ");
/* 114:    */           }
/* 115:    */         }
/* 116:    */         else {
/* 117:100 */           hqlSb.append("and a.stat in ('1','3','9') ");
/* 118:    */         }
/* 119:    */       }
/* 120:102 */       else if (("payManageChecked".equals(type)) || (type == "payManageChecked"))
/* 121:    */       {
/* 122:103 */         hqlSb.append("and a.ywlx = '2' ");
/* 123:104 */         hqlSb.append("and a.sffs = '1' ");
/* 124:105 */         if (accountFlow.getStat() != null)
/* 125:    */         {
/* 126:106 */           if (!"".equals(accountFlow.getStat())) {
/* 127:107 */             hqlSb.append("and a.stat = '" + accountFlow.getStat() + "' ");
/* 128:    */           }
/* 129:    */         }
/* 130:    */         else {
/* 131:110 */           hqlSb.append("and a.stat = '1' ");
/* 132:    */         }
/* 133:112 */         if ((accountFlow.getAccount() != null) && (accountFlow.getAccount().getBusiness() != null) && (accountFlow.getAccount().getBusiness().getDdlx() != null) && 
/* 134:113 */           (!"".equals(accountFlow.getAccount().getBusiness().getDdlx()))) {
/* 135:114 */           hqlSb.append("and a.account.business.ddlx = '" + accountFlow.getAccount().getBusiness().getDdlx() + "' ");
/* 136:    */         }
/* 137:    */       }
/* 138:117 */       else if (("payManageRechecked".equals(type)) || (type == "payManageRechecked"))
/* 139:    */       {
/* 140:118 */         hqlSb.append("and a.ywlx = '2' ");
/* 141:119 */         hqlSb.append("and a.stat in ('2','9') ");
/* 142:    */       }
/* 143:120 */       else if (("payFinaneChecked".equals(type)) || (type == "payFinaneChecked"))
/* 144:    */       {
/* 145:121 */         hqlSb.append("and a.ywlx = '2' ");
/* 146:122 */         hqlSb.append("and a.sffs <> '3' ");
/* 147:123 */         if ((accountFlow != null) && (accountFlow.getStat() != null) && (!"".equals(accountFlow.getStat())))
/* 148:    */         {
/* 149:124 */           hqlSb.append("and a.stat = '" + accountFlow.getStat() + "' ");
/* 150:125 */           if ("9".equals(accountFlow.getStat())) {
/* 151:126 */             hqlSb.append("and a.spr is not null ");
/* 152:    */           }
/* 153:    */         }
/* 154:    */         else
/* 155:    */         {
/* 156:129 */           hqlSb.append("and a.stat in ('2','3') or (a.stat = '9' and a.spr is not null) ");
/* 157:    */         }
/* 158:    */       }
/* 159:131 */       else if (("payFinaneRechecked".equals(type)) || (type == "payFinaneRechecked"))
/* 160:    */       {
/* 161:132 */         hqlSb.append("and a.ywlx = '2' ");
/* 162:133 */         hqlSb.append("and a.stat in ('3','9') ");
/* 163:    */       }
/* 164:134 */       else if (("viewData".equals(type)) || (type == "viewData"))
/* 165:    */       {
/* 166:135 */         if (getLoginUser().getRoleValues().contains("ROLE_ONE")) {
/* 167:136 */           hqlSb.append("and a.user.id = '" + getLoginUser().getId() + "' ");
/* 168:    */         }
/* 169:    */       }
/* 170:138 */       else if (("takePayment".equals(type)) || (type == "takePayment"))
/* 171:    */       {
/* 172:139 */         hqlSb.append("and a.ywlx = '2' ");
/* 173:140 */         hqlSb.append("and a.sffs = '3' ");
/* 174:141 */         if ((accountFlow != null) && (accountFlow.getStat() != null) && (!"".equals(accountFlow.getStat())))
/* 175:    */         {
/* 176:142 */           hqlSb.append("and a.stat = '" + accountFlow.getStat() + "' ");
/* 177:143 */           if ("9".equals(accountFlow.getStat())) {
/* 178:144 */             hqlSb.append("and a.spr is not null ");
/* 179:    */           }
/* 180:    */         }
/* 181:    */         else
/* 182:    */         {
/* 183:147 */           hqlSb.append("and a.stat in ('2','3') or (a.stat = '9' and a.spr is not null) ");
/* 184:    */         }
/* 185:    */       }
/* 186:    */     }
/* 187:152 */     hqlSb.append("order by a.stat, a.orderId");
/* 188:153 */     return hqlSb;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public AccountFlow doSearchServices(Integer id)
/* 192:    */   {
/* 193:157 */     AccountFlow accountFlow = (AccountFlow)get(id);
/* 194:158 */     return accountFlow;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public AccountFlow checkRepeatRefer(String accountId)
/* 198:    */   {
/* 199:162 */     StringBuffer hqlSb = new StringBuffer("select a from AccountFlow a where 1=1 ");
/* 200:163 */     if ((accountId != null) && (!"".equals(accountId))) {
/* 201:164 */       hqlSb.append("and a.accountId = '" + accountId + "'");
/* 202:    */     }
/* 203:166 */     return (AccountFlow)findUnique(hqlSb.toString(), new Object[0]);
/* 204:    */   }
/* 205:    */   
/* 206:    */   public int deleteAccountFlow(Integer accountIds)
/* 207:    */   {
/* 208:170 */     String hqlSb = "delete from chee_account_flow where account_id = '" + accountIds + "'";
/* 209:171 */     return getSession().createSQLQuery(hqlSb).executeUpdate();
/* 210:    */   }
/* 211:    */   
/* 212:    */   public long noGenerate()
/* 213:    */   {
/* 214:175 */     String hql = "select count(*) from AccountFlow where date_format(lrsj,'%Y-%m-%d') ='" + DateUtil.formatDate(new Date(), "yyyy-MM-dd") + "'";
/* 215:176 */     return ((Long)getSession().createQuery(hql).uniqueResult()).longValue();
/* 216:    */   }
/* 217:    */ }


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.dao.impl.AccountFlowDaoImpl
 * JD-Core Version:    0.7.0.1
 */