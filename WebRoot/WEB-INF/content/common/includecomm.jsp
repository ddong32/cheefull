<%@ page language="java" pageEncoding="UTF-8"%>
<script language="JavaScript">
<!--
	function nocontextmenu() {
		event.cancelBubble = true
		event.returnValue = false;

		return false;
	}

	//document.oncontextmenu = nocontextmenu; // for IE5+
//-->
</script>
<!-- 提示信息框 -->
<div class="addWin" id="popWin" style="display: none">
	<div class="title">
		<span class="close" onmouseover="this.className='close closeHover'" onmouseout="this.className='close'"></span>
		<h5>提示信息</h5>
	</div>
	<div style="padding: 6px;">
		<p style="margin-left:5px;"></p>
	</div>
</div>
<input type="hidden" id="username" value="<s:property value="#request.username"/>"/> 
<input type="hidden" id="password" value="<s:property value="#request.password"/>"/> 
