$(document).ready(function() {
    get();
});
var uuid = (url('?uuid') != null) ? url('?uuid') : '';

function get() {
    $.get('../rest/backadmin/qcbcompany/operateGet?qcbCompany=' + uuid, function(r) {
        if (r.status == 'SUCCESS') {
            var template = $.templates('#DataTpl');
            var html = template.render(r.data);
            $('#DataCnt').html(html);

            $("#allRight").unbind("click").click(function() {
                layer.confirm("确认变更状态吗？", function(index) {
                    if (flagSubmit == false) {
                        return false;
                    }
                    flagSubmit = false;
                    setTimeout(function() {
                        flagSubmit = true;
                    }, 3000);

                    $("#checked").ajaxSubmit(function(r) {
                        if (r.status == "SUCCESS") {
                            layer.msg(r.message, {
                                time: 2000
                            }, function() {
                                get();
                            });
                        } else {
                            layer.msg(r.message, {
                                time: 2000
                            });
                        }
                    });

                    layer.close(index);
                });
            });
            $("#pass").colorbox({
                iframe: true,
                width: "520px",
                height: "320px",
                opacity: '0.5',
                overlayClose: false,
                escKey: true
            });


        } else {
            layer.msg(r.message, {
                time: 2000
            });
        }
    });
}