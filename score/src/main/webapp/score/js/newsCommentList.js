var pageNum = 1,
    pageSize = 20;
var flagSubmit = true;
$(function(){
	getList();
});
//回车事件
document.onkeydown = function (event) {
    var e = event || window.event;
    if (e && e.keyCode == 13) { //回车键的键值为13
    	getList(); //调用登录按钮的登录事件
    }
};
//获取列表
function getList(){
	var content = encodeURI(encodeURI($("#content").val().replace(/(^\s*)|(\s*$)/g, "")));	
	$.ajax({
        url: '../back/newsComment/list',
        type: 'get',
        async:false,
        data: {
        	"content":content,
            "pageSize":pageSize,
            "pageNum":pageNum
        }
    }).done(function(r) {
    		if (r.status == "SUCCESS") {
    			var tableHtml = '<tr>'+
								'<th width="25%">新闻标题</th>'+
								'<th width="40%">评论内容</th>'+
								'<th width="6%">评论人</th>'+
								'<th width="10%">手机号码</th>'+
								'<th width="12%">创建时间</th>'+
								'<th width="7%" class="text-right">操作</th>'+
							'</tr>';
    			for(var i=0;i<r.data.length;i++){
    				var status = "";//状态
    				if(r.data[i].status=="normal"){
    					status = "正常";
    				}else if(r.data[i].status=="delete"){
    					status = "已删除";
    				}
    				
    				tableHtml += '<tr>'+
    				'<td><p class="commentTd singleRow">'+r.data[i].newsTitle+'</p></td>'+
    				'<td><p class="commentTd singleRow">'+decodeURIComponent(r.data[i].content)+'</p></td>'+
    				'<td>'+(r.data[i].creatorName == null ? "" : r.data[i].creatorName) +'</td>'+
    				'<td>'+handlePhoneNumber(r.data[i].creatorMobile)+'</td>'+
    				'<td>'+formatDate(r.data[i].createtime)+'</td>'+
    				'<td data-id="'+r.data[i].uuid+'" class="text-right">'+
    				'<a class="deleteBtn">删除</a></td></tr>'; 
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
    				tableHtml += '<tr><td colspan="7" align="center">暂无相关评论</td></tr>';
    			}
    			$(".table-list").html(tableHtml);
    			allCheck();
    			$(".deleteBtn").click(function(){
    				deleteList($(this).parent().attr("data-id"));
    				return false;
    			});
    			$(".table-list tr").hover(function(){
    				$(this).find(".commentTd").removeClass("singleRow");
    			},function(){
    				$(this).find(".commentTd").addClass("singleRow");
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
function deleteList(listid){
	var deleteUuid = "";		
	if(listid){
		deleteUuid = listid;
	}else{
		$(".check.light").each(function(){
			deleteUuid += $(this).val() + ",";
		});
		if(deleteUuid==""){
			layer.msg("请选择要删除的评论");
			return false;
		}else{
			deleteUuid = deleteUuid.substring(0,deleteUuid.length-1);
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
	        url: '../back/newsComment/delete',
	        type: 'get',
	        async:false,
	        data: {
	           "uuid":deleteUuid,
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