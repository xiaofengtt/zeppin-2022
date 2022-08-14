$(document).ready(function() {
    var uuid = url("?uuid");
    get();

    function get() {
        $.ajax({
                url: '../rest/backadmin/bkpayment/get',
                type: 'get',
                data: {
                    "uuid": uuid
                }
            })
            .done(function(r) {
                if (r.status == "SUCCESS") {
                    $("#paymentName").val(r.data.payment);
                    $("#paymentDes").val(r.data.paymentDes);
                    $("#uuid").val(r.data.uuid);
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

    }


    //添加企业账户表单提交
    $("#add_submit").click(function() {
        $("#add_submit").prop("disabled", true);
        if ($("#paymentName").val() == "") {
            layer.msg("名称不能为空", {
                time: 2000
            });
            $("#add_submit").prop("disabled", false);
            return false;
        }




        if ($("#paymentDes").val() == "") {
            layer.msg("描述不能为空", {
                time: 2000
            });
            $("#add_submit").prop("disabled", false);
            return false;
        }



        $("#add").ajaxSubmit(function(r) {
            if (r.status == "SUCCESS") {
                layer.msg(r.message, {
                    time: 2000
                }, function() {
                    window.location.href = "./alipayStatusList.jsp";
                });

            } else {
                layer.msg(r.message, {
                    time: 2000
                });
                $("#add_submit").prop("disabled", false);
            }
        });


    });

}); //$(document).ready();
