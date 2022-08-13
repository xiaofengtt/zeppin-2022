$(document).ready(function() {
	var uuid = (url('?uuid') != null) ? url('?uuid') : '';
	$.get('../rest/backadmin/operator/get?uuid='+uuid,function(r) {
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