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



    //获取产品信息表头
    function getProductMessage() {
        $.ajax({
                url: '../rest/backadmin/bankFinancialProductInvest/operateGet',
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

                    superFlag = r.data.status;


                    $.getScript("./js/uploadFile.js", function() {
                        $(".ajax-file-upload").css({
                            "overflow": "default"
                        });
                        $("#delete").unbind("click").click(function() {
                            // $(".ajax-file-upload").find(".uploadImg").remove();
                            layer.confirm("确定要删除吗？", function(index) {
                                $(".ajax-file-upload").find(".uploadSrc").remove();
                                $(".fileBox").find(".uploadSrc").remove();
                                fileArr = [];
                                $('input[name="receipt"]').val("");
                                console.log(fileArr + "," + $('input[name="receipt"]').val());
                                layer.close(index);
                            });
                        });
                        if (r.data.listReceipt.length == 0) {
                            $("#delete").hide();
                        }
                        $.each(r.data.listReceipt, function(index, el) {
                            fileArr.push(el.uuid);
                            $('input[name="receipt"]').val(fileArr.join(','));
                            console.log(fileArr);
                            $(".ajax-file-upload").prepend(
                                "<a class='uploadSrc' href=../" + el.url + " target='_blank'><img src=../" + el.url + " class='uploadImg'></a>"
                            );
                            $(".fileBox").prepend(
                                "<a class='uploadSrc' href=../" + el.url + " target='_blank'><img src=../" + el.url + " class='uploadImg'></a>"
                            );
                        });
                        hehe = false;
                    });
                } else {
                    layer.msg(r.message, {
                        time: 2000
                    });
                }

                $.ajax({
                        url: '../rest/backadmin/bankFinancialProduct/get',
                        type: 'get',
                        data: {
                            uuid: r.data.bankFinancialProduct
                        }
                    })
                    .done(function(data) {
                        if (data.status == "SUCCESS") {
                            //渲染模板
                            var template = $.templates("#productNameTpl");
                            var html = template.render(data.data);
                            $("#productNameCnt").html(html);
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
                getInvestMessage(companyAccountUUId);






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
                //如果同一条赎回审核通过，本条赎回金额等于accountBalance+赎回金额
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
                };
                ! function() {

                    //laydate.skin('molv');

                    laydate({
                        elem: '#dateValue',
                        format: 'YYYY-MM-DD hh:mm:ss',
                        istime: true
                    });

                }();



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

                $(".totalReturn").each(function(index, el) {
                    if (isNaN($(".totalReturn").eq(index).html()) == false) {
                        returnAllValue += parseFloat($(".totalReturn").eq(index).html());
                    }

                });


                //计算赎回金额
                $(".rede_num").each(function(index, el) {
                    if ($(".rede_num").eq(index).val() != "") {
                        redeAll += parseFloat($(".rede_num").eq(index).val());
                    }
                });
                // 计算预计收益总额
                var redeAllV = (redeAll * returnRate * term).toFixed(10);
                var redeAllS = redeAllV.substring(0, redeAllV.lastIndexOf('.') + 3);
                $("#returnAll").html(redeAllS);

                $("#redeValue").html(redeAll);
                $("#redeConfirm").html(redeAll);

                var realReturnRate = (parseFloat($("#realReturn").val()) / parseFloat($("#redeValue").html()) / term * 100).toFixed(10);
                var realReturnRateV = realReturnRate.substring(0, realReturnRate.lastIndexOf('.') + 3);
                $("#targetReturnConfirm").html(realReturnRateV + "%");
                $("#targetReturn").html(realReturnRateV + "%");



                $(".rede_num").bind("input", function() {

                    redeAll = 0;
                    $(".rede_num").each(function(index, el) {
                        if ($(".rede_num").eq(index).val() != "") {
                            redeAll += parseFloat($(".rede_num").eq(index).val());
                        }
                    });

                    $("#redeValue").html(redeAll);
                    $("#redeConfirm").html(redeAll.toFixed(2));


                    var realValue = parseFloat($("#realReturn").val()) / parseFloat($("#redeValue").val()) / term;
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
                    // $("#realReturnConfirm").html(parseFloat($("#realReturn").val()).toFixed(2));

                    var realValue = parseFloat($("#realReturn").val()) / parseFloat($("#redeValue").html()) / term;
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
                    $("#allRight").prop("disabled", true);
                    productArr = [];
                    var this_rede = 0;
                    var this_invest = 0;
                    var flag = true;
                    $(".rede_num").each(function(index, el) {
                        this_rede = $(".rede_num").eq(index).val();
                        this_invest = $(".investment").eq(index).val();
                        if (this_rede == "") {
                            layer.msg("请正确填写赎回金额", {
                                time: 2000
                            });
                            flag = false;
                            productArr = [];
                            return false;
                        } else {
                            flag = true;
                        }
                        if (parseFloat(this_rede) > parseFloat(this_invest)) {
                            layer.msg("赎回金额大于投资余额", {
                                time: 2000
                            });
                            flag = false;
                            $("#allRight").prop("disabled", false);
                            productArr = [];
                            return false;
                        } else {
                            flag = true;
                        }
                    });

                    if ($("#dateValue").val() == '') {
                        layer.msg("请填写赎回时间", {
                            time: 2000
                        });
                        productArr = [];
                        $("#allRight").prop("disabled", false);
                        return false;
                    }
                    if ($("#redeValue").html() == "" || parseFloat($("#redeValue").html()) <= 0) {
                        layer.msg("请填写赎回金额", {
                            time: 2000
                        });
                        productArr = [];
                        $("#allRight").prop("disabled", false);
                        return false;
                    }

                    if (parseFloat($("#realReturn").val()) > parseFloat($("#redeValue").html())) {
                        $("#targetReturn").html("");
                        layer.msg("实际收益不能大于赎回金额", {
                            time: 2000
                        });
                        productArr = [];
                        $("#allRight").prop("disabled", false);
                        return false;
                    }

                    if ($("#realReturn").val() == "") {
                        layer.msg("请填写实际收益", {
                            time: 2000
                        });
                        productArr = [];
                        $("#allRight").prop("disabled", false);
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
                    if (flag == true) {
                        var data = "";
                        data += "redeemTime=" + $("#dateValue").val() + "&";
                        data += "companyAccount=" + $("#accountList").find("option:selected").val() + "&";
                        data += "bankFinancialProduct=" + uuid + "&";
                        data += "totalReturn=" + parseFloat($("#realReturn").val()).toFixed(2) + "&";
                        data += "remark=" + $("#psValue").val() + "&";
                        data += "uuid=" + uuid + "&";
                        data += "receipt=" + $('input[name="receipt"]').val() + "&";
                        data += "CSRFToken=" + $('input[name="CSRFToken"]').val() + "&";
                        $.each(productArr, function(index, el) {
                            data += "data=" + el + "&";
                        });
                        console.log(parseFloat($(".realReturn").val()).toFixed(2));
                        $.ajax({
                                url: '../rest/backadmin/bankFinancialProductInvest/operateEdit?',
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
                                    $("#allRight").prop("disabled", false);
                                }
                            })
                            .fail(function() {
                                layer.msg("error", {
                                    time: 2000
                                });
                                $("#allRight").prop("disabled", false);
                            });
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