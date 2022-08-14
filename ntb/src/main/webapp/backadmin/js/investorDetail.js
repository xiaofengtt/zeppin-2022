$(document).ready(function() {
    var uuid = (url('?uuid') != null) ? url('?uuid') : '';
    $.get('../rest/backadmin/investor/get?uuid=' + uuid, function(r) {
        if (r.status == 'SUCCESS') {
            var template = $.templates('#DataTpl');
            var html = template.render(r.data);
            $('#DataCnt').html(html);
        } else {
            layer.msg(r.message, {
                time: 2000
            });
        }
    });

    flag = true;
    var pageNum = 1;
    buyList();

    function buyList() {
        $.ajax({
                url: '../rest/backadmin/investor/getBill?uuid=' + uuid + '&pageSize=10&pageNum=' + pageNum,
                type: 'get',
                success: function(r) {
                    if (r.status == "SUCCESS") {
                        if (r.totalResultCount != 0) {
                            var template = $.templates("#billTpl").render(r.data);
                            $("#bill_box").html(template);

                            //弹框
                            $(".bill_message").colorbox({
                                iframe: true,
                                width: "600px",
                                height: "580px",
                                opacity: '0.5',
                                overlayClose: false,
                                escKey: true
                            });
                        } else {
                            $("#bill_box").html("<tr><td colspan='7'>没有数据！</td></tr>");
                        }
                    } else {
                        layer.msg(r.message, {
                            time: 2000
                        });
                    }
                }
            })
            .done(function(r) {
                for (var i = 0; i < $(".price").length; i++) {
                    var priceNum = parseFloat($(".price").eq(i).html()).toFixed(2);
                    if (priceNum > 0) {
                        $(".price").eq(i).css({
                            "color": "orange"
                        });
                    } else {
                        $(".price").eq(i).css({
                            "color": "green"
                        });
                    }
                }
                if (flag) {
                    $('#pageTool').Paging({
                        pagesize: r.pageSize,
                        count: r.totalResultCount,
                        callback: function(page, size, count) {
                            pageNum = page;
                            buyList();
                            flag = false;
                        },
                        render: function(ops) {

                        }
                    });
                    $("#pageTool").find(".ui-paging-container:last").siblings().remove();
                }
            });
    }

});