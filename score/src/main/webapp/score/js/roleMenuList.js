/**
 * 
 */
var roleList = "";//角色列表
var roleMenuHtml = "";
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
			roleList = '<tr><th width="15%">角色名</th><th width="85%">角色权限</th><th width="10%" class="text-right">操作</th></tr>';
			for(var i=0;i<r.data.length;i++){
				getRoleMenuList(r.data[i].uuid);
				roleList += '<tr><td>'+baseRoleArr[r.data[i].name]+
				'</td><td>'+roleMenuHtml+
				'</td><td class="text-right"><a href="roleMenuEdit.html?uuid='+r.data[i].uuid+'&role='+r.data[i].name+'">编辑</a></td></tr>';
			}
			$(".content-inner").html("<table class='table-list'>"+roleList+"</table>");
			
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

//获取角色菜单列表
function getRoleMenuList(id){
	$.ajax({
        url: '../back/roleMenu/list',
        type: 'get',
        async:false,
        data: {
        	"role":id
        }
    }).done(function(r) {
		if (r.status == "SUCCESS") {
			roleMenuHtml = "";
			
			for(var i=0;i<r.data.length;i++){
				roleMenuHtml += "<p>"+r.data[i].name+"（";
				for(var j=0;j<r.data[i].children.length;j++){
					roleMenuHtml += r.data[i].children[j].name+"、";
				}
				if(r.data[i].children.length>0){
					roleMenuHtml =roleMenuHtml.substring(0,roleMenuHtml.length-1)+"）</p>";
				}else{
					roleMenuHtml +="）</p>";
				}
			}
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







