$(function() {
	$.get('../front/admin/province!execute?uid=l0006',function(r){
		if(r.status == 'success') {
			var templateHtml='';
			$.each(r.data,function(i,v){
				templateHtml+="<option value='"+v.id+"'>"+v.name+"</option>";
				var hidden = $("<input type='hidden' id="+v.id+" value="+v.imgurl+">");
				$('#myimg').append(hidden);
			})
			$('#templateSelect').html(templateHtml);
			//添加缩略图
			var img = $('#templateSelect option:selected') .val();
			var imgurl = $('#'+img).val();
			var newimg = $("<img src=../assets/img/"+imgurl+">");
			$('#timg').append(newimg);
			$('#timg').css("width","300px").css("margin-left","120px").css("margin-bottom","15px");
		}else {
			alert(r.message);
		}
	})
	$('#formsubmit').submit(function() {
		var str = $(this).serialize();
		$.post('../front/admin/province!execute?uid=l0003&'+ str, function(data) {
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
//select  事件
function change()
{
	var img = $('#templateSelect option:selected').val();
	var imgurl = $('#'+img).val();
	$('#timg').empty();
	var newimg = $("<img src=../assets/img/"+imgurl+">");
	$('#timg').html(newimg);

}