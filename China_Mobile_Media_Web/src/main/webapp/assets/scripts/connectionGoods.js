var Video;
var img;
$(function(){
	var i=0;
//	$(".picbox_inner li").each(function(index, element) {
//		i=index+1;
//		$(".picbox_inner").css("width",(index+1)*180+"px");
//    });
	var n=0;
//	$(".rightImg").click(function(){
//		var ll=i-5;
//		var left=parseInt($(".picbox_inner").css("margin-left"));
//		if(left>-180*ll){
//			n++;
//			$(".picbox_inner").animate({"margin-left":-(n+1)*(180)},100);
//			
//		}else{
//					
//		}
//	});
//	$(".leftImg").click(function(){
//		var left=parseInt($(".picbox_inner").css("margin-left"));
//		if(left<0){
//			n--;
//			$(".picbox_inner").animate({"margin-left":-(n)*(180)},100);
//			
//		}else{
//				
//		}
//	});
	$("#contents .content_inners .picbox .picbox_inner li").click(function(){
		var time=$(this).find("span").attr("name");
		Video.currentTime=time;
		Video.pause();
		$(".ZeppinVideo_Top b").css("display","block");
		$(".kongzhianniu b").addClass("bgligh")
	});
	$(".ZeppinVideo .ZeppinVideo_Bottom .Progress .progressQQ").hover(function(event){
		t = setInterval(updatebars(event.pageX), 400);
//		setInterval(updatebars,100);
//      var top = event.pageY;
//		var left = event.pageX;
//		$("body").append(img);
//		$("#tips").css({
//			"top":top-40,    
//			"left":left-30	
//		});	
	},function(){
		clearInterval(t);
		$("#tips").remove();
	});
	$(".ZeppinVideo .ZeppinVideo_Bottom .Progress .progressQQ").mousemove(function(event){
		t = setInterval(updatebars(event.pageX), 400);
//      var top = event.pageY;
//		var left = event.pageX;
//		$("body").append(img);
//		alert(img)
//		$("#tips").css({
//			"top":top-40,    
//			"left":left-30	
//		});	
	});
	$(".ZeppinVideo .ZeppinVideo_Bottom .Progress .progressQQ").mouseleave(function(event){
		$("#tips").remove();	
	});
	
	
	
	 if(isFirefox=navigator.userAgent.indexOf("Firefox")>0){  
       // $(".kongzhianniu input").css("margin-top","5px");
   }  
   
    	document.onkeydown=function(event){
		var e = event || window.event || arguments.callee.caller.arguments[0];
	    var volume = $(Video).parent().siblings().find(".kongzhianniu input").get(0);
	    Video.volume = volume.value;
			if(e && e.keyCode==38){//上,左
				if(volume.value>=0&&volume.value<1){
					volume.value=Number(volume.value)+0.1;
				}else{
					volume.value=volume.value;
				}
			}else if(e && e.keyCode==40){
				if(volume.value>0){
					volume.value-=0.1;
				}else{
					volume.value=volume.value;
				}
			}
			if(e && e.keyCode==37){//下,右
				Video.currentTime-=1;
			}else if(e && e.keyCode==39){
				Video.currentTime+=1;
			}
	}; 


})
function ZeppinVideo_TopB(obj) {
    var VIDEO = $(obj).parent().parent().parent().parent().parent().siblings().find("video").get(0);
    //VIDEO.pause()
    $(VIDEO).parent().find("b").css({ "display": "block" })
    $(VIDEO).parent().siblings().find(".kongzhianniu b").addClass("bgligh")
}

function ClickVideo(video){

    video.controls = false;
    video.addEventListener("timeupdate", updateProgress, false);


    $(video).click(function () {
        CLICK()
    })

    $(video).parent().siblings().find(".kongzhianniu b").click(function () {
        CLICK()
    })
    $(video).prev().click(function () {
        CLICK()

    })
    $(video).parent().siblings().find(".kongzhianniu p").click(function () {
        launchFullScreen(video);
        //video.attr("autoplay") 
    })

    //静音开关
    $(video).parent().siblings().find(".kongzhianniu button").click(function () {
        video.muted = !video.muted;
        if ($(video).parent().siblings().find(".kongzhianniu button").hasClass("bglight")) {
            $(video).parent().siblings().find(".kongzhianniu button").removeClass("bglight")
        } else {
            $(video).parent().siblings().find(".kongzhianniu button").addClass("bglight");
        }
    })
    $(video).on('ended', function () {
        //CLICK()
        $(video).parent().find("b").css({ "display": "block" })
        $(video).parent().siblings().find(".kongzhianniu b").addClass("bgligh")
        //$(obj).parent().find(".Control_bottom b").addClass("bgligh")
    });

    //音量大小控制
    $(video).parent().siblings().find(".kongzhianniu input").change(function () {
        var volume = $(this).get(0);
        video.volume = volume.value;
    })

    function CLICK() {
        if (video.paused || video.ended) {
            video.play();
            $(video).parent().find("b").css({ "display": "none" })
            $(video).parent().siblings().find(".kongzhianniu b").removeClass("bgligh")
        }
        else {
            video.pause();
            $(video).parent().find("b").css({ "display": "block" })
            $(video).parent().siblings().find(".kongzhianniu b").addClass("bgligh")
        }
    }
    //进度条左右操作
    var timeDrag = false; /* check for drag event 拖动事件检查 */
    $(video).parent().siblings().find(".progressQQ").on('mousedown', function (e) {
        timeDrag = true;
        updatebar(e.pageX);
    });
    $(document).on('mouseup', function (e) {
        if (timeDrag) {
            timeDrag = false;
            updatebar(e.pageX);
        }
    });
    $(document).on('mousemove', function (e) {
        if (timeDrag) {
            updatebar(e.pageX);             
        }
        
    });

    var updatebar = function (x) {
        var progress = $('.progressQQ');
        //calculate drag position 计算拖拽位置
        //and update video currenttime 更新视频时间
        //as well as progress bar 以及进度条
        var maxduration = video.duration;
        var position = x - progress.offset().left;
        var percentage = 100 * position / progress.width();
        if (percentage > 100) {
            percentage = 100;
        }
        if (percentage < 0) {
            percentage = 0;
        }
        $(video).parent().siblings().find(".timeBar").css('width', percentage + '%');
        video.currentTime = maxduration * percentage / 100;
    };
	

    //进度条
    function updateProgress() {
        var progress = $(video).parent().siblings().find(".timeBar").get(0);
        var value = 0;
        if (video.currentTime > 0) {
            value = Math.floor((100 / video.duration) * video.currentTime);
        }
        progress.style.width = value + "%";
    }

    //全屏显示
    function launchFullScreen(element) {
        if (element.requestFullscreen) {
            element.requestFullscreen();
        } else if (element.mozRequestFullScreen) {
            element.mozRequestFullScreen();
        } else if (element.webkitRequestFullscreen) {
            element.webkitRequestFullscreen();
        } else if (element.msRequestFullscreen) {
            element.msRequestFullscreen();
        }
    }
}


var updatebars = function (x) {
        var progress = $('.progressQQ');
        if(Video!=null){
	        var maxduration = Video.duration;
	        var positions = x - progress.offset().left;
	        var percentage = 100 * positions / progress.width();
	        if (percentage > 100) {
	            percentage = 100;
	        }
	        if (percentage < 0) {
	            percentage = 0;
	        }
	        var intNumber=Math.round(maxduration * percentage / 100);
	        var Number=parseInt(intNumber/60);
	        var xiaoshu=intNumber-Number*60;
	        img = "<span id='tips' style='z-index:666'>"+Number+":"+xiaoshu+"</span>";
	        $("#tips").remove();	
	        $("body").prepend(img);
	        var top = event.pageY;
			var left = event.pageX;
	        $("#tips").css({
				"top":top-40,    
				"left":left-30	
			});	
        }
    };