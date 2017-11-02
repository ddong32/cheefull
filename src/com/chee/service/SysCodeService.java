package com.chee.service;

import com.chee.entity.SysCode;
import java.util.List;

public abstract interface SysCodeService
  extends BaseService<SysCode, Integer>
{
  public abstract List<SysCode> getSysCodeList(int paramInt1, int paramInt2);
}


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.service.SysCodeService
 * JD-Core Version:    0.7.0.1
 */