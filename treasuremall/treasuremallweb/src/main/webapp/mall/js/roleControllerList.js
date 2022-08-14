/**
 * 
 */

var roleList = "";//角色列表
var flagSubmit = true;
$(function(){
	getRoleList();
})
//获取角色列表
function getRoleList(){
	$.ajax({
        url: '../back/role/all',
        type: 'get',
        async:false,
        data: {
        	
        }
    }).done(function(r) {
		if (r.status == "SUCCESS") {
			for(var i=0;i<r.data.length;i++){
				roleList += '<li data-id="'+r.data[i].uuid+'" >'+baseRoleArr[r.data[i].name]+'</li>';
			}
			$(".role-list").html(roleList+"<div class='clear'></div>");
			$(".role-list li").click(function(){
				$('#contain-all').removeClass('hidden');
				$('.nodata').addClass('hidden');
				$(this).addClass("role-selected").siblings().removeClass("role-selected");
				$('#roleName').html($(this).html());
				$('#role').val($(this).attr("data-id"));
				getList();
			});
			
		}else {
			if(r.errorCode=="302"){
				layer.msg(r.message, {
		            time: 2000
		        },function(){
		        	window.location.href="login.html";
		        });
			}else{
				layer.msg(r.message);
			}
    		
		}
    }).fail(function(r){
    	layer.msg("error", {
            time: 2000
        },function(){
        	
        });
    });
}

function selectAll(){
	$('.contain-body input[type="checkbox"]').prop("checked", true);
}

function selectChildren(obj){
	var uuid = $(obj).attr("id")
	if($(obj).is(":checked")){
		$("[parent='"+uuid+"']").prop("checked", true);
	}else{
		$("[parent='"+uuid+"']").prop("checked", false);
	}
}
function selectLastChildren(obj){
	var uuid = $(obj).attr("id")
	if($(obj).is(":checked")){
		$("[parents='"+uuid+"']").prop("checked", true);
	}else{
		$("[parents='"+uuid+"']").prop("checked", false);
	}
}

function getList(){
	var role = $(".role-selected").attr("data-id");
	if(role != ''){
		$.get('../back/roleMethod/all',function(r) {
			if(r.status =='SUCCESS') {
				var leftHtml='';
				var rightHtml='';
				var lastHtml = '';
				$.each(r.data,function(i,v){
					leftHtml += '<li data-id="'+v.uuid+'"';
					if(i==0){
						leftHtml += ' class="selected"';
					}
					leftHtml +='><div><input class="hidden" type="checkbox" id="'+v.uuid+'" name="method" value="'+v.uuid+'" onchange="selectChildren(this)"/><label class="checkbox-img" for="'+v.uuid+'"></label><span>'+v.name+'</span></div></li>';
					rightHtml +='<div id="children_' + v.uuid + '" ';
					if(i!=0){
						rightHtml += ' class="hidden"';
					}
					rightHtml +='><ul>';
					$.each(v.children,function(j,w){
						rightHtml +='<li data-id="'+w.uuid+'"><div><input class="hidden" type="checkbox" id="'+w.uuid+'" parent ="'+w.parent+'" name="method" value="'+w.uuid+'" onchange="selectLastChildren(this)"/><label class="checkbox-img" for="'+w.uuid+'"></label><span>'+w.name+'</span></div></li>'
						lastHtml +='<div id="last_' + w.uuid + '" ';
						if(j!=0){
							lastHtml += ' class="hidden"';
						}
						lastHtml +='><ul>';
						$.each(w.children,function(l,m){
							lastHtml +='<li><div><input class="hidden" type="checkbox" id="'+m.uuid+'" parents="'+m.parent+'"  parent ="'+w.parent+'" name="method" value="'+m.uuid+'"/><label class="checkbox-img" for="'+m.uuid+'"></label><span>'+m.name+'</span></div></li>'
						});
						lastHtml +='</ul></div>';
					});
					rightHtml +='</ul></div>';
				})
				$('#left-ul').html(leftHtml);
				$('#contain-body-right').html(rightHtml);
				$('#contain-body-last').html(lastHtml);
				$('#left-ul li').click(function(){
					$('#left-ul li').removeClass("selected");
					$(this).addClass("selected");
					var data_id = $(this).attr("data-id");
					$('#contain-body-right>div').addClass("hidden");
					$('#children_'+data_id).removeClass("hidden");
				});
				$("#contain-body-right li").click(function(){
					$(this).addClass("selected").siblings().removeClass("selected");
					var data_id = $(this).attr("data-id");
					$('#contain-body-last>div').addClass("hidden");
					$('#last_'+data_id).removeClass("hidden");
				});
			} else {
				layer.msg(r.message, {
					time: 2000 
				})
			}
		}).done(function(r){
			if(r.totalResultCount > 0){
				$.get('../back/roleMethod/list?role='+role,function(ret) {
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

$('.submit-button').click(function() {
	var menu = [];
	$("input[name='method']").each(function(){
		if($(this).is(":checked")){
			menu.push($(this).val());
//			if($(this).attr('parent')){
//				if($("[data-id='"+$(this).attr('parent')+"']").is(":checked")){
//					
//				}else{
//					menu.push($(this).attr('parent'));
//				}
//			}
		}
	});
	if (flagSubmit == false) {
        return false;
    }
    flagSubmit = false;
    setTimeout(function() {
        flagSubmit = true;
    }, 3000);
	$.ajax({
        url: '../back/roleMethod/edit',
        type: 'post',
        async:false,
        traditional: true,
        data: {
        	"role":$('#role').val(),
        	"method":uniq(menu)
        }
    }).done(function(data) {
		if (data.status == "SUCCESS") {
			layer.msg('修改成功', {
				time: 1000 
			}, function(){
				location.reload();
			}); 
		} else {
			if(r.errorCode=="302"){
				layer.msg(r.message, {
		            time: 2000
		        },function(){
		        	window.location.href="login.html";
		        });
			}else{
				layer.msg(r.message);
			}
		}
	}).fail(function(r){
    	layer.msg("error", {
            time: 2000
        },function(){
        	
        });
    }); 
	
	return false;
});
layui.use(['form', 'element'], function(){
	var form = layui.form
	,layer = layui.layer
	,$ = layui.jquery
	,element = layui.element;
})