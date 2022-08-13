var Video=document.getElementById("zeppinVideo");
var Show="";
$(function(){
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

var Show;
//360度展示
function shows(id){
	document.getElementById("zeppinVideo").pause();
	$(".barbar b").addClass("bglight");
	$.get('../front/web/webInterface!execute?uid=i0004&id='+id,function(r){
		if(r.status=="success"){
			Show=layer.open({
				  type: 1,
				  title: false,
				  shadeClose: true,
				  offset: ['20px',''],
				  shade: 0.8,
				  closeBtn: 1,
				  scrollbar: false,
				  area: ['40%', ''],
				  content: '<div class="wrap"><p class="leftAndright"><img src="../web/images/leftRight.png" alt="左右滑动查看机身"></p><div class="container pb30" style="width:90%"><div class="row"><div class=""><div class=""><div class=""><div id="circlr"><div id="loader"></div></div></div></div></div></div></div><div class="shoppers"><div class="shoppersLeft"><h1></h1></div><p class="money">&yen;<span class="number"></span><img src="../web/images/noMoney.png" alt="享受0元购机" /></p><a class="goBuy" href="javascript:" target="_blank">去购买</a><div class="clear"></div></div></div>' 
				}); 
				$(".layui-layer-setwin .layui-layer-close2").css("display", "block");
			   	$(".layui-layer-rim").css({"border":"none","border-radius":"0px"});
			$(".shoppers a.goBuy").attr("href",r.data.commodityURL);
			$(".shoppers .shoppersLeft h1").html(r.data.name);
			$(".shoppers p.money span").html(r.data.price);
			var str ="";
			for ( var i = 0, l = r.data.webCommodityDisplays.length; i < l; i++ ) {
				str="<img data-src=.."+r.data.webCommodityDisplays[i].displayImageURL+">"+str;
			}	
			$("#circlr").prepend(str);
			var crl = circlr('circlr', {
				  scroll : true,
				  loader : 'loader'
				});
		}else{
			alert(r.message);
		}
	});

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
var pointTime
$(function(){
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
function layerVideo(a){
	$.get('../front/web/webInterface!execute?uid=i0003&id='+$(a).attr("name"),function(r){
		if(r.status=="success"){
			Show=layer.open({
				type: 1,
				area: ['70%', ''],
				title: false,
				closeBtn: 1,
				offset: ['80px',''],
				scrollbar: false,
				shadeClose: false,
				content: "<div class='ZeppinVideo'><div class='ZeppinVideo_Top'><video id='zeppinVideo' controls='controls' style='width:100%;'><source id='videoSource' src='.."+r.data.videoURLs[1]+"'>您的版本不支持该视频播放，请更换浏览器或者升级浏览器版本</video></div><div class='ZeppinVideo_Bottom'><div class='ProgressBar'><div class='ProgressBarNode'></div><div class='timeBar'></div></div><div class='barbar'><b class='bglight'></b><span class='currentTime'>00:00</span><span class='totleTime'>00:00</span><select class='type'  onchange='changeType(this)'><option>流畅</option><option selected='selected'>高清</option><option>超清</option></select><input min='0' max='1' step='0.1' type='range'><button></button></div></div></div>"
				}); 
			   	$(".layui-layer-rim").css({"border":"none","border-radius":"0px"});
			for(var i=0;i<r.data.videoURLs.length;i++){
				$(".barbar select option:eq("+i+")").attr("value",r.data.videoURLs[i]);			
			}
			if(navigator.userAgent.toLowerCase().indexOf('chrome')>-1) {			    
				$(".barbar input").css("margin-top","25px")
			}
			var str="";
			var nodestr="";
			var pinfo="";
			var animatestr="";
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
			setInterval("playTime()",500);
			for(var i=r.data.webVideoPoints.length-1;i>=0;i--){		
				nodestr="";
				pinfo="";
				animatestr="<div class='videoAnimate videoAnimate"+i+"'><a onclick='shows(\""+r.data.webVideoPoints[i].commodity+"\")'><p class='titleP titleP"+i+"'>点击购买"+r.data.webVideoPoints[i].showMessage+"</p><p class='imgP imgP"+i+"'><img src='.."+r.data.webVideoPoints[i].commodityCover+"' alt='产品图片'></p><p class='scale scale"+i+"'></p></div>"+animatestr;
				arr[i]=r.data.webVideoPoints[i].timepointSecond;
				var left=r.data.webVideoPoints[i].timepointSecond/r.data.timepointSecond*100;
				nodestr="<i class='node"+i+" node'></i>";
				$(".ProgressBarNode").prepend(nodestr);
				$(".node"+i).css("left",left+"%");
				pinfo="<a href='Show.html?id="+r.data.webVideoPoints[i].commodity+"&parentid="+id+"' onclick='aaa(this);' style='display:none;'><div class='animate animation"+i+"'><img src='.."+r.data.webVideoPoints[i].commodityCover+"'><span>"+r.data.webVideoPoints[i].showMessage+"</span><div class='clear'></div></div></a>";
				$(".playAnimate").append(pinfo);
				left=left-12;
				if(left<0){
					left=0;
				}
				$(".animation"+i).css("left",left+"%");
			}
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


var pagenum = '1';

function changeStatus(t) {
	var obj = $(t),cUrl = obj.attr('data-url');
	$.getJSON(cUrl, function(ret) {
		if (ret.status == 'success') {
			getList();
		} else {
			alert('失败,' + ret.message);
		}
	})
	return false;
}

$('#searchForm').submit(function(){
	var key = $('input[name="stype"]:checked').val(),obj = {};
	obj[key] = $('#searchheader').val();
	var str = '&'+key+'='+obj[key];
	getList(str);
	return false;
});


//加载列表
function getList(params) {
	if (params == undefined){
		params = '';
	}
	var page = (typeof pagenum == 'undefined') ? 1 : pagenum;
	var d = dialog({
	    title: '系统提示',
	    width : 220,
		height:60,
	    content: '<p style="line-height:50px;">加载中...</p>'
	});
	d.showModal();
	$.getJSON('../front/admin/videoinfo!execute?uid=g0008',function(r) {
		if(r.status == 'success') {
			$('#status_checked').html(r.data.checked);
			$('#status_unchecked').html(r.data.unchecked);
			$('#status_deleted').html(r.data.deleted);
			$('#status_uploaded').html(r.data.uploaded);
			$('#status_failed').html(r.data.failed);
			$('#status_transcoding').html(r.data.transcoding);
		}
	});
	var filterStatus= $("#statusChecked").val();
	$.getJSON('../front/admin/videoinfo!execute?uid=g0001&pagesize=10&sort=createtime desc'+params+'&pagenum='+page+'&status='+filterStatus, function(r) {
		r.totalPageCount && $('.quepager').html('<span style="font-weight:normal">'+ r.pageNum +'</span>/'+ r.totalPageCount);

		if(r.status == 'success' && r.data.length > 0) {
		    var template = $.templates('#queboxTpl');
		    var html = template.render(r.data);
		    $('#queboxCnt').html(html);
		    
		    $(".btn-edit").colorbox({
				iframe : true,
				width : "860px",
				height : "500px",
				opacity : '0.5',
				overlayClose : false,
				escKey : true
			});
		    
		} else if (r.status == 'success' && r.data.length == 0) {
			$('#pagnationPaper').html('');
			$('#queboxCnt').html('<div class="no_data">无搜索结果</div>');
		}
		$(".main").animate({scrollTop: 0}, 1e3);
		
		d.close().remove();
		
	}).done(function(r){//分页
		$('#pagnationPaper').pagination({
			currentPage : r.pageNum,
	        items: r.totalResultCount,
			edges: 3,
	        itemsOnPage : r.pageSize,
	        
			onPageClick : function(pageNumber,event) {
				pagenum = pageNumber;
				getList();
			}
	    });
		
	});
}



$(function(){
	function init() {
		getList();
	};
	init();
	$(".statusbar a").click(function(){
		$(this).addClass("light").siblings().removeClass("light");
		$("#statusChecked").val($(this).find("span").attr("name"));
		pagenum = '1';
		setTimeout("getList()",100);
	});
})

