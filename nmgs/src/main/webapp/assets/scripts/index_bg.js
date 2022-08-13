function gotoUrl(url){
	window.location.href=url;
}

$(function() {
	var html = '';
	$.getJSON('../front/admin/left!execute?uid=c0001', function(r) {
		if(r.status == "success"){
			$.each(r.data,function(i,v){
				html += '<li><a  href="javascript:;" onclick="gotoUrl(\'../views/videoPublishList.html?parent='+v.id+'\');">'+v.name+'</a><ul class="sub-menu">'
				$.each(r.data[i].child,function(n,vv){
					html += '<li><a href="javascript:;" onclick="gotoUrl(\'../views/videoPublishList.html?parent='+vv.id+'\');">'+vv.name+'</a>'
				})
				html += '</ul></li>';
			})
			
		}
	}).done(function(){
		$('.categoryList').html(html);
	})
	
	//获取屏幕宽高
	var height=$(window).height();
	var width=$("body").width();
	
	$(".page-content").css("min-height",height-96+"px");
	$("#contentIframes").css("width",width-235+"px");
	$(window).resize(function() {
		var height=$(window).height();
		var width=$("body").width();
		$(".page-content").css("min-height",height-96+"px");
		$("#contentIframes").css("width",width-235+"px");
	});
	
})