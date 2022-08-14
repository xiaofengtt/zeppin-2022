$(document).ready(function() {
    var uuid = url("?uuid");
    //获取企业账户信息
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

    function getAccountMessageList() {

        $.ajax({
                url: '../rest/backadmin/companyAccount/operateGet',
                type: 'get',
                data: {
                    "uuid": uuid
                }
            })
            .done(function(r) {
                if (r.status == "SUCCESS") {
                    $("#totalAmount").html(r.data.investmentCN);
                    var template = $.templates("#account_message_tpl");
                    var html = template.render(r.data);
                    var template_ = $.templates("#accountMessageEditTpl");
                    var html_ = template_.render(r.data);
                    $("#account_message_Cnt").html(html);
                    $("#accountMessageEditCnt").html(html_);


                    $("#type").val(r.data.newData.type);
                    $("#type").prop("disabled", true);
                    $("#status").val(r.data.newData.status);

                    getbranchBankList(r.data.newData.bank, r.data.newData.branchBank);
                    $("#branchBankList").select2();

                } else {
                    layer.msg(r.status, {
                        time: 2000
                    });
                }

                getBindAccount(r.data.newData.type, r.data.newData.bindAccount);



                if (r.data.status == "unchecked" || r.data.status == "checked") {
                    $("#edit").hide();
                }
                $("#edit").click(function() {
                    $("#account_message_Cnt").hide();
                    $(".edit").show();
                });
                $("#close").click(function() {
                    $("#account_message_Cnt").show();
                    $(".edit").hide();
                });

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
                        layer.msg("请正确填写账号", {
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
                        if ($("#branchBankList").val() == "0") {
                            layer.msg("请选择支行", {
                                time: 2000
                            });
                            $("#add_submit").prop("disabled", false);
                            $("#type").prop("disabled", false);
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



                    $("#add").ajaxSubmit(function(data) {
                        if (data.status == "SUCCESS") {
                            layer.msg(data.data, {
                                time: 2000
                            }, function() {
                                window.location.href = document.referrer;
                                // window.close();
                            });
                        } else {
                            layer.msg(data.message, {
                                time: 2000
                            });
                            $("#add_submit").prop("disabled", false);
                            $("#type").prop("disabled", true);
                        }
                    });
                });


            })
            .fail(function() {
                layer.msg("error", {
                    time: 2000
                });
            });
    } //getAccountMessage();
});