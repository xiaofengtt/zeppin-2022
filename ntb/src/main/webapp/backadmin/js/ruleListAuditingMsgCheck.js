$(document).ready(function() {
    var uuid = url("?uuid");
    $("#statusConfirm").hide();
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
            $.get('../rest/backadmin/couponStrategy/operateCheck?uuid=' + uuid + '&status=' + status + '&reason=' + reason, function(r) {
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
                url: '../rest/backadmin/couponStrategy/operateGet',
                type: 'get',
                data: {
                    "uuid": uuid
                }
            })
            .done(function(r) {
                if (r.status == "SUCCESS") {
                    if (r.data.type == "delete") {
                        $("#strategyIdentification").html(r.data.oldData.strategyIdentification);
                        $("#expiryDate").html(r.data.oldData.expiryDateCN);
                        $("#createTime").html(r.data.submittimeCN);
                        $("#createName").html(r.data.creatorName);
                        $('.auditing_check').attr("data-uuid", r.data.uuid);
                        if (r.data.status == "checked" || r.data.status == "unpassed") {
                            $("#reasonMsg").html(r.data.reason);
                            $("#reasonConfirm").show();
                            $("#reasonValue").hide();
                            $(".auditing_check").hide();
                            if (r.data.status == "checked") {
                                $("#reasonMsg").addClass('color_green');
                            } else {
                                $("#reasonMsg").addClass('color_red');
                            }
                        } else {
                            $("#reasonValue").show();
                            $("#reasonConfirm").hide();
                        }

                        if (r.data.type == "close" || r.data.type == "open" || r.data.type == "delete") {

                            if (r.data.type == "close") {
                                $("#statusMsg").html("关闭");
                            } else if (r.data.type == "open") {
                                $("#statusMsg").html("开启");
                            } else {
                                $("#statusMsg").html("删除");
                            }
                            $("#statusConfirm").show();

                        }

                        var template = $.templates("#queboxTpl");
                        var html = template.render(r.data.oldData.couponMap.couponList);
                        $("#queboxCnt").html(html);
                    } else {
                        $("#strategyIdentification").html(r.data.newData.strategyIdentification);
                        $("#expiryDate").html(r.data.newData.expiryDateCN);
                        $("#createTime").html(r.data.submittimeCN);
                        $("#createName").html(r.data.creatorName);
                        $('.auditing_check').attr("data-uuid", r.data.uuid);
                        if (r.data.status == "checked" || r.data.status == "unpassed") {
                            $("#reasonMsg").html(r.data.reason);
                            $("#reasonConfirm").show();
                            $("#reasonValue").hide();
                            $(".auditing_check").hide();
                            if (r.data.status == "checked") {
                                $("#reasonMsg").addClass('color_green');
                            } else {
                                $("#reasonMsg").addClass('color_red');
                            }
                        } else {
                            $("#reasonValue").show();
                            $("#reasonConfirm").hide();
                        }

                        if (r.data.type == "close" || r.data.type == "open" || r.data.type == "delete") {

                            if (r.data.type == "close") {
                                $("#statusMsg").html("关闭");
                            } else if (r.data.type == "open") {
                                $("#statusMsg").html("开启");
                            } else {
                                $("#statusMsg").html("删除");
                            }
                            $("#statusConfirm").show();

                        }

                        var template = $.templates("#queboxTpl");
                        var html = template.render(r.data.newData.couponMap.couponList);
                        $("#queboxCnt").html(html);
                    }
                } else {
                    layer.msg(r.status, {
                        time: 2000
                    });
                }

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


                    if ($("#strategyIdentification").val() == "") {
                        layer.msg("投放ID不能为空", {
                            time: 2000
                        });
                        $("#add_submit").prop("disabled", false);
                        return false;
                    }

                    if ($("#expiryDate").val() == "") {
                        layer.msg("截止时间不能为空", {
                            time: 2000
                        });
                        $("#add_submit").prop("disabled", false);
                        return false;
                    }

                    if (cardListArr.length == 0) {
                        layer.msg("请选择添加优惠券", {
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