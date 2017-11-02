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
		<s:if test="page.result.size() <= 0">
    		$("#deleteButton").attr("disabled",true).css("cursor","default");
   		</s:if>
		$(function() {
			//提醒
			$("#notice1").dialog({
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
			//提交按扭
			$("#addButton").bind('click',function(){
				var pId = [];
				$(".list #listTable input[name='ids']:checked").each(function (index, doEle){
					pId[index] = $(this).attr("parentId");
				});
				if(pId.length > 0){
					pId = pId.sort();
					for(var i = 0; i < pId.length - 1; i++) {
					   if (pId[i] != pId[i+1]) {
					       alert("请选择同一家组团社的收款项目！");
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
							$("#popupDialog").dialog({position:[X+20, Y+10]});
							$("#popupDialog").html(msg).dialog("open");
							$(".ui-widget-overlay").css("height",document.body.scrollHeight);
						}
					});
				}else{
					alert("请勾选不能为空！");
				}
			});
			
			//2015.11.12  修改为单笔提交
			$(".sktjbutton").bind('click',function(){
				var X = $(this).offset().top; 
				var Y = $(this).offset().left;
				$.ajax({
					type: "POST",
					cache: false,
					url: $(this).attr("href"),
					success: function(msg){
						$("#popupDialog").dialog({dialogClass:"ppisDialog",width:450});
						$("#popupDialog").dialog({position:[Y-500, X]});
						$("#popupDialog").html(msg).dialog("open");
						$(".ui-widget-overlay").css("height",document.body.scrollHeight);
					}
				});
				return false;
			});
			
			//招回
			$(".recallbutton").bind('click', function(){
				$.ajax({
					url: $(this).attr("href"),
					type: "POST",
					dataType: "json",
					async: true,
					success: function(data){
						isSuccess= (trim(data.status)=="success")?true :false;
		                $("#notice1").html(data.message);
		                $("#notice1").dialog('open');
		                $(".ui-widget-overlay").css("height", document.body.scrollHeight);
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
		})
		
		//2.双击单行进行审核
        function audit(buessinID){
			if(buessinID != ""){
			  	url = "${ctx}/admin/business!order.action?buessinID=" + buessinID;
			    showDialog(url,"","","");
		  	}
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
		<form id="listForm" action="${ctx}/admin/account!onReceiptsList.action" method="post">
    	<div class="pub_inp_bg"><div class="pub_inp"><div>
            <table class="con_table">
                <tr>
					<td align="right" width="80">出发日期：</td>
                	<td align="left" width="240">
						<input type="text" class="Wdate" name="account.beginDate" id="bd" size="12" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'ed\');}',startDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd',readOnly:true})" readonly="true" class="Wdate" style="width:100px;text-indent:4px" value="${account.beginDate}" />
						至
						<input type="text" class="Wdate" name="account.endDate" id="ed" size="12" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'bd\');}',startDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd',readOnly:true})" readonly="true" class="Wdate" style="width:100px;text-indent:4px"  value="${account.endDate}"/>
					</td>
					<td align="right" width="80">订单状态：</td>
					<td align="left" width="135">
						<s:select name="account.business.ddzt" list="#{'1':'已确认','0':'待确认'}" style="width:100px;"/>
					</td>
					<td align="right" width="80">业务状态：</td>
					<td align="left" width="120">
						<s:select name="account.stat" list="accountStatMap" listKey="key" listValue="%{value}" headerKey="" headerValue="查看全部" style="width:100px"></s:select>
					</td>
					<td align="right" width="80">客户名称：</td>
                    <td align="left" width="300">
						<input type="text" name="account.businessCustomer.customerDwmc" id="treeSel" onclick="showMenu();" class="ppisDialog ui-multiselect ui-widget ui-state-default ui-corner-all" style="width: 300px;" value="${account.businessCustomer.customerDwmc}"></input>
                    </td>
                </tr>
            </table>
        </div></div></div>
        <div class="buttonArea_l">
            <input type="button" id="searchButton" class="pub_but searchButton formButton" value="查找" />
        	<input type="button" id="addButton" class="pub_but" url="${ctx}/admin/account!input.action" value="提交" style="display:none"/>
        	<input type="button" id="deleteButton" value="不能删" style="display:none"/>
        </div>
        <div class="datagrid">
			<table id="listTable" class="listTable" cellpadding="0" cellspacing="0">
               	<colgroup>
                    <col width="3%"/>
                    <col width="3%"/>
                    <col width="3%"/>
                    <col width="8%"/>
                    <col width="18%"/>
                    <col width="30%"/>
                    <col width="4%"/>
                    <col width="6%"/>
                    <col width="6%"/>
                    <col width="6%"/>
                    <col width="6%"/>
                    <col width="5%"/>
                </colgroup>
				<tr>
					<!-- <th class="check"><input type="checkbox" id="checkbox" name="checkbox" class="allCheck"/></th> -->
					<th><span>状态</span></th>
					<th><span>类型</span></th>
					<th><span>方式</span></th>
					<th><span>订单号</span></th>
					<th><span>客户名称</span></th>
					<th><span>旅行线路</span></th>
					<th><span>操作人</span></th>
					<th><span class="fr">应收</span></th>
					<th><span class="fr">已收</span></th>
					<th><span class="fr">未收</span></th>
					<th><span class="fr">金额</span></th>
					<th><span>操作</span></th>
				</tr>
				<s:iterator value="page.result" var="list" status="status">
					<tr ondblclick="audit('<s:property value="#list[11]"/>');" <s:if test="#status.count%2==1">class="cor"</s:if>>
						<!--  <td class="tips"><input type="checkbox" name="ids" value="<s:property value="#list[0]"/>" parentid="<s:property value="#list[12]"/>"/></td> -->
						<td class="tips">
							<span class="td1"><s:property value="accountStatMap.get(#list[10])"/></span>
							<span class="td2 blue" onclick="audit('<s:property value="#list[11]"/>');" style="display:none;"><a href="#button" class="button danger">打开</a></span>
						</td>
						<td class="tips"><s:property value="sflxMap.get(#list[1])"/></td>
						<td class="tips"><s:property value="sffsMap.get(#list[9])"/></td>
						<td class="tips red"><s:property value="#list[2]"/></td>
						<td class="tips" title="<s:property value="#list[3]"/>"><s:property value="#list[3]"/></td>
						<td class="tips"><s:property value="#list[4]"/></td>
						<td class="tips"><s:property value="#list[6]"/></td>
						<td class="tips tar red"><s:property value="getFormatMoney(#list[13])"/></td>
						<td class="tips tar red"><s:property value="getFormatMoney(#list[14])"/></td>
						<td class="tips tar green"><s:property value="getFormatMoney(#list[15])"/></td>
						<td class="tips tar red"><s:property value="getFormatMoney(#list[7])"/></td>
						<td class="tips">
							<s:if test="#list[10] in {\"0\",\"8\",\"9\"}">
								<a class="popup sktjbutton" href="${ctx}/admin/account!receiptsInput.action?account.id=<s:property value='#list[0]'/>" title="">[提交]</a>
							</s:if>
							<s:elseif test="#list[10] in {\"1\"}">
								<a class="recallbutton" href="${ctx}/admin/account!ajaxAccountStatRecall.action?ids=<s:property value='#list[0]'/>" title="">[招回]</a>
							</s:elseif>
							<s:else>
								
							</s:else>
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
        <div id="popupDialog" title="挂账提交"></div>
        <div id="notice1" title="提示消息"></div>
        <script type="text/javascript" src="${ctx}/scripts/address_tree.js"></script>
	</body>
</html>