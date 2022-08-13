/**
 * Created by thanosYao on 2015/7/6.
 */
// this is the id of the form
$("#signUpForm").submit(function() {
    $(".login").text("登录中……");
    var url = "../teacher/tlg_login.action";
    var rememberMe="";
    if($("#remember")[0].checked)rememberMe="1";

    $.ajax({
        type: "POST",
        url: url,
        data: {
            username: $("#inputEmail").val(),
            passwd: $("#inputPassword").val(),
            rememberMe: rememberMe
        },
        success: function(data) {
            if(data.Result == "OKEY") {
                var loginStr = data.IdCard+"_"+data.Password;
                $.cookie("loginStr",null);
                $.cookie("loginStr",loginStr,{expires:7});
                window.location.href="personal.html";
            } else if(data.Result == "OK"){
                window.location.href="personal.html";
            }
            else{
                $(".login").text("登录");
                $(".errorInfo").text(data.Message);
                $(".alert-danger").removeClass("hide");
            }
        }
    });

    return false; // avoid to execute the actual submit of the form.
});