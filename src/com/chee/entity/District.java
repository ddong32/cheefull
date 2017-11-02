/*  1:   */ package com.chee.entity;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import java.util.Date;
/*  5:   */ import javax.persistence.Column;
/*  6:   */ import javax.persistence.Entity;
/*  7:   */ import javax.persistence.Id;
/*  8:   */ import javax.persistence.Table;
/*  9:   */ import org.hibernate.annotations.GenericGenerator;
/* 10:   */ 
/* 11:   */ @Entity
/* 12:   */ @Table(name="s_district")
/* 13:   */ public class District
/* 14:   */   implements Serializable
/* 15:   */ {
/* 16:   */   private static final long serialVersionUID = 1L;
/* 17:   */   private Integer districtID;
/* 18:   */   private String districtName;
/* 19:   */   private Integer cityID;
/* 20:   */   private Date lrsj;
/* 21:   */   private Date gxsj;
/* 22:   */   
/* 23:   */   @Id
/* 24:   */   @GenericGenerator(name="paymentableGenerator", strategy="native")
/* 25:   */   @Column(name="DistrictID", precision=20, scale=0)
/* 26:   */   public Integer getDistrictID()
/* 27:   */   {
/* 28:28 */     return this.districtID;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public void setDistrictID(Integer districtID)
/* 32:   */   {
/* 33:32 */     this.districtID = districtID;
/* 34:   */   }
/* 35:   */   
/* 36:   */   @Column(name="DistrictName")
/* 37:   */   public String getDistrictName()
/* 38:   */   {
/* 39:37 */     return this.districtName;
/* 40:   */   }
/* 41:   */   
/* 42:   */   public void setDistrictName(String districtName)
/* 43:   */   {
/* 44:41 */     this.districtName = districtName;
/* 45:   */   }
/* 46:   */   
/* 47:   */   @Column(name="CityID")
/* 48:   */   public Integer getCityID()
/* 49:   */   {
/* 50:46 */     return this.cityID;
/* 51:   */   }
/* 52:   */   
/* 53:   */   public void setCityID(Integer cityID)
/* 54:   */   {
/* 55:50 */     this.cityID = cityID;
/* 56:   */   }
/* 57:   */   
/* 58:   */   @Column(name="DateCreated")
/* 59:   */   public Date getLrsj()
/* 60:   */   {
/* 61:55 */     return this.lrsj;
/* 62:   */   }
/* 63:   */   
/* 64:   */   public void setLrsj(Date lrsj)
/* 65:   */   {
/* 66:59 */     this.lrsj = lrsj;
/* 67:   */   }
/* 68:   */   
/* 69:   */   @Column(name="DateUpdated")
/* 70:   */   public Date getGxsj()
/* 71:   */   {
/* 72:64 */     return this.gxsj;
/* 73:   */   }
/* 74:   */   
/* 75:   */   public void setGxsj(Date gxsj)
/* 76:   */   {
/* 77:68 */     this.gxsj = gxsj;
/* 78:   */   }
/* 79:   */ }


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.entity.District
 * JD-Core Version:    0.7.0.1
 */