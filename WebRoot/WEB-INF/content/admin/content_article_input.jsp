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
	var data={
	    url:'',
	    alt:''
	};
	
	function initTinymce(){
		tinymce.init({
	        selector: '#textarea_shine,#textarea_work,#textarea_dep',
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
				$("#wxContent_text").html(tinyMCE.activeEditor.getContent());
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
				if(data.status == "success"){
					if(data.message != "") $("#wxContent_id").attr("value",data.message);
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
	 
	function doRoundTrip(){
		layer.open({
		    type: 2,
		    title: '添加行程',
		    shadeClose: true,
		    shade: 0.8,
		    area: ['92%', '90%'],
		    content: "${ctx}/admin/content!roundtrip_layer.action",
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
		
		$("ul.nav-tabs li a").click(function(){
			if($(this).attr("href") == "#tab_seo"){
				$("#btn-roundtrip").css('display','block'); 
			}else{
				$("#btn-roundtrip").css('display','none'); 
			}
		});
		
		var url = window.location.protocol  +"//"+ window.location.host+"/jpress/c/" ;
			$("#url_preffix").text(url);
			$('#slug_text').editable('setValue'," 标题");
		   
			$('#titleurl').on('save', function(e, params) {
			$('#content_slug').attr('value',params.newValue);
		});
		
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
			<form action="${ctx}/admin/content!save.action" id="form" method="post">
				<div class="row">
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
											<input type="hidden" name="wxContent.thumbnail" value="" id="wxContent_thumbnail">
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
					
					<div class="col-md-3">
					</div>
					
					<!-- /.col -->
					<div class="col-md-6">
						<div class="form-group">
							<input name="wxContent.title" class="form-control input-lg" id="title" value="" type="text" placeholder="在此输入标题">
		                </div>
						<div class="box box-solid">
							<div class="box-body no-padding">
								<div class="tab-pane form-horizontal">
					                <div class="form-group">
					                	<label for="wxContent_price" class="col-sm-2 control-label">标准价格</label>
					                  	<div class="col-sm-10">
					                    	<input type="text" class="form-control" id="wxContent_price" name="wxContent.price" value="">
					                	</div>
					                </div>
					                <div class="form-group">
					                	<label for="wxContent_vip_price" class="col-sm-2 control-label">会员价格</label>
					                  	<div class="col-sm-10">
					                    	<input type="text" class="form-control" id="wxContent_vip_price" name="wxContent.vip_price" value="">
					                	</div>
					                </div>
					                <div class="form-group">
							            <label for="wxContent_days" class="col-sm-2 control-label">行程天数</label>
					                  	<div class="col-sm-10">
					                    	<input type="text" class="form-control" id="wxContent_days" name="wxContent.days" value="">
					                	</div>
					                </div>
				                </div>
							</div>
						</div>
						<div class="box box-solid">
							<!-- /.box-header -->
							<div class="box-body no-padding">
								<!-- Custom Tabs -->
								<div class="nav-tabs-custom">
									<ul class="nav nav-tabs">
										<li class="active"><a href="#tab_shine" data-toggle="tab">行程亮点</a></li>
										<li><a href="#tab_seo" data-toggle="tab">行程</a></li>
										<li><a href="#tab_position" data-toggle="tab">说明</a></li>
										<button type="button" id="btn-roundtrip" class="btn btn-default btn-sm" onclick="doRoundTrip()" style="float:right;margin-top:7px;margin-right:10px;display:none">新增行程</button>
									</ul>
									<div class="tab-content">
										<div class="tab-pane active " id="tab_shine">
							                <div class="box-body no-padding">
												<textarea id="textarea_shine" name="textarea_shine" ></textarea>
												<textarea id="wxContent_text" name="wxContent.text" aria-hidden="true" style="display: none; visibility: hidden;"></textarea>
											</div>
										</div>
									
										<!-- /.tab-pane -->
										<div class="tab-pane " id="tab_seo">
											<div class="box-body no-padding ">
												<textarea id="textarea_work" name="textarea_work" ></textarea>
												<textarea id="wxContent_work" name="wxContent.work" aria-hidden="true" style="display: none; visibility: hidden;"></textarea>
											</div>
										</div>
										
										<div class="tab-pane" id="tab_position">
											<div class="box-body no-padding">
												<textarea id="textarea_dep" name="textarea_dep" ></textarea>
												<textarea id="wxContent_dep" name="wxContent.dep" aria-hidden="true" style="display: none; visibility: hidden;"></textarea>
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
					
				</div>
				<!-- /.row -->
				<input type="hidden" name="wxContent.id" id="wxContent_id" value="${wxContent.id}">
			</form>
		</div>
	</div>		<!-- /.content -->

</body>
</html>