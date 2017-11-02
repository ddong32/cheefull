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
		$("input[name='expence.bxnr']").val($("textarea[name='expence.bxnr']").val());
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
	
	if($("#expence_id").val() != ""){
		$("#expence_fssj").val('<s:date name="expence.fssj" format="yyyy-MM-dd"/>');
	}
});

</script>
<div class="input">
	<div class="pub_inp">
		<form id="inputForm" class="validate" action="${ctx}/admin/expence!save.action" method="post">
			<s:hidden name="expence.id"></s:hidden>
			<s:hidden name="expence.bxnr"></s:hidden>
			<table class="enforce_table" id="expenceAddFrom" cellpadding="0" cellspacing="0" width="100%">
				<colgroup>
					<col width="90"/><col/>
					<col width=""/><col/>
					<col width="90"/><col/>
					<col width=""/><col/>
				</colgroup>
				<tr>
					<td align="right" height="24" class="enforcetd_bg">报销人：</td>
					<td><s:property value="expence.user.name"/></td>
                    <td rowspan="6" align="right" valign="middle" class="enforcetd_bg" width="90">报销内容：</td>
					<td rowspan="6">
                        <textarea name="expence.bxnr" cols="" rows="" class="textarea w250" maxlength="512" style="height:130px; width:98%; padding:2px; font-size:13px;"><s:property value="expence.bxnr"></s:property></textarea>
                    </td>
				</tr>
				<tr>
					<td align="right" height="24" class="enforcetd_bg">报销部门：</td>
					<td><s:property value="expence.user.department.name"/></td>
                </tr>
				<tr>
					<td align="right" height="24" class="enforcetd_bg">报销类型：</td>
					<td>
						<s:select name="expence.bxlx" list="expenceMap" listKey="key" listValue="%{value}" headerKey="" headerValue="请选择" rule="noempty" style="width:114px;"></s:select>
					</td>
                </tr>
				<tr>
					<td align="right" height="24" class="enforcetd_bg">发生日期：</td>
					<td><input type="text" class="Wdate" name="expence.fssj" id="expence_fssj" size="12" onfocus="WdatePicker()" readonly="true" style="width:112px;" rule="noempty" /></td>
                </tr>
				<tr>
					<td align="right" height="24" class="enforcetd_bg">报销金额：</td>
					<td><s:textfield name="expence.bxje" rule="noempty" maxlength="40" cssClass="w110"></s:textfield></td>
                </tr>
				<tr>
					<td align="right" height="24" class="enforcetd_bg">收款账号：</td>
					<td><s:textfield name="expence.skzh" rule="noempty" maxlength="40" cssClass="w110"></s:textfield></td>
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