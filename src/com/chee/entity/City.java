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
/* 14:   */ @Table(name="s_city")
/* 15:   */ public class City
/* 16:   */   implements Serializable
/* 17:   */ {
/* 18:   */   private static final long serialVersionUID = 1L;
/* 19:   */   private Integer cityID;
/* 20:   */   private String cityName;
/* 21:   */   private Integer provinceID;
/* 22:   */   private Date lrsj;
/* 23:   */   private Date gxsj;
/* 24:   */   
/* 25:   */   @Id
/* 26:   */   @GenericGenerator(name="paymentableGenerator", strategy="native")
/* 27:   */   @GeneratedValue(strategy=GenerationType.AUTO)
/* 28:   */   @Column(name="cityID", precision=20, scale=0)
/* 29:   */   public Integer getCityID()
/* 30:   */   {
/* 31:29 */     return this.cityID;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public void setCityID(Integer cityID)
/* 35:   */   {
/* 36:33 */     this.cityID = cityID;
/* 37:   */   }
/* 38:   */   
/* 39:   */   @Column(name="cityName")
/* 40:   */   public String getCityName()
/* 41:   */   {
/* 42:38 */     return this.cityName;
/* 43:   */   }
/* 44:   */   
/* 45:   */   public void setCityName(String cityName)
/* 46:   */   {
/* 47:42 */     this.cityName = cityName;
/* 48:   */   }
/* 49:   */   
/* 50:   */   @Column(name="provinceID")
/* 51:   */   public Integer getProvinceID()
/* 52:   */   {
/* 53:47 */     return this.provinceID;
/* 54:   */   }
/* 55:   */   
/* 56:   */   public void setProvinceID(Integer provinceID)
/* 57:   */   {
/* 58:51 */     this.provinceID = provinceID;
/* 59:   */   }
/* 60:   */   
/* 61:   */   @Column(name="DateCreated")
/* 62:   */   public Date getLrsj()
/* 63:   */   {
/* 64:56 */     return this.lrsj;
/* 65:   */   }
/* 66:   */   
/* 67:   */   public void setLrsj(Date lrsj)
/* 68:   */   {
/* 69:60 */     this.lrsj = lrsj;
/* 70:   */   }
/* 71:   */   
/* 72:   */   @Column(name="DateUpdated")
/* 73:   */   public Date getGxsj()
/* 74:   */   {
/* 75:65 */     return this.gxsj;
/* 76:   */   }
/* 77:   */   
/* 78:   */   public void setGxsj(Date gxsj)
/* 79:   */   {
/* 80:69 */     this.gxsj = gxsj;
/* 81:   */   }
/* 82:   */ }


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.entity.City
 * JD-Core Version:    0.7.0.1
 */