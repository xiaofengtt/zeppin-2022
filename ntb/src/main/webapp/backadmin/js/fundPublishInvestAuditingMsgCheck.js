$(document).ready(function() {
    var uuid = url("?uuid");
    var productArr = [];
    var productTable = [];
    var flag = true;
    var allPrice = 0;
    var accountBanlance = 0;
    $(".invest_confirm_box").show();
    $(".product_msg").hide();
    getProductMessage();
    // getInvestMessage();

    function checkThis(t) {
        var reason = $('#reason').val();
        layer.confirm('确定要变更状态吗?', function(index) {
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
                var template = $.templates("#tableConfirmTpl");
                var html = template.render(r.data);
                $("#tableConfirmCnt").html(html);
                var allAmount = 0;
                $(".publishAmount").each(function(i, el) {
                    allAmount += parseFloat($(".publishAmount").eq(i).val());
                    $(".all_price").html("总计：" + allAmount.toFixed(2));
                });
                //投资列表
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
                        //审核
                        $(".auditing_check").click(function() {
                            checkThis(this);
                        });
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
                url: '../rest/backadmin/fundInvest/list',
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
                    $(".all_price").html("总计：" + allPrice);

                    flag = true;
                    productArr = [];
                    productTable = [];
                });

                //输入投资金额修改总计
                $(".balance_num").bind("input", function() {
                    allPrice = 0;
                    $(".balance_num").each(function(i, el) {
                        if ($(".balance_num").eq(i).prop("disabled") != true && $(".balance_num").eq(i).val() != "") {
                            allPrice += parseFloat($(".balance_num").eq(i).val());
                        }
                    });
                    $(".all_price").html("总计：" + allPrice);
                });


                //点击确认按钮
                $("#allRight").click(function() {
                    productArr = [];
                    productTable = [];
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
                            if (this_value == "") {
                                layer.msg("投资金额不能为空", {
                                    time: 2000
                                });
                                productArr = [];
                                productTable = [];
                                return false;
                            }

                            //投资金额大于余额，提示信息，跳出循环，清空数组
                            if (parseFloat(this_value) > parseFloat(this_banlance)) {
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
                    if (parseFloat(accountBanlance) < parseFloat(allPrice)) {
                        layer.msg("账户余额不足", {
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
                    invest();
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
        data += "companyAccount=" + $("#accountList").val() + "&";
        data += "bankFinancialProduct=" + $("#bankFinancialProduct").val() + "&";
        data += "poundage=" + parseFloat($("#poundageValue").val()).toFixed(2) + "&";
        data += "remark=" + $("#psValue").val();

        $.ajax({
                url: '../rest/backadmin/fundInvest/operateEdit?',
                type: 'post',
                data: data,
            })
            .done(function(r) {
                if (r.status == "SUCCESS") {
                    layer.msg(r.message, {
                        time: 2000
                    }, function() {
                        window.close();
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
        $(".all_price").html("总计：" + allPrice);

    });

});