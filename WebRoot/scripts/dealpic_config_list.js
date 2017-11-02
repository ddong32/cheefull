var isSuccess = false;	// 提交页面是否成功
$(document).ready(function (e) {
	
	// 普通弹出窗口初始化
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
	
	// 初始化OSD配置dialog
	$("#OSDConfigDialog").dialog({
		dialogClass:"ppisDialog",
		autoOpen: false,
		resizable: false,
		height:725,
		width:1050,
		maxHeight: window.screen.height-250,
		modal: true,
		close: function() {
		}
	});	
	
	//	是否需要处理图片
	var needDealPic = $("#NeedDealPic").val();
	$(":radio[name='needDealPic'][value='" + needDealPic + "']").attr("checked", "checked");
	switchNeedDealPic(needDealPic);
	
	//	三张违章图片合成模式
	var threePicMode = $("#Bind3Type").val();
	switch (threePicMode) {
	
		//	田字型
		case "0" : 
			updatePicCompMode("compose1", "threePicMode");
			break;
			
		//	横排
		case "1" :
			updatePicCompMode("compose2", "threePicMode");
			break;
			
		//	纵排	
		case "2" :
			updatePicCompMode("compose3", "threePicMode");
			break;
			
		//	不合成仅压缩
		case "3" :
			updatePicCompMode("compose4", "threePicMode");
			break;	
		// 无default
	}
	
	//	田字型合成特写
	var fieldCompFeature = $("#BindMode").val();
	$("#fieldCompFeature").find("option[value=" + fieldCompFeature + "]").attr("selected", "selected");
	
	//	车牌未标识合成配置
	var plateUnknownCompCfg = $("#NoCarNumDeal").val();
	$("#plateUnknownCompCfg").find("option[value=" + plateUnknownCompCfg + "]").attr("selected", "selected");
	
	//	两张违章图片合成模式
	var doublePicMode = $("#Bind2Type").val();
	switch (doublePicMode) {
	
		//	不处理
		case "0" :
			updatePicCompMode("compose5", "doublePicMode");
			break;
			
		//	横排
		case "1" :
			updatePicCompMode("compose7", "doublePicMode");
			break;
			
		//	纵排	
		case "2" :
			updatePicCompMode("compose6", "doublePicMode");
			break;
			
		//	仅压缩
		case "3" :
			updatePicCompMode("compose4", "doublePicMode");
			break;
		// 无default
	}
	
	//	图片大小上限
	var maxPicLength = $("#MaxPicLength").val();
	$("#maxPicLength").val(maxPicLength);
	
	//	图片质量
	var picQuality = $("#BindRatio").val();
	$("#picQuality").val(picQuality);
	
	//	OSD是否配置
	var OSDEnable = $("#OSDEnable").val();
	
	//	如果OSD设置为0则隐藏按钮
	if (OSDEnable == 0) {
		$("#OSDConfig").hide();
	}
	$(":radio[name='OSDEnable'][value='" + OSDEnable + "']").attr("checked", "checked");
	
	//	设置字体大小
	$("#OSDFontSize").val($("#TextSize").val());
	
	//	选中字体颜色
	$("#OSDFontColor option[value='" + $("#TextColor").val() + "']").attr("selected", "selected");
	
	//	存放初始化的已选模板
	var picNameList = [];
	$("#picNameR ul li").each(function(index, domEle){
		var paramObject = new Object();
		paramObject.preStr = $(domEle).find(".pre").val();
		paramObject.middleStr = $(domEle).find(".mid").text();
		paramObject.codeCode = $(domEle).find(".mid").attr("name");
		paramObject.afterStr = $(domEle).find(".aft").val();
		picNameList.push(paramObject);
	});
	$("#OSDConfigValue").data("OSDInfo", picNameList);
	$("#OSDConfigValue").data("TextColor", $("#OSDFontColor option:checked").val());
	$("#OSDConfigValue").data("TextSize", $("#OSDFontSize").val());
	
	// 提交页面保存
	$("#confirm").bind("click", function() {
		var paramList = [];
		
		//	是否需要处理图片
		var NeedDealPic = $(":radio[name='needDealPic']:checked").val();
		paramList.push({ key: "NeedDealPic", value: "" + NeedDealPic});
		
		//	如果需要处理图片则提交以下参数
		if (NeedDealPic == 1) {
			
			//	3张图片合成类型
			var Bind3Type = $("#threePicMode li.selected").val();
			paramList.push({ key: "Bind3Type", value: "" + Bind3Type});
			
			//	如果为田子型
			if (Bind3Type == 0) {
				
				//	合成特写
				var BindMode = $("#fieldCompFeature option:checked").val();
				paramList.push({ key: "BindMode", value: "" + BindMode});
				
				//	车牌未识别合成类型
				var NoCarNumDeal = $("#plateUnknownCompCfg option:checked").val();
				paramList.push({ key: "NoCarNumDeal", value: "" + NoCarNumDeal});
			}
			
			//	2张图片合成类型
			var Bind2Type = $("#doublePicMode li.selected").val();
			paramList.push({ key: "Bind2Type", value: "" + Bind2Type});
			
			//	图片大小上限
			var MaxPicLength = $("#maxPicLength").val();
			if (fieldIsEmpty(MaxPicLength) == true || filedIsNumber(MaxPicLength) == false ) {
				$("#notice").html("图片大小上限不能为空且只能为正整数。").dialog("open");
				return;
			}
			paramList.push({ key: "MaxPicLength", value:"" +  MaxPicLength});
			
			//	合成质量
			var BindRatio = $("#picQuality").val();
			if (fieldIsEmpty(BindRatio) == true || filedIsNumber(BindRatio) == false || BindRatio > 100 ) {
				$("#notice").html("图片质量不能为空且只能为范围0~100的整数。").dialog("open");
				return;
			}
			paramList.push({ key: "BindRatio", value: "" + BindRatio});
			
			//	是否添加OSD设置
			var OSDEnable = $(":radio[name='OSDEnable']:checked").val();
			paramList.push({ key: "OSDEnable", value: "" + OSDEnable});
			
			//	如果添加OSD设置则将OSD相关参数传入后台
			if (OSDEnable == 1) {
				
				//	OSD设置信息
				var OSDInfo = $("#OSDConfigValue").data("OSDInfo");
				var OSDInfoArray = [];
				for (var i = 0; i <= OSDInfo.length; i++) {
					if (OSDInfo[i] != null) {
						OSDInfoArray.push(OSDInfo[i].preStr  + OSDInfo[i].middleStr + OSDInfo[i].afterStr);
					}
				}
				if (OSDInfo.length == 0) {
					$("#notice").html("OSD配置已选模板为空，请重新选择。").dialog("open");
					return;
					paramList.push({ key: "OSDInfo", value: "0"});
				} else {
					paramList.push({ key: "OSDInfo", value: "" + OSDInfoArray.join("$")});
				}
				
				//	OSD字体大小
				var TextSize = $("#OSDConfigValue").data("TextSize");
				paramList.push({ key: "TextSize", value: "" + TextSize});
				
				//	OSD字体颜色
				var TextColor = $("#OSDConfigValue").data("TextColor");
				paramList.push({ key: "TextColor", value: "" + TextColor});
			}
		}
		
		if (paramList.length>0) {
			var paramObject = new Object();
			paramObject.paramJson = JSON.stringify(paramList);
			$("#paramJson").val(JSON.stringify(paramObject));
			$.ajax({
				url: $("#ctx").val() + "/admin/dealpic_config!saveByJson.action",
				type: "POST",
				data: paramObject,
				success: function (msg) {
					isSuccess = true;
					$("#submitNotice").html(msg).dialog("open");
					return;
				}
			});
		}
		return false;
	});
	
	//	三张违章图片合成模式点击事件
	$("#threePicMode ul li").bind("click", function () {
		var selectId = $(this).attr("id");
		updatePicCompMode(selectId, "threePicMode");
	});
	
	//	两张违章图片合成模式点击事件
	$("#doublePicMode ul li").bind("click", function () {
		var selectId = $(this).attr("id");
		updatePicCompMode(selectId, "doublePicMode");
	});
	
	//	切换是否需要处理图片事件
	$(":radio[name='needDealPic']").bind("click", function () {
		var needDealPic = $(this).val();
		switchNeedDealPic(needDealPic);
	});
	
	//	OSD配置
	$("#OSDConfig").bind("click", function () {
		var OSDInfo = $("#OSDConfigValue").data("OSDInfo");
		var TextSize = $("#OSDConfigValue").data("TextSize");
		var TextColor = $("#OSDConfigValue").data("TextColor");
		$("#OSDFontSize").val(TextSize);
		$("#OSDFontColor").find("option[value='" + TextColor + "']").attr("selected", "selected");
		var $html = $("<ul></ul>");
    	for (var i = 0; i < OSDInfo.length; i++) {
    		var pre = OSDInfo[i].preStr;
    		var text = OSDInfo[i].middleStr;
    		var name = OSDInfo[i].codeCode;
    		var aft = OSDInfo[i].afterStr;
			var templet = $("#templet_li li").clone(true);
			templet.find(".pre").val(pre);
			templet.find(".mid").text(text);
			templet.find(".mid").attr("name", name);
			templet.find(".aft").val(aft);
			$html.append(templet);
    	}
    	$("#" + g_property.picNameId + "R").html($html);
    	
    	// 更新上面的模板配置
    	update(g_property.picNameType);
    	
    	// 去掉所有选中的模板字段
    	$("#" + g_property.picNameId + "L ul li :checkbox").removeAttr("checked");
    	
    	// 更新选中左边的模板字段
    	init(g_property.picNameId);
		$("#OSDConfigDialog").dialog("open");
	});
	
	$(":radio[name='OSDEnable']").bind("click", function () {
		var osdIsVaild = $(this).val();
		if (osdIsVaild == 0) {
			$("#OSDConfig").hide();
		} else {
			$("#OSDConfig").show();
		}
	});
});

/**
 * 更新图片合成模式（三张或者2张）
 * @author xupeipei2@dahuatech.com 21517
 * @date   下午8:13:52
 */
function updatePicCompMode(selectId, modeId){
	var ctx = $("#ctx").val();
	$("#" + modeId + " ul li").each(function(index, domEle){
		$("#" + modeId + " ul li").each(function(index, domEle){
			var thisId = $(domEle).attr("id");
			if (thisId == selectId) {
				$(domEle).html("<img src=\"" + ctx + "/images/hover_" + thisId + ".png\" width=\"88\" height=\"78\" />");
				$(domEle).addClass("selected");
				if (modeId == "threePicMode") {
					if (thisId == "compose1") {
						$("#fieldExtend").show();
					} else {
						$("#fieldExtend").hide();
					}
				}
			} else {
				$(domEle).html("<img src=\"" + ctx + "/images/normal_" + thisId + ".png\" width=\"88\" height=\"78\" />");
				$(domEle).removeClass("selected");
			}
		});
	});
}

/**
 * 切换处理图片 needDealPic：0 不处理图片；1：处理图片
 * @author xupeipei2@dahuatech.com 21517
 * @date   下午3:01:42
 */
function switchNeedDealPic(needDealPic){
	if (needDealPic == 0) {
		$("#notDealPic").show();
		$("#dealPic").hide();
	} else {
		$("#notDealPic").hide();
		$("#dealPic").show();
	}
}