var pageNum = '1';
var flag=true;
function init(){
	pageNum = '1';
	flag=true;
}
function deleteThis(t) {
	layer.titleConfirm('确认要删除吗?', "删除确认", function(index){
		var obj = $(t),cUrl = obj.attr('data-url');
		$.get(cUrl, function(ret) {
			if (ret.status == 'SUCCESS') {
				init();
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
	var uuid = (url('?uuid') != null) ? url('?uuid') : '';
	$("#addButton").attr("href","../backadmin/methodInfoAdd.jsp?controller="+uuid)
	getList();
})

function getList(){
	var page = (typeof pageNum == 'undefined') ? 1 : pageNum;
	var uuid = (url('?uuid') != null) ? url('?uuid') : '';
	var controllerName = '';
	$.get('../rest/backadmin/controllerMethod/methodlist?pageNum='+page+'&pageSize=10&uuid='+uuid,function(r) {
		if(r.status =='SUCCESS') {
			r.totalPageCount && $('.quepager').html('<span style="font-weight:normal">'+ r.pageNum +'</span>/'+ r.totalPageCount);
			if(r.totalResultCount > 0){
				var template = $.templates('#queboxTpl');
				var html = template.render(r.data);
				$('#queboxCnt').html(html);
			}else{
				var html = '<tr class="nodata"><td colspan=5>没有数据！</td></tr>'
				$('#queboxCnt').html(html);
			}
		} else {
			layer.msg(r.message, {
				time: 2000 
			})
		}
	}).done(function(r){
		if(flag){
			$('#pageTool').Paging({pagesize:r.pageSize,count:r.totalResultCount,callback:function(page,size,count){			
				pageNum = page;
				getList();
				flag=false;
				document.body.scrollTop = document.documentElement.scrollTop = 0;
			},render:function(ops){
				
			}});
			$("#pageTool").find(".ui-paging-container:last").siblings().remove();
		}
	})
}