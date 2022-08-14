$(document).ready(function() {
    var uuid = url("?uuid");
    var productArr = [];
    var productTable = [];
    var flag = true;
    var allPrice = 0;
    var accountBanlance = 0;
    $(".invest_confirm_box").hide();
    getProductMessage();
    getInvestMessage();
    //获取产品信息表头
    function getProductMessage() {
        $.ajax({
                url: '../rest/backadmin/bankFinancialProduct/get',
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
                $(".current").html("【" + r.data.custodianCN + "】" + r.data.name);

                getAccountList(r.data.custodian);
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
                            window.location.href = document.referrer;
                        });
                    } else {
                        $.each(r.data, function(index, el) {
                            $("#accountList").append("<option value=" + el.uuid + " data-balance=" + el.accountBalance + ">" + el.accountName + " " + el.accountNum + "</option>");
                        });
                        var balance = $("#accountList").find("option:selected").attr("data-balance");
                        var balanceV = parseFloat(balance).toFixed(10);
                        var balanceS = balanceV.substring(0, balanceV.lastIndexOf('.') + 3);
                        $("#balance").html(balanceS);
                        accountBanlance = parseFloat(balance);
                    }
                }
                $("#accountList").change(function() {
                    var balance = $(this).find("option:selected").attr("data-balance");
                    var balanceV = parseFloat(balance).toFixed(10);
                    var balanceS = balanceV.substring(0, balanceV.lastIndexOf('.') + 3);
                    $("#balance").html(balanceS);
                    accountBanlance = parseFloat(balance);
                });

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
                url: '../rest/backadmin/bankFinancialProductPublish/list',
                type: 'get',
                data: {
                    "status": "checked",
                    "investType": 1,
                    "pageNum": 1,
                    "pageSize": 10000
                }
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


                //点击确认按钮
                $("#allRight").click(function() {
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
                        var this_Amount = parseFloat($(".all_num").html()) + parseFloat($("#poundageValue").val());
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
                                return false;
                            }

                            //
                            if (this_Amount > accountBanlance) {
                                layer.msg("余额不足", {
                                    time: 2000
                                });
                                productArr = [];
                                productTable = [];
                                return false;
                            }


                            //投资金额大于余额，提示信息，跳出循环，清空数组
                            if (parseFloat(this_value) > parseFloat(this_banlance).toFixed(2)) {
                                layer.msg("投资金额大于余额", {
                                    time: 2000
                                });
                                productArr = [];
                                productTable = [];
                                return false;
                            }

                            console.log(productArr);
                            console.log(productTable);
                        }
                    });

                    //判断手续费
                    if ($("#poundageValue").val() == "") {
                        layer.msg("请正确填写手续费", {
                            time: 2000
                        });
                        return false;
                    }


                    //如果未选择条目，flag为true,提示信息
                    if (flag == true) {
                        layer.msg("请选择募集产品", {
                            time: 2000
                        });
                        return false;
                    }

                    //如果数组有数据，切换到确认页面，并禁用账户选择
                    if (productArr.length != 0) {
                        $(".product_msg").hide();
                        $(".invest_confirm_box").show();
                        $("#accountList").prop("disabled", true);

                        var template = $.templates("#tableConfirmTpl");
                        var html = template.render(productTable);
                        $("#tableConfirmCnt").html(html);
                    }
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
        data += "companyAccount=" + $("#accountList").val() + "&";
        data += "bankFinancialProduct=" + $("#bankFinancialProduct").val() + "&";
        data += "poundage=" + parseFloat($("#poundageValue").val()).toFixed(2) + "&";
        data += "remark=" + $("#psValue").val() + "&";
        data += "receipt=" + $('input[name="receipt"]').val() + "&";
        data += "CSRFToken=" + $('input[name="CSRFToken"]').val();
        $.ajax({
                url: '../rest/backadmin/bankFinancialProductInvest/invest?',
                type: 'post',
                data: data,
            })
            .done(function(r) {
                if (r.status == "SUCCESS") {
                    layer.msg(r.message, {
                        time: 2000
                    }, function() {
                        // window.location.href = document.referrer;
                        history.go(-1);
                    });
                } else {
                    layer.msg(r.message, {
                        time: 2000
                    });
                    $("#invest").prop("disabled", false);
                }

            })
            .fail(function() {
                layer.msg("error", {
                    time: 2000
                });
                $("#invest").prop("disabled", false);
            });

    }

    //投资按钮点击事件
    $("#invest").click(function() {
        $("#invest").prop("disabled", true);
        if (parseFloat(accountBanlance) >= parseFloat(allPrice)) {
            invest();
        } else {
            layer.msg("账户余额不足", {
                time: 2000
            });
            $("#invest").prop("disabled", false);
        }
    });

});