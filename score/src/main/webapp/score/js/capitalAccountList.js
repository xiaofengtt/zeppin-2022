var uuid = (url('?uuid') != null) ? url('?uuid') : '';
var pageNum = 1,
    pageSize = 20;
var flagSubmit = true;
$(function(){
	getList();
});


//获取列表
function getList(){
	$.ajax({
        url: '../back/capitalAccount/list',
        type: 'get',
        async:false,
        data: {
        	"capitalPlatform": uuid,
            "pageSize":pageSize,
            "pageNum":pageNum
        }
    }).done(function(r) {
    		if (r.status == "SUCCESS") {
    			var tableHtml = '<tr>'+
								'<th width="15%">渠道名称</th>'+
								'<th width="10%">名称</th>'+
								'<th width="15%">账号</th>'+
								'<th width="8%">每日限额</th>'+
								'<th width="8%">当日累计</th>'+
								'<th width="9%">历史累计</th>'+
								'<th width="5%" class="text-center">排序</th>'+
								'<th width="5%" class="text-center">状态</th>'+
								'<th width="15%">创建时间</th>'+
								'<th width="10%" class="text-right">操作</th>'+
							'</tr>';
    			for(var i=0;i<r.data.length;i++){
    				var status = "";//状态
    				if(r.data[i].status=="normal"){
    					status = "正常";
    				}else if(r.data[i].status=="disable"){
    					status = "停用";
    				}else if(r.data[i].status=="delete"){
    					status = "已删除";
    				}
    				
    				tableHtml += '<tr data-url="capitalAccountDetail.html?uuid='+r.data[i].uuid+'">'+
    				'<td>'+r.data[i].capitalPlatformName+'</td>'+
    				'<td>'+r.data[i].name+'</td>'+
    				'<td class="bankAccounts">'+r.data[i].accountNum+'</td>'+
    				'<td>'+moneyFormat(r.data[i].dailyMax)+'</td>'+
    				'<td>'+moneyFormat(r.data[i].dailySum)+'</td>'+
    				'<td>'+moneyFormat(r.data[i].balance)+'</td>'+
    				'<td class="text-center">'+r.data[i].sort+'</td>'+
    				'<td class="text-center">'+status+'</td>'+
    				'<td>'+formatDate(r.data[i].createtime)+'</td>'+
    				'<td class="text-right" data-id="'+r.data[i].uuid+'">'+
    				'<a href="capitalAccountDetail.html?uuid='+r.data[i].uuid+'">修改</a> <a class="disableBtn ml-5">禁用</a></td></tr>'; 
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
    				tableHtml += '<tr><td colspan="8" align="center">暂无相关数据</td></tr>';
    			}
    			$(".table-list").html(tableHtml);
    			allCheck();
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

function addOne(){
	location.href = "capitalAccountDetail.html?capitalPlatform="+uuid
}


//批量禁用
function disableList(listid){
	var disableUuid = "";		
	if(listid){
		disableUuid = listid;
	}else{
		$(".check.light").each(function(){
			disableUuid += $(this).val() + ",";
		});
		if(disableUuid==""){
			layer.msg("请选择要禁用的渠道");
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
	        url: '../back/capitalAccount/changeStatus',
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
		        	getList();
		        	getStatusNumber();
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