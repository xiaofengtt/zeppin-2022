var uuid = url("?uuid") != null ? url("?uuid") : "";
$(function(){
	getNewsDetails();
})
//新闻详情
function getNewsDetails(){
	$.ajax({
		type: "get",
		url: "../front/news/get",
		async: true,
		data: {
			'uuid':uuid
		}
	})
	.done(function(r) {
		if(r.status == "SUCCESS") {			
			$(".newsDetail h1").html(r.data.title);
			$(".newsTime").html(r.data.newstime);
			$(".readNumber").html(parseInt(r.data.uuid.substring(r.data.uuid.length-3,r.data.uuid.length),16)+"&nbsp;阅读");
			$(".newsContent").html(r.data.content);
			getNewsList(r.data.category,r.data.uuid);
			getCommentList(r.data.uuid);
		}else {
						
		}
	})
	.fail(function() {	
		
	});	
}
//相关新闻
function getNewsList(category,except){
	$.ajax({
		type: "get",
		url: "../front/news/list",
		async: true,
		data: {
			'uuid':uuid,
			"pageSize":4,
			"pageNum":1,
			"category":category,
			"except":except,
			"sort":"checktime desc"
		}
	})
	.done(function(r) {
		if(r.status == "SUCCESS") {			
			var html = "";
			for(var i=0;i<r.data.length;i++){
				html+='<li><a href="share.html?uuid='+r.data[i].uuid+'">'+
				'<img src="..'+r.data[i].coverUrl+'">'+
				'<div><h1>'+r.data[i].title+'</h1><span>'+r.data[i].newstime.slice(5, r.data[i].newstime.length)
				+'</span><span class="text-right">'+parseInt(r.data[i].uuid.substring(r.data[i].uuid.length-3,r.data[i].uuid.length),16)
				+'&nbsp;阅读</span></div></a></li>'
			}
			$(".related-recommend ul").html(html);
		}else {
						
		}
	})
	.fail(function() {	
		
	});	
}
//全部评论
function getCommentList(except){
	$.ajax({
		type: "get",
		url: "../front/news/commentList",
		async: true,
		data: {
			"pageNum":1,
			"newsPublish":except
		}
	})
	.done(function(r) {
		if(r.status == "SUCCESS") {			
			var html = "";
			for(var i=0;i<r.data.length;i++){
				html+='<li><img src="img/default-avater-nor.png"><div><span class="user-name">热心球迷</span>'+
				'<span class="user-date">'+formatDateTime(new Date(r.data[i].createtime)).slice(5)+'</span><p class="user-comment">'+r.data[i].content+'</p></div></li>'
			}
			if(r.data.length==0){
				$(".comment-nodata").show();
				$(".lookMore").hide();
			}else{
				$(".comment ul").html(html).show();
				$(".lookMore").show();
				$(".comment-nodata").hide();
			}
			
		}else {
						
		}
	})
	.fail(function() {	
		
	});	
}
