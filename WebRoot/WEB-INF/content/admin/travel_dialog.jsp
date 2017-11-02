<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/content/common/taglibs.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
<script>
	
jQuery.ajaxSettings.traditional = true;
var isSuccess=true;

$(document).ready(function(e) {
	$("#searchDialog").click( function() {
		var url = $("#dialogForm").attr("action");
		$("#dialogForm").submit();
	});
	travelTrClick();
});


// 根据ID预加载数据
function ajaxDialog() {
	var param=getParam("dialogForm");
	var line = $("#customer_dwmc").val();
	var lxr  = $("#customer_lxr").val();
	$.ajax({
		url: "${ctx}/admin/travel!ajaxDialog.action",
		data: {"travel.line":encodeURI(line)},
		async: true,
		dataType: "json",
		contentType: "application/x-www-form-urlencoded; charset=UTF-8",
		success : function(data) {
			if (data.length > 0) {
				var datahtml = '';
				$.each(data, function(i) {
					var trclass  = '';
					if(i%2==0){
						trclass = "class=\"cor\"";
					}
					datahtml += '<tr ' + trclass + '>'
					datahtml += '<td style="display:none">' + data[i][1]+ '</td>'
					datahtml += '<td style="display:none">' + data[i][2]+ '</td>'
					datahtml += '<td style="display:none">' + data[i][3]+ '</td>'
					datahtml += '<td style="display:none">' + data[i][4]+ '</td>'
					datahtml += '<td style="display:none">' + data[i][5]+ '</td>'
				});
			}
			$("tbody#reflash tr").remove();
			$("tbody#reflash").append(datahtml);
			travelTrClick();
		}
	});
}
</script>
<form id="dialogForm" action="${ctx}/admin/travel!ajaxDialog.action" method="post">
	<div class="pub_inp_bg"><div class="pub_inp">
		<table class="dialog">
		    <tr>
				<td align="right" width="73">名称：</td>
				<td align="left" width="135"><s:textfield name="travel.line" cssClass="pub_input"/></td>
		    </tr>
		</table>
	</div></div>
       
    <div class="buttonArea_l">
        <input type="button" id="" class="pub_but searchButton formButton" value="查找" onclick="ajaxDialog()"/>
        <span>请点击选择</span>
    </div>
   	<div class="datagrid">
		<table id="listTable" class="listTable" cellpadding="0" cellspacing="0">
			<colgroup>
			    <col width="70%"/>
			    <col width="15%"/>
			    <col width="15%"/>
			</colgroup>
			<tr>
				<th>旅行线路</th>
				<th>操作人</th>
				<th>录入时间</th>
			</tr>
			<tbody id="reflash">
			<s:iterator value="page.result" var="list" status="status">
				<tr <s:if test="#status.count%2==1">class="cor"</s:if>>
					<td class="tips"><s:property value="#list[0]"/></td>
					<td class="tips"><s:property value="#list[1]"/></td>
					<td class="tips"><s:date name="#list[2]" format="yyyy-MM-dd"/></td>
				</tr>
			</s:iterator>
			</tbody>
		</table>
		<s:if test="page.result.size() > 0">
			<div class="pagerBar clear">
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