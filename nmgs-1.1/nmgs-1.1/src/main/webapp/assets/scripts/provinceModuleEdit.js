$(function() {
	var id = url('?id');
	$('input[name="id"]').val(id);
	$.get('../front/admin/provinceModule!execute?uid=m0003&id='+id,function(r){
		if(r.status == 'success') {
			var template = $.templates('#queboxTpl');
		    var html = template.render(r.data);
		    $('#HtmlTpl').html(html);
		    $('#prioritySelect').val(r.data.priority);
		    $.get('../front/admin/provinceModule!execute?uid=m0007&id='+id,function(result){
				if(result.status == 'success') {
					var moduleHtml='';
					$.each(result.data,function(i,v){
						if(v.id==r.data.module){
							moduleHtml+="<option value='"+v.id+"' selected>"+v.name+"</option>";
						}else{
							moduleHtml+="<option value='"+v.id+"'>"+v.name+"</option>";
						}
					})
					$('#moduleSelect').html(moduleHtml);
					$(".input-text").blur(function(){
						getByteLen($(this).val(),$(this).attr("length"),this);
					})
					$(".input-text-nullable").blur(function(){
						checkNullableLen($(this).val(),$(this).attr("length"),this);
					})
					$(".input-url").blur(function(){
						checkUrl($(this).val(),$(this).attr("length"),this);		
					})
				}else {
					alert(result.message);
				}
			})
		}else {
			alert(r.message);
		}
	}).done(function(){
		$('#formsubmit').submit(function() {
			var str = $(this).serialize();
			var rs1=getByteLen($("#input-title").val(),$("#input-title").attr("length"),"#input-title");
			var rs2=checkNullableLen($("#input-content").val(),$("#input-content").attr("length"),"#input-content");
			var rs3=checkUrl($("#input-url").val(),$("#input-url").attr("length"),"#input-url");
			if(rs1&&rs2&&rs3){
				$.post('../front/admin/provinceModule!execute?uid=m0005&'+ str, function(data) {
					if (data.status == "success") {
						window.parent.location.reload();
					} else {
						$('.alert-danger').html(data.message).show();
					}
				})
			}
			return false;
		});
		$("#resourceId").uploadFile({
			id:"1",
			url:"../front/admin/resource!execute?uid=d0001",
			allowedTypes:"jpg,png,jpeg,bmp,tiff",
			maxFileSize:1024*1024*1024*10,
			fileName:"video",
			maxFileCount : 1,
			dragDropStr: "",
			extErrorStr:"??????????????????????????????????????????????????????????????????",
			multiple:false,
			showDelete: true,
			showStatusAfterSuccess:true,
			showProgress: true,
			deletelStr : '??????',
			showAbort:false,
			showDone:false,
			maxFileCountErrorStr: "????????????????????????????????????",
			onSuccess:function(files,data,xhr)
			{
				if(data.status=="success"){
					$('input[name="image"]').val(data.data.id);
					
					$("#img").remove();
					$("#change").hide();
				}
			}
		});
		$("#change").click(function(){
			$("#img").remove();
			$('input[name="image"]').val('');
			$(this).hide();
		});
	})
	
	//???????????? helper ??????
	$(document).on("click",function(e) {
		if(!$(e.target).parents().is('.ufc'))
			$('.uul').hide();
	});
})