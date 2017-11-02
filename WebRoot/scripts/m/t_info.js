$("#loader").hide();
var swiper = new Swiper('.swiper-container', {
	pagination: '.swiper-pagination',
	paginationClickable: true,
	loop:true,
	centeredSlides: true,
	autoplay: 3000,
	autoplayDisableOnInteraction: false,
	lazyLoading:true
});
var hei=$(".detail-box > .tab").offset().top; 
$(window).on("scroll touch",function(){
	if($(window).scrollTop()>=hei){
	   $(".detail-box > .tab").addClass("tabfixed");
	   $(".detail-box > .detail-list").addClass("detail-list-top")  
	}else{
	   $(".detail-box > .tab").removeClass("tabfixed");
	   $(".detail-box > .detail-list").removeClass("detail-list-top")   
	}  
});
$(".tab-list > li.tab-n").on("click touch",function(){
   $(this).addClass("col-1").siblings("li.tab-n").removeClass("col-1");
   var n=$(this).index(),key=$(this).data("key");
   $(".tab-list > li.tab-slider").css("left",(n*33.33)+"%");
   $(".detail-list > .list-item").hide();
   $("#list-"+key).show();
   $(window).scrollTop(hei)	
});
$("a.booking-2").on("click touch",function(){
   error($(this).data("rel"))
});
$("a.booking").on("click touch",function(){
   $("#OrderForm").submit()
});	
$(window).load(function(){
   $(".list-item img").each(function(){
		var imgs=new Image();
		imgs.src=$(this).attr("src");
		var a=imgs.naturalWidth;  
		var b=imgs.naturalHeight;
		if( a < 80 ){
			$(this).width(a);
			$(this).height(b);
		}else{
			$(this).width("100%");
			$(this).height("auto");
	   }		
   });
});