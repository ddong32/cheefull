/*  1:   */ package com.chee.entity;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import javax.persistence.Column;
/*  5:   */ import javax.persistence.Entity;
/*  6:   */ import javax.persistence.GeneratedValue;
/*  7:   */ import javax.persistence.GenerationType;
/*  8:   */ import javax.persistence.Id;
/*  9:   */ import javax.persistence.Table;
/* 10:   */ import org.hibernate.annotations.GenericGenerator;
/* 11:   */ 
/* 12:   */ @Entity
/* 13:   */ @Table(name="chwx_option")
/* 14:   */ public class WxOption
/* 15:   */   implements Serializable
/* 16:   */ {
/* 17:   */   private static final long serialVersionUID = 1L;
/* 18:   */   private Integer id;
/* 19:   */   private String option_key;
/* 20:   */   private String option_value;
/* 21:   */   public static final String KEY_WEB_NAME = "web_name";
/* 22:   */   public static final String KEY_TEMPLATE_ID = "web_template_id";
/* 23:   */   
/* 24:   */   @Id
/* 25:   */   @GenericGenerator(name="paymentableGenerator", strategy="native")
/* 26:   */   @GeneratedValue(strategy=GenerationType.AUTO)
/* 27:   */   @Column(name="ID", precision=10, scale=0)
/* 28:   */   public Integer getId()
/* 29:   */   {
/* 30:30 */     return this.id;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public void setId(Integer id)
/* 34:   */   {
/* 35:34 */     this.id = id;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public String getOption_key()
/* 39:   */   {
/* 40:38 */     return this.option_key;
/* 41:   */   }
/* 42:   */   
/* 43:   */   public void setOption_key(String option_key)
/* 44:   */   {
/* 45:42 */     this.option_key = option_key;
/* 46:   */   }
/* 47:   */   
/* 48:   */   public String getOption_value()
/* 49:   */   {
/* 50:46 */     return this.option_value;
/* 51:   */   }
/* 52:   */   
/* 53:   */   public void setOption_value(String option_value)
/* 54:   */   {
/* 55:50 */     this.option_value = option_value;
/* 56:   */   }
/* 57:   */   
/* 58:   */   public int hashCode()
/* 59:   */   {
/* 60:54 */     return this.id == null ? System.identityHashCode(this) : this.id.hashCode();
/* 61:   */   }
/* 62:   */   
/* 63:   */   public boolean equals(Object obj)
/* 64:   */   {
/* 65:58 */     if (this == obj) {
/* 66:59 */       return true;
/* 67:   */     }
/* 68:61 */     if (obj == null) {
/* 69:62 */       return false;
/* 70:   */     }
/* 71:64 */     if (getClass().getPackage() != obj.getClass().getPackage()) {
/* 72:65 */       return false;
/* 73:   */     }
/* 74:67 */     WxOption other = (WxOption)obj;
/* 75:68 */     if (this.id == null)
/* 76:   */     {
/* 77:69 */       if (other.getId() != null) {
/* 78:70 */         return false;
/* 79:   */       }
/* 80:   */     }
/* 81:71 */     else if (!this.id.equals(other.getId())) {
/* 82:72 */       return false;
/* 83:   */     }
/* 84:74 */     return true;
/* 85:   */   }
/* 86:   */ }


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.entity.WxOption
 * JD-Core Version:    0.7.0.1
 */