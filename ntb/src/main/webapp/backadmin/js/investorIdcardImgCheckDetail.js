var uuid = (url('?uuid') != null) ? url('?uuid') : '';
$(document).ready(function() {
	var uuid = (url('?uuid') != null) ? url('?uuid') : '';
	$.get('../rest/backadmin/investor/getidcardimgInfo?uuid='+uuid,function(r) {
		if(r.status =='SUCCESS') {
			var template = $.templates('#DataTpl');
		    var html = template.render(r.data);
		    $('#pageTool').html(html);
		    if(r.data.status == 'checked'){
		    	$('.operateCheck').html('<span class="stageTag back-green">审核结果：已通过</span>')
		    } else if (r.data.status == 'unpassed') {
		    	$('.operateCheck').html('<span class="stageTag back-red">审核结果：审核不通过</span>')
		    }
		} else {
			layer.msg(r.message, {
				time: 2000 
			})
		}
	});
});

function checked(t) {
	var reason = $('#checkReason').val();
	$.post('../rest/backadmin/investor/checked',{
		uuid : uuid,
		status : 'checked',
		reason : reason
	}, function(r) {
		if (r.status == 'SUCCESS') {
			layer.msg('操作成功', {
				time: 1000 
			}, function(){
				window.close();
			});
			window.parent.init();
			window.parent.getStatusList();
			window.parent.getList();
			parent.$.colorbox.close();
		} else {
			layer.msg('操作失败,' + r.message, {
				time: 1000 
			}, function(){
				window.close();
			});
			return false;
		}
	})
}

function unpassed(t) {
	var reason = $('#checkReason').val();
	$.post('../rest/backadmin/investor/checked',{
		uuid : uuid,
		status : 'unpassed',
		reason : reason
	}, function(r) {
		if (r.status == 'SUCCESS') {
			layer.msg('操作成功', {
				time: 1000 
			}, function(){
				window.close();
			});
			window.parent.init();
			window.parent.getStatusList();
			window.parent.getList();
			parent.$.colorbox.close();
		} else {
			layer.msg('操作失败,' + r.message, {
				time: 1000 
			}, function(){
				window.close();
			});
			return false;
		}
	})
}
