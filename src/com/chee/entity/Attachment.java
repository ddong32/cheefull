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
@Table(name = "chwx_attachment")
public class Attachment implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String title;
    private Integer user_id;
    private Integer content_id;
    private String path;
    private String mime_type;
    private String suffix;
    private String type;
    private String flag;
    private double lat;
    private double lng;
    private Date order_number;
    private Date created;
    private String beginDate;
    private String endDate;
    private String mime;
    private String name;
    private String size;
    private String ratio;

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

    @Column(name = "title")
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "user_id")
    public Integer getUser_id() {
        return this.user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    @Column(name = "content_id")
    public Integer getContent_id() {
        return this.content_id;
    }

    public void setContent_id(Integer content_id) {
        this.content_id = content_id;
    }

    @Column(name = "path")
    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Column(name = "mime_type")
    public String getMime_type() {
        return this.mime_type;
    }

    public void setMime_type(String mime_type) {
        this.mime_type = mime_type;
    }

    @Column(name = "suffix")
    public String getSuffix() {
        return this.suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    @Column(name = "type")
    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "flag")
    public String getFlag() {
        return this.flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    @Column(name = "lat")
    public double getLat() {
        return this.lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    @Column(name = "lng")
    public double getLng() {
        return this.lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    @Column(name = "order_number")
    public Date getOrder_number() {
        return this.order_number;
    }

    public void setOrder_number(Date order_number) {
        this.order_number = order_number;
    }

    @Column(name = "created")
    public Date getCreated() {
        return this.created;
    }

    public void setCreated(Date created) {
        this.created = created;
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
    public String getMime() {
        return this.mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }

    @Transient
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Transient
    public String getSize() {
        return this.size;
    }

    @Transient
    public void setSize(String size) {
        this.size = size;
    }

    @Transient
    public String getRatio() {
        return this.ratio;
    }

    public void setRatio(String ratio) {
        this.ratio = ratio;
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
        Attachment other = (Attachment) obj;
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
