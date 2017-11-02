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
	<script type="text/javascript">
		window.onunload = function() {
			try{
				var explorer =navigator.userAgent ;
				if (explorer.indexOf("MSIE") >= 0) {
					window.dialogArguments.auditRefresh();
				}else if(explorer.indexOf("Chrome") >= 0){
					window.opener.auditRefresh();
				}
			}catch(e){
			}
		};
	</script>
</head>
<body class="skin-blue  pace-done">
	<div class="content-wrapper" style="min-height: 697px;">
	<section class="content-header">
		<h1></h1>
	</section>

	<!-- Main content -->
	<section class="content">
		<div class="col-xs-12  upload-box ">
			
			<div class="row"><div class="col-md-offset-3 col-md-6  col-xs-12"><div id="fine-uploader-gallery"><div class="qq-uploader-selector qq-uploader qq-gallery" qq-drop-area-text="或拖动文件到此上传">
	            
	            <div class="qq-total-progress-bar-container-selector qq-total-progress-bar-container qq-hide">
	                <div role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" class="qq-total-progress-bar-selector qq-progress-bar qq-total-progress-bar"></div>
	            </div>
	            <div class="qq-upload-drop-area-selector qq-upload-drop-area" qq-hide-dropzone="" style="display: none;">
	                
	            </div>
	            <div class="qq-upload-button-selector btn btn-primary" style="position: relative; overflow: hidden; direction: ltr;">
	                <div>选择文件</div>
	            <input qq-button-id="589ec3ac-2f81-463d-b767-b23160c02ddc" title="file input" multiple="" type="file" name="qqfile" style="position: absolute; right: 0px; top: 0px; font-family: Arial; font-size: 118px; margin: 0px; padding: 0px; cursor: pointer; opacity: 0; height: 100%;"></div>
	            <span class="qq-drop-processing-selector qq-drop-processing qq-hide">
	                <span>正在处理拖动的文件...</span>
	                <span class="qq-drop-processing-spinner-selector qq-drop-processing-spinner"></span>
	            </span>
	            <ul class="qq-upload-list-selector qq-upload-list" role="region" aria-live="polite" aria-relevant="additions removals"></ul>
	
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
	            
	    	</div></div></div></div>
	
			<p class="upload-text">最大上传文件大小：200 MB。</p>
		
		</div>
	</section>

	<script type="text/template" id="qq-template-gallery">
		<div class="qq-uploader-selector qq-uploader qq-gallery" qq-drop-area-text="或拖动文件到此上传">
            <div class="qq-total-progress-bar-container-selector qq-total-progress-bar-container">
                <div role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" class="qq-total-progress-bar-selector qq-progress-bar qq-total-progress-bar"></div>
            </div>
            <div class="qq-upload-drop-area-selector qq-upload-drop-area" qq-hide-dropzone>
                <span class="qq-upload-drop-area-text-selector"></span>
            </div>
            <div class="qq-upload-button-selector btn btn-primary ">
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
                        <span class="qq-btn qq-retry-icon" aria-label="重试"></span>重试
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
		
		<script>
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
	             		if(!response.success) alert(response.message);
					}
				}
	        });
		</script>
	</div>
</body>
</html>