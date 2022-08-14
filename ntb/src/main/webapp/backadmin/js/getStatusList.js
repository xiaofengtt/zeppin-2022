function getStatusList(url) {
    $("#allCount").html("(0)");
    $("#draftCount").html("(0)");
    $("#uncheckedCount").html("(0)");
    $("#checkedCount").html("(0)");
    $("#unpassedCount").html("(0)");
    $.ajax({
            url: url,
            type: "get"
        })
        .done(function(r) {
            if (r.status == "SUCCESS") {
                if (r.totalResultCount > 0) {
                    var totalCount = 0;
                    $.each(r.data, function(index, el) {
                        $("#" + el.status + "Count").html("(" + el.count + ")");
                        totalCount += el.count;
                    });
                    $("#allCount").html("(" + totalCount + ")");
                }
            } else {
                layer.msg(r.message, {
                    time: 2000
                });
            }
        })
        .fail(function(r) {
            layer.msg("error", {
                time: 2000
            });
        });


} //getStatusList()
