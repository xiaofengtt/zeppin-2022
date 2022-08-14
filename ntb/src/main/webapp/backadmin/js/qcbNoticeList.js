$(document).ready(function() {
    var pageNum = 1;
    var sort = "";
    var flag = true;
    var conditionList = {
        "list": [],
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
    getStatusList();

    function init() {
        pageNum = '1';
        flag = true;
    }

    //获取筛选项
    function getStatusList() {
        $("#normalCount").html("(0)");
        $("#disabledCount").html("(0)");
        $("#allCount").html("(0)");
        $.ajax({
                url: "../rest/backadmin/qcbNotice/statusList",
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


    }


    //获取列表
    function getList(name, sorts) {
        var sort = "";
        var nameValue = "";
        var statusValue = $(".filter").find(".statusLight").prop("id");
        if (sorts != undefined) {
            sort = sorts;
        }
        if (name != undefined) {
            nameValue = name;
        }
        $.ajax({
                url: '../rest/backadmin/qcbNotice/list',
                type: 'get',
                data: {
                    "pageNum": pageNum,
                    "pageSize": 10,
                    "name": nameValue,
                    "sorts": sort,
                    "status": statusValue
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