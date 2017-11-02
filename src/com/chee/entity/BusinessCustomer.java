/*   1:    */ package com.chee.entity;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.Date;
/*   5:    */ import javax.persistence.Column;
/*   6:    */ import javax.persistence.Entity;
/*   7:    */ import javax.persistence.FetchType;
/*   8:    */ import javax.persistence.GeneratedValue;
/*   9:    */ import javax.persistence.GenerationType;
/*  10:    */ import javax.persistence.Id;
/*  11:    */ import javax.persistence.JoinColumn;
/*  12:    */ import javax.persistence.ManyToOne;
/*  13:    */ import javax.persistence.Table;
/*  14:    */ import javax.persistence.Transient;
/*  15:    */ import org.hibernate.annotations.GenericGenerator;
/*  16:    */ 
/*  17:    */ @Entity
/*  18:    */ @Table(name="chee_business_customer")
/*  19:    */ public class BusinessCustomer
/*  20:    */   implements Serializable
/*  21:    */ {
/*  22:    */   private static final long serialVersionUID = 1L;
/*  23:    */   private Integer id;
/*  24:    */   private String orderId;
/*  25:    */   private String customerDwmc;
/*  26:    */   private Integer parentId;
/*  27:    */   private String adultNo;
/*  28:    */   private String childNo;
/*  29:    */   private String escortNo;
/*  30:    */   private double ygs;
/*  31:    */   private double yjs;
/*  32:    */   private double crj;
/*  33:    */   private double etj;
/*  34:    */   private double ptj;
/*  35:    */   private Date lrsj;
/*  36:    */   private Date gxsj;
/*  37:    */   private String lrr;
/*  38:    */   private String bz;
/*  39:    */   private Customer customer;
/*  40:    */   private Business business;
/*  41:    */   private String beginDate;
/*  42:    */   private String endDate;
/*  43:    */   private String parent_id;
/*  44:    */   private String type;
/*  45:    */   private String uid;
/*  46:    */   
/*  47:    */   @Id
/*  48:    */   @GenericGenerator(name="paymentableGenerator", strategy="native")
/*  49:    */   @GeneratedValue(strategy=GenerationType.AUTO)
/*  50:    */   @Column(name="id", precision=11, scale=0)
/*  51:    */   public Integer getId()
/*  52:    */   {
/*  53: 55 */     return this.id;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void setId(Integer id)
/*  57:    */   {
/*  58: 59 */     this.id = id;
/*  59:    */   }
/*  60:    */   
/*  61:    */   @Column(name="order_id")
/*  62:    */   public String getOrderId()
/*  63:    */   {
/*  64: 64 */     return this.orderId;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void setOrderId(String orderId)
/*  68:    */   {
/*  69: 68 */     this.orderId = orderId;
/*  70:    */   }
/*  71:    */   
/*  72:    */   @Column(name="customer_dwmc")
/*  73:    */   public String getCustomerDwmc()
/*  74:    */   {
/*  75: 73 */     return this.customerDwmc;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void setCustomerDwmc(String customerDwmc)
/*  79:    */   {
/*  80: 77 */     this.customerDwmc = customerDwmc;
/*  81:    */   }
/*  82:    */   
/*  83:    */   @Column(name="parent_id")
/*  84:    */   public Integer getParentId()
/*  85:    */   {
/*  86: 82 */     return this.parentId;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void setParentId(Integer parentId)
/*  90:    */   {
/*  91: 86 */     this.parentId = parentId;
/*  92:    */   }
/*  93:    */   
/*  94:    */   @Column(name="adult_no")
/*  95:    */   public String getAdultNo()
/*  96:    */   {
/*  97: 91 */     return this.adultNo;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void setAdultNo(String adultNo)
/* 101:    */   {
/* 102: 95 */     this.adultNo = adultNo;
/* 103:    */   }
/* 104:    */   
/* 105:    */   @Column(name="child_no")
/* 106:    */   public String getChildNo()
/* 107:    */   {
/* 108:100 */     return this.childNo;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void setChildNo(String childNo)
/* 112:    */   {
/* 113:104 */     this.childNo = childNo;
/* 114:    */   }
/* 115:    */   
/* 116:    */   @Column(name="escort_no")
/* 117:    */   public String getEscortNo()
/* 118:    */   {
/* 119:109 */     return this.escortNo;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void setEscortNo(String escortNo)
/* 123:    */   {
/* 124:113 */     this.escortNo = escortNo;
/* 125:    */   }
/* 126:    */   
/* 127:    */   @Column(name="ygs")
/* 128:    */   public double getYgs()
/* 129:    */   {
/* 130:118 */     return this.ygs;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void setYgs(double ygs)
/* 134:    */   {
/* 135:122 */     this.ygs = ygs;
/* 136:    */   }
/* 137:    */   
/* 138:    */   @Column(name="yjs")
/* 139:    */   public double getYjs()
/* 140:    */   {
/* 141:127 */     return this.yjs;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public void setYjs(double yjs)
/* 145:    */   {
/* 146:131 */     this.yjs = yjs;
/* 147:    */   }
/* 148:    */   
/* 149:    */   @Column(name="crj")
/* 150:    */   public double getCrj()
/* 151:    */   {
/* 152:136 */     return this.crj;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void setCrj(double crj)
/* 156:    */   {
/* 157:140 */     this.crj = crj;
/* 158:    */   }
/* 159:    */   
/* 160:    */   @Column(name="etj")
/* 161:    */   public double getEtj()
/* 162:    */   {
/* 163:145 */     return this.etj;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public void setEtj(double etj)
/* 167:    */   {
/* 168:149 */     this.etj = etj;
/* 169:    */   }
/* 170:    */   
/* 171:    */   @Column(name="ptj")
/* 172:    */   public double getPtj()
/* 173:    */   {
/* 174:154 */     return this.ptj;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public void setPtj(double ptj)
/* 178:    */   {
/* 179:158 */     this.ptj = ptj;
/* 180:    */   }
/* 181:    */   
/* 182:    */   @Column(name="lrr")
/* 183:    */   public String getLrr()
/* 184:    */   {
/* 185:163 */     return this.lrr;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public void setLrr(String lrr)
/* 189:    */   {
/* 190:167 */     this.lrr = lrr;
/* 191:    */   }
/* 192:    */   
/* 193:    */   @Column(name="lrsj")
/* 194:    */   public Date getLrsj()
/* 195:    */   {
/* 196:172 */     return this.lrsj;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public void setLrsj(Date lrsj)
/* 200:    */   {
/* 201:176 */     this.lrsj = lrsj;
/* 202:    */   }
/* 203:    */   
/* 204:    */   @Column(name="gxsj")
/* 205:    */   public Date getGxsj()
/* 206:    */   {
/* 207:181 */     return this.gxsj;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public void setGxsj(Date gxsj)
/* 211:    */   {
/* 212:185 */     this.gxsj = gxsj;
/* 213:    */   }
/* 214:    */   
/* 215:    */   @Column(name="bz")
/* 216:    */   public String getBz()
/* 217:    */   {
/* 218:190 */     return this.bz;
/* 219:    */   }
/* 220:    */   
/* 221:    */   public void setBz(String bz)
/* 222:    */   {
/* 223:194 */     this.bz = bz;
/* 224:    */   }
/* 225:    */   
/* 226:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 227:    */   @JoinColumn(name="customer_id", referencedColumnName="id")
/* 228:    */   public Customer getCustomer()
/* 229:    */   {
/* 230:200 */     return this.customer;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public void setCustomer(Customer customer)
/* 234:    */   {
/* 235:204 */     this.customer = customer;
/* 236:    */   }
/* 237:    */   
/* 238:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 239:    */   @JoinColumn(name="business_id", referencedColumnName="id")
/* 240:    */   public Business getBusiness()
/* 241:    */   {
/* 242:210 */     return this.business;
/* 243:    */   }
/* 244:    */   
/* 245:    */   public void setBusiness(Business business)
/* 246:    */   {
/* 247:214 */     this.business = business;
/* 248:    */   }
/* 249:    */   
/* 250:    */   @Transient
/* 251:    */   public String getBeginDate()
/* 252:    */   {
/* 253:219 */     return this.beginDate;
/* 254:    */   }
/* 255:    */   
/* 256:    */   public void setBeginDate(String beginDate)
/* 257:    */   {
/* 258:223 */     this.beginDate = beginDate;
/* 259:    */   }
/* 260:    */   
/* 261:    */   @Transient
/* 262:    */   public String getEndDate()
/* 263:    */   {
/* 264:228 */     return this.endDate;
/* 265:    */   }
/* 266:    */   
/* 267:    */   public void setEndDate(String endDate)
/* 268:    */   {
/* 269:232 */     this.endDate = endDate;
/* 270:    */   }
/* 271:    */   
/* 272:    */   @Transient
/* 273:    */   public String getParent_id()
/* 274:    */   {
/* 275:237 */     return this.parent_id;
/* 276:    */   }
/* 277:    */   
/* 278:    */   public void setParent_id(String parent_id)
/* 279:    */   {
/* 280:241 */     this.parent_id = parent_id;
/* 281:    */   }
/* 282:    */   
/* 283:    */   @Transient
/* 284:    */   public String getType()
/* 285:    */   {
/* 286:246 */     return this.type;
/* 287:    */   }
/* 288:    */   
/* 289:    */   public void setType(String type)
/* 290:    */   {
/* 291:250 */     this.type = type;
/* 292:    */   }
/* 293:    */   
/* 294:    */   @Transient
/* 295:    */   public String getUid()
/* 296:    */   {
/* 297:255 */     return this.uid;
/* 298:    */   }
/* 299:    */   
/* 300:    */   public void setUid(String uid)
/* 301:    */   {
/* 302:259 */     this.uid = uid;
/* 303:    */   }
/* 304:    */   
/* 305:    */   public int hashCode()
/* 306:    */   {
/* 307:263 */     return this.id == null ? System.identityHashCode(this) : this.id.hashCode();
/* 308:    */   }
/* 309:    */   
/* 310:    */   public boolean equals(Object obj)
/* 311:    */   {
/* 312:267 */     if (this == obj) {
/* 313:268 */       return true;
/* 314:    */     }
/* 315:270 */     if (obj == null) {
/* 316:271 */       return false;
/* 317:    */     }
/* 318:273 */     if (getClass().getPackage() != obj.getClass().getPackage()) {
/* 319:274 */       return false;
/* 320:    */     }
/* 321:276 */     BusinessCustomer other = (BusinessCustomer)obj;
/* 322:277 */     if (this.id == null)
/* 323:    */     {
/* 324:278 */       if (other.getId() != null) {
/* 325:279 */         return false;
/* 326:    */       }
/* 327:    */     }
/* 328:280 */     else if (!this.id.equals(other.getId())) {
/* 329:281 */       return false;
/* 330:    */     }
/* 331:283 */     return true;
/* 332:    */   }
/* 333:    */ }


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.entity.BusinessCustomer
 * JD-Core Version:    0.7.0.1
 */