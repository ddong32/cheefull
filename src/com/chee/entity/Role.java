/*   1:    */ package com.chee.entity;
/*   2:    */ 
/*   3:    */ import com.chee.util.ConvertUtils;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import java.util.Date;
/*   6:    */ import java.util.HashSet;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.Set;
/*   9:    */ import javax.persistence.Column;
/*  10:    */ import javax.persistence.Entity;
/*  11:    */ import javax.persistence.FetchType;
/*  12:    */ import javax.persistence.GeneratedValue;
/*  13:    */ import javax.persistence.GenerationType;
/*  14:    */ import javax.persistence.Id;
/*  15:    */ import javax.persistence.JoinTable;
/*  16:    */ import javax.persistence.ManyToMany;
/*  17:    */ import javax.persistence.OrderBy;
/*  18:    */ import javax.persistence.Table;
/*  19:    */ import javax.persistence.Transient;
/*  20:    */ import org.hibernate.annotations.Fetch;
/*  21:    */ import org.hibernate.annotations.FetchMode;
/*  22:    */ import org.hibernate.annotations.GenericGenerator;
/*  23:    */ 
/*  24:    */ @Entity
/*  25:    */ @Table(name="chee_role")
/*  26:    */ public class Role
/*  27:    */   implements Serializable
/*  28:    */ {
/*  29:    */   private static final long serialVersionUID = 1L;
/*  30:    */   private Integer id;
/*  31:    */   private String name;
/*  32:    */   private String value;
/*  33:    */   private String description;
/*  34:    */   private Boolean isSystem;
/*  35:    */   private Date createDate;
/*  36:    */   private Date updateDate;
/*  37: 35 */   private Set<User> userSet = new HashSet();
/*  38: 36 */   private Set<Resource> resourceSet = new HashSet();
/*  39:    */   
/*  40:    */   @Id
/*  41:    */   @GenericGenerator(name="paymentableGenerator", strategy="native")
/*  42:    */   @GeneratedValue(strategy=GenerationType.AUTO)
/*  43:    */   @Column(name="ID", precision=11, scale=0)
/*  44:    */   public Integer getId()
/*  45:    */   {
/*  46: 43 */     return this.id;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void setId(Integer id)
/*  50:    */   {
/*  51: 47 */     this.id = id;
/*  52:    */   }
/*  53:    */   
/*  54:    */   @Column(name="create_date")
/*  55:    */   public Date getCreateDate()
/*  56:    */   {
/*  57: 52 */     return this.createDate;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void setCreateDate(Date createDate)
/*  61:    */   {
/*  62: 56 */     this.createDate = createDate;
/*  63:    */   }
/*  64:    */   
/*  65:    */   @Column(name="update_date")
/*  66:    */   public Date getUpdateDate()
/*  67:    */   {
/*  68: 61 */     return this.updateDate;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void setUpdateDate(Date updateDate)
/*  72:    */   {
/*  73: 65 */     this.updateDate = updateDate;
/*  74:    */   }
/*  75:    */   
/*  76:    */   @Column(name="name", length=50, nullable=false, unique=true)
/*  77:    */   public String getName()
/*  78:    */   {
/*  79: 70 */     return this.name;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void setName(String name)
/*  83:    */   {
/*  84: 74 */     this.name = name;
/*  85:    */   }
/*  86:    */   
/*  87:    */   @Column(name="value", length=255, nullable=false, unique=true)
/*  88:    */   public String getValue()
/*  89:    */   {
/*  90: 79 */     return this.value;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void setValue(String value)
/*  94:    */   {
/*  95: 83 */     this.value = value;
/*  96:    */   }
/*  97:    */   
/*  98:    */   @Column(name="description", length=255)
/*  99:    */   public String getDescription()
/* 100:    */   {
/* 101: 88 */     return this.description;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void setDescription(String description)
/* 105:    */   {
/* 106: 92 */     this.description = description;
/* 107:    */   }
/* 108:    */   
/* 109:    */   @ManyToMany(fetch=FetchType.LAZY, mappedBy="roleSet")
/* 110:    */   public Set<User> getUserSet()
/* 111:    */   {
/* 112: 97 */     return this.userSet;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void setUserSet(Set<User> userSet)
/* 116:    */   {
/* 117:101 */     this.userSet = userSet;
/* 118:    */   }
/* 119:    */   
/* 120:    */   @ManyToMany(fetch=FetchType.LAZY)
/* 121:    */   @Fetch(FetchMode.SUBSELECT)
/* 122:    */   @OrderBy("sort desc")
/* 123:    */   @JoinTable(name="chee_role_resource", joinColumns={@javax.persistence.JoinColumn(name="role_id")}, inverseJoinColumns={@javax.persistence.JoinColumn(name="resource_id")})
/* 124:    */   public Set<Resource> getResourceSet()
/* 125:    */   {
/* 126:111 */     return this.resourceSet;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void setResourceSet(Set<Resource> resourceSet)
/* 130:    */   {
/* 131:115 */     this.resourceSet = resourceSet;
/* 132:    */   }
/* 133:    */   
/* 134:    */   @Column(name="is_system")
/* 135:    */   public Boolean getIsSystem()
/* 136:    */   {
/* 137:120 */     return this.isSystem;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public void setIsSystem(Boolean isSystem)
/* 141:    */   {
/* 142:124 */     this.isSystem = isSystem;
/* 143:    */   }
/* 144:    */   
/* 145:    */   @Transient
/* 146:    */   public String getResourceNames()
/* 147:    */   {
/* 148:129 */     return ConvertUtils.convertElementPropertyToString(this.resourceSet, "name", ",");
/* 149:    */   }
/* 150:    */   
/* 151:    */   @Transient
/* 152:    */   public String getResourceIdStr()
/* 153:    */   {
/* 154:134 */     return ConvertUtils.convertElementPropertyToString(this.resourceSet, "id", ",");
/* 155:    */   }
/* 156:    */   
/* 157:    */   @Transient
/* 158:    */   public List<String> getResourceIds()
/* 159:    */   {
/* 160:140 */     return ConvertUtils.convertElementPropertyToList(this.resourceSet, "id");
/* 161:    */   }
/* 162:    */   
/* 163:    */   public int hashCode()
/* 164:    */   {
/* 165:144 */     return this.id == null ? System.identityHashCode(this) : this.id.hashCode();
/* 166:    */   }
/* 167:    */   
/* 168:    */   public boolean equals(Object obj)
/* 169:    */   {
/* 170:148 */     if (this == obj) {
/* 171:149 */       return true;
/* 172:    */     }
/* 173:151 */     if (obj == null) {
/* 174:152 */       return false;
/* 175:    */     }
/* 176:154 */     if (getClass().getPackage() != obj.getClass().getPackage()) {
/* 177:155 */       return false;
/* 178:    */     }
/* 179:157 */     Role other = (Role)obj;
/* 180:158 */     if (this.id == null)
/* 181:    */     {
/* 182:159 */       if (other.getId() != null) {
/* 183:160 */         return false;
/* 184:    */       }
/* 185:    */     }
/* 186:161 */     else if (!this.id.equals(other.getId())) {
/* 187:162 */       return false;
/* 188:    */     }
/* 189:164 */     return true;
/* 190:    */   }
/* 191:    */ }


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.entity.Role
 * JD-Core Version:    0.7.0.1
 */