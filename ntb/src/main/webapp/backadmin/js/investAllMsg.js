$(document).ready(function() {
    var pageNum = 1;
    var flag = true;
    var uuid = url("?uuid");
    get();
    getList();

    function get() {
        $.ajax({
                url: '../rest/backadmin/bankFinancialProduct/get',
                type: 'get',
                data: {
                    "uuid": uuid
                }
            })
            .done(function(r) {
                if (r.status == "SUCCESS") {
                    var template = $.templates("#nameTpl");
                    var html = template.render(r.data);
                    $("#nameCnt").html(html);

                } else {
                    layer.msg(r.message, {
                        time: 2000
                    });
                }

            })
            .fail(function() {
                layer.msg('error', {
                    time: 2000
                });
            });
    }

    function getList() {
        $.ajax({
                url: '../rest/backadmin/bankFinancialProductInvest/operateCheckList',
                type: 'get',
                data: {
                    "bankFinancialProduct": uuid,
                    "status": "checked",
                    "pageNum": pageNum,
                    "pageSize": 10
                }
            })
            .done(function(r) {
                if (r.status == "SUCCESS") {
                    if (r.totalResultCount > 0) {
                        var template = $.templates("#listTpl");
                        var html = template.render(r.data);
                        $("#listCnt").html(html);

                        $(".totalAmount_msg").each(function(index, el) {
                            var amount = parseFloat($(".totalAmount_msg").eq(index).html()).toFixed(10);
                            var amountV = amount.substring(0, amount.lastIndexOf('.') + 3);
                            var amountS = formatNum(amountV);
                            $(".totalAmount_msg").eq(index).html(amountS);
                        });
                    } else {
                        $("#listCnt").html("<tr><td colspan='8'>没有数据</td></tr>");
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
                layer.msg('error', {
                    time: 2000
                });
            });
    }



});
