/*   1:    */ package com.chee.entity;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.Date;
/*   5:    */ import java.util.List;
/*   6:    */ import javax.persistence.Column;
/*   7:    */ import javax.persistence.Entity;
/*   8:    */ import javax.persistence.FetchType;
/*   9:    */ import javax.persistence.GeneratedValue;
/*  10:    */ import javax.persistence.GenerationType;
/*  11:    */ import javax.persistence.Id;
/*  12:    */ import javax.persistence.JoinColumn;
/*  13:    */ import javax.persistence.ManyToOne;
/*  14:    */ import javax.persistence.Table;
/*  15:    */ import javax.persistence.Transient;
/*  16:    */ import org.hibernate.annotations.GenericGenerator;
/*  17:    */ 
/*  18:    */ @Entity
/*  19:    */ @Table(name="chee_expence")
/*  20:    */ public class Expence
/*  21:    */   implements Serializable
/*  22:    */ {
/*  23:    */   private static final long serialVersionUID = 1L;
/*  24:    */   private Integer id;
/*  25:    */   private String stat;
/*  26:    */   private Department department;
/*  27:    */   private User user;
/*  28:    */   private Date bxsj;
/*  29:    */   private Date fssj;
/*  30:    */   private String bxnr;
/*  31:    */   private String bxje;
/*  32:    */   private String skzh;
/*  33:    */   private String bxr;
/*  34:    */   private Date gxsj;
/*  35:    */   private String gxr;
/*  36:    */   private String bxlx;
/*  37:    */   private Integer bankRunningId;
/*  38:    */   private String beginDate;
/*  39:    */   private String endDate;
/*  40:    */   private List<Integer> proUserIds;
/*  41:    */   
/*  42:    */   @Id
/*  43:    */   @GenericGenerator(name="paymentableGenerator", strategy="native")
/*  44:    */   @GeneratedValue(strategy=GenerationType.AUTO)
/*  45:    */   @Column(name="ID", precision=11, scale=0)
/*  46:    */   public Integer getId()
/*  47:    */   {
/*  48: 47 */     return this.id;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void setId(Integer id)
/*  52:    */   {
/*  53: 51 */     this.id = id;
/*  54:    */   }
/*  55:    */   
/*  56:    */   @Column(name="stat")
/*  57:    */   public String getStat()
/*  58:    */   {
/*  59: 56 */     return this.stat;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void setStat(String stat)
/*  63:    */   {
/*  64: 60 */     this.stat = stat;
/*  65:    */   }
/*  66:    */   
/*  67:    */   @ManyToOne(fetch=FetchType.LAZY, optional=true)
/*  68:    */   @JoinColumn(name="department_id", referencedColumnName="id")
/*  69:    */   public Department getDepartment()
/*  70:    */   {
/*  71: 66 */     return this.department;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void setDepartment(Department department)
/*  75:    */   {
/*  76: 70 */     this.department = department;
/*  77:    */   }
/*  78:    */   
/*  79:    */   @ManyToOne(fetch=FetchType.LAZY, optional=true)
/*  80:    */   @JoinColumn(name="user_id", referencedColumnName="id")
/*  81:    */   public User getUser()
/*  82:    */   {
/*  83: 76 */     return this.user;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void setUser(User user)
/*  87:    */   {
/*  88: 80 */     this.user = user;
/*  89:    */   }
/*  90:    */   
/*  91:    */   @Column(name="bxsj")
/*  92:    */   public Date getBxsj()
/*  93:    */   {
/*  94: 85 */     return this.bxsj;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void setBxsj(Date bxsj)
/*  98:    */   {
/*  99: 89 */     this.bxsj = bxsj;
/* 100:    */   }
/* 101:    */   
/* 102:    */   @Column(name="fssj")
/* 103:    */   public Date getFssj()
/* 104:    */   {
/* 105: 94 */     return this.fssj;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void setFssj(Date fssj)
/* 109:    */   {
/* 110: 98 */     this.fssj = fssj;
/* 111:    */   }
/* 112:    */   
/* 113:    */   @Column(name="bxnr")
/* 114:    */   public String getBxnr()
/* 115:    */   {
/* 116:103 */     return this.bxnr;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void setBxnr(String bxnr)
/* 120:    */   {
/* 121:107 */     this.bxnr = bxnr;
/* 122:    */   }
/* 123:    */   
/* 124:    */   @Column(name="bxje")
/* 125:    */   public String getBxje()
/* 126:    */   {
/* 127:112 */     return this.bxje;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void setBxje(String bxje)
/* 131:    */   {
/* 132:116 */     this.bxje = bxje;
/* 133:    */   }
/* 134:    */   
/* 135:    */   @Column(name="skzh")
/* 136:    */   public String getSkzh()
/* 137:    */   {
/* 138:121 */     return this.skzh;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void setSkzh(String skzh)
/* 142:    */   {
/* 143:125 */     this.skzh = skzh;
/* 144:    */   }
/* 145:    */   
/* 146:    */   @Column(name="bxr")
/* 147:    */   public String getBxr()
/* 148:    */   {
/* 149:130 */     return this.bxr;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void setBxr(String bxr)
/* 153:    */   {
/* 154:134 */     this.bxr = bxr;
/* 155:    */   }
/* 156:    */   
/* 157:    */   @Column(name="gxsj")
/* 158:    */   public Date getGxsj()
/* 159:    */   {
/* 160:139 */     return this.gxsj;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void setGxsj(Date gxsj)
/* 164:    */   {
/* 165:143 */     this.gxsj = gxsj;
/* 166:    */   }
/* 167:    */   
/* 168:    */   @Column(name="gxr")
/* 169:    */   public String getGxr()
/* 170:    */   {
/* 171:148 */     return this.gxr;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public void setGxr(String gxr)
/* 175:    */   {
/* 176:152 */     this.gxr = gxr;
/* 177:    */   }
/* 178:    */   
/* 179:    */   @Column(name="bxlx")
/* 180:    */   public String getBxlx()
/* 181:    */   {
/* 182:157 */     return this.bxlx;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public void setBxlx(String bxlx)
/* 186:    */   {
/* 187:161 */     this.bxlx = bxlx;
/* 188:    */   }
/* 189:    */   
/* 190:    */   @Column(name="bank_running_id")
/* 191:    */   public Integer getBankRunningId()
/* 192:    */   {
/* 193:166 */     return this.bankRunningId;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public void setBankRunningId(Integer bankRunningId)
/* 197:    */   {
/* 198:170 */     this.bankRunningId = bankRunningId;
/* 199:    */   }
/* 200:    */   
/* 201:    */   @Transient
/* 202:    */   public String getBeginDate()
/* 203:    */   {
/* 204:175 */     return this.beginDate;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public void setBeginDate(String beginDate)
/* 208:    */   {
/* 209:179 */     this.beginDate = beginDate;
/* 210:    */   }
/* 211:    */   
/* 212:    */   @Transient
/* 213:    */   public String getEndDate()
/* 214:    */   {
/* 215:184 */     return this.endDate;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public void setEndDate(String endDate)
/* 219:    */   {
/* 220:188 */     this.endDate = endDate;
/* 221:    */   }
/* 222:    */   
/* 223:    */   @Transient
/* 224:    */   public List<Integer> getProUserIds()
/* 225:    */   {
/* 226:193 */     return this.proUserIds;
/* 227:    */   }
/* 228:    */   
/* 229:    */   public void setProUserIds(List<Integer> proUserIds)
/* 230:    */   {
/* 231:197 */     this.proUserIds = proUserIds;
/* 232:    */   }
/* 233:    */   
/* 234:    */   public int hashCode()
/* 235:    */   {
/* 236:201 */     return this.id == null ? System.identityHashCode(this) : this.id.hashCode();
/* 237:    */   }
/* 238:    */   
/* 239:    */   public boolean equals(Object obj)
/* 240:    */   {
/* 241:205 */     if (this == obj) {
/* 242:206 */       return true;
/* 243:    */     }
/* 244:208 */     if (obj == null) {
/* 245:209 */       return false;
/* 246:    */     }
/* 247:211 */     if (getClass().getPackage() != obj.getClass().getPackage()) {
/* 248:212 */       return false;
/* 249:    */     }
/* 250:214 */     Expence other = (Expence)obj;
/* 251:215 */     if (this.id == null)
/* 252:    */     {
/* 253:216 */       if (other.getId() != null) {
/* 254:217 */         return false;
/* 255:    */       }
/* 256:    */     }
/* 257:218 */     else if (!this.id.equals(other.getId())) {
/* 258:219 */       return false;
/* 259:    */     }
/* 260:221 */     return true;
/* 261:    */   }
/* 262:    */ }


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.entity.Expence
 * JD-Core Version:    0.7.0.1
 */