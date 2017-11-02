<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/content/common/taglibs.jsp"%>
<script>

$.fx.speeds._default = 1000;
jQuery.ajaxSettings.traditional = true;
var isSuccess=true;

$(function() {
	
	$("#notice").dialog({
		dialogClass:"ppisDialog",
		autoOpen: false,
		resizable:false,
		modal: true,
		buttons: {
			"确定" : function(){
				$(this).dialog('close');
				if (isSuccess){
					$("#listForm").submit();
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
	$( "#dialog" ).dialog({
		dialogClass:"ppisDialog departmentDialog",
		autoOpen: false,
		maxWidth: 500,
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

	$( "#user_department_name" ).click(function() {
		$( "#dialog" ).dialog( "open" );
		$(".ui-widget-overlay").css("height",document.body.scrollHeight);
		return false;
	});

	$("#dept").jstree({
		"json_data" :
		{
			"ajax" :
			{
				"url" : "${ctx}/admin/department!ajaxDepartmentTree.action",
				"data" : function (n) {return { 'department.id' : n.attr ? n.attr("id") : "" };}
			}
		},
		"themes" :
		{
			"theme" : "default"
		},
		//"core" : { "initially_open" : [ "phtml_1","phtml_3","phtml_4"] },
		"plugins" : [ "themes", "json_data", "ui" ]
	})
	.bind("select_node.jstree", function (event, data) { 
		$("#user_department_name").val(data.rslt.obj.attr("title"));
		$("#user_department_id").val(data.rslt.obj.attr("id"));
		$("#dialog").dialog("close");
	})
	.bind("loaded.jstree", function (event, data) { 
		data.inst.open_all(-1);
	});
	
});

$(document).ready(function(e) {
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
		if(!checkNumEn($("#uname").val())) {
			$("#notice1").html("用户名格式输入有误!").dialog('open');
			return false;
		}
		var upcf = $("#upcf").val();
		var upsd = $("#upsd").val();
		if(upcf.length != upsd.length) {
			$("#notice1").html("两次输入密码不一致!").dialog('open');
			return false;
		}
		var param=getParam("inputForm");
		$.ajax({
			url: $(this).attr("action"),
			type: "POST",
			data: param,
			success: function(msg){
				isSuccess= (trim(msg)=="您的操作已成功!")?true :false;
				$("#notice").html(msg);
				$("#notice").dialog('open');
				$(".ui-widget-overlay").css("height", document.body.scrollHeight);
			}
		});
		return false;
	});
});
</script>
<div class="input">
	<div id="dialog" title="组织机构" style="display: none">
		<div id="dept" class="demo"></div>
	</div>
	<div id="notice" title="提示消息"></div>
    <div id="notice1" title="提示消息"></div>
	<div class="pub_inp">
		<form id="inputForm" class="validate" action="${ctx}/admin/user!save.action" method="post">
			<s:hidden name="user.id"></s:hidden>
			<table class="enforce_table" cellpadding="0" cellspacing="0" width="100%">
				<colgroup>
					<col width="75"/>
					<col/>
				</colgroup>
				<tr>
					<td align="right" height="24" class="enforcetd_bg">登录名：</td>
					<td align="left"><s:textfield id="uname" name="user.username" maxlength="20" rule="noempty numchar" onpaste="return false" cssClass="formText"></s:textfield><label class="requireField">*</label></td>
				</tr>
				<tr>
					<td align="right" height="24" class="enforcetd_bg">姓名：</td>
					<td align="left"><s:textfield name="user.name" maxlength="12" rule="noempty" cssClass="formText"></s:textfield><label class="requireField">*</label></td>
				</tr>
				<tr>
					<td align="right" height="24" class="enforcetd_bg">用户密码：</td>
					<td align="left"><s:password id="upsd" name="user.password" maxlength="16" rule="numchar" cssClass="formText" onkeypress="return checkNumAndEn(event)" onpaste="return false"></s:password><label class="requireField">* 如果要修改密码，请填写密码</label></td>
				</tr>
				<tr height="29">
					<td align="right" height="24" class="enforcetd_bg">确认密码：</td>
					<td align="left"><s:password id="upcf" name="user.passwordConfirm" maxlength="16" rule="numchar" cssClass="formText" onpaste="return false" onkeypress="return checkNumAndEn(event)"></s:password><label class="requireField">* 若留空，密码将保持不变</label></td>
				</tr>
				<tr>
					<td align="right" height="24" class="enforcetd_bg">手机号码：</td>
					<td align="left"><s:textfield name="user.phone" maxlength="11" rule="num" cssClass="formText"></s:textfield></td>
				</tr>
				<tr>
					<td align="right" height="24" class="enforcetd_bg">紧急联系：</td>
					<td align="left"><s:textfield name="user.jjlx" maxlength="11" rule="num" cssClass="formText"></s:textfield></td>
				</tr>
				<tr>
					<td align="right" height="24" class="enforcetd_bg">ＱＱ号码：</td>
					<td align="left"><s:textfield name="user.qq" maxlength="11" rule="num" cssClass="formText"></s:textfield></td>
				</tr>
				<tr>
					<td align="right" height="24" class="enforcetd_bg">星座：</td>
					<td align="left"><s:textfield name="user.xz" maxlength="8" cssClass="formText"></s:textfield></td>
				</tr>
				<tr>
					<td align="right" height="24" class="enforcetd_bg">血型：</td>
					<td align="left"><s:textfield name="user.xx" maxlength="8" rule="numchar" cssClass="formText" style="text-transform: uppercase"></s:textfield></td>
				</tr>
				<tr>
					<td align="right" height="24" class="enforcetd_bg">入职时间：</td>
					<td align="left"><s:textfield name="user.rzsj" maxlength="12" cssClass="formText"></s:textfield></td>
				</tr>
				<tr>
					<td align="right" height="24" class="enforcetd_bg">身份证号：</td>
					<td align="left"><s:textfield name="user.idCard" maxlength="18" cssClass="formText"></s:textfield></td>
				</tr>
				<tr>
					<td align="right" height="24" class="enforcetd_bg">户籍地址：</td>
					<td align="left"><s:textfield name="user.lxdz" maxlength="100" cssClass="formText"></s:textfield></td>
				</tr>
				<tr>
					<td align="right" height="24" class="enforcetd_bg">备注：</td>
					<td align="left"><s:textfield name="user.bz" maxlength="100" cssClass="formText"></s:textfield></td>
				</tr>
				<tr>
					<td align="right" height="24" class="enforcetd_bg">员工编号：</td>
					<td align="left"><s:textfield name="user.jobNumber" maxlength="15" rule="numchar" cssClass="formText"></s:textfield></td>
				</tr>
				<tr>
					<td align="right" height="24" class="enforcetd_bg">邮箱：</td>
					<td align="left"><s:textfield name="user.email" maxlength="50" rule="mail" cssClass="formText"></s:textfield></td>
				</tr>
				<tr>
					<td align="right" height="24" class="enforcetd_bg">组织机构：</td>
					<td align="left">
						<s:hidden name="user.department.id"></s:hidden>
						<s:textfield name="user.department.name" readonly="true" id="user_department_name" cssClass="formText" rule="noempty"></s:textfield>
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr style="display:none">
					<td align="right" height="24" class="enforcetd_bg">审核等级：</td>
					<td align="left">
						<s:select name="user.grade" list="#{1:'一级管理',2:'二级财务',3:'三级主管',4:'四级员工'}" /><label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<td align="right" class="enforcetd_bg">所属角色：</td>
					<td align="left"><!-- multiple="true"  -->
						<s:select name="roleIds" list="allRole" listKey="id" listValue="name" rule="noempty" /><label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<td align="right" height="24" class="enforcetd_bg">是否启用：</td>
					<td align="left">
						<s:select name="user.isAccountEnabled" list="#{true:'启用',false:'禁用'}" /><label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<td align="right" height="24" class="enforcetd_bg">是否锁定：</td>
					<td align="left">
						<s:select name="user.isAccountLocked" list="#{false:'否',true:'是'}" /><label class="requireField">*</label>
					</td>
				</tr>
			</table>
			<div style="color: red" align="center">
				<s:fielderror/>
			 	<s:actionerror/>
			</div>
			<div class="buttonArea">
				<input type="submit" class="pub_but formButton" value="确  定" hidefocus="true" />&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" class="pub_but formButton" value="返  回" hidefocus="true" id="cancel" />
			</div>
		</form>
        <p id="errInfo" style="text-align:center;">信息填写不完整或格式错误！</p>
	</div>
</div>