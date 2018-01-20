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
			width:600,
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
		
		var setting = {
			data: {
				simpleData: {
					enable: true
				}
			}
		};

	});
	</script>
</HEAD>
<body class="con_r">
    <form id="listForm" action="${ctx}/admin/bank!list.action" method="post">
    	<div class="pub_inp_bg"><div class="pub_inp">
			<table class="con_table">
				<tr>
					<td align="right" width="73">银行卡号:</td>
					<td align="left" width="135"><s:textfield name="bank.code" cssClass="pub_input" /></td>
				</tr>
			</table>
        </div></div>
        <div class="buttonArea_l">
            <input type="button" id="searchButton" class="pub_but searchButton formButton" value="查找" />
            <security:authorize ifAnyGranted="ROLE_ADMIN,ROLE_MANAGER">
            	<input type="button" id="addButton" class="pub_but formButton" href="${ctx}/admin/bank!input.action" value="添加" />
            </security:authorize> 
            <input type="button" id="transferButton" class="pub_but formButton" href="${ctx}/admin/bank!transferinput.action" value="划转" />  &nbsp;
        </div>
    	<div class="datagrid">
			<table id="listTable" class="listTable" cellpadding="0" cellspacing="0">
               	<colgroup>
            		<col width="7%"/>
            		<col width="5%"/>
            		<col width="18%"/>
            		<col width="14%"/>
                    <col width="16%"/>
                    <col width="16%"/>
                    <col width="16%"/>
                    <col width="8%"/>
                </colgroup>
				<tr>
					<th>登记名</th>
					<th>户名</th>
					<th>开户行</th>
					<th>卡号</th>
					<th class="tar" style="text-align: right">系统余额</th>
					<th class="tar" style="text-align: right">银行余额</th>
					<th class="tar" style="text-align: right">未审余额</th>
					<th>&nbsp;</th>
				</tr>
				<s:iterator value="resultMap['list']" var="list" status="status">
					<tr <s:if test="#status.count%2==1">class="cor"</s:if>>
						<td class="tips" title=""><s:property value="#list['djm']"/></td>
						<td class="tips" title=""><s:property value="#list['name']"/></td>
						<td class="tips" title=""><s:property value="#list['khh']"/></td>
						<td class="tips" title=""><s:property value="#list['code']"/></td>
						<td class="tips tar green" title=""><s:property value="getFormatMoney(#list['xtje'])"/></td>
						<td class="tips tar blue" title=""><s:property value="getFormatMoney(#list['yhje'])"/></td>
						<td class="tips tar red" title=""><s:property value="getFormatMoney(#list['wsje'])"/></td>
						<td>
							<security:authorize ifAnyGranted="ROLE_ADMIN,ROLE_MANAGER">
							<a class="popup" href="${ctx}/admin/bank!input.action?bank.id=<s:property value='#list[\'id\']'/>" title="编辑">[编辑]</a>
							</security:authorize>
						</td>
					</tr>
				</s:iterator>
				<tr>
                    <td class="tips ">&nbsp;</td>
                    <td class="tips ">&nbsp;</td>
                    <td class="tips ">&nbsp;</td>
                    <td class="tips" >总计：</td>
                    <td class="tips tar green" id="_total"><s:property value="getFormatMoney(resultMap['sumXtje'])"/></td>
                    <td class="tips tar blue" id="_total"><s:property value="getFormatMoney(resultMap['sumYhje'])"/></td>
                    <td class="tips tar red" id="_total"><s:property value="getFormatMoney(resultMap['sumWsje'])"/></td>
                    <td></td>
                </tr>
			</table>
		</div>
	</form>
	<div id="popupDialog" title="添加/编辑银行账户"></div>
	<div id="tranDialog" title="添加/编辑账内划转"></div>
    <div id="notice2" title="提示消息"></div>
</body>
</html>