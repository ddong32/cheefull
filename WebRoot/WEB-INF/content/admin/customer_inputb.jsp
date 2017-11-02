<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/content/common/taglibs.jsp"%>
<script>

$.fx.speeds._default = 1000;
jQuery.ajaxSettings.traditional = true;
var isSuccess=true;
var zTree = "";
$(function() {
	$("#notice2").dialog({
		dialogClass:"ppisDialog",
		autoOpen: false,
		resizable:false,
		modal: true,
		buttons: {
			"确定" : function(){
				$(this).dialog('close');
				$( "#popupDialog" ).dialog('close');
			}
		}
	});
	$("#notice3").dialog({
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

	///---------------------供应商-------------------------
	var setting = {
		edit: {
			enable: false,
			editNameSelectAll: true
		},
		async: {
			enable: true,
			url:"${ctx}/admin/customer!ajaxCustomerZTree.action?noFilter="+$("#noFilter").val()+"&username="+$("#username").val()+"&password="+$("#password").val(),
			otherParam:{"customerIds":$('#customerIds').val()}
		},
		check: {
			enable: false
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		callback: {
			beforeClick: beforeClick,
			onClick: onClick
		}
	};
	zTree = $.fn.zTree.init($("#customerTree"), setting);
	
	var log, className = "dark";
	function beforeClick(treeId, treeNode, clickFlag) {
		className = (className === "dark" ? "":"dark");
		return (treeNode.click != false);
	}
	
	function onClick(e, treeId, treeNode) {
		var typeVal=$("input[name='customer.type']:checked").val();
		if((typeVal) == undefined) {
			typeVal=$("#customer_type").val()
		}
		if(treeNode.type == (typeVal - 1)){
			$("#parent_id").val(treeNode.id);
			$("#parent_areaName").val(treeNode.name);
		}
	}
});

$(document).ready(function(e) {
	$("#cancel").bind('click',function (){
		$( "#popupDialog" ).dialog('close');
	});
	
	$("#inputFormC").bind("submit", function(){
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
		var param=getParam("inputFormC");
		$.ajax({
			url: $(this).attr("action"),
			type: "POST",
			data: param,
			success: function(msg){
				isSuccess= (trim(msg)=="您的操作已成功!")?true :false;
				if(isSuccess){
					zTree = $.fn.zTree.init($("#customerTree"), setting);
				}
				$("#notice2").html(msg);
				$("#notice2").dialog('open');
				$(".ui-widget-overlay").css("height",document.body.scrollHeight);
			}
		});
		return false;
	});
	
	$("#searchBoxx").keyup(function(){
        var key = $("#searchBoxx").val()
		if(/^[\u4e00-\u9fa5]+$/i.test(key)){
			mNodes=zTree.getNodesByParamFuzzy("name", key, null);
	        gNodes= everyNode(zTree);
	        var dontHide=[];
	        for(var m in mNodes){
	            dontHide.push(mNodes[m])
	            var p=getParents(mNodes[m]);
	            dontHide=dontHide.concat(p);
	        }
	        for (var g in gNodes){
	            if($.inArray(gNodes[g], dontHide) < 0){
	                zTree.hideNode(gNodes[g]);
	            }else{
	                zTree.showNode(gNodes[g]);
	                zTree.expandNode(gNodes[g],true);
	            }
	        }
		}
	});
	
	var typeVal = <s:property value="customer.type"/>;
	changeType(typeVal);
	
});


function changeType(typeVal){
	if(typeVal == 1){
		$("#addressText").html("所在地：");
		$("#customerText").html("单位名称：");
		$(".type_lxr").hide();
		$(".type_lxr [rule]").attr("norule",true);
		$(".type_kh").show();
		if($("#customer_id").val() == null) $(".type_kh input").attr("value","");
		$(".type_kh [rule]").removeAttr("norule");
		$("#inputFormC").attr("action", "${ctx}/admin/customer!saveDirectory.action");
		$("#searchBoxx").attr("disabled",true);
	}else if(typeVal == 2){
		$("#addressText").html("上级目录：");
		$("#customerText").html("联系人：");
		$(".type_kh").hide();
		$(".type_kh [rule]").attr("norule",true);
		$(".type_lxr").show();
		if($("#customer_id").val() == null) $(".type_lxr input").attr("value","");
		$(".type_lxr [rule]").removeAttr("norule");
		$("#inputFormC").attr("action", "${ctx}/admin/customer!save.action");
		$("#searchBoxx").attr("disabled",false);
	}

	$(".errNotice").removeClass("errNotice");
	$(".errType").remove();
	$("#errInfo").hide();

}

function getParents(node){
    var pa_arr=[];
    findIt(node);
    function findIt(node){
        if(node.parentTId){
            pa_arr.push(node.getParentNode())
            var newNode=zTree.getNodeByTId(node.parentTId);
            findIt(newNode);
        }
    }
    return pa_arr;
}

function everyNode(zTree){
    var Nodes=zTree.getNodes();
    var arr=[];
    findIt(Nodes);
    function findIt(node){
        for (var n in node){
            arr.push(node[n])
            if (node[n].children){
                findIt(node[n].children)
            }
        }
    }
    return arr
}

function expandNode(e) {
	type = e.data.type,
	nodes = zTree.getSelectedNodes();
	if (type.indexOf("All")<0 && nodes.length == 0) {
		alert("请先选择一个父节点");
	}

	if (type == "expandAll") {
		zTree.expandAll(true);
	} else if (type == "collapseAll") {
		zTree.expandAll(false);
	} else {
		var callbackFlag = $("#callbackTrigger").attr("checked");
		for (var i=0, l=nodes.length; i<l; i++) {
			zTree.setting.view.fontCss = {};
			if (type == "expand") {
				zTree.expandNode(nodes[i], true, null, null, callbackFlag);
			} else if (type == "collapse") {
				zTree.expandNode(nodes[i], false, null, null, callbackFlag);
			} else if (type == "toggle") {
				zTree.expandNode(nodes[i], null, null, null, callbackFlag);
			} else if (type == "expandSon") {
				zTree.expandNode(nodes[i], true, true, null, callbackFlag);
			} else if (type == "collapseSon") {
				zTree.expandNode(nodes[i], false, true, null, callbackFlag);
			}
		}
	}
}
</script>
<div class="input">
	<div id="notice2" title="提示消息"></div>
    <div id="notice3" title="提示消息"></div>
    <div class="pub_inp" style="float:left;width:235px;height:320px;">
 		<div class="searchFrame">
			<span>筛选: </span>
			<input type="text" id="searchBoxx" style="width:195px;"/>
		</div>
		<ul id="customerTree" class="ztree" style="margin-top:5px;padding:0; width:235px; height:290px; overflow-x:auto;"></ul>
	</div>
	<div class="pub_inp" style="float:right; height:320px;">
		<form id="inputFormC" class="validate" action='${ctx}<s:if test="customer.type == 2">/admin/customer!save.action</s:if><s:if test="customer.type == 1">/admin/customer!saveDirectory.action</s:if>' method="post" style="width:500px;">
			<s:hidden name="customer.id"></s:hidden>
			<table class="enforce_table" cellpadding="0" cellspacing="0" width="100%" style="font-size:14px;">
				<s:if test="customer.id == null">
					<tr height="29">
						<td align="right" class="enforcetd_bg">添加类型：</td>
						<td align="left" colspan="3"><s:radio name="customer.type" cssClass="customer_type" list="#{1:'公司',2:'联系人'}" onclick="changeType(this.value)"/></td>
					</tr>
				</s:if>
				<s:else>
					<s:hidden name="customer.type"></s:hidden>
				</s:else>
                <tr height="29" class="type_kh type_lxr">
					<td align="right" class="enforcetd_bg" id="addressText">上级目录：</td>
					<td align="left">
						<s:textfield name="parent.id" id="parent_id" readonly="true" cssClass="formText" cssStyle="width:50px"></s:textfield>
						<s:textfield name="parent.areaName" id="parent_areaName" readonly="true" rule="noempty" cssClass="formText"></s:textfield>
						<label class="requireField">*</label>
					</td>
				</tr>
                <tr height="29" class="type_kh type_lxr">
					<td align="right" class="enforcetd_bg" width="90" id="customerText">单位名称：</td>
					<td align="left" >
						<s:textfield name="customer.areaName" rule="noempty" cssClass="formText w300"></s:textfield><label class="requireField">*</label>
					</td>
                </tr>
                <tr height="29" class="type_kh">
					<td align="right" class="enforcetd_bg">单位地址：</td>
					<td align="left"><s:textfield name="customer.dwdz" cssClass="formText w300"></s:textfield></td>
                </tr>
				<tr height="29" class="type_lxr">
					<td align="right" class="enforcetd_bg">手机号码：</td>
					<td align="left"><s:textfield name="customer.lxdh" rule="noempty" maxlength="20" cssClass="formText"></s:textfield><label class="requireField">*</label></td>
				</tr>
				<tr height="29" class="type_lxr">
					<td align="right" class="enforcetd_bg">工作QQ：</td>
					<td align="left"><s:textfield name="customer.qq" cssClass="formText"></s:textfield></td>
				</tr>
				<tr height="29" class="type_lxr">
					<td align="right" class="enforcetd_bg" width="90">微信号码：</td>
					<td align="left"><s:textfield name="customer.wxh" maxlength="20" cssClass="formText"></s:textfield></td>
				</tr>
				<tr height="29" class="type_lxr">
					<td align="right" class="enforcetd_bg">公司电话：</td>
					<td align="left"><s:textfield name="customer.gsdh" maxlength="20" cssClass="formText"></s:textfield></td>
				</tr>
				<tr height="29" class="type_lxr">
					<td align="right" class="enforcetd_bg">传真号码：</td>
                    <td align="left"><s:textfield name="customer.czh" maxlength="20" cssClass="formText"></s:textfield></td>
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