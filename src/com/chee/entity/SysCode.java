/*   1:    */ package com.chee.entity;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import javax.persistence.Column;
/*   5:    */ import javax.persistence.Entity;
/*   6:    */ import javax.persistence.GeneratedValue;
/*   7:    */ import javax.persistence.GenerationType;
/*   8:    */ import javax.persistence.Id;
/*   9:    */ import javax.persistence.Table;
/*  10:    */ import org.hibernate.annotations.GenericGenerator;
/*  11:    */ 
/*  12:    */ @Entity
/*  13:    */ @Table(name="chee_syscode", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"CODE_CODE", "CODE_TYPE"})})
/*  14:    */ public class SysCode
/*  15:    */   implements Serializable
/*  16:    */ {
/*  17:    */   private static final long serialVersionUID = -6078547904348733418L;
/*  18:    */   private Integer id;
/*  19:    */   private Integer codeId;
/*  20:    */   private String codeCode;
/*  21:    */   private String codeName;
/*  22:    */   private Integer codeType;
/*  23:    */   private String codeTypeName;
/*  24:    */   private Integer codeOrder;
/*  25:    */   private Integer stat;
/*  26:    */   
/*  27:    */   @Id
/*  28:    */   @GenericGenerator(name="paymentableGenerator", strategy="native")
/*  29:    */   @GeneratedValue(strategy=GenerationType.AUTO)
/*  30:    */   @Column(name="ID", precision=11, scale=0)
/*  31:    */   public Integer getId()
/*  32:    */   {
/*  33: 31 */     return this.id;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void setId(Integer id)
/*  37:    */   {
/*  38: 35 */     this.id = id;
/*  39:    */   }
/*  40:    */   
/*  41:    */   @Column(name="CODE_ID", precision=5, scale=0)
/*  42:    */   public Integer getCodeId()
/*  43:    */   {
/*  44: 40 */     return this.codeId;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void setCodeId(Integer codeId)
/*  48:    */   {
/*  49: 44 */     this.codeId = codeId;
/*  50:    */   }
/*  51:    */   
/*  52:    */   @Column(name="CODE_CODE", length=50)
/*  53:    */   public String getCodeCode()
/*  54:    */   {
/*  55: 49 */     return this.codeCode;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void setCodeCode(String codeCode)
/*  59:    */   {
/*  60: 53 */     this.codeCode = codeCode;
/*  61:    */   }
/*  62:    */   
/*  63:    */   @Column(name="CODE_NAME", nullable=false, length=1000)
/*  64:    */   public String getCodeName()
/*  65:    */   {
/*  66: 58 */     return this.codeName;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void setCodeName(String codeName)
/*  70:    */   {
/*  71: 62 */     this.codeName = codeName;
/*  72:    */   }
/*  73:    */   
/*  74:    */   @Column(name="CODE_TYPE", nullable=false, precision=5, scale=0)
/*  75:    */   public Integer getCodeType()
/*  76:    */   {
/*  77: 67 */     return this.codeType;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void setCodeType(Integer codeType)
/*  81:    */   {
/*  82: 71 */     this.codeType = codeType;
/*  83:    */   }
/*  84:    */   
/*  85:    */   @Column(name="CODE_TYPE_NAME", length=50)
/*  86:    */   public String getCodeTypeName()
/*  87:    */   {
/*  88: 76 */     return this.codeTypeName;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void setCodeTypeName(String codeTypeName)
/*  92:    */   {
/*  93: 80 */     this.codeTypeName = codeTypeName;
/*  94:    */   }
/*  95:    */   
/*  96:    */   @Column(name="CODE_ORDER", precision=5, scale=0)
/*  97:    */   public Integer getCodeOrder()
/*  98:    */   {
/*  99: 85 */     return this.codeOrder;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void setCodeOrder(Integer codeOrder)
/* 103:    */   {
/* 104: 89 */     this.codeOrder = codeOrder;
/* 105:    */   }
/* 106:    */   
/* 107:    */   @Column(name="STAT", nullable=false, precision=5, scale=0)
/* 108:    */   public Integer getStat()
/* 109:    */   {
/* 110: 94 */     return this.stat;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void setStat(Integer stat)
/* 114:    */   {
/* 115: 98 */     this.stat = stat;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public int hashCode()
/* 119:    */   {
/* 120:102 */     return this.id == null ? System.identityHashCode(this) : this.id.hashCode();
/* 121:    */   }
/* 122:    */   
/* 123:    */   public boolean equals(Object obj)
/* 124:    */   {
/* 125:106 */     if (this == obj) {
/* 126:107 */       return true;
/* 127:    */     }
/* 128:109 */     if (obj == null) {
/* 129:110 */       return false;
/* 130:    */     }
/* 131:112 */     if (getClass().getPackage() != obj.getClass().getPackage()) {
/* 132:113 */       return false;
/* 133:    */     }
/* 134:115 */     SysCode other = (SysCode)obj;
/* 135:116 */     if (this.id == null)
/* 136:    */     {
/* 137:117 */       if (other.getId() != null) {
/* 138:118 */         return false;
/* 139:    */       }
/* 140:    */     }
/* 141:119 */     else if (!this.id.equals(other.getId())) {
/* 142:120 */       return false;
/* 143:    */     }
/* 144:122 */     return true;
/* 145:    */   }
/* 146:    */ }


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.entity.SysCode
 * JD-Core Version:    0.7.0.1
 */