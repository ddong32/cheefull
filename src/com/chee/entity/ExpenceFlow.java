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
/*  14:    */ import org.hibernate.annotations.GenericGenerator;
/*  15:    */ 
/*  16:    */ @Entity
/*  17:    */ @Table(name="chee_expence_flow")
/*  18:    */ public class ExpenceFlow
/*  19:    */   implements Serializable
/*  20:    */ {
/*  21:    */   private static final long serialVersionUID = 1L;
/*  22:    */   private Integer id;
/*  23:    */   private Date gxsj;
/*  24:    */   private User user;
/*  25:    */   private Expence expence;
/*  26:    */   private String optContent;
/*  27:    */   private Integer ywlc;
/*  28:    */   
/*  29:    */   @Id
/*  30:    */   @GenericGenerator(name="paymentableGenerator", strategy="native")
/*  31:    */   @GeneratedValue(strategy=GenerationType.AUTO)
/*  32:    */   @Column(name="ID", precision=11, scale=0)
/*  33:    */   public Integer getId()
/*  34:    */   {
/*  35: 34 */     return this.id;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public void setId(Integer id)
/*  39:    */   {
/*  40: 38 */     this.id = id;
/*  41:    */   }
/*  42:    */   
/*  43:    */   @Column(name="gxsj")
/*  44:    */   public Date getGxsj()
/*  45:    */   {
/*  46: 43 */     return this.gxsj;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void setGxsj(Date gxsj)
/*  50:    */   {
/*  51: 47 */     this.gxsj = gxsj;
/*  52:    */   }
/*  53:    */   
/*  54:    */   @Column(name="opt_content")
/*  55:    */   public String getOptContent()
/*  56:    */   {
/*  57: 52 */     return this.optContent;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void setOptContent(String optContent)
/*  61:    */   {
/*  62: 56 */     this.optContent = optContent;
/*  63:    */   }
/*  64:    */   
/*  65:    */   @Column(name="ywlc")
/*  66:    */   public Integer getYwlc()
/*  67:    */   {
/*  68: 61 */     return this.ywlc;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void setYwlc(Integer ywlc)
/*  72:    */   {
/*  73: 65 */     this.ywlc = ywlc;
/*  74:    */   }
/*  75:    */   
/*  76:    */   @ManyToOne(fetch=FetchType.LAZY, optional=true)
/*  77:    */   @JoinColumn(name="user_id", referencedColumnName="id")
/*  78:    */   public User getUser()
/*  79:    */   {
/*  80: 71 */     return this.user;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void setUser(User user)
/*  84:    */   {
/*  85: 75 */     this.user = user;
/*  86:    */   }
/*  87:    */   
/*  88:    */   @ManyToOne(fetch=FetchType.LAZY, optional=true)
/*  89:    */   @JoinColumn(name="expence_id", referencedColumnName="id")
/*  90:    */   public Expence getExpence()
/*  91:    */   {
/*  92: 81 */     return this.expence;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void setExpence(Expence expence)
/*  96:    */   {
/*  97: 85 */     this.expence = expence;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public int hashCode()
/* 101:    */   {
/* 102: 89 */     return this.id == null ? System.identityHashCode(this) : this.id.hashCode();
/* 103:    */   }
/* 104:    */   
/* 105:    */   public boolean equals(Object obj)
/* 106:    */   {
/* 107: 93 */     if (this == obj) {
/* 108: 94 */       return true;
/* 109:    */     }
/* 110: 96 */     if (obj == null) {
/* 111: 97 */       return false;
/* 112:    */     }
/* 113: 99 */     if (getClass().getPackage() != obj.getClass().getPackage()) {
/* 114:100 */       return false;
/* 115:    */     }
/* 116:102 */     ExpenceFlow other = (ExpenceFlow)obj;
/* 117:103 */     if (this.id == null)
/* 118:    */     {
/* 119:104 */       if (other.getId() != null) {
/* 120:105 */         return false;
/* 121:    */       }
/* 122:    */     }
/* 123:106 */     else if (!this.id.equals(other.getId())) {
/* 124:107 */       return false;
/* 125:    */     }
/* 126:109 */     return true;
/* 127:    */   }
/* 128:    */ }


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.entity.ExpenceFlow
 * JD-Core Version:    0.7.0.1
 */