$(function() {
	var province = url('?province');
	var module = url('?module');
	$('input[name="province"]').val(province);
	$('input[name="module"]').val(module);
	
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
			$('input[name="image"]').val(data.data.id);
		},
	});
	
	$('#formsubmit').submit(function() {
		var str = $(this).serialize();
		$.post('../front/admin/provinceModule!execute?uid=m0004&'+ str, function(data) {
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