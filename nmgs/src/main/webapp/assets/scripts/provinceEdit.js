$(function() {
	var id = url('?id');
	$('input[name="id"]').val(id);
	$.get('../front/admin/province!execute?uid=l0002&id='+id,function(r){
		if(r.status == 'success') {
			var template = $.templates('#queboxTpl');
		    var html = template.render(r.data);
		    $('#HtmlTpl').html(html);
		    $.get('../front/admin/province!execute?uid=l0006',function(result){
				if(result.status == 'success') {
					var templateHtml='';
					$.each(result.data,function(i,v){
						if(v.id==r.data.template){
							templateHtml+="<option name='video' value='"+v.id+"' selected>"+v.name+"</option>";
						}else{
							templateHtml+="<option name='video' value='"+v.id+"'>"+v.name+"</option>";
						}
						//缩略图
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
					alert(result.message);
				}
			})
		}else {
			alert(r.message);
		}
	}).done(function(){
		$('#formsubmit').submit(function() {
			var str = $(this).serialize();
			$.post('../front/admin/province!execute?uid=l0004&'+ str, function(data) {
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
//select  事件
function change()
{
	var img = $('#templateSelect option:selected') .val();
	var imgurl = $('#'+img).val();
	$('#timg').empty();
	var newimg = $("<img src=../assets/img/"+imgurl+">");
	$('#timg').html(newimg);
}