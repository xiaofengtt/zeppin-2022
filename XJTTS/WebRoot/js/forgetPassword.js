/**
 * Created by thanosYao on 2015/8/4.
 */
function replace(e){
	var timestamp = Date.parse(new Date());
	var random = Math.ceil(Math.random()*100);
	e.src='../admin/login_authImg.action?timestr='+timestamp+random;
}
function showbox1(){
    $(".box1").removeClass("hide");
    $(".submitPhone").removeClass("hide");
    $(".box2").addClass("hide");
    $(".submitEmail").addClass("hide");
}

function showbox2(){
    $(".box1").addClass("hide");
    $(".submitPhone").addClass("hide");
    $(".box2").removeClass("hide");
    $(".submitEmail").removeClass("hide");
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
//    setTimeout("send();", 1000);
    t = setTimeout("send();", 1000);
}
$("#sendCodeBtn").click(function(){
    var smsCode = $("#inputMobile").val();
    var authCode = $('#inputImageCode').val();
    if(idCard.value.length<15){
        errorInfo.innerHTML = "身份证号码错误";
        $(".errorPhoneBox").show();
        return false;
    }else{
        $(".errorPhoneBox").hide();
    }
    if(!(/^1[3|4|5|6|7|8|9][0-9]\d{4,8}$/.test(smsCode))){
        errorInfo.innerHTML= "手机号错误";
        $(".errorPhoneBox").show();
        return false;
    }else{
        $(".errorPhoneBox").hide();
    }
    send();
    $.ajax({
        url: "../teacher/tlg_sendSms.action",
        method: "POST",
        data: {
            phone:smsCode,
            idCard:idCard.value,
            authCode:authCode
        },
        success : function(data){
            if(data.Result == "OK"){
                console.log("smsCode send success!");
                $("#inputMobile").attr("disabled","disabled");
                
            }
            else{
            	clearTimeout(t);
                document.getElementById("sendCodeBtn").value = "发送验证码";
                document.getElementById("sendCodeBtn").disabled = "";
                i = 60;
                errorInfo.innerHTML= data.Message;
                $(".errorPhoneBox").show();
            }
        }
    })
})
function submitPhone(){
    var smsCode = $("#inputMobile").val();
    if(idCard.value.length<15){
        errorInfo.innerHTML = "身份证号码错误";
        $(".errorPhoneBox").show();
        return false;
    }else{
        $(".errorPhoneBox").hide();
    }
    if(!(/^1[3|4|5|6|7|8|9][0-9]\d{4,8}$/.test(smsCode))){
        errorInfo.innerHTML= "手机号错误";
        $(".errorPhoneBox").show();
        return false;
    }else{
        $(".errorPhoneBox").hide();
    }
    if(inputVeriyCode.value.length==0){
        errorInfo.innerHTML = "验证码未填写";
        $(".errorPhoneBox").show();
    }
    $.ajax({
        url: "../teacher/tlg_getPassword.action",
        method: "POST",
        data: {
            code:inputVeriyCode.value,
        },
        success : function(data){
            if(data.Result == "OK"){
                window.location.href="modifyPassword.html";
            }
            else{
                errorInfo.innerHTML= data.Message;
                $(".errorPhoneBox").show();
            }
        }
    })
}

function submitEmail(){
    if(email.value==""){
        errorEmail.innerHTML = "请正确填写邮箱";
        $(".errorEmailBox").show();
        return false;
    }else{
        $(".errorEmailBox").hide();
    }
    $.ajax({
        url: "../teacher/tlg_getPasswordByEmail.action",
        method: "POST",
        data: {
            email:email.value,
        },
        success : function(data){
            if(data.Result == "OK"){
                $(".main>.row").html("<h3 class='text-center'>已经发送到您注册时的邮箱，请登录邮箱查收邮件，点击邮件里的链接重置密码。</h3>");
            }
            else{
                errorEmail.innerHTML= data.Message;
                $(".errorEmailBox").show();
            }
        }
    })
}
