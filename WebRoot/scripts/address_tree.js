var checkedNodes=[];
var setting = {
	view: {
		//addHoverDom: addHoverDom,
		//removeHoverDom: removeHoverDom
	},
	edit: {
		enable: false,
		editNameSelectAll: true
	},
	async: {
		enable: true,
		url:"../admin/customer!ajaxCustomerZTree.action?noFilter="+$("#noFilter").val()+"&username="+$("#username").val()+"&password="+$("#password").val(),
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

var log, className = "dark";
function beforeClick(treeId, treeNode, clickFlag) {
	className = (className === "dark" ? "":"dark");
	return (treeNode.click != false);
}

function onClick(e, treeId, treeNode) {
	$("#listForm #treeSel").val(treeNode.pathName);
}

function showMenu() {
	var cityObj = $("#treeSel");
	var cityOffset = $("#treeSel").offset();
	$("#menuContent").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");
	$("html").bind("mousedown", onBodyDown);
}
function hideMenu() {
	$("#menuContent").fadeOut("fast");
	$("body").unbind("mousedown", onBodyDown);
}
function onBodyDown(event) {
	if (!(event.target.id == "menuBtn" || event.target.id == "treeSel" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
		hideMenu();
	}
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

function initCheckedNodes(event, treeId, treeNode, msg){
	var tree = $.fn.zTree.getZTreeObj(treeId);
	var checkedNodes = tree.getCheckedNodes(true);
	for (i in checkedNodes){
		tree.checkNode(checkedNodes[i], checkedNodes[i].checked, true);
	}
}

var zTree;
var mNodes=[];
var gNodes=[];
$(document).ready(function(){
	$("#tabs").tabs();
	$.fn.zTree.init($("#selectTree"), setting);
    zTree = $.fn.zTree.getZTreeObj("selectTree");

    $("#treeSel").keyup(function(){
        mNodes=zTree.getNodesByParamFuzzy("name", $("#treeSel").val(), null);
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
	});
});
