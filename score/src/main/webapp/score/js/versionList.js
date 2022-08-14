var pageNum = 1,
    pageSize = 20;
var flagSubmit = true;
var flagArray = {
                 'true':'是',
                 'false':'否'
				}
var statusArray = {
		'normal':'正常',
		'disable':'不可用',
		'delete':'已删除'
}
$(function(){
	getList();
});

//获取列表
function getList(){
	$.ajax({
        url: '../back/version/list',
        type: 'get',
        async:false,
        data: {
            "pageSize":pageSize,
            "pageNum":pageNum
        }
    }).done(function(r) {
    		if (r.status == "SUCCESS") {
    			var tableHtml = '<tr>'+
								'<th width="9%">类型</th>'+
								'<th width="7%">包名</th>'+
								'<th width="7%">应用名称</th>'+
								'<th width="7%" class="text-center">版本名称</th>'+
								'<th width="7%">渠道号</th>'+
								'<th width="18%">主包地址</th>'+
								'<th width="17%">下载地址</th>'+
								'<th width="4%" class="text-center">开关状态</th>'+
								'<th width="6%" class="text-center">版本状态</th>'+
								'<th width="4%" class="text-center">是否更新</th>'+
								'<th width="5%" class="text-center">是否强制更新</th>'+
								'<th width="9%">操作</th>'+
							'</tr>';
    			for(var i=0;i<r.data.length;i++){
    				var opteration = "";//操作按钮
    				if(r.data[i].status!="delete"){
    					opteration = '<a class="mr-5" href="versionDetail.html?uuid='+r.data[i].uuid+'">编辑</a><a class="deleteBtn">删除</a>';
    				}else if(r.data[i].status=="publish"){
    					opteration = '';
    				}
    				tableHtml += '<tr>'+
    					'<td class="status-'+r.data[i].status+'"><b>'+r.data[i].type+'</b></td>'+
    					'<td>'+r.data[i].bundleid+'</td>'+
    					'<td>'+r.data[i].displayname+'</td>'+
    					'<td class="text-center">'+r.data[i].name+'</td>'+
    					'<td>'+r.data[i].channel+'</td>'+
    					'<td>'+r.data[i].adverturl+'</td>'+
    					'<td>'+r.data[i].downloadurl+'</td>'+
    					'<td class="text-center">'+flagArray[r.data[i].flag]+'</td>'+
    					'<td class="text-center">'+statusArray[r.data[i].status]+'</td>'+
    					'<td class="text-center">'+flagArray[r.data[i].flagupdate]+'</td>'+
    					'<td class="text-center">'+flagArray[r.data[i].flagcompel]+'</td>'+
    					'<td data-id="'+r.data[i].uuid+'">'+opteration+'</td>'+
    					'</tr>'; 
    			}
    			if (r.totalPageCount!=0) {
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
                            getList();
                            flag = false;
                            document.body.scrollTop = document.documentElement.scrollTop = 0;
                        }
                    });
                    $("#pageTool").show().find(".ui-paging-container:last").siblings().remove();
                }else{
                	$("#pageTool").hide();
                	
                }
                $(".ui-select-pagesize").unbind("change").change(function() {
                    pageSize = $(this).val();
                    pageNum = 1;
                    getList();
                });
    			if(r.data.length==0){
    				tableHtml += '<tr><td colspan="12" align="center">暂无相关数据</td></tr>';
    			}
    			$(".table-list").html(tableHtml);
    			$(".deleteBtn").click(function(){
    				deleteGuessing($(this).parent().attr("data-id"));
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
function deleteGuessing(uuid){
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
	        url: '../back/version/delete',
	        type: 'GET',
	        async:false,
	        data: {
	           "uuid":uuid
	        }
	    }).done(function(r) {
    		if (r.status == "SUCCESS") {
    			layer.msg("删除成功！", {
		            time: 2000
		        },function(){
		        	getList();
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