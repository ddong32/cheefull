<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%   
response.setHeader("Pragma","No-cache");   
response.setHeader("Cache-Control","no-cache");   
response.setHeader("Cache-Control", "no-store");   
response.setDateHeader("Expires", 0);   
%> 
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
