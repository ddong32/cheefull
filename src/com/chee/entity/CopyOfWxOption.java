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
/*  14:    */ @Table(name="chwx_option")
/*  15:    */ public class CopyOfWxOption
/*  16:    */   implements Serializable
/*  17:    */ {
/*  18:    */   private static final long serialVersionUID = 1L;
/*  19:    */   private Integer id;
/*  20:    */   private String appid;
/*  21:    */   private String appsecret;
/*  22:    */   private String token;
/*  23:    */   private Date lrsj;
/*  24:    */   private Date gxsj;
/*  25:    */   
/*  26:    */   @Id
/*  27:    */   @GenericGenerator(name="paymentableGenerator", strategy="native")
/*  28:    */   @GeneratedValue(strategy=GenerationType.AUTO)
/*  29:    */   @Column(name="ID", precision=10, scale=0)
/*  30:    */   public Integer getId()
/*  31:    */   {
/*  32: 31 */     return this.id;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public void setId(Integer id)
/*  36:    */   {
/*  37: 35 */     this.id = id;
/*  38:    */   }
/*  39:    */   
/*  40:    */   @Column(name="appid")
/*  41:    */   public String getAppid()
/*  42:    */   {
/*  43: 40 */     return this.appid;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void setAppid(String appid)
/*  47:    */   {
/*  48: 44 */     this.appid = appid;
/*  49:    */   }
/*  50:    */   
/*  51:    */   @Column(name="appsecret")
/*  52:    */   public String getAppsecret()
/*  53:    */   {
/*  54: 49 */     return this.appsecret;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void setAppsecret(String appsecret)
/*  58:    */   {
/*  59: 53 */     this.appsecret = appsecret;
/*  60:    */   }
/*  61:    */   
/*  62:    */   @Column(name="token")
/*  63:    */   public String getToken()
/*  64:    */   {
/*  65: 58 */     return this.token;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void setToken(String token)
/*  69:    */   {
/*  70: 62 */     this.token = token;
/*  71:    */   }
/*  72:    */   
/*  73:    */   @Column(name="lrsj")
/*  74:    */   public Date getLrsj()
/*  75:    */   {
/*  76: 67 */     return this.lrsj;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void setLrsj(Date lrsj)
/*  80:    */   {
/*  81: 71 */     this.lrsj = lrsj;
/*  82:    */   }
/*  83:    */   
/*  84:    */   @Column(name="gxsj")
/*  85:    */   public Date getGxsj()
/*  86:    */   {
/*  87: 76 */     return this.gxsj;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void setGxsj(Date gxsj)
/*  91:    */   {
/*  92: 80 */     this.gxsj = gxsj;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public int hashCode()
/*  96:    */   {
/*  97: 84 */     return this.id == null ? System.identityHashCode(this) : this.id.hashCode();
/*  98:    */   }
/*  99:    */   
/* 100:    */   public boolean equals(Object obj)
/* 101:    */   {
/* 102: 88 */     if (this == obj) {
/* 103: 89 */       return true;
/* 104:    */     }
/* 105: 91 */     if (obj == null) {
/* 106: 92 */       return false;
/* 107:    */     }
/* 108: 94 */     if (getClass().getPackage() != obj.getClass().getPackage()) {
/* 109: 95 */       return false;
/* 110:    */     }
/* 111: 97 */     CopyOfWxOption other = (CopyOfWxOption)obj;
/* 112: 98 */     if (this.id == null)
/* 113:    */     {
/* 114: 99 */       if (other.getId() != null) {
/* 115:100 */         return false;
/* 116:    */       }
/* 117:    */     }
/* 118:101 */     else if (!this.id.equals(other.getId())) {
/* 119:102 */       return false;
/* 120:    */     }
/* 121:104 */     return true;
/* 122:    */   }
/* 123:    */ }


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.entity.CopyOfWxOption
 * JD-Core Version:    0.7.0.1
 */