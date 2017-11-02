<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/content/common/taglibs.jsp"%>
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
					//$(this).html("<p>没有符合的数据</p>");
				}
			}
		});
	});
	$(document).ready(function(e) {
		$("#notice").dialog({
			dialogClass:"ppisDialog",
			autoOpen: false,
			resizable:false,
			modal:true,
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
			var param=getParam("inputForm");
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
	<div id="dialog" title="上级组织结构" style="display: none">
		<div id="dept" class="demo"></div>
	</div>
    <div id="notice" title="提示消息"></div>
	<div class="pub_inp">
		<form id="inputForm" class="validate" action="${ctx}/admin/cooperator!save.action" method="post">
			<s:hidden name="cooperator.id"></s:hidden>
			<table class="enforce_table" cellpadding="0" cellspacing="0" width="100%">
				<tr height="29">
					<td align="right">采购类型：</td>
					<td align="left"><s:select name="cooperator.type" list="cooptypeMap" listKey="key" listValue="%{value}" headerKey="" headerValue="请选择" rule="noempty"></s:select><label class="requireField">*</label></td>
				</tr>
				<tr height="29">
					<td align="right">单位名称：</td>
					<td align="left"><s:textfield name="cooperator.dwmc" rule="noempty" maxlength="200" cssClass="formText"></s:textfield><label class="requireField">*</label></td>
				</tr>
				<tr height="29">
					<td align="right">联 系 人：</td>
					<td align="left"><s:textfield name="cooperator.lxr" rule="noempty" maxlength="50" cssClass="formText"></s:textfield><label class="requireField">*</label></td>
				</tr>
				<tr height="29">
					<td align="right">联系电话：</td>
					<td align="left"><s:textfield name="cooperator.lxdh" maxlength="100" cssClass="formText"></s:textfield><label class="requireField">*</label></td>
				</tr>
				<tr height="29">
					<td align="right">开户行：</td>
					<td align="left"><s:textfield name="cooperator.khx" maxlength="100" cssClass="formText"></s:textfield></td>
				</tr>
				<tr height="29">
					<td align="right">银行账户：</td>
					<td align="left">
						<s:textfield name="cooperator.yhzh" cssClass="formText"></s:textfield>
					</td>
				</tr>
				<tr height="29">
					<td align="right">账户名：</td>
					<td align="left">
						<s:textfield name="cooperator.yhhm" cssClass="formText"></s:textfield>
					</td>
				</tr>
				<tr height="29">
					<td align="right">排序：</td>
					<td align="left">
						<s:textfield name="cooperator.sort" cssClass="formText"></s:textfield>
					</td>
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