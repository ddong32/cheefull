package com.chee.dao;

import com.chee.entity.Expence;
import com.chee.entity.ExpenceFlow;
import java.util.List;

public abstract interface ExpenceFlowDao
  extends BaseDao<ExpenceFlow, Integer>
{
  public abstract List<ExpenceFlow> findExpenceFlowList(Expence paramExpence);
}


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.dao.ExpenceFlowDao
 * JD-Core Version:    0.7.0.1
 */