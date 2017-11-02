<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/content/common/taglibs.jsp"%>
<script>
	jQuery.ajaxSettings.traditional = true;
	var isSuccess = true;
	$(document).ready(function(e) {
		$("#notice").dialog({
			dialogClass : "ppisDialog",
			autoOpen : false,
			resizable : false,
			modal : true,
			buttons : {
				"确定" : function() {
					$(this).dialog('close');
					if (isSuccess) {
						$("#listForm").submit();
					}
				}
			}
		});

		$("#cancel").bind('click', function() {
			$("#popupDialog").dialog('close');
		});

		$("#inputForm").bind("submit", function() {
			var isIllegal = false;
			$("[rule]").each(function(index, element) {
				isIllegal |= testIllegal($(this));
			});
			if (isIllegal) {
				$("#errInfo").show();
				return false;
			} else {
				$("#errInfo").hide();
			}
			$("input[name='affiche.content']").val($("textarea[name='affiche.content']").val());
			var param = getParam("inputForm");
			$.ajax({
				url : $(this).attr("action"),
				type : "POST",
				data : param,
				success : function(msg) {
					isSuccess = (trim(msg) == "您的操作已成功!") ? true : false;
					$("#notice").html(msg);
					$("#notice").dialog('open');
					$(".ui-widget-overlay").css("height", document.body.scrollHeight);
				}
			});
			return false;
		});
	});
</script>
<div class="input">
    <div id="notice" title="提示消息"></div>
	<div class="pub_inp">
		<form id="inputForm" class="validate" action="${ctx}/admin/affiche!save.action" method="post">
			<table class="enforce_table" cellpadding="0" cellspacing="0" width="100%">
				<s:hidden name="affiche.id"></s:hidden>
				<s:hidden name="affiche.content"></s:hidden>
				<tr height="29">
					<td align="right" width="100" class="enforcetd_bg">公告标题:</td>
					<td align="left"><s:textfield name="affiche.title" rule="noempty" maxlength="128" cssClass="formText" onkeyup="limitTextAreaLength($(this), 70)" style="width:450px;"></s:textfield><label class="requireField">*</label></td>
				</tr>
				<tr height="29">
					<td align="right" width="100" class="enforcetd_bg">公告内容:</td>
					<td align="left"><s:textarea name="affiche.content" rule="noempty" maxlength="512" cssClass="textarea" onkeyup="limitTextAreaLength($(this), 340)" style="width:450px;height:150px;"></s:textarea><label class="requireField" style="margin-bottom:70px;">*</label></td>
				</tr>
				<tr height="29">
					<td align="right" width="100" class="enforcetd_bg">公告类型:</td>
					<td align="left"><s:select name="affiche.type" list="#{1:'一般公告',2:'紧急公告'}" /><label class="requireField">*</label></td>
				</tr>
				<tr height="29">
					<td align="right" width="100" class="enforcetd_bg">是否发布:</td>
					<td align="left"><s:select name="affiche.stat" list="#{0:'否',1:'是'}" /><label class="requireField">*</label></td>
				</tr>
			</table>
			<div style="color: red" align="center">
				<s:fielderror/>
			 	<s:actionerror/>
			</div>
			<div class="buttonArea">
				<input type="submit" class="pub_but formButton" value="确  定" hidefocus="true" />&nbsp;&nbsp;&nbsp;&nbsp;
				<input id="cancel" type="button" class="pub_but formButton" value="返  回" hidefocus="true" />
			</div>
		</form>
        <p id="errInfo" style="text-align:center;">信息填写不完整或格式错误！</p>
	</div>
</div>