<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/content/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>供应商列表</title>
		<%@ include file="/WEB-INF/content/common/include.jsp"%>
		<script type="text/javascript">
		$(document).ready(function() {
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
		
			$( "#popupDialog" ).dialog({
				dialogClass:"ppisDialog",
				autoOpen: false,
				resizable: false,
				width:500,
				maxHeight: window.screen.height-250,
				modal: true

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
			
			$(".popupDelete").bind('click',function(){
				if(window.confirm('你确定要删除信息吗？')){
					$.ajax({
						type: "POST",
						cache: false,
						url: $(this).attr("href"),
						success: function(msg){
							isSuccess= (trim(msg)=="您的操作已成功!")?true :false;
							$("#notice2").html(msg);
							$("#notice2").dialog('open');
							$(".ui-widget-overlay").css("height", document.body.scrollHeight);
						}
					});
				}
				return false;
			});

		});
	</script>
	</head>
	<body class="con_r">
		<form id="listForm" action="${ctx}/admin/travel!list.action" method="post">
    	<div class="pub_inp_bg"><div class="pub_inp">
            <div>
				<table class="con_table">
				    <tr>
				    	<td align="right" width="73">线路名称:</td>
				        <td align="left" width="300">
				        	<s:textfield name="travel.line" cssClass="pub_input w250" />
				        </td>
				    </tr>
				</table>
            </div>
        </div></div>
        <div class="buttonArea_l">
            <input type="button" id="searchButton" class="pub_but searchButton formButton" value="查找" />
            <input type="button" id="addButton" class="pub_but formButton" href="${ctx}/admin/travel!input.action" value="添加" />
        </div>
        <div class="datagrid">
			<table id="listTable" class="listTable" cellpadding="0" cellspacing="0">
               	<colgroup>
                    <col width="5%"/>
                    <col width="65%"/>
                    <col width="10%"/>
                    <col width="10%"/>
                    <col width="10%"/>
                </colgroup>
				<tr>
					<th>序号</th>
					<th align="left">线路名称</th>
			        <th>操作人</th>
			        <th>录入时间</th>
			        <th>操作</th>
				</tr>
				<s:iterator value="page.result" var="list" status="status">
					<tr <s:if test="#status.count%2==1">class="cor"</s:if>>
						<td class="tips"><s:property value="#list.id"/></td>
						<td class="tips"><s:property value="#list.line"/></td>
						<td class="tips"><s:property value="#list.lrr"/></td>
						<td class="tips"><s:date name="#list.lrsj" format="yyyy-MM-dd"/></td>
						<td>
							<a class="popup" href="${ctx}/admin/travel!input.action?travel.id=<s:property value='#list.id'/>" title="编辑">[编辑]</a>
							<a class="popupDelete" href="${ctx}/admin/travel!delete.action?travel.id=<s:property value='#list.id'/>" title="删除">[删除]</a>
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
        <div id="popupDialog" title="添加/编辑订单"></div>
        <div id="notice2" title="提示消息"></div>
	</body>
</html>