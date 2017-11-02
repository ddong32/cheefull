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
		dialogClass:"ppisDialog",
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
	
	$("#cancel").bind('click',function (){
		$( "#popupDialog" ).dialog('close');
		$("#listForm").submit();
	});
});

$(document).ready(function(e) {
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
	<div class="pub_inp">
		<form id="inputForm" class="validate" action="${ctx}/admin/bank!save.action" method="post">
			<s:hidden name="bank.id"></s:hidden>
			<s:hidden name="redirectionUrl"></s:hidden>
			<table class="enforce_table" id="businessAddFrom" cellpadding="0" cellspacing="0" width="100%">
				<colgroup>
					<col width="90"/><col/>
				</colgroup>
				<tr>
					<td align="right" height="24" class="enforcetd_bg">登记名:</td>
					<td><s:textfield name="bank.djm" rule="noempty" maxlength="20" cssClass="pub_input"></s:textfield></td>
				</tr>
				<tr>
					<td align="right" height="24" class="enforcetd_bg">户名:</td>
					<td><s:textfield name="bank.name" rule="noempty" maxlength="20" cssClass="pub_input"></s:textfield></td>
				</tr>
				<tr>
					<td align="right" height="24" class="enforcetd_bg">余额:</td>
					<td><s:textfield name="bank.cush" rule="noempty float" maxlength="20" cssClass="pub_input"></s:textfield></td>
				</tr>
				<tr>
					<td align="right" height="24" class="enforcetd_bg">卡号:</td>
					<td><s:textfield name="bank.code" rule="noempty num" maxlength="30" cssClass="pub_input"></s:textfield></td>
				</tr>
				<tr>
					<td align="right" height="24" class="enforcetd_bg">开户行:</td>
					<td><s:textfield name="bank.khh" rule="noempty" maxlength="40" cssClass="pub_input w200"></s:textfield></td>
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
	<div id="notice" title="提示消息"></div>
    <div id="notice1" title="提示消息"></div>
</div>