function dhZoomer($imgFrame,o){
    var $dhzoomer = $("#dhZoomer");
    var size = {
            w:"",
            h:"",
            pw : $imgFrame.find('.img').width(),
            ph : $imgFrame.find('.img').height()
        },
        option = {};
    if ($dhzoomer.length <= 0 || typeof ($dhzoomer) == "undefined"){
        if(typeof (o) != "undefined") {option = o;}
        if (typeof(option.width) != "undefined"){
            size.w = option.width;
        } else {
            size.w = size.pw;
        }
        if (typeof (option.height) != "undefined"){
            size.h = option.height;
        } else {
            size.h = size.ph;
        }

        var init = function ($imgFrame,size){
            var $dhzoomer = $('<div/>').attr("id","dhZoomer").css({position:"absolute", zIndex:9999, display:"none", overflow:"hidden",width:size.w, height:size.h});
            var $zoomerImg = $("<img/>").attr("id","zoomerImg").css({position:"absolute"});
            var $mask = $("<div/>").attr("id","dhZoomerMask").css({position:"absolute", zIndex:9998,display:"none",backgroundColor:"#FFF",opacity:"0.3", filter:"Alpha(Opacity=30)"});
            $imgFrame.find('.img').each(function(){
                var $this = $(this);
                var previewWidth = size.pw;
                var previewHeight = size.ph;
                var previewTop = $this.offset().top;
                var previewLeft = $this.offset().left;
                if ($this.find(".toolbar").length<=0){
                	var $toolbar = $("<div/>").css({display:"none", position:"absolute", top:0,width:"100%", height:24, zIndex:999}).attr("class","toolbar");
                	var $toolbarBtn = $("<div/>").css({cursor:"pointer", height:20,width:60,float:"right",marginRight:10,marginTop:5, lineHeight:"20px",textAlign:"center", background:"#FFF"})
                	.html("查看原图")
                	.click(function(){
                		window.open($this.find("img").attr("src"));
                	})
                	.appendTo($toolbar);
                	$this.find("img").before($toolbar);
                }
                if(!$this.data("bindZoomer")){ //未绑定鼠标事件
                    $this.mouseenter(function(e){ //鼠标进入
                        var image = new Image(),
                            src,
                            imgWidth,
                            imgHeight;
                        image.onload = function(){
                            imgWidth = image.width;
                            imgHeight = image.height;
                            if (imgWidth < 1500){
                            	imgHeight = 1500*imgHeight/imgWidth;
                            	imgWidth = 1500;         	
                            }
                            $zoomerImg.css({width:imgWidth, height:imgHeight});
                            $zoomerImg.attr("src",src).appendTo($dhzoomer); //放大镜加载图片并显示
                            if (!$this.data("bindZoomer") && option.position == "inside"){
                            	$mask.css({width:size.w/imgWidth*previewWidth, height:size.h/imgHeight*previewHeight*0.3});
                            } else {
                            	$mask.css({width:size.w/imgWidth*previewWidth, height:size.h/imgHeight*previewHeight});
                            }
                            $mask.data("target",$this);
                        }
                        src = $this.find("img").attr("src");
                        image.src = src;
                        var offsetLeft = previewLeft + size.pw;
                        var offsetTop = previewTop;
                        if (offsetLeft+size.w >= $imgFrame.width()+100){
                            offsetLeft = previewLeft - size.w;
                        }
                        if (!$this.data("bindZoomer")){
	                        if(option.position == "inside"){
	                        	size.h = size.h*0.3;
		                        $dhzoomer.css({left:0, top:0, height:size.h});
		                        $dhzoomer.appendTo($imgFrame);
	                        } else {
	                        	$dhzoomer.css({left:offsetLeft, top:offsetTop});
		                        $dhzoomer.appendTo($('body'));
	                        }
                        } else if(option.position != "inside"){
                        	$dhzoomer.css({left:offsetLeft, top:offsetTop});
                        }
                        $mask.appendTo($this);
                        $mask.show();
                        $dhzoomer.show();
                        if (typeof($toolbar)!="undefined"){
                        	$toolbar.show();
                        }
                        $this.data("bindZoomer",true);
                    })
                    $this.bind('mousemove',function(e){ //鼠标移动
                        var point = {x:e.clientX-previewLeft, y:e.clientY-previewTop};
                        var offsetTop = -point.y*$zoomerImg.height()/size.ph+size.h/2;
                        var offsetLeft = -point.x*$zoomerImg.width()/size.pw+size.w/2;
                        var maskTop = point.y-$mask.height()/2;
                        var maskLeft = point.x-$mask.width()/2;
                        offsetTop = offsetTop >= 0 ? 0 : offsetTop+$zoomerImg.height() < size.h ? size.h-$zoomerImg.height() : offsetTop;
                        offsetLeft = offsetLeft >=0 ? 0 : offsetLeft+$zoomerImg.width() < size.w ? size.w-$zoomerImg.width() : offsetLeft;
                        if (maskTop <= 0){
                            maskTop = 0;
                        } else if(maskTop+$mask.height() >= size.ph){
                            maskTop = size.ph - $mask.height();
                        }
                        if (maskLeft <= 0){
                            maskLeft = 0;
                        } else if(maskLeft+$mask.width() >= size.pw){
                            maskLeft = size.pw - $mask.width();
                        }
                        $zoomerImg.css({top:offsetTop, left:offsetLeft});
                        $mask.css({top:maskTop, left:maskLeft});
                    });

                }
                $this.mouseleave(function(e){ //鼠标移开
                    $mask.hide();
                    $dhzoomer.hide();
                    if (typeof($toolbar)!="undefined"){
                    	$toolbar.hide();
                    }
                });
            });

            $dhzoomer.bind('mouseover',function(){
            	if (option.position =="inside"){
            		if ($dhzoomer.css("top")=="0px"){
            			$dhzoomer.css({top:"", bottom:0});
            		} else {
            		    $dhzoomer.css({top:0, bottom:""});
            		}
            	}
                $dhzoomer.hide();
            });
        };
        init($imgFrame, size);
    }

}
