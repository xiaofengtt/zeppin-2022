$(document).ready(function() {
    var uuid = url("?uuid");


    $.ajax({
            url: '../rest/backadmin/companyAccountTransfer/get',
            type: 'get',
            data: {
                "uuid": uuid
            }
        })
        .done(function(r) {
            if (r.status == "SUCCESS") {
                var template = $.templates("#queBoxTpl");
                var html = template.render(r.data);
                $("#queBoxCnt").html(html);
                $(".recharge_confirm").show();
            } else {
                layer.msg(r.message, {
                    time: 2000
                });
            }
            var totalAmount = changeMoneyToChinese($("#totalAmount").val());
            $("#rechargeBigConfirm").html("人民币：" + totalAmount);

            $("#rechargeConfirm").html(formatNum(r.data.totalAmount.toString()));

        })
        .fail(function() {
            layer.msg("error", {
                time: 2000
            });
        });

});