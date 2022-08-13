$(function() {
	var id = url('?id');
	$('input[name="originalVideo"]').val(id);
	
	$('#formsubmit').submit(function() {
		var str = $(this).serialize();
		$.post('../front/admin/videoinfo!execute?uid=g0004&'+ str, function(data) {
			if (data.status == "success") {
				parent.$.colorbox.close();
				parent.layer.msg("标引成功！");
				parent.$("#HtmlTpl_"+id).remove();
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