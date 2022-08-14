$(document).ready(function() {
    var useruuid = url("?uuid");
    var billuuid = url("?billuuid");
    var userType = url("?userType");
    $.ajax({
            url: '../rest/backadmin/investor/getBillInfo',
            type: 'get',
            data: {
                "uuid": useruuid,
                "billid": billuuid,
                "userType": userType
            }
        })
        .done(function(r) {
            if (r.status == "SUCCESS") {
                var template = $.templates("#queboxTpl");
                var html = template.render(r.data);
                $("#queboxCnt").html(html);

                $(".close").click(function() {
                    parent.$.colorbox.close();
                });
            }
        })
        .fail(function() {
            layer.msg("error", {
                time: 2000
            });
        });

});