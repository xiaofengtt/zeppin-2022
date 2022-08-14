$(document).ready(function() {
    var reg = /(?:png|jpg|jpeg)$/;

    $("#file_img").change(function() { //选择文件后执行的函数
        if (testFile(reg, this) == true) {
            uploadImg(); //符合格式验证，上传文件
        } else {
            layer.msg("文件格式错误", {
                time: 2000
            });
        }
    });

    $("#add_submit").click(function() {
        //表单验证
        if ($("#title").val() == "") {
            layer.msg("请填写标题", {
                time: 2000
            });
            return false;
        }

        if ($("#img_id").val() == "") {
            layer.msg("请填上传文件", {
                time: 2000
            });
            return false;
        }

        submit();

    });


    //上传文件函数
    function uploadImg() {
        $("#upload_img").ajaxSubmit(function(r) {
            if (r.status == "SUCCESS") {
                $("#file_img_name").prop("src", ".." + r.data.url); //r.upload为接口返回图片路径
                $("#file_img_name").parent().prop("href", ".." + r.data.url); //图片超链接赋值
                $("#img_id").val(r.data.uuid); //文件id赋值到隐藏input
                layer.msg(r.message, {
                    time: 2000
                });
            } else {
                layer.msg(r.message, {
                    time: 2000
                });
            }
        });
    }


    //表单提交函数
    function submit() {
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

    //验证上传图片格式，返回boolean
    function testFile(_reg, _obj) {
        var obj = $(_obj);
        if (_reg.test(obj.val()) == false) {
            return false;
        } else {
            return true;
        }
    }
});