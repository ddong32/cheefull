/*   1:    */ package com.chee.template;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ 
/*   5:    */ public class Template
/*   6:    */ {
/*   7:    */   private String id;
/*   8:    */   private String title;
/*   9:    */   private String description;
/*  10:    */   private String author;
/*  11:    */   private String authorWebsite;
/*  12:    */   private String version;
/*  13:    */   private int versionCode;
/*  14:    */   private String updateUrl;
/*  15:    */   private String path;
/*  16:    */   private String renderType;
/*  17:    */   private String screenshot;
/*  18:    */   private List<TplModule> modules;
/*  19:    */   private List<Thumbnail> thumbnails;
/*  20:    */   
/*  21:    */   public String getId()
/*  22:    */   {
/*  23: 39 */     return this.id;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public void setId(String id)
/*  27:    */   {
/*  28: 43 */     this.id = id;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public String getTitle()
/*  32:    */   {
/*  33: 47 */     return this.title;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void setTitle(String title)
/*  37:    */   {
/*  38: 51 */     this.title = title;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public String getDescription()
/*  42:    */   {
/*  43: 55 */     return this.description;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void setDescription(String description)
/*  47:    */   {
/*  48: 59 */     this.description = description;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public String getAuthor()
/*  52:    */   {
/*  53: 63 */     return this.author;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void setAuthor(String author)
/*  57:    */   {
/*  58: 67 */     this.author = author;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public String getAuthorWebsite()
/*  62:    */   {
/*  63: 71 */     return this.authorWebsite;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void setAuthorWebsite(String authorWebsite)
/*  67:    */   {
/*  68: 75 */     this.authorWebsite = authorWebsite;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public String getVersion()
/*  72:    */   {
/*  73: 79 */     return this.version;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void setVersion(String version)
/*  77:    */   {
/*  78: 83 */     this.version = version;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public int getVersionCode()
/*  82:    */   {
/*  83: 87 */     return this.versionCode;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void setVersionCode(int versionCode)
/*  87:    */   {
/*  88: 91 */     this.versionCode = versionCode;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public List<TplModule> getModules()
/*  92:    */   {
/*  93: 95 */     return this.modules;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void setModules(List<TplModule> modules)
/*  97:    */   {
/*  98: 99 */     this.modules = modules;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public List<Thumbnail> getThumbnails()
/* 102:    */   {
/* 103:103 */     return this.thumbnails;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void setThumbnails(List<Thumbnail> thumbnails)
/* 107:    */   {
/* 108:107 */     this.thumbnails = thumbnails;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public String getUpdateUrl()
/* 112:    */   {
/* 113:111 */     return this.updateUrl;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void setUpdateUrl(String updateUrl)
/* 117:    */   {
/* 118:115 */     this.updateUrl = updateUrl;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public String getPath()
/* 122:    */   {
/* 123:119 */     return this.path;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void setPath(String path)
/* 127:    */   {
/* 128:123 */     this.path = path;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public String getRenderType()
/* 132:    */   {
/* 133:127 */     return this.renderType;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void setRenderType(String renderType)
/* 137:    */   {
/* 138:131 */     this.renderType = renderType;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public String getScreenshot()
/* 142:    */   {
/* 143:136 */     return this.screenshot;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public void setScreenshot(String screenshot)
/* 147:    */   {
/* 148:140 */     this.screenshot = screenshot;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public TplModule getModuleByName(String name)
/* 152:    */   {
/* 153:145 */     if ((this.modules != null) && (name != null)) {
/* 154:146 */       for (TplModule m : this.modules) {
/* 155:147 */         if (name.equals(m.getName())) {
/* 156:148 */           return m;
/* 157:    */         }
/* 158:    */       }
/* 159:    */     }
/* 160:152 */     return null;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public Thumbnail getThumbnailByName(String name)
/* 164:    */   {
/* 165:156 */     if ((this.thumbnails != null) && (name != null)) {
/* 166:157 */       for (Thumbnail t : this.thumbnails) {
/* 167:158 */         if (name.equals(t.getName())) {
/* 168:159 */           return t;
/* 169:    */         }
/* 170:    */       }
/* 171:    */     }
/* 172:163 */     return null;
/* 173:    */   }
/* 174:    */ }


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.template.Template
 * JD-Core Version:    0.7.0.1
 */