<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/content/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>挂账审批</title>
		<%@ include file="/WEB-INF/content/common/include.jsp"%>
		<link type="text/css" rel="stylesheet" href="${ctx}/styles/gh-buttons.css">
		<link type="text/css" rel="stylesheet" href="${ctx}/styles/prettify.css"> 
		<script type="text/javascript" src="${ctx}/scripts/github/prettify.js"></script> 
		<script type="text/javascript">
		<s:if test="page.result.size() <= 0">
    		$("#deleteButton").attr("disabled",true).css("cursor","default");
   		</s:if>
		$(function() {
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
			
			$(".popupDelete").bind('click',function(){
				if(window.confirm('你确定要删除信息吗？')){
					$.ajax({
						type: "POST",
						cache: false,
						url: $(this).attr("url"),
						success: function(msg){
							isSuccess= (trim(msg)=="您的操作已成功!")?true :false;
							$("#notice").html(msg);
							$("#notice").dialog('open');
							$(".ui-widget-overlay").css("height", document.body.scrollHeight);
						}
					});
				}
				return false;
			});
			
			//提交按扭
			$("#addButton").bind('click',function(){
				var pId = [];
				$(".list #listTable input[name='ids']:checked").each(function (index, doEle){
					pId[index] = $(this).attr("coopId");
				});
				if(pId.length > 0){
					pId = pId.sort();
					for(var i = 0; i < pId.length - 1; i++) {
					   if (pId[i] != pId[i+1]) {
					       alert("请选择同一家供应商进行付款款申请审核操作！");
					       return false;
					   }
					}
				}
			
				var X = $(this).offset().top; 
				var Y = $(this).offset().left;
				var $idsCheckedCheck = $(".list #listTable input[name='ids']:checked").serialize();
				if($idsCheckedCheck != "" && $idsCheckedCheck.indexOf("ids") > -1){
					$.ajax({
						type: "POST",
						cache: false,
						url: $(this).attr("url"),
						data: $idsCheckedCheck,
						success: function(msg){
							$("#popupDialog").dialog({dialogClass:"ppisDialog",width:450});
							$("#popupDialog").dialog({position:[X+18, Y+15]});
							$("#popupDialog").html(msg).dialog("open");
							$(".ui-widget-overlay").css("height",document.body.scrollHeight);
						}
					});
				}else{
					alert("请勾选不能为空！");
				}
			});
			
			$( "#coopeDialog" ).dialog({
				dialogClass:"ppisDialog",
				autoOpen: false,
				resizable: false,
				width:500,
				maxHeight: window.screen.height-250,
				modal: true,
				close: function() {
					$("#coopeDialog").dialog("close");
				}
			});
			
			$("#listTable tr").hover(function(){
				$(this).find("span.td1").hide();
				$(this).find("span.td2").show();
			},function(){
				$(this).find("span.td1").show();
				$(this).find("span.td2").hide();
			})
			
			//供应商，搜索
			$( "#cooperator_name_span" ).click(function() {
				$.ajax({
					type: "POST",
					cache: false,
					url: "${ctx}/admin/cooperator!dialog.action",
					success: function(msg){
						$("#coopeDialog").html(msg).dialog("open");
						$(".ui-widget-overlay").css("height",document.body.scrollHeight);
						cooperatorTrClick();
					}
				});
				return false;
			});
		})
		
		$(document).ready(function(e) {
			checkboxLock();
			
			$("#listForm input[name='jqchecked']").click( function() {
	        	if ($(this).attr("checked") == "checked") {
	        		$("#account_jqchecked").val("1");
	        	}else{
	        		$("#account_jqchecked").val("0");
	        	}
	        });
			
			if($("#account_jqchecked").val() == "1"){
				$("#listForm input[name='jqchecked']").attr("checked", true);
			}
		})
		
		//锁定提交
		function checkboxLock(){
			var stat = $("#account_stat").val();
			if(stat == '1'){
				$("#listTable input[name='ids']:enabled").attr("disabled", false);
				$(".allCheck").attr("disabled", false);
				$("#addButton").attr("disabled", false);
				$("#addButton").removeClass("pub_but_disable").addClass("pub_but");
			}else{
				$("#listTable input[name='ids']:enabled").attr("disabled", true);
				$(".allCheck").attr("disabled", true);
				$("#addButton").attr("disabled", true);
				$("#addButton").removeClass("pub_but").addClass("pub_but_disable");
			}
		}
		//2.双击单行进行审核
        function audit(buessinID){
			if(buessinID != ""){
			  	url = "${ctx}/admin/business!order.action?buessinID=" + buessinID;
			    showDialog(url,"","","");
		  	}
		}
		
        function cooperatorTrClick(){
        	$("#coopeDialog table:eq(1) tr:gt(0)").click(function () {
        		//$("#listForm #cooperator_id").val($(this).find("td").eq(0).html());
        		$("#listForm #treeSel").val($(this).find("td").eq(2).html());
        		$("#coopeDialog").dialog("close");
        	})
        }
        
	</script>
	</head>
	<body class="list con_r">
		<div id="menuContent" class="menuContent">
            <div id="tabs" class="ppisDialog" style="width:310px;">
                <ul></ul>
                <div id="tabs-1" >
                    <ul id="selectTree" class="ztree" style="margin-top:0; width:300px;"></ul>
                </div>
            </div>
		</div>
		<form id="listForm" action="${ctx}/admin/account!onPaymentList.action" method="post">
    	<div class="pub_inp_bg"><div class="pub_inp"><div>
            <table class="con_table">
                <tr>
					<td align="right" width="70">线路:</td>
					<td align="left" width="120">
						<s:select name="account.business.ddlx" list="DdlxMap" listKey="key" listValue="%{value}" headerKey="" headerValue="请选择" rule="noempty" style="width:100px;"></s:select>
					</td>
                	<td align="right" width="70">
						出发日期:
					</td>
					<td align="left" width="220">
						<input type="text" class="Wdate" name="account.beginDate" id="bd" size="12" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'ed\');}',startDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd',readOnly:true})" readonly="true" class="Wdate" style="width:90px;text-indent:4px" value="${account.beginDate}" />
						至
						<input type="text" class="Wdate" name="account.endDate" id="ed" size="12" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'bd\');}',startDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd',readOnly:true})" readonly="true" class="Wdate" style="width:90px;text-indent:4px" value="${account.endDate}"/>
					</td>
                	<td align="right" width="70">订单编号:</td>
					<td align="left" width="135"><s:textfield name="account.orderId" cssClass="pub_input" style="width:120px"/></td>
					<td align="right" width="80">业务状态：</td>
					<td align="left" width="80">
						<s:select name="account.stat" list="#{'1':'未审','2':'经理','9':'回退'}" style="width:80px"></s:select>
					</td>
					<td align="right" width="80">采购名称：</td>
                    <td align="left" width="250">
						<input type="text" name="account.cooperator.dwmc" id="treeSel" class="ppisDialog ui-multiselect ui-widget ui-state-default ui-corner-all pub_input" style="height: 20px; line-height: 20px; width: 200px;" value="${account.cooperator.dwmc}"></input>
                    	<span id="cooperator_name_span" style="margin-left:5px; line-height:25px; font-weight:bold; cursor:pointer; text-decoration:underline; font-size: 12px; color:red; "> 搜索 </span>
		                <!-- s:hidden name="cooperator.id"/> -->
                    </td>
                	<td align="right" width="70">精确查询:</td>
					<td align="left" width="20"><input type="checkbox" name="jqchecked""/><s:hidden name="account.jqchecked" /></td>
				</tr>
            </table>
        </div></div></div>
        <div class="buttonArea_l">
            <input type="button" id="searchButton" class="pub_but searchButton formButton" value="查找" />
        	<input type="button" id="addButton" class="pub_but" url="${ctx}/admin/account!paymentInput.action" value="经理审核"/>
        	<input type="button" id="deleteButton" value="不能删" style="display:none"/>
        </div>
        <div class="datagrid">
			<table id="listTable" class="listTable" cellpadding="0" cellspacing="0">
               	<colgroup>
                    <col width="2%"/>
                    <col width="3%"/>
                    <col width="3%"/>
                    <col width="8%"/>
                    <col width=""/>
                    <col width="7%"/>
                    <col width="7%"/>
                    <col width="7%"/>
                    <col width="7%"/>
                    <col width="7%"/>
                    <col width="7%"/>
                    <col width="7%"/>
                    <col width="7%"/>
                    <col width="1%"/>
                </colgroup>
				<tr>
					<th class="check"><input type="checkbox" id="checkbox" name="checkbox" class="allCheck"/></th>
					<th><span>状态</span></th>
					<th><span>线路</span></th>
					<th><span>订单号</span></th>
					<th><span>单位名称</span></th>
					<th><span class="fr">金额</span></th>
					<th><span class="fr">应收</span></th>
					<th><span class="fr">应付</span></th>
					<th><span class="fr">已收</span></th>
					<th><span class="fr">未收</span></th>
					<th><span class="fr">利润</span></th>
					<th><span class="fr">操作人</span></th>
					<th><span class="fr">提交日期</span></th>
					<th></th>
				</tr>
				<s:iterator value="page.result" var="list" status="status">
					<tr ondblclick="audit('<s:property value="#list[11]"/>');" <s:if test="#status.count%2==1">class="cor"</s:if>>
						<td class="tips"><input type="checkbox" name="ids" value="<s:property value="#list[0]"/>" coopid="<s:property value="#list[17]"/>" /></td>
						<td class="tips">
							<span class="td1"><s:property value="accountStatMap.get(#list[10])"/></span>
							<span class="td2 blue" onclick="audit('<s:property value="#list[11]"/>');" style="display:none;"><a href="#button" class="button danger">打开</a></span>
						</td>
						<td class="tips"><s:property value="ddlxMap.get(#list[18])"/></td>
						<td class="tips red"><s:property value="#list[2]"/></td>
						<td class="tips" title="<s:property value="#list[3]"/>"><s:property value="#list[3]"/></td>
						<td class="tips tar green"><s:property value="getFormatMoney(#list[8])"/></td>
						<td class="tips tar red"><s:property value="getFormatMoney(#list[12])"/></td>
						<td class="tips tar green"><s:property value="getFormatMoney(#list[13])"/></td>
						<td class="tips tar red"><s:property value="getFormatMoney(#list[14])"/></td>  <!-- 已收 -->
						<td class="tips tar green"><s:property value="getFormatMoney(#list[15])"/></td><!-- 未收 -->
						<td class="tips tar blue"><s:property value="getFormatMoney(#list[16])"/></td>	
						<td class="tips tar"><s:property value="#list[6]"/></td>
						<td class="tips tar"><s:date name="#list[5]" format="yyyy-MM-dd"/></td>
						<td class="tips tar">&nbsp;</td>
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
        <div id="popupDialog" title="挂账提交"></div>
        <div id="coopeDialog" title="采购单位"></div>
        <div id="notice" title="提示消息"></div>
	</body>
</html>