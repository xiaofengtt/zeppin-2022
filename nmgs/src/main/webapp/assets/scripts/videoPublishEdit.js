$(function() {
	var id = url('?id');
		$('input[name="id"]').val(id);
	$.get('../front/admin/videoPublish!execute?uid=h0002&id='+url('?id'),function(r){
		if(r.status == 'success') {
			var st = JSON.stringify(r);
		    var template = $('#DataTpl').html();
		    var html = Mustache.render(template, $.parseJSON(st));
		    $('#HtmlTpl').html(html);
		    $('#sequenceSelect').val(r.data.sequence);
		    $("#resourceId").uploadFile({
		    	id:"1",
				url:"../front/admin/resource!execute?uid=d0001",
				allowedTypes:"jpg,png,jpeg,bmp,tiff",
				maxFileSize:1024*1024*1024*10,
				fileName:"video",
				maxFileCount : 1,
				dragDropStr: "",
				extErrorStr:"文件格式不正确，请上传指定类型类型的视频文件",
				showStatusAfterSuccess:true,
				showDelete : true,
				showDone : false,
				showProgress: true,
				deletelStr : '删除',
				showAbort:true,
				onSuccess:function(files,data,xhr){
					if(data.status=="success"){
						$('input[name="cover"]').val(data.data.id);
						
						$("#coverImg").remove();
						$("#change").hide();
					}
				}
			});
			
			$("#change").click(function(){
				$("#coverImg").remove();
				$('input[name="cover"]').val('');
				$(this).hide();
			});
		}else {
			alert('服务端出错！请稍后重试！');
		}
	}).done(function(){
		$('#formsubmit').submit(function() {
			var str = $(this).serialize();
			$.post('../front/admin/videoPublish!execute?uid=h0004&'+ str, function(data) {
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