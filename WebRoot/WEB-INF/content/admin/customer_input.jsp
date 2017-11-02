<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/content/common/taglibs.jsp"%>
<script>

$.fx.speeds._default = 1000;
jQuery.ajaxSettings.traditional = true;
var isSuccess=true;

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

var setting2 = {
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

$(function() {
	$("#notice2").dialog({
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
});


var zTree = "", zNodes = "" ,key;
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
		var typeVal=$("input[name='customer.type']:checked").val();
		if((typeVal) == "0") {
			$("#parent_id").val("");
			$("#parent_areaName").val("");
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
	
	zTree = $.fn.zTree.init($("#customerTree"), setting);

	key = $("#searchBoxx");
	key.bind("propertychange", searchNode).bind("input", searchNode);
	
	var typeVal = <s:property value="customer.type"/>;
	changeType(typeVal);
	
});

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

var cloneObj = function(obj){
    var str, newobj = obj.constructor === Array ? [] : {};
    if(typeof obj !== 'object'){
        return;
    } else if(window.JSON){
        str = JSON.stringify(obj), //系列化对象
        newobj = JSON.parse(str); //还原
    } else {
        for(var i in obj){
            newobj[i] = typeof obj[i] === 'object' ? 
            cloneObj(obj[i]) : obj[i]; 
        }
    }
    return newobj;
};

// 树形结构搜索
function searchNode(){
	if(zNodes == ""){
		zNodes = everyNode(zTree).slice(0);
	}
	//alert(JSON.stringify(zNodes[0]));
	if(zNodes.length == 0){
		return false;
	}
	
	// 声明一个新的树对象
	var newZNodes = null;
	// 将原树对象复制出一个副本，并将这个副本JSON字符串转换成新的树对象
	var treeJSON  = cloneTreeNodes(zNodes[0]);
	newZNodes = eval('(' + '[' + treeJSON + ']' + ')');
	
	var root = newZNodes[0];
	
	var key = $("#searchBoxx").val();
	if(key != ""){
		if(!/^[\u4e00-\u9fa5]+$/i.test(key)){
			return false;
		}
	}else{
		$.fn.zTree.init($("#customerTree"), setting2, root);
		return false;
	}
	
	var nodeMap = {};
	// 对新树对象建立反向引用关系（在子节点中增加父节点的引用）
	// 构造节点映射表（下面借助该映射表建立反向引用关系）
	nodeMap = buildNodeMap(root, nodeMap);
	
	// 建立子节点对父节点的引用
	nodeMap = buildParentRef(root, nodeMap);
	
	// 设置树节点为“不可见”状态
	setTreeNotVisible(root);
	// 搜索包含关键字的树节点，将包含关键字的节点所在路径设置为“可见”，例如：如果某一节点包含搜索关键字，
	// 那么它的所有上级节点和所有下级节点都设置为“可见”
	searchTreeNode(root, root, nodeMap, key);
	// 对树形结构数据进行搜索过滤后，根据JavaScript树状对象，重新生成JSON字符串
	treeJSON = reBuildTreeJSON(root);
	newZNodes = eval('(' + '[' + treeJSON + ']' + ')');
	//alert(JSON.stringify(newZNodes));
	$.fn.zTree.init($("#customerTree"), setting2, newZNodes);
	$.fn.zTree.getZTreeObj("customerTree").expandAll(true);
};

// 将原树形结构数据复制出一个副本，以备对副本进行搜索过滤，而不破坏原始数据（原始数据用来恢复原状用）【先序遍历法】
function cloneTreeNodes(root) {
	var treeJSON = '{' + 'name : \'' + root.name + '\', id : \'' + root.id + '\',' + 'pId : \'' + root.pId + '\',' + ' type : \'' + root.type + '\',' + ' pathName : \'' + root.pathName + '\', open : \'' + root.open + '\',' + 'checked : false, visible : true, parentNode : null';
	if (root.children && root.children.length != 0) {
		treeJSON += ', children : [';
		for (var i = 0; i < root.children.length; i++) {
			treeJSON += cloneTreeNodes((root.children)[i]) + ',';
		}
		treeJSON = treeJSON.substring(0, treeJSON.length - 1);
		treeJSON += "]";
	}
	return treeJSON + '}';
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

function buildNodeMap(node, nodeMap) {
	var newObj = new Object();
	newObj.name = node.name;
	newObj.id = node.id;
	newObj.pId = node.pId;
	newObj.type = node.type;
	newObj.checked = node.checked;
	newObj.open = node.open;
	newObj.visible = node.visible;
	newObj.pathName = node.pathName;
	nodeMap['_' + node.id] = newObj;
	if (node.children && node.children.length != 0) {
		for (var i = 0; i < node.children.length; i++) {
			buildNodeMap((node.children)[i], nodeMap);
		}
	}
	return nodeMap; // 这里需要将nodeMap返回去，然后传给buildParentRef()函数使用，这和Java中的引用传递不一样，怪异！！
}

// 建立子节点对父节点的引用
function buildParentRef(node, nodeMap) {
	for (id in nodeMap) {
		if ((nodeMap[id]).pId == '') {
			(nodeMap[id]).parentNode = null;
		} else {
			(nodeMap[id]).parentNode = nodeMap['_' + (nodeMap[id]).pId];
		}
	}
	return nodeMap;
}

// 设置树节点为“不可见”状态【先序遍历法】
function setTreeNotVisible(root) {
	root.visible = false;
	if (root.children && root.children.length != 0) {
		for (var i = 0; i < root.children.length; i++) {
			setTreeNotVisible((root.children)[i]);
		}
	}
}

// 设置树节点为“可见”状态【先序遍历法】
function setTreeVisible(root) {
	root.visible = true;
	if (root.children && root.children.length != 0) {
		for (var i = 0; i < root.children.length; i++) {
			setTreeVisible((root.children)[i]);
		}
	}
}

// 设置当前节点及其所有上级节点为“可见”状态
function setRouteVisible(root, node, nodeMap) {
	node.visible = true;
	var parentNodes = [];
	var currentNode = nodeMap['_' + node.id];
	var parentNode = currentNode.parentNode;
	while (parentNode != null) {
		parentNodes.push(parentNode);
		parentNode = parentNode.parentNode;
	}			
	// 如果没有上级节点，说明当前节点就是根节点，直接返回即可
	if (parentNodes.length == 0) {
		return;
	}			
	setParentNodesVisible(root, parentNodes);
}

// 设置所有上级节点为“可见”，
// 这里的『设置上级节点为“可见”』使用的方法与《附录二：新概念『智能树形菜单』--利用加权多叉树结合JavaScript树形控件实现》
// 中的『设置功能路径可见』方法不一样，这是由于在JavaScript中无法建立像Java中的那种带有双向引用的多叉树结构（即父节点
// 引用子节点，子节点引用父节点），在JavaScript中如果做这种双向引用的话，会造成『Stack overflow』异常，所以只能分别建立
// 两棵多叉树对象，一棵是原始树形结构对象，另一棵是利用nodeMap建立的多叉树对象，专门用于反向引用，即子节点对父节点的引用。
// 而在Java中，直接可以根据一个节点的父节点引用，找到它所有的父节点。但是在这里，只能采用一种笨办法，先从反向引用的多叉树
// 中找到某一节点的所有父节点，存在一个数组里，然后在原始树形结构对象中使用先序遍历方法，从顶向下依次查找，把某一节点的所有
// 父节点设置为可见，效率较低，但与利用反向引用查找父节点的方法目的是一样的。
function setParentNodesVisible(node, parentNodes) {
	if (containNode(node, parentNodes)) {
		node.visible = true;
	}
	if (node.children && node.children.length != 0) {
		var i = 0;
		for (; i < node.children.length; i++) {
				if (containNode(node, parentNodes)) {
					setParentNodesVisible((node.children)[i], parentNodes);
				}
		}
		// 如果在本层节点中没有找到要设置“可见性”的节点，说明需要设置“可见性”的节点都已经找完了，不需要再向下一层节点中寻找了，
		// 直接退出递归函数
		if (i == node.children.length - 1) {
			return;
		}
	}
}

// 检查数组中是否包含与指定节点编号相同的节点
function containNode(node, parentNodes) {
	for (var i = 0; i < parentNodes.length; i++) {
		if (parentNodes[i].id == node.id) {
			return true;
		}
	}
	return false;
}

// 搜索包含关键字的树节点，将包含关键字的节点所在路径设置为“可见”，例如：如果某一节点包含搜索关键字，
// 那么它的所有上级节点和所有下级节点都设置为“可见”【先序遍历法】
function searchTreeNode(root1, root2, nodeMap, keyWord) {
	if (root2.name.indexOf(keyWord) > -1) {
		setTreeVisible(root2);
		setRouteVisible(root1, root2, nodeMap);
	} else {
		if (root2.children && root2.children.length != 0) {
			for (var i = 0; i < root2.children.length; i++) {
				searchTreeNode(root1, (root2.children)[i], nodeMap, keyWord);
			}
		}
	}
}

// 对树形结构数据进行搜索过滤后，根据JavaScript树状对象，重新生成JSON字符串【先序遍历法】
function reBuildTreeJSON(node) {
	if (node.visible) {
		//var treeJSON = '{' + 'name : \'' + node.name + '\', id : \'' + node.id + '\',' + 'pid : \'' + node.pid + '\',' + ' ex_weight : ' + node.ex_weight + ', ex_visible : ' + node.ex_visible + ', ex_parentNode : null';
		var treeJSON = '{' + 'name : \'' + node.name + '\', id : \'' + node.id + '\',' + 'pId : \'' + node.pId + '\',' + ' type : \'' + node.type + '\',' + ' pathName : \'' + node.pathName + '\', visible : ' + node.visible + ', open : true, checked : false, parentNode : null';
		if (node.children && node.children.length != 0) {
			treeJSON += ', children : [';
			for (var i = 0; i < node.children.length; i++) {
				if ((node.children)[i].visible) {
					treeJSON += reBuildTreeJSON((node.children)[i]) + ',';
				} else {
					treeJSON += reBuildTreeJSON((node.children)[i]);
				}
			}
			treeJSON = treeJSON.substring(0, treeJSON.length - 1);
			treeJSON += "]";
		}
		return treeJSON + '}';
	} else {
		return '';
	}
}

function changeType(typeVal){
	if(typeVal == 0){
		$("#customerText").html("城市：");
		$(".type_kh").hide();
		$(".type_lxr").hide();
		$(".type_kh [rule]").attr("norule",true);
		$(".type_lxr [rule]").attr("norule",true);
		$(".type_cs").show();
		$(".type_cs [rule]").removeAttr("norule");
		$("#inputFormC").attr("action", "${ctx}/admin/customer!saveCity.action");
		$("#searchBoxx").attr("disabled",true);
	}else if(typeVal == 1){
		$("#addressText").html("所在地：");
		$("#customerText").html("单位名称：");
		$(".type_cs").hide();
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
		$(".type_cs").hide();
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
						<td align="left" colspan="3"><s:radio name="customer.type" cssClass="customer_type" list="#{0:'城市',1:'公司',2:'联系人'}" onclick="changeType(this.value)"/></td>
					</tr>
				</s:if>
				<s:else>
					<s:hidden name="customer.type"></s:hidden>
				</s:else>
                <tr height="29" class="type_kh type_lxr">
					<td align="right" width="90" class="enforcetd_bg" id="addressText">上级目录：</td>
					<td align="left">
						<s:textfield name="parent.id" id="parent_id" readonly="true" cssClass="formText" cssStyle="width:50px"></s:textfield>
						<s:textfield name="parent.areaName" id="parent_areaName" rule="noempty" cssClass="formText"></s:textfield>
						<label class="requireField">*</label>
					</td>
				</tr>
                <tr height="29" class="type_cs type_kh type_lxr">
					<td align="right" class="enforcetd_bg" width="90" id="customerText">单位名称：</td>
					<td align="left" >
						<s:textfield name="customer.areaName" rule="noempty" cssClass="formText w300"></s:textfield><label class="requireField">*</label>
					</td>
                </tr>
                <tr height="29" class="type_cs">
					<td align="right" class="enforcetd_bg">排序：</td>
                    <td align="left"><s:textfield name="customer.sort" maxlength="20" cssClass="formText"></s:textfield></td>
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