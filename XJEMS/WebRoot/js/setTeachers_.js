




$(document).ready(function () {
	var urlId = url("?id");
	var examId="";
	$.ajax({
		type:"post",
		url:"../admin/teacherGet",
		async:true,
		data:{
			id:urlId
		},
		success:function(r){
			var schoolNum=[];
			var typeNum=[];
			
			$(".modal input").click(function(){
				$(".modal").fadeOut();
			})
			if(r.Status=="success"){
				var template = $.templates("#template").render(r.Records);
				$("#tpl_main").html(template);
				$("#teacherinfo img").attr("src",r.Records.photo.sourcePath);
				//预览头像
				var file = document.querySelector('.sub_head');
				var fileImg = document.querySelector('.file_img');
				
				//身份类型，主考经验，复考经验赋值
				$("#type").val(r.Records.type);
				$("#isMixedExaminer").val(r.Records.isMixedExaminer);
				$("#isChiefExaminer").val(r.Records.isChiefExaminer);
				$("#sex").val(r.Records.sex);
				//获取民族
				$.ajax({
					type:"get",
					url:"../admin/teacherEthnicList",
					async:true,
					success:function(data){
						for(var i = 0;i<data.Records.length;i++){
							$("#ethnic").append("<option value="+data.Records[i].id+">"+data.Records[i].name+"</option>")
						}
						$("#ethnic").val(r.Records.ethnicId);
					}
				});
				
				
				
				//获取监考校区和类型的数组
				schoolNum=r.Records.invigilateCampusArry;
				typeNum=r.Records.invigilateTypeArry;
				$("#school .hidden_value").val(schoolNum.join(','));
				$("#type .hidden_value").val(typeNum.join(','));
				
				//将file上传的img文件转位base64,并为img.src赋值,异步上传
				file.onchange = function () {
					var reg = /(?:jpg|jpeg|png)$/;
					if(reg.test($(this).val()) == true){
						$("#warning").css({"color":"#9B9B9B"});
						var imgFile = file.files[0];
					    var fr = new FileReader();
					    fr.onload = function () {
					        fileImg.src = fr.result;
					    };
					    fr.readAsDataURL(imgFile);
					    $("#teachersinfo").ajaxSubmit(function(r){
					    		if(r.Status=="success"){
					    			$("#photo").val(r.Records.id);
								$(".modal").fadeIn().find('p').html("头像上传成功！");
					    		}else{
								$(".modal").fadeIn().find('p').html("头像上传失败，请重试！");
					    		}
						});
					}else{
						$("#warning").css({"color":"#E0615F"});
					}    
				};
				
				//生成时间控件
				;
				! function() {
					laydate({
						elem: '#inschooltime',
						format: 'YYYY-MM-DD hh:mm:ss',
						istime: true
					});
				}();
				/////
				function getArray(ele,type){
					for(var i = 0;i < $(ele).length;i++){
						if(type.indexOf($(ele).eq(i).attr("data-value")) != -1){
							$(ele).eq(i).attr("data-index",'2');
						}
					}
				}

				var get_1 = new getArray("#school .check",r.Records.invigilateCampusArry);
				var get_2 = new getArray("#type .check",r.Records.invigilateTypeArry);
				
				
				
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
				changeColor('.check','#E0615F','#FFFFFF');
				//监考类型等复选添加颜色逻辑。
			    function clickColor(elem, bgColor, fontColor) {
//			        for (var i = 0; i < $(elem).length; i++) {
//			            $(elem).eq(i).attr("data-index", "1");
//			        }
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
				////////////////
				
				
			    
			    
			    ///////////////

				$("#school .check").click(function(){
					var schoolValue = $(this).attr('data-value');
					if($(this).attr('data-index')!='1'){
						schoolNum.push(schoolValue);
						$("#school .hidden_value").val(schoolNum.join(','))
						console.log($("#school .hidden_value").val())
					}else{
						schoolNum.splice(schoolNum.indexOf(schoolValue),1);
						$("#school .hidden_value").val(schoolNum.join(','))
						console.log($("#school .hidden_value").val())
					}
				});
				
				
				$("#type .check").click(function(){
					var typeValue = $(this).attr('data-value');
					if($(this).attr('data-index')!='1'){
						
						typeNum.push(typeValue);
						$("#type .hidden_value").val(typeNum.join(','))
					}else{
						typeNum.splice(typeNum.indexOf(typeValue),1);
						$("#type .hidden_value").val(typeNum.join(','))
					}
				});
				
				
				///////////////////
				
				
				//表单验证
				
				$(".modal input").click(function(){
					$(".modal").fadeOut();
				})
				//错误提示框点击关闭 事件注册
				$(".modal input").click(function(){
					$(".modal").fadeOut();
				})
				
				//返回上一个页面
				$(".sub a").click(function(){
//					window.history.go(-1);
					window.close();
				})
				//实例化验证函数
				new input_test(":text");	
				new input_test_nan("#bankCard,#sid");
				new input_test("#specialty");
				new input_test_nanl("#intgral");
					
				//身份证号验证
				var regCardID = /^(\d{15}$|^\d{18}$|^\d{17}(\d|X|x))$/;
				new input_test_reg("#idcard",regCardID);
				
				
				//手机号验证
				var regTel =/^1(3|4|5|7|8)\d{9}$/;
				new input_test_reg("#mobile",regTel);
				
				/////////////
				
				//选择研究生字段
				if($("#type").val()=='2'){
					$("#jobDuty").parent().hide();
					$("#jobDuty").attr("disabled",true);
					
					
					$("#studyGrade").parent().show();
					$("#studyGrade").attr("disabled",false);
				}else{
					$("#jobDuty").attr("disabled",false);
					$("#jobDuty").parent().show();
					
					$("#studyGrade").parent().hide();
					$("#studyGrade").attr("disabled",true);
					
				}
				$("#type").change(function(){
					if($("#type").val() == '2') {
						$("#jobDuty").parent().hide();
						$("#jobDuty").attr("disabled",true);
						$("#jobDuty").siblings('.warning').hide();
						$("#jobDuty").css({"border-color":"#C1C1C1"});
						
						$("#studyGrade").parent().show();
						$("#studyGrade").attr("disabled",false);
					} else {
						$("#jobDuty").attr("disabled",false);
						$("#jobDuty").parent().show();
						
						$("#studyGrade").parent().hide();
						$("#studyGrade").attr("disabled",true);
						$("#studyGrade").siblings('.warning').hide();
						$("#studyGrade").css({"border-color":"#C1C1C1"});
						
					}
				})
				
				$("#subForm").unbind("click").click(function(){
					$(this).prop("disabled",true);
					if($("#name").val()==''){
						$(".modal").fadeIn().find('p').html("请正确填写姓名");
						$(this).prop("disabled",false);
						return false;
					}
					
					if(regCardID.test($("#idcard").val())==false){
						$(".modal").fadeIn().find('p').html("请正确填写身份证号");
						$(this).prop("disabled",false);
						return false;
					}
					
					if($("#ethnic").val() == 0){
						$(".modal").fadeIn().find('p').html("您未选择民族");
						$(this).prop("disabled",false);
						return false;
					}
					
					if(regTel.test($("#mobile").val())==false){
						$(".modal").fadeIn().find('p').html("请正确填写联系方式");
						$(this).prop("disabled",false);
						return false;
					}
					
					if($("#major").val()==''){
						$(".modal").fadeIn().find('p').html("请正确填写专业");
						$(this).prop("disabled",false);
						return false;
					}
					
					if($("#type").val() == 0){
						$(".modal").fadeIn().find('p').html("您未选择身份类别");
						$(this).prop("disabled",false);
						return false;
					}
					
					
					if($("#organization").val()==''){
						$(".modal").fadeIn().find('p').html("请正确填写所在学校或部门");
						$(this).prop("disabled",false);
						return false;
					}
					
					if($("#inschooltime").val()==''){
						$(".modal").fadeIn().find('p').html("请正确填写入校时间");
						$(this).prop("disabled",false);
						return false;
					}
					
					
					if($("#intgral").val() == '' || isNaN($("#intgral").val()) == true){
						$(".modal").fadeIn().find('p').html("请正确填写监考积分");
						$(this).prop("disabled",false);
						return false;
					}
					
					if($("#bankCard").val() == ''){
						$(".modal").fadeIn().find('p').html("请正确填写银行卡号");
						$(this).prop("disabled",false);
						return false;
					}
					
					if($("#sid").val() == ''){
						$(".modal").fadeIn().find('p').html("请正确填写学/工号");
						$(this).prop("disabled",false);
						return false;
					}
					
					
					if($("#type").val() == 1 || $("#type").val() == 3){
						if($("#jobDuty").val() == ''){
							$(".modal").fadeIn().find('p').html("请正确填写职务");
							$(this).prop("disabled",false);
							return false;
						}	
					}
					
					
					if($("#type").val() == 2){
						if($("#studyGrade").val() == ''){
							$(".modal").fadeIn().find('p').html("请正确填写研究生所在年级");
							$(this).prop("disabled",false);
							return false;
						}
					}
					
					if(schoolNum.length==0){
						$(".modal").fadeIn().find('p').html("您未选择监考校区");
						$(this).prop("disabled",false);
						return false;
					}
					if(typeNum.length==0){
						$(".modal").fadeIn().find('p').html("您未选择监考类型");
						$(this).prop("disabled",false);
						return false;
					}
					
					if($("#specialty").val() == ''){
						$(".modal").fadeIn().find('p').html("请正确填写特长");
						$(this).prop("disabled",false);
						return false;
					}
					
					$("#teachers_msg").ajaxSubmit(function(r){
						if(r.Status=='success'){
							$(".modal").fadeIn().find('p').html("添加成功");
							window.location.href="../admin/teachersMessage.jsp?exam="+examId;	
						}else{
							$(".modal").fadeIn().find('p').html(r.Message);
							$("#subForm").prop("disabled",false);
							if(r.Message == "请正确填写身份证号"){
								$("#idcard").siblings(".warning").show();
								$("#idcard").css({"border-color":'#E0615F'});
							}
						}
					});
					
				});
				
				///////////
				//获取当前考试信息
				function getCurrent(){
					var mUrl = '../admin/examGetCurrent?';
					$.get(mUrl, function(r) {
						if (r.Status == 'success') {
							examId = r.Records.id;
						}
					}).done(function(r) {
						
					});
				}
				getCurrent();
			}
		}//成功函数结束
	});
	
	
	
	
	
	
	
	
	
	
	
	$.ajax({
		type:"get",
		url:"../admin/teacherEthnicList",
		async:true,
		success:function(r){
			for(var i = 0;i<r.Records.length;i++){
				$("#ethnic").append("<option value="+r.Records[i].id+">"+r.Records[i].name+"</option>")
			}
		}
	});
	
	

	
	


    
	
	
});


	
	
	
	
	
//日期控件示例
//;
//! function () {
//
//  laydate({
//      elem: '#sc_time',
//      format: 'YYYY/MM/DD hh:mm:ss'
//  });
//}();
