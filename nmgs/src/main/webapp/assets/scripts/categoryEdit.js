$(function() {
	var id = url('?id');
	$('input[name="id"]').val(id);
	$.get('../front/admin/category!execute?uid=a0002&id='+id,function(r){
		if(r.status == 'success') {
			var template = $.templates('#queboxTpl');
		    var html = template.render(r.data);
		    $('#HtmlTpl').html(html);
		    $.get('../front/admin/component!execute?uid=k0001&status=normal',function(result){
				if(r.status == 'success') {
					var componentHtml='';
					$.each(result.data,function(i,v){
						if(v.id==r.data.component){
							componentHtml+="<option value='"+v.id+"' selected>"+v.name+"</option>";
						}else{
							componentHtml+="<option value='"+v.id+"'>"+v.name+"</option>";
						}
					})
					$('#componentSelect').html(componentHtml);
				}else {
					alert(r.message);
				}
			})
		}else {
			alert(r.message);
		}
	}).done(function(){
		$('#formsubmit').submit(function() {
			var str = $(this).serialize();
			$.post('../front/admin/category!execute?uid=a0004&'+ str, function(data) {
				if (data.status == "success") {
					window.parent.location.reload();
				} else {
					$('.alert-danger').html(data.message).show();
				}
			})
			return false;
		});
	})
	
	//项目类型 helper 函数
	$(document).on("click",function(e) {
		if(!$(e.target).parents().is('.ufc'))
			$('.uul').hide();
	});
})