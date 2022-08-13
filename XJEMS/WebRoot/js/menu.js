var exam;

var start = document.getElementById("start");
var stop = document.getElementById("stop");
var end = document.getElementById("end");
var target = true;
// 开始申报
$("#start").click(function(){
	if(target == true){
		change(exam, 1);
	};
	
});

// 停止申报
$("#stop").click(function(){
	if(target == false){
		change(exam, 2);
	};
	
});

// 结束考试
end.onclick = function() {
	change(exam, 0);
	window.location.href = "../admin/menu_f.jsp?exam="+url("?exam");
};

function change(id, status) {
	$.ajax({
		type : "post",
		url : "../admin/examChange",
		async : true,
		data : {
			"id" : id,
			"status" : status
		},
		success : function(data) {
			$("#bg").hide();
			if (data.Status == 'success') {
				$(".easy_modal p").html(data.Message);
				$(".easy_modal").fadeIn();
				getCurrent();
			} else {
				alert(data.Message);
				$(".easy_modal p").html(data.Message);
				$(".easy_modal").fadeIn();
			}
		},
		fail : function() {
			$(".easy_modal p").html("失败，请稍后再试");
			$(".easy_modal").fadeIn();
		},
		beforeSend:function(){
			$("#bg").show();
		}
	});
}

// 数据加载
$(document).ready(function() {
	$("#bg").height($('body').height());
	getCurrent();
	$(".easy_modal input").eq(0).click(function(){
		$(".easy_modal").fadeOut();
	})
})

function getCurrent() {
	var mUrl = '../admin/examGetCurrent?';
	$.get(mUrl, function(r) {
		if (r.Status == 'success') {
			var template = $.templates('#queboxTpl');
			var html = template.render(r.Records);
			$('#queboxCnt').html(html);
			exam = r.Records.id;
			$('#roomInfo').attr('href', 'roomInfo.jsp?exam=' + exam);
			$('#teachersControl').attr('href', 'teachersControl.jsp?exam=' + exam);
			$('#roomMessage').attr('href', 'roomMessage.jsp?exam=' + exam);
			$('#teachersConfirm').attr('href', 'teachersConfirm.jsp?exam=' + exam);
			$('#dTeacherInfo').attr('href', '../admin/documentDownloadTeacherInfo?exam=' + exam +'&name='+r.Records.name);
			$('#dUndTeacher').attr('href', '../admin/documentDownloadUndTeacher?exam=' + exam +'&name='+r.Records.name);
			$('#teacherAssess').attr('href', 'teacherAssess.jsp?exam=' + exam);
			$('#edit').attr('href', 'publish_.jsp?id=' + exam);
			switch (r.Records.status) {
			case -1://待发布
				start.style.color = "#E69B21";
				stop.style.color = "#C0C0C0";
				target = true;
				stop.style.cursor = "default";
				break;
			case 2:// 已发布(开始申报)
				start.style.color = "#E69B21";
				start.style.cursor = "pointer";
				stop.style.color = "#C0C0C0";
				target = true;
				stop.style.cursor = "default";
				break;
			case 1:// 进行中（不可申报）
				stop.style.color = "#E69B21";
				stop.style.cursor = "pointer";
				start.style.color = "#C0C0C0";
				start.style.cursor = "default";
				target = false;
				break;
			}
		}
	}).done(function(r) {});
}