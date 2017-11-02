<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/content/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=7,IE=9" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
	<meta http-equiv="Content-Language" content="utf-8"/> 
	<meta http-equiv="pragma" content="no-cache"/>
	<meta http-equiv="cache-control" content="no-cache"/>
	<meta http-equiv="expires" content="0"/>
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3"/>
	<meta http-equiv="description" content="This is my page"/>
    <title>违章地点审核数据量报表</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/scripts/jquery-easyui-1.3.2/themes/bootstrap/easyui.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/scripts/jquery-easyui-1.3.2/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/scripts/jquery-easyui-1.3.2/demo.css">
    
    <script type="text/javascript" src="${ctx}/scripts/jquery-easyui-1.3.2/jquery-1.8.0.min.js"></script>
    <script type="text/javascript" src="${ctx}/scripts/jquery-easyui-1.3.2/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${ctx}/scripts/My97DatePicker/WdatePicker.js"></script>
	
	<script type="text/javascript">
		$(document).ready(function(){
			
			//$("#_usersId").multiselect({buttonWidth: 155, minWidth:155, selectedList:1, noneSelectedText:"全部", classes: 'ppisDialog'});
			
			initCombobox();
			
			$('#object-data-list').treegrid({
				title:'收款统计',
			    rownumbers: false,
			    fit:true,
				collapsible:true,
				fitColumns:true,
			    animate:true,  
				url:'${ctx}/admin/report!reportBusinessCustomerFrist.action' + getParams(),
				idField:'id',  //指明哪一个字段是标识字段。类似于gridview中的id主键字段（个人理解）
				parentField : 'parent_id',
			    treeField:'area_name', //指明那个字段是可以为树形的字段
			    lines:true,
			    showFooter:true,
			    loadMsg:'数据加载中...',  
                onBeforeExpand:function(row){  
                    $("#object-data-list").treegrid('options').url = '${ctx}/admin/report!reportBusinessCustomerSecond.action'+getParams()+'&parent_id=' + row.id + '&type=' + row.type;               
                },
				onClickCell:function(a, b) {
					if (a == 'area_name') { 
						if (b.state=='open') {  
							$('#object-data-list').treegrid('collapse',b.id);  
						}  
						else {  
							$('#object-data-list').treegrid('expand',b.id) ;  
						}  
					} else {  
						return;  
					}
				},
			    columns:[[
					{field:'area_name',title:'组团社',width:230,align:'left'},
			        {field:'order_id',title:'订单编号',width:100,align:'center'},
			        {field:'travel_line',title:'线程名称',width:280,align:'left'},  
			        {field:'lrr',title:'操作人',width:60,align:'center'},
			        {field:'adult_no',title:'成人',width:60,align:'center'},  
			        {field:'child_no',title:'儿童',width:60,align:'center'},  
			        {field:'escort_no',title:'陪同',width:60,align:'center'},  
			        {field:'ygs',title:'应收',width:90,align:'right'},  
			        {field:'yjs',title:'已收',width:90,align:'right'}, 
			        {field:'wsk',title:'未收',width:90,align:'right'}
			    ]]  
			});
			
		});
		
		function getParams(){
			var beginDate = $("#beginDate").val();
			var endDate   = $("#endDate").val();
			var params  = "?beginDate="+beginDate+"&endDate="+endDate;
			var userIds = $("#_usersId").combobox('getValues');
			if (null != userIds && "" != userIds && ("undefined" != typeof (userIds))) {
				params = params + "&uid=" + userIds;
			}
			var areaName  = $("#areaName").val();
			if (null == areaName || "" == areaName || ("undefined" == typeof (areaName))) {
				areaName = "";
			}else{
				areaName = "&areaName=" + encodeURI(encodeURI(areaName));
				params = params + areaName;
			}
			//alert(params);
			return params;
		}
		
		function search(){
			var params   = getParams();
			//encodeURI
			var url      = '${ctx}/admin/report!reportBusinessCustomerFrist.action' + params;
			//alert(url);
	        $.post(url, {}, function(data) {
	            var d = data;//返回json格式结果
	            $('#object-data-list').treegrid('loadData',d);//加载数据更新treegrid
	        }, 'json');

		}

		//参数：id  控件id   code 字典编码  
		function initCombobox(){
            var value = "";
            //加载下拉框复选框 
            $('#_usersId').combobox({
                url:'${ctx}/admin/report!getComboboxData.action', //后台获取下拉框数据的url
                method:'post',
                panelHeight:'300',//设置为固定高度，combobox出现竖直滚动条 
                valueField:'id',
                textField:'name',
                multiple:true,
                formatter: function (row) { //formatter方法就是实现了在每个下拉选项前面增加checkbox框的方法  
                    var opts = $(this).combobox('options'); 
                    return '<input type="checkbox" class="combobox-checkbox">' + row[opts.textField]  
                },<%--
                onLoadSuccess: function () {  //下拉框数据加载成功调用  
                    var opts = $(this).combobox('options');  
                    var target = this;  
                    var values = $(target).combobox('getValues');//获取选中的值的values  
                    $.map(values, function (value) {  
                        var el = opts.finder.getEl(target, value);  
                        el.find('input.combobox-checkbox')._propAttr('checked', true);   
                    })  
                },  
                onSelect: function (row) { //选中一个选项时调用  
                    var opts = $(this).combobox('options');  
                    //获取选中的值的values  
                    $("#"+id).val($(this).combobox('getValues'));  
                     
                   //设置选中值所对应的复选框为选中状态  
                    var el = opts.finder.getEl(this, row[opts.valueField]);  
                    el.find('input.combobox-checkbox')._propAttr('checked', true);  
                },  
                onUnselect: function (row) {//不选中一个选项时调用  
                    var opts = $(this).combobox('options');  
                    //获取选中的值的values  
                    $("#"+id).val($(this).combobox('getValues'));  
                    
                    var el = opts.finder.getEl(this, row[opts.valueField]);  
                    el.find('input.combobox-checkbox')._propAttr('checked', false);  
                }
                --%>
            });
        }  
    </script>
</head>
<body class="easyui-layout" style="margin:5px">
	<div data-options="region:'north'," style="height:40px;line-height:30px;">
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
				<td align="left" width="120">
					<input id="_usersId" name="business.proUserIds" style="width: 150px;" class="easyui-combobox" />
				</td>
				<td align="right" width="80">公司名称：</td>
				<td align="left" width="300">
					<input type="text" name="areaName" id="areaName" class="pub_input" value="${areaName}"></input>
				</td>
				<td><input type="button" onclick="search();" id="searchButton" class="pub_but searchButton formButton" value="查询" /></td>
			</tr>
		</table>
	</div>
	<div data-options="region:'center'">
		<table id="object-data-list"></table>
	</div>
</body>
</html>