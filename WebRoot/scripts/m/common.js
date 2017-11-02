(function (a, b, c, d) { var e = a(b); a.fn.lazyload = function (c) { function i() { var b = 0; f.each(function () { var c = a(this); if (h.skip_invisible && !c.is(":visible")) return; if (!a.abovethetop(this, h) && !a.leftofbegin(this, h)) if (!a.belowthefold(this, h) && !a.rightoffold(this, h)) c.trigger("appear"), b = 0; else if (++b > h.failure_limit) return !1 }) } var f = this, g, h = { threshold: 0, failure_limit: 0, event: "scroll", effect: "show", container: b, data_attribute: "original", skip_invisible: !0, appear: null, load: null }; return c && (d !== c.failurelimit && (c.failure_limit = c.failurelimit, delete c.failurelimit), d !== c.effectspeed && (c.effect_speed = c.effectspeed, delete c.effectspeed), a.extend(h, c)), g = h.container === d || h.container === b ? e : a(h.container), 0 === h.event.indexOf("scroll") && g.bind(h.event, function (a) { return i() }), this.each(function () { var b = this, c = a(b); b.loaded = !1, c.one("appear", function () { if (!this.loaded) { if (h.appear) { var d = f.length; h.appear.call(b, d, h) } a("<img />").bind("load", function () { c.hide().attr("src", c.data(h.data_attribute))[h.effect](h.effect_speed), b.loaded = !0; var d = a.grep(f, function (a) { return !a.loaded }); f = a(d); if (h.load) { var e = f.length; h.load.call(b, e, h) } }).attr("src", c.data(h.data_attribute)) } }), 0 !== h.event.indexOf("scroll") && c.bind(h.event, function (a) { b.loaded || c.trigger("appear") }) }), e.bind("resize", function (a) { i() }), /iphone|ipod|ipad.*os 5/gi.test(navigator.appVersion) && e.bind("pageshow", function (b) { b.originalEvent.persisted && f.each(function () { a(this).trigger("appear") }) }), a(b).load(function () { i() }), this }, a.belowthefold = function (c, f) { var g; return f.container === d || f.container === b ? g = e.height() + e.scrollTop() : g = a(f.container).offset().top + a(f.container).height(), g <= a(c).offset().top - f.threshold }, a.rightoffold = function (c, f) { var g; return f.container === d || f.container === b ? g = e.width() + e.scrollLeft() : g = a(f.container).offset().left + a(f.container).width(), g <= a(c).offset().left - f.threshold }, a.abovethetop = function (c, f) { var g; return f.container === d || f.container === b ? g = e.scrollTop() : g = a(f.container).offset().top, g >= a(c).offset().top + f.threshold + a(c).height() }, a.leftofbegin = function (c, f) { var g; return f.container === d || f.container === b ? g = e.scrollLeft() : g = a(f.container).offset().left, g >= a(c).offset().left + f.threshold + a(c).width() }, a.inviewport = function (b, c) { return !a.rightoffold(b, c) && !a.leftofbegin(b, c) && !a.belowthefold(b, c) && !a.abovethetop(b, c) }, a.extend(a.expr[":"], { "below-the-fold": function (b) { return a.belowthefold(b, { threshold: 0 }) }, "above-the-top": function (b) { return !a.belowthefold(b, { threshold: 0 }) }, "right-of-screen": function (b) { return a.rightoffold(b, { threshold: 0 }) }, "left-of-screen": function (b) { return !a.rightoffold(b, { threshold: 0 }) }, "in-viewport": function (b) { return a.inviewport(b, { threshold: 0 }) }, "above-the-fold": function (b) { return !a.belowthefold(b, { threshold: 0 }) }, "right-of-fold": function (b) { return a.rightoffold(b, { threshold: 0 }) }, "left-of-fold": function (b) { return !a.rightoffold(b, { threshold: 0 }) } }) })(jQuery,window,document)

document.onreadystatechange = subSomething;
function subSomething() {
	//当页面加载状态
	if (document.readyState == "complete") {
		$('#loader').fadeOut();
		$('#wait').delay(300).fadeOut('slow');
	}
}

$(function(){
   	headTop();
});
function headTop(){
   var m=$(".header-menu");
   $("#goHome").on("click touch",function(){
		if($(".header-menu-bg").length){
		   m.addClass("open"); 
		   $(".header-menu-bg").show().addClass("open")   
		}else{ 
		   $("body").append('<div class="header-menu-bg"></div>');
		   m.addClass("open"); 
		   $(".header-menu-bg").show().addClass("open")
		}  
		$(".header-menu-bg").on("click touch",function(){ 
			  $(".header-menu").removeClass("open");
			  $(this).removeClass("open"); 
			  setTimeout(function(){
				 $(".header-menu-bg").hide()   
			  },200)   
		})	 
   });	
}
function mePage(id,fun){
    var BackBtn = null, name = $(".header h1.es").text(), foot = $("#footer").css("display");
    //事件集合
    var Fn = {
        backFn: function (e) {
            if (BackBtn != null) {
                e.preventDefault();
                BackBtn();
                BackBtn = null;
            }
        },
        hide: function (obj,container) {
            scrollTo(0,1);
			$(obj).removeClass("cur");
			$(obj).stop(true).animate({left:0},200,function(){
			   	$(obj).hide();
				$(container).show();
				$(".header .rg-head").show();
				$("#footer").css("display", foot)
			})
        },
        show: function (obj,container) {
            scrollTo(0,1);
			$(obj).show().stop(true).animate({left:0},100,function(){
			   	$(obj).addClass("cur");
				$(container).hide();
				$(".header .rg-head").hide();
				$("#footer").hide()
			})     
        }
    }
    $("#goBack").on("click touch",function (e) { Fn.backFn(e) }); 
	$(id).on("touch click",function(){
		var a=$(this).data("page"),id="#page-"+a;
		Fn.show(id,"#main");
		$(".header h1.es").text($(this).data("name"));
		BackBtn = function(){ Fn.hide(id,"#main");$(".header h1.es").text(name)};
		if(typeof fun=="function"){
			fun(BackBtn);
		}
	})
};
function reTop(){ 
   var top=$("body").append('<a href="javascript:;" id="return-top"></a>');
   $(window).on("scroll touch",function(){
		if($(window).scrollTop()>$(window).height()){
			$("#return-top").show();  
		}else{
			$("#return-top").hide();
		}  
   }) 
   $("#return-top").on("click touch",function(){ 
	   $(window).scrollTop(0);
   });     	
}
function error(txt){
   var err='<div id="tips"><div class="tips-item"><div class="tips-img"></div><p>&nbsp;</p></div></div>',tips;
   if($("#tips").length>0){
	   tips=$("#tips"); 
   }else{
	  $("body").append(err);
	  tips=$("#tips"); 
   }
    tips.find("div.tips-item>p").text(txt);
    tips.show();
    setTimeout(function(){
        tips.hide();
    },3000);	
}
//显示错误提示信息
function showErr(txt,fun){
    //$("html").css("overflow-y","hidden");
    if ($("#bgDiv").html() == null) {
        $('<div id="bgDiv"></div>').appendTo("body");
    }
    if ($("#showMsg").html() != null) {
        $("#showMsg").remove();
    }
	if(fun==undefined){
	   $('<div id="showMsg"><div class="msg-content">' + txt + '</div><div class="msg-btn"><a href="javascript:hideErr();">确定</a></div></div>').appendTo("body");	
	}else{
	   $('<div id="showMsg"><div class="msg-content">' + txt + '</div><div class="msg-btn"><a href="javascript:hideErr('+fun+');">确定</a></div></div>').appendTo("body");
	}
    var wh = $(window).height();
    var sh = $(window).scrollTop();
    var lowh = $("#showMsg").height();
    $("#bgDiv").css({"height": $(document).innerHeight() }).show();
    $("#showMsg").css({"top": (wh-lowh)/2}).show();
}
//隐藏错误提示信息
function hideErr(fun){ 
   // $("html").css("overflow-y","visible");
    $("#bgDiv,#showMsg").hide();
	if(typeof fun=="function"){
		fun()
	}
}
function submitTip(txt){
    $("body").append('<div id="tipBg"></div><div id="tipTxt"><p>'+txt+'</p></div>');	
	$("#tipBg").show();
	$("#tipTxt").stop(true).fadeIn(300)
}
//验证姓名
function checkPerName(obj) {
    var tj = true;
    if ($("#" + obj).val()!="") {
       if (/^(.*?)+[\d~!@@#$%^&*()_\-+\={}\[\];:'"\|,.<>?！￥……（）——｛｝【】；：'"'"、《》，。、？]/.test($("#" + obj).val())) {
          showErr("姓名不得包含数字或特殊字符！");
          tj=false;
       }
    }else{
       showErr("姓名不得为空！");
       tj = false
    }
    return tj
}
//验证账号
function checkUserName(obj) {
	var tj = true;
	if ($("#" + obj).val() != "") {
		if (!/^\w+$/.test($("#" + obj).val())) {
			showErr("账号只能包含英文字母、数字和下划线");
			tj = false;
			return tj;
		}
	} else {
		showErr("请输入账号！");
		tj = false;
	}
	return tj;
}
//验证手机号码
function checkPerPhone(obj) {
    var tj = true;
    if ($("#" + obj).val()!="") {
       if (!/^1((([3578])\d{9})|(47\d{8}))$/.test($("#"+obj).val())) {
          showErr("请输入正确的手机号码！");
          tj=false;
       }
    }else{
       showErr("手机号码不得为空！");
       tj = false
    }
    return tj
}
function checkCard(obj) {
    var tj = true;
    if ($("#"+obj).val()!="") {
        if (!checkidcard($("#"+obj).val())) {
            showErr("请正确输入身份证号码！");
            tj = false;
        }
    } else {
        showErr("请输入身份证号码！");
        tj = false;
    }
    return tj;
}
//验证常用邮箱
function checkEmail(obj) {
	var tj = true,em = /^([a-zA-Z0-9]+[_|\-|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\-|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
	if ($("#"+obj).val()!="") {
		if (!em.test($("#" + obj).val())) {
			showErr("请正确填写邮箱地址！");
			tj = false;
		}
	} else {
		showErr("请填写邮箱地址！");
		tj = false;
	}
	return tj;
}
//判断身份证信息
function checkidcard(card)
{
	card=card.toLowerCase();
	var vcity={ 11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外"};
	var arrint = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2); 
	var arrch = new Array('1', '0', 'x', '9', '8', '7', '6', '5', '4', '3', '2'); 
	var reg = /(^\d{15}$)|(^\d{17}(\d|x)$)/;
	if(!reg.test(card))return false;
	if(vcity[card.substr(0,2)] == undefined)return false;
	var len=card.length;
	if(len==15)   
		reg=/^(\d{6})(\d{2})(\d{2})(\d{2})(\d{3})$/;
	else
		reg=/^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|x)$/;
	var d,a = card.match(new RegExp(reg));
	if(!a)return false;
	if (len==15){   
		d = new Date("19"+a[2]+"/"+a[3]+"/"+a[4]);     
	}else{   
		d = new Date(a[2]+"/"+a[3]+"/"+a[4]);    
	}   
	if (!(d.getFullYear()==a[2]&&(d.getMonth()+1)==a[3]&&d.getDate()==a[4]))return false;
	if(len=18)
	{
		len=0;
		for(var i=0;i<17;i++)len += card.substr(i, 1) * arrint[i];
		return arrch[len % 11] == card.substr(17, 1);
	}
	return true;
}

