/*  1:   */ package com.chee.util;
/*  2:   */ 
/*  3:   */ import com.jfinal.weixin.sdk.api.ApiConfig;
/*  4:   */ import com.jfinal.weixin.sdk.api.ApiResult;
/*  5:   */ import com.jfinal.weixin.sdk.api.MenuApi;
/*  6:   */ import com.jfinal.weixin.sdk.api.UserApi;
/*  7:   */ import com.jfinal.weixin.sdk.utils.HttpUtils;
/*  8:   */ import java.util.Map;
/*  9:   */ 
/* 10:   */ public class WechatApiUtil
/* 11:   */ {
/* 12:   */   public static ApiConfig getApiConfig()
/* 13:   */   {
/* 14:12 */     ApiConfig config = new ApiConfig();
/* 15:13 */     config.setAppId((String)StaticUtils.getOptionMap().get("wechat_appid"));
/* 16:14 */     config.setAppSecret((String)StaticUtils.getOptionMap().get("wechat_appsecret"));
/* 17:15 */     config.setToken((String)StaticUtils.getOptionMap().get("wechat_token"));
/* 18:16 */     return config;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public static ApiResult createMenu(String jsonString)
/* 22:   */   {
/* 23:20 */     return MenuApi.createMenu(jsonString);
/* 24:   */   }
/* 25:   */   
/* 26:   */   public static ApiResult getUserInfo(String openId)
/* 27:   */   {
/* 28:24 */     return UserApi.getUserInfo(openId);
/* 29:   */   }
/* 30:   */   
/* 31:   */   public static ApiResult getOpenId(String appId, String appSecret, String code)
/* 32:   */   {
/* 33:29 */     String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid={appid}&secret={secret}&code={code}&grant_type=authorization_code";
/* 34:30 */     String getOpenIdUrl = url.replace("{appid}", appId).replace("{secret}", appSecret).replace("{code}", code);
/* 35:   */     
/* 36:32 */     String jsonResult = null;
/* 37:   */     try
/* 38:   */     {
/* 39:34 */       jsonResult = HttpUtils.get(getOpenIdUrl);
/* 40:   */     }
/* 41:   */     catch (Exception e)
/* 42:   */     {
/* 43:36 */       e.printStackTrace();
/* 44:   */     }
/* 45:39 */     if (jsonResult == null) {
/* 46:39 */       return null;
/* 47:   */     }
/* 48:41 */     return new ApiResult(jsonResult);
/* 49:   */   }
/* 50:   */ }


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.util.WechatApiUtil
 * JD-Core Version:    0.7.0.1
 */