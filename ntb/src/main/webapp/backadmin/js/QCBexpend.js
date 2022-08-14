$(document).ready(function() {
    var uuid = url("?uuid");
    var rate = url("?rate");
    getAccountMessage();

    $("#companyAccountIn").val(uuid);

    $("#allRight").click(function() {


        if ($("#rechargeValue").val() == "" || parseFloat($("#rechargeValue").val()) <= 0) {
        	 var flagValue = $('input[name="flag"]:checked').val();
             var flags = flagValue ==="false" ? false : true;
             
             if(flags){
            	 layer.msg("请正确填写开票金额", {
                     time: 2000
                 });
             } else {
            	 layer.msg("请正确填写应扣除金额", {
                     time: 2000
                 });
             }
            
            $("#allRight").prop("disabled", false);
            return false;
        }

        //如果需要上传凭证，请解除下面代码
        // if ($('input[name="receipt"]').val() == "") {
        //     layer.msg("请上传凭证", {
        //         time: 2000
        //     });
        //     $("#allRight").prop("disabled", false);
        //     return false;
        // }
        $(".recharge_msg").hide();
        $(".recharge_confirm").show();
    });

    $("#back").click(function() {
        $(".recharge_msg").show();
        $(".recharge_confirm").hide();
    });
    $("#allRightConfirm").click(function() {
        $("#allRightConfirm").prop("disabled", true);
        $("#rechargeSubmit").ajaxSubmit(function(r) {
            if (r.status == "SUCCESS") {
                layer.msg(r.message, {
                    time: 2000
                }, function() {
                    window.location.href = document.referrer;
                });

            } else {
                layer.msg(r.message, {
                    time: 2000
                });
                $("#allRightConfirm").prop("disabled", false);
            }
        });
    });

    function getAccountMessage() {
        $.ajax({
                url: '../rest/backadmin/qcbcompany/get',
                type: 'get',
                data: {
                    "uuid": uuid
                }
            })
            .done(function(r) {
                //生成模板加载数据
                if (r.status == "SUCCESS") {
                    $("#qcbCompany").val(uuid);
                    $(".company-name").html(r.data.name);
                    $(".rate").html(formatNum(rate) + "%");
                } else {
                    layer.msg(r.message, {
                        time: 2000
                    });
                }
            })
            .fail(function() {
                layer.msg("error", {
                    time: 2000
                });
            });
    } //getAccountMessage()
    $("#psValue").on("input", function() {
        $("#psConfirm").html($(this).val());
    });
    $("#rechargeValue").bind("input", function() {
        if ($(this).val().length > 10) {
            $(this).val($(this).val().slice(0, 10));
        }
        var flagValue = $('input[name="flag"]:checked').val();
        var flags = flagValue ==="false" ? false : true;
        
        if ($(this).val() != "") {

            var bigValue = changeMoneyToChinese($("#rechargeValue").val());
            var rechargeValue = parseFloat($("#rechargeValue").val()).toFixed(10);
            var rechargeValueV = rechargeValue.toString();
            $("#rechargeConfirm").html(formatNum(rechargeValueV));
            $("#rechargeBigValue").html("人民币：" + bigValue);
            $("#rechargeBigConfirm").html("人民币：" + bigValue);

            //计算扣除金额
            var thisValue = Number($(this).val());
            var thisRate = Number(rate) / 100 + 1;
            var finalValue = thisValue - (thisValue / thisRate);
            
            if(!flags){
            	$('.flagValueConfirm').html('手动扣除');
            	$('.noticeFloat').html('应扣除');
            	finalValue = thisValue;
            } else {
            	$('.flagValueConfirm').html('固定费率');
            	$('.noticeFloat').html('开票');
            }
            $(".expendValue").html(formatNum(finalValue.toFixed(2)));
            $(".expendBigValue").html("人民币：" + changeMoneyToChinese(finalValue.toFixed(2)));
        } else {
        	if(flags){
        		$(".expendValue").html("请输入开票金额");
        	} else {
        		$(".expendValue").html("请输入应扣除金额");
        	}
        	
            $(".expendBigValue").html("");
            $("#rechargeConfirm").html("");
            $("#rechargeBigValue").html("");
            $("#rechargeBigConfirm").html("");
        }
    });

    $('input[name="flag"]').on('change',function(){
    	if ($("#rechargeValue").val().length > 10) {
            $("#rechargeValue").val($("#rechargeValue").val().slice(0, 10));
        }
    	var flagValue = $('input[name="flag"]:checked').val();
        var flags = flagValue ==="false" ? false : true;
        
        if ($("#rechargeValue").val() != "") {

            var bigValue = changeMoneyToChinese($("#rechargeValue").val());
            var rechargeValue = parseFloat($("#rechargeValue").val()).toFixed(10);
            var rechargeValueV = rechargeValue.toString();
            $("#rechargeConfirm").html(formatNum(rechargeValueV));
            $("#rechargeBigValue").html("人民币：" + bigValue);
            $("#rechargeBigConfirm").html("人民币：" + bigValue);

            //计算扣除金额
            var thisValue = Number($("#rechargeValue").val());
            var thisRate = Number(rate) / 100 + 1;
            var finalValue = thisValue - (thisValue / thisRate);
            
            if(!flags){
            	$('.flagValueConfirm').html('手动扣除');
            	$('.noticeFloat').html('应扣除');
            	finalValue = thisValue;
            } else {
            	$('.flagValueConfirm').html('固定费率');
            	$('.noticeFloat').html('开票');
            }
            $(".expendValue").html(formatNum(finalValue.toFixed(2)));
            $(".expendBigValue").html("人民币：" + changeMoneyToChinese(finalValue.toFixed(2)));
        } else {
        	if(flags){
        		$(".expendValue").html("请输入开票金额");
        		$('.noticeFloat').html('开票');
        	} else {
        		$(".expendValue").html("请输入应扣除金额");
        		$('.noticeFloat').html('应扣除');
        	}
            $(".expendBigValue").html("");
            $("#rechargeConfirm").html("");
            $("#rechargeBigValue").html("");
            $("#rechargeBigConfirm").html("");
        }
    })

}); //document.ready;