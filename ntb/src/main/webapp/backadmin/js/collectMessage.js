$(document).ready(function() {
    var uuid = url("?uuid");
    var pageNum = 1;
    var flag = true;

    getList();

    function getList() {
        $.ajax({
                url: '../rest/backadmin/investorProduct/listProductBuyRecords?',
                type: 'get',
                data: {
                    'product': uuid,
                    "pageSize": 10,
                    "pageNum": pageNum
                }
            })
            .done(function(r) {
                if (r.status == "SUCCESS") {
                    if (r.totalResultCount > 0) {
                        var template = $.templates("#queboxTpl");
                        var html = template.render(r.data.dataList);
                        $("#queboxCnt").html(html);
                    } else {
                        var html = "<tr><td cols=4>没有数据</td></tr>";
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
