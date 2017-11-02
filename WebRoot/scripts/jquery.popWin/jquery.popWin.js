/***
*作者:陈堂宋
*日期:2013-3-01
*个人网站:www.uinote.cn
*插件说明:
* 1.定义你的弹窗窗体最外围关键div position:fix; right:2px; bottom:2px;
* 2.最外围关键div style="display:none"事先隐藏的。
* 3.调用插件方法$.fn.popWin({
	PopSelector:".demo",  *************弹出的窗体
	CloseSelector:".demo .close", *****关闭窗体的触发开关
	lideTime:600  *********************窗体出场入场动画的时间
	})
***/
/*ie6不支持position:fixed属性，以下是引用positionFixed插件*/
(function($j){
    $j.positionFixed = function(el){
        $j(el).each(function(){
            new fixed(this)
        });
        return el;                  
    };
    $j.fn.positionFixed = function(){
        return $j.positionFixed(this)
    }
    var fixed = $j.positionFixed.impl = function(el){
        var o=this;
        o.sts={
            target : $j(el).css('position','fixed'),
            container : $j(window)
        }
        o.sts.currentCss = {
            top : o.sts.target.css('top'),              
            right : o.sts.target.css('right'),              
            bottom : o.sts.target.css('bottom'),                
            left : o.sts.target.css('left')             
        }
        if(!o.ie6)return;
        o.bindEvent();
    }
    $j.extend(fixed.prototype,{
        ie6 : $.browser.msie && $.browser.version < 7.0,
        bindEvent : function(){
            var o=this;
            o.sts.target.css('position','absolute')
            o.overRelative().initBasePos();
            o.sts.target.css(o.sts.basePos)
            o.sts.container.scroll(o.scrollEvent()).resize(o.resizeEvent());
            o.setPos();
        },
        overRelative : function(){
            var o=this;
            var relative = o.sts.target.parents().filter(function(){
                if($j(this).css('position')=='relative')return this;
            })
            if(relative.size()>0)relative.after(o.sts.target)
            return o;
        },
        initBasePos : function(){
            var o=this;
            o.sts.basePos = {
                top: o.sts.target.offset().top - (o.sts.currentCss.top=='auto'?o.sts.container.scrollTop():0),
                left: o.sts.target.offset().left - (o.sts.currentCss.left=='auto'?o.sts.container.scrollLeft():0)
            }
            return o;
        },
        setPos : function(){
            var o=this;
            o.sts.target.css({
                top: o.sts.container.scrollTop() + o.sts.basePos.top,
                left: o.sts.container.scrollLeft() + o.sts.basePos.left
            })
        },
        scrollEvent : function(){
            var o=this;
            return function(){
                o.setPos();
            }
        },
        resizeEvent : function(){
            var o=this;
            return function(){
                setTimeout(function(){
                    o.sts.target.css(o.sts.currentCss)      
                    o.initBasePos();
                    o.setPos()
                },1)    
            }           
        }
    })
})(jQuery);

/*
*定义一个弹窗类，有两个参数
*PopSelector:要把哪个容器弹出，即弹出层的选择器。
*CloseSelector：自定义的关闭按钮
**/
(function($){
    $.fn.popWin =function(options){
		var def={
			PopSelector:"",  //弹窗的选择器
			CloseSelector:"",//关闭开关的选择器
			slideTime:600    //弹窗出场入场动画的时间
		};
		var options =$.extend(def,options);
		var noie6 =!($.browser.msie && ($.browser.version == "6.0") && !$.support.style);
		var PopSelector=options.PopSelector,CloseSelector=options.CloseSelector,slideTime=options.slideTime;
		if (noie6) {
			$(PopSelector).slideDown(slideTime)} 
		else{//解决ie6不兼容fixed
			$(PopSelector).fadeIn(slideTime);//ie6的效果，没有slideDown那么炫。
			var thisPop = $(PopSelector);
			jQuery(function($j){
					$j(thisPop).positionFixed()})
			}
		if (noie6) {
      		$(CloseSelector).click(function(){$(PopSelector).slideUp(slideTime);});
     		}
	 	else{
      		$(CloseSelector).click(function(){$(PopSelector).fadeOut(slideTime);});//ie6的效果，没有slideUp那么炫
		}
	}
})(jQuery);