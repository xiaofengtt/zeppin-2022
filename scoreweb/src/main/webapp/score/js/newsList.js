var pageNum = 1,
    pageSize = 10;
var status = "all";
var categroyArr = [];
var flagSubmit = true;
$(function(){
	getCategroy();
	getList();
	getStatusNumber();
});


//获取列表
function getList(){
	$.ajax({
        url: '../back/news/list',
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
								'<th width="35%">标题</th>'+
								'<th width="7%">来源</th>'+
								'<th width="15%">类别</th>'+
								'<th width="16%">创建时间</th>'+
								'<th width="12%">操作</th>'+
								'<th width="5%" class="align-right"><input type="checkbox" id="allCheck" name="" value=""></th>'+
							'</tr>';
    			for(var i=0;i<r.data.length;i++){
    				var status = "";//状态
    				var source = "";//来源
    				var opteration = "";//操作按钮
    				var categoryHtml = '';//分类
    				if(r.data[i].status=="normal"){
    					status = "未处理";
    					opteration = '<a class="disableBtn mr-5">禁用</a><a class="deleteBtn">删除</a>';
    				}else if(r.data[i].status=="publish"){
    					status = "已发布";
    					opteration = '';
    				}else if(r.data[i].status=="disable"){
    					status = "不使用";
    					opteration = '<a class="deleteBtn">删除</a>';
    				}
    				for(var m=0;m<r.data[i].categoryList.length;m++){
    					categoryHtml += (categroyArr[r.data[i].categoryList[m]]?categroyArr[r.data[i].categoryList[m]]:"未知")+",";
    				}
    				tableHtml += '<tr data-url="newsDetail.html?uuid='+r.data[i].uuid+
    				'"><td class="status-'+r.data[i].status+
    				'"><b>'+status+'</b></td><td>'+r.data[i].title+
    				'</td><td>'+(baseSourceArr[r.data[i].source]?baseSourceArr[r.data[i].source]:"未知")+'</td><td>'+categoryHtml.substring(0,categoryHtml.length-1)+
    				'</td><td>'+formatDate(r.data[i].createtime)+
    				'</td><td data-id="'+r.data[i].uuid+
    				'">'+opteration+'</td><td class="align-right"> <input class="check" type="checkbox" name="" value="'+r.data[i].uuid+
    				'"> </td></tr>'; 
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
    				tableHtml += '<tr><td colspan="7" align="center">暂无相关数据</td></tr>';
    			}
    			$(".table-list").html(tableHtml);
    			allCheck();
    			$(".deleteBtn").click(function(){
    				deleteList($(this).parent().attr("data-id"));
    				return false;
    			});
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
//获取category列表
//处理子级循环
function treeCategorys(r){
	if(r.length>0){
		for(var i=0;i<r.length;i++){
			categroyArr[r[i].uuid] = r[i].name ;
			if(r[i].children && r[i].children.length>0){
				treeCategorys(r[i].children);
			}
		}
		
	}	
}
//获取类别
function getCategroy(){
	$.ajax({
        url: '../back/category/list',
        type: 'get',
        async:false,
        data: {
        	"istag":true
        }
    }).done(function(r) {
    		if (r.status == "SUCCESS") {
    			for(var i = 0;i<r.data.length;i++){
    				treeCategorys(r.data[i].children);
    				categroyArr[r.data[i].uuid] = r.data[i].name ;
    			}
    			
    		} else {
    			
    		}
    }).fail(function(r) {
        
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

//删除
function deleteList(listid){
	var uuid = "";		
	if(listid){
		uuid = listid;
	}else{
		$(".check.light").each(function(){
			uuid += $(this).val() + ",";
		});
		if(uuid==""){
			layer.msg("请选择要删除的文章");
			return false;
		}else{
			uuid = uuid.substring(0,uuid.length-1);
		}			
	}		
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
	        url: '../back/news/delete',
	        type: 'get',
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
			layer.msg("请选择要禁用的文章");
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
	        url: '../back/news/changeStatus',
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

//状态数据
function getStatusNumber(){
	$.ajax({
        url: '../back/news/statusList',
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
