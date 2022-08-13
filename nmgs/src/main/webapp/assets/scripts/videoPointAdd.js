$(function() {
	var video = url('?video');
	var timepoint = url('?timepoint');
	$('input[name="video"]').val(video);
	$.get('../front/admin/videoIframe!execute?uid=f0001&video='+video+'&timepoint='+timepoint,function(r){
		if(r.status == 'success') {
			var st = JSON.stringify(r);
		    var template = $('#DataTpl').html();
		    var html = Mustache.render(template, $.parseJSON(st));
		    $('#HtmlTpl').html(html);
		}else {
			alert(r.message);
		}
	}).done(function(){
		$.get('../front/admin/commodity!execute?uid=b0006&status=normal',function(r){
			if(r.status == 'success') {
				var commodityHtml='';
				$.each(r.data,function(i,v){
					commodityHtml+="<option name='video' value='"+v.id+"'>"+v.name+"</option>";
				})
				$('#commoditySelect').html(commodityHtml);
			}else {
				alert(r.message);
			}
		})
		$('#formsubmit').submit(function() {
			var str = $(this).serialize();
			$.post('../front/admin/videoCommodityPoint!execute?uid=e0004&'+ str, function(data) {
				if (data.status == "success") {
					window.parent.getList();
					layer.msg("添加成功！");
					setTimeout('parent.$.colorbox.close()',1000);
				} else {
					$('.alert-danger').html(data.message).show();
				}
			})
			return false;
		});
		$("#resourceId").uploadFile({
			id:"1",
			url:"../front/admin/resource!execute?uid=d0001",
			allowedTypes:"jpg,png,jpeg,bmp,tiff",
			maxFileSize:1024*1024*1024*10,
			fileName:"video",
			maxFileCount : 1,
			dragDropStr: "",
			extErrorStr:"文件格式不正确，请上传指定类型类型的视频文件",
			multiple:false,
			showDelete: true,
			showStatusAfterSuccess:true,
			showProgress: true,
			deletelStr : '删除',
			showAbort:false,
			showDone:false,
			maxFileCountErrorStr: "文件数量过多，请先删除。",
			onSuccess:function(files,data,xhr)
			{
				if($('input[name="showBanner"]').length > 0) {
					$('input[name="showBanner"]').val(data.data.id);
				}else {
					$('input[name="showBanner"]').val(data.data.id);
				}
			},
		});
	})
	
	//项目类型 helper 函数
	$(document).on("click",function(e) {
		if(!$(e.target).parents().is('.ufc'))
			$('.uul').hide();
	});
})