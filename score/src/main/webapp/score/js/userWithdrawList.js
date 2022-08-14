var pageNum = 1,
    pageSize = 20;
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
        	"type":"user_withdraw",
        	"pageSize": pageSize,
        	"pageNum": pageNum
        }
    }).done(function(r) {
    		if (r.status == "SUCCESS") {
    			var tableHtml = '<tr>'+
								'<th width="10%">手机号</th>'+
								'<th width="10%">用户名</th>'+
								'<th width="15%">提现渠道</th>'+
								'<th width="10%">提现银行</th>'+
								'<th width="10%" class="text-right">提现金额</th>'+
								'<th width="5%" class="text-right">手续费</th>'+
								'<th width="10%" class="text-center">经办人</th>'+
								'<th width="10%" class="text-left">创建时间</th>'+
								'<th width="5%">状态</th>'+
								'<th width="15%" class="text-right">操作</th>'+
							'</tr>';
    			for(var i=0;i<r.data.length;i++){
    				var status = "";//状态
    				var role = "";
    				if(r.data[i].status=="normal"){
    					status = "待处理";
    				}else if(r.data[i].status=="confirm"){
    					status = "已确认";
    				}else if(r.data[i].status=="warning"){
    					status = "交易失败";
    				}else if(r.data[i].status=="close"){
    					status = "已关闭";
    				}else if(r.data[i].status=="success"){
    					status = "交易成功";
    				}
    				tableHtml += '<tr>'
    				+'<td><b>'+handlePhoneNumber(r.data[i].frontUserMobile)+'</b></td>'
    				+'<td>'+(r.data[i].frontUserName == null ? "" : r.data[i].frontUserName) +'</td>'
    				+'<td><b>'+(r.data[i].capitalAccountName == null ? "" : r.data[i].capitalAccountName)+'</b></td>'
    				+'<td><b>'+(r.data[i].frontUserBankcardBankName == null ? "" : r.data[i].frontUserBankcardBankName)+'</b></td>'
    				+'<td class="text-right">'+moneyFormat(r.data[i].pay)+'元</td>'
    				+'<td class="text-right">'+moneyFormat(r.data[i].poundage)+'元</td>'
    				+'<td class="text-center">'+(r.data[i].operatorName == null ? "" : r.data[i].operatorName) +'</td>'
    				+'<td class="text-left">'+formatDate(r.data[i].createtime)+'</td>'
    				+'<td>'+status+'</td>'
    				if(r.data[i].status == "normal" || r.data[i].status == "warning"){
    					tableHtml = tableHtml 
    					+'<td class="text-right" data-id="'+r.data[i].uuid+'">'
    					+'<a class="" href="userWithdrawConfirm.html?uuid='+r.data[i].uuid+'">确认完成</a> '
    					+'<a class="" href="userWithdrawApply.html?uuid='+r.data[i].uuid+'">申请付款</a> '
    					+'<a class="" href="userWithdrawRollback.html?uuid='+r.data[i].uuid+'">申请退回</a> '
    					+'<a class="" href="userWithdrawDelete.html?uuid='+r.data[i].uuid+'">删除</a>'
    					+'</td>'
    				}else{
    					tableHtml = tableHtml +'<td class="text-right" data-id="'+r.data[i].uuid+'"><a class="" href="userWithdrawDetail.html?uuid='+r.data[i].uuid+'">查看详情</a></td>'
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
                        getList();
                        flag = false;
                        document.body.scrollTop = document.documentElement.scrollTop = 0;
                    }
                });
                $("#pageTool").show().find(".ui-paging-container:last").siblings().remove();
                
                $(".ui-select-pagesize").unbind("change").change(function() {
                    pageSize = $(this).val();
                    pageNum = 1;
                    getList();
                });
                if(r.data.length==0){
    				tableHtml += '<tr><td colspan="10" align="center">暂无相关数据</td></tr>';
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
	pageNum = 1;
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
           type : "user_withdraw"
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