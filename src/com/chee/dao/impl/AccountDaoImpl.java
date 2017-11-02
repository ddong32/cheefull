/*   1:    */ package com.chee.dao.impl;
/*   2:    */ 
/*   3:    */ import com.chee.common.Page;
/*   4:    */ import com.chee.dao.AccountDao;
/*   5:    */ import com.chee.entity.Account;
/*   6:    */ import com.chee.entity.Business;
/*   7:    */ import com.chee.entity.BusinessCustomer;
/*   8:    */ import com.chee.entity.Cooperator;
/*   9:    */ import com.chee.entity.User;
/*  10:    */ import com.chee.util.UserUtil;
/*  11:    */ import java.util.HashMap;
/*  12:    */ import java.util.List;
/*  13:    */ import java.util.Map;
/*  14:    */ import javax.annotation.Resource;
/*  15:    */ import org.hibernate.Query;
/*  16:    */ import org.hibernate.SQLQuery;
/*  17:    */ import org.hibernate.Session;
/*  18:    */ import org.springframework.stereotype.Repository;
/*  19:    */ 
/*  20:    */ @Repository
/*  21:    */ public class AccountDaoImpl
/*  22:    */   extends BaseDaoImpl<Account, Integer>
/*  23:    */   implements AccountDao
/*  24:    */ {
/*  25:    */   @Resource
/*  26:    */   UserUtil userUtil;
/*  27:    */   
/*  28:    */   public User getLoginUser()
/*  29:    */   {
/*  30: 23 */     return this.userUtil.getLoginUser();
/*  31:    */   }
/*  32:    */   
/*  33:    */   public List<Account> findOrderID(String order_id)
/*  34:    */   {
/*  35: 27 */     StringBuffer hqlSb = new StringBuffer();
/*  36: 28 */     hqlSb.append("FROM Account WHERE 1=1");
/*  37: 29 */     hqlSb.append(" and orderId = '" + order_id + "' ");
/*  38: 30 */     hqlSb.append("order by sflx, businessCustomer, lrsj");
/*  39: 31 */     return getSession().createQuery(hqlSb.toString()).list();
/*  40:    */   }
/*  41:    */   
/*  42:    */   public List<Account> findAccountList(Integer id, String order_id)
/*  43:    */   {
/*  44: 35 */     StringBuffer hqlSb = new StringBuffer();
/*  45: 36 */     hqlSb.append("FROM Account WHERE 1=1");
/*  46: 37 */     hqlSb.append(" and businessCustomer.id =" + id + " and orderId = '" + order_id + "' ");
/*  47: 38 */     return getSession().createQuery(hqlSb.toString()).list();
/*  48:    */   }
/*  49:    */   
/*  50:    */   public Page<Account> findBusinessPage(Page<Account> page, Account account)
/*  51:    */   {
/*  52: 42 */     StringBuffer hqlSb = new StringBuffer();
/*  53: 43 */     hqlSb.append("select a.id,");
/*  54: 44 */     hqlSb.append(" a.sflx,");
/*  55: 45 */     hqlSb.append(" a.orderId,");
/*  56: 46 */     hqlSb.append(" a.sffs,");
/*  57: 47 */     hqlSb.append(" b.customerDwmc,");
/*  58: 48 */     hqlSb.append(" a.skje,");
/*  59: 49 */     hqlSb.append(" a.fkje,");
/*  60: 50 */     hqlSb.append(" a.lrsj,");
/*  61: 51 */     hqlSb.append(" a.lrr,");
/*  62: 52 */     hqlSb.append(" a.shsj,");
/*  63: 53 */     hqlSb.append(" a.shr,");
/*  64: 54 */     hqlSb.append(" a.stat");
/*  65: 55 */     hqlSb.append(" from Account a left join a.businessCustomer b where 1=1 ");
/*  66:    */     
/*  67: 57 */     Map<String, Object> map = new HashMap();
/*  68: 58 */     if ((account.getBeginDate() != null) && (!"".equals(account.getBeginDate()))) {
/*  69: 59 */       hqlSb.append("and date_format(a.lrsj,'%Y-%m-%d') >= '" + account.getBeginDate() + "'");
/*  70:    */     }
/*  71: 61 */     if ((account.getEndDate() != null) && (!"".equals(account.getEndDate()))) {
/*  72: 62 */       hqlSb.append("and date_format(a.lrsj,'%Y-%m-%d') <= '" + account.getEndDate() + "'");
/*  73:    */     }
/*  74: 64 */     if ((account.getStat() != null) && (!"".equals(account.getStat())))
/*  75:    */     {
/*  76: 65 */       hqlSb.append("and a.stat =:stat ");
/*  77: 66 */       map.put("stat", account.getStat());
/*  78:    */     }
/*  79: 68 */     hqlSb.append("order by a.lrsj desc ");
/*  80:    */     
/*  81: 70 */     return findPage(page, hqlSb.toString(), map);
/*  82:    */   }
/*  83:    */   
/*  84:    */   public List<Account> findAccountIds(Integer[] account_ids)
/*  85:    */   {
/*  86: 74 */     StringBuffer hqlSb = new StringBuffer();
/*  87: 75 */     hqlSb.append("FROM Account WHERE 1=1 and id in (:account_ids)");
/*  88: 76 */     Map<String, Object> map = new HashMap();
/*  89: 77 */     map.put("account_ids", account_ids);
/*  90: 78 */     return find(hqlSb.toString(), map);
/*  91:    */   }
/*  92:    */   
/*  93:    */   public List<Account> findAccountCountJe(String order_id)
/*  94:    */   {
/*  95: 83 */     StringBuffer hqlSb = new StringBuffer();
/*  96: 84 */     hqlSb.append("FROM Account WHERE 1=1");
/*  97: 85 */     hqlSb.append(" and orderId = '" + order_id + "'");
/*  98: 86 */     hqlSb.append(" and stat = '3' ");
/*  99: 87 */     return getSession().createQuery(hqlSb.toString()).list();
/* 100:    */   }
/* 101:    */   
/* 102:    */   public List<Account> findAccountCountYfk(String order_id)
/* 103:    */   {
/* 104: 92 */     StringBuffer hqlSb = new StringBuffer();
/* 105: 93 */     hqlSb.append("FROM Account WHERE 1=1");
/* 106: 94 */     hqlSb.append(" and orderId = '" + order_id + "'");
/* 107: 95 */     return getSession().createQuery(hqlSb.toString()).list();
/* 108:    */   }
/* 109:    */   
/* 110:    */   public List<Account> findAccountCountYjs(String order_id, Integer b_c_id)
/* 111:    */   {
/* 112:100 */     StringBuffer hqlSb = new StringBuffer();
/* 113:101 */     hqlSb.append("FROM Account WHERE 1=1");
/* 114:102 */     hqlSb.append(" and stat = 3");
/* 115:103 */     hqlSb.append(" and orderId = '" + order_id + "'");
/* 116:104 */     hqlSb.append(" and businessCustomer.id = '" + b_c_id + "'");
/* 117:105 */     return getSession().createQuery(hqlSb.toString()).list();
/* 118:    */   }
/* 119:    */   
/* 120:    */   public Page<Account> findOnTrustPage(Page<Account> page, Account account)
/* 121:    */   {
/* 122:110 */     StringBuffer hqlSb = new StringBuffer();
/* 123:111 */     hqlSb.append("select");
/* 124:112 */     hqlSb.append(" a.id,");
/* 125:113 */     hqlSb.append(" a.sflx,");
/* 126:114 */     hqlSb.append(" a.orderId,");
/* 127:115 */     hqlSb.append(" c.customerDwmc,");
/* 128:116 */     hqlSb.append(" b.travelLine,");
/* 129:117 */     hqlSb.append(" b.cfsj,");
/* 130:118 */     hqlSb.append(" a.lrr,");
/* 131:119 */     hqlSb.append(" a.skje,");
/* 132:120 */     hqlSb.append(" a.fkje,");
/* 133:121 */     hqlSb.append(" a.sffs,");
/* 134:122 */     hqlSb.append(" a.stat,");
/* 135:123 */     hqlSb.append(" b.id,");
/* 136:124 */     hqlSb.append(" c.parentId,");
/* 137:125 */     hqlSb.append(" c.ygs,");
/* 138:126 */     hqlSb.append(" c.yjs,");
/* 139:127 */     hqlSb.append(" c.ygs - c.yjs");
/* 140:128 */     hqlSb.append(" from Account a left join a.business b left join a.businessCustomer c ");
/* 141:129 */     hqlSb.append(" where 1=1 ");
/* 142:    */     
/* 143:131 */     Map<String, Object> map = new HashMap();
/* 144:133 */     if ((account.getBeginDate() != null) && (!"".equals(account.getBeginDate()))) {
/* 145:134 */       hqlSb.append("and date_format(b.cfsj,'%Y-%m-%d')>='" + account.getBeginDate() + "' ");
/* 146:    */     }
/* 147:136 */     if ((account.getEndDate() != null) && (!"".equals(account.getEndDate()))) {
/* 148:137 */       hqlSb.append("and date_format(b.cfsj,'%Y-%m-%d') <= '" + account.getEndDate() + "' ");
/* 149:    */     }
/* 150:139 */     if ((account.getStat() != null) && (!"".equals(account.getStat())))
/* 151:    */     {
/* 152:140 */       hqlSb.append("and a.stat=:stat ");
/* 153:141 */       map.put("stat", account.getStat());
/* 154:    */     }
/* 155:143 */     if ((account.getBusinessCustomer() != null) && 
/* 156:144 */       (account.getBusinessCustomer().getCustomerDwmc() != null) && 
/* 157:145 */       (!"".equals(account.getBusinessCustomer().getCustomerDwmc())))
/* 158:    */     {
/* 159:146 */       hqlSb.append("and c.customerDwmc like:customerDwmc ");
/* 160:147 */       map.put("customerDwmc", "%" + account.getBusinessCustomer().getCustomerDwmc() + "%");
/* 161:    */     }
/* 162:149 */     if ((account.getBusiness() != null) && (account.getBusiness().getDdzt() != null) && (!"".equals(account.getBusiness().getDdzt())))
/* 163:    */     {
/* 164:150 */       hqlSb.append("and b.ddzt =:ddzt ");
/* 165:151 */       map.put("ddzt", account.getBusiness().getDdzt());
/* 166:    */     }
/* 167:153 */     if (getLoginUser().getRoleValues().contains("ROLE_ONE"))
/* 168:    */     {
/* 169:154 */       hqlSb.append("and b.lrr =:lrr ");
/* 170:155 */       map.put("lrr", getLoginUser().getName());
/* 171:    */     }
/* 172:158 */     hqlSb.append("and a.sflx = '1' and a.sffs = '3' order by b.cfsj desc ");
/* 173:    */     
/* 174:160 */     return findPage(page, hqlSb.toString(), map);
/* 175:    */   }
/* 176:    */   
/* 177:    */   public Page<Account> findOnPaymentPage(Page<Account> page, Account account)
/* 178:    */   {
/* 179:165 */     StringBuffer hqlSb = new StringBuffer();
/* 180:166 */     hqlSb.append("select");
/* 181:167 */     hqlSb.append(" a.id,");
/* 182:168 */     hqlSb.append(" a.sflx,");
/* 183:169 */     hqlSb.append(" a.orderId,");
/* 184:170 */     hqlSb.append(" c.dwmc,");
/* 185:171 */     hqlSb.append(" b.travelLine,");
/* 186:172 */     hqlSb.append(" a.gxsj,");
/* 187:173 */     hqlSb.append(" a.user.name,");
/* 188:174 */     hqlSb.append(" a.skje,");
/* 189:175 */     hqlSb.append(" a.fkje,");
/* 190:176 */     hqlSb.append(" a.sffs,");
/* 191:177 */     hqlSb.append(" a.stat,");
/* 192:178 */     hqlSb.append(" b.id,");
/* 193:179 */     hqlSb.append(" b.ygs,");
/* 194:180 */     hqlSb.append(" b.ygf,");
/* 195:181 */     hqlSb.append(" b.yjs,");
/* 196:182 */     hqlSb.append(" b.ygs - b.yjs,");
/* 197:183 */     hqlSb.append(" b.ygs - b.ygf,");
/* 198:184 */     hqlSb.append(" c.id,");
/* 199:185 */     hqlSb.append(" b.ddlx");
/* 200:    */     
/* 201:187 */     hqlSb.append(" from Account a left join a.business b left join a.cooperator c ");
/* 202:188 */     hqlSb.append(" where 1=1 ");
/* 203:    */     
/* 204:190 */     Map<String, Object> map = new HashMap();
/* 205:192 */     if ((account.getBeginDate() != null) && (!"".equals(account.getBeginDate()))) {
/* 206:193 */       hqlSb.append("and date_format(b.cfsj,'%Y-%m-%d')>='" + account.getBeginDate() + "' ");
/* 207:    */     }
/* 208:195 */     if ((account.getEndDate() != null) && (!"".equals(account.getEndDate()))) {
/* 209:196 */       hqlSb.append("and date_format(b.cfsj,'%Y-%m-%d') <= '" + account.getEndDate() + "' ");
/* 210:    */     }
/* 211:199 */     if ((account != null) && (account.getOrderId() != null) && (!"".equals(account.getOrderId()))) {
/* 212:200 */       hqlSb.append("and a.orderId like '" + account.getOrderId() + "%' ");
/* 213:    */     }
/* 214:203 */     if ((account.getStat() != null) && (!"".equals(account.getStat())))
/* 215:    */     {
/* 216:204 */       hqlSb.append("and a.stat=:stat ");
/* 217:205 */       map.put("stat", account.getStat());
/* 218:    */     }
/* 219:    */     else
/* 220:    */     {
/* 221:207 */       hqlSb.append("and a.stat in ('1','2','9') ");
/* 222:    */     }
/* 223:209 */     if ((account.getCooperator() != null) && 
/* 224:210 */       (account.getCooperator().getDwmc() != null) && 
/* 225:211 */       (!"".equals(account.getCooperator().getDwmc()))) {
/* 226:213 */       if (("1".equals(account.getJqchecked())) || (account.getJqchecked() == "1"))
/* 227:    */       {
/* 228:214 */         hqlSb.append("and c.dwmc=:dwmc ");
/* 229:215 */         map.put("dwmc", account.getCooperator().getDwmc());
/* 230:    */       }
/* 231:    */       else
/* 232:    */       {
/* 233:217 */         hqlSb.append("and c.dwmc like:dwmc ");
/* 234:218 */         map.put("dwmc", "%" + account.getCooperator().getDwmc() + "%");
/* 235:    */       }
/* 236:    */     }
/* 237:222 */     if ((account.getBusiness() != null) && (account.getBusiness().getDdzt() != null) && (!"".equals(account.getBusiness().getDdzt())))
/* 238:    */     {
/* 239:223 */       hqlSb.append("and b.ddzt =:ddzt ");
/* 240:224 */       map.put("ddzt", account.getBusiness().getDdzt());
/* 241:    */     }
/* 242:226 */     if (getLoginUser().getRoleValues().contains("ROLE_ONE"))
/* 243:    */     {
/* 244:227 */       hqlSb.append("and a.user.id =:lrr ");
/* 245:228 */       map.put("lrr", getLoginUser().getId());
/* 246:    */     }
/* 247:230 */     if ((account.getBusiness() != null) && (account.getBusiness().getDdlx() != null) && 
/* 248:231 */       (!"".equals(account.getBusiness().getDdlx()))) {
/* 249:232 */       hqlSb.append("and b.ddlx = '" + account.getBusiness().getDdlx() + "' ");
/* 250:    */     }
/* 251:235 */     hqlSb.append("and a.sflx = '2' and a.sffs = '3' order by a.stat, b.cfsj desc ");
/* 252:236 */     page.setPageSize(1000);
/* 253:237 */     return findPage(page, hqlSb.toString(), map);
/* 254:    */   }
/* 255:    */   
/* 256:    */   public List<Account> findAccountUnique(Integer id)
/* 257:    */   {
/* 258:241 */     StringBuffer hqlSb = new StringBuffer();
/* 259:242 */     hqlSb.append("FROM Account WHERE 1=1 and id = " + id);
/* 260:243 */     return getSession().createQuery(hqlSb.toString()).list();
/* 261:    */   }
/* 262:    */   
/* 263:    */   public int updateAccountBz(Account account)
/* 264:    */   {
/* 265:247 */     String sql = "update chee_account t set t.bz = '" + account.getBz() + "' where t.id=" + account.getId();
/* 266:248 */     return getSession().createSQLQuery(sql).executeUpdate();
/* 267:    */   }
/* 268:    */ }


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.dao.impl.AccountDaoImpl
 * JD-Core Version:    0.7.0.1
 */