$(document).ready(function() {
    var uuid = url("?uuid");
    var flag;
    getAccountMessage();


    $("#allRight").click(function() {
        $("#allRight").prop("disabled", true);
        if ($("#rechargeValue").val() == "") {
            layer.msg("平台手续费率填写有误", {
                time: 2000
            });
            $("#allRight").prop("disabled", false);
            return false;
        }

        $("#fee").val(Number($("#rechargeValue").val()) / 100);
        $("#rechargeSubmit").ajaxSubmit(function(r) {
            if (r.status == "SUCCESS") {
                layer.msg(r.data, {
                    time: 2000
                }, function() {
                    window.location.href = document.referrer;
                });

            } else {
                layer.msg(r.message, {
                    time: 2000
                });
                $("#allRight").prop("disabled", false);

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
                url: '../rest/backadmin/qcbcompany/operateFeeGet',
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
                flag = r.data.newData.type;

                if (r.data.status == "draft" || r.data.status == "unpassed") {
                    $("#allRightConfirm").show();
                } else {
                    $("#allRightConfirm").hide();
                }
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