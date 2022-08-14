$(document).ready(function() {
    var uuid = url("?uuid");
    getAccountMessageList();

    function getBindAccount(type, value) {
        $.ajax({
                url: '../rest/backadmin/companyAccount/list',
                type: 'get',
                data: {
                    "pageNum": 1,
                    "pageSize": 1000,
                    "type": "collect"
                }
            })
            .done(function(r) {
                $.each(r.data, function(index, el) {
                    $("#bindAccount").append("<option value=" + el.uuid + ">" + el.accountName + "</option>");
                });
                if (type == "third") {
                    $("#bindAccount").val(value);
                    $("#bindAccount").parent().show();
                    $("#bindAccount").prop("disabled", false);
                } else {
                    $("#bindAccount").parent().hide();
                    $("#bindAccount").prop("disabled", true);
                }

            })
            .fail(function() {
                layer.msg("error", {
                    time: 2000
                });
            });
    }


    function getbranchBankList(bank, branchBankValue) {
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
                $("#branchBankList").val(branchBankValue);
            })
            .fail(function() {
                layer.msg("error", {
                    time: 2000
                });
            });
    }
    //获取企业账户信息
    function getAccountMessageList() {
        $.ajax({
                url: '../rest/backadmin/companyAccount/get',
                type: 'get',
                data: {
                    "uuid": uuid
                }
            })
            .done(function(r) {
                //生成模板
                if (r.status == "SUCCESS") {
                    var template = $.templates("#account_message_tpl");
                    var html = template.render(r.data);
                    $("#account_message_Cnt").html(html);
                    $("#branchBankList").select2();
                } else {
                    layer.msg(r.status, {
                        time: 2000
                    });
                }


                getBindAccount(r.data.type, r.data.bindAccount);
                $("#type").prop("disabled", true);
                $("#type").val(r.data.type);
                $("#status").val(r.data.status);

                getbranchBankList(r.data.bank, r.data.branchBank);



            })
            .fail(function() {
                layer.msg("error", {
                    time: 2000
                });
            });
    } //getAccountMessage();



    //添加企业账户表单提交
    $("#add_submit").click(function() {
        $("#add_submit").prop("disabled", true);
        $("#type").prop("disabled", false);
        if ($("#companyName").val() == "") {
            layer.msg("开户名不能为空", {
                time: 2000
            });
            $("#add_submit").prop("disabled", false);
            $("#type").prop("disabled", true);
            return false;
        }

        if ($("#accountName").val() == "") {
            layer.msg("开户别名不能为空", {
                time: 2000
            });
            $("#add_submit").prop("disabled", false);
            $("#type").prop("disabled", true);
            return false;
        }
        if ($("#accountNum").val() == "") {
            layer.msg("账号不能为空", {
                time: 2000
            });
            $("#add_submit").prop("disabled", false);
            $("#type").prop("disabled", true);
            return false;
        }

        if ($("#type").val() == "0") {
            layer.msg("请选择账户类型", {
                time: 2000
            });
            $("#add_submit").prop("disabled", false);
            $("#type").prop("disabled", true);
            return false;
        }
        if ($("#type").val() == "third") {
            if ($("#bindAccount").val() == "0") {
                layer.msg("请选择绑定账户", {
                    time: 2000
                });
                $("#add_submit").prop("disabled", false);
                $("#type").prop("disabled", true);
                return false;
            }

        }
        if ($("#status").val() == "0") {
            layer.msg("请选择状态", {
                time: 2000
            });
            $("#add_submit").prop("disabled", false);
            $("#type").prop("disabled", true);
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
                $("#type").prop("disabled", true);
            }
        });


    }); //添加企业账户表单提交

}); //$(document).ready();