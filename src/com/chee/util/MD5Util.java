/*  1:   */ package com.chee.util;
/*  2:   */ 
/*  3:   */ import java.io.PrintStream;
/*  4:   */ import java.io.UnsupportedEncodingException;
/*  5:   */ import java.security.MessageDigest;
/*  6:   */ import java.security.NoSuchAlgorithmException;
/*  7:   */ import org.w3c.dom.Document;
/*  8:   */ import org.w3c.dom.Element;
/*  9:   */ import org.w3c.dom.Node;
/* 10:   */ import org.w3c.dom.NodeList;
/* 11:   */ 
/* 12:   */ public class MD5Util
/* 13:   */ {
/* 14:   */   public static String getMD5Str(String str)
/* 15:   */   {
/* 16:13 */     MessageDigest messageDigest = null;
/* 17:   */     try
/* 18:   */     {
/* 19:15 */       messageDigest = MessageDigest.getInstance("MD5");
/* 20:16 */       messageDigest.reset();
/* 21:17 */       messageDigest.update(str.getBytes("UTF-8"));
/* 22:   */     }
/* 23:   */     catch (NoSuchAlgorithmException e)
/* 24:   */     {
/* 25:19 */       System.out.println("NoSuchAlgorithmException caught!");
/* 26:20 */       System.exit(-1);
/* 27:   */     }
/* 28:   */     catch (UnsupportedEncodingException e)
/* 29:   */     {
/* 30:22 */       e.printStackTrace();
/* 31:   */     }
/* 32:24 */     byte[] byteArray = messageDigest.digest();
/* 33:25 */     StringBuffer md5StrBuff = new StringBuffer();
/* 34:26 */     for (int i = 0; i < byteArray.length; i++) {
/* 35:27 */       if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) {
/* 36:28 */         md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
/* 37:   */       } else {
/* 38:30 */         md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
/* 39:   */       }
/* 40:   */     }
/* 41:32 */     return md5StrBuff.toString();
/* 42:   */   }
/* 43:   */   
/* 44:   */   private static String anylizeElement(Document xmlTree)
/* 45:   */   {
/* 46:36 */     Element element = xmlTree.getDocumentElement();
/* 47:37 */     NodeList children = element.getChildNodes();
/* 48:38 */     StringBuffer buf = new StringBuffer();
/* 49:39 */     for (int i = 0; i < children.getLength(); i++)
/* 50:   */     {
/* 51:40 */       Node node = children.item(i);
/* 52:41 */       buf.append(node.getNodeValue());
/* 53:   */     }
/* 54:43 */     return buf.toString();
/* 55:   */   }
/* 56:   */ }


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.util.MD5Util
 * JD-Core Version:    0.7.0.1
 */