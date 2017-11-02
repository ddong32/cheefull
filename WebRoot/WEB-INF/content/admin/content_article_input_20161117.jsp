<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/content/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 transitional//EN" "http://www.w3.org/tr/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="height:100%;">
<HEAD>
	<title>文章添加\编辑</title>
	<%@ include file="/WEB-INF/content/common/includewechat.jsp"%>

	<style type="text/css">.pub_input{width:150px;}</style>
	<script>
	
	// increase the default animation speed to exaggerate the effect
	$.fx.speeds._default = 1000;
	jQuery.ajaxSettings.traditional = true;
	var isSuccess=true;
	
	var data={
	    url:'',
	    alt:''
	};
	
	function initTinymce(){
		tinymce.init({
	        selector: '#textarea',
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
	        toolbar1: '  bold italic underline strikethrough removeformat | blockquote hr table image media codesample | anchor link   unlink | alignleft aligncenter alignright alignjustify | bullist numlist     ',
	        toolbar2: '  formatselect | outdent indent | forecolor backcolor  |  undo redo | code  fullscreen',
		});
	}
	
	var simplemde ;
	function initMarkdownEditor(){
		 simplemde = new SimpleMDE({ element: $("#textarea")[0] });
	}
	
	var _editor = "tinymce";
	if("tinymce" == _editor){
		initTinymce();
	}else{
		initMarkdownEditor();
	}
	    
	function save(){
		
		$('#content_slug').attr('value',$("#slug_text").text());
		
		if("tinymce" == _editor){
			tinymce.activeEditor.uploadImages(function(success) {
				tinymce.triggerSave();
		 		doSubmit();
			});
		}else{
			$("#textarea").text(simplemde.markdown(simplemde.value()));
			doSubmit();
		}
		return false;
	}
	
	function saveAsDraft(){
		$("#content_status").attr("value","draft");
		save();
	}
	
	function doSubmit(){
		$("#form").ajaxSubmit({
			type : "post", 
			dataType : "json", 
			success : function(data) { 
				if(data.errorCode == 0){
					$("#content_id").attr("value",data.data);
					toastr.success('保存成功！','操作成功');
				}else{
					toastr.error(data.message,'操作失败');
				}
			},
			error : function() {
				alert("信息提交错误");
			}
		});
	}
	 
	function doSelectThumbnail(){
	 	layer.open({
		    type: 2,
		    title: '选择图片',
		    shadeClose: true,
		    shade: 0.8,
		    area: ['92%', '90%'],
		    content: "${ctx}/admin/attachment!choose_layer.action",
		    end:function(){
		    	if(''!=data.url && null != data.url){
		    		$("#thumbnail").attr("src",data.url);
		    		$("#content_thumbnail").attr("value",data.url);
		    	}
		    }
		}); 
	 }
	 
	 function doRemoveThumbnail(){
	 	$("#thumbnail").attr("src","../images/nothumbnail.jpg");
	 	$("#content_thumbnail").val("");
	 }
	 
	 function doChangeEditor(){
	 	if("tinymce" == _editor){
	 		doAjax("${ctx}/admin/content/changeEditor/markdown");
	 	}else{
	 		doAjax("${ctx}/admin/content/changeEditor/tinymce");
	 	}
	 }
	 
	function doAjax(url){
		$.get(url, function(result){
			if(result.errorCode > 0){
				toastr.error(result.message,'操作失败');
			}else{
				location.reload();
			}
		}); 
	}
	 
	$('#_category').tagEditor();
	$('#_feature').tagEditor();
	$('#_tag').tagEditor();
	
	
	$("#title").keyup(function(){
		if($('#content_slug').val() == ""){
		   $("#slug_text").text(this.value);
	       $('#slug_text').editable('setValue',this.value);
		}
	});
	
	function initTree(){
		var pActive = $("#article[class='treeview']");
		pActive.attr("class","treeview active");
	  	pActive.find("#edit").attr("class","active");
	}
	
	function fix() {
	    var neg = $('.main-header').outerHeight() + $('.main-footer').outerHeight();
	    var window_height = $(window).height();
	    var sidebar_height = $(".sidebar").height();
	    
	    if (window_height >= sidebar_height + neg) {
	      $(".content-wrapper, .right-side").css('min-height', window_height - neg);
	    } else {
	      $(".content-wrapper, .right-side").css('min-height', sidebar_height );
	    }
	}
	  
	function initUrls(){
		 $(".treeview-menu").each(function(){
			var pid = $(this).parent().attr("id");
			if(typeof(pid) == 'undefined' || null == pid){
				return;
			}
			$(this).find('a').each(function(){
				var cid = $(this).parent().attr("id");
				var href  = $(this).attr("href");
				if(href.indexOf("?") == -1){
					href = href+ "?p="+pid;
				}else{
					href = href+ "&p="+pid;
				}
				if("undefined" != typeof(cid) && "" != cid){
					href +=  "&c="+cid;
				}
				$(this).attr("href",href);
			});
		});
	}
	
	function initToast(){
		toastr.options = {
			"closeButton": true,
			"debug": false,
			"newestOnTop": false,
			"progressBar": true,
			"positionClass": "toast-top-center",
			"preventDuplicates": false,
			"onclick": null,
			"showDuration": "300",
			"hideDuration": "1000",
			"timeOut": "2000",
			"extendedTimeOut": "1000",
			"showEasing": "swing",
			"hideEasing": "linear",
			"showMethod": "fadeIn",
			"hideMethod": "fadeOut"
		}
	}

	$(document).ready(function(){
		
		 $.fn.editable.defaults.mode = 'inline';
		 $('#slug_text').editable();
		 
		 var url = window.location.protocol  +"//"+ window.location.host+"/jpress/c/" ;
	     $("#url_preffix").text(url);
	     $('#slug_text').editable('setValue'," 标题");
	     
	     $('#titleurl').on('save', function(e, params) {
	    	 $('#content_slug').attr('value',params.newValue);
		 });
	});
	$(document).ready(function(){
		initTree();
		fix();
		initUrls();
		initToast();
		
		initTinymce();
	});
		
	</script>
</HEAD>
<body class="con_r">
    
    <div class="content-wrapper" style="min-height: 697px;">

		<!-- Main content -->
		<div class="content" style="z-index: 9999">
			<form action="/jpress/admin/content/save" id="form" method="post">
				<input type="hidden" name="content.module" value="article"> 
				<input type="hidden" id="content_id" name="content.id" value=""> 
				<input type="hidden" name="content.status" value="normal" id="content_status"> 
				<input type="hidden" name="content.created" value=""> 
				<input type="hidden" id="content_slug" name="content.slug" value=""> 
				<input type="hidden" name="ucode" value="c12d1f6e0aaed1151aeda1704b85e12a">
				<div class="row">
					<!-- /.col -->
					<div class="col-md-9">
						<div class="form-group">
							<input name="content.title" class="form-control input-lg" id="title" value="" type="text" placeholder="在此输入标题">
						</div>
						<!--<div class="pull-right"><a href="javascript:void(0);" onclick="doChangeEditor()">切换编辑器 </a> </div>-->
						<label>
							网址：<span id="url_preffix">http://127.0.0.1/jpress/c/</span><span id="slug_text" class="editable editable-click" style="display: inline;">标题</span>
						</label>
						<div class="box box-solid">
							<div class="box-body no-padding">
								<textarea id="textarea" name="content.text" ></textarea>
							</div>
						</div>
		
						<div class="box box-solid">
							<div class="box-header with-border">
								<h3 class="box-title">属性设置</h3>
								<div class="box-tools">
									<button type="button" class="btn btn-box-tool" data-widget="collapse">
										<i class="fa fa-minus"></i>
									</button>
								</div>
							</div>
							<!-- /.box-header -->
							<div class="box-body no-padding">
								<!-- Custom Tabs -->
								<div class="nav-tabs-custom">
									<ul class="nav nav-tabs">
										<li class="active"><a href="#tab_1" data-toggle="tab">常用</a></li>
										<li><a href="#tab_seo" data-toggle="tab">SEO</a></li>
										<li><a href="#tab_position" data-toggle="tab">位置</a></li>
										<li><a href="#tab_remarks" data-toggle="tab">备注</a></li>
										<li><a href="#tab_metadata" data-toggle="tab">元数据</a></li>
									</ul>
									<div class="tab-content">
										<div class="tab-pane active form-horizontal" id="tab_1">
							                <div class="form-group">
							                	<label for="_flag" class="col-sm-1 control-label">标识</label>
												<div class="col-sm-11">
							               			<input type="text" class="form-control" name="content.flag" id="_flag" value="">
							                	</div>
							                </div>
							                <div class="form-group">
							                	<label for="_uname" class="col-sm-1 control-label">作者</label>
							                  	<div class="col-sm-11">
							                    	<input type="text" class="form-control" id="_uname" name="username" value="">
							                	</div>
							                </div>
							                <div class="form-group">
									            <label for="_uname" class="col-sm-1 control-label">评论</label>
									            <div class="col-sm-11">
									             <select class="form-control" name="content.comment_status">
													<option value="">默认</option> 
													<option value="open">开启</option>  
													<option value="close">关闭</option> 
									             </select>
									            </div>
							                </div>
										</div>
										
										<!-- /.tab-pane -->
										<div class="tab-pane " id="tab_seo">
											<div class="form-group ">
												<textarea class="form-control " rows="2" name="content.meta_keywords" placeholder="请输入关键字"></textarea>
											</div>
											<div class="form-group ">
												<textarea class="form-control" rows="2" name="content.meta_description" placeholder="请输入描述内容"></textarea>
											</div>
										</div>
										
										<div class="tab-pane form-horizontal" id="tab_position">
											
											<div class="form-group">
							                 	<label for="_lng" class="col-sm-1 control-label">经度</label>
							
								                <div class="col-sm-11">
													<input type="text" class="form-control" id="_lng" name="content.lng" value="">
								                </div>
							               </div>
							               <div class="form-group">
							                 	<label for="_lat" class="col-sm-1 control-label">纬度</label>
							                 	<div class="col-sm-11">
							                 		<input type="text" class="form-control" id="_lat" name="content.lat" value="">
							                 	</div>
							               </div>
										</div>
										
										<div class="tab-pane " id="tab_remarks">
											<div class="form-group">
												<textarea class="form-control" rows="3" name="content.remarks" placeholder="请输入备注信息"></textarea>
											</div>
										</div>
										
										<div class="tab-pane form-horizontal" id="tab_metadata">
										
											 <div class="form-group">
								                 <label for="meta:_meta1" class="col-sm-1 control-label" style="width: 10%">元数据1</label>
								                 <div class="col-sm-11" style="width: 90%">
								                   <input type="text" class="form-control" placeholder="元数据测试1" id="meta:_meta1" name="meta:_meta1" value="">
								                 </div>
								              </div>
											 <div class="form-group">
								                 <label for="meta:_meta2" class="col-sm-1 control-label" style="width: 10%">元数据2</label>
								                 <div class="col-sm-11" style="width: 90%">
								                   <input type="text" class="form-control" placeholder="元数据测试2" id="meta:_meta2" name="meta:_meta2" value="">
								                 </div>
								              </div>
										
										</div>
										<!-- /.tab-pane -->
									</div>
									<!-- /.tab-content -->
								</div>
								<!-- nav-tabs-custom -->
							</div>
						</div>
					</div>
					<!-- /.col -->
					<div class="col-md-3">
						<div class="box box-solid jp-release-set jp-borde-top">
							<div class="timestamp-wrap timestamp-wrap-box">
								<button type="button" class="btn btn-default btn-sm jp-inline-right2" onclick="saveAsDraft()">草稿</button>
								<button type="button" class="btn btn-primary btn-sm jp-inline-right1" onclick="save()">发布</button>
								<a href="#">移至垃圾箱</a>
							</div>
						</div>
						<div class="box box-solid">
							<div class="box-header with-border">
								<h3 class="box-title">分类</h3>
						
								<div class="box-tools">
									<button type="button" class="btn btn-box-tool" data-widget="collapse">
										<i class="fa fa-minus"></i>
									</button>
								</div>
							</div>
							<div class="box-body jp-content-select-box">
								
							</div>
						</div>
						<!-- /.box 
						<div class="box box-solid">
							<div class="box-header with-border">
								<h3 class="box-title">专题</h3>
						
								<div class="box-tools">
									<button type="button" class="btn btn-box-tool" data-widget="collapse">
										<i class="fa fa-minus"></i>
									</button>
								</div>
							</div>
							<div class="box-body jp-content-select-box">
								
							</div>
						</div> -->
						<!-- /.box 
						<div class="box box-solid">
							<div class="box-header with-border">
								<h3 class="box-title">标签</h3>
								<div class="box-tools">
									<button type="button" class="btn btn-box-tool" data-widget="collapse">
										<i class="fa fa-minus"></i>
									</button>
								</div>
							</div>
							<!-- /.box-header --
							<div class="box-body no-padding ">
								<div class="nav-tabs-custom1">
									<ul class="nav">
										<li>
											<div class="form-group" style="margin-bottom: 0;">
												<textarea name="_tag" id="_tag" class="form-control  tag-editor-hidden-src">						
											</textarea><ul class="tag-editor"><li style="width:1px">&nbsp;</li></ul>
											</div>
											<div class="clr"></div>
										</li>
										<li class="jp-release-set jp-borde-top">				
											多个标签请用 “回车键” 或英文逗号 “,” 隔开。
										<div class="clr"></div>
									</li>
									</ul>
								</div>
								
							</div>
							<!-- /.box-body --
						</div>
						<!-- /.box --
						-->
						<div class="box box-solid">
							<div class="box-header with-border">
								<h3 class="box-title">缩略图</h3>
								<div class="box-tools">
									<button type="button" class="btn btn-box-tool" data-widget="collapse">
										<i class="fa fa-minus"></i>
									</button>
								</div>
							</div>
							<div class="box-body no-padding">
								<ul class="nav ">
									<li class="">
										<div class="jp-content-thumbnail-box">
											<img src="../images/nothumbnail.jpg" id="thumbnail" class="jp-content-thumbnail">
											<input type="hidden" name="content.thumbnail" value="" id="content_thumbnail">
										</div>
										<div class="clr"></div>
									</li>
									<li class="jp-release-set jp-borde-top">				
										<button type="button" class="btn btn-default btn-sm" onclick="doSelectThumbnail()">选择图片</button>
										<a href="javascript:;" style="display: inline;" onclick="doRemoveThumbnail()">移除缩略图</a>
										<div class="clr"></div>
									</li>
								</ul>
							</div>
							<!-- /.box-body -->
						</div>
						<!-- /. box -->	
					</div>
				</div>
				<!-- /.row -->
			</form>
		</div>
	</div>		<!-- /.content -->
	</div>
	<div id="popupDialog" title="添加/编辑银行账户"></div>
	<div id="tranDialog" title="添加/编辑账内划转"></div>
    <div id="notice2" title="提示消息"></div>
</body>
</html>