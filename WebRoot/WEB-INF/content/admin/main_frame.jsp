<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/content/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 transitional//EN" "http://www.w3.org/tr/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <title>喜洋洋国旅办公系统</title>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="stylesheet" type="text/css" href="${ctx}/styles/main.css" />
  <link rel="icon" href="${ctx}/images/favicon.ico" mce_href="${ctx}/images/favicon.ico" type="image/x-icon"/>
  <link rel="shortcut icon" href="${ctx}/images/favicon.ico" mce_href="${ctx}/images/favicon.ico" type="image/x-icon"/>
<script type="text/javascript">
	//审核窗口句柄 
	var auditWindow = null;
	function myunload() {
		if(auditWindow){
			auditWindow.close();
		}
	}
</script>
</head>
<frameset rows="94,*" cols="*" frameborder="no" border="0" framespacing="0">
	<frame src="${ctx}/admin/main!header.action" name="topFrame" scrolling="No" id="topFrame" noresize="noresize"/>
	<frameset cols="169,*" frameborder="no" border="0" framespacing="0">
	    <frame src="${ctx}/admin/main!left.action" name="leftFrame" scrolling="No" noresize="noresize" id="leftFrame"/>
		<frame src="${ctx}/admin/main!content.action" name="mainFrame" id="mainFrame"/>
	</frameset>
</frameset>
<noframes>
	<body></body>
</noframes>
</html>
