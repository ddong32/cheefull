/*   1:    */ package com.chee.entity;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.Date;
/*   5:    */ import java.util.Set;
/*   6:    */ import javax.persistence.Column;
/*   7:    */ import javax.persistence.Entity;
/*   8:    */ import javax.persistence.FetchType;
/*   9:    */ import javax.persistence.GeneratedValue;
/*  10:    */ import javax.persistence.GenerationType;
/*  11:    */ import javax.persistence.Id;
/*  12:    */ import javax.persistence.JoinColumn;
/*  13:    */ import javax.persistence.ManyToOne;
/*  14:    */ import javax.persistence.OneToMany;
/*  15:    */ import javax.persistence.OrderBy;
/*  16:    */ import javax.persistence.Table;
/*  17:    */ import org.hibernate.annotations.Fetch;
/*  18:    */ import org.hibernate.annotations.FetchMode;
/*  19:    */ import org.hibernate.annotations.GenericGenerator;
/*  20:    */ 
/*  21:    */ @Entity
/*  22:    */ @Table(name="chee_customer")
/*  23:    */ public class Customer
/*  24:    */   implements Serializable
/*  25:    */ {
/*  26:    */   private static final long serialVersionUID = 1L;
/*  27:    */   private Integer id;
/*  28:    */   private String areaName;
/*  29:    */   private String dwdz;
/*  30:    */   private String gsdh;
/*  31:    */   private String lxdh;
/*  32:    */   private String czh;
/*  33:    */   private String lxr;
/*  34:    */   private String wxh;
/*  35:    */   private String qq;
/*  36:    */   private String szdsf;
/*  37:    */   private String szdcs;
/*  38:    */   private String szdxf;
/*  39:    */   private Date lrsj;
/*  40:    */   private Date gxsj;
/*  41:    */   private Integer stat;
/*  42:    */   private Integer sort;
/*  43:    */   private String path;
/*  44:    */   private String pathName;
/*  45:    */   private Integer type;
/*  46:    */   private Integer grade;
/*  47:    */   private Customer parent;
/*  48:    */   private Set<Customer> children;
/*  49:    */   
/*  50:    */   @Id
/*  51:    */   @GenericGenerator(name="paymentableGenerator", strategy="native")
/*  52:    */   @GeneratedValue(strategy=GenerationType.AUTO)
/*  53:    */   @Column(name="id", precision=10, scale=0)
/*  54:    */   public Integer getId()
/*  55:    */   {
/*  56: 57 */     return this.id;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void setId(Integer id)
/*  60:    */   {
/*  61: 61 */     this.id = id;
/*  62:    */   }
/*  63:    */   
/*  64:    */   @Column(name="AREA_NAME")
/*  65:    */   public String getAreaName()
/*  66:    */   {
/*  67: 66 */     return this.areaName;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void setAreaName(String areaName)
/*  71:    */   {
/*  72: 70 */     this.areaName = areaName;
/*  73:    */   }
/*  74:    */   
/*  75:    */   @Column(name="dwdz")
/*  76:    */   public String getDwdz()
/*  77:    */   {
/*  78: 75 */     return this.dwdz;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void setDwdz(String dwdz)
/*  82:    */   {
/*  83: 79 */     this.dwdz = dwdz;
/*  84:    */   }
/*  85:    */   
/*  86:    */   @Column(name="lxdh")
/*  87:    */   public String getLxdh()
/*  88:    */   {
/*  89: 84 */     return this.lxdh;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void setLxdh(String lxdh)
/*  93:    */   {
/*  94: 88 */     this.lxdh = lxdh;
/*  95:    */   }
/*  96:    */   
/*  97:    */   @Column(name="lxr")
/*  98:    */   public String getLxr()
/*  99:    */   {
/* 100: 93 */     return this.lxr;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void setLxr(String lxr)
/* 104:    */   {
/* 105: 97 */     this.lxr = lxr;
/* 106:    */   }
/* 107:    */   
/* 108:    */   @Column(name="szdsf")
/* 109:    */   public String getSzdsf()
/* 110:    */   {
/* 111:102 */     return this.szdsf;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void setSzdsf(String szdsf)
/* 115:    */   {
/* 116:106 */     this.szdsf = szdsf;
/* 117:    */   }
/* 118:    */   
/* 119:    */   @Column(name="szdcs")
/* 120:    */   public String getSzdcs()
/* 121:    */   {
/* 122:111 */     return this.szdcs;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void setSzdcs(String szdcs)
/* 126:    */   {
/* 127:115 */     this.szdcs = szdcs;
/* 128:    */   }
/* 129:    */   
/* 130:    */   @Column(name="szdxf")
/* 131:    */   public String getSzdxf()
/* 132:    */   {
/* 133:120 */     return this.szdxf;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void setSzdxf(String szdxf)
/* 137:    */   {
/* 138:124 */     this.szdxf = szdxf;
/* 139:    */   }
/* 140:    */   
/* 141:    */   @Column(name="lrsj")
/* 142:    */   public Date getLrsj()
/* 143:    */   {
/* 144:129 */     return this.lrsj;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void setLrsj(Date lrsj)
/* 148:    */   {
/* 149:133 */     this.lrsj = lrsj;
/* 150:    */   }
/* 151:    */   
/* 152:    */   @Column(name="gxsj")
/* 153:    */   public Date getGxsj()
/* 154:    */   {
/* 155:138 */     return this.gxsj;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public void setGxsj(Date gxsj)
/* 159:    */   {
/* 160:142 */     this.gxsj = gxsj;
/* 161:    */   }
/* 162:    */   
/* 163:    */   @Column(name="czh")
/* 164:    */   public String getCzh()
/* 165:    */   {
/* 166:147 */     return this.czh;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public void setCzh(String czh)
/* 170:    */   {
/* 171:151 */     this.czh = czh;
/* 172:    */   }
/* 173:    */   
/* 174:    */   @Column(name="qq")
/* 175:    */   public String getQq()
/* 176:    */   {
/* 177:156 */     return this.qq;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public void setQq(String qq)
/* 181:    */   {
/* 182:160 */     this.qq = qq;
/* 183:    */   }
/* 184:    */   
/* 185:    */   @Column(name="gsdh")
/* 186:    */   public String getGsdh()
/* 187:    */   {
/* 188:175 */     return this.gsdh;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public void setGsdh(String gsdh)
/* 192:    */   {
/* 193:179 */     this.gsdh = gsdh;
/* 194:    */   }
/* 195:    */   
/* 196:    */   @Column(name="wxh")
/* 197:    */   public String getWxh()
/* 198:    */   {
/* 199:184 */     return this.wxh;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public void setWxh(String wxh)
/* 203:    */   {
/* 204:188 */     this.wxh = wxh;
/* 205:    */   }
/* 206:    */   
/* 207:    */   @Column(name="stat")
/* 208:    */   public Integer getStat()
/* 209:    */   {
/* 210:193 */     return this.stat;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public void setStat(Integer stat)
/* 214:    */   {
/* 215:197 */     this.stat = stat;
/* 216:    */   }
/* 217:    */   
/* 218:    */   @Column(name="sort")
/* 219:    */   public Integer getSort()
/* 220:    */   {
/* 221:202 */     return this.sort;
/* 222:    */   }
/* 223:    */   
/* 224:    */   public void setSort(Integer sort)
/* 225:    */   {
/* 226:206 */     this.sort = sort;
/* 227:    */   }
/* 228:    */   
/* 229:    */   @Column(name="path")
/* 230:    */   public String getPath()
/* 231:    */   {
/* 232:211 */     return this.path;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public void setPath(String path)
/* 236:    */   {
/* 237:215 */     this.path = path;
/* 238:    */   }
/* 239:    */   
/* 240:    */   @Column(name="path_name")
/* 241:    */   public String getPathName()
/* 242:    */   {
/* 243:220 */     return this.pathName;
/* 244:    */   }
/* 245:    */   
/* 246:    */   public void setPathName(String pathName)
/* 247:    */   {
/* 248:224 */     this.pathName = pathName;
/* 249:    */   }
/* 250:    */   
/* 251:    */   @Column(name="type")
/* 252:    */   public Integer getType()
/* 253:    */   {
/* 254:229 */     return this.type;
/* 255:    */   }
/* 256:    */   
/* 257:    */   public void setType(Integer type)
/* 258:    */   {
/* 259:233 */     this.type = type;
/* 260:    */   }
/* 261:    */   
/* 262:    */   @Column(name="grade")
/* 263:    */   public Integer getGrade()
/* 264:    */   {
/* 265:238 */     return this.grade;
/* 266:    */   }
/* 267:    */   
/* 268:    */   public void setGrade(Integer grade)
/* 269:    */   {
/* 270:242 */     this.grade = grade;
/* 271:    */   }
/* 272:    */   
/* 273:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 274:    */   @JoinColumn(name="parent_id", referencedColumnName="id")
/* 275:    */   public Customer getParent()
/* 276:    */   {
/* 277:248 */     return this.parent;
/* 278:    */   }
/* 279:    */   
/* 280:    */   public void setParent(Customer parent)
/* 281:    */   {
/* 282:252 */     this.parent = parent;
/* 283:    */   }
/* 284:    */   
/* 285:    */   @OneToMany(mappedBy="parent", fetch=FetchType.LAZY)
/* 286:    */   @Fetch(FetchMode.SUBSELECT)
/* 287:    */   @OrderBy("sort asc")
/* 288:    */   public Set<Customer> getChildren()
/* 289:    */   {
/* 290:259 */     return this.children;
/* 291:    */   }
/* 292:    */   
/* 293:    */   public void setChildren(Set<Customer> children)
/* 294:    */   {
/* 295:263 */     this.children = children;
/* 296:    */   }
/* 297:    */   
/* 298:    */   public int hashCode()
/* 299:    */   {
/* 300:267 */     return this.id == null ? System.identityHashCode(this) : this.id.hashCode();
/* 301:    */   }
/* 302:    */   
/* 303:    */   public boolean equals(Object obj)
/* 304:    */   {
/* 305:271 */     if (this == obj) {
/* 306:272 */       return true;
/* 307:    */     }
/* 308:274 */     if (obj == null) {
/* 309:275 */       return false;
/* 310:    */     }
/* 311:277 */     if (getClass().getPackage() != obj.getClass().getPackage()) {
/* 312:278 */       return false;
/* 313:    */     }
/* 314:280 */     Customer other = (Customer)obj;
/* 315:281 */     if (this.id == null)
/* 316:    */     {
/* 317:282 */       if (other.getId() != null) {
/* 318:283 */         return false;
/* 319:    */       }
/* 320:    */     }
/* 321:284 */     else if (!this.id.equals(other.getId())) {
/* 322:285 */       return false;
/* 323:    */     }
/* 324:287 */     return true;
/* 325:    */   }
/* 326:    */ }


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.entity.Customer
 * JD-Core Version:    0.7.0.1
 */