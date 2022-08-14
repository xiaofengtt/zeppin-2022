var Video=document.getElementById("zeppinVideo");
var pagetype= (url('?pagetype') != null) ? url('?pagetype') : '';
$(function(){
		if(navigator.userAgent.indexOf('Android') > -1) {
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
					$(".barbar .currentTime").css("font-size","0.2rem");
					$(".barbar .totleTime").css("font-size","0.2rem");
					$(".videoAnimate .titleP").css("font-size","0.18rem");
					$(".playAnimate").css("display","none");

				}else if(navigator.userAgent.toLowerCase().indexOf('chrome')>-1) {
					$(".swiper-container").css("display","none");
					$(".playAnimate").css("display","none");

				}else if(navigator.userAgent.toLowerCase().indexOf('firefox')>-1&&navigator.userAgent.toLowerCase().indexOf('360')>-1) {
					$(".swiper-container").css("display","none");

				}else if(navigator.userAgent.toLowerCase().indexOf("Opera")>-1) {
					$(".swiper-container").css("display","none");
					$(".barbar .currentTime").css("font-size","0.2rem");
					$(".barbar .totleTime").css("font-size","0.2rem");
					$(".videoAnimate .titleP").css("font-size","0.26rem");
					$(".playAnimate").css("display","none");
				}else if(navigator.userAgent.toLowerCase().indexOf('qqbrowser')>-1&&navigator.userAgent.toLowerCase().indexOf('chrome')>-1&&navigator.userAgent.toLowerCase().indexOf('se')>-1) {
				   $(".playAnimate").css("display","none");
				   $(".swiper-container").css("display","block");

				}else{ 
					$(".playAnimate").css("display","none");
				}

		}else if(navigator.userAgent.indexOf('iPhone') > -1) {
			$(".barbar .currentTime").css("font-size","0.28rem");
			$(".barbar .totleTime").css("font-size","0.28rem");
			$(".videoAnimate .titleP").css("font-size","0.28rem");
			if(navigator.userAgent.toLowerCase().match(/MicroMessenger/i)=="micromessenger") {
				$(".swiper-container").css("display","none");
				$(".playAnimate").css("display","none");

			}else if(navigator.userAgent.toLowerCase().indexOf('qq')>-1) {
				$(".swiper-container").css("display","none");
				$(".playAnimate").css("display","none");

			}else if(navigator.userAgent.toLowerCase().indexOf('se')>-1) {
				$(".swiper-container").css("display","none");

			}else{
				$(".playAnimate").css("display","none");
			}
			

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

    $(video).parent().siblings().find(".barbar b").click(function () {
    	$(".cover").css("display","none");
    	$(".animateBg").css("display","none");
    	clickZeppinVideo();
    })
    $(video).prev().click(function () {
    	clickZeppinVideo();

    })
    $(video).on('ended', function () {
        $(video).parent().siblings().find(".barbar b").addClass("bglight");
    });

    function clickZeppinVideo() {
        if (video.paused || video.ended) {
            video.play();
            $(video).parent().find("b").css({ "display": "none" });
            $(video).parent().siblings().find(".barbar b").removeClass("bglight");
        }
        else {
            video.pause();
            $(video).parent().find("b").css({ "display": "block" });
            $(video).parent().siblings().find(".barbar b").addClass("bglight");
        }
    }

    var timeDrag = false;
    $(video).parent().siblings().find(".ProgressBarNode").on('touchstart', function (e) {
    	var event = arguments.callee.caller.arguments[0] || window.event; 
        timeDrag = true;
        updatebar(Number(event.touches[0].pageX));
    });
    $(document).on('touchend', function (e) {
    	var event = arguments.callee.caller.arguments[0] || window.event; 
        if (timeDrag) {
            timeDrag = false;
            updatebar(Number(event.touches[0].pageX));
            for(var i=0;i<arr.length;i++){
            	$(".videoAnimate"+i).css("bottom","0.8rem");
            }
        }
    });
    $(document).on('touchmove', function (e) {   	
    	var event = arguments.callee.caller.arguments[0] || window.event; 
        if (timeDrag) {
            updatebar(Number(event.touches[0].pageX));  
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
        $(video).parent().siblings().find(".timeBar").css('width', percentage + '%');
        video.currentTime = maxduration * percentage / 100;
    };
	
    function updateProgressBar() {
        var ProgressBar = $(video).parent().siblings().find(".timeBar").get(0);
        var value = 0;
        if (video.currentTime > 0) {
            value = Math.floor((100 / video.duration) * video.currentTime);
        }
        
        if(ProgressBar != undefined){
        	ProgressBar.style.width = value + "%";
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

}
var Shows;
function showsss(){
	Shows=layer.open({
		type: 1,
	    content: '<div>hello world!</div>',
	    style: 'position:fixed; left:25%; top:25%; width:50%; height:50%; border:none;'
	})
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
						bottom=Number(bottom.substring(0,bottom.length-2))/100+1.4;
						$(".videoAnimate"+j).animate({"bottom":bottom+"rem"});
					
				}
				$("div.animation"+i).parent().fadeIn();
				$(".videoAnimate"+i).css("display","block");
				$(".videoAnimate .titleP"+i).css({"-webkit-animation":"title 1s ease-in-out alternate","-moz-animation":"title 1s ease-in-out alternate","-o-animation":"title 1s ease-in-out alternate","animation":"title 1s ease-in-out alternate"});
				$(".videoAnimate .scale"+i).css({"-webkit-animation":"bian 1.5s ease-out infinite normal","-moz-animation":"bian 1.5s ease-out infinite normal","-o-animation":"bian 1.5s ease-out infinite normal","animation":"bian 1.5s ease-out infinite normal"});
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
var publish;
$(function(){
	getList();
	id = (url('?id') != null) ? url('?id') : '';
	urls=decodeURI((url('?url') != null) ? url('?url') : '');
	pointTime = (url('pointTime') != null) ? url('?pointTime') : '';
	category = (url('category') != null) ? url('?category') : '';
	publish = (url('publish') != null) ? url('?publish') : '';
	var point;
	arr = [];
	videoInfo(id,category);
	related(category,publish);
	setInterval("playTime()",500);
	if(id==""){
		history.go(-1);
	}
})

//获取列表
function getList(){
	$.get('../front/web/webInterface!execute?uid=i0001&component=76b40d20-0957-43e0-9e3c-49dbe16a3b62',function(r){
		if(r.status=="success"){
			var str ="";
			for ( var i = 0, l = r.data.length; i < l; i++ ) {
				if(i>3){
					break;
				}else{
					str+="<li><a href='List.html?id="+r.data[i].id+"&title="+encodeURI(r.data[i].name)+"&pagetype="+(parseInt(i)+parseInt(1))+"'><i class='icon2 icon'><img src='images/icon"+Number(i+2)+".png' alt='icon'></i><label>"+r.data[i].name+"</label><i class='navLiBg'><img src='images/navLiBg.png' alt='箭头'></i></a></li>";
				}
			}	
			$("ul.Index").append(str);
		}else{
			
		}
	});
}
var arr = [];
function aaa(e){
	$(e).attr("href",$(e).attr("name")+"&pointTime="+document.getElementById("zeppinVideo").currentTime);
}

//获取视频信息
function videoInfo(a,e){
	$.get('../front/web/webInterface!execute?uid=i0003&id='+a,function(r){
		if(r.status=="success"){
			$("#content .content_inner div.infomation p.title").html("简介");
			$("#content .content_inner div.infomation p.detailss").html(r.data.videoContext);
			$(".cover").attr("src",".."+r.data.videoThumbanil);
			var str="";
			var nodestr="";
			var pinfo="";
			var animatestr="";
			var videostr="<video id='zeppinVideo' webkit-playsinline><source id='videoSource' src='.."+r.data.videoURLs[1]+"'>您的版本不支持该视频播放，请更换浏览器或者升级浏览器版本</video>";
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
			$(".totleTime").html(totleMinutes+":"+totleSeconds);
			ClickVideo(document.getElementById("zeppinVideo"));
			for(var i=r.data.webVideoPoints.length-1;i>=0;i--){		
				nodestr="";
				pinfo="";
				arr[i]=r.data.webVideoPoints[i].timepointSecond;
				animatestr="<div class='videoAnimate videoAnimate"+i+"'><a name='Show.html?id="+r.data.webVideoPoints[i].commodity+"&publish="+publish+"&parentid="+id+"&category="+e+"&pagetype="+pagetype+"' onclick='aaa(this);'><p class='titleP titleP"+i+"'>点击购买"+r.data.webVideoPoints[i].showMessage+"</p><p class='imgP imgP"+i+"'><img src='.."+r.data.webVideoPoints[i].commodityCover+"' alt='产品图片'></p><p class='scale scale"+i+"'></p></div>"+animatestr;
				str+="<div class='swiper-slide'><a name='Show.html?id="+r.data.webVideoPoints[i].commodity+"&publish="+publish+"&parentid="+id+"&category="+e+"&pagetype="+pagetype+"' onclick='aaa(this);' style='display:inline-block;width:100%;height:100%;'><img src='../"+r.data.webVideoPoints[i].showBannerURL+"'></a></div>";
				
				var left=r.data.webVideoPoints[i].timepointSecond/r.data.timepointSecond*100;
				var right=100-left;
				nodestr="<i class='node"+i+" node'></i>";
				$(".ProgressBarNode").prepend(nodestr);
				if(right>98.5){
					$(".node"+i).css("left","0%");
				}else{
					$(".node"+i).css("right",right+"%");
				}
				pinfo="<a href='Show.html?id="+r.data.webVideoPoints[i].commodity+"&publish="+publish+"&parentid="+id+"&category="+e+"&pagetype="+pagetype+"' onclick='aaa(this);' style='display:none;'><div class='animate animation"+i+"'><img src='.."+r.data.webVideoPoints[i].commodityCover+"'><span>"+r.data.webVideoPoints[i].showMessage+"</span><div class='clear'></div></div></a>";
				$(".playAnimate").append(pinfo);
				left=left-12;
				if(left<0){
					left=0;
				}
				$(".animation"+i).css("left",left+"%");
			}
			$(".swiper-wrapper").html(str);
			$(".ZeppinVideo_Top").prepend(animatestr);
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
	}).done(function(){
		if(pointTime != ""&&pointTime!=null){
			document.getElementById("zeppinVideo").currentTime = pointTime;
			$(".cover").css("display","none");
			$(".animateBg").css("display","none");
//			document.getElementById("zeppinVideo").play();
			
		}else{
			
		}
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

//相关评测
function related(e,p){
	var src="";
	var clear="<div class='clear'></div>";
	$.get('../front/web/webInterface!execute?uid=i0002&except='+p+'&category='+e+'&pagesize=4&pagenum=&sort=',function(r){
		if(r.status=="success"){
			var str ="";
			for ( var i = 0, l = r.data.length; i < l; i++ ) {
				var title=r.data[i].title;
				var titles = title;
				var urlimg=r.data[i].coverURL;
				if(pagetype==1){
					if(i%2==0){
						str+="<a href='Video.html?publish=" + r.data[i].publishId +"&id="+r.data[i].id+"&category="+e+"&pagetype=1'><div class='video_review video_reviewleft'><div class='top top1'><div class='cover-img cover-img1' style='background-image:url(../"+urlimg+");'></div></div><p>"+titles+"</p></div></a>";
					}else{
						str+="<a href='Video.html?publish=" + r.data[i].publishId +"&id="+r.data[i].id+"&category="+e+"&pagetype=1'><div class='video_review video_reviewright'><div class='top top1'><div class='cover-img cover-img1' style='background-image:url(../"+urlimg+");'></div></div><p>"+titles+"</p></div></a>";
					}
				}else if(pagetype==2||pagetype==4){
					if(i%2==0){
						str+="<a href='Video.html?publish=" + r.data[i].publishId +"&id="+r.data[i].id+"&category="+e+"&pagetype=2'><div class='video_review video_reviewleft'><div class='top'><div class='cover-img' style='background-image:url(../"+urlimg+");'></div></div><p>"+titles+"</p></div></a>";
					}else{
						str+="<a href='Video.html?publish=" + r.data[i].publishId +"&id="+r.data[i].id+"&category="+e+"&pagetype=2'><div class='video_review video_reviewright'><div class='top'><div class='cover-img' style='background-image:url(../"+urlimg+");'></div></div><p>"+titles+"</p></div></a>";
					}
				}else if(pagetype==3){
					str+="<a href='Video.html?publish=" + r.data[i].publishId +"&id="+r.data[i].id+"&category="+e+"&pagetype=3'><div class='video_review video_reviewleft'><div class='top top3'><div class='cover-img cover-img3' style='background-image:url(../"+urlimg+");'></div></div><p>"+titles+"</p></div></a>";
				}
			}	
			$("#content .content_inner div.correlation").append(str+clear);
		}else{
			alert(r.message);
		}
	})
}

/* 在页面加载的时候调用 */
$(function(){
	if(window.orient==0){  
        document.body.setAttribute("orient","portrait");  
    }else{  
        document.body.setAttribute("orient","landscape");  
    }  
});

window.onorientationchange=function(){  
    var body=document.body;  
    var viewport=document.getElementById("viewport");  
    if(body.getAttribute("orient")=="landscape"){  
        body.setAttribute("orient","portrait"); 
        $(".ZeppinVideo .ZeppinVideo_Top video").css({"height":(document.documentElement.clientHeight)/100+"rem","width":"auto"});
    }else{  
        body.setAttribute("orient","landscape");  
        $(".ZeppinVideo .ZeppinVideo_Top video").css({"height":"4.21rem","width":"100%"});
    }  
};  