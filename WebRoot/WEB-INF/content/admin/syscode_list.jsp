<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/content/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta content="text/html; charset=utf-8" http-equiv="content-type"/>
		<title>系统字典列表</title>
		<%@ include file="/WEB-INF/content/common/include.jsp"%>
        <script type="text/javascript">
        function checkRule(){
			var isIllegal = false;
			$("[rule]").each(function(index, element) {
				isIllegal |= testIllegal($(this));
			});
			if (isIllegal){
				$("#errInfo").show();
				return false;
			} else {
				$("#errInfo").hide();
				return true;
			}
		}
		$(document).ready(function() {
			$("#searchButton").click(function(){
				$("#pageNo").val(1);
				if (!checkRule()){
					return false;
				}
				listForm.submit();
			});
			$( "#popupDialog" ).dialog({
				dialogClass:"ppisDialog",
				autoOpen: false,
				resizable: false,
				width:400,
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
		<form id="listForm" action="${ctx}/admin/sys_code!list.action" method="post">
        <div class="pub_inp_bg">
            <div class="pub_inp">
				<table class="con_table">
					<tr>
						<td align="right" width="73">字典编码:</td>
						<td align="left" width="174"><s:textfield name="sysCode.codeCode" cssClass="pub_input" maxlength="100"/></td>
						<td align="right" width="73">字典名称:</td>
						<td align="left" width="174"><s:textfield name="sysCode.codeName" cssClass="pub_input" maxlength="100"/></td>
						<td align="right" width="73">字典类型:</td>
						<td align="left" width="174"><s:textfield name="sysCode.codeType" rule="num" noappend="true" cssClass="pub_input" maxlength="100"/></td>
						<td align="right" width="73">类型名称:</td>
						<td align="left" width="174"><s:textfield name="sysCode.codeTypeName" cssClass="pub_input" maxlength="100"/></td>
					</tr>
				</table>
            </div>
        </div>
        <div class="buttonArea_l">
            <input type="button" id="searchButton" class="pub_but searchButton formButton" value="查找" />
            <!-- 
            <input type="button" id="addButton" class="pub_but formButton" href="${ctx}/admin/sys_code!input.action" value="添加" />
             -->
            <span id="errInfo">查询条件含有非法字符！</span>
        </div>
        <div class="datagrid">
				<table id="listTable" class="listTable" cellpadding="0" cellspacing="0">
                	<colgroup>
                    	<col width="7%" />
                        <col width="7%" />
                        <col />
                        <col width="7%" />
                        <col width="7%" />
                        <col width="7%" />
                        <col width="7%" />
                    </colgroup>
					<tr>
						<th>
							<span class="sort" name="codeId">字典编号</span>
						</th>
						<th>
							<span class="sort" name="codeCode">字典编码</span>
						</th>
						<th>
							<span class="sort" name="codeName">字典名称</span>
						</th>
						<th>
							<span class="sort" name="codeType">字典类型</span>
						</th>
						<th>
							<span class="sort" name="codeTypeName">类型名称</span>
						</th>
						<th>
							<span class="sort" name="codeOrder">字典顺序</span>
						</th>
						<th>
							<span class="sort" name="stat">字典状态</span>
						</th>
						<!-- 
						<th>
							<span>操作</span>
						</th>
						 -->
					</tr>
					<s:iterator value="page.result" var="list" status="status">
						<tr <s:if test="#status.count%2==1">class="cor"</s:if>>
							<td>
								<s:property value="#list.codeId"/>
							</td>
							<td>
								<s:property value="#list.codeCode"/>
							</td>
							<td>
								<s:property value="#list.codeName"/>
							</td>
							<td>
								<s:property value="#list.codeType"/>
							</td>
							<td>
								<s:property value="#list.codeTypeName"/>
							</td>
							<td>
								<s:property value="#list.codeOrder"/>
							</td>
							<td>
								<s:if test="#list.stat == 1">启用</s:if><s:if test="#list.stat==0"><font color="red">禁用</font></s:if>
							</td>
							<!-- 
							<td>
								<a class="popup" href="${ctx}/admin/sys_code!input.action?sysCode.id=<s:property value='#list.id'/>" title="编辑">[编辑]</a>
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
                            总数<s:property value="page.totalCount"/>条/第<s:property value="page.pageNo"/>页/共<s:property value="page.totalPages"/>页
                        </div>
					</div>
				</s:if>
				<s:else>
					<div class="noRecord">没有找到任何记录!</div>
				</s:else>
		</div>
        </form>
        <div id="popupDialog" title="添加/编辑系统字典"></div>
	</body>

</html>