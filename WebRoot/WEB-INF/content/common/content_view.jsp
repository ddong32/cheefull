<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/content/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html>
<head>
	<title>订单管理</title>
	<%@ include file="/WEB-INF/content/common/includewechat.jsp"%>
	<script type="text/javascript" src="${ctx}/scripts/content_view.js"></script>
	<link type="text/css" rel="stylesheet" href="${ctx}/styles/m/content_view.css"/>
	<script type="text/javascript">

	</script>
	<style> 
		select{ border:1px solid #b4d0e5;line-height:30px;margin:-1px;padding:4px 3px;font-size:13px;} 
	</style> 
</head>
<body class="list nobg">
    <div class="input" style="zoom: 1; padding:10px; min-width:1200px;">
        <p id="title"></p>
    </div>

    <form id="inputForm" class="validate" action="" method="post">
       	<input type="hidden" id="_contentID" value="${contentID}" />
       	<input type="hidden" id="ctx" value="${ctx}"/>
    </form>
    
    <!-- iframe id="reportFrame" src="" style="display:none; height:0"></iframe> -->
</body>
</html>