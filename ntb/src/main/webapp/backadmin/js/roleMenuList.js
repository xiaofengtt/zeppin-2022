$(document).ready(function() {
	var html = '<div class="nodata">请选择角色！</div>'
	$('#queboxCnt').html(html);
	$.get('../rest/backadmin/role/list',function(r) {
		if(r.status =='SUCCESS') {
			var html = '';
			if(r.data.length>0){
				$.each(r.data,function(i,v){
					html += '<button class="roleButton" id="'+v.uuid+'">'+v.description+'</button>';
				})
			}
			$('#roleSelect').html(html);
			$('.roleButton').click(function(){
				$('.roleButton').removeClass("role-selected");
				$(this).addClass("role-selected");
				getList();
			})
		} else {
			layer.msg(r.message, {
				time: 2000 
			})
		}
	})
})

function pSortUp(obj){
	var $tr = $(obj).parents("table");
	if ($tr.index() != 0) {
		var uuid = $(obj).attr('data-uuid');
		$.get('../rest/backadmin/roleMenu/sort?type=up&uuid='+uuid,function(r) {
			layer.msg(r.message, {
				time: 2000 
			})
		}).done(function(){
			getList();
		})
	}
}

function pSortDown(obj){
	var tableLength = $("#queboxCnt").find("table").length;
	var $table = $(obj).parents("table"); 
	if ($table.index() != tableLength - 1) {
		var uuid = $(obj).attr('data-uuid');
		$.get('../rest/backadmin/roleMenu/sort?type=down&uuid='+uuid,function(r) {
			layer.msg(r.message, {
				time: 2000 
			})
		}).done(function(){
			getList();
		})
	}
}

function cSortUp(obj){
	var $tr = $(obj).parents("tr");
	if ($tr.index() != 1) {
		var uuid = $(obj).attr('data-uuid');
		$.get('../rest/backadmin/roleMenu/sort?type=up&uuid='+uuid,function(r) {
			layer.msg(r.message, {
				time: 2000 
			})
		}).done(function(){
			getList();
		})
	}
}

function cSortDown(obj){
	var table = $(obj).closest("table");
	var trLength = table.find("tr").length
	var $tr = $(obj).parents("tr"); 
	if ($tr.index() != trLength - 1) {
		var uuid = $(obj).attr('data-uuid');
		$.get('../rest/backadmin/roleMenu/sort?type=down&uuid='+uuid,function(r) {
			layer.msg(r.message, {
				time: 2000 
			})
		}).done(function(){
			getList();
		})
	}
}

function getList(){
	var role = $(".role-selected").attr("id");
	if(role != undefined){
		$.get('../rest/backadmin/roleMenu/list?role='+role,function(r) {
			if(r.status =='SUCCESS') {
				if(r.totalResultCount > 0){
					var template = $.templates('#queboxTpl');
					var html = template.render(r.data);
					$('#queboxCnt').html(html);
					$('#queboxCnt .menu-table:first .menu-table-head .sortup').addClass("sortup-disable");
					$('#queboxCnt .menu-table:last .menu-table-head .sortdown').addClass("sortdown-disable");
					var tableLength =$('#queboxCnt .menu-table').length;
					for(var i=1; i<=tableLength;i++){
						$('#menu-table-'+i+' .menu-table-row:first .sortup').addClass("sortup-disable");
						$('#menu-table-'+i+' .menu-table-row:last .sortdown').addClass("sortdown-disable");
					}
				}else{
					var html = '<div class="nodata">没有数据！</div>'
					$('#queboxCnt').html(html);
				}
			} else {
				layer.msg(r.message, {
					time: 2000 
				})
			}
		}).done(function(){
			$('#editButton').attr("href","../backadmin/roleMenuEdit.jsp?role="+role)
		})
	}else{
		var html = '<div class="nodata">请选择角色！</div>'
		$('#queboxCnt').html(html);
		$('#editButton').attr("href","#")
	}
}