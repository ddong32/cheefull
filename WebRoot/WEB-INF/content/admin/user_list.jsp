<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/content/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>用户列表</title>
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
			
            $( "#dialog" ).dialog({
        		dialogClass:"ppisDialog departmentDialog",
        		autoOpen: false,
        		maxWidth: 500,
        		height: "auto",
        		minHeight: 0,
        		resizable: false,
        		modal: true,
        		open : function (){
        			if ( $(this).find("ul").html()=="" ){
        				$(this).html("<p>没有符合的数据</p>");
        			}
        		}
        	});
            $("#list_user_department_name").click(function() {
        		$("#dialog").dialog("open");
        		$(".ui-widget-overlay").css("height",document.body.scrollHeight);
        		return false;
        	});
			$("#dept").jstree({
				"json_data" : {
					"ajax" : {
						"url" : "${ctx}/admin/department!ajaxDepartmentTree.action",
						"data" : function(n) {
							return {
								'department.id' : n.attr ? n.attr("id") : ""
							};
						}
					}
				},
				"themes" : { "theme" : "default" },
				"plugins" : [ "themes", "json_data", "ui" ]
			}).bind("select_node.jstree", function(event, data) {
				$("#list_user_department_name").val(data.rslt.obj.attr("title"));
				$("#user_userDepartmentId").val(data.rslt.obj.attr("id"));
				$("#dialog").dialog("close");
			}).bind("loaded.jstree", function(event, data) {
				data.inst.open_all(-1);
			});
			
			$("#clearDeptButton").click(function() {
				$("#list_user_department_name").val("");
				$("#user_userDepartmentId").val("");
        	});
		});
	</script>
	</head>
	<body class="con_r">
		<div id="dialog" title="组织机构" style="display: none">
			<div id="dept" class="demo"></div>
		</div>
		<form id="listForm" action="${ctx}/admin/user!list.action" method="post">
    	<div class="pub_inp_bg"><div class="pub_inp">
            <div>
				<table class="con_table">
				    <tr>
				    	<td align="right" width="73">管理部门：</td>
				        <td align="left" width="175">
							<s:hidden name="user.userDepartmentId"></s:hidden>
							<s:textfield name="user.department.name" readonly="true" id="list_user_department_name" cssClass="pub_input" style="width:120px;"></s:textfield>
							<span id="clearDeptButton" style="margin-left:5px;font-weight:bold;cursor:hand;text-decoration:underline;">清除</span>
						</td>
						<td align="right" width="73">登录名：</td>
						<td align="left" width="135"><s:textfield name="user.username" cssClass="pub_input" maxlength="100"/></td>
						<td align="right" width="73">姓名：</td>
						<td align="left" width="135"><s:textfield name="user.name" cssClass="pub_input" maxlength="100"/></td>
						<td align="right" width="73">员工编号：</td>
						<td align="left" width="135"><s:textfield name="user.jobNumber" cssClass="pub_input" maxlength="100"/></td>
				    </tr>
				</table>
            </div>
        </div></div>
        
        <div class="buttonArea_l">
            <input type="button" id="searchButton" class="pub_but searchButton formButton" value="查找" />
            <input type="button" id="addButton" class="pub_but formButton" href="${ctx}/admin/user!input.action" value="添加" />
        </div>
        <div class="datagrid">
				<table id="listTable" class="listTable" cellpadding="0" cellspacing="0">
                	<colgroup>
                		<col width="2%"/>
                        <col/>
                        <col/>
                        <col width="10%"/>
                        <col width="7%"/>
                        <col/>
                        <col width="10%"/>
                        <col width="10%"/>
                        <col width="10%"/>
                        <col width="7%"/>
                        <col width="7%"/>
                    </colgroup>
					<tr>
						<th></th>
						<th><span class="sort" name="username">登录名</span></th>
						<th><span class="sort" name="name">姓名</span></th>
						<th><span>员工编号</span></th>
						<th><span>手机号码</span></th>
						<th><span>邮箱</span></th>
						<th><span class="sort" name="department.name">部门名称</span></th>
						<th><span class="sort" name="department.code">部门编码</span></th>
						<th><span>角色</span></th>
						<th><span class="sort" name="isAccountLocked">是否锁定</span></th>
						<th><span class="sort" name="isAccountEnabled">是否启用</span></th>
						<th>
							<span>操作</span>
						</th>
					</tr>
					<s:iterator value="page.result" var="list" status="status">
						<tr <s:if test="#status.count%2==1">class="cor"</s:if>>
							<td class="tips" title=""><s:property value="#list.id"/></td>
							<td class="tips" title="<s:property value='#list.username'/>"><s:property value="#list.username"/></td>
							<td class="tips" title="<s:property value='#list.name'/>"><s:property value="#list.name"/></td>
							<td class="tips" title="<s:property value="#list.jobNumber"/>"><s:property value="#list.jobNumber"/></td>
							<td class="tips" title="<s:property value='#list.mobile'/>"><s:property value="#list.mobile"/></td>
							<td class="tips" title="<s:property value='#list.email'/>"><s:property value="#list.email"/></td>
							<td class="tips" title="<s:property value='#list.department.name'/>"><s:property value="#list.department.name"/></td>
							<td class="tips" title="<s:property value="#list.department.code"/>"><s:property value="#list.department.code"/></td>
							<td class="tips" title="<s:iterator value="#list.roleSet" var="role"><s:property value="#role.name"/></s:iterator>">
								<s:iterator value="#list.roleSet" var="role">
									<s:property value="#role.name"/>
								</s:iterator>
							</td>
							<td class="tips" title="<s:if test="#list.isAccountLocked == 0">否</s:if><s:if test="#list.isAccountLocked == 1">是</s:if>">
								<s:if test="#list.isAccountLocked == 0">否</s:if><s:if test="#list.isAccountLocked == 1"><font color="red">是</font></s:if>
							</td>
							<td class="tips" title="<s:if test="#list.isAccountEnabled == 1">启用</s:if><s:if test="#list.isAccountEnabled == 0">禁用</s:if>">
								<s:if test="#list.isAccountEnabled == 1">启用</s:if><s:if test="#list.isAccountEnabled == 0"><font color="red">禁用</font></s:if>
							</td>
							<td>
								<a class="popup" href="${ctx}/admin/user!input.action?user.id=<s:property value='#list.id'/>" title="编辑">[编辑]</a>
								<a class="popupDelete" href="${ctx}/admin/user!delete.action?user.id=<s:property value='#list.id'/>" title="删除">[删除]</a>
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
        <div id="popupDialog" title="添加/编辑用户"></div>
        <div id="notice2" title="提示消息"></div>
	</body>
</html>