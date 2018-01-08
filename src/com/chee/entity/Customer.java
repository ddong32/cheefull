package com.chee.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "chee_customer")
public class Customer implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String areaName;
    private String dwdz;
    private String gsdh;
    private String lxdh;
    private String czh;
    private String lxr;
    private String lrr;
    private String wxh;
    private String qq;
    private String szdsf;
    private String szdcs;
    private String szdxf;
    private Date lrsj;
    private Date gxsj;
    private Integer stat;
    private Integer sort;
    private String path;
    private String pathName;
    private Integer type;
    private Integer grade;
    private Customer parent;
    private Set<Customer> children;

    @Id
    @GenericGenerator(name = "paymentableGenerator", strategy = "native")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", precision = 10, scale = 0)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "AREA_NAME")
    public String getAreaName() {
        return this.areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    @Column(name = "dwdz")
    public String getDwdz() {
        return this.dwdz;
    }

    public void setDwdz(String dwdz) {
        this.dwdz = dwdz;
    }

    @Column(name = "lxdh")
    public String getLxdh() {
        return this.lxdh;
    }

    public void setLxdh(String lxdh) {
        this.lxdh = lxdh;
    }

    @Column(name = "lxr")
    public String getLxr() {
        return this.lxr;
    }

    public void setLxr(String lxr) {
        this.lxr = lxr;
    }

    @Column(name = "lrr")
    public String getLrr() {
        return lrr;
    }

    public void setLrr(String lrr) {
        this.lrr = lrr;
    }

    @Column(name = "szdsf")
    public String getSzdsf() {
        return this.szdsf;
    }

    public void setSzdsf(String szdsf) {
        this.szdsf = szdsf;
    }

    @Column(name = "szdcs")
    public String getSzdcs() {
        return this.szdcs;
    }

    public void setSzdcs(String szdcs) {
        this.szdcs = szdcs;
    }

    @Column(name = "szdxf")
    public String getSzdxf() {
        return this.szdxf;
    }

    public void setSzdxf(String szdxf) {
        this.szdxf = szdxf;
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

    @Column(name = "czh")
    public String getCzh() {
        return this.czh;
    }

    public void setCzh(String czh) {
        this.czh = czh;
    }

    @Column(name = "qq")
    public String getQq() {
        return this.qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    @Column(name = "gsdh")
    public String getGsdh() {
        return this.gsdh;
    }

    public void setGsdh(String gsdh) {
        this.gsdh = gsdh;
    }

    @Column(name = "wxh")
    public String getWxh() {
        return this.wxh;
    }

    public void setWxh(String wxh) {
        this.wxh = wxh;
    }

    @Column(name = "stat")
    public Integer getStat() {
        return this.stat;
    }

    public void setStat(Integer stat) {
        this.stat = stat;
    }

    @Column(name = "sort")
    public Integer getSort() {
        return this.sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Column(name = "path")
    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Column(name = "path_name")
    public String getPathName() {
        return this.pathName;
    }

    public void setPathName(String pathName) {
        this.pathName = pathName;
    }

    @Column(name = "type")
    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Column(name = "grade")
    public Integer getGrade() {
        return this.grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    public Customer getParent() {
        return this.parent;
    }

    public void setParent(Customer parent) {
        this.parent = parent;
    }

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    @OrderBy("sort asc")
    public Set<Customer> getChildren() {
        return this.children;
    }

    public void setChildren(Set<Customer> children) {
        this.children = children;
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
        Customer other = (Customer) obj;
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
