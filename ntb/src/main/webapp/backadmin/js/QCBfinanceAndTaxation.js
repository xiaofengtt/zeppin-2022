$(document).ready(function() {
    var pageNum = 1;
    var flag = true;

    function init() {
        pageNum = 1;
        flag = true;
    }


    var conditionList = {
        "filter": "",
        "name": "",
        "pageNum": "",
        "url": getHtmlDocName()
    };


    if (sessionStorage.getItem("condition")) {
        var json = JSON.parse(sessionStorage.getItem("condition"));
        if (json.url == getHtmlDocName()) {
            $(".filter").find("#" + json.filter).addClass('statusLight').siblings().removeClass("statusLight");
            pageNum = json.pageNum;
            $("#search").val(json.name);
        } else {
            sessionStorage.removeItem("condition");
        }
    }

    $("#queboxCnt").on("click", "a", function() {
        sessionStorage.removeItem("condition");
        conditionList.filter = $(".filter").find(".statusLight").prop("id");
        conditionList.pageNum = pageNum;
        conditionList.name = $("#search").val();
        sessionStorage.setItem("condition", JSON.stringify(conditionList));
    });

    $("#queboxCnt").on("click", ".open", function() {
        var _thisId = $(this).attr("data-uuid");
        layer.confirm("确定要变更状态吗？", function(index) {
            if (flagSubmit == false) {
                return false;
            }
            flagSubmit = false;
            setTimeout(function() {
                flagSubmit = true;
            }, 3000);
            $.ajax({
                    url: '../rest/backadmin/qcbfinance/check',
                    type: 'get',
                    data: {
                        "uuid": _thisId
                    }
                })
                .done(function(r) {
                    if (r.status == "SUCCESS") {
                        layer.msg(r.message, {
                            time: 2000
                        });
                        getList();
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

        });
    });
    getList();
    getStatusList();

    function searchBtn() {
        init();
        getList();
        return false;
    }
    $("#search").keypress(function() {
        if (event.keyCode == 13) {
            searchBtn();
            return false;
        }
    });

    $(".input-group-addon").click(function() {
        searchBtn();
    });

    $(".filter a").click(function() {
        $(this).addClass("statusLight").siblings().removeClass("statusLight");
        init();
        // getTypeList();
        getList();
    });


    function getStatusList() {
        $("#normalCount").html("(0)");
        $("#uncheckCount").html("(0)");
        $("#allCount").html("(0)");
        $("#unauthCount").html("(0)");
        $.get('../rest/backadmin/qcbfinance/statusList', function(r) {
            if (r.status == 'SUCCESS') {
                var totalCount = 0;
                if (r.totalResultCount > 0) {
                    $.each(r.data, function(i, v) {
                        totalCount += v.count;
                        $("#" + v.status + "Count").html("(" + v.count + ")");
                    });
                    $("#allCount").html("(" + totalCount + ")");
                }
            } else {
                layer.msg(r.message, {
                    time: 2000
                });
            }
        });
    }

    function getList() {
        var name = $("#search").val();
        var statusValue = $(".filter").find(".statusLight").prop("id");
        $.ajax({
                url: '../rest/backadmin/qcbfinance/list',
                type: 'get',
                data: {
                    "pageNum": pageNum,
                    "pageSize": 10,
                    "name": name,
                    "status": statusValue
                }
            })
            .done(function(r) {
                if (r.status == "SUCCESS") {
                    if (r.totalResultCount > 0) {
                        var template = $.templates("#queboxTpl");
                        var html = template.render(r.data);
                        $("#queboxCnt").html(html);
                    } else {
                        $("#queboxCnt").html("<tr><td style='text-align:center;' colspan=6>没有数据</td></tr>");
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

    }


});