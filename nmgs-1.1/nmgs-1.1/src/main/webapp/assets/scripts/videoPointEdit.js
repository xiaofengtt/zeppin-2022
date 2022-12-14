$(function() {
	var id = url('?id');
	$('input[name="id"]').val(id);
	$.get('../front/admin/videoCommodityPoint!execute?uid=e0003&id='+id,function(r){
		if(r.status == 'success') {
			var province = (url('?province') != null) ? url('?province') : '';
			var st = JSON.stringify(r);
		    var template = $('#DataTpl').html();
		    var html = Mustache.render(template, $.parseJSON(st));
		    $('#HtmlTpl').html(html);
		    $(".input-text").blur(function(){
				getByteLen($(this).val(),$(this).attr("length"),this);
			})
			$(".input-number").blur(function() {
				checkNumber($(this).val(),$(this).attr("length"),this);
			})
		    $.get('../front/admin/commodity!execute?uid=b0006&status=normal&province='+province,function(result){
				if(result.status == 'success') {
					var commodityHtml='';
					$.each(result.data,function(i,v){
						if(v.id==r.data.commodity){
							commodityHtml+="<option name='video' value='"+v.id+"' selected>"+v.name+"</option>";
						}else{
							commodityHtml+="<option name='video' value='"+v.id+"'>"+v.name+"</option>";
						}
					})
					$('#commoditySelect').html(commodityHtml);
				}else {
					alert(r.message);
				}
			})
		}else {
			alert(r.message);
		}
	}).done(function(){
		$('#formsubmit').submit(function() {
			var str = $(this).serialize();
			var rs1=checkNumber($("#input-showTime").val(),$("#input-showTime").attr("length"),"#input-showTime");
			var rs2=getByteLen($("#input-showMessage").val(),$("#input-showMessage").attr("length"),"#input-showMessage");
			if(rs1&&rs2){
				$.post('../front/admin/videoCommodityPoint!execute?uid=e0005&'+ str, function(data) {
					if (data.status == "success") {
						window.parent.getList();
						parent.$.colorbox.close();
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
					$('input[name="showBanner"]').val(data.data.id);
					
					$("#bannerImg").remove();
					$("#change").hide();
				}
			}
		});
		$("#change").click(function(){
			$("#bannerImg").remove();
			$('input[name="showBanner"]').val('');
			$(this).hide();
		});
	})
	
	//???????????? helper ??????
	$(document).on("click",function(e) {
		if(!$(e.target).parents().is('.ufc'))
			$('.uul').hide();
	});
})