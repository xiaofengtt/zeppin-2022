var buttonType;
function backToList(){
	var parent = (url('?cid') != null) ? url('?cid') : '';
	var province = (url('?province') != null) ? url('?province') : '';
	window.location.href="../views/videoPublishList.html?parent="+parent+"&province="+province;
}
$(function() {
	var province = (url('?province') != null) ? url('?province') : '';
	$.getJSON('../front/admin/province!execute?uid=l0002&id='+province, function(r) {
		if(r.status == 'success') {
			$("#provinceName").html("（"+r.data.name+"）");
		} else{
			window.location.href = "../views/sourceMain.html";
		}
	})
	
	var parent = (url('?cid') != null) ? url('?cid') : '';
	$("#category").val(parent);
	var html="";
	$.getJSON('../front/admin/videoinfo!execute?uid=g0001&pagesize=&sort=&pagenum=&status=checked&province='+province, function(r) {
		if(r.status == 'success') {
			$.each(r.data,function(i,v){
				html+="<option name='video' value='"+v.id+"'>"+v.title+"</option>"
			})
		}else{
			$('.alert-danger').html(r.message).show();
		}
	}).done(function(){
		$("#video").append(html);
	});
	$('#formsubmit').submit(function() {
		var str = $(this).serialize();
		var rs1=getByteLen($("#input-title").val(),$("#input-title").attr("length"),"#input-title");
		var rs2=checkNullableLen($("#input-shortTitle").val(),$("#input-shortTitle").attr("length"),"#input-shortTitle");
		if(rs1&&rs2){
			$.post('../front/admin/videoPublish!execute?uid=h0003&' + str, function(data) {
				if (data.status == "success") {
					if(buttonType==1){
						layer.msg("添加成功！");
						setTimeout('window.location.reload(true)',1000);
					}else{
						layer.msg("添加成功！");
						setTimeout('window.location.href="../views/videoPublishList.html?parent='+parent+'&province='+province+'"',1000);
					}
				} else {
					$('.alert-danger').html(data.message).show();
				}
			})
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
		if(data.status == 'success'){
			$('input[name="cover"]').val(data.data.id);
			
			layer.msg("上传成功！");
		}else{
			$('.alert-danger').html(data.message).show();
		}
	},
});