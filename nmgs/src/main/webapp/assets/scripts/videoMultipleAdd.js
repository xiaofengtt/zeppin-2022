var resourceIndex=0;
$(function() {
	newUpload();
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
function newUpload(){
	$(".htmlTpl").css("background","#fafafa");
	$("#uploadQueue").prepend('<div class="htmlTpl" id="HtmlTpl_'+resourceIndex+'"><div class="control-group" id="uploadcon" style="border:1px solid #e5e5e5"><div class="controls"style="text-align:center;margin-left: 0px"><div id="resourceId_'+resourceIndex+'"><span>&nbsp;上传视频&nbsp;&nbsp;</span></div></div></div><div id="resourceAdd"></div></div>');
	$("#resourceId_"+resourceIndex).uploadFile({
		url:"../front/admin/resource!execute?uid=d0001",
		allowedTypes:"mp4,avi,mov,3gp,flv,wmv,rmvb",
		maxFileSize:1024*1024*1024*10,
		fileName:"video",
		maxFileCount : 1,
		dragDropStr: "",
		extErrorStr:"文件格式不正确，请以下类型的视频文件：",
		showStatusAfterSuccess:false,
		showDelete : false,
		showProgress: true,
		deletelStr : '删除',
		showAbort:false,
		fileIndex:resourceIndex,
		onSubmit: function(e, t) {
			newUpload();
		},
		onSuccess:function(files,data,xhr,e)
		{
			if(data.status == 'success'){
				$("#HtmlTpl_"+e.id).attr("id","HtmlTpl_"+data.data.id);
				$("#indexingButton_"+e.id).html('<a href="../views/videoIndexing.html?id='+data.data.id+'" class="btn-edit" data-fancybox-type="iframe"">标引</a>');
				$(".btn-edit").colorbox({
					iframe : true,
					width : "860px",
					height : "580px",
					opacity : '0.5',
					overlayClose : false,
					escKey : true
				});
				layer.msg(files+"上传成功！");
			}else{
				$('.alert-danger').html(files+":"+data.message).show();
			}
		}
	});
	resourceIndex++;
}