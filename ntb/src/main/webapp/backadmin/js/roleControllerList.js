$(document).ready(function() {
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
				$('#contain-all').removeClass('hidden');
				$('#nodata').addClass('hidden');
				$('.roleButton').removeClass("role-selected");
				$(this).addClass("role-selected");
				$('#roleName').html($(this).html());
				$('#role').val($(this).attr("id"));
				getList();
			})
		} else {
			layer.msg(r.message, {
				time: 2000 
			})
		}
	})
})

function selectAll(){
	$('.main-contain .contain-body input[type="checkbox"]').prop("checked", true);
}

function selectChildren(obj){
	var uuid = $(obj).attr("id")
	if($(obj).is(":checked")){
		$("[parent='"+uuid+"']").prop("checked", true);
	}else{
		$("[parent='"+uuid+"']").prop("checked", false);
	}
}

function getList(){
	var role = $(".role-selected").attr("id");
	if(role != ''){
		$.get('../rest/backadmin/controllerMethod/all',function(r) {
			if(r.status =='SUCCESS') {
				var leftHtml='';
				var rightHtml='';
				$.each(r.data,function(i,v){
					leftHtml += '<li data-id="'+v.uuid+'"';
					if(i==0){
						leftHtml += ' class="selected"';
					}
					leftHtml +='><div><input class="hidden" type="checkbox" id="'+v.uuid+'" name="controller" value="'+v.uuid+'" onchange="selectChildren(this)"/><label class="checkbox-img" for="'+v.uuid+'"></label><span>'+v.description+'</span></div></li>';
					rightHtml +='<div id="children_' + v.uuid + '" ';
					if(i!=0){
						rightHtml += ' class="hidden"';
					}
					rightHtml +='><ul>';
					$.each(v.children,function(j,w){
						rightHtml +='<li><div><input class="hidden" type="checkbox" id="'+w.uuid+'" parent ="'+w.controller+'" name="method" value="'+w.uuid+'"/><label class="checkbox-img" for="'+w.uuid+'"></label><span>'+w.description+'</span></div></li>'
					})
					rightHtml +='</ul></div>';
				})
				$('#left-ul').html(leftHtml);
				$('#contain-body-right').html(rightHtml);
				$('#left-ul li').click(function(){
					$('#left-ul li').removeClass("selected");
					$(this).addClass("selected");
					var data_id = $(this).attr("data-id");
					$('#contain-body-right>div').addClass("hidden");
					$('#children_'+data_id).removeClass("hidden");
				})
			} else {
				layer.msg(r.message, {
					time: 2000 
				})
			}
		}).done(function(r){
			if(r.totalResultCount > 0){
				$.get('../rest/backadmin/roleMethod/list?role='+role,function(ret) {
					if(ret.status =='SUCCESS') {
						if(ret.totalResultCount > 0){
							$.each(ret.data,function(i,v){
								$('#'+v.method).prop("checked", true);
								$('#'+v.controller).prop("checked", true);
							})
						}
					}
				})
			}
		})
	}else{
		$('#contain-all').addClass('hidden');
		$('#nodata').removeClass('hidden');
	}
}

$('#formsubmit').submit(function() {
	var str = $(this).serialize();
	$.post('../rest/backadmin/roleMethod/edit',str, function(data) {
		if (data.status == "SUCCESS") {
			layer.msg('修改成功', {
				time: 1000 
			}, function(){
				location.reload();
			}); 
		} else {
			layer.msg(data.message, {
				time: 2000 
			})
		}
	}) 
	return false;
});