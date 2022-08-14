$(document).ready(function() {
    $(".redemption_confirm").show();
    $(".redemption_msg").hide();
    var uuid = url("?uuid");
    var investAllValue = 0;
    var returnAllValue = 0;
    var redeAll = 0;
    var productArr = [];
    var productuuid = "";
    var productList;
    var returnRate = "";
    var term = "";
    var superFlag = "";
    getProductMessage();

    $("#back").click(function() {
        $(".redemption_confirm").hide();
        $(".redemption_msg").show();
        $(".rede_num").prop("disabled", false);
        productArr = [];
    });


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
            $.get('../rest/backadmin/fundInvest/operateCheck?uuid=' + uuid + '&status=' + status + '&reason=' + reason, function(r) {
                if (r.status == 'SUCCESS') {
                    layer.msg('操作成功', {
                        time: 2000
                    }, function() {
                        // getProductMessage();
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
    $(".auditing_check").attr('data-uuid', uuid);

    //获取产品信息表头
    function getProductMessage() {
        $.ajax({
                url: '../rest/backadmin/fundInvest/operateGet',
                type: 'get',
                data: {
                    "uuid": uuid
                }
            })
            .done(function(r) {
                if (r.status == "SUCCESS") {
                    //渲染模板
                    productuuid = r.data.bankFinancialProduct;
                    productList = r;
                    superFlag = r.data.status;
                    $("#realReturnConfirm").html(r.data.totalReturnCN);
                    $("#realReturn").val(r.data.totalReturn);
                    $("#dateConfirm").html(r.data.createtimeCN);
                    $("#dateValue").val(r.data.createtimeCN);
                    $("#psConfirm").html(r.data.dataList[0].remark);
                    $("#psValue").val(r.data.dataList[0].remark);



                    if (r.data.status == "draft" || r.data.status == "unpassed") {
                        $("#allRightConfirm").show();
                    } else {
                        $("#allRightConfirm").hide();
                    }
                    if (r.data.status != "unchecked") {
                        $(".auditing_box").hide();
                    }

                    $("#createName").html(r.data.creatorName);
                    $("#createTime").html(r.data.submittimeCN);
                    $("#reasonMsg").html(r.data.reason);
                    $(".auditing_check").attr("data-uuid", r.data.uuid);
                    $(".auditing_check").eq(0).attr("data-status", "checked");
                    $(".auditing_check").eq(1).attr("data-status", "unpassed");

                    if (r.data.status == "unchecked") {
                        $("#reasonConfirm").hide();
                        $("#reasonValue").show();
                        $(".auditing_check").show();
                    } else {
                        $("#reasonConfirm").show();
                        $("#reasonValue").hide();
                        $(".auditing_check").hide();
                    }
                    if (r.data.status == "checked") {
                        $("#reasonMsg").addClass('color_green');
                        $("#reasonMsg").removeClass('color_red');
                    } else {
                        $("#reasonMsg").addClass('color_red');
                        $("#reasonMsg").removeClass('color_green');
                    }



                } else {
                    layer.msg(r.message, {
                        time: 2000
                    });
                }

                $.ajax({
                        url: '../rest/backadmin/fund/get',
                        type: 'get',
                        data: {
                            uuid: r.data.fund
                        }
                    })
                    .done(function(data) {
                        if (data.status == "SUCCESS") {
                            //渲染模板
                            var template = $.templates("#productNameTpl");
                            var html = template.render(data.data);
                            $("#productNameCnt").html(html);

                            var templates = $.templates("#tableTpl");
                            var htmls = templates.render(r.data.dataList);
                            $("#tableCnt").html(htmls);

                            //计算投资总额
                            $(".investment").each(function(index, el) {
                                investAllValue += parseFloat($(".investment").eq(index).val());
                            });
                            $("#investAll").html("总投资：" + investAllValue.toFixed(2));

                            $('#redeConfirm').html(r.data.totalAmountCN);
                            $('#accountBalanceConfirm').html(r.data.dataList[0].accountBalanceCN);
                            //审核
                            $(".auditing_check").click(function() {
                                checkThis(this);
                            });
                        } else {
                            layer.msg(data.message, {
                                time: 2000
                            });
                        }
                        //收益率赋值
                        returnRate = data.data.targetAnnualizedReturnRate / 100;
                        //期限赋值
                        term = data.data.term / 365;
                        getAccountList(r.data.dataList[0].companyAccount);
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
    } //getProductMessage

    function getAccountList(custodian_uuid) {
        $.ajax({
                url: '../rest/backadmin/companyAccount/get',
                type: 'get',
                data: {
                    "uuid": custodian_uuid
                }
            })
            .done(function(r) {
                if (r.status == "SUCCESS") {
                    $("#accountList").append("<option value=" + r.data.uuid + " data-balance=" + r.data.accountBalance + ">" + r.data.accountName + " " + r.data.accountNumStar + "</option>");
                    var balance = parseFloat($("#accountList").find("option:selected").attr("data-balance"));
                    $("#balance").html("余额：" + balance.toFixed(2));
                    accountBanlance = parseFloat(balance).toFixed(2);
                }
                var companyAccountUUId = $("#accountList").find("option:selected").val();
                // getInvestMessage(companyAccountUUId);






            })
            .fail(function() {
                layer.msg("error", {
                    time: 2000
                });
            });
    } //getAccountList()

    //获取投资列表
    function getInvestMessage(companyAccount, data) {
        $.ajax({
                url: '../rest/backadmin/bankFinancialProductInvest/list',
                type: 'get',
                data: {
                    "companyAccount": companyAccount,
                    "bankFinancialProduct": productuuid,
                    "pageNum": 1,
                    "pageSize": 10000
                }
            })
            .done(function(data) {
                if (data.status == "SUCCESS") {
                    var template = $.templates("#tableTpl");
                    var html = template.render(data.data);
                    $("#tableCnt").html(html);


                } else {
                    layer.msg(data.message, {
                        time: 2000
                    });
                }
                $.each(productList.data.dataList, function(index, el) {
                    $.each(data.data, function(i, e) {
                        if (el.productPublish == e.productPublish) {
                            $(".rede_num").eq(i).val(el.totalAmount);
                        }
                    });
                });
                // 如果同一条赎回审核通过， 本条赎回金额等于accountBalance + 赎回金额
                if (superFlag == "checked") {
                    $.each(data.data, function(index, el) {
                        $.each(productList.data.dataList, function(i, e) {
                            if (el.product == e.product) {
                                var operateBalance = parseFloat(e.accountBalanceCN.replace(/,/g, ""));
                                var this_rede_value = parseFloat($(".rede_num").eq(index).val());
                                $(".accountBalance").eq(index).html((operateBalance + this_rede_value).toFixed(2));
                            }
                        });
                    });
                }






                //计算投资总额
                $(".investment").each(function(index, el) {
                    investAllValue += parseFloat($(".investment").eq(index).val());
                });
                $("#investAll").html("总投资：" + investAllValue.toFixed(2));

                //计算每条预计收益
                $(".totalReturn").each(function(index, el) {
                    var investMent = parseFloat($(".investment").eq(index).val()) * returnRate;
                    var investMentV = (investMent * term).toFixed(10);
                    var investMentS = investMentV.substring(0, investMentV.lastIndexOf('.') + 3);
                    $(".totalReturn").eq(index).html(formatNum(investMentS));
                });




                //计算赎回金额
                $(".rede_num").each(function(index, el) {
                    if ($(".rede_num").eq(index).val() != "") {
                        redeAll += parseFloat($(".rede_num").eq(index).val());
                    }
                });
                $("#redeValue").val(redeAll);
                $("#redeConfirm").html(redeAll);

                var realReturnRate = (parseFloat($("#realReturn").val()) / parseFloat($("#redeValue").val()) / term * 100).toFixed(10);
                var realReturnRateV = realReturnRate.substring(0, realReturnRate.lastIndexOf('.') + 3);
                $("#targetReturnConfirm").html(realReturnRateV + "%");
                $("#targetReturn").val(realReturnRateV + "%");

                $(".rede_num").bind("input", function() {

                    redeAll = 0;
                    $(".rede_num").each(function(index, el) {
                        if ($(".rede_num").eq(index).val() != "") {
                            redeAll += parseFloat($(".rede_num").eq(index).val());
                        }
                    });

                    $("#redeValue").val(redeAll);
                    $("#redeConfirm").html(redeAll);
                });

                // 计算预计收益总额
                var redeAllV = (redeAll * returnRate * term).toFixed(10);
                var redeAllS = redeAllV.substring(0, redeAllV.lastIndexOf('.') + 3);
                $("#returnAll").html(redeAllS);

                //输入实际收益计算收益利率
                $("#realReturn").bind("input", function() {
                    if ($("#realReturn").val() == "") {
                        $("#targetReturn").val("");
                        return false;
                    }
                    // $("#realReturnConfirm").html(parseFloat($("#realReturn").val()).toFixed(2));

                    var realValue = parseFloat($("#realReturn").val()) / parseFloat($("#redeValue").val()) / term;
                    var realReturn = realValue * 100;
                    var realReturnV = realReturn.toFixed(10);
                    var realReturnS = realReturnV.substring(0, realReturnV.lastIndexOf('.') + 3);
                    $("#targetReturn").html(realReturnS + "%");
                    $("#targetReturnConfirm").html(realReturnS + "%");
                });

                $("#psValue").bind("input", function() {
                    $("#psConfirm").html($("#psValue").val());
                });

                $("#allRight").click(function() {
                    productArr = [];
                    var this_rede = 0;
                    var this_invest = 0;
                    $(".rede_num").each(function(index, el) {
                        this_rede = $(".rede_num").eq(index).val();
                        this_invest = $(".investment").eq(index).val();

                        if (parseFloat(this_rede) > parseFloat(this_invest)) {
                            layer.msg("赎回金额大于投资余额", {
                                time: 2000
                            });

                            return false;
                        }
                    });

                    if ($("#dateValue").val() == '') {
                        layer.msg("请填写赎回时间", {
                            time: 2000
                        });
                        return false;
                    }
                    if ($("#redeValue").val() == "") {
                        layer.msg("请填写赎回金额", {
                            time: 2000
                        });
                        return false;
                    }

                    if (parseFloat($("#realReturn").val()) > parseFloat($("#redeValue").val())) {
                        $("#targetReturn").val("");
                        layer.msg("实际收益不能大于赎回金额", {
                            time: 2000
                        });
                        return false;
                    }

                    if ($("#realReturn").val() == "") {
                        layer.msg("请填写实际收益", {
                            time: 2000
                        });
                        return false;
                    }

                    $("#dateConfirm").html($("#dateValue").val());

                    //向数组添加uuid_value
                    $(".rede_num").each(function(index, el) {
                        if ($(".rede_num").eq(index).val() != "") {
                            productArr.push($(".rede_num").eq(index).parent().siblings('.productuuid').val() + "_" + $(".rede_num").eq(index).val());
                        }
                    });
                    console.log(productArr);

                    var data = "";
                    data += "redeemTime=" + $("#dateValue").val() + "&";
                    data += "companyAccount=" + $("#accountList").find("option:selected").val() + "&";
                    data += "bankFinancialProduct=" + uuid + "&";
                    data += "totalReturn=" + parseFloat($("#realReturn").val()).toFixed(2) + "&";
                    data += "remark=" + $("#psValue").val() + "&";
                    data += "uuid=" + uuid + "&";
                    $.each(productArr, function(index, el) {
                        data += "data=" + el + "&";
                    });
                    console.log(parseFloat($(".realReturn").val()).toFixed(2));
                    $.ajax({
                            url: '../rest/backadmin/fundInvest/operateEdit?',
                            type: 'post',
                            data: data
                        })
                        .done(function(r) {
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
                            }
                        })
                        .fail(function() {
                            layer.msg("error", {
                                time: 2000
                            });
                        });
                });
            })
            .fail(function() {
                layer.msg("error", {
                    time: 2000
                });
            });
    } //getInvestMessage()

    $("#allRightConfirm").click(function() {
        $(".redemption_confirm").hide();
        $(".redemption_msg").show();
        $(".rede_num").prop("disabled", false);
    });


    //获取平台账户余额
    // function getBalance() {
    //     $.ajax({
    //             url: '../rest/backadmin/companyAccount/getPlatformBalance',
    //             type: 'get'
    //         })
    //         .done(function(r) {
    //             var template = $.templates("#balanceTpl");
    //             var html = template.render(r.data);
    //             $("#tableCnt").append(html);
    //
    //
    //         })
    //         .fail(function() {
    //             layer.msg("error", {
    //                 time: 2000
    //             });
    //         });
    // }
}); //document.ready()