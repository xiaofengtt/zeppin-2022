var pageNum = '1';
var pagesize;
var count;
var flag=true;
var _name;
var _sorts;
var target = true;
$(".main_header a").click(function(){
    $(this).addClass("lighting").siblings().removeClass("lighting");
    pageNum = '1';
    flag = true;
    getList(_name,_sorts);
});
$.ajax({
	type:"get",
	url:"../admin/examGetCurrent",
	async:true,
	success:function(r){
		$(".title").html(r.Records.name);
	}
});

function searchBtn(){
		_name = $(".search").val();
		pageNum = '1';
		flag=true;
		getList(_name);
		return false;
}
$(document).ready(function() {
	
	function sort(sort){
		getList(_name,sort);
	}
	getList();
	
	
	//排序
	var sort_flag_0 = true;	
	var sort_flag_1 = true;	
	var sort_flag_2 = true;	
	var sort_flag_3 = true;	
	var sort_flag_4 = true;	
	var sort_flag_5 = true;	
	var sort_flag_6 = true;	
	var sort_flag_7 = true;	
	var sort_flag_8 = true;	
	var sort_flag_9 = true;
	var sort_flag_10 = true;
	
	$(".sort_th").eq(0).click(function() {
			sort_flag_1 = true;	
			sort_flag_2 = true;	
			sort_flag_3 = true;	
			sort_flag_4 = true;	
			sort_flag_5 = true;	
			sort_flag_6 = true;	
			sort_flag_7 = true;	
			sort_flag_8 = true;
			sort_flag_9 = true;
			sort_flag_10 = true;
			
		if(sort_flag_0 == true){
			$(this).addClass('light');
			$(this).find('img').attr('src','../img/toptop_r_14.png');
			$(this).siblings().removeClass('light');
			$(this).siblings().find('img').attr('src','');
			sort("name-desc");
			_sorts = "name_desc";
			sort_flag_0 = false;
		}else{
			$(this).find('img').attr('src','../img/bottombottom_r_14.png');
			sort("name-asc");
			_sorts = "name-asc";
			sort_flag_0 = true;
		}
	});
	
	$(".sort_th").eq(1).click(function() {
			sort_flag_0 = true;	
			sort_flag_2 = true;	
			sort_flag_3 = true;	
			sort_flag_4 = true;	
			sort_flag_5 = true;	
			sort_flag_6 = true;	
			sort_flag_7 = true;	
			sort_flag_8 = true;
			sort_flag_9 = true;
			sort_flag_10 = true;
		if(sort_flag_1 == true){
			$(this).addClass('light');
			$(this).find('img').attr('src','../img/toptop_r_14.png');
			$(this).siblings().removeClass('light');
			$(this).siblings().find('img').attr('src','');
			sort("mobile-desc");
			_sorts = "mobile-desc";
			sort_flag_1 = false;
		}else{
			$(this).find('img').attr('src','../img/bottombottom_r_14.png');
			sort("mobile-asc");
			_sorts = "mobile-asc";
			sort_flag_1 = true;
		}
	});
	
	$(".sort_th").eq(2).click(function() {
			sort_flag_0 = true;	
			sort_flag_1 = true;	
			sort_flag_3 = true;	
			sort_flag_4 = true;	
			sort_flag_5 = true;	
			sort_flag_6 = true;	
			sort_flag_7 = true;	
			sort_flag_8 = true;
			sort_flag_9 = true;
			sort_flag_10 = true;
		if(sort_flag_2 == true){
			$(this).addClass('light');
			$(this).find('img').attr('src','../img/toptop_r_14.png');
			$(this).siblings().removeClass('light');
			$(this).siblings().find('img').attr('src','');
			sort("ethnic-desc");
			_sort = "ethnic-desc";
			sort_flag_2 = false;
		}else{
			$(this).find('img').attr('src','../img/bottombottom_r_14.png');
			sort("ethnic-asc");
			_sorts = "ethnic-asc";
			sort_flag_2 = true;
		}
	});
	
	
	$(".sort_th").eq(3).click(function() {
			sort_flag_0 = true;	
			sort_flag_1 = true;	
			sort_flag_2 = true;	
			sort_flag_4 = true;	
			sort_flag_5 = true;	
			sort_flag_6 = true;	
			sort_flag_7 = true;	
			sort_flag_8 = true;
			sort_flag_9 = true;
			sort_flag_10 = true;
		if(sort_flag_3 == true){
			$(this).addClass('light');
			$(this).find('img').attr('src','../img/toptop_r_14.png');
			$(this).siblings().removeClass('light');
			$(this).siblings().find('img').attr('src','');
			sort("sex-desc");
			_sorts = "sex-desc";
			sort_flag_3 = false;
		}else{
			$(this).find('img').attr('src','../img/bottombottom_r_14.png');
			sort("sex-asc");
			_sorts = "sex-asc";
			sort_flag_3 = true;
		}
	});
	
	$(".sort_th").eq(4).click(function() {
			sort_flag_0 = true;	
			sort_flag_1 = true;	
			sort_flag_2 = true;	
			sort_flag_3 = true;	
			sort_flag_5 = true;	
			sort_flag_6 = true;	
			sort_flag_7 = true;	
			sort_flag_8 = true;
			sort_flag_9 = true;
			sort_flag_10 = true;
		if(sort_flag_4 == true){
			$(this).addClass('light');
			$(this).find('img').attr('src','../img/toptop_r_14.png');
			$(this).siblings().removeClass('light');
			$(this).siblings().find('img').attr('src','');
			sort("isChiefExaminer-desc");
			_sorts = "isChiefExaminer-desc";
			sort_flag_4 = false;
		}else{
			$(this).find('img').attr('src','../img/bottombottom_r_14.png');
			sort("isChiefExaminer-asc");
			_sorts = "isChiefExaminer-asc";
			sort_flag_4 = true;
		}
	});
	
	
	$(".sort_th").eq(5).click(function() {
			sort_flag_0 = true;	
			sort_flag_1 = true;	
			sort_flag_2 = true;	
			sort_flag_3 = true;	
			sort_flag_4 = true;	
			sort_flag_6 = true;	
			sort_flag_7 = true;	
			sort_flag_8 = true;
			sort_flag_9 = true;
			sort_flag_10 = true;
		if(sort_flag_5 == true){
			$(this).addClass('light');
			$(this).find('img').attr('src','../img/toptop_r_14.png');
			$(this).siblings().removeClass('light');
			$(this).siblings().find('img').attr('src','');
			sort("isMixedExaminer-desc");
			_sorts = "isMixedExaminer-desc";
			sort_flag_5 = false;
		}else{
			$(this).find('img').attr('src','../img/bottombottom_r_14.png');
			sort("isMixedExaminer-asc");
			_sorts = "isMixedExaminer-asc";
			sort_flag_5 = true;
		}
	});
	
	
	$(".sort_th").eq(6).click(function() {
			sort_flag_0 = true;	
			sort_flag_1 = true;	
			sort_flag_2 = true;	
			sort_flag_3 = true;	
			sort_flag_4 = true;	
			sort_flag_5 = true;	
			sort_flag_7 = true;
			sort_flag_8 = true;
			sort_flag_9 = true;
			sort_flag_10 = true;
		if(sort_flag_6 == true){
			$(this).addClass('light');
			$(this).find('img').attr('src','../img/toptop_r_14.png');
			$(this).siblings().removeClass('light');
			$(this).siblings().find('img').attr('src','');
			sort("type-desc");
			_sorts = "type-desc";
			sort_flag_6 = false;
		}else{
			$(this).find('img').attr('src','../img/bottombottom_r_14.png');
			sort("type-asc");
			_sorts = "type-asc";
			sort_flag_6 = true;
		}
	});
	
	
	$(".sort_th").eq(7).click(function() {
			sort_flag_0 = true;	
			sort_flag_1 = true;	
			sort_flag_2 = true;	
			sort_flag_3 = true;	
			sort_flag_4 = true;	
			sort_flag_5 = true;	
			sort_flag_6 = true;	
			sort_flag_8 = true;	
			sort_flag_9 = true;	
			sort_flag_10 = true;
		if(sort_flag_7 == true){
			$(this).addClass('light');
			$(this).find('img').attr('src','../img/toptop_r_14.png');
			$(this).siblings().removeClass('light');
			$(this).siblings().find('img').attr('src','');
			sort("major-desc");
			_sorts = "major-desc";
			sort_flag_7 = false;
		}else{
			$(this).find('img').attr('src','../img/bottombottom_r_14.png');
			sort("major-asc");
			_sorts = "major-asc";
			sort_flag_7 = true;
		}
	});
	
	$(".sort_th").eq(8).click(function() {
			sort_flag_0 = true;	
			sort_flag_1 = true;	
			sort_flag_2 = true;	
			sort_flag_3 = true;	
			sort_flag_4 = true;	
			sort_flag_5 = true;	
			sort_flag_6 = true;	
			sort_flag_7 = true;	
			sort_flag_9 = true;	
			sort_flag_10 = true;
		if(sort_flag_8 == true){
			$(this).addClass('light');
			$(this).find('img').attr('src','../img/toptop_r_14.png');
			$(this).siblings().removeClass('light');
			$(this).siblings().find('img').attr('src','');
			sort("intgral-desc");
			_sorts = "intgral-desc";
			sort_flag_8 = false;
		}else{
			$(this).find('img').attr('src','../img/bottombottom_r_14.png');
			sort("intgral-asc");
			_sorts = "intgral-asc";
			sort_flag_8 = true;
		}
	});
	
	$(".sort_th").eq(9).click(function() {
			sort_flag_0 = true;	
			sort_flag_1 = true;	
			sort_flag_2 = true;	
			sort_flag_3 = true;	
			sort_flag_4 = true;	
			sort_flag_5 = true;	
			sort_flag_6 = true;	
			sort_flag_7 = true;	
			sort_flag_8 = true;	
			sort_flag_10 = true;
		if(sort_flag_9 == true){
			$(this).addClass('light');
			$(this).find('img').attr('src','../img/toptop_r_14.png');
			$(this).siblings().removeClass('light');
			$(this).siblings().find('img').attr('src','');
			sort("isConfirm0-desc");
			_sorts = "isConfirm0-desc";
			sort_flag_9 = false;
		}else{
			$(this).find('img').attr('src','../img/bottombottom_r_14.png');
			sort("isConfirm0-asc");
			_sorts = "isConfirm-0asc";
			sort_flag_9 = true;
		}
	});
	
	
	$(".sort_th").eq(10).click(function() {
			sort_flag_0 = true;	
			sort_flag_1 = true;	
			sort_flag_2 = true;	
			sort_flag_3 = true;	
			sort_flag_4 = true;	
			sort_flag_5 = true;	
			sort_flag_6 = true;	
			sort_flag_7 = true;	
			sort_flag_8 = true;	
			sort_flag_9 = true;
		if(sort_flag_10 == true){
			$(this).addClass('light');
			$(this).find('img').attr('src','../img/toptop_r_14.png');
			$(this).siblings().removeClass('light');
			$(this).siblings().find('img').attr('src','');
			sort("applytime0-desc");
			_sorts = "applytime0-desc";
			sort_flag_10 = false;
		}else{
			$(this).find('img').attr('src','../img/bottombottom_r_14.png');
			sort("applytime0-asc");
			_sorts = "applytime0-asc";
			sort_flag_10 = true;
		}
	});
	
	
	
	
	
})//$(document).ready()

function getList(name,sorts){
	var exam = url('?exam') == null ? '' : url('?exam');
//	var applyStatus = url('&applyStatus') == null ? '' : url('&applyStatus');
//	var type = url('&type') == null ? '' : url('&type');
//	var teacherStatus = url('&teacherStatus') == null ? '' : url('&teacherStatus');
//	var isChief = url('&isChief') == null ? '' : url('&isChief');
//	var isMixed = url('&isMixed') == null ? '' : url('&isMixed');
	
	var teacherinfo='';
	if(typeof(name)!="undefined"){
		teacherinfo=name;
	}
	var sort='';
	if(typeof(sorts)!="undefined"){
		sort=sorts;
	}
	var page = (typeof pageNum == 'undefined') ? 1 : pageNum;
	var pagesize = (typeof pagesize == 'undefined') ? 50 : pagesize;
	var mUrl = '../admin/recordsAllList?';
	mUrl += 'pagenum='+page;
	mUrl += '&pagesize='+pagesize;
	mUrl += '&exam='+exam;
	if(teacherinfo != ''){
		mUrl += '&teacherinfo='+teacherinfo;
	}
	var applyStatus = $(".filter-applyStatus").find(".lighting").attr("data-value");
	var type = $(".filter-type").find(".lighting").attr("data-value");
	var teacherStatus = $(".filter-teacherStatus").find(".lighting").attr("data-value");
	var isChief = $(".filter-isChief").find(".lighting").attr("data-value");
	var isMixed = $(".filter-isMixed").find(".lighting").attr("data-value");
	
	mUrl += '&sorts='+sort;
	mUrl += '&applyStatus='+applyStatus;
	mUrl += '&type='+type;
	mUrl += '&teacherStatus='+teacherStatus;
	mUrl += '&isChief='+isChief;
	mUrl += '&isMixed='+isMixed;
	
	$.get(mUrl,function(r) {
		if(r.Status =='success') {
//			r.totalPageCount && $('.quepager').html('<span style="font-weight:normal">'+ r.pageNum +'</span>/'+ r.totalPageCount);
			if(r.TotalRecordCount > 0){
				var template = $.templates('#queboxTpl');
				var html = template.render(r.Records);
				$('#queboxCnt').html(html);
				pagesize=r.pageSize;
				count=r.totalResultCount;
			}else{
				var html = '<tr><td colspan=13>没有数据！</td></tr>'
				$('#queboxCnt').html(html);
			}
		} else {
			alert(r.Message);
		}
	}).done(function(r){
		if(flag){
			$('#select_page').Paging({
				pagesize: r.PageSize,
				count: r.TotalRecordCount,
				callback: function(page, size, count) {
					pageNum = page;
					getList(_name,_sorts);
					flag = false;
				}
			});
			$("#select_page").find(".ui-paging-container:last").siblings().remove();
		};
		
	});
	
}