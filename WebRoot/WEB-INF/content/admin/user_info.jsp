<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/content/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 transitional//EN" "http://www.w3.org/tr/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="height:100%;">
<HEAD>
	<title>编辑用户信息</title>
	<%@ include file="/WEB-INF/content/common/include.jsp"%>
	<style type="text/css">
	.formText{width:150px;}
	</style>
	<script>
	
	// increase the default animation speed to exaggerate the effect
	$.fx.speeds._default = 1000;
	jQuery.ajaxSettings.traditional = true;
	var isSuccess=true;
	$(function() {
		$( "#dialog" ).dialog({
			dialogClass:"ppisDialog departmentDialog",
			autoOpen: false,
			height: "auto",
			minHeight: 0,
			resizable: false,
			modal: true,
			open : function (){
				if ( $(this).find("ul").html()=="" ){
					$(this).html("<p>没有符合的数据</p>");
				}
			}
		});
	});
	$(document).ready(function(e) {
		$("#notice").dialog({
			dialogClass:"ppisDialog",
			autoOpen: false,
			resizable:false,
			modal: true,
			buttons: {
				"确定" : function(){
					$(this).dialog('close');
					if (isSuccess){
						location.reload();
					}
				}
			}
		});
		//提醒
		$("#notice1").dialog({
			dialogClass:"ppisDialog",
			autoOpen: false,
			resizable:false,
			modal: true,
			buttons: {
				"确定" : function(){
					$(this).dialog('close');
				}
			}
		});
		$("#cancel").bind('click',function (){
			$( "#popupDialog" ).dialog('close');
		});
		$("#inputForm").bind("submit", function(){
			var isIllegal = false;
			$("[rule]").each(function(index, element) {
				isIllegal |= testIllegal($(this));
			});
			if (isIllegal){
				$("#errInfo").show();
				return false;
			} else {
				$("#errInfo").hide();
			}
			var upcf = $("#upcf").val();
			var upsd = $("#upsd").val();
			if(upcf.length != upsd.length) {
				$("#notice1").html("两次输入密码不一致!").dialog('open');
				return false;
			}
			$("input[name='user.lxdz']").val($("textarea[name='user.lxdz']").val());
			$("input[name='user.phone']").val($("textarea[name='user.phone']").val());
			var param=getParam("inputForm");
			$.ajax({
				url: $(this).attr("action"),
				type: "POST",
				data: param,
				success: function(msg){
					isSuccess= (trim(msg)=="您的操作已成功!")?true :false;
					$("#notice").html(msg);
					$("#notice").dialog('open');
				}
			});
			return false;
		});
	});
	</script>
</HEAD>
<body class="con_r">
    <div class="input">
        <div id="notice" title="提示消息"></div>
        <div id="notice1" title="提示消息1"></div>
        <form id="inputForm" class="validate" action="${ctx}/admin/user!updateUserPassword.action" method="post">
            <div class="pub_inp_bg">
                <div class="pub_inp">
                    <table class="enforce_table" cellpadding="0" cellspacing="0" width="100%">
                        <s:hidden name="user.id"></s:hidden>
                        <colgroup>
                            <col width="100"/>
                            <col/>
                        </colgroup>
                        <tr height="36">
                        	<td align="right" class="enforcetd_bg">原密码：</td>
                            <td align="left">
                                <s:password id="upys" name="user.passwordCurrent" maxlength="20" rule="noempty" cssClass="formText"></s:password>
                            </td>
                        </tr>
                        <tr height="36">
                        	<td align="right" class="enforcetd_bg">新密码：</td>
                            <td align="left">
                                <s:password id="upsd" name="user.password" maxlength="20" rule="noempty" cssClass="formText"></s:password>
                            </td>
                        </tr>
                        <tr height="36">
                            <td align="right" class="enforcetd_bg">确认密码：</td>
                            <td align="left"><s:password id="upcf" name="user.passwordConfirm" maxlength="20" rule="noempty" cssClass="formText"></s:password></td>
                        </tr>
                    </table>
                    <div style="color: red" align="center">
                        <s:fielderror/>
                        <s:actionerror/>
                    </div>
                </div>
            </div>
            <div class="buttonArea_l">
                <input style="margin:0px;" type="submit" class="pub_but formButton" value="确  定" hidefocus="true" />&nbsp;
            </div>
        </form>
        <p id="errInfo" style="text-align:center;">信息填写不完整或格式错误！</p>
	</div>
</body>
</html>