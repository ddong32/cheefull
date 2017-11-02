package com.chee.dao;

import com.chee.entity.SysCode;
import java.util.List;

public abstract interface SysCodeDao
  extends BaseDao<SysCode, Integer>
{
  public abstract List<SysCode> getSysCodeList(int paramInt1, int paramInt2);
}


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.dao.SysCodeDao
 * JD-Core Version:    0.7.0.1
 */