<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/content/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta content="text/html; charset=utf-8" http-equiv="content-type" />
<title>公告列表</title>
<%@ include file="/WEB-INF/content/common/include.jsp"%>
<script type="text/javascript">
	$(document).ready(function() {
		<s:if test="page.result.size() <= 0">
    		$("#deleteButton").attr("disabled",true).css("cursor","default");
   		</s:if>
		$("#popupDialog").dialog({
			dialogClass : "ppisDialog",
			autoOpen : false,
			resizable : false,
			height : 400,
			width : 800,
			maxHeight : window.screen.height - 250,
			modal : true,
			close : function() {
				$("#popupDialog").empty();
			}
		});
		$(".popup,#addButton").bind('click', function() {
			$.ajax({
				type : "POST",
				cache : false,
				url : $(this).attr("href"),
				success : function(msg) {
					$("#popupDialog").html(msg).dialog("open");
					$(".ui-widget-overlay").css("height", document.body.scrollHeight);
				}
			});
			return false;
		});
	});
</script>
</head>
<body class="list con_r">
	<form id="listForm" action="${ctx}/admin/affiche!list.action" method="post">
		<div class="pub_inp_bg">
			<div class="pub_inp">
				<table class="con_table">
					<tr>
						<td align="right" width="100">公告标题名称:</td>
						<td align="left" width="274"><s:textfield name="affiche.title" cssClass="pub_input" maxlength="100" /></td>
					</tr>
				</table>
			</div>
		</div>
		<div class="buttonArea_l">
			<input type="button" id="searchButton" class="pub_but searchButton formButton" value="查找"  /> 
			<input type="button" id="addButton" class="pub_but formButton" href="${ctx}/admin/affiche!input.action" value="添加" />
			<input type="button" id="deleteButton" class="pub_but" url="${ctx}/admin/affiche!ajaxBatchDelete.action" value="选中删除" disabled hidefocus />
		</div>
		<div class="datagrid">
			<table id="listTable" class="listTable" cellpadding="0" cellspacing="0">
				<colgroup>
					<col width="20"/>
					<col width="80" />
					<col width="45%" />  
					<col />
					<col />
					<col />
					<col width="6%" />
				</colgroup>
				<tr>
					<th class="check"><input type="checkbox" id="checkbox" name="checkbox" class="allCheck" /></th>
					<th><span>类型</span></th>
					<th><span>标题</span></th>
					<th><span>创建时间</span></th>
					<th><span>创建人</span></th>
					<th><span>是否发布</span></th>
					<th><span>操作</span></th>
				</tr>
				<s:iterator value="page.result" var="list" status="status">
					<tr <s:if test="#status.count%2==1">class="cor"</s:if>>
						<td><input type="checkbox" name="ids" value="<s:property value='#list.id'/>" /></td>
						<td title="<s:if test="#list.type == 1">一般公告</s:if><s:elseif test="#list.type == 2">紧急公告</s:elseif><s:else>未知</s:else>">
							<s:if test="#list.type == 1">一般公告</s:if>
							<s:elseif test="#list.type == 2">紧急公告</s:elseif>
							<s:else>未知</s:else>
						</td>
						<td nowrap title="<s:property value="#list.title"/>"><s:property value="#list.title" /></td>
						<td title="<s:date name="#list.createDate" format="yyyy-MM-dd HH:mm:ss"/>"><s:date name="#list.createDate" format="yyyy-MM-dd HH:mm:ss"/></td>
						<td title="<s:property value="#list.user.name"/>"><s:property value="#list.user.name" /></td>
						<td title="<s:if test="#list.stat == 1">是</s:if> <s:else>否</s:else>"><s:if test="#list.stat == 1">是</s:if> <s:else>否</s:else></td>
						<td><a class="popup" href="${ctx}/admin/affiche!input.action?affiche.id=<s:property value='#list.id'/>" title="编辑">[编辑]</a></td>
					</tr>
				</s:iterator>
			</table>
			<s:if test="page.result.size() > 0">
				<div class="pagerBar clear">
					<div class="pager pub_l">
						<%@ include file="/WEB-INF/content/common/page.jsp"%>
					</div>
					<div class="bar">
						公告列表&nbsp;总数<s:property value="page.totalCount" />条/第<s:property value="page.pageNo" />页/共<s:property value="page.totalPages" />页
					</div>
				</div>
			</s:if>
			<s:else>
				<div class="noRecord">没有找到任何记录!</div>
			</s:else>
		</div>
	</form>
	<div id="popupDialog" title="添加/编辑公告"></div>
</body>

</html>