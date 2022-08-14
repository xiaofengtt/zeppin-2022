var pageNum = '1';
var pagesize;
var count;
var flag = true;
var _name = '';
var _sorts = '';

//批量审核数组
var assessNum = [];
var assessNumStr;

$(".main_header").on("click", "a", function() {
	$(this).addClass("lighting").siblings().removeClass("lighting");
	pageNum = '1';
	flag = true;
	getList(_name, _sorts);
});
//导出列表
$("#download").click(function() {
	$(".easy_modal").fadeOut();
	$("#modal").fadeOut();
	var checkStatus = $(".filter-checkStatus").find(".lighting").attr("data-value");
	var type = $(".filter-type").find(".lighting").attr("data-value");
	var status = $(".filter-status").find(".lighting").attr("data-value");
	var isChief = $(".filter-isChief").find(".lighting").attr("data-value");
	var isMixed = $(".filter-isMixed").find(".lighting").attr("data-value");
	var studyLength = $(".filter-studyLength").find(".lighting").attr("data-value");
	var studyGrade = $(".filter-studyGrade").find(".lighting").attr("data-value");
	var downLoadUrl = "../admin/teacherDownloadList?checkStatus=" + checkStatus + "&type=" + type + "&status=" + status + "&isChief=" + isChief + "&isMixed=" + isMixed + "&name=" + $(".search").val() + "&sorts=" + _sorts;
	if(type == 2){
		downLoadUrl += '&studyLength=' + studyLength;
		downLoadUrl += '&studyGrade=' + studyGrade;
	}
	$("#download").prop("href", downLoadUrl);
});

function searchBtn() {
	_name = $(".search").val();
	pageNum = '1';
	flag = true;
	getList(_name, _sorts);
	return false;
};
$(document).ready(function() {
	getList();

	//关闭简单的提示框
	$(".easy_modal input").eq(0).click(function() {
		$(".easy_modal").fadeOut(function() {
			$(this).css({
				"height": "100px"
			});
		});
	});

	var year = new Date().getFullYear();
	for(var i = 0; i < 4; i++) {
		$(".filter-studyGrade").append("<a data-value='" + year + "级'>" + year + "级</a>");
		year--;
	}
	$(".filter-studyGrade").append("<a data-value='other'>其他</a>");

	function sort(sort) {
		getList(_name, sort);
		flag = false;
	};

	//排序
	var DorA_1 = true;
	var DorA_2 = true;
	var DorA_3 = true;
	var DorA_4 = true;

	$(".select_bar_left li").eq(0).click(function() {
		DorA_2 = true;
		DorA_3 = true;
		DorA_4 = true;

		if(DorA_1 == true) {
			$(this).find('a').addClass('light');
			$(this).find('img').attr('src', '../img/toptop_r_14.png');
			$(this).siblings().find('a').removeClass('light');
			$(this).siblings().find('img').attr('src', '../img/toptop_b_14.png');
			_sorts = "pinyin_desc";
			sort("pinyin-desc");
			DorA_1 = false;
		} else {
			$(this).find('img').attr('src', '../img/bottombottom_r_14.png');
			_sorts = "pinyin-asc";
			sort("pinyin-asc");
			DorA_1 = true;
		}

	});

	$(".select_bar_left li").eq(1).click(function() {
		DorA_1 = true;
		DorA_3 = true;
		DorA_4 = true;
		if(DorA_2 == true) {
			$(this).find('a').addClass('light');
			$(this).find('img').attr('src', '../img/toptop_r_14.png');
			$(this).siblings().find('a').removeClass('light');
			$(this).siblings().find('img').attr('src', '../img/toptop_b_14.png');
			_sorts = "sex-desc";
			sort("sex-desc");
			DorA_2 = false;
		} else {
			$(this).find('img').attr('src', '../img/bottombottom_r_14.png');
			_sorts = "sex-asc";
			sort("sex-asc");
			DorA_2 = true;
		}

	});

	$(".select_bar_left li").eq(3).click(function() {
		DorA_1 = true;
		DorA_2 = true;
		DorA_4 = true;
		if(DorA_3 == true) {
			$(this).find('a').addClass('light');
			$(this).find('img').attr('src', '../img/toptop_r_14.png');
			$(this).siblings().find('a').removeClass('light');
			$(this).siblings().find('img').attr('src', '../img/toptop_b_14.png');
			_sorts = "createtime-desc";
			sort("createtime-desc");
			DorA_3 = false;
		} else {
			$(this).find('img').attr('src', '../img/bottombottom_r_14.png');
			_sorts = "createtime-asc";
			sort("createtime-asc");
			DorA_3 = true;
		}
	});
	$(".select_bar_left li").eq(2).click(function() {
		DorA_1 = true;
		DorA_2 = true;
		DorA_3 = true;
		if(DorA_4 == true) {
			$(this).find('a').addClass('light');
			$(this).find('img').attr('src', '../img/toptop_r_14.png');
			$(this).siblings().find('a').removeClass('light');
			$(this).siblings().find('img').attr('src', '../img/toptop_b_14.png');
			_sorts = "checkStatus-desc";
			sort("checkStatus-desc");
			DorA_4 = false;
		} else {
			$(this).find('img').attr('src', '../img/bottombottom_r_14.png');
			_sorts = "checkStatus_asc";
			sort("checkStatus-asc");
			DorA_4 = true;
		}
	});

	//排序结束

})

function getList(name, sorts) {
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
	var url = '../admin/teacherList?';
	url += 'pagenum=' + page;
	url += '&pagesize=' + pagesize;
	if(teacherinfo != '') {
		url += '&name=' + teacherinfo;
	}
	if(sort != '') {
		url += '&sorts=' + sort;
	}

	var checkStatus = $(".filter-checkStatus").find(".lighting").attr("data-value");
	var type = $(".filter-type").find(".lighting").attr("data-value");
	var status = $(".filter-status").find(".lighting").attr("data-value");
	var isChief = $(".filter-isChief").find(".lighting").attr("data-value");
	var isMixed = $(".filter-isMixed").find(".lighting").attr("data-value");
	var studyLength = $(".filter-studyLength").find(".lighting").attr("data-value");
	var studyGrade = $(".filter-studyGrade").find(".lighting").attr("data-value");
	var photoStatus = $(".filter-photo").find(".lighting").attr("data-value");
	

	url += '&checkStatus=' + checkStatus;
	url += '&type=' + type;
	url += '&status=' + status;
	url += '&isChief=' + isChief;
	url += '&isMixed=' + isMixed;
	url += '&photo=' + photoStatus;
	
	if(type == 2 || type == 4) {
		url += '&studyLength=' + studyLength;
		url += '&studyGrade=' + studyGrade;
	}
	$.get(url, function(r) {
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
		$(".unauditing").unbind("change").change(function() {
			$(this).parent().siblings().find(".unauditing").prop("checked", false);
			if($(this).is(":checked") == true) {
				$("#reason").val($(this).val());
			}
		})
		$("#reason").bind("input", function() {
			$(".unauditing").prop("checked", false);
		});
		$(".unauditing_").unbind("change").change(function() {
			$(this).parent().siblings().find(".unauditing_").prop("checked", false);
			if($(this).is(":checked") == true) {
				$("#reason_").val($(this).val());
			}
		})
		$("#reason_").bind("input", function() {
			$(".unauditing_").prop("checked", false);
		});

		$(".ban").unbind("click").click(function() {
			var _this = $(this);
			$("#reason").val('');
			$("#modal").fadeIn();
			$(".easy_modal").fadeOut(function() {
				$(this).css({
					"height": "100px"
				});
			});
			$("#submit").unbind("click").click(function() {
				if($("#modal textarea").val() != '') {
					$.ajax({
						type: "post",
						url: "../admin/teacherChange",
						async: true,
						data: {
							id: _this.find("input").attr("id"),
							status: 0,
							reason: $("#modal textarea").val()
						},
						success: function(r) {
							if(r.Status == "success") {
								$(".easy_modal p").html(r.Message);
								$(".easy_modal").fadeIn();
								$(".bg").fadeOut();
								$("#modal").fadeOut();
								$(".unauditing").prop("checked", false);
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
		});
		$("#close").click(function() {
			$("#modal").fadeOut();
			$(".unauditing").prop("checked", false);
		})
		$("#close_").click(function() {
			$("#modal_").fadeOut();
			$(".unauditing_").prop("checked", false);
		})

		$(".click_card").unbind("click").click(function() {
			var this_index = $(this).siblings("input").val();
			if(r.Records[this_index].idcardPhoto.id == 1) {
				$(".easy_modal").fadeIn().find("p").html("该教师未上传身份证图片");
			} else {
				$(".easy_modal").find("p").html("<img class='card_img' src=" + r.Records[this_index].idcardPhoto.sourcePath + " />")
			}
			$(".easy_modal img").load(function() {
				$(".easy_modal").fadeIn();
				$(".easy_modal").css({
					"height": "100px"
				});
				$(".easy_modal").css({
					"height": $(".easy_modal").height() + $(".card_img").height()
				});

			})
		});
		$("#unauditing_all").click(function(){
			if(assessNum.length != 0){
				$("#modal_").fadeIn();
				$(".easy_modal").fadeOut();
				$("#reason_").val('');	
			}else{
				$(".easy_modal p").html("请勾选教师");
				$(".easy_modal").fadeIn();
				return false;
			}
			
			$("#submit_").unbind("click").click(function() {
				if($("#modal_ textarea").val() != '') {
					$.ajax({
						type: "post",
						url: "../admin/teacherChange",
						async: true,
						data: {
							id: assessNumStr,
							status: 0,
							reason: $("#modal_ textarea").val()
						},
						success: function(r) {
							if(r.Status == "success") {
								$(".easy_modal p").html(r.Message);
								$(".easy_modal").fadeIn();
								$("#modal_").fadeOut();
								$(".unauditing_").prop("checked", false);
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
			
		});
		//批量审核
		$("#auditing_all").click(function() {
			$("#modal").fadeOut();

			if(assessNum.length != 0) {
				$.ajax({
					type: "post",
					url: "../admin/teacherChange",
					async: true,
					data: {
						id: assessNumStr,
						status:2
					},
					success: function(r) {
						if(r.Status == "success") {
							$(".easy_modal p").html(r.Message);
							$(".easy_modal").fadeIn();
							assessNum = [];
							getList(_name, _sorts);
						} else {
							$(".easy_modal p").html(r.Message);
							$(".easy_modal").fadeIn();
						}
					}
				});
			} else {
				$(".easy_modal p").html("请填写勾选教师");
				$(".easy_modal").fadeIn();
			}
		});

		//批量停用
		$(".checkbox").unbind("click").click(function() {
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
			$("#modal").fadeOut();
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

//审核通过
function change(id, status) {
	$(".easy_modal").fadeOut();
	$("#modal").fadeOut();
	$(".unauditing").prop("checked", false);
	$.ajax({
		type: "post",
		url: "../admin/teacherChange",
		async: true,
		data: {
			"id": id,
			"status": status,
			"reason": ""
		},
		success: function(data) {
			if(data.Status == 'success') {
				$(".easy_modal p").html(data.Message);
				$(".easy_modal").fadeIn();
				getList(_name, _sorts);
			} else {
				$(".easy_modal p").html(data.Message);
				$(".easy_modal").fadeIn();
			}
		}
	});
}