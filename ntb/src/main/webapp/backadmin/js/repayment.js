$(document).ready(function() {
    pageNum = 1;
    flag = true;
    var returnRate = "";
    var term = "";
    var uuid = url("?uuid");
    var RealV = "";
    var investAll;
    var returnRateAll;
    var totalCollect;
    $("#back_boolean").hide();
    $("#useConfirm").hide();
    $("[name='my-checkbox']").bootstrapSwitch();
    $(".repayment_confirm").hide();


    //获取产品信息
    getProduct();


    function getProduct() {
        $.ajax({
                url: '../rest/backadmin/bankFinancialProductPublish/get',
                type: 'get',
                data: {
                    uuid: uuid
                }
            })
            .done(function(r) {
                if (r.status == "SUCCESS") {
                    var template = $.templates("#productTpl");
                    var html = template.render(r.data);
                    $("#productCnt").html(html);
                    $("#useValue").val(r.data.targetAnnualizedReturnRate);
                    $("#useConfirm").html(r.data.targetAnnualizedReturnRate + "%");


                    //募集总额
                    $("#collectValue").html(r.data.realCollectCN);
                    $("#allPrice").html(r.data.realCollectCN);
                    totalCollect = r.data.realCollect;

                    //募集信息入口弹框
                    $(".collectMessage").colorbox({
                        iframe: true,
                        width: "1050px",
                        height: "650px",
                        opacity: '0.5',
                        overlayClose: false,
                        escKey: true
                    });

                    //预期收益率
                    $("#targetReturn").html("预期收益率：" + r.data.targetAnnualizedReturnRate + "%");

                    //收益率赋值
                    returnRate = r.data.targetAnnualizedReturnRate / 100;
                    //期限赋值
                    term = r.data.term / 365;

                    $('input[name="my-checkbox"]').on('switchChange.bootstrapSwitch', function(event, state) {
                        if (state == true) {
                            $("#back_boolean").html("是");
                            $("#useValue").val(r.data.targetAnnualizedReturnRate);
                            $("#useConfirm").html(r.data.targetAnnualizedReturnRate + "%");
                            var collect = parseFloat($("#collectValue").html().replace(/,/g, ""));
                            var realReturn = parseFloat($("#useValue").val()) / 100;
                            var realReturnV = (collect * realReturn * term).toFixed(10);
                            $("#returnValue").html(realReturnV.substring(0, realReturnV.lastIndexOf('.') + 3));
                            var allValue = parseFloat($("#returnValue").html().replace(/,/g, "")) + r.data.realCollect;
                            $("#allPrice").html(formatNum(allValue.toFixed(2)));
                        } else {
                            $("#back_boolean").html("否");
                        }
                        //   console.log(this); // DOM element
                        //   console.log(event); // jQuery event
                        //   console.log(state); // true | false
                    });



                    $("#useValue").bind("input", function() {
                        $('input[name="my-checkbox"]').bootstrapSwitch('state', false);
                        var collect = parseFloat($("#collectValue").html().replace(/,/g, ""));
                        var realReturn = parseFloat($("#useValue").val()) / 100;
                        $("#useConfirm").html($("#useValue").val() + "%");
                        var realReturnV = (collect * realReturn * term).toFixed(10);
                        $("#returnValue").html(realReturnV.substring(0, realReturnV.lastIndexOf('.') + 3));
                        var returnValue = parseFloat($("#returnValue").html());
                        var allValue = parseFloat($("#returnValue").html().replace(/,/g, "")) + r.data.realCollect;
                        $("#allPrice").html(formatNum(allValue.toFixed(2)));
                        if ($("#useValue").val() == "") {
                            $("#returnValue").html("0");
                            $("#allPrice").html(r.data.realCollectCN);
                        }
                    });
                    getInvestMessage();
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

    function getInvestMessage() {
        $.ajax({
                url: '../rest/backadmin/bankFinancialProductInvest/list?',
                type: 'get',
                data: {
                    "bankFinancialProductPublish": uuid,
                    "pageNum": 1,
                    "pageSize": 1000
                }
            })
            .done(function(r) {
                if (r.status == "SUCCESS") {
                    if (r.totalResultCount > 0) {
                        var template = $.templates("#investTpl");
                        var html = template.render(r.data);
                        $("#investCnt").html(html);
                    } else {
                        var html = "<tr><td colspan=5>没有数据</td></tr>";
                        $("#investCnt").html(html);
                    }

                } else {
                    layer.msg(r.message, {
                        time: 2000
                    });
                }
                // // //计算每条预期收益
                // $(".totalReturn").each(function(index, el) {
                //     var investMent = parseFloat($(".investment").eq(index).val()) * returnRate;
                //     $(".totalReturn").eq(index).html(formatNum((investMent * term).toFixed(2)));
                // });
                //计算总投资
                investAll = 0;
                $(".invest").each(function(index, el) {
                    var investValue = $(".invest").eq(index).html().replace(/,/g, "");
                    investAll += parseFloat(investValue);
                });
                $("#investAll").html("总投资：" + investAll.toFixed(2));

                //计算总收益
                var totalReturn = 0;
                $(".totalReturn").each(function(index, el) {
                    if (isNaN($(".totalReturn").eq(index).html()) == false) {
                        var returnValue = $(".totalReturn").eq(index).html().replace(/,/g, "");
                        totalReturn += parseFloat(returnValue);
                    }
                });

                $("#totalReturnAll").html(totalReturn);

                //计算收益率
                returnRateAll = (totalReturn / investAll / term) * 100;

                if (isNaN(returnRateAll) == false) {
                    var returnRateAllV = returnRateAll.toFixed(10);
                    $("#returnRateAll").html(returnRateAllV.substring(0, returnRateAllV.lastIndexOf('.') + 4) + "%");
                } else {
                    $("#returnRateAll").html("0.000%");
                }


                //returnRateAll.substring(returnRateAll.lastIndexOf('.'), 4) + "%" : "0.00"




                var collect = parseFloat($("#collectValue").html().replace(/,/g, ""));
                var realReturn = parseFloat($("#useValue").val()) / 100;
                var realReturnV = (collect * realReturn * term).toFixed(10);
                $("#returnValue").html(realReturnV.substring(0, realReturnV.lastIndexOf('.') + 3));
                var allValue = parseFloat($("#returnValue").html().replace(/,/g, "")) + totalCollect;
                $("#allPrice").html(formatNum(allValue.toFixed(2)));


                $("#allRight").click(function() {
                    if ($("#useValue").val() == "") {
                        layer.msg("请填写实际收益率", {
                            time: 2000
                        });
                        return false;
                    }
                    RealV = $("#useValue").val();
                    $(".bootstrap-switch").hide();
                    $("#back_boolean").show();
                    $("#useValue").hide();
                    $("#useConfirm").show();
                    $(".btn_group").eq(0).hide();
                    $(".repayment_confirm").show();

                    getRepay();

                });
                $("#back").click(function() {
                    $(".bootstrap-switch").show();
                    $("#back_boolean").hide();
                    $("#useValue").show();
                    $("#useConfirm").hide();
                    $(".btn_group").eq(0).show();
                    $(".repayment_confirm").hide();
                });
            })
            .fail(function() {
                layer.msg("error", {
                    time: 2000
                });
            });
    }

    function getRepay() {
        $.ajax({
                url: '../rest/backadmin/investorProduct/listProductBuy',
                type: 'get',
                data: {
                    "product": uuid,
                    "pageNum": pageNum,
                    "pageSize": "10"
                }
            })
            .done(function(r) {
                if (r.status == "SUCCESS") {
                    if (r.totalResultCount > 0) {
                        var template = $.templates("#repayTpl");
                        var html = template.render(r.data);
                        $("#repayCnt").html(html);
                    } else {
                        var html = "<tr><td colspan=5>没有数据</td></tr>";
                        $("#repayCnt").html(html);
                    }
                } else {
                    layer.msg(r.message, {
                        time: 2000
                    });
                }

                $(".totalAmount").each(function(index, el) {

                    var realReturn = parseFloat($(".totalAmount").eq(index).val()) * ($("#useValue").val() / 100) * term;
                    var realReturnV = realReturn.toFixed(10);
                    var realReturnS = realReturnV.substring(0, realReturnV.lastIndexOf('.') + 3);
                    $(".return").eq(index).html(realReturnS);
                    $(".all_price").eq(index).html(formatNum((parseFloat(realReturnS) + parseFloat($(".totalAmount").eq(index).val())).toFixed(2)));
                });

                if (flag) {
                    $('#pageTool').Paging({
                        pagesize: r.pageSize,
                        count: r.totalResultCount,
                        callback: function(page, size, count) {
                            pageNum = page;
                            getList();
                            flag = false;
                            document.body.scrollTop = document.documentElement.scrollTop = 0;
                        },
                        render: function(ops) {

                        }
                    });
                    $("#pageTool").find(".ui-paging-container:last").siblings().remove();
                }
            })
            .fail(function() {
                layer.msg("error", {
                    time: 2000
                });
            });
    }

    //提交！！！
    $("#allRightConfirm").click(function() {
        submit();
    });

    function submit() {
        $.ajax({
                url: '../rest/backadmin/productPublishBalance/balance',
                type: 'post',
                data: {
                    "uuid": uuid,
                    "realRate": RealV,
                    "CSRFToken": $('input[name="CSRFToken"]').val()
                }
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

    }
}); //document.ready