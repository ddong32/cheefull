/**
 * 判断字段是否为空
 * @param fieldValue 字段值
 * @param errorMsg 错误提示
 * @return false表示为空， true表示不为空
 */
function fieldIsEmpty(fieldValue)
{
	if($.trim(fieldValue) == "")
	{
		return true;
	}
	return false;

}

/**
 * 校验字段是不是数字
 * @param {} fieldValue 字段值
 * @param {} errorMsg 错误提示信息
 * @return {Boolean} true是数字，false不是数字
 */
function filedIsNumber(fieldValue)
{   
	if(null == fieldValue || "" == fieldValue)
	{
		return true;
	}
    var reg = /^[0-9]+$/;
    if (!reg.test(fieldValue)) 
    {
     	return false;
   	} 
	return true;
}

/**
 * 检查是否数字
 * @param obj 对象
 */
function checkFiledIsNumber(obj) {
	var fieldValue = $(obj).val();
	if (null == fieldValue || "" == fieldValue) {
		$(obj).val(fieldValue);
	}
	var reg = /^[0-9]+$/;
	if (!reg.test(fieldValue)) {
		$(obj).val("");
	} else {
		$(obj).val(fieldValue);
	}
}

/**
 * 检查是否为数字
 * 
 * @returns {Boolean}如果输入是数字则返回true，否则返回false
 * @author 朱敬成(20431)
 * @date 2013.09.24
 */
function checkNum(evt)	
{
	evt = (evt)? evt:((window.event)?window.event:"");
	var k = evt.keyCode?evt.keyCode:evt.which;
	/**
	 * 数字说明
	 * 1、48-57是大键盘的数字键，
	 * 3、8是退格键。
	 */
	if((k <=57 && k >= 48) || (k == 8))
	{
		return true;
	}
	else
	{
		return false;
	}
}

/**
 * 检查是否含有部分特殊字符
 * @param evt
 */
function checkNumCnEnOther(value)	
{
	var reg = /[\s\/\~\!\@\#\$\^\&\*\<\>]/;
	if(reg.test(value))
	{
		return false;
	}
	else
	{
		return true;
	}
}

/**
 * 检查是否含有部分特殊字符
 * @param evt
 */
function checkNumEn(value)	
{
	var reg = /[a-zA-Z0-9]+/;
	if(reg.test(value))
	{
		return true;
	}
	return false;
}


/**
 * 限制输入空或小时大于23
 * 适用于违法地点时间段配置
 * @author wangguilin
 * @param event
 */
function checkMaxHSValue(event)
{
	if(event.srcElement.value == "" || event.srcElement.value > 23)
	{
		event.srcElement.value = "00";
	}	
}

/**
 * 限制输入空或分秒大于59
 * 适用于违法地点时间段配置
 * @author wangguilin
 * @param event
 */
function checkMaxMSValue(event)
{
	if(event.srcElement.value == "" || event.srcElement.value > 59)
	{
		event.srcElement.value = "00";
	}	
}

/**
 * 限制textarea输入长度
 * @param obj
 * @param length
 * @author 朱敬成 20431
 * @data 2013-11-04
 */
function limitTextAreaLength(obj, length)
{
	// 保存原始光标位置
	var pos = getCursorPos(obj);
	var temp = obj.attr("value");
	if(temp.length >= length)
	{
		obj.attr("value",temp.substr(0, length));
	}
	// 当前光标位置
	pos = pos-(temp.length - obj.attr("value").length);
	// 设置光标
	setCursorPos(pos);
}

/**
 * 获取光标的位置
 * @author 朱敬成(20431)
 * @date 2013.11.04
 */
function getCursorPos(obj)
{
	obj.focus();
	var currentRange = document.selection.createRange();
	var workRange = currentRange.duplicate();
	obj.select();
	var allRange = document.selection.createRange();
	var pos = 0;
	while(workRange.compareEndPoints("StartTOStart",allRange)>0)
	{
		workRange.moveStart("character",-1);
		pos++;
	}
	currentRange.select();
	return pos;
}

/**
 * 定位光标
 * @param pos    光标位置
 * @author 朱敬成 20431
 * @data 2013-11-04
 */
function setCursorPos(pos)
{
	var endPos = event.srcElement.createTextRange();
	endPos.moveStart("character",pos);
	endPos.collapse(true);
	endPos.select();
}

/**
 * 校验是否输入了#，用在模板配置上
 * @param {} fieldValue 字段名称
 * @param {} errorMsg 错误信息
 * @return {Boolean} true表示不包含特殊字符,FALSE表示包含特殊字符
 */
function checkJH(fieldValue)
{
	// 名称字符校验
	var reg = /#/;
	if (reg.test(fieldValue)) 
	{
		return true;
	}
	return false;
}

/**
 * 校验是否输入了$，用在模板配置上
 * @param {} fieldValue 字段名称
 * @param {} errorMsg 错误信息
 * @return {Boolean} true表示不包含特殊字符,FALSE表示包含特殊字符
 */
function checkMYF(fieldValue)
{
	// 名称字符校验
	var reg = /\$/;
	if (reg.test(fieldValue)) 
	{
		return true;
	}
	return false;
}

function checkYHOfEn(fieldValue)
{
	// 名称字符校验
	var reg = /[\'\"]+/;
	if (reg.test(fieldValue)) 
	{
		return true;
	}
	return false;
}

// 验证输入文件名是否有效，即如果含有以下字符为无效文件名：\/:*?"<>|
function checkFolderNameValid(folderName) {
	
	// 如果文件名为空则无效
    var reg = /^.*[\\\/:\*\?\<\>\|]+.*$/;
    if (reg.test(folderName)) {
     	return false;
   	}  else {
   		return true;
   	}
}

// 验证输入文件路径是否有效，即如果含有以下字符为无效文件路径名：\:*?"<>|
function checkPathNameValid(pathName) {
	
	// 如果文件名为空则无效
    var reg = /^.*[\\:\*\?\<\>\|]+.*$/;
    if (reg.test(pathName)) {
     	return false;
   	}  else {
   		return true;
   	}
}

/**
 * 检查是否为数字或英文字母
 * @returns {Boolean}如果输入是数字或英文则返回true，否则返回false
 * @author 朱敬成(20431)
 * @date 2013.09.24
 */
function checkNumAndEn(evt)	
{
	evt = (evt)? evt:((window.event)?window.event:"");
	var k = evt.keyCode?evt.keyCode:evt.which;
	/**
	 * 数字说明
	 * 1、48-57是大键盘的数字键，
	 * 3、8是退格键。
	 */
	if((k <=57 && k >= 48) || (k <= 90 && k >= 65) || (k <= 122 && k >= 97) || (k == 8))
	{
		return true;
	}
	else
	{
		return false;
	}
}

/**
 * 校验字段是不是IP地址
 * @param {} fieldValue字段值
 * @return {Boolean}true是IP false不是
 */
function filedIsIP(fieldValue)
{
	var reg = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])(\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])){3}$/;
    if (!reg.test(fieldValue)) 
    {
     	return false;
   	} 
	return true;
}

/**
 * 检查是否为数字或英文字母或下划线
 * @returns {Boolean}如果输入是数字或英文字母或下划线则返回true，否则返回false
 * @author 朱敬成(20431)
 * @date 2013.09.24
 */
function checkNumEnAndDash(evt)	
{
	evt = (evt)? evt:((window.event)?window.event:"");
	var k = evt.keyCode?evt.keyCode:evt.which;
	/**
	 * 数字说明
	 * 1、48-57是大键盘的数字键，
	 * 2、8是退格键。
	 * 3、95是下划线
	 */
	if((k <=57 && k >= 48) || (k <= 90 && k >= 65) || (k <= 122 && k >= 97) || (k == 8) || (k == 95))
	{
		return true;
	}
	else
	{
		return false;
	}
}

/**
 * 获取时间控件
 * @param id 主键名称
 * @param maxDateID 时间上限限制ID值，默认值：空
 * @param minDateID 时间下限限制ID值，默认值：空 
 * @param format 需要转换的格式，默认值：yyyy-MM-dd HH:mm:ss
 * @author 朱敬成(20431)
 * @date 2013.10.10
 */
function dateWidget(id, maxDateID, minDateID, dFormat)
{
	// 判断是否传入id
	if(typeof id === 'undefined' || id == null || id == "")
	{
		alert("未绑定相关的时间ID,请检查代码。", "提醒");
	}
	
	// 获取id对象
	var $id = $("#"+id);
	
	//	设置输入框只读
	$id.attr("readonly", "readonly");
	
	// maxDateID赋初始值
	if(typeof maxDateID === 'undefined' || maxDateID == null)
	{
		maxDateID = "";
	}
	
	// minDateID赋初始值
	if(typeof minDateID === 'undefined' || minDateID == null)
	{
		minDateID = "";
	}
	
	// 绑定事件
	$id.bind('click',function(){
		// 假如不做限制
		if(maxDateID == "" && minDateID == "")
		{
			WdatePicker({dateFmt:dFormat, readOnly:true,isShowClear:false});
		}
		// 限制最大值
		else if(maxDateID != "" && minDateID == "")
		{
			WdatePicker({dateFmt:dFormat,readOnly:true,isShowClear:false,maxDate:"#F{$dp.$D('"+maxDateID+"')}"});
		}
		// 限制最小值
		else if(maxDateID == "" && minDateID != "")
		{
			WdatePicker({dateFmt:dFormat,readOnly:true,isShowClear:false,minDate:"#F{$dp.$D('"+minDateID+"')}"});
		}
		// 限制最大值和最小值
		else
		{
			WdatePicker({dateFmt:dFormat,readOnly:true,isShowClear:false,maxDate:"#F{$dp.$D('"+maxDateID+"')}",minDate:"#F{$dp.$D('"+minDateID+"')}"});
		}
	});
	}