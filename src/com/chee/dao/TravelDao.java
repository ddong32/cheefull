package com.chee.dao;

import com.chee.common.Page;
import com.chee.entity.Travel;

public abstract interface TravelDao
  extends BaseDao<Travel, Integer>
{
  public abstract Page<Travel> findTravelDialogPage(Page<Travel> paramPage, Travel paramTravel);
}


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.dao.TravelDao
 * JD-Core Version:    0.7.0.1
 */