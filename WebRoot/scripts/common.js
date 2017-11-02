/*################### 公用的JS ###########################*/
function getContextPath(){
	var pathname = window.location.pathname;
	pathname = pathname.substring(1,pathname.length);
	return "/"+pathname.substring(0,pathname.indexOf("/"));
}
//翻页功能
function goToPage(){
	var pagePath = $("#pagePath").val();
	var pageIndex = $("#pageIndex").val();
	var pageTotal = $("#pageTotal").val();
	if ("0" == pageTotal){
		return;
	}
	if(isNaN(pageIndex)){
		alert("输入页码错误，请输入1-"+pageTotal+"之间的数字");
		return;
	}
	pageIndex = parseInt(pageIndex);
	pageTotal = parseInt(pageTotal);
	if( pageIndex > pageTotal || pageIndex <= 0 ){
		alert("输入页码错误，请输入1-"+pageTotal+"之间的数字");
		return;
    }
	self.location= pagePath+"&p=" +pageIndex;
}

/**
 * 将json字符串解析成数组
 * @param jsonStr 字符串
 */
function getParamStr(jsonStr){
    var jsonArray = new Array();
    for (var key in jsonStr){
        jsonArray[jsonArray.length] = jsonStr[key];
    }
    return jsonArray;
}
/**
 * 显示指定的DIV对象
 * @param div div的ID
 */
function showDiv(div){
    if($('#'+div).is(":hidden")){
        $('#'+div).show();
    }
}
/**
 * 隐藏指定的DIV对象
 * @param div div的ID
 */
function hideDiv(div){
    if(!$('#'+div).is(":hidden")){
        $('#'+div).hide();
    }
}
/**
 * 显示对话框
 * @param divOverlay 遮罩层
 * @param divBox 需要显示的div
 * @param top 弹出框的位置
 */
function showDialogBox(divOverlay,divBox,top){
    $('#'+divOverlay).fadeIn('fast', function () {
        $('#'+divBox).animate({ 'top': top+'px' }, 300);
    });
}
/**
 * 隐藏对话框
 * @param divOverlay 遮罩层
 * @param divBox 需要显示的div
 * @param top 弹出框的位置
 */
function hideDialogBox(divOverlay,divBox,top){
    $('#'+divBox).animate({ 'top': top+'px' }, 300, function () {
        hideDiv(divOverlay);
    });
}
/*关闭提示框*/
function closeMsgBox() {
    hideDialogBox("zxxBlank","wrapOut","-500");
}
/**
 * 根据指定的参数创建图表
 * @param swfName 图表样式名称
 * @param width 宽度
 * @param height 高度
 * @param xmlData 数据
 * @param showDiv 显示的DIV
 */
function creatFlash(swfName,width,height,xmlData,showDiv){
    var chart = new FusionCharts(getContextPath()+"/oss/inc/FusionChartsV3/"+swfName, "ChartId", ""+width+"", ""+height+"");
    chart.setDataXML(xmlData);
    chart.addParam("wmode","Opaque");
    chart.render(showDiv);
}
/**
 * 创建弹出框方法
 * @param width 弹出框的宽
 * @param height 高度
 * @param title 弹出框的名称
 * @param divId 需要显示的divID
 * @param top 向下的高度
 */
function creatDialogBox(width,height,title,divId,top){
    var htm = document.getElementById(divId).innerHTML;
    if(width==null||width=='')
        width = 300;
    if(height==null||height=='')
        height = 200;
//    var clientWidth = $(document).width();
//    var leftWidth = (clientWidth-width)/2;
    var leftWidth = (($(document).width()-width)/2);
    var css = '<style type="text/css">' +
              '#zxxBlank{width:100%;height:100%;background:#333333;filter:Alpha(Opacity=20, Style=0);opacity:0.20;position:fixed;top:0;bottom:0;left:0;right:0;z-index:100;}' +
              '.wrap_out{top:-500px;margin-left:'+leftWidth+'px;color:#7F7F7F;width:'+width+'px;height:'+height+'px;padding:5px;box-shadow:0 0 6px rgba(0,0,0,.5);-moz-box-shadow:0 0 6px rgba(0,0,0,0.5);-webkit-box-shadow:0 0 6px rgba(0,0,0,.5);position:absolute;z-index:2000;}' +
              '.wrap_in{background:#fafafa;border:1px solid #ccc;height:100%;}' +
              '.wrap_bar{width:100%;background:#f7f7f7;border-top:3px solid #f9f9f9;border-bottom:4px solid #eee;margin-top:2px;}' +
              '.wrap_title{line-height:5px;background:#f3f3f3;border-top:4px solid #f5f5f5;border-bottom:5px solid #f1f1f1;margin-top:3px;}' +
              '.wrap_title span{position:relative;margin-left:10px;cursor:text;}.wrap_body{display:inline-block;border-top:1px solid #ddd;background:white;}' +
              '.wrap_close{margin-top:-18px;margin-right:10px;color:#34538b;font-weight:bold;font-family:Tahoma;text-decoration:none;cursor:pointer;float:right;}' +
              '.wrap_close:hover{text-decoration:none;color:#f30;}' +
              '</style>';
	$("head").append(css);
    //弹框的显示
    var WRAP = '<div id="zxxBlank" style="FILTER:Alpha(Opacity=20, Style=0);opacity:0.20;" onselectstart="return false;"></div>' +
                '<div id="wrapOut" class="wrap_out" style="width:'+width+'px;height:'+height+'px;">' +
                   '<div class="wrap_in" id="wrapIn">' +
                       '<div id="wrapBar" class="wrap_bar" onselectstart="return false;">' +
                           '<div class="wrap_title"><span>'+title+'</span></div>' +
                           '<a href="javascript:closeMsgBox();" class="wrap_close" id="wrapClose">×</a>' +
                       '</div>' +
                       '<div class="wrap_body" id="wrapBody">'+htm+'</div>' +
                   '</div>' +
               '</div>';
    if ($("#wrapOut").size()) {
        $("#wrapOut").show();
        $("#zxxBlank").show();
    } else {
        $("body").append(WRAP);
    }
    showDialogBox("zxxBlank","wrapOut",top);
}
/**
 * 清除输入数据
 * @param array 输入框的ID数组
 */
function clear(array){
    if(array != null){
        for(var i=0;i<array.length;i++){
            $("#"+array[i]).attr("value","");
        }
    }
}

function creatAlarmFlash(){
    j$.ajax({
        type: "POST",
        url: getContextPath()+"/oss/alarm_getChartDataXML",
        success: function(json) {
            var jsonObj = json.split(",");
            var xmlData = "<chart baseFontSize=\"12\" canvasBorderThickness=\"0\" showBorder=\"0\" bgColor=\"E7F1FB\" showValues=\"1\">" +
                "<set label=\"严重\" value=\""+jsonObj[0]+"\" color=\"CF5351\"/>" +
                "<set label=\"重要\" value=\""+jsonObj[1]+"\" color=\"f7ba1c\"/>" +
                "<set label=\"次要\" value=\""+jsonObj[2]+"\" color=\"ffff99\"/>" +
                "<set label=\"一般\" value=\""+jsonObj[3]+"\" color=\"00ffff\"/>" +
                "<set label=\"提示\" value=\""+jsonObj[4]+"\" color=\"ccffff\"/></chart>";
            creatFlash("Bar2D.swf","350","150",xmlData,"chartdiv");
        }
    });
}

/*关闭提示框*/
function closeAlarmBox() {
    hideDialogBox("alarm-overlay","alarm-box","-500");
}

/*################### 主窗口模块使用 ###########################*/
function resize() {
    var mainHeight;
    var clientWidth;
    var clientHeight;
    if (navigator.userAgent.indexOf("MSIE 8.0") > 0) {
        clientWidth = document.body.clientWidth;
        clientHeight = document.body.clientHeight;
    }else if(navigator.userAgent.indexOf("MSIE 7.0") > 0){
        clientWidth = document.documentElement.clientWidth;
        clientHeight = document.documentElement.clientHeight;
    }else if(navigator.userAgent.indexOf("MSIE 6.0") > 0){
        clientWidth = document.documentElement.clientWidth;
        clientHeight = document.documentElement.clientHeight;
    }else{
        clientWidth = document.body.clientWidth;
        clientHeight = document.body.clientHeight;
    }
    if(clientWidth<1024)
        clientWidth = 1024;
    $('#content').css('width', clientWidth);
    var div_main_height = $("#main").height();
    div_main_height = div_main_height+130;
    //判断是否出现滚动条
    //alert(div_main_height+"----"+clientHeight);
    if(div_main_height<=clientHeight){
        $('#content').css('height', clientHeight);
        mainHeight = clientHeight - 130;
        $('#main').css('height', mainHeight);
    }
}

/**
 * 初始化包装的alert弹窗
 * @param message
 * @param callback
 */
function alertDIV(message, callback)
{
	$.alerts.alert(htmlEncode(message), "提醒", callback, true);
}

/**
 * 初始化包装的confirm弹窗
 * @param message
 * @param callback
 */
function confirmDIV(message, callback)
{
	$.alerts.confirm(htmlEncode(message), "提醒", callback, true);
}