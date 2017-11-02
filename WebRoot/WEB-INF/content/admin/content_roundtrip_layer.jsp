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
		<script>
		
		</script>
	</head>
	<body class="skin-blue  pace-done">
		<div class="pace pace-inactive">
			<div class="pace-progress" data-progress-text="100%" data-progress="99" style="transform: translate3d(100%, 0px, 0px);">
				<div class="pace-progress-inner"></div>
			</div>
			<div class="pace-activity"></div>
		</div>
		
		<div class="row">
			<div class="col-md-12">
				<div class="nav-tabs-custom">
					
					<div class="tab-content">
						<div class="tab-pane active" id="tab_2">
							<div class="box box-solid">
								<div class="box-body no-padding">
									<div class="tab-pane form-horizontal">
						                <div class="form-group">
						                	<label for="wxContent_price" class="col-sm-1 control-label">标准价格</label>
						                  	<div class="col-sm-11">
						                    	<input type="text" class="form-control" id="wxContent_price" name="wxContent.price" value="">
						                	</div>
						                </div>
						                <div class="form-group">
						                	<label for="wxContent_vip_price" class="col-sm-1 control-label">会员价格</label>
						                  	<div class="col-sm-11">
						                    	<input type="text" class="form-control" id="wxContent_vip_price" name="wxContent.vip_price" value="">
						                	</div>
						                </div>
						                <div class="form-group">
								            <label for="wxContent_days" class="col-sm-1 control-label">行程天数</label>
						                  	<div class="col-sm-11">
						                    	<input type="text" class="form-control" id="wxContent_days" name="wxContent.days" value="">
						                	</div>
						                </div>
					                </div>
								</div>
							</div>
						
							<div class="jp-borwer">
								<ul class="list-inline list-unstyled">
									
								</ul>
								<div class="box-body no-padding">
									<textarea id="textarea_shine" name="textarea_shine" ></textarea>
									<textarea id="wxContent_text" name="wxContent.text" aria-hidden="true" style="display: none; visibility: hidden;"></textarea>
								</div>
							</div>
			
						</div><!-- tab-pane active -->
					</div>    <!-- tab-content -->
				</div>
			</div>
		</div>                <!-- row -->
		
		<button class="btn btn-block btn-primary jp-submiti-button" onclick="doConfirm()"> 确&nbsp;定 </button>
		
	
		<!-- Page script -->
		<script>
	
		var pUrl = null;
		var pAlt = null;
		function doChoose(url,alt){
			pUrl=url;
			pAlt=alt; 
		}
	
		function doConfirm(){
			parent.data.url=pUrl;
			parent.data.alt=pAlt; 
			parent.layer.closeAll(); 
		}
	
		function initTinymce(){
			tinymce.init({
		        selector: '#textarea_shine',
		        height: 280,
		        language: 'zh_CN',
		        menubar: false,
		        automatic_uploads: true,
		        paste_data_images: true,
		        convert_urls: false,
		        relative_urls : false,
		        imagetools_toolbar: "rotateleft rotateright | flipv fliph | editimage imageoptions",
		        imagetools_proxy: '${ctx}/admin/attachment!proxy.action',
		        images_upload_url: '${ctx}/admin/attachment!upload.action',
		        wordcount_countregex: /[\u4e00-\u9fa5_a-zA-Z0-9]/g,
				file_picker_callback: function(callback, value, meta) {
					layer.open({
					    type: 2,
					    title: '选择图片',
					    shadeClose: true,
					    shade: 0.8,
					    area: ['92%', '90%'],
					    content: '${ctx}/admin/attachment!choose_layer.action',
					    end:function(){
					    	if(''!=data.url && null != data.url){
					    		callback(data.url, {alt: data.alt});
					    	}
					    }
					});
				},
		        plugins: [
				    "advlist autolink autosave link image media imagetools lists charmap print preview hr anchor pagebreak spellchecker",
				    "searchreplace wordcount visualblocks visualchars code codesample fullscreen insertdatetime media nonbreaking",
				    "table contextmenu directionality emoticons template textcolor paste fullpage textcolor colorpicker textpattern"
				],
		        toolbar1: '  bold italic underline strikethrough removeformat | blockquote hr table image media codesample | anchor link  unlink | alignleft aligncenter alignright alignjustify ',
		        toolbar2: '  formatselect | bullist numlist | outdent indent | forecolor backcolor  |  undo redo | code  fullscreen',
			});
		}
		
		$(document).ready(function(){
			initTinymce();
		});
	
		
		</script>
	
	</body>
</html>