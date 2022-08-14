function deleteProvinceModule(t) {
	if (window.confirm("确认删除这条记录吗?")) {
		var obj = $(t),id = obj.attr('data-id');
		$.getJSON('../front/admin/provinceModule!execute?uid=m0006&id='+id, function(ret) {
			if (ret.status == 'success') {
				window.location.reload();
			} else {
				alert('失败,' + ret.message);
			}
		})
	}
	return false;
}

$(document).ready(function() {
	var province = (url('?province') != null) ? url('?province') : '';
	$.getJSON('../front/admin/province!execute?uid=l0002&id='+province, function(r) {
		if(r.status == 'success') {
			$("#provinceName").html("（"+r.data.name+"）");
		} else{
			window.location.href = "../views/sourceMain.html";
		}
	})
	if(province != ''){
		$(".moduleName").css("display","block");
		$.get('../front/admin/provinceModule!execute?uid=m0001&province='+province,function(r) {
			if(r.status =='success') {
				$("#templateName").html(r.data.name);
				var template = $.templates('#queboxTpl');
			    var html = template.render(r.data.module);
			    $('#queboxCnt').html(html);
			}
		}).done(function(){
			$(".btn-edit").colorbox({
				iframe : true,
				width : "860px",
				height : "600px",
				opacity : '0.5',
				overlayClose : false,
				escKey : true
			});
		})
	}else{
		$(".tips").css("display","inline-block");
		$(".moduleName").css("display","none");
	}
})