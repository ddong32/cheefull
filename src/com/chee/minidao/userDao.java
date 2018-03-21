package com.chee.minidao;

import java.util.List;
import java.util.Map;

import org.jeecgframework.minidao.annotation.Arguments;
import org.jeecgframework.minidao.annotation.MiniDao;
import org.jeecgframework.minidao.annotation.Param;
import org.jeecgframework.minidao.annotation.ResultType;
import org.jeecgframework.minidao.annotation.Sql;

import com.chee.entity.User;

@MiniDao
public interface userDao {

    @Arguments({ "user" })
    @Sql("select * from user")
    List<Map<String, Object>> getAllUser(User user);

}