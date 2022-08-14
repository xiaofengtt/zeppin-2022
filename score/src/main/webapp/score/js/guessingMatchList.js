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
        url: '../back/guessingMatch/list',
        type: 'get',
        async:false,
        data: {
        	"status":status,
            "pageSize":pageSize,
            "pageNum":pageNum
        }
    }).done(function(r) {
    		if (r.status == "SUCCESS") {
    			var tableHtml = '<tr>'+
								'<th width="10%">状态</th>'+
								'<th width="15%">赛事</th>'+
								'<th width="15%">主队</th>'+
								'<th width="15%">客队</th>'+
								'<th width="15%">比赛时间</th>'+
								'<th width="15%">竞猜类型</th>'+
								'<th width="15%">操作</th>'+
							'</tr>';
    			for(var i=0;i<r.data.length;i++){
    				var status = "";//状态
    				var guessingType = "";
    				var opteration = "";//操作按钮
    				if(r.data[i].status=="normal"){
    					status = "未发布";
    					opteration = '<a class="mr-5" href="guessingMatchTypeDetail.html?uuid='+r.data[i].uuid+'">竞猜方案</a><a class="deleteBtn">删除</a>';
    				}else if(r.data[i].status=="publish"){
    					status = "已发布";
    					opteration = '<a class="mr-5" href="guessingMatchTypeDetail.html?uuid='+r.data[i].uuid+'">竞猜方案</a>';
    				}else if(r.data[i].status=="waiting"){
    					status = "待结算";
    					opteration = '<a class="mr-5" href="guessingMatchSettle.html?uuid='+r.data[i].uuid+'">结算</a>';
    				}else if(r.data[i].status=="finished"){
    					status = "已完成";
    					opteration = '<a class="mr-5" href="guessingMatchTypeDetail.html?uuid='+r.data[i].uuid+'">竞猜方案</a>';
    				}
    				for(var j=0;j<r.data[i].guessingTypeArray;j++){
    					var guessingTypeName = "";
    					if(r.data[i].guessingTypeArray[j] == "score"){
    						guessingTypeName = "猜比分";
    					}else if(r.data[i].guessingTypeArray[j] == "victory"){
    						guessingTypeName = "猜胜负";
    					}
    					guessingType += guessingTypeName + "<br/>"
    				}
    				
    				
    				tableHtml += '<tr>'+
    					'<td class="status-'+r.data[i].status+'"><b>'+status+'</b></td>'+
    					'<td>'+r.data[i].matchDetail.categoryName+'</td>'+
    					'<td>'+r.data[i].matchDetail.hometeamName+'</td>'+
    					'<td>'+r.data[i].matchDetail.awayteamName+'</td>'+
    					'<td>'+formatDate(r.data[i].matchDetail.time)+'</td>'+
    					'<td>'+guessingType+'</td>'+
    					'<td data-id="'+r.data[i].uuid+'">'+opteration +'</td>'+
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
    				tableHtml += '<tr><td colspan="6" align="center">暂无相关数据</td></tr>';
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

$(".screen-box ul.screen-ul li").click(function(){
	$(this).addClass("light").siblings().removeClass("light");
	status = $(this).attr("data-status");
	pageNum = 1;
	getList();
	getStatusNumber();
});
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
	        url: '../back/guessingMatch/delete',
	        type: 'POST',
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

//状态数据
function getStatusNumber(){
	$.ajax({
        url: '../back/guessingMatch/statusList',
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
