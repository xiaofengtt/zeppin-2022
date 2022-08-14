$(document).ready(function() {
    var pageNum = 1;
    var sort = "";
    var flag = true;

    var conditionList = {
        "pageNum": "",
        "url": getHtmlDocName()
    };

    if (sessionStorage.getItem("condition")) {
        var json = JSON.parse(sessionStorage.getItem("condition"));
        if (json.url == getHtmlDocName()) {
            pageNum = json.pageNum;
        } else {
            sessionStorage.removeItem("condition");
        }

    }


    $("#queboxCnt").on("click", "a", function() {
        sessionStorage.removeItem("condition");
        conditionList.pageNum = pageNum;
        sessionStorage.setItem("condition", JSON.stringify(conditionList));
    });

    $(".filter a").click(function() {
        $(this).addClass("statusLight").siblings().removeClass("statusLight");
        init();
        getList();
    });

    getList();

    function init() {
        pageNum = '1';
        flag = true;
    }


    //开启/关闭
    $(".msg_table").on("click", ".change", function() {
        var _this = $(this);
        layer.confirm("确定要变更状态吗？", function(index) {
            if (flagSubmit == false) {
                return false;
            }
            flagSubmit = false;
            setTimeout(function() {
                flagSubmit = true;
            }, 3000);

            $.ajax({
                    url: '../rest/backadmin/bkpayment/change',
                    type: 'post',
                    data: {
                        "uuid": _this.attr("data-uuid"),
                        "switchFlag": _this.attr("data-status")
                    }
                })
                .done(function(r) {
                    if (r.status == "SUCCESS") {
                        layer.msg(r.message, {
                            time: 2000
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
            layer.close(index);
        });
    });

    $(".msg_table").on("click", ".delete", function() {
        var _this = $(this);
        layer.confirm("确定要变更状态吗？", function(index) {
            if (flagSubmit == false) {
                return false;
            }
            flagSubmit = false;
            setTimeout(function() {
                flagSubmit = true;
            }, 3000);

            $.ajax({
                    url: '../rest/backadmin/bkpayment/delete',
                    type: 'get',
                    data: {
                        "uuid": _this.attr("data-uuid")
                    }
                })
                .done(function(r) {
                    if (r.status == "SUCCESS") {
                        layer.msg(r.message, {
                            time: 2000
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
            layer.close(index);
        });
    });

    // //获取筛选项
    // function getTypeList() {
    //     $("#thirdCount").html("(0)");
    //     $("#investCount").html("(0)");
    //     $("#collectCount").html("(0)");
    //     $("#redeemCount").html("(0)");
    //     $("#allCount").html("(0)");
    //     $.ajax({
    //             url: "../rest/backadmin/bkpayment/list",
    //             type: "get"
    //         })
    //         .done(function(r) {
    //             if (r.status == "SUCCESS") {
    //                 if (r.totalResultCount > 0) {
    //                     var totalCount = 0;
    //                     $.each(r.data, function(index, el) {
    //                         $("#" + el.status + "Count").html("(" + el.count + ")");
    //                         totalCount += el.count;
    //                     });
    //                     $("#allCount").html("(" + totalCount + ")");
    //                 }
    //             } else {
    //                 layer.msg(r.message, {
    //                     time: 2000
    //                 });
    //             }
    //         })
    //         .fail(function(r) {
    //             layer.msg("error", {
    //                 time: 2000
    //             });
    //         });
    //
    //
    // } //getTypeList()


    //获取列表
    function getList(name, sorts) {
        var sort = "";
        var nameValue = "";
        // var typeValue = $(".filter").find(".statusLight").prop("id");
        if (sorts != undefined) {
            sort = sorts;
        }
        if (name != undefined) {
            nameValue = name;
        }
        $.ajax({
                url: '../rest/backadmin/bkpayment/list',
                type: 'get',
                data: {
                    "pageNum": pageNum,
                    "pageSize": 10
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