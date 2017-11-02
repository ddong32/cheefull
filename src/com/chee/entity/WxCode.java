/*   1:    */ package com.chee.entity;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.Date;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Set;
/*   8:    */ import javax.persistence.Column;
/*   9:    */ import javax.persistence.Entity;
/*  10:    */ import javax.persistence.FetchType;
/*  11:    */ import javax.persistence.GeneratedValue;
/*  12:    */ import javax.persistence.GenerationType;
/*  13:    */ import javax.persistence.Id;
/*  14:    */ import javax.persistence.JoinColumn;
/*  15:    */ import javax.persistence.ManyToOne;
/*  16:    */ import javax.persistence.OneToMany;
/*  17:    */ import javax.persistence.OrderBy;
/*  18:    */ import javax.persistence.Table;
/*  19:    */ import javax.persistence.Transient;
/*  20:    */ import org.hibernate.annotations.Fetch;
/*  21:    */ import org.hibernate.annotations.FetchMode;
/*  22:    */ import org.hibernate.annotations.GenericGenerator;
/*  23:    */ 
/*  24:    */ @Entity
/*  25:    */ @Table(name="chwx_code")
/*  26:    */ public class WxCode
/*  27:    */   implements Serializable
/*  28:    */ {
/*  29:    */   private static final long serialVersionUID = 1L;
/*  30:    */   private Integer id;
/*  31:    */   private String title;
/*  32:    */   private String text;
/*  33:    */   private Integer markdown_enable;
/*  34:    */   private Integer order_number;
/*  35:    */   private String status;
/*  36:    */   private Date created;
/*  37:    */   private Date modified;
/*  38:    */   private String flag;
/*  39:    */   private String module;
/*  40:    */   private WxCode parent;
/*  41:    */   private Set<WxCode> children;
/*  42:    */   private List<WxCode> childList;
/*  43:    */   
/*  44:    */   @Id
/*  45:    */   @GenericGenerator(name="paymentableGenerator", strategy="native")
/*  46:    */   @GeneratedValue(strategy=GenerationType.AUTO)
/*  47:    */   @Column(name="ID", precision=10, scale=0)
/*  48:    */   public Integer getId()
/*  49:    */   {
/*  50: 50 */     return this.id;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void setId(Integer id)
/*  54:    */   {
/*  55: 54 */     this.id = id;
/*  56:    */   }
/*  57:    */   
/*  58:    */   @Column(name="title")
/*  59:    */   public String getTitle()
/*  60:    */   {
/*  61: 59 */     return this.title;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void setTitle(String title)
/*  65:    */   {
/*  66: 63 */     this.title = title;
/*  67:    */   }
/*  68:    */   
/*  69:    */   @Column(name="text")
/*  70:    */   public String getText()
/*  71:    */   {
/*  72: 68 */     return this.text;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void setText(String text)
/*  76:    */   {
/*  77: 72 */     this.text = text;
/*  78:    */   }
/*  79:    */   
/*  80:    */   @Column(name="markdown_enable")
/*  81:    */   public Integer getMarkdown_enable()
/*  82:    */   {
/*  83: 77 */     return this.markdown_enable;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void setMarkdown_enable(Integer markdown_enable)
/*  87:    */   {
/*  88: 81 */     this.markdown_enable = markdown_enable;
/*  89:    */   }
/*  90:    */   
/*  91:    */   @Column(name="order_number")
/*  92:    */   public Integer getOrder_number()
/*  93:    */   {
/*  94: 86 */     return this.order_number;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void setOrder_number(Integer order_number)
/*  98:    */   {
/*  99: 90 */     this.order_number = order_number;
/* 100:    */   }
/* 101:    */   
/* 102:    */   @Column(name="status")
/* 103:    */   public String getStatus()
/* 104:    */   {
/* 105: 95 */     return this.status;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void setStatus(String status)
/* 109:    */   {
/* 110: 99 */     this.status = status;
/* 111:    */   }
/* 112:    */   
/* 113:    */   @Column(name="created ")
/* 114:    */   public Date getCreated()
/* 115:    */   {
/* 116:104 */     return this.created;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void setCreated(Date created)
/* 120:    */   {
/* 121:108 */     this.created = created;
/* 122:    */   }
/* 123:    */   
/* 124:    */   @Column(name="modified")
/* 125:    */   public Date getModified()
/* 126:    */   {
/* 127:113 */     return this.modified;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void setModified(Date modified)
/* 131:    */   {
/* 132:117 */     this.modified = modified;
/* 133:    */   }
/* 134:    */   
/* 135:    */   @Column(name="flag")
/* 136:    */   public String getFlag()
/* 137:    */   {
/* 138:122 */     return this.flag;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void setFlag(String flag)
/* 142:    */   {
/* 143:126 */     this.flag = flag;
/* 144:    */   }
/* 145:    */   
/* 146:    */   @Column(name="module")
/* 147:    */   public String getModule()
/* 148:    */   {
/* 149:131 */     return this.module;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void setModule(String module)
/* 153:    */   {
/* 154:135 */     this.module = module;
/* 155:    */   }
/* 156:    */   
/* 157:    */   @ManyToOne(fetch=FetchType.LAZY)
/* 158:    */   @JoinColumn(name="parent_id", referencedColumnName="id")
/* 159:    */   public WxCode getParent()
/* 160:    */   {
/* 161:141 */     return this.parent;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public void setParent(WxCode parent)
/* 165:    */   {
/* 166:145 */     this.parent = parent;
/* 167:    */   }
/* 168:    */   
/* 169:    */   @OneToMany(mappedBy="parent", fetch=FetchType.LAZY)
/* 170:    */   @Fetch(FetchMode.SUBSELECT)
/* 171:    */   @OrderBy("order_number")
/* 172:    */   public Set<WxCode> getChildren()
/* 173:    */   {
/* 174:152 */     return this.children;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public void setChildren(Set<WxCode> children)
/* 178:    */   {
/* 179:156 */     this.children = children;
/* 180:    */   }
/* 181:    */   
/* 182:    */   @Transient
/* 183:    */   public void addChild(WxCode child)
/* 184:    */   {
/* 185:161 */     if (this.childList == null) {
/* 186:162 */       this.childList = new ArrayList();
/* 187:    */     }
/* 188:166 */     if (!this.childList.contains(child)) {
/* 189:167 */       this.childList.add(child);
/* 190:    */     }
/* 191:    */   }
/* 192:    */   
/* 193:    */   @Transient
/* 194:    */   public List<WxCode> getChildList()
/* 195:    */   {
/* 196:173 */     return this.childList;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public boolean hasChild()
/* 200:    */   {
/* 201:177 */     return (this.childList != null) && (!this.childList.isEmpty());
/* 202:    */   }
/* 203:    */   
/* 204:    */   public int hashCode()
/* 205:    */   {
/* 206:181 */     return this.id == null ? System.identityHashCode(this) : this.id.hashCode();
/* 207:    */   }
/* 208:    */   
/* 209:    */   public boolean equals(Object obj)
/* 210:    */   {
/* 211:185 */     if (this == obj) {
/* 212:186 */       return true;
/* 213:    */     }
/* 214:188 */     if (obj == null) {
/* 215:189 */       return false;
/* 216:    */     }
/* 217:191 */     if (getClass().getPackage() != obj.getClass().getPackage()) {
/* 218:192 */       return false;
/* 219:    */     }
/* 220:194 */     WxCode other = (WxCode)obj;
/* 221:195 */     if (this.id == null)
/* 222:    */     {
/* 223:196 */       if (other.getId() != null) {
/* 224:197 */         return false;
/* 225:    */       }
/* 226:    */     }
/* 227:198 */     else if (!this.id.equals(other.getId())) {
/* 228:199 */       return false;
/* 229:    */     }
/* 230:201 */     return true;
/* 231:    */   }
/* 232:    */ }


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.entity.WxCode
 * JD-Core Version:    0.7.0.1
 */