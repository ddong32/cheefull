<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/content/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>订单列表</title>
		<%@ include file="/WEB-INF/content/common/include.jsp"%>
		<link type="text/css" rel="stylesheet" href="${ctx}/styles/gh-buttons.css">
	    <link type="text/css" rel="stylesheet" href="${ctx}/styles/prettify.css"> 
	    <script type="text/javascript" src="${ctx}/scripts/github/prettify.js"></script> 
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
				resizable: true,
				width:500,
				maxHeight: window.screen.height-250,
				modal: true
			});
			
			$("#exportButton").click(function(){
				$("#totalForm #business_beginDate").val($("#listForm #bd").val());
	        	$("#totalForm #business_endDate").val($("#listForm #ed").val());
	        	$("#totalForm #business_ddzt").val($("#listForm #business_ddzt").val());
	        	$("#totalForm #business_orderId").val($("#listForm #business_orderId").val());
	        	var userids = $("#listForm #_usersId").val();
	        	if(userids instanceof Array){
	        		$("#totalForm #business_userids").val(userids.join(","));
	        	}
				var oldUrl = document.forms[1].action;
       		  	document.forms[1].action = "${ctx}/admin/business!exportBusinessList.action";
			    document.forms[1].submit();
			  	document.forms[1].action = oldUrl;
			});
			
            $("#addButton").bind('click',function(){
            	var X = $(this).offset().top; 
				var Y = $(this).offset().left;
				$.ajax({
					type: "POST",
					cache: false,
					url: $(this).attr("href"),
					success: function(msg){
						$("#popupDialog").dialog({dialogClass:"ppisDialog",width:600});
						$("#popupDialog").dialog({position:[X+20, Y+10]});
						$("#popupDialog").html(msg).dialog("open");
						$(".ui-widget-overlay").css("height",document.body.scrollHeight);
					}
				});
				return false;
			});
			

		    $("#listTable tr").hover(function(){
		    	$(this).find("span.td1").hide();
		    	$(this).find("span.td2").show();
		    },function(){
		    	$(this).find("span.td1").show();
		    	$(this).find("span.td2").hide();
		    })
			
			$("#_usersId").multiselect({buttonWidth: 155, minWidth:155, selectedList:1, noneSelectedText:"全部", classes: 'ppisDialog'});
			total();
		});
		
		function dbClickEvent(buessinID){
        	if(buessinID != ""){
			  	url = "${ctx}/admin/business!order.action?buessinID=" + buessinID;
			    showDialog(url,"","","");
		  	}
        }

	</script>
	</head>
	<body class="con_r">
		<form id="listForm" action="${ctx}/admin/bank_transferlog!list.action" method="post">
    	<div class="pub_inp_bg"><div class="pub_inp">
			<table class="con_table">
				<tr>
					<td align="right" width="70">起止日期:</td>
					<td align="left" width="220">
						<input type="text" class="Wdate" name="bank.beginDate" id="bd" size="12" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'ed\');}',startDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd',readOnly:true})" readonly="true" class="Wdate" style="width:90px;text-indent:4px" value="${bank.beginDate}" />
						至
						<input type="text" class="Wdate" name="bank.endDate" id="ed" size="12" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'bd\');}',startDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd',readOnly:true})" readonly="true" class="Wdate" style="width:90px;text-indent:4px" value="${bank.endDate}"/>
					</td>
					<td align="right" width="90">划出账号:</td>
					<td align="left" width="90">
						<s:select id="account_bank_id" list="bankMap" listKey="key" listValue="%{value}" headerKey="" headerValue="请选择"></s:select>
					</td>
					<td align="right" width="70">划入账号:</td>
					<td align="left" width="90">
						<s:select id="account_bank_id" list="bankMap" listKey="key" listValue="%{value}" headerKey="" headerValue="请选择"></s:select>
					</td>
					<td align="right" width="70">金额:</td>
					<td align="left" width="220">
						<s:textfield name="bank.orderId" cssClass="pub_input w90" maxlength="12"/> - 
						<s:textfield name="bank.orderId" cssClass="pub_input w90" maxlength="12"/>
					</td>
					<td align="right" width="70">类型:</td>
					<td align="left" width="280">
						<s:select name="business.sdate" list="#{'1':'支出','2':'收入'}" headerKey="" headerValue="请选择"/>
					</td>
				</tr>
			</table>
        </div></div>
        <div class="buttonArea_l">
            <input type="button" id="searchButton" class="pub_but searchButton formButton" value="查找" />
        </div>
        <div class="datagrid">
			<table id="listTable" class="listTable" cellpadding="0" cellspacing="0">
               	<colgroup>
					<col width="13%"/>
					<col width="13%"/>
					<col width="13%"/>
					<col width="13%"/>
					<col width="13%"/>
					<col width="13%"/>
					<col width="13%"/>
				</colgroup>
				<tr>
					<th><span>操作日期</span></th>
					<th><span>转出账户</span></th>
					<th><span>转出</span></th>
					<th><span>转出账户余额</span></th>
					<th><span>转入账户</span></th>
					<th><span>转入账户余额</span></th>
					<th><span>操作人</span></th>
					<th></th>
					<!-- <th><span>客|收|支</span></th> -->
				</tr>
				<s:iterator value="page.result" var="list" status="status">
				<tr id="" <s:if test="#status.count%2==1">class="cor"</s:if>>
					<td class="tips"><s:date name="#list[0]" format="yyyy-MM-dd HH:mm:ss"/></td>
					<td class="tips"><s:property value="bankMap.get(#list[1])"/></td>
					<td class="tips"><s:property value="#list[2]"/></td>
					<td class="tips"><s:property value="#list[3]"/></td>
					<td class="tips"><s:property value="bankMap.get(#list[4])"/></td>
					<td class="tips"><s:property value="#list[5]"/></td>
					<td class="tips"><s:property value="#list[6]"/></td>
					<td class="tips">&nbsp;</td>
				</tr>
				</s:iterator>
                <s:if test="page.result.size() > 0">
                    <tr>
                        <td colspan="8" class="tips ">
                        <div class="pagerBar clear">
                            <div class="pager pub_l">
                                <%@ include file="/WEB-INF/content/common/page.jsp"%>
                            </div>
                            <div class="bar">
				 总数<s:property value="page.totalCount"/>条/第<s:property value="page.pageNo"/>页/共<s:property value="page.totalPages"/>页
                            </div>
                        </div>
                        </td>
                    </tr>
                </s:if>
                <s:else>
                    <tr colspan="8" ><div class="noRecord">没有找到任何记录!</div></tr>
                </s:else>
			</table>
		</div>
        </form>

        <div id="popupDialog" title="新增订单"></div>
        <div id="notice2" title="提示消息"></div>
	</body>
</html>