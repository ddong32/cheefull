package com.chee.service;

import com.chee.common.Page;
import com.chee.entity.Cooperator;

public abstract interface CooperatorService
  extends BaseService<Cooperator, Integer>
{
  public abstract Page<Cooperator> findCooperatorDialogPage(Page<Cooperator> paramPage, Cooperator paramCooperator);
}


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.service.CooperatorService
 * JD-Core Version:    0.7.0.1
 */