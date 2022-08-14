$(document).ready(function() {
    var pageNum = 1;
    var sort = "";
    var flag = true;

    var conditionList = {
        "list": [],
        "name": "",
        "pageNum": "",
        "url": getHtmlDocName()
    };

    if (sessionStorage.getItem("condition")) {
        var json = JSON.parse(sessionStorage.getItem("condition"));
        if (json.url == getHtmlDocName()) {
            $.each(json.list, function(index, el) {
                $("#" + el).addClass('statusLight').siblings().removeClass('statusLight');
            });
            pageNum = json.pageNum;
            $("#search").val(json.name);
        } else {
            sessionStorage.removeItem("condition");
        }

    }


    $("#queboxCnt").on("click", "a", function() {
        sessionStorage.removeItem("condition");
        $(".statusLight").each(function(index, el) {
            if ($(".statusLight").eq(index).prop("id") != "all") {
                conditionList.list.push($(".statusLight").eq(index).prop("id"));
            }
        });
        conditionList.pageNum = pageNum;
        conditionList.name = $("#search").val();
        sessionStorage.setItem("condition", JSON.stringify(conditionList));
    });

    $(".filter a").click(function() {
        $(this).addClass("statusLight").siblings().removeClass("statusLight");
        init();
        getList();
    });

    getList();
    getStatusList();

    function init() {
        pageNum = '1';
        flag = true;
    }

    function searchBtn() {
        init();
        getList();
        return false;
    }
    $("#searchButton").click(function() {
        searchBtn();
    });
    $("#search").bind("keypress", function(event) {
        if (event.keyCode == 13) {
            searchBtn();
            return false;
        }
    });


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
                    url: '../rest/backadmin/version/change',
                    type: 'get',
                    data: {
                        "uuid": _this.attr("data-uuid"),
                        "status": _this.attr("data-status")
                    }
                })
                .done(function(r) {
                    if (r.status == "SUCCESS") {
                        layer.msg(r.message, {
                            time: 2000
                        });
                        getList();
                        getStatusList();
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

    //获取筛选项
    function getStatusList() {
        $("#publishedCount").html("(0)");
        $("#unpublishCount").html("(0)");
        $("#deleteCount").html("(0)");
        $.ajax({
                url: "../rest/backadmin/version/statusList",
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
    function getList(sorts) {
        var sort = "";
        var name = $("#search").val();
        var statusValue = $(".filter-status").find(".statusLight").prop("id");
        var deviceValue = $(".filter-device").find(".statusLight").prop("id");
        if (sorts != undefined) {
            sort = sorts;
        }
        $.ajax({
                url: '../rest/backadmin/version/list',
                type: 'get',
                data: {
                    "pageNum": pageNum,
                    "pageSize": 10,
                    "name": name,
                    "status": statusValue,
                    "device": deviceValue
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
                        $("#queboxCnt").html("<tr><td colspan='6'>没有数据</td></tr>");
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