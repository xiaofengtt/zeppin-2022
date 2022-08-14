$(document).ready(function() {
    var data = "";
    var pageNum = 1;
    var flag = true;

    function init() {
        pageNum = '1';
        flag = true;
    }

    $(".level_1 ul li").click(function() {
        data = $(this).parent().attr("data-item");
        $("[data-name=" + data + "]").show().siblings(".right_box").hide();
    });
    sort();

    //发布到栏目 弹框
    $(".add_account").colorbox({
        iframe: true,
        width: "1050px",
        height: "720px",
        opacity: '0.5',
        overlayClose: false,
        escKey: true
    });

    //上移，下移
    function sort() {
        $("msg_table").on('click', '.sorts', function(event) {
            $.ajax({
                    url: '',
                    type: 'post',
                    data: {
                        "type": $(this).attr("data-type"),
                        "uuid": $(this).attr("data-uuid")
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

    //移出
    function out(t) {
        layer.confirm("确定要移出本条？", function(index) {
            if (flagSubmit == false) {
                return false;
            }
            flagSubmit = false;
            setTimeout(function() {
                flagSubmit = true;
            }, 3000);

            $.ajax({
                    url: '',
                    type: 'post',
                    data: {
                        param1: 'value1'
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

    function getList() {
        $.ajax({
                url: '',
                type: 'get',
                data: {
                    param1: 'value1'
                }
            })
            .done(function(r) {

            })
            .fail(function() {
                layer.msg("error", {
                    time: 2000
                });
            });
    }
});