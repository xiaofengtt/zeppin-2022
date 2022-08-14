$(document).ready(function() {
    var uuid = url("?uuid");

    getAccountMessage();

    $("#companyAccountIn").val(uuid);

    function checkThis(t) {
        var reason = $('#reason').val();
        layer.confirm('确定要变更状态吗?', function(index) {
            if (flagSubmit == false) {
                return false;
            }
            flagSubmit = false;
            setTimeout(function() {
                flagSubmit = true;
            }, 3000);
            var obj = $(t);
            var uuid = obj.attr('data-uuid');
            var status = obj.attr('data-status');
            $.get('../rest/backadmin/companyAccountTransfer/operateCheck?uuid=' + uuid + '&status=' + status + '&reason=' + reason, function(r) {
                if (r.status == 'SUCCESS') {
                    layer.msg('操作成功', {
                        time: 2000
                    }, function() {
                        getAccountMessage();
                        window.location.href = document.referrer;
                    });
                } else {
                    alert('操作失败,' + r.message);
                }
            });
            layer.close(index);
        });
        return false;
    }

    $("#allRight").click(function() {
        if ($("#rechargeValue").val() == "") {
            layer.msg("请填写充值金额", {
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
        $("#rechargeSubmit").ajaxSubmit(function(r) {
            if (r.status == "SUCCESS") {
                layer.msg(r.data, {
                    time: 2000
                }, function() {
                    window.location.href = document.referrer;
                });

            } else {
                layer.msg(r.data, {
                    time: 2000
                });
            }
        });
    });

    $("#back").click(function() {
        window.location.reload();
    });

    $("#allRightConfirm").click(function() {
        $(".recharge_msg").show();
        $(".recharge_confirm").hide();

    });

    function getAccountMessage() {
        $.ajax({
                url: '../rest/backadmin/companyAccountTransfer/operateGet',
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

                    var template_ = $.templates("#msgTpl");
                    var html_ = template_.render(r.data);
                    $("#msgCnt").html(html_);

                    var template_v = $.templates("#valueTpl");
                    var html_v = template_v.render(r.data);
                    $("#valueCnt").html(html_v);
                } else {
                    layer.msg(r.message, {
                        time: 2000
                    });
                }
                //审核
                $(".auditing_check").click(function() {
                    checkThis(this);
                });
                if (r.data.status == "draft" || r.data.status == "unpassed") {
                    $("#allRightConfirm").show();
                } else {
                    $("#allRightConfirm").hide();
                }

                var bigPrice = changeMoneyToChinese($("#rechargeConfirm").html().replace(/,/g, ""));
                $("#rechargeBigConfirm").html("人民币：" + bigPrice);


                var bigPriceValue = changeMoneyToChinese($("#rechargeValue").val());
                $("#rechargeBigValue").html("人民币：" + bigPriceValue);
                $("#rechargeValue").bind("input", function() {
                    var bigValue = changeMoneyToChinese($("#rechargeValue").val());
                    $("#rechargeBigValue").html("人民币：" + bigValue);
                    // $("#rechargeBigConfirm").html("人民币：" + bigValue);
                });
            })
            .fail(function() {
                layer.msg("error", {
                    time: 2000
                });
            });
    } //getAccountMessage()


    // $("#useValue").bind("input", function() {
    //     $("#useConfirm").html($("#useValue").val());
    // });
    // $("#psValue").bind("input", function() {
    //     $("#psConfirm").html($("#psValue").val());
    // });




}); //document.ready;