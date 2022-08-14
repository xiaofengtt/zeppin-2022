$(document).ready(function() {
    var pageNum = 1;
    var flag = true;


    function init() {
        pageNum = '1';
        flag = true;
    }
    getList();

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
                    url: '../rest/backadmin/couponStrategy/delete',
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

    function change(_this) {
        layer.confirm("确定要变更状态吗？", function(index) {

            if (flagSubmit == false) {
                return false;
            }
            flagSubmit = false;
            setTimeout(function() {
                flagSubmit = true;
            }, 3000);
            $.ajax({
                    url: '../rest/backadmin/couponStrategy/change',
                    type: "post",
                    data: {
                        "uuid": $(_this).attr("data-uuid"),
                        "switchFlag": $(_this).attr("data-status"),
                        "CSRFToken": $('input[name="CSRFToken"]').val()
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
    } //change




    function getList() {
        $.ajax({
                url: '../rest/backadmin/couponStrategy/list',
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
                $(".edit").colorbox({
                    iframe: true,
                    width: "1050px",
                    height: "820px",
                    opacity: '0.5',
                    overlayClose: false,
                    escKey: true
                });

                $(".delete").click(function() {
                    deleteThis(this);
                });

                $(".change").click(function() {
                    change(this);
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
    //入口弹框
    $("#add").colorbox({
        iframe: true,
        width: "1050px",
        height: "820px",
        opacity: '0.5',
        overlayClose: false,
        escKey: true
    });

});