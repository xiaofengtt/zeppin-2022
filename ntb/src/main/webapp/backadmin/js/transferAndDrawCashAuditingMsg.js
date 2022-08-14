$(document).ready(function() {
    var uuid = url("?uuid");

    $(".recharge_confirm").show();
    $(".recharge_msg").hide();


    $("#back").click(function() {
        window.location.reload();
    });

    $("#allRightConfirm").click(function() {
        $(".recharge_msg").show();
        $(".recharge_confirm").hide();

    });


    $("#allRight").click(function() {
        var this_Amount = parseFloat($("#poundage").val()) + parseFloat($("#transferValue").val());
        $("#allRight").prop("disabled", true);
        if ($("#transferValue").val() == "" || parseFloat($("#transferValue").val()) <= 0) {
            layer.msg("请正确填写转账金额", {
                time: 2000
            });
            $("#allRight").prop("disabled", false);
            return false;
        }
        if (this_Amount > $("#aBalance").html().replace(/,/g, "")) {
            layer.msg("余额不足", {
                time: 2000
            });
            $("#allRight").prop("disabled", false);
            return false;
        }

        if ($("#poundage").val() == "") {
            layer.msg("请正确填写手续费", {
                time: 2000
            });
            $("#allRight").prop("disabled", false);
            return false;
        }

        if ($("#useValue").val() == "") {
            layer.msg("请填写资金用途", {
                time: 2000
            });
            $("#allRight").prop("disabled", false);
            return false;
        }
        if ($("#psValue").val() == "") {
            layer.msg("请填写备注", {
                time: 2000
            });
            $("#allRight").prop("disabled", false);
            return false;
        }
        $("#submit").ajaxSubmit(function(r) {
            if (r.status == 'SUCCESS') {
                layer.msg(r.data, {
                    time: 2000
                }, function() {
                    // window.location.href = "./accountControllerList.jsp";
                    window.location.href = document.referrer;
                });
            } else {
                layer.msg(r.data, {
                    time: 2000
                });
                $("#allRight").prop("disabled", false);
            }
        });

    });

    $("#back").click(function() {
        $(".recharge_msg").show();
        $(".recharge_confirm").hide();
    });

    getAccountMessage();

    function getAccountMessage() {
        $.ajax({
                url: '../rest/backadmin/companyAccountTransfer/operateGet',
                type: 'get',
                data: {
                    "uuid": uuid
                }
            })
            .done(function(r) {
                //生成模板加载数据
                if (r.status == "SUCCESS") {
                    var template = $.templates("#queboxTpl");
                    var html = template.render(r.data);
                    $("#queboxCnt").html(html);

                    var template_ = $.templates("#msgTpl");
                    var html_ = template_.render(r.data);
                    $("#msgCnt").html(html_);

                    var template_v = $.templates("#valueTpl");
                    var html_v = template_v.render(r.data);
                    $("#valueCnt").html(html_v);

                    $.getScript("./js/uploadFile.js",function(){
                        $("#delete").unbind("click").click(function(){
                          // $(".ajax-file-upload").find(".uploadImg").remove();
                            layer.confirm("确定要删除吗？",function(index){
                                $(".ajax-file-upload").find(".uploadSrc").remove();
                                $(".fileBox").find(".uploadSrc").remove();
                                fileArr = [];
                                $('input[name="receipt"]').val("");
                                console.log(fileArr+","+  $('input[name="receipt"]').val());
                                layer.close(index);
                            });
                        });
                        $.each(r.data.listReceipt,function(index, el) {
                          fileArr.push(el.uuid);
                          $('input[name="receipt"]').val(fileArr.join(','));
                          console.log(fileArr);
                          $(".ajax-file-upload").prepend(
                            "<a class='uploadSrc' href=../"+el.url+" target='_blank'><img src=../"+el.url+" class='uploadImg'></a>"
                          );
                        });
                        hehe = false;
                    });
                } else {
                    layer.msg(r.message, {
                        time: 2000
                    });
                }

                if (r.data.status == "draft" || r.data.status == "unpassed") {
                    $("#allRightConfirm").show();
                } else {
                    $("#allRightConfirm").hide();
                }

                var bigPrice = changeMoneyToChinese($("#transferConfirm").html().replace(/,/g, ""));
                $("#transferBigConfirm").html("人民币：" + bigPrice);


                var bigPriceValue = changeMoneyToChinese($("#transferValue").val());
                $("#transferBigValue").html("人民币：" + bigPriceValue);
                $("#transferValue").bind("input", function() {
                    if ($(this).val().length > 10) {
                        $(this).val($(this).val().slice(0, 10));
                    }
                    var bigValue = changeMoneyToChinese($("#transferValue").val());
                    $("#transferBigValue").html("人民币：" + bigValue);
                    // $("#rechargeBigConfirm").html("大写：" + bigValue);
                });

                $("#poundage").bind("input", function() {
                    if ($(this).val().length > 10) {
                        $(this).val($(this).val().slice(0, 10));
                    }
                });


            })
            .fail(function() {
                layer.msg("error", {
                    time: 2000
                });
            });
    } //getAccountMessage()









}); //document.ready;
