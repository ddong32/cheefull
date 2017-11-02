<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/content/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>订单列表</title>
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
				resizable: true,
				width:500,
				maxHeight: window.screen.height-250,
				modal: true
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
			$("#_usersId").multiselect({buttonWidth: 155, minWidth:155, selectedList:1, noneSelectedText:"全部", classes: 'ppisDialog'});
		
				    //
		    $(".popup").live('click',function(){
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
			//
			$(".treeNode").live("click",function(){
				getChild($(this));
				$this = $(this);
				var id = $(this).attr("id");
				var $parent = $(this).parent();
				var grade = $("#tr_"+id).attr("grade");
				var minGrade = grade;
				$("#tr_"+id).nextAll("tr").each(function(){
					var tmpGrade = $(this).attr("grade");
					if (tmpGrade<grade){
						minGrade = tmpGrade;
					}
				});
				var isopen = $parent.hasClass("open");
				$("#tr_"+id).nextAll("tr").each(function(){
					var thisGrade = $(this).attr("grade");
					var node=$(this).find(".treeNode");
					if(thisGrade <=  grade) {
						return false;
					}
					if(!isopen){
						$(this).show();
						if (thisGrade!=minGrade){
							node.parent().removeClass("close").addClass("open");
							$parent.removeClass("close").addClass("open");
						}
					} else {
						$(this).hide();
						$parent.removeClass("open").addClass("close");
					}
				});
			});
		});
		
		function dbClickEvent(buessinID){
        	if(buessinID != ""){
			  	url = "${ctx}/admin/business!order.action?buessinID=" + buessinID;
			    showDialog(url,"","","");
		  	}
        }
        
        //
		function getChild($o){
			var id = $o.attr("id");
			var $parent = $o.parent();
			var showTab = $parent.find("font").attr("showTab");
			showTab = parseInt(showTab)+1;
			if($o.data("contentLoaded")!= 1 && !$o.hasClass("end")){
				$o.data("contentLoaded", 1);
				$.ajax({
					url: 'customer!ajaxChildren.action',
					data: {'customer.id': id},
					dataType: 'json',
					async: true,
					beforeSend: function() {
						$parent.removeClass("close").addClass("loading");
					},
					success: function(data) {
						var datahtml = '';
						$.each(data, function(i) {
							datahtml += '<tr id="tr_'+data[i].id+'" grade="'+data[i].grade+'">';
							var gradeW = showTab * 30 - 30;
							var hasClass = "";
							var endClass = "";
							if(data[i].hasChildren == 1){
								hasClass = "close";
							}else{
								hasClass = "open";
								endClass = "end";
							}
	
							var tmpDatahtml="";
							tmpDatahtml = htmlEncode(data[i].areaName);
							datahtml += '<td class="tips" title="'+tmpDatahtml+'" align="left"><span style="margin-left:'+gradeW+'px;" class="'+hasClass+'" ><font id="'+data[i].id+'" class="treeNode '+endClass+'" showTab="'+showTab+'">';
							datahtml += tmpDatahtml;
							datahtml += '</font></span></td>';
							
							datahtml += '<td class="tips" title="'+data[i].lxdh+'">'+data[i].lxdh+'</td>';
							datahtml += '<td class="tips" title="'+data[i].qq+'">'+data[i].qq+'</td>';
							datahtml += '<td class="tips" title="'+data[i].wxh+'">'+data[i].wxh+'</td>';
							datahtml += '<td class="tips" title="'+data[i].gsdh+'">'+data[i].gsdh+'</td>';
							datahtml += '<td>'+data[i].typeName+'</td>';
							datahtml += '<td>'+data[i].statName+'</td>';
	
							datahtml += '<td>';
							datahtml += '<a class="popup" href="customer!input.action?parent.id='+data[i].parentId+'&customer.id='+data[i].id+'" title="编辑">[编辑]</a> ';
							datahtml += '</td>';
							datahtml += '</tr>';
						});
						$("#tr_"+id).after(datahtml);
					},
					error:function()
					{
						alert('=error=');
					},
					complete:function(){
						$parent.removeClass("loading").addClass("open");
					}
				});
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
						<s:select name="business.sdate" list="#{'1':'出发日期','2':'录入日期'}" value="1" style="display:none"/>
					</td>
					<td align="left" width="220">
						<input type="text" class="Wdate" name="business.beginDate" id="bd" size="12" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'ed\');}',startDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd',readOnly:true})" readonly="true" class="Wdate" style="width:90px;text-indent:4px" value="${business.beginDate}" />
						至
						<input type="text" class="Wdate" name="business.endDate" id="ed" size="12" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'bd\');}',startDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd',readOnly:true})" readonly="true" class="Wdate" style="width:90px;text-indent:4px" value="${business.endDate}"/>
					</td>
					<td align="right" width="73">订单状态:</td>
					<td align="left" width="90">
						<s:select name="business.ddzt" list="#{'0':'待确认','1':'已确认'}" style="width:80px;" headerKey="" headerValue="未选择"/>
					</td>
					<td align="right" width="70">操作人:</td>
					<td align="left" width="120"><s:select cssClass="multiselect" multiple="true" name="business.proUserIds" id="_usersId" list="userList" listKey="id" listValue="name"/></td>
					<td align="right" width="70">订单编号:</td>
					<td align="left" width="135"><s:textfield name="business.orderId" cssClass="pub_input" /></td>
				</tr>
				<tr>
					<td align="right" width="70">采购单位:</td>
					<td align="left" width="135"><s:textfield name="customer.areaName" cssClass="pub_input"/></td>
				</tr>
			</table>
        </div></div>
        <div class="buttonArea_l">
            <input type="button" id="searchButton" class="pub_but searchButton formButton" value="查找" />
            <input type="button" id="addButton" class="pub_but formButton" href="${ctx}/admin/business!input.action" value="添加" />
        </div>
        <div class="datagrid">
			<table id="listTable" class="listTable" cellpadding="0" cellspacing="0">
               	<colgroup>
               		<col width="4%"/>
               		<col width="9%"/>
					<col width="4%"/>
					<col width="35%"/>
					<col width="7%"/>
					<col width="5%"/>
					<col width="6%"/>
					<col width="6%"/>
					<col width="6%"/>
					<col width="6%"/>
					<col width="6%"/>
					<col width="1%"/>
				</colgroup>
				<tr>
					<th><span>状态</span></th>
					<th><span>订单号</span></th>
					<th><span>类型</span></th>
					<th><span>线路名称</span></th>
					<th><span>操作人</span></th>
					<th><span>人数</span></th>
					<th><span class="fr">应收</span></th>
					<th><span class="fr">应付</span></th>
					<th><span class="fr">已收</span></th>
					<th><span class="fr">未收</span></th>
					<th><span class="fr">利润</span></th>
					<th></th>
					<!-- <th><span>客|收|支</span></th> -->
				</tr>
				<s:iterator value="page.result" var="list" status="status">
				<tr ondblclick="dbClickEvent('<s:property value="#list[0]"/>');" <s:if test="#status.count%2==1">class="cor"</s:if>>
					<td class="tips <s:if test="#list[1] == 1">red</s:if><s:else>green</s:else>"><s:property value="ddztMap.get(#list[1])"/></td>
					<td class="tips red"><s:property value="#list[2]"/></td>
					<td class="tips"><s:property value="ywlxMap.get(#list[3])"/></td>
					<td class="tips" nowrap title="<s:property value="#list[4]"/>"><s:property value="#list[4]"/></td>
					<td class="tips"><s:property value="#list[6]"/></td>
					<td class="tips"><s:property value="#list[7]"/>|<s:property value="#list[8]"/>|<s:property value="#list[9]"/></td>
					<td class="tips red tar"><s:property value="getFormatMoney(#list[10])"/></td>
					<td class="tips green tar"><s:property value="getFormatMoney(#list[11])"/></td>
					<td class="tips red tar"><s:property value="getFormatMoney(#list[13])"/></td>
					<td class="tips green tar"><s:property value="getFormatMoney(#list[14])"/></td>
					<td class="tips blue tar"><span><s:property value="getFormatMoney(#list[12])"/></span></td>
					<!-- td class="tips">3|5/8|4/5</td> -->
					<td class="tips">&nbsp;</td>
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
        <div id="notice2" title="提示消息"></div>
	</body>
</html>