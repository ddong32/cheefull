/*   1:    */ package com.chee.service.impl;
/*   2:    */ 
/*   3:    */ import com.chee.common.Page;
/*   4:    */ import com.chee.dao.BusinessCustomerDao;
/*   5:    */ import com.chee.entity.Account;
/*   6:    */ import com.chee.entity.Business;
/*   7:    */ import com.chee.entity.BusinessCustomer;
/*   8:    */ import com.chee.entity.Customer;
/*   9:    */ import com.chee.service.AccountService;
/*  10:    */ import com.chee.service.BusinessCustomerService;
/*  11:    */ import com.chee.service.BusinessService;
/*  12:    */ import com.chee.util.DoubleUtil;
/*  13:    */ import java.io.PrintStream;
/*  14:    */ import java.util.Date;
/*  15:    */ import java.util.List;
/*  16:    */ import java.util.Map;
/*  17:    */ import javax.annotation.Resource;
/*  18:    */ import org.hibernate.SQLQuery;
/*  19:    */ import org.hibernate.Session;
/*  20:    */ import org.hibernate.transform.Transformers;
/*  21:    */ import org.springframework.beans.BeanUtils;
/*  22:    */ import org.springframework.stereotype.Service;
/*  23:    */ 
/*  24:    */ @Service
/*  25:    */ public class BusinessCustomerServiceImpl
/*  26:    */   extends BaseServiceImpl<BusinessCustomer, Integer>
/*  27:    */   implements BusinessCustomerService
/*  28:    */ {
/*  29:    */   @Resource
/*  30:    */   private BusinessCustomerDao businessCustomerDao;
/*  31:    */   @Resource
/*  32:    */   private BusinessService businessService;
/*  33:    */   @Resource
/*  34:    */   private AccountService accountService;
/*  35:    */   
/*  36:    */   @Resource
/*  37:    */   public void setBaseDao(BusinessCustomerDao businessCustomerDao)
/*  38:    */   {
/*  39: 36 */     super.setBaseDao(businessCustomerDao);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public Page<BusinessCustomer> findPage(Page<BusinessCustomer> page, BusinessCustomer businessCustomer)
/*  43:    */   {
/*  44: 40 */     return this.businessCustomerDao.findPage(page, businessCustomer);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public List<BusinessCustomer> findOrderID(String order_id)
/*  48:    */   {
/*  49: 44 */     return this.businessCustomerDao.findOrderID(order_id);
/*  50:    */   }
/*  51:    */   
/*  52:    */   public String doBusinessCustomerSaveOrUpdate(BusinessCustomer businessCustomer, String sessionName)
/*  53:    */   {
/*  54: 48 */     String result = "";
/*  55:    */     try
/*  56:    */     {
/*  57: 50 */       BusinessCustomer persistent = null;
/*  58: 51 */       if (businessCustomer.getCustomer().getId() == null) {
/*  59: 52 */         businessCustomer.setCustomer(null);
/*  60:    */       }
/*  61: 54 */       if ((businessCustomer != null) && (businessCustomer.getId() != null))
/*  62:    */       {
/*  63: 55 */         persistent = (BusinessCustomer)this.businessCustomerDao.load(businessCustomer.getId());
/*  64: 56 */         BeanUtils.copyProperties(businessCustomer, persistent, new String[] { "id", "lrr", "lrsj", "gxsj", "yjs" });
/*  65: 57 */         persistent.setGxsj(new Date());
/*  66: 58 */         this.businessCustomerDao.update(persistent);
/*  67: 59 */         updateBusinessInfo(businessCustomer.getOrderId());
/*  68:    */       }
/*  69:    */       else
/*  70:    */       {
/*  71: 61 */         businessCustomer.setLrr(sessionName);
/*  72: 62 */         businessCustomer.setLrsj(new Date());
/*  73: 63 */         businessCustomer.setYjs(0.0D);
/*  74: 64 */         this.businessCustomerDao.save(businessCustomer);
/*  75: 65 */         updateBusinessInfo(businessCustomer.getOrderId());
/*  76:    */       }
/*  77:    */     }
/*  78:    */     catch (Exception e)
/*  79:    */     {
/*  80: 68 */       System.out.println(e.toString());
/*  81: 69 */       result = e.toString();
/*  82:    */     }
/*  83: 71 */     return result;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public String doBatchDelete(Integer[] ids, String orderId)
/*  87:    */   {
/*  88: 75 */     String result = "";
/*  89:    */     try
/*  90:    */     {
/*  91: 77 */       String customerDwmc = "";
/*  92: 78 */       if ((ids != null) && (orderId != null) && (!"".equals(orderId)))
/*  93:    */       {
/*  94:    */         Integer[] arrayOfInteger;
/*  95: 79 */         int j = (arrayOfInteger = ids).length;
/*  96: 79 */         for (int i = 0; i < j; i++)
/*  97:    */         {
/*  98: 79 */           int id = arrayOfInteger[i].intValue();
/*  99: 80 */           List<Account> accountList = this.accountService.findAccountList(Integer.valueOf(id), orderId);
/* 100: 81 */           for (Account account : accountList) {
/* 101: 82 */             if (account.getBusinessCustomer() != null) {
/* 102: 84 */               if (account.getBusinessCustomer().getCustomer() != null) {
/* 103: 86 */                 if (account.getBusinessCustomer().getCustomer().getAreaName() == null) {
/* 104: 88 */                   customerDwmc = customerDwmc + " " + account.getBusinessCustomer().getCustomer().getAreaName();
/* 105:    */                 }
/* 106:    */               }
/* 107:    */             }
/* 108:    */           }
/* 109:    */         }
/* 110: 91 */         if ((!"".equals(customerDwmc)) && (customerDwmc != ""))
/* 111:    */         {
/* 112: 92 */           result = "操作失败：已增加收款项目，" + customerDwmc;
/* 113:    */         }
/* 114:    */         else
/* 115:    */         {
/* 116: 94 */           this.businessCustomerDao.delete(ids);
/* 117: 95 */           updateBusinessInfo(orderId);
/* 118: 96 */           result = "操作完成!";
/* 119:    */         }
/* 120:    */       }
/* 121:    */       else
/* 122:    */       {
/* 123: 99 */         result = "没有选中数据,操作失败!ids=[" + ids + "], orderId=[" + orderId + "]";
/* 124:    */       }
/* 125:    */     }
/* 126:    */     catch (Exception e)
/* 127:    */     {
/* 128:102 */       System.out.println(e.toString());
/* 129:103 */       result = "[BCS_doBatchDelete]：操作失败!ids=[" + ids + "], orderId=[" + orderId + "] " + e.toString();
/* 130:    */     }
/* 131:105 */     return result;
/* 132:    */   }
/* 133:    */   
/* 134:    */   private void updateBusinessInfo(String orderId)
/* 135:    */   {
/* 136:110 */     double hjygs = 0.0D;
/* 137:111 */     int sumAdultNo = 0;int sumChildNo = 0;int sumEscortNo = 0;
/* 138:112 */     List<BusinessCustomer> businessCustomerList = this.businessCustomerDao.getList("orderId", orderId);
/* 139:113 */     for (BusinessCustomer businessCustomer : businessCustomerList)
/* 140:    */     {
/* 141:114 */       hjygs = DoubleUtil.add(Double.valueOf(hjygs), Double.valueOf(businessCustomer.getYgs())).doubleValue();
/* 142:115 */       sumAdultNo += Integer.valueOf(businessCustomer.getAdultNo()).intValue();
/* 143:116 */       sumChildNo += Integer.valueOf(businessCustomer.getChildNo()).intValue();
/* 144:117 */       sumEscortNo += Integer.valueOf(businessCustomer.getEscortNo()).intValue();
/* 145:    */     }
/* 146:120 */     Business persistent = (Business)this.businessService.get("orderId", orderId);
/* 147:121 */     persistent.setYgs(hjygs);
/* 148:122 */     persistent.setAdultNo(Integer.valueOf(sumAdultNo));
/* 149:123 */     persistent.setChildNo(Integer.valueOf(sumChildNo));
/* 150:124 */     persistent.setEscortNo(Integer.valueOf(sumEscortNo));
/* 151:125 */     this.businessService.update(persistent);
/* 152:    */   }
/* 153:    */   
/* 154:    */   public int updateYjs(double yjs, String order_id, Integer id)
/* 155:    */   {
/* 156:129 */     String sql = "update chee_business_customer t set t.yjs = " + yjs + " where t.order_id = '" + order_id + "' and t.id=" + id;
/* 157:130 */     return getSession().createSQLQuery(sql).executeUpdate();
/* 158:    */   }
/* 159:    */   
/* 160:    */   public int updateOrderId(Integer businessId, String orderIdold, String orderIdnew)
/* 161:    */   {
/* 162:134 */     String sql = "update chee_business_customer t set t.order_id = '" + orderIdnew + "' where t.order_id = '" + orderIdold + "' and t.business_id=" + businessId;
/* 163:135 */     return getSession().createSQLQuery(sql).executeUpdate();
/* 164:    */   }
/* 165:    */   
/* 166:    */   public List<Map<String, Object>> reportBusinessCustomer(BusinessCustomer businessCustomer)
/* 167:    */   {
/* 168:139 */     String hqlSb = sqlBusinessCustomer(businessCustomer);
/* 169:140 */     SQLQuery query = getSession().createSQLQuery(hqlSb.toString());
/* 170:141 */     query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
/* 171:142 */     List<Map<String, Object>> list = query.list();
/* 172:143 */     return list;
/* 173:    */   }
/* 174:    */   
/* 175:    */   private String sqlBusinessCustomer(BusinessCustomer businessCustomer)
/* 176:    */   {
/* 177:148 */     String beginDate = businessCustomer.getBeginDate();
/* 178:149 */     String endDate = businessCustomer.getEndDate();
/* 179:150 */     String parent_id = businessCustomer.getParent_id();
/* 180:151 */     String uid = businessCustomer.getUid();
/* 181:152 */     String type = businessCustomer.getType();
/* 182:153 */     String areaName = businessCustomer.getCustomerDwmc();
/* 183:    */     
/* 184:155 */     StringBuffer hqlSb = new StringBuffer();
/* 185:156 */     if (type == null)
/* 186:    */     {
/* 187:157 */       hqlSb.append("select r.* from (select id, area_name, sum(adult_no) adult_no, sum(child_no) child_no, sum(escort_no) escort_no, sum(ygs) ygs, sum(yjs) yjs, sum(wsk) wsk, '1' type, 'closed' state from (");
/* 188:158 */       hqlSb.append("  SELECT ");
/* 189:159 */       hqlSb.append("    substr(c.path, 1, (locate(',', c.path) - 1)) as id, ");
/* 190:160 */       hqlSb.append("    substr(c.path_name, 1, (locate('|', c.path_name) - 1)) as area_name, ");
/* 191:161 */       hqlSb.append("    a.adult_no, ");
/* 192:162 */       hqlSb.append("    a.child_no, ");
/* 193:163 */       hqlSb.append("    a.escort_no, ");
/* 194:164 */       hqlSb.append("    a.ygs, ");
/* 195:165 */       hqlSb.append("    a.yjs, ");
/* 196:166 */       hqlSb.append("    (a.ygs - a.yjs) wsk,");
/* 197:167 */       hqlSb.append("    c.sort ");
/* 198:168 */       hqlSb.append("\tFROM ");
/* 199:169 */       hqlSb.append("    chee_business_customer a, chee_business b, chee_customer c ");
/* 200:170 */       hqlSb.append("\tWHERE 1=1 ");
/* 201:171 */       hqlSb.append("and a.business_id = b.id ");
/* 202:172 */       hqlSb.append("and a.customer_id = c.id ");
/* 203:173 */       if ((beginDate != null) && (!"".equals(beginDate))) {
/* 204:174 */         hqlSb.append(" and date_format(b.cfsj,'%Y-%m-%d') >= '" + beginDate + "' ");
/* 205:    */       }
/* 206:176 */       if ((endDate != null) && (!"".equals(endDate))) {
/* 207:177 */         hqlSb.append(" and date_format(b.cfsj,'%Y-%m-%d') <= '" + endDate + "' ");
/* 208:    */       }
/* 209:179 */       if ((uid != null) && (!"".equals(uid))) {
/* 210:180 */         hqlSb.append(" and b.user_id in (" + uid + ") ");
/* 211:    */       }
/* 212:182 */       if ((areaName != null) && (!"".equals(areaName))) {
/* 213:183 */         hqlSb.append(" and c.path_name like '%" + areaName + "%' ");
/* 214:    */       }
/* 215:185 */       hqlSb.append(") report group by id, area_name) r, chee_customer s where r.id = s.id and s.type=0 order by s.sort");
/* 216:    */     }
/* 217:186 */     else if ("1".equals(type))
/* 218:    */     {
/* 219:187 */       hqlSb.append("select id, area_name, parent_id, sum(adult_no) adult_no, sum(child_no) child_no, sum(escort_no) escort_no, sum(ygs) ygs, sum(yjs) yjs, sum(wsk) wsk, '2' type, 'closed' state from (");
/* 220:188 */       hqlSb.append("\tSELECT ");
/* 221:189 */       hqlSb.append("    substr(substring_index(c.path,',',2), length(substring_index(c.path,',',1))+2) as id, ");
/* 222:190 */       hqlSb.append("    replace(substring_index(c.path_name,'|',2), substr(c.path_name, 1, (locate('|', c.path_name))), '') as area_name, ");
/* 223:191 */       hqlSb.append("    substring_index(c.path,',',1) parent_id, ");
/* 224:192 */       hqlSb.append("    a.adult_no, ");
/* 225:193 */       hqlSb.append("    a.child_no, ");
/* 226:194 */       hqlSb.append("    a.escort_no, ");
/* 227:195 */       hqlSb.append("    a.ygs, ");
/* 228:196 */       hqlSb.append("    a.yjs, ");
/* 229:197 */       hqlSb.append("    (a.ygs - a.yjs) wsk,");
/* 230:198 */       hqlSb.append("    c.sort ");
/* 231:199 */       hqlSb.append("  FROM ");
/* 232:200 */       hqlSb.append("    chee_business_customer a, chee_business b, chee_customer c ");
/* 233:201 */       hqlSb.append("\tWHERE 1=1 ");
/* 234:202 */       hqlSb.append("and a.business_id = b.id ");
/* 235:203 */       hqlSb.append("and a.customer_id = c.id ");
/* 236:204 */       if ((beginDate != null) && (!"".equals(beginDate))) {
/* 237:205 */         hqlSb.append(" and date_format(b.cfsj,'%Y-%m-%d') >= '" + beginDate + "' ");
/* 238:    */       }
/* 239:207 */       if ((endDate != null) && (!"".equals(endDate))) {
/* 240:208 */         hqlSb.append(" and date_format(b.cfsj,'%Y-%m-%d') <= '" + endDate + "' ");
/* 241:    */       }
/* 242:210 */       if (!"".equals(parent_id)) {
/* 243:211 */         hqlSb.append("\tand substring_index(c.path,',',1) = " + parent_id);
/* 244:    */       }
/* 245:213 */       if ((uid != null) && (!"".equals(uid))) {
/* 246:214 */         hqlSb.append(" and b.user_id in (" + uid + ") ");
/* 247:    */       }
/* 248:216 */       if ((areaName != null) && (!"".equals(areaName))) {
/* 249:217 */         hqlSb.append(" and c.path_name like '%" + areaName + "%' ");
/* 250:    */       }
/* 251:219 */       hqlSb.append(") report group by id, area_name, parent_id order by area_name");
/* 252:    */     }
/* 253:220 */     else if ("2".equals(type))
/* 254:    */     {
/* 255:221 */       hqlSb.append("SELECT");
/* 256:222 */       hqlSb.append(" c.id,");
/* 257:223 */       hqlSb.append(" c.area_name,");
/* 258:224 */       hqlSb.append(" c.parent_id,");
/* 259:225 */       hqlSb.append(" a.adult_no,");
/* 260:226 */       hqlSb.append(" a.child_no,");
/* 261:227 */       hqlSb.append(" a.escort_no,");
/* 262:228 */       hqlSb.append(" a.ygs,");
/* 263:229 */       hqlSb.append(" a.yjs,");
/* 264:230 */       hqlSb.append(" (a.ygs - a.yjs) wsk,");
/* 265:231 */       hqlSb.append(" a.lrr,");
/* 266:232 */       hqlSb.append(" b.order_id,");
/* 267:233 */       hqlSb.append(" b.travel_line ");
/* 268:234 */       hqlSb.append("FROM chee_business_customer a, chee_business b, chee_customer c ");
/* 269:235 */       hqlSb.append(" where 1=1 ");
/* 270:236 */       hqlSb.append("and a.business_id = b.id ");
/* 271:237 */       hqlSb.append("and a.customer_id = c.id ");
/* 272:238 */       if ((beginDate != null) && (!"".equals(beginDate))) {
/* 273:239 */         hqlSb.append(" and date_format(b.cfsj,'%Y-%m-%d') >= '" + beginDate + "' ");
/* 274:    */       }
/* 275:241 */       if ((endDate != null) && (!"".equals(endDate))) {
/* 276:242 */         hqlSb.append(" and date_format(b.cfsj,'%Y-%m-%d') <= '" + endDate + "' ");
/* 277:    */       }
/* 278:244 */       if (!"".equals(parent_id)) {
/* 279:245 */         hqlSb.append("\tand c.parent_id = " + parent_id);
/* 280:    */       }
/* 281:247 */       if ((uid != null) && (!"".equals(uid))) {
/* 282:248 */         hqlSb.append(" and b.user_id in (" + uid + ") ");
/* 283:    */       }
/* 284:250 */       if ((areaName != null) && (!"".equals(areaName))) {
/* 285:251 */         hqlSb.append(" and c.path_name like '%" + areaName + "%' ");
/* 286:    */       }
/* 287:253 */       hqlSb.append(" order by c.area_name, b.order_id ");
/* 288:    */     }
/* 289:255 */     return hqlSb.toString();
/* 290:    */   }
/* 291:    */   
/* 292:    */   public List<Map<String, Object>> reportBusinessCustomerTotal(BusinessCustomer businessCustomer)
/* 293:    */   {
/* 294:260 */     String beginDate = businessCustomer.getBeginDate();
/* 295:261 */     String endDate = businessCustomer.getEndDate();
/* 296:262 */     String parent_id = businessCustomer.getParent_id();
/* 297:263 */     String uid = businessCustomer.getUid();
/* 298:264 */     String areaName = businessCustomer.getCustomerDwmc();
/* 299:    */     
/* 300:266 */     StringBuffer hqlSb = new StringBuffer();
/* 301:267 */     hqlSb.append("SELECT");
/* 302:268 */     hqlSb.append(" '合计' area_name,");
/* 303:269 */     hqlSb.append(" sum(a.adult_no) adult_no,");
/* 304:270 */     hqlSb.append(" sum(a.child_no) child_no,");
/* 305:271 */     hqlSb.append(" sum(a.escort_no) escort_no,");
/* 306:272 */     hqlSb.append(" sum(a.ygs) ygs,");
/* 307:273 */     hqlSb.append(" sum(a.yjs) yjs,");
/* 308:274 */     hqlSb.append(" sum(a.ygs - a.yjs) wsk ");
/* 309:275 */     hqlSb.append("FROM chee_business_customer a, chee_business b, chee_customer c");
/* 310:276 */     hqlSb.append(" WHERE 1=1 ");
/* 311:277 */     hqlSb.append(" and a.business_id = b.id");
/* 312:278 */     hqlSb.append(" and a.customer_id = c.id");
/* 313:279 */     if ((beginDate != null) && (!"".equals(beginDate))) {
/* 314:280 */       hqlSb.append(" and date_format(cfsj,'%Y-%m-%d') >= '" + beginDate + "' ");
/* 315:    */     }
/* 316:282 */     if ((endDate != null) && (!"".equals(endDate))) {
/* 317:283 */       hqlSb.append(" and date_format(cfsj,'%Y-%m-%d') <= '" + endDate + "' ");
/* 318:    */     }
/* 319:285 */     if ((parent_id != null) && (!"".equals(parent_id))) {
/* 320:286 */       hqlSb.append(" and .parent_id = " + parent_id);
/* 321:    */     }
/* 322:288 */     if ((uid != null) && (!"".equals(uid))) {
/* 323:289 */       hqlSb.append(" and b.user_id in (" + uid + ") ");
/* 324:    */     }
/* 325:291 */     if ((areaName != null) && (!"".equals(areaName))) {
/* 326:292 */       hqlSb.append(" and c.path_name like '%" + areaName + "%' ");
/* 327:    */     }
/* 328:294 */     SQLQuery query = getSession().createSQLQuery(hqlSb.toString());
/* 329:295 */     query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
/* 330:296 */     List<Map<String, Object>> list = query.list();
/* 331:297 */     return list;
/* 332:    */   }
/* 333:    */ }


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.service.impl.BusinessCustomerServiceImpl
 * JD-Core Version:    0.7.0.1
 */