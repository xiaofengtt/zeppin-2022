$(function() {
	var id = url('?id');
	var province = (url('?province') != null) ? url('?province') : '';
	$('input[name="originalVideo"]').val(id);
	$('input[name="province"]').val(province);
	$('#formsubmit').submit(function() {
		var str = $(this).serialize();
		var rs1=getByteLen($("#input-title").val(),$("#input-title").attr("length"),"#input-title");
		var rs2=checkNullableLen($("#input-tag").val(),$("#input-tag").attr("length"),"#input-tag");
		var rs3=checkNullableLen($("#input-context").val(),$("#input-context").attr("length"),"#input-context");
		var rs4=checkNullableLen($("#input-source").val(),$("#input-source").attr("length"),"#input-source");
		var rs5=checkNullableLen($("#input-copyright").val(),$("#input-copyright").attr("length"),"#input-copyright");
		var rs6=checkNullableLen($("#input-author").val(),$("#input-author").attr("length"),"#input-author");
		if(rs1&&rs2&&rs3&&rs4&&rs5&&rs6){
			$.post('../front/admin/videoinfo!execute?uid=g0004&'+ str, function(data) {
				if (data.status == "success") {
					parent.$.colorbox.close();
					parent.layer.msg("标引成功！");
					parent.$("#HtmlTpl_"+id).remove();
				} else {
					$('.alert-danger').html(data.message).show();
				}
			})
		}
		return false;
	});
	
	//项目类型 helper 函数
	$(document).on("click",function(e) {
		if(!$(e.target).parents().is('.ufc'))
			$('.uul').hide();
	});
})