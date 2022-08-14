$(document).ready(function() {
    var regApp = /(?:ipa)$/;
    var regApk = /(?:apk)$/;



    //上传APP
    $("#file_html").change(function() {
        //截取上传文件名
        var this_value = $(this).val().substring($(this).val().lastIndexOf('\\') + 1, $(this).val().length);
        if (testFile(regApp, this) == true) {
            //文件名赋值
            $("#file_html_name").html(this_value);
            $("#device").val("IOS"); //如果传的是苹果，赋值苹果
            $("#deviceValue").val("03");
            uploadHtml();
        } else if (testFile(regApk, this) == true) {
            //文件名赋值
            $("#file_html_name").html(this_value);
            $("#device").val("Android"); //如果传的是安卓，赋值安卓
            $("#deviceValue").val("02");
            uploadHtml();

        } else {
            layer.msg("文件格式错误!<br>请上传扩展名为'.ipa .apk'的文件", {
                time: 2000
            });
            $("#file_html_name").html("");
            $(this).val("");
            $("#device").val("");
            $("#deviceValue").val("");
            $("#file_html_name").html("请上传文件...");
        }
    });





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
        var i = 0;
        $(".bar-inner").css({
            "width": "0%"
        });
        var t = setInterval(function() {
            i++;
            $(".bar-inner").css({
                "width": i * 10 + "%"
            });
            $("#rate").html(i * 10 + "%");
            if (i == 9) {
                $(".bar-inner").css({
                    "width": "99%"
                });
                $("#rate").html("99%");
            }
            clearInterval(t);

        }, 2000);
        $("#upload_html").ajaxSubmit(function(r) {
            if (r.status == "SUCCESS") {
                $("#pkg_id").val(r.data.uuid);
                $("#file_html_name").val(r.data.filename);
                layer.msg(r.message);
                clearInterval(t);
                $(".bar-inner").css({
                    "width": "100%"
                });
                $("#rate").html("100%");

            } else {
                layer.msg(r.message, {
                    time: 2000
                });
                $("#file_html").val("");
                $("#device").val("");
                $("#deviceValue").val("");
                $("#file_html_name").html("请上传文件...");
                $(".bar-inner").css({
                    "width": "0%"
                });
                $("#rate").html("");
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