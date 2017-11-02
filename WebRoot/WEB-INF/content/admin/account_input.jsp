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
		width: 600,
		height: "auto",
		minHeight: 0,
		resizable: true,
		modal: true
	});
	
	$("#travelLine").autocomplete("${ctx}/admin/travel!ajaxAutoTravel.action", {
		minChars: 0,
		width: 400,
		autoFill: false,
		dataType: "json",
		parse: function(data) {
			return $.map(data, function(row) {
				return {
					data: row,
					value: row.line,
					result: row.line
				}
			});
		},
		formatItem: function(row, i, max) {
			return i + "/" + max + ": \"" + row.line + "\" ";
		},
		formatResult: function(row) {
			return row.line;
		}
	});
});

$(document).ready(function(e) {
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
		var param=getParam("inputForm");
		$.ajax({
			url: $(this).attr("action"),
			type: "POST",
			dataType: "json",
			data: param,
			success: function(msg){
				if(msg){
					if (msg.status == "success") {
						$("#popupDialog").dialog('close');
						$("#listForm").submit();
						dbClickEvent(msg.message);
					}else{
						$("#notice1").html(msg.status+", "+msg.message);
						$("#notice1").dialog('open');
					}
				}
			}
		});
		return false;
	});

});

$( "#travel_name_span" ).click(function() {
	var X = $(this).offset().top; 
	var Y = $(this).offset().left;
	$.ajax({
		type: "POST",
		cache: false,
		url: "${ctx}/admin/travel!dialog.action",
		success: function(msg){
			$("#dialog").html(msg).dialog("open");
			$(".ui-widget-overlay").css("height",document.body.scrollHeight);
		}
	});
	return false;
});

function tableTRClick(){
	$("#dialog table:eq(1) tr:gt(0)").click(function () {
		$("#inputForm #business_travelLine").val($(this).find("td").eq(0).html());
		$("#dialog").dialog("close");
	})
}

</script>
<div class="input">
	<div class="pub_inp">
		<form id="inputForm" class="validate" action="${ctx}/admin/business!save.action" method="post">
			<s:hidden name="business.id"></s:hidden>
			<s:hidden name="redirectionUrl"></s:hidden>
			<table class="enforce_table" id="businessAddFrom" cellpadding="0" cellspacing="0" width="100%">
				<colgroup>
					<col width="90"/><col/>
                    <col width=""/><col/>
                    <col width="90"/><col/>
                    <col width=""/><col/>
				</colgroup>
				<tr>
					<td align="right" height="30" class="enforcetd_bg">收支类型：</td>
					<td align="left">
						<s:select id="account_sflx" list="sflxMap" listKey="key" listValue="%{value}" onchange="changsflx(this.value)" class="w100"></s:select>
					</td>
					<td align="right" height="30" class="enforcetd_bg">收支方式：</td>
                    <td width="" class="tdLeft">
                        <s:select id="account_sffs" list="sffsMap" listKey="key" listValue="%{value}" headerKey="" headerValue="请选择" value="1" class="w150"></s:select>
                    </td>
				</tr>
				<tr>
					<td align="right" height="30" class="enforcetd_bg">收支名称：</td>
					<td align="left">
						<select id="customerDwmcSelect" ></select>
                        <span id="_accountfklx" style="display:none">
                            <s:select id="account_fklx" list="fklxMap" listKey="key" listValue="%{value}" cssClass="formText left" style="display:none"></s:select>
                            <s:textfield id="cooperator_dwmc" maxlength="12" rule="" cssClass="formText left" style="width:68%; margin-left:5px; height:25px; line-height:25px"></s:textfield>
                            <span id="cooperator_name_span" style="margin-left:5px; line-height:25px; font-weight:bold; cursor:pointer; text-decoration:underline; font-size: 12px; color:red; "> 搜索 </span>
                            <s:hidden name="cooperator.id"/>
                        </span>
					</td>
                    <td align="right" height="30" class="enforcetd_bg">金额：</td>
	                <td width="" class="tdLeft"><input type="text" name="account.je" id="account_je" value="" class="w150"/> 元</td>
				</tr>
				<tr>
                    <td align="right" height="30" class="enforcetd_bg">收支银行：</td>
                    <td width="" class="tdLeft">
                        <s:select id="account_bank_id" list="bankMap" listKey="key" listValue="%{value}" headerKey="" headerValue="请选择" class="w150"></s:select>
                    </td>
                    <td align="right" height="30" class="enforcetd_bg">收支时间：</td>
                    <td width=""class="tdLeft">
                        <input type="text" class="Wdate w150" name="account.sfsj" id="account_sfsj" size="12" onfocus="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd'})" readonly="true" value=""/>
                    </td>
                </tr>
                <tr>
                    <td align="right" height="30" class="enforcetd_bg" width="90">备注：</td>
                    <td colspan="3" class="tdLeft">
                        <textarea class="combo-text validatebox-text textarea" name="account.bz" id="account_bz" maxlength="600"></textarea>
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
	<div id="dialog" title="旅行线路"></div>
	<div id="notice" title="提示消息"></div>
    <div id="notice1" title="提示消息"></div>
</div>