var pageNum = 1,
    pageSize = 20;
var status = "normal";
var flagSubmit = true;

$(function(){
	getList();
	getStatusNumber();
});

//获取列表
function getList(){
	$.ajax({
        url: '../back/userBet/list',
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
								'<th width="15%">用户手机号</th>'+
								'<th width="15%">用户姓名</th>'+
								'<th width="15%">投注总额</th>'+
								'<th width="20%">投注时间</th>'+
								'<th width="13%">经办人</th>'+
								'<th width="12%">状态</th>'+
								'<th width="10%" class="text-right">操作</th>'+
							'</tr>';
    			for(var i=0;i<r.data.length;i++){
    				var type = "";//类型
    				var status = "";//状态
    				var role = "";
    				if(r.data[i].status=="normal"){
    					status = "待审核";
    				}else if(r.data[i].status=="confirm"){
    					status = "已确认";
    				}else if(r.data[i].status=="failed"){
    					status = "审核未通过";
    				}else if(r.data[i].status=="finished"){
    					status = "已完成";
    				}
    				tableHtml += '<tr>'
    				+'<td><b>'+handlePhoneNumber(r.data[i].frontUserMobile)+'</b></td>'
    				+'<td>'+(r.data[i].frontUserName == null ? "" : r.data[i].frontUserName) +'</td>'
    				+'<td>'+moneyFormat(r.data[i].price) +'</td>'
    				+'<td>'+formatDate(r.data[i].createtime) +'</td>'
    				+'<td>'+(r.data[i].checkerName == null ? "" : r.data[i].checkerName) +'</td>'
    				+'<td>'+status+'</td>'
    				if(r.data[i].status == "normal"){
    					tableHtml = tableHtml +'<td class="text-right" data-id="'+r.data[i].uuid+'"><a class="" href="guessingCheckDetail.html?type=1&uuid='+r.data[i].uuid+'">审核</a></td>'
    				}else{
    					tableHtml = tableHtml +'<td class="text-right" data-id="'+r.data[i].uuid+'"><a class="" href="guessingCheckDetail.html?type=0&uuid='+r.data[i].uuid+'">查看</a></td>'
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
    				tableHtml += '<tr><td colspan="7" align="center">暂无相关数据</td></tr>';
    			}
    			$(".table-list").html(tableHtml);
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

$(".lottery-btn").click(function(){
	$.ajax({
	      url: '../back/userBet/lottery',
	      type: 'post',
	      async:false,
	      data: {
	      }
	}).done(function(r) {
		if (r.status == "SUCCESS") {
			layer.msg(r.message, {
				time: 2000
			},function(){
				window.location.href=window.location.href;
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
})

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
        url: '../back/userBet/statusList',
        type: 'get',
        async:false,
        data: {
        }
    }).done(function(r) {
		if (r.status == "SUCCESS") {
			for(var i=0;i<r.data.length;i++){
				$("#"+r.data[i].status+"Status").html("("+r.data[i].count+")");
			}
		} else {
			
		}
    }).fail(function(r) {
    	
    });   
}