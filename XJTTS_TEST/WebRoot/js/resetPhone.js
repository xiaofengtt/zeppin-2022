/**
 * Created by thanosYao on 2015/7/24.
 */
$("#header").load("header.html");
$("#footer").load("footer.html");
$(document).ready(function(){
    init();
    getAvatar();
    $('[data-toggle="tooltip"]').tooltip();
});
function init() {
    $.ajax({
        type: "post",
        url: "../teacher/tinfo_initMobilePage.action?"+Math.random(),
        success: function (data) {
            if(data.Result == "OK"){
                phone.innerHTML=data.Records.mobile;
            }
            else{
                console.log(data);
            }
        }
    })
}
function getAvatar(){
    $.ajax({
        type: "post",
        url: "../teacher/tinfo_getHeadImg.action",
        success: function (data) {
            if(data.Result == "OK"){
                if(data.Records.headImgPath == '0'){
                    $("#bigAvatar").attr("src","../images/userInfomation/default.png");
                }
                else{
                    $("#bigAvatar").attr("src",data.Records.headImgPath);
                }
                userName.innerHTML     = data.Records.name;
            }
            else{
                console.log(data);
            }
        }
    })
}
var i = 60;
function send(){
    i--;
    if (i == -1) {
        document.getElementById("sendCodeBtn").disabled = "";
        i = 60;
        document.getElementById("sendCodeBtn").value = "重新发送";
        return null;
    }
    document.getElementById("sendCodeBtn").disabled = "disabled";
    document.getElementById("sendCodeBtn").value = i + "秒后重发";
    setTimeout("send();", 1000);
}
$("#sendCodeBtn").click(function(){
    var smsCode = $("#inputMobile").val();

    if(!(/^1[3|4|5|8][0-9]\d{4,8}$/.test(smsCode))){
        $("#inputMobileError").show();
        return false;
    }else{
        $("#inputMobileError").hide();
    }
    $.ajax({
        url: "../teacher/tinfo_sendSms.action",
        method: "POST",
        data: {
            phone:smsCode
        },
        success : function(data){
            if(data.Result == "OK"){
                console.log("smsCode send success!");
                $("#inputMobile").attr("disabled","disabled");
                send();
            }
            else{
                $("#inputMobileError").show();
            }
        }
    })
})
$(".submit").click(function(){
    var smsCode = $("#inputMobile").val();
    if(inputVerifyCode.value.length!=6){
        $("#inputVerifyCodeError").show();
        return false;
    }
    else if(!(/^1[3|4|5|8][0-9]\d{4,8}$/.test(smsCode))){
        $("#inputMobileError").show();
        return false;
    }
    else{
        $.ajax({
            url: "../teacher/tinfo_changeMobile.action",
            method: "POST",
            data: {
                mobile:$("#inputMobile").val(),
                code:$("#inputVerifyCode").val()
            },
            success:function(data){
                if(data.Result == "OK"){
                    console.log(data);
                    $(".content").html('<h4>修改成功，页面即将跳转。</h4>');
                    window.setInterval("location='personal.html'",3000);
                }
                else{
                    console.log(data);
                    errorInfo.innerHTML = data.Message;
                    $(".alert-danger").removeClass("hide");
                }
            }
        })
    }
})