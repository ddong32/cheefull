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
});

$(document).ready(function(e) {
    $("#cancel").bind('click',function (){
		$( "#popupDialog" ).dialog('close');
	});
	$("#bankRunningForm").bind("submit", function(){
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
		var je = $("#bankRunning_je").val();
		if(je == "" || je == 0 || je == 0.0) {
			$("#notice1").html("输入金额不能为空或为0!").dialog('open');
			return false;
		}
		$("input[name='bankRunning.bz']").val($("textarea[name='bankRunning.bz']").val());
		var param=getParam("bankRunningForm");
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
	var bankId=$("#bankRunningForm #bankRunning_bank_id").val()
	$("#bankRunningForm #_selectBankId").val(bankId);
	$("#bankRunningForm #currentBankId").val(bankId);
	bankYe(bankId);
});

function bankYe(id) {
	$("#bankRunningForm #bankRunning_bank_id").val(id);
	if(id != ""){
		$.ajax({
			type : "post",
			url : "${ctx}/admin/bank!ajaxBankCash.action?bank.id="+id,
			async : false,
			success : function(msg) {
				if (msg != "") {
					try{
						var jsonObj = jQuery.parseJSON(msg)[0];
						$("#bankRunningAddFrom #_cush").text(jsonObj.cush);
					}catch(e){
						eval(msg.replace("<script>","").replace("<//script>",""));
						return;
					}
				} 
			}
		});
	}
}
</script>
<div class="input">
    <div id="notice" title="提示消息"></div>
    <div id="notice1" title="提示消息"></div>
	<div class="pub_inp">
		<form id="bankRunningForm" class="validate" action="${ctx}/admin/bank_running!save.action" method="post">
			<table class="enforce_table" id="bankRunningAddFrom" cellpadding="0" cellspacing="0" width="100%" style="font-size: 14px">
				<tr>
					<td align="right" width="90" height="30" class="enforcetd_bg">收支账户：</td>
					<td align="left">
						<s:select id="_selectBankId" list="bankMap" listKey="key" listValue="%{value}" headerKey="" headerValue="请选择" onchange="bankYe(this.value)" rule="noempty" style="width:110px;"></s:select>
						<label class="requireField">*</label>
						<label id="_cush" class=""></label>
					</td>
				</tr>
				<tr>
					<td align="right" height="30" class="enforcetd_bg">收支类型：</td>
					<td align="left">
						<s:select name="bankRunning.sflx" list="sflxMap" listKey="key" listValue="%{value}" style="width:110px;"></s:select>
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<td align="right" height="30" class="enforcetd_bg">记账日期：</td>
					<td align="left">
						<input type="text" class="Wdate" name="bankRunning.jzsj" size="12" onfocus="WdatePicker()" readonly="true" style="width:110px;" rule="noempty" value="${bankRunning.today}"/>
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<td align="right" height="30" class="enforcetd_bg">金额：</td>
					<td align="left">
						<s:textfield name="bankRunning.je" maxlength="9" cssClass="formText w110" rule="noempty"/>
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<td align="right" height="30" class="enforcetd_bg">对方账户：</td>
					<td align="left">
						<s:textfield name="bankRunning.dfzh" maxlength="30" cssClass="formText w250" rule="noempty"/>
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<td align="right" height="30" class="enforcetd_bg">对方户名：</td>
					<td align="left">
						<s:textfield name="bankRunning.dfhm" maxlength="256" cssClass="formText w250" rule="noempty"/>
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<td align="right" height="30" class="enforcetd_bg">备注：</td>
					<td align="left">
						<s:hidden name="bankRunning.bz"></s:hidden>
                        <textarea name="bankRunning.bz" cols="" rows="" class="textarea w250" maxlength="512" style="height:100px; padding:2px; font-size:13px;"><s:property value="bankRunning.bz"></s:property></textarea>
                    </td>
				</tr>
			</table>
			<div style="color: red" align="center">
				<s:fielderror />
				<s:actionerror />
			</div>
			<div class="buttonArea">
				<input type="submit" class="pub_but formButton" value="确  定" hidefocus="true" />&nbsp;&nbsp;&nbsp;&nbsp; 
				<input type="button" id="cancel" class="pub_but formButton" value="返  回" hidefocus="true" />
				<s:hidden name="bankRunning.id"></s:hidden>
				<s:hidden name="bankRunning.bank.id"></s:hidden>
				<s:hidden name="currentBankId"></s:hidden>
			</div>
		</form>
		<p id="errInfo" style="text-align:center;">信息填写不完整或格式错误！</p>
	</div>
</div>