var pageNum = '1';
var pagesize;
var count;
var flag = true;
var exam = "";
var _sorts;
var _name;


$(document).ready(function() {
//	getList();
	getCurrent();
	
	$(".easy_modal input").eq(0).click(function(){
		$(".easy_modal").fadeOut();
	})
})
function searchBtn() {
	_name = $(".search").val();
	pageNum = '1';
	flag=true;
	getList(_name,_sorts);
	return false;
}
function sort(sort) {
	_name = $(".search").val();
	getList(_name, sort);
}


//排序
			var DorA_1 = true;
			var DorA_2 = true;
			var DorA_3 = true;
			var DorA_4 = true;
			
			$(".select_bar_left li").eq(0).unbind("click").click(function() {
				DorA_2 = true;
				DorA_3 = true;
				if(DorA_1==true){
					$(this).find('a').addClass('light');
					$(this).find('img').attr('src','../img/toptop_r_14.png');
					$(this).siblings().find('a').removeClass('light');
					$(this).siblings().find('img').attr('src','../img/toptop_b_14.png');
					_sorts = "name-desc";
					sort("name-desc");
					DorA_1=false;
				}else{
					$(this).find('img').attr('src','../img/bottombottom_r_14.png');
					_sorts = "name-asc";
					sort("name-asc");
					
					DorA_1=true;
				}
		
				
			});
			
			$(".select_bar_left li").eq(1).unbind("click").click(function() {
				DorA_1 = true;
				DorA_3 = true;
				if(DorA_2==true){
					$(this).find('a').addClass('light');
					$(this).find('img').attr('src','../img/toptop_r_14.png');
					$(this).siblings().find('a').removeClass('light');
					$(this).siblings().find('img').attr('src','../img/toptop_b_14.png');
					sort("sex-desc");
					_sorts = "sex-desc"
					DorA_2=false;
				}else{
					$(this).find('img').attr('src','../img/bottombottom_r_14.png');
					sort("sex-asc");
					_sorts = "sex-asc";
					DorA_2=true;
				}
		
			});
			
			$(".select_bar_left li").eq(2).unbind("click").click(function() {
				DorA_1 = true;
				DorA_2 = true;
				
				if(DorA_3==true){
					$(this).find('a').addClass('light');
					$(this).find('img').attr('src','../img/toptop_r_14.png');
					$(this).siblings().find('a').removeClass('light');
					$(this).siblings().find('img').attr('src','../img/toptop_b_14.png');
					sort("createtime-desc");
					_sorts = "createtime-desc";
					DorA_3=false;
				}else{
					$(this).find('img').attr('src','../img/bottombottom_r_14.png');
					sort("createtime-asc");
					_sorts = "createtime-asc";
					DorA_3=true;
				}
			});
			
				



	
	

//排序结束


$(".main_header a").click(function() {
	$(this).addClass("lighting").siblings().removeClass("lighting");
	pageNum = '1';
	flag=true;
	getList(_name,_sorts);
})



//申请监考
function apply(id) {
	$.ajax({
		type: "post",
		url: "../admin/recordsAdd",
		async: true,
		data: {
			"teacher":id,
			"exam": exam
			
		},
		success: function(data) {
			if (data.Status == 'success') {
				$(".easy_modal p").html(data.Message);
				$(".easy_modal").fadeIn();
				getList(_name,_sorts);
			}else{
				$(".easy_modal p").html(data.Message);
				$(".easy_modal").fadeIn();
			}
		},
		fail: function() {
			$(".easy_modal p").html("请求错误，请稍后重试");
			$(".easy_modal").fadeIn();
		}
	});
}
//获取当前考试信息
function getCurrent(){
	var mUrl = '../admin/examGetCurrent?';
	$.get(mUrl, function(r) {
		if (r.Status == 'success') {
			exam = r.Records.id;
		}else{
			$(".easy_modal p").html(r.Message);
			$(".easy_modal").fadeIn();
		}
	getList(_name,_sorts);
	}).done(function(r) {
	});
}

//获取监考教师信息列表
function getList(name, sorts) {
	var teacherinfo = '';
	if (typeof (name) != "undefined") {
		teacherinfo = name;
	}
	var sort = '';
	if (typeof (sorts) != "undefined") {
		sort = sorts;
	}
	var page = (typeof pageNum == 'undefined') ? 1 : pageNum;
	var pagesize = (typeof pagesize == 'undefined') ? 10 : pagesize;
	var mUrl = '../admin/teacherList?'; 
	mUrl += 'pagenum=' + page;
	mUrl += '&pagesize=' + pagesize;
	mUrl += '&exam=' + exam;
	mUrl += '&checkStatus=2';
	if (teacherinfo != '') {
		mUrl += '&name=' + teacherinfo;
	}
	if (sort != '') {
		mUrl += '&sorts=' + sort;
	}

	var type = $(".filter-type").find(".lighting").attr("data-value");
	var status = $(".filter-status").find(".lighting").attr("data-value");
	var isChief = $(".filter-isChief").find(".lighting").attr("data-value");
	var isMixed = $(".filter-isMixed").find(".lighting").attr("data-value");

	mUrl += '&type=' + type;
	mUrl += '&status=' + status;
	mUrl += '&isChief=' + isChief;
	mUrl += '&isMixed=' + isMixed;

	$.get(mUrl, function(r) {
		if (r.Status == 'success') {
			if (r.TotalRecordCount > 0) {
				var template = $.templates('#queboxTpl');
				var html = template.render(r.Records);
				$('#queboxCnt').html(html);
				pagesize = r.pageSize;
				count = r.totalResultCount;
			} else {
				var html = '<div><span>没有数据！</span></div>'
				$('#queboxCnt').html(html);
			}
		} else {
			// layer.msg(r.message, {
			// time: 2000
			// })
		}
	}).done(function(r) {
		if (flag) {
			$('#select_page').Paging({
				pagesize : r.PageSize,
				count : r.TotalRecordCount,
				callback : function(page, size, count) {
					pageNum = page;
					getList(_name,_sorts);
					flag = false;
				}
			});
			$("#select_page").find(".ui-paging-container:last").siblings().remove();
		}
		$(".action").click(function(){
			var _this = $(this);
			$(".modal_action").fadeIn();
			$(".easy_modal").fadeOut();
			$("#modal").fadeOut();
			$("#delete_teacher_modal").fadeOut();
			$(".modal_action .button_g input").eq(0).unbind("click").click(function(){
				$("#action_id").attr("value",_this.attr("data-id"));
				$.ajax({
					type:"post",
					url:"../admin/teacherResume",
					async:true,
					data:{
						"id":$("#action_id").val(),
						'exam':exam
					},
					success:function(r){
						if(r.Status = "success"){
							$(".easy_modal p").html(r.Message);
							$(".easy_modal").fadeIn();
							$(".modal_action").fadeOut();
							getList(_name,_sorts);
						}else{
							$(".easy_modal p").html(r.Message);
							$(".easy_modal").fadeIn();

						}
					}
				});
			})
		})
		
		$(".modal_action .button_g input").eq(1).click(function(){
			$(".modal_action").fadeOut();
		})
		
		$(".delete").click(function(){
			var _this = $(this);
			$("#teacherId").attr("value",_this.attr("data-id"));
			$("#reason").val('');
			$(".modal_action").fadeOut();
			$(".easy_modal").fadeOut();
			$("#delete_teacher_modal").fadeOut();
			$("#modal").fadeIn();
			$(".time_check").unbind("change").change(function() {
	            $(this).prop("disabled", true);
	            $(this).siblings('.time_check').prop("disabled", false);
	            $(this).siblings('.time_check').prop("checked", false);
	            $("#time_value").val($(this).val());
	        })
			$("#submit").unbind("click").click(function(){
				if($("#reason").val()!=""){
					$.ajax({
						type:"post",
						url:"../admin/recordsDisable",
						async:true,
						data:{
							exam:exam,
							id: $("#teacherId").val(),
							reason: $("#reason").val(),
							disableType: $("#time_value").val()
						},
						success:function(r){
							if(r.Status=="success"){
								$(".easy_modal p").html(r.Message);
								$(".easy_modal").fadeIn();
								$(".time_check").prop("disabled", false).prop("checked", false);
								$(".time_check").eq(0).prop("disabled", true).prop("checked", true);
								$("#time_value").val('2');
								$("#modal").fadeOut();
								$("#reason").val('');
								getList(_name,_sorts);
							}else{
								$(".easy_modal p").html(r.Message);
								$(".easy_modal").fadeIn();
							}
						}
					});
				}else{
					$(".easy_modal p").html("请填写原因");
					$(".easy_modal").fadeIn();
				}
			});
			$("#close").unbind("click").click(function(){
				$("#modal").fadeOut();
			})
		});
		
		$(".delete_teacher").click(function(){
			var _this = $(this);
			$("#delete_teacher_modal").fadeIn();
			$(".modal_action").fadeOut();
			$("#modal").fadeOut();
			$(".easy_modal").fadeOut();
			$("#delete_teacher_modal input").eq(0).unbind("click").click(function(){
				$.ajax({
					type:"post",
					url:"../admin/teacherDelete",
					async:true,
					data:{
						id:_this.attr('data-id')
					},
					success:function(r){
						$(".easy_modal").fadeIn().find('p').html(r.Message);
						$("#delete_teacher_modal").fadeOut();
						getList(_name,_sorts);
					}
				});
			});
		});
		
		
		$("#delete_teacher_modal input").eq(1).click(function(){
			$("#delete_teacher_modal").fadeOut();
		});
		
	});
}
