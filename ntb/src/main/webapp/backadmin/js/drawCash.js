$(document).ready(function() {
    var uuid = url("?uuid");
    $("#companyAccountOut").val(uuid);



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
        if ($("#transferValue").val() == "") {
            layer.msg("请正确填写提现金额", {
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
                $("#allRightConfirm").prop("disabled", false);
                layer.msg(r.message, {
                    time: 2000
                });
            }
        });
    });
    getAccountMessage();

    function getAccountMessage() {
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
                    var template = $.templates("#thirdTpl");
                    var html = template.render(r.data);
                    $("#thirdCnt").html(html);
                } else {
                    layer.msg(r.message, {
                        time: 2000
                    });
                }

                //请求绑定账户信息
                $.ajax({
                        url: '../rest/backadmin/companyAccount/get',
                        type: 'get',
                        data: {
                            "uuid": r.data.bindAccount
                        }
                    })
                    .done(function(data) {
                        //生成模板加载数据
                        if (data.status == "SUCCESS") {
                            var template = $.templates("#targetTpl");
                            var html = template.render(data.data);
                            $("#targetCnt").html(html);
                        } else {
                            layer.msg(data.message, {
                                time: 2000
                            });
                        }
                    })
                    .fail(function() {
                        layer.msg("error", {
                            time: 2000
                        });
                    });


            })
            .fail(function() {
                layer.msg("error", {
                    time: 2000
                });
            });
    } //getAccountMessage()


    $("#transferValue").bind("input", function() {
        var bigValue = changeMoneyToChinese($("#transferValue").val());
        $("#transferConfirm").html($("#transferValue").val());
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
        $("#poundageConfirm").html($("#poundage").val());
    });




}); //document.ready;
