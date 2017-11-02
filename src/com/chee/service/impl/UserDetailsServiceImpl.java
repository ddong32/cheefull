/*  1:   */ package com.chee.service.impl;
/*  2:   */ 
/*  3:   */ import com.chee.dao.UserDao;
/*  4:   */ import com.chee.entity.Department;
/*  5:   */ import com.chee.entity.Role;
/*  6:   */ import com.chee.entity.User;
/*  7:   */ import java.util.HashSet;
/*  8:   */ import java.util.Set;
/*  9:   */ import javax.annotation.Resource;
/* 10:   */ import org.springframework.dao.DataAccessException;
/* 11:   */ import org.springframework.security.GrantedAuthority;
/* 12:   */ import org.springframework.security.GrantedAuthorityImpl;
/* 13:   */ import org.springframework.security.userdetails.UserDetailsService;
/* 14:   */ import org.springframework.security.userdetails.UsernameNotFoundException;
/* 15:   */ import org.springframework.stereotype.Service;
/* 16:   */ import org.springframework.transaction.annotation.Transactional;
/* 17:   */ 
/* 18:   */ @Service
/* 19:   */ @Transactional
/* 20:   */ public class UserDetailsServiceImpl
/* 21:   */   implements UserDetailsService
/* 22:   */ {
/* 23:   */   @Resource
/* 24:   */   private UserDao userDao;
/* 25:   */   
/* 26:   */   public User loadUserByUsername(String username)
/* 27:   */     throws UsernameNotFoundException, DataAccessException
/* 28:   */   {
/* 29:26 */     User user = (User)this.userDao.get("username", username);
/* 30:27 */     if (user == null) {
/* 31:28 */       throw new UsernameNotFoundException("用户[" + username + "]不存在!");
/* 32:   */     }
/* 33:30 */     user.setAuthorities(getGrantedAuthorities(user));
/* 34:31 */     if (user.getDepartment() != null)
/* 35:   */     {
/* 36:32 */       user.setDepartmentCode(user.getDepartment().getCode());
/* 37:33 */       user.setDepartmentName(user.getDepartment().getName());
/* 38:   */     }
/* 39:   */     else
/* 40:   */     {
/* 41:35 */       user.setDepartmentCode("001");
/* 42:36 */       user.setDepartmentName("001");
/* 43:   */     }
/* 44:38 */     return user;
/* 45:   */   }
/* 46:   */   
/* 47:   */   public GrantedAuthority[] getGrantedAuthorities(User user)
/* 48:   */   {
/* 49:42 */     Set grantedAuthorities = new HashSet();
/* 50:43 */     for (Role role : user.getRoleSet()) {
/* 51:44 */       grantedAuthorities.add(new GrantedAuthorityImpl(role.getValue()));
/* 52:   */     }
/* 53:46 */     return (GrantedAuthority[])grantedAuthorities.toArray(new GrantedAuthority[grantedAuthorities.size()]);
/* 54:   */   }
/* 55:   */ }


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.service.impl.UserDetailsServiceImpl
 * JD-Core Version:    0.7.0.1
 */