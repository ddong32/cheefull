<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/content/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta content="text/html; charset=utf-8" http-equiv="content-type"/>
		<title>组织结构列表</title>
		<%@ include file="/WEB-INF/content/common/include.jsp"%>
        <script type="text/javascript">
		$(document).ready(function() {
			$( "#popupDialog" ).dialog({
				dialogClass:"ppisDialog",
				autoOpen: false,
				resizable: false,
				width:600,
				maxHeight: window.screen.height-250,
				modal: true,
				close: function() {
					$("#dialog").dialog("close");
				}
			});
            $(".popup,#addButton").bind('click',function(){
				$.ajax({
					type: "POST",
					cache:false,
					url: $(this).attr("href"),
					success: function(msg){
						$("#popupDialog").html(msg).dialog("open");
						$(".ui-widget-overlay").css("height",document.body.scrollHeight);
					}
				});
				return false;
			});
			
			
			var setting = {
				data: {
					simpleData: {
						enable: true
					}
				}
			};
			
			// 显示消息内容
			$(".showMessageContent").live("click",function(){
				var $this = $(this);
				var $thisMessageContentTr = $this.parent().parent().next(".messageContentTr");
				var $loading=$('<span/>').addClass("loading").html("加载中...");
				if ($.trim($thisMessageContentTr.find(".messageContent").text()) == "") {
					var id = $this.attr("id");
					$.ajax({
						"url" : "${ctx}/admin/department!ajaxHasAddressTree.action",
						data: {"department.id": id},
						async: false,
						dataType: "json",
						beforeSend: function(data) {
							$thisMessageContentTr.find(".messageContent").append($loading);
						},
						success: function(data) {
							if (data.length<=0){
								$thisMessageContentTr.find(".messageContent").find($loading).html("此目录下无数据");
								$("#addressTree"+id).hide();
							} else {
								$thisMessageContentTr.find(".messageContent").find($loading).remove();
								$.fn.zTree.init($("#addressTree"+id),setting,  data);
							}
						}
					});
				}
				if ($thisMessageContentTr.css("display")=="none"){
					$(this).addClass("open");
					$thisMessageContentTr.show();
				} else {
					$(this).removeClass("open");
					$thisMessageContentTr.hide();
				}
			});
			
		});
        </script>
        
		<style type="text/css">
		<!--
		.messageContentTr {display: none;}
		.showMessageContent{padding-left:14px; background:url(${ctx}/images/treearrows.png) no-repeat 0px 2px;}
		.open {background-position:0px -10px; }
		-->
		</style>
	</head>
	
	<body class="con_r">
		<form id="listForm" action="${ctx}/admin/department!list.action" method="post">
        <div class="pub_inp_bg">
            <div class="pub_inp">
				<table class="con_table">
					<tr>
						<td align="right" width="120">组织结构名称：</td>
						<td align="left" width="174"><s:textfield name="department.name" cssClass="pub_input" maxlength="100"></s:textfield></td>
						<td align="right" width="120">组织结构编码：</td>
						<td align="left" width="174"><s:textfield name="department.code" cssClass="pub_input" maxlength="100"></s:textfield></td>
					</tr>
				</table>
            </div>
        </div>
        <div class="buttonArea_l">
            <input type="button" id="searchButton" class="pub_but searchButton formButton" value="查找" />
            <input type="button" id="addButton" class="pub_but formButton" href="${ctx}/admin/department!input.action" value="添加" />
        </div>
        <div class="datagrid">
				<table id="listTable" class="listTable" cellpadding="0" cellspacing="0">
	               	<colgroup>
	               		<col width="25%"/>
						<col width="25%"/>
						<col width="25%"/>
						<col width="25%"/>
					</colgroup>
					<tr>
						<th><span>组织结构名称</span></th>
						<th><span>组织结构编码</span></th>
						<th><span class="sort" name="grade">级别</span></th>
						<th><span>操作</span></th>
					</tr>
					<s:iterator value="page.result" var="list" status="status">
						<tr <s:if test="#status.count%2==1">class="cor"</s:if>>
							<td class="tips" title="<s:property value="#list.name"/>" style="padding-left: ${(list.grade-1)*35+10}px"><a class="showMessageContent" id="<s:property value="#list.id"/>" href="javascript: void(0);"><s:property value="#list.name"/></a></td>
							<td class="tips" title="<s:property value="#list.code"/>"><s:property value="#list.code"/></td>
							<td class="tips" title="<s:property value="#list.grade"/>"><s:property value="#list.grade"/></td>
							<td>
								<a class="popup" href="${ctx}/admin/department!input.action?department.id=<s:property value='#list.id'/>&parent.id=<s:property value='#list.parent.id'/>" title="编辑">[编辑]</a>
							</td>
						</tr>
						<tr class="messageContentTr <s:if test="#status.count%2==1">cor</s:if>" >
							<td colspan="6" class="messageContent" style="padding-left: ${(list.grade-1)*35+20}px">
								<ul id="addressTree<s:property value="#list.id"/>" style="overflow-y:hidden;" class="ztree deviceZtree"></ul>
							</td>
						</tr>
					</s:iterator>
				</table>
				<s:if test="page.result.size() > 0">
					<div class="pagerBar clear">
						<div class="pager pub_l">
							<%@ include file="/WEB-INF/content/common/page.jsp"%>
						</div>
                        <div class="bar">
                            总数<s:property value="page.totalCount"/>条/第<s:property value="page.pageNo"/>页/共<s:property value="page.totalPages"/>页
                        </div>
					</div>
				</s:if>
				<s:else>
					<div class="noRecord">没有找到任何记录!</div>
				</s:else>
		</div>
        </form>
        <div id="popupDialog" title="添加/编辑组织结构"></div>
	</body>

</html>