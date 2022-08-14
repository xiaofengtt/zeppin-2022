var smsCode = true;
$(function() {
    $(".main-left-item:eq(1)").children("a").addClass("color-orange").children("img").attr("src", "./img/r-2.png");
    $(".main-left-item:eq(1) ul li:eq(0) a").addClass("color-orange");
    getBankList(); //银行列表
    getArea("province", "1", "", ""); //获取地区
    //点击发送验证码
    $(".smsCode").click(function() {
        sendCode();
    });
    $(".bankBranchList").select2();
});
//发送验证码倒计时
function delayCode() {
    var delay = Number($(".smsCode span").html());
    var t = setTimeout("delayCode()", 1000);
    if (delay > 1) {
        delay--;
        $(".smsCode span").html(delay);
    } else {
        clearTimeout(t);
        smsCode = true;
        $(".smsCode").html("重新获取验证码");
    }
}

//银行列表
function getBankList() {
    $.ajax({
        url: '../rest/qcb/bank/list',
        type: 'get',
        data: {
            "pageNum": 1,
            "pageSize": 1000,
            "timestamp":new Date().getTime() 
        }
    }).done(function(r) {
    	if(r.status == "SUCCESS"){
    		$(".bankList").html("<option value='0'>请选择</option>");
            $.each(r.data, function(index, el) {
                $(".bankList").append("<option value=" + el.uuid + " scode = " + el.scode + ">" + el.name + "</option>");
            });
            $(".bankList").change(function() {
                getBankbranchList();
            });
            $(".loadingOver").show();
        	$(".loadingDiv").hide();
    	}else{
    		$(".loadingDiv").hide();
    		layer.msg(r.message);
    	}
        
    }).fail(function() {
    	$(".loadingDiv").hide();
        layer.msg("error", {
            time: 2000
        });
    });
}
//获取地区
function getArea(className, level, pid, scode) {
    $.ajax({
        url: '../rest/qcb/area/list',
        type: 'get',
        data: {
            "level": level,
            "pid": pid,
            "scode": scode,
            "pageNum": 1,
            "pageSize": 1000,
            "timestamp":new Date().getTime() 
        }
    }).done(function(r) {
    	if(r.status == "SUCCESS"){
    		$("." + className).html("<option value='0'>请选择</option>");
            $.each(r.data, function(index, el) {
                if (el.level == 1 && el.name == "全国") {

                } else {
                    $("." + className).append("<option value=" + el.uuid + " scode = " + el.scode + ">" + el.name + "</option>");
                }
            });

            $(".province").change(function(event) {
                getArea("city", "2", $(this).val(), $(this).attr("scode"));
            });
            $(".city").change(function(event) {
                getArea("county", "3", $(this).val(), $(this).attr("scode"));
            });
    	}else{
    		layer.msg(r.message);
    	}
        
    }).fail(function() {
        layer.msg("error", {
            time: 2000
        });
    });
}

//支行列表
function getBankbranchList() {
    $.ajax({
        url: '../rest/qcb/bank/branchList',
        type: 'get',
        data: {
            "bank": $(".bankList").val(),
            "pageNum": 1,
            "pageSize": 1000,
            "timestamp":new Date().getTime() 
        }
    }).done(function(r) {
    	if(r.status == "SUCCESS"){
    		$(".bankBranchList").html("<option value='0'>请选择</option>");
            $.each(r.data, function(index, el) {
                $(".bankBranchList").append("<option value=" + el.uuid + " scode = " + el.scode + ">" + el.name + "</option>");
            });
    	}else{
    		layer.msg(r.message);
    	}
        
    }).fail(function() {
        layer.msg("error", {
            time: 2000
        });
    });
}
//发送短信验证码
function sendCode() {
    if (smsCode) {
        var form = $("#getCodesubmit");
        smsCode = false;
        $(".smsCode").html("<span>100</span>s后，重新获取");
        delayCode();
        $.post(form.attr("action"), "type=qcb_bankcard_add&CSRFToken=" + $('input[name="CSRFToken"]').val(), function(data) {
            if (data.status == "SUCCESS") {
                layer.msg(data.message);
            } else {
                layer.msg(data.message);
                clearTimeout(t);
                smsCode = true;
                $(".smsCode").html("重新获取验证码");
            }
        });
        return false;
    }
}
//保存
$(".sureBtn").click(function() {
    if (checkNumber($(".bankAccount").val().replace(/[ ]/g,""))&&checkNonempty($(".bankAccount").val().replace(/[ ]/g,""))) {
        $(".bankAccount").removeClass("light").parent().siblings(".tips").fadeOut();
    } else {
        $('html').animate({
            scrollTop: 0
        }, 500);
        $(".bankAccount").addClass("light").focus().parent().siblings(".tips").fadeIn();
        return false;
    }
    if ($(".bankList").val()!="0") {
        $(".bankList").removeClass("light").parent().siblings(".tips").fadeOut();
    } else {
        $('html').animate({
            scrollTop: 100
        }, 500);
        $(".bankList").addClass("light").focus().parent().siblings(".tips").fadeIn();
        return false;
    }
  //校验地区
    if ($(".province").val() == "0" || $(".city").val() == "0" || $(".county").val() == "0") {
        $(".province").parent().siblings(".tipsBox").fadeIn();
        if ($(".province").val() == "0") {
            $(".province").addClass("light");
            $(".city").removeClass("light");
            $(".county").removeClass("light");
        } else if ($(".city").val() == "0") {
            $(".city").addClass("light");
            $(".province").removeClass("light");
            $(".county").removeClass("light");
        } else {
            $(".county").addClass("light");
            $(".province").removeClass("light");
            $(".city").removeClass("light");
        }
        $('html').animate({
            scrollTop: 200
        }, 500);
        $(".province").parent().siblings(".tips").fadeIn();
        return false;
    } else {
        $(".province").removeClass("light");
        $(".city").removeClass("light");
        $(".county").removeClass("light");
        $(".province").parent().siblings(".tips").fadeOut();
    }
    if ($(".bankBranchList").val()!="0") {
        $(".bankBranchList").removeClass("light").parent().siblings(".tips").fadeOut();
    } else {
        $('html').animate({
            scrollTop: 200
        }, 500);
        $(".bankBranchList").addClass("light").focus().parent().siblings(".tips").fadeIn();
        return false;
    }
    if(checkNonempty($(".bingingCardPhone").val())&&checkPhone($(".bingingCardPhone").val())){
		$(".bingingCardPhone").removeClass("light").parent().siblings(".tips").fadeOut();			
	}else{
		$(".bingingCardPhone").addClass("light").focus().parent().siblings(".tips").fadeIn();
		return false;
	}
    if (checkNonempty($(".smsCodeinput").val())) {
        $(".smsCodeinput").removeClass("light").parent().parent().siblings(".tips").fadeOut();
    } else {
        $(".smsCodeinput").addClass("light").focus().parent().parent().siblings(".tips").fadeIn();
        return false;
    }
    $(".sureBtn").addClass("loadingBtn").html("").removeClass("sureBtn");
    var form = $("#addCompanyBanksubmit");
    $.post(form.attr("action"), "bank=" + $(".bankList").val() +
        "&branchBank=" + $(".bankBranchList").val() +
        "&area=" + $(".county").val() +
        "&bindingBankCard=" + $(".bankAccount").val().replace(/[ ]/g,"") +
        "&bindingCardPhone=" + $(".bingingCardPhone").val().replace(/(^\s*)|(\s*$)/g, "") +
        "&bindingCardType=company" +
        "&code=" + $(".smsCodeinput").val().replace(/(^\s*)|(\s*$)/g, "") +
        "&CSRFToken=" + $('input[name="CSRFToken"]').val(),
        function(data) {
            if (data.status == "SUCCESS") {                
                layer.msg(data.message, {
                    time: 2000
                },function(){
                	window.location.href = "accountInformation.jsp";
                });
            } else {
            	$(".btnGroup .btn").html("保存").removeClass("loadingBtn").addClass("sureBtn");
                layer.msg(data.message);
            }
        });
    return false;
});

$(".bankAccount").blur(function(){
	if($(this).val().replace(/(^\s*)|(\s*$)/g, "")!=""){
		if (checkNumber($(".bankAccount").val().replace(/[ ]/g,""))) {
	        $(".bankAccount").removeClass("light").parent().siblings(".tips").fadeOut();
	    } else {
	        $(".bankAccount").addClass("light").parent().siblings(".tips").fadeIn();
	    }
	}
});
$(".bankList").blur(function(){
	if($(this).val()!="0"){
		$(".bankList").removeClass("light").parent().siblings(".tips").fadeOut();
	}
});
$(".province").blur(function(){
	if($(this).val()!="0"&&$(".city").val()!="0"&&$(".county").val()!="0"){
		$(".province").removeClass("light");
        $(".city").removeClass("light");
        $(".county").removeClass("light");
		$(".province").parent().siblings(".tips").fadeOut();
	}else if($(this).val()!="0"){
		$(this).removeClass("light");
	}
});
$(".city").blur(function(){
	if($(".province").val()!="0"&&$(".city").val()!="0"&&$(".county").val()!="0"){
		$(".province").removeClass("light");
        $(".city").removeClass("light");
        $(".county").removeClass("light");
		$(".province").parent().siblings(".tips").fadeOut();
	}else if($(this).val()!="0"){
		$(this).removeClass("light");
	}
});
$(".county").blur(function(){
	if($(".province").val()!="0"&&$(".city").val()!="0"&&$(".county").val()!="0"){
		$(".province").removeClass("light");
        $(".city").removeClass("light");
        $(".county").removeClass("light");
		$(".province").parent().siblings(".tips").fadeOut();
	}else if($(this).val()!="0"){
		$(this).removeClass("light");
	}
});
$(".bankBranchList").blur(function(){
	if($(this).val()!="0"){
		$(".bankBranchList").removeClass("light").parent().siblings(".tips").fadeOut();
	}
});
$(".bingingCardPhone").blur(function(){
	if($(this).val().replace(/(^\s*)|(\s*$)/g, "")!=""){
		if(checkNonempty($(".bingingCardPhone").val())&&checkPhone($(".bingingCardPhone").val())){
			$(".bingingCardPhone").removeClass("light").parent().siblings(".tips").fadeOut();			
		}else{
			$(".bingingCardPhone").addClass("light").parent().siblings(".tips").fadeIn();
		}
	}
});
$(".smsCodeinput").blur(function(){
	if($(this).val().replace(/(^\s*)|(\s*$)/g, "")!=""){
		$(".smsCodeinput").removeClass("light").parent().parent().siblings(".tips").fadeOut();
	}
});
