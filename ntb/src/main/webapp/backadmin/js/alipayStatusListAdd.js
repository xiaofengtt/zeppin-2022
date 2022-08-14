$(document).ready(function() {

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
