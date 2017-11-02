package com.chee.dao;

import com.chee.common.Page;
import com.chee.entity.WxContent;

public abstract interface WxContentDao
  extends BaseDao<WxContent, Integer>
{
  public abstract Page<WxContent> findContentPage(Page<WxContent> paramPage, WxContent paramWxContent);
}


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.dao.WxContentDao
 * JD-Core Version:    0.7.0.1
 */