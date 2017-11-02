package com.chee.entity;

import com.chee.util.ConvertUtils;
import com.google.gson.Gson;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.userdetails.UserDetails;

@Entity
@Table(name = "chee_user")
public class User implements UserDetails, Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String username;
    private String password;
    private String email;
    private String name;
    private String sex;
    private String jobNumber;
    private String idCard;
    private String phone;
    private Integer grade;
    private Boolean isSystem;
    private Boolean isAccountEnabled;
    private Boolean isAccountLocked;
    private Boolean isAccountExpired;
    private Boolean isCredentialsExpired;
    private Integer loginFailureCount;
    private String description;
    private Date lockedDate;
    private Date loginDate;
    private String loginIp;
    private Date createDate;
    private Date updateDate;
    private String qq;
    private String lxdz;
    private String jjlx;
    private Department department;
    private Set<Role> roleSet = new HashSet<Role>();
    private GrantedAuthority[] authorities;
    private String departmentCode;
    private String departmentName;
    private String passwordConfirm;
    private String passwordCurrent;
    private Integer userDepartmentId;
    private String xx;
    private String xz;
    private String rzsj;
    private String bz;

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

    @Column(name = "create_date")
    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Column(name = "update_date")
    public Date getUpdateDate() {
        return this.updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @Column(name = "user_name", length = 50, nullable = false, unique = true)
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "password", length = 50, nullable = false)
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "email", length = 50)
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "name", length = 50)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "job_number", length = 50)
    public String getJobNumber() {
        return this.jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    @Column(name = "id_card", length = 50)
    public String getIdCard() {
        return this.idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    @Column(name = "phone", length = 50)
    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    public Department getDepartment() {
        return this.department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Column(name = "is_account_enabled", nullable = false)
    public Boolean getIsAccountEnabled() {
        return this.isAccountEnabled;
    }

    public void setIsAccountEnabled(Boolean isAccountEnabled) {
        this.isAccountEnabled = isAccountEnabled;
    }

    @Column(name = "is_account_locked", nullable = false)
    public Boolean getIsAccountLocked() {
        return this.isAccountLocked;
    }

    public void setIsAccountLocked(Boolean isAccountLocked) {
        this.isAccountLocked = isAccountLocked;
    }

    @Column(name = "is_account_expired", nullable = false)
    public Boolean getIsAccountExpired() {
        return this.isAccountExpired;
    }

    public void setIsAccountExpired(Boolean isAccountExpired) {
        this.isAccountExpired = isAccountExpired;
    }

    @Column(name = "is_credentials_expired", nullable = false)
    public Boolean getIsCredentialsExpired() {
        return this.isCredentialsExpired;
    }

    public void setIsCredentialsExpired(Boolean isCredentialsExpired) {
        this.isCredentialsExpired = isCredentialsExpired;
    }

    @Column(name = "login_failure_count", nullable = false)
    public Integer getLoginFailureCount() {
        return this.loginFailureCount;
    }

    public void setLoginFailureCount(Integer loginFailureCount) {
        this.loginFailureCount = loginFailureCount;
    }

    @Column(name = "locked_date")
    public Date getLockedDate() {
        return this.lockedDate;
    }

    public void setLockedDate(Date lockedDate) {
        this.lockedDate = lockedDate;
    }

    @Column(name = "login_date")
    public Date getLoginDate() {
        return this.loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    @Column(name = "login_ip", length = 50)
    public String getLoginIp() {
        return this.loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    @Column(name = "is_system")
    public Boolean getIsSystem() {
        return this.isSystem;
    }

    public void setIsSystem(Boolean isSystem) {
        this.isSystem = isSystem;
    }

    @Column(name = "description", length = 255)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "grade", precision = 1)
    public Integer getGrade() {
        return this.grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    @Column(name = "sex")
    public String getSex() {
        return this.sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Column(name = "XX")
    public String getXx() {
        return this.xx;
    }

    public void setXx(String xx) {
        this.xx = xx;
    }

    @Column(name = "XZ")
    public String getXz() {
        return this.xz;
    }

    public void setXz(String xz) {
        this.xz = xz;
    }

    @Column(name = "RZSJ")
    public String getRzsj() {
        return this.rzsj;
    }

    public void setRzsj(String rzsj) {
        this.rzsj = rzsj;
    }

    @Column(name = "QQ")
    public String getQq() {
        return this.qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    @Column(name = "LXDZ")
    public String getLxdz() {
        return this.lxdz;
    }

    public void setLxdz(String lxdz) {
        this.lxdz = lxdz;
    }

    @Column(name = "JJLX")
    public String getJjlx() {
        return this.jjlx;
    }

    public void setJjlx(String jjlx) {
        this.jjlx = jjlx;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    @OrderBy("id asc")
    @JoinTable(name = "chee_user_role", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = { @JoinColumn(name = "role_id") })
    public Set<Role> getRoleSet() {
        return this.roleSet;
    }

    public void setRoleSet(Set<Role> roleSet) {
        this.roleSet = roleSet;
    }

    @Column(name = "BZ")
    public String getBz() {
        return this.bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    @Transient
    public boolean isEnabled() {
        return this.isAccountEnabled.booleanValue();
    }

    @Transient
    public boolean isAccountNonLocked() {
        return !this.isAccountLocked.booleanValue();
    }

    @Transient
    public boolean isAccountNonExpired() {
        return !this.isAccountExpired.booleanValue();
    }

    @Transient
    public boolean isCredentialsNonExpired() {
        return !this.isCredentialsExpired.booleanValue();
    }

    @Transient
    public List<Integer> getRoleIds() {
        return ConvertUtils.convertElementPropertyToList(this.roleSet, "id");
    }

    @Transient
    public String getRoleValues() {
        return ConvertUtils.convertElementPropertyToString(this.roleSet, "value", ",");
    }

    @Transient
    public String getRoleNames() {
        return ConvertUtils.convertElementPropertyToString(this.roleSet, "name", ",");
    }

    @Transient
    public String getDepartmentCode() {
        return this.departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    @Transient
    public String getDepartmentName() {
        return this.departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @Transient
    public String getPasswordConfirm() {
        return this.passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    @Transient
    public String getPasswordCurrent() {
        return this.passwordCurrent;
    }

    public void setPasswordCurrent(String passwordCurrent) {
        this.passwordCurrent = passwordCurrent;
    }

    @Transient
    public Integer getUserDepartmentId() {
        return this.userDepartmentId;
    }

    public void setUserDepartmentId(Integer userDepartmentId) {
        this.userDepartmentId = userDepartmentId;
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
        User other = (User) obj;
        if (this.id == null) {
            if (other.getId() != null) {
                return false;
            }
        } else if (!this.id.equals(other.getId())) {
            return false;
        }
        return true;
    }

    @Transient
    public GrantedAuthority[] getAuthorities() {
        return this.authorities;
    }

    public void setAuthorities(GrantedAuthority[] authorities) {
        this.authorities = authorities;
    }

    public String toString() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("id", this.id+"");
        map.put("username", this.username);
        map.put("name", this.name);
        Gson gson = new Gson();
        String s = gson.toJson(map);
        return s;
    }
}
