<%@ page language="java" pageEncoding="UTF-8"%>
<script>
alert("您的账号在别处登录,您已经被请出系统!");
if (top.location != self.location) {
	top.location = self.location;
}
if (window.opener != null) {
	//window.opener.location = self.location;
	window.close();
}
if (window.dialogArguments != null) {
	//window.dialogArguments.location.href = self.location;
	window.close();
}
top.location = "../admin/logout";
</script>

