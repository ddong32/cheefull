package com.chee.entity;

import com.chee.util.DateUtil;
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
@Table(name = "chee_bank_running")
public class BankRunning implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private Bank bank;
    private Date jzsj;
    private double zcje;
    private double srje;
    private double lrje;
    private double je;
    private String dfzh;
    private String dfhm;
    private String sflx;
    private String lrr;
    private Date lrsj;
    private Date gxsj;
    private String bz;
    private String shbj;
    private String orderId;
    private String today;
    private String beginDate;
    private String endDate;
    private Integer beginJe;
    private Integer endJe;
    private String fkje;
    private Business business;

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

    @Column(name = "jzsj")
    public Date getJzsj() {
        return this.jzsj;
    }

    public void setJzsj(Date jzsj) {
        this.jzsj = jzsj;
    }

    @Column(name = "zcje")
    public double getZcje() {
        return this.zcje;
    }

    public void setZcje(double zcje) {
        this.zcje = zcje;
    }

    @Column(name = "srje")
    public double getSrje() {
        return this.srje;
    }

    public void setSrje(double srje) {
        this.srje = srje;
    }

    @Column(name = "lrje")
    public double getLrje() {
        return this.lrje;
    }

    public void setLrje(double lrje) {
        this.lrje = lrje;
    }

    @Column(name = "dfzh")
    public String getDfzh() {
        return this.dfzh;
    }

    public void setDfzh(String dfzh) {
        this.dfzh = dfzh;
    }

    @Column(name = "dfhm")
    public String getDfhm() {
        return this.dfhm;
    }

    public void setDfhm(String dfhm) {
        this.dfhm = dfhm;
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

    @Column(name = "lrr")
    public String getLrr() {
        return this.lrr;
    }

    public void setLrr(String lrr) {
        this.lrr = lrr;
    }

    @Column(name = "sflx")
    public String getSflx() {
        return this.sflx;
    }

    public void setSflx(String sflx) {
        this.sflx = sflx;
    }

    @Column(name = "bz")
    public String getBz() {
        return this.bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    @Column(name = "shbj")
    public String getShbj() {
        return this.shbj;
    }

    public void setShbj(String shbj) {
        this.shbj = shbj;
    }

    @Column(name = "order_id")
    public String getOrderId() {
        return this.orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Transient
    public String getToday() {
        return this.today;
    }

    public void setToday(String today) {
        this.today = today;
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
    public Integer getBeginJe() {
        return this.beginJe;
    }

    public void setBeginJe(Integer beginJe) {
        this.beginJe = beginJe;
    }

    @Transient
    public Integer getEndJe() {
        return this.endJe;
    }

    public void setEndJe(Integer endJe) {
        this.endJe = endJe;
    }

    @Transient
    public double getJe() {
        return this.je;
    }

    public void setJe(double je) {
        this.je = je;
    }

    @Transient
    public String getFkje() {
        return this.fkje;
    }

    public void setFkje(String fkje) {
        this.fkje = fkje;
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
        BankRunning other = (BankRunning) obj;
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
        Map<String, String> map = new HashMap<String, String>();
        map.put("id", this.id.toString());
        if ((this.bank != null) && (this.bank.getId() != null)) {
            map.put("bank", this.bank.getId().toString());
        }
        map.put("orderId", this.orderId);
        map.put("lrje", this.lrje+"");
        map.put("jzsj", DateUtil.formatDate(this.jzsj, "yyyy-mm-dd"));
        map.put("gxsj", DateUtil.formatDate(this.gxsj, "yyyy-mm-dd"));
        map.put("sflx", this.sflx);
        Gson gson = new Gson();
        String s = gson.toJson(map);
        return s;
    }
}
