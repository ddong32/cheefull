/*  1:   */ package com.chee.util;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import java.util.Map;
/*  5:   */ import org.apache.commons.lang.StringUtils;
/*  6:   */ import org.codehaus.jackson.map.ObjectMapper;
/*  7:   */ 
/*  8:   */ public class JsonUtils
/*  9:   */ {
/* 10: 9 */   private static final ObjectMapper objectMapper = new ObjectMapper();
/* 11:   */   
/* 12:   */   public static String getObject2Json(Object object)
/* 13:   */     throws Exception
/* 14:   */   {
/* 15:12 */     String stringValue = null;
/* 16:13 */     if (object == null) {
/* 17:14 */       return null;
/* 18:   */     }
/* 19:16 */     stringValue = objectMapper.writeValueAsString(object);
/* 20:17 */     return stringValue;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public static List<?> getJson2list(String json)
/* 24:   */     throws Exception
/* 25:   */   {
/* 26:21 */     List listValue = null;
/* 27:22 */     if (StringUtils.isEmpty(json)) {
/* 28:23 */       return null;
/* 29:   */     }
/* 30:25 */     listValue = (List)objectMapper.readValue(json, List.class);
/* 31:26 */     return listValue;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public static Map<?, ?> getJson2Map(String json)
/* 35:   */     throws Exception
/* 36:   */   {
/* 37:30 */     Map mapValue = null;
/* 38:31 */     if (StringUtils.isEmpty(json)) {
/* 39:32 */       return null;
/* 40:   */     }
/* 41:34 */     mapValue = (Map)objectMapper.readValue(json, Map.class);
/* 42:35 */     return mapValue;
/* 43:   */   }
/* 44:   */   
/* 45:   */   public static Object getJson2Object(String json, Class class1)
/* 46:   */     throws Exception
/* 47:   */   {
/* 48:39 */     Object objectValue = null;
/* 49:40 */     if (StringUtils.isEmpty(json)) {
/* 50:41 */       return null;
/* 51:   */     }
/* 52:43 */     objectValue = objectMapper.readValue(json, class1);
/* 53:44 */     return objectValue;
/* 54:   */   }
/* 55:   */ }


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.util.JsonUtils
 * JD-Core Version:    0.7.0.1
 */