package com.chee.service;

import com.chee.entity.Expence;
import com.chee.entity.ExpenceFlow;
import java.util.List;

public abstract interface ExpenceFlowService
  extends BaseService<ExpenceFlow, Integer>
{
  public abstract List<ExpenceFlow> findExpenceFlowList(Expence paramExpence);
}


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.service.ExpenceFlowService
 * JD-Core Version:    0.7.0.1
 */