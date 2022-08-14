$(document).ready(function() {
    var uuid = url("?uuid");
    //获取企业账户信息
    get();





    function get() {

        $.ajax({
                url: '../rest/backadmin/bkpayment/operateGet',
                type: 'get',
                data: {
                    "uuid": uuid
                }
            })
            .done(function(r) {
                if (r.status == "SUCCESS") {
                    $("#totalAmount").html(r.data.investmentCN);
                    var template = $.templates("#account_message_tpl");
                    var html = template.render(r.data);
                    var template_ = $.templates("#accountMessageEditTpl");
                    var html_ = template_.render(r.data);
                    $("#account_message_Cnt").html(html);
                    $("#accountMessageEditCnt").html(html_);






                } else {
                    layer.msg(r.status, {
                        time: 2000
                    });
                }

                if (r.data.status == "unchecked" || r.data.status == "checked") {
                    $("#edit").hide();
                }
                $("#edit").click(function() {
                    $("#account_message_Cnt").hide();
                    $(".edit").show();
                });
                $("#close").click(function() {
                    $("#account_message_Cnt").show();
                    $(".edit").hide();
                });

                $("#add_submit").click(function() {
                    $("#add_submit").prop("disabled", true);

                    if ($("#paymentName").val() == "") {
                        layer.msg("名称不能为空", {
                            time: 2000
                        });
                        $("#add_submit").prop("disabled", false);
                        return false;
                    }

                    if ($("#paymentDes").val() == "") {
                        layer.msg("描述不能为空", {
                            time: 2000
                        });
                        $("#add_submit").prop("disabled", false);
                        return false;
                    }



                    $("#add").ajaxSubmit(function(data) {
                        if (data.status == "SUCCESS") {
                            layer.msg(data.data, {
                                time: 2000
                            }, function() {
                                window.location.href = document.referrer;
                                // window.close();
                            });
                        } else {
                            layer.msg(data.message, {
                                time: 2000
                            });
                            $("#add_submit").prop("disabled", false);
                            $("#type").prop("disabled", true);
                        }
                    });
                });


            })
            .fail(function() {
                layer.msg("error", {
                    time: 2000
                });
            });
    } //getAccountMessage();
});
