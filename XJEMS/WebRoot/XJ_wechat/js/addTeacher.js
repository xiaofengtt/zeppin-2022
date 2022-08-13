$(document).ready(function() {
	var openid = url('?openid') == null ? '' : url('?openid');
	$("#openid").val(openid);
	
	$("#inschooltime").change(function(){
		$(this).siblings('input').val($(this).val());
	});
	
	$("select").change(function(){
		$(this).css({"color":"#000000"});
	});
	
	//上传头像
	var file = document.querySelector('.load');	
	var fileImg = document.querySelector('.file_img');
	$(".load").change(function(){
		$("#up_head").ajaxSubmit(function(r) {
			if(r.Status == "success") {
				$("#idcardPhoto").val(r.Records.id);
				$(".file_img").prop("src",r.Records.sourcePath);
			} else {
				alert("头像上传失败，请检查网络状况，图片类型必须为：.jpg .jpeg .png");
			}
		});
	})
	
	//上传身份证
	var fileCard = document.querySelector('#card_file');	
	var cardImg = document.querySelector('#card_img');
	$("#card_file").change(function(){
		$(".card_title").hide();
		$("#card_form").ajaxSubmit(function(r) {
			if(r.Status == "success") {
				$("#idcardPhoto").val(r.Records.id);
				$("#card_img").prop("src",r.Records.sourcePath);
			} else {
				alert("头像上传失败，请检查网络状况，图片类型必须为：.jpg .jpeg .png");
			}
		});
	});
	
	
	


	$("#subForm").click(function() {
		$(this).prop("disabled",true);
		if($("#idcardPhoto").val() == ""){
			alert("请上传正面身份证图片");
			$(this).prop("disabled",false);
			return false;
		}
		if($("#name").val() == ''){
			alert("请正确填写姓名");
			$(this).prop("disabled",false);
			return false;
		}
		if(regCardID.test($("#idcard").val())==false){
			alert("请正确填写身份证号");
			$(this).prop("disabled",false);
			return false;
		}
		if($("#bankCard").val() == '' || isNaN($("#bankCard").val())==true){
			alert("请正确填写交通银行卡号");
			$(this).prop("disabled",false);
			return false;
		}
		if($("#ethnic option:selected").val() == 0){
			alert("请选择民族");
			$(this).prop("disabled",false);
			return false;
		}
		if(regTel.test($("#mobile").val())==false){
			alert("请正确填写手机号");
			$(this).prop("disabled",false);
			return false;
		}
		
		if($("#code").val() == '' || isNaN($("#code").val())==true){
			alert("请正确填写验证码");
			$(this).prop("disabled",false);
			return false;
		}
		
		if($("#type option:selected").val() == 0){
			alert("请选择身份类别");
			$(this).prop("disabled",false);
			return false;
		}
		
		if($("#organization").val() == ""){
			alert("请正确填写所在学校部门");
			$(this).prop("disabled",false);
			return false;
		}
		
		if($("#type option:selected").val() == 2){
			if($("#studyGrade").val() == ''){
				alert("请正确填写研究生所在年级");
				$(this).prop("disabled",false);
				return false;
			}
		}
		
		if($("#major").val() == ''){
			alert("请正确填写专业");
			$(this).prop("disabled",false);
			return false;
		}
		
		if($("#type option:selected").val() == 1 || $("#type option:selected").val() == 3){			
			if($("#jobDuty").val() == ""){
				alert("请正确填写职务");
				$(this).prop("disabled",false);
				return false;
			}
		}
		
		if($("#date").val() == ""){
			alert("请正确填写入校时间");
			$(this).prop("disabled",false);
			return false;
		}
		if(schoolNum.length==0){
			alert("您未选择监考校区");
			$(this).prop("disabled",false);
			return false;
		}
		if($("#specialty").val() == ""){
			alert("请正确填写特长");
			$(this).prop("disabled",false);
			return false;
		}
		
		$("#teachers_msg").ajaxSubmit(function(r) {
			if(r.Status == 'success') {
				alert("注册成功");
				//跳转后台返回的用户信息页面
				window.location.href= r.Records;
			} else {
				alert(r.Message);
				$("#subForm").prop("disabled",false);
			}

		});
	});//提交结束

	

	// 性别
	$(".man").click(function() {
		$(".woman").attr("src", "./img/ic_default_woman.png");
		$(".man").attr("src", "./img/ic_click_man.png");
		$("#sex").val("1");
	});

	$(".woman").click(function() {
		$(".woman").attr("src", "./img/ic_click_woman.png");
		$(".man").attr("src", "./img/ic_default_man.png");
		$("#sex").val("2");
	});
	
	function clickColor(elem, bgColor, fontColor) {
        $(elem).unbind("click").click(function () {
            if ($(this).attr('data-index') == "1") {
                $(this).css({
                    "background-color": bgColor,
                    "color": fontColor
                });
                $(this).attr('data-index', "2");
            } else {
                $(this).css({
                    "background-color": fontColor,
                    "color": bgColor
                });
                $(this).attr('data-index', "1");
            }
        });
	};
    clickColor('.check','#E0615F','#FFFFFF');
	var schoolNum=[];
	$("#invigilateCampus .check").click(function(){
		var schoolValue = $(this).attr('data-value');
		if($(this).attr('data-index')!='1'){
			
			schoolNum.push(schoolValue);
			$("#invigilateCampus .hidden_value").val(schoolNum.join(','));
			console.log($("#invigilateCampus .hidden_value").val())
		}else{
			schoolNum.splice(schoolNum.indexOf(schoolValue),1);
			$("#invigilateCampus .hidden_value").val(schoolNum.join(','));
			console.log($("#invigilateCampus .hidden_value").val())
		}
	});

	//发送验证码
	$("#sendCode").click(function() {
		var _this = $(this);
		var mobile = $("#mobile").val();
		var t;
		var i = 60;
		_this.hide();
		$("#time").show();
		t = setInterval(function(){
			i--;
			$("#time").html(i+"秒后重新发送");
			if(i==-1){
				clearInterval(t);
				$("#time").hide();
				$("#time").html("60秒后重新发送");
				_this.show();
			}
			
		},1000);
		$.ajax({
			type: "post",
			url: "../weixin/smsSendSms",
			async: true,
			timeout:3000,
			data: {
				"mobile": mobile, //手机号
				"check": 1 //是否验证
			},
			success: function(data) {
				
				if(data.Status == 'success') {
					
				} else {
					alert(data.Message);
					clearInterval(t)
					$("#time").hide();
					$("#time").html("60秒后重新发送");
					_this.show();
				}
			},
			error: function() {
				alert("链接超时，请稍后重试");
			}
		});
	});//验证码结束
});

// 表单验证

	new input_test("#name,#major,#studyMajor,#studyGrade,#jobDuty,#organization");
	new input_test_nan("#bankCard,#code");
	$("#specialty").blur(function(){
		if($(this).val() == ''){
			$(this).css({"border":"1px solid #E0615F"}).siblings('label').css({"color":"#E0615F"});
		}else{
			$(this).css({"border":"none"}).siblings('label').css({"color":"#696969"});
		}
	})
// 身份证号
var regCardID = /^(\d{15}$|^\d{18}$|^\d{17}(\d|X|x))$/;
	new input_test_reg("#idcard",regCardID);

// 手机号
var regTel = /^1(3|4|5|7|8)\d{9}$/;
	new input_test_reg("#mobile",regTel);

// 选择研究生字段
$("#studyGrade").parent().hide();
$("#type").change(function() {
	if($("#type").val() == '2') {
		$("#jobDuty").parent().hide();
		$("#jobDuty").attr("disabled",true);
		$("#jobDuty").parent().removeClass('b_b_r');
		
		$("#studyGrade").parent().show();
		$("#studyGrade").attr("disabled",false);
	} else {
		$("#jobDuty").attr("disabled",false);
		$("#jobDuty").parent().show();
		
		$("#studyGrade").parent().hide();
		$("#studyGrade").attr("disabled",true);
		$("#studyGrade").parent().removeClass('b_b_r');
	}
})