var pageNum = '1';
var pagesize;
var count;
var flag = true;

$(".main_header a").click(function() {
	$(this).css({
		"background-color" : "#E0615F",
		"color" : "#FFFFFF"
	}).siblings("a").css({
		"background-color" : "#FFFFFF",
		"color" : "#9B9B9B"
	});

})

$(document).ready(function() {
	getList();
	getHistoryList();
	
	$(".easy_modal input").eq(0).click(function(){
		$(".easy_modal").fadeOut(function(){
			$(this).css({"height":"100px"});
		});
	});
})

function getList() {
	var id = url('?id') == null ? '' : url('?id');
	var page = (typeof pageNum == 'undefined') ? 1 : pageNum;
	var pagesize = (typeof pagesize == 'undefined') ? 10 : pagesize;
	var mUrl = '../admin/teacherGet?';
	mUrl += 'id=' + id;

	$.get(mUrl, function(r) {
		if (r.Status == 'success') {
			var template = $.templates('#queboxTpl');
			var html = template.render(r.Records);
			$('#queboxCnt').html(html);
			pagesize = r.pageSize;
			count = r.totalResultCount;
		}
	}).done(function(r) {
		$("#card_img").unbind("click").click(function(){
//			var this_index = $(this).siblings("input").val();
			
			$(".easy_modal").find("p").html("<img class='card_img' src="+r.Records.idcardPhoto.sourcePath+" />")
			$(".easy_modal img").load(function(){
				$(".easy_modal").fadeIn();
				$(".easy_modal").css({"height":"100px"});
				$(".easy_modal").css({"height":$(".easy_modal").height() + $(".card_img").height()});
				
			});
		})
		
	});
}

function getHistoryList() {
	var id = url('?id') == null ? '' : url('?id');
	var page = (typeof pageNum == 'undefined') ? 1 : pageNum;
	var pagesize = (typeof pagesize == 'undefined') ? 10 : pagesize;
	var mUrl = '../admin/teacherHistoryList?';
	mUrl += 'id=' + id;
	mUrl += '&pagenum=' + page;
	mUrl += '&pagesize=' + pagesize;

	$.get(mUrl, function(r) {
		if (r.Status == 'success') {
			if (r.TotalRecordCount > 0) {
				var template = $.templates('#historyInfo');
				var html = template.render(r.Records);
				$('#historyContent').html(html);
				pagesize = r.pageSize;
				count = r.totalResultCount;
			} else {
				var html = '<tr><td colspan=6>没有数据！</td></tr>'
				$('#historyContent').html(html);
			}
		}
	}).done(function(r) {
		if (flag) {
			$('#select_page').Paging({
				pagesize : r.PageSize,
				count : r.TotalRecordCount,
				callback : function(page, size, count) {
					pageNum = page;
					getHistoryList();
					flag = false;

				}
			});
		}
	});
}