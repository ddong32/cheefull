<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/content/common/taglibs.jsp"%>
<script>
jQuery.ajaxSettings.traditional = true;
var isSuccess=true;
function trim(str){  
	var notValid=/(^\s)|(\s$)/;  
	while(notValid.test(str)){  
	  str=str.replace(notValid,"");
	}
	return str;
}
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
		var param=new Object;
		$("#inputForm input[type=text],#inputForm input[type=hidden],#inputForm input[type=password],#inputForm select").each(function(index, element) {
			param[$(this).attr("name")]=$(this).val();
			
		});
		param['resourceIdStr']=$("#resourceIdStr").val();
		$.ajax({
			url: $(this).attr("action"),
			type: "POST",
			data: param,
			success: function(msg){
				isSuccess= (trim(msg)=="您的操作已成功!")?true :false;
				$("#notice").html(msg);
				$("#notice").dialog('open');
				$(".ui-widget-overlay").css("height",document.body.scrollHeight);
			}
		});
		return false;
	});
});
</script>
<div class="input">
	<div class="pub_inp">
    <div id="notice" title="提示消息"></div>
		<form id="inputForm" class="validate" action="${ctx}/admin/sys_code!save.action" method="post">
			<table class="enforce_table" cellpadding="0" cellspacing="0" width="100%">
				<s:hidden name="sysCode.id"></s:hidden>
				<colgroup>
					<col width="80"/>
					<col/>
				</colgroup>
				<tr height="29">
					<td align="right">字典编号:</td>
					<td align="left"><s:textfield name="sysCode.codeId" rule="noempty num" maxlength="9" cssClass="formText"></s:textfield><label class="requireField">*</label></td>
				</tr>
				<tr height="29">
					<td align="right">字典编码:</td>
					<td align="left"><s:textfield name="sysCode.codeCode" rule="noempty" maxlength="20" cssClass="formText"></s:textfield><label class="requireField">*</label></td>
				</tr>
				<tr height="29">
					<td align="right">字典名称:</td>
					<td align="left"><s:textfield name="sysCode.codeName" rule="noempty" maxlength="20" cssClass="formText"></s:textfield><label class="requireField">*</label></td>
				</tr>
				<tr height="29">
					<td align="right">字典类型:</td>
					<td align="left"><s:textfield name="sysCode.codeType" rule="noempty num" maxlength="9" cssClass="formText"></s:textfield><label class="requireField">*</label></td>
				</tr>
				<tr height="29">
					<td align="right">类型名称:</td>
					<td align="left"><s:textfield name="sysCode.codeTypeName" rule="noempty" maxlength="20" cssClass="formText"></s:textfield><label class="requireField">*</label></td>
				</tr>
				<tr height="29">
					<td align="right">字典顺序:</td>
					<td align="left"><s:textfield name="sysCode.codeOrder" rule="num" maxlength="9" cssClass="formText"></s:textfield></td>
				</tr>
				<tr height="29">
					<td align="right">字典状态:</td>
					<td align="left"><s:select name="sysCode.stat" list="#{1:'启用',0:'禁用'}" /><label class="requireField">*</label></td>
				</tr>
			</table>
			<div style="color: red" align="center">
				<s:fielderror/>
			 	<s:actionerror/>
			</div>
			<div class="buttonArea">
				<input type="submit" class="pub_but formButton" value="确  定" hidefocus="true" />&nbsp;&nbsp;&nbsp;&nbsp;
				<input id="cancel" type="button" class="pub_but formButton" value="返  回" hidefocus="true" />
			</div>
		</form>
		<p id="errInfo" style="text-align:center;">信息填写不完整或格式错误！</p>
	</div>
</div>