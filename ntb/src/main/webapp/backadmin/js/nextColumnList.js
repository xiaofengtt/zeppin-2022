$(document).ready(function() {
    var pageNum = 1;
    var flag = true;

    function init() {
        pageNum = '1';
        flag = true;
    }
    //删除本条
    function deleteThis(_this) {
        $.ajax({
                url: '',
                type: "get",
                data: {
                    param1: $(_this).siblings('input').val()
                }
            })
            .done(function(r) {
                if (r.status == "SUCCESS") {
                    layer.msg(r.message, {
                        time: 2000
                    });
                    init();
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

    }

    function getList() {

        $.ajax({
                url: '',
                type: 'get',
                data: {
                    "pageNum": pageNum,
                    "pageSize": 10
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

                $(".delete").unbind("click").click(function() {
                    deleteThis(this);
                });
            })
            .fail(function() {
                layer.msg("error", {
                    time: 2000
                });
            });

    }
});
