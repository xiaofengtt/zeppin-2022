var Video;
var img;
$(function(){
	var i=0;
	var n=0;
	$("#contents .content_inners .picbox .picbox_inner li").click(function(){
		var time=$(this).find("span").attr("name");
		Video.currentTime=time;
		Video.pause();
		$(".ZeppinVideo_Top b").css("display","block");
		$(".kongzhianniu b").addClass("bgligh")
	});
	$(".ZeppinVideo .ZeppinVideo_Bottom .Progress .progressQQ").hover(function(event){
		t = setInterval(updatebars(event.pageX), 400);
	},function(){
		clearInterval(t);
		$("#tips").remove();
	});
	$(".ZeppinVideo .ZeppinVideo_Bottom .Progress .progressQQ").mousemove(function(event){
		t = setInterval(updatebars(event.pageX), 400);
	});
	$(".ZeppinVideo .ZeppinVideo_Bottom .Progress .progressQQ").mouseleave(function(event){
		$("#tips").remove();	
	});
	
	if(isFirefox=navigator.userAgent.indexOf("Firefox")>0){
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
    $(VIDEO).parent().find("b").css({ "display": "block" })
    $(VIDEO).parent().siblings().find(".kongzhianniu b").addClass("bgligh")
}
var volume;
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
    })
    //静音开关
    $(video).parent().siblings().find(".kongzhianniu button").click(function () {
        video.muted = !video.muted;
        if ($(video).parent().siblings().find(".kongzhianniu button").hasClass("bglight")) {
            $(video).parent().siblings().find(".kongzhianniu button").removeClass("bglight");
            $("#voiceInput").val(volume);
        } else {
            $(video).parent().siblings().find(".kongzhianniu button").addClass("bglight");
            volume = $("#voiceInput").val();
            $("#voiceInput").val("0");
        }
    })
    $(video).on('ended', function () {
        $(video).parent().find("b").css({ "display": "block" })
        $(video).parent().siblings().find(".kongzhianniu b").addClass("bgligh")
    });

    //音量大小控制
    /*$(video).parent().siblings().find(".kongzhianniu input").change(function () {
	    if(video.muted==true){
	    	video.muted = !video.muted;
	    }else{
	    	
	    }
    	$(video).parent().siblings().find(".kongzhianniu button").removeClass("bglight")
        var volume = $(this).get(0);
        video.volume = volume.value;
    })*/
    $(video).parent().siblings().find(".kongzhianniu input").on('mousedown',function () {
    	if(video.muted==true){
	    	video.muted = !video.muted;
	    }else{
	    	
	    }
    	$(video).parent().siblings().find(".kongzhianniu button").removeClass("bglight");
    	
    })
    $(video).parent().siblings().find(".kongzhianniu input").on('mousemove',function () {
    	if(video.muted==true){
	    	video.muted = !video.muted;
	    }else{
	    	
	    }
    	
        var volume = $(this).get(0);
        video.volume = volume.value;
        if($("#voiceInput").val()=="0"){
    		$(video).parent().siblings().find(".kongzhianniu button").addClass("bglight");
    	}else{
    		$(video).parent().siblings().find(".kongzhianniu button").removeClass("bglight");
    	}
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
        img= "<span id='tips' style='z-index:666'>";
        if(Number<10){
        	img = img + "0" +Number+":";
        }else{
        	img = img +Number+":";
        }
        if(xiaoshu<10){
        	img = img + "0" + xiaoshu;
        }else{
        	img = img + xiaoshu;
        }
        img = img + "</span>";
//        img = "<span id='tips' style='z-index:666'>"+Number+":"+xiaoshu+"</span>";
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