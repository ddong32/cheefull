package com.chee.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "chee_business_customer")
public class BusinessCustomer implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String orderId;
    private String customerDwmc;
    private Integer parentId;
    private String adultNo;
    private String childNo;
    private String escortNo;
    private double ygs;
    private double yjs;
    private double crj;
    private double etj;
    private double ptj;
    private Date lrsj;
    private Date gxsj;
    private String lrr;
    private String bz;
    private Customer customer;
    private Business business;
    private String beginDate;
    private String endDate;
    private String parent_id;
    private String type;
    private String uid;

    @Id
    @GenericGenerator(name = "paymentableGenerator", strategy = "native")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", precision = 11, scale = 0)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "order_id")
    public String getOrderId() {
        return this.orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Column(name = "customer_dwmc")
    public String getCustomerDwmc() {
        return this.customerDwmc;
    }

    public void setCustomerDwmc(String customerDwmc) {
        this.customerDwmc = customerDwmc;
    }

    @Column(name = "parent_id")
    public Integer getParentId() {
        return this.parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    @Column(name = "adult_no")
    public String getAdultNo() {
        return this.adultNo;
    }

    public void setAdultNo(String adultNo) {
        this.adultNo = adultNo;
    }

    @Column(name = "child_no")
    public String getChildNo() {
        return this.childNo;
    }

    public void setChildNo(String childNo) {
        this.childNo = childNo;
    }

    @Column(name = "escort_no")
    public String getEscortNo() {
        return this.escortNo;
    }

    public void setEscortNo(String escortNo) {
        this.escortNo = escortNo;
    }

    @Column(name = "ygs")
    public double getYgs() {
        return this.ygs;
    }

    public void setYgs(double ygs) {
        this.ygs = ygs;
    }

    @Column(name = "yjs")
    public double getYjs() {
        return this.yjs;
    }

    public void setYjs(double yjs) {
        this.yjs = yjs;
    }

    @Column(name = "crj")
    public double getCrj() {
        return this.crj;
    }

    public void setCrj(double crj) {
        this.crj = crj;
    }

    @Column(name = "etj")
    public double getEtj() {
        return this.etj;
    }

    public void setEtj(double etj) {
        this.etj = etj;
    }

    @Column(name = "ptj")
    public double getPtj() {
        return this.ptj;
    }

    public void setPtj(double ptj) {
        this.ptj = ptj;
    }

    @Column(name = "lrr")
    public String getLrr() {
        return this.lrr;
    }

    public void setLrr(String lrr) {
        this.lrr = lrr;
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

    @Column(name = "bz")
    public String getBz() {
        return this.bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_id", referencedColumnName = "id")
    public Business getBusiness() {
        return this.business;
    }

    public void setBusiness(Business business) {
        this.business = business;
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

    @Transient
    public String getParent_id() {
        return this.parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    @Transient
    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Transient
    public String getUid() {
        return this.uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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
        BusinessCustomer other = (BusinessCustomer) obj;
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