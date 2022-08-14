$(document).ready(function() {
    var uuid = url("?uuid");
    var pageNum = 1;
    var flag = true;

    getList();
    //持有记录
    function getList() {
        $.ajax({
                url: '../rest/backadmin/fundPublish/investList?',
                type: 'get',
                data: {
                    'name': '',
                    "pageSize": 10,
                    "pageNum": pageNum
                }
            })
            .done(function(r) {
                if (r.status == "SUCCESS") {
                    if (r.totalResultCount > 0) {
                        var template = $.templates("#queboxTpl");
                        var html = template.render(r.data);
                    } else {
                        var html = "<tr><td colspan='3'>没有数据</td></tr>";
                    }
                    $("#queboxCnt").html(html);
                } else {
                    layer.msg(r.message, {
                        time: 2000
                    });
                }

                //分页构造函数
                if (flag) {
                    $('#pageTool').Paging({
                        pagesize: '10',
                        count: r.totalResultCount,
                        callback: function(page, size, count) {
                            pageNum = page;
                            getList();
                            flag = false;
                        }
                    });
                    $("#pageTool").find(".ui-paging-container:last").siblings().remove();
                }

                $(".price").each(function(index, el) {
                    var this_html = formatNum($(".price").eq(index).html());
                    $(".price").eq(index).html(this_html);
                });
            })
            .fail(function() {
                layer.msg("error", {
                    time: 2000
                });
            });
    }

});
