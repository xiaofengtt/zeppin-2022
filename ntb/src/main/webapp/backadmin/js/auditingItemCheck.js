$(document).ready(function() {
    $.ajax({
            url: '../rest/backadmin/companyAccount/operateCheckTotalTypeList',
            type: 'get'
        })
        .done(function(r) {
            if (r.status == "SUCCESS") {
                $.each(r.data, function(index, el) {
                    $("#" + el.status).html(el.count);
                });
            }
        })
        .fail(function() {
            layer.msg("error", {
                time: 2000
            });
        });

});
