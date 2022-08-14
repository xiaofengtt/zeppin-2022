$(document).ready(function() {
    $("#bindAccount").parent().hide();
    $("#bindAccount").prop("disabled", true);
    $("#branchBankList").select2();
    $("#type").change(function() {
        if ($(this).val() == "third") {
            $("#bankList").parent().hide();
            $("#branchBbankList").parent().hide();
            $(".submit_logo").hide();
            $("#bankList").prop("disabled", true);
            $("#branchBankList").prop("disabled", true);
        } else {
            $("#bankList").parent().show();
            $("#branchBankList").parent().show();
            $(".submit_logo").show();
            $("#bankList").prop("disabled", false);
            $("#branchBankList").prop("disabled", false);
        }
    });


    //获取开户银行列表
    getBankList();
    // getBindAccount();

    function getBankList() {
        $.ajax({
                url: '../rest/backadmin/bank/list',
                type: 'get',
                data: {
                    "pageNum": 1,
                    "pageSize": 1000
                }
            })
            .done(function(r) {
                $.each(r.data, function(index, el) {
                    $("#bankList").append("<option value=" + el.uuid + " data-src=" + el.logoUrl + ">" + el.name + "</option>");
                });

                $("#bankList").change(function(event) {
                    var thisUrl = $("#bankList").find("option:selected").attr("data-src");
                    var thisUuid = $("#bankList").find("option:selected").attr("value");
                    $(".submit_logo img").prop("src", ".." + thisUrl);
                    getbranchBankList(thisUuid);
                });
            })
            .fail(function() {
                layer.msg("error", {
                    time: 2000
                });
            });
    }

    function getbranchBankList(bank) {
        $.ajax({
                url: '../rest/backadmin/branchBank/list',
                type: 'get',
                data: {
                    "bank": bank,
                    "pageNum": 1,
                    "pageSize": 1000
                }
            })
            .done(function(r) {
                $.each(r.data, function(index, el) {
                    $("#branchBankList").append("<option value=" + el.uuid + ">" + el.name + "</option>");
                });

            })
            .fail(function() {
                layer.msg("error", {
                    time: 2000
                });
            });
    }

    // function getBindAccount() {
    //     $.ajax({
    //             url: '../rest/backadmin/companyAccount/list',
    //             type: 'get',
    //             data: {
    //                 "pageNum": 1,
    //                 "pageSize": 1000,
    //                 "type": "collect"
    //             }
    //         })
    //         .done(function(r) {
    //             $.each(r.data, function(index, el) {
    //                 $("#bindAccount").append("<option value=" + el.uuid + ">" + el.accountName + "</option>");
    //             });
    //
    //         })
    //         .fail(function() {
    //             layer.msg("error", {
    //                 time: 2000
    //             });
    //         });
    // }



    //添加企业账户表单提交
    $("#add_submit").click(function() {
        $("#add_submit").prop("disabled", true);
        if ($("#type").val() == "0") {
            layer.msg("请选择账户类型", {
                time: 2000
            });
            $("#add_submit").prop("disabled", false);
            return false;
        }

        if ($("#type").val() != "third") {

            if ($("#bankList").val() == "0") {
                layer.msg("请选择开户行", {
                    time: 2000
                });
                $("#add_submit").prop("disabled", false);
                return false;
            }
            if ($("#branchBankList").val() == "0") {
                layer.msg("请选择支行", {
                    time: 2000
                });
                $("#add_submit").prop("disabled", false);
                return false;
            }
        }


        if ($("#companyName").val() == "") {
            layer.msg("开户名不能为空", {
                time: 2000
            });
            $("#add_submit").prop("disabled", false);
            return false;
        }

        if ($("#accountName").val() == "") {
            layer.msg("开户别名不能为空", {
                time: 2000
            });
            $("#add_submit").prop("disabled", false);
            return false;
        }
        if ($("#accountNum").val() == "") {
            layer.msg("账号不能为空", {
                time: 2000
            });
            $("#add_submit").prop("disabled", false);
            return false;
        }



        if ($("#status").val() == "0") {
            layer.msg("请选择状态", {
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
                    window.location.href = "./accountControllerList.jsp";
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