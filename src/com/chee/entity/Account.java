package com.chee.entity;

import com.google.gson.Gson;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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
@Table(name = "chee_account")
public class Account implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String sflx;
    private String fklx;
    private String sffs;
    private double skje;
    private double fkje;
    private String lrr;
    private Date lrsj;
    private Date gxsj;
    private String bz;
    private String shr;
    private Date shsj;
    private String stat;
    private String orderId;
    private Date sfsj;
    private String jlshr;
    private Date jlshsj;
    private BusinessCustomer businessCustomer;
    private Business business;
    private Bank bank;
    private Cooperator cooperator;
    private User user;
    private String sdate;
    private String beginDate;
    private String endDate;
    private String zwsflx;
    private Integer counts;
    private String dwmc;
    private double je;
    private String jqchecked;

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

    @Column(name = "lrr", length = 20)
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

    @Column(name = "sflx")
    public String getSflx() {
        return this.sflx;
    }

    public void setSflx(String sflx) {
        this.sflx = sflx;
    }

    @Column(name = "sffs")
    public String getSffs() {
        return this.sffs;
    }

    public void setSffs(String sffs) {
        this.sffs = sffs;
    }

    @Column(name = "skje")
    public double getSkje() {
        return this.skje;
    }

    public void setSkje(double skje) {
        this.skje = skje;
    }

    @Column(name = "fkje")
    public double getFkje() {
        return this.fkje;
    }

    public void setFkje(double fkje) {
        this.fkje = fkje;
    }

    @Column(name = "fklx")
    public String getFklx() {
        return this.fklx;
    }

    public void setFklx(String fklx) {
        this.fklx = fklx;
    }

    @Column(name = "bz")
    public String getBz() {
        return this.bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    @Column(name = "shr")
    public String getShr() {
        return this.shr;
    }

    public void setShr(String shr) {
        this.shr = shr;
    }

    @Column(name = "shsj")
    public Date getShsj() {
        return this.shsj;
    }

    public void setShsj(Date shsj) {
        this.shsj = shsj;
    }

    @Column(name = "stat")
    public String getStat() {
        return this.stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    @Column(name = "order_id")
    public String getOrderId() {
        return this.orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Column(name = "sfsj")
    public Date getSfsj() {
        return this.sfsj;
    }

    public void setSfsj(Date sfsj) {
        this.sfsj = sfsj;
    }

    @Column(name = "jlshr")
    public String getJlshr() {
        return this.jlshr;
    }

    public void setJlshr(String jlshr) {
        this.jlshr = jlshr;
    }

    @Column(name = "jlshsj")
    public Date getJlshsj() {
        return this.jlshsj;
    }

    public void setJlshsj(Date jlshsj) {
        this.jlshsj = jlshsj;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "b_c_id", referencedColumnName = "id")
    public BusinessCustomer getBusinessCustomer() {
        return this.businessCustomer;
    }

    public void setBusinessCustomer(BusinessCustomer businessCustomer) {
        this.businessCustomer = businessCustomer;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "bank_id", referencedColumnName = "id")
    public Bank getBank() {
        return this.bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "business_id", referencedColumnName = "id")
    public Business getBusiness() {
        return this.business;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "coop_id", referencedColumnName = "id")
    public Cooperator getCooperator() {
        return this.cooperator;
    }

    public void setCooperator(Cooperator cooperator) {
        this.cooperator = cooperator;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Transient
    public String getSdate() {
        return this.sdate;
    }

    public void setSdate(String sdate) {
        this.sdate = sdate;
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
    public String getZwsflx() {
        return this.zwsflx;
    }

    public void setZwsflx(String zwsflx) {
        this.zwsflx = zwsflx;
    }

    @Transient
    public Integer getCounts() {
        return this.counts;
    }

    public void setCounts(Integer counts) {
        this.counts = counts;
    }

    @Transient
    public String getDwmc() {
        return this.dwmc;
    }

    public void setDwmc(String dwmc) {
        this.dwmc = dwmc;
    }

    @Transient
    public double getJe() {
        return this.je;
    }

    public void setJe(double je) {
        this.je = je;
    }

    @Transient
    public String getJqchecked() {
        return this.jqchecked;
    }

    public void setJqchecked(String jqchecked) {
        this.jqchecked = jqchecked;
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
        Account other = (Account) obj;
        if (this.id == null) {
            if (other.getId() != null) {
                return false;
            }
        } else if (!this.id.equals(other.getId())) {
            return false;
        }
        return true;
    }

    public String toString() {
        Map<String, String> map = new HashMap();
        map.put("id", this.id.toString());
        map.put("orderId", this.orderId);
        map.put("stat", this.stat);
        map.put("sflx", this.sflx);
        if ("1".equals(this.sflx)) {
            map.put("skje", this.skje + "");
            if ((this.businessCustomer != null) && (this.businessCustomer.getId() != null)) {
                map.put("businessCustomer", this.businessCustomer.getId().toString());
            }
            if ((this.bank != null) && (this.bank.getId() != null)) {
                map.put("bank", this.bank.getId().toString());
            }
        } else {
            map.put("fkje", this.fkje + "");
            if ((this.cooperator != null) && (this.cooperator.getId() != null)) {
                map.put("cooperator", this.cooperator.getId().toString());
            }
        }
        map.put("sffs", this.sffs);
        map.put("fklx", this.fklx);
        map.put("je", this.je + "");
        Gson gson = new Gson();
        String s = gson.toJson(map);
        return s;
    }
}
