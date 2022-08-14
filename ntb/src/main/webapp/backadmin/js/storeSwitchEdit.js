$(document).ready(function() {
    var uuid = url("?uuid");
    var version = "";
    get();


    //获取版本号列表
    function getVersion() {
        $.ajax({
                url: '../rest/backadmin/version/list',
                type: 'get',
                data: {
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
                        $("#version").val(version);
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

    function get() {
        $.ajax({
                url: '../rest/backadmin/market/get',
                type: 'get',
                data: {
                    "uuid": uuid
                }
            })
            .done(function(r) {
                if (r.status == "SUCCESS") {
                    var template = $.templates("#queboxTpl");
                    var html = template.render(r.data);
                    $("#queboxCnt").html(html);
                    version = r.data.version;
                    getVersion();
                } else {
                    layer.msg(r.message, {
                        time: 2000
                    });
                }

                $("#add_submit").click(function() {
                    if ($("#version").val() == "") {
                        layer.msg("版本号不能为空", {
                            time: 2000
                        });
                        return false;
                    }

                    if ($("#device").val() == "") {
                        layer.msg("未上传文件", {
                            time: 2000
                        });
                        return false;
                    }

                    if ($("#versionName").val() == "") {
                        layer.msg("版本名称不能为空", {
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
            })
            .fail(function() {
                layer.msg("error", {
                    time: 2000
                });
            });

    }

    //验证文件格式
    function testFile(_reg, _obj) {
        var obj = $(_obj);
        if (_reg.test(obj.val()) == false) {
            return false;
        } else {
            return true;
        }
    }

    function uploadHtml() {
        $("#upload_html").ajaxSubmit(function(r) {
            if (r.status == "SUCCESS") {
                $("#pkg_id").val(r.data.uuid);
                $("#file_html_name").val(r.data.filename);
                layer.msg(r.message);
            } else {
                layer.msg(r.message, {
                    time: 2000
                });
                $("#device").val("");
                $("#deviceValue").val("");
                $("#file_html_name").val("请上传文件...");
            }
        });
    }



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