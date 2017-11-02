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
	
	$( "#travelLine" ).autocomplete("${ctx}/admin/travel!ajaxAutoTravel.action", {
		width: 400,
		matchContains: true,  
		scrollHeight: 280,
		dataType: "json",
		delay:500,
		minChars: 1,
		max: 10,
		extraParams: {
		    travelLine: function() { return $("#travelLine").val(); }
		},
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
			return row.line;
		},
		formatMatch: function(row, i, max) {
            return row.line;
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
		$("#business_travelLine").val($("#travelLine").val());
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
				</colgroup>
				<tr>
					<td align="right" height="24" class="enforcetd_bg">线路类型:</td>
					<td align="left">
						<s:select name="business.ddlx" list="DdlxMap" listKey="key" listValue="%{value}" headerKey="" headerValue="请选择" rule="noempty" style="width:122px;"></s:select>
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<td align="right" height="24" class="enforcetd_bg">业务类型:</td>
					<td align="left">
						<s:select name="business.ywlx" list="YwlxMap" listKey="key" listValue="%{value}" headerKey="" headerValue="请选择" rule="noempty" style="width:122px;"></s:select>
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<td align="right" height="24" class="enforcetd_bg">出发日期:</td>
					<td align="left"><!-- WdatePicker({minDate:'%y-%M-{%d+1}',doubleCalendar:true}) -->
						<input type="text" class="Wdate" name="business.cfsj" id="bd" size="12" onfocus="WdatePicker({doubleCalendar:true})" readonly="true" style="width:120px;" rule="noempty"/>
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<td align="right" height="24" class="enforcetd_bg">旅行线路:</td>
					<td align="left">
						<s:textfield name="travelLine" id="travelLine" cssClass="formText" style="width:400px;height:20px;line-height:20px;"></s:textfield>
						<s:hidden name="business.travelLine"></s:hidden>
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