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
        url: '../back/userRecharge/checkList',
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
								'<th width="10%">用户姓名</th>'+
								'<th width="10%">经办人</th>'+
								'<th width="15%">提交时间</th>'+
								'<th width="10%">状态</th>'+
								'<th width="10%">操作</th>'+
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
    				+'<td><b>'+r.data[i].frontUserMobile+'</b></td>'
    				+'<td>'+(r.data[i].frontUserName == null ? "" : r.data[i].frontUserName) +'</td>'
    				+'<td>'+(r.data[i].creatorName == null ? "" : r.data[i].creatorName) +'</td>'
    				+'<td>'+formatDate(r.data[i].submittime) +'</td>'
    				+'<td>'+status+'</td>'
    				if(r.data[i].status == "normal"){
    					tableHtml = tableHtml +'<td data-id="'+r.data[i].uuid+'"><a class="mr-5" href="userRechargeCheckDetail.html?type=1&uuid='+r.data[i].uuid+'">审核</a></td>'
    				}else{
    					tableHtml = tableHtml +'<td data-id="'+r.data[i].uuid+'"><a class="mr-5" href="userRechargeCheckDetail.html?type=0&uuid='+r.data[i].uuid+'">查看</a></td>'
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
    				tableHtml += '<tr><td colspan="7" align="center">暂无相关数据</td></tr>';
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
        url: '../back/userRecharge/checkStatusList',
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