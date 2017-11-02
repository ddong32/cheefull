/*  1:   */ package com.chee.util;
/*  2:   */ 
/*  3:   */ import com.chee.entity.Department;
/*  4:   */ import com.chee.entity.User;
/*  5:   */ import com.chee.service.UserService;
/*  6:   */ import java.util.List;
/*  7:   */ import javax.annotation.Resource;
/*  8:   */ import javax.servlet.http.HttpServletRequest;
/*  9:   */ import org.apache.commons.codec.digest.DigestUtils;
/* 10:   */ import org.apache.commons.lang.StringUtils;
/* 11:   */ import org.apache.struts2.ServletActionContext;
/* 12:   */ import org.springframework.security.Authentication;
/* 13:   */ import org.springframework.security.context.SecurityContext;
/* 14:   */ import org.springframework.security.context.SecurityContextHolder;
/* 15:   */ import org.springframework.stereotype.Service;
/* 16:   */ 
/* 17:   */ @Service("UserTest")
/* 18:   */ public class UserUtil
/* 19:   */ {
/* 20:   */   @Resource
/* 21:   */   private UserService userService;
/* 22:   */   
/* 23:   */   public User getLoginUser()
/* 24:   */   {
/* 25:   */     try
/* 26:   */     {
/* 27:22 */       HttpServletRequest request = ServletActionContext.getRequest();
/* 28:23 */       String username = request.getParameter("username");
/* 29:24 */       String password = request.getParameter("password");
/* 30:25 */       if ((StringUtils.isNotEmpty(username)) && (StringUtils.isNotEmpty(password)))
/* 31:   */       {
/* 32:26 */         String passwordMd5 = DigestUtils.md5Hex(password);
/* 33:27 */         List list = this.userService.getUserList(username, passwordMd5);
/* 34:28 */         if (list.size() > 0)
/* 35:   */         {
/* 36:29 */           User user = (User)list.get(0);
/* 37:30 */           if (StringUtils.isEmpty(user.getDepartmentCode())) {
/* 38:31 */             user.setDepartmentCode(user.getDepartment().getCode());
/* 39:   */           }
/* 40:33 */           if (StringUtils.isEmpty(user.getDepartmentName())) {
/* 41:34 */             user.setDepartmentName(user.getDepartment().getName());
/* 42:   */           }
/* 43:36 */           return user;
/* 44:   */         }
/* 45:   */       }
/* 46:   */     }
/* 47:   */     catch (Exception localException)
/* 48:   */     {
/* 49:41 */       if (SecurityContextHolder.getContext().getAuthentication() != null) {
/* 50:42 */         return (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
/* 51:   */       }
/* 52:   */     }
/* 53:44 */     return (User)this.userService.get(Integer.valueOf(1));
/* 54:   */   }
/* 55:   */ }


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.util.UserUtil
 * JD-Core Version:    0.7.0.1
 */