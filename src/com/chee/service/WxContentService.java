package com.chee.service;

import com.chee.common.Page;
import com.chee.entity.User;
import com.chee.entity.WxContent;
import java.util.List;

public abstract interface WxContentService
  extends BaseService<WxContent, Integer>
{
  public abstract List<WxContent> findMenus();
  
  public abstract List<WxContent> findParentMenus();
  
  public abstract WxContent loadMenu(Integer paramInteger);
  
  public abstract Long findCountInNormalByParentId(Integer paramInteger);
  
  public abstract String dosave(WxContent paramWxContent, User paramUser);
  
  public abstract WxContent findBySlug(String paramString);
  
  public abstract Page<WxContent> findContentPage(Page<WxContent> paramPage, WxContent paramWxContent);
}


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.service.WxContentService
 * JD-Core Version:    0.7.0.1
 */