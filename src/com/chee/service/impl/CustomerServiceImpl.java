/*  1:   */ package com.chee.service.impl;
/*  2:   */ 
/*  3:   */ import com.chee.common.Page;
/*  4:   */ import com.chee.dao.CustomerDao;
/*  5:   */ import com.chee.entity.Customer;
/*  6:   */ import com.chee.service.CustomerService;
/*  7:   */ import java.util.List;
/*  8:   */ import java.util.Set;
/*  9:   */ import javax.annotation.Resource;
/* 10:   */ import org.springframework.stereotype.Service;
/* 11:   */ 
/* 12:   */ @Service
/* 13:   */ public class CustomerServiceImpl
/* 14:   */   extends BaseServiceImpl<Customer, Integer>
/* 15:   */   implements CustomerService
/* 16:   */ {
/* 17:   */   @Resource
/* 18:   */   CustomerDao customerDao;
/* 19:   */   
/* 20:   */   @Resource
/* 21:   */   public void setBaseDao(CustomerDao customerDao)
/* 22:   */   {
/* 23:21 */     super.setBaseDao(customerDao);
/* 24:   */   }
/* 25:   */   
/* 26:   */   public Page<Customer> findPage(Page<Customer> page, Customer customer)
/* 27:   */   {
/* 28:25 */     return this.customerDao.findPage(page, customer);
/* 29:   */   }
/* 30:   */   
/* 31:   */   public List<Customer> getCustomerList(Integer grade)
/* 32:   */   {
/* 33:29 */     return this.customerDao.getCustomerList(grade);
/* 34:   */   }
/* 35:   */   
/* 36:   */   public List<Customer> getPathCustomerList(Set<Integer> paramSet)
/* 37:   */   {
/* 38:33 */     return this.customerDao.getPathCustomerList(paramSet);
/* 39:   */   }
/* 40:   */ }


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.service.impl.CustomerServiceImpl
 * JD-Core Version:    0.7.0.1
 */