var pageNum = '1';
var pagesize;
var count;
var flag = true;
var examId = url("?id");

$(document).ready(function() {
//	$('#examinfo').submit(
//		function() {
//			var str = $(this).serialize();
//			$.post('../admin/examUpdate', str, function(data) {
//				var Result = data.Status;
//				var message = data.Message;
//				if(Result == "success") {
//					window.location.href = '../admin/menu.jsp';
//				} else {
//					alert('失败，' + message);
//				}
//			})
//			return false;
//		});
})


$(document).ready(function() {
	var examId = url("?id");
	//获取表单数据
	$.ajax({
		type: "get",
		url: "../admin/examGet?id="+examId,	
		async: true,
		success: function(data) {
			var html = $.templates("#template").render(data.Records);
			$("#show").html(html);
			$("#status").val(data.Records.status);
			
			;
			! function() {

				//laydate.skin('molv');

				laydate({
					elem: '#applyEndTime',
					format: 'YYYY-MM-DD hh:mm:ss',
					istime: true
				});
				laydate({
					elem: '#checkEndTime',
					format: 'YYYY-MM-DD hh:mm:ss',
					istime: true
				});
				laydate({
					elem: '#starttime'
				});
				laydate({
					elem: '#endtime'
				});
				
				$(".back").click(function(){
					window.history.go(-1);
				})
			}();
			
			info.ready(function() {
				info.setContent(data.Records.information);
			})
			statement.ready(function() {
				statement.setContent(data.Records.invigilationContract);
			})
			applyNotice.ready(function() {
				applyNotice.setContent(data.Records.applyNotice);
			})
			invigilationNotice.ready(function() {
				invigilationNotice.setContent(data.Records.invigilationNotice);
			})
			//表单验证
	
			new input_test(":text");
			new input_test_nanl("#integral");
		},
		fail: function() {
			alert("请求失败");
		}
	});
	$.ajax({
		type:"get",
		url:"../admin/examTemplateList",
		async:true,
		success:function(data){
				for(var i=0;i<data.Records.length;i++){
					var ii=i+1
					$(".select_msg").append("<option>"+data.Records[i].title+"</option>");
				}
				$(".select_msg").change(function(){
					statement.setContent(data.Records[$(this).get(0).selectedIndex-1].content);
				})
			
		}
	});
	
	//点击按钮取消确认框
	$("#modal input").unbind("click").click(function(){
		$("#modal").fadeOut();
	})
	
	$(".sub_publish").click(function() {		
		
		if($("#name").val()==''){
			$("#modal").fadeIn().find('p').html("请正确填写姓名");
			return false;
		}
		
		if($("#starttime").val()==''){
			$("#modal").fadeIn().find('p').html("请正确填写考试开始时间");
			return false;
		}
		
		if($("#endtime").val()==''){
			$("#modal").fadeIn().find('p').html("请正确填写考试结束");
			return false;
		}
		
		if($("#integral").val() == '' || isNaN($("#integral").val()) == true){
			$("#modal").fadeIn().find('p').html("请正确填写监考积分");
			return false;
		}
		
		if($("#applyEndTime").val()==''){
			$("#modal").fadeIn().find('p').html("请正确填写申报截止时间");
			return false;
		}
		
		if($("#checkEndTime").val()==''){
			$("#modal").fadeIn().find('p').html("请正确填写确认截止时间");
			return false;
		}
		
		if(statement.getContent() == ""){
			$("#modal").fadeIn().find('p').html("请正确填写监考责任书");
			return false;
		}
		
		if(info.getContent() == ""){
			$("#modal").fadeIn().find('p').html("请正确填写考试信息");
			return false;
		}
		
		if(applyNotice.getContent() == ""){
			$("#modal").fadeIn().find('p').html("请正确填写申报注意事项");
			return false;
		}
		
		$("#examinfo").ajaxSubmit(function(r) {
			if(r.Status=="success"){
				$("#modal").fadeIn().find('p').html(r.Message);
				if($("#status option:selected").val()==-1 || $("#status option:selected").val()==1 || $("#status option:selected").val()==2){
					setTimeout(function(){
						window.location.href="../admin/menu.jsp?exam="+examId;
					},1500);
				}else{
					setTimeout(function(){
						window.location.href="../admin/menu_f.jsp?exam="+examId;
					},1500);
				}				
			}else {
				$("#modal").fadeIn().find('p').html(r.Message);
			}
		});
	});

	
	
});