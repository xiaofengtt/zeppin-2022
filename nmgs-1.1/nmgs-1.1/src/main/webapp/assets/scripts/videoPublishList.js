var pagenum = '1';
function changeStatus(t) {
	var obj = $(t),cUrl = obj.attr('data-url');
	$.getJSON(cUrl, function(ret) {
		if (ret.status == 'success') {
			pagenum = '1';
			getList();
		} else {
			alert('失败,' + ret.message);
		}
	})
	return false;
}

function addPublish(){
	var province = (url('?province') != null) ? url('?province') : '';
	var categoryFirst = $("#categoryFirstSelect").val();
	var categorySecond = $("#categorySecondSelect").val();
	if (categoryFirst==''){
		alert("请先选择一级栏目");
	}else{
		if(categorySecond==''){
			alert("请先选择二级栏目");
		}else{
			window.location.href = "../views/videoPublishAdd.html?province="+province+"&cid="+categorySecond;
		}
	}
	
}

function deleteRow(t) {
	if (window.confirm("确认删除这条记录吗?")) {
		var obj = $(t),id = obj.attr('data-id');
		$.getJSON('../front/admin/videoPublish!execute?uid=h0005&id='+id, function(ret) {
			if (ret.status == 'success') {
				pagenum = '1';
				getList();
			} else {
				alert('失败,' + ret.message);
			}
		})
	}
	return false;
}

$('#searchForm').submit(function(){
	pagenum = '1';
	getList();
	return false;
});

function getComponent(){
	$.getJSON('../front/admin/component!execute?uid=k0001&status=normal', function(r) {
		if(r.status == 'success') {
		    var componentHtml = '<option value="">全部</option>';
		    $.each(r.data,function(i,v){
		    	componentHtml += '<option value="'+v.id+'">'+v.name+'</option>';
			})
			$("#componentSelect").html(componentHtml);
		}
	})
}

function getFirstCategory(){
	var province = (url('?province') != null) ? url('?province') : '';
	$.getJSON('../front/admin/category!execute?uid=a0001&level=1&status=normal&province='+province, function(r) {
		if(r.status == 'success') {
		    var categoryFirstHtml = '<option value="">全部</option>';
		    $.each(r.data,function(i,v){
		    	categoryFirstHtml += '<option value="'+v.id+'">'+v.name+'</option>';
			})
			$("#categoryFirstSelect").html(categoryFirstHtml);
		}
	})
}

function getFirstCategory(){
	var province = (url('?province') != null) ? url('?province') : '';
	var component = $("#componentSelect").val();
	$.getJSON('../front/admin/category!execute?uid=a0001&status=normal&level=1&province='+province+'&component='+component, function(r) {
		if(r.status == 'success') {
		    var categoryFirstHtml = '<option value="">全部</option>';
		    $.each(r.data,function(i,v){
		    	categoryFirstHtml += '<option value="'+v.id+'" component="'+v.component+'">'+v.name+'</option>';
			})
			$("#categoryFirstSelect").html(categoryFirstHtml);
		    $("#categorySecondSelect").html('<option value="">全部</option>');
		    getList();
		}
	})
	
}

function getSecondCategory(){
	var province = (url('?province') != null) ? url('?province') : '';
	var parent = $("#categoryFirstSelect").val();
	if(parent!=''){
		$.getJSON('../front/admin/category!execute?uid=a0001&status=normal&level=2&province='+province+'&parent='+parent, function(r) {
			if(r.status == 'success') {
			    var categorySecondHtml = '<option value="">全部</option>';
			    $.each(r.data,function(i,v){
			    	categorySecondHtml += '<option value="'+v.id+'">'+v.name+'</option>';
				})
				$("#categorySecondSelect").html(categorySecondHtml);
			    getList();
			}
		})
	}else{
		$("#categorySecondSelect").html('<option value="">全部</option>');
		getList();
	}
}

function changeComponent(){
	pagenum = '1';
	getFirstCategory();
}

function changeFirstCategory(){
	pagenum = '1';
	var comp = $("#categoryFirstSelect").children('option:selected').attr("component")
	if(comp != undefined){
		$("#componentSelect").val(comp);
	}
	getSecondCategory();
}

function changeSecondCategory(){
	pagenum = '1';
	getList();
}

//加载列表
function getList() {
	var province = (url('?province') != null) ? url('?province') : '';
	var component = $("#componentSelect").val();
	
	var key = $('input[name="stype"]:checked').val(),obj = {};
	obj[key] = $('#searchheader').val();
	var str = '&'+key+'='+obj[key];
	var page = (typeof pagenum == 'undefined') ? 1 : pagenum;
	
	var category="";
	var categorySecond = $("#categorySecondSelect").val();
	if(categorySecond != ""){
		category = categorySecond;
	}else{
		var categoryFirst = $("#categoryFirstSelect").val();
		if(categoryFirst != ""){
			category = categoryFirst;
		}
	}
	
	var d = dialog({
	    title: '系统提示',
		width : 220,
		height:60,
	    content: '<p style="line-height:50px;">加载中...</p>'
	});
	d.showModal();
	$.getJSON('../front/admin/videoPublish!execute?uid=h0007&province='+province+'&component='+component+'&category='+category+str,function(r) {
		if(r.status == 'success') {
			$('#status_checked').html(r.data.checked);
			$('#status_unchecked').html(r.data.unchecked);
		}
	});
	var filterStatus= $("#statusChecked").val();
	$.getJSON('../front/admin/videoPublish!execute?uid=h0001&pagesize=10&pagenum='+page+'&status='+filterStatus+'&component='+component+'&province='+province+str+'&category='+category, function(r) {
		r.totalPageCount && $('.quepager').html('<span style="font-weight:normal">'+ r.pageNum +'</span>/'+ r.totalPageCount);

		if(r.status == 'success' && r.data.length > 0) {
		    var template = $.templates('#queboxTpl');
		    var html = template.render(r.data);
		    $('#queboxCnt').html(html);
		    
		    $(".btn-edit").colorbox({
				iframe : true,
				width : "860px",
				height : "600px",
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
	var province = (url('?province') != null) ? url('?province') : '';
	$.getJSON('../front/admin/province!execute?uid=l0002&id='+province, function(r) {
		if(r.status == 'success') {
			$("#provinceName").html("（"+r.data.name+"）");
		} else{
			window.location.href = "../views/sourceMain.html";
		}
	}).done(function(r){
		init();
	})
	function init() {
		getComponent();
		getFirstCategory();
		getList();
	};
	$(".statusbar a").click(function(){
		$(this).addClass("light").siblings().removeClass("light");
		$("#statusChecked").val($(this).find("span").attr("name"));
		pagenum = '1';
		getList();
	});
})