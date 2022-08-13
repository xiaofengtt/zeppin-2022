$(document).ready(function(){
	var pageNum = '1';
	var pagesize;
	var count;
	var flag=true;
//	$.ajax({
//		type:"post",
//		url:"../admin/recordsDistributeRoomList",
//		async:true,
//		data:{
//			exam:url("?exam")
//		},
//		success:function(r){
//			if(r.Status=="success"){
//				var template = $.templates("#template").render(r.Records);
//				$("#msg").html(template);
//			}else{
//				var template = $.templates("#template").render(r.Records);
//				$("#msg").html('<p class="title">信息获取失败，请重新返回</p>');
//			}
//		}
//	});
	
	function getList(name,sorts){
		$("#bg").show();
		var exam = url('?exam') == null ? "" : url('?exam');
		if(exam == ''){
			var html = '<tr class="nodata"><td colspan=11>数据获取错误！请返回重新获取</td></tr>'
			$('#msg').html(html);
			return false;
		}
		
		var roominfo='';
		if(typeof(name)!="undefined"){
			roominfo=name;
		}
		var sort='';
		if(typeof(sorts)!="undefined"){
			sort=sorts;
		}
		var page = (typeof pageNum == 'undefined') ? 1 : pageNum;
		var pagesize = (typeof pagesize == 'undefined') ? 20 : pagesize;
		var urls = '../admin/recordsDistributeRoomList?';
		urls += 'pagenum='+page;
		urls += '&pagesize='+pagesize;
		urls += '&exam='+exam;
//		urls += '&roomId='+roomId;
		if(roominfo != ''){
			urls += '&roominfo='+roominfo;
		}
		
		if(sort != ''){
			urls += '&sorts='+sort;
		}
		
		$.get(urls,function(r) {
			if(r.Status =='success') {
				if(r.TotalRecordCount > 0){
					var template = $.templates('#template');
					var html = template.render(r.Records);
					$('#msg').html(html);
					pagesize=r.pageSize;
					count=r.totalResultCount;
				}else{
					var html = '<tr class="nodata"><td colspan=11>没有数据！</td></tr>'
					$('#msg').html(html);
				}
			} else {
				var html = '<tr class="nodata"><td colspan=11>'+r.Message+'！</td></tr>'
				$('#msg').html(html);
			}
		}).done(function(r){
			minigrid('#msg','.main_bottom_room',20);	
			$(window).resize(function(){
				minigrid('#msg','.main_bottom_room',20);		
			})
			$(".title").html("关于"+r.Records[0].examStarttime+" 至 "+r.Records[0].examEndtime+"考试监考教师相关事宜");
			if(flag){
				$('#select_page').Paging({
					pagesize: r.PageSize,
					count: r.TotalRecordCount,
					callback: function(page, size, count) {
						pageNum = page;
						getList();
						flag = false;
						
					}
				});
				flag=false;
			}

		})
	}
	
	getList();
	
	
});
