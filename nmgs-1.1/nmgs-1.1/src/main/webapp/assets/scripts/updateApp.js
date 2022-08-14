var buttonType;

$.get('../front/admin/component!execute?uid=k0001&status=normal',function(r) {
	if(r.status =='success') {
		var html = '<option value="634ce983-ac42-46cd-9984-b927c6231a07">Android</option>';
		$('#componentSelect').html(html);
	}
})

function backToList(){
	var province = (url('?province') != null) ? url('?province') : '';
	window.location.href="../views/appList.html?province="+province;
}
function isMoney(obj){
   if (! obj) return false;
   return (/^\d+(\.\d{1,2})?$/).test(obj);   
}
$(function() {
	var province = (url('?province') != null) ? url('?province') : '';
	$('input[name="province"]').val(province);
	$.getJSON('../front/admin/province!execute?uid=l0002&id='+province, function(r) {
		if(r.status == 'success') {
			$("#provinceName").html("（"+r.data.name+"）");
		} else{
			window.location.href = "../views/sourceMain.html";
		}
	})
	
	$('#formsubmit').submit(function() {
		var str = $(this).serialize();
			$.post('../front/admin/appVersion!execute?uid=x0001&component=634ce983-ac42-46cd-9984-b927c6231a07&' + str, function(data) {
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
	url:"../front/admin/resourceApk!execute?uid=y0001",
	allowedTypes:"apk",
	maxFileSize:1024*1024*1024*10,
	fileName:"apk",
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
			$('.alert-danger').hide();
			$('input[name="apkId"]').val(data.data.id);
			layer.msg("上传成功！");
		}else{
			$('.alert-danger').html(data.message).show();
			layer.msg(data.message);
		}
	},
});


function changeComponent(t){
	var province = (url('?province') != null) ? url('?province') : '';
	var component = $(t).val();
	window.location.href="../views/updateApk.html?province="+province+"&component="+component;
}