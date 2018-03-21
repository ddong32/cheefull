package com.chee.dao;

import com.chee.common.Page;
import com.chee.entity.Attachment;

public abstract interface AttachmentDao extends BaseDao<Attachment, Integer> {
    public abstract Page<Attachment> findAttachmentPage(Page<Attachment> paramPage, Attachment paramAttachment);
}
