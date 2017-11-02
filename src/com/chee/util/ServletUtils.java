/*   1:    */ package com.chee.util;
/*   2:    */ 
/*   3:    */ import java.io.UnsupportedEncodingException;
/*   4:    */ import java.util.Enumeration;
/*   5:    */ import java.util.Map;
/*   6:    */ import java.util.StringTokenizer;
/*   7:    */ import java.util.TreeMap;
/*   8:    */ import javax.servlet.ServletRequest;
/*   9:    */ import javax.servlet.http.HttpServletRequest;
/*  10:    */ import javax.servlet.http.HttpServletResponse;
/*  11:    */ import org.springframework.util.Assert;
/*  12:    */ 
/*  13:    */ public class ServletUtils
/*  14:    */ {
/*  15:    */   public static final String TEXT_TYPE = "text/plain";
/*  16:    */   public static final String JSON_TYPE = "application/json";
/*  17:    */   public static final String XML_TYPE = "text/xml";
/*  18:    */   public static final String HTML_TYPE = "text/html";
/*  19:    */   public static final String JS_TYPE = "text/javascript";
/*  20:    */   public static final String EXCEL_TYPE = "application/vnd.ms-excel";
/*  21:    */   public static final String AUTHENTICATION_HEADER = "Authorization";
/*  22:    */   public static final long ONE_YEAR_SECONDS = 31536000L;
/*  23:    */   
/*  24:    */   public static void setExpiresHeader(HttpServletResponse response, long expiresSeconds)
/*  25:    */   {
/*  26: 24 */     response.setDateHeader("Expires", System.currentTimeMillis() + expiresSeconds * 1000L);
/*  27:    */     
/*  28: 26 */     response.setHeader("Cache-Control", "private, max-age=" + expiresSeconds);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public static void setDisableCacheHeader(HttpServletResponse response)
/*  32:    */   {
/*  33: 30 */     response.setDateHeader("Expires", 1L);
/*  34: 31 */     response.addHeader("Pragma", "no-cache");
/*  35:    */     
/*  36: 33 */     response.setHeader("Cache-Control", "no-cache, no-store, max-age=0");
/*  37:    */   }
/*  38:    */   
/*  39:    */   public static void setLastModifiedHeader(HttpServletResponse response, long lastModifiedDate)
/*  40:    */   {
/*  41: 37 */     response.setDateHeader("Last-Modified", lastModifiedDate);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public static void setEtag(HttpServletResponse response, String etag)
/*  45:    */   {
/*  46: 41 */     response.setHeader("ETag", etag);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public static boolean checkIfModifiedSince(HttpServletRequest request, HttpServletResponse response, long lastModified)
/*  50:    */   {
/*  51: 45 */     long ifModifiedSince = request.getDateHeader("If-Modified-Since");
/*  52: 46 */     if ((ifModifiedSince != -1L) && (lastModified < ifModifiedSince + 1000L))
/*  53:    */     {
/*  54: 47 */       response.setStatus(304);
/*  55: 48 */       return false;
/*  56:    */     }
/*  57: 50 */     return true;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public static boolean checkIfNoneMatchEtag(HttpServletRequest request, HttpServletResponse response, String etag)
/*  61:    */   {
/*  62: 54 */     String headerValue = request.getHeader("If-None-Match");
/*  63: 55 */     if (headerValue != null)
/*  64:    */     {
/*  65: 56 */       boolean conditionSatisfied = false;
/*  66: 57 */       if (!"*".equals(headerValue))
/*  67:    */       {
/*  68: 58 */         StringTokenizer commaTokenizer = new StringTokenizer(headerValue, ",");
/*  69:    */         do
/*  70:    */         {
/*  71: 61 */           String currentToken = commaTokenizer.nextToken();
/*  72: 62 */           if (currentToken.trim().equals(etag)) {
/*  73: 63 */             conditionSatisfied = true;
/*  74:    */           }
/*  75: 60 */           if (conditionSatisfied) {
/*  76:    */             break;
/*  77:    */           }
/*  78: 60 */         } while (commaTokenizer.hasMoreTokens());
/*  79:    */       }
/*  80:    */       else
/*  81:    */       {
/*  82: 66 */         conditionSatisfied = true;
/*  83:    */       }
/*  84: 69 */       if (conditionSatisfied)
/*  85:    */       {
/*  86: 70 */         response.setStatus(304);
/*  87: 71 */         response.setHeader("ETag", etag);
/*  88: 72 */         return false;
/*  89:    */       }
/*  90:    */     }
/*  91: 75 */     return true;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public static void setFileDownloadHeader(HttpServletResponse response, String fileName)
/*  95:    */   {
/*  96:    */     try
/*  97:    */     {
/*  98: 80 */       String encodedfileName = new String(fileName.getBytes(), "ISO8859-1");
/*  99: 81 */       response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedfileName + "\"");
/* 100:    */     }
/* 101:    */     catch (UnsupportedEncodingException localUnsupportedEncodingException) {}
/* 102:    */   }
/* 103:    */   
/* 104:    */   public static Map<String, Object> getParametersStartingWith(ServletRequest request, String prefix)
/* 105:    */   {
/* 106: 87 */     Assert.notNull(request, "Request must not be null");
/* 107: 88 */     Enumeration paramNames = request.getParameterNames();
/* 108: 89 */     Map params = new TreeMap();
/* 109: 90 */     if (prefix == null) {
/* 110: 91 */       prefix = "";
/* 111:    */     }
/* 112: 93 */     while ((paramNames != null) && (paramNames.hasMoreElements()))
/* 113:    */     {
/* 114: 94 */       String paramName = (String)paramNames.nextElement();
/* 115: 95 */       if (("".equals(prefix)) || (paramName.startsWith(prefix)))
/* 116:    */       {
/* 117: 96 */         String unprefixed = paramName.substring(prefix.length());
/* 118: 97 */         String[] values = request.getParameterValues(paramName);
/* 119: 98 */         if ((values != null) && (values.length != 0)) {
/* 120: 99 */           if (values.length > 1) {
/* 121:100 */             params.put(unprefixed, values);
/* 122:    */           } else {
/* 123:102 */             params.put(unprefixed, values[0]);
/* 124:    */           }
/* 125:    */         }
/* 126:    */       }
/* 127:    */     }
/* 128:106 */     return params;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public static String encodeHttpBasic(String userName, String password)
/* 132:    */   {
/* 133:110 */     String encode = userName + ":" + password;
/* 134:111 */     return "Basic " + EncodeUtils.base64Encode(encode.getBytes());
/* 135:    */   }
/* 136:    */ }


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.util.ServletUtils
 * JD-Core Version:    0.7.0.1
 */