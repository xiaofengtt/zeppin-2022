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
        url: '../back/userRecharge/list',
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
					    			'<th width="10%">类型</th>'+
									'<th width="15%">用户手机号</th>'+
									'<th width="15%">用户姓名</th>'+
									'<th width="10%">充值金额</th>'+
									'<th width="10%">审核人</th>'+
									'<th width="20%">审核原因</th>'+
									'<th width="10%">状态</th>'+
									'<th width="10%" class="text-right">操作</th>'+
								'</tr>';
    			for(var i=0;i<r.data.length;i++){
    				var type = "";//类型
    				var status = "";//状态
    				var role = "";
    				if(r.data[i].type=="add"){
    					type = "录入";
    				}else if(r.data[i].type=="confirm"){
    					type = "确认";
    				}
    				if(r.data[i].status=="normal"){
    					status = "待审核";
    				}else if(r.data[i].status=="checked"){
    					status = "审核通过";
    				}else if(r.data[i].status=="nopass"){
    					status = "审核未通过";
    				}
    				tableHtml += '<tr>'
    				+'<td><b>'+type+'</b></td>'
    				+'<td><b>'+handlePhoneNumber(r.data[i].frontUserMobile)+'</b></td>'
    				+'<td>'+(r.data[i].frontUserName == null ? "" : r.data[i].frontUserName) +'</td>'
    				+'<td>'+moneyFormat(r.data[i].value.income) +'</td>'
    				+'<td>'+(r.data[i].checkerName == null ? "" : r.data[i].checkerName) +'</td>'
    				+'<td>'+(r.data[i].reason == null ? "" : r.data[i].reason) +'</td>'
    				+'<td>'+status+'</td>'
    				if(r.data[i].status == "nopass"){
    					tableHtml = tableHtml +'<td class="text-right" data-id="'+r.data[i].uuid+'"><a class="" href="userRechargeOperateDetail.html?type=1&uuid='+r.data[i].uuid+'">重新提交</a></td>'
    				}else{
    					tableHtml = tableHtml +'<td class="text-right" data-id="'+r.data[i].uuid+'"><a class="" href="userRechargeOperateDetail.html?type=0&uuid='+r.data[i].uuid+'">查看</a></td>'
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
    				tableHtml += '<tr><td colspan="8" align="center">暂无相关数据</td></tr>';
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
        url: '../back/userRecharge/statusList',
        type: 'get',
        async:false,
        data: {
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