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

		$( "#parent_name,#parent_id" ).click(function() {
			$( "#dialog" ).dialog( "open" );
			$(".ui-widget-overlay").css("height",document.body.scrollHeight);
			return false;
		});

	$("#dept").jstree({
		"json_data" : {
			"ajax" : {
				"url" : "${ctx}/admin/department!ajaxDepartmentTree.action?selfId=${department.id}",
				"data" : function (n) { 
					return { 'department.id' : n.attr ? n.attr("id") : "" }; 
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
	//var checkedNodes=[];
	//var setting = {
	//	edit: {
	//		enable: false,
	//		editNameSelectAll: true
	//	},
	//	async: {
	//		enable: true,
	//		url:"${ctx}/admin/department!ajaxAddressTree.action?department.id=${department.id}",
	//		otherParam:{"addressIdStr":$('#addressIdStr').val()}
	//	},
	//	check: {
	//		enable: true
	//	},
	//	data: {
	//		simpleData: {
	//			enable: true
	//		}
	//	},
	//	callback: {
	//		onCheck: onCheck
	//	}
	//};
	//$.fn.zTree.init($("#addressTree"), setting);
	//function onCheck(e, treeId, treeNode) {
	//	var zTree = $.fn.zTree.getZTreeObj("addressTree"),
	//	nodes1 = zTree.getCheckedNodes(true),
	//	count = 0,
	//	ids = "";
	//	for (var i=0, l=nodes1.length; i<l; i++) {
	//		if(nodes1[i].type != 0){
	//			count ++;
	//			ids += nodes1[i].id + ",";
	//		}
	//	}
	//	$("#addressIdStr").val(ids);
	//}
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
		<form id="inputForm" class="validate" action="${ctx}/admin/department!save.action" method="post">
			<s:hidden name="department.id"></s:hidden>
			<table class="enforce_table" cellpadding="0" cellspacing="0" width="100%">
				<tr height="29">
					<td align="right" class="enforcetd_bg">组织结构名称:</td>
					<td align="left"><s:textfield name="department.name" rule="noempty" maxlength="15" cssClass="formText"></s:textfield><label class="requireField">*</label></td>
				</tr>
				<tr height="29">
					<td align="right" class="enforcetd_bg">组织结构编码:</td>
					<td align="left"><s:textfield name="department.code" rule="noempty num" maxlength="10" cssClass="formText"></s:textfield><label class="requireField">*</label></td>
				</tr>
				<tr height="29">
					<td align="right" class="enforcetd_bg">上级组织结构名称:</td>
					<td align="left">
						<s:textfield name="parent.id" id="parent_id" cssClass="formText" cssStyle="width:50px" readonly="true"></s:textfield>
						<s:textfield name="parent.name" id="parent_name" cssClass="formText" readonly="true"></s:textfield>
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