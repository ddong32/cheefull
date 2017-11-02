/*   1:    */ package com.chee.service.impl;
/*   2:    */ 
/*   3:    */ import com.chee.common.Page;
/*   4:    */ import com.chee.dao.ExpenceDao;
/*   5:    */ import com.chee.dao.ExpenceFlowDao;
/*   6:    */ import com.chee.entity.Bank;
/*   7:    */ import com.chee.entity.BankRunning;
/*   8:    */ import com.chee.entity.Expence;
/*   9:    */ import com.chee.entity.ExpenceFlow;
/*  10:    */ import com.chee.entity.User;
/*  11:    */ import com.chee.service.BankRunningService;
/*  12:    */ import com.chee.service.BankService;
/*  13:    */ import com.chee.service.ExpenceService;
/*  14:    */ import java.util.Date;
/*  15:    */ import java.util.List;
/*  16:    */ import javax.annotation.Resource;
/*  17:    */ import org.springframework.beans.BeanUtils;
/*  18:    */ import org.springframework.stereotype.Service;
/*  19:    */ 
/*  20:    */ @Service
/*  21:    */ public class ExpenceServiceImpl
/*  22:    */   extends BaseServiceImpl<Expence, Integer>
/*  23:    */   implements ExpenceService
/*  24:    */ {
/*  25:    */   @Resource
/*  26:    */   private ExpenceDao expenceDao;
/*  27:    */   @Resource
/*  28:    */   private ExpenceFlowDao expenceFlowDao;
/*  29:    */   @Resource
/*  30:    */   private BankRunningService bankRunningService;
/*  31:    */   @Resource
/*  32:    */   private BankService bankService;
/*  33:    */   
/*  34:    */   @Resource
/*  35:    */   public void setBaseDao(ExpenceDao expenceDao)
/*  36:    */   {
/*  37: 35 */     super.setBaseDao(expenceDao);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public Page<Expence> findExpencePage(Page<Expence> page, Expence expence, String param)
/*  41:    */   {
/*  42: 39 */     return this.expenceDao.findExpencePage(page, expence, param);
/*  43:    */   }
/*  44:    */   
/*  45:    */   public List<Integer> findAuditIdList(Expence expence)
/*  46:    */   {
/*  47: 43 */     return this.expenceDao.findAuditIdList(expence);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public Integer save(Expence expence, User user)
/*  51:    */   {
/*  52: 48 */     Integer id = (Integer)this.expenceDao.save(expence);
/*  53: 49 */     expence.setId(id);
/*  54: 50 */     ExpenceFlow expenceFlow = new ExpenceFlow();
/*  55: 51 */     expenceFlow.setGxsj(new Date());
/*  56: 52 */     expenceFlow.setExpence(expence);
/*  57: 53 */     expenceFlow.setUser(user);
/*  58: 54 */     expenceFlow.setOptContent("新增");
/*  59: 55 */     expenceFlow.setYwlc(Integer.valueOf(1));
/*  60: 56 */     this.expenceFlowDao.save(expenceFlow);
/*  61: 57 */     return id;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public String doPass(Expence expence, User user)
/*  65:    */   {
/*  66: 61 */     String result = "";
/*  67: 62 */     Expence persistent = (Expence)this.expenceDao.load(expence.getId());
/*  68: 63 */     if (("2".equals(persistent.getStat())) || (persistent.getStat() == "2")) {
/*  69: 64 */       return result = "[id=" + expence.getId() + "]该项数据已经审核！";
/*  70:    */     }
/*  71: 68 */     ExpenceFlow expenceFlow = new ExpenceFlow();
/*  72: 69 */     expenceFlow.setGxsj(new Date());
/*  73: 70 */     expenceFlow.setExpence(expence);
/*  74: 71 */     expenceFlow.setUser(user);
/*  75: 72 */     expenceFlow.setOptContent("审核");
/*  76: 73 */     expenceFlow.setYwlc(Integer.valueOf(2));
/*  77: 74 */     this.expenceFlowDao.save(expenceFlow);
/*  78:    */     
/*  79:    */ 
/*  80: 77 */     BeanUtils.copyProperties(expence, persistent, new String[] { "id", "user", "bxsj", "fssj", "bxnr", "bxje", "bxr", "skzh", "bxlx" });
/*  81: 78 */     persistent.setGxr(user.getName());
/*  82: 79 */     persistent.setGxsj(new Date());
/*  83: 80 */     persistent.setStat("2");
/*  84:    */     
/*  85: 82 */     return result;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public String doPayy(Expence expence, BankRunning bankRunning, User user)
/*  89:    */   {
/*  90: 86 */     String result = "";
/*  91: 87 */     Expence persistent = (Expence)this.expenceDao.load(expence.getId());
/*  92: 88 */     if (("3".equals(persistent.getStat())) || (persistent.getStat() == "3")) {
/*  93: 89 */       return result = "[id=" + persistent.getId() + ", " + persistent.getStat() + "]该项数据已经审批！";
/*  94:    */     }
/*  95: 92 */     ExpenceFlow expenceFlow = new ExpenceFlow();
/*  96: 93 */     expenceFlow.setGxsj(new Date());
/*  97: 94 */     expenceFlow.setExpence(expence);
/*  98: 95 */     expenceFlow.setUser(user);
/*  99: 96 */     expenceFlow.setOptContent("审批");
/* 100: 97 */     expenceFlow.setYwlc(Integer.valueOf(3));
/* 101: 98 */     this.expenceFlowDao.save(expenceFlow);
/* 102:    */     
/* 103:    */ 
/* 104:101 */     BeanUtils.copyProperties(expence, persistent, new String[] { "id", "user", "bxsj", "fssj", "bxnr", "bxje", "bxr", "skzh", "bxlx" });
/* 105:102 */     persistent.setGxr(user.getName());
/* 106:103 */     persistent.setGxsj(new Date());
/* 107:104 */     persistent.setStat("3");
/* 108:108 */     if (bankRunning != null)
/* 109:    */     {
/* 110:110 */       bankRunning.setZcje(Double.parseDouble(bankRunning.getFkje()));
/* 111:111 */       bankRunning.setLrje(0.0D - Double.parseDouble(bankRunning.getFkje()));
/* 112:112 */       bankRunning.setLrsj(new Date());
/* 113:113 */       bankRunning.setLrr(user.getName());
/* 114:114 */       bankRunning.setShbj("1");
/* 115:115 */       bankRunning.setOrderId(expenceFlow.getId().toString());
/* 116:116 */       bankRunning.setBz(expenceFlow.getOptContent());
/* 117:117 */       Integer b_r_id = (Integer)this.bankRunningService.save(bankRunning);
/* 118:    */       
/* 119:119 */       persistent.setBankRunningId(b_r_id);
/* 120:120 */       this.expenceDao.update(persistent);
/* 121:    */       
/* 122:122 */       Bank b = (Bank)this.bankService.get(bankRunning.getBank().getId());
/* 123:123 */       double cush = new Double(b.getCush() + bankRunning.getLrje()).doubleValue();
/* 124:124 */       this.bankService.updateBankCush(cush, Integer.valueOf(bankRunning.getBank().getId().intValue()));
/* 125:    */     }
/* 126:126 */     return result;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public String doCancel(Integer id, User user)
/* 130:    */   {
/* 131:130 */     String result = "";String returnStat = "0";
/* 132:    */     
/* 133:132 */     Expence expence = (Expence)this.expenceDao.get(Integer.valueOf(id.intValue()));
/* 134:133 */     if (("0".equals(expence.getStat())) || (expence.getStat() == "0") || ("1".equals(expence.getStat())) || (expence.getStat() == "1")) {
/* 135:134 */       return result = "[id=" + expence.getId() + "]该项数据已经回退！";
/* 136:    */     }
/* 137:138 */     ExpenceFlow expenceFlow = new ExpenceFlow();
/* 138:139 */     expenceFlow.setGxsj(new Date());
/* 139:140 */     expenceFlow.setExpence(expence);
/* 140:141 */     expenceFlow.setUser(user);
/* 141:142 */     expenceFlow.setOptContent("回退");
/* 142:143 */     expenceFlow.setYwlc(Integer.valueOf(0));
/* 143:144 */     this.expenceFlowDao.save(expenceFlow);
/* 144:146 */     if ((expence.getStat() == "3") || ("3".equals(expence.getStat())))
/* 145:    */     {
/* 146:147 */       if (expence.getBankRunningId() == null) {
/* 147:148 */         return result = "[flow_id=" + id + "]bankRunningId为空！";
/* 148:    */       }
/* 149:151 */       BankRunning bankRunning = (BankRunning)this.bankRunningService.get(Integer.valueOf(expence.getBankRunningId().intValue()));
/* 150:152 */       if (bankRunning == null) {
/* 151:153 */         return "银行流水id：" + expence.getBankRunningId();
/* 152:    */       }
/* 153:157 */       if ((bankRunning.getBank() != null) && (!"".equals(bankRunning.getBank().getId())))
/* 154:    */       {
/* 155:158 */         Bank b = (Bank)this.bankService.get(bankRunning.getBank().getId());
/* 156:159 */         double cush = new Double(b.getCush() - bankRunning.getLrje()).doubleValue();
/* 157:160 */         this.bankService.updateBankCush(cush, Integer.valueOf(bankRunning.getBank().getId().intValue()));
/* 158:161 */         this.bankRunningService.delete(expence.getBankRunningId());
/* 159:    */       }
/* 160:    */     }
/* 161:166 */     expence.setGxsj(new Date());
/* 162:167 */     expence.setStat(returnStat);
/* 163:168 */     this.expenceDao.update(expence);
/* 164:    */     
/* 165:170 */     return result;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public void update(Expence expence)
/* 169:    */   {
/* 170:174 */     this.expenceDao.update(expence);
/* 171:    */   }
/* 172:    */   
/* 173:    */   public Expence getExpenceIds(Integer id)
/* 174:    */   {
/* 175:178 */     return this.expenceDao.getExpenceIds(id);
/* 176:    */   }
/* 177:    */ }


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.service.impl.ExpenceServiceImpl
 * JD-Core Version:    0.7.0.1
 */