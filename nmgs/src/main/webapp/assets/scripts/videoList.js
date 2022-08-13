var t;
var urls;
var id;
var pointTime
$(function(){
	var point;
	arr = [];
	if(id==""){
		history.go(-1);
	}
})

var index;
//获取视频信息
function layerVideo(a){
	index=layer.open({
		type: 2,
		  title: false,
		  shadeClose: false,
		  shade: 0.8,
		  scrollbar: false,
		  area: ['80%', '80%'],
		  content: 'flash.html?id='+$(a).attr("name") 
	}); 
		$(".layui-layer-setwin .layui-layer-close2").css("display", "block");
	   	$(".layui-layer-rim").css({"border":"none","border-radius":"0px"});
}

var pagenum = '1';
function changeStatus(t) {
	var obj = $(t),cUrl = obj.attr('data-url');
	$.getJSON(cUrl, function(ret) {
		if (ret.status == 'success') {
			getList();
		} else {
			alert('失败,' + ret.message);
		}
	})
	return false;
}

$('#searchForm').submit(function(){
	getList();
	return false;
});


//加载列表
function getList() {
	var key = $('input[name="stype"]:checked').val(),obj = {};
	obj[key] = $('#searchheader').val();
	var str = '&'+key+'='+obj[key];
	var page = (typeof pagenum == 'undefined') ? 1 : pagenum;
	var d = dialog({
	    title: '系统提示',
	    width : 220,
		height:60,
	    content: '<p style="line-height:50px;">加载中...</p>'
	});
	d.showModal();
	$.getJSON('../front/admin/videoinfo!execute?uid=g0008'+str,function(r) {
		if(r.status == 'success') {
			$('#status_checked').html(r.data.checked);
			$('#status_unchecked').html(r.data.unchecked);
			$('#status_deleted').html(r.data.deleted);
			$('#status_uploaded').html(r.data.uploaded);
			$('#status_failed').html(r.data.failed);
			$('#status_transcoding').html(r.data.transcoding);
		}
	});
	var filterStatus= $("#statusChecked").val();
	$.getJSON('../front/admin/videoinfo!execute?uid=g0001&pagesize=10&sort='+str+'&pagenum='+page+'&status='+filterStatus, function(r) {
		r.totalPageCount && $('.quepager').html('<span style="font-weight:normal">'+ r.pageNum +'</span>/'+ r.totalPageCount);
		if(r.status == 'success' && r.data.length > 0) {
		    var template = $.templates('#queboxTpl');
		    var html = template.render(r.data);
		    $('#queboxCnt').html(html);
		    $(".btn-edit").colorbox({
				iframe : true,
				width : "860px",
				height : "580px",
				opacity : '0.5',
				overlayClose : false,
				escKey : true
			});
		} else if (r.status == 'success' && r.data.length == 0) {
			$('#pagnationPaper').html('');
			$('#queboxCnt').html('<div class="no_data">无搜索结果</div>');
		}
		$(".main").animate({scrollTop: 0}, 1e3);
		d.close().remove();
	}).done(function(r){//分页
		$('#pagnationPaper').pagination({
			currentPage : r.pageNum,
	        items: r.totalResultCount,
			edges: 3,
	        itemsOnPage : r.pageSize,
			onPageClick : function(pageNumber,event) {
				pagenum = pageNumber;
				getList();
			}
	    });
	});
}

$(function(){
	function init() {
		getList();
	};
	init();
	$(".statusbar a").click(function(){
		$(this).addClass("light").siblings().removeClass("light");
		$("#statusChecked").val($(this).find("span").attr("name"));
		pagenum = '1';
		setTimeout("getList()",100);
	});
})

function deleteRow(t) {
	if (window.confirm("确认彻底删除这条记录吗?")) {
		var obj = $(t),id = obj.attr('data-id');
		$.getJSON('../front/admin/videoinfo!execute?uid=g0006&id='+id, function(ret) {
			if (ret.status == 'success') {
				window.location.reload();
			} else {
				alert('失败,' + ret.message);
			}
		})
	}
	return false;
}