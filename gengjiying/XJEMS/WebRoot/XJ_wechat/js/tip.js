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
						$("#apply").click(function(){
							if($("#item").hasClass("color") == false) {
								alert("请仔细阅读以上条款，并点击同意按钮");
							}else{
								window.location.href = "./apply.html?id="+ $(this).attr("data-id") + "&tid="+ $(this).attr("data-tid") + "&code=" + code;
							}
						})
						
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