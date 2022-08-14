var pageNum = 1;
var pageSize = 10;
var flag = true;
var smsCode = true;
var uuid;
$(document).ready(function() {
    $(".main-left-item:eq(2)").children("a").addClass("color-orange").children("img").attr("src", "./img/r-3.png");
    $(".main-left-item:eq(2) ul li:eq(1) a").addClass("color-orange");
    $(".iframe").css({
        "height": $(window).height()
    });
    $(".closeIframe").click(function() {
    	clearTimeout(t);
        $(".iframe").hide();
    });
    $(".bg").css({
        "height": $(window).height(),
        "width": $(window).width()
    });
    $(window).resize(function() {
        $(".bg").css({
            "height": $(window).height(),
            "width": $(window).width()
        });
    });

    $("#modal-close").click(function() {
        $(".bg").hide();
        $(".sureBtn").removeClass("loadingBtn").html("确认").addClass("distributionBtn");
        $(".smsCode").removeClass("light").siblings(".tip").fadeOut();
    });
    $("#confirm").click(function() {
        wechatFlag();
    });
});

//薪资发放记录列表
function getlist() {
    $.ajax({
        url: '../rest/qcb/companyPayroll/list',
        type: 'get',
        data: {
            "pageNum": pageNum,
            "pageSize": pageSize,
            "timestamp": new Date().getTime()

        }
    }).done(function(r) {
        if (r.status == "SUCCESS") {
            var html = '<tr class="first-tr"><th width="12%" class="td-padding-item">发放时间</th>' +
                '<th width="21%">发放内容</th><th width="7%">发放状态</th><th width="14%">员工反馈</th><th width="11%">发放人</th>' +
                '<th width="23%">原始文件</th><th width="10%">操作</th></tr>';
            if (r.data.length > 0) {
                for (var i = 0; i < r.data.length; i++) {
                    if (r.data[i].status == "failed") {
                        html += '<tr><td class="td-padding-item"><p>' + r.data[i].payTimeCN.substring(0, 10) +
                            '</p><small>' + r.data[i].payTimeCN.substring(11, 19) +
                            '</small></td><td>' + r.data[i].title +
                            '</td><td>'+ r.data[i].statusCN +'</td><td>确认<span class="color-orange">&nbsp;' + r.data[i].comfirmCount +
                            '&nbsp;</span><i>/</i><span>&nbsp;' + r.data[i].payrollCount +
                            '&nbsp;</span>&nbsp;反馈<span class="color-orange">&nbsp;' + r.data[i].feedbackCount +
                            '&nbsp;</span></td><td>' + r.data[i].creatorName + '(管理员)</td><td><a href="..' + r.data[i].sourcefileUrl +
                            '" class="color-orange" target="_blank">' + r.data[i].sourcefileName +
                            '</a></td><td><a href="wagesListDetail.jsp?uuid=' + r.data[i].uuid + '">查看</a>'+
                            '<a onclick="wechatFlag(this);" uuid="'+ r.data[i].uuid +'">重新发放</a></td></tr>';
                    } else {
                        html += '<tr><td class="td-padding-item"><p>' + r.data[i].payTimeCN.substring(0, 10) +
                            '</p><small>' + r.data[i].payTimeCN.substring(11, 19) +
                            '</small></td><td>' + r.data[i].title +
                            '</td><td>'+ r.data[i].statusCN +'</td><td>确认<span class="color-orange">&nbsp;' + r.data[i].comfirmCount +
                            '&nbsp;</span><i>/</i><span>&nbsp;' + r.data[i].payrollCount +
                            '&nbsp;</span>&nbsp;反馈<span class="color-orange">&nbsp;' + r.data[i].feedbackCount +
                            '&nbsp;</span></td><td>' + r.data[i].creatorName + '(管理员)</td><td><a href="..' + r.data[i].sourcefileUrl +
                            '" class="color-orange" target="_blank">' + r.data[i].sourcefileName +
                            '</a></td><td><a href="wagesListDetail.jsp?uuid=' + r.data[i].uuid + '">查看</a></td></tr>';
                    }

                }


            } else {
                html += '<tr><td colspan="7" style="text-align:center">暂无发放记录</td></tr>';
            }
            $(".table").html(html);
            $(".loadingOver").show();
            $(".loadingDiv").hide();

        } else {
            $(".loadingDiv").hide();
            layer.msg(r.message);
        }
        if (r.totalPageCount!=0) {
	        $('#pageTool').Paging({
	            prevTpl: "<",
	            nextTpl: ">",
	            pagesize: r.pageSize,
	            count: r.totalResultCount,
	            current: pageNum,
	            pageSizeList: [10, 20, 50],
	            toolbar: true,
	            callback: function(page, size, count) {
	                pageNum = page;
	                getlist();
	                document.body.scrollTop = document.documentElement.scrollTop = 0;
	            }
	        });
	        $("#pageTool").find(".ui-paging-container:last").siblings().remove();
        }
        $(".ui-select-pagesize").unbind("change").change(function() {
            pageSize = $(this).val();
            pageNum = 1;
            getlist();
        });
    }).fail(function(r) {
        $(".loadingDiv").hide();
        layer.msg("error", {
            time: 2000
        });
    });
}
//点击发送验证码
$(".iframeInner .getCode").click(function() {
    if (checkNonempty($(".oldphone").val())) {
        $(".oldphone").removeClass("light").siblings(".tip").fadeOut();
    } else {
        $(".oldphone").addClass("light").siblings(".tip").fadeIn();
        return false;
    }
    if (smsCode) {
        smsCode = false;
        $(".getCode").html("<span>60</span>s后，重新获取");
        invokeSettime();
        var form = $("#sendCodeToCheck");
        $.post(form.attr("action"), "type=qcb_payroll&CSRFToken=" + $('input[name="CSRFToken"]').val(), function(data) {
            if (data.status == "SUCCESS") {
                layer.msg(data.message);
            } else {
                layer.msg(data.message);
                clearTimeout(timer);
                smsCode = true;
                $(".getCode").html("重新获取验证码");
            }
        });
        return false;
    }
});

var timer;
//短信验证码倒计时
function invokeSettime() {
    var delay = Number($(".getCode span").html());
    timer = setTimeout("invokeSettime()", 1000);
    if (delay > 1) {
        delay--;
        $(".getCode span").html(delay);
    } else {
        clearTimeout(timer);
        smsCode = true;
        $(".getCode").html("重新获取验证码");
    }
}

$(".distributionBtn").click(function() {
    if ($(".smsCode").val().replace(/(^\s*)|(\s*$)/g, "")!="") {
        $(".smsCode").removeClass("light").siblings(".tip").fadeOut();
    } else {
        $(".smsCode").addClass("light").focus().siblings(".tip").fadeIn();
        return false;
    }
	$(".sureBtn").addClass("loadingBtn").html("").removeClass("distributionBtn");
    var form = $("#sureSend");
    $.post(form.attr("action"), "uuid=" + uuid +
        "&code=" + $(".smsCode").val().replace(/(^\s*)|(\s*$)/g, "") +
        "&CSRFToken=" + $('input[name="CSRFToken"]').val(),
        function(data) {
            if (data.status == "SUCCESS") {
                $(".bg").hide();
                layer.msg("重新发放成功", {
                    time: 2000
                }, function() {
                	getlist();
                });
            } else {
                layer.msg(data.message);
            }
            $(".sureBtn").removeClass("loadingBtn").html("确认").addClass("distributionBtn");
        });
    return false;
});
$(window).resize(function() {
    $(".iframe").css({
        "height": $(window).height()
    });
});

function wechatFlag(obj) {
    $.ajax({
        url: '../rest/qcb/admin/getWechatFlag',
        type: 'get',
        async:false,
        data: {
            "timestamp":new Date().getTime() 
        }
    }).done(function(r) {
        if (r.status == "SUCCESS") {
            uuid=$(obj).attr("uuid");
            if (r.data == true) {
                $(".submitGrant").show();
//                $("#wechatBtn").attr("href", "safetyCenterIframe.jsp?type=payroll&uuid=" + uuid);
                window.open("safetyCenterIframe.jsp?type=payroll&uuid=" + uuid);
//                document.getElementById("wechatBtn").click();
                grantFlag();
            } else {
                $(".bg").fadeIn();
            }
        } else {
            layer.msg(r.message);
        }
    }).fail(function(r) {
        layer.msg("error", {
            time: 2000
        });
    });
}

var t;

function grantFlag() {
    t = setTimeout("grantFlag()", 1000);
    $.ajax({
        url: '../rest/qcb/companyPayroll/get',
        type: 'get',
        data: {
        	"uuid": uuid,
            "timestamp":new Date().getTime() 
        }
    }).done(function(r) {
        if (r.status == "SUCCESS") {
            if (r.data.status != "draft") {
                clearTimeout(t);
                $(".iframe").hide();
                layer.msg("重新发放成功", {
                    time: 2000
                }, function() {
                	getlist();
                });

            } else {
                /*  layer.msg("绑定失败，请重新绑定");  */
            }
        } else {
            layer.msg(r.message);
        }
    }).fail(function(r) {
        layer.msg("error", {
            time: 2000
        });
    });
}
