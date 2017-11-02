package com.chee.dao;

import com.chee.common.Page;
import com.chee.entity.Expence;
import java.util.List;

public abstract interface ExpenceDao
  extends BaseDao<Expence, Integer>
{
  public abstract List<Integer> findAuditIdList(Expence paramExpence);
  
  public abstract Page<Expence> findExpencePage(Page<Expence> paramPage, Expence paramExpence, String paramString);
  
  public abstract Expence getExpenceIds(Integer paramInteger);
}


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.dao.ExpenceDao
 * JD-Core Version:    0.7.0.1
 */