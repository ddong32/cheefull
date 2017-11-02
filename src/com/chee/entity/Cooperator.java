/*   1:    */ package com.chee.entity;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.Date;
/*   5:    */ import javax.persistence.Column;
/*   6:    */ import javax.persistence.Entity;
/*   7:    */ import javax.persistence.GeneratedValue;
/*   8:    */ import javax.persistence.GenerationType;
/*   9:    */ import javax.persistence.Id;
/*  10:    */ import javax.persistence.Table;
/*  11:    */ import org.hibernate.annotations.GenericGenerator;
/*  12:    */ 
/*  13:    */ @Entity
/*  14:    */ @Table(name="chee_cooperator")
/*  15:    */ public class Cooperator
/*  16:    */   implements Serializable
/*  17:    */ {
/*  18:    */   private static final long serialVersionUID = 1L;
/*  19:    */   private Integer id;
/*  20:    */   private String dwmc;
/*  21:    */   private String lxdh;
/*  22:    */   private String lxr;
/*  23:    */   private String lrr;
/*  24:    */   private String emeil;
/*  25:    */   private String khx;
/*  26:    */   private String yhhm;
/*  27:    */   private String yhzh;
/*  28:    */   private Date lrsj;
/*  29:    */   private Date gxsj;
/*  30:    */   private String type;
/*  31:    */   private Integer sort;
/*  32:    */   
/*  33:    */   @Id
/*  34:    */   @GenericGenerator(name="paymentableGenerator", strategy="native")
/*  35:    */   @GeneratedValue(strategy=GenerationType.AUTO)
/*  36:    */   @Column(name="ID", precision=11, scale=0)
/*  37:    */   public Integer getId()
/*  38:    */   {
/*  39: 37 */     return this.id;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void setId(Integer id)
/*  43:    */   {
/*  44: 41 */     this.id = id;
/*  45:    */   }
/*  46:    */   
/*  47:    */   @Column(name="dwmc", length=300)
/*  48:    */   public String getDwmc()
/*  49:    */   {
/*  50: 46 */     return this.dwmc;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void setDwmc(String dwmc)
/*  54:    */   {
/*  55: 50 */     this.dwmc = dwmc;
/*  56:    */   }
/*  57:    */   
/*  58:    */   @Column(name="lrr")
/*  59:    */   public String getLrr()
/*  60:    */   {
/*  61: 55 */     return this.lrr;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void setLrr(String lrr)
/*  65:    */   {
/*  66: 59 */     this.lrr = lrr;
/*  67:    */   }
/*  68:    */   
/*  69:    */   @Column(name="lxdh", length=100)
/*  70:    */   public String getLxdh()
/*  71:    */   {
/*  72: 64 */     return this.lxdh;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void setLxdh(String lxdh)
/*  76:    */   {
/*  77: 68 */     this.lxdh = lxdh;
/*  78:    */   }
/*  79:    */   
/*  80:    */   @Column(name="yhzh")
/*  81:    */   public String getYhzh()
/*  82:    */   {
/*  83: 73 */     return this.yhzh;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void setYhzh(String yhzh)
/*  87:    */   {
/*  88: 77 */     this.yhzh = yhzh;
/*  89:    */   }
/*  90:    */   
/*  91:    */   @Column(name="lxr")
/*  92:    */   public String getLxr()
/*  93:    */   {
/*  94: 82 */     return this.lxr;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void setLxr(String lxr)
/*  98:    */   {
/*  99: 86 */     this.lxr = lxr;
/* 100:    */   }
/* 101:    */   
/* 102:    */   @Column(name="emeil", length=30)
/* 103:    */   public String getEmeil()
/* 104:    */   {
/* 105: 91 */     return this.emeil;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void setEmeil(String emeil)
/* 109:    */   {
/* 110: 95 */     this.emeil = emeil;
/* 111:    */   }
/* 112:    */   
/* 113:    */   @Column(name="khx", length=100)
/* 114:    */   public String getKhx()
/* 115:    */   {
/* 116:100 */     return this.khx;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void setKhx(String khx)
/* 120:    */   {
/* 121:104 */     this.khx = khx;
/* 122:    */   }
/* 123:    */   
/* 124:    */   @Column(name="lrsj")
/* 125:    */   public Date getLrsj()
/* 126:    */   {
/* 127:109 */     return this.lrsj;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void setLrsj(Date lrsj)
/* 131:    */   {
/* 132:113 */     this.lrsj = lrsj;
/* 133:    */   }
/* 134:    */   
/* 135:    */   @Column(name="gxsj")
/* 136:    */   public Date getGxsj()
/* 137:    */   {
/* 138:118 */     return this.gxsj;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void setGxsj(Date gxsj)
/* 142:    */   {
/* 143:122 */     this.gxsj = gxsj;
/* 144:    */   }
/* 145:    */   
/* 146:    */   @Column(name="yhhm")
/* 147:    */   public String getYhhm()
/* 148:    */   {
/* 149:127 */     return this.yhhm;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void setYhhm(String yhhm)
/* 153:    */   {
/* 154:131 */     this.yhhm = yhhm;
/* 155:    */   }
/* 156:    */   
/* 157:    */   @Column(name="type")
/* 158:    */   public String getType()
/* 159:    */   {
/* 160:136 */     return this.type;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void setType(String type)
/* 164:    */   {
/* 165:140 */     this.type = type;
/* 166:    */   }
/* 167:    */   
/* 168:    */   @Column(name="sort")
/* 169:    */   public Integer getSort()
/* 170:    */   {
/* 171:145 */     return this.sort;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public void setSort(Integer sort)
/* 175:    */   {
/* 176:149 */     this.sort = sort;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public int hashCode()
/* 180:    */   {
/* 181:153 */     return this.id == null ? System.identityHashCode(this) : this.id.hashCode();
/* 182:    */   }
/* 183:    */   
/* 184:    */   public boolean equals(Object obj)
/* 185:    */   {
/* 186:157 */     if (this == obj) {
/* 187:158 */       return true;
/* 188:    */     }
/* 189:160 */     if (obj == null) {
/* 190:161 */       return false;
/* 191:    */     }
/* 192:163 */     if (getClass().getPackage() != obj.getClass().getPackage()) {
/* 193:164 */       return false;
/* 194:    */     }
/* 195:166 */     Cooperator other = (Cooperator)obj;
/* 196:167 */     if (this.id == null)
/* 197:    */     {
/* 198:168 */       if (other.getId() != null) {
/* 199:169 */         return false;
/* 200:    */       }
/* 201:    */     }
/* 202:170 */     else if (!this.id.equals(other.getId())) {
/* 203:171 */       return false;
/* 204:    */     }
/* 205:173 */     return true;
/* 206:    */   }
/* 207:    */ }


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.entity.Cooperator
 * JD-Core Version:    0.7.0.1
 */