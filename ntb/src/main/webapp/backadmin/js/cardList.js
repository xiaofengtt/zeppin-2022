var pageNum = 1;
var flag = true;
$(document).ready(function() {
    function init() {
        pageNum = '1';
        flag = true;
    }
    getList();

    // $(".delete").click(function() {
    //     deleteThis(this);
    // });
    $(".msg_table").on("click", ".delete", function(event) {
        deleteThis(event.target);
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
                    url: '../rest/backadmin/coupon/delete',
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

        });
    }
    //入口弹框
    $("#addCard").colorbox({
        iframe: true,
        width: "1050px",
        height: "620px",
        opacity: '0.5',
        overlayClose: false,
        escKey: true
    });

});

function getList() {
    $.ajax({
            url: '../rest/backadmin/coupon/list',
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


                    //编辑页弹窗
                    $(".edit_card").colorbox({
                        iframe: true,
                        width: "1050px",
                        height: "620px",
                        opacity: '0.5',
                        overlayClose: false,
                        escKey: true
                    });
                } else {
                    $("#queboxCnt").html("<tr><td colspan=5>没有数据</td></tr>");
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