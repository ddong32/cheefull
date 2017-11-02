package com.chee.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "chee_bank")
public class Bank implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;
    private String djm;
    private String code;
    private String khh;
    private double cush;
    private Date lrsj;
    private Date gxsj;
    private String xtdm;
    private Integer goid;
    private Integer coid;
    private double je;
    private String beginDate;
    private String endDate;

    @Id
    @GenericGenerator(name = "paymentableGenerator", strategy = "native")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", precision = 10, scale = 0)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "name")
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "code")
    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "djm")
    public String getDjm() {
        return this.djm;
    }

    public void setDjm(String djm) {
        this.djm = djm;
    }

    @Column(name = "khh")
    public String getKhh() {
        return this.khh;
    }

    public void setKhh(String khh) {
        this.khh = khh;
    }

    @Column(name = "cush")
    public double getCush() {
        return this.cush;
    }

    public void setCush(double cush) {
        this.cush = cush;
    }

    @Column(name = "lrsj")
    public Date getLrsj() {
        return this.lrsj;
    }

    public void setLrsj(Date lrsj) {
        this.lrsj = lrsj;
    }

    @Column(name = "gxsj")
    public Date getGxsj() {
        return this.gxsj;
    }

    public void setGxsj(Date gxsj) {
        this.gxsj = gxsj;
    }

    @Column(name = "xtdm")
    public String getXtdm() {
        return this.xtdm;
    }

    public void setXtdm(String xtdm) {
        this.xtdm = xtdm;
    }

    @Transient
    public double getJe() {
        return this.je;
    }

    public void setJe(double je) {
        this.je = je;
    }

    @Transient
    public Integer getGoid() {
        return this.goid;
    }

    public void setGoid(Integer goid) {
        this.goid = goid;
    }

    @Transient
    public Integer getCoid() {
        return this.coid;
    }

    public void setCoid(Integer coid) {
        this.coid = coid;
    }

    @Transient
    public String getBeginDate() {
        return this.beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    @Transient
    public String getEndDate() {
        return this.endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int hashCode() {
        return this.id == null ? System.identityHashCode(this) : this.id.hashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass().getPackage() != obj.getClass().getPackage()) {
            return false;
        }
        Bank other = (Bank) obj;
        if (this.id == null) {
            if (other.getId() != null) {
                return false;
            }
        } else if (!this.id.equals(other.getId())) {
            return false;
        }
        return true;
    }
}
