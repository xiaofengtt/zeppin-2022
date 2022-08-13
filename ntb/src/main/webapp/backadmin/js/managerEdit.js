$(document).ready(function() {
	var uuid = (url('?uuid') != null) ? url('?uuid') : '';
	$.get('../rest/backadmin/manager/get?uuid='+uuid,function(r) {
		if(r.status =='SUCCESS') {
			var template = $.templates('#DataTpl');
		    var html = template.render(r.data);
		    $('#DataCnt').html(html);
		}
	}).done(function(r){
		var resume = UE.getEditor('resume');
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
				$('input[name="photo"]').val(data.data.uuid);
				$('#imageShow').attr('src','..'+data.data.url);
				$(".ajax-upload-dragdrop").css("display","none");
			}
		})
	})
});
//提交
$('#formsubmit').submit(function() {
	var name=$("#name").val().replace(/(^\s*)|(\s*$)/g, "");
	var idcard=$("#idcard").val().replace(/(^\s*)|(\s*$)/g, "");
	var mobile=$("#mobile").val().replace(/(^\s*)|(\s*$)/g, "");
	var type=$("#type").val().replace(/(^\s*)|(\s*$)/g, "");
	var workage=$("#workage").val().replace(/(^\s*)|(\s*$)/g, "");
	
	if(name==""){
		layer.msg('姓名不能为空', {
			time: 2000 
		});
	}else if(idcard==""){
		layer.msg('身份证号不能为空', {
			time: 2000 
		});
	}else if(mobile==""){
		layer.msg('手机号码不能为空', {
			time: 2000 
		});
	}else if(type==""){
		layer.msg('用户类型不能为空', {
			time: 2000 
		});
	}else if (!(/(^[0-9]\d*$)/.test(workage))){
		layer.msg('从业年限应为数字类型', {
			time: 2000 
		});
	}else{
		var str = $(this).serialize();
		$.post('../rest/backadmin/manager/edit',str, function(data) {
			if (data.status == "SUCCESS") {
				layer.msg('修改成功', {
					time: 1000 
				}, function(){
//					window.parent.getList();
//					parent.$.colorbox.close()
					window.location.href="managerList.jsp";
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