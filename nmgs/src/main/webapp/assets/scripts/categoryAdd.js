$(function() {
	var parent = (url('?parent') != null) ? url('?parent') : '';
	var component = (url('?component') != null) ? url('?component') : '';
	$.get('../front/admin/component!execute?uid=k0001&status=normal',function(r){
		if(r.status == 'success') {
			if(parent!=''){
				var componentHtml='';
				$.each(r.data,function(i,v){
					if(component == v.id){
						componentHtml+="<option value='"+v.id+"' selected>"+v.name+"</option>";
					}
				})
				$('#componentSelect').html(componentHtml);
			}else{
				var componentHtml='';
				$.each(r.data,function(i,v){
					if(component == v.id){
						componentHtml+="<option value='"+v.id+"' selected>"+v.name+"</option>";
					}else{
						componentHtml+="<option value='"+v.id+"'>"+v.name+"</option>";
					}
				})
				$('#componentSelect').html(componentHtml);
			}
		}else {
			alert(r.message);
		}
	})
	
	$('#formsubmit').submit(function() {
		var str = $(this).serialize();
		$.post('../front/admin/category!execute?uid=a0003&parent='+parent+'&'+ str, function(data) {
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