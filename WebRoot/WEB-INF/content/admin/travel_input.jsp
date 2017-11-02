<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/content/common/taglibs.jsp"%>
<script>

$.fx.speeds._default = 1000;
jQuery.ajaxSettings.traditional = true;
var isSuccess=true;

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
		width: 600,
		height: "auto",
		minHeight: 0,
		resizable: true,
		modal: true
	});
	
	$("#cancel").bind('click',function (){
		$( "#popupDialog" ).dialog('close');
		$("#listForm").submit();
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
		$("#inputForm input[name='travel.line']").val($("textarea[name='travel.line']").val());
		var param=getParam("inputForm");
		$.ajax({
			url: "${ctx}/admin/travel!save.action",
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
	<div id="notice" title="提示消息"></div>
    <div id="dialog" title="线路名称"></div>
	<div class="pub_inp">
		<form id="inputForm" class="validate" action="${ctx}/admin/travel!save.action" method="post">
			<s:hidden name="travel.id"></s:hidden>
			<s:hidden name="redirectionUrl"></s:hidden>
			<table class="enforce_table" id="travelAddFrom" cellpadding="0" cellspacing="0" width="100%">
				<colgroup>
					<col width="75"/><col/>
				</colgroup>
				<tr>
					<td align="right" height="24" class="enforcetd_bg">旅行线路:</td>
					<td align="left">
						<s:textarea name="travel.line" rule="noempty" maxlength="512" cssClass="textarea" cols="20" rows="10" style="width:310px; height:50px; overflow:hidden; word-wrap:break-word; word-break:break-all;"></s:textarea>
						<label class="requireField">*</label>
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
				<s:hidden name="travel.line"></s:hidden>
			</div>
		</form>
        <p id="errInfo" style="text-align:center;">信息填写不完整或格式错误！</p>
	</div>
</div>