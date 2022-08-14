$(document).ready(function() {
    var uuid = url("?uuid");
    var productArr = [];
    var productTable = [];
    var flag = true;
    var allPrice = 0;
    var accountBanlance = 0;
    var this_Amount;
    var operateList;
    productList = [];
    $(".invest_confirm_box").show();
    $(".product_msg").hide();
    getProductMessage();


    //../rest/backadmin/bankFinancialProduct/get 请求表头  uuid==参数名：bankFinancialProduct
    //../rest/backadmin/companyAccount/get       请求与表头对应的 募集户 uuid == 参数名：data.dataList[0].companyAccount
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
                operateList = r.data.dataList;
                getInvestMessage();
                var template = $.templates("#tableConfirmTpl");
                var html = template.render(r.data);
                $("#tableConfirmCnt").html(html);
                var allAmount = 0;
                $(".publishAmount").each(function(i, el) {
                    allAmount += parseFloat($(".publishAmount").eq(i).val());
                    $(".all_num").html(allAmount.toFixed(2));
                });

                if (r.data.status == "draft" || r.data.status == "unpassed") {
                    $("#invest").show();
                } else {
                    $("#invest").hide();
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
                        } else {
                            layer.msg(data.message, {
                                time: 2000
                            });
                        }
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
                    var balanceV = parseFloat(balance).toFixed(10);
                    var balanceS = balanceV.substring(0, balanceV.lastIndexOf('.') + 3);
                    $("#balance").html("余额：" + balanceS);
                    accountBanlance = parseFloat(balance);
                }
                // $("#accountList").change(function() {
                //     var balance = $(this).find("option:selected").attr("data-balance");
                //     $("#balance").html("余额：" + balance);
                //     accountBanlance = parseFloat(balance);
                // });

            })
            .fail(function() {
                layer.msg("error", {
                    time: 2000
                });
            });
    } //getAccountList()

    //投资明细列表
    function getInvestMessage() {
        $.ajax({
                url: '../rest/backadmin/fundPublish/getAccount',
                type: 'get'
            })
            .done(function(r) {
                if (r.status == "SUCCESS") {
                    var template = $.templates("#tableTpl");
                    var html = template.render(r.data);
                    $("#tableCnt").html(html);



                } else {
                    layer.msg(r.message, {
                        time: 2000
                    });
                }

                getBalance();



            })
            .fail(function() {
                layer.msg("error", {
                    time: 2000
                });
            });
    } //getInvestMessage()

    //获取账户平台余额
    function getBalance() {
        $.ajax({
                url: '../rest/backadmin/companyAccount/getPlatformBalance',
                type: 'get'
            })
            .done(function(r) {
                var template = $.templates("#balanceTpl");
                var html = template.render(r.data);
                $("#tableCnt").append(html);

                // var List = [];
                // $.each(operateList, function(index, el) {
                //     var flag = true;
                //     $(".uuid").each(function(i, e) {
                //
                //         if (el.productPublish == $(".uuid").eq(i).val()) {
                //             flag = false;
                //
                //         }
                //     });
                //     if (flag == true) {
                //         productList.push(el);
                //     }
                //     // List += productList;
                //
                // });
                // // productList = List;
                // console.log(productList);
                // var templates = $.templates("#tablesTpl");
                // var htmls = templates.render(productList);
                // $("#tableCnt").prepend(htmls);


                $("#poundageValue").bind("input", function() {
                    if ($(this).val().length > 10) {
                        $(this).val($(this).val().slice(0, 10));
                    }
                    $("#poundageConfirm").html($("#poundageValue").val());
                });
                $("#psValue").bind("input", function() {
                    $("#psConfirm").html($("#psValue").val());
                });

                //点击复选框解除表单禁用,更改总计
                $(".check").change(function() {
                    allPrice = 0;
                    if ($(this).is(":checked")) {
                        $(this).parent().parent().find(".balance_num").prop("disabled", false);
                    } else {
                        $(this).parent().parent().find(".balance_num").prop("disabled", true);
                    }
                    $(".balance_num").each(function(i, el) {
                        if ($(".balance_num").eq(i).prop("disabled") != true && $(".balance_num").eq(i).val() != "") {
                            allPrice += parseFloat($(".balance_num").eq(i).val());
                        }
                    });
                    $(".all_num").html(allPrice.toFixed(2));

                    flag = true;
                    productArr = [];
                    productTable = [];
                });

                //输入投资金额修改总计
                $(".balance_num").bind("input", function() {
                    if ($(this).val().length > 10) {
                        $(this).val($(this).val().slice(0, 10));
                    }
                    allPrice = 0;
                    $(".balance_num").each(function(i, el) {
                        if ($(".balance_num").eq(i).prop("disabled") != true && $(".balance_num").eq(i).val() != "") {
                            allPrice += parseFloat($(".balance_num").eq(i).val());
                        }
                    });
                    $(".all_num").html(allPrice.toFixed(2));
                });

                //参考值
                var submitFlag = true;
                //点击确认按钮
                $("#allRight").click(function() {
                    $("#allRight").prop("disabled", true);
                    productArr = [];
                    productTable = [];
                    submitFlag = true;
                    if ($("#accountList").val() == 0) { //如果为选择账户，提示信息
                        layer.msg("请选择账户", {
                            time: 2000
                        });
                        return false;
                    }
                    $(".check").each(function(i, el) {
                        var this_check = $(".check").eq(i);
                        var this_uuid = this_check.parent().parent().find(".uuid").val();
                        var this_value = this_check.parent().parent().find(".balance_num").val();
                        var this_name = this_check.parent().parent().find(".name").val();
                        var this_returnRate = this_check.parent().parent().find(".returnRate").val();
                        var this_valueDate = this_check.parent().parent().find(".valueDate").val();
                        var this_banlance = this_check.parent().parent().find(".banlanceCN").val();
                        this_Amount = parseFloat($(".all_num").html()) + parseFloat($("#poundageValue").val());
                        if (this_check.is(":checked")) {
                            flag = false; //只要有被选条目，flag = false；

                            //向两个数组push选中条目的数据
                            productArr.push(this_uuid + "_" + this_value);
                            productTable.push({
                                "name": this_name,
                                "returnRate": this_returnRate,
                                "valueDate": this_valueDate,
                                "value": this_value
                            });

                            //选中但未输入投资金额，提示信息，跳出循环，清空数组
                            if (this_value == "" || this_value <= 0) {
                                layer.msg("请正确填写投资金额", {
                                    time: 2000
                                });
                                productArr = [];
                                productTable = [];
                                submitFlag = false;
                                $("#allRight").prop("disabled", false);
                                return false;
                            }

                            //投资金额大于余额，提示信息，跳出循环，清空数组
                            if (parseFloat(this_value) > parseFloat(this_banlance)) {
                                layer.msg("投资金额大于余额", {
                                    time: 2000
                                });
                                productArr = [];
                                productTable = [];
                                submitFlag = false;
                                $("#allRight").prop("disabled", false);
                                return false;
                            }

                            console.log(productArr);
                            console.log(productTable);
                        }
                    });
                    if (parseFloat(accountBanlance) < parseFloat(allPrice) + parseFloat($("#poundageValue").val())) {
                        layer.msg("账户余额不足", {
                            time: 2000
                        });
                        $("#allRight").prop("disabled", false);
                        return false;
                    }


                    //如果未选择条目，flag为true,提示信息
                    if (flag == true) {
                        layer.msg("请选择募集产品", {
                            time: 2000
                        });
                        $("#allRight").prop("disabled", false);
                        return false;
                    }
                    if (submitFlag == true) {
                        invest();
                    } else {
                        $("#allRight").prop("disabled", false);
                    }
                    // //如果数组有数据，切换到确认页面，并禁用账户选择
                    // if (productArr.length != 0) {
                    //     $(".product_msg").hide();
                    //     $(".invest_confirm_box").show();
                    //     $("#accountList").prop("disabled", true);
                    //
                    //     var template = $.templates("#tableConfirmTpl");
                    //     var html = template.render(productTable);
                    //     $("#tableConfirmCnt").html(html);
                    // }
                });

                //点击确认页面的返回按钮清空数组
                $("#back").click(function() {
                    productArr = [];
                    productTable = [];
                    $(".invest_confirm_box").hide();
                    $(".product_msg").show();
                });
            })
            .fail(function() {
                layer.msg("error", {
                    time: 2000
                });
            });



    } //getBalance();


    //投资请求！！
    function invest() {
        var data = "";
        $.each(productArr, function(index, el) {
            data += "data=" + el + "&";
        });
        data += "uuid=" + uuid + "&";
        data += "companyAccount=" + $("#accountList").val() + "&";
        data += "fund=" + $("#bankFinancialProduct").val() + "&";
        data += "accountBalance=" + parseFloat($("#poundageValue").val()).toFixed(2) + "&";
        data += "remark=" + $("#psValue").val() + "&";
        data += "CSRFToken=" + $('input[name="CSRFToken"]').val() + "&";

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

    //投资按钮点击事件
    $("#invest").click(function() {
        $(".invest_confirm_box").hide();
        $(".product_msg").show();

        $(".publishuuid").each(function(index, el) {
            $(".uuid").each(function(i, e) {
                var puuid = $(".publishuuid").eq(index).val();
                var pAmount = $(".publishAmount").eq(index).val();
                var uuid = $(".uuid").eq(i).val();
                if (puuid == uuid) {
                    $(".uuid").eq(i).siblings('td').find('.check').prop("checked", true);
                    $(".uuid").eq(i).siblings('td').find('.balance_num').val(pAmount).prop("disabled", false);
                }
            });
        });
        $(".balance_num").each(function(i, el) {
            if ($(".balance_num").eq(i).prop("disabled") != true && $(".balance_num").eq(i).val() != "") {
                allPrice += parseFloat($(".balance_num").eq(i).val());
            }
        });
        $(".all_num").html(allPrice.toFixed(2));

    });

});