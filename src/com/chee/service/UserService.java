package com.chee.service;

import com.chee.entity.User;
import java.util.List;

public abstract interface UserService
  extends BaseService<User, Integer>
{
  public abstract List<User> getUserList(String paramString1, String paramString2);
}


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.service.UserService
 * JD-Core Version:    0.7.0.1
 */