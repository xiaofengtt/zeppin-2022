$(function(){
	
	getList();
})
//获取列表
function getList(){
	$.get('../front/web/webInterface!execute?uid=i0001',function(r){
		if(r.status=="success"){
			var str ="";
			var strs="";
			for ( var i = 0, l = r.data.length; i < l; i++ ) {
				str+="<li><a href='List.html?id="+r.data[i].id+"&title="+encodeURI(r.data[i].name)+"&pagetype="+(parseInt(i)+parseInt(1))+"'><i class='icon"+i+2+" icon'><img src='images/icon"+Number(i+2)+".png' alt='icon'></i><label>"+r.data[i].name+"</label><i class='navLiBg'><img src='images/navLiBg.png' alt='箭头'></i></a></li>";
				strs="";
				strs+="<a href='List.html?id="+r.data[i].id+"&title="+encodeURI(r.data[i].name)+"&pagetype="+(parseInt(i)+parseInt(1))+"'><h1>"+r.data[i].name+"</h1></a>";
				for(var j=0;j<r.data[i].child.length;j++){
					if(j>=2){
						break;
					}
					strs+="<a href='List.html?id="+r.data[i].child[j].id+"&name="+encodeURI(r.data[i].child[j].name)+"&title="+encodeURI(r.data[i].name)+"&pagetype="+(parseInt(i)+parseInt(1))+"'><h2>"+r.data[i].child[j].name+"</h2></a>";
				}
				if(i==0){
					$("#content .content_inner .NewRelease .list").prepend(strs);
					$("#content .content_inner .NewRelease .list h3").parent().attr("href","List.html?id="+r.data[i].id+"&title="+encodeURI(r.data[i].name)+"&pagetype=1");
					getIndexlist(r.data[i].id,"5");
					
				}else if(i==1){
					$("#content .content_inner .review .list").prepend(strs);
					$("#content .content_inner .review .list h3").parent().attr("href","List.html?id="+r.data[i].id+"&title="+encodeURI(r.data[i].name)+"&pagetype=2");
					getIndexlists(r.data[i].id,"6");
				}else if(i==3){
					$("#content .content_inner .Business .list").prepend(strs);
					$("#content .content_inner .Business .list h3").parent().attr("href","List.html?id="+r.data[i].id+"&title="+encodeURI(r.data[i].name)+"&pagetype=3");
					getIndexlistss(r.data[i].id,"2");
				}else if(i==2){
					$("#content .content_inner .starShow .list").prepend(strs);
					$("#content .content_inner .starShow .list h3").parent().attr("href","List.html?id="+r.data[i].id+"&title="+encodeURI(r.data[i].name)+"&pagetype=2");
					getIndexlistsss(r.data[i].id,"4");
				}
				
			}	
			$("ul.Index").append(str);
		}else{
			alert(r.message);
		}
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
					str+="<a href='Video.html?id="+r.data[0].id+"&category="+id+"&pagetype=1'><div class='top'><div class='cover-img banner' style='background-image:url(../"+urlimg+");'></div></div><p class='bannerInfo'>"+titles+"</p></a>";
				}else{
					if(i>4){
						break;
					}else{
						str+="<a href='Video.html?id="+r.data[i].id+"&category="+id+"&pagetype=1'><div class='video'><div class='img'><div class='top top1'><div class='cover-img' style='background-image:url(../"+urlimg+");'></div></div></div><p>"+titles+"</p><div class='clear'></div></div></a>";	

					}
				}
			}	
			$("#content .content_inner .NewRelease").append(str);
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
					if(i%2==0){
						str+="<a href='Video.html?id="+r.data[i].id+"&category="+id+"&pagetype=2'><div class='video_review video_reviewleft'><div class='top'><div class='cover-img' style='background-image:url(../"+urlimg+");'></div></div><p>"+titles+"</p></div></a>";
					}else{
						str+="<a href='Video.html?id="+r.data[i].id+"&category="+id+"&pagetype=2'><div class='video_review video_reviewright'><div class='top'><div class='cover-img cover-img1' style='background-image:url(../"+urlimg+");'></div></div><p>"+titles+"</p></div></a>";
					}
				}
				
			}	
			$("#content .content_inner .review .list").after(str);
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
			for ( var i = 0, l = r.data.length; i < l; i++ ) {
				var urlimg=r.data[i].coverURL;
				if(i>2){
					break;
				}else{
					str+="<a href='Video.html?id="+r.data[i].id+"&category="+id+"&pagetype=3'><div class='top top3'><div class='cover-img' style='background-image:url(../"+urlimg+");'></div></div></a>";
				}
			}	
			$("#content .content_inner .Business").append(str);
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
					if(i%2==0){
						str+="<a href='Video.html?id="+r.data[i].id+"&category="+id+"&pagetype=2'><div class='video_review video_reviewleft'><div class='top'><div class='cover-img' style='background-image:url(../"+urlimg+");'></div></div><p>"+titles+"</p></div></a>";
					}else{
						str+="<a href='Video.html?id="+r.data[i].id+"&category="+id+"&pagetype=2'><div class='video_review video_reviewright'><div class='top'><div class='cover-img' style='background-image:url(../"+urlimg+");'></div></div><p>"+titles+"</p></div></a>";
					}
				}
				
			}	
			$("#content .content_inner .starShow .list").after(str);
		}else{
			alert(r.message);
		}
	});
}


