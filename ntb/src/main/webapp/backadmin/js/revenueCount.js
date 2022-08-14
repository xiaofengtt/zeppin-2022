$(document).ready(function() {
    var uuid = url("?uuid");
    $.ajax({
            url: '../rest/backadmin/bankFinancialProductPublish/get',
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

                var realReturnRate = $(".realReturnRate").html().substring(0, $(".realReturnRate").html().lastIndexOf('.') + 3);
                $(".realReturnRate").html(realReturnRate + "%");
            }
        })
        .fail(function() {
            layer.msg("error", {
                time: 2000
            });
        });

});
