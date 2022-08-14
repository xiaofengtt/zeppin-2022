$(document).ready(function() {
    var uuid = (url('?uuid') != null) ? url('?uuid') : '';
    $.get('../rest/backadmin/shbxUser/get?uuid=' + uuid, function(r) {
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


    var flag = true;
    var pageNum = 1;
    var pageNum1 = 1;

    function init() {
        pageNum = 1;
        pageNum1 = 1;
        flag = true;
    }
    insuredList();
    orderList();

    function insuredList() {
        $.ajax({
                url: '../rest/backadmin/shbxUser/insuredList?shbxUser=' + uuid + '&pageSize=10&pageNum=' + pageNum,
                type: 'get',
                success: function(r) {
                    if (r.status == "SUCCESS") {
                        if (r.totalResultCount != 0) {
                            var template = $.templates("#insuredTpl").render(r.data);
                            $("#insured_box").html(template);
                        } else {
                            $("#insured_box").html("<div>没有数据！</div>");
                        }
                    } else {
                        layer.msg(r.message, {
                            time: 2000
                        });
                    }
                }
            })
            .done(function(r) {
                if (flag) {
                    $('#pageTool').Paging({
                        pagesize: r.pageSize,
                        count: r.totalResultCount,
                        callback: function(page, size, count) {
                            pageNum = page;
                            insuredList();
                            flag = false;
                        },
                        render: function(ops) {

                        }
                    });
                    $("#pageTool").find(".ui-paging-container:last").siblings().remove();
                }
            });
    }
    
    function orderList() {
        $.ajax({
                url: '../rest/backadmin/shbxOrder/orderInfoList?uuid=' + uuid + '&pageSize=10&pageNum=' + pageNum1,
                type: 'get',
                success: function(r) {
                    if (r.status == "SUCCESS") {
                        if (r.totalResultCount != 0) {
                            var template = $.templates("#orderTpl").render(r.data);
                            $("#order_box").html(template);
                        } else {
                            $("#order_box").html("<div>没有数据！</div>");
                        }
                    } else {
                        layer.msg(r.message, {
                            time: 2000
                        });
                    }
                }
            })
            .done(function(r) {
                if (flag) {
                    $('#pageTool1').Paging({
                        pagesize: r.pageSize,
                        count: r.totalResultCount,
                        callback: function(page, size, count) {
                            pageNum1 = page;
                            orderList();
                            flag = false;
                        },
                        render: function(ops) {

                        }
                    });
                    $("#pageTool1").find(".ui-paging-container:last").siblings().remove();
                }
            });
    }
});
//切tab
$(".selectBar button").click(function(){
	$(".select-item").hide();
	$(this).addClass("light").siblings().removeClass("light");
	$("."+$(this).attr("data")).show();
});