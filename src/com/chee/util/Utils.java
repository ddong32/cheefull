/*  1:   */ package com.chee.util;
/*  2:   */ 
/*  3:   */ import java.io.File;
/*  4:   */ import java.net.URL;
/*  5:   */ import java.util.regex.Matcher;
/*  6:   */ import java.util.regex.Pattern;
/*  7:   */ 
/*  8:   */ public class Utils
/*  9:   */ {
/* 10:   */   public static boolean isNumeric(String str)
/* 11:   */   {
/* 12: 8 */     if ((str != null) && (!"".equals(str)))
/* 13:   */     {
/* 14: 9 */       Pattern pattern = Pattern.compile("^[0-9]*$");
/* 15:10 */       return pattern.matcher(str).matches();
/* 16:   */     }
/* 17:12 */     return false;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public static String getRootPath()
/* 21:   */   {
/* 22:16 */     String rootPath = "";
/* 23:17 */     String strClassesPath = FileUtil.class.getResource("/").toString().replace("file:/", "");
/* 24:18 */     if (File.separator.equals("\\")) {
/* 25:19 */       rootPath = strClassesPath.substring(0, strClassesPath.indexOf("WEB-INF")).replace('/', '\\');
/* 26:   */     } else {
/* 27:21 */       rootPath = File.separator + strClassesPath.substring(0, strClassesPath.indexOf("WEB-INF"));
/* 28:   */     }
/* 29:23 */     return rootPath;
/* 30:   */   }
/* 31:   */ }


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.util.Utils
 * JD-Core Version:    0.7.0.1
 */