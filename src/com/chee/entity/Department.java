/*   1:    */ package com.chee.entity;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.Date;
/*   5:    */ import java.util.Set;
/*   6:    */ import javax.persistence.Column;
/*   7:    */ import javax.persistence.Entity;
/*   8:    */ import javax.persistence.FetchType;
/*   9:    */ import javax.persistence.GeneratedValue;
/*  10:    */ import javax.persistence.GenerationType;
/*  11:    */ import javax.persistence.Id;
/*  12:    */ import javax.persistence.JoinColumn;
/*  13:    */ import javax.persistence.ManyToOne;
/*  14:    */ import javax.persistence.OneToMany;
/*  15:    */ import javax.persistence.OrderBy;
/*  16:    */ import javax.persistence.Table;
/*  17:    */ import org.hibernate.annotations.Fetch;
/*  18:    */ import org.hibernate.annotations.FetchMode;
/*  19:    */ import org.hibernate.annotations.GenericGenerator;
/*  20:    */ 
/*  21:    */ @Entity
/*  22:    */ @Table(name="chee_department")
/*  23:    */ public class Department
/*  24:    */   implements Serializable
/*  25:    */ {
/*  26:    */   private static final long serialVersionUID = 1L;
/*  27:    */   private Integer id;
/*  28:    */   private String name;
/*  29:    */   private String code;
/*  30:    */   private Department parent;
/*  31:    */   private Integer grade;
/*  32:    */   private Integer sort;
/*  33:    */   private String path;
/*  34:    */   private Date createDate;
/*  35:    */   private Date updateDate;
/*  36:    */   private Set<Department> children;
/*  37:    */   private Set<User> userSet;
/*  38:    */   private String description;
/*  39:    */   
/*  40:    */   @Id
/*  41:    */   @GenericGenerator(name="paymentableGenerator", strategy="native")
/*  42:    */   @GeneratedValue(generator="paymentableGenerator", strategy=GenerationType.AUTO)
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
/*  54:    */   @Column(name="org_name", length=50, nullable=true)
/*  55:    */   public String getName()
/*  56:    */   {
/*  57: 52 */     return this.name;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void setName(String name)
/*  61:    */   {
/*  62: 56 */     this.name = name;
/*  63:    */   }
/*  64:    */   
/*  65:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  66:    */   @JoinColumn(name="parent_id", referencedColumnName="id")
/*  67:    */   public Department getParent()
/*  68:    */   {
/*  69: 62 */     return this.parent;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void setParent(Department parent)
/*  73:    */   {
/*  74: 66 */     this.parent = parent;
/*  75:    */   }
/*  76:    */   
/*  77:    */   @Column(name="grade", precision=11)
/*  78:    */   public Integer getGrade()
/*  79:    */   {
/*  80: 71 */     return this.grade;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void setGrade(Integer grade)
/*  84:    */   {
/*  85: 75 */     this.grade = grade;
/*  86:    */   }
/*  87:    */   
/*  88:    */   @Column(name="sort", precision=11)
/*  89:    */   public Integer getSort()
/*  90:    */   {
/*  91: 80 */     return this.sort;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void setSort(Integer sort)
/*  95:    */   {
/*  96: 84 */     this.sort = sort;
/*  97:    */   }
/*  98:    */   
/*  99:    */   @Column(name="path", length=255)
/* 100:    */   public String getPath()
/* 101:    */   {
/* 102: 89 */     return this.path;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void setPath(String path)
/* 106:    */   {
/* 107: 93 */     this.path = path;
/* 108:    */   }
/* 109:    */   
/* 110:    */   @Column(name="description", length=255)
/* 111:    */   public String getDescription()
/* 112:    */   {
/* 113: 98 */     return this.description;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void setDescription(String description)
/* 117:    */   {
/* 118:102 */     this.description = description;
/* 119:    */   }
/* 120:    */   
/* 121:    */   @OneToMany(mappedBy="parent", fetch=FetchType.LAZY)
/* 122:    */   @Fetch(FetchMode.SUBSELECT)
/* 123:    */   @OrderBy("sort asc")
/* 124:    */   public Set<Department> getChildren()
/* 125:    */   {
/* 126:109 */     return this.children;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void setChildren(Set<Department> children)
/* 130:    */   {
/* 131:113 */     this.children = children;
/* 132:    */   }
/* 133:    */   
/* 134:    */   @OneToMany(mappedBy="department", fetch=FetchType.LAZY)
/* 135:    */   @Fetch(FetchMode.SUBSELECT)
/* 136:    */   public Set<User> getUserSet()
/* 137:    */   {
/* 138:119 */     return this.userSet;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void setUserSet(Set<User> userSet)
/* 142:    */   {
/* 143:123 */     this.userSet = userSet;
/* 144:    */   }
/* 145:    */   
/* 146:    */   @Column(name="create_date")
/* 147:    */   public Date getCreateDate()
/* 148:    */   {
/* 149:128 */     return this.createDate;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void setCreateDate(Date createDate)
/* 153:    */   {
/* 154:132 */     this.createDate = createDate;
/* 155:    */   }
/* 156:    */   
/* 157:    */   @Column(name="update_date")
/* 158:    */   public Date getUpdateDate()
/* 159:    */   {
/* 160:137 */     return this.updateDate;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void setUpdateDate(Date updateDate)
/* 164:    */   {
/* 165:141 */     this.updateDate = updateDate;
/* 166:    */   }
/* 167:    */   
/* 168:    */   @Column(name="org_code", length=20, unique=true)
/* 169:    */   public String getCode()
/* 170:    */   {
/* 171:146 */     return this.code;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public void setCode(String code)
/* 175:    */   {
/* 176:150 */     this.code = code;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public int hashCode()
/* 180:    */   {
/* 181:154 */     return this.id == null ? System.identityHashCode(this) : this.id.hashCode();
/* 182:    */   }
/* 183:    */   
/* 184:    */   public boolean equals(Object obj)
/* 185:    */   {
/* 186:158 */     if (this == obj) {
/* 187:159 */       return true;
/* 188:    */     }
/* 189:161 */     if (obj == null) {
/* 190:162 */       return false;
/* 191:    */     }
/* 192:164 */     if (getClass().getPackage() != obj.getClass().getPackage()) {
/* 193:165 */       return false;
/* 194:    */     }
/* 195:167 */     Department other = (Department)obj;
/* 196:168 */     if (this.id == null)
/* 197:    */     {
/* 198:169 */       if (other.getId() != null) {
/* 199:170 */         return false;
/* 200:    */       }
/* 201:    */     }
/* 202:171 */     else if (!this.id.equals(other.getId())) {
/* 203:172 */       return false;
/* 204:    */     }
/* 205:174 */     return true;
/* 206:    */   }
/* 207:    */ }


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.entity.Department
 * JD-Core Version:    0.7.0.1
 */