$(document).ready(function() {
    var uuid = url("?uuid");
    var flag;
    getAccountMessage();


    $("#allRight").click(function() {
        var this_Amount = parseFloat($("#rechargeValue").val());
        $("#allRight").prop("disabled", true);
        if ($("#rechargeValue").val() == "" || parseFloat($("#rechargeValue").val()) <= 0) {
            layer.msg("请正确填写开票金额", {
                time: 2000
            });
            $("#allRight").prop("disabled", false);
            return false;
        }

        $("#rechargeSubmit").ajaxSubmit(function(r) {
            if (r.status == "SUCCESS") {
                layer.msg(r.data, {
                    time: 2000
                }, function() {
                    window.location.href = document.referrer;
                });

            } else {
                layer.msg(r.message, {
                    time: 2000
                });
                $("#allRight").prop("disabled", false);

            }
        });
    });

    $("#back").click(function() {
        window.location.reload();
    });

    $("#allRightConfirm").click(function() {
        $(".recharge_msg").show();
        $(".recharge_confirm").hide();

    });

    function getAccountMessage() {
        $.ajax({
                url: '../rest/backadmin/qcbCompanyTransfer/operateGet',
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


                    //如果需要上传凭证，请解除下面代码
                    // $.getScript("./js/uploadFile.js", function() {
                    //     $("#delete").unbind("click").click(function() {
                    //         // $(".ajax-file-upload").find(".uploadImg").remove();
                    //         layer.confirm("确定要删除吗？", function(index) {
                    //             $(".ajax-file-upload").find(".uploadSrc").remove();
                    //             $(".fileBox").find(".uploadSrc").remove();
                    //             fileArr = [];
                    //             $('input[name="receipt"]').val("");
                    //             console.log(fileArr + "," + $('input[name="receipt"]').val());
                    //             $("#delete").hide();
                    //             layer.close(index);
                    //         });
                    //     });
                    //     $.each(r.data.listReceipt, function(index, el) {
                    //         fileArr.push(el.uuid);
                    //         $('input[name="receipt"]').val(fileArr.join(','));
                    //         console.log(fileArr);
                    //         $(".ajax-file-upload").prepend(
                    //             "<a class='uploadSrc' href=../" + el.url + " target='_blank'><img src=../" + el.url + " class='uploadImg'></a>"
                    //         );
                    //     });
                    //     hehe = false;
                    // });
                } else {
                    layer.msg(r.message, {
                        time: 2000
                    });
                }
                flag = r.data.newData.type;

                if (r.data.status == "draft" || r.data.status == "unpassed") {
                    $("#allRightConfirm").show();
                } else {
                    $("#allRightConfirm").hide();
                }

                var bigPrice = changeMoneyToChinese($("#rechargeConfirm").html().replace(/,/g, ""));
                $("#rechargeBigConfirm").html("人民币：" + bigPrice);

                var expendValue = changeMoneyToChinese($(".expendValue").eq(0).html());
                $(".expendBigValue").html("人民币：" + expendValue);

                var bigPriceValue = changeMoneyToChinese($("#rechargeValue").val());
                $("#rechargeBigValue").html("人民币：" + bigPriceValue);
                $("#rechargeValue").bind("input", function() {
                    if ($(this).val().length > 10) {
                        $(this).val($(this).val().slice(0, 10));
                    }
                    var bigValue = changeMoneyToChinese($("#rechargeValue").val());
                    $("#rechargeBigValue").html("人民币：" + bigValue);
                    // $("#rechargeBigConfirm").html("人民币：" + bigValue);
                });


                $("#rechargeValue").bind("input", function() {
                    if ($(this).val().length > 10) {
                        $(this).val($(this).val().slice(0, 10));
                    }
                    if ($(this).val() != "") {

                        var bigValue = changeMoneyToChinese($("#rechargeValue").val());
                        var rechargeValue = parseFloat($("#rechargeValue").val()).toFixed(10);
                        var rechargeValueV = rechargeValue.toString();
                        $("#rechargeConfirm").html(formatNum(rechargeValueV));
                        $("#rechargeBigValue").html("人民币：" + bigValue);
                        $("#rechargeBigConfirm").html("人民币：" + bigValue);

                        //计算扣除金额
                        var thisValue = Number($(this).val());
                        var thisRate = Number(r.data.newData.feeTicketCN) / 100 + 1;
                        var finalValue = thisValue - (thisValue / thisRate);
                        $(".expendValue").html(formatNum(finalValue.toFixed(2)));
                        $(".expendBigValue").html("人民币：" + changeMoneyToChinese(finalValue.toFixed(2)));
                    } else {
                        $(".expendValue").html("请输入开票金额");
                        $(".expendBigValue").html("");
                        $("#rechargeConfirm").html("");
                        $("#rechargeBigValue").html("");
                        $("#rechargeBigConfirm").html("");
                    }
                });
            })
            .fail(function() {
                layer.msg("error", {
                    time: 2000
                });
            });
    } //getAccountMessage()


    // $("#useValue").bind("input", function() {
    //     $("#useConfirm").html($("#useValue").val());
    // });
    // $("#psValue").bind("input", function() {
    //     $("#psConfirm").html($("#psValue").val());
    // });




}); //document.ready;