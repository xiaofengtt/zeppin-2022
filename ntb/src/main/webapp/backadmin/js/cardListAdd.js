$(document).ready(function() {
    // $("#jx").parent().hide(); //加息字段默认隐藏
    $("#couponJ").prop("disabled", true); //加息字段表单禁用
    $("#deadline").prop("disabled", true); //使用期限默认禁用
    $("#expriyDate").prop("disabled", true); //截止时间默认禁用

    $("#couponType").change(function(event) {
        var this_value = $(this).find("option:selected").val();
        if (this_value == "cash") {
            $("#couponM").parent().show();
            $("#couponM").prop("disabled", false);
            $("#couponJ").parent().hide();
            $("#couponJ").prop("disabled", true);
        } else {
            $("#couponM").parent().hide();
            $("#couponM").prop("disabled", true);
            $("#couponJ").parent().show();
            $("#couponJ").prop("disabled", false);
        }
    });

    $(".checkbox").eq(0).change(function(event) {
        if ($(this).is(":checked") == true) {
            $("#deadline").prop("disabled", false);
        } else {
            $("#deadline").prop("disabled", true);
        }
    });

    $(".checkbox").eq(1).change(function(event) {
        if ($(this).is(":checked") == true) {
            $("#expriyDate").prop("disabled", false);
        } else {
            $("#expriyDate").prop("disabled", true);
        }
    });


    function add() {
        laydate({
            elem: '#expriyDate',
            format: 'YYYY-MM-DD hh:mm:ss',
            istime: true
        });
    }
    add();


    //提交表单
    $("#allRight").click(function() {
        //名称
        if ($("#couponName").val() == "") {
            layer.msg("名称不能为空", {
                time: 2000
            });
            return false;
        }


        //面值
        if ($("#couponType").find("option:selected").val() == "cash") {
            if ($("#couponM").val() == "") {
                layer.msg("面值不能为空", {
                    time: 2000
                });
                return false;
            }
        }


        //加息
        if ($("#couponName").find("option:selected").val() == "interests") {
            if ($("##couponJ").val() == "") {
                layer.msg("加息字段不能为空", {
                    time: 2000
                });
                return false;
            }
        }

        //最小投资额
        if ($("#minInvestAccount").val() == "") {
            layer.msg("请正确填写最小投资额", {
                time: 2000
            });
            return false;
        }

        //使用期限与截止时间勾选状态
        if ($(".checkbox").eq(0).prop("checked") == false && $(".checkbox").eq(1).prop("checked") == false) {
            layer.msg("请勾选使用期限", {
                time: 2000
            });
            return false;
        }


        // 使用期限
        if ($(".checkbox").eq(0).prop("checked") == true) {
            if ($("#deadline").val() == "") {
                layer.msg("请正确填写使用期限", {
                    time: 2000
                });
                return false;
            }
        }

        // 截止时间
        if ($(".checkbox").eq(1).prop("checked") == true) {
            if ($("#expriyDate").val() == "") {
                layer.msg("请正确填写截止时间", {
                    time: 2000
                });
                return false;
            }
        }

        if (flagSubmit == false) {
            return false;
        }
        flagSubmit = false;
        setTimeout(function() {
            flagSubmit = true;
        }, 3000);


        $("#submit").ajaxSubmit(function(r) {
            if (r.status == "SUCCESS") {
                layer.msg(r.message, {
                    time: 2000
                }, function() {
                    parent.getList();
                    parent.$.colorbox.close();
                });

            } else {
                layer.msg(r.message, {
                    time: 2000
                });
            }
        });

    }); //提交表单
}); //document.ready