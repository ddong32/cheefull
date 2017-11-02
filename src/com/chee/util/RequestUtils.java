/*   1:    */ package com.chee.util;
/*   2:    */ 
/*   3:    */ import javax.servlet.http.HttpServletRequest;
/*   4:    */ 
/*   5:    */ public class RequestUtils
/*   6:    */ {
/*   7:  7 */   static String[] mobileAgents = { "iphone", "android", "phone", "mobile", "wap", "netfront", "java", "opera mobi", 
/*   8:  8 */     "opera mini", "ucweb", "windows ce", "symbian", "series", "webos", "sony", "blackberry", "dopod", "nokia", 
/*   9:  9 */     "samsung", "palmsource", "xda", "pieplus", "meizu", "midp", "cldc", "motorola", "foma", "docomo", 
/*  10: 10 */     "up.browser", "up.link", "blazer", "helio", "hosin", "huawei", "novarra", "coolpad", "webos", "techfaith", 
/*  11: 11 */     "palmsource", "alcatel", "amoi", "ktouch", "nexian", "ericsson", "philips", "sagem", "wellcom", "bunjalloo", 
/*  12: 12 */     "maui", "smartphone", "iemobile", "spice", "bird", "zte-", "longcos", "pantech", "gionee", "portalmmm", 
/*  13: 13 */     "jig browser", "hiptop", "benq", "haier", "^lct", "320x320", "240x320", "176x220", "w3c ", "acs-", "alav", 
/*  14: 14 */     "alca", "amoi", "audi", "avan", "benq", "bird", "blac", "blaz", "brew", "cell", "cldc", "cmd-", "dang", 
/*  15: 15 */     "doco", "eric", "hipt", "inno", "ipaq", "java", "jigs", "kddi", "keji", "leno", "lg-c", "lg-d", "lg-g", 
/*  16: 16 */     "lge-", "maui", "maxo", "midp", "mits", "mmef", "mobi", "mot-", "moto", "mwbp", "nec-", "newt", "noki", 
/*  17: 17 */     "oper", "palm", "pana", "pant", "phil", "play", "port", "prox", "qwap", "sage", "sams", "sany", "sch-", 
/*  18: 18 */     "sec-", "send", "seri", "sgh-", "shar", "sie-", "siem", "smal", "smar", "sony", "sph-", "symb", "t-mo", 
/*  19: 19 */     "teli", "tim-", "tsm-", "upg1", "upsi", "vk-v", "voda", "wap-", "wapa", "wapi", "wapp", "wapr", "webc", 
/*  20: 20 */     "winw", "winw", "xda", "xda-", "googlebot-mobile" };
/*  21:    */   
/*  22:    */   public static boolean isAjaxRequest(HttpServletRequest request)
/*  23:    */   {
/*  24: 23 */     String header = request.getHeader("X-Requested-With");
/*  25: 24 */     return "XMLHttpRequest".equalsIgnoreCase(header);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public static boolean isMultipartRequest(HttpServletRequest request)
/*  29:    */   {
/*  30: 28 */     String contentType = request.getContentType();
/*  31: 29 */     return (contentType != null) && (contentType.toLowerCase().indexOf("multipart") != -1);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public static boolean isMoblieBrowser(HttpServletRequest request)
/*  35:    */   {
/*  36: 38 */     String ua = request.getHeader("User-Agent");
/*  37: 39 */     if (ua == null) {
/*  38: 40 */       return false;
/*  39:    */     }
/*  40: 42 */     ua = ua.toLowerCase();
/*  41: 43 */     for (String mobileAgent : mobileAgents) {
/*  42: 44 */       if (ua.indexOf(mobileAgent) >= 0) {
/*  43: 45 */         return true;
/*  44:    */       }
/*  45:    */     }
/*  46: 48 */     return false;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public static boolean isWechatBrowser(HttpServletRequest request)
/*  50:    */   {
/*  51: 57 */     String ua = request.getHeader("User-Agent");
/*  52: 58 */     if (ua == null) {
/*  53: 59 */       return false;
/*  54:    */     }
/*  55: 61 */     ua = ua.toLowerCase();
/*  56: 62 */     if (ua.indexOf("micromessenger") > 0) {
/*  57: 63 */       return true;
/*  58:    */     }
/*  59: 65 */     return false;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public static boolean isIEBrowser(HttpServletRequest request)
/*  63:    */   {
/*  64: 74 */     String ua = request.getHeader("User-Agent");
/*  65: 75 */     if (ua == null) {
/*  66: 76 */       return false;
/*  67:    */     }
/*  68: 79 */     ua = ua.toLowerCase();
/*  69: 80 */     if (ua.indexOf("msie") > 0) {
/*  70: 81 */       return true;
/*  71:    */     }
/*  72: 84 */     if ((ua.indexOf("gecko") > 0) && (ua.indexOf("rv:11") > 0)) {
/*  73: 85 */       return true;
/*  74:    */     }
/*  75: 87 */     return false;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public static String getIpAddress(HttpServletRequest request)
/*  79:    */   {
/*  80: 91 */     String ip = request.getHeader("X-requested-For");
/*  81: 92 */     if ((StringUtils.isBlank(ip)) || ("unknown".equalsIgnoreCase(ip))) {
/*  82: 93 */       ip = request.getHeader("X-Forwarded-For");
/*  83:    */     }
/*  84: 95 */     if ((StringUtils.isBlank(ip)) || ("unknown".equalsIgnoreCase(ip))) {
/*  85: 96 */       ip = request.getHeader("Proxy-Client-IP");
/*  86:    */     }
/*  87: 98 */     if ((StringUtils.isBlank(ip)) || ("unknown".equalsIgnoreCase(ip))) {
/*  88: 99 */       ip = request.getHeader("WL-Proxy-Client-IP");
/*  89:    */     }
/*  90:101 */     if ((StringUtils.isBlank(ip)) || ("unknown".equalsIgnoreCase(ip))) {
/*  91:102 */       ip = request.getHeader("HTTP_CLIENT_IP");
/*  92:    */     }
/*  93:104 */     if ((StringUtils.isBlank(ip)) || ("unknown".equalsIgnoreCase(ip))) {
/*  94:105 */       ip = request.getHeader("HTTP_X_FORWARDED_FOR");
/*  95:    */     }
/*  96:107 */     if ((StringUtils.isBlank(ip)) || ("unknown".equalsIgnoreCase(ip))) {
/*  97:108 */       ip = request.getRemoteAddr();
/*  98:    */     }
/*  99:110 */     return ip;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public static String getUserAgent(HttpServletRequest request)
/* 103:    */   {
/* 104:114 */     return request.getHeader("User-Agent");
/* 105:    */   }
/* 106:    */ }


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.util.RequestUtils
 * JD-Core Version:    0.7.0.1
 */