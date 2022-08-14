var Video=document.getElementById("zeppinVideo");
var pagetype= (url('?pagetype') != null) ? url('?pagetype') : '';
var ie = !-[1,]; 
var height=$(window).height();
document.onkeydown=function(event){
    var e = event || window.event || arguments.callee.caller.arguments[0];
    if(e && e.keyCode==27){ // 按 Esc 
    	$("#show").css("display","block");
    	$("#hide").css("display","none");
    	$(".ZeppinVideo .ZeppinVideo_Top video").removeAttr("style");
    	$("#quanping").removeAttr("style");
    	$(".layerDD").css({"height":"662px"});
    	$("#quanping .quanping-container").removeAttr("style");
      } 
   }
$(function(){
	if(navigator.userAgent.toLowerCase().indexOf('chrome')>-1) {
		$("#quanping").css("display","none");
		videoInfo(id,category);
	}else if(navigator.userAgent.toLowerCase().indexOf('firefox')>-1||navigator.userAgent.indexOf("Safari") > -1){

		$("#videoFlash").css("display","none");
		videoInfos(id,category);
		setInterval("playTime()",500);
		
		//$(".wraps").height($(window).height()*0.9);
	}else{
		$("#quanping").css("display","none");
		videoInfo(id,category);
	}
	if(ie==false){
		
	}
	if(navigator.userAgent.indexOf("Safari") > -1){
		$(".barbar input").css("margin-top","25px");
	}
			if(navigator.userAgent.indexOf('UCBrowser') > -1) {
				$(".playAnimate").css("display","none");
				$(".ZeppinVideo_Bottom").css("display","none");
				$(".ZeppinVideo").css("padding-bottom","0rem");

				}else if(navigator.userAgent.toLowerCase().indexOf("360se")>-1) {
					$(".playAnimate").css("display","none");
					$(".ZeppinVideo_Bottom").css("display","none");
					$(".ZeppinVideo").css("padding-bottom","0rem");

				}else if(navigator.userAgent.toLowerCase().indexOf('baidu')>-1) {
					$(".playAnimate").css("display","none");
					$(".ZeppinVideo_Bottom").css("display","none");
					$(".ZeppinVideo").css("padding-bottom","0rem");

				}else if(navigator.userAgent.toLowerCase().indexOf('qqbrowser')>-1) {
					$(".playAnimate").css("display","none");
					$(".ZeppinVideo_Bottom").css("display","none");
					$(".ZeppinVideo").css("padding-bottom","0rem");

				}else if(navigator.userAgent.toLowerCase().indexOf('se')>-1) {
					$(".playAnimate").css("display","none");
					$(".ZeppinVideo_Bottom").css("display","none");
					$(".ZeppinVideo").css("padding-bottom","0rem");

				}else if(navigator.userAgent.toLowerCase().indexOf('firefox')>-1) {
					$(".swiper-container").css("display","none");
					//$(".barbar .currentTime").css("font-size","0.2rem");
					//$(".barbar .totleTime").css("font-size","0.2rem");
					//$(".videoAnimate .titleP").css("font-size","0.18rem");
					$(".barbar select").css("font-size","14px");
					$(".playAnimate").css("display","none");

				}else if(navigator.userAgent.toLowerCase().indexOf('chrome')>-1) {
					$(".swiper-container").css("display","none");
					$(".playAnimate").css("display","none");

				}else if(navigator.userAgent.toLowerCase().indexOf('firefox')>-1&&navigator.userAgent.toLowerCase().indexOf('360')>-1) {
					$(".swiper-container").css("display","none");

				}else if(navigator.userAgent.toLowerCase().indexOf("Opera")>-1) {
					$(".swiper-container").css("display","none");
					$(".playAnimate").css("display","none");
				}else if(navigator.userAgent.toLowerCase().indexOf('qqbrowser')>-1&&navigator.userAgent.toLowerCase().indexOf('chrome')>-1&&navigator.userAgent.toLowerCase().indexOf('se')>-1) {
				   $(".playAnimate").css("display","none");
				   $(".swiper-container").css("display","block");

				}else{ 
					$(".playAnimate").css("display","none");
				}
		
	$(".ZeppinVideo .ZeppinVideo_Bottom .ProgressBar .ProgressBarNode i.node").hover(function(){
		$(this).find("p").stop().fadeIn(500);
	},function(){
		$(this).find("p").stop().fadeOut(500);
	});
	 if(isFirefox=navigator.userAgent.indexOf("Firefox")>0){  
        $(".barbar input").css("margin-top","5px");
   }  
   

})

function ClickVideo(video){
	 video.controls = false;
	    video.addEventListener("timeupdate", updateProgressBar, false);

	    $(video).click(function () {
	    	clickZeppinVideo();
	    })
	    $(".barbar b").click(function () {
	    	clickZeppinVideo();
	    })
	    $(video).prev().click(function () {
	    	clickZeppinVideo();
	    })
	    $(video).parent().siblings().find(".barbar button").click(function () {
	        video.muted = !video.muted;
	        if ($(video).parent().siblings().find(".barbar button").hasClass("bglightt")) {
	            $(video).parent().siblings().find(".barbar button").removeClass("bglightt")
	        } else {
	            $(video).parent().siblings().find(".barbar button").addClass("bglightt");
	        }
	    })
	    $(video).on('ended', function () {
	        $(video).parent().siblings().find(".barbar b").addClass("bglight");
	    });
	    $(video).parent().siblings().find(".barbar input").change(function () {
	        var volume = $(this).get(0);
	        video.volume = volume.value;
	    })

	    function clickZeppinVideo() {
	        if (video.paused || video.ended) {
	            video.play();
	            $(".barbar b").removeClass("bglight");
	        }
	        else {
	            video.pause();
	            $(".barbar b").addClass("bglight");
	        }
	    }

	    var timeDrag = false;
	    $(".ProgressBarNode").on('mousedown', function (e) {
	    	var event = arguments.callee.caller.arguments[0] || window.event; 
	        timeDrag = true;
	        updatebar(Number(event.pageX));
	    });
	    $(document).on('mouseup', function (e) {
	    	var event = arguments.callee.caller.arguments[0] || window.event; 
	        if (timeDrag) {
	            timeDrag = false;
	            updatebar(Number(event.pageX));
	            for(var i=0;i<arr.length;i++){
	            	$(".videoAnimate"+i).css("bottom","80px");
	            }
	        }
	    });
	    $(document).on('mousemove', function (e) {   	
	    	var event = arguments.callee.caller.arguments[0] || window.event; 
	        if (timeDrag) {
	            updatebar(Number(event.pageX));  
	        }
	    });

	    var updatebar = function (x) {
	        var ProgressBar = $('.ProgressBarNode');
	        var maxduration = video.duration;
	        var position = x - ProgressBar.offset().left;
	        var percentage = 100 * position / ProgressBar.width();
	        if (percentage > 100) {
	            percentage = 100;
	        }
	        if (percentage < 0) {
	            percentage = 0;
	        }
	        $(".timeBar").css('width', percentage + '%');
	        video.currentTime = maxduration * percentage / 100;
	    };
		
	    function updateProgressBar() {
	        var ProgressBar = $(".timeBar").get(0);
	        var value = 0;
	        if (video.currentTime > 0) {
	            value = Math.floor((100 / video.duration) * video.currentTime);
	        }
	        if(ProgressBar != undefined){
	        	ProgressBar.style.width = value + "%";
	        }
	    }
}


//阻止默认事件和冒泡
function stopBubble(e) {
	if ( e && e.stopPropagation ) { 
	    e.stopPropagation(); 
	}else{
	    window.event.cancelBubble = true; 
	}
	if ( e && e.preventDefault ){
        e.preventDefault(); 
   	}else{
        window.event.returnValue = false; 
    }
    return false;
}



function ZeppinVideo_TopB(obj) {
    var VIDEO = $(obj).parent().parent().parent().parent().parent().siblings().find("video").get(0);
    $(VIDEO).parent().find("b").css({ "display": "block" })
    $(VIDEO).parent().siblings().find(".barbar b").addClass("bglight")
}
//视频播放时间
function playTime(){
	var totleTime=Math.round(document.getElementById("zeppinVideo").currentTime);
	var totleMinutes=parseInt(totleTime/60);
	var totleSeconds=Math.round(totleTime%60);
	if(totleMinutes<10){
		totleMinutes="0"+totleMinutes;
	}
	if(totleSeconds<10){
		totleSeconds="0"+totleSeconds;
	}
	$(".currentTime").html(totleMinutes+":"+totleSeconds);
	if(document.getElementById("zeppinVideo").paused){
		
	}else{
		$(".cover").css("display","none");
    	$(".animateBg").css("display","none");
	}
	for(var i=0;i<arr.length;i++){
		
		if(Math.round(document.getElementById("zeppinVideo").currentTime)>=Math.round(arr[i])&&Math.round(document.getElementById("zeppinVideo").currentTime)<=Number(Math.round(arr[i])+20)){
			if($(".videoAnimate"+i).css("display")=="none"){
				for(var j=i+1;j<arr.length;j++){
						var bottom=$(".videoAnimate"+j).css("bottom");
						bottom=Number(bottom.substring(0,bottom.length-2))+70;
						$(".videoAnimate"+j).animate({"bottom":bottom+"px"});
					
				}
				$("div.animation"+i).parent().fadeIn();
				$(".videoAnimate"+i).css("display","block").animate({"right":"20px"});
//				$(".videoAnimate .titleP"+i).css({"-webkit-animation":"title 1s ease-in-out alternate","-moz-animation":"title 1s ease-in-out alternate","-o-animation":"title 1s ease-in-out alternate","animation":"title 1s ease-in-out alternate"});
//				$(".videoAnimate .scale"+i).css({"-webkit-animation":"bian 1.5s ease-out infinite normal","-moz-animation":"bian 1.5s ease-out infinite normal","-o-animation":"bian 1.5s ease-out infinite normal","animation":"bian 1.5s ease-out infinite normal"});
			}
		}else{
			$("div.animation"+i).parent().fadeOut();
			$(".videoAnimate"+i).css("display","none");
		}
	}
}
var urls;
var id;
var pointTime;
var category;
$(function(){
	id = (url('?id') != null) ? url('?id') : '';
	urls=decodeURI((url('?url') != null) ? url('?url') : '');
	pointTime = (url('pointTime') != null) ? url('?pointTime') : '';
	category = (url('category') != null) ? url('?category') : '';
	var point;
	arr = [];

	
	if(id==""){
		history.go(-1);
	}
})

var arr = [];
function aaa(e){
	$(e).attr("href",$(e).attr("name")+"&pointTime="+document.getElementById("zeppinVideo").currentTime);
}

//获取视频信息
function videoInfos(a,e){
	$.get('../front/web/webInterface!execute?uid=i0003&id='+a,function(r){
		if(r.status=="success"){
			$(".videoInfo .videoInfo-container h4").html(r.data.videoTitle);
			$(".videoInfo .videoInfo-container p.intro").html(r.data.videoContext);
			$("#content .content_inner div.infomation p.title").html(r.data.videoTitle);
			$("#content .content_inner div.infomation p.detailss").html(r.data.videoContext);
//			$(".cover").attr("src",".."+r.data.videoThumbanil);
			for(var i=0;i<r.data.videoURLs.length;i++){
				$(".barbar select option:eq("+i+")").attr("value",r.data.videoURLs[i]);			
			}
			if(navigator.userAgent.toLowerCase().indexOf('chrome')>-1) {			    
				$(".barbar input").css("margin-top","25px")
			}
			if(navigator.userAgent.toLowerCase().indexOf('firefox')>-1) {	
				$(".barbar input").css("margin-top","18px")
			}
			var str="";
			var nodestr="";
			var pinfo="";
			var animatestr="";
			var videostr="<video id='zeppinVideo' webkit-playsinline><source id='videoSource' src='.."+r.data.videoURLs[1]+"'>您的版本不支持该视频播放，请更换浏览器或者升级浏览器版本</video>";
//			var videostr="<video id='zeppinVideo' webkit-playsinline><source id='videoSource' src='http://flv2.bn.netease.com/videolib3/1605/20/RsqFn9155/SD/RsqFn9155-mobile.mp4'>您的版本不支持该视频播放，请更换浏览器或者升级浏览器版本</video>";
			$(".ZeppinVideo_Top").append(videostr);
//			总时长
			var totleTime=Math.round(r.data.timepointSecond);
			var totleMinutes=parseInt(totleTime/60);
			var totleSeconds=Math.round(totleTime%60);
			if(totleMinutes<10){
				totleMinutes="0"+totleMinutes;
			}
			if(totleSeconds<10){
				totleSeconds="0"+totleSeconds;
			}
			$(".totleTime").html("/"+totleMinutes+":"+totleSeconds);
			ClickVideo(document.getElementById("zeppinVideo"));
			for(var i=r.data.webVideoPoints.length-1;i>=0;i--){		
				nodestr="";
				pinfo="";
				animatestr="<div class='videoAnimate videoAnimate"+i+"'><a onclick='getCommodity(\""+r.data.webVideoPoints[i].commodity+"\")'><p class='titleP titleP"+i+"'>点击购买"+r.data.webVideoPoints[i].showMessage+"</p><p class='imgP imgP"+i+"'><img src='.."+r.data.webVideoPoints[i].commodityCover+"' alt='产品图片'></p><p class='scale scale"+i+"'></p></div>"+animatestr;
				str+="<div class='swiper-slide'><a name='Show.html?id="+r.data.webVideoPoints[i].commodity+"&parentid="+id+"&category="+e+"&pagetype="+pagetype+"' onclick='aaa(this);' style='display:inline-block;width:100%;height:100%;'><img src='../"+r.data.webVideoPoints[i].showBannerURL+"'></a></div>";
				arr[i]=r.data.webVideoPoints[i].timepointSecond;
				var left=r.data.webVideoPoints[i].timepointSecond/r.data.timepointSecond*100;
				var right=100-left;
				nodestr="<i class='node"+i+" node'></i>";
				$(".ProgressBarNode").prepend(nodestr);
				if(right>98.5){
					$(".node"+i).css("left","0%");
				}else{
					$(".node"+i).css("right",right+"%");
				}
				pinfo="<a href='Show.html?id="+r.data.webVideoPoints[i].commodity+"&parentid="+id+"&category="+e+"&pagetype="+pagetype+"' onclick='aaa(this);' style='display:none;'><div class='animate animation"+i+"'><img src='.."+r.data.webVideoPoints[i].commodityCover+"'><span>"+r.data.webVideoPoints[i].showMessage+"</span><div class='clear'></div></div></a>";
				$(".playAnimate").append(pinfo);
				left=left-12;
				if(left<0){
					left=0;
				}
				$(".animation"+i).css("left",left+"%");
			}
			$(".swiper-wrapper").html(str);
			$(".ZeppinVideo_Top").prepend(animatestr);
			
		}else{
			alert(r.message);
		}
	}).done(function(){
		if(pointTime != ""&&pointTime!=null){
			$("#zeppinVideo").currentTime = pointTime;
			$(".cover").css("display","none");
			$(".animateBg").css("display","none");
			document.getElementById("zeppinVideo").play();
			
		}
		$("#show").click(function(){
    		initBox();
    		$("#hide").css("display","block");
    		$("#show").css("display","none");
    	})
    	$(window).resize(function(){
    	var h=$(window).height();
    	var w=$(window).width();
    	
//    	$("#box").width(w);
//    	$("#box").height(h);
//    	$(".ZeppinVideo").width(w);
//    	$(".ZeppinVideo").height(h);
    	})
	})
}
var clickBannerTime=0;
function changeType(t){
	var thistime = document.getElementById("zeppinVideo").currentTime;
	var playType = document.getElementById("zeppinVideo").paused;
	var url = $(t).val();
	document.getElementById("zeppinVideo").remove();
	var videostr="<video id='zeppinVideo' webkit-playsinline><source id='videoSource' src='.."+$(".barbar select").val()+"'>您的版本不支持该视频播放，请更换浏览器或者升级浏览器版本</video>";
	$(".ZeppinVideo_Top").append(videostr);
	ClickVideo(document.getElementById("zeppinVideo"));
	if(!playType){
		document.getElementById("zeppinVideo").play();
	}else{
		document.getElementById("zeppinVideo").play();
	}
	document.getElementById("zeppinVideo").currentTime = thistime;
}

function initBox(){
	$("video").height(window.screen.height-70+"px");
	$("#quanping").height(window.screen.height);
	$("#quanping .quanping-container").css("max-width","100%")
	$(".layerDD").height(window.screen.height);
}
function launchFullscreen(element) {
	if(element.requestFullscreen) {
		element.requestFullscreen();
	} else if(element.mozRequestFullScreen) {
		element.mozRequestFullScreen();
	} else if(element.webkitRequestFullscreen) {
		element.webkitRequestFullscreen();
	} else if(element.msRequestFullscreen) {
		element.msRequestFullscreen();
	}
}
document.getElementById("show").onclick = function() {
//	launchFullscreen(document.documentElement); // 整个网页
	launchFullscreen(document.getElementById("quanping"));
}

function exitFullscreen() {
	if(document.exitFullscreen) {
		document.exitFullscreen();
	} else if(document.mozCancelFullScreen) {
		document.mozCancelFullScreen();
	} else if(document.webkitExitFullscreen) {
		document.webkitExitFullscreen();
	}
}
document.getElementById("hide").onclick=function(){
	exitFullscreen();
	$("#show").css("display","block");
	$("#hide").css("display","none");
	$(".ZeppinVideo .ZeppinVideo_Top video").removeAttr("style");
	$("#quanping").removeAttr("style");
	$(".layerDD").css({"height":height});
	$("#quanping .quanping-container").removeAttr("style");
}

var Show;
//360度展示
function showss(id){
	document.getElementById("zeppinVideo").pause();
	Show=layer.open({
	  type: 2,
	  title: false,
	  shadeClose: false,
	  shade: 0.8,
	  scrollbar: false,
	  area: ['60%', '90%'],
	  content: 'show.html?id='+id 
	}); 
	$(".layui-layer-setwin .layui-layer-close2").click(function(){
		document.getElementById("zeppinVideo").play();
	});
 	$(".layui-layer-rim").css({"border":"none","border-radius":"0px"});

}
function getCommodity(id){
	$(".barbar b").addClass("bglight");
	document.getElementById("zeppinVideo").pause();
	$(".layerDD").css("display","block");
	$(".layerDD").css({"width":$(window).width(),"height":document.body.scrollHeight,"padding-top":0.05*$(window).height()+$(window).scrollTop()});
	$("iframe").attr("src","show.html?id="+id).css("height","662px");
}


function closeDD(){
	$(".layerDD").css("display","none");
//	document.getElementById("zeppinVideo").play();
}
