var pageNum = 1,
    pageSize = 10;
var status = "all";
var flagSubmit = true;

$(function(){
	getList();
	getStatusNumber();
});

//获取列表
function getList(){
	$.ajax({
        url: '../back/userHistory/list',
        type: 'get',
        async:false,
        data: {
        	"status":status,
        	"pageSize": pageSize,
        	"pageNum": pageNum
        }
    }).done(function(r) {
    		if (r.status == "SUCCESS") {
    			var tableHtml = '<tr>'+
								'<th width="15%">手机号</th>'+
								'<th width="10%">用户名</th>'+
								'<th width="15%">充值渠道</th>'+
								'<th width="15%">充值账户</th>'+
								'<th width="10%">充值金额</th>'+
								'<th width="7%">手续费</th>'+
								'<th width="8%">经办人</th>'+
								'<th width="10%">状态</th>'+
								'<th width="10%">操作</th>'+
							'</tr>';
    			for(var i=0;i<r.data.length;i++){
    				var status = "";//状态
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
    				tableHtml += '<tr>'
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
	status = $(this).attr("data-status");
	if(status=="publish"||status=="disable"||status=="delete"){//已发布状态
		$(".screen-operate a").hide();
		$(".screen-operate .add-btn").show();
	}else{
		$(".screen-operate a").show();
	}
	getList();
	getStatusNumber();
});

//状态数据
function getStatusNumber(){
	$.ajax({
        url: '../back/userHistory/statusList',
        type: 'get',
        async:false,
        data: {
           type : "user_recharge"
        }
    }).done(function(r) {
		if (r.status == "SUCCESS") {
			var allStatus = 0;
			for(var i=0;i<r.data.length;i++){
				$("#"+r.data[i].status+"Status").html("("+r.data[i].count+")");
				allStatus += r.data[i].count;
			}
			$("#allStatus").html("("+allStatus+")");
		} else {
			
		}
    }).fail(function(r) {
    	
    });   
}