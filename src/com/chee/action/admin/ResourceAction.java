/*   1:    */ package com.chee.action.admin;
/*   2:    */ 
/*   3:    */ import com.chee.common.Page;
/*   4:    */ import com.chee.service.ResourceService;
/*   5:    */ import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
/*   6:    */ import com.opensymphony.xwork2.validator.annotations.Validations;
/*   7:    */ import java.util.ArrayList;
/*   8:    */ import java.util.Date;
/*   9:    */ import java.util.HashMap;
/*  10:    */ import java.util.List;
/*  11:    */ import java.util.Map;
/*  12:    */ import java.util.Set;
/*  13:    */ import net.sf.json.JSONArray;
/*  14:    */ import net.sf.json.JSONObject;
/*  15:    */ import org.apache.struts2.convention.annotation.ParentPackage;
/*  16:    */ import org.apache.struts2.convention.annotation.Results;
/*  17:    */ import org.hibernate.criterion.DetachedCriteria;
/*  18:    */ import org.hibernate.criterion.Restrictions;
/*  19:    */ import org.springframework.beans.BeanUtils;
/*  20:    */ 
/*  21:    */ @ParentPackage("admin")
/*  22:    */ @Results({@org.apache.struts2.convention.annotation.Result(name="index", location="resource!list.action", type="redirect")})
/*  23:    */ public class ResourceAction
/*  24:    */   extends BaseAction<com.chee.entity.Resource, Integer>
/*  25:    */ {
/*  26:    */   private static final long serialVersionUID = -1066168819528324882L;
/*  27:    */   private com.chee.entity.Resource resource;
/*  28:    */   private com.chee.entity.Resource parent;
/*  29:    */   @javax.annotation.Resource
/*  30:    */   private ResourceService resourceService;
/*  31:    */   
/*  32:    */   public String list()
/*  33:    */   {
/*  34: 31 */     DetachedCriteria detachedCriteria = DetachedCriteria.forClass(com.chee.entity.Resource.class);
/*  35: 32 */     if (this.resource != null)
/*  36:    */     {
/*  37: 33 */       if ((this.resource.getName() != null) && (!"".equals(this.resource.getName()))) {
/*  38: 34 */         detachedCriteria.add(Restrictions.like("name", "%" + this.resource.getName() + "%"));
/*  39:    */       }
/*  40: 36 */       if ((this.resource.getValue() != null) && (!"".equals(this.resource.getValue()))) {
/*  41: 37 */         detachedCriteria.add(Restrictions.like("value", "%" + this.resource.getName() + "%"));
/*  42:    */       }
/*  43:    */     }
/*  44: 40 */     this.page.setOrder("asc");
/*  45: 41 */     this.page.setOrderBy("sort");
/*  46: 42 */     this.page.setPageSize(1000);
/*  47: 43 */     this.page = this.resourceService.findPage(this.page, detachedCriteria);
/*  48: 44 */     return "list";
/*  49:    */   }
/*  50:    */   
/*  51:    */   public String delete()
/*  52:    */   {
/*  53: 48 */     this.resourceService.delete(this.resource.getId());
/*  54: 49 */     this.redirectionUrl = "resource!list.action";
/*  55: 50 */     return "success";
/*  56:    */   }
/*  57:    */   
/*  58:    */   public String input()
/*  59:    */   {
/*  60: 54 */     if ((this.parent != null) && (this.parent.getId() != null)) {
/*  61: 55 */       this.parent = ((com.chee.entity.Resource)this.resourceService.get(this.parent.getId()));
/*  62:    */     } else {
/*  63: 57 */       this.parent = new com.chee.entity.Resource();
/*  64:    */     }
/*  65: 59 */     if ((this.resource != null) && (this.resource.getId() != null)) {
/*  66: 60 */       this.resource = ((com.chee.entity.Resource)this.resourceService.load(this.resource.getId()));
/*  67:    */     } else {
/*  68: 62 */       this.resource = new com.chee.entity.Resource();
/*  69:    */     }
/*  70: 64 */     return "input";
/*  71:    */   }
/*  72:    */   
/*  73:    */   @Validations(requiredStrings={@com.opensymphony.xwork2.validator.annotations.RequiredStringValidator(fieldName="resource.name", message="资源名称不允许为空!")}, stringLengthFields={@com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator(fieldName="resource.name", maxLength="125", message="资源名称长度不允许大于${maxLength}!"), @com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator(fieldName="resource.value", maxLength="125", message="资源值长度不允许大于${maxLength}!"), @com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator(fieldName="resource.description", maxLength="125", message="描述长度不允许大于${maxLength}!")}, intRangeFields={@com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator(fieldName="resource.sort", min="0", message="排序必须为零或正整数!")})
/*  74:    */   @InputConfig(resultName="error")
/*  75:    */   public String save()
/*  76:    */   {
/*  77: 70 */     if (this.resource.getId() != null)
/*  78:    */     {
/*  79: 71 */       com.chee.entity.Resource persistent = (com.chee.entity.Resource)this.resourceService.load(this.resource.getId());
/*  80: 72 */       if (!this.resourceService.isUnique("name", persistent.getName(), this.resource.getName()))
/*  81:    */       {
/*  82: 73 */         addActionError("资源名称: " + this.resource.getName() + " 已经存在!请重新命名!");
/*  83: 74 */         return "error";
/*  84:    */       }
/*  85: 76 */       if (!this.resourceService.isUnique("value", persistent.getValue(), this.resource.getValue()))
/*  86:    */       {
/*  87: 77 */         addActionError("资源值: " + this.resource.getValue() + " 已经存在!请重新命名!");
/*  88: 78 */         return "error";
/*  89:    */       }
/*  90: 80 */       if (this.parent.getId() != null) {
/*  91: 81 */         persistent.setParent((com.chee.entity.Resource)this.resourceService.get(this.parent.getId()));
/*  92:    */       } else {
/*  93: 83 */         persistent.setParent(null);
/*  94:    */       }
/*  95: 85 */       BeanUtils.copyProperties(this.resource, persistent, new String[] { "id", "createDate", "updateDate", "path", "parent", "children", "isSystem", "roleSet" });
/*  96: 86 */       persistent.setUpdateDate(new Date());
/*  97: 87 */       this.resourceService.update(persistent);
/*  98:    */     }
/*  99:    */     else
/* 100:    */     {
/* 101: 89 */       if (!this.resourceService.isUnique("name", null, this.resource.getName()))
/* 102:    */       {
/* 103: 90 */         addActionError("权限名称: " + this.resource.getName() + " 已经存在!请重新命名!");
/* 104: 91 */         return "error";
/* 105:    */       }
/* 106: 93 */       if (!this.resourceService.isUnique("value", null, this.resource.getValue()))
/* 107:    */       {
/* 108: 94 */         addActionError("权限值: " + this.resource.getValue() + " 已经存在!请重新命名!");
/* 109: 95 */         return "error";
/* 110:    */       }
/* 111: 97 */       if (this.parent.getId() != null) {
/* 112: 98 */         this.resource.setParent((com.chee.entity.Resource)this.resourceService.get(this.parent.getId()));
/* 113:    */       } else {
/* 114:100 */         this.resource.setParent(null);
/* 115:    */       }
/* 116:102 */       this.resource.setCreateDate(new Date());
/* 117:103 */       this.resource.setUpdateDate(new Date());
/* 118:104 */       this.resourceService.save(this.resource);
/* 119:    */     }
/* 120:106 */     this.redirectionUrl = "resource!list.action";
/* 121:107 */     return "success";
/* 122:    */   }
/* 123:    */   
/* 124:    */   public String ajaxResourceTree()
/* 125:    */   {
/* 126:111 */     List<com.chee.entity.Resource> childrenResourceList = new ArrayList();
/* 127:112 */     if ((this.resource != null) && (this.resource.getId() != null)) {
/* 128:113 */       childrenResourceList = this.resourceService.getChildrenResourceList(this.resource);
/* 129:    */     } else {
/* 130:115 */       childrenResourceList = this.resourceService.getRootResourceList();
/* 131:    */     }
/* 132:117 */     List optionList = new ArrayList();
/* 133:118 */     for (com.chee.entity.Resource resource : childrenResourceList)
/* 134:    */     {
/* 135:119 */       Map map = new HashMap();
/* 136:120 */       if (resource.getChildren().size() > 0) {
/* 137:121 */         map.put("state", "closed");
/* 138:    */       }
/* 139:123 */       map.put("data", resource.getName());
/* 140:124 */       Map attrMap = new HashMap();
/* 141:125 */       attrMap.put("id", resource.getId().toString());
/* 142:126 */       attrMap.put("title", resource.getName());
/* 143:127 */       attrMap.put("Class", resource.getName());
/* 144:128 */       JSONObject attrObject = JSONObject.fromObject(attrMap);
/* 145:129 */       map.put("attr", attrObject);
/* 146:130 */       optionList.add(map);
/* 147:    */     }
/* 148:132 */     JSONArray jsonArray = JSONArray.fromObject(optionList);
/* 149:    */     
/* 150:134 */     return ajaxJson(jsonArray.toString());
/* 151:    */   }
/* 152:    */   
/* 153:    */   public com.chee.entity.Resource getResource()
/* 154:    */   {
/* 155:138 */     return this.resource;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public void setResource(com.chee.entity.Resource resource)
/* 159:    */   {
/* 160:142 */     this.resource = resource;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public com.chee.entity.Resource getParent()
/* 164:    */   {
/* 165:146 */     return this.parent;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public void setParent(com.chee.entity.Resource parent)
/* 169:    */   {
/* 170:150 */     this.parent = parent;
/* 171:    */   }
/* 172:    */ }


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.action.admin.ResourceAction
 * JD-Core Version:    0.7.0.1
 */