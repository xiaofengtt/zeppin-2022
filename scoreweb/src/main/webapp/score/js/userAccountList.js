var pageNum = 1,
    pageSize = 10;
var flagSubmit = true;

$(function(){
	getList();
});
$(".search-input").on('input propertychange',inputValue);
function inputValue(obj){
	getList();
}
//获取列表
function getList(){
	$.ajax({
        url: '../back/user/list',
        type: 'get',
        async:false,
        data: {
        	"mobile":$(".search-input").val().replace(/(^\s*)|(\s*$)/g, ""),
        	"pageSize": pageSize,
        	"pageNum": pageNum
        }
    }).done(function(r) {
    		if (r.status == "SUCCESS") {
    			var tableHtml = '<tr>'+
								'<th width="20%">手机号</th>'+
								'<th width="20%">用户名</th>'+
								'<th width="20%">可提现余额</th>'+
								'<th width="20%">不可提现余额</th>'+
								'<th width="10%">状态</th>'+
								'<th width="10%">操作</th>'+
							'</tr>';
    			for(var i=0;i<r.data.length;i++){
    				var status = "";//状态
    				var role = "";
    				if(r.data[i].status=="normal"){
    					status = "正常";
    				}else if(r.data[i].status=="disable"){
    					status = "停用";
    				}
    				tableHtml += '<tr>'
    				+'<td><b>'+r.data[i].mobile+'</b></td>'
    				+'<td>'+(r.data[i].realname == null ? "" : r.data[i].realname) +'</td>'
    				+'<td>'+moneyFormat(r.data[i].balanceFree)+'</td>'
    				+'<td>'+moneyFormat(r.data[i].balanceLock)+'</td>'
    				+'<td>'+status+'</td>'
    				+'<td data-id="'+r.data[i].uuid+'"><a class="mr-5" href="userAccountRecharge.html?uuid='+r.data[i].uuid+'">充值</a></td>'
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
			layer.msg("请选择要禁用的用户");
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
	        url: '../back/user/changeStatus',
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