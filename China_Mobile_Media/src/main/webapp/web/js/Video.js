var Video=document.getElementById("shuangkashuangdai");
$(function(){

	
		if(navigator.userAgent.indexOf('Android') > -1) {
			   //alert("Android");
			if(navigator.userAgent.indexOf('UCBrowser') > -1) {
				   // alert("uc");
				$(".swiper-container").css("display","none");

				}
				if(navigator.userAgent.toLowerCase().indexOf('360')>-1) {
				    //alert("360");
					$(".swiper-container").css("display","none");

				}
				if(navigator.userAgent.toLowerCase().indexOf('baidu')>-1) {
				   // alert("baidu");
					$(".swiper-container").css("display","none");

				}
				if(navigator.userAgent.toLowerCase().indexOf('qqbrowser')>-1) {
				   // alert("qq");
					$(".swiper-container").css("display","none");

				}
				if(navigator.userAgent.toLowerCase().indexOf('se')>-1) {
				   // alert("sougo");
					$(".swiper-container").css("display","none");

				}
				if(navigator.userAgent.toLowerCase().indexOf('firefox')>-1) {
				    //alert("huohu");
					$(".swiper-container").css("display","none");

				}
				if(navigator.userAgent.toLowerCase().indexOf('chrome')>-1) {
				    //alert("chrome");
					$(".swiper-container").css("display","none");

				}
				if(navigator.userAgent.toLowerCase().indexOf('firefox')>-1&&navigator.userAgent.toLowerCase().indexOf('360')>-1) {
				    //alert("chromehuohu");
					$(".swiper-container").css("display","none");

				}
				if(navigator.userAgent.toLowerCase().indexOf('opera')>-1) {
				    //alert("opera");
					$(".swiper-container").css("display","none");

				}

		}else if(navigator.userAgent.indexOf('iPhone') > -1) {
			   //alert("iPhone");
			$(".playAnimate").css("display","none");

		}else{
			$(".playAnimate").css("display","none");
		}
	
		
	$(".ZeppinVideo .ZeppinVideo_Bottom .Progress .progressQQ i.node").hover(function(){
		$(this).find("p").stop().fadeIn(500);
	},function(){
		$(this).find("p").stop().fadeOut(500);
	});
	 if(isFirefox=navigator.userAgent.indexOf("Firefox")>0){  
        $(".kongzhianniu input").css("margin-top","5px");
   }  
   
//    	document.onkeydown=function(event){
//		var e = event || window.event || arguments.callee.caller.arguments[0];
//	    var volume = $(Video).parent().siblings().find(".kongzhianniu input").get(0);
//	    Video.volume = volume.value;
//			if(e && e.keyCode==38){//上,左
//				if(volume.value>=0&&volume.value<1){
//					volume.value=Number(volume.value)+0.1;
//				}else{
//					volume.value=volume.value;
//				}
//			}else if(e && e.keyCode==40){
//				if(volume.value>0){
//					volume.value-=0.1;
//				}else{
//					volume.value=volume.value;
//				}
//			}
//			if(e && e.keyCode==37){//下,右
//				Video.currentTime-=1;
//			}else if(e && e.keyCode==39){
//				Video.currentTime+=1;
//			}
//	}; 


})

function ClickVideo(video){

    video.controls = false;
    video.addEventListener("timeupdate", updateProgress, false);


    $(video).click(function () {
        CLICK()
    })

    $(video).parent().siblings().find(".kongzhianniu b").click(function () {
    	$(".cover").css("display","none");
    	$(".animateBg").css("display","none");
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
//  $(video).parent().siblings().find(".progressQQ").on('mousedown', function (e) {
//      timeDrag = true;
//      updatebar(e.pageX);
//  });
//  $(document).on('mouseup', function (e) {
//      if (timeDrag) {
//          timeDrag = false;
//          updatebar(e.pageX);
//      }
//  });
//  $(document).on('mousemove', function (e) {
//      if (timeDrag) {
//          updatebar(e.pageX);             
//      }
//      
//  });
$(video).parent().siblings().find(".progressQQ").on('touchstart', function (e) {
        timeDrag = true;
        updatebar(Number(event.touches[0].pageX));
    });
    $(document).on('touchend', function (e) {
        if (timeDrag) {
            timeDrag = false;
            updatebar(Number(event.touches[0].pageX));
        }
    });
    $(document).on('touchmove', function (e) {   	
        if (timeDrag) {
            updatebar(Number(event.touches[0].pageX));         
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
        
        if(progress != undefined){
        	progress.style.width = value + "%";
        }
       
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

var Show;
//360度展示
function shows(){
	Show=layer.open({
	  type: 2,
	  title: false,
	  shadeClose: true,
	  shade: 0.8,
	  scrollbar: false,
	  area: ['100%', '90%'],
	  content: 'Show.html' 
	}); 
	$(".layui-layer-setwin .layui-layer-close2").css("display", "none");
   	$(".layui-layer-rim").css({"border":"none","border-radius":"0px"});
//	$(".bg").css("display","block");
//	$(".showlayer").css("display","block");

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
    //VIDEO.pause()
    $(VIDEO).parent().find("b").css({ "display": "block" })
    $(VIDEO).parent().siblings().find(".kongzhianniu b").addClass("bgligh")
}
//视频播放时间
function playTime(){
	if(document.getElementById("shuangkashuangdai").paused){
		
	}else{
		$(".cover").css("display","none");
    	$(".animateBg").css("display","none");
	}
	for(var i=0;i<arr.length;i++){
		if(Math.round(document.getElementById("shuangkashuangdai").currentTime)>=Math.round(arr[i])&&Math.round(document.getElementById("shuangkashuangdai").currentTime)<=Number(Math.round(arr[i])+5)){
			$("div.animation"+i).parent().fadeIn();
		}else{
			$("div.animation"+i).parent().fadeOut();
		}
	}
	
//	else if(Math.round(Video.currentTime)==15){
//		$("p.animate").fadeOut(1500);
//	}
}
var urls;
var id;
var pointTime
$(function(){
	getList();
	id = (url('?id') != null) ? url('?id') : '';
	urls=decodeURI((url('?url') != null) ? url('?url') : '');
	pointTime = (url('pointTime') != null) ? url('?pointTime') : '';
	var point;
	arr = [];
	videoInfo(id);
	related(id);
//	alert(point);
	setInterval("playTime()",500);
//	var videostr="<video id='shuangkashuangdai'><source id='videoSource' src='.."+urls+"'>您的版本不支持该视频播放，请更换浏览器或者升级浏览器版本</video>";
//	$(".ZeppinVideo_Top").append(videostr);
//	ClickVideo(document.getElementById("shuangkashuangdai"));
	
})

//获取列表
function getList(){
	$.get('../rest/webinterface/categoryList',function(r){
		if(r.status=="success"){
			var str ="";
			for ( var i = 0, l = r.data.length; i < l; i++ ) {
				str+="<li><a href='List.html?id="+r.data[i].id+"&title="+encodeURI(r.data[i].name)+"'><i class='icon2 icon'><img src='images/icon2.png' alt='icon'></i><label>"+r.data[i].name+"</label><i class='navLiBg'><img src='images/navLiBg.png' alt='箭头'></i></a></li>";
			}	
			$("ul.Index").append(str);
		}else{
			//alert(r.message);
		}
	});
}
var arr = [];
function aaa(e){
	$(e).attr("href",$(e).attr("name")+"&pointTime="+document.getElementById("shuangkashuangdai").currentTime);
}

//获取视频信息
function videoInfo(a){
	$.get('../rest/webinterface/videoInfo?id='+a,function(r){
		//console.log(r)
		if(r.status=="success"){
			$("#content .content_inner div.infomation p.title").html(r.data.videoTitle);
			$("#content .content_inner div.infomation p.detailss").html(r.data.videoContext);
			$(".cover").attr("src",".."+r.data.videoThumbanil);
			var str="";
			var nodestr="";
			var pinfo="";
			for(var i=0;i<r.data.videoURLs.length;i++){
				$(".kongzhianniu select option:eq("+i+")").attr("value",r.data.videoURLs[i]);			
			}
			var videostr="<video id='shuangkashuangdai'><source id='videoSource' src='.."+$(".kongzhianniu select").val()+"'>您的版本不支持该视频播放，请更换浏览器或者升级浏览器版本</video>";
			$(".ZeppinVideo_Top").append(videostr);
			ClickVideo(document.getElementById("shuangkashuangdai"));
			if(pointTime != ""&&pointTime!=null){
				
				document.getElementById("shuangkashuangdai").currentTime = pointTime;
				$(".cover").css("display","none");
				$(".animateBg").css("display","none");
				document.getElementById("shuangkashuangdai").play();
				
			}
			for(var i=0;i<r.data.webVideoPoints.length;i++){	
				nodestr="";
				pinfo="";
				str+="<div class='swiper-slide'><a name='Show.html?id="+r.data.webVideoPoints[i].commodity+"&parentid="+id+"' onclick='aaa(this);'><img src='../"+r.data.webVideoPoints[i].showBannerURL+"'></a></div>";
				arr[i]=r.data.webVideoPoints[i].timepointSecond;
				var left=r.data.webVideoPoints[i].timepointSecond/r.data.timepointSecond*100;
				nodestr="<i class='node"+i+" node'></i>";
				$(".progressQQ").prepend(nodestr);
				$(".node"+i).css("left",left+"%");
				var title=r.data.webVideoPoints[i].showMessage;
				var titles = title;
				if(title.length>10){
					titles=title.substring(0,10)+"...";
				}
				pinfo="<a href='Show.html?id="+r.data.webVideoPoints[i].commodity+"&parentid="+id+"&pointTime="+arr[i]+"' style='display:none;'><div class='animate animation"+i+"'><img src='.."+r.data.webVideoPoints[i].commodityCover+"'><span>"+titles+"</span><div class='clear'></div></div></a>";
				$(".playAnimate").append(pinfo);
				left=left-10;
				if(left<0){
					left=0;
				}
				$(".animation"+i).css("left",left+"%");
			}
			$(".swiper-wrapper").html(str);
			var swiper = new Swiper('.swipe1', {
				mode:'horizontal',
				pagination: '#figure_pagination_1',
				paginationClickable: true,
				autoplay: 2500,
				autoplayDisableOnInteraction : false,
				loop:true
			});
		}else{
			alert(r.message);
		}
	})
}
var clickBannerTime=0;
function changeType(t){
	var thistime = document.getElementById("shuangkashuangdai").currentTime;
	var playType = document.getElementById("shuangkashuangdai").paused;
	var url = $(t).val();
	document.getElementById("shuangkashuangdai").remove();
	var videostr="<video id='shuangkashuangdai'><source id='videoSource' src='.."+$(".kongzhianniu select").val()+"'>您的版本不支持该视频播放，请更换浏览器或者升级浏览器版本</video>";
	$(".ZeppinVideo_Top").append(videostr);
	
	ClickVideo(document.getElementById("shuangkashuangdai"));
	document.getElementById("shuangkashuangdai").currentTime = thistime;
	if(!playType){
		document.getElementById("shuangkashuangdai").play();
	}
	document.getElementById("shuangkashuangdai").play();
}

//相关评测
function related(e){
	var src="";
	var clear="<div class='clear'></div>";
	$.get('../rest/webinterface/publishList?video='+e+'&pagesize=4&pagenum=&sort=',function(r){
		if(r.status=="success"){
			var str ="";
			for ( var i = 0, l = r.data.length; i < l; i++ ) {
				var title=r.data[i].title;
				var titles = title;
				if(title.length>16){
					titles=title.substring(0,16)+"...";
				}
				if(i%2==0){
					str+="<a href='Video.html?id="+r.data[i].id+"'><div class='video_review video_reviewleft'><img src='../"+r.data[i].coverURL+"' alt='评测'><p>"+titles+"</p></div></a>";
				}else{
					str+="<a href='Video.html?id="+r.data[i].id+"'><div class='video_review video_reviewright'><img src='../"+r.data[i].coverURL+"' alt='评测'><p>"+titles+"</p></div></a>";
				}
			}	
			$("#content .content_inner div.correlation").append(str+clear);
		}else{
			alert(r.message);
		}
	})
}

