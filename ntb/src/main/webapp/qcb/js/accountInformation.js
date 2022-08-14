var pageNum = 1;
var order = "desc";
var smsCode = true;
$(document).ready(function() {
    $(".main-left-item:eq(1)").children("a").addClass("color-orange").children("img").attr("src", "./img/r-2.png");
    $(".main-left-item:eq(1) ul li:eq(0) a").addClass("color-orange");
    document.onkeydown = function(event){
		if(event.keyCode==13) {
			if($(".bindPhoneIframe").css("display")=="block"){
				$(".deleteBtn").click();
			}			
			return false;
		}
	}
    $(".iframe").css({
        "height": $(window).height()
    });
    $(".closeIframe").click(function() {
        $(".iframe").hide();
        $(".smsCode").val("");
        $(".sureBtn").html("确定").removeClass("loadingBtn").addClass("deleteBtn");
        $(".smsCode").removeClass("light").siblings(".tip").fadeOut();
    });
    $(".sort").click(function() {
        $(this).addClass("color-orange");
        $(this).siblings(".sort").removeClass("color-orange");
        $(this).removeClass("asc").removeClass("desc");
        order = order == 'desc' ? 'asc' : 'desc';
        $(this).addClass(order).siblings(".sort").removeClass("asc").removeClass("desc");
        //		getList();
    });
    noMargin();
    get();
    getBankCardList();
    $(".getCode").click(function() {
        sendCode();
    });

    function get() {
        $.ajax({
                url: '../rest/qcb/companyAccount/getAccountInfo',
                type: 'get',
                data: {
                    "timestamp":new Date().getTime() 
                }
            })
            .done(function(r) {
            	if(r.status == "SUCCESS"){
            		$("#accountBalance").html(r.data.accountBalanceCN);
                    $("#accountPay").html(r.data.accountPayCN);
                    $("#totalInvest").html(r.data.totalInvestCN);
                    $("#totalReturn").html(r.data.totalReturnCN);
            	}else{
            		layer.msg(r.message);
            	}
                
            }).fail(function(){
            	layer.msg("error", {
                    time: 2000
                });
            });
    }
    $('#pageTool').Paging({
        prevTpl: "<",
        nextTpl: ">",
        pagesize: 10,
        count: 20,
        callback: function(page, size, count) {
            pageNum = page;
            //			getList();
            //			flag = false;
            //			document.body.scrollTop = document.documentElement.scrollTop = 0;
        },
        render: function(ops) {

        }
    });
    $("#pageTool").find(".ui-paging-container:last").siblings().remove();
});
//发送短信验证码
function sendCode() {
    if (smsCode) {
        var form = $("#getCodesubmit");
        smsCode = false;
        $(".getCode").html("<span>100</span>s后，重新获取");
        delayCode();
        $.post(form.attr("action"), "type=qcb_bankcard_delete&CSRFToken=" + $('input[name="CSRFToken"]').val(), function(data) {
            if (data.status == "SUCCESS") {
                layer.msg(data.message);
            } else {
                layer.msg(data.message);
                clearTimeout(t);
                smsCode = true;
                $(".getCode").html("重新获取验证码");
            }
        });
        return false;
    }
}

function noMargin() {
    $(".main-right-mid-content div").each(function(index, el) {
        if (index % 3 == 1) {
            $(".main-right-mid-content div").eq(index).css({
                "margin-right": "0px"
            });
        }
    });
}

//发送验证码倒计时
function delayCode() {
    var delay = Number($(".getCode span").html());
    var t = setTimeout("delayCode()", 1000);
    if (delay > 1) {
        delay--;
        $(".getCode span").html(delay);
    } else {
        clearTimeout(t);
        smsCode = true;
        $(".getCode").html("重新获取验证码");
    }
}

//删除
$(".deleteBtn").click(function() {
    if (checkNonempty($(".smsCode").val())) {
        $(".smsCode").removeClass("light").siblings(".tip").fadeOut();
    } else {
        $(".smsCode").addClass("light").focus().siblings(".tip").fadeIn();
        return false;
    }
    $(".sureBtn").addClass("loadingBtn").html("").removeClass("deleteBtn");
    var form = $("#deleteBankCard");
    $.get(form.attr("action"),
        "&uuid=" + $("#uuid").val() +
        "&code=" + $(".smsCode").val().replace(/(^\s*)|(\s*$)/g, "") +
        "&CSRFToken=" + $('input[name="CSRFToken"]').val(),
        function(data) {
            if (data.status == "SUCCESS") {
                layer.msg(data.message, {
                    time: 2000
                }, function() {
                    $(".iframe").hide();
                    $(".smsCode").val("");
                    $(".sureBtn").html("确定").removeClass("loadingBtn").addClass("deleteBtn");
                    getBankCardList();
                });
            } else {
            	$(".sureBtn").html("确定").removeClass("loadingBtn").addClass("deleteBtn");
                layer.msg(data.message);
            }
        });
    return false;
});

function getBankCardList() {
    $.ajax({
            url: '../rest/qcb/companyBankcard/list',
            type: 'get',
            data: {
                "pageNum": 1,
                "pageSize": 1000,
                "timestamp":new Date().getTime()               
            }
        })
        .done(function(r) {
            if (r.status == "SUCCESS") {
                var template = $.templates("#bankCardTpl");
                var html = template.render(r.data);
                $(".main-right-mid-content").html(html);
                $(".main-right-mid-content").append("<div class='add-box'><a href='addBankAccount.jsp' class='add-bank-account'><p></p><span>添加银行账户</span></a></div>");
                noMargin();
                $(".delete").unbind("click").click(function() {
                    $(".iframe").fadeIn();
                    $("#uuid").val($(this).attr("data-uuid"));
                });
                $(".loadingOver").show();
            	$(".loadingDiv").hide();
            } else {
                layer.msg(r.message);
            }
        })
        .fail(function() {
            layer.msg("服务器错误");
        });

}
$(window).resize(function(){
	$(".iframe").css({
        "height": $(window).height()
    });
});