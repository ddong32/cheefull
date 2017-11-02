<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/content/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta content="text/html; charset=utf-8" http-equiv="content-type"/>
		<title>资源列表</title>
		<%@ include file="/WEB-INF/content/common/include.jsp"%>
        <script type="text/javascript">
		$(document).ready(function() {
			$( "#popupDialog" ).dialog({
				dialogClass:"ppisDialog",
				autoOpen: false,
				resizable: false,
				width:450,
				maxHeight: window.screen.height-250,
				modal: true,
				close: function() {
					$("#dialog").dialog("close");
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
		<form id="listForm" action="${ctx}/admin/resource!list.action" method="post">
        <div class="pub_inp_bg">
            <div class="pub_inp">
                    <table class="con_table">
                        <tr>
                            <td align="right" width="73">资源名称: </td>
                            <td align="left" width="174"><s:textfield name="resource.name" cssClass="pub_input" maxlength="100"/></td>
                            <td align="right" width="73">资源值: </td>
                            <td align="left" width="174"><s:textfield name="resource.value" cssClass="pub_input" maxlength="100"/></td>
                        </tr>
                    </table>
            </div>
        </div>
        <div class="buttonArea_l">
            <input type="button" id="searchButton" class="pub_but searchButton formButton" value="查找" />
            <!-- 
            <input type="button" id="addButton" class="pub_but formButton" href="${ctx}/admin/resource!input.action" value="添加" />
             -->
        </div>
        <div class="datagrid">
				<table id="listTable" class="listTable" cellpadding="0" cellspacing="0">
                	<colgroup>
                        <col/>
                        <col/>
                        <col width="6%"/>
                        <col width="6%"/>
                    </colgroup>
					<tr>
						<th><span>资源名称</span></th>
						<th><span>资源值</span></th>
						<th><span>排序</span></th>
						<th><span class="sort" name="grade">级别</span></th>
						<!-- 
						<th>
							<span>操作</span>
						</th>
						 -->
					</tr>
					<s:iterator value="page.result" var="list" status="status">
						<tr <s:if test="#status.count%2==1">class="cor"</s:if>>
							<td style="padding-left: ${(list.grade-1)*35+10}px" title="<s:property value="#list.name"/>"> ■ <s:property value="#list.name"/></td>
							<td title="<s:property value="#list.value"/>"><s:property value="#list.value"/></td>
							<td><s:property value="#list.sort"/></td>
							<td><s:property value="#list.grade"/></td>
							<!-- 
							<td>
								<a class="popup" href="${ctx}/admin/resource!input.action?resource.id=<s:property value='#list.id'/>&parent.id=<s:property value='#list.parent.id'/>" title="编辑">[编辑]</a>
							</td>
							 -->
						</tr>
					</s:iterator>
				</table>
				<s:if test="page.result.size() > 0">
					<div class="pagerBar clear">
						<div class="pager pub_l">
							<%@ include file="/WEB-INF/content/common/page.jsp"%>
						</div>
                        <div class="bar">
                            资源列表&nbsp;总数<s:property value="page.totalCount"/>条/第<s:property value="page.pageNo"/>页/共<s:property value="page.totalPages"/>页
                        </div>
					</div>
				</s:if>
				<s:else>
					<div class="noRecord">没有找到任何记录!</div>
				</s:else>
		</div>
        </form>
        <div id="popupDialog" title="添加/编辑资源"></div>
	</body>
</html>