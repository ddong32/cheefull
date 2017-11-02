<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/content/common/taglibs.jsp"%>

<s:if test="errorMessages.size() > 0">
	<s:iterator value="errorMessages" var="list" status="status">
		<s:property value="#list"/>
	</s:iterator>
</s:if>
<s:elseif test="actionMessages.size() > 0">
	<s:iterator value="actionMessages" var="list" status="status">
		<s:property value="#list"/>
	</s:iterator>
</s:elseif>
<s:elseif test="fieldErrors.size() > 0">
	<s:iterator value="fieldErrors.key" var="list" status="status">
		<s:property value="#list"/>
	</s:iterator>
</s:elseif>
<s:else>
	您的操作已成功!
</s:else>