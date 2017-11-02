<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/content/common/taglibs.jsp"%>
	<script>
	// increase the default animation speed to exaggerate the effect
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
			dialogClass:"ppisDialog departmentDialog",
			autoOpen: false,
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

		$( "#parent_name,#parent_id" ).click(function() {
			$( "#dialog" ).dialog( "open" );
			$(".ui-widget-overlay").css("height",document.body.scrollHeight);
			return false;
		});

	$("#resource").jstree({
		"json_data" : {
			"ajax" : {
				"url" : "${ctx}/admin/resource!ajaxResourceTree.action",
				"data" : function (n) { 
					return { 'resource.id' : n.attr ? n.attr("id") : "" }; 
				}
			}
		},
		"themes" : {
			"theme" : "default"
		},
		//"core" : { "initially_open" : [ "phtml_1","phtml_3","phtml_4"] },
		"plugins" : [ "themes", "json_data", "ui" ]
		})
		.bind("select_node.jstree", function (event, data) { 
			//if(data.rslt.obj.hasClass("jstree-leaf")){
				$("#parent_name").val(data.rslt.obj.attr("title"));
				$("#parent_id").val(data.rslt.obj.attr("id"));
				$("#dialog").dialog("close");
			//}
		})
		.bind("loaded.jstree", function (event, data) { 
			data.inst.open_all(-1); // -1 opens all nodes in the container 
		})
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
	<div id="dialog" title="上级资源" style="display: none">
		<div id="resource" class="demo"></div>
	</div>
    <div id="notice" title="提示消息"></div>
	<div class="pub_inp">
		<form id="inputForm" class="validate" action="${ctx}/admin/resource!save.action" method="post">
			<s:hidden name="resource.id"></s:hidden>
			<table class="enforce_table" cellpadding="0" cellspacing="0" width="100%">
				<colgroup>
					<col width="100"/>
					<col/>
				</colgroup>
				<tr height="29">
					<td align="right">资源名称:</td>
					<td align="left">
						<s:textfield name="resource.name" rule="noempty" maxlength="100" cssClass="formText"></s:textfield><label class="requireField">*</label>
					</td>
				</tr>
				<tr height="29">
					<td align="right">资源值:</td>
					<td align="left">
						<s:textfield name="resource.value" maxlength="125" cssClass="formText"></s:textfield><label class="requireField">*</label>
					</td>
				</tr>
				<tr height="29">
					<td align="right">上级资源名称:</td>
					<td align="left">
						<s:textfield name="parent.id" id="parent_id" cssClass="formText" cssStyle="width:50px"></s:textfield>
						<s:textfield name="parent.name" id="parent_name" cssClass="formText"></s:textfield>
					</td>
				</tr>
				<tr height="29">
					<td align="right">排序:</td>
					<td align="left"><s:textfield name="resource.sort" rule="num" maxlength="9" cssClass="formText"></s:textfield></td>
				</tr>
				<tr>
					<td align="right">描述:</td>
					<td align="left"><s:textfield name="resource.description" maxlength="125" cssClass="formText"></s:textfield></td>
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