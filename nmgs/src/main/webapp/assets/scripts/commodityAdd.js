var buttonType;
function backToList(){
	window.location.href="../views/commodityList.html";
}
function isMoney(obj){
   if (! obj) return false;
   return (/^\d+(\.\d{1,2})?$/).test(obj);   
}
$(function() {
	$('#formsubmit').submit(function() {
		var str = $(this).serialize();
		$.post('../front/admin/commodity!execute?uid=b0003&' + str, function(data) {
			if (data.status == "success") {
				if(buttonType==1){
					layer.msg("添加成功！");
					setTimeout('window.location.href="../views/commodityAdd.html"',1000);
				}else{
					layer.msg("添加成功！");
					setTimeout('window.location.href="../views/commodityList.html"',1000);
				}
			} else {
				$('.alert-danger').html(data.message).show();
			}
		})
		return false;
	});

	//项目类型 helper 函数
	$(document).on("click", function(e) {
		if (!$(e.target).parents().is('.ufc')) {
			$('.uul').hide();
		}
		if(!$(e.target).parent().is('#bido') && !$(e.target).is('#clId') && !$(e.target).parent().is('.listnode')){
			$('#clListBox').hide();
		}
	});
});

$("#resourceId").uploadFile({
	id:"1",
	url:"../front/admin/resource!execute?uid=d0001",
	allowedTypes:"jpg,png,jpeg,bmp,tiff",
	maxFileSize:1024*1024*1024*10,
	fileName:"video",
	maxFileCount : 1,
	dragDropStr: "",
	extErrorStr:"文件格式不正确，请上传指定类型类型的文件",
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
		if(data.status == 'success'){
			if($('input[name="id"]').length > 0) {
				$('input[name="id"]').val(data.data.id);
			}else {
				$('input[name="cover"]').val('');
				$('input[name="cover"]').val(data.data.id);
			}
			layer.msg("上传成功！");
		}else{
			$('.alert-danger').html(data.message).show();
			layer.msg(data.message);
		}
	},
});

$("#resourceId2").uploadFile({
	id:"2",
	url:"../front/admin/resource!execute?uid=d0002",
	allowedTypes:"rar,zip",
	maxFileSize:1024*1024*1024*10,
	fileName:"display",
	maxFileCount : 1,
	dragDropStr: "",
	extErrorStr:"文件格式不正确，请上传指定类型类型的文件",
	multiple:false,
	showDelete: true,
	showStatusAfterSuccess:false,
	showDelete : false,
	showProgress: true,
	deletelStr : '删除',
	showAbort:false,
	onSuccess:function(files,data,xhr)
	{
		if(data.status == 'success'){
			if($('input[name="id"]').length > 0) {
				$('input[name="id"]').val(data.Records.id);
			}else {
				$('input[name="displays"]').val('');
				('input[name="displays"]').val(data.data.display);
			}
			layer.msg("上传成功！");
		}else{
			$('.alert-danger').html(data.message).show();
			layer.msg(data.message);
		}
	}
});