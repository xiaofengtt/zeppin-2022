var pageNum = 1,
    pageSize = 10;
var type = "all";
var flagSubmit = true;

$(function(){
	getPlatformList();
	getList();
	getTypeNumber();
});

//获取列表
function getList(){
	$.ajax({
        url: '../back/capitalAccountHistory/list',
        type: 'get',
        async:false,
        data: {
        	"capitalPlatform":$("#capitalPlatformSelect").val(),
        	"type":type,
        	"pageSize": pageSize,
        	"pageNum": pageNum
        }
    }).done(function(r) {
    		if (r.status == "SUCCESS") {
    			var tableHtml = '<tr>'+
    							'<th width="10%">交易类型</th>'+
								'<th width="10%">手机号</th>'+
								'<th width="10%">用户名</th>'+
								'<th width="10%">交易渠道</th>'+
								'<th width="15%">交易账户</th>'+
								'<th width="10%">交易金额</th>'+
								'<th width="10%">手续费</th>'+
								'<th width="10%">经办人</th>'+
								'<th width="5%">状态</th>'+
								'<th width="10%">操作</th>'+
							'</tr>';
    			for(var i=0;i<r.data.length;i++){
    				var status = "";//状态
    				var dataType = ""
    				var role = "";
    				if(r.data[i].status=="normal"){
    					status = "待处理";
    				}else if(r.data[i].status=="failed"){
    					status = "交易失败";
    				}else if(r.data[i].status=="close"){
    					status = "已关闭";
    				}else if(r.data[i].status=="success"){
    					status = "交易成功";
    				}
    				if(r.data[i].type=="user_recharge"){
    					dataType = "用户充值";
    				}else if(r.data[i].type=="user_withdraw"){
    					dataType = "用户提现";
    				}
    				tableHtml += '<tr>'
    				+'<td><b>'+dataType+'</b></td>'
    				+'<td><b>'+r.data[i].frontUserMobile+'</b></td>'
    				+'<td>'+(r.data[i].frontUserName == null ? "" : r.data[i].frontUserName) +'</td>'
    				+'<td><b>'+r.data[i].capitalPlatformName+'</b></td>'
    				+'<td><b>'+r.data[i].capitalAccountName+'</b></td>'
    				+'<td>'+moneyFormat(r.data[i].income)+'</td>'
    				+'<td>'+moneyFormat(r.data[i].poundage)+'</td>'
    				+'<td>'+(r.data[i].operatorName == null ? "" : r.data[i].operatorName) +'</td>'
    				+'<td>'+status+'</td>'
    				if(r.data[i].status == "normal" || r.data[i].status == "close" || r.data[i].status == "failed"){
    					tableHtml = tableHtml +'<td data-id="'+r.data[i].uuid+'"><a class="mr-5" href="userRechargeConfirm.html?uuid='+r.data[i].uuid+'">确认收款</a></td>'
    				}else{
    					tableHtml = tableHtml +'<td></td>'
    				}
    				tableHtml = tableHtml +'</tr>'
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
    				tableHtml += '<tr><td colspan="9" align="center">暂无相关数据</td></tr>';
    			}
    			$(".table-list").html(tableHtml);
    			allCheck();
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

$(".screen-box ul.screen-ul li").click(function(){
	$(this).addClass("light").siblings().removeClass("light");
	type = $(this).attr("data-status");
	getList();
	getTypesNumber();
});

//渠道列表
function getPlatformList(){
	$.ajax({
        url: '../back/capitalPlatform/list',
        type: 'get',
        async:false
    }).done(function(r) {
    		if (r.status == "SUCCESS") {
    			var optionHtml = '<option value="">全部</option>'
    			for(var i in r.data){
    				optionHtml = optionHtml + '<option value="'+r.data[i].uuid+'">'+r.data[i].name+'</option>'
    			}
    			$("#capitalPlatformSelect").html(optionHtml);
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
function changePlatfrom(){
	getList();
}

//状态数据
function getTypeNumber(){
	$.ajax({
        url: '../back/capitalAccountHistory/typeList',
        type: 'get',
        async:false
    }).done(function(r) {
		if (r.status == "SUCCESS") {
			var allTypes = 0;
			for(var i=0;i<r.data.length;i++){
				$("#"+r.data[i].status+"Types").html("("+r.data[i].count+")");
				allTypes += r.data[i].count;
			}
			$("#allTypes").html("("+allTypes+")");
		} else {
			
		}
    }).fail(function(r) {
    	
    });   
}