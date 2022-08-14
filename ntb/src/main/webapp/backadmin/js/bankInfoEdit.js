$(document).ready(function() {
	var uuid = (url('?uuid') != null) ? url('?uuid') : '';
	$.get('../rest/backadmin/bank/get?uuid='+uuid,function(r) {
		if(r.status =='SUCCESS') {
			var template = $.templates('#DataTpl');
		    var html = template.render(r.data);
		    $('#DataCnt').html(html);
		} else {
			layer.msg(r.message, {
				time: 2000 
			})
		}
	}).done(function(){
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
				 $('#imageShow').prop("target","_blank");
				$(".self .ajax-upload-dragdrop").css("display","none");
			}
		});
		//上传图片
		$("#iconId").uploadFile({
			id:"1",
			url:"../rest/backadmin/resource/add",
			allowedTypes:"jpg,png,jpeg,bmp,tiff,gif",
			maxFileSize:1024*1024*1024*10,
			fileName:"file",
			maxFileCount : 1,
			dragDropStr: "",
			extErrorStr:"文件格式不正确，请上传指定类型类型的图片文件",
			multiple:false,
			showDone:false,
			showDelete : false,
			deletelStr : '删除',
			doneStr: "完成",
			showAbort:false,
			showStatusAfterSuccess : false,
			maxFileCountErrorStr: "文件数量过多，请先删除。",
			onSuccess:function(files,data,xhr){
				$('input[name="icon"]').val(data.data.uuid);
				$('#iconShow').attr('src','..'+data.data.url);
				$(".other .ajax-upload-dragdrop").css("display","none");
			}
		});
		//上传图片
		$("#iconColorId").uploadFile({
			id:"1",
			url:"../rest/backadmin/resource/add",
			allowedTypes:"jpg,png,jpeg,bmp,tiff,gif",
			maxFileSize:1024*1024*1024*10,
			fileName:"file",
			maxFileCount : 1,
			dragDropStr: "",
			extErrorStr:"文件格式不正确，请上传指定类型类型的图片文件",
			multiple:false,
			showDone:false,
			showDelete : false,
			deletelStr : '删除',
			doneStr: "完成",
			showAbort:false,
			showStatusAfterSuccess : false,
			maxFileCountErrorStr: "文件数量过多，请先删除。",
			onSuccess:function(files,data,xhr){
				$('input[name="iconColor"]').val(data.data.uuid);
				$('#iconColorShow').attr('src','..'+data.data.url);
				$(".third .ajax-upload-dragdrop").css("display","none");
			}
		});
	})
});
//提交
$('#formsubmit').submit(function() {
	if (flagSubmit == false) {
        return false;
    }
    flagSubmit = false;
    setTimeout(function() {
        flagSubmit = true;
    }, 3000);
	var name=$("#name").val().replace(/(^\s*)|(\s*$)/g, "");
	var logo=$("#logo").val().replace(/(^\s*)|(\s*$)/g, "");
	
	var icon=$("#icon").val().replace(/(^\s*)|(\s*$)/g, "");
	var color=$("#color").val().replace(/(^\s*)|(\s*$)/g, "");
	
	var singleLimit=$("#singleLimit").val().replace(/(^\s*)|(\s*$)/g, "");
	var dailyLimit=$("#dailyLimit").val().replace(/(^\s*)|(\s*$)/g, "");
	
	var shortName=$("#shortName").val().replace(/(^\s*)|(\s*$)/g, "");
	
	var iconColor=$("#iconColor").val().replace(/(^\s*)|(\s*$)/g, "");
	
	var code=$("#code").val().replace(/(^\s*)|(\s*$)/g, "");
	
	var codeNum=$("#codeNum").val().replace(/(^\s*)|(\s*$)/g, "");
	
	if(name==""){
		layer.msg('银行名称不能为空', {
			time: 2000 
		});      
	}else if(shortName==""){
		layer.msg('请填写银行简称', {
			time: 2000 
		});
	}else if(code==""){
		layer.msg('请填写银行编码', {
			time: 2000 
		});
	}else if(singleLimit=="" || singleLimit=="0"){
		layer.msg('请填写单笔限额', {
			time: 2000 
		});
	}else if(dailyLimit=="" || dailyLimit=="0"){
		layer.msg('请填写每日限额', {
			time: 2000 
		});
	}else if(color==""){
		layer.msg('请填写显示色值', {
			time: 2000 
		});
	}else if(codeNum==""){
		layer.msg('请填写数字编码', {
			time: 2000
		});
	}else if(logo==""){
		layer.msg('请上传LOGO', {
			time: 2000 
		});
	}else if(icon==""){
		layer.msg('请上传ICON', {
			time: 2000 
		});
	}else if(iconColor==""){
		layer.msg('请上传彩色ICON', {
			time: 2000 
		});
	}else{
		var str = $(this).serialize();
		$.post('../rest/backadmin/bank/edit',str, function(data) {
			if (data.status == "SUCCESS") {
				layer.msg('修改成功', {
					time: 1000 
				}, function(){
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