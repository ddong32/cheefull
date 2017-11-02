/*  1:   */ package com.chee.util;
/*  2:   */ 
/*  3:   */ import java.util.HashMap;
/*  4:   */ import java.util.Map;
/*  5:   */ 
/*  6:   */ public class WechatConsts
/*  7:   */ {
/*  8:   */   public static final String TYPE_CLICK = "click";
/*  9:   */   public static final String TYPE_VIEW = "view";
/* 10:   */   public static final String TYPE_SCANCODE_PUSH = "scancode_push";
/* 11:   */   public static final String TYPE_SCANCODE_WAITMSG = "scancode_waitmsg";
/* 12:   */   public static final String TYPE_PIC_SYSPHOTO = "pic_sysphoto";
/* 13:   */   public static final String TYPE_PIC_PHOTO_OR_ALBUM = "pic_photo_or_album";
/* 14:   */   public static final String TYPE_PIC_WEIXIN = "pic_weixin";
/* 15:   */   public static final String TYPE_LOCATION_SELECT = "location_select";
/* 16:   */   public static final String TYPE_MEDIA_ID = "media_id";
/* 17:   */   public static final String TYPE_VIEW_LIMITED = "view_limited";
/* 18:21 */   static Map<Integer, String> errors = new HashMap();
/* 19:   */   
/* 20:   */   static
/* 21:   */   {
/* 22:24 */     errors.put(Integer.valueOf(-1), "微信服务器繁忙，此时请稍候再试");
/* 23:25 */     errors.put(Integer.valueOf(40001), "获取accessToken时AppSecret错误，或者accessToken无效");
/* 24:26 */     errors.put(Integer.valueOf(40013), "不合法的AppID，请开发者检查AppID的正确性，避免异常字符，注意大小写");
/* 25:27 */     errors.put(Integer.valueOf(40015), "不合法的菜单类型");
/* 26:28 */     errors.put(Integer.valueOf(40016), "菜单按钮数量超出限制,一级菜单最多3个,二级菜单最多5个");
/* 27:29 */     errors.put(Integer.valueOf(40017), "菜单按钮数量超出限制");
/* 28:30 */     errors.put(Integer.valueOf(40018), "一级菜单名称长度超出限制,一级菜单最多4个汉字");
/* 29:31 */     errors.put(Integer.valueOf(40019), "菜单点击事件的key值长度超出限制");
/* 30:32 */     errors.put(Integer.valueOf(40020), "菜单URL长度超出限制");
/* 31:33 */     errors.put(Integer.valueOf(40021), "不合法的菜单版本号");
/* 32:34 */     errors.put(Integer.valueOf(40022), "菜单级数超出限制,请确保只有两级菜单,且一级菜单不超过三个,二级菜单不超过5个");
/* 33:35 */     errors.put(Integer.valueOf(40023), "二级菜单数量超出限制,请确保每个一级菜单下的二级菜单不超过5个");
/* 34:36 */     errors.put(Integer.valueOf(40024), "菜单类型错误,请检查菜单类型是否是key和URL的其中一个");
/* 35:37 */     errors.put(Integer.valueOf(40025), "二级菜单名称长度超出限制,二级菜单最多7个汉字");
/* 36:38 */     errors.put(Integer.valueOf(40026), "二级菜单点击事件的key值长度超出限制");
/* 37:39 */     errors.put(Integer.valueOf(40027), "二级菜单URL长度超出限制");
/* 38:40 */     errors.put(Integer.valueOf(40119), "请确保你的公众号非个人号且已获得认证");
/* 39:41 */     errors.put(Integer.valueOf(40120), "请确保你的公众号非个人号且已获得认证");
/* 40:42 */     errors.put(Integer.valueOf(48001), "您的公众号还未获得该API授权。");
/* 41:   */   }
/* 42:   */   
/* 43:   */   public static String getErrorMessage(int errorCode)
/* 44:   */   {
/* 45:46 */     return (String)errors.get(Integer.valueOf(errorCode));
/* 46:   */   }
/* 47:   */ }


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.util.WechatConsts
 * JD-Core Version:    0.7.0.1
 */