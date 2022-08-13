var index= (url('?pagetype') != null) ? url('?pagetype') : '';
var id = (url('?id') != null) ? url('?id') : '';
var category = (url('category') != null) ? url('?category') : '';
$(function(){		
	getList();
	
	related(category);
	$("#a1").bind("contextmenu",function(e){
		e.preventDefault();
		return false;
	})
	
})
//获取列表
function getList(){
	$.get('../front/web/webInterface!execute?uid=i0001',function(r){
		if(r.status=="success"){
			var str ="";
			var secondary="";
			for ( var i = 0, l = r.data.length; i < l; i++ ) {
				secondary="";
				if(i>=4){
					break;
				}else{
					for(var j=0;j<r.data[i].child.length;j++){
						secondary+="<li><a href='list.html?id="+r.data[i].child[j].id+"&pagetype="+(parseInt(i))+"&j="+j+"'>"+r.data[i].child[j].name+"</a></li>";
					}
					str+="<li class='parentLi'><a class='primary' href='list.html?id="+r.data[i].id+"&pagetype="+(parseInt(i))+"'>"+r.data[i].name+"</a><ul>"+secondary+"</ul></li>";				
				}
			}	
			$("ul.nav").append(str);
		}else{
			alert(r.message);
		}
	}).done(function(){
		$(".head .top-container ul.nav li.parentLi:eq("+index+")").addClass("light").siblings().removeClass("light");
		$(".head .top-container ul.nav li").hover(function(){
			$(this).find("ul").css("display","block");
		},function(){
			$(this).find("ul").css("display","none");
		})
	});
}
var flashvars;
var arr = [];
var tArray = new Array();
//获取视频信息
function videoInfo(a,e){
	$.get('../front/web/webInterface!execute?uid=i0003&id='+a,function(r){		
		if(r.status=="success"){
			$(".videoInfo .videoInfo-container h4").html(r.data.videoTitle);
			$(".videoInfo .videoInfo-container p.intro").html(r.data.videoContext);
			var tips="";
			var deff="";
			var tipword="";
			var animatestr="";
			for(var i=r.data.webVideoPoints.length-1;i>=0;i--){		
				nodestr="";
				pinfo="";
				if(i==0){
					tips+=r.data.webVideoPoints[i].timepointSecond;
					tipword+=r.data.webVideoPoints[i].showMessage;
				}else{
					tips+=r.data.webVideoPoints[i].timepointSecond+"|";
					tipword+=r.data.webVideoPoints[i].showMessage+"|";
				}
				animatestr="<div class='videoAnimate videoAnimate"+i+"'><a onclick='shows(\""+r.data.webVideoPoints[i].commodity+"\")'><p class='titleP titleP"+i+"'>点击购买"+r.data.webVideoPoints[i].showMessage+"</p><p class='imgP imgP"+i+"'><img src='.."+r.data.webVideoPoints[i].commodityCover+"' alt='产品图片'></p><p class='scale scale"+i+"'></p></a></div>"+animatestr;
				arr[i]=r.data.webVideoPoints[i].timepointSecond;
				tArray[i]=[r.data.webVideoPoints[i].commodityCover,r.data.webVideoPoints[i].showMessage,r.data.webVideoPoints[i].commodity,i]; 
							
			}
			$("#videoFlash").append(animatestr);
			for(var i=0;i<r.data.videoURLs.length;i++){
				if(i==2){
					deff+="../../"+r.data.videoURLs[i];
				}else{
					deff+="../../"+r.data.videoURLs[i]+"|";
				}			
			}
				flashvars={
						f:'http://img.ksbbs.com/asset/Mon_1605/0ec8cc80112a2d6.mp4',//视频地址
						flashvars:'',
						a:'',//调用时的参数，只有当s>0的时候有效
						s:'0',//调用方式，0=普通方法（f=视频地址），1=网址形式,2=xml形式，3=swf形式(s>0时f=网址，配合a来完成对地址的组装)
						c:'0',//是否读取文本配置,0不是，1是
						x:'',//调用配置文件路径，只有在c=1时使用。默认为空调用的是ckplayer.xml
						i:'',//初始图片地址
						d:'',//暂停时播放的广告，swf/图片,多个用竖线隔开，图片要加链接地址，没有的时候留空就行
						u:'',//暂停时如果是图片的话，加个链接地址
						l:'',//前置广告，swf/图片/视频，多个用竖线隔开，图片和视频要加链接地址
						r:'',//前置广告的链接地址，多个用竖线隔开，没有的留空
						t:'10|10',//视频开始前播放swf/图片时的时间，多个用竖线隔开
						y:'',//这里是使用网址形式调用广告地址时使用，前提是要设置l的值为空
						z:'',//缓冲广告，只能放一个，swf格式
						e:'2',//视频结束后的动作，0是调用js函数，1是循环播放，2是暂停播放并且不调用广告，3是调用视频推荐列表的插件，4是清除视频流并调用js功能和1差不多，5是暂停播放并且调用暂停广告
						v:'80',//默认音量，0-100之间
						p:'0',//视频默认0是暂停，1是播放，2是不加载视频
						h:'0',//播放http视频流时采用何种拖动方法，=0不使用任意拖动，=1是使用按关键帧，=2是按时间点，=3是自动判断按什么(如果视频格式是.mp4就按关键帧，.flv就按关键时间)，=4也是自动判断(只要包含字符mp4就按mp4来，只要包含字符flv就按flv来)
						q:'',//视频流拖动时参考函数，默认是start
						m:'',//让该参数为一个链接地址时，单击播放器将跳转到该地址
						o:'',//当p=2时，可以设置视频的时间，单位，秒
						w:'',//当p=2时，可以设置视频的总字节数
						g:'',//视频直接g秒开始播放
						j:'',//跳过片尾功能，j>0则从播放多少时间后跳到结束，<0则总总时间-该值的绝对值时跳到结束
						k:'32|63|73',//提示点时间，如 30|60鼠标经过进度栏30秒，60秒会提示n指定的相应的文字
						n:'这是提示点的功能，如果不需要删除k和n的值|提示点测试60秒|提示啥',//提示点文字，跟k配合使用，如 提示点1|提示点2
						wh:'',//宽高比，可以自己定义视频的宽高或宽高比如：wh:'4:3',或wh:'1080:720'
						lv:'0',//是否是直播流，=1则锁定进度栏
						loaded:'addListener',//当播放器加载完成后发送该js函数loaded
						//调用播放器的所有参数列表结束
						//以下为自定义的播放器参数用来在插件里引用的
						my_title:'演示视频标题文字',
						my_url:encodeURIComponent(window.location.href)//本页面地址
						//调用自定义播放器参数结束
					};
				flashvars.k=tips;
				flashvars.n=tipword;
				flashvars.deff=deff;
				flashvars.i=".."+r.data.videoThumbanil;
				flashvars.f=".."+r.data.videoURLs[1];
					var params={bgcolor:'#FFF',allowFullScreen:true,allowScriptAccess:'always',wmode:'transparent'};
					CKobject.embedSWF('ckplayer/ckplayer.swf','a1','ckplayer_a1','100%','600',flashvars,params);
//			$(".cover").attr("src",".."+r.data.videoThumbanil);
		}else{
			alert(r.message);
		}
	}).done(function(){
//		addListener()
	})
}
//相关评测
function related(e){
	var src="";
	var clear="<div class='clear'></div>";
	$.get('../front/web/webInterface!execute?uid=i0002&category='+e+'&pagesize=3&pagenum=&sort=',function(r){
		if(r.status=="success"){
			var str ="";
			for ( var i = 0, l = r.data.length; i < l; i++ ) {
				var title=r.data[i].title;
				var titles = title;
				var urlimg=r.data[i].coverURL;
				if(index==0){
					str+="<a href='video.html?id="+r.data[i].id+"&category="+e+"&pagetype=0'><div class='video'><div class='img'><div class='top top5'><div class='cover-img' style='background-image:url(../"+urlimg+");'></div></div></div><p>"+titles+"</p><div class='clear'></div></div></a>";	
				}else if(index==1||index==3){
					str+="<a href='video.html?id="+r.data[i].id+"&category="+e+"&pagetype=1'><div class='video'><div class='top top1'><div class='cover-img cover-img1' style='background-image:url(../"+urlimg+");'></div></div><p>"+titles+"</p></div></a>";
				}else if(index==2){
					str+="<a href='video.html?id="+r.data[i].id+"&category="+e+"&pagetype=2'><div class='video'><div class='top top3'><div class='cover-img cover-img1' style='background-image:url(../"+urlimg+");'></div></div></div></a>";
				}
			}	
			$(".review .review-container").append(str+clear);
		}else{
			alert(r.message);
		}
	})
}
function addListener(){
	
	CKobject.getObjectById('ckplayer_a1').addListener('time',ckplayer_status);
//	ckplayer_status()
}

function ckplayer_status(){
	var a=CKobject.getObjectById('ckplayer_a1').getStatus();
	for(var i=0;i<arr.length;i++){		
		if(a["time"]>=Math.round(arr[i])&&a["time"]<=Number(Math.round(arr[i])+20)){			
			if($(".videoAnimate"+i).css("display")=="none"){
				for(var j=i+1;j<arr.length;j++){			
						var bottom=$(".videoAnimate"+j).css("bottom");
						bottom=Number(bottom.substring(0,bottom.length-2))+80;
					
				}
				$("div.animation"+i).parent().fadeIn();
				$(".videoAnimate"+i).css("display","block").animate({"right":"20px","height":80*(i-2)+180+"px"});
			}
		}else{
			textBoxClose(i);
			$("div.animation"+i).parent().fadeOut();
			$(".videoAnimate"+i).css("display","none");
		}
	}
}

var o={};
function textBoxShow(img,title,id,name){//增加提示文字
	o = {
		name: 'prompt', //该文本的名称，主要作用是关闭时需要用到
		coor: '0,2,-100,-100', //坐标
		text: '{a href="" target="_blank"}{font color="#FFFFFF" size="12" face="Microsoft YaHei,微软雅黑"}{/font}{/a}', //文字
		bgColor: '0x000000', //背景颜色
		borderColor: '0x000000', //边框颜色
		radius: 3, //圆角弧度
		alpha:0,//总体透明度
		bgAlpha: 50, //背景透明度
		xWidth: 20, //宽度修正
		xHeight: 5, //高度修正
		pic: [''], //附加图片地址数组，可以增加多个图片
		pwh:[[30,30]],//图片绽放宽高
		pEvent:[['url','']],//图片事件数组
		pCoor: ['0,0,-22,-3'], //图片坐标数组
		pRadius: [30], //附加图片的弧度
		tween:[['x',1,50,0.3],['alpha',1,100,0.3]]//缓动效果
	};
	o.pic[0]='../'+img;
	o.name='prompt'+name;
	o.text='{a href="javascript:shows(id)"}{font color="#FFFFFF" size="12" face="Microsoft YaHei,微软雅黑"}'+title+' {/font}{/a}';
	var boxtemp=CKobject.getObjectById('ckplayer_a1').textBoxShow(o);
}


function textBoxClose(i){
	var is=CKobject.getObjectById('ckplayer_a1').textBoxClose('prompt'+i);
}
function textBoxTween(i){
	CKobject.getObjectById('ckplayer_a1').textBoxTween('prompt'+i,[['y',0,-50,0.3]]);
}


var Show;
//360度展示
function shows(id){
	CKobject.getObjectById('ckplayer_a1').videoPause();
	Show=layer.open({
	  type: 2,
	  title: false,
	  shadeClose: false,
	  shade: 0.8,
	  scrollbar: false,
	  area: ['1000px', '662px'],
	  content: 'show.html?id='+id 
	}); 
	$(".layui-layer-setwin .layui-layer-close2").click(function(){
		CKobject.getObjectById('ckplayer_a1').videoPlay();
	});
   	$(".layui-layer-rim").css({"border":"none","border-radius":"0px"});

}

