/**
 * Created by thanosYao on 2015/8/12.
 */
function getUrlParam(name) {
    var url = window.location.href;
    var paraString = url.substring(url.indexOf("?")+1,url.length).split("&");
    var paraObj = {}
    var i = 0;
    var j;
    for (i = 0; j = paraString[i]; i++){
        paraObj[j.substring(0,j.indexOf("=")).toLowerCase()] = j.substring(j.indexOf("=")+1,j.length);
    }

    var returnValue = paraObj[name.toLowerCase()];
    if(typeof(returnValue)=="undefined"){
        return "";
    }else{
        return returnValue;
    }
};
var validator;
$(document).ready(function () {
    validator = $("#modifyPwdForm").validate({
        rules: {
            password: {
                required: true,
                minlength: 6,
                maxlength: 16
            },
            "repassword": {
                equalTo: "#password"
            }
        },
        messages: {
            password: {
                required: "必须填写密码",
                minlength: "密码最小为6位",
                maxlength: "密码最大为16位"
            },
            "repassword": {
                equalTo: "两次输入的密码不一致"
            }
        },
        submitHandler: function (form) {
            $.ajax({
                url : "../teacher/tlg_changePassword.action",
                method: "POST",
                data: { password: password.value, pwd: repassword.value },
                success:function(data){
                    if(data.Result == "OK"){
                        $("#modifyPwdForm").html("<h2 class='text-center'>修改成功，3秒后页面将跳转到登录页</h2>");
                        setTimeout(function () {
                            window.location.href="login.html";
                        }, 3000);
                    }else{
                        console.log(data.Result);
                    }
                }
            })
        }
    });
});