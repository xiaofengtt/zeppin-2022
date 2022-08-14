var pageNum = 1,
    pageSize = 20;
var parentPageNum = 1;
var categroyArr = [];
var flagSubmit = true;
var football = '0ae36f97-90b4-11e8-95fb-fcaa14314cbe';
var parentid = '0ae36f97-90b4-11e8-95fb-fcaa14314cbe';
$(function(){
	$(".screen-box").hide();
	getList(football);
});

//返回上一级
function getParent(){
	$(".screen-box").hide();
	pageNum = parentPageNum;
	getList(football);
}

//返回上一级
function getChildren(parent){
	$(".screen-box").show();
	parentPageNum = pageNum;
	pageNum = 1;
	getList(parent);
}

//获取列表
function getList(parent){
	parentid = parent;
	$.ajax({
        url: '../back/category/list',
        type: 'get',
        async:false,
        data: {
        	"pageSize": pageSize,
        	"pageNum": pageNum,
        	"parent": parent
        }
    }).done(function(r) {
    		if (r.status == "SUCCESS") {
    			var tableHtml = '<tr>'+
								'<th width="25%">名称</th>'+
								'<th width="25%">英文名称</th>'+
								'<th width="15%">下级</th>'+
								'<th width="15%">是否作为标签</th>'+
								'<th width="5%">状态</th>'+
								'<th width="15%" class="text-right">操作</th>'+
							'</tr>';
    			for(var i=0;i<r.data.length;i++){
    				var status = "";//状态
    				if(r.data[i].status=="normal"){
    					status = "正常";
    				}else if(r.data[i].status=="disable"){
    					status = "停用";
    				}
    				tableHtml += '<tr>'
    				+'<td><b>'+r.data[i].name+'</b></td>'
    				+'<td>'+r.data[i].shortname+'</td>';
    				if(r.data[i].level==2){
    					tableHtml = tableHtml+'<td><a onclick="getChildren(\'' +r.data[i].uuid+ '\')">查看下级列表</a></td>';
    				}else{
    					tableHtml = tableHtml+'<td>无</td>';
    				}
    				if(r.data[i].istag){
    					tableHtml = tableHtml+'<td>是</td>';
    				}else{
    					tableHtml = tableHtml+'<td>否</td>';
    				}
    				tableHtml = tableHtml+'<td>'+status+'</td>'
    				+'<td class="text-right" data-id="'+r.data[i].uuid+'"><a class="mr-5" href="categoryDetail.html?uuid='+r.data[i].uuid+'">修改</a><a class="disableBtn mr-5">禁用</a><a class="deleteBtn">删除</a></td>'
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

//删除
function deleteList(listid){
	var uuid = "";		
	if(listid){
		uuid = listid;
	}else{
		$(".check.light").each(function(){
			uuid += $(this).val() + ",";
		});
		if(uuid==""){
			layer.msg("请选择要删除的赛事");
			return false;
		}else{
			uuid = uuid.substring(0,uuid.length-1);
		}			
	}		
	layer.confirm('确定要删除吗？', {
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
	        url: '../back/category/delete',
	        type: 'get',
	        async:false,
	        data: {
	           "uuid":uuid
	        }
	    }).done(function(r) {
    		if (r.status == "SUCCESS") {
    			layer.msg("删除成功！", {
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
			layer.msg("请选择要禁用的赛事");
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
	        url: '../back/category/changeStatus',
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