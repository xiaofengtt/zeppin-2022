$(document).ready(function() {
    var regHtml = /(?:html)$/;
    var regImg = /(?:png|jpg|jpeg)$/;



    //上传HTML
    $("#file_html").change(function() {
        //截取上传文件名
        var this_value = $(this).val().substring($(this).val().lastIndexOf('\\') + 1, $(this).val().length);
        if (testFile(regHtml, this) == true) {
            //文件名赋值
            $("#file_html_name").html(this_value);
            uploadHtml();

        } else {
            layer.msg("文件格式错误!<br>请上传扩展名为'.html'的文件", {
                time: 2000
            });
            $("#file_html_name").html("");
            $("#file_html_name").prop("href", "");
            $(this).val("");
        }
    });


    //上传图片
    $("#file_img").change(function() {

        if (testFile(regImg, this) == true) {

            uploadImg();

        } else {
            layer.msg("文件格式错误!<br>请上传扩展名为'.png .jpg .jpeg'的文件", {
                time: 2000
            });
            $("#file_img_name").prop("src", "");
            $(this).val("");
        }
    });


    $("#add_submit").click(function() {
        if ($("#bt").val() == "") {
            layer.msg("标题不能为空", {
                time: 2000
            });
            return false;
        }

        if ($("#html_id").val() == "") {
            layer.msg("未上传HTML文件", {
                time: 2000
            });
            return false;
        }

        if ($("#img_id").val() == "") {
            layer.msg("未上传封面", {
                time: 2000
            });
            return false;
        }

        if ($("#wjm").val() == "") {
            layer.msg("文件名不能为空", {
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

        layer.msg("okok", {
            time: 2000
        }, function() {
            parent.$.colorbox.close();
        });

        // sub();
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
        $("#upload_html").ajaxSubmit(function(r) {
            if (r.status == "SUCCESS") {
                //把文件ID赋值到隐藏的input
                //把文件路径赋值到a标签 以供预览
            } else {
                layer.msg(r.message, {
                    time: 2000
                });
            }
        });
    }

    function uploadImg() {
        $("#upload_img").ajaxSubmit(function(r) {
            if (r.status == "SUCCESS") {
                //把文件ID赋值到隐藏的input
                //把文件路径赋值到img标签 以供预览
            } else {
                layer.msg(r.message, {
                    time: 2000
                });
            }
        });
    }


    function sub() {
        $.ajax({
                url: '',
                type: 'post',
                data: {
                    param1: 'value1'
                }
            })
            .done(function(r) {
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
            })
            .fail(function() {
                layer.msg("error", {
                    time: 2000
                });
            });
    }
});
