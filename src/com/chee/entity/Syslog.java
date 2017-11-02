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
/*  14:    */ @Table(name="chee_syslog")
/*  15:    */ public class Syslog
/*  16:    */   implements Serializable
/*  17:    */ {
/*  18:    */   private static final long serialVersionUID = -6718838800112233445L;
/*  19:    */   private Integer id;
/*  20:    */   private Integer userid;
/*  21:    */   private String username;
/*  22:    */   private Date createdate;
/*  23:    */   private String operation;
/*  24:    */   private String content;
/*  25:    */   private String classname;
/*  26:    */   private String methodname;
/*  27:    */   private String modelname;
/*  28:    */   private String ip;
/*  29:    */   private String argument;
/*  30:    */   private String level;
/*  31:    */   private String keyword;
/*  32:    */   
/*  33:    */   @Id
/*  34:    */   @GenericGenerator(name="paymentableGenerator", strategy="native")
/*  35:    */   @GeneratedValue(strategy=GenerationType.AUTO)
/*  36:    */   @Column(name="ID", precision=11, scale=0)
/*  37:    */   public Integer getId()
/*  38:    */   {
/*  39: 39 */     return this.id;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void setId(Integer id)
/*  43:    */   {
/*  44: 43 */     this.id = id;
/*  45:    */   }
/*  46:    */   
/*  47:    */   @Column(name="userid", length=20)
/*  48:    */   public Integer getUserid()
/*  49:    */   {
/*  50: 48 */     return this.userid;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void setUserid(Integer userid)
/*  54:    */   {
/*  55: 52 */     this.userid = userid;
/*  56:    */   }
/*  57:    */   
/*  58:    */   @Column(name="username", length=20)
/*  59:    */   public String getUsername()
/*  60:    */   {
/*  61: 57 */     return this.username;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void setUsername(String username)
/*  65:    */   {
/*  66: 61 */     this.username = username;
/*  67:    */   }
/*  68:    */   
/*  69:    */   @Column(name="createdate", length=20, nullable=false)
/*  70:    */   public Date getCreatedate()
/*  71:    */   {
/*  72: 66 */     return this.createdate;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void setCreatedate(Date createdate)
/*  76:    */   {
/*  77: 70 */     this.createdate = createdate;
/*  78:    */   }
/*  79:    */   
/*  80:    */   @Column(name="operation", length=200)
/*  81:    */   public String getOperation()
/*  82:    */   {
/*  83: 75 */     return this.operation;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void setOperation(String operation)
/*  87:    */   {
/*  88: 79 */     this.operation = operation;
/*  89:    */   }
/*  90:    */   
/*  91:    */   @Column(name="content", length=8000)
/*  92:    */   public String getContent()
/*  93:    */   {
/*  94: 84 */     return this.content;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void setContent(String content)
/*  98:    */   {
/*  99: 88 */     this.content = content;
/* 100:    */   }
/* 101:    */   
/* 102:    */   @Column(name="classname", length=100, nullable=false)
/* 103:    */   public String getClassname()
/* 104:    */   {
/* 105: 93 */     return this.classname;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void setClassname(String classname)
/* 109:    */   {
/* 110: 97 */     this.classname = classname;
/* 111:    */   }
/* 112:    */   
/* 113:    */   @Column(name="methodname", length=100, nullable=false)
/* 114:    */   public String getMethodname()
/* 115:    */   {
/* 116:102 */     return this.methodname;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void setMethodname(String methodname)
/* 120:    */   {
/* 121:106 */     this.methodname = methodname;
/* 122:    */   }
/* 123:    */   
/* 124:    */   @Column(name="modelname", length=200)
/* 125:    */   public String getModelname()
/* 126:    */   {
/* 127:111 */     return this.modelname;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void setModelname(String modelname)
/* 131:    */   {
/* 132:115 */     this.modelname = modelname;
/* 133:    */   }
/* 134:    */   
/* 135:    */   @Column(name="ip", length=30)
/* 136:    */   public String getIp()
/* 137:    */   {
/* 138:120 */     return this.ip;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void setIp(String ip)
/* 142:    */   {
/* 143:124 */     this.ip = ip;
/* 144:    */   }
/* 145:    */   
/* 146:    */   @Column(name="argument", length=1000)
/* 147:    */   public String getArgument()
/* 148:    */   {
/* 149:129 */     return this.argument;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void setArgument(String argument)
/* 153:    */   {
/* 154:133 */     this.argument = argument;
/* 155:    */   }
/* 156:    */   
/* 157:    */   @Column(name="level", length=1)
/* 158:    */   public String getLevel()
/* 159:    */   {
/* 160:138 */     return this.level;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void setLevel(String level)
/* 164:    */   {
/* 165:142 */     this.level = level;
/* 166:    */   }
/* 167:    */   
/* 168:    */   @Column(name="keyword")
/* 169:    */   public String getKeyword()
/* 170:    */   {
/* 171:147 */     return this.keyword;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public void setKeyword(String keyword)
/* 175:    */   {
/* 176:151 */     this.keyword = keyword;
/* 177:    */   }
/* 178:    */ }


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.entity.Syslog
 * JD-Core Version:    0.7.0.1
 */