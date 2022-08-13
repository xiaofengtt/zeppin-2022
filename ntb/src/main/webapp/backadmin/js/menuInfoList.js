var pageNum = '1';
var flag=true;
var cname='';
var ctitle='';
var flag2 = true;
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
				flag2=false;
				getList();
			} else {
				alert('失败,' + ret.message);
			}
		})
		layer.close(index);
	});
	return false;
}

//$(".btn-add").colorbox({
//	iframe : true,
//	width : "900px",
//	height : "600px",
//	opacity : '0.5',
//	overlayClose : false,
//	escKey : true
//});

$(document).ready(function() {
	var uuid = (url('?pid') != null) ? url('?pid') : '';
	if(uuid != ''){
		$.get('../rest/backadmin/menu/menuget?uuid='+uuid,function(r) {
			if(r.status =='SUCCESS') {
				cname = r.data.name
				ctitle = r.data.title;
			} else {
				layer.msg(r.message, {
					time: 2000 
				})
			}
		}).done(function(){
			getList();
		})
		
	}else{
		getList();
	}
	
})

function getList(){
	var page = (typeof pageNum == 'undefined') ? 1 : pageNum;
	var pid = (url('?pid') != null) ? url('?pid') : '';
	if(pid != '' && cname != '' && flag2){
		$('.current').attr('href','../backadmin/menuInfoList.jsp');
		$('.current').removeAttr("class");
		$('.locationLeft').append('<span>></span><a class="current">（'+ctitle+'）二级菜单管理</a>');
	}
	$.get('../rest/backadmin/menu/menulist?pageNum='+page+'&pageSize=10&pid='+pid,function(r) {
		if(r.status =='SUCCESS') {
			r.totalPageCount && $('.quepager').html('<span style="font-weight:normal">'+ r.pageNum +'</span>/'+ r.totalPageCount);
			if(r.totalResultCount > 0){
				var template = $.templates('#queboxTpl');
				var html = template.render(r.data);
				$('#queboxCnt').html(html);
				if(r.data[0].level>1){
					$('.add').attr('href','../backadmin/menuInfoAdd.jsp?pid='+r.data[0].pid);
				}
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