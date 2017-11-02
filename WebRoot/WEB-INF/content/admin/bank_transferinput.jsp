<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/content/common/taglibs.jsp"%>
<script>

$.fx.speeds._default = 1000;
jQuery.ajaxSettings.traditional = true;
var isSuccess=true;

$(function() {
	$("#dialog").dialog({
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
});

$(document).ready(function(e) {
	$("#cancel").bind('click',function (){
		$("#tranDialog").dialog('close');
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

		var bank_goid = $("#bank_goid").val();
		var bank_coid = $("#bank_coid").val();
		if(bank_goid == bank_coid){
			alert("转入转出银行卡[不可相同]！");
			return false;
		}
		
		var _go_cush = changeDouble("#_go_cush"); //转出金额
		var _bank_je = changeDouble("#_bank_je"); //转入金额
		if(_go_cush && _bank_je){
			if(_go_cush <= 0){
				alert("转出卡余额："+_go_cush+"，[小于等于0]！");
				return false;
			}
			if(_go_cush < _bank_je){
				alert("转出卡余额："+_go_cush+"，小于转出金额："+_bank_je+" ！");
				return false;
			}
		}else{
			return false;
		}
		
		var param=getParam("inputForm");
		$.ajax({
			url: $(this).attr("action"),
			type: "POST",
			data: param,
			success: function(msg){
				isSuccess= (trim(msg)=="您的操作已成功!")?true :false;
				$("#dialog").html(msg);
				$("#dialog").dialog('open');
				$(".ui-widget-overlay").css("height", document.body.scrollHeight);
			}
		});
		return false;
	});

});

function changeDouble(_id){
	try{
		return parseFloat($(_id).val().replace(/,/gm,'').replace(",",""));
	}catch(e){
		alert(e)
		return false;
	}
};

function bankYe(_no, id) {
	if(id == ""){
		return false;
	}
	$.ajax({
		type : "post",
		url : "${ctx}/admin/bank!ajaxBankCash.action?bank.id="+id,
		async : false,
		success : function(msg) {
			if (msg == "") {
				return false;
			} 
			try{
				var jsonObj = jQuery.parseJSON(msg)[0];
				if(_no == "1"){
					$("#bankTransfer #_go_cush").val(jsonObj.cush);
				}else if(_no == "2"){
					$("#bankTransfer #_come_cush").val(jsonObj.cush);
				}
			}catch(e){
				eval(msg.replace("<script>","").replace("<//script>",""));
				return;
			}
		}
	});
}
</script>
<div class="input">
	<div class="pub_inp">
		<form id="inputForm" class="validate" action="${ctx}/admin/bank!transfer.action" method="post">
			<table class="enforce_table" id="bankTransfer" cellpadding="0" cellspacing="0" width="100%" style="font-size: 14px">
				<colgroup>
					<col width="90"/><col/>
				</colgroup>
				<tr>
					<td align="right" height="30" class="enforcetd_bg">输出银行:</td>
					<td class="tdLeft">
						<s:select name="bank.goid" list="bankMap" listKey="key" listValue="%{value}" headerKey="" headerValue="请选择" onchange="bankYe('1', this.value)" style="width:120px;" rule="noempty"></s:select>
                    	<label class="requireField">*</label>
                    	<input type="text" id="_go_cush" value="" style="width:120px;" cssClass="pub_input" onpaste="return false" readonly />元
                    </td>
				</tr>
				<tr>
					<td align="right" height="30" class="enforcetd_bg">转入银行:</td>
					<td class="tdLeft">
						<s:select name="bank.coid" list="bankMap" listKey="key" listValue="%{value}" headerKey="" headerValue="请选择" onchange="bankYe('2', this.value)" style="width:120px;" rule="noempty"></s:select>
                    	<label class="requireField">*</label>
						<input type="text" id="_come_cush" value="" cssClass="pub_input" style="width:120px;" readonly/>元
                    </td>
				</tr>
				<tr>
					<td align="right" height="24" class="enforcetd_bg">转账日期:</td>
					<td align="tdLeft" >
						<input type="text" class="Wdate" name="jzsj" id="jzsj" size="12"
							onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" 
							readonly="true" class="Wdate" style="width:120px; text-indent:4px" value="${jzsj}" 
						/>
					</td>
				</tr>
				<tr>
					<td align="right" height="24" class="enforcetd_bg">划转金额:</td>
					<td align="tdLeft" >
						<input type="text" name="bank.Je" id="_bank_je" value="" rule="noempty float" style="width:120px;"/>元
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
	<div id="dialog" title="提示消息"></div>
</div>