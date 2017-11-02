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
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "chee_affiche")
public class Affiche implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private Integer stat;
    private Date createDate;
    private String content;
    private Integer type;
    private String title;
    private User user;

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

    @Column(name = "STAT")
    public Integer getStat() {
        return this.stat;
    }

    public void setStat(Integer stat) {
        this.stat = stat;
    }

    @Column(name = "CREATE_DATE")
    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Column(name = "CONTENT")
    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Column(name = "TYPE")
    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Column(name = "TITLE")
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
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
        Affiche other = (Affiche) obj;
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
