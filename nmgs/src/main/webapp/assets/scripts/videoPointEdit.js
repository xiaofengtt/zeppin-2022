$(function() {
	var id = url('?id');
	$('input[name="id"]').val(id);
	$.get('../front/admin/videoCommodityPoint!execute?uid=e0003&id='+id,function(r){
		if(r.status == 'success') {
			var st = JSON.stringify(r);
		    var template = $('#DataTpl').html();
		    var html = Mustache.render(template, $.parseJSON(st));
		    $('#HtmlTpl').html(html);
		    $.get('../front/admin/commodity!execute?uid=b0006&status=normal',function(result){
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
			$.post('../front/admin/videoCommodityPoint!execute?uid=e0005&'+ str, function(data) {
				if (data.status == "success") {
					window.parent.getList();
					parent.$.colorbox.close();
				} else {
					$('.alert-danger').html(data.message).show();
				}
			})
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
	
	//项目类型 helper 函数
	$(document).on("click",function(e) {
		if(!$(e.target).parents().is('.ufc'))
			$('.uul').hide();
	});
})