$(function() {
	$('#formsubmit').submit(function() {
		var str = $(this).serialize();
		$.post('../front/admin/component!execute?uid=k0003&'+ str, function(data) {
			if (data.status == "success") {
				layer.msg("添加成功！");
				setTimeout('window.parent.location.reload();',1000);
			} else {
				$('.alert-danger').html(data.message).show();
			}
		})
		return false;
	});
	
	//项目类型 helper 函数
	$(document).on("click",function(e) {
		if(!$(e.target).parents().is('.ufc'))
			$('.uul').hide();
	});
})