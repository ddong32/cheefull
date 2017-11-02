<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/content/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 transitional//EN" "http://www.w3.org/tr/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/main.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/zTreeStyle_menu.css" />
<title>无标题文档</title>
	<script type="text/javascript" src="${ctx}/scripts/jquery-1.8.2.min.js"></script>
	<script type="text/javascript" src="${ctx}/scripts/jquery.ztree.core-3.5.js"></script>
	<script type="text/javascript" src="${ctx}/scripts/jquery.ztree.exhide-3.5.js"></script>
    <SCRIPT type="text/javascript">
        <!--
        var curMenu = null, zTree_Menu = null;
        var setting = {
            view: {
                showLine: false,
                selectedMulti: false,
                dblClickExpand: false
            },
            data: {
                simpleData: {
                    enable: true
                }
            },
            callback: {
                onNodeCreated: this.onNodeCreated,
                beforeClick: this.beforeClick,
                onClick: this.onClick
            }
        };
        function onClick(e, treeId, node)
        {
            url = node.path;
            window.frames['frame_content'].document.location.href=url;
        }

        var zNodes =[
			<s:iterator value="resourceList" var="resource" status="st">
				<security:authorize ifAnyGranted="${resource.roleValues}">
	            	<s:iterator value="#resource.children" var="secondResource" status="st">
	                <security:authorize ifAnyGranted="${secondResource.roleValues}">
	                    { id:${secondResource.id}, pId:0, name:"${secondResource.name}" ,hid:"${resource.id}" ,open:true},
	                    <s:iterator value="#secondResource.children" var="thirdResource" status="st">
	                        <security:authorize ifAnyGranted="${thirdResource.roleValues}">
	                        { id:${secondResource.id*2}, pId:${secondResource.id}, name:"${thirdResource.name}", url:"../${thirdResource.value}", target:"mainFrame"},
	                        </security:authorize>
	                    </s:iterator>
	                </security:authorize>
	                </s:iterator> 
				</security:authorize>
			</s:iterator>
		];

        function beforeClick(treeId, node) {
            if (node.isParent) {
                if (node.level === 0) {
                    {
                        if( node.open )
                        {
                            zTree_Menu.expandNode(node, false);
                            $("#"+node.tId+"_a").removeClass("curr");
                        }else
                        {
                            zTree_Menu.expandNode(node, true);
                            $("#"+node.tId+"_a").addClass("curr");
                        }
                    }


                    //var pNode = curMenu;
                    //while (pNode && pNode.level !==0) {
                    //	pNode = pNode.getParentNode();
                    //}
                    //if (pNode !== node) {
                    //var a = $("#" + pNode.tId + "_a");
                    //a.removeClass("cur");
                    //	zTree_Menu.expandNode(pNode, false);
                    //}
                    //a = $("#" + node.tId + "_a");
                    //a.addClass("cur");

                    //var isOpen = false;
                    //for (var i=0,l=node.children.length; i<l; i++) {
                    //	if(node.children[i].open) {
                    //		isOpen = true;
                    //		break;
                    //	}
                    //}
                    //if (isOpen) {
                    //	zTree_Menu.expandNode(node, true);
                    //	curMenu = node;
                    //} else {
                    //判断，它的子节点是否为父节点
                    //	zTree_Menu.expandNode(node.children[0].isParent?node.children[0]:node, true);
                    //	curMenu = node.children[0];
                    //}
                } else {
                    zTree_Menu.expandNode(node);
                }
            }
            return !node.isParent;
        }
        function onClick(e, treeId, node) {
            //alert("Do what you want to do!");
        }
		function changeTree(hid){
			zTree_Menu.hideNodes(zTree_Menu.getNodes());
			var nodes = zTree_Menu.getNodesByParam("hid", hid, null);
			zTree_Menu.showNodes(nodes);
		}
		
        $(document).ready(function(){
            $.fn.zTree.init($("#treeDemo"), setting, zNodes);
            zTree_Menu = $.fn.zTree.getZTreeObj("treeDemo");
            nodes = zTree_Menu.getNodes();
            //curMenu = zTree_Menu.getNodes()[0].children[0].children[0];
            //zTree_Menu.selectNode(curMenu);
            for (n in nodes){
            	if (nodes[n].open){
            		var a = $("#" + nodes[n].tId + "_a");
           			a.addClass("curr");
            	}
            }
            if(zNodes.length>0){
				changeTree(zNodes[0].hid);
            }
        });
        //-->
    </SCRIPT> 
    <style type="text/css">*{margin:0; padding:0;}</style>
</head>
<body style="background:url(${ctx}/images/left_bg.jpg); padding-right:2px;">
	<div class="con_l_title">
		<div class="ico1"><a href="#"></a></div>
	</div>
	<div class="content_wrap">
		<div class="zTreeDemoBackground left">
		    <ul id="treeDemo" class="ztree"></ul>
		</div>
	</div>
</body>
</html>