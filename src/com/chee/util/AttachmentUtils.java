/*  1:   */ package com.chee.util;
/*  2:   */ 
/*  3:   */ import com.jfinal.kit.PathKit;
/*  4:   */ import com.jfinal.upload.UploadFile;
/*  5:   */ import java.io.File;
/*  6:   */ import java.io.PrintStream;
/*  7:   */ import java.text.SimpleDateFormat;
/*  8:   */ import java.util.ArrayList;
/*  9:   */ import java.util.Date;
/* 10:   */ import java.util.List;
/* 11:   */ import java.util.UUID;
/* 12:   */ 
/* 13:   */ public class AttachmentUtils
/* 14:   */ {
/* 15:15 */   static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
/* 16:   */   
/* 17:   */   public static String moveFile(UploadFile uploadFile)
/* 18:   */   {
/* 19:22 */     if (uploadFile == null) {
/* 20:23 */       return null;
/* 21:   */     }
/* 22:25 */     File file = uploadFile.getFile();
/* 23:26 */     if (!file.exists()) {
/* 24:27 */       return null;
/* 25:   */     }
/* 26:30 */     String webRoot = PathKit.getWebRootPath();
/* 27:   */     
/* 28:32 */     String uuid = UUID.randomUUID().toString().replace("-", "");
/* 29:   */     
/* 30:34 */     StringBuilder newFileName = new StringBuilder(webRoot).append(File.separator).append("attachment")
/* 31:35 */       .append(File.separator).append(dateFormat.format(new Date())).append(File.separator).append(uuid)
/* 32:36 */       .append(FileUtils.getSuffix(file.getName()));
/* 33:   */     
/* 34:38 */     File newfile = new File(newFileName.toString());
/* 35:40 */     if (!newfile.getParentFile().exists()) {
/* 36:41 */       newfile.getParentFile().mkdirs();
/* 37:   */     }
/* 38:44 */     file.renameTo(newfile);
/* 39:   */     
/* 40:46 */     return FileUtils.removePrefix(newfile.getAbsolutePath(), webRoot);
/* 41:   */   }
/* 42:   */   
/* 43:49 */   static List<String> imageSuffix = new ArrayList();
/* 44:   */   
/* 45:   */   static
/* 46:   */   {
/* 47:52 */     imageSuffix.add(".jpg");
/* 48:53 */     imageSuffix.add(".jpeg");
/* 49:54 */     imageSuffix.add(".png");
/* 50:55 */     imageSuffix.add(".bmp");
/* 51:56 */     imageSuffix.add(".gif");
/* 52:   */   }
/* 53:   */   
/* 54:   */   public static boolean isImage(String path)
/* 55:   */   {
/* 56:60 */     String sufffix = FileUtils.getSuffix(path);
/* 57:61 */     if (StringUtils.isNotBlank(sufffix)) {
/* 58:62 */       return imageSuffix.contains(sufffix.toLowerCase());
/* 59:   */     }
/* 60:63 */     return false;
/* 61:   */   }
/* 62:   */   
/* 63:   */   public static void main(String[] args)
/* 64:   */   {
/* 65:67 */     System.out.println(FileUtils.getSuffix("xxx.jpg"));
/* 66:   */   }
/* 67:   */ }


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.util.AttachmentUtils
 * JD-Core Version:    0.7.0.1
 */