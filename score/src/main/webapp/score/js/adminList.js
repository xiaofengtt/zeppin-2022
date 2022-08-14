var pageNum = 1,
    pageSize = 20;
var flagSubmit = true;

$(function(){
	getList();
});

//获取列表
function getList(){
	$.ajax({
        url: '../back/admin/list',
        type: 'get',
        async:false,
        data: {
        	"pageSize": pageSize,
        	"pageNum": pageNum
        }
    }).done(function(r) {
    		if (r.status == "SUCCESS") {
    			var tableHtml = '<tr>'+
								'<th width="20%">账号</th>'+
								'<th width="25%">真实名称</th>'+
								'<th width="25%">角色</th>'+
								'<th width="10%">状态</th>'+
								'<th width="20%" class="text-right">操作</th>'+
							'</tr>';
    			for(var i=0;i<r.data.length;i++){
    				var status = "";//状态
    				var role = "";
    				if(r.data[i].status=="normal"){
    					status = "正常";
    				}else if(r.data[i].status=="disable"){
    					status = "停用";
    				}
    				if(r.data[i].role=="002170ff-082d-412f-a8a4-b021183fa365"){
    					role = "管理员";
    				}else if(r.data[i].role=="002170ff-082d-412f-a8a4-b021183fa332"){
    					role = "审核人";
    				}else if(r.data[i].role=="002170ff-082d-412f-a8a4-b021183fa311"){
    					role = "编辑";
    				}
    				tableHtml += '<tr>'
    				+'<td><b>'+r.data[i].username+'</b></td>'
    				+'<td>'+r.data[i].realname+'</td>'
    				+'<td>'+role+'</td>'
    				+'<td>'+status+'</td>'
    				+'<td class="text-right" data-id="'+r.data[i].uuid+'"><a class="" href="adminDetail.html?uuid='+r.data[i].uuid+'">修改</a></td>'
    				+'</tr>';
    			}
                $('#pageTool').Paging({
                    prevTpl: "<",
                    nextTpl: ">",
                    pagesize: pageSize,
                    count: r.totalResultCount,
                    current: pageNum,
                    toolbar: true,
                    pageSizeList: [10, 20, 50],
                    callback: function(page, size, count) {
                        pageNum = page;
                        getList(parentid);
                        flag = false;
                        document.body.scrollTop = document.documentElement.scrollTop = 0;
                    }
                });
                $("#pageTool").show().find(".ui-paging-container:last").siblings().remove();
                
                $(".ui-select-pagesize").unbind("change").change(function() {
                    pageSize = $(this).val();
                    pageNum = 1;
                    getList(parentid);
                });
                if(r.data.length==0){
    				tableHtml += '<tr><td colspan="7" align="center">暂无相关数据</td></tr>';
    			}
    			$(".table-list").html(tableHtml);
    			allCheck();
    			$(".deleteBtn").click(function(){
    				deleteList($(this).parent().attr("data-id"));
    				return false;
    			});
    			$(".disableBtn").click(function(){
    				disableList($(this).parent().attr("data-id"));
    				return false;
    			});
    			$(".table-list tr").click(function(){
    				var dataUrl = $(this).attr("data-url");
    				if(dataUrl){
    					window.location.href = dataUrl;
    				}
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
    }).fail(function(r) {
        layer.msg("error", {
            time: 2000
        },function(){
        	
        });
    });   	
}

//禁用
function disableList(listid){
	var disableUuid = "";		
	if(listid){
		disableUuid = listid;
	}else{
		$(".check.light").each(function(){
			disableUuid += $(this).val() + ",";
		});
		if(disableUuid==""){
			layer.msg("请选择要禁用的管理员");
			return false;
		}else{
			disableUuid = disableUuid.substring(0,disableUuid.length-1);
		}			
	}		
	layer.confirm('确定要禁用吗？', {
		btn: ['确定','取消'] //按钮
	}, function(){
		if(flagSubmit == false) {
			return false;
		}
		flagSubmit = false;
		setTimeout(function() {
			flagSubmit = true;
		}, 3000);
		$.ajax({
	        url: '../back/admin/changeStatus',
	        type: 'post',
	        async:false,
	        data: {
	           "uuid":disableUuid,
	           "status":"disable"
	        }
	    }).done(function(r) {
    		if (r.status == "SUCCESS") {
    			layer.msg("禁用成功！", {
		            time: 2000
		        },function(){
		        	getList(parentid);
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
	    }).fail(function(r) {
	    	layer.msg("error", {
		            time: 2000
		        },function(){
		        	window.location.href=window.location.href;
		        });
	    });   
	}, function(){
		layer.closeAll();
	});
}