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
			
			$("#listForm input[name='wskchecked']").click( function() {
	        	if ($(this).attr("checked") == "checked") {
	        		$("#business_wskchecked").val("1");
	        	}else{
	        		$("#business_wskchecked").val("0");
	        	}
	        });
			
			if($("#business_wskchecked").val() == "1"){
				$("#listForm input[name='wskchecked']").attr("checked", true);
			}
			
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
        
		function total(){
			$("#totalForm #business_beginDate").val($("#listForm #bd").val());
        	$("#totalForm #business_endDate").val($("#listForm #ed").val());
        	$("#totalForm #business_ddzt").val($("#listForm #business_ddzt").val());
        	$("#totalForm #business_orderId").val($("#listForm #business_orderId").val());
        	$("#totalForm #business_wskchecked").val($("#listForm #business_wskchecked").val());
        	$("#totalForm #business_ddlx").val($("#listForm #business_ddlx").val());
        	var userids = $("#listForm #_usersId").val();
        	if(userids instanceof Array){
        		$("#totalForm #business_userids").val(userids.join(","));
        	}
        	var param=getParam("totalForm");
			$.ajax({
				url : "${ctx}/admin/business!ajaxListTotal.action",
				type : "POST",
				data: param,
				success : function(result) {
					setBean(result);
				}
			});
			return false;
        }
        
        //设置
        function setBean(msg) {
        	if(msg == "") return false;
        	try{
				var jsonObj = jQuery.parseJSON(msg)[0];
				if(jsonObj.error == ""){
					$("#_peopleNum").text(jsonObj.adultNo+"|"+jsonObj.childNo+"|"+jsonObj.escortNo);
					$("#_ygs").text(jsonObj.ygs);
					$("#_ygf").text(jsonObj.ygf);
					$("#_yjs").text(jsonObj.yjs);
					$("#_yjf").text(jsonObj.yjf);
					$("#_wsk").text(jsonObj.wsk);
					$("#_lr").text(jsonObj.lr);
				}else{
					alert("[setBean] error:"+jsonObj.error);
				}
			}catch(e){
				alert("[setBean] "+e.message);
				return;
			}
        }
        
	</script>
	</head>
	<body class="con_r">
		<form id="listForm" action="${ctx}/admin/business!list.action" method="post">
    	<div class="pub_inp_bg"><div class="pub_inp">
			<table class="con_table">
				<tr>
					<td align="right" width="80">
						出发日期:
						<s:hidden name="business.sdate" id="business_sdate_1" value="1"/>
						<%-- s:select name="business.sdate" list="#{'1':'出发日期','2':'录入日期'}" value="1" style="display:none"/  --%>
					</td>
					<td align="left" width="220">
						<input type="text" class="Wdate" name="business.beginDate" id="bd" size="12" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'ed\');}',startDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd',readOnly:true})" readonly="true" style="width:90px;text-indent:4px" value="${business.beginDate}" />
						至
						<input type="text" class="Wdate" name="business.endDate" id="ed" size="12" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'bd\');}',startDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd',readOnly:true})" readonly="true" style="width:90px;text-indent:4px" value="${business.endDate}"/>
					</td>
					<td align="right" width="80">线路分类：</td>
					<td align="left" width="60">
				      	<s:select name="business.ddlx" list="ddlxMap" listKey="key" listValue="%{value}" headerKey="" headerValue="请选择" ></s:select>
					</td>
					<td align="right" width="50">状态:</td>
					<td align="left" width="90">
						<s:select name="business.ddzt" list="#{'0':'待确认','1':'已确认'}" style="width:80px;" headerKey="" headerValue="未选择"/>
					</td>
					<td align="right" width="70">操作人:</td>
					<td align="left" width="120"><s:select cssClass="multiselect" multiple="true" name="business.proUserIds" id="_usersId" list="userList" listKey="id" listValue="name"/></td>
					<td align="right" width="70">订单编号:</td>
					<td align="left" width="100"><s:textfield name="business.orderId" cssClass="pub_input w90" maxlength="12"/></td>
					<td align="right" width="90">只查未收款:</td>
					<td align="left" width="30"><input type="checkbox" name="wskchecked""/><s:hidden name="business.wskchecked" /></td>
				</tr>
			</table>
        </div></div>
        <div class="buttonArea_l">
            <input type="button" id="searchButton" class="pub_but searchButton formButton" value="查找" />
            <input type="button" id="addButton" class="pub_but formButton" href="${ctx}/admin/business!input.action" value="添加" />
            <input type="button" id="exportButton" class="pub_but exportButton formButton" value="Excel导出" />&nbsp;
        </div>
        <div class="datagrid">
			<table id="listTable" class="listTable" cellpadding="0" cellspacing="0">
               	<colgroup>
					<col width="5%"/>
					<col width="9%"/>
					<col width="5%"/>
					<col width="22%"/>
					<col width="4%"/>
					<col width="7%"/>
					<col width="8%"/>
					<col width="8%"/>
					<col width="8%"/>
					<col width="8%"/>
					<col width="8%"/>
					<col width="8%"/>
					<col width="1%"/>
				</colgroup>
				<tr>
					<th><span>状态</span></th>
					<th><span>订单号</span></th>
					<th><span>类型</span></th>
					<th><span>线路名称</span></th>
					<th><span>操作人</span></th>
					<th><span class="fr">人数</span></th>
					<th><span class="fr">应收</span></th>
					<th><span class="fr">应付</span></th>
					<th><span class="fr">已收</span></th>
					<th><span class="fr">已付</span></th>
					<th><span class="fr">未收</span></th>
					<th><span class="fr">利润</span></th>
					<th></th>
					<!-- <th><span>客|收|支</span></th> -->
				</tr>
				<s:iterator value="page.result" var="list" status="status">
				<tr id="<s:property value="#list[2]"/>" ondblclick="dbClickEvent('<s:property value="#list[0]"/>');" <s:if test="#status.count%2==1">class="cor"</s:if>>
					<td class="tips <s:if test="#list[1] == 1">red</s:if><s:else>green</s:else>">
						<span class="td1"><s:property value="ddztMap.get(#list[1])"/></span>
						<span class="td2 blue" onclick="dbClickEvent('<s:property value="#list[0]"/>');" style="display:none;"><a href="#button" class="button danger">打开</a></span>
					</td>
					<td class="tips red"><s:property value="#list[2]"/></td>
					<td class="tips"><s:property value="ywlxMap.get(#list[3])"/></td>
					<td class="tips" nowrap title="<s:property value="#list[4]"/>"><s:property value="#list[4]"/></td>
					<td class="tips"><s:property value="#list[6]"/></td>
					<td class="tips tar"><s:property value="#list[7]"/>|<s:property value="#list[8]"/>|<s:property value="#list[9]"/></td>
					<td class="tips red tar"><s:property value="getFormatMoney(#list[10])"/></td>
					<td class="tips green tar"><s:property value="getFormatMoney(#list[11])"/></td>
					<td class="tips red tar"><s:property value="getFormatMoney(#list[13])"/></td>
					<td class="tips blue tar"><s:property value="getFormatMoney(#list[15])"/></td>
					<td class="tips green tar"><s:property value="getFormatMoney(#list[14])"/></td>
					<td class="tips blue tar"><span><s:property value="getFormatMoney(#list[12])"/></span></td>
					<!-- td class="tips">3|5/8|4/5</td> -->
					<td class="tips">&nbsp;</td>
				</tr>
				</s:iterator>
                <s:if test="page.result.size() > 0">
                    <tr>
                        <td colspan="5" class="tips ">
                        <div class="pagerBar clear">
                            <div class="pager pub_l">
                                <%@ include file="/WEB-INF/content/common/page.jsp"%>
                            </div>
                            <div class="bar">
                                总数<s:property value="page.totalCount"/>条/第<s:property value="page.pageNo"/>页/共<s:property value="page.totalPages"/>页
                            </div>
                        </div>
                        </td>
                        <td class="tips tar" id="_peopleNum">&nbsp;</td>
                        <td class="tips red tar" id="_ygs">&nbsp;</td>
                        <td class="tips green tar" id="_ygf">&nbsp;</td>
                        <td class="tips red tar" id="_yjs">&nbsp;</td>
                        <td class="tips blue tar" id="_yjf">&nbsp;</td>
                        <td class="tips green tar" id="_wsk">&nbsp;</td>
                        <td class="tips blue tar" id="_lr">&nbsp;</td>
                        <td class="tips">&nbsp;</td>
                    </tr>
                </s:if>
                <s:else>
                    <tr colspan="5" ><div class="noRecord">没有找到任何记录!</div></tr>
                </s:else>
			</table>
		</div>
        </form>
        <form id="totalForm" action="" method="post">
        	<s:hidden name="business.sdate" value="1"/>
        	<s:hidden name="business.beginDate"/>
        	<s:hidden name="business.endDate" />
        	<s:hidden name="business.ddzt" />
        	<s:hidden name="business.orderId" />
        	<s:hidden name="business.wskchecked" />
        	<s:hidden name="business.userids" />
        	<s:hidden name="business.ddlx" />
        </form>
        <div id="popupDialog" title="新增订单"></div>
        <div id="notice2" title="提示消息"></div>
	</body>
</html>