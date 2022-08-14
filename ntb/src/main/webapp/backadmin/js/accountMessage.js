$(document).ready(function() {
    var pageNum = 1;
    var _pageNum = 1;
    var flag = true;
    var _flag = true;
    var uuid = url("?uuid");

    getAccountMessageList();
    investList();
    historyList();
    getTypeList();

    function init() {
        _pageNum = 1;
        _flag = true;
    }

    $(".statusDiv a").click(function() {
        $(this).addClass("statusLight").siblings().removeClass("statusLight");
        init();
        historyList();
    });

    //获取企业账户信息
    function getAccountMessageList() {

        $.ajax({
                url: '../rest/backadmin/companyAccount/get',
                type: 'get',
                data: {
                    "uuid": uuid
                }
            })
            .done(function(r) {
                if (r.status == "SUCCESS") {
                    $("#totalAmount").html(r.data.investmentCN);
                    var template = $.templates("#account_mssage_tpl");
                    var html = template.render(r.data);
                    $("#account_message_Cnt").html(html);
                } else {
                    layer.msg(r.status, {
                        time: 2000
                    });
                }
            })
            .fail(function() {
                layer.msg("error", {
                    time: 2000
                });
            });
    } //getAccountMessage();

    //获取投资总额
    function investList(name, sorts) {
        var sort = "";
        var nameValue = "";
        if (sorts != undefined) {
            sort = sorts;
        }
        if (name != undefined) {
            nameValue = name;
        }
        $.ajax({
                url: '../rest/backadmin/companyAccount/investList',
                type: 'get',
                data: {
                    "pageNum": pageNum,
                    "pageSize": 10,
                    "name": nameValue,
                    "sorts": sort,
                    "companyAccount": uuid
                }
            })
            .done(function(r) {
                //生成模板加载数据
                if (r.status == "SUCCESS") {
                    if (r.totalResultCount > 0) {
                        var template = $.templates("#invest_all");
                        var html = template.render(r.data);
                        $("#all_price_Cnt").html(html);
                    } else {
                        $("#all_price_Cnt").html("<tr><td colspan='4'>没有数据</td></tr>");
                    }
                } else {
                    layer.msg(r.message, {
                        time: 2000
                    });
                }

                //分页构造函数
                if (flag) {
                    $('#pageTool_1').Paging({
                        pagesize: r.pageSize,
                        count: r.totalResultCount,
                        callback: function(page, size, count) {
                            pageNum = page;
                            investList();
                            flag = false;
                        }
                    });
                    $("#pageTool_1").find(".ui-paging-container:last").siblings().remove();
                }
            })
            .fail(function() {
                layer.msg("error", {
                    time: 2000
                });
            });
    }

    //获取资金流水
    function historyList(name, sorts) {
        var sort = "";
        var nameValue = "";
        if (sorts != undefined) {
            sort = sorts;
        }
        if (name != undefined) {
            nameValue = name;
        }
        $.ajax({
                url: '../rest/backadmin/companyAccount/historyList',
                type: 'get',
                data: {
                    "pageNum": _pageNum,
                    "pageSize": 10,
                    "name": nameValue,
                    "companyAccount": uuid,
                    "sorts": sort,
                    "status": "",
                    "type": $(".filter").find(".statusLight").attr("id")
                }
            })
            .done(function(r) {
                //生成模板加载数据
                if (r.status == "SUCCESS") {
                    if (r.totalResultCount > 0) {
                        var template = $.templates("#balance_all");
                        var html = template.render(r.data);
                        $("#price_journal_Cnt").html(html);
                    } else {
                        $("#price_journal_Cnt").html("<tr><td colspan='6'>没有数据</td></tr>");
                    }
                } else {
                    layer.msg(r.message, {
                        time: 2000
                    });
                }

                //分页构造函数
                if (_flag) {
                    $('#pageTool_2').Paging({
                        pagesize: r.pageSize,
                        count: r.totalResultCount,
                        callback: function(page, size, count) {
                            _pageNum = page;
                            historyList();
                            _flag = false;
                        }
                    });
                    $("#pageTool_2").find(".ui-paging-container:last").siblings().remove();
                }
            })
            .fail(function() {
                layer.msg("error", {
                    time: 2000
                });
            });
    }

    function getTypeList() {
        $("#allCount").html("(0)");
        $("#transferCount").html("(0)");
        $("#rechargeCount").html("(0)");
        $("#expendCount").html("(0)");
        $("#investCount").html("(0)");
        $("#redeemCount").html("(0)");
        $("#returnCount").html("(0)");
        $("#takeoutCount").html("(0)");
        $("#fillinCount").html("(0)");
        $("#qcb_takeoutCount").html("(0)");
        $("#qcb_rechargeCount").html("(0)");
        $("#emp_takeoutCount").html("(0)");
        $("#emp_fillinCount").html("(0)");

        $.ajax({
                url: "../rest/backadmin/companyAccount/historyTypeList",
                type: "get",
                data: {
                    "uuid": uuid
                }
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
});