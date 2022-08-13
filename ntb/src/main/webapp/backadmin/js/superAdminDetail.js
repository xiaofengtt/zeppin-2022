$(document).ready(function() {
	var uuid = (url('?uuid') != null) ? url('?uuid') : '';
	$.get('../rest/backadmin/superAdmin/get?uuid='+uuid,function(r) {
		if(r.status =='SUCCESS') {
			var template = $.templates('#DataTpl');
		    var html = template.render(r.data);
		    $('#DataCnt').html(html);
		} else {
			layer.msg(r.message, {
				time: 2000 
			})
		}
	});
});

function editPassword(t){
	var obj = $(t),uuid = obj.attr('data-uuid');
	$.get('../rest/backadmin/superAdmin/password?uuid='+uuid,function(r) {
		if (r.status == "SUCCESS") {
			layer.msg('重置成功', {
				time: 1000 
			}, function(){
				parent.$.colorbox.close()
			}); 
		} else {
			layer.msg(r.message, {
				time: 2000 
			})
		}
	})
}