var buttonType;
function backToList(){
	var province = (url('?province') != null) ? url('?province') : '';
	window.location.href="../views/videoList.html?province="+province;
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
		$('.btn-single').attr("disabled",true);
		var str = $(this).serialize();
		var rs1=getByteLen($("#input-title").val(),$("#input-title").attr("length"),"#input-title");
		var rs2=checkNullableLen($("#input-tag").val(),$("#input-tag").attr("length"),"#input-tag");
		var rs3=checkNullableLen($("#input-context").val(),$("#input-context").attr("length"),"#input-context");
		var rs4=checkNullableLen($("#input-source").val(),$("#input-source").attr("length"),"#input-source");
		var rs5=checkNullableLen($("#input-copyright").val(),$("#input-copyright").attr("length"),"#input-copyright");
		var rs6=checkNullableLen($("#input-author").val(),$("#input-author").attr("length"),"#input-author");
		if(rs1&&rs2&&rs3&&rs4&&rs5&&rs6){
			$.post('../front/admin/videoinfo!execute?uid=g0004&' + str, function(data) {
				if (data.status == "success") {
					var province = (url('?province') != null) ? url('?province') : '';
					if(buttonType==1){
						layer.msg("添加成功！");
						setTimeout('window.location.href="../views/videoAdd.html?province='+province+'"',1000);
					}else{
						layer.msg("添加成功！");
						setTimeout('window.location.href="../views/videoList.html?province='+province+'"',1000);
					}
				} else {
					$('.alert-danger').html(data.message).show();
					$('.btn-single').attr("disabled",false);
				}
			})
		}else{
			$('.btn-single').attr("disabled",false);
		}
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
	url:"../front/admin/resource!execute?uid=d0001",
	allowedTypes:"mp4,avi,mov,3gp,flv,wmv,rmvb,mpg",
	maxFileSize:1024*1024*1024*10,
	fileName:"video",
	maxFileCount : 1,
	dragDropStr: "",
	extErrorStr:"文件格式不正确，请使用以下类型的视频文件：",
	showStatusAfterSuccess:false,
	showDelete : false,
	showProgress: true,
	deletelStr : '删除',
	showAbort:false,
	onSuccess:function(files,data,xhr)
	{
		if(data.status == 'success'){
			if($('input[name="id"]').length > 0) {
				$('input[name="id"]').val(data.data.id);
			}else {
				$("#resourceAdd").append('<input type="hidden" name="originalVideo" value="' + data.data.id + '">');
			}
			layer.msg("上传成功！");
		}else{
			$('.alert-danger').html(data.message).show();
		}
	}
});