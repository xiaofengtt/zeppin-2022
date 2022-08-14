var buttonType;
function backToList(){
	var province = (url('?province') != null) ? url('?province') : '';
	window.location.href="../views/commodityList.html?province="+province;
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
		var rs1=getByteLen($("#input-name").val(),$("#input-name").attr("length"),"#input-name");
		var rs2=checkNumber($("#input-originalPrice").val(),$("#input-originalPrice").attr("length"),"#input-originalPrice");
		var rs3=checkNumber($("#input-price").val(),$("#input-price").attr("length"),"#input-price");
		var rs4=checkUrl($("#input-url").val(),$("#input-url").attr("length"),"#input-url");	
		if(rs1&&rs2&&rs3&&rs4){
			$.post('../front/admin/commodity!execute?uid=b0003&' + str, function(data) {
				if (data.status == "success") {
					if(buttonType==1){
						layer.msg("添加成功！");
						setTimeout('window.location.href="../views/commodityAdd.html?province='+province+'"',1000);
					}else{
						layer.msg("添加成功！");
						setTimeout('window.location.href="../views/commodityList.html?province='+province+'"',1000);
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
	url:"../front/admin/resource360!execute?uid=z0001",
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
			$('.alert-danger').hide();
			$('input[name="displays"]').val(data.data.display);
			layer.msg("上传成功！");
		}else{
			$('.alert-danger').html(data.message).show();
			layer.msg(data.message);
		}
	}
});