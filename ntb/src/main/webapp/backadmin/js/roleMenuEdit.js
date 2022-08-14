$(document).ready(function() {
	var role = (url('?role') != null) ? url('?role') : '';
	if(role != ''){
		$('#role').val(role);
		$.get('../rest/backadmin/role/get?uuid='+role,function(r) {
			if(r.status =='SUCCESS') {
				$('#roleName').html(r.data.description);
			} else {
				layer.msg(r.message, {
					time: 2000 
				})
			}
		});
		getList();
	}
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

$('#formsubmit').submit(function() {
	if (flagSubmit == false) {
        return false;
    }
    flagSubmit = false;
    setTimeout(function() {
        flagSubmit = true;
    }, 3000);
	var str = $(this).serialize();
	$.post('../rest/backadmin/roleMenu/edit',str, function(data) {
		if (data.status == "SUCCESS") {
			layer.msg('修改成功', {
				time: 1000 
			}, function(){
				window.location.href = "roleMenuList.jsp";
			}); 
		} else {
			layer.msg(data.message, {
				time: 2000 
			})
		}
	}) 
	
	return false;
});
function getList(){
	$.get('../rest/backadmin/menu/all',function(r) {
		if(r.status =='SUCCESS') {
			if(r.totalResultCount > 0){
				var leftHtml='';
				var rightHtml='';
				$.each(r.data,function(i,v){
					leftHtml += '<li data-id="'+v.uuid+'"';
					if(i==0){
						leftHtml += ' class="selected"';
					}
					leftHtml +='><div><input class="hidden" type="checkbox" id="'+v.uuid+'" name="menu" value="'+v.uuid+'" onchange="selectChildren(this)"/><label class="checkbox-img" for="'+v.uuid+'"></label><span>'+v.title+'</span></div></li>';
					rightHtml +='<div id="children_' + v.uuid + '" ';
					if(i!=0){
						rightHtml += ' class="hidden"';
					}
					rightHtml +='><ul>';
					$.each(v.children,function(j,w){
						rightHtml +='<li><div><input class="hidden" type="checkbox" id="'+w.uuid+'" parent ="'+w.pid+'" name="menu" value="'+w.uuid+'"/><label class="checkbox-img" for="'+w.uuid+'"></label><span>'+w.title+'</span></div></li>'
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
			}else {
				layer.msg(r.message, {
					time: 2000 
				})
			}
		}
	}).done(function(){
		var role = (url('?role') != null) ? url('?role') : '';
		$.get('../rest/backadmin/roleMenu/list?role='+role,function(ret) {
			if(ret.status =='SUCCESS') {
				if(ret.totalResultCount > 0){
					$.each(ret.data,function(i,v){
						$('#'+v.menu).prop("checked", true);
						$.each(v.children,function(j,w){
							$('#'+w.menu).prop("checked", true);
						})
					})
				}
			}
		})
	})
	
}