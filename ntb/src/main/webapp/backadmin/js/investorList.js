var pageNum = '1';
var flag=true;
function init(){
	pageNum = '1';
	flag=true;
}
function searchBtn(){
	init();
	getList();
	return false;
}

$(document).ready(function() {
	getList();
})

function getList(){
	var name=$("#search").val().replace(/(^\s*)|(\s*$)/g, "");
	var page = (typeof pageNum == 'undefined') ? 1 : pageNum;
	$.get('../rest/backadmin/investor/list?pageNum='+page+'&pageSize=10&name='+name,function(r) {
		if(r.status =='SUCCESS') {
			r.totalPageCount && $('.quepager').html('<span style="font-weight:normal">'+ r.pageNum +'</span>/'+ r.totalPageCount);
			if(r.totalResultCount > 0){
				var template = $.templates('#queboxTpl');
				var html = template.render(r.data);
				$('#queboxCnt').html(html);
			}else{
				var html = '<div class="nodata">没有数据！</div>'
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