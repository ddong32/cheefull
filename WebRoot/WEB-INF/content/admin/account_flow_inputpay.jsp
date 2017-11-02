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
				$("#popupDialog").dialog('close');
				$("#notice").dialog('close');
				if (isSuccess){
					refreshBankRunnig();
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
	$("#payfinance").bind('click',function (){
		$("#bankRunning_bank_id").val($("#bankRunningAddFrom #_selectBankId").val());
		$("#bankRunning_je").val($("#bankRunningAddFrom #br_je").val());
		$("#bankRunning_jzsj").val($("#bankRunningAddFrom #br_jzsj").val());
		$( "#popupDialog" ).dialog('close');
		collateRecord();
	});
	$("#bankRunningAddFrom #br_je").val($("#bankRunning_je").val())
	$("#bankRunningAddFrom #coop_dwmc").text($("#_dwmc").val());
	$("#bankRunningAddFrom #_selectBankId").val("11");
	bankYe("11");
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
	}else{
		$("#bankRunningAddFrom #_cush").text("");
	}
}
</script>
<div class="input">
    <div id="notice" title="提示消息"></div>
    <div id="notice1" title="提示消息"></div>
	<div class="pub_inp">
		<form id="bankRunningForm" class="validate" action="" method="post">
			<table class="enforce_table" id="bankRunningAddFrom" cellpadding="0" cellspacing="0" width="100%" style="font-size: 14px">
				<tr>
					<td align="right" width="90" height="30" class="enforcetd_bg">收款单位：</td>
					<td align="left"><span id="coop_dwmc"></span></td>
				</tr>
				<tr>
					<td align="right" width="90" height="30" class="enforcetd_bg">付款账户：</td>
					<td align="left">
						<s:select id="_selectBankId" list="bankMap" listKey="key" listValue="%{value}" headerKey="" headerValue="请选择" onchange="bankYe(this.value)" rule="noempty" style="width:110px;" ></s:select>
						<label class="requireField">*</label>
						<label id="_cush" class=""></label>
					</td>
				</tr>
				<tr>
					<td align="right" height="30" class="enforcetd_bg">记账日期：</td>
					<td align="left">
						<input type="text" class="Wdate" name="br.jzsj" id="br_jzsj" size="12" onfocus="WdatePicker()" readonly="true" style="width:110px;" rule="noempty" value="${bankRunning.today}"/>
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<td align="right" height="30" class="enforcetd_bg">金额：</td>
					<td align="left">
						<s:textfield name="br.je" maxlength="5" cssClass="formText w110" rule="noempty"  readonly="true"/>
						<label class="requireField">*</label>
					</td>
				</tr>
			</table>
			<div style="color: red" align="center">
				<s:fielderror />
				<s:actionerror />
			</div>
			<div class="buttonArea">
				<input type="button" id="payfinance" class="pub_but formButton" value="确  定" hidefocus="true" />&nbsp;&nbsp;&nbsp;&nbsp; 
				<input type="button" id="cancel" class="pub_but formButton" value="返  回" hidefocus="true" />
			</div>
		</form>
		<p id="errInfo" style="text-align:center;">信息填写不完整或格式错误！</p>
	</div>
</div>