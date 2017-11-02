<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/content/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 transitional//EN" "http://www.w3.org/tr/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="height:100%;">
<HEAD>
	<title>菜单设置</title>
	<meta charset="utf-8"><meta name="”renderer”" content="”webkit”">
	<meta http-equiv="”X-UA-Compatible”" content="”IE=Edge,chrome=1″">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<%@ include file="/WEB-INF/content/common/includewechat.jsp"%>
	<style type="text/css">
		.formText{width:150px;}
	</style>
	<script>
	// increase the default animation speed to exaggerate the effect
	$.fx.speeds._default = 1000;
	jQuery.ajaxSettings.traditional = true;
	var isSuccess=true;
	$(function() {
		$( "#dialog" ).dialog({
			dialogClass:"ppisDialog departmentDialog",
			autoOpen: false,
			height: "auto",
			minHeight: 0,
			resizable: false,
			modal: true,
			open : function (){
				if ( $(this).find("ul").html()=="" ){
					$(this).html("<p>没有符合的数据</p>");
				}
			}
		});
		
	});
	$(document).ready(function(e) {
		$("#notice").dialog({
			dialogClass:"ppisDialog",
			autoOpen: false,
			resizable:false,
			modal: true,
			buttons: {
				"确定" : function(){
					$(this).dialog('close');
					if (isSuccess){
						location.reload();
					}
				}
			}
		});
		
		$("#doSubmit").bind('click',function (){
			var isIllegal = false;
			$("[rule]").each(function(index, element) {
				isIllegal |= testIllegal($(this));
			});
			if (isIllegal){
				$("#errInfo").show();
				return false;
			} else {
				$("#errInfo").hide();
			}
			var param=getParam("form");
			$.ajax({
				url : $("#ctx").val() + "/admin/wechat!menuSave.action",
				type: "POST",
				data: param,
				success: function(msg){
					isSuccess= (trim(msg)=="您的操作已成功!")?true :false;
					$("#notice").html(msg);
					$("#notice").dialog('open');
				}
			});
			return false;
		});
		
		loadmenus();
		loadparent();
	});
	
	 function menuDel(id){
		if (confirm("您确定吗？") == true) {
			$.ajax({
				type: "post",
				url : $("#ctx").val() + "/admin/wechat!menuDel.action",
				data : {
					'wxCode.id' : id
				},
				async: true,
				success: function(data) {
					isSuccess= (trim(msg)=="您的操作已成功!")?true :false;
					$("#notice").html(msg);
					$("#notice").dialog('open');
				},
				complete: function() {
			        
				},
	            error:function(){
	            	alert("操作错误");
	            }
			});
			loadmenus();
			loadparent();
		}
	 }
	 
	 function doSync(){
		$.ajax({
			type: "post",
			url : $("#ctx").val() + "/admin/wechat!menuSync.action",
			dataType: "json",
			async: true,
			beforeSend: function(data) {
				
			},
			success: function(data) {
				if (data && data.status == "success") {
					alert(data.message);
				}else if(data.status == "error"){
					alert(data.message);
				}
			},
			complete: function() {
		        
			},
            error:function(){
            	alert("操作错误");
            }
			
		});
		return false;
	 }
	 
	 //
	 function loadmenus(){
		$.ajax({
			url : $("#ctx").val() + "/admin/wechat!loadmenus.action",
			data : {
				//'wxContent.id' : id
			},
			async : true,
			success : function(data) {
				setmenuTable(data);
			},
			error : function() {
			}
		});
	}
	 
	function setmenuTable(msg){
		try{
			var jsonObj = jQuery.parseJSON(msg)[0];
			var data    = jsonObj.optionList;
		}catch(e){
			alert("setmenuTable --> "+e+msg);
			return;
		}
		if (data.length > 0) {
			var datahtml = '';
			$.each(data, function(i) {
				datahtml += '<tr class="jp-onmouse">';
				datahtml += '<td><strong><a href="#">' + data[i].title + '</a></strong>';
				datahtml += '<div class="jp-flash-comment">';
				datahtml += '<p class="row-actions jp-common-pad" style="display: none;">';
				datahtml += '<span class="approve"><a class="vim-a" href="javascript:loadMenu('+ data[i].id +')">编辑</a></span> ';
				datahtml += '<span class="spam">| <a class="vim-s vim-destructive" href="javascript:menuDel('+ data[i].id +')">删除</a></span>';
				datahtml += '</p></div></td>';
				datahtml += '<td>'+ data[i].flag +'</td>';
				datahtml += '<td>'+ data[i].text +'</td>';
				datahtml += '<td>'+ data[i].order_number +'</td>';
				datahtml += '</tr>';
			});
			$("table.table tbody tr:not(:first)").remove();
			$("table.table tbody ").append(datahtml);
		}
		onmouse();
	}
	
	function onmouse(){
		$(".jp-onmouse").mouseover(function() {
			$(this).find(".row-actions").show();
		}).mouseout(function() {
			$(".row-actions").hide()
		})
	}
	
		 //
	 function loadparent(){
		$.ajax({
			url : $("#ctx").val() + "/admin/wechat!loadparent.action",
			data : {
				//'wxCode.id' : id
			},
			async : true,
			success : function(data) {
				setparent(data);
			},
			error : function() {
			}
		});
	}
	
	function setparent(msg){
		try{
			var jsonObj = jQuery.parseJSON(msg)[0];
			var data    = jsonObj.parentList;
		}catch(e){
			alert("setparent --> "+e+msg);
			return;
		}
		if (data.length > 0) {
			$("#wxContent_parent_id option").remove();
			$("#wxContent_parent_id").append("<option value=''>无</option>");
			$.each(data, function(i) {
				$("#wxContent_parent_id").append("<option value='"+data[i].id+"'>"+data[i].title+"</option>");
			});
		}
	}
	
	 //
	 function loadMenu(id){
		$.ajax({
			url : $("#ctx").val() + "/admin/wechat!loadMenu.action",
			data : {
				'wxCode.id' : id
			},
			async : true,
			success : function(msg) {
				try{
					if (msg == "") {
						alert(id+"订单信息为空！");
						return false;
					}
					var jsonObj = jQuery.parseJSON(msg)[0];
					$("#form #wxCode_id").val(jsonObj.id);
					$("#form #wxCode_title").val(jsonObj.title);
					$("#form #wxCode_flag").val(jsonObj.flag);
					$("#form #wxCode_text").val(jsonObj.text);
					$("#form #wxCode_order_number").val(jsonObj.order_number);
					$("#form #wxCode_parent_id").val(jsonObj.parent_id);
				}catch(e){
					alert(e);
					return false;
				}
			},
			error : function() {
			}
		});
		
	}
	</script>
</HEAD>
<body class="" style="background-color: #ecf0f5;">
	<!-- Content Wrapper. Contains page content -->
	<div class="content-wrapper" style="min-height: 665px; padding-top:10px; padding-left:15px; ">
    <div class="row">
		<div class="col-md-4">
			<form id="form" action="${ctx}/admin/wechat!menuSave.action" method="POST">
				<s:hidden name="ucode"></s:hidden>
				<s:hidden name="wxCode.id"></s:hidden>
				<input type="hidden" id="ctx" value="${ctx}"/>
				<h4 class="jp-common-pad">添加新菜单</h4>
				<div class="form-group jp-input-item">
					<label for="wxCode_title" class="input-title">名称</label>
					<s:textfield name="wxCode.title" rule="noempty" onpaste="return false" cssClass="form-control"></s:textfield>
					<p>这将显示在微信的菜单底部。</p>
				</div>

				<div class="form-group ">
					<div class="col-xs-5 jp-common-pad">
						<label class="input-title">菜单类型</label> 
						<select class="form-control input-sm" name="wxCode.flag" id="wxCode_flag">
							<option value="click" >点击推事件</option>
							<option value="view" >跳转URL</option>
							<option value="scancode_push" >扫码推事件</option>
							<option value="scancode_waitmsg" >扫码推事件且弹出“消息接收中”提示框</option>
							<option value="pic_sysphoto">弹出系统拍照发图</option>
							<option value="pic_photo_or_album">弹出拍照或者相册发图</option>
							<option value="pic_weixin" >弹出微信相册发图器</option>
							<option value="location_select" >弹出地理位置选择器</option>
						</select>
					</div>
					<div class="clr"></div>
				</div>
				<div class="form-group jp-input-item">
					<label for="wxCode_flag" class="input-title">关键字</label>
					<s:textfield name="wxCode.flag" rule="noempty" onpaste="return false" cssClass="form-control"></s:textfield>
					<p>填写的关键字将会触发“自动回复”匹配的内容，访问网页请填写URL地址。</p>
				</div>
				<div class="form-group jp-input-item">
					<label for="wxCode_order_number" class="input-title">排序</label>
					<input id="wxCode_order_number" class="form-control" type="text" name="wxCode.order_number" value="${wxCode.order_number}">
					<p>排序数字。</p>
				</div>
				<div class="form-group jp-input-item">
					<div class="col-xs-5 jp-common-pad">
						<label class="input-title">父节点</label> 
						<select class="form-control input-sm" name="wxCode.parent.id" id="wxCode_parent_id">
							<option value="">无</option> 
						</select>
					</div>
					<div class="clr"></div>
					<p>父级菜单。</p>
				</div>

				<button type="button" id="doSubmit" class="btn btn-primary" >保存</button>

			</form>
			<!-- /.box -->
		</div>
		<!-- /.col -->
		<div class="col-md-8 ">
			<div class="col-xs-12 jp-common-pad ">
				<div class="jp-left  ">
					<button class="btn btn-sm btn-default" type="button" onclick="doSync()">同步到微信菜单</button>
				</div>
			</div>
			<div class="clr"></div>
			<div class="box " >

				<!-- /.box-header -->
				<div class="box-body jp-common-pad">
					<table class="table table-striped">
						<thead>
							<tr>
								<th style="width: 30%">名称</th>
								<th>菜单类型</th>
								<th>关键字</th>
								<th>排序</th>
							</tr>
						</thead>
						<tbody>
							
						</tbody>
					</table>
				</div>
				<!-- /.box-body -->
				<!-- /.box-footer -->
			</div>

			<!-- /. box -->
		</div>
		<!-- /.col -->
	</div>
	<!-- /.row -->
	</div>
	<!-- /.content-wrapper -->
	<div id="notice" title="提示消息"></div>
</body>
</html>