
$(function(){
	$("#content .content_inner").css("min-height",window.screen.height/100-2+"rem");
	var parent = (url('?id') != null) ? url('?id') : '';
	var childname=decodeURI((url('?name') != null) ? url('?name') : '');
	var title=decodeURI((url('?title') != null) ? url('?title') : '');
	getList();
	screen(parent);
	if(childname==""){
		$("#content .content_inner p.title").html(title);
		secondary(parent);
	}else{
		$("#content .content_inner p.title").html(title+"<span style='font-size:0.30rem;'>&nbsp;&bull;&nbsp;"+childname+"</span>")
	}
	if(parent==""||title==""){
		history.go(-1);
	}
	$(".goback").click(function(){
		history.go(-1);
	})
})

//获取列表
function getList(){
	$.get('../front/web/webInterface!execute?uid=i0001',function(r){
		if(r.status=="success"){
			var str ="";
			for ( var i = 0, l = r.data.length; i < l; i++ ) {
				str+="<li><a href='List.html?id="+r.data[i].id+"&title="+encodeURI(r.data[i].name)+"'><i class='icon2 icon'><img src='images/icon"+Number(i+2)+".png' alt='icon'></i><label>"+r.data[i].name+"</label><i class='navLiBg'><img src='images/navLiBg.png' alt='箭头'></i></a></li>";
			}	
			$("ul.Index").append(str);
		}else{
			alert(r.message);
		}
	});
	
}

var n=1;
var count;
//筛选
function screen(e){
	n=1;
	var src="";
	var clear="<div class='clear'></div>";
	$.get('../front/web/webInterface!execute?uid=i0002&category='+e+'&pagesize=6&pagenum='+n+'&sort=',function(r){
    	count=r.totalPageCount;
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
			$("#content .content_inner .review .dataList").html(str+clear);
		}else{
			alert(r.message);
		}
	}).done(function(){
		if(n==count){
			$(".morehr").css("display","none");
			$("span.loadmore").css("display","none");
		}else if(count=="0"){
			$(".morehr").css("display","none");
			$("span.loadmore").css("display","none");
			$("#content .content_inner .review .dataList").html("<p style='text-align:center; font-size:0.20rem;line-height:2.0rem;height:5rem'>抱歉！没有符合条件的相关内容</p>");
		}
		n++;
	});

	
}

//加载更多
function screens(){
	var src="";
	var clear="<div class='clear'></div>";
	$.get('../front/web/webInterface!execute?uid=i0002&category='+e+'&pagesize=6&pagenum='+n+'&sort=',function(r){
    	count=r.totalPageCount;
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
			$("#content .content_inner .review").append(str+clear);
		}else{
			alert(r.message);
		}
	}).done(function(){
		if(n==count){
			$(".morehr").css("display","none");
			$("span.loadmore").css("display","none");
		}
		n++;
	});
	
}

//获取二级列表
function secondary(parent){
	$.get('../front/web/webInterface!execute?uid=i0001&parent='+parent,function(r){
		if(r.status=="success"){
			var str ="";
			for ( var i = 0, l = r.data.length; i < l; i++ ) {
				str+="<li><a name='"+r.data[i].id+"' href='javascript:' onclick='screen(\""+r.data[i].id+"\")'>"+r.data[i].name+"</a></li>";
				
			}	
			$("#content .content_inner p.title").after("<ul class='aaaa'><li class='light' onclick='screen(\""+parent+"\")'><a name=>全部</a></li>"+str+"<div class='clear'></div></ul>");
		}else{
			alert(r.message);
		}
	}).done(function(){
		$("#content .content_inner ul li").click(function(){
			$(this).addClass("light").siblings().removeClass("light");
			
		});
	});
}




