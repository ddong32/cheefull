package com.chee.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
@Table(name = "chee_account_flow")
public class AccountFlow implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String lsh;
    private double skje;
    private double fkje;
    private String lrr;
    private Date lrsj;
    private Date gxsj;
    private String shr;
    private Date shsj;
    private String stat;
    private Date sksj;
    private String accountId;
    private String orderId;
    private String bz;
    private Bank bank;
    private Account account;
    private String spr;
    private Date spsj;
    private String sffs;
    private Integer bankRunningId;
    private String beginDate;
    private String endDate;
    private String ywlx;
    private User user;
    private List<Integer> proUserIds;

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

    @Column(name = "lsh", length = 20)
    public String getLsh() {
        return this.lsh;
    }

    public void setLsh(String lsh) {
        this.lsh = lsh;
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

    @Column(name = "account_id")
    public String getAccountId() {
        return this.accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    @Column(name = "order_id")
    public String getOrderId() {
        return this.orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Column(name = "sksj")
    public Date getSksj() {
        return this.sksj;
    }

    public void setSksj(Date sksj) {
        this.sksj = sksj;
    }

    @Column(name = "ywlx")
    public String getYwlx() {
        return this.ywlx;
    }

    public void setYwlx(String ywlx) {
        this.ywlx = ywlx;
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
    @JoinColumn(name = "a_id", referencedColumnName = "id")
    public Account getAccount() {
        return this.account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Column(name = "bank_running_id")
    public Integer getBankRunningId() {
        return this.bankRunningId;
    }

    public void setBankRunningId(Integer bankRunningId) {
        this.bankRunningId = bankRunningId;
    }

    @Column(name = "spr")
    public String getSpr() {
        return this.spr;
    }

    public void setSpr(String spr) {
        this.spr = spr;
    }

    @Column(name = "spsj")
    public Date getSpsj() {
        return this.spsj;
    }

    public void setSpsj(Date spsj) {
        this.spsj = spsj;
    }

    @Column(name = "sffs")
    public String getSffs() {
        return this.sffs;
    }

    public void setSffs(String sffs) {
        this.sffs = sffs;
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
    public List<Integer> getProUserIds() {
        return this.proUserIds;
    }

    public void setProUserIds(List<Integer> proUserIds) {
        this.proUserIds = proUserIds;
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
        AccountFlow other = (AccountFlow) obj;
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
