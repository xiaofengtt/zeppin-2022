var pageNum = '1';
var pagesize;
var count;
var flag=true;

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
	getList();
})

function getList(){
	var page = (typeof pageNum == 'undefined') ? 1 : pageNum;
	$.get('../rest/backadmin/bank/list?pageNum='+page+'&pageSize=10',function(r) {
		if(r.status =='SUCCESS') {
			r.totalPageCount && $('.quepager').html('<span style="font-weight:normal">'+ r.pageNum +'</span>/'+ r.totalPageCount);
			if(r.totalResultCount > 0){
				var template = $.templates('#queboxTpl');
				var html = template.render(r.data);
				$('#queboxCnt').html(html);
				pagesize=r.pageSize;
				count=r.totalResultCount;
			}else{
				var html = '<tr class="nodata"><td colspan=4>没有数据！</td></tr>'
				$('#queboxCnt').html(html);
			}
		} else {
			layer.msg(r.message, {
				time: 2000 
			})
		}
	}).done(function(r){
		if(flag){
			$('#pageTool').Paging({pagesize:pagesize,count:count,callback:function(page,size,count){			
				pageNum = page;
				getList();
				flag=false;
				document.body.scrollTop = document.documentElement.scrollTop = 0;
			},render:function(ops){
				
			}});
			$("#pageTool").find(".ui-paging-container:last").siblings().remove();
		}
		$(".table tbody tr").each(function(){
			//$(this).find("td:eq(1) span img").css("margin-top",(42-$(this).find("td:eq(1) span img").height())/2+"px");
			$("tr:even td span").css("color","rgba(43,43,43,0.8)");
		});
	});
}
