package com.chee.service;

import com.chee.entity.WxCode;
import java.util.List;

public abstract interface WxCodeService
  extends BaseService<WxCode, Integer>
{
  public abstract void menuSync();
  
  public abstract List<WxCode> findMenus();
  
  public abstract List<WxCode> findParentMenus();
  
  public abstract WxCode loadMenu(Integer paramInteger);
  
  public abstract Long findCountInNormalByParentId(Integer paramInteger);
}


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.service.WxCodeService
 * JD-Core Version:    0.7.0.1
 */