$(document).ready(function() {
    var pageNum = 1;
    var flag = true;
    var name = "";

    function searchBtn() {
        init();
        getList();
        return false;
    }

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

    if (sessionStorage.getItem("condition")) {
        var json = JSON.parse(sessionStorage.getItem("condition"));
        if (json.url == getHtmlDocName()) {
            pageNum = json.pageNum;
            $("#search").val(json.name);
        } else {
            sessionStorage.removeItem("condition");
        }
    }
    getList();

    $("#queboxCnt").on("click", "a", function() {
        sessionStorage.removeItem("condition");
        // conditionList.pageNum = pageNum;
        conditionList.name = $("#search").val();
        sessionStorage.setItem("condition", JSON.stringify(conditionList));
    });

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
                    url: '',
                    type: "post",
                    data: {
                        param1: $(_this).siblings('input').val()
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



    function getList() {
        name = $("#search").val();

        $.ajax({
                url: '../rest/backadmin/advert/list',
                type: 'get',
                data: {
                    "pageNum": pageNum,
                    "pageSize": 1000,
                    "title": name
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

                // //分页构造函数
                // if (flag) {
                //     $('#pageTool').Paging({
                //         pagesize: r.pageSize,
                //         count: r.totalResultCount,
                //         callback: function(page, size, count) {
                //             pageNum = page;
                //             getList();
                //             flag = false;
                //         }
                //     });
                //     $("#pageTool").find(".ui-paging-container:last").siblings().remove();
                // }
            })
            .fail(function() {
                layer.msg("error", {
                    time: 2000
                });
            });

    }
});