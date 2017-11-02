<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/content/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 transitional//EN" "http://www.w3.org/tr/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="height:100%;">
<HEAD>
	<title>编辑用户信息</title>
	<%@ include file="/WEB-INF/content/common/include.jsp"%>
	<style type="text/css">.pub_input{width:150px;}</style>
	<script>
	
	// increase the default animation speed to exaggerate the effect
	$.fx.speeds._default = 1000;
	jQuery.ajaxSettings.traditional = true;
	var isSuccess=true;
	$(document).ready(function(e) {
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
		
		//------------------
		$( "#popupDialog" ).dialog({
			dialogClass:"ppisDialog",
			autoOpen: false,
			resizable: false,
			width:700,
			maxHeight: window.screen.height-250,
			modal: true
		});
		
		$( "#tranDialog" ).dialog({
			dialogClass:"ppisDialog",
			autoOpen: false,
			resizable: false,
			width:550,
			maxHeight: window.screen.height-250,
			modal: true
		});
		
	    $(".popup, #addButton").bind('click',function(){
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
		
		$("#transferButton").bind('click',function(){
			$.ajax({
				type: "POST",
				cache:false,
				url: $(this).attr("href"),
				success: function(msg){
					$("#tranDialog").html(msg).dialog("open");
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
		
		$("#_usersId").multiselect({buttonWidth: 155, minWidth:155, selectedList:1, noneSelectedText:"全部", classes: 'ppisDialog'});

	});
	</script>
</HEAD>
<body class="con_r">
    <form id="listForm" action="${ctx}/admin/expence!list.action" method="post">
    	<div class="pub_inp_bg"><div class="pub_inp">
			<table class="con_table">
				<tr>
					<td align="right" width="73">报销时间:</td>
					<td align="left" width="240">
						<input type="text" class="Wdate" name="expence.beginDate" id="bd" size="12" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'ed\');}',startDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd',readOnly:true})" readonly="true" class="Wdate" style="width:100px;text-indent:4px" value="${expence.beginDate}" />
						至
						<input type="text" class="Wdate" name="expence.endDate" id="ed" size="12" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'bd\');}',startDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd',readOnly:true})" readonly="true" class="Wdate" style="width:100px;text-indent:4px"  value="${expence.endDate}"/>
					</td>
					<td align="right" width="73">审批状态:</td>
					<td align="left" width="120"><s:select name="expence.stat" list="#{'1':'未审','2':'已审','3':'支付','0':'回退'}" headerKey="" headerValue="请选择"/></td>
					<td align="right" width="73">报销类型:</td>
					<td align="left" width="120"><s:select name="expence.bxlx" list="expenceMap" listKey="key" listValue="%{value}" headerKey="" headerValue="请选择" style="width:110px;"></s:select></td>
				</tr>
			</table>
        </div></div>
        <div class="buttonArea_l">
            <input type="button" id="searchButton" class="pub_but searchButton formButton" value="查找" />
            <input type="button" id="addButton" class="pub_but formButton" href="${ctx}/admin/expence!input.action" value="添加" /> 
        </div>
    	<div class="datagrid">
			<table id="listTable" class="listTable" cellpadding="0" cellspacing="0">
               	<colgroup>
            		<col width="4%"/>
                    <col width="8%"/>
                    <col width="8%"/>
                    <col width="8%"/>
                    <col width=""/>
                    <col width="8%"/>
                    <col width="9%"/>
                    <col width="8%"/>
                    <col width="8%"/>
                    <col width="10%"/>
                </colgroup>
				<tr>
					<th>编号</th>
					<th>审批状态</th>
					<th>报销类型</th>
					<th>发生日期</th>
					<th class="tar">报销内容</th>
					<th>金额</th>
					<th>报销部门</th>
					<th>报销人</th>
					<th>报账日期</th>
					<th>操作</th>
				</tr>
				<s:iterator value="page.result" var="list" status="status">
				<tr <s:if test="#status.count%2==1">class="cor"</s:if>>
					<td class="tips"><s:property value="#list.id"/></td>
					<td class="tips"><s:property value="expenceStatMap.get(#list.stat)"/></td>
					<td class="tips"><s:property value="expenceMap.get(#list.bxlx)"/></td>
					<td class="tips"><s:date name="#list.fssj" format="yyyy-MM-dd"/></td>
					<td class="tips"><s:property value="#list.bxnr"/></td>
					<td class="tips"><s:property value="getFormatMoney(#list.bxje)"/></td>
					<td class="tips"><s:property value="#list.user.department.name"/></td>
					<td class="tips"><s:property value="#list.bxr"/></td>
					<td class="tips"><s:date name="#list.bxsj" format="yyyy-MM-dd"/></td>
					<td>
						<s:if test="#list.stat==\"0\" || #list.stat==\"1\"">
							<a class="popup" href="${ctx}/admin/expence!input.action?expence.id=<s:property value='#list.id'/>" title="编辑">[编辑]</a>
							<a class="popupDelete" href="${ctx}/admin/expence!delete.action?expence.id=<s:property value='#list.id'/>" title="删除">[删除]</a>
						</s:if>
						<s:else>
							<label style="color: #666"></label>
						</s:else>
					</td>
				</tr>
				</s:iterator>
			</table>
		</div>
	</form>
	<div id="popupDialog" title="添加/编辑报销信息"></div>
	<div id="tranDialog" title="添加/编辑账内划转"></div>
    <div id="notice2" title="提示消息"></div>
</body>
</html>