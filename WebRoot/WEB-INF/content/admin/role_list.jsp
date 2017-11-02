<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/content/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta content="text/html; charset=utf-8" http-equiv="content-type"/>
		<title>角色列表</title>
		<%@ include file="/WEB-INF/content/common/include.jsp"%>
        <script type="text/javascript">
		$(document).ready(function() {
			$( "#popupDialog" ).dialog({
				dialogClass:"ppisDialog",
				autoOpen: false,
				resizable: false,
				width:400,
				maxHeight: window.screen.height-250,
				modal: true,
				close: function() {
					$("#popupDialog").empty();
				}
			});
            $(".popup,#addButton").bind('click',function(){
				$.ajax({
					type: "POST",
					cache: false,
					url: $(this).attr("href"),
					success: function(msg){
						$("#popupDialog").html(msg).dialog("open");
						$(".ui-widget-overlay").css("height",document.body.scrollHeight);
					}
				});
				return false;
			});
        });
        </script>
	</head>
	<body class="con_r">
		<form id="listForm" action="${ctx}/admin/role!list.action" method="post">
        <div class="pub_inp_bg">
            <div class="pub_inp">
				<table class="con_table">
					<tr>
						<td align="right" width="73">角色名称:</td>
                        <td align="left" width="174"><s:textfield name="role.name" cssClass="pub_input" maxlength="100"/></td>
					</tr>
				</table>
            </div>
        </div>
        <div class="buttonArea_l">
        	<input type="button" id="searchButton" class="pub_but searchButton formButton" value="查找" />
        	<input type="button" id="addButton" class="pub_but formButton" href="${ctx}/admin/role!input.action" value="添加" />
        </div>
        <div class="datagrid">
				<table id="listTable" class="listTable" cellpadding="0" cellspacing="0">
                	<colgroup>
                    	<col />
                        <col />
                        <col />
                        <col width="6%"/>
                    </colgroup>
					<tr>
						<th><span class="sort" name="name">角色名称</span></th>
						<th><span>角色标识</span></th>
						<th><span>备注</span></th>
						<th>
							<span>操作</span>
						</th>
					</tr>
					<s:iterator value="page.result" var="list" status="status">
						<tr <s:if test="#status.count%2==1">class="cor"</s:if>>
							<td class="tips" title="<s:property value="#list.name"/>"><s:property value="#list.name"/></td>
							<td class="tips" title="<s:property value="#list.value"/>"><s:property value="#list.value"/></td>
							<td class="tips" title="<s:property value="#list.description"/>"><s:property value="#list.description"/></td>
							<td>
								<a class="popup" href="${ctx}/admin/role!input.action?role.id=<s:property value='#list.id'/>" title="编辑">[编辑]</a>
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
                            角色列表&nbsp;总数<s:property value="page.totalCount"/>条/第<s:property value="page.pageNo"/>页/共<s:property value="page.totalPages"/>页
                        </div>
					</div>
				</s:if>
				<s:else>
					<div class="noRecord">没有找到任何记录!</div>
				</s:else>
		</div>
        </form>
        <div id="popupDialog" title="添加/编辑角色"></div>
	</body>

</html>