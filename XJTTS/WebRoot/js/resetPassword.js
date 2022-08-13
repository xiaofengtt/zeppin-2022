/**
 * Created by thanosYao on 2015/7/24.
 */
$("#header").load("header.html");
$("#footer").load("footer.html");
$('[data-toggle="tooltip"]').tooltip();
$(".submit").click(function(){
    if(oldPassword.value.length<6){
        $("#oldPasswordError").show();
        return false;
    }else{
        $("#oldPasswordError").hide();
    }
    if(inputPassword.value.length<6){
        $("#PasswordError").show();
        return false;
    }else{
        $("#PasswordError").hide();
    }
    if(inputRepeatPassword.value!=inputPassword.value){
        $("#rePasswordError").show();
        return false;
    }else{
        $("#rePasswordError").hide();
    }
    $.ajax({
        url: "../teacher/tinfo_changePassword.action",
        method: "POST",
        data: {
            password:inputPassword.value,
            oldpwd:oldPassword.value
        },
        success:function(data){
            if(data.Result == "OK"){
                console.log(data);
                $(".content").html('<h4>修改成功，即将跳转到登录页</h4>');
                window.setInterval("location='login.html'",3000);
            }
            else{
                console.log(data);
                errorInfo.innerHTML= data.Message;
                $(".alert-danger").removeClass("hide");
            }
        }
    })

})
$(document).ready(function(){
    getAvatar();
    var height=$(window).height();
	$(".main").css("min-height",height-$("footer").outerHeight(true)-$(".navbar").outerHeight(true)-115+"px");
	$(window).resize(function(){
		var height=$(window).height();
		$(".main").css("min-height",height-$("footer").outerHeight(true)-$(".navbar").outerHeight(true)-115+"px");	
	});
});
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