var pageNum = '1';
var pagesize;
var count;
var flag = true;
var _name;
var _sorts;
exam = "";

$.ajax({
    type: "get",
    url: "../admin/examGetCurrent",
    async: true,
    success: function(r) {
        $(".title").html(r.Records.name);
        exam = r.Records.id;
        laydate({
				elem: '#startTime',
				min:r.Records.starttime,
				max:r.Records.endtime,
				start:r.Records.starttime,
				choose:function(){
					if($("#startTime").val() != "" && $("#endTime").val() != ""){
						getList(_name, _sorts);
					}
				}
			});
			laydate({
				elem: '#endTime',
				min:r.Records.starttime,
				max:r.Records.endtime,
				start:r.Records.starttime,
				choose:function(){
					if($("#startTime").val() != "" && $("#endTime").val() != ""){
						getList(_name, _sorts);
					}
				}
			});
    }
});
$(".main_header a").click(function() {
    $(this).addClass("lighting").siblings().removeClass("lighting");
    pageNum = '1';
    flag = true;
    getList(_name, _sorts);
})
$(document).ready(function() {
    getList();
    $(".easy_modal input").eq(0).click(function() {
        $(".easy_modal").fadeOut();
    });
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

    $(".sort_th").eq(0).click(function() {
        sort_flag_1 = true;
        sort_flag_2 = true;
        sort_flag_3 = true;
        sort_flag_4 = true;
        sort_flag_5 = true;
        sort_flag_6 = true;
        sort_flag_7 = true;
        sort_flag_8 = true;
        if (sort_flag_0 == true) {
            $(this).addClass('light');
            $(this).find('img').attr('src', '../img/toptop_r_14.png');
            $(this).siblings().removeClass('light');
            $(this).siblings().find('img').attr('src', '');
            sort("name-desc");
            _sorts = "name_desc";
            sort_flag_0 = false;
        } else {
            $(this).find('img').attr('src', '../img/bottombottom_r_14.png');
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
        if (sort_flag_1 == true) {
            $(this).addClass('light');
            $(this).find('img').attr('src', '../img/toptop_r_14.png');
            $(this).siblings().removeClass('light');
            $(this).siblings().find('img').attr('src', '');
            sort("mobile-desc");
            _sorts = "mobile-desc";
            sort_flag_1 = false;
        } else {
            $(this).find('img').attr('src', '../img/bottombottom_r_14.png');
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
        if (sort_flag_2 == true) {
            $(this).addClass('light');
            $(this).find('img').attr('src', '../img/toptop_r_14.png');
            $(this).siblings().removeClass('light');
            $(this).siblings().find('img').attr('src', '');
            sort("ethnic-desc");
            _sort = "ethnic-desc";
            sort_flag_2 = false;
        } else {
            $(this).find('img').attr('src', '../img/bottombottom_r_14.png');
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
        if (sort_flag_3 == true) {
            $(this).addClass('light');
            $(this).find('img').attr('src', '../img/toptop_r_14.png');
            $(this).siblings().removeClass('light');
            $(this).siblings().find('img').attr('src', '');
            sort("sex-desc");
            _sorts = "sex-desc";
            sort_flag_3 = false;
        } else {
            $(this).find('img').attr('src', '../img/bottombottom_r_14.png');
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
        if (sort_flag_4 == true) {
            $(this).addClass('light');
            $(this).find('img').attr('src', '../img/toptop_r_14.png');
            $(this).siblings().removeClass('light');
            $(this).siblings().find('img').attr('src', '');
            sort("type-desc");
            _sorts = "type-desc";
            sort_flag_4 = false;
        } else {
            $(this).find('img').attr('src', '../img/bottombottom_r_14.png');
            sort("type-asc");
            _sorts = "type-asc";
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
        if (sort_flag_5 == true) {
            $(this).addClass('light');
            $(this).find('img').attr('src', '../img/toptop_r_14.png');
            $(this).siblings().removeClass('light');
            $(this).siblings().find('img').attr('src', '');
            sort("isChief0-desc");
            _sorts = "isChief0-desc";
            sort_flag_5 = false;
        } else {
            $(this).find('img').attr('src', '../img/bottombottom_r_14.png');
            sort("isChief0-asc");
            _sorts = "isChief0-asc";
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
        if (sort_flag_6 == true) {
            $(this).addClass('light');
            $(this).find('img').attr('src', '../img/toptop_r_14.png');
            $(this).siblings().removeClass('light');
            $(this).siblings().find('img').attr('src', '');
            sort("isConfirm0-desc");
            _sorts = "isConfirm0-desc";
            sort_flag_6 = false;
        } else {
            $(this).find('img').attr('src', '../img/bottombottom_r_14.png');
            sort("isConfirm0-asc");
            _sorts = "isConfirm0-asc";
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
        if (sort_flag_7 == true) {
            $(this).addClass('light');
            $(this).find('img').attr('src', '../img/toptop_r_14.png');
            $(this).siblings().removeClass('light');
            $(this).siblings().find('img').attr('src', '');
            sort("applytime0-desc");
            _sorts = "applytime0-desc";
            sort_flag_7 = false;
        } else {
            $(this).find('img').attr('src', '../img/bottombottom_r_14.png');
            sort("applytime0-asc");
            _sorts = "applytime0-asc";
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
        if (sort_flag_8 == true) {
            $(this).addClass('light');
            $(this).find('img').attr('src', '../img/toptop_r_14.png');
            $(this).siblings().removeClass('light');
            $(this).siblings().find('img').attr('src', '');
            sort("status0-desc");
            _sorts = "status0-desc";
            sort_flag_8 = false;
        } else {
            $(this).find('img').attr('src', '../img/bottombottom_r_14.png');
            sort("status0-asc");
            _sorts = "status0-asc";
            sort_flag_8 = true;
        }
    });
})

function searchBtn() {
    _name = $(".search").val();
    pageNum = '1';
    flag = true;
    getList(_name);
    return false;
}

function sort(sort) {
    getList(_name, sort);
}

function getList(name, sorts) {
    var exam = url('?exam') == null ? '' : url('?exam');
    var teacherinfo = '';
    if (typeof(name) != "undefined") {
        teacherinfo = name;
    }
    var sort = '';
    if (typeof(sorts) != "undefined") {
        sort = sorts;
    }
    var page = (typeof pageNum == 'undefined') ? 1 : pageNum;
    var pagesize = (typeof pagesize == 'undefined') ? 50 : pagesize;
    var mUrl = '../admin/recordsConfirmList?';
    mUrl += 'pagenum=' + page;
    mUrl += '&pagesize=' + pagesize;
    mUrl += '&exam=' + exam;
    if($("#startTime").val() != "" && $("#endTime").val() != ""){
		mUrl += "&starttime=" + $("#startTime").val();
		mUrl += "&endtime=" + $("#endTime").val();
	}
    
    if (teacherinfo != '') {
        mUrl += '&teacherinfo=' + teacherinfo;
    }
    if (sort != '') {
        mUrl += '&sorts=' + sort;
    }
    var isconfirm = $(".filter-isconfirm").find(".lighting").attr("data-value");
    var type = $(".filter-type").find(".lighting").attr("data-value");
    var isChief = $(".filter-isChief").find(".lighting").attr("data-value");

    mUrl += '&type=' + type;
    mUrl += '&isChief=' + isChief;
    mUrl += '&isconfirm=' + isconfirm;

    $.get(mUrl, function(r) {
        if (r.Status == 'success') {
            if (r.TotalRecordCount > 0) {
                var template = $.templates('#queboxTpl');
                var html = template.render(r.Records);
                $('#queboxCnt').html(html);
                pagesize = r.pageSize;
                count = r.totalResultCount;
            } else {
                var html = "<tr><td colspan='15'>没有数据！</td></tr>"
                $('#queboxCnt').html(html);
            }
        } else {
            //			layer.msg(r.message, {
            //				time : 2000
            //			})
        }
    }).done(function(r) {
        if (flag) {
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


        //确认

        $(".assess").unbind("click").click(function() {
            var _this = $(this);
            $(".easy_modal").fadeOut();
            $(".modal_delete_all").fadeOut();
            $(".modal_disable_all").fadeOut();
            $(".modal_unAssess").fadeOut();
            $(".modal_assess").fadeIn();
            $("#sub_assess").unbind("click").click(function() {
                $.ajax({
                    type: "post",
                    url: "../admin/recordsConfirm",
                    async: true,
                    data: {
                        id: _this.siblings('input').attr('id')
                    },
                    success: function(data) {
                        if (data.Status == 'success') {
                            $(".modal_assess").fadeOut();
                            $(".easy_modal p").html(data.Message);
                            $(".easy_modal").fadeIn();
                            getList();
                        } else {
                            $(".modal_assess").fadeOut();
                            $(".easy_modal p").html(data.Message);
                            $(".easy_modal").fadeIn();
                        }
                    },
                    fail: function() {
                        $(".modal_assess").fadeOut();
                        $(".easy_modal p").html(data.Message);
                        $(".easy_modal").fadeIn();

                    }
                })
            });
            ////
            $("#close_assess").unbind("click").click(function() {
                $(".modal_assess").fadeOut();
            });
        });


		//取消二次确认
		$(".unAssess").unbind("click").click(function() {
            var _this = $(this);
            $(".easy_modal").fadeOut();
            $(".modal_delete_all").fadeOut();
            $(".modal_disable_all").fadeOut();
            $(".modal_assess").fadeOut();
            $(".modal_unAssess").fadeIn();
            $("#sub_unAssess").unbind("click").click(function() {
                $.ajax({
                    type: "post",
                    url: "../admin/recordsCancelConfirm",
                    async: true,
                    data: {
                        id: _this.siblings('input').attr('id')
                    },
                    success: function(data) {
                        if (data.Status == 'success') {
                            $(".modal_unAssess").fadeOut();
                            $(".easy_modal p").html(data.Message);
                            $(".easy_modal").fadeIn();
                            getList();
                        } else {
                            $(".modal_unAssess").fadeOut();
                            $(".easy_modal p").html(data.Message);
                            $(".easy_modal").fadeIn();
                        }
                    },
                    fail: function() {
                        $(".modal_unAssess").fadeOut();
                        $(".easy_modal p").html(data.Message);
                        $(".easy_modal").fadeIn();

                    }
                })
            });
            $("#close_unAssess").unbind("click").click(function(){
				 $(".modal_unAssess").fadeOut();
            });
		});

		
        //批量删除
        var assessNum = [];
        var assessNumStr;

        $(".checkbox").unbind("click").click(function() {
            var thisId = $(this).siblings('input').attr('id');
            if ($(this).is(':checked')) {
                assessNum.push(thisId);
                assessNumStr = assessNum.join(',');
            } else {
                if (assessNum.indexOf(thisId) != -1) {
                    assessNum.splice(assessNum.indexOf(thisId), 1);
                    assessNumStr = assessNum.join(',');

                }

            }
        });
        $("#select_all").unbind("click").click(function() {
            assessNum = [];
            assessNumStr = '';
            for (var i = 0; i < $(".checkbox").length; i++) {
                $(".checkbox").eq(i).prop('checked', true);
                assessNum.push($(".checkbox").eq(i).siblings('input').attr('id'));
                assessNumStr = assessNum.join(',');
                console.log(assessNumStr);
            }
        })
        $("#unselect_all").unbind("click").click(function() {
            for (var i = 0; i < $(".checkbox").length; i++) {
                var checkboxId = $(".checkbox").eq(i).siblings('input').attr('id');
                if ($(".checkbox").eq(i).is(':checked')) {
                    $(".checkbox").eq(i).prop('checked', false);
                    assessNum.splice(assessNum.indexOf(checkboxId), 1);
                    assessNumStr = assessNum.join(',');
                    console.log(assessNumStr);
                } else {
                    $(".checkbox").eq(i).prop('checked', true);
                    assessNum.push(checkboxId);
                    assessNumStr = assessNum.join(',');
                    console.log(assessNumStr);
                }
            }
        });
        //
        //
        //		没有教师被选中时，组织弹框
        function isTure(ele, modal) {
            for (var i = 0; i < $(ele).length; i++) {
                if ($(ele).eq(i).is(":checked")) {
                    $(modal).fadeIn();
                    return false;
                }
            }
            $(".easy_modal p").html("请先勾选教师后在进行批量操作");
            $(".easy_modal").fadeIn();
        };

        $("#delete_all").unbind("click").click(function() {
            $(".easy_modal").fadeOut();
            new isTure(".checkbox", ".modal_delete_all");
            $(".modal_assess").fadeOut();
            $(".modal_disable_all").fadeOut();
        });

        $("#sub_delete_all").unbind("click").click(function() {
            $.ajax({
                type: "post",
                url: "../admin/recordsDelete",
                async: true,
                data: {
                    id: assessNumStr
                },
                success: function(data) {
                    if (data.Status == 'success') {
                        $(".modal_delete_all").fadeOut();
                        $(".easy_modal p").html(data.Message);
                        $(".easy_modal").fadeIn();
                        getList(_name, _sorts);
                    } else {
                        $(".easy_modal p").html(data.Message);
                        $(".easy_modal").fadeIn();
                        $(".modal_delete_all").fadeOut();
                    }
                },
                fail: function(data) {
                    $(".easy_modal p").html("操作失败，请稍后再试");
                    $(".easy_modal").fadeIn();
                    $(".modal_delete_all").fadeOut();
                }
            });
        });
        $("#close_delete_all").unbind("click").click(function() {
            $(".modal_delete_all").fadeOut();
        });



        //批量禁用
        $("#disable_all").unbind("click").click(function() {
            $(".easy_modal").fadeOut();
            new isTure(".checkbox", ".modal_disable_all");
            $(".modal_assess").fadeOut();
            $(".modal_delete_all").fadeOut();
        });
        $(".time_check").change(function() {
            $(this).prop("disabled", true);
            $(this).siblings('.time_check').prop("disabled", false);
            $(this).siblings('.time_check').prop("checked", false);
            $("#time_value").val($(this).val());
        })
        $("#sub_disable_all").unbind("click").click(function() {
            if ($("#reason").val() != '') {
                $.ajax({
                    type: "post",
                    url: "../admin/recordsDisable",
                    async: true,
                    data: {
                    		exam:exam,
                        id: assessNumStr,
                        reason: $("#reason").val(),
                        disableType: $("#time_value").val()
                    },
                    success: function(data) {
                        if (data.Status == 'success') {
                            $(".modal_disable_all").fadeOut();
                            $("#reason").val('');
                            $(".time_check").prop("disabled", false).prop("checked", false);
                            $(".time_check").eq(0).prop("disabled", true).prop("checked", true);
                            $("#time_value").val('2');
                            $(".easy_modal p").html(data.Message);
                            $(".easy_modal").fadeIn();
                            getList(_name, _sorts);
                        } else {
                            $(".easy_modal p").html(data.Message);
                            $(".easy_modal").fadeIn();
                            $(".modal_disable_all").fadeOut();
                        }
                    },
                    fail: function() {
                        $(".easy_modal p").html("操作失败，请稍候再试");
                        $(".easy_modal").fadeIn();
                        $(".modal_disable_all").fadeOut();
                    }
                });
            } else {
                $(".easy_modal").fadeIn().find('p').html("请填写原因");
            }
        });
        $("#close_disable_all").unbind("click").click(function() {
            $(".modal_disable_all").fadeOut();
            $("#reason").val('');
            $(".time_check").prop("disabled", false).prop("checked", false);
            $(".time_check").eq(0).prop("disabled", true).prop("checked", true);
            $("#time_value").val('2');

        });




    }); //done
}
