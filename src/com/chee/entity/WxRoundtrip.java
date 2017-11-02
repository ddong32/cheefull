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
/* 14:   */ @Table(name="chwx_roundtrip")
/* 15:   */ public class WxRoundtrip
/* 16:   */   implements Serializable
/* 17:   */ {
/* 18:   */   private static final long serialVersionUID = 1L;
/* 19:   */   private Integer id;
/* 20:   */   private Integer content_id;
/* 21:   */   private String text;
/* 22:   */   private Date created;
/* 23:   */   private Date modified;
/* 24:   */   
/* 25:   */   @Id
/* 26:   */   @GenericGenerator(name="paymentableGenerator", strategy="native")
/* 27:   */   @GeneratedValue(strategy=GenerationType.AUTO)
/* 28:   */   @Column(name="ID", precision=11, scale=0)
/* 29:   */   public Integer getId()
/* 30:   */   {
/* 31:41 */     return this.id;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public void setId(Integer id)
/* 35:   */   {
/* 36:45 */     this.id = id;
/* 37:   */   }
/* 38:   */   
/* 39:   */   @Column(name="text")
/* 40:   */   public String getText()
/* 41:   */   {
/* 42:50 */     return this.text;
/* 43:   */   }
/* 44:   */   
/* 45:   */   public void setText(String text)
/* 46:   */   {
/* 47:54 */     this.text = text;
/* 48:   */   }
/* 49:   */   
/* 50:   */   @Column(name="created ")
/* 51:   */   public Date getCreated()
/* 52:   */   {
/* 53:59 */     return this.created;
/* 54:   */   }
/* 55:   */   
/* 56:   */   public void setCreated(Date created)
/* 57:   */   {
/* 58:63 */     this.created = created;
/* 59:   */   }
/* 60:   */   
/* 61:   */   @Column(name="modified")
/* 62:   */   public Date getModified()
/* 63:   */   {
/* 64:68 */     return this.modified;
/* 65:   */   }
/* 66:   */   
/* 67:   */   public void setModified(Date modified)
/* 68:   */   {
/* 69:72 */     this.modified = modified;
/* 70:   */   }
/* 71:   */   
/* 72:   */   public boolean equals(Object obj)
/* 73:   */   {
/* 74:76 */     if (this == obj) {
/* 75:77 */       return true;
/* 76:   */     }
/* 77:79 */     if (obj == null) {
/* 78:80 */       return false;
/* 79:   */     }
/* 80:82 */     if (getClass().getPackage() != obj.getClass().getPackage()) {
/* 81:83 */       return false;
/* 82:   */     }
/* 83:85 */     WxRoundtrip other = (WxRoundtrip)obj;
/* 84:86 */     if (this.id == null)
/* 85:   */     {
/* 86:87 */       if (other.getId() != null) {
/* 87:88 */         return false;
/* 88:   */       }
/* 89:   */     }
/* 90:89 */     else if (!this.id.equals(other.getId())) {
/* 91:90 */       return false;
/* 92:   */     }
/* 93:92 */     return true;
/* 94:   */   }
/* 95:   */ }


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.entity.WxRoundtrip
 * JD-Core Version:    0.7.0.1
 */