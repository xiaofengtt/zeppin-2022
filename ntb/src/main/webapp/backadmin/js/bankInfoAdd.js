$(document).ready(function() {
	//上传图片
	$("#resourceId").uploadFile({
		id:"1",
		url:"../rest/backadmin/resource/add",
		allowedTypes:"jpg,png,jpeg,bmp,tiff,gif",
		maxFileSize:1024*1024*1024*10,
		fileName:"file",
		maxFileCount : 1,
		dragDropStr: "",
		extErrorStr:"文件格式不正确，请上传指定类型类型的视频文件",
		multiple:false,
		showDone:false,
		showDelete : false,
		deletelStr : '删除',
		doneStr: "完成",
		showAbort:false,
		showStatusAfterSuccess : false,
		maxFileCountErrorStr: "文件数量过多，请先删除。",
		onSuccess:function(files,data,xhr){
			$('input[name="logo"]').val(data.data.uuid);
			$('#imageShow').attr('src','..'+data.data.url);
			$(".ajax-upload-dragdrop").css("display","none");
		}
	});
});
//提交
$('#formsubmit').submit(function() {
	var name=$("#name").val().replace(/(^\s*)|(\s*$)/g, "");
	var logo=$("#logo").val().replace(/(^\s*)|(\s*$)/g, "");
	
	if(name==""){
		layer.msg('银行名称不能为空', {
			time: 2000 
		});      
	}else if(logo==""){
		layer.msg('请上传LOGO', {
			time: 2000 
		});
	}else{
		var str = $(this).serialize();
		$.post('../rest/backadmin/bank/add',str, function(data) {
			if (data.status == "SUCCESS") {
				layer.msg('添加成功', {
					time: 1000 
				}, function(){
//					window.parent.getList();
//					parent.$.colorbox.close();
					window.location.href="bankInfoList.jsp";
				}); 
			} else {
				layer.msg(data.message, {
					time: 2000 
				})
			}
		}) 
	}
	return false;
});