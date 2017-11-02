var isSuccess = false;	// 是否提交成功 
var g_property = {
		picNameId : "picName",					// 图片名称模板配置ID
		picPathId : "picPath",					// 图片路径模板配置ID
		compressPicPathId : "compressPicPath",	// 压缩图片路径模板配置ID
		SMSModelId : "SMSModel",				// 短信模板配置
		picNameType : 1,						// 图片名称模板类型
		picPathType : 2,						// 图片路径模板类型
		compressPicPathType : 4,				// 压缩图片路径模板类型（4与html压缩图片路径tab页id对应）
		cursor : null							// 光标位置
};
var canSubmit = false;	//是否允许保存
$(document).ready(function(){
	
	// 普通提示框初始化
	$("#notice").dialog({
		dialogClass:"ppisDialog",
		autoOpen: false,
		resizable:false,
		modal: true,
		buttons: {
			"确定" : function(){
				$(this).dialog("close");
			}
		}
	});
	
	// 提交页面后弹出框初始化
	$("#submitNotice").dialog({
		dialogClass:"ppisDialog",
		autoOpen: false,
		resizable:false,
		modal: true,
		buttons: {
			"确定" : function(){
				if (isSuccess) {
					location.reload();
				} else {
					$(this).dialog("close");
				}
			}
		}
	});
	
	// 点击已选模板行选中该模板
	$("#content_1 .pub_r li, #content_2 .pub_r li, #content_3 .pub_r li, " +
			"#content_4 .pub_r li, #templet_li li").bind("click", function(event) {
		var $_target = $(event.target);
		$_target.closest("ul").find("li").removeClass("selected");
		$_target.closest("li").addClass("selected");
	});
	
	// 向上移动
	$("#content_1 .btn_up, #content_2 .btn_up, #content_4 .btn_up").bind("click", function(event) {
		var $_content = null;
		var updateId = "";
		
		//	如果图片名称配置显示
		if ($("#content_1").is(":visible")) {
			$_content = $("#content_1");
			updateId = g_property.picNameId;
			
		} else if ($("#content_2").is(":visible")) {	//	如果图片文件路径显示
			$_content = $("#content_2");
			updateId = g_property.picPathId;
		} else {	// 如果压缩图片路径显示
			$_content = $("#content_4");
			updateId = g_property.compressPicPathId;
		}
		var selectedLine = $(".pub_r ul li.selected", $_content);
		var prevLine  = selectedLine.prev("li:last");
		
		//	如果选中行为第一行，则不向上移动
		if (prevLine.length != 0) {
			selectedLine.clone(true).insertBefore(prevLine);
			selectedLine.remove();
		}
		update(updateId);
	});
	
	// 向下移动
	$("#content_1 .btn_down, #content_2 .btn_down, #content_4 .btn_down").bind("click", function(event) {
		var $_content = null;
		var updateId = "";
		//	如果图片名称配置显示
		if ($("#content_1").is(":visible")) {
			$_content = $("#content_1");
			updateId = g_property.picNameId;
			
		} else if ($("#content_2").is(":visible")) { //	如果图片文件路径显示
			$_content = $("#content_2");
			updateId = g_property.picPathId;
		} else {
			$_content = $("#content_4");
			updateId = g_property.compressPicPathId;
		}
		var selectedLine = $(".pub_r ul li.selected", $_content);
		var nextLine  = selectedLine.next("li:first");
		
		//	如果选中行为最后一行，则不向下移动
		if (nextLine.length != 0) {
			selectedLine.clone(true).insertAfter(nextLine);
			selectedLine.remove();
		}
		update(updateId);
	});
	
	//	点击选中可选模板字段后增加到已选模板字段（图片名称和图片路径）
	$("#content_1 .pub_l ul :checkbox, #content_2 .pub_l ul :checkbox, #content_4 .pub_l ul :checkbox").
			bind("click", function(event) {
		var $_content = null;
		var updateId = "";
		
		//	如果图片名称配置显示
		if($("#content_1").is(":visible"))
		{
			$_content = $("#content_1");
			updateId = g_property.picNameId;
			
		} else if ($("#content_2").is(":visible")){ //	如果图片文件路径显示
			$_content = $("#content_2");
			updateId = g_property.picPathId;
		} else {
			$_content = $("#content_4");
			updateId = g_property.compressPicPathId;
		}
		var $_target = $(event.target);
		
		//	如果选中模板字段
		if ($_target.is(":checked")) {
			
			var text = $_target.closest("li").text();
			text = $.trim(text);
			var name = $_target.closest("li").find("input").attr("name");
			var templet = $("#templet_li li").clone(true);
			templet.find(".mid").text(text);
			templet.find(".mid").attr("name", name);
			$(".pub_r ul", $_content).append(templet);
			
		} else { //	如果取消勾选该模板字段
			var text = $_target.closest("li").text();
			var name = $_target.closest("li").find("input").attr("name");
			$(".pub_r ul", $_content).find("label[name=" + name + "]").closest("li").remove();
		}
		
		// 如果为图片名称模板或则图片路径模板则更新
		if(updateId != null) {
			update(updateId);
		}
	});
	
	//	短信配置点击可选模板字段自动添加到短信模板里去
	$("#content_3 .pub_l ul :checkbox").bind("click", function(event) {
		var $_target = $(event.target);
		//	如果选中模板字段,则在模板里插入字段
		if ($_target.is(":checked")) {
			if (g_property.cursor === null) {
				$("#notice").html("请先选择插入的位置(鼠标点击选择)。").dialog("open");
				$(this).removeAttr("checked");
				return;
			}
			var text = $_target.closest("li").text();
			g_property.cursor.text = text;
			g_property.cursor.select();
			
		} else { //	如果取消勾选该模板字段，则清空模板里该字段
			if (g_property.cursor === null && document.selection) {
				g_property.cursor = document.selection.createRange();
				var noteArea = $("#" + g_property.SMSModelId + "R").text();
				var text = $_target.closest("li").text();
				
				//	删除文字所在文本位置
				var index = noteArea.indexOf(text);
				$("#" + g_property.SMSModelId + "R").text(noteArea.replace(text,""));
				
				//	光标移动到删除字段所在位置
				g_property.cursor.moveStart("character", index);
				g_property.cursor.collapse(true);
				g_property.cursor.select();
				g_property.cursor = null;
				return;
			}
			var noteArea = $("#" + g_property.SMSModelId + "R").text();
			var text = $_target.closest("li").text();
			
			//	删除文字所在文本位置
			var index = noteArea.indexOf(text);
			$("#" + g_property.SMSModelId + "R").text(noteArea.replace(text,""));
			
			//	光标移动到删除字段所在位置
			g_property.cursor.moveStart("character", index);
			g_property.cursor.collapse(true);
			g_property.cursor.select();
		}	
	});
	
	//	鼠标移开已选模板字段文本框时自动更新模板名称
	$("#" + g_property.picNameId + "R input, #" + g_property.picPathId + "R input," +
			" #" + g_property.compressPicPathId + "R input, #templet_li input").bind("blur", function() {
		update();
	});
	
	//	获得默认图片名称模板配置
	$("#" + g_property.picNameId + "Default").bind("click", function(){
		getDefaultConfig(1, $("#ctx").val() + "/admin/basic_templet!viewDefault.action");
	});
	
	//	获得默认图片路径模板配置
	$("#" + g_property.picPathId + "Default").bind("click", function(){
		getDefaultConfig(2, $("#ctx").val() + "/admin/basic_templet!viewDefault.action");
	});
	
	//	获得默认压缩图片路径模板配置
	$("#" + g_property.compressPicPathId + "Default").bind("click", function(){
		getDefaultConfig(3, $("#ctx").val() + "/admin/basic_templet!viewDefault.action");
	});
	
	//  保存
	$("#confirm").bind("click", function(){
		var picNameList = [], picPathList = [], compressPicPathList = [],
			flag = true;	// 判断提交内容是否含有非法字符
		var MAX_SMS = 300;	// 短信配置最长长度。	
		// 校验图片名称已选模板特殊字符
		$("#" + g_property.picNameId + "R ul li").each(function(index, domEle) {
			var pre = $(domEle).find(".pre").val();
			var mid = $(domEle).find(".mid").text();
			var aft = $(domEle).find(".aft").val();
			if (checkYHOfEn((pre+aft)) || checkJH(pre) || checkJH(aft) || checkMYF(pre) || checkMYF(aft) || !(checkFolderNameValid(pre)) || !(checkFolderNameValid(aft))) {
				$("#notice").html("图片文件名模板字段限制输入\#$/:'*?<>|，请重新输入。").dialog("open");
				flag =  false;
			}
			picNameList.push(pre + mid + aft);
		});
		if (flag === false) {
			return;
		}
		
		// 校验图片路径已选模板特殊字符
		$("#" + g_property.picPathId + "R ul li").each(function (index, domEle) {
			var pre = replaceBlankOfSlash($(domEle).find(".pre").val());
			var mid = $(domEle).find(".mid").text();
			var aft = replaceBlankOfSlash($(domEle).find(".aft").val());
			if (checkYHOfEn((pre+aft)) || checkJH(pre) || checkJH(aft) || checkMYF(pre) || checkMYF(aft)|| !(checkPathNameValid(pre)) || !(checkPathNameValid(aft))) {
				$("#notice").html("图片文件路径模板字段限制输入\#$/:'*?<>|，请重新输入。").dialog("open");
				flag = false;
			}
			picPathList.push(pre + mid + aft);
		});
		if (flag === false) {
			return;
		}
		
		// 校验压缩图片路径已选模板特殊字符
		$("#" + g_property.compressPicPathId + "R ul li").each(function (index, domEle) {
			var pre = replaceBlankOfSlash($(domEle).find(".pre").val());
			var mid = $(domEle).find(".mid").text();
			var aft = replaceBlankOfSlash($(domEle).find(".aft").val());
			if (checkYHOfEn((pre+aft)) || checkJH(pre) || checkJH(aft) || checkMYF(pre) || checkMYF(aft)|| !(checkPathNameValid(pre)) || !(checkPathNameValid(aft))) {
				$("#notice").html("压缩图片路径模板字段限制输入\#$/:'*?<>|，请重新输入。").dialog("open");
				flag = false;
			}
			compressPicPathList.push(pre + mid + aft);
		});
		if (flag === false){
			return;
		}
		
		var picNameTemplet = picNameList.join("$");
		var picPathTemplet = picPathList.join("$");
		var compressPicPathTemplet = compressPicPathList.join("$");
		
		// 验证文件名长度
		if (picNameList.join("").length > 180) {
			$("#notice").html("图片名称模板设置过长。").dialog("open");
			return;
		}else if(picNameList.join("").length <= 0) {
			$("#notice").html("图片名称模板设置不能为空。").dialog("open");
			return;
		}
		
		if (picPathList.join("").length > 180) {
			$("#notice").html("图片路径模板设置过长。").dialog("open");
			return;
		}else if(picPathList.join("").length <= 0) {
			$("#notice").html("图片路径模板设置不能为空。").dialog("open");
			return;
		}
		
		if (compressPicPathList.join("").length > 180) {
			$("#notice").html("压缩图片路径模板设置过长。").dialog("open");
			return;
		}else if(compressPicPathList.join("").length <= 0) {
			$("#notice").html("压缩图片路径模板设置不能为空。").dialog("open");
			return;
		}
		//	短信已选模板
		var SMS = $.trim($("#" + g_property.SMSModelId + "R").text());
		if(null == SMS || "" == SMS) {
			$("#notice").html("违法短信内容配置不能为空。").dialog("open");
			return;
		}
		if (SMS.length > MAX_SMS) {
			$("#notice").html("短信配置最大只能输入300个字符。").dialog("open");
			return;
		}
		
		//	短信通知模式
		var SMSModel = $("#" + g_property.SMSModelId + " option:selected").val();
		var paramList = [];
		paramList.push({ key: "strPicNameMode", value: picNameTemplet});
		paramList.push({ key: "strPicPathMode", value: picPathTemplet});
		paramList.push({ key: "strCompressPicPathMode", value: compressPicPathTemplet});
		paramList.push({ key: "SMS", value: SMS});
		paramList.push({ key: "SMSModel", value: SMSModel});
		var paramObject = new Object();
		paramObject.paramJson = JSON.stringify(paramList);
		
		// 保存模板配置
		$.ajax({
			url: $("#templetForm").attr("action"),
			type: "POST",
			data: paramObject,
			success: function (msg) {
				isSuccess = true;
				$("#submitNotice").html(msg).dialog("open");
				return;
			}
		});
	});
	
	//  保存光标位置
	$("#" +g_property.SMSModelId + "R").bind("click", function(){
		addComma(this);
	});
	
	//	在可选择模板中选中已选择的模板字段
	init(g_property.picNameId);
	init(g_property.picPathId);
	init(g_property.compressPicPathId);
	init(g_property.SMSModelId);
	
	//	初始化短信通知模式
	var fieldSMSModel = $("#fieldSMSModel").val();
	$("#SMSModel option[value='" + fieldSMSModel + "']").attr("selected", "selected");
	
	//	更新模板
	update(g_property.picNameType);
	update(g_property.picPathType);
	update(g_property.compressPicPathType);
});

/*
 * 切换tab页
 * @author xupeipei2@dahuatech.com 21517
 * @date   下午1:34:52
 */
function show(n) {
	$(".tab", $("#templet_tab")).removeClass("tabon").addClass("tabout");
	$("#tab_" + n).removeClass("tabout").addClass("tabon");
	$(".content").hide();
	$("#content_" + n).show();
}
/*
 * 更新图片名称 id: picName或图片路径 id: picPath
 * @author xupeipei2@dahuatech.com 21517
 * @date   上午11:34:21
 */
function update(templateType) {
	var id = "";
	
	if(templateType === g_property.picNameType) {
		id = g_property.picNameId;
	} else if (templateType === g_property.picPathType) {
		id = g_property.picPathId;
	} else if (templateType === g_property.compressPicPathType) {
		id = g_property.compressPicPathId;
	} else {
		if ($("#content_1").is(":visible")) {
			id = g_property.picNameId;
		} else if ($("#content_2").is(":visible")) {
			id = g_property.picPathId;
		} else if ($("#content_4").is(":visible")) {
			id = g_property.compressPicPathId;
		}
	}
	var name = "";
	$("#" + id + "R ul li").each(function(index, domEle){
		var pre = $(domEle).find(".pre").val();
		var mid = $(domEle).find(".mid").text();
		var aft = $(domEle).find(".aft").val();
		name = name + pre + mid + aft;
	});
	$("#" + id).html(htmlEncode(name));
}

/*
 * 在可选择模板中选中已选择的模板字段
 * @author xupeipei2@dahuatech.com 21517
 * @date   上午10:38:20
 */
function init(id)
{
	if (id === g_property.SMSModelId) {
		$("#initSMSModelDiv input").each(function(index, domEle) {
			var codeCode = $(domEle).attr("id");
			$("#" + id + "L ul li :checkbox[name=" + codeCode + "]").attr("checked", "checked");
		});
		return;
	}
	$("#" + id + "R ul li").each(function(index, domEle) {
		var name = $(domEle).find("label.mid").attr("name");
		$("#" + id + "L ul li :checkbox[name=" + name + "]").attr("checked", "checked");
	});
}

/*
 * 获得默认配置 templetType:0图片名称模板；1:图片路径模板
 * @author xupeipei2@dahuatech.com 21517
 * @date   上午10:57:18
 */
function getDefaultConfig(templateType, url){
	var paramObject = new Object();
	paramObject.templateType = templateType;
	$.ajax({
        type: "POST",
        url: url,
        data: paramObject,
		async: true,
        success: function(msg){
        	var defaultTemplate = JSON.parse(msg);
        	var $html = $("<ul></ul>");
        	for (var i = 0; i < defaultTemplate.length; i++) {
        		var pre = defaultTemplate[i].preStr;
        		var text = defaultTemplate[i].middleStr;
        		var name = defaultTemplate[i].codeCode;
        		var aft = defaultTemplate[i].afterStr;
    			var templet = $("#templet_li li").clone(true);
    			templet.find(".pre").val(pre);
    			templet.find(".mid").text(text);
    			templet.find(".mid").attr("name", name);
    			templet.find(".aft").val(aft);
    			$html.append(templet);
        	}
        	var id = "";
        	
        	// 如果是图片名称模板
        	if (templateType === 1) {
        		id = g_property.picNameId;
        		
        	} else if (templateType === 2) { // 如果是图片路径模板
        		id = g_property.picPathId;
        		
        	} else if (templateType === 3) {	// 如果是压缩图片路径模板
        		id = g_property.compressPicPathId;
        	}
        	
        	// 更新右边的模板
        	$("#" + id + "R").html($html);
        	
        	// 更新上面的模板配置
        	update(templateType);
        	
        	// 去掉所有选中的模板字段
        	$("#" + id + "L ul li :checkbox").removeAttr("checked");
        	
        	// 更新选中左边的模板字段
        	init(id);
        },
		complete: function(){
		},
        error:function(){
        }
    });
}

// 保存光标位置
function addComma(obj){
	if (document.selection) {
		g_property.cursor = document.selection.createRange();
	}
}