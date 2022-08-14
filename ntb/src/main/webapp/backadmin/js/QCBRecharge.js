$(document).ready(function() {
    var uuid = url("?uuid");
    ! function() {
        var now = laydate.now('YYYY-MM-DD hh:mm:ss');
        var date = new Date(); //时间戳为10位需*1000，时间戳为13位的话不需乘1000
        Y = date.getFullYear() + '年';
        M = (date.getMonth() + 1 < 10 ? (date.getMonth() + 1) : date.getMonth() + 1) + '月';
        D = date.getDate() + '';
        h = (date.getHours() < 10 ? '0' + (date.getHours()) : date.getHours());
        m = (date.getMinutes() < 10 ? '0' + (date.getMinutes()) : date.getMinutes());
        s = (date.getSeconds() < 10 ? '0' + (date.getSeconds()) : date.getSeconds());
        laydate({
            elem: '#rechargeTimeValue',
            format: 'YYYY-MM-DD hh:mm:ss',
            istime: true,
            max: now + " " + h + ":" + m + ":" + s,
            choose: function(dates) {
                $("#rechargeTimeConfirm").html(dates);
            }
        });
    }();

    getAccountMessage();

    $("#companyAccountIn").val(uuid);

    $("#allRight").click(function() {


        if ($("#rechargeValue").val() == "" || parseFloat($("#rechargeValue").val()) <= 0) {
            layer.msg("请正确填写充值金额", {
                time: 2000
            });
            $("#allRight").prop("disabled", false);
            return false;
        }
        if ($("#rechargeTimeValue").val() == "") {
            layer.msg("请填写充值时间", {
                time: 2000
            });
            $("#allRight").prop("disabled", false);
            return false;
        }
        if ($("#psValue").val() == "") {
            layer.msg("请填写备注", {
                time: 2000
            });
            $("#allRight").prop("disabled", false);
            return false;
        }

        if ($('input[name="receipt"]').val() == "") {
            layer.msg("请上传凭证", {
                time: 2000
            });
            $("#allRight").prop("disabled", false);
            return false;
        }
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
                url: '../rest/backadmin/qcbVirtualAccounts/get',
                type: 'get',
                data: {
                    "uuid": uuid
                }
            })
            .done(function(r) {
                //生成模板加载数据
                if (r.status == "SUCCESS") {
                    var template = $.templates("#queboxTpl");
                    var html = template.render(r.data);
                    $("#queboxCnt").html(html);
                    $("#queboxCnt_").html(html);

                    $(".company-name").html(r.data.qcbCompanyName);
                    $("#qcbCompany").val(r.data.qcbCompany);
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

    $("#rechargeValue").bind("input", function() {
        if ($(this).val().length > 10) {
            $(this).val($(this).val().slice(0, 10));
        }
        var bigValue = changeMoneyToChinese($("#rechargeValue").val());

        var rechargeValue = parseFloat($("#rechargeValue").val()).toFixed(10);
        var rechargeValueV = rechargeValue.toString();

        $("#rechargeConfirm").html(formatNum(rechargeValueV));
        $("#rechargeBigValue").html("人民币：" + bigValue);
        $("#rechargeBigConfirm").html("人民币：" + bigValue);
    });
    $("#useValue").bind("input", function() {
        $("#useConfirm").html($("#useValue").val());
    });
    $("#psValue").bind("input", function() {
        $("#psConfirm").html($("#psValue").val());
    });



}); //document.ready;