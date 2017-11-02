/*   1:    */ package com.chee.util;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.List;
/*   5:    */ import org.jsoup.Jsoup;
/*   6:    */ import org.jsoup.nodes.Attribute;
/*   7:    */ import org.jsoup.nodes.Document;
/*   8:    */ import org.jsoup.nodes.Element;
/*   9:    */ import org.jsoup.safety.Whitelist;
/*  10:    */ import org.jsoup.select.Elements;
/*  11:    */ 
/*  12:    */ public class JsoupUtils
/*  13:    */ {
/*  14:    */   public static String getFirstImageSrc(String html)
/*  15:    */   {
/*  16: 30 */     if (html == null) {
/*  17: 31 */       return null;
/*  18:    */     }
/*  19: 33 */     Elements es = Jsoup.parseBodyFragment(html).select("img");
/*  20: 34 */     if ((es != null) && (es.size() > 0)) {
/*  21: 35 */       return es.first().attr("src");
/*  22:    */     }
/*  23: 37 */     return null;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public static List<String> getImageSrcs(String html)
/*  27:    */   {
/*  28: 41 */     if (StringUtils.isBlank(html)) {
/*  29: 42 */       return null;
/*  30:    */     }
/*  31: 45 */     List<String> list = new ArrayList();
/*  32:    */     
/*  33: 47 */     Document doc = Jsoup.parseBodyFragment(html);
/*  34: 48 */     Elements es = doc.select("img");
/*  35: 49 */     if ((es != null) && (es.size() > 0)) {
/*  36: 50 */       for (Element e : es) {
/*  37: 51 */         list.add(e.attr("src"));
/*  38:    */       }
/*  39:    */     }
/*  40: 54 */     return list;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public static String getText(String html)
/*  44:    */   {
/*  45: 58 */     return Jsoup.parse(html).text();
/*  46:    */   }
/*  47:    */   
/*  48:    */   public static String getBodyHtml(String html)
/*  49:    */   {
/*  50: 62 */     if (StringUtils.isNotBlank(html))
/*  51:    */     {
/*  52: 63 */       Document document = Jsoup.parse(html);
/*  53: 64 */       if ((document != null) && (document.body() != null)) {
/*  54: 65 */         return document.body().html().toString();
/*  55:    */       }
/*  56:    */     }
/*  57: 68 */     return html;
/*  58:    */   }
/*  59:    */   
/*  60: 71 */   static MyWhitelist whitelist = new MyWhitelist();
/*  61:    */   
/*  62:    */   public static String clear(String html)
/*  63:    */   {
/*  64: 74 */     if (StringUtils.isNotBlank(html)) {
/*  65: 75 */       return Jsoup.clean(html, whitelist);
/*  66:    */     }
/*  67: 77 */     return html;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public static class MyWhitelist
/*  71:    */     extends Whitelist
/*  72:    */   {
/*  73:    */     public MyWhitelist()
/*  74:    */     {
/*  75: 88 */       addTags(new String[] { "a", "b", "blockquote", "br", "caption", "cite", "code", "col", "colgroup", "dd", "div", "dl", "dt", 
/*  76: 89 */         "em", "h1", "h2", "h3", "h4", "h5", "h6", "i", "img", "li", "ol", "p", "pre", "q", "small", 
/*  77: 90 */         "strike", "strong", "sub", "sup", "table", "tbody", "td", "tfoot", "th", "thead", "tr", "u", "ul" });
/*  78:    */       
/*  79: 92 */       addAttributes("a", new String[] { "href", "title", "target" });
/*  80: 93 */       addAttributes("blockquote", new String[] { "cite" });
/*  81: 94 */       addAttributes("col", new String[] { "span" });
/*  82: 95 */       addAttributes("colgroup", new String[] { "span" });
/*  83: 96 */       addAttributes("img", new String[] { "align", "alt", "src", "title" });
/*  84: 97 */       addAttributes("ol", new String[] { "start" });
/*  85: 98 */       addAttributes("q", new String[] { "cite" });
/*  86: 99 */       addAttributes("table", new String[] { "summary" });
/*  87:100 */       addAttributes("td", new String[] { "abbr", "axis", "colspan", "rowspan", "width" });
/*  88:101 */       addAttributes("th", new String[] { "abbr", "axis", "colspan", "rowspan", "scope", "width" });
/*  89:102 */       addAttributes("video", new String[] { "src", "autoplay", "controls", "loop", "muted", "poster", "preload" });
/*  90:    */       
/*  91:104 */       addAttributes(":all", new String[] { "class" });
/*  92:105 */       addAttributes(":all", new String[] { "style" });
/*  93:106 */       addAttributes(":all", new String[] { "height" });
/*  94:107 */       addAttributes(":all", new String[] { "width" });
/*  95:108 */       addAttributes(":all", new String[] { "type" });
/*  96:109 */       addAttributes(":all", new String[] { "id" });
/*  97:110 */       addAttributes(":all", new String[] { "name" });
/*  98:    */       
/*  99:112 */       addProtocols("a", "href", new String[] { "ftp", "http", "https", "mailto", "tel" });
/* 100:113 */       addProtocols("blockquote", "cite", new String[] { "http", "https" });
/* 101:114 */       addProtocols("cite", "cite", new String[] { "http", "https" });
/* 102:115 */       addProtocols("img", "src", new String[] { "http", "https" });
/* 103:116 */       addProtocols("q", "cite", new String[] { "http", "https" });
/* 104:    */     }
/* 105:    */     
/* 106:    */     protected boolean isSafeAttribute(String tagName, Element el, Attribute attr)
/* 107:    */     {
/* 108:122 */       return (("img".equals(tagName)) && ("src".equals(attr.getKey())) && (attr.getValue().startsWith("data:;base64"))) || (super.isSafeAttribute(tagName, el, attr));
/* 109:    */     }
/* 110:    */   }
/* 111:    */ }


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.util.JsoupUtils
 * JD-Core Version:    0.7.0.1
 */