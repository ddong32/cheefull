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

	$( "#dialog" ).dialog({
		dialogClass:"ppisDialog",
		autoOpen: false,
		width: 500,
		height: "auto",
		minHeight: 0,
		resizable: true,
		modal: true
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
		$.ajax({
			url: "${ctx}/admin/account!ajaxAccountOnTrustRefer.action",
			type: "POST",
			data: {
				'account.bank.id' : $("#account_bank_id").val(),
				'account.sfsj' : $("#account_sfsj").val(),
				'account_ids' : $("#account_ids").val()
			},
			dataType: "json",
			success: function(data){
				isSuccess= (trim(data.status)=="success")?true :false;
                $("#notice").html(data.message);
                $("#notice").dialog('open');
                $(".ui-widget-overlay").css("height", document.body.scrollHeight);
			}
		});
		return false;
	});
	
});
</script>
<div class="input">
	<div id="dialog" title="采购单位"></div>
    <div id="notice" title="提示消息"></div>
	<div class="pub_inp">
		<form id="inputForm" class="validate" action="${ctx}/admin/account!ajaxAccountOnTrustRefer.action" method="post">
			<s:hidden name="business.id"></s:hidden>
			<s:hidden name="redirectionUrl"></s:hidden>
			<table class="enforce_table" id="businessAddFrom" cellpadding="0" cellspacing="0" width="100%">
				<tr>
					<td align="right" width="90" height="30" class="enforcetd_bg">收支类型:</td>
					<td align="left">
						<s:property value="account.zwsflx"/>
					</td>
				</tr>
				<tr>
					<td align="right" height="30" class="enforcetd_bg">单位名称:</td>
					<td class="tdLeft">
						<s:property value="account.dwmc"/>
                    </td>
				</tr>
				<tr>
					<td align="right" height="30" class="enforcetd_bg">收支金额:</td>
					<td align="left">
						<s:property value="account.skje"/>
					</td>
				</tr>
				<tr>
					<td align="right" height="30" class="enforcetd_bg">收支银行:</td>
					<td class="tdLeft">
						<s:select id="account_bank_id" list="bankMap" listKey="key" listValue="%{value}" headerKey="" headerValue="请选择" style="width:100px;" rule="noempty"></s:select>
                    	<label class="requireField">*</label>
                    </td>
				</tr>
				<tr>
					<td align="right" height="30" class="enforcetd_bg">收支日期:</td>
					<td align="left">
						<input type="text" class="Wdate" name="account.sfsj" id="account_sfsj" size="12" onfocus="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd'})" readonly="true" rule="noempty" style="width:100px;" value=""/>
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
				<s:hidden name="account_ids"></s:hidden>
			</div>
		</form>
        <p id="errInfo" style="text-align:center;">信息填写不完整或格式错误！</p>
	</div>
</div>