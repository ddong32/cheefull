<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/content/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Basic TreeGrid - jQuery EasyUI Demo</title>
	<link type="text/css" rel="stylesheet" href="${ctx}/styles/jquery.multiselect.css"/>
	<link type="text/css" rel="stylesheet" href="${ctx}/styles/jquery.multiselect.filter.css"/>
	<link rel="stylesheet" type="text/css" href="${ctx}/scripts/jquery-easyui-1.3.2/themes/bootstrap/easyui.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/scripts/jquery-easyui-1.3.2/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/scripts/jquery-easyui-1.3.2/demo.css">
    
    <script type="text/javascript" src="${ctx}/scripts/jquery-easyui-1.3.2/jquery-1.8.0.min.js"></script>
    <script type="text/javascript" src="${ctx}/scripts/jquery-easyui-1.3.2/jquery.easyui.min.js"></script>

	<script type="text/javascript">
		$(document).ready(function(){
			
			//$("#_usersId").multiselect({buttonWidth: 155, minWidth:155, selectedList:1, noneSelectedText:"全部", classes: 'ppisDialog'});
			
			$('#object-data-list').treegrid({  
			    title:'Complex TreeGrid',  
			    width:1000,
			    rownumbers: false,  
			    animate:true,  
			    url:'${ctx}/admin/report!reportBusinessCustomerFrist.action',
			    idField:'id',  //指明哪一个字段是标识字段。类似于gridview中的id主键字段（个人理解）
			    treeField:'area_name', //指明那个字段是可以为树形的字段
			    loadFilter: myLoadFilter，  //指明数据进行过滤后应用的方法，这里应该是点击树形父节点的时候出发的方法
			    columns:[[
					{field:'id',title:'编号',width:10,align:'left'},
					{field:'area_name',title:'组团社',width:100,align:'left'},
			        {field:'order_id',title:'订单编号',width:50,align:'center'},  
			        {field:'trave_name',title:'线程名称',width:50,align:'center'},  
			        {field:'user_name',title:'操作人',width:50,align:'center'},
			        {field:'f4',title:'成人',width:50,align:'center'},  
			        {field:'f5',title:'儿童',width:50,align:'center'},  
			        {field:'f6',title:'陪同',width:50,align:'center'},  
			        {field:'f7',title:'应收',width:50,align:'right'},  
			        {field:'f8',title:'已收',width:50,align:'right'}, 
			        {field:'f8',title:'未收',width:50,align:'right'}
			    ]]  
			});
			
		});
		
		function myLoadFilter(data, parentId){
			function setData(){//重新整理数据的children属性
				var todo = [];
				for(var i=0; i<data.length; i++){
					todo.push(data[i]);//数组末端添加 将源数据添加到数组中便于后续利用
				}
				while(todo.length){
					var node = todo.shift();//方法用于把数组的第一个元素从其中删除，并返回第一个元素的值。
					if (node.children){//如果有子节点
						node.state = 'closed';
						node.children1 = node.children;
						node.children = undefined;
						todo = todo.concat(node.children1);//数组联结
	　　　　　　　　　　　　　　　　　　}
				}
			}
			
			setData(data);
			var tg = $(this);//当前页面
			var opts = tg.treegrid('options');//获取属性表格的属性对象
			//节点展开前执行的方法  row是行数据
	　　　　　　　　　opts.onBeforeExpand = function(row){
				if (row.children1){
					tg.treegrid('append',{
						parent: row[opts.idField],//父节点id
						data: row.children1//节点数据
					});
					row.children1 = undefined;
					tg.treegrid('expand', row[opts.idField]);//打开某个节点
				}
				return row.children1 == undefined;
			};
			return data;
		}
    </script>
</head>
<body class="easyui-layout" style="margin:5px">
	<div data-options="region:'north'," style="height:60px;line-height:30px;">
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
				<td align="right" width="80">公司名称：</td>
				<td align="left" width="300">
					<input type="text" name="areaName" id="areaName" class="pub_input" value="${areaName}"></input>
				</td>
				<td><input type="button" onclick="search();" id="searchButton" class="pub_but searchButton formButton" value="查询" /></td>
			</tr>
		</table>
	</div>
	<div data-options="region:'center',">
		<table id="object-data-list"></table>
	</div>
</body>
</html>