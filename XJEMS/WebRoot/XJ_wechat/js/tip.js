$(document).ready(
		function() {
			var code = url('?code') == null ? '' : url('?code');
			$.ajax({
				type : "get",
				url : "../weixin/weixinGetExamInfo?code=" + code,
				timeout : 3000,
				async : true,
				success : function(r) {
					if (r.Status == 'unlogin') {
						window.location.href =r.Records;
					} else if (r.Status == 'success') {
						
						var template = $.templates('#queboxTpl');
						var html = template.render(r.Records);
						$('#queboxCnt').html(html);
						var height = $(window).height()-($("header").outerHeight(true) + $("footer").outerHeight(true));
						$("main").css({"min-height":height-20+'px'});
						$("#item").click(function(){
							if($("#item").hasClass("color")){
								$("#item").removeClass("color");
							}else{
								$("#item").addClass("color");
							}
						});
					} else { // 获取失败
						var template = $.templates('#queboxTpl');
						var html = "<p class='warning'>提示</p><p class='remind'>" + r.Message + "</p>";
						$('#queboxCnt').html(html);
						
					}
				},
				error:function(){
					alert("链接超时，请稍后重试");
				}
			});
		})




// 申请监考 tid:教师id
function apply(exam, tid) {
	if($("#item").hasClass("color")==false){
		alert("请仔细阅读以上条款，并点击同意按钮");
		return false;
	}
	$("#apply").attr("disabled",true);
	$("#apply").css({"background-color":"#C1C1C1"});
	$.ajax({
		type : "post",
		url : "../weixin/weixinApply",
		async : true,
		timeout : 3000,
		data : {
			"exam" : exam,
			"tid" : tid
		},
		success : function(data) {
			if (data.Status == 'success') {
				alert("申请成功")
				window.location.href =  data.Records;
//				$("#errorMsg").hide();
			} else {
//				$("#errorMsg").show();
				alert(data.Message);
				$("#apply").attr("disabled",false);
				$("#apply").css({"background-color":"#0BC66C"});
				
			}
		},
		error : function() {
			alert("链接超时，请稍后再试");
		}
	});
}
// 取消申请监考 id：监考记录id
function cancelApply(id) {
	$.ajax({
		type : "post",
		url : "../weixin/weixinCancelApply",
		async : true,
		timeout : 3000,
		data : {
			"id" : id,
		},
		success : function(data) {
			if (data.Status == 'success') {
				alert(data.Message);
				window.location.href = data.Records;
//				$("#errorMsg").hide();
			} else {
//				$("#errorMsg").show();
//				$("#errorMsg").text(data.Message);
				alert(data.Message);
			}
		},
		error : function() {
			alert('链接超时，请稍后再试');
		}
	});
}