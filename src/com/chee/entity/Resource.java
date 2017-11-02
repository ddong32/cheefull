/*   1:    */ package com.chee.entity;
/*   2:    */ 
/*   3:    */ import com.chee.util.ReflectionUtils;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import java.util.Date;
/*   6:    */ import java.util.HashSet;
/*   7:    */ import java.util.Set;
/*   8:    */ import javax.persistence.Column;
/*   9:    */ import javax.persistence.Entity;
/*  10:    */ import javax.persistence.FetchType;
/*  11:    */ import javax.persistence.GeneratedValue;
/*  12:    */ import javax.persistence.GenerationType;
/*  13:    */ import javax.persistence.Id;
/*  14:    */ import javax.persistence.JoinColumn;
/*  15:    */ import javax.persistence.ManyToMany;
/*  16:    */ import javax.persistence.ManyToOne;
/*  17:    */ import javax.persistence.OneToMany;
/*  18:    */ import javax.persistence.OrderBy;
/*  19:    */ import javax.persistence.Table;
/*  20:    */ import javax.persistence.Transient;
/*  21:    */ import org.hibernate.annotations.Fetch;
/*  22:    */ import org.hibernate.annotations.FetchMode;
/*  23:    */ import org.hibernate.annotations.GenericGenerator;
/*  24:    */ 
/*  25:    */ @Entity
/*  26:    */ @Table(name="chee_resource")
/*  27:    */ public class Resource
/*  28:    */   implements Serializable
/*  29:    */ {
/*  30:    */   private static final long serialVersionUID = -3126052566856228265L;
/*  31:    */   private Integer id;
/*  32:    */   private String name;
/*  33:    */   private String value;
/*  34:    */   private String description;
/*  35:    */   private Boolean isSystem;
/*  36:    */   private Integer sort;
/*  37:    */   private Resource parent;
/*  38:    */   private Integer grade;
/*  39:    */   private String path;
/*  40:    */   private Date createDate;
/*  41:    */   private Date updateDate;
/*  42:    */   private Set<Resource> children;
/*  43: 41 */   private Set<Role> roleSet = new HashSet();
/*  44:    */   
/*  45:    */   @Id
/*  46:    */   @GenericGenerator(name="paymentableGenerator", strategy="native")
/*  47:    */   @GeneratedValue(strategy=GenerationType.AUTO)
/*  48:    */   @Column(name="ID", precision=11, scale=0)
/*  49:    */   public Integer getId()
/*  50:    */   {
/*  51: 48 */     return this.id;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void setId(Integer id)
/*  55:    */   {
/*  56: 52 */     this.id = id;
/*  57:    */   }
/*  58:    */   
/*  59:    */   @Column(name="name", length=255, nullable=false)
/*  60:    */   public String getName()
/*  61:    */   {
/*  62: 57 */     return this.name;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void setName(String name)
/*  66:    */   {
/*  67: 61 */     this.name = name;
/*  68:    */   }
/*  69:    */   
/*  70:    */   @Column(name="value", length=255)
/*  71:    */   public String getValue()
/*  72:    */   {
/*  73: 66 */     return this.value;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void setValue(String value)
/*  77:    */   {
/*  78: 70 */     this.value = value;
/*  79:    */   }
/*  80:    */   
/*  81:    */   @Column(name="description", length=255)
/*  82:    */   public String getDescription()
/*  83:    */   {
/*  84: 75 */     return this.description;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void setDescription(String description)
/*  88:    */   {
/*  89: 79 */     this.description = description;
/*  90:    */   }
/*  91:    */   
/*  92:    */   @Column(name="sort", precision=11)
/*  93:    */   public Integer getSort()
/*  94:    */   {
/*  95: 84 */     return this.sort;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void setSort(Integer sort)
/*  99:    */   {
/* 100: 88 */     this.sort = sort;
/* 101:    */   }
/* 102:    */   
/* 103:    */   @Column(name="is_system")
/* 104:    */   public Boolean getIsSystem()
/* 105:    */   {
/* 106: 93 */     return this.isSystem;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void setIsSystem(Boolean isSystem)
/* 110:    */   {
/* 111: 97 */     this.isSystem = isSystem;
/* 112:    */   }
/* 113:    */   
/* 114:    */   @ManyToMany(mappedBy="resourceSet", fetch=FetchType.EAGER)
/* 115:    */   @Fetch(FetchMode.SUBSELECT)
/* 116:    */   public Set<Role> getRoleSet()
/* 117:    */   {
/* 118:103 */     return this.roleSet;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void setRoleSet(Set<Role> roleSet)
/* 122:    */   {
/* 123:107 */     this.roleSet = roleSet;
/* 124:    */   }
/* 125:    */   
/* 126:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 127:    */   @JoinColumn(name="parent_id", referencedColumnName="id")
/* 128:    */   public Resource getParent()
/* 129:    */   {
/* 130:113 */     return this.parent;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void setParent(Resource parent)
/* 134:    */   {
/* 135:117 */     this.parent = parent;
/* 136:    */   }
/* 137:    */   
/* 138:    */   @OneToMany(mappedBy="parent", fetch=FetchType.LAZY)
/* 139:    */   @Fetch(FetchMode.SUBSELECT)
/* 140:    */   @OrderBy("sort asc")
/* 141:    */   public Set<Resource> getChildren()
/* 142:    */   {
/* 143:124 */     return this.children;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public void setChildren(Set<Resource> children)
/* 147:    */   {
/* 148:128 */     this.children = children;
/* 149:    */   }
/* 150:    */   
/* 151:    */   @Column(name="grade", precision=11)
/* 152:    */   public Integer getGrade()
/* 153:    */   {
/* 154:133 */     return this.grade;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public void setGrade(Integer grade)
/* 158:    */   {
/* 159:137 */     this.grade = grade;
/* 160:    */   }
/* 161:    */   
/* 162:    */   @Column(name="path", length=255)
/* 163:    */   public String getPath()
/* 164:    */   {
/* 165:142 */     return this.path;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public void setPath(String path)
/* 169:    */   {
/* 170:146 */     this.path = path;
/* 171:    */   }
/* 172:    */   
/* 173:    */   @Transient
/* 174:    */   public String getRoleValues()
/* 175:    */   {
/* 176:151 */     return ReflectionUtils.convertElementPropertyToString(this.roleSet, "value", ",");
/* 177:    */   }
/* 178:    */   
/* 179:    */   @Column(name="create_date")
/* 180:    */   public Date getCreateDate()
/* 181:    */   {
/* 182:156 */     return this.createDate;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public void setCreateDate(Date createDate)
/* 186:    */   {
/* 187:160 */     this.createDate = createDate;
/* 188:    */   }
/* 189:    */   
/* 190:    */   @Column(name="update_date")
/* 191:    */   public Date getUpdateDate()
/* 192:    */   {
/* 193:165 */     return this.updateDate;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public void setUpdateDate(Date updateDate)
/* 197:    */   {
/* 198:169 */     this.updateDate = updateDate;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public int hashCode()
/* 202:    */   {
/* 203:173 */     return this.id == null ? System.identityHashCode(this) : this.id.hashCode();
/* 204:    */   }
/* 205:    */   
/* 206:    */   public boolean equals(Object obj)
/* 207:    */   {
/* 208:177 */     if (this == obj) {
/* 209:178 */       return true;
/* 210:    */     }
/* 211:180 */     if (obj == null) {
/* 212:181 */       return false;
/* 213:    */     }
/* 214:183 */     if (getClass().getPackage() != obj.getClass().getPackage()) {
/* 215:184 */       return false;
/* 216:    */     }
/* 217:186 */     Resource other = (Resource)obj;
/* 218:187 */     if (this.id == null)
/* 219:    */     {
/* 220:188 */       if (other.getId() != null) {
/* 221:189 */         return false;
/* 222:    */       }
/* 223:    */     }
/* 224:190 */     else if (!this.id.equals(other.getId())) {
/* 225:191 */       return false;
/* 226:    */     }
/* 227:193 */     return true;
/* 228:    */   }
/* 229:    */ }


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.entity.Resource
 * JD-Core Version:    0.7.0.1
 */