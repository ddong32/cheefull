<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/content/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 transitional//EN" "http://www.w3.org/tr/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="height:100%;">
<HEAD>
	<title>微信配置</title>
	<%@ include file="/WEB-INF/content/common/include.jsp"%>
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
		//提醒
		$("#notice1").dialog({
			dialogClass:"ppisDialog",
			autoOpen: false,
			resizable:false,
			modal: true,
			buttons: {
				"确定" : function(){
					$(this).dialog('close');
				}
			}
		});
		$("#cancel").bind('click',function (){
			$( "#popupDialog" ).dialog('close');
		});
		$("#inputForm").bind("submit", function(){
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
			var param=getParam("inputForm");
			$.ajax({
				url: $(this).attr("action"),
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
		
		showinfo();
	});
	
	function showinfo(){
		$.ajax({
			url : "${ctx}/admin/wechat!ajaxLoadOption.action",
			type: "POST",
			async: false,
			success: function(msg){
				if (msg == "") {
					return;
				}
				try{
					var jsonObj = jQuery.parseJSON(msg)[0];
					if(jsonObj.success == "true"){
						$("#inputForm #appid").val(jsonObj.wechat_appid);
						$("#inputForm #appsecret").val(jsonObj.wechat_appsecret);
						$("#inputForm #token").val(jsonObj.wechat_token);
					}else{
						alert(jsonObj.message);
					}
				}catch(e){
					//eval(msg.replace("<script>","").replace("<//script>",""));
					alert(e);
					return;
				}
			}
		});
	}
	</script>
</HEAD>
<body class="con_r">
    <div class="input">
        <div id="notice" title="提示消息"></div>
        <div id="notice1" title="提示消息1"></div>
        <form id="inputForm" class="validate" action="${ctx}/admin/wechat!saveOption.action" method="post">
            <div class="pub_inp_bg">
                <div class="pub_inp">
                    <table class="enforce_table" cellpadding="0" cellspacing="0" width="100%">
                        <s:hidden name="wxOption.id" id="id"></s:hidden>
                        <colgroup>
                            <col width="100"/>
                            <col/>
                        </colgroup>
                        <tr height="36">
                        	<td align="right" class="enforcetd_bg">微信AppID</td>
                            <td align="left"><input text="text" name="wxOption.appid" id="appid" rule="noempty" cssClass="formText" style="width:300px"/></td>
                        </tr>
                        <tr height="36">
                        	<td align="right" class="enforcetd_bg">微信APPSecret</td>
                            <td align="left"><input text="text" name="wxOption.appsecret" id="appsecret" rule="noempty" cssClass="formText" style="width:300px"/></td>
                        </tr>
                        <tr height="36">
                            <td align="right" class="enforcetd_bg">微信 Token</td>
                            <td align="left"><input text="text" name="wxOption.token" id="token" rule="noempty" cssClass="formText" style="width:300px"/></td>
                        </tr>
                    </table>
                    <div style="color: red" align="center">
                        <s:fielderror/>
                        <s:actionerror/>
                    </div>
                </div>
            </div>
            <div class="buttonArea_l">
                <input style="margin:0px;" type="submit" class="pub_but formButton" value="确  定" hidefocus="true" />&nbsp;
            </div>
        </form>
        <p id="errInfo" style="text-align:center;">信息填写不完整或格式错误！</p>
	</div>
	
	<!-- 
	<div class="content-wrapper" style="min-height: 918px;">
		<section class="content-header">
			<h1>微信设置1</h1>
		</section>

		Main content
		<section class="content">
			<p class="content-msg"> 通过JPress的微信设置，让JPress网站和微信公众号互动互通。</p>
		
			<form action="/jpress/admin/option/save" method="POST" id="form">
				<input type="hidden" name="ucode" value="81c9965c3ce5ae8ef6a2ce43db78ad02"> 
				<input type="hidden" name="autosave" value="wechat_appid,wechat_appsecret,wechat_token,wechat_dkf_enter_key,wechat_dkf_quit_key,wechat_search_article_enable,
		wechat_search_page_enable,
		">
				<div class="row">
					<div class="col-xs-12">
						<h4 class="jp-set-title">常规选项</h4>
						<ul class="list-unstyled">
							<li class="jp-set-pad">
								<div class="col-sm-3 ">
									<div class="  jp-set-info ">微信AppID</div>
								</div>
								<div class="col-sm-9  jp-set-title-line">
									<div class="col-sm-6 jp-table-distance-top ">
										<input class="form-control input-md" type="text" name="wechat_appid" value="">
									</div>
								</div>
								<div class="clr"></div>
							</li>
		
							<li class="jp-set-pad">
								<div class="col-sm-3 ">
									<div class="  jp-set-info ">微信APPSecret</div>
								</div>
								<div class="col-sm-9  jp-set-title-line">
									<div class="col-sm-6 jp-table-distance-top ">
										<input class="form-control input-md" type="text" name="wechat_appsecret" value="">
									</div>
								</div>
								<div class="clr"></div>
							</li>
		
							<li class="jp-set-pad">
								<div class="col-sm-3 ">
									<div class="  jp-set-info ">微信 Token</div>
								</div>
								<div class="col-sm-9  jp-set-title-line">
									<div class="col-sm-6 jp-table-distance-top ">
										<input class="form-control input-md" type="text" name="wechat_token" value="">
									</div>
								</div>
								<div class="clr"></div>
							</li>
						</ul>
					</div>
					 /.box 
				</div>
		
				<div class="row">
					<div class="col-xs-12">
						<h4 class="jp-set-pad jp-set-title">多客服</h4>
						<ul class="list-unstyled">
		
							<li class="jp-set-pad">
								<div class="col-sm-3 ">
									<div class="  jp-set-info ">进入多客服关键字</div>
								</div>
								<div class="col-sm-9  jp-set-title-line">
									<div class="col-sm-6 jp-table-distance-top ">
										<input class="form-control input-md" type="text" name="wechat_dkf_enter_key" value="">
									</div>
								</div>
								<div class="clr"></div>
							</li>
		
							<li class="jp-set-pad">
								<div class="col-sm-3 ">
									<div class="  jp-set-info ">退出多客服关键字</div>
								</div>
								<div class="col-sm-9  jp-set-title-line">
									<div class="col-sm-6 jp-table-distance-top ">
										<input class="form-control input-md" type="text" name="wechat_dkf_quit_key" value="">
										默认，如果用户5分钟未咨询，也会自动退出多客服。
									</div>
								</div>
								<div class="clr"></div>
							</li>
		
						</ul>
		
					</div>
				</div>
		
				<div class="row">
					<div class="col-xs-12">
						<h4 class="jp-set-pad jp-set-title">搜索</h4>
						 
						<ul class="list-unstyled">
							<li class="jp-set-pad">
								<div class="col-sm-3  jp-set-info">文章搜索功能开启</div>
								<div class="col-sm-9  jp-set-title-line">
									<div class="col-sm-6 jp-table-distance-top ">
										<label>
										<input type="checkbox" value="true" name="wechat_search_article_enable">开启
										</label>
									</div>
								</div>
								<div class="clr"></div>
							</li>
							<li class="jp-set-pad">
								<div class="col-sm-3  jp-set-info">文章 搜素前缀</div>
								<div class="col-sm-9  jp-set-title-line">
									<div class="col-sm-6 jp-table-distance-top ">
										<input class="form-control input-md" type="text" name="wechat_search_article_prefix">
									</div>
								</div>
								<div class="clr"></div>
							</li>
		
							<li class="jp-set-pad">
								<div class="col-sm-3  jp-set-info">文章 搜索结果条数</div>
								<div class="col-sm-9  jp-set-title-line">
									<div class="col-sm-6 jp-table-distance-top ">
										<input class="form-control input-md" type="text" name="wechat_search_article_count">
									</div>
								</div>
								<div class="clr"></div>
							</li>
						</ul>
						
						<ul class="list-unstyled">
							<li class="jp-set-pad">
								<div class="col-sm-3  jp-set-info">页面搜索功能开启</div>
								<div class="col-sm-9  jp-set-title-line">
									<div class="col-sm-6 jp-table-distance-top ">
										<label>
										<input type="checkbox" value="true" name="wechat_search_page_enable">开启
										</label>
									</div>
								</div>
								<div class="clr"></div>
							</li>
							<li class="jp-set-pad">
								<div class="col-sm-3  jp-set-info">页面 搜素前缀</div>
								<div class="col-sm-9  jp-set-title-line">
									<div class="col-sm-6 jp-table-distance-top ">
										<input class="form-control input-md" type="text" name="wechat_search_page_prefix">
									</div>
								</div>
								<div class="clr"></div>
							</li>
		
							<li class="jp-set-pad">
								<div class="col-sm-3  jp-set-info">页面 搜索结果条数</div>
								<div class="col-sm-9  jp-set-title-line">
									<div class="col-sm-6 jp-table-distance-top ">
										<input class="form-control input-md" type="text" name="wechat_search_page_count">
									</div>
								</div>
								<div class="clr"></div>
							</li>
						</ul>
						 
					</div>
				</div>
				<div class="row">
					<div class="col-xs-12">
						<button type="button" onclick="doSubmit()" class="btn btn-primary">保存更改</button>
					</div>
				</div>
				 /.row 
			</form>
		</section>
		 /.content 
	</div>
	 -->
</body>
</html>