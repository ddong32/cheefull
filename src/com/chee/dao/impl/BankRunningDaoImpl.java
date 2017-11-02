/*   1:    */ package com.chee.dao.impl;
/*   2:    */ 
/*   3:    */ import com.chee.common.Page;
/*   4:    */ import com.chee.dao.BankRunningDao;
/*   5:    */ import com.chee.entity.Bank;
/*   6:    */ import com.chee.entity.BankRunning;
/*   7:    */ import com.chee.entity.Business;
/*   8:    */ import com.chee.util.DateUtil;
/*   9:    */ import java.util.HashMap;
/*  10:    */ import java.util.List;
/*  11:    */ import java.util.Map;
/*  12:    */ import org.hibernate.criterion.DetachedCriteria;
/*  13:    */ import org.hibernate.criterion.Restrictions;
/*  14:    */ import org.springframework.stereotype.Repository;
/*  15:    */ 
/*  16:    */ @Repository
/*  17:    */ public class BankRunningDaoImpl
/*  18:    */   extends BaseDaoImpl<BankRunning, Integer>
/*  19:    */   implements BankRunningDao
/*  20:    */ {
/*  21:    */   public Page<BankRunning> findBankRunningPage(Page<BankRunning> page, BankRunning bankRunning)
/*  22:    */   {
/*  23: 20 */     StringBuffer hqlSb = new StringBuffer();
/*  24: 21 */     hqlSb.append("select");
/*  25: 22 */     hqlSb.append(" this_.id,");
/*  26: 23 */     hqlSb.append(" this_.shbj,");
/*  27: 24 */     hqlSb.append(" b.djm,");
/*  28: 25 */     hqlSb.append(" this_.jzsj,");
/*  29: 26 */     hqlSb.append(" b.cush,");
/*  30: 27 */     hqlSb.append(" this_.srje,");
/*  31: 28 */     hqlSb.append(" this_.zcje,");
/*  32: 29 */     hqlSb.append(" this_.dfzh,");
/*  33: 30 */     hqlSb.append(" this_.dfhm,");
/*  34: 31 */     hqlSb.append(" this_.lrr,");
/*  35: 32 */     hqlSb.append(" this_.lrsj,");
/*  36: 33 */     hqlSb.append(" this_.bz,");
/*  37: 34 */     hqlSb.append(" this_.orderId");
/*  38: 35 */     hqlSb.append(" from BankRunning this_ left join this_.bank b left join this_.business a where 1=1");
/*  39:    */     
/*  40: 37 */     Map<String, Object> map = new HashMap();
/*  41: 39 */     if ((bankRunning.getBeginDate() != null) && (!"".equals(bankRunning.getBeginDate()))) {
/*  42: 40 */       hqlSb.append(" and date_format(this_.jzsj,'%Y-%m-%d') >= '" + bankRunning.getBeginDate() + "'");
/*  43:    */     }
/*  44: 42 */     if ((bankRunning.getEndDate() != null) && (!"".equals(bankRunning.getEndDate()))) {
/*  45: 43 */       hqlSb.append(" and date_format(this_.jzsj,'%Y-%m-%d') <= '" + bankRunning.getEndDate() + "'");
/*  46:    */     }
/*  47: 45 */     if ((bankRunning.getSflx() != null) && (!"".equals(bankRunning.getSflx()))) {
/*  48: 48 */       hqlSb.append(" and this_.sflx = " + bankRunning.getSflx());
/*  49:    */     }
/*  50: 50 */     if ((bankRunning.getBusiness() != null) && (bankRunning.getBusiness().getDdlx() != null) && (!"".equals(bankRunning.getBusiness().getDdlx()))) {
/*  51: 51 */       hqlSb.append(" and a.ddlx = " + bankRunning.getBusiness().getDdlx());
/*  52:    */     }
/*  53: 53 */     if ((bankRunning.getBank() != null) && (bankRunning.getBank().getId() != null) && (!"".equals(bankRunning.getBank().getId()))) {
/*  54: 54 */       hqlSb.append(" and b.id = " + bankRunning.getBank().getId());
/*  55:    */     }
/*  56: 56 */     if ((bankRunning.getShbj() != null) && (!"".equals(bankRunning.getShbj())))
/*  57:    */     {
/*  58: 57 */       hqlSb.append(" and this_.shbj =:shbj ");
/*  59: 58 */       map.put("shbj", bankRunning.getShbj());
/*  60:    */     }
/*  61: 60 */     if ((bankRunning.getBeginJe() != null) && (!"".equals(bankRunning.getBeginJe()))) {
/*  62: 61 */       hqlSb.append(" and abs(this_.lrje) >= " + bankRunning.getBeginJe());
/*  63:    */     }
/*  64: 63 */     if ((bankRunning.getEndJe() != null) && (!"".equals(bankRunning.getEndJe()))) {
/*  65: 64 */       hqlSb.append(" and abs(this_.lrje) <= " + bankRunning.getEndJe());
/*  66:    */     }
/*  67: 66 */     hqlSb.append(" order by this_.shbj asc, this_.jzsj desc");
/*  68:    */     
/*  69:    */ 
/*  70: 69 */     List<Object[]> list = find(hqlSb.toString(), map);
/*  71:    */     
/*  72: 71 */     double sumZcje = 0.0D;double sumSrje = 0.0D;
/*  73: 73 */     for (Object[] bean : list)
/*  74:    */     {
/*  75: 74 */       sumSrje += Double.parseDouble(bean[5].toString());
/*  76: 75 */       sumZcje += Double.parseDouble(bean[6].toString());
/*  77:    */     }
/*  78: 78 */     page.setSumSrje(getFormatMoney(sumSrje));
/*  79: 79 */     page.setSumZcje(getFormatMoney(sumZcje));
/*  80:    */     
/*  81: 81 */     return findPage(page, hqlSb.toString(), map);
/*  82:    */   }
/*  83:    */   
/*  84:    */   public BankRunning findBankRunningUnique(BankRunning bankRunning)
/*  85:    */   {
/*  86: 85 */     DetachedCriteria detachedCriteria = DetachedCriteria.forClass(BankRunning.class);
/*  87: 86 */     if (bankRunning != null)
/*  88:    */     {
/*  89: 87 */       if ((bankRunning.getBank() != null) && (bankRunning.getBank().getId() == null))
/*  90:    */       {
/*  91: 88 */         detachedCriteria.createAlias("bank", "bank");
/*  92: 89 */         detachedCriteria.add(Restrictions.eq("bank.id", bankRunning.getBank().getId()));
/*  93:    */       }
/*  94: 91 */       if ("1".equals(bankRunning.getSflx()))
/*  95:    */       {
/*  96: 92 */         detachedCriteria.add(Restrictions.eq("sflx", bankRunning.getSflx()));
/*  97: 93 */         detachedCriteria.add(Restrictions.eq("srje", Double.valueOf(bankRunning.getJe())));
/*  98:    */       }
/*  99:    */       else
/* 100:    */       {
/* 101: 95 */         detachedCriteria.add(Restrictions.eq("sflx", bankRunning.getSflx()));
/* 102: 96 */         detachedCriteria.add(Restrictions.eq("zcje", Double.valueOf(bankRunning.getJe())));
/* 103:    */       }
/* 104: 98 */       if (bankRunning.getJzsj() != null) {
/* 105: 99 */         detachedCriteria.add(Restrictions.eq("jzsj", bankRunning.getJzsj()));
/* 106:    */       }
/* 107:101 */       if ((bankRunning.getDfzh() != null) && (!"".equals(bankRunning.getDfzh()))) {
/* 108:102 */         detachedCriteria.add(Restrictions.eq("dfzh", bankRunning.getDfzh()));
/* 109:    */       }
/* 110:104 */       if ((bankRunning.getDfhm() != null) && (!"".equals(bankRunning.getDfhm()))) {
/* 111:105 */         detachedCriteria.add(Restrictions.eq("dfhm", bankRunning.getDfhm()));
/* 112:    */       }
/* 113:    */     }
/* 114:108 */     return (BankRunning)findUnique(detachedCriteria);
/* 115:    */   }
/* 116:    */   
/* 117:    */   public List<BankRunning> findAuditBankRunningList(BankRunning bankRunning)
/* 118:    */   {
/* 119:112 */     StringBuffer hqlSb = new StringBuffer();
/* 120:113 */     hqlSb.append("select a from BankRunning a left join a.bank b where 1=1 ");
/* 121:    */     
/* 122:115 */     Map<String, Object> map = new HashMap();
/* 123:116 */     if ((bankRunning.getJzsj() != null) && (!"".equals(bankRunning.getJzsj()))) {
/* 124:117 */       hqlSb.append("and date_format(a.jzsj,'%Y-%m-%d') = '" + DateUtil.formatDate(bankRunning.getJzsj(), "yyyy-MM-dd") + "'");
/* 125:    */     }
/* 126:119 */     if ((bankRunning.getBank() != null) && (bankRunning.getBank().getId() != null) && (!"".equals(bankRunning.getBank().getId()))) {
/* 127:120 */       hqlSb.append("and b.id = '" + bankRunning.getBank().getId() + "'");
/* 128:    */     }
/* 129:122 */     if ((bankRunning.getId() != null) && (!"".equals(bankRunning.getId()))) {
/* 130:123 */       hqlSb.append("and a.id = '" + bankRunning.getId() + "'");
/* 131:    */     } else {
/* 132:125 */       hqlSb.append("and a.shbj = '0' ");
/* 133:    */     }
/* 134:127 */     if ((bankRunning.getSflx() != null) && (!"".equals(bankRunning.getSflx())))
/* 135:    */     {
/* 136:128 */       hqlSb.append("and a.sflx =:sflx ");
/* 137:129 */       map.put("sflx", bankRunning.getSflx());
/* 138:130 */       if (("1".equals(bankRunning.getSflx())) || (bankRunning.getSflx() == "1")) {
/* 139:131 */         hqlSb.append("and a.srje = '" + bankRunning.getJe() + "' ");
/* 140:    */       } else {
/* 141:133 */         hqlSb.append("and a.zcje = '" + bankRunning.getJe() + "' ");
/* 142:    */       }
/* 143:    */     }
/* 144:136 */     return find(hqlSb.toString(), map);
/* 145:    */   }
/* 146:    */   
/* 147:    */   public List<BankRunning> findBankRunningListTotal(BankRunning bankRunning)
/* 148:    */   {
/* 149:140 */     StringBuffer hqlSb = new StringBuffer();
/* 150:141 */     hqlSb.append(" from BankRunning this_ where 1=1 ");
/* 151:    */     
/* 152:143 */     Map<String, Object> map = new HashMap();
/* 153:145 */     if ((bankRunning.getBeginDate() != null) && (!"".equals(bankRunning.getBeginDate()))) {
/* 154:146 */       hqlSb.append(" and date_format(this_.jzsj,'%Y-%m-%d') >= '" + bankRunning.getBeginDate() + "'");
/* 155:    */     }
/* 156:148 */     if ((bankRunning.getEndDate() != null) && (!"".equals(bankRunning.getEndDate()))) {
/* 157:149 */       hqlSb.append(" and date_format(this_.jzsj,'%Y-%m-%d') <= '" + bankRunning.getEndDate() + "'");
/* 158:    */     }
/* 159:151 */     if ((bankRunning.getSflx() != null) && (!"".equals(bankRunning.getSflx())))
/* 160:    */     {
/* 161:152 */       hqlSb.append(" and this_.sflx =:sflx ");
/* 162:153 */       map.put("sflx", bankRunning.getSflx());
/* 163:    */     }
/* 164:155 */     if ((bankRunning.getBank() != null) && (bankRunning.getBank().getId() != null) && (!"".equals(bankRunning.getBank().getId()))) {
/* 165:156 */       hqlSb.append(" and b.id = " + bankRunning.getBank().getId());
/* 166:    */     }
/* 167:158 */     if ((bankRunning.getShbj() != null) && (!"".equals(bankRunning.getShbj())))
/* 168:    */     {
/* 169:159 */       hqlSb.append(" and this_.shbj =:shbj ");
/* 170:160 */       map.put("shbj", bankRunning.getShbj());
/* 171:    */     }
/* 172:163 */     if ((bankRunning.getBeginJe() != null) && (!"".equals(bankRunning.getBeginJe()))) {
/* 173:164 */       hqlSb.append(" and (this_.srje >= " + bankRunning.getBeginJe() + " or this_.zcje >= " + bankRunning.getBeginJe() + ")");
/* 174:    */     }
/* 175:166 */     if ((bankRunning.getEndJe() != null) && (!"".equals(bankRunning.getEndJe()))) {
/* 176:167 */       hqlSb.append(" and (this_.srje <= " + bankRunning.getEndJe() + " and this_.zcje <= " + bankRunning.getEndJe() + ")");
/* 177:    */     }
/* 178:170 */     hqlSb.append(" order by this_.shbj asc, this_.jzsj desc ");
/* 179:    */     
/* 180:172 */     return find(hqlSb.toString(), map);
/* 181:    */   }
/* 182:    */ }


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.dao.impl.BankRunningDaoImpl
 * JD-Core Version:    0.7.0.1
 */