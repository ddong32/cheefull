package com.chee.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "s_city")
public class City implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer cityID;
    private String cityName;
    private Integer provinceID;
    private Date lrsj;
    private Date gxsj;

    @Id
    @GenericGenerator(name = "paymentableGenerator", strategy = "native")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cityID", precision = 20, scale = 0)
    public Integer getCityID() {
        return this.cityID;
    }

    public void setCityID(Integer cityID) {
        this.cityID = cityID;
    }

    @Column(name = "cityName")
    public String getCityName() {
        return this.cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @Column(name = "provinceID")
    public Integer getProvinceID() {
        return this.provinceID;
    }

    public void setProvinceID(Integer provinceID) {
        this.provinceID = provinceID;
    }

    @Column(name = "DateCreated")
    public Date getLrsj() {
        return this.lrsj;
    }

    public void setLrsj(Date lrsj) {
        this.lrsj = lrsj;
    }

    @Column(name = "DateUpdated")
    public Date getGxsj() {
        return this.gxsj;
    }

    public void setGxsj(Date gxsj) {
        this.gxsj = gxsj;
    }
}
