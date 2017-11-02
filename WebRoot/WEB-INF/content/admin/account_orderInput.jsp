<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/content/common/taglibs.jsp"%>
<script>

$.fx.speeds._default = 1000;
jQuery.ajaxSettings.traditional = true;
var isSuccess=true;

$(function() {
	
	$("#WarnNotice").dialog({
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
		width: 700,
		height: "auto",
		minHeight: 0,
		resizable: true,
		modal: true
	});

    /*进入焦点时触发*/
    var currentvalue = "0";
	$("#business_adult_no, #business_child_no, #business_escort_no").focus(function(){
	     currentvalue = $(this).val();
	     $(this).val("");
	}).blur(function(){
		if($(this).val() == ""){
			$(this).val(currentvalue);
		}
	});
});

$(document).ready(function(e) {
	$("#businessCustomer_orderId").val($("#inputForm #business_orderId").val());
	$(".renshu").focus(function() {
		this.value=""; 
	}).blur(function() {
		if(this.value==""){
			this.value=0;
		} 
	});
	$("#cancel").bind('click',function (){
		$( "#popupDialog" ).dialog('close');
	});
	$("#businessCustomerForm").bind("submit", function(){
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
		if($("#businessCustomer_childNo").val() == ""){
			$("#businessCustomer_childNo").val("0");
		}
		if($("#businessCustomer_escortNo").val() == ""){
			$("#businessCustomer_escortNo").val("0");
		}
		$("input[name='businessCustomer.bz']").val($("textarea[name='businessCustomer.bz']").val());
		var param=getParam("businessCustomerForm");
		$.ajax({
			url: $(this).attr("action"),
			type: "POST",
			data: param,
			success: function(msg){
                isSuccess= (trim(msg)=="您的操作已成功!")?true :false;
                $("#notice1").html(msg);
                $("#notice1").dialog('open');
                $(".ui-widget-overlay").css("height", document.body.scrollHeight);
            }
		});
		return false;
	});
	$( "#customer_name_span" ).click(function() {
		var X = $(this).offset().top; 
		var Y = $(this).offset().left;
		$.ajax({
			type: "POST",
			cache: false,
			url: "${ctx}/admin/customer!dialog.action",
			success: function(msg){
				$("#dialog").html(msg).dialog("open");
				$(".ui-widget-overlay").css("height",document.body.scrollHeight);
				tableTRClick();
			}
		});
		return false;
	});
});
function tableTRClick(){
	$("#dialog table:eq(1) tr:gt(0)").click(function () {
		$("#businessCustomer_customer_id").val($(this).find("td").eq(0).html());
		$("#business_customer_customerDwmc").val($(this).find("td").eq(2).html());
		$("#dialog").dialog("close");
	})
}
</script>
<div class="input">
	<div id="dialog" title="采购单位"></div>
    <div id="WarnNotice" title="提示消息"></div>
	<div class="pub_inp">
		<form id="businessCustomerForm" class="validate" action="${ctx}/admin/business_customer!save.action" method="post">
			<table class="enforce_table" id="businessAddFrom" cellpadding="0" cellspacing="0" width="100%" style="font-size: 14px">
				<colgroup>
					<col width="75" height="40"/>
					<col height="40"/>
				</colgroup>
				<tr>
                    <td width="90" align="right" class="enforcetd_bg">收付类型：<br></td><td width="360" colspan="3">
                    	<s:hidden name="businessCustomer.id"/>
                    	<s:hidden name="businessCustomer.orderId" id="businessCustomer_orderId" value=""/>
                    	<s:hidden name="businessCustomer.customer.id"/>
                    	<s:select id="account_sflx" list="sflxMap" listKey="key" listValue="%{value}" onchange="changsflx(this.value)"></s:select>
                    </td>
                </tr>
				<tr>
					<td align="right" height="24" class="enforcetd_bg">收付名称：</td>
					<td align="left">
						<s:select id="account_fklx" list="sffsMap" listKey="key" listValue="%{value}" onchange="changsflx(this.value)" style="display:none"></s:select>
                        <select name="account.customer_id" id="account_customer_id" >
                            <option value="1">飞机票11</option>
                            <option value="2">火车票11</option>
                            <option value="3">地接11</option>
                            <option value="4">返11佣</option>
                            <option value="5">其11它</option>
                        </select>
					</td>
				</tr>
				<tr>
					<td align="right" height="24" class="enforcetd_bg">支付方式：</td>
					<td align="left">
						<select name="account.sffs" id="account_sffs">
                            <option value="1">现金</option>
                            <option value="2">挂账</option>
                        </select>
					</td>
				</tr>
				<tr>
					<td align="right" height="24" class="enforcetd_bg">入帐银行：</td>
					<td class="tdLeft">
						<s:select list="bankMap" listKey="key" listValue="%{value}"></s:select>
	                </td>
				</tr>
				<tr>
					<td align="right" height="24" class="enforcetd_bg">金额：</td>
	                <td class="tdLeft"><input type="text" name="account.je" id="account_je" value="" style="width:80px;"/>元</td>
				</tr>
				<tr>
					<td align="right" height="24" class="enforcetd_bg">备注：</td>
					<td align="left">
						<s:hidden name="businessCustomer.bz"></s:hidden>
                        <textarea name="businessCustomer.bz" cols="" rows="" class="textarea" maxlength="512" style="width:99%; height:60px; padding:2px; font-size:13px;"></textarea>
                    </td>
				</tr>
			</table>
			<div style="color: red" align="center">
				<s:fielderror/>
			 	<s:actionerror/>
			</div>
			<div class="buttonArea">
				<input type="submit" class="pub_but formButton" value="确  定" hidefocus="true" />&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" id="cancel" class="pub_but formButton" value="返  回" hidefocus="true" />
			</div>
		</form>
        <p id="errInfo" style="text-align:center;">信息填写不完整或格式错误！</p>
	</div>
</div>