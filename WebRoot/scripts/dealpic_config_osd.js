var g_property = {
		picNameId : "picName"			//图片名称模板配置ID
};

$(document).ready(function(){
	/********************************绑定事件开始*********************************/
	//点击已选模板行选中该模板
	$("#content_1 .pub_r li, #templet_li li").bind("click", function(event){
		var $_target = $(event.target);
		$_target.closest("ul").find("li").removeClass("selected");
		$_target.closest("li").addClass("selected");
	});
	//向上移动
	$("#content_1 .btn_up").bind("click", function(event){
		var $_content = $("#content_1");
		var updateId = g_property.picNameId;
		var selectedLine = $(".pub_r ul li.selected", $_content);
		var prevLine  = selectedLine.prev("li:last");
//		如果选中行为第一行，则不向上移动
		if(prevLine.length != 0)
		{
			selectedLine.clone(true).insertBefore(prevLine);
			selectedLine.remove();
		}
		update(updateId);
	});
	//向下移动
	$("#content_1 .btn_down").bind("click", function(event){
		var $_content = $("#content_1");
		var updateId = g_property.picNameId;
		var selectedLine = $(".pub_r ul li.selected", $_content);
		var nextLine  = selectedLine.next("li:first");
//		如果选中行为最后一行，则不向下移动
		if(nextLine.length != 0)
		{
			selectedLine.clone(true).insertAfter(nextLine);
			selectedLine.remove();
		}
		update(updateId);
	});
//	点击选中可选模板字段后增加到已选模板字段
	$("#content_1 .pub_l ul :checkbox").bind("click", function(event){
		var $_content = $("#content_1");
		var updateId = g_property.picNameId;
		var $_target = $(event.target);
//		如果选中模板字段
		if($_target.is(":checked"))
		{
			
			var text = $.trim($_target.closest("li").text());
			var name = $_target.closest("li").find("input").attr("name");
			var templet = $("#templet_li li").clone(true);
			templet.find(".mid").text(text);
			templet.find(".mid").attr("name", name);
			$(".pub_r ul", $_content).append(templet);
		}
//		如果取消勾选该模板字段
		else
		{
			var text = $_target.closest("li").text();
			var name = $_target.closest("li").find("input").attr("name");
			$(".pub_r ul", $_content).find("label[name=" + name + "]").closest("li").remove();
		}
		//如果为图片名称模板或则图片路径模板则更新
		if(updateId != null)
		{
			update(updateId);
		}
	});
//	鼠标移开已选模板字段文本框时自动更新模板名称
	$("#picNameR input, #templet_li input").bind("blur", function(){
		update();
	});
//	获得默认图片名称模板配置
	$("#picNameDefault").bind("click", function(){
		getDefaultConfig($("#ctx").val() + "/admin/dealpic_config!osddefaultlist.action");
	});
//	确定
	$("#OK").bind("click", function(){
		var OSDFontSize = $("#OSDFontSize").val();
//		字体不得为空
		if(fieldIsEmpty(OSDFontSize) == true)
		{
			$("#notice").html("字体大小不得为空。").dialog("open");
			return;
		}
		if(filedIsNumber(OSDFontSize) == false || (OSDFontSize > 72 || OSDFontSize < 10))
		{
			$("#notice").html("字体大小只能为10到72的正整数。").dialog("open");
			return;
		}
		var picNameList = [];
		$("#picNameR ul li").each(function(index, domEle){
			var paramObject = new Object();
			paramObject.preStr = $(domEle).find(".pre").val();
			paramObject.middleStr = $(domEle).find(".mid").text();
			paramObject.codeCode = $(domEle).find(".mid").attr("name");
			paramObject.afterStr = $(domEle).find(".aft").val();
			picNameList.push(paramObject);
		});
		if(picNameList.length == 0)
		{
			$("#notice").html("已选模板字段不能为空。").dialog("open");
			return;
		}
		
		var swStr = "";
		for(var idx = 0; idx < picNameList.length; idx++){
			 swStr += (picNameList[idx].preStr+picNameList[idx].afterStr);
		}
		if(swStr.length > 180) {
			$("#notice").html("OSD模板字段内容输入过长。").dialog("open");
			return;
		}else if (checkJH(swStr) || checkYHOfEn((swStr)) || checkMYF(swStr)) {
			$("#notice").html("OSD模板字段限制输入#$'，请重新输入。").dialog("open");
			return;
		}
		
		$("#OSDConfigValue").data("OSDInfo", picNameList);
		$("#OSDConfigValue").data("TextColor", $("#OSDFontColor option:checked").val());
		$("#OSDConfigValue").data("TextSize", OSDFontSize);
		$("#OSDConfigDialog").dialog("close");
	});
//	取消
	$("#cancel").bind("click", function(){
		$("#OSDConfigDialog").dialog("close");
	});
	
	// 换行 
	$("#newline_btn").bind("click", function() {
		var $_content = $("#content_1");
		var templet = $("#templet_li li").clone(true);
		templet.find(".mid").text("#换行#");
		templet.find(".mid").attr("name", "");
		$(".pub_r ul", $_content).append(templet);
		update();
	});
	
	// 移除
	$("#delete_btn").bind("click", function() {
		var selectedItem = $("#picNameR ul li.selected");
		if (selectedItem.length === 0) {
			$("#notice").html("请在已选模板字段中选中某一行。").dialog("open");
			return;
		} 
		var name = selectedItem.find(".mid").attr("name");
		selectedItem.remove();
		$(":checkbox[name='" + name + "']", $("#picNameL")).removeAttr("checked", "checked");
		update();
	});
	/*******************************绑定事件结束***********************************/
//	在可选择模板中选中已选择的模板字段
	init(g_property.picNameId);
//	更新模板
	update();
});
/**
 * 更新图片名称 id: picName或图片路径 id: picPath
 * @author xupeipei2@dahuatech.com 21517
 * @date   上午11:34:21
 */
function update() {
	var id = g_property.picNameId;
	var name = "";
	$("#" + id + "R ul li").each(function (index, domEle) {
		var pre = $(domEle).find(".pre").val();
		var mid = $(domEle).find(".mid").text();
		var aft = $(domEle).find(".aft").val();
		name = name + pre + mid + aft;
	});
	$("#" + id).html(htmlEncode(name));
}

/**
 * 在可选择模板中选中已选择的模板字段
 * @author xupeipei2@dahuatech.com 21517
 * @date   上午10:38:20
 */
function init(id)
{
	$("#" + id + "R ul li").each(function(index, domEle){
		var name = $(domEle).find("label.mid").attr("name");
		$("#" + id + "L ul li :checkbox[name=" + name + "]").attr("checked", "checked");
	});
}
/**
 * 获得默认配置 templetType:0图片名称模板；1:图片路径模板
 * @author xupeipei2@dahuatech.com 21517
 * @date   上午10:57:18
 */
function getDefaultConfig(url)
{
	$.ajax({
        type: "POST",
        url: url,
		async: true,
        success: function(msg){
        	var defaultTemplate = JSON.parse(msg);
        	var $html = $("<ul></ul>");
        	for(var i = 0; i < defaultTemplate.length; i++)
        	{
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
        	var id = g_property.picNameId;
//        	更新右边的模板
        	$("#" + id + "R").html($html);
//        	更新上面的模板配置
        	update();
//        	去掉所有选中的模板字段
        	$("#" + id + "L ul li :checkbox").removeAttr("checked");
//        	更新选中左边的模板字段
        	init(id);
        },
		complete: function() {
		},
        error:function(){
        }
    });
}