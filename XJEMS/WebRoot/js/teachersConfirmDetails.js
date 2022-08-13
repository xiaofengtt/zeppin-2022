
function deleteThis(t) {
	layer.titleConfirm('确认要删除吗?', "删除确认", function(index){
		var obj = $(t),cUrl = obj.attr('data-url');
		$.get(cUrl, function(ret) {
			if (ret.status == 'SUCCESS') {
				flag=true;
				getList();
			} else {
				alert('失败,' + ret.message);
			}
		})
		layer.close(index);
	});
	return false;
}

$(document).ready(function() {
	getInfo();
})

function getInfo(){
	var id = url('?id') == null ? '' : url('?id');
	if(id == ''){
		$('#queboxCnt').html('<div><span>数据获取出错，请返回重新查看！</span><div>')//错误提示信息
		return false;
	}
	var urls = '../admin/recordsGet?';
	urls += 'id='+id;
	
	$.get(urls,function(r) {
		if(r.Status =='success') {
				var template = $.templates('#queboxTpl');
				var html = template.render(r.Records);
				$('#queboxCnt').html(html);
				pagesize=r.pageSize;
				count=r.totalResultCount;
		} else {
			var html = '<div><span>没有数据！</span><div>'
			$('#queboxCnt').html(html);
		}
	}).done(function(r){
		$("#back").click(function(){
//			window.history.go(-1);
			window.close();
		})
	});
}
