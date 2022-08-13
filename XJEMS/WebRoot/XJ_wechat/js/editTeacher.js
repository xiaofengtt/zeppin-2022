

$(document).ready(function () {
	
	
	
	var id = url('?id') == null ? '' : url('?id');
	var mUrl = '../weixin/weixinGetInfo?';
	mUrl = mUrl + "id="+ id; 
	$.get(mUrl, function(r) {
		if (r.Status == 'success') {
			var template = $.templates('#queboxTpl');
			var html = template.render(r.Records);
			var type = r.Records.type;
			var ethnicId = r.Records.ethnicId;
			$('#queboxCnt').html(html);
			
			
			var schoolNum=[];
			
			schoolNum = r.Records.invigilateCampusArry;
			$("#invigilateCampus .hidden_value").val(schoolNum.join(','));
			
			function getArray(ele,type){
				for(var i = 0;i < $(ele).length;i++){
					if(type.indexOf($(ele).eq(i).attr("data-value")) != -1){
						$(ele).eq(i).attr("data-index",'2');
					}
				}
			}

			var get_1 = new getArray("#invigilateCampus .check",r.Records.invigilateCampusArry);
			
			//根据data-index改变监考类型，监考校区按钮颜色
			function changeColor(ele,bgColor,fontColor){
				for(var i = 0;i < ele.length;i++){
					if($(ele).eq(i).attr('data-index')==2){
						$(ele).eq(i).css({
		                    "background-color": bgColor,
		                    "color": fontColor
		                });
					}
				}
			}
			changeColor('#invigilateCampus .check','#E0615F','#FFFFFF');
			
			//监考类型等复选添加颜色逻辑。
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
		        
		    }
		    clickColor('.check','#E0615F','#FFFFFF');
			
			$(".check").click(function(){
				var schoolValue = $(this).attr('data-value');
				if($(this).attr('data-index')!='1'){
					
					schoolNum.push(schoolValue);
					$(".hidden_value").val(schoolNum.join(','))
					console.log($(".hidden_value").val())
				}else{
					schoolNum.splice(schoolNum.indexOf(schoolValue),1);
					$(".hidden_value").val(schoolNum.join(','))
					console.log($(".hidden_value").val())
				}
			});
			
			//民族默认选中
			$("#ethnic").val(ethnicId);
			
			var regCardID = /^(\d{15}$|^\d{18}$|^\d{17}(\d|X|x))$/;
			var regTel =/^1(3|4|5|7|8)\d{9}$/;
			$("#subForm").click(function(){
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
				$("#teachers_msg").ajaxSubmit(function(r){
					if(r.Status=='success'){
						alert("修改成功");
							//跳转到用户信息页面
						window.location.href=r.Records;
					}else{
						alert(r.Message);
						$("#subForm").prop("disabled",false);
					}
				});
			});
			//身份默认选中
			$("#type").val(r.Records.type);
			if(r.Records.type == 1){
				$("#type").html("<option value='1'>考务组</option>");
//				$("#type").val(r.Records.type);
//				$("#type").attr('disabled','true');
			}

			//选择研究生字段
			if($("#type").val()=='2'){
					$("#jobDuty").parent().hide();
					$("#jobDuty").attr("disabled",true);
					
					$("#studyGrade").parent().show();
					$("#studyGrade").attr("disabled",false);

			}else{
					$("#jobDuty").attr("disabled",false);
					$("#jobDuty").parent().show();
					
					$("#studyGrade").attr("disabled",true);
					$("#studyGrade").parent().hide();
					
			}
			$("#type").change(function(){
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
			});
			
			


			
			//性别
			$(".man").click(function(){
				$(".woman").attr("src","./img/ic_default_woman.png");
				$(".man").attr("src","./img/ic_click_man.png");
				$("#sex").val("1");
			});
			
			$(".woman").click(function(){
				$(".woman").attr("src","./img/ic_click_woman.png");
				$(".man").attr("src","./img/ic_default_man.png");
				$("#sex").val("2");
			});
			
			
		};
	}).done(function(r) {
		
		if(r.Records.idcardPhoto.id == 1){
			$(".card_title").show();
		}
		
		
		var file = document.querySelector('.load');	
		var fileImg = document.querySelector('.file_img');
		$(".load").change(function(){
			$("#up_head").ajaxSubmit(function(r) {
				if(r.Status == "success") {
					$("#photo").val(r.Records.id);
					$(".file_img").prop("src",r.Records.sourcePath);
				} else {
					alert("头像上传失败，请检查网络状况，图片类型必须为：.jpg .jpeg .png");
				}
			});
		})//头像
		
		
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
		
		$("#inschooltime").change(function(){
			$(this).siblings('input').val($(this).val());
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
					"check": 0 //是否验证
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
		});//发送验证码结束
		
		
		
		
		//表单验证
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
			});//.done
			
		});
		


	
	
	
	
	//选择研究生字段
//	$("#studyGrade").parent().hide();
//	$("#studyMajor").parent().hide();
//	$("#type").change(function(){
//		if($("#type").val()=='2'){
//			$("#jobDuty").parent().hide();
//			$("#studyGrade").parent().show();
//		}else{
//			$("#major").attr("name","major");
//			$("#jobDuty").parent().show();
//			$("#studyGrade").parent().hide();
//			$("#studyMajor").parent().hide();
//		}
//		if ($("#type").val() == '2') {
//			$("#jobDuty").parent().hide();
//			$("#major").parent().hide();
//			$("#studyMajor").parent().show();
//			$("#studyGrade").parent().show();
//		} else {
//			$("#jobDuty").parent().show();
//			$("#major").parent().show();
//			$("#studyGrade").parent().hide();
//			$("#studyMajor").parent().hide();
//		}
//	})
