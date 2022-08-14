var pageNum = 1;
var flag = true;
var name = "";

function getList() {
    name = $("#search").val();
    var statusValue = $(".filter").find(".statusLight").attr("id");
    $.ajax({
            url: '../rest/backadmin/qcbVirtualAccounts/list',
            type: 'get',
            data: {
                "pageNum": pageNum,
                "pageSize": 10,
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
                    $("#queboxCnt").html("<tr><td colspan=6>没有数据</td></tr>");
                }
            } else {
                layer.msg(r.message, {
                    time: 2000
                });
            }

            //弹框
            $(".add_account").colorbox({
                iframe: true,
                width: "600px",
                height: "410px",
                opacity: '0.5',
                overlayClose: false,
                escKey: true,
                onComplete: function() {
                    $("body").css({
                        "overflow": "hidden"
                    });
                },
                onClosed: function() {
                    $("body").css({
                        "overflow": "scroll"
                    });
                }
            });
            $(".delete_account").colorbox({
                iframe: true,
                width: "600px",
                height: "410px",
                opacity: '0.5',
                overlayClose: false,
                escKey: true,
                onComplete: function() {
                    $("body").css({
                        "overflow": "hidden"
                    });
                },
                onClosed: function() {
                    $("body").css({
                        "overflow": "scroll"
                    });
                }
            });



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

$(document).ready(function() {

    function searchBtn() {
        init();
        getList();
        return false;
    }
    $(".msg_table").on("click", "a", function() {
        deleteThis(this);
    });

    function init() {
        pageNum = '1';
        flag = true;
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
    var conditionList = {
        "name": "",
        "url": getHtmlDocName()
    };
    $(".filter a").click(function() {
        $(this).addClass("statusLight").siblings().removeClass("statusLight");
        init();
        getList();
    });
    // if (sessionStorage.getItem("condition")) {
    //     var json = JSON.parse(sessionStorage.getItem("condition"));
    //     if (json.url == getHtmlDocName()) {
    //         pageNum = json.pageNum;
    //         $("#search").val(json.name);
    //     } else {
    //         sessionStorage.removeItem("condition");
    //     }
    // }
    getList();
    getStatusList();
    // $("#queboxCnt").on("click", "a", function() {
    //     sessionStorage.removeItem("condition");
    //     // conditionList.pageNum = pageNum;
    //     conditionList.name = $("#search").val();
    //     sessionStorage.setItem("condition", JSON.stringify(conditionList));
    // });


    function deleteThis(_this) {
        layer.confirm("确定要删除吗？", function(index) {

            if (flagSubmit == false) {
                return false;
            }
            flagSubmit = false;
            setTimeout(function() {
                flagSubmit = true;
            }, 3000);
            $.ajax({
                    url: '../rest/backadmin/qcbVirtualAccounts/delete',
                    type: "get",
                    data: {
                        "uuid": $(_this).attr("data-uuid")
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
            layer.close(index);
        });
    } //delete_this

    function getStatusList() {
        $("#allCount").html("(0)");
        $("#normalCount").html("(0)");
        $("#usedCount").html("(0)");
        $.ajax({
                url: "../rest/backadmin/qcbVirtualAccounts/statusList",
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


    } //getStatusList()


});