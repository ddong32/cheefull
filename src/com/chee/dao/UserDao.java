package com.chee.dao;

import com.chee.entity.User;
import java.util.List;

public abstract interface UserDao
  extends BaseDao<User, Integer>
{
  public abstract List<User> getUserList(String paramString1, String paramString2);
}


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.dao.UserDao
 * JD-Core Version:    0.7.0.1
 */