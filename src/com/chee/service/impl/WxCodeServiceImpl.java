/*  1:   */ package com.chee.service.impl;
/*  2:   */ 
/*  3:   */ import com.chee.dao.WxCodeDao;
/*  4:   */ import com.chee.entity.WxCode;
/*  5:   */ import com.chee.service.WxCodeService;
/*  6:   */ import java.util.HashMap;
/*  7:   */ import java.util.List;
/*  8:   */ import java.util.Map;
/*  9:   */ import javax.annotation.Resource;
/* 10:   */ import org.springframework.stereotype.Service;
/* 11:   */ 
/* 12:   */ @Service
/* 13:   */ public class WxCodeServiceImpl
/* 14:   */   extends BaseServiceImpl<WxCode, Integer>
/* 15:   */   implements WxCodeService
/* 16:   */ {
/* 17:   */   @Resource
/* 18:   */   private WxCodeDao wxCodeDao;
/* 19:   */   
/* 20:   */   @Resource
/* 21:   */   public void setBaseDao(WxCodeDao wxCodeDao)
/* 22:   */   {
/* 23:22 */     super.setBaseDao(wxCodeDao);
/* 24:   */   }
/* 25:   */   
/* 26:   */   public List<WxCode> findMenus()
/* 27:   */   {
/* 28:27 */     Map map = new HashMap();
/* 29:28 */     StringBuffer hqlSb = new StringBuffer(" from WxCode t where t.module = 'wechat_menu' order by order_number");
/* 30:29 */     List<WxCode> wechat_menus = this.wxCodeDao.find(hqlSb.toString(), map);
/* 31:30 */     return wechat_menus;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public List<WxCode> findParentMenus()
/* 35:   */   {
/* 36:34 */     Map map = new HashMap();
/* 37:35 */     StringBuffer hqlSb = new StringBuffer(" from WxCode t where t.module = 'wechat_menu' and parent.id is null order by order_number");
/* 38:36 */     List<WxCode> wechat_menus = this.wxCodeDao.find(hqlSb.toString(), map);
/* 39:37 */     return wechat_menus;
/* 40:   */   }
/* 41:   */   
/* 42:   */   public WxCode loadMenu(Integer id)
/* 43:   */   {
/* 44:41 */     WxCode wechat_menus = (WxCode)this.wxCodeDao.get(id);
/* 45:42 */     return wechat_menus;
/* 46:   */   }
/* 47:   */   
/* 48:   */   public Long findCountInNormalByParentId(Integer id)
/* 49:   */   {
/* 50:46 */     StringBuffer hqlSb = new StringBuffer("select count(*) from WxCode t where t.module = 'wechat_menu'");
/* 51:47 */     if (id == null) {
/* 52:48 */       hqlSb.append(" and parent.id is null");
/* 53:   */     } else {
/* 54:50 */       hqlSb.append(" and parent.id = " + id);
/* 55:   */     }
/* 56:52 */     Map map = new HashMap();
/* 57:53 */     long count = this.wxCodeDao.countHqlResult(hqlSb.toString(), map);
/* 58:54 */     return Long.valueOf(count);
/* 59:   */   }
/* 60:   */   
/* 61:   */   public void menuSync() {}
/* 62:   */ }


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.service.impl.WxCodeServiceImpl
 * JD-Core Version:    0.7.0.1
 */