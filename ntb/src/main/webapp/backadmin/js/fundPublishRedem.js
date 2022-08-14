$(document).ready(function() {
    $(".redemption_confirm").hide();
    var uuid = url("?uuid");
    var investAllValue = 0;
    var returnAllValue = 0;
    var redeAll = 0;
    var productArr = [];
    var returnRate = "";
    var term = "";
    var term_ = "";

    getProductMessage();

    $("#back").click(function() {
        $(".redemption_confirm").hide();
        $(".redemption_msg").show();
        $(".rede_num").prop("disabled", false);
        productArr = [];
    });



    //获取产品信息表头
    function getProductMessage() {
        $.ajax({
                url: '../rest/backadmin/fund/get',
                type: 'get',
                data: {
                    "uuid": uuid
                }
            })
            .done(function(r) {
                if (r.status == "SUCCESS") {
                    //渲染模板
                    var template = $.templates("#productNameTpl");
                    var html = template.render(r.data);
                    $("#productNameCnt").html(html);
                } else {
                    layer.msg(r.message, {
                        time: 2000
                    });
                }

                //收益率赋值
                returnRate = r.data.targetAnnualizedReturnRate / 100;
                //期限赋值
                term = r.data.term / 365;
                term_ = r.data.term;
                console.log(term);
                $(".current").html("【" + r.data.gpName + "】" + r.data.gpName);
                getAccountList(r.data.gp);
            })
            .fail(function() {
                layer.msg("error", {
                    time: 2000
                });
            });
    } //getProductMessage

    function getAccountList(custodian_uuid) {
        $.ajax({
                url: '../rest/backadmin/companyAccount/list',
                type: 'get',
                data: {
                    "pageSize": '1000',
                    "pageNum": '1',
                    "bank": custodian_uuid
                }
            })
            .done(function(r) {
                if (r.status == "SUCCESS") {
                    if (r.data.length == 0) {
                        layer.msg("没有可选择的账户", {
                            time: 2000
                        }, function() {
                            //                            window.close();
                            window.location.href = document.referrer;
                        });
                    } else {
                        $.each(r.data, function(index, el) {
                            $("#accountList").append("<option value=" + el.uuid + " data-balance=" + el.accountBalance + ">" + el.accountName + " " + el.accountNum + "</option>");
                        });

                    }
                }
                var balance = $("#accountList").find("option:selected").attr("data-balance");
                var companyAccountUUId = $("#accountList").find("option:selected").val();
                accountBanlance = parseFloat(balance);
                $("#balance").html("余额：" + accountBanlance.toFixed(2));
                getInvestMessage(companyAccountUUId);

                $("#accountList").change(function() {
                    var balance = $(this).find("option:selected").attr("data-balance");
                    var companyAccountUUId = $(this).find("option:selected").val();
                    accountBanlance = parseFloat(balance);
                    $("#balance").html("余额：" + accountBanlance.toFixed(2));
                    getInvestMessage(companyAccountUUId);

                });




            })
            .fail(function() {
                layer.msg("error", {
                    time: 2000
                });
            });
    } //getAccountList()

    //获取投资列表
    function getInvestMessage(companyAccount) {
        $.ajax({
                url: '../rest/backadmin/fundInvest/investList',
                type: 'get',
                data: {
                    "fund": uuid,
                    "pageNum": 1,
                    "pageSize": 10000
                }
            })
            .done(function(r) {
                if (r.status == "SUCCESS") {
                    var template = $.templates("#tableTpl");
                    var html = template.render(r);
                    $("#tableCnt").html(html);
                    $('.fund-tr').each(function(index, el) {
                        var thisId = $(el).find('.productuuid').val();
                        $(el).find('td').eq(1).html(r.data[thisId]);
                        $(el).find('.investment').val(r.data[thisId]);
                    });
                } else {
                    layer.msg(r.message, {
                        time: 2000
                    });
                }





                //计算每条预计收益
                $(".totalReturn").each(function(index, el) {
                    var investMent = parseFloat($(".investment").eq(index).val()) * returnRate;
                    var investMentV = (investMent * term).toFixed(10);
                    var investMentS = investMentV.substring(0, investMentV.lastIndexOf('.') + 3);
                    $(".totalReturn").eq(index).html(formatNum(investMentS));

                });

                //计算投资总额
                $(".investment").each(function(index, el) {
                    investAllValue += parseFloat($(".investment").eq(index).val());
                });
                $("#investAll").html("总投资：" + investAllValue.toFixed(2));


                //计算赎回金额
                $(".rede_num").each(function(index, el) {
                    if ($(".rede_num").eq(index).val() != "") {
                        redeAll += parseFloat($(".rede_num").eq(index).val());
                    }
                });
                $("#redeValue").html(redeAll);
                $("#redeConfirm").html(redeAll.toFixed(2));

                // 计算预计收益总额
                var redeAllV = (redeAll * returnRate * term).toFixed(10);
                var redeAllS = redeAllV.substring(0, redeAllV.lastIndexOf('.') + 3);
                $("#returnAll").html(redeAllS);


                $(".rede_num").bind("input", function() {

                    redeAll = 0;

                    // if ($("#realReturn").val() == "") {
                    //     $("#targetReturn").val("");
                    //     return false;
                    // }
                    // $("#realReturnConfirm").html(parseFloat($("#realReturn").val()).toFixed(2));


                    $(".rede_num").each(function(index, el) {
                        if ($(".rede_num").eq(index).val() != "") {
                            redeAll += parseFloat($(".rede_num").eq(index).val());
                        }
                    });

                    $("#redeValue").html(redeAll);
                    $("#redeConfirm").html(redeAll);

                    var realValue = parseFloat($("#realReturn").val()) / parseFloat($("#redeValue").html()) / term_ * 365;
                    var realReturn = realValue * 100;
                    var realReturnV = realReturn.toFixed(10);
                    var realReturnS = realReturnV.substring(0, realReturnV.lastIndexOf('.') + 3);
                    $("#targetReturn").html(realReturnS + "%");
                    $("#targetReturnConfirm").html(realReturnS + "%");

                    // 计算预计收益总额
                    var redeAllV = (redeAll * returnRate * term).toFixed(10);
                    var redeAllS = redeAllV.substring(0, redeAllV.lastIndexOf('.') + 3);
                    $("#returnAll").html(redeAllS);
                });

                //输入实际收益计算收益利率
                $("#realReturn").bind("input", function() {
                    if ($("#realReturn").val() == "") {
                        $("#targetReturn").html("");
                        return false;
                    }
                    $("#realReturnConfirm").html(parseFloat($("#realReturn").val()).toFixed(2));

                    var realValue = parseFloat($("#realReturn").val()) / parseFloat($("#redeValue").html()) / term_ * 365;
                    var realReturn = realValue * 100;
                    var realReturnV = realReturn.toFixed(10);
                    var realReturnS = realReturnV.substring(0, realReturnV.lastIndexOf('.') + 3);
                    $("#targetReturn").html(realReturnS + "%");
                    $("#targetReturnConfirm").html(realReturnS + "%");
                });

                $("#psValue").bind("input", function() {
                    $("#psConfirm").html($("#psValue").val());
                });

                $("#accountBalanceValue").bind('input', function() {
                    $('#accountBalanceConfirm').html($('#accountBalanceValue').val());
                });

                $("#allRight").click(function() {
                    var this_rede = 0;
                    var this_invest = 0;
                    var flag = true;
                    $(".rede_num").each(function(index, el) {
                        this_rede = $(".rede_num").eq(index).val();
                        this_invest = $(".investment").eq(index).val();

                        if (parseFloat(this_rede) > parseFloat(this_invest)) {
                            layer.msg("赎回金额大于投资余额", {
                                time: 2000
                            });
                            flag = false;
                            productArr = [];
                            return false;
                        } else {
                            flag = true;
                        }
                    });
                    if ($("#redeValue").html() == "" || parseFloat($("#redeValue").html()) <= 0) {
                        layer.msg("请正确填写赎回金额", {
                            time: 2000
                        });
                        productArr = [];
                        return false;
                    }


                    //向数组添加uuid_value
                    $(".rede_num").each(function(index, el) {
                        if ($(".rede_num").eq(index).val() != "") {
                            productArr.push($(".rede_num").eq(index).parent().siblings('.productuuid').val() + "_" + $(".rede_num").eq(index).val());
                        }
                    });
                    console.log(productArr);
                    if (flag == true) {
                        $(".rede_num").prop("disabled", true);
                        $(".redemption_confirm").show();
                        $(".redemption_msg").hide();
                    }

                });
            })
            .fail(function() {
                layer.msg("error", {
                    time: 2000
                });
            });
    } //getInvestMessage()

    $("#allRightConfirm").click(function() {
        var data = "";
        data += "companyAccount=" + $("#accountList").find("option:selected").val() + "&";
        data += "fund=" + uuid + "&";
        data += "remark=" + $("#psValue").val() + "&";
        data += "accountBalance=" + $("#accountBalanceValue").val() + "&";
        data += "CSRFToken=" + $('input[name="CSRFToken"]').val() + "&";

        $.each(productArr, function(index, el) {
            data += "data=" + el + "&";
        });
        $.ajax({
                url: '../rest/backadmin/fundInvest/redeem?',
                type: 'post',
                data: data
            })
            .done(function(r) {
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
                }
            })
            .fail(function() {
                layer.msg("error", {
                    time: 2000
                });
            });


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