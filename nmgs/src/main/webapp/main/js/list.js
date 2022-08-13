var index= (url('?pagetype') != null) ? url('?pagetype') : '';
var child= (url('?j') != null) ? url('?j') : '';
var id= (url('?id') != null) ? url('?id') : '';
var title="";
var firstTite;
var firstid;
var edid;
$(function(){	
	getList();
	screen(id);
})
//获取列表
function getList(){
	$.get('../front/web/webInterface!execute?uid=i0001',function(r){
		if(r.status=="success"){
			var str ="";
			var secondary="";
			for ( var i = 0, l = r.data.length; i < l; i++ ) {
				if(child!=""){
					firstTite=r.data[index].name;
					title=r.data[index].child[child].name;
					firstid=r.data[index].id;
					edid=r.data[index].child[child].id;
				}else{
					firstTite=r.data[index].name;
					firstid=r.data[index].id;
				}
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
		$(".review .review-container h1").html(firstTite).attr("onclick","screenss(\""+firstid+"\")");
		$(".review .review-container h2").html(title).attr("onclick","screen(\""+edid+"\")");
		$(".head .top-container ul.nav li").hover(function(){
			$(this).find("ul").css("display","block");
		},function(){
			$(this).find("ul").css("display","none");
		})
	});
}

var n=1;
var count;
//筛选
function screen(e){
	$(".div").html("");
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
				var urlimg=r.data[i].coverURL;
				if(index==0){
					str+="<a href='video.html?id="+r.data[i].id+"&category="+id+"&pagetype=1'><div class='video'><div class='img'><div class='top top5'><div class='cover-img' style='background-image:url(../"+urlimg+");'></div></div></div><p>"+titles+"</p><div class='clear'></div></div></a>";	
				}else if(index==1||index==3){
					str+="<a href='video.html?id="+r.data[i].id+"&category="+id+"&pagetype=2'><div class='video'><div class='top top1'><div class='cover-img cover-img1' style='background-image:url(../"+urlimg+");'></div></div><p>"+titles+"</p></div></a>";
				}else if(index==2){
					str+="<a href='video.html?id="+r.data[i].id+"&category="+id+"&pagetype=3'><div class='video'><div class='top top3'><div class='cover-img cover-img1' style='background-image:url(../"+urlimg+");'></div></div></div></a>";
				}
				
			}	
			$(".div").append(str+clear);
		}else{
			alert(r.message);
		}
	}).done(function(){
		if(n==count){
			$("span.loadmore").css("display","none");
		}else if(count=="0"){
			$("span.loadmore").css("display","none");
			$("#content .content_inner .review .dataList").html("<p style='text-align:center; font-size:0.20rem;line-height:2.0rem;height:5rem'>抱歉！没有符合条件的相关内容</p>");
		}else{
			$("span.loadmore").css("display","block").click(function(){
				screens(e);
			});
		}
		n++;
	});

	
}

//加载更多
function screens(e){
	var src="";
	var clear="<div class='clear'></div>";
	$.get('../front/web/webInterface!execute?uid=i0002&category='+e+'&pagesize=6&pagenum='+n+'&sort=',function(r){
		console.log(r)
    	count=r.totalPageCount;
		if(r.status=="success"){
			var str ="";
			for ( var i = 0, l = r.data.length; i < l; i++ ) {
				var title=r.data[i].title;
				var titles = title;
				var urlimg=r.data[i].coverURL;
				if(index==0){
					str+="<a href='video.html?id="+r.data[i].id+"&category="+id+"&pagetype=1'><div class='video'><div class='img'><div class='top top5'><div class='cover-img' style='background-image:url(../"+urlimg+");'></div></div></div><p>"+titles+"</p><div class='clear'></div></div></a>";	
				}else if(index==1||index==3){
					str+="<a href='video.html?id="+r.data[i].id+"&category="+id+"&pagetype=2'><div class='video'><div class='top top1'><div class='cover-img cover-img1' style='background-image:url(../"+urlimg+");'></div></div><p>"+titles+"</p></div></a>";
				}else if(index==2){
					str+="<a href='video.html?id="+r.data[i].id+"&category="+id+"&pagetype=3'><div class='video'><div class='top top3'><div class='cover-img cover-img1' style='background-image:url(../"+urlimg+");'></div></div></div></a>";
				}
			}	
			$(".div").append(str+clear);
		}else{
			alert(r.message);
		}
	}).done(function(){
		if(n==count){
			$("span.loadmore").css("display","none");
		}
		n++;
	});
	
}



function screenss(e){
	$(".review .review-container h2").css("display","none");
	$(".div").html("");
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
				var urlimg=r.data[i].coverURL;
				if(index==0){
					str+="<a href='video.html?id="+r.data[i].id+"&category="+id+"&pagetype=1'><div class='video'><div class='img'><div class='top top5'><div class='cover-img' style='background-image:url(../"+urlimg+");'></div></div></div><p>"+titles+"</p><div class='clear'></div></div></a>";	
				}else if(index==1||index==3){
					str+="<a href='video.html?id="+r.data[i].id+"&category="+id+"&pagetype=2'><div class='video'><div class='top top1'><div class='cover-img cover-img1' style='background-image:url(../"+urlimg+");'></div></div><p>"+titles+"</p></div></a>";
				}else if(index==2){
					str+="<a href='video.html?id="+r.data[i].id+"&category="+id+"&pagetype=3'><div class='video'><div class='top top3'><div class='cover-img cover-img1' style='background-image:url(../"+urlimg+");'></div></div></div></a>";
				}
				
			}	
			$(".div").append(str+clear);
		}else{
			alert(r.message);
		}
	}).done(function(){
		if(n==count){
			$("span.loadmore").css("display","none");
		}else if(count=="0"){
			$("span.loadmore").css("display","none");
			$("#content .content_inner .review .dataList").html("<p style='text-align:center; font-size:0.20rem;line-height:2.0rem;height:5rem'>抱歉！没有符合条件的相关内容</p>");
		}else{
			$("span.loadmore").css("display","block").click(function(){
				screens(e);
			});
		}
		n++;
	});

	
}




