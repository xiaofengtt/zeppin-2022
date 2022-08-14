var pageNum = 1,
    pageSize = 10;
var flagSubmit = true;
$(function(){
	getList();
});


//获取列表
function getList(){
	$.ajax({
        url: '../back/capitalPlatform/list',
        type: 'get',
        async:false,
        data: {
            "pageSize":pageSize,
            "pageNum":pageNum
        }
    }).done(function(r) {
    		if (r.status == "SUCCESS") {
    			var tableHtml = '<tr>'+
								'<th width="20%">名称</th>'+
								'<th width="10%">类别</th>'+
								'<th width="5%">排序</th>'+
								'<th width="25%">备注</th>'+
								'<th width="5%">状态</th>'+
								'<th width="15%">创建时间</th>'+
								'<th width="15%">操作</th>'+
								'<th width="5%" class="align-right"><input type="checkbox" id="allCheck" name="" value=""></th>'+
							'</tr>';
    			for(var i=0;i<r.data.length;i++){
    				var status = "";//状态
    				var type = "";//类型
    				if(r.data[i].status=="normal"){
    					status = "正常";
    				}else if(r.data[i].status=="disable"){
    					status = "停用";
    				}else if(r.data[i].status=="delete"){
    					status = "已删除";
    				}
    				switch (r.data[i].type){
    				case "company_bankcard":
    					type = "企业银行卡"
    					break
    				case "company_alipay":
    					type = "企业支付宝"
    					break
    				case "personal_bankcard":
    					type = "个人银行卡"
    					break
    				case "personal_alipay":
    					type = "个人支付宝"
    					break
    				case "wechat":
    					type = "微信"
    					break
    				}
    				
    				tableHtml += '<tr data-url="capitalPlatformDetail.html?uuid='+r.data[i].uuid+'">'+
    				'<td>'+r.data[i].name+'</td>'+
    				'<td>'+type+'</td>'+
    				'<td>'+r.data[i].sort+'</td>'+
    				'<td>'+r.data[i].remark+'</td>'+
    				'<td>'+status+'</td>'+
    				'<td>'+formatDate(r.data[i].createtime)+'</td>'+
    				'<td data-id="'+r.data[i].uuid+'">'+
    				'<a href="capitalAccountList.html?uuid='+r.data[i].uuid+'">管理账户</a> '+
    				'<a href="capitalPlatformDetail.html?uuid='+r.data[i].uuid+'">修改</a> <a class="disableBtn mr-5">禁用</a></td><td class="align-right"> <input class="check" type="checkbox" name="" value="'+r.data[i].uuid+'">'+
    				'</td></tr>'; 
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
	        url: '../back/capitalPlatform/changeStatus',
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