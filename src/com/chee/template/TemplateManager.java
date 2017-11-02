/*   1:    */ package com.chee.template;
/*   2:    */ 
/*   3:    */ import com.chee.util.StaticUtils;
/*   4:    */ import com.chee.util.StringUtils;
/*   5:    */ import com.jfinal.kit.PathKit;
/*   6:    */ import com.jfinal.kit.PropKit;
/*   7:    */ import java.io.File;
/*   8:    */ import java.io.FileFilter;
/*   9:    */ import java.util.ArrayList;
/*  10:    */ import java.util.Iterator;
/*  11:    */ import java.util.List;
/*  12:    */ import java.util.Map;
/*  13:    */ 
/*  14:    */ public class TemplateManager
/*  15:    */ {
/*  16:    */   private Template cTemplate;
/*  17: 33 */   private List<String> cTemplateHtmls = new ArrayList();
/*  18: 34 */   private List<String> cWechatTemplateHtmls = new ArrayList();
/*  19: 35 */   private List<String> cMobileTemplateHtmls = new ArrayList();
/*  20: 40 */   private static final TemplateManager me = new TemplateManager();
/*  21:    */   
/*  22:    */   public static TemplateManager me()
/*  23:    */   {
/*  24: 43 */     return me;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public boolean existsFile(String fileName)
/*  28:    */   {
/*  29: 47 */     return this.cTemplateHtmls.contains(fileName);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public boolean existsFileInWechat(String fileName)
/*  33:    */   {
/*  34: 51 */     return this.cWechatTemplateHtmls.contains(fileName);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public boolean existsFileInMobile(String fileName)
/*  38:    */   {
/*  39: 55 */     return this.cMobileTemplateHtmls.contains(fileName);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public boolean isSupportWechat()
/*  43:    */   {
/*  44: 59 */     return this.cWechatTemplateHtmls.size() > 0;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public boolean isSupportMobile()
/*  48:    */   {
/*  49: 63 */     return this.cMobileTemplateHtmls.size() > 0;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public String currentTemplatePath()
/*  53:    */   {
/*  54: 67 */     return currentTemplate().getPath();
/*  55:    */   }
/*  56:    */   
/*  57:    */   public List<TplModule> currentTemplateModules()
/*  58:    */   {
/*  59: 71 */     return currentTemplate().getModules();
/*  60:    */   }
/*  61:    */   
/*  62:    */   public String[] currentTemplateModulesAsArray()
/*  63:    */   {
/*  64: 75 */     List<TplModule> list = currentTemplate().getModules();
/*  65: 76 */     String[] modules = new String[list.size()];
/*  66: 77 */     for (int i = 0; i < modules.length; i++) {
/*  67: 78 */       modules[i] = ((TplModule)list.get(i)).getName();
/*  68:    */     }
/*  69: 80 */     return modules;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public TplModule currentTemplateModule(String name)
/*  73:    */   {
/*  74: 84 */     return currentTemplate().getModuleByName(name);
/*  75:    */   }
/*  76:    */   
/*  77:    */   public Thumbnail currentTemplateThumbnail(String name)
/*  78:    */   {
/*  79: 88 */     return currentTemplate().getThumbnailByName(name);
/*  80:    */   }
/*  81:    */   
/*  82:    */   public Template currentTemplate()
/*  83:    */   {
/*  84: 92 */     if (this.cTemplate == null)
/*  85:    */     {
/*  86: 94 */       List<Template> templateList = getTemplates();
/*  87:    */       
/*  88: 96 */       String templateId = (String)StaticUtils.getOptionMap().get("web_template_id");
/*  89: 98 */       if (StringUtils.isNotBlank(templateId)) {
/*  90: 99 */         for (Template tpl : templateList) {
/*  91:100 */           if (templateId.equals(tpl.getId())) {
/*  92:101 */             this.cTemplate = tpl;
/*  93:    */           }
/*  94:    */         }
/*  95:    */       }
/*  96:106 */       if (this.cTemplate == null) {
/*  97:107 */         templateId = PropKit.get("default_template");
/*  98:    */       }
/*  99:110 */       if (StringUtils.isBlank(templateId)) {
/* 100:111 */         throw new RuntimeException("default_template config error in jpress.properties.");
/* 101:    */       }
/* 102:114 */       for (Template tpl : templateList) {
/* 103:115 */         if (templateId.equals(tpl.getId())) {
/* 104:116 */           this.cTemplate = tpl;
/* 105:    */         }
/* 106:    */       }
/* 107:120 */       if (this.cTemplate == null) {
/* 108:121 */         throw new RuntimeException(
/* 109:122 */           "get current template error. please define correct template in jpress.properties.");
/* 110:    */       }
/* 111:125 */       File tDir = new File(PathKit.getWebRootPath(), this.cTemplate.getPath());
/* 112:    */       
/* 113:127 */       this.cTemplateHtmls.clear();
/* 114:128 */       this.cMobileTemplateHtmls.clear();
/* 115:129 */       this.cWechatTemplateHtmls.clear();
/* 116:    */       
/* 117:131 */       scanFillTemplate(tDir, this.cTemplateHtmls);
/* 118:132 */       scanFillTemplate(new File(tDir, "tpl_mobile"), this.cMobileTemplateHtmls);
/* 119:133 */       scanFillTemplate(new File(tDir, "tpl_wechat"), this.cWechatTemplateHtmls);
/* 120:    */     }
/* 121:137 */     return this.cTemplate;
/* 122:    */   }
/* 123:    */   
/* 124:    */   private void scanFillTemplate(File tDir, List<String> templates)
/* 125:    */   {
/* 126:141 */     if ((tDir.exists()) && (tDir.isDirectory()))
/* 127:    */     {
/* 128:142 */       File[] templateFiles = tDir.listFiles(new FileFilter()
/* 129:    */       {
/* 130:    */         public boolean accept(File pathname)
/* 131:    */         {
/* 132:144 */           return pathname.getName().endsWith(".html");
/* 133:    */         }
/* 134:    */       });
/* 135:147 */       if ((templateFiles != null) && (templateFiles.length > 0)) {
/* 136:148 */         for (int i = 0; i < templateFiles.length; i++) {
/* 137:149 */           templates.add(templateFiles[i].getName());
/* 138:    */         }
/* 139:    */       }
/* 140:    */     }
/* 141:    */   }
/* 142:    */   
/* 143:    */   public boolean doChangeTemplate(String templateId)
/* 144:    */   {
/* 145:156 */     if (StringUtils.isBlank(templateId)) {
/* 146:157 */       return false;
/* 147:    */     }
/* 148:160 */     List<Template> templateList = getTemplates();
/* 149:161 */     if ((templateList == null) || (templateList.isEmpty())) {
/* 150:162 */       return false;
/* 151:    */     }
/* 152:165 */     Template template = null;
/* 153:166 */     for (Template tpl : templateList) {
/* 154:167 */       if (templateId.equals(tpl.getId())) {
/* 155:168 */         template = tpl;
/* 156:    */       }
/* 157:    */     }
/* 158:172 */     if (template == null) {
/* 159:173 */       return false;
/* 160:    */     }
/* 161:180 */     this.cTemplate = null;
/* 162:181 */     return true;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public List<Template> getTemplates()
/* 166:    */   {
/* 167:186 */     String basePath = PathKit.getWebRootPath() + "/templates";
/* 168:    */     
/* 169:188 */     List<File> templateFolderList = new ArrayList();
/* 170:189 */     scanTemplateFloders(new File(basePath), templateFolderList);
/* 171:    */     
/* 172:191 */     List<Template> templatelist = null;
/* 173:192 */     if (templateFolderList.size() > 0)
/* 174:    */     {
/* 175:193 */       templatelist = new ArrayList();
/* 176:    */       File localFile;
/* 177:194 */       for (Iterator localIterator = templateFolderList.iterator(); localIterator.hasNext(); localFile = (File)localIterator.next()) {}
/* 178:    */     }
/* 179:199 */     return templatelist;
/* 180:    */   }
/* 181:    */   
/* 182:    */   private void scanTemplateFloders(File file, List<File> list)
/* 183:    */   {
/* 184:203 */     if (file.isDirectory())
/* 185:    */     {
/* 186:205 */       File configFile = new File(file, "tpl_config.xml");
/* 187:207 */       if ((configFile.exists()) && (configFile.isFile()))
/* 188:    */       {
/* 189:208 */         list.add(file);
/* 190:    */       }
/* 191:    */       else
/* 192:    */       {
/* 193:210 */         File[] files = file.listFiles();
/* 194:211 */         if ((files != null) && (files.length > 0)) {
/* 195:212 */           for (File f : files) {
/* 196:213 */             if (f.isDirectory()) {
/* 197:214 */               scanTemplateFloders(f, list);
/* 198:    */             }
/* 199:    */           }
/* 200:    */         }
/* 201:    */       }
/* 202:    */     }
/* 203:    */   }
/* 204:    */ }


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.template.TemplateManager
 * JD-Core Version:    0.7.0.1
 */