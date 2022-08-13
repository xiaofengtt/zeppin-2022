var page = (url('?page') != null) ? url('?page') : '1';	
$(function() {
	getTable(-1,'');
	$(".btn-screen").click(
		function(e) {
			e.preventDefault();
			window.location.href="../admin/sizer_initLoad.action?page="+page;
	});
	
})
var urlstr = "";
function getTable(status,name) {
	var page = (url('?page') != null) ? url('?page') : '1';
	
	var index = (page - 1) * 10;
	$
	.getJSON(
		'../admin/sizer_getList.action?status='+status+'&name='+name+'&jtStartIndex='
			+ index + '&jtPageSize=10&jtSorting=id ASC',
	function(json) {
		if (json.Result == "OK") {
			var table="";
			if(json.Records.length=="0"){
				table="<p class='text-center' style='line-height:100px;font-size:20px;color:#666;'>暂无内容</p>";
			}
			for (var i = 0; i < json.Records.length; i++) {
				if(json.Records[i].status=="1"){
					table+='<table class="tableItem table-bordered"><tr><td class="filterId col-md-2 text-center">ID：<span>'
						+json.Records[i].id+'</span></td><td class="filterName col-md-6 text-left">'
						+json.Records[i].name+'</td><td class="filterState col-md-1 text-center">运行</td>'
						+""+'<td class="filterOperate col-md-3 text-center"><a href="../admin/sizer_initLoad.action?id='
						+json.Records[i].id+'&page='+page+'">编辑</a>&nbsp;&nbsp;<a onclick="changeState('
						+json.Records[i].id+',this)">停用</a>&nbsp;&nbsp;<a onclick="deleteFilter('
						+json.Records[i].id+')">删除</a></td></tr><tr><td class="filterWeight col-md-2 text-center no-border">权重：<span>'
						+json.Records[i].weight+'</span></td><td class="col-md-6 no-border"></td>'
						+""+'<td class="filterTime text-right no-border" colspan="2">创建时间：<span>'
						+json.Records[i].createtime+'</span></td></tr></table>';
				}else if(json.Records[i].status=="0"){
					table+='<table class="tableItem table-bordered"><tr><td class="filterId col-md-2 text-center">ID：<span>'
						+json.Records[i].id+'</span></td><td class="filterName col-md-6 text-left">'
						+json.Records[i].name+'</td><td class="filterState col-md-1 text-center red">停止</td>'
						+""+'<td class="filterOperate col-md-3 text-center"><a href="../admin/sizer_initLoad.action?id='
						+json.Records[i].id+'&page='+page+'">编辑</a>&nbsp;&nbsp;<a onclick="changeState('
						+json.Records[i].id+',this)">启用</a>&nbsp;&nbsp;<a onclick="deleteFilter('
						+json.Records[i].id+')">删除</a></td></tr><tr><td class="filterWeight col-md-2 text-center no-border">权重：<span>'
						+json.Records[i].weight+'</span></td><td class="col-md-6 no-border"></td>'
						+""+'<td class="filterTime text-right no-border" colspan="2">创建时间：<span>'
						+json.Records[i].createtime+'</span></td></tr></table>';
				}
				
			}
			$(".tableDiv").html(table);
			var totalPage = Math
			.ceil(json.TotalRecordCount / 10);
			if(totalPage==0){
				totalPage=1;
			}
			var options = {
				currentPage : page,
				totalPages : totalPage,
				shouldShowPage : function(type, page,
						current) {
					switch (type) {
					default:
						return true;
					}
				},
				onPageClicked : function(e, originalEvent,
						type, page) {
					window.location = updateURLParameter(
							url(), 'page', page);
				}
	
			}
			$('#pagination').bootstrapPaginator(options);
		} else {

		}

	}).done(
	function() {
		
	})
}

//停用筛查器
function changeState(id,obj){
	$.ajax({
        type: "POST",
        url: "../admin/sizer_changeStatus.action",
        data: {id:id},
        dataType: "text",
        async: true,
        success: function (data) {
        	var json = (new Function("", "return " + data.split("&&&")[0]))();
        	if(json.Result=="OK"){
        		if($(obj).parent().parent().find(".filterState").hasClass("red")){
        			$(obj).html("停用");
        			$(obj).parent().parent().find(".filterState").removeClass("red").html("运行");
        		}else{
        			$(obj).html("启用");
        			$(obj).parent().parent().find(".filterState").addClass("red").html("停止");
        		}
        	}else{
        		layer.confirm(json.Message, {
    				btn : [ '确定' ]
    			//按钮
    			}, function() {
    				layer.closeAll();
    			});
        	}
        }
	});
}

//删除筛查器
function deleteFilter(id){
	$.ajax({
        type: "POST",
        url: "../admin/sizer_delete.action",
        data: {id:id},
        dataType: "text",
        async: true,
        success: function (data) {
        	var json = (new Function("", "return " + data.split("&&&")[0]))();
        	if(json.Result=="OK"){
        		layer.msg('删除成功！', {
        			  icon: 1,
        			  time: 2000 
        			}, function(){
        				window.location.href=window.location.href;
        			});   
        	}else{
        		layer.confirm(json.Message, {
    				btn : [ '确定' ]
    			//按钮
    			}, function() {
    				layer.closeAll();
    			});
        	}
        }
	});
}

