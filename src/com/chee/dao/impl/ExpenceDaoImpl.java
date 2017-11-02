/*   1:    */ package com.chee.dao.impl;
/*   2:    */ 
/*   3:    */ import com.chee.common.Page;
/*   4:    */ import com.chee.dao.ExpenceDao;
/*   5:    */ import com.chee.entity.Department;
/*   6:    */ import com.chee.entity.Expence;
/*   7:    */ import com.chee.entity.User;
/*   8:    */ import com.chee.service.DepartmentService;
/*   9:    */ import com.chee.util.UserUtil;
/*  10:    */ import java.util.HashMap;
/*  11:    */ import java.util.List;
/*  12:    */ import java.util.Map;
/*  13:    */ import javax.annotation.Resource;
/*  14:    */ import org.springframework.stereotype.Repository;
/*  15:    */ 
/*  16:    */ @Repository
/*  17:    */ public class ExpenceDaoImpl
/*  18:    */   extends BaseDaoImpl<Expence, Integer>
/*  19:    */   implements ExpenceDao
/*  20:    */ {
/*  21:    */   @Resource
/*  22:    */   UserUtil userUtil;
/*  23:    */   @Resource
/*  24:    */   DepartmentService departmentService;
/*  25:    */   
/*  26:    */   public User getLoginUser()
/*  27:    */   {
/*  28: 25 */     return this.userUtil.getLoginUser();
/*  29:    */   }
/*  30:    */   
/*  31:    */   public List<Integer> findAuditIdList(Expence expence)
/*  32:    */   {
/*  33: 30 */     StringBuffer hqlSb = new StringBuffer("select id from Expence a where 1=1 ");
/*  34: 31 */     hqlSb.append(doPropertyFilter(expence));
/*  35: 32 */     return find(hqlSb.toString(), new Object[0]);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public Page<Expence> findExpencePage(Page<Expence> page, Expence expence, String param)
/*  39:    */   {
/*  40: 36 */     StringBuffer hqlSb = new StringBuffer("from Expence a where 1=1 ");
/*  41: 37 */     Map<String, Object> map = new HashMap();
/*  42: 38 */     hqlSb.append(doPropertyFilter(expence, param));
/*  43: 39 */     return findPage(page, hqlSb.toString(), map);
/*  44:    */   }
/*  45:    */   
/*  46:    */   private StringBuffer doPropertyFilter(Expence expence)
/*  47:    */   {
/*  48: 44 */     StringBuffer hqlSb = new StringBuffer();
/*  49:    */     
/*  50: 46 */     Map<String, Object> map = new HashMap();
/*  51: 48 */     if ((expence.getBeginDate() != null) && (!"".equals(expence.getBeginDate()))) {
/*  52: 49 */       hqlSb.append("and date_format(a.fssj,'%Y-%m-%d') >= '" + expence.getBeginDate() + "' ");
/*  53:    */     }
/*  54: 51 */     if ((expence.getEndDate() != null) && (!"".equals(expence.getEndDate()))) {
/*  55: 52 */       hqlSb.append("and date_format(a.fssj,'%Y-%m-%d') <= '" + expence.getEndDate() + "' ");
/*  56:    */     }
/*  57: 54 */     if ((expence.getStat() != null) && (!"".equals(expence.getStat()))) {
/*  58: 55 */       hqlSb.append("and a.stat ='" + expence.getStat() + "' ");
/*  59:    */     }
/*  60: 57 */     if ((expence.getBxlx() != null) && (!"".equals(expence.getBxlx()))) {
/*  61: 58 */       hqlSb.append("and a.bxlx ='" + expence.getBxlx() + "' ");
/*  62:    */     }
/*  63: 69 */     if ((expence.getProUserIds() != null) && (expence.getProUserIds().size() > 0))
/*  64:    */     {
/*  65: 70 */       hqlSb.append("and a.user.id in ( ");
/*  66: 71 */       for (int i = 0; expence.getProUserIds().size() > i; i++)
/*  67:    */       {
/*  68: 72 */         if (i != 0) {
/*  69: 73 */           hqlSb.append(" , ");
/*  70:    */         }
/*  71: 75 */         hqlSb.append(expence.getProUserIds().get(i));
/*  72:    */       }
/*  73: 77 */       hqlSb.append(") ");
/*  74:    */     }
/*  75: 80 */     else if (!getLoginUser().getRoleValues().contains("ROLE_ADMIN"))
/*  76:    */     {
/*  77: 82 */       if (getLoginUser().getRoleValues().contains("ROLE_MANAGER"))
/*  78:    */       {
/*  79: 83 */         List orgCodeList = this.departmentService.getConnectOrgCodes(getLoginUser().getDepartment().getId());
/*  80: 85 */         if (orgCodeList != null) {
/*  81: 86 */           if (orgCodeList.size() == 1)
/*  82:    */           {
/*  83: 87 */             hqlSb.append(" and a.user.department.code = '" + orgCodeList.get(0) + "'");
/*  84:    */           }
/*  85:    */           else
/*  86:    */           {
/*  87: 89 */             hqlSb.append(" and a.user.department.code in ( ");
/*  88: 90 */             for (int i = 0; i < orgCodeList.size(); i++)
/*  89:    */             {
/*  90: 91 */               if (i != 0) {
/*  91: 92 */                 hqlSb.append(" , ");
/*  92:    */               }
/*  93: 94 */               hqlSb.append("'" + orgCodeList.get(i) + "'");
/*  94:    */             }
/*  95: 96 */             hqlSb.append(" ) ");
/*  96:    */           }
/*  97:    */         }
/*  98:    */       }
/*  99:    */       else
/* 100:    */       {
/* 101:101 */         hqlSb.append("and a.user.id = '" + getLoginUser().getId() + "' ");
/* 102:    */       }
/* 103:    */     }
/* 104:105 */     hqlSb.append("order by a.stat, a.bxsj desc ");
/* 105:106 */     return hqlSb;
/* 106:    */   }
/* 107:    */   
/* 108:    */   private StringBuffer doPropertyFilter(Expence expence, String param)
/* 109:    */   {
/* 110:111 */     StringBuffer hqlSb = new StringBuffer();
/* 111:    */     
/* 112:113 */     Map<String, Object> map = new HashMap();
/* 113:115 */     if ((expence.getBeginDate() != null) && (!"".equals(expence.getBeginDate()))) {
/* 114:116 */       hqlSb.append("and date_format(a.fssj,'%Y-%m-%d') >= '" + expence.getBeginDate() + "' ");
/* 115:    */     }
/* 116:118 */     if ((expence.getEndDate() != null) && (!"".equals(expence.getEndDate()))) {
/* 117:119 */       hqlSb.append("and date_format(a.fssj,'%Y-%m-%d') <= '" + expence.getEndDate() + "' ");
/* 118:    */     }
/* 119:121 */     if ((expence.getStat() != null) && (!"".equals(expence.getStat()))) {
/* 120:122 */       hqlSb.append("and a.stat ='" + expence.getStat() + "' ");
/* 121:124 */     } else if ("pass".equals(param)) {
/* 122:125 */       hqlSb.append("and a.stat <> '3' ");
/* 123:126 */     } else if ("payy".equals(param)) {
/* 124:127 */       hqlSb.append("and a.stat <> '1' ");
/* 125:    */     }
/* 126:130 */     if ((expence.getBxlx() != null) && (!"".equals(expence.getBxlx()))) {
/* 127:131 */       hqlSb.append("and a.bxlx ='" + expence.getBxlx() + "' ");
/* 128:    */     }
/* 129:142 */     if (("list".equals(param)) || (param == "list"))
/* 130:    */     {
/* 131:143 */       hqlSb.append("and a.user.id = '" + getLoginUser().getId() + "' ");
/* 132:    */     }
/* 133:145 */     else if ((expence.getProUserIds() != null) && (expence.getProUserIds().size() > 0))
/* 134:    */     {
/* 135:146 */       hqlSb.append("and a.user.id in ( ");
/* 136:147 */       for (int i = 0; expence.getProUserIds().size() > i; i++)
/* 137:    */       {
/* 138:148 */         if (i != 0) {
/* 139:149 */           hqlSb.append(" , ");
/* 140:    */         }
/* 141:151 */         hqlSb.append(expence.getProUserIds().get(i));
/* 142:    */       }
/* 143:153 */       hqlSb.append(") ");
/* 144:    */     }
/* 145:180 */     hqlSb.append("order by a.stat, a.bxsj desc ");
/* 146:181 */     return hqlSb;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public Expence getExpenceIds(Integer id)
/* 150:    */   {
/* 151:185 */     Expence expence = (Expence)get(id);
/* 152:186 */     return expence;
/* 153:    */   }
/* 154:    */ }


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.dao.impl.ExpenceDaoImpl
 * JD-Core Version:    0.7.0.1
 */