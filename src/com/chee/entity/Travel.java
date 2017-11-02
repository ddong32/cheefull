/*  1:   */ package com.chee.entity;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import java.util.Date;
/*  5:   */ import javax.persistence.Column;
/*  6:   */ import javax.persistence.Entity;
/*  7:   */ import javax.persistence.GeneratedValue;
/*  8:   */ import javax.persistence.GenerationType;
/*  9:   */ import javax.persistence.Id;
/* 10:   */ import javax.persistence.Table;
/* 11:   */ import org.hibernate.annotations.GenericGenerator;
/* 12:   */ 
/* 13:   */ @Entity
/* 14:   */ @Table(name="chee_travel")
/* 15:   */ public class Travel
/* 16:   */   implements Serializable
/* 17:   */ {
/* 18:   */   private static final long serialVersionUID = -6078547904348733418L;
/* 19:   */   private Integer id;
/* 20:   */   private String line;
/* 21:   */   private String lrr;
/* 22:   */   private Date lrsj;
/* 23:   */   
/* 24:   */   @Id
/* 25:   */   @GenericGenerator(name="paymentableGenerator", strategy="native")
/* 26:   */   @GeneratedValue(strategy=GenerationType.AUTO)
/* 27:   */   @Column(name="ID", precision=11, scale=0)
/* 28:   */   public Integer getId()
/* 29:   */   {
/* 30:29 */     return this.id;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public void setId(Integer id)
/* 34:   */   {
/* 35:33 */     this.id = id;
/* 36:   */   }
/* 37:   */   
/* 38:   */   @Column(name="line")
/* 39:   */   public String getLine()
/* 40:   */   {
/* 41:38 */     return this.line;
/* 42:   */   }
/* 43:   */   
/* 44:   */   public void setLine(String line)
/* 45:   */   {
/* 46:42 */     this.line = line;
/* 47:   */   }
/* 48:   */   
/* 49:   */   @Column(name="lrr")
/* 50:   */   public String getLrr()
/* 51:   */   {
/* 52:47 */     return this.lrr;
/* 53:   */   }
/* 54:   */   
/* 55:   */   public void setLrr(String lrr)
/* 56:   */   {
/* 57:51 */     this.lrr = lrr;
/* 58:   */   }
/* 59:   */   
/* 60:   */   @Column(name="lrsj")
/* 61:   */   public Date getLrsj()
/* 62:   */   {
/* 63:56 */     return this.lrsj;
/* 64:   */   }
/* 65:   */   
/* 66:   */   public void setLrsj(Date lrsj)
/* 67:   */   {
/* 68:60 */     this.lrsj = lrsj;
/* 69:   */   }
/* 70:   */   
/* 71:   */   public int hashCode()
/* 72:   */   {
/* 73:64 */     return this.id == null ? System.identityHashCode(this) : this.id.hashCode();
/* 74:   */   }
/* 75:   */   
/* 76:   */   public boolean equals(Object obj)
/* 77:   */   {
/* 78:68 */     if (this == obj) {
/* 79:69 */       return true;
/* 80:   */     }
/* 81:71 */     if (obj == null) {
/* 82:72 */       return false;
/* 83:   */     }
/* 84:74 */     if (getClass().getPackage() != obj.getClass().getPackage()) {
/* 85:75 */       return false;
/* 86:   */     }
/* 87:77 */     Travel other = (Travel)obj;
/* 88:78 */     if (this.id == null)
/* 89:   */     {
/* 90:79 */       if (other.getId() != null) {
/* 91:80 */         return false;
/* 92:   */       }
/* 93:   */     }
/* 94:81 */     else if (!this.id.equals(other.getId())) {
/* 95:82 */       return false;
/* 96:   */     }
/* 97:84 */     return true;
/* 98:   */   }
/* 99:   */ }


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.entity.Travel
 * JD-Core Version:    0.7.0.1
 */