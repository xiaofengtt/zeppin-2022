$(document).ready(function() {
    var uuid = url("?uuid");
    var target_uuid = url("?target_uuid");
    $("#companyAccountOut").val(uuid);
    $("#companyAccountIn").val(target_uuid);
    var outAccount = new getAccountMessage(uuid, "#outTpl", "#outCnt");
    var inAccount = new getAccountMessage(target_uuid, "#inTpl", "#inCnt");


    $("#allRightConfirm").click(function() {
        $("#allRightConfirm").prop("disabled", true);
        $("#submit").ajaxSubmit(function(r) {
            if (r.status == 'SUCCESS') {
                layer.msg(r.message, {
                    time: 2000
                }, function() {
                    window.location.href = "./accountControllerList.jsp";
                });
            } else {
                layer.msg(r.message, {
                    time: 2000
                });
                $("#allRightConfirm").prop("disabled", false);
            }
        });
    });

    $("#allRight").click(function() {
        var this_Amount = parseFloat($("#poundage").val()) + parseFloat($("#transferValue").val());
        if ($("#transferValue").val() == "" || parseFloat($("#transferValue").val()) <= 0) {
            layer.msg("请正确填写转账金额", {
                time: 2000
            });
            return false;
        }
        if (this_Amount > $("#aBalance").html().replace(/,/g, "")) {
            layer.msg("余额不足", {
                time: 2000
            });
            return false;
        }

        if ($("#poundage").val() == "") {
            layer.msg("请正确填写手续费", {
                time: 2000
            });
            return false;
        }

        if ($("#useValue").val() == "") {
            layer.msg("请填写资金用途", {
                time: 2000
            });
            return false;
        }
        if ($("#psValue").val() == "") {
            layer.msg("请填写备注", {
                time: 2000
            });
            return false;
        }

        $(".recharge_msg").hide();
        $(".recharge_confirm").show();
    });

    $("#back").click(function() {
        $(".recharge_msg").show();
        $(".recharge_confirm").hide();
    });


    function getAccountMessage(id, tpl, cnt) {
        $.ajax({
                url: '../rest/backadmin/companyAccount/get',
                type: 'get',
                data: {
                    "uuid": id
                }
            })
            .done(function(r) {
                //生成模板加载数据
                if (r.status == "SUCCESS") {
                    var template = $.templates(tpl);
                    var html = template.render(r.data);
                    $(cnt).html(html);
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
    getBalance();

    function getBalance() {
        $.ajax({
                url: '../rest/backadmin/companyAccount/get',
                type: 'get',
                data: {
                    "uuid": uuid
                }
            })
            .done(function(r) {
                //生成模板加载数据
                if (r.status == "SUCCESS") {
                    $(".form_balance").html("余额：" + r.data.accountBalanceCN);
                } else {
                    layer.msg(r.data, {
                        time: 2000
                    });
                }
            })
            .fail(function() {
                layer.msg("error", {
                    time: 2000
                });
            });
    } //getBalance()

    $("#transferValue").bind("input", function() {
        if ($(this).val().length > 10) {
            $(this).val($(this).val().slice(0, 10));
        }
        var bigValue = changeMoneyToChinese($("#transferValue").val());
        var transferValue = parseFloat($("#transferValue").val()).toFixed(10);
        var transferValueV = transferValue.toString();
        $("#transferConfirm").html(formatNum(transferValueV));
        $("#rechargeBigValue").html("人民币：" + bigValue);
        $("#rechargeBigConfirm").html("人民币：" + bigValue);
    });
    $("#useValue").bind("input", function() {
        $("#useConfirm").html($("#useValue").val());
    });
    $("#psValue").bind("input", function() {
        $("#psConfirm").html($("#psValue").val());
    });
    $("#poundage").bind("input", function() {
        if ($(this).val().length > 10) {
            $(this).val($(this).val().slice(0, 10));
        }
        $("#poundageConfirm").html($("#poundage").val());
    });




}); //document.ready;
