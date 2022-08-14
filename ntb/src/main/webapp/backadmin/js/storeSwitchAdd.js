$(document).ready(function() {

    getVersion();

    //获取版本号列表
    function getVersion() {
        $.ajax({
                url: '../rest/backadmin/version/list',
                type: 'get',
                data: {
//                    "device": '02',
                    "pageNum": "1",
                    "pageSize": "1000"
                }
            })
            .done(function(r) {
                if (r.status == "SUCCESS") {
                    if (r.totalResultCount > 0) {
                        $.each(r.data, function(index, el) {
                            $("#version").append("<option value=" + el.uuid + ">" + el.versionName + "</option>");
                        });
                    } else {
                        layer.msg("请先添加版本信息！", {
                            time: 2000
                        }, function() {
                            window.location.href = document.referrer;
                        });
                    }

                } else {
                    layer.msg(r.message, {
                        time: 2000
                    });
                }
            })
            .fail(function() {
                layer.msg("error", {
                    time: 2000
                });
            });
    }




    $("#add_submit").click(function() {
        if ($("#webmarket").val() == "") {
            layer.msg("应用市场标识码不能为空", {
                time: 2000
            });
            return false;
        }

        if ($("#webmarketName").val() == "") {
            layer.msg("应用市场名称不能为空", {
                time: 2000
            });
            return false;
        }

        if ($("#version").val() == "0") {
            layer.msg("请选择版本号", {
                time: 2000
            });
            return false;
        }


        if (flagSubmit == false) {
            return false;
        }
        flagSubmit = false;
        setTimeout(function() {
            flagSubmit = true;
        }, 3000);

        sub();


    });






    function sub() {
        $("#sub").ajaxSubmit(function(r) {
            if (r.status == "SUCCESS") {
                layer.msg(r.message, {
                    time: 2000
                }, function() {
                    window.location.href = document.referrer;
                });
            } else {
                layer.msg(r.message, {
                    time: 2000
                });
            }
        });

    }
});