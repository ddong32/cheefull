function dhZoomer($imgFrame,o){
    var scale = 1;
    var size = {
            w:"",
            h:"",
            pw : $imgFrame.width(),
            ph : $imgFrame.height(),
            scale : 1
        },
        option = {
    		scaleStep: 0.05,
    		initScale: 1.5,
    		maxScale: 5,
    		toolbar: false
    	};


    if (true){
        if(typeof (o) != "undefined") {option = $.extend({}, option, o);}

        var init = function ($imgFrame,size){
        	var $this = $imgFrame;
        	var $dhzoomer = null;
        	var $mask = null;
        	if ($("#dhZoomer").length <= 0 || $("#dhZoomerMask").length <= 0){
        		$("#dhZoomer").remove();
        		$dhzoomer = $('<div/>').attr("id","dhZoomer").css({position:"absolute", zIndex:9999, display:"none"});
        		if (option.type == "flow"){
                	$dhzoomer.css({width:option.zWidth, height:option.zHeight, overflow:"hidden", border:"1px solid #fff"}).appendTo($("body"));
                	$mask = $("<div/>").attr("id","dhZoomerMask").css({position:"absolute", zIndex:9998,display:"none",backgroundColor:"#FFF"}).addClass("translucent").appendTo($this);
                } else {
                	$dhzoomer.appendTo($this);
                }
        	} else {
        			$dhzoomer = $("#dhZoomer");
        			$mask = $("#dhZoomerMask");
        	}
        	var $zoomerImg;
        	if($("#zoomerImg").length<=0){
        		$zoomerImg = $("<img/>").attr("id","zoomerImg").css({width:"100%"}).appendTo($dhzoomer);
        	} else {
        		$zoomerImg = $("#zoomerImg");
        	}
        	var image = new Image();
            var src;
	        image.onload = function(){
	            size.w = image.width;
	            size.h = image.height;
	            size.scale= size.ph/size.h*option.initScale;
	            if (option.type == "flow"){
	            	$zoomerImg.css({position:"absolute"});
	            }
	            image = null;
	        }
	        src = typeof($this.find("img").attr("data-original"))=="undefined" ? $this.find("img").attr("src") : $this.find("img").attr("data-original");
	        image.src = src;
	        
            if(option.toolbar){
	            if ($this.find(".toolbar").length<=0){
	            	var $toolbar = $("<div/>").css({display:"none", position:"absolute", bottom:10,width:"100%", height:24, zIndex:9999}).attr("class","toolbar");
	            	var $toolbarBtn = $("<div/>").css({height:20,width:125,float:"right",marginRight:10,marginTop:5, lineHeight:"20px",textAlign:"center", color:"#FFF"}).addClass("translucent")
	            	.html("点击图片可查看原图")
	            	.appendTo($toolbar);
	            	$this.find("img").before($toolbar);
	            }
            }
            $this.mouseenter(function(e){
            	$zoomerImg.attr("src",$this.find("img").attr("src")).css({width:size.w*size.scale, height:size.h*size.scale});
            	if (option.type == "flow"){
    	        	$mask.appendTo($this);
    	        } else {
    	        	$dhzoomer.appendTo($this);
    	        }
    	        if (typeof($toolbar)!="undefined"){
    	        	$toolbar.show();
    	        }
    	        $dhzoomer.show();
                $mask.show();
    	        setMaskSize();
                setZoomerPosition(e);
                
                if(option.border){
                	$this.css({border:"#FF0000 2px solid"});
                }
            });
            $this.bind('mousemove',function(e){
                setZoomerPosition(e);
            });
            $this.unbind("mousewheel");
            $this.mousewheel(function(e,delta){
            	e.preventDefault();
                if (delta<0){
                    if (size.h*(size.scale-option.scaleStep) > size.ph && size.w*(size.scale-0.05) > size.pw){
                        size.scale-=option.scaleStep;
                    }
                } else if (delta>0){
                    if (size.scale-option.scaleStep < size.ph/size.h*option.maxScale){
                        size.scale+=option.scaleStep;
                    }
                }
                $zoomerImg.css({width:size.w*size.scale, height:size.h*size.scale});
                setMaskSize()
                setZoomerPosition(e);
            });
            $this.mouseleave(function(e){
            	if (typeof($toolbar)!="undefined"){
                	$toolbar.hide();
                }
                $dhzoomer.hide();
                if(option.type=="flow"){
                	$mask.hide();
                }
                if(option.border){
                	$this.css({border:"#dbdeec 2px solid" , width:size.pw, height:size.ph});
                }
            });
            
            if (option.toolbar){
	            $this.click(function(){
	        		window.open(typeof($this.find("img").attr("orsrc"))=="undefined" || $this.find("img").attr("orsrc")=="" ? $this.find("img").attr("src"): $this.find("img").attr("orsrc"),'picview',"width="+window.screen.availWidth+",height="+window.screen.availHeight+",scrollbars=yes,left=0,top=0");
	            });
            }

            var setZoomerPosition = function(e){
            	var browseroffset = {left:$this[0].getBoundingClientRect().left, top:$this[0].getBoundingClientRect().top}
                var point = {x:e.clientX-browseroffset.left, y:e.clientY-browseroffset.top};
                var offsetTop = 0;
                var offsetLeft = 0;
                if(option.type = "flow"){
                    
                    var mw = $mask.width();
                    var mh = $mask.height();
                    var ot = point.y-mh/2
                    var ol = point.x-mw/2;
                    if(ot <= 0){
                    	ot = 0;
                    } else if (ot+mh >= size.ph){
                    	ot = size.ph-mh;
                    }
                    if(ol <= 0){
                    	ol = 0;
                    } else if (ol+mw >= size.pw){
                    	ol = size.pw-mw;
                    }
                	$mask.css({top:ot, left:ol});
                	
                	var mo = $mask.offset();
                	offsetTop = -point.y*size.h*size.scale/size.ph+option.zHeight/2;
                    offsetLeft = -point.x*size.w*size.scale/size.pw+option.zWidth/2;
                    offsetTop = offsetTop >= 0 ? 0 : offsetTop-option.zHeight < -size.h*size.scale ? -size.h*size.scale+option.zHeight : offsetTop;
                    offsetLeft = offsetLeft >=0 ? 0 : offsetLeft-option.zWidth < -size.w*size.scale ? -size.w*size.scale+option.zWidth : offsetLeft;
                    
                	if(mo.left+mw+option.zWidth >= document.body.clientWidth-10){
                		$dhzoomer.css({top:mo.top, left:mo.left-option.zWidth});
                	} else {
                		$dhzoomer.css({top:mo.top, left:mo.left+$mask.width()});
                	}
                	$zoomerImg.css({top:offsetTop, left:offsetLeft});
                } else {
                	offsetTop = -point.y*size.h*size.scale/size.ph+size.ph/2;
                    offsetLeft = -point.x*size.w*size.scale/size.pw+size.pw/2;
                	offsetTop = offsetTop >= 0 ? 0 : offsetTop-size.ph < -size.h*size.scale ? -size.h*size.scale+size.ph : offsetTop;
                    offsetLeft = offsetLeft >=0 ? 0 : offsetLeft-size.pw < -size.w*size.scale ? -size.w*size.scale+size.pw : offsetLeft;
                	$dhzoomer.css({top:offsetTop, left:offsetLeft});
                }
            }
            
            var setMaskSize = function(){
            	$mask.css({width:option.zWidth*size.pw/(size.w*size.scale), height:option.zHeight*size.ph/(size.h*size.scale)});
            }
        };
        init($imgFrame, size);
    }

}