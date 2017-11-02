<%@ page contentType="text/html; charset=GB2312"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<link href="css/default.css" rel="stylesheet" type="text/css" />
<title>文件上传</title>

<script type="text/javascript" src="js/swfupload.js"></script>
<script type="text/javascript" src="js/swfupload.queue.js"></script>
<script type="text/javascript" src="js/fileprogress.js"></script>
<script type="text/javascript" src="js/handlers.js"></script>
<script type="text/javascript">
		var swfu;

		window.onload = function() {
			var settings = {
				flash_url : "js/swfupload.swf",
				upload_url: "../admin/violation_enter!uploadBatchPicture.action",
				file_size_limit : "5 MB",
				file_types : "*.jpg",
				file_types_description : "All Files",
				file_upload_limit : 20,
				file_queue_limit : 0,
				custom_settings : {
					progressTarget : "fsUploadProgress",
					uploadButtonId : "btnUpload",
					cancelButtonId : "btnCancel"
				},
				debug: false,
				auto_upload:false,

				// Button settings
				button_image_url: "images/TestImageNoText_65x29.png",
				button_width: "65",
				button_height: "29",
				button_placeholder_id: "spanButtonPlaceHolder",
				button_text: '<span class="theFont">选择</span>',
				button_text_style: ".theFont { font-size: 14; }",
				button_text_left_padding: 15,
				button_text_top_padding: 3,
				
				// The event handler functions are defined in handlers.js
				file_queued_handler : fileQueued,
				file_queue_error_handler : fileQueueError,
				file_dialog_complete_handler : fileDialogComplete,
				upload_start_handler : uploadStart,
				upload_progress_handler : uploadProgress,
				upload_error_handler : uploadError,
				upload_success_handler : uploadSuccess,
				upload_complete_handler : uploadComplete,
				queue_complete_handler : queueComplete	// Queue plugin event
			};

			swfu = new SWFUpload(settings);
			document.getElementById("file_upload_limit").innerText = swfu.settings.file_upload_limit;
			document.getElementById("file_size_limit").innerText = swfu.settings.file_size_limit;
	     };
			
	     window.onunload = function() {
	    	 //clear();
	    	 //window.opener.refreshPictureList();
		 };

		function clear() {
			if(swfu){
		    	swfu.destroy();
			}
		};
	</script>
</head>

<body style="overflow-x:hidden;background-color:#DEE3F1;">
	<div style="font-weight:bold;font-size:13px;margin-top:15px;margin-left:15px;">
		说明：最多只能选择<span id="file_upload_limit" style="color:red;">20</span>张图片,每张图片大小不能超过<span id="file_size_limit" style="color:red;">10M</span>,
		           未出现&lt;选择&gt;按钮点击<a id="file_size_limit" style="color:red;text-decoration:underline;" href="../help/flash_player_active.exe">下载文件</a>
	</div>
	<form id="form1" action="../admin/violation_enter!uploadBatchPicture.action" method="post"
		enctype="multipart/form-data">
		<div class="wrap" id="content">
			<div class="titleDiv">
				<div class="titleFont">
					<img src="images/up.png" width="16" height="16" /> 文件上传
				</div>
			</div>
			<div class="content">
				<div class="fieldset flash" id="fsUploadProgress" style="border-color:#5E638D;height:300px;overflow:auto;">
					<div>
						<span class="legend" style="background-color:#5E638D;color:#fff">上传列表</span>
					</div>
				</div>
				<div id="divStatus" class="num">0 个文件上传</div>
				<div style="margin-left: 30px;">
					<span id="spanButtonPlaceHolder"></span>
					<div style="margin-top: -20px; margin-left: 60px;">
						<input id="btnUpload" type="button" value="上 传"
							onclick="swfu.startUpload();"
							style="margin-left: 20px; margin-bottom: 15px" class="pub_but" /> <input
							id="btnCancel" type="button"  value="取消上传"
							onclick="swfu.cancelQueue();"
							style="margin-left: 20px; margin-bottom: 15px" class="pub_but" />
					</div>
				</div>
			</div>
		</div>
	</form>
</body>
</html>
