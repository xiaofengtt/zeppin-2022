/**
 * 
 */
var flagSubmit = true;
var uuid = (url('?uuid') != null) ? url('?uuid') : '';
$(function(){
	if(baseRoleArr[uuid]){
		$(".content-head p").html(baseRoleArr[uuid]+"page permission setting");
	}
	getList();
});


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

$('.submit-button').click(function() {
	var menu = [];
	$("input[name='menu']").each(function(){
		if($(this).is(":checked")){
			menu.push($(this).val());
			if($(this).attr('parent')){
				if($("[data-id='"+$(this).attr('parent')+"']").is(":checked")){
					
				}else{
					menu.push($(this).attr('parent'));
				}
			}
			
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
        url: '../system/roleMenu/edit',
        type: 'post',
        async:false,
        traditional: true,
        data: {
        	"role":uuid,
        	"menu":uniq(menu)
        }
    }).done(function(data) {
		if (data.status == "SUCCESS") {
			layer.msg('Successful', {
				time: 1000 
			}, function(){
				window.location.href = "roleMenuList.html";
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
function getList(){
	$.get('../system/roleMenu/all',function(r) {
		if(r.status =='SUCCESS') {
			if(r.totalResultCount > 0){
				var leftHtml='';
				var rightHtml='';
				$.each(r.data,function(i,v){
					leftHtml += '<li data-id="'+v.uuid+'"';
					if(i==0){
						leftHtml += ' class="selected"';
					}
					leftHtml +='><div><input class="hidden" type="checkbox" id="'+v.uuid+'" name="menu" value="'+v.uuid+'" onchange="selectChildren(this)"/><label class="checkbox-img" for="'+v.uuid+'"></label><span>'+v.name+'</span></div></li>';
					rightHtml +='<div id="children_' + v.uuid + '" ';
					if(i!=0){
						rightHtml += ' class="hidden"';
					}
					rightHtml +='><ul>';
					$.each(v.children,function(j,w){
						rightHtml +='<li><div><input class="hidden" type="checkbox" id="'+w.uuid+'" parent ="'+w.parent+'" name="menu" value="'+w.uuid+'"/><label class="checkbox-img" for="'+w.uuid+'"></label><span>'+w.name+'</span></div></li>'
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
		$.get('../system/roleMenu/list?role='+uuid,function(ret) {
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


