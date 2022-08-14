$(document).ready(function() {
    var uuid = url("?uuid");


    //获取栏目信息
    function get() {

        $.ajax({
                url: '',
                data: {
                    param1: 'value1'
                }
            })
            .done(function(r) {
                if (r.status == "SUCCESS") {
                    var template = $.templates("#queboxTpl");
                    var html = template.render(r.data);
                    $("#queboxCnt").html(html);
                } else {
                    layer.msg(r.message, {
                        time: 2000
                    });
                }



                $("#add_submit").click(function() {

                    //栏目地址
                    if ($("#lmdz").val() == "") {
                        layer.msg("栏目地址不能为空", {
                            time: 2000
                        });
                        return false;
                    }


                    //栏目名称
                    if ($("#lmmc").val() == "") {
                        layer.msg("栏目名称不能为空", {
                            time: 2000
                        });
                        return false;
                    }




                    //顺序
                    if ($("#sx").val() == "") {
                        layer.msg("请正确填写顺序", {
                            time: 2000
                        });
                        return false;
                    }

                    //编号
                    if ($("#bh").val() == "") {
                        layer.msg("请正确填写编号", {
                            time: 2000
                        });
                        return false;
                    }


                    if (flagSubmit == false) {
                        return false;
                    }
                    flagSubmit = false;
                    setTimeout(function() {
                        flagSubmit = true;
                    }, 3000);

                    layer.msg("okok", {
                        time: 2000
                    }, function() {
                        parent.$.colorbox.close();
                    });

                    // $("#add").ajaxSubmit(function(r) {
                    //     if (r.status == "SUCCESS") {
                    //         layer.msg(r.message, {
                    //             time: 2000
                    //         });
                    //         parent.$.colorbox.close();
                    //     } else {
                    //         layer.msg(r.message, {
                    //             time: 2000
                    //         });
                    //     }
                    // });

                }); //提交表单
            })
            .fail(function() {
                layer.msg("error", {
                    time: 2000
                });
            });

    }
});
