package com.chee.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "chee_business")
public class Business implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String orderId;
    private String travelLine;
    private Integer adultNo = Integer.valueOf(0);
    private Integer childNo = Integer.valueOf(0);
    private Integer escortNo = Integer.valueOf(0);
    private double ygs;
    private double yjs;
    private double ygf;
    private double yjf;
    private String lrr;
    private String ywlx;
    private Date cfsj;
    private Date lrsj;
    private Date gxsj;
    private String ddzt;
    private String ddlx;
    private String bz;
    private String sdate;
    private String beginDate;
    private String endDate;
    private String sflx;
    private String bankId;
    private User user;
    private Set<Account> accountSet;
    private List<Integer> proUserIds;
    private String userids;
    private String wskchecked;

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

    @Column(name = "order_id")
    public String getOrderId() {
        return this.orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Column(name = "travel_line")
    public String getTravelLine() {
        return this.travelLine;
    }

    public void setTravelLine(String travelLine) {
        this.travelLine = travelLine;
    }

    @Column(name = "adult_no")
    public Integer getAdultNo() {
        return this.adultNo;
    }

    public void setAdultNo(Integer adultNo) {
        this.adultNo = adultNo;
    }

    @Column(name = "child_no")
    public Integer getChildNo() {
        return this.childNo;
    }

    public void setChildNo(Integer childNo) {
        this.childNo = childNo;
    }

    @Column(name = "escort_no")
    public Integer getEscortNo() {
        return this.escortNo;
    }

    public void setEscortNo(Integer escortNo) {
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

    @Column(name = "ygf")
    public double getYgf() {
        return this.ygf;
    }

    public void setYgf(double ygf) {
        this.ygf = ygf;
    }

    @Column(name = "yjf")
    public double getYjf() {
        return this.yjf;
    }

    public void setYjf(double yjf) {
        this.yjf = yjf;
    }

    @Column(name = "lrr", length = 20)
    public String getLrr() {
        return this.lrr;
    }

    public void setLrr(String lrr) {
        this.lrr = lrr;
    }

    @Column(name = "ywlx", length = 1)
    public String getYwlx() {
        return this.ywlx;
    }

    public void setYwlx(String ywlx) {
        this.ywlx = ywlx;
    }

    @Column(name = "cfsj")
    public Date getCfsj() {
        return this.cfsj;
    }

    public void setCfsj(Date cfsj) {
        this.cfsj = cfsj;
    }

    @Column(name = "lrsj", updatable = false)
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

    @Column(name = "ddzt")
    public String getDdzt() {
        return this.ddzt;
    }

    public void setDdzt(String ddzt) {
        this.ddzt = ddzt;
    }

    @Column(name = "ddlx")
    public String getDdlx() {
        return this.ddlx;
    }

    public void setDdlx(String ddlx) {
        this.ddlx = ddlx;
    }

    @Column(name = "bz")
    public String getBz() {
        return this.bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @OneToMany(mappedBy = "business", fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    public Set<Account> getAccountSet() {
        return this.accountSet;
    }

    public void setAccountSet(Set<Account> accountSet) {
        this.accountSet = accountSet;
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
    public String getSflx() {
        return this.sflx;
    }

    public void setSflx(String sflx) {
        this.sflx = sflx;
    }

    @Transient
    public String getBankId() {
        return this.bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    @Transient
    public List<Integer> getProUserIds() {
        return this.proUserIds;
    }

    public void setProUserIds(List<Integer> proUserIds) {
        this.proUserIds = proUserIds;
    }

    @Transient
    public String getUserids() {
        return this.userids;
    }

    public void setUserids(String userids) {
        this.userids = userids;
    }

    @Transient
    public String getWskchecked() {
        return this.wskchecked;
    }

    public void setWskchecked(String wskchecked) {
        this.wskchecked = wskchecked;
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
        Business other = (Business) obj;
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
