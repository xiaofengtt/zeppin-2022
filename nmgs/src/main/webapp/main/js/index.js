$(function(){
	var swiper = new Swiper('.swipe1', {
		mode:'horizontal',
		pagination: '#figure_pagination_1',
		paginationClickable: true,
		autoplay: 2500,
		loop:true,
		autoplayDisableOnInteraction : false
	});
	
	getList();
});
var clear="<div class='clear'></div>";
//获取列表
function getList(){
	$.get('../front/web/webInterface!execute?uid=i0001',function(r){
		if(r.status=="success"){
			var str ="";
			var strs="";
			var secondary="";
			for ( var i = 0, l = r.data.length; i < l; i++ ) {
				strs="";
				secondary="";
				if(i>=4){
					break;
				}else{
					strs+="<a href='list.html?id="+r.data[i].id+"&pagetype="+(parseInt(i))+"'><h1>"+r.data[i].name+"</h1></a>";
					for(var j=0;j<r.data[i].child.length;j++){
						secondary+="<li><a href='list.html?id="+r.data[i].child[j].id+"&pagetype="+(parseInt(i))+"&j="+j+"'>"+r.data[i].child[j].name+"</a></li>";
						strs+="<a href='list.html?id="+r.data[i].child[j].id+"&pagetype="+(parseInt(i))+"&j="+j+"''><h2>"+r.data[i].child[j].name+"</h2></a>";
					}
					str+="<li><a class='primary' href='list.html?id="+r.data[i].id+"&pagetype="+(parseInt(i))+"'>"+r.data[i].name+"</a><ul>"+secondary+"</ul></li>";
					if(i==0){
						$(".NewRelease .NewRelease-container").prepend(strs);
						getIndexlist(r.data[i].id,"5");
						
					}else if(i==1){
						$(".review .review-container").prepend(strs);
						getIndexlists(r.data[i].id,"6");
					}else if(i==2){
						$(".bussiness .bussness-container").prepend(strs);
						getIndexlistss(r.data[i].id,"8");
					}else if(i==3){
						$(".star .star-container").prepend(strs);
						getIndexlistsss(r.data[i].id,"4");
					}
				}
				
				
			}	
			$("ul.nav").append(str);
		}else{
			alert(r.message);
		}
	}).done(function(){
		$(".head .top-container ul.nav li").hover(function(){
			$(this).find("ul").css("display","block");
		},function(){
			$(this).find("ul").css("display","none");
		})
	});
}
//首页信息新机发布
function getIndexlist(id,num){
	$.get('../front/web/webInterface!execute?uid=i0002&category='+id+'&pagesize='+num+'&pagenum=&sort=',function(r){
		if(r.status=="success"){
			var str ="";
			for ( var i = 0, l = r.data.length; i < l; i++ ) {
				var title=r.data[i].title;
				var titles = title;
				var urlimg=r.data[i].coverURL;
				if(i==0){
					str+="<a href='video.html?id="+r.data[i].id+"&category="+id+"&pagetype=1'><div class='video video1'><div class='img'><div class='top top4'><div class='cover-img' style='background-image:url(../"+urlimg+");'></div></div></div><p>"+titles+"</p><div class='clear'></div></div></a>";
				}else{
					if(i>4){
						break;
					}else{
						str+="<a href='video.html?id="+r.data[i].id+"&category="+id+"&pagetype=1'><div class='video'><div class='img'><div class='top top5'><div class='cover-img' style='background-image:url(../"+urlimg+");'></div></div></div><p>"+titles+"</p><div class='clear'></div></div></a>";	

					}
				}
			}	
			$(".NewRelease .NewRelease-container").append(str+clear);
		}else{
			alert(r.message);
		}
	});
}

//首页信息测评
function getIndexlists(id,num){
	$.get('../front/web/webInterface!execute?uid=i0002&category='+id+'&pagesize='+num+'&pagenum=&sort=',function(r){
		if(r.status=="success"){
			var str ="";
			for ( var i = 0, l = r.data.length; i < l; i++ ) {
				var title=r.data[i].title;
				var titles = title;
				var urlimg=r.data[i].coverURL;
				if(i>6){
					break;
				}else{
					str+="<a href='video.html?id="+r.data[i].id+"&category="+id+"&pagetype=2'><div class='video'><div class='top top1'><div class='cover-img cover-img1' style='background-image:url(../"+urlimg+");'></div></div><p>"+titles+"</p></div></a>";
				}
				
			}	
			$(".review .review-container").append(str+clear);
		}else{
			alert(r.message);
		}
	});
}

//首页信息业务办理
function getIndexlistss(id,num){
	$.get('../front/web/webInterface!execute?uid=i0002&category='+id+'&pagesize='+num+'&pagenum=&sort=',function(r){
		if(r.status=="success"){
			var str ="";	
			var ulstr="";
			for ( var i = 0, l = r.data.length; i < l; i++ ) {
				var urlimg=r.data[i].coverURL;
				var title=r.data[i].title;
				var titles = title;
				if(i>1){
					ulstr+="<li><a href='video.html'?id="+r.data[i].id+"&category="+id+"&pagetype=3'>"+r.data[i].title+"</a></li>";
				}else{
					str+="<a href='video.html?id="+r.data[i].id+"&category="+id+"&pagetype=3'><div class='video'><div class='top top3'><div class='cover-img cover-img1' style='background-image:url(../"+urlimg+");'></div></div></div></a>";
				}
			}	
			$(".bussiness .bussness-container ul").append(ulstr);
			$(".bussiness .bussness-container ul").before(str);
			
		}else{
			alert(r.message);
		}
	});
}

//首页信息明星Show
function getIndexlistsss(id,num){
	$.get('../front/web/webInterface!execute?uid=i0002&category='+id+'&pagesize='+num+'&pagenum=&sort=',function(r){
		if(r.status=="success"){
			var str ="";
			for ( var i = 0, l = r.data.length; i < l; i++ ) {
				var title=r.data[i].title;
				var titles = title;
				var urlimg=r.data[i].coverURL;
				if(i>6){
					break;
				}else{
					str+="<a href='video.html?id="+r.data[i].id+"&category="+id+"&pagetype=2'><div class='video'><div class='top top1'><div class='cover-img cover-img1' style='background-image:url(../"+urlimg+");'></div></div><p>"+titles+"</p></div></a>";
				}			
			}	
			$(".star .star-container").append(str+clear);
		}else{
			alert(r.message);
		}
	});
}






