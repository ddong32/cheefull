/*  1:   */ package com.chee.action.exception;
/*  2:   */ 
/*  3:   */ import com.opensymphony.xwork2.ActionContext;
/*  4:   */ import com.opensymphony.xwork2.ActionInvocation;
/*  5:   */ import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
/*  6:   */ import java.io.PrintWriter;
/*  7:   */ import java.io.StringWriter;
/*  8:   */ import org.apache.log4j.Logger;
/*  9:   */ 
/* 10:   */ public class ExceptionInterceptor
/* 11:   */   extends AbstractInterceptor
/* 12:   */ {
/* 13:11 */   private Logger logger = Logger.getLogger(getClass());
/* 14:   */   private static final long serialVersionUID = -8398129435681085658L;
/* 15:   */   public static final String EXCEPTION = "exception";
/* 16:   */   
/* 17:   */   public String intercept(ActionInvocation invocation)
/* 18:   */     throws Exception
/* 19:   */   {
/* 20:   */     try
/* 21:   */     {
/* 22:17 */       return invocation.invoke();
/* 23:   */     }
/* 24:   */     catch (Exception e)
/* 25:   */     {
/* 26:19 */       ActionContext context = invocation.getInvocationContext();
/* 27:20 */       StringWriter writer = new StringWriter();
/* 28:21 */       e.printStackTrace(new PrintWriter(writer, true));
/* 29:22 */       context.put("tipMessage", e.getMessage());
/* 30:23 */       context.put("tipCourse", writer.toString());
/* 31:24 */       this.logger.error("具体报错信息：" + writer.toString());
/* 32:   */     }
/* 33:26 */     return "exception";
/* 34:   */   }
/* 35:   */ }


/* Location:           C:\Users\asus\Desktop\full\WEB-INF\classes\
 * Qualified Name:     com.chee.action.exception.ExceptionInterceptor
 * JD-Core Version:    0.7.0.1
 */