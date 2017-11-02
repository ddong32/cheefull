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
		<div class="pace  pace-inactive">
			<div class="pace-progress" data-progress-text="100%" data-progress="99" style="transform: translate3d(100%, 0px, 0px);">
				<div class="pace-progress-inner"></div>
			</div>
			<div class="pace-activity"></div>
		</div>
		
		<div class="row">
			<div class="col-md-12">
				<div class="nav-tabs-custom">
					<ul class="nav nav-tabs">
						<li><a href="#tab_1" data-toggle="tab">上传文件</a></li>
						<li class="active"><a href="#tab_2" data-toggle="tab">媒体库</a></li>
					</ul>
					<div class="tab-content">
						<div class="tab-pane" id="tab_1">
							<div class="jp-borwer-tab1">
								<div id="fine-uploader-gallery"></div><!-- fine-uploader-gallery -->
								<p class="jp-brower-filesize">最大上传文件大小100MB</p>
							</div>
						</div>

						<div class="tab-pane active" id="tab_2">
							<div class="jp-borwer">
								<ul class="list-inline list-unstyled">
									<s:iterator value="page.result" var="list" status="status">
									<li>
										<img src="${ctx}/<s:property value="#list[4]"/>" title="<s:property value="#list[1]"/>" path="${ctx}/<s:property value="#list[4]"/>" class="jp-grids-photos img-responsive">
										<div class="brower-active-icon" style="display: none">
											<i class="fa fa-check-square-o fa-2x" aria-hidden="true"></i>
										</div>
									</li>
									</s:iterator>
								</ul>
							</div>
			
							<s:if test="page.result.size() > 0">
								<div class="pagerBar clear">
									<div class="pager pub_l">
										<%@ include file="/WEB-INF/content/common/page.jsp"%>
									</div>
									<div class="bar">
										总数<s:property value="page.totalCount"/>条/第<s:property value="page.pageNo"/>页/共<s:property value="page.totalPages"/>页
									</div>
								</div>
								<ul class="pagination">
									<li class="paginate_button previous disabled">
										<a href="#" data-dt-idx="0" tabindex="0">上页</a>
									</li>
		
									<li class="paginate_button active">
										<a href="#" data-dt-idx="1" tabindex="0">1</a>
				                 	</li>
							
									<li class="paginate_button next disabled">
					                     <a href="#" data-dt-idx="2" tabindex="0">下页</a>
									</li>
								</ul>
							</s:if>
							<s:else>
								<div class="noRecord">没有找到任何记录!</div>
							</s:else>
							
						</div><!-- tab-pane active -->
					</div>    <!-- tab-content -->
				</div>
			</div>
		</div>                <!-- row -->
		
		<button class="btn btn-block btn-primary jp-submiti-button" onclick="doConfirm()"> 确&nbsp;定 </button>
		<script type="text/template" id="qq-template-gallery">
        <div class="qq-uploader-selector qq-uploader qq-gallery" qq-drop-area-text="或拖动文件到此上传">
            <div class="qq-total-progress-bar-container-selector qq-total-progress-bar-container">
                <div role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" class="qq-total-progress-bar-selector qq-progress-bar qq-total-progress-bar"></div>
            </div>
            <div class="qq-upload-drop-area-selector qq-upload-drop-area" qq-hide-dropzone>
                <span class="qq-upload-drop-area-text-selector"></span>
            </div>
            <div class="qq-upload-button-selector  btn btn-primary ">
                <div>选择文件</div>
            </div>
            <span class="qq-drop-processing-selector qq-drop-processing">
                <span>正在处理拖动的文件...</span>
                <span class="qq-drop-processing-spinner-selector qq-drop-processing-spinner"></span>
            </span>
            <ul class="qq-upload-list-selector qq-upload-list" role="region" aria-live="polite" aria-relevant="additions removals">
                <li>
                    <span role="status" class="qq-upload-status-text-selector qq-upload-status-text"></span>
                    <div class="qq-progress-bar-container-selector qq-progress-bar-container">
                        <div role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" class="qq-progress-bar-selector qq-progress-bar"></div>
                    </div>
                    <span class="qq-upload-spinner-selector qq-upload-spinner"></span>
                    <div class="qq-thumbnail-wrapper">
                        <img class="qq-thumbnail-selector" qq-max-size="120" qq-server-scale>
                    </div>
                    <button type="button" class="qq-upload-cancel-selector qq-upload-cancel">X</button>
                    <button type="button" class="qq-upload-retry-selector qq-upload-retry">
                        <span class="qq-btn qq-retry-icon" aria-label="重试"></span>
						重试
                    </button>

                    <div class="qq-file-info">
                        <div class="qq-file-name">
                            <span class="qq-upload-file-selector qq-upload-file"></span>
                            <span class="qq-edit-filename-icon-selector qq-edit-filename-icon" aria-label="Edit filename"></span>
                        </div>
                        <input class="qq-edit-filename-selector qq-edit-filename" tabindex="0" type="text">
                        <span class="qq-upload-size-selector qq-upload-size"></span>
                        <button type="button" class="qq-btn qq-upload-delete-selector qq-upload-delete">
                            <span class="qq-btn qq-delete-icon" aria-label="Delete"></span>
                        </button>
                        <button type="button" class="qq-btn qq-upload-pause-selector qq-upload-pause">
                            <span class="qq-btn qq-pause-icon" aria-label="Pause"></span>
                        </button>
                        <button type="button" class="qq-btn qq-upload-continue-selector qq-upload-continue">
                            <span class="qq-btn qq-continue-icon" aria-label="Continue"></span>
                        </button>
                    </div>
                </li>
            </ul>

            <dialog class="qq-alert-dialog-selector">
                <div class="qq-dialog-message-selector"></div>
                <div class="qq-dialog-buttons">
                    <button type="button" class="qq-cancel-button-selector">Close</button>
                </div>
            </dialog>

            <dialog class="qq-confirm-dialog-selector">
                <div class="qq-dialog-message-selector"></div>
                <div class="qq-dialog-buttons">
                    <button type="button" class="qq-cancel-button-selector">No</button>
                    <button type="button" class="qq-ok-button-selector">Yes</button>
                </div>
            </dialog>

            <dialog class="qq-prompt-dialog-selector">
                <div class="qq-dialog-message-selector"></div>
                <input type="text">
                <div class="qq-dialog-buttons">
                    <button type="button" class="qq-cancel-button-selector">Cancel</button>
                    <button type="button" class="qq-ok-button-selector">Ok</button>
                </div>
            </dialog>
        </div>
		</script>
	
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
	
		$(document).ready(function(){
			$(".img-responsive").click(function(){
				doChoose($(this).attr("path"),$(this).attr("alt"));
				 
				$(".list-inline li").removeClass("jp-borwer-active-li");
				$(".brower-active-icon").hide();
				
				$(this).parent().addClass("jp-borwer-active-li");
				$(this).next().show();
			});
		});
	
		$('#fine-uploader-gallery').fineUploader({
	        template: 'qq-template-gallery',
	        request: {
	            endpoint: '${ctx}/admin/attachment!doUpload.action'
	        },
	        thumbnails: {
	            placeholders: {
	                waitingPath: '${ctx}/scripts/wechat/plugins/fine-uploader/placeholders/waiting-generic.png',
	                notAvailablePath: '${ctx}/scripts/wechat/plugins/fine-uploader/placeholders/not_available-generic.png'
	            }
	        },
			callbacks: {
				onComplete: function(id, name, response) {
             		doChoose(response.src,'');
				}
			}
		});
		</script>
	
	</body>
</html>