var pagenum = '1';

function deleteRow(t) {
	if (window.confirm("确认删除这条记录吗?")) {
		var obj = $(t),id = obj.attr('data-id');
		$.getJSON('../front/admin/commodity!execute?uid=b0005&id='+id, function(ret) {
			if (ret.status == 'success') {
				window.location.reload();
			} else {
				alert('失败,' + ret.message);
			}
		})
	}
	return false;
}

$('#searchForm').submit(function(){
	var key = $('input[name="stype"]:checked').val(),obj = {};
	obj[key] = $('#searchheader').val();
	var str = '&'+key+'='+obj[key];
	pagenum = '1';
	getList(str);
	return false;
});


//加载列表
function getList(params) {
	var province = (url('?province') != null) ? url('?province') : '';
	if (params == undefined){
		params = '';
	}
	var page = (typeof pagenum == 'undefined') ? 1 : pagenum;
	var d = dialog({
	    title: '系统提示',
	    width : 220,
		height:60,
	    content: '<p style="line-height:50px;">加载中...</p>'
	});
	d.showModal();

	$.getJSON('../front/admin/commodity!execute?uid=b0001&pagesize=10&sorts=createtime desc&province='+province+params+'&pagenum='+page, function(r) {
		r.totalPageCount && $('.quepager').html('<span style="font-weight:normal">'+ r.pageNum +'</span>/'+ r.totalPageCount);

		if(r.status == 'success' && r.data.length > 0) {
		    var template = $.templates('#queboxTpl');
		    var html = template.render(r.data);
		    $('#queboxCnt').html(html);
		    $(".btn-edit").colorbox({
				iframe : true,
				width : "860px",
				height : "710px",
				opacity : '0.5',
				overlayClose : false,
				escKey : true
			});
		} else if (r.status == 'success' && r.data.length == 0) {
			$('#pagnationPaper').html('');
			$('#queboxCnt').html('<tr><td class="no_data" colspan ="5" style="height:100px">无搜索结果</td></tr>');
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
		var province = (url('?province') != null) ? url('?province') : '';
		$("#addButton").attr("href","../views/commodityAdd.html?province="+province)
		$.getJSON('../front/admin/province!execute?uid=l0002&id='+province, function(r) {
			if(r.status == 'success') {
				$("#provinceName").html("（"+r.data.name+"）");
			} else{
				window.location.href = "../views/sourceMain.html";
			}
		})
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