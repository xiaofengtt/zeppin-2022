/**
 * 红包管理
 */
var pageNum = (url('?pagenum') != null) ? url('?pagenum') : 1,
	pageSize = (url('?pagesize') != null) ? url('?pagesize') : 50;
var typeObj = { //公告类型
	'1':'平台公告',
	'2':'活动通知'
}
var typeStatus = {
	'online':'在线',
	'offline':'下线',
	'delete':'删除'
}
$(function(){
	$(".goPrePage").click(function(){
		window.location.href='noticeDetail.html?pagesize='+pageSize+'&pagenum='+pageNum
	});
	getList();
});
function getList(){
	$.ajax({
        url: '../back/noticePublic/list',
        type: 'get',
        async:false,
        data: {
        	'pageSize':pageSize,
        	'pageNum':pageNum
        }
    }).done(function(r) {
		if (r.status == "SUCCESS") {
			var tableHtml = '<tr>'+
				'<th width="15%" class="text-center">公告类型</th>'+
				'<th width="20%" class="text-center">标题</th>'+
				'<th width="20%" class="text-center">发布日期</th>'+
				'<th width="10%" class="text-center">发布人</th>'+
				'<th width="15%" class="text-center">状态</th>'+
				'<th width="15%" class="text-center">操作</th>'+
				'</tr>';
			for(var i=0;i<r.data.length;i++){
				var categoryHtml = '';
				var classColor = "";
				if(r.data[i].status=="online"){
					categoryHtml = '<a class="changeBtn mr-5">下线</a>';
				}else if(r.data[i].status=="offline"){
					categoryHtml = '<a class="editBtn mr-5">编辑</a><a class="deleteBtn">删除</a>';
				}
				if(r.data[i].status=="online"){
					classColor = "color-green"
				}else if(r.data[i].status=="delete"){
					classColor = "color-orange"
				}else if(r.data[i].status=="offline"){
					classColor = "color-red"
				}
				tableHtml += '<tr>'
					+'<td class="text-center">'+typeObj[r.data[i].type]+'</td>'
					+'<td class="text-center">'+r.data[i].title+'</td>'
					+'<td class="text-center">'+formatDate(r.data[i].onlinetime)+'</td>'
					+'<td class="text-center">'+r.data[i].creatorCN+'</td>'
					+'<td class="text-center '+classColor+'">'+typeStatus[r.data[i].status]+'</td>'
					+'<td class="text-center" data-id="'+r.data[i].uuid+'">'+categoryHtml+'</td>'
					+'</tr>'; 
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
				tableHtml += '<tr><td colspan="6" align="center">暂无相关数据</td></tr>';
			}
			$("table").html(tableHtml);
			$(".editBtn").click(function(){
				window.location.href = 'noticeDetail.html?uuid='+$(this).parent().attr("data-id")+'&pagesize='+pageSize+'&pagenum='+pageNum;
				return false;
			});
			$(".deleteBtn").click(function(){
				deleteData($(this).parent().attr("data-id"));
				return false;
			});
			$(".changeBtn").click(function(){
				changeData($(this).parent().attr("data-id"));
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
//删除
function deleteData(uuid){
	layer.confirm('是否确认删除该公告？',function(index, layero){
    	$.ajax({
            url: '../back/noticePublic/delete',
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
    },function(){
    	
    });
}
//改状态
function changeData(uuid){
	$.ajax({
        url: '../back/noticePublic/changeStatus',
        type: 'get',
        async:false,
        data: {
        	'uuid':uuid,
        	'status':'offline'
        }
    }).done(function(r) {
		if (r.status == "SUCCESS") {
			layer.msg("操作成功", {
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
		layer.close(index);
    })
}
layui.use(['form', 'element'], function(){
	var form = layui.form
	,layer = layui.layer
	,$ = layui.jquery
	,element = layui.element;
})