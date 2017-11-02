<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/content/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 transitional//EN" "http://www.w3.org/tr/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title></title>
<link href="${ctx}/styles/main_content.css" rel="stylesheet" type="text/css" />
<%@ include file="/WEB-INF/content/common/include.jsp"%>
<script type="text/javascript">
	var _width,_height;
	$(document).ready(function() {
		//checkDogValid();
		resizeWindow();
		if ($.browser.msie){
			if(!($.browser.version.indexOf("6") == 0)){
				$(window).resize(function(){
					resizeWindow();
				});
			}
		}
		$("#afficheDialog").dialog({
			dialogClass : "ppisDialog",
			autoOpen : false,
			resizable : false,
			height : 480,
			width : 800,
			maxHeight : window.screen.height - 250,
			modal : true,
			close : function() {
				$("#afficheDialog").empty();
			}
		});
		$(".lengthnowrap").each(function(i) {
			if ($(this).text().length > 45) {
				$(this).attr("title", $(this).text());
				var text = $(this).text().substring(0, 45) + "...";
				$(this).text(text);
			}
		});
		/**
		$("#alertTime").text("告警(未上传成功)" + "   时间段：" + "<s:property value="#request.alertBeginTime"/>" + " 至 " + "<s:property value="#request.alertEndTime"/>");
		if($("#failUploadSize").val()>0){
			$("#alertCount").html("当前未成功上传六合一违法数据量：" + $("#failUploadSize").val() + "条");
		}
		*/
	});

	function showAffiche(id, createDate, userName) {
		var affichetitle = $("#affiche_id_" + id).attr("affichetitle");
		var affichecontent = $("#affiche_id_" + id).attr("affichecontent");
		var _html = "<div style=\"text-align:center;margin-top:15px;\">";
		_html = _html + "<h2>" + htmlEncode(affichetitle) + "</h2>";
		_html = _html + "</div>";
		_html = _html + "<div style=\"margin-left:15px;margin-top:15px;margin-bottom:15px;\">";
		_html = _html + htmlEncode(affichecontent).replace(new RegExp("\r\n", "gm"), "<br/>").replace(new RegExp("\n", "gm"), "<br/>");
		_html = _html + "</div>";
		_html = _html + "<div style=\"float:right;\">";
		_html = _html + "发布者：" + userName + "   发布时间：" + createDate;
		_html = _html + "</div>";
		$("#afficheDialog").html(_html).dialog("open");
	}

	function resizeWindow(){
		if (_width == $(window).width() && _height == $(window).height()) {
			return;
		}
		_width = $(window).width();
		_height = $(window).height();
		$("#alertDIV").height($(window).height() - ($("#main_content_layout_container").outerHeight(true) - $("#alertDIV").height()) - 10);
		$("#main_content_layout_container").width($(window).width() - 360);
		$("#main_content_layout_sidebar").find(".datagrid").height($(window).height() / 2 - ($("#main_content_layout_container").outerHeight(true) - $("#alertDIV").height()) - 5)
	}
</script>
</head>
<body style="background: #AFB6D1">
	<!-- 
	<input type="hidden" id="ctx" value="${ctx}" />
	<input type="hidden" id="failUploadSize" value="<s:property value="#request.failUploadList"/>" />
	<div id="notice" title="提示消息"></div>
	<div id="afficheDialog" title="公告消息"></div>
	<div class="main_content_layout" id="main_content_layout">
		<div class="main_content_layout_container" id="main_content_layout_container">
			<div class="pub_inp_bg">
				<div class="pub_inp">
					<h2 id="alertTime">告警</h2>
					<div class="datagrid" style="margin-bottom: 0;" id="alertDIV" >
						<div style="margin-left: 10px;margin-top: 10px;">
							<span id="alertCount" style="font-size:14px;font-weight:bold;"></span>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div class="main_content_layout_sidebar" id="main_content_layout_sidebar">
			<div class="pub_inp_bg">
				<div class="pub_inp">
					<h2>公告</h2>
					<div class="datagrid" style="margin-bottom: 0; min-height: 0;">
						<ul>
							<s:iterator value="afficheList" var="list" status="status">
								<li <s:if test="#status.count%2==1">class="cor"</s:if> <s:if test="#list[0].type == 2">style="color:red;"</s:if> onclick="showAffiche('<s:property value="#list[0].id"/>','<s:date name="#list[0].createDate" format="yyyy-MM-dd HH:mm:ss"/>','<s:property value="#list[0].user.name"/>')"><span>[<s:if test="#list[0].type == 1">一般公告</s:if> <s:elseif test="#list[0].type == 2">紧急公告</s:elseif> <s:else>未知</s:else>]
								</span> <span class="lengthnowrap" title="<s:property value="#list[0].title"/>"><s:property value="#list[0].title" /></span> <input type="hidden" id="affiche_id_<s:property value="#list[0].id"/>" affichetitle="<s:property value="#list[0].title"/>" affichecontent="<s:property value="#list[0].content"/>"></input></li>
							</s:iterator>
						</ul>
					</div>
				</div>
			</div>

		</div>
		<div style="clear: both;"></div>
	</div>
	-->
</body>
</html>
