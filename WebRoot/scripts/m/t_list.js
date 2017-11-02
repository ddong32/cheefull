$(function () {
    reTop();
    mePage("#dest-name", function (m) {
        $("#s_cName").val("");
        setTimeout(function(){
			$("#s_cName").focus()
		},500);
        $("#dest-close").on("touch click", function () {
            m();
            $("#goBack").click();
			$(".tab-screen").removeClass("tab-fixed")
        });
        $("#dest-click").on("touch click", function () {
            $("#LinesSearch").submit()
        }); 
    });
    if ($(".flag-pri li.c-ba").data("sell") != 0 && $(".flag-pri li.c-ba").data("base") != 0) {
        $("#sort-4 .flag-um li").eq(1).addClass("lab")
    }
    if ($(".flag-day li.c-ba").data("rel") != "" || $(".flag-day li.c-ba").data("rel") != 0) {
        $("#sort-4 .flag-um li").eq(0).addClass("lab")
    }
});
var _page=1,sa=0;
//getList();
bindScroll();
function bindScroll(){
    $(window).on("scroll touch", function () {
        var top = $(this).scrollTop();
        var hei = $(document).height();
        var wh = $(this).height();
        if (top + wh >= hei - 60) {
            if (sa == 0) {
                getList();
                sa = 1
            }
        }
    });
}
function getList(){
   var LCid=$("#LCid").val(),Sche=$("#SchedulingDayNumber").val(),Day=$("#SchedulingDay").val(),Base=$("#BasePrice").val(),Sell=$("#SellPrice").val(),City=$("#city-hid").val(),Name=encodeURI($("#keyword").val());
   $.ajax({
       type:"GET",
       url:"/mobile/Lvyou/ListMore.html",
       data: "Name=" + Name + "&page=" + _page + "&LCid=" + LCid + "&Sche=" + Sche + "&SchedulingDay=" + Day + "&BasePrice=" + Base + "&SellPrice=" + Sell + "&City=" + City,
       dataType:"html",
       success: function (data) { 
           if (data == false) {
               sa = 1;
               if (_page == 1) {
                  $("#t-list").empty();
				  $(".list-more").text("暂无产品")
               }else{
				  $(".list-more").text("没有更多产品啦") 
			   }
           } else {
			   $(".list-more").text("正在加载更多"); 
               var n=0; 
               if (_page == 1) {
                   $("#t-list").empty();
                   $("#t-list").html(data);
				   $(window).scrollTop(0,0);
               } else {
				   n = $("#t-list").find(".list-item").length;
                   $("#t-list").append(data); 
               }
               var n2 = $("#t-list").find(".list-item").length;
               if (n2 - n < 10) {
                   sa = 1;
                   $(".list-more").text("没有更多产品啦");
               } else {
                   _page++;
                   sa = 0;
               }
           }
       }
   })
}
$(window).on("scroll",function () { 
	if ($(this).scrollTop() >=$(".tab-nav").offset().top) { 
		$(".tab-screen").addClass("tab-fixed")
	}else{
		$(".tab-screen").removeClass("tab-fixed")
    }
});
$(".tab-ul > li").on("click touch",function(){
	var flag=$(this).data("flag");
	if($(this).hasClass("c-on")){
	   	hid()
	}else{
		$(this).addClass("c-on").siblings().removeClass("c-on");
		$("#sort-bg").show();
		$("html").css("overflow","hidden");
		$("body").css("overflow-x","visible");
		$(".sort-ani").hide();
		$("#"+flag).stop(true).fadeIn(200);
	}
})
$("#sort-bg").on("click touch",function(){
	hid()
})
$(".city-item li").on("click touch", function () {
    var id = $(this).data("id"), name = $(this).text();
    $("#city-hid").val(id);
    $(this).addClass("col-1").siblings().removeClass("col-1");
    $(".tab-ul li").eq(0).find("b").text(name);
	GetListCity(1);
    hid();
    _page = 1;
    getList();
})
//GetListCity(0);
//加载目的地
function GetListCity(am) {
    $.ajax({
        type: "GET",
        url: "/mobile/Lvyou/ListCity.html",
        data: "City=" + $("#city-hid").val(),
        success: function (data) {
            $("#sort-2").html(data);
			if(am){
			  $(".tab-ul li").eq(1).find("b").text("目的地");
			  $("#LCid").val("");
			}
			sortTwo()
        }
    })
}    
function hid(){
	$(".tab-ul > li").removeClass("c-on");
	$(".sort-ani").hide();
	$("#sort-bg").hide();
	$("html").css("overflow","visible");
	$("body").css("overflow-x","hidden");
}
function sortTwo(){
  $(".panel-um li").on("click touch",function(){ 
	  $(this).addClass("col-1").siblings().removeClass("col-1");
	  if($(this).hasClass("penel-m")){
		 var rel=$(this).data("rel"),name=$(this).data("name");
		 $(".tab-ul li").eq(1).find("b").text(name);
		 $(".panel-item li").removeClass("col-1");
		 $("#LCid").val(rel);
		 hid();
		 _page=1;
		 getList() 
	  }else{
		 var n=$(this).index();
		 $(this).closest(".filter-panel").find(".panel-item").eq(n).show().siblings(".panel-item").hide()
	  }
  })
  $(".panel-item li").on("click touch",function(){ 
	  $(this).addClass("col-1").siblings().removeClass("col-1");
	  var rel=$(this).data("rel"),name=$(this).data("name");
	  $(".tab-ul li").eq(1).find("b").text(name);
	  $("#LCid").val(rel);
	  hid();
	  _page=1;
	  getList()
  })
}
$(".dia-um li").on("click touch",function(){ 
    $(this).addClass("col-1").siblings().removeClass("col-1");
	var id=$(this).data("rel"),name=$(this).text();
	$(".tab-ul li").eq(2).find("b").text(name);
    $("#SchedulingDayNumber").val(id);
    hid();
    _page=1;
    getList() 
})
$(".flag-um li").on("click touch",function(){ 
    $(this).addClass("col-1").siblings().removeClass("col-1");
	var n=$(this).index();
	$(this).closest(".flag-box").find(".flag-item").eq(n).show().siblings(".flag-item").hide()
})
$(".flag-day li").on("click touch",function(){ 
    $(this).addClass("c-ba").siblings().removeClass("c-ba");
	var n=$(this).index();
	if(n==0){
	   $(".flag-um li").eq(0).removeClass("lab")	
	}else{
	   $(".flag-um li").eq(0).addClass("lab")	
	}
})
$(".flag-pri li").on("click touch",function(){ 
    $(this).addClass("c-ba").siblings().removeClass("c-ba");
	var n=$(this).index();
	if(n==0){
	   $(".flag-um li").eq(1).removeClass("lab")	
	}else{
	   $(".flag-um li").eq(1).addClass("lab")	
	}
})
$("a.confirm").on("click touch",function(){ 
    $("#SchedulingDay").val($(".flag-day li.c-ba").data("rel"));
	$("#BasePrice").val($(".flag-pri li.c-ba").data("base"));
	$("#SellPrice").val($(".flag-pri li.c-ba").data("sell"));
    hid();
    _page=1;
    getList()
})