<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/content/common/taglibs.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
<script>
	
jQuery.ajaxSettings.traditional = true;
var isSuccess=true;

$(document).ready(function(e) {
	$("#cityList").bind("change", function() {
		//loadDistrict($(this).val());
	});
	$("#searchDialog").click( function() {
		var url = $("#dialogForm").attr("action");
		$("#dialogForm").submit();
	});
	businessCustomerTrClick();
});

// 根据ID预加载数据
function loadDistrict(id) {
	if (id == "" || id == null || isNaN(id)) {
		return;
	}
	$.ajax({
		url : '${ctx}/admin/customer!loadDistrict.action',
		dataType : "json",
		data : {'cityID' : id},
		async : true,
		success : function(data) {
               var options_str = "<option value=\"\">请选择</option>";
               $("#district").html("");
               $.each(data,function(i){
				options_str += "<option value=\"" + data[i].districtID + "\">" + data[i].districtName + "</option>";
			});
               $("#district").append(options_str);
		},
		error : function() {
		}
	});
}
// 根据ID预加载数据
function ajaxDialog() {
	var param=getParam("dialogForm");
	var city = $("#city").val();
	var dwmc = $("#customer_dwmc").val();
	var lxr  = $("#customer_lxr").val();
	$.ajax({
		url: "${ctx}/admin/customer!ajaxDialog.action",
		data: {"city":city,"customer.dwmc":encodeURI(dwmc),"customer.lxr":encodeURI(lxr)},
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
					if(data[i][1] == "218") { city = "南宁市"
					} else if (data[i][1] == "219") { city = "柳州市"
					} else if (data[i][1] == "220") { city = "桂林市"
					} else if (data[i][1] == "221") { city = "梧州市"
					} else if (data[i][1] == "222") { city = "北海市"
					} else if (data[i][1] == "223") { city = "防城港市"
					} else if (data[i][1] == "224") { city = "钦州市"
					} else if (data[i][1] == "225") { city = "贵港市"
					} else if (data[i][1] == "226") { city = "玉林市"
					} else if (data[i][1] == "227") { city = "百色市"
					} else if (data[i][1] == "228") { city = "贺州市"
					} else if (data[i][1] == "229") { city = "河池市"
					} else if (data[i][1] == "230") { city = "来宾市"
					} else if (data[i][1] == "231") { city = "崇左市"
					} else {city = ""}
					datahtml += '<tr ' + trclass + '>'
					datahtml += '<td style="display:none">' + data[i][0]+ '</td>'
					datahtml += '<td class="tips">' + city+ '</td>'
					datahtml += '<td class="tips">' + data[i][2]+ '</td>'
					datahtml += '<td class="tips">' + data[i][3]+ '</td>'
					datahtml += '<td style="display:none">' + data[i][4]+ '</td>'
					datahtml += '<td style="display:none">' + data[i][5]+ '</td>'
					datahtml += '<td style="display:none">' + data[i][6]+ '</td>'
					datahtml += '<td style="display:none">' + data[i][7]+ '</td>'
					datahtml += '<td style="display:none">' + data[i][8]+ '</td>'
					datahtml += '<td style="display:none">' + data[i][9] +'</td></tr>'
				});
			}
			$("tbody#reflash tr").remove();
			$("tbody#reflash").append(datahtml);
			businessCustomerTrClick();
		}
	});
}
</script>
<form id="" action="${ctx}/admin/customer!ajaxDialog.action" method="post">
	<div class="pub_inp_bg"><div class="pub_inp">
		<table class="dialog">
		    <tr>
		    	<td align="right" width="73">所在地区：</td>
		        <td align="left" width="135">
					<s:select name="city" list="cityList" listValue="cityName" listKey="cityID" headerKey="" headerValue="=请选择城市=" style="float:left"/>
					<!-- select name="district" id="district"  style="float:left"><option value="">请选择</option></select> -->
				</td>
				<td align="right" width="73">单位名称：</td>
				<td align="left" width="135"><s:textfield name="customer.dwmc" cssClass="pub_input"/></td>
				<td align="right" width="73">联系人：</td>
				<td align="left" width="135"><s:textfield name="customer.lxr" cssClass="pub_input"/></td>
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
			    <col width="50%"/>
			    <col width="10%"/>
			</colgroup>
			<tr>
				<th>城市</th>
				<th>单位名称</th>
				<th>联系人</th>
			</tr>
			<tbody id="reflash">
			<s:iterator value="page.result" var="list" status="status">
				<tr <s:if test="#status.count%2==1">class="cor"</s:if>>
					<td style="display:none"><s:property value="#list[0]"/></td>
					<td class="tips" title="">
					    <s:if test="#list[1] == \"218\"">南宁市</s:if>
					    <s:elseif test="#list[1] == \"219\"">柳州市</s:elseif>
					    <s:elseif test="#list[1] == \"220\"">桂林市</s:elseif>
					    <s:elseif test="#list[1] == \"221\"">梧州市</s:elseif>
					    <s:elseif test="#list[1] == \"222\"">北海市</s:elseif>
					    <s:elseif test="#list[1] == \"223\"">防城港市</s:elseif>
					    <s:elseif test="#list[1] == \"224\"">钦州市</s:elseif>
					    <s:elseif test="#list[1] == \"225\"">贵港市</s:elseif>
					    <s:elseif test="#list[1] == \"226\"">玉林市</s:elseif>
					    <s:elseif test="#list[1] == \"227\"">百色市</s:elseif>
					    <s:elseif test="#list[1] == \"228\"">贺州市</s:elseif>
					    <s:elseif test="#list[1] == \"229\"">河池市</s:elseif>
					    <s:elseif test="#list[1] == \"230\"">来宾市</s:elseif>
					    <s:elseif test="#list[1] == \"231\"">崇左市</s:elseif>
					    <s:else></s:else>
					</td>
					<td class="tips"><s:property value="#list[2]"/></td>
					<td class="tips"><s:property value="#list[3]"/></td>
					<td style="display:none"><s:property value="#list[4]"/></td>
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
					总数<s:property value="page.totalCount"/>条/第<s:property value="page.pageNo"/>页/共<s:property value="page.totalPages"/>页
				</div>
			</div>
		</s:if>
		<s:else>
			<div class="noRecord">没有找到任何记录!</div>
		</s:else>
	</div>
</form>