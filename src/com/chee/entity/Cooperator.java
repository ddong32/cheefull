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
@Table(name = "chee_cooperator")
public class Cooperator implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String dwmc;
    private String lxdh;
    private String lxr;
    private String lrr;
    private String emeil;
    private String khx;
    private String yhhm;
    private String yhzh;
    private Date lrsj;
    private Date gxsj;
    private String type;
    private Integer sort;

    @Id
    @GenericGenerator(name = "paymentableGenerator", strategy = "native")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", precision = 11, scale = 0)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "dwmc", length = 300)
    public String getDwmc() {
        return this.dwmc;
    }

    public void setDwmc(String dwmc) {
        this.dwmc = dwmc;
    }

    @Column(name = "lrr")
    public String getLrr() {
        return this.lrr;
    }

    public void setLrr(String lrr) {
        this.lrr = lrr;
    }

    @Column(name = "lxdh", length = 100)
    public String getLxdh() {
        return this.lxdh;
    }

    public void setLxdh(String lxdh) {
        this.lxdh = lxdh;
    }

    @Column(name = "yhzh")
    public String getYhzh() {
        return this.yhzh;
    }

    public void setYhzh(String yhzh) {
        this.yhzh = yhzh;
    }

    @Column(name = "lxr")
    public String getLxr() {
        return this.lxr;
    }

    public void setLxr(String lxr) {
        this.lxr = lxr;
    }

    @Column(name = "emeil", length = 30)
    public String getEmeil() {
        return this.emeil;
    }

    public void setEmeil(String emeil) {
        this.emeil = emeil;
    }

    @Column(name = "khx", length = 100)
    public String getKhx() {
        return this.khx;
    }

    public void setKhx(String khx) {
        this.khx = khx;
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

    @Column(name = "yhhm")
    public String getYhhm() {
        return this.yhhm;
    }

    public void setYhhm(String yhhm) {
        this.yhhm = yhhm;
    }

    @Column(name = "type")
    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "sort")
    public Integer getSort() {
        return this.sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
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
        Cooperator other = (Cooperator) obj;
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