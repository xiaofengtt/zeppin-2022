var smsCode = true;
var flagSms = false;
var base = new Base64();
$(function() {
    $(".main-left-item:eq(1)").children("a").addClass("color-orange").children("img").attr("src", "./img/r-2.png");
    $(".main-left-item:eq(1) ul li:eq(2) a").addClass("color-orange");
    getAccountInfo();
    $(".piaochecked").on("click", function() {
        if (!$(this).hasClass("on_check")) {
            $(this).css("border", "1px solid #c6c6c6");
        } else {
            $(this).css("border", "none");
        }
        $(this).toggleClass("on_check");
    });


    //返回修改
    $(".backBtn").click(function() {
        $(".withdrawalSec").hide();
        $(".withdrawalFir").show();
    });

    //点击发送验证码
    $(".smsCode").click(function() {
        sendCode();
    });
});
$(document).click(function(){
	$(".accoundDiv").css({"display":"none"});
});

//发送短信验证码
function sendCode() {
    if (smsCode) {
        var form = $("#getCodesubmit");
        smsCode = false;
        $(".smsCode").html("<span>60</span>s后，重新获取");
        delayCode();
        $.post(form.attr("action"), "type=qcb_withdraw&CSRFToken=" + $('input[name="CSRFToken"]').val(), function(data) {
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

//获取企业信息
function getAccountInfo() {
    $.ajax({
        url: '../rest/qcb/companyAccount/getAccountInfo',
        type: 'get',
        data: {
        	"timestamp":new Date().getTime() 
        }
    }).done(function(r) {
        if (r.status == "SUCCESS") {
            $(".accountBalance").html(r.data.accountPayCN);
        } else {
            layer.msg(r.message);
        }
    }).fail(function() {
        layer.msg("error", {
            time: 2000
        });
    });
}
//获取银行列表
function getbankInfo() {
    $.ajax({
        url: '../rest/qcb/companyBankcard/list',
        type: 'get',
        data: {
            'status': 'normal',
            "pageNum": 1,
            "pageSize": 10000,
            "timestamp":new Date().getTime() 
        }
    }).done(function(r) {
        if (r.status == "SUCCESS") {
            var singleItem = '';
            var totleItem = '';
            if (r.data.length > 0) {
                singleItem = '<p class="bankName">【' + r.data[0].bankName + r.data[0].bindingCardTypeCN +
                    '】</p><img src="..' + r.data[0].bankIconUrl +
                    '" alt="银行logo" /><p class="bankLastNumber">尾号：<span>' + r.data[0].bindingBankCard.substring(r.data[0].bindingBankCard.length - 4) +
                    '</span></p><a class="arrow-down"></a><div class="clear"></div>';
                $("input[name='bankcard']").val(r.data[0].uuid);
            } else {
                singleItem = '<a href="addBankAccount.jsp" class="color-gray">+添加银行账户</a>'
            }
            for (var i = 0; i < r.data.length; i++) {
            	if(r.data[i].uuid==$("input[name='bankcard']").val()){
            		totleItem += '<div class="accoundItem chooseAccount chooseaccoundItem" id="' + r.data[i].uuid +
                    '"><p class="bankName">【' + r.data[i].bankName + r.data[i].bindingCardTypeCN +
                    '】</p><img src="..' + r.data[i].bankIconUrl +
                    '" alt="银行logo" /><p class="bankLastNumber">尾号：<span>' + r.data[i].bindingBankCard.substring(r.data[i].bindingBankCard.length - 4) +
                    '</span></p><a class="arrow-down" style="display:none;"></a><div class="clear"></div></div>';
            	}else{
            		totleItem += '<div class="accoundItem chooseAccount" id="' + r.data[i].uuid +
                    '"><p class="bankName">【' + r.data[i].bankName + r.data[i].bindingCardTypeCN +
                    '】</p><img src="..' + r.data[i].bankIconUrl +
                    '" alt="银行logo" /><p class="bankLastNumber">尾号：<span>' + r.data[i].bindingBankCard.substring(r.data[i].bindingBankCard.length - 4) +
                    '</span></p><a class="arrow-down" style="display:none;"></a><div class="clear"></div></div>';
            	}
                
            }

            $(".chooseAccountShow").html(singleItem);
            $(".accoundDiv").html(totleItem);

            $(".chooseAccountShow").click(function(event) {
                $(".accoundDiv").toggle();
                event.stopPropagation();
            });
            $(".accoundItem").click(function(event) {
                $(".accoundDiv").hide();
                $(".chooseAccountShow").html($(this).html()).find(".arrow-down").show();
                $("input[name='bankcard']").val($(this).attr("id"));
                $(this).addClass("chooseaccoundItem").siblings().removeClass("chooseaccoundItem");
                $(".arrow-down").click(function(event) {
                    $(".accoundDiv").toggle();
                    event.stopPropagation();
                });
            });
        	$(".loadingOver").show();
        	$(".loadingDiv").hide();
        } else {
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

//校验第一步内容
$(".stepFir").click(function() {
    if (checkNonempty($(".amount").val()) && checkMoney($(".amount").val())) {
        $(".amount").removeClass("light").parent().siblings(".tips").fadeOut();
    } else {
        $(".amount").addClass("light").focus().parent().siblings(".tips").fadeIn();
        return false;
    }
    $(".withdrawalSec .chooseAccount").html($(".chooseAccountShow").html()).find(".arrow-down").hide();
    $(".rechangeAccount").html($(".amount").val().split('').reverse().join('').replace(/(\d{3})/g,'$1,').replace(/\,$/,'').split('').reverse().join('')+".00");
    if ($(".noteInformation").val().replace(/(^\s*)|(\s*$)/g, "") == "") {
        $(".noteInformationDiv").html("暂无备注信息");
    } else {
        $(".noteInformationDiv").html($(".noteInformation").val().replace(/(^\s*)|(\s*$)/g, ""));
    }
    if ($(".piaochecked").hasClass("on_check")) {
        $(".messageNotification").hide();
        flagSms = false;
    } else {
        $(".messageNotification").show();
        flagSms = true;
    }
    var numberCost=0;
    if(Number($(".amount").val())>4000){
    	numberCost = Number(Number($(".amount").val())*0.0025).toFixed(2);		
	}else{
		numberCost = 10;
	}
    if(Number($(".accountBalance").html().replace(/,/g,''))<Number($(".amount").val())+Number(numberCost)){
    	layer.msg("余额不足，请充值");
    	return false;
    }
    $(".withdrawalSec").show();
    $(".withdrawalFir").hide();
});

//确认转账
$(".transferBtn").click(function() {
    if (checkNonempty($(".smsCodeinput").val())) {
        $(".smsCodeinput").removeClass("light").parent().parent().siblings(".tips").fadeOut();
    } else {
        $(".smsCodeinput").addClass("light").focus().parent().parent().siblings(".tips").fadeIn();
        return false;
    }
    if (checkNonempty($(".loginPassword").val())) {
        $(".loginPassword").removeClass("light").parent().parent().siblings(".tips").fadeOut();
    } else {
        $(".loginPassword").addClass("light").focus().parent().parent().siblings(".tips").fadeIn();
        return false;
    }
    $(".sureBtn").addClass("loadingBtn").html("").removeClass("transferBtn");
    var form = $("#companyTransaction");
    $.post(form.attr("action"), form.serialize() + "&totalAmount=" + new Base64().encode(String(Number($(".amount").val().replace(/(^\s*)|(\s*$)/g, "")) * 100)) +
        "&flagSms=" + flagSms + "&CSRFToken=" + $('input[name="CSRFToken"]').val()+
        "&password="+new Base64().encode($(".loginPassword").val().replace(/(^\s*)|(\s*$)/g, "")),
        function(data) {
            if (data.status == "SUCCESS") {
            	getTransferSuccess(data.data);                
            } else {
                layer.msg(data.message);
            }
            $(".sureBtn").removeClass("loadingBtn").html("确认转账").addClass("transferBtn");
        });
    return false;
});
$(".amount").blur(function(){
	if($(this).val().replace(/(^\s*)|(\s*$)/g, "")!=""){
		if (checkNonempty($(".amount").val()) && checkMoney($(".amount").val())) {
	        $(".amount").removeClass("light").parent().siblings(".tips").fadeOut();
	    } else {
	        $(".amount").addClass("light").parent().siblings(".tips").fadeIn();
	        return false;
	    }
	}	
});
$(".smsCodeinput").blur(function(){
	if($(this).val().replace(/(^\s*)|(\s*$)/g, "")!=""){
		$(".smsCodeinput").removeClass("light").parent().parent().siblings(".tips").fadeOut();
	}
});
$(".loginPassword").blur(function(){
	if($(this).val().replace(/(^\s*)|(\s*$)/g, "")!=""){
		$(".loginPassword").removeClass("light").parent().parent().siblings(".tips").fadeOut();
	}
});
document.onkeyup = function(event){
	if(Number($(".amount").val())>4000){
		var number = Number(Number($(".amount").val())*0.0025).toFixed(2);
		$(".serviceCharges").html(number.split('').reverse().join('').replace(/(\d{3})/g,'$1,').replace(/\,$/,'').split('').reverse().join(''));
	}else{
		$(".serviceCharges").html('10.00');
	}
} 
//转账成功
function getTransferSuccess(uuid){
	$.ajax({
        url: '../rest/qcb/companyTransaction/get',
        type: 'get',
        data: {
            'uuid': uuid,
            "timestamp":new Date().getTime() 
        }
    }).done(function(r) {
        if (r.status == "SUCCESS") {
        	if(r.data.reachtimeCN!=null){
        		$(".reachTime").html("预计到账时间&nbsp;"+r.data.reachtimeCN.replace('年','-').replace('月','-').replace('日','')+"&nbsp;前");//到账时间
        	}
        	if(r.data.companyAccountName!=null){
        		$(".companyAccountName").html(r.data.companyAccountName);//付款账户名称
        	}
        	if(r.data.price!=null){
        		$(".rechangeAccountPrice").html(r.data.price)//转账金额
        	}
        	
        	if(r.data.qcbCompanyBankcardNum!=null){
            	$(".nm-b").html(r.data.qcbCompanyBankcardNum.replace(/[^\d]/g,'').replace(/(\d{4})(?=\d)/g,'$1 '));//银行卡号
        	}
        	if(r.data.qcbCompanyBankcardName!=null&&r.data.qcbCompanyBankcardAddress!=null){
            	$(".qcbCompanyBankcardName").html(r.data.qcbCompanyBankcardName+"&nbsp;"+r.data.qcbCompanyBankcardAddress);//银行名称
        	}
        	if(r.data.poundage!=null){
        		$(".poundage").html(r.data.poundage+"元");//手续费
        	}
        	if(r.data.orderNum!=null){
        		$(".orderNum").html(r.data.orderNum);//流水号
        	}
        	if(r.data.createtimeCN!=null){
        		$(".getNowFormatDate").html(r.data.createtimeCN.replace('年','-').replace('月','-').replace('日',''));//审批时间
        	}            
            if(r.data.creatorName!=null){
            	$(".creator").html(r.data.creatorName+"&nbsp;&nbsp;");//审批人
            }
        	$(".main-right .box").hide();
            $(".main-right-body").show();
        }else{
        	layer.msg(r.message);
        }
    }).fail(function(r){
    	layer.msg("error");
	});
}
