/*   1:    */ package com.chee.util;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.PrintWriter;
/*   5:    */ import javax.servlet.http.HttpServletRequest;
/*   6:    */ import javax.servlet.http.HttpServletResponse;
/*   7:    */ import javax.servlet.http.HttpSession;
/*   8:    */ import org.apache.commons.lang.StringUtils;
/*   9:    */ import org.apache.struts2.ServletActionContext;
/*  10:    */ import org.codehaus.jackson.map.ObjectMapper;
/*  11:    */ 
/*  12:    */ public class Struts2Utils
/*  13:    */ {
/*  14:    */   private static final String HEADER_ENCODING = "encoding";
/*  15:    */   private static final String HEADER_NOCACHE = "no-cache";
/*  16:    */   private static final String DEFAULT_ENCODING = "UTF-8";
/*  17:    */   private static final boolean DEFAULT_NOCACHE = true;
/*  18: 16 */   private static ObjectMapper mapper = new ObjectMapper();
/*  19:    */   
/*  20:    */   public static HttpSession getSession()
/*  21:    */   {
/*  22: 19 */     return ServletActionContext.getRequest().getSession();
/*  23:    */   }
/*  24:    */   
/*  25:    */   public static HttpSession getSession(boolean isNew)
/*  26:    */   {
/*  27: 23 */     return ServletActionContext.getRequest().getSession(isNew);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public static Object getSessionAttribute(String name)
/*  31:    */   {
/*  32: 27 */     HttpSession session = getSession(false);
/*  33: 28 */     return session != null ? session.getAttribute(name) : null;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public static HttpServletRequest getRequest()
/*  37:    */   {
/*  38: 32 */     return ServletActionContext.getRequest();
/*  39:    */   }
/*  40:    */   
/*  41:    */   public static String getParameter(String name)
/*  42:    */   {
/*  43: 36 */     return getRequest().getParameter(name);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public static HttpServletResponse getResponse()
/*  47:    */   {
/*  48: 40 */     return ServletActionContext.getResponse();
/*  49:    */   }
/*  50:    */   
/*  51:    */   public static void render(String contentType, String content, String[] headers)
/*  52:    */   {
/*  53: 44 */     HttpServletResponse response = initResponseHeader(contentType, headers);
/*  54:    */     try
/*  55:    */     {
/*  56: 46 */       response.getWriter().write(content);
/*  57: 47 */       response.getWriter().flush();
/*  58:    */     }
/*  59:    */     catch (IOException e)
/*  60:    */     {
/*  61: 49 */       throw new RuntimeException(e.getMessage(), e);
/*  62:    */     }
/*  63:    */   }
/*  64:    */   
/*  65:    */   public static void renderText(String text, String[] headers)
/*  66:    */   {
/*  67: 54 */     render("text/plain", text, headers);
/*  68:    */   }
/*  69:    */   
/*  70:    */   public static void renderHtml(String html, String[] headers)
/*  71:    */   {
/*  72: 58 */     render("text/html", html, headers);
/*  73:    */   }
/*  74:    */   
/*  75:    */   public static void renderXml(String xml, String[] headers)
/*  76:    */   {
/*  77: 62 */     render("text/xml", xml, headers);
/*  78:    */   }
/*  79:    */   
/*  80:    */   public static void renderJson(String jsonString, String[] headers)
/*  81:    */   {
/*  82: 66 */     render("application/json", jsonString, headers);
/*  83:    */   }
/*  84:    */   
/*  85:    */   public static void renderJson(Object data, String[] headers)
/*  86:    */   {
/*  87: 70 */     HttpServletResponse response = initResponseHeader("application/json", headers);
/*  88:    */     try
/*  89:    */     {
/*  90: 72 */       mapper.writeValue(response.getWriter(), data);
/*  91:    */     }
/*  92:    */     catch (IOException e)
/*  93:    */     {
/*  94: 74 */       throw new IllegalArgumentException(e);
/*  95:    */     }
/*  96:    */   }
/*  97:    */   
/*  98:    */   public static void renderJsonp(String callbackName, Object object, String[] headers)
/*  99:    */   {
/* 100: 79 */     String jsonString = null;
/* 101:    */     try
/* 102:    */     {
/* 103: 81 */       jsonString = mapper.writeValueAsString(object);
/* 104:    */     }
/* 105:    */     catch (IOException e)
/* 106:    */     {
/* 107: 83 */       throw new IllegalArgumentException(e);
/* 108:    */     }
/* 109: 85 */     String result = callbackName + "(" + jsonString + ");";
/* 110:    */     
/* 111: 87 */     render("text/javascript", result, headers);
/* 112:    */   }
/* 113:    */   
/* 114:    */   private static HttpServletResponse initResponseHeader(String contentType, String[] headers)
/* 115:    */   {
/* 116: 91 */     String encoding = "UTF-8";
/* 117: 92 */     boolean noCache = true;
/* 118: 93 */     for (String header : headers)
/* 119:    */     {
/* 120: 94 */       String headerName = StringUtils.substringBefore(header, ":");
/* 121: 95 */       String headerValue = StringUtils.substringAfter(header, ":");
/* 122: 96 */       if (StringUtils.equalsIgnoreCase(headerName, "encoding")) {
/* 123: 97 */         encoding = headerValue;
/* 124: 98 */       } else if (StringUtils.equalsIgnoreCase(headerName, "no-cache")) {
/* 125: 99 */         noCache = Boolean.parseBoolean(headerValue);
/* 126:    */       } else {
/* 127:101 */         throw new IllegalArgumentException(headerName + "不是一个合法的header类型");
/* 128:    */       }
/* 129:    */     }
/* 130:104 */     HttpServletResponse response = ServletActionContext.getResponse();
/* 131:    */     
/* 132:106 */     String fullContentType = contentType + ";charset=" + encoding;
/* 133:107 */     response.setContentType(fullContentType);
/* 134:108 */     if (noCache) {
/* 135:109 */       ServletUtils.setDisableCacheHeader(response);
/* 136:    */     }
/* 137:111 */     return response;
/* 138:    */   }
/* 139:    */ }


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.util.Struts2Utils
 * JD-Core Version:    0.7.0.1
 */