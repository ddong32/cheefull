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
/* 14:   */ @Table(name="s_province")
/* 15:   */ public class Province
/* 16:   */   implements Serializable
/* 17:   */ {
/* 18:   */   private static final long serialVersionUID = 1L;
/* 19:   */   private Integer provinceID;
/* 20:   */   private String provinceName;
/* 21:   */   private Date lrsj;
/* 22:   */   private Date gxsj;
/* 23:   */   
/* 24:   */   @Id
/* 25:   */   @GenericGenerator(name="paymentableGenerator", strategy="native")
/* 26:   */   @GeneratedValue(strategy=GenerationType.AUTO)
/* 27:   */   @Column(name="ProvinceID", precision=20, scale=0)
/* 28:   */   public Integer getProvinceID()
/* 29:   */   {
/* 30:28 */     return this.provinceID;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public void setProvinceID(Integer provinceID)
/* 34:   */   {
/* 35:32 */     this.provinceID = provinceID;
/* 36:   */   }
/* 37:   */   
/* 38:   */   @Column(name="ProvinceName")
/* 39:   */   public String getProvinceName()
/* 40:   */   {
/* 41:37 */     return this.provinceName;
/* 42:   */   }
/* 43:   */   
/* 44:   */   public void setProvinceName(String provinceName)
/* 45:   */   {
/* 46:41 */     this.provinceName = provinceName;
/* 47:   */   }
/* 48:   */   
/* 49:   */   @Column(name="DateCreated")
/* 50:   */   public Date getLrsj()
/* 51:   */   {
/* 52:46 */     return this.lrsj;
/* 53:   */   }
/* 54:   */   
/* 55:   */   public void setLrsj(Date lrsj)
/* 56:   */   {
/* 57:50 */     this.lrsj = lrsj;
/* 58:   */   }
/* 59:   */   
/* 60:   */   @Column(name="DateUpdated")
/* 61:   */   public Date getGxsj()
/* 62:   */   {
/* 63:55 */     return this.gxsj;
/* 64:   */   }
/* 65:   */   
/* 66:   */   public void setGxsj(Date gxsj)
/* 67:   */   {
/* 68:59 */     this.gxsj = gxsj;
/* 69:   */   }
/* 70:   */ }


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.entity.Province
 * JD-Core Version:    0.7.0.1
 */