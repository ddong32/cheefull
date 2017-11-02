<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/content/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>${systemCity}${systemName}</title>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8" />
    <meta http-equiv="Cache-Control" content="no-cache" />
    <link rel="icon" href="${ctx}/images/favicon.ico" mce_href="${ctx}/images/favicon.ico" type="image/x-icon"/>
	<link rel="shortcut icon" href="${ctx}/images/favicon.ico" mce_href="${ctx}/images/favicon.ico" type="image/x-icon"/>
    <link rel="stylesheet" type="text/css"  href="${ctx}/styles/login.css" />
	<script type="text/javascript" src="${ctx}/scripts/jquery-1.8.2.min.js"></script>
    <script type="text/javascript" src="${ctx}/scripts/jquery.cookie.js"></script>
    <script type="text/javascript">
    	if(this.location!=top.location)
		{
			top.location=this.location;
		}
		var tmpUsername="";
        //初始化页面时验证是否记住了密码
        $(document).ready(function() {
            if ($.cookie('PPIS_ISSAVE') == "true") {
                $('#issave').attr("checked", true);
                $("#j_username").val($.cookie('PPIS_USERSNAME'));
                $("#j_password").val($.cookie('PPIS_PASSWROD'));
            }
            $("#j_username").focus();
            $("#j_username").bind('focus',function(){
            	tmpUsername=$(this).val();
            });
            $("#j_username").bind('keyup',function(){
            	if(tmpUsername!=$(this).val()){
            		$("#j_password").val("");
            	}
            });
            $("#j_username").bind('click',function(){});
            
            document.onkeydown = function(e){ 
			    var ev = document.all ? window.event : e;
			    if(ev.keyCode==13) {
					onSubmit();
				}
			}
        });
        
        
        //保存用户信息
        function onSubmit() {
        	var result = false;
       		$.ajax({
				type: "POST",
				cache: false,
				async:false,
				url: '${ctx}/admin/user!checkSession.action',
				success: function(msg){
					if ($("#issave").attr("checked") == "checked") {
		               	var loginName = $("#j_username").val();
		                var userPass = $("#j_password").val();
		                $.cookie('PPIS_ISSAVE', "true", { expires: 30 }); // 存储一个带30天期限的 cookie
		                $.cookie('PPIS_USERSNAME', loginName, { expires: 30 });
		                $.cookie('PPIS_PASSWROD', userPass, { expires: 30 });
		            } else {
		                $.cookie('PPIS_ISSAVE', "false", { expires: -1 });
		                $.cookie('PPIS_USERSNAME', '', { expires: -1 });
		                $.cookie('PPIS_PASSWROD', '', { expires: -1 });
		            }
					result = true;
				},
                complete: function() {
				}
			});
			if(result){
				$("form").submit();
			}
        }
    </script>
</head>
<body scroll="no">

    	
<div class="login_outer1">
	<div class="login_bg">
		<div id="title" class="title">${systemCity}${systemName}</div>
	 	<div id="loginPanel" class="loginPanel">
        	<form action="${ctx}/admin/loginVerify" method="post" name="login" target="_self">
		    <table class="loginTable">
				<tr>
					<th>用户名：</th>
					<td><input type="text" id="j_username" name="j_username" /></td>
				</tr>
				<tr>
					<th>密　码：</th>
					<td><input type="password" id="j_password" name="j_password" /></td>
				</tr>
				<tr>
					<th></th>
					<td>
						<label class="rememberLabel">
							<input type="checkbox" id="issave" name="issave" title="记住密码" />
							<label for="issave">记住密码</label>
						</label>
					</td>
				</tr>
				<tr>
					<th></th>
					<td>
                        <a class="loginBtn" id="loginBtn" href="javascript:void(0)" onclick="onSubmit()"></a>
					</td>
				</tr>
				<tr>
					<th style="width:100px"></th>
					<td>
						<div id="errorMsg" style="color: red; text-align:center">
                            <s:fielderror/>
                            <s:actionerror/>
                            <%
                                String same = request.getParameter("same");
                                if(same != null && same.equals("true")){
                            %>
                                    <p>您的账号在别处登录,您已经被请出系统!</p>
                                    <p>同一账号不可以多处同时登陆.</p>
                                    <p>请保管好您的账号和密码!</p>
                            <%		
                                }else if(same != null && same.equals("more")){
                            %> 
                                    <p>您的账号已经退出系统!</p>
                                    <p>请勿在同一客户端重复登录多个账号!</p>
                            <%
                                }
                            %>
                        </div>
					</td>
				</tr>
			</table>
            </form>
		</div>
	</div>
    
	<div class="copyright">
		<span><br /></span>
	</div>
    
</div>
</body>
</html>
