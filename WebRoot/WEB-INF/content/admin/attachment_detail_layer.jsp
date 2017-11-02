<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/content/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 transitional//EN" "http://www.w3.org/tr/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="height:100%;">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<title>选择图片</title>
		<meta name="”renderer”" content="”webkit”">
		<meta http-equiv="”X-UA-Compatible”" content="”IE=Edge,chrome=1″">
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
		<%@ include file="/WEB-INF/content/common/includewechat.jsp"%>
		<style type="text/css">
		.list-inline>li {
		    display: inline-block;
		    padding: 3px;
		}
		</style>
		
		<link rel="stylesheet" href="${ctx}/scripts/wechat/layer/skin/layer.css" id="layui_layer_skinlayercss">
	</head>
	<body class="skin-blue  pace-done">
		<!-- Main content -->
		<div class="jp-borwer" >
			<div class="brower-image">
				<#if attachment.isImage() >
					<img src="${ctx}${attachment.path!}" >
				<#else>
					<img src="${ctx}/static/jpress/admin/image/nothumbnail.jpg" >
				</#if>
			</div>
			<div class="brower-image-info" id="brower-image-info">
				<div class="brower-image-details">
					<div>
						<strong>文件名：</strong> <span class="filename">${(attachmentName)!}</span>
					</div>
					<div>
						<strong>文件类型：</strong> <span class="filetype">${attachment.mime_type!}（${attachment.suffix!}）</span>
					</div>
					<div class="uploaded">
						<strong>上传于：</strong> ${attachment.created!}
					</div>
					<div class="brower-image-size">
						<strong>文件大小：</strong>${(attachmentSize)!}
			
					</div>
					<div class="brower-image-dimensions">
						<strong>分辨率：</strong> ${(attachmentRatio)!}
					</div>
				</div>
			
			
				<div class="brower-settings">
					<dl class="brower-horizontal">
						<dt class="">URL</dt>
						<dd>
							<div class="form-group">
								<label class="sr-only">URL</label> 
								<textarea class="form-control" disabled="disabled" rows="2">${CPATH}${attachment.path!}</textarea>
							</div>
						</dd>
						<dt class="">标题</dt>
						<dd>
							<div class="form-group">
								<label class="sr-only">标题</label> <input class="form-control" type="text" value="${attachment.title!}">
							</div>
						</dd>
						<dt class="">说明</dt>
						<dd>
							<div class="form-group">
								<label class="sr-only">说明</label>
								<textarea class="form-control" placeholder="Enter ..." rows="3"></textarea>
							</div>
						</dd>
					</dl>
				</div>
				<p class="comment-item-actions">
					<a class="comment-item-green" href="">保存</a>
					<span>|</span>
					<a href="javascript:;" class="comment-item-red" onclick="del('${attachment.id!}')">彻底删除</a>
				</p>
			
			</div>
		</div>
		<!-- /.content -->
		
		<!-- Page script -->
		<script>
		$(document).ready(function(){
			var height = $(document).height();
			$("#brower-image-info").css("height",height+"px");
			$("#brower-image-info").css("min-height",height+"px");
		});

		function del(id){
			$.get("${ctx}/admin/attachment/delete?id="+id, function(result){
				if(result.errorCode > 0){
					alert(result.message);
				}else{
					parent.reload = true;
					parent.layer.closeAll();
				}
			});

		}
		</script>
	
	</body>
</html>