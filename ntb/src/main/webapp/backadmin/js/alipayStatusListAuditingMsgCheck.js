$(document).ready(function() {
    var uuid = url("?uuid");
    //获取企业账户信息
    get();



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
            $.get('../rest/backadmin/bkpayment/operateCheck?uuid=' + uuid + '&status=' + status + '&reason=' + reason, function(r) {
                if (r.status == 'SUCCESS') {
                    layer.msg('操作成功', {
                        time: 2000
                    }, function() {
                        // getAccountMessageList();
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
                // if (r.data.status == "unchecked" || r.data.status == "checked") {
                //     $("#edit").hide();
                // }
                $("#edit").click(function() {
                    $("#account_message_Cnt").hide();
                    $(".edit").show();
                });
                $("#close").click(function() {
                    $("#account_message_Cnt").show();
                    $(".edit").hide();
                });

                //审核
                $(".auditing_check").click(function() {
                    checkThis(this);
                });



                $("#add_submit").click(function() {
                    $("#add_submit").prop("disabled", true);

                    if ($("#companyName").val() == "") {
                        layer.msg("开户名不能为空", {
                            time: 2000
                        });
                        $("#add_submit").prop("disabled", false);
                        return false;
                    }

                    if ($("#accountName").val() == "") {
                        layer.msg("开户别名不能为空", {
                            time: 2000
                        });
                        $("#add_submit").prop("disabled", false);
                        return false;
                    }
                    if ($("#accountNum").val() == "") {
                        layer.msg("账号不能为空", {
                            time: 2000
                        });
                        $("#add_submit").prop("disabled", false);
                        return false;
                    }

                    if ($("#type").val() == "0") {
                        layer.msg("请选择账户类型", {
                            time: 2000
                        });
                        $("#add_submit").prop("disabled", false);
                        return false;
                    }
                    if ($("#status").val() == "0") {
                        layer.msg("请选择状态", {
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
                                // window.location.href = "./accountMessageAuditing.jsp";
                                window.close();
                            });
                        } else {
                            layer.msg(data.data, {
                                time: 2000
                            });
                            $("#add_submit").prop("disabled", false);
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