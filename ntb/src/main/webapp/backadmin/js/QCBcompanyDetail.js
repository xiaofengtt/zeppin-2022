$(document).ready(function() {
    var uuid = (url('?uuid') != null) ? url('?uuid') : '';
    $.get('../rest/backadmin/qcbcompany/get?uuid=' + uuid, function(r) {
        if (r.status == 'SUCCESS') {
            var template = $.templates('#DataTpl');
            var html = template.render(r.data);
            $('#DataCnt').html(html);

            // //弹框
            $(".confirm-page").colorbox({
                iframe: true,
                width: "800px",
                height: "900px",
                opacity: '0.5',
                overlayClose: false,
                escKey: true
            });

        } else {
            layer.msg(r.message, {
                time: 2000
            });
        }
    });


    var flag = true;
    var pageNum = 1;

    function init() {
        pageNum = 1;
        flag = true;
    }
    buyList();
    getTypeList();

    // $(".statusDiv a").click(function() {
    //     $(this).addClass("statusLight").siblings().removeClass("statusLight");
    //     init();
    //     buyList();
    // });

    function buyList() {
        $.ajax({
                url: '../rest/backadmin/qcbcompany/historyList?uuid=' + uuid + '&pageSize=10&pageNum=' + pageNum,
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
                            $("#bill_box").html("<tr><td colspan='6'>没有数据！</td></tr>");
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

    // function getTypeList() {
    //     $("#allCount").html("(0)");
    //     $("#transferCount").html("(0)");
    //     $("#rechargeCount").html("(0)");
    //     $("#expendCount").html("(0)");
    //     $("#investCount").html("(0)");
    //     $("#redeemCount").html("(0)");
    //     $("#returnCount").html("(0)");
    //     $("#takeoutCount").html("(0)");
    //     $("#fillinCount").html("(0)");
    //     $("#qcb_takeoutCount").html("(0)");
    //     $("#qcb_rechargeCount").html("(0)");
    //     $("#qcb_payrollCount").html("(0)");
    //     $("#emp_takeoutCount").html("(0)");
    //     $("#emp_fillinCount").html("(0)");
    //
    //     $.ajax({
    //             url: "../rest/backadmin/qcbcompanyAccount/historyTypeList",
    //             type: "get",
    //             data: {
    //                 "uuid": uuid
    //             }
    //         })
    //         .done(function(r) {
    //             if (r.status == "SUCCESS") {
    //                 if (r.totalResultCount > 0) {
    //                     var totalCount = 0;
    //                     $.each(r.data, function(index, el) {
    //                         $("#" + el.status + "Count").html("(" + el.count + ")");
    //                         totalCount += el.count;
    //                     });
    //                     $("#allCount").html("(" + totalCount + ")");
    //                 }
    //             } else {
    //                 layer.msg(r.message, {
    //                     time: 2000
    //                 });
    //             }
    //         })
    //         .fail(function(r) {
    //             layer.msg("error", {
    //                 time: 2000
    //             });
    //         });
    //
    //
    // } //getTypeList()

});