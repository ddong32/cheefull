<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/content/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>违章地点审核数据量报表</title>
    <%@ include file="/WEB-INF/content/common/include.jsp"%>
    <script type="text/javascript">
    	var curType = "1";

		function getParams(){
			var userIds = $("#_usersId").val();
			if (null == userIds || "" == userIds || ("undefined" == typeof (userIds))) {
				var ids = $("#userIds").val();
				userIds=ids;
			}
			var startdate = $("#beginDate").val();
			var enddate   = $("#endDate").val();
			var coopid    = $("#cooperator_id").val();
			var params    = "&uid="+userIds+"&startdate="+startdate+"&enddate="+enddate+"&coopid="+coopid;
			return params;
		}

		function search(){
			var params = getParams();
			var hostname = window.location.host;
			var reportAdd_a = "http://"+hostname+"/WebReport/ReportServer?reportlet=businessCooperator.cpt&op=view"+params;
			reportAdd_1 = encodeURI(reportAdd_a);
			switch(curType){
				case "1":
					$("#reportFrame").attr("src",reportAdd_1);
					break;
				case "2":
					break;
				default:;
			}
			$.ajax({  
				url : "http://"+hostname+"/WebReport/ReportServer", 
				data : {  
					op : 'fr_utils',  
					cmd : 'gs_gc'  
				},  
				async : false,  
			})
		}
		
		$(document).ready(function(){
			$("#_usersId").multiselect({buttonWidth: 155, minWidth:155, selectedList:1, noneSelectedText:"全部", classes: 'ppisDialog'});
			$("#switchTab li").bind("click",function(){
				$this = $(this);
				curType = $this.attr("tabtype");
				$(".hover").removeClass("hover");
				$this.addClass("hover");
				search();
			});
			var windowHeight = document.documentElement.clientHeight;
			var iframeHeight = windowHeight-$("#reportFrame").offset().top-20;
			$("#reportFrame").height(iframeHeight);
			
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
			
			search();
		});
		
		function cooperatorTrClick(){
        	$("#coopeDialog table:eq(1) tr:gt(0)").click(function () {
        		$("#cooperator_id").val($(this).find("td").eq(0).html());
        		$("#treeSel").val($(this).find("td").eq(2).html());
        		$("#coopeDialog").dialog("close");
        	})
        }
    </script>
</head>
<body class="con_r list">
	<div class="pub_inp_bg"><div class="pub_inp">
		<table class="con_table">
			<tr>
				<td align="right" width="80">
					出发日期:
				</td>
				<td align="left" width="220">
					<input type="text" class="Wdate" name="beginDate" id="beginDate" size="12" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\');}',startDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd',readOnly:true})" readonly="true" class="Wdate" style="width:90px;text-indent:4px" value="${beginDate}" />
					至
					<input type="text" class="Wdate" name="endDate" id="endDate" size="12" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'beginDate\');}',startDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd',readOnly:true})" readonly="true" class="Wdate" style="width:90px;text-indent:4px" value="${endDate}"/>
				</td>
				<td align="right" width="70">操作人:</td>
				<td align="left" width="120"><s:select cssClass="multiselect" multiple="true" name="business.proUserIds" id="_usersId" list="userList" listKey="id" listValue="name"/></td>
				<td align="right" width="80">采购名称：</td>
				<td align="left" width="300">
					<input type="text" name="account.cooperator.dwmc" id="treeSel" class="ppisDialog ui-multiselect ui-widget ui-state-default ui-corner-all pub_input" style="height: 20px; line-height: 20px; width: 200px;" value="${account.cooperator.dwmc}"></input>
					<span id="cooperator_name_span" style="margin-left:5px; line-height:25px; font-weight:bold; cursor:pointer; text-decoration:underline; font-size: 12px; color:red; "> 搜索 </span>
					<s:hidden name="cooperator.id"/>
				</td>
				<td><input type="button" onclick="search();" id="searchButton" class="pub_but searchButton formButton" value="查询" /></td>
			</tr>
		</table>
	</div></div>
	<div class="listtab">
		<iframe id="reportFrame" src="" style="width:100%; border: 0; background:#fff"></iframe>
	</div>
    <s:hidden id="userIds" name="#request.ids"></s:hidden>
    <div id="coopeDialog" title="采购单位"></div>
</body>
</html>
