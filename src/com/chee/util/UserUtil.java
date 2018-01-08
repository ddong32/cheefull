package com.chee.util;

import com.chee.entity.User;
import com.chee.service.UserService;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service("UserTest")
public class UserUtil {
    @Resource
    private UserService userService;

    public User getLoginUser() {
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            if ((StringUtils.isNotEmpty(username)) && (StringUtils.isNotEmpty(password))) {
                String passwordMd5 = DigestUtils.md5Hex(password);
                List list = this.userService.getUserList(username, passwordMd5);
                if (list.size() > 0) {
                    User user = (User) list.get(0);
                    if (StringUtils.isEmpty(user.getDepartmentCode())) {
                        user.setDepartmentCode(user.getDepartment().getCode());
                    }
                    if (StringUtils.isEmpty(user.getDepartmentName())) {
                        user.setDepartmentName(user.getDepartment().getName());
                    }
                    return user;
                }
            }
        } catch (Exception localException) {
            if (SecurityContextHolder.getContext().getAuthentication() != null) {
                return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            }
        }
        return (User) this.userService.get(Integer.valueOf(1));
    }
}