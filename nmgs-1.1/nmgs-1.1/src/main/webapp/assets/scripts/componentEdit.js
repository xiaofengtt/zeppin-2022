$(function() {
	var id = url('?id');
	$('input[name="id"]').val(id);
	$.get('../front/admin/component!execute?uid=k0002&id='+id,function(r){
		if(r.status == 'success') {
			var template = $.templates('#queboxTpl');
		    var html = template.render(r.data);
		    $('#HtmlTpl').html(html);
		    $(".input-text").blur(function(){
				getByteLen($(this).val(),$(this).attr("length"),this);
			})
		}else {
			alert(r.message);
		}
	}).done(function(){
		$('#formsubmit').submit(function() {
			var str = $(this).serialize();
			var rs1=getByteLen($("#input-name").val(),$("#input-name").attr("length"),"#input-name");
			if(rs1){
				$.post('../front/admin/component!execute?uid=k0004&'+ str, function(data) {
					if (data.status == "success") {
						window.parent.location.reload();
					} else {
						$('.alert-danger').html(data.message).show();
					}
				})
			}
			return false;
		});
	})
	
	//项目类型 helper 函数
	$(document).on("click",function(e) {
		if(!$(e.target).parents().is('.ufc'))
			$('.uul').hide();
	});
})