var pageNum = '1';
var pagesize;
var count;
var flag=true;
var name;
function searchBtn(){
	var _this = $(".search");
	name = _this.val();
	pageNum = '1';
	flag=true;
	getList(name);
	return false;
}

$(document).ready(function() {
	getList();
})

function getList(name){
	var page = (typeof pageNum == 'undefined') ? 1 : pageNum;
	var pagesize = (typeof pagesize == 'undefined') ? 50 : pagesize;
	var name = (typeof name == 'undefined') ? '' : name;
	var url = '../admin/examList?';
	url += 'pagenum='+page;
//	url += '&pagesize=10&exam='+id;
	url += '&pagesize='+pagesize;
	url += '&name='+name
	$.get(url,function(r) {
		if(r.Status =='success') {
			if(r.TotalRecordCount > 0){
				var template = $.templates('#queboxTpl');
				var html = template.render(r.Records);
				$('#queboxCnt').html(html);
				pagesize=r.pageSize;
				count=r.totalResultCount;
			}else{
				var html = '<tr><td colspan=11>没有数据！</td></tr>'
				$('#queboxCnt').html(html);
			}
		}
	}).done(function(r){
		if(flag){
			$('#select_page').Paging({
				pagesize: r.PageSize,
				count: r.TotalRecordCount,
				callback: function(page, size, count) {
					pageNum = page;
					getList(name);
					flag = false;
				}
			});
			$("#select_page").find(".ui-paging-container:last").siblings().remove();
		}

		
		
		$(".change").click(function(){
			var _this = $(this);
			var hiddenId = $(this).siblings("input").attr("id");
			window.location.href="publish_.jsp?id="+hiddenId;
//			$.ajax({
//				type:"get",
//				url:"../admin/examGetCurrent",
//				async:true,
//				success:function(r){
//					if(r.Status=="end"){
//
//					}else{
//						alert("有考试正在进行！");
//					}
//				}
//			});
		})
	});
}
