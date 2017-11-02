//获取表单内所有数据
//参数o为表单ID
function getParam(o){
	var param=new Object;
	$("#"+o+" input,#"+o+" select").each(function(index, element) {
		var obj=$(this);
		var n=obj.attr("name");
        switch ($(this).attr("type")){
			case "checkbox":
				if (obj.attr("checked")){
					if (typeof(param[n])=="undefined"){
						param[n]=[obj.val()];
					} else {
						param[n].push(obj.val());
					}
				}
				break;
			case "radio":
				if (obj.attr("checked")){
					param[n]=obj.val();
				}
				break;
			case "submit":
			case "button":
				break;
			default:
				param[n]=obj.val();
				break;
		}
    });
	return param;
}

//去除空字符
function trim(str){  
	var notValid=/(^\s)|(\s$)/;  
	while(notValid.test(str)){  
	  str=str.replace(notValid,"");
	}
	return str;
}

function showInfoWnd(t,geturl,w,h){
	$.ajax({
		url : geturl,
		type: "GET",
		success: function(msg){
			var InfoWnd='<div id="InfoWnd"></div>';
			$("#InfoWnd").remove();
			$("body").append(InfoWnd);
			$("#InfoWnd").attr("title",t);
			$("#InfoWnd").html(msg);
			$("#InfoWnd").dialog( "destroy" );
			$("#InfoWnd").dialog({
				dialogClass:"ppisDialog",
				width: w,
				height: h,
				minHeight: 0
			})
			
		}
	});
}

//字符过滤,违反限制规则提示
function testIllegal($this){
	var attrrule = $this.attr("rule");
	var testVal = $this.val();
	var isIllegal = false;
	var rules = attrrule.split(" ");
	var errTypeText = "";
	if (typeof $this.attr("norule") === "undefined"){
		for (var i in rules){
			switch (rules[i]){
				case "num":
					var reg = /[^\d]/g;
					if ( testVal!= "" && testVal.search(reg) != -1 ){
						isIllegal = true;
						errTypeText = "只能输入数字";
					}
					break;
				case "float":
					var reg = /^([+-]?)\\d*\\.\\d+$/g;
					if ( testVal!= "" && testVal.search(reg) != -1 ){
						isIllegal = true;
						errTypeText = "只能输入数字";
					}
					break;
				case "numchar":
					var reg = /[^\d\w]/g;
					if ( testVal!= "" && testVal.search(reg) != -1 ){
						isIllegal = true;
						errTypeText = "只能输入英文和数字";
					}
					break;
				case "char":
					var reg = /[^\d\w\u4e00-\u9fa5]/g;
					if ( testVal!= "" && testVal.search(reg) != -1 ){
						isIllegal = true;
						errTypeText = "不能含有特殊符号";
					}
					break;
				case "noempty":
					if ( testVal == "" || testVal== null || typeof(testVal)=="undefined" ){
						isIllegal = true;
						errTypeText = "该项不能为空";
					}
					break;
				case "mail":
					var reg = /[\w\d]+@[\w\d]+\.[\w\d]+/g;
					if (testVal != "" && testVal.search(reg) == -1){
						isIllegal = true;
						errTypeText = "邮箱格式不正确";
					}
					break;
				default:;
			}
		}
	}
	return errorTip(isIllegal, $this, errTypeText);
}

// 错误提示代码重构
function errorTip(isIllegal, $this, errTypeText) {
	var $errType = $("<span/>").addClass("errType");
	var thisName = $this.attr("name");
	var noAppend = $this.attr("noappend");
	if (isIllegal) {
		$errType.html(errTypeText).attr("for",thisName);
		$this.addClass("errNotice");
		var $requireField = $this.siblings(".requireField");
		var $tmpErrType = $this.siblings(".errType");
		var $tmpErrTypeCount = $tmpErrType.size();
		if (typeof noAppend === "undefined"){
			if ($tmpErrTypeCount === 0) {
				if ($requireField.length > 0) {
					$requireField.after($errType);
				} else {
					$this.after($errType);
				}
			} else {
				$tmpErrType.html(errTypeText).attr("for",thisName);;
			}
		}
		return true;
	} else {
		$this.removeClass("errNotice");
		if (typeof noAppend === "undefined"){
			var $tmpErrType = $this.siblings(".errType");
			if ($tmpErrType.attr("for") === thisName){
				$tmpErrType.remove();
			}
		}
		return false;
	}
}

// 验证后面的值要比前面的值大（都是数字）
function checkBackMoreFront($front, $back) {
	var front_number = parseInt($front.val(), [10]);	// 以10进值转（否则023会被认为8进制，转为19）
	var back_number = parseInt($back.val(), [10]);
	var isIllegal = false;
	var errTypeText = "";
	// 验证参数是否为非数字
	if (isNaN(front_number) || isNaN(back_number)) {
		isIllegal = true;
		errTypeText = "请输入数字";
	} else {
		if (back_number <= front_number) {
			isIllegal = true;
			errTypeText = "后面的值必须比前面的值大";
		}
	}
	return errorTip(isIllegal, $front, errTypeText);
}

function addMask(){
	//var screenHeight=document.documentElement.clientHeight;
	//var $mask = $("<div/>").attr("id","mask").height(screenHeight).appendTo($("body"));
	//$("<p/>").html("页面正在加载，请稍候...").appendTo($mask);
}

function removeMask(){
	//$("#mask").remove();
	//setTimeout('$("#mask").hide()',1000);
}

function GetDateStr(AddDayCount) {
    var dd = new Date();
    dd.setDate(dd.getDate()+AddDayCount);//获取AddDayCount天后的日期
    var y = dd.getFullYear();  
    var m = (dd.getMonth()+1) < 10 ? "0"+(dd.getMonth()+1) : (dd.getMonth()+1);
	var d = dd.getDate() < 10 ? "0"+ dd.getDate() : dd.getDate();
    return y+"-"+m+"-"+d;
}
//上传图片后缀名校验
function getPicFmt(path){
	var picStr = path.toLowerCase().split(".");
	var fmt = picStr[picStr.length-1];
	if(fmt == "jpg")
	{
		return true;
	}
	return false;
}
//日期格式化
Date.prototype.format = function(format){ 
	var o = { 
		"M+" : this.getMonth()+1, //month 
		"d+" : this.getDate(),    //day 
		"h+" : this.getHours(),   //hour 
		"m+" : this.getMinutes(), //minute 
		"s+" : this.getSeconds(), //second 
		"q+" : Math.floor((this.getMonth()+3)/3), //quarter 
		"S" : this.getMilliseconds()              //millisecond 
	}; 

	if(/(y+)/.test(format)) { 
		format = format.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
	} 

	for(var k in o) { 
		if(new RegExp("("+ k +")").test(format)) { 
			format = format.replace(RegExp.$1, RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length)); 
		} 
	} 
	return format; 
}; 

//对字符进行html编码，防止脚本注入
function htmlEncode(value) {
	value = $.trim(value);
	return !value ? value : String(value).replace(/&/g, "&amp;").replace(/\"/g, "&quot;").replace(/</g, "&lt;").replace(/>/g, "&gt;");
}

//对已html编码的字符进行解码
function stripHtmlEncode(value) {
	value = String(value);
	var regexp = /<("[^"]*"|'[^']*'|[^'">])*>/gi;
	if (value) {
		value = value.replace(regexp,"");
		return (value && value !== '&nbsp;' && value !== '&#160;') ? value.replace(/\"/g,"'") : "";
	} 
	return value;
}

function showDialog(url,percent,width,height,names){
	
	var windownName = "";
	if(names != null && names !="" && typeof(names) != "undefined" ){
		windownName = names
	}else{
		windownName = 'auditwindow';
	}
	
	var s = windownName;
	var h = window.screen.availHeight*0.9;
  	var w = window.screen.availWidth*0.95;
  	
  	if(percent != null && percent !="" && typeof(percent) != "undefined" ){
  		h = window.screen.availHeight * percent;
  	  	w = window.screen.availWidth * percent;
  	}else{
		if(width != null && width !="" && typeof(width) != "undefined") w=width;
		if(height != null && height!="" && typeof(height) != "undefined") h=height;
  	}
    var t = (window.screen.availHeight-30-h)/2 - 10; //获得窗口的垂直位置 
    var l = (window.screen.availWidth-10-w)/2;  //获得窗口的水平位置 
	
	var explorer = navigator.userAgent ;
	if(explorer.indexOf("Chrome") >= 0) {
	  	if(parent.auditWindow){
	  		parent.auditWindow = window.open(url,s,"width="+w+",height="+h+",left="+l+",top="+t+",help=no,status=yes,scrollbars=yes,titlebar=no,menubar=no,toolbar=no,resizable=no,location=no");
	  	}else{
	  		window.open(url,s,"width="+w+",height="+h+",left="+l+",top="+t+",help=no,status=yes,scrollbars=yes,titlebar=no,menubar=no,toolbar=no,resizable=no,location=no");
	  	}
		return windownName;
	} else if (explorer.indexOf("compatible") >= 0 || explorer.indexOf("MSIE") >= 0) {
		window.showModalDialog(url,window,"dialogHeight="+h+"px;dialogWidth="+w+"px;scrollbars=yes;resizable=no;help=no;status=no;center=yes;dialogTop=25;");
		//window.open(url,s,"width="+w+",height="+h+",left="+l+",top="+t+",help=no,status=yes,scrollbars=yes,titlebar=no,menubar=no,toolbar=no,resizable=no,location=no");
	} else {
		window.open(url,s,"width="+w+",height="+h+",left="+l+",top="+t+",help=no,status=yes,scrollbars=yes,titlebar=no,menubar=no,toolbar=no,resizable=no,location=no");
	}
}

/*
 * 过滤斜杠旁边的空格
 */
function replaceBlankOfSlash(str) {
	return str.replace(/\/\s+/, "\/").replace(/\s+\//, "\/");
}

function getActionAddParam(linkstr) {
	var username = $("#username").val();
	var password = $("#password").val();
	if (username != "" && password != "") {
		return linkstr + "username=" + username + "&password=" + password;
	} else {
		return "";
	}
}


/**
 * 解析url参数
 * @returns {___anonymous90431_90432}
 * 覃东梁 19622
 * 2014年12月17日
 * 上午9:16:30
 */
var queryStringArgs = getQueryStringArgs();
function getQueryStringArgs() {
	
	// 取得查询字符串并去掉开头的问号
	var qs = (location.search.length > 0 ? location.search.substring(1) : ""),
	
	// 保存数据对象
	args = {},
	
	// 取得每一项
	items = qs.length ? qs.split("&") : [],
	item  = null,
	name  = null,
	value = null,
	
	// 在for循环中使用
	i = 0,
	len = items.length;
	
	// 逐个将每一项添加到args对象中
	for (i = 0; i < len; i++) {
		item = items[i].split("=");
		name = decodeURIComponent(item[0]);
		value = decodeURIComponent(item[1]);
		
		if (name.length) {
			args[name] = value;
		}
	}
	
	return args;
}