package com.chee.dao;

import com.chee.common.Page;
import com.chee.entity.Attachment;

public abstract interface AttachmentDao
  extends BaseDao<Attachment, Integer>
{
  public abstract Page<Attachment> findAttachmentPage(Page<Attachment> paramPage, Attachment paramAttachment);
}


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.dao.AttachmentDao
 * JD-Core Version:    0.7.0.1
 */