/**
 * 红包管理
 */
var pageNum = (url('?pagenum') != null) ? url('?pagenum') : 1,
	pageSize = (url('?pagesize') != null) ? url('?pagesize') : 50;
var flagSubmit = true;
$(function(){
	getList();
});
function getList(size,num){
	$.ajax({
        url: '../back/voucher/list',
        type: 'get',
        async:false,
        data: {
        	'pageSize':size?size:pageSize,
        	'pageNum':num?num:pageNum,
        	'title':$(".layui-name").val().replace(/(^\s*)|(\s*$)/g, "")
        }
    }).done(function(r) {
		if (r.status == "SUCCESS") {
			var tableHtml = '<tr>'+
				'<th width="5%" class="text-center">序号</th>'+
				'<th width="17%" class="text-center">金券名称</th>'+
				'<th width="8%" class="text-center">金券面值</th>'+
				'<th width="10%" class="text-center">使用最低限额</th>'+
				'<th width="15%" class="text-center">限制描述</th>'+
				'<th width="10%" class="text-center">开始时间</th>'+
				'<th width="10%" class="text-center">结束时间</th>'+
				'<th width="8%" class="text-center">操作</th>'+
				'</tr>';
			for(var i=0;i<r.data.length;i++){
				var starttime='-', endtime='长期';
				if(r.data[i].starttime != null){
					starttime = r.data[i].starttime.length > 10 ? formatDate(r.data[i].starttime) : r.data[i].starttime;
				}
				if(r.data[i].endtime != null){
					endtime = r.data[i].endtime.length > 10 ? formatDate(r.data[i].endtime) : r.data[i].endtime;
				}
				var categoryHtml = '<a class="editBtn mr-5">编辑</a><a class="deleteBtn">删除</a>';//分类
				tableHtml += '<tr>'
					+'<td class="text-center">'+(pageSize*(pageNum-1)+i+1)+'</td>'
					+'<td class="text-center">'+r.data[i].title+'</td>'
					+'<td class="text-center">'+(r.data[i].dAmount).toFixed(2)+'</td>'
					+'<td class="text-center">'+(r.data[i].payMin).toFixed(2)+'</td>'
					+'<td class="text-center">'+r.data[i].discription+'</td>'
					+'<td class="text-center">'+starttime+'</td>'
					+'<td class="text-center">'+endtime+'</td>'
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
				tableHtml += '<tr><td colspan="8" align="center">暂无相关数据</td></tr>';
			}
			$("table").html(tableHtml);
			$(".editBtn").click(function(){
				window.location.href = 'voucherDetail.html?uuid='+$(this).parent().attr("data-id")+'&pagesize='+pageSize+'&pagenum='+pageNum;
				return false;
			});
			$(".deleteBtn").click(function(){
				deleteData($(this).parent().attr("data-id"));
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
	layer.confirm('是否确认删除该金券？', function(index, layero){		
		if(flagSubmit == false) {
			return false;
		}
		flagSubmit = false;
		setTimeout(function() {
			flagSubmit = true;
		}, 3000);
	$.ajax({
		url: '../back/voucher/changeStatus',
        type: 'post',
        async:false,
        data: {
        	'uuid':uuid,
        	'status': 'delete'
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
    		layer.close(index);
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