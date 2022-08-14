$(document).ready(function() {
    var pageNum = 1;
    var sort = "";
    var flag = true;
    var conditionList = {
        "list": [],
        "pageNum": "",
        "url": getHtmlDocName()
    };
    $(".balance_value").hide();
    $(".balance_none").show();
    if (localStorage.getItem("flag")) {
        if (localStorage.getItem("flag") == "true") {
            $(".balance_value").hide();
            $(".balance_none").show();
        } else {
            $(".balance_value").show();
            $(".balance_none").hide();
        }
    } else {
        localStorage.setItem("flag", true);
    }

    if (sessionStorage.getItem("condition")) {
        var json = JSON.parse(sessionStorage.getItem("condition"));
        if (json.url == getHtmlDocName()) {
            $.each(json.list, function(index, el) {
                $("#" + el).addClass('statusLight').siblings().removeClass('statusLight');
            });
            pageNum = json.pageNum;
        } else {
            sessionStorage.removeItem("condition");
        }
    }

    $(".msg_table").on("click", "a", function() {
        $(".statusLight").each(function(index, el) {
            if ($(".statusLight").eq(index).prop("id") != "all") {
                conditionList.list.push($(".statusLight").eq(index).prop("id"));
            }
        });
        conditionList.pageNum = pageNum;
        sessionStorage.setItem("condition", JSON.stringify(conditionList));
    });


    $(".filter a").click(function() {
        $(this).addClass("statusLight").siblings().removeClass("statusLight");
        init();
        getList();
    });


    getList();
    getTypeList();
    balanceAll();
    getQcbEmpBalance();

    function init() {
        pageNum = '1';
        flag = true;
    }


    // $("#recharge1").click(function() {
    //     $.ajax({
    //             url: '../rest/backadmin/investorProduct/recharge',
    //             type: 'get'
    //         })
    //         .done(function(r) {
    //             layer.msg(r.message, {
    //                 time: 2000
    //             });
    //         })
    //         .fail(function() {
    //             layer.msg("error", {
    //                 time: 2000
    //             });
    //         });
    //
    // });
    // $("#draw1").click(function() {
    //     $.ajax({
    //             url: '../rest/backadmin/investorProduct/withdraw',
    //             type: 'get'
    //         })
    //         .done(function(r) {
    //             layer.msg(r.message, {
    //                 time: 2000
    //             });
    //         })
    //         .fail(function() {
    //             layer.msg("error", {
    //                 time: 2000
    //             });
    //         });
    //
    // });

    function getQcbEmpBalance() {
        $.ajax({
                url: '../rest/backadmin/companyAccount/getQcbEmpBalance',
                type: 'get',
                cache: false
            })
            .done(function(r) {
                if (r.status == "SUCCESS") {
                    $(".balance_value").eq(5).html(r.data.qcbEmpTotalBalanceCN);
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

    //获取账户金额一览
    function balanceAll() {
        $.ajax({
                url: '../rest/backadmin/companyAccount/getPlatform',
                type: 'get'
            })
            .done(function(r) {

                $.ajax({
                        url: '../rest/backadmin/companyAccount/getPlatformBalance',
                        type: 'get'
                    })
                    .done(function(data) {


                        $(".balance_value").eq(0).html(r.data.totalBalanceCN);

                        if (r.data.totalBalance >= 0) {
                            $(".balance_value").eq(0).addClass('color_orange').removeClass("color_green");
                        } else if (r.data.totalBalance < 0) {
                            $(".balance_value").eq(0).addClass('color_green').removeClass("color_orange");
                        }



                        $(".balance_value").eq(1).html(r.data.accountBalanceCN);
                        if (r.data.accountBalance >= 0) {
                            $(".balance_value").eq(1).addClass('color_orange').removeClass("color_green");
                        } else if (r.data.accountBalance < 0) {
                            $(".balance_value").eq(1).addClass('color_green').removeClass("color_orange");
                        }


                        $(".balance_value").eq(2).html(r.data.investorInvestmentCN);
                        $(".balance_value").eq(3).html(r.data.investmentCN);
                        $(".balance_value").eq(4).html(r.data.totalReturnCN);

                        $(".account_price_content").click(function() {
                            if (localStorage.getItem("flag") == "true") {
                                localStorage.setItem("flag", "false");
                            } else {
                                localStorage.setItem("flag", "true");
                            }
                            $(".balance_value").toggle();
                            $(".balance_none").toggle();
                        });






                    })
                    .fail(function() {
                        layer.msg("error", {
                            time: 2000
                        });
                    });




                $(".account_price_content_value").find("img").click(function() {

                });


            })
            .fail(function() {
                layer.msg("error", {
                    time: 2000
                });
            });

    }

    //获取筛选项
    function getTypeList() {
        $("#thirdCount").html("(0)");
        $("#investCount").html("(0)");
        $("#collectCount").html("(0)");
        $("#redeemCount").html("(0)");
        $("#allCount").html("(0)");
        $.ajax({
                url: "../rest/backadmin/companyAccount/typeList",
                type: "get"
            })
            .done(function(r) {
                if (r.status == "SUCCESS") {
                    if (r.totalResultCount > 0) {
                        var totalCount = 0;
                        $.each(r.data, function(index, el) {
                            $("#" + el.status + "Count").html("(" + el.count + ")");
                            totalCount += el.count;
                        });
                        $("#allCount").html("(" + totalCount + ")");
                    }
                } else {
                    layer.msg(r.message, {
                        time: 2000
                    });
                }
            })
            .fail(function(r) {
                layer.msg("error", {
                    time: 2000
                });
            });


    } //getTypeList()


    //获取列表
    function getList(name, sorts) {
        var sort = "";
        var nameValue = "";
        var typeValue = $(".filter").find(".statusLight").prop("id");
        if (sorts != undefined) {
            sort = sorts;
        }
        if (name != undefined) {
            nameValue = name;
        }
        $.ajax({
                url: '../rest/backadmin/companyAccount/list',
                type: 'get',
                data: {
                    "pageNum": pageNum,
                    "pageSize": 10,
                    "name": nameValue,
                    "sorts": sort,
                    "type": typeValue
                }
            })
            .done(function(r) {
                //生成模板加载数据
                if (r.status == "SUCCESS") {
                    if (r.totalResultCount > 0) {
                        var template = $.templates("#queboxTpl");
                        var html = template.render(r.data);
                        $("#queboxCnt").html(html);
                    } else {
                        $("#queboxCnt").html("<tr><td colspan='9'>没有数据</td></tr>");
                    }
                } else {
                    layer.msg(r.message, {
                        time: 2000
                    });
                }

                $(".investment").each(function(index, el) {
                    var thisValue = parseFloat($(".investment").eq(index).html()).toFixed(2);
                    $(".investment").eq(index).html(formatNum(thisValue));

                });

                //投资入口弹框
                $(".invest").colorbox({
                    iframe: true,
                    width: "1050px",
                    height: "720px",
                    opacity: '0.5',
                    overlayClose: false,
                    escKey: true
                });

                //分页构造函数
                if (flag) {
                    $('#pageTool').Paging({
                        pagesize: r.pageSize,
                        count: r.totalResultCount,
                        current: pageNum,
                        callback: function(page, size, count) {
                            pageNum = page;
                            getList();
                            flag = false;
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
    } //getList()
});