<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/content/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 transitional//EN" "http://www.w3.org/tr/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>${systemCity}${systemName}</title>
    <%@ include file="/WEB-INF/content/common/include.jsp"%>
    <script type="text/javascript">
    var sessionID="";
    $(document).ready(function(){
    	$(".module").click(function(){
			window.parent.leftFrame.changeTree($(this).attr("id"));
			$(".down").removeClass("down");
			$(this).parent("li").addClass("down");
    	});
    	
    	$(".moduleHomePage").click(function(){
			$(".down").removeClass("down");
			$(this).parent("li").addClass("down");
    	});
    	
     });
     function myunload(){
        window.parent.myunload(); 
        if (top.location != self.location) {
            top.location = self.location;
        }
        if(window.opener != null) {
            window.opener.location = self.location;
            window.close();
        }
        if(window.dialogArguments != null) {
            window.dialogArguments.location.href = self.location;
            window.close();
        }
        top.location="${ctx}/admin/logout";
    }

    function homePage(){
    	parent.window.frames['mainFrame'].document.location.href="${ctx}/admin/main!content.action";
    }
    </script>
</head>
<body>
<div class="top_main">
    <div class="left_logo"></div>
    <div class="title_text">${systemCity}${systemName}</div>
    <div class="user">
        <ul>
            <li><span class="user_ico pub_l"></span>用户名：<security:authentication property="principal.name"/>， 角色：<security:authentication property='principal.roleNames'/>， 管理部门：<security:authentication property='principal.DepartmentName'/></li>
            <!-- 
            <li><a id="aboutBtn" href="javascript:;"><span class="help_ico"></span>关于</a></li>
            <li><a id="helpBtn" href="javascript:;"><span class="help_ico"></span>帮助</a></li>
             -->
            <li><a href="javascript:myunload();"><span class="exit_ico"></span>退出</a></li>
        </ul>
    </div>

</div>
<div class="nav">
    <ul>
    	<!-- li class="down"><a class="moduleHomePage" href="javascript:homePage();" onfocus="this.blur()">首页</a></li -->
    	<s:iterator value="resourceList" var="resource" status="st">
			<security:authorize ifAnyGranted="${resource.roleValues}">
				<li><a id="${resource.id}" class="module" href="javascript:;" onfocus="this.blur()">${resource.name}</a></li>
			</security:authorize>   
		</s:iterator>
    </ul>
</div>
<input type="hidden" id ="sessionId" value="${sessionId}"/>
</body>
</html>