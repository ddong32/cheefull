<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/content/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>收款财务列表</title>
		<%@ include file="/WEB-INF/content/common/include.jsp"%>
		<link type="text/css" rel="stylesheet" href="${ctx}/styles/gh-buttons.css">
		<link type="text/css" rel="stylesheet" href="${ctx}/styles/prettify.css"> 
		<script type="text/javascript" src="${ctx}/scripts/github/prettify.js"></script> 
		
		<script type="text/javascript">
		
		$(document).ready(function() {
			$("#notice").dialog({
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
				resizable: true,
				width:500,
				maxHeight: window.screen.height-250,
				modal: true
			});
		
			//单击按钮进行审核
			$("#auditButton").click(function() {
				$("#pageNo").val(1);
				if (!checkRule()){
					return false;
				}
				//listForm.submit();
				audit();
			});
			
			$("#listTable tr").hover(function(){
				$(this).find("span.td1").hide();
				$(this).find("span.td2").show();
			},function(){
				$(this).find("span.td1").show();
				$(this).find("span.td2").hide();
			})
		});
		
		//2.双击单行进行审核
        function audit(id){
        	var url = "${ctx}/admin/account_flow!audit.action?type=takeFinaneChecked";
        	if( typeof(id) != "undefined" && id != null && id != ""){
        		url = url + "&id=" + id
        	}
        	if( $("#bd").val() != null && $("#bd").val() != "" ){
        		url = url + "&accountFlow.beginDate=" + $("#bd").val()
        	}
        	if( $("#ed").val() != null && $("#ed").val() != "" ){
        		url = url + "&accountFlow.endDate=" + $("#ed").val()
        	}
			if( $("#accountFlow_bank_id").val() != null && $("#accountFlow_bank_id").val() != "" ){
        		url = url + "&accountFlow.bank.id=" + $("#accountFlow_bank_id").val()
        	}
        	if( $("#accountFlow_stat").val() != null && $("#accountFlow_stat").val() != "" ){
        		url = url + "&accountFlow.stat=" + $("#accountFlow_stat").val()
        	}
        	if( $("#accountFlow_orderId").val() != null && $("#accountFlow_orderId").val() != "" ){
        		url = url + "&accountFlow.orderId=" + $("#accountFlow_orderId").val()
        	}
        	url = url + getActionAddParam("&");
       	  	showDialog(url,"","","");
        }
        
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
	</script>
	</head>
	<body class="list con_r">
		<form id="listForm" action="${ctx}/admin/account_flow!takeFinaneChecked.action" method="post">
    	<div class="pub_inp_bg"><div class="pub_inp">
			<table class="con_table">
				<tr>
					<td align="right" width="80">业务状态：</td>
					<td align="left" width="120">
						<s:select name="accountFlow.stat" list="#{'1':'未审','3':'已审','9':'回退'}" headerKey="" headerValue="未选择" style="width:100px"></s:select>
					</td>
					<td align="right" width="80">收款日期：</td>
					<td align="left" width="250">
						<input type="text" class="Wdate" name="accountFlow.beginDate" id="bd" size="12" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'ed\');}',startDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd',readOnly:true})" readonly="true" class="Wdate" style="width:90px;text-indent:4px" value="${accountFlow.beginDate}" />
						至
						<input type="text" class="Wdate" name="accountFlow.endDate" id="ed" size="12" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'bd\');}',startDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd',readOnly:true})" readonly="true" class="Wdate" style="width:90px;text-indent:4px" value="${accountFlow.endDate}"/>
					</td>
					<td align="right" width="73">收支银行：</td>
					<td align="left" width="135">
						<s:select name="accountFlow.bank.id" list="bankMap" listKey="key" listValue="%{value}" headerKey="" headerValue="请选择"></s:select>
					</td>
					<td align="right" width="73">订单编号:</td>
					<td align="left" width="135"><s:textfield name="accountFlow.orderId" cssClass="pub_input" /></td>
				</tr>
			</table>
        </div></div>
        <div class="buttonArea_l">
            <input type="button" id="searchButton" class="pub_but searchButton formButton" value="查找" />
        	<input type="button" id="auditButton" class="pub_but" value="审核"/>&nbsp;
        </div>
        <div class="datagrid">
			<table id="listTable" class="listTable" cellpadding="0" cellspacing="0">
               	<colgroup>
               		<col width="3%"/>
					<col width="7%"/>
					<col width="5%"/>
					<col width="7%"/>
					<col width="5%"/>
					<col width="5%"/>
					<col width="1%"/>
					<col width="7%"/>
					<col width="30%"/>
				</colgroup>
				<tr>
	                <th>状态</th>
	                <th>订单编号</th>
	                <th>收款时间</th> 
	                <th>提交人</th>
	                <th>提交时间</th>
	                <th><span class="fr">收款金额</span></th>
					<th></th>
	                <th>银行</th>
	                <th>备注</th>
				</tr>
				<s:iterator value="page.result" var="list" status="status">
					<tr ondblclick="audit('<s:property value="#list[0]"/>');" <s:if test="#status.count%2==1">class="cor"</s:if>>
						<td class="tips <s:if test="#list[1] == 1">red</s:if><s:elseif test="#list[1] == 3">green</s:elseif>">
							<span class="td1"><s:property value="accountStatMap.get(#list[1])"/></span>
							<span class="td2 blue" onclick="audit('<s:property value="#list[0]"/>');" style="display:none;"><a href="#button" class="button danger">打开</a></span>
						</td>
						<!-- <td class="tips red"><s:property value="#list[2]"/></td>  -->
						<td class="tips red"><s:property value="#list[12]"/></td>
						<td class="tips"><s:date name="#list[3]" format="yyyy-MM-dd"/></td>
						<td class="tips"><s:property value="#list[4]"/></td>
						<td class="tips"><s:date name="#list[5]" format="yyyy-MM-dd"/></td>
						<td class="tips red tar"><s:property value="getFormatMoney(#list[6])"/></td>
						<td class="tips">&nbsp;</td>
						<td class="tips"><s:property value="#list[8]"/></td>
						<td class="tips"><s:property value="#list[11]"/></td>
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
        <div id="popupDialog" title="新增订单"></div>
        <div id="notice" title="提示消息"></div>
	</body>
</html>