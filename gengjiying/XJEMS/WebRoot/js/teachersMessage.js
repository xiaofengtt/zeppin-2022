var pageNum = '1';
var pagesize;
var count;
var flag = true;
var exam = "";
var _sorts;
var _name;
var loadData="";
//批量停用数组
var assessNum = [];
var assessNumStr;

$(document).ready(function() {
	//	getList();
	getCurrent();

	$(".easy_modal input").eq(0).click(function() {
		$(".easy_modal").fadeOut();
	})

	var year = new Date().getFullYear();
	for(var i = 0; i < 4; i++) {
		$(".filter-studyGrade").append("<a data-value='" + year + "级'>" + year + "级</a>");
		year--;
	}
	$(".filter-studyGrade").append("<a data-value='other'>其他</a>");
})

function searchBtn() {
	_name = $(".search").val();
	pageNum = '1';
	flag = true;
	getList(_name, _sorts);
	return false;
}

function sort(sort) {
	_name = $(".search").val();
	getList(_name, sort);
}

$("#apply").click(function(){
	$(this).attr("disabled",true);
	var startTime = Date.parse(new Date($("#startTime").val()));
	var endTime = Date.parse(new Date($("#endTime").val()));
	if(startTime > endTime){
		$(".easy_modal").find("p").html("开始日期不能大于结束日期");
		$(".easy_modal").fadeIn();
		$(this).attr("disabled",false);
		return false;
	}

	apply();
})

//排序
var DorA_1 = true;
var DorA_2 = true;
var DorA_3 = true;
var DorA_4 = true;

$(".select_bar_left li").eq(0).unbind("click").click(function() {
	DorA_2 = true;
	DorA_3 = true;
	if(DorA_1 == true) {
		$(this).find('a').addClass('light');
		$(this).find('img').attr('src', '../img/toptop_r_14.png');
		$(this).siblings().find('a').removeClass('light');
		$(this).siblings().find('img').attr('src', '../img/toptop_b_14.png');
		_sorts = "pinyin-desc";
		sort("pinyin-desc");
		DorA_1 = false;
	} else {
		$(this).find('img').attr('src', '../img/bottombottom_r_14.png');
		_sorts = "pinyin-asc";
		sort("pinyin-asc");

		DorA_1 = true;
	}

});

$(".select_bar_left li").eq(1).unbind("click").click(function() {
	DorA_1 = true;
	DorA_3 = true;
	if(DorA_2 == true) {
		$(this).find('a').addClass('light');
		$(this).find('img').attr('src', '../img/toptop_r_14.png');
		$(this).siblings().find('a').removeClass('light');
		$(this).siblings().find('img').attr('src', '../img/toptop_b_14.png');
		sort("sex-desc");
		_sorts = "sex-desc"
		DorA_2 = false;
	} else {
		$(this).find('img').attr('src', '../img/bottombottom_r_14.png');
		sort("sex-asc");
		_sorts = "sex-asc";
		DorA_2 = true;
	}

});

$(".select_bar_left li").eq(2).unbind("click").click(function() {
	DorA_1 = true;
	DorA_2 = true;

	if(DorA_3 == true) {
		$(this).find('a').addClass('light');
		$(this).find('img').attr('src', '../img/toptop_r_14.png');
		$(this).siblings().find('a').removeClass('light');
		$(this).siblings().find('img').attr('src', '../img/toptop_b_14.png');
		sort("createtime-desc");
		_sorts = "createtime-desc";
		DorA_3 = false;
	} else {
		$(this).find('img').attr('src', '../img/bottombottom_r_14.png');
		sort("createtime-asc");
		_sorts = "createtime-asc";
		DorA_3 = true;
	}
});

//排序结束

//$(".main_header a").click(function() {
//	
//})
$(".main_header").on("click", "a", function() {
	$(this).addClass("lighting").siblings().removeClass("lighting");
	pageNum = '1';
	flag = true;
	getList(_name, _sorts);
});

$("#studyGrade").change(function() {
	pageNum = '1';
	flag = true;
	getList(_name, _sorts);
})

$("#working").click(function() {
	updateList();
})

//导出
$("#load-list").click(function(){
	$("#bg").show();
	$.ajax({
		type:"get",
		url:"../admin/teacherDownloadTeacherApplyList",
		async:true,
		data:loadData,
		beforeSend:function(){
			$("#bg").show();
		}
	})
	.done(function(){
		$("#bg").hide();
		window.location.href="../admin/teacherDownloadTeacherApplyList?"+loadData;
	});
})


//$(".filter-type a").click(function() {
//	if ($(".main_header").height() > 85) {
//		if($(".filter-type").find(".lighting").attr("data-value")==2){
//			$(".main_header").css({
//				"height" : "220px"
//			});
//		}else{
//			$(".main_header").css({
//				"height" : "150px"
//			});
//		}
//	}
//
//})

//申请监考
function apply() {
	$.ajax({
		type: "post",
		url: "../admin/recordsAdd",
		async: true,
		data: {
			"teacher": $("#applyTeacher").val(),
			"exam": exam,
			"startTime":$("#startTime").val(),
			"endTime":$("#endTime").val()
		},
		success: function(data) {
			if(data.Status == 'success') {
				$(".easy_modal p").html(data.Message);
				$(".easy_modal").fadeIn();
				$("#apply_modal").fadeOut();
				getList(_name, _sorts);
				$("#startTime").val("");
				$("#endTime").val("");
			} else {
				$(".easy_modal p").html(data.Message);
				$(".easy_modal").fadeIn();
			}
			$("#apply").attr("disabled",false);
		},
		fail: function() {
			$(".easy_modal p").html("请求错误，请稍后重试");
			$(".easy_modal").fadeIn();
			$("#apply").attr("disabled",false);
		}
	});
}


//获取当前考试信息
function getCurrent() {
	var mUrl = '../admin/examGetCurrent?';
	$.get(mUrl, function(r) {
		if(r.Status == 'success') {
			exam = r.Records.id;
			laydate({
				elem: '#startTime',
				min:r.Records.starttime,
				max:r.Records.endtime,
				start:r.Records.starttime
			});
			laydate({
				elem: '#endTime',
				min:r.Records.starttime,
				max:r.Records.endtime,
				start:r.Records.starttime
			});
			
		} else {
			$(".easy_modal p").html(r.Message);
			$(".easy_modal").fadeIn();
		}
		getList(_name, _sorts);
	}).done(function(r) {});
}

//禁用结果
function updateList() {
	var mUrl = '../admin/teacherUpdateList?';
	var type = $(".filter-type").find(".lighting").attr("data-value");
	var status = $(".filter-status").find(".lighting").attr("data-value");
	var isChief = $(".filter-isChief").find(".lighting").attr("data-value");
	var isMixed = $(".filter-isMixed").find(".lighting").attr("data-value");
	var studyLength = $(".filter-studyLength").find(".lighting").attr("data-value");
	mUrl += 'type=' + type;
	mUrl += '&status=' + status;
	mUrl += '&isChief=' + isChief;
	mUrl += '&isMixed=' + isMixed;
	if(type == 2) {
		mUrl += '&studyLength=' + studyLength;
		mUrl += '&studyGrade=' + $("#studyGrade").val();
	}
	$.get(mUrl, function(r) {
		if(r.Status == 'success') {
			alert("此处样式问题");
			//			window.location.reload();
		}
	})
}

//获取监考教师信息列表
function getList(name, sorts) {
	loadData = "";
	var teacherinfo = '';
	if(typeof(name) != "undefined") {
		teacherinfo = name;
	}
	var sort = '';
	if(typeof(sorts) != "undefined") {
		sort = sorts;
	}
	var page = (typeof pageNum == 'undefined') ? 1 : pageNum;
	var pagesize = (typeof pagesize == 'undefined') ? 10 : pagesize;
	var mUrl = '../admin/teacherList?';
	mUrl += 'pagenum=' + page;
	mUrl += '&pagesize=' + pagesize;
	mUrl += '&exam=' + exam;
	mUrl += '&checkStatus=2';
	loadData += '&exam=' + exam;
	loadData += '&checkStatus=2';
	if(teacherinfo != '') {
		mUrl += '&name=' + teacherinfo;
		loadData += 'name=' + teacherinfo;
	}
	if(sort != '') {
		mUrl += '&sorts=' + sort;
		loadData += '&sorts=' + sort;
	}

	var type = $(".filter-type").find(".lighting").attr("data-value");
	var status = $(".filter-status").find(".lighting").attr("data-value");
	var isChief = $(".filter-isChief").find(".lighting").attr("data-value");
	var isMixed = $(".filter-isMixed").find(".lighting").attr("data-value");
	var studyLength = $(".filter-studyLength").find(".lighting").attr("data-value");
	var studyGrade = $(".filter-studyGrade").find(".lighting").attr("data-value");

	mUrl += '&type=' + type;
	mUrl += '&status=' + status;
	mUrl += '&isChief=' + isChief;
	mUrl += '&isMixed=' + isMixed;
	loadData += '&type=' + type;
	loadData += '&status=' + status;
	loadData += '&isChief=' + isChief;
	loadData += '&isMixed=' + isMixed;
	if(type == 2) {
		mUrl += '&studyLength=' + studyLength;
		mUrl += '&studyGrade=' + studyGrade;
		loadData += '&studyLength=' + studyLength;
		loadData += '&studyGrade=' + studyGrade;
	}
	 
	$.get(mUrl, function(r) {
		if(r.Status == 'success') {
			if(r.TotalRecordCount > 0) {
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
		if(flag) {
			$('#select_page').Paging({
				pagesize: r.PageSize,
				count: r.TotalRecordCount,
				callback: function(page, size, count) {
					pageNum = page;
					getList(_name, _sorts);
					flag = false;
				}
			});
			$("#select_page").find(".ui-paging-container:last").siblings().remove();
		}
		$(".action").click(function() {
			assessNum = [];
			$(".checkbox").prop("checked",false);
			var _this = $(this);
			$(".modal_action").fadeIn();
			$(".easy_modal").fadeOut();
			$("#modal").fadeOut();
			$("#delete_teacher_modal").fadeOut();
			$(".modal_action .button_g input").eq(0).unbind("click").click(function() {
				$("#action_id").attr("value", _this.attr("data-id"));
				$.ajax({
					type: "post",
					url: "../admin/teacherResume",
					async: true,
					data: {
						"id": $("#action_id").val(),
						'exam': exam
					},
					success: function(r) {
						if(r.Status = "success") {
							$(".easy_modal p").html(r.Message);
							$(".easy_modal").fadeIn();
							$(".modal_action").fadeOut();
							getList(_name, _sorts);
						} else {
							$(".easy_modal p").html(r.Message);
							$(".easy_modal").fadeIn();

						}
					}
				});
			})
		})

		$(".apply").unbind("click").click(function(){
			$("#apply_modal").fadeIn();
			$("#modal_").fadeOut();
			$("#modal").fadeOut();
			$(".easy_modal").fadeOut();
			$(".modal_action").fadeOut();
			$("#applyTeacher").val($(this).attr("data-id"));
		});
		
		$("#apply_modal input[type='button']:nth-of-type(2)").click(function(){
			$("#apply_modal").fadeOut();
			$("#startTime").val("");
			$("#endTime").val("");
		});
		
		$(".modal_action .button_g input").eq(1).click(function() {
			$(".modal_action").fadeOut();
		})
		
		//批量停用
		$("#delete_all").click(function() {
			$(".modal_action").fadeOut();
			$(".easy_modal").fadeOut();
			$("#delete_teacher_modal").fadeOut();
			
			if(assessNum.length != 0) {
				$("#reason_").val('');
				$("#modal_").fadeIn();
				$(".time_check_").unbind("change").change(function() {
					$(this).prop("disabled", true);
					$(this).siblings('.time_check').prop("disabled", false);
					$(this).siblings('.time_check').prop("checked", false);
					$("#time_value_").val($(this).val());
				})
				$("#submit_").unbind("click").click(function() {
					if($("#reason_").val() != "") {
						$.ajax({
							type: "post",
							url: "../admin/recordsDisable",
							async: true,
							data: {
								exam: exam,
								id: assessNumStr,
								reason: $("#reason_").val(),
								disableType: $("#time_value_").val()
							},
							success: function(r) {
								if(r.Status == "success") {
									$(".easy_modal p").html(r.Message);
									$(".easy_modal").fadeIn();
									$(".time_check_").prop("disabled", false).prop("checked", false);
									$(".time_check_").eq(0).prop("disabled", true).prop("checked", true);
									$("#time_value_").val('2');
									$("#modal_").fadeOut();
									$("#reason_").val('');
									assessNum = [];
									getList(_name, _sorts);
								} else {
									$(".easy_modal p").html(r.Message);
									$(".easy_modal").fadeIn();
								}
							}
						});
					} else {
						$(".easy_modal p").html("请填写原因");
						$(".easy_modal").fadeIn();
					}
				});
				$("#close_").unbind("click").click(function() {
					$("#modal_").fadeOut();
				})
			} else {
				$(".easy_modal p").html("请勾选教师");
				$(".easy_modal").fadeIn();
			}
		})
		
		
		//单独停用
		$(".delete").click(function() {
			assessNum = [];
			var _this = $(this);
			//			$("#teacherId").attr("value", _this.attr("data-id"));
			$(".checkbox").prop("checked", false);
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
			$("#submit").unbind("click").click(function() {
				if($("#reason").val() != "") {
					$.ajax({
						type: "post",
						url: "../admin/recordsDisable",
						async: true,
						data: {
							exam: exam,
							id: _this.attr("data-id"),
							reason: $("#reason").val(),
							disableType: $("#time_value").val()
						},
						success: function(r) {
							if(r.Status == "success") {
								$(".easy_modal p").html(r.Message);
								$(".easy_modal").fadeIn();
								$(".time_check").prop("disabled", false).prop("checked", false);
								$(".time_check").eq(0).prop("disabled", true).prop("checked", true);
								$("#time_value").val('2');
								$("#modal").fadeOut();
								$("#reason").val('');
								getList(_name, _sorts);
							} else {
								$(".easy_modal p").html(r.Message);
								$(".easy_modal").fadeIn();
							}
						}
					});
				} else {
					$(".easy_modal p").html("请填写原因");
					$(".easy_modal").fadeIn();
				}
			});
			$("#close").unbind("click").click(function() {
				$("#modal").fadeOut();
			})
		});

		$(".delete_teacher").click(function() {
			assessNum = [];
			$(".checkbox").prop("checked",false);
			var _this = $(this);
			$("#delete_teacher_modal").fadeIn();
			$(".modal_action").fadeOut();
			$("#modal").fadeOut();
			$(".easy_modal").fadeOut();
			$("#delete_teacher_modal input").eq(0).unbind("click").click(function() {
				$.ajax({
					type: "post",
					url: "../admin/teacherDelete",
					async: true,
					data: {
						id: _this.attr('data-id')
					},
					success: function(r) {
						$(".easy_modal").fadeIn().find('p').html(r.Message);
						$("#delete_teacher_modal").fadeOut();
						assessNum = [];
						getList(_name, _sorts);
					}
				});
			});
		});

		$("#delete_teacher_modal input").eq(1).click(function() {
			$("#delete_teacher_modal").fadeOut();
		});

		//批量停用
		$(".checkbox").unbind("click").click(function() {
			$("#delete_teacher_modal").fadeOut();
			$(".modal_action").fadeOut();
			$("#modal").fadeOut();
			$(".easy_modal").fadeOut();
			var thisId = $(this).attr('data-id');
			if($(this).is(':checked')) {
				assessNum.push(thisId);
				assessNumStr = assessNum.join(',');
				console.log(assessNumStr);
			} else {
				if(assessNum.indexOf(thisId) != -1) {
					assessNum.splice(assessNum.indexOf(thisId), 1);
					assessNumStr = assessNum.join(',');
					console.log(assessNumStr);
				}

			}
		});
		$("#select_all").unbind("click").click(function() {
			assessNum = [];
			assessNumStr = '';
			for(var i = 0; i < $(".checkbox").length; i++) {
				$(".checkbox").eq(i).prop('checked', true);
				assessNum.push($(".checkbox").eq(i).attr('data-id'));
				assessNumStr = assessNum.join(',');
				console.log(assessNumStr);
			}
		})

	});
}