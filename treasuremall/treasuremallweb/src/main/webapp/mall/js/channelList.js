/**
 * 用户注册渠道
 */
var pageNum = (url('?pagenum') != null) ? url('?pagenum') : 1,
	pageSize = (url('?pagesize') != null) ? url('?pagesize') : 50;
var flagSubmit = true;
$(function(){
	getList();	
	$(".addChannel").click(function(){
		layer.open({
		  type: 2, 
		  title:false,
		  area: ['500px', '400px'],
		  content:['channelDetail.html?pagesize='+pageSize+'&pagenum='+pageNum,'no']
		}); 
	});
});
function getList(size,num){
	$.ajax({
        url: '../back/channel/list',
        type: 'get',
        async:false,
        data: {
        	'pageSize':size?size:pageSize,
            'pageNum':num?num:pageNum,
        }
    }).done(function(r) {
    		if (r.status == "SUCCESS") {
    			var tableHtml = '<tr>'+
								'<th width="15%" class="text-center">ID</th>'+
								'<th width="25%" class="text-center">标题</th>'+
								'<th width="20%" class="text-center">appkey</th>'+
								'<th width="20%" class="text-center">channel</th>'+
								'<th width="20%" class="text-center">操作</th>'+
							'</tr>';
				for(var i=0;i<r.data.length;i++){
					var status = "";//状态
					var source = "";//来源
					var opteration = "";//操作按钮
					var categoryHtml = '<a class="defaultBtn mr-5">设为默认</a><a class="editBtn mr-5">编辑</a><a class="deleteBtn">删除</a>';//分类
					tableHtml += '<tr data-url="channelDetail.html?uuid='+r.data[i].uuid+
					'"><td class="text-center">'+r.data[i].channelId+'</td><td class="text-center">'+r.data[i].title+
					'</td><td class="text-center">'+r.data[i].appkey+'</td><td class="text-center">'+r.data[i].channel+'</td><td data-id="'+r.data[i].uuid+
					'" class="text-center">'+categoryHtml+'</td></tr>'; 
				}
				if (r.totalPageCount!=0) {
				    $('#pageTool').Paging({
				        prevTpl: "<",
				        nextTpl: ">",
				        pagesize: pageSize,
				        count: r.totalResultCount,
				        current: pageNum,
				        toolbar: true,
				        pageSizeList: [50, 200, 1000],
				        callback: function(page, size, count) {
				            pageNum = page;
				            getList();
				            flag = false;
				            document.body.scrollTop = document.documentElement.scrollTop = 0;
				        }
				    });
				    $(".ui-paging-container .ui-paging-toolbar").append("<b class='paging-total'>共"+r.totalResultCount+"条</b>")
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
					tableHtml += '<tr><td colspan="5" align="center">暂无相关数据</td></tr>';
				}
				$("table").html(tableHtml);

				$(".deleteBtn").click(function(){
					deleteList($(this).parent().attr("data-id"));
					return false;
				});
				$(".defaultBtn").click(function(){
					defaultList($(this).parent().attr("data-id"));
					return false;
				});				
				$(".editBtn").click(function(){
					layer.open({
					  type: 2, 
					  title:false,
					  area: ['500px', '400px'],
					  content:['channelDetail.html?uuid='+$(this).parent().attr("data-id")+'&pagesize='+pageSize+'&pagenum='+pageNum,'no']
					}); 
					return false;
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
}
//设为默认
function defaultList(uuid){
	$.ajax({
        url: '../back/channel/isdefault',
        type: 'get',
        async:false,
        data: {
        	'uuid':uuid
        }
    }).done(function(r) {
    		if (r.status == "SUCCESS") {
    			layer.msg("设置成功", {
		            time: 2000
		        },function(){
		        	getList();	
		        });
    		}else {
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
    })
}
//删除
function deleteList(uuid){
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
        url: '../back/channel/delete',
        type: 'get',
        async:false,
        data: {
        	'uuid':uuid
        }
    }).done(function(r) {
    		if (r.status == "SUCCESS") {
    			layer.msg("删除成功", {
		            time: 2000
		        },function(){
		        	getList();	
		        });
    		}else {
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
    })
	}, function(){
		layer.closeAll();
	});
}
layui.use(['form', 'element'], function(){
	var form = layui.form
	,layer = layui.layer
	,$ = layui.jquery
	,element = layui.element;
})