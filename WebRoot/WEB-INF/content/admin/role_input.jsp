<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/content/common/taglibs.jsp"%>
<script>
jQuery.ajaxSettings.traditional = true;
var isSuccess=true;
$(document).ready(function(e) {
	
	if(null == $("#rid").val() || "" == $("#rid").val())
	{
		$("#rvalue").val("ROLE_");
	}
	
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
	
	$("#resourceTree").jstree({
		"json_data" :
		{
			"ajax" :
			{
				"url" : "${ctx}/admin/role!ajaxResourceTree.action",
				"data" : function (n) { return { 'resource.id' : n.attr ? n.attr("id") : "" };}
			}
		},
		"themes" :
		{
			"theme" : "default"
		},
		//"core" : { "initially_open" : [ "phtml_1","phtml_3","phtml_4"] },
		"plugins" : [ "themes", "json_data", "checkbox", "sort", "ui" ]
	}).bind("loaded.jstree", function (event, data) {
        //得到服务器传过来的原有权限id的字符串，格式自定义，我的格式为"0001;0002;xxx;"
   		var checkMenu = $("#resourceIdStr").val();
   		//分割字符串成数组
   		var array = checkMenu.split(",");
   		for(var i = 0; i < array.length; i++)
   		{
    		var obj = $("#"+array[i]);
    		if(obj.hasClass("jstree-leaf"))
    		{
    			$("#resourceTree").jstree("check_node",obj);
    		}
     	}
	}).bind("change_state.jstree", function (node,uncheck) {
		//取得所有选中的节点，返回节点对象的集合
		var ids = "";
		//得到半选择节点的id，拼接成字符串
		var undetermined_val =  $(".jstree-undetermined");
       	for(var i = 0; i < undetermined_val.size(); i++)
       	{
        	ids += undetermined_val[i].id+",";
        }
        //得到节点的id，拼接成字符串
       	var checked_val = $(".jstree-checked");
       	for(var i=0;i<checked_val.size();i++)
       	{
        	ids += checked_val[i].id+",";
        }
        $("#resourceIdStr").val(ids);
	});
	
	$("#resourceTree").height(250).css("overflow-y","scroll");
	
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
	<div id="dialog" title="部门" style="display: none">
		<div id="dept" class="demo"></div>
	</div>
    <div id="notice" title="提示消息"></div>
	<div class="pub_inp">
		<form id="inputForm" class="validate" action="${ctx}/admin/role!save.action" method="post">
			<table class="enforce_table" cellpadding="0" cellspacing="0" width="100%">
				<s:hidden id="rid" name="role.id"></s:hidden>
				<tr height="29">
					<td align="right">角色名称:</td>
					<td align="left"><s:textfield name="role.name" rule="noempty" maxlength="15" cssClass="formText"></s:textfield><label class="requireField">*</label></td>
				</tr>
				<s:if test="role.value != 'ROLE_MANAGER' && role.value != 'ROLE_ADMIN'">
					<tr height="29">
						<td align="right">角色标识:</td>
						<td align="left"><s:textfield id="rvalue" name="role.value" rule="noempty numchar" maxlength="40" style="ime-mode:disabled;" onkeypress="return checkNumEnAndDash(event)" onpaste="return false" cssClass="formText"></s:textfield><label class="requireField">*</label></td>
					</tr>
				</s:if>
				<s:else>
					<s:hidden name="role.value"></s:hidden>
				</s:else>
				<tr height="29">
					<td align="right">描述:</td>
					<td align="left"><s:textfield name="role.description" maxlength="80" cssClass="formText"></s:textfield></td>
				</tr>
				<tr>
					<td align="right">拥有权限:</td>
					<td align="left">
						<div id="resourceTree" style="width:300px;"></div>
						<s:hidden name="resourceIdStr" rule="noempty"></s:hidden>
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