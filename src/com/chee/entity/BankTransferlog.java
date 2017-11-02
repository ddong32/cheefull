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
@Table(name = "chee_bank_transferlog")
public class BankTransferlog implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private Integer foid;
    private Integer toid;
    private double fo_cush;
    private double to_cush;
    private Date czsj;
    private double zcje;
    private User user;
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

    @Column(name = "foid")
    public Integer getFoid() {
        return this.foid;
    }

    public void setFoid(Integer foid) {
        this.foid = foid;
    }

    @Column(name = "toid")
    public Integer getToid() {
        return this.toid;
    }

    public void setToid(Integer toid) {
        this.toid = toid;
    }

    @Column(name = "fo_cush")
    public double getFo_cush() {
        return this.fo_cush;
    }

    public void setFo_cush(double fo_cush) {
        this.fo_cush = fo_cush;
    }

    @Column(name = "to_cush")
    public double getTo_cush() {
        return this.to_cush;
    }

    public void setTo_cush(double to_cush) {
        this.to_cush = to_cush;
    }

    @Column(name = "czsj")
    public Date getCzsj() {
        return this.czsj;
    }

    public void setCzsj(Date czsj) {
        this.czsj = czsj;
    }

    @Column(name = "zcje")
    public double getZcje() {
        return this.zcje;
    }

    public void setZcje(double zcje) {
        this.zcje = zcje;
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
        BankTransferlog other = (BankTransferlog) obj;
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
