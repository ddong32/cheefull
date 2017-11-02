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
	cooperatorTrClick();
});


// 根据ID预加载数据
function ajaxDialog() {
	var param=getParam("dialogForm");
	var dwmc = $("#cooperator_dwmc").val();
	var lxr  = $("#cooperator_lxr").val();
	$.ajax({
		url: "${ctx}/admin/cooperator!ajaxDialog.action",
		data: {"cooperator.dwmc":encodeURI(dwmc),"cooperator.lxr":encodeURI(lxr)},
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
					
					var city = ""
					if(data[i][1] == "1") { city = "地接"
					} else if (data[i][1] == "2") { city = "机票"
					} else if (data[i][1] == "3") { city = "特殊"
					} 
					
					datahtml += '<tr ' + trclass + '>'
					datahtml += '<td>' + city + '</td>'
					datahtml += '<td>' + data[i][2] + '</td>'
					datahtml += '<td>' + data[i][3] + '</td>'
					datahtml += '<td>' + data[i][4] + '</td></tr>'
				});
			}
			$("tbody#reflash tr").remove();
			$("tbody#reflash").append(datahtml);
			cooperatorTrClick();
		}
	});
}
</script>
<form id="dialogForm" action="${ctx}/admin/cooperator!ajaxDialog.action" method="post">
	<div class="pub_inp_bg"><div class="pub_inp">
		<table class="dialog">
		    <tr>
				<td align="right" width="80">单位名称：</td>
				<td align="left" width="135"><s:textfield name="cooperator.dwmc" cssClass="pub_input"/></td>
				<td align="right" width="73">联系人：</td>
				<td align="left" width="135"><s:textfield name="cooperator.lxr" cssClass="pub_input"/></td>
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
				<col width="10%"/>
			    <col width="45%"/>
			    <col width="15%"/>
			    <col width="30%"/>
			</colgroup>
			<tr>
				<th>类型</th>
				<th>单位名称</th>
				<th>联系人</th>
				<th>联系电话</th>
			</tr>
			<tbody id="reflash">
			<s:iterator value="page.result" var="list" status="status">
				<tr <s:if test="#status.count%2==1">class="cor"</s:if>>
					<td style="display:none"><s:property value="#list[0]"/></td>
					<td class="tips"><s:property value="cooptypeMap.get(#list[1])"/></td>
					<td class="tips"><s:property value="#list[2]"/></td>
					<td class="tips"><s:property value="#list[3]"/></td>
					<td class="tips"><s:property value="#list[4]"/></td>
					<td style="display:none"><s:property value="#list[5]"/></td>
					<td style="display:none"><s:property value="#list[6]"/></td>
					<td style="display:none"><s:property value="#list[7]"/></td>
					<td style="display:none"><s:property value="#list[8]"/></td>
					<td style="display:none"><s:property value="#list[9]"/></td>
				</tr>
			</s:iterator>
			</tbody>
		</table>
		<s:if test="page.result.size() > 0">
			<div class="pagerBar clear">
				<div class="bar">
					总数<s:property value="page.totalCount"/>条
				</div>
			</div>
		</s:if>
		<s:else>
			<div class="noRecord">没有找到任何记录!</div>
		</s:else>
	</div>
</form>