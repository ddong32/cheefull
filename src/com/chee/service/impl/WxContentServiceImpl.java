/*  1:   */ package com.chee.service.impl;
/*  2:   */ 
/*  3:   */ import com.chee.common.Page;
/*  4:   */ import com.chee.dao.WxContentDao;
/*  5:   */ import com.chee.entity.User;
/*  6:   */ import com.chee.entity.WxContent;
/*  7:   */ import com.chee.service.WxContentService;
/*  8:   */ import java.util.HashMap;
/*  9:   */ import java.util.List;
/* 10:   */ import java.util.Map;
/* 11:   */ import javax.annotation.Resource;
/* 12:   */ import org.springframework.stereotype.Service;
/* 13:   */ 
/* 14:   */ @Service
/* 15:   */ public class WxContentServiceImpl
/* 16:   */   extends BaseServiceImpl<WxContent, Integer>
/* 17:   */   implements WxContentService
/* 18:   */ {
/* 19:   */   @Resource
/* 20:   */   WxContentDao wxContentDao;
/* 21:   */   
/* 22:   */   @Resource
/* 23:   */   public void setBaseDao(WxContentDao wxOptionDao)
/* 24:   */   {
/* 25:32 */     super.setBaseDao(wxOptionDao);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public List<WxContent> findMenus()
/* 29:   */   {
/* 30:36 */     Map map = new HashMap();
/* 31:37 */     StringBuffer hqlSb = new StringBuffer(" from WxContent t where t.module = 'wechat_menu' order by order_number");
/* 32:38 */     List<WxContent> wechat_menus = this.wxContentDao.find(hqlSb.toString(), map);
/* 33:39 */     return wechat_menus;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public List<WxContent> findParentMenus()
/* 37:   */   {
/* 38:43 */     Map map = new HashMap();
/* 39:44 */     StringBuffer hqlSb = new StringBuffer(" from WxContent t where t.module = 'wechat_menu' and parent.id is null order by order_number");
/* 40:45 */     List<WxContent> wechat_menus = this.wxContentDao.find(hqlSb.toString(), map);
/* 41:46 */     return wechat_menus;
/* 42:   */   }
/* 43:   */   
/* 44:   */   public WxContent loadMenu(Integer id)
/* 45:   */   {
/* 46:50 */     WxContent wechat_menus = (WxContent)this.wxContentDao.get(id);
/* 47:51 */     return wechat_menus;
/* 48:   */   }
/* 49:   */   
/* 50:   */   public Long findCountInNormalByParentId(Integer id)
/* 51:   */   {
/* 52:55 */     StringBuffer hqlSb = new StringBuffer("select count(*) from WxContent t where t.module = 'wechat_menu'");
/* 53:56 */     if (id == null) {
/* 54:57 */       hqlSb.append(" and parent.id is null");
/* 55:   */     } else {
/* 56:59 */       hqlSb.append(" and parent.id = " + id);
/* 57:   */     }
/* 58:61 */     Map map = new HashMap();
/* 59:62 */     long count = this.wxContentDao.countHqlResult(hqlSb.toString(), map);
/* 60:63 */     return Long.valueOf(count);
/* 61:   */   }
/* 62:   */   
/* 63:   */   public String dosave(WxContent wxContent, User user)
/* 64:   */   {
/* 65:68 */     return null;
/* 66:   */   }
/* 67:   */   
/* 68:   */   public WxContent findBySlug(String slug)
/* 69:   */   {
/* 70:72 */     Map map = new HashMap();
/* 71:73 */     StringBuffer hqlSb = new StringBuffer(" from WxContent t where t.slug = " + slug + " order by t.id");
/* 72:74 */     WxContent wxContent = (WxContent)this.wxContentDao.findUnique(hqlSb.toString(), map);
/* 73:75 */     return wxContent;
/* 74:   */   }
/* 75:   */   
/* 76:   */   public Page<WxContent> findContentPage(Page<WxContent> page, WxContent wxContent)
/* 77:   */   {
/* 78:79 */     return this.wxContentDao.findContentPage(page, wxContent);
/* 79:   */   }
/* 80:   */ }


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.service.impl.WxContentServiceImpl
 * JD-Core Version:    0.7.0.1
 */