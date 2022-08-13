function changeProvince(t){
	var province = $(t).val();
	window.location.href="../views/provinceModule.html?province="+province;
}

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
	$.get('../front/admin/province!execute?uid=l0001&status=normal',function(r) {
		if(r.status =='success') {
			var html = '<option value="">请选择</option>';
			if(r.data.length>0){
				$.each(r.data,function(i,v){
					html += '<option value="'+v.id+'" '+(v.id==province?'selected':'')+'>'+v.name+'</option>';
				})
			}
			$('#provinceSelect').html(html);
		}
	}).done(function(){
		if(province != ''){
			$(".tips").css("display","none");
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
})