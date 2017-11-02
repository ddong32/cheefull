/*  1:   */ package com.chee.util;
/*  2:   */ 
/*  3:   */ import java.sql.Date;
/*  4:   */ import java.text.SimpleDateFormat;
/*  5:   */ import java.util.Locale;
/*  6:   */ import net.sf.json.JsonConfig;
/*  7:   */ import net.sf.json.processors.JsonValueProcessor;
/*  8:   */ 
/*  9:   */ public class JsonDateValueProcessor
/* 10:   */   implements JsonValueProcessor
/* 11:   */ {
/* 12:10 */   private String datePattern = "yyyy-MM-dd HH:mm:ss";
/* 13:   */   
/* 14:   */   public JsonDateValueProcessor() {}
/* 15:   */   
/* 16:   */   public JsonDateValueProcessor(String format)
/* 17:   */   {
/* 18:16 */     this.datePattern = format;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public Object processArrayValue(Object value, JsonConfig jsonConfig)
/* 22:   */   {
/* 23:20 */     return process(value);
/* 24:   */   }
/* 25:   */   
/* 26:   */   public Object processObjectValue(String key, Object value, JsonConfig jsonConfig)
/* 27:   */   {
/* 28:24 */     return process(value);
/* 29:   */   }
/* 30:   */   
/* 31:   */   private Object process(Object value)
/* 32:   */   {
/* 33:   */     try
/* 34:   */     {
/* 35:29 */       if ((value instanceof Date))
/* 36:   */       {
/* 37:30 */         SimpleDateFormat sdf = new SimpleDateFormat(this.datePattern, Locale.UK);
/* 38:31 */         return sdf.format((Date)value);
/* 39:   */       }
/* 40:33 */       return value == null ? "" : value.toString();
/* 41:   */     }
/* 42:   */     catch (Exception localException) {}
/* 43:36 */     return "";
/* 44:   */   }
/* 45:   */   
/* 46:   */   public String getDatePattern()
/* 47:   */   {
/* 48:40 */     return this.datePattern;
/* 49:   */   }
/* 50:   */   
/* 51:   */   public void setDatePattern(String pDatePattern)
/* 52:   */   {
/* 53:44 */     this.datePattern = pDatePattern;
/* 54:   */   }
/* 55:   */ }


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.util.JsonDateValueProcessor
 * JD-Core Version:    0.7.0.1
 */