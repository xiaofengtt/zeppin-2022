//提交
$('#formsubmit').submit(function() {
	if (flagSubmit == false) {
        return false;
    }
    flagSubmit = false;
	var uuid = (url('?uuid') != null) ? url('?uuid') : '';
	$("#uuid").val(uuid);
	
	var receipt=$("#receipt").val()
	if(receipt==""){
		layer.msg('请上传凭证', {
			time: 2000 
		});    
	}else{
		var str = $(this).serialize();
		$.get('../rest/backadmin/shbxOrder/process',str, function(data) {
			if (data.status == "SUCCESS") {
				layer.msg('订单处理成功!', {
					time: 2000 
				}, function(){
					parent.$.colorbox.close()
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
$(".uploadBtn").click(function(){
	$(".ajax-file-upload").find("input[type='file']").eq(0).click();
});