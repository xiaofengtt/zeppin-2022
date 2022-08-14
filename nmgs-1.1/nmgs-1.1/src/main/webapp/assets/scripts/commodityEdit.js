function isMoney(obj){
   if (! obj) return false;
   return (/^\d+(\.\d{1,2})?$/).test(obj);   
}
$(function() {
	var id = url('?id');
		$('input[name="id"]').val(id);
	$.get('../front/admin/commodity!execute?uid=b0002&id='+url('?id'),function(r){
		if(r.status == 'success') {
			var st = JSON.stringify(r);
		    var template = $('#DataTpl').html();
		    var html = Mustache.render(template, $.parseJSON(st));
		    $('#HtmlTpl').html(html);
		    if($('#displayOri').val() == ''){
		    	$('#displayStatus').html("未上传 &nbsp;选择文件&nbsp;&nbsp;");
		    }else{
		    	$('#displayStatus').html("已上传 &nbsp;替换文件&nbsp;&nbsp;");
		    }
		    //验证字符长度
			$(".input-text").blur(function(){
				getByteLen($(this).val(),$(this).attr("length"),this);
			})
			//验证为数字且长度限制
			$(".input-number").blur(function() {
				checkNumber($(this).val(),$(this).attr("length"),this);
			})
			//验证url
			$(".input-url").blur(function(){
				checkUrl($(this).val(),$(this).attr("length"),this);		
			})
		    $("#resourceId").uploadFile({
		    	id:"1",
				url:"../front/admin/resource!execute?uid=d0001",
				allowedTypes:"jpg,png,jpeg,bmp,tiff",
				maxFileSize:1024*1024*1024*10,
				fileName:"video",
				maxFileCount : 1,
				dragDropStr: "",
				extErrorStr:"文件格式不正确，请上传指定类型类型的文件",
				showStatusAfterSuccess:true,
				showDelete : true,
				showDone : false,
				showProgress: true,
				deletelStr : '删除',
				showAbort:true,
				onSuccess:function(files,data,xhr){
					if(data.status=="success"){
						$('input[name="cover"]').val('');
						$('input[name="cover"]').val(data.data.id);
						layer.msg("上传成功！");
						$("#coverImg").remove();
						$("#change").hide();
						
					}else{
						$('.alert-danger').html(data.message).show();
						layer.msg(data.message);
					}
				}
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
				showStatusAfterSuccess:false,
				showDelete : false,
				showProgress: true,
				deletelStr : '删除',
				showAbort:false,
				onSuccess:function(files,data,xhr){
					if(data.status=="success"){
						$('.alert-danger').hide();
						$('input[name="displays"]').val(data.data.display);
						layer.msg("上传成功！");
						
					}else{
						$('.alert-danger').html(data.message).show();
						layer.msg(data.message);
					}
				}
			});
			
			$("#change").click(function(){
				$("#coverImg").remove();
				$('input[name="cover"]').val('');
				$(this).hide();
			});
		}else {
			alert('服务端出错！请稍后重试！');
		}
	}).done(function(){
		$('#formsubmit').submit(function() {
			var str = $(this).serialize();
			var rs1=getByteLen($("#input-name").val(),$("#input-name").attr("length"),"#input-name");
			var rs2=checkNumber($("#input-originalPrice").val(),$("#input-originalPrice").attr("length"),"#input-originalPrice");
			var rs3=checkNumber($("#input-price").val(),$("#input-price").attr("length"),"#input-price");
			var rs4=checkUrl($("#input-url").val(),$("#input-url").attr("length"),"#input-url");	
			if(rs1&&rs2&&rs3&&rs4){
				$.post('../front/admin/commodity!execute?uid=b0004&'+ str, function(data) {
					if (data.status == "success") {
						window.parent.location.reload();
					} else {
						$('.alert-danger').html(data.message).show();
					}
				})
			}
			return false;
		});
	})
	
	//项目类型 helper 函数
	$(document).on("click",function(e) {
		if(!$(e.target).parents().is('.ufc'))
			$('.uul').hide();
	});
})