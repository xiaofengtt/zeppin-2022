/**
 * 银行卡管理
 */
var pageNum = 1,
    pageSize = 50;
var statusObj = {
	'normal':'正常',
	'disable':'停用',
}
$(function(){
	getList();
});
layui.use(['table', 'layer','element'], function(){
	var form = layui.form
		,layer = layui.layer
		,$ = layui.jquery
		,element = layui.element;
});
$('.addBtn').click(function(){
	layer.open({
		type: 2, 
		title:false,
		area: ['600px', '450px'],
		content:['bankcardDetail.html']
	}); 
})
function changeStatus(uuid,status){
	$.ajax({
        url: '../store/companyBankcard/changeStatus',
        type: 'post',
        data: {
        	'uuid': uuid,
        	'status': status
        }
    }).done(function(r) {
    		if (r.status == "SUCCESS") {
    			layer.msg('操作成功', {
    				time: 1000 
    			}, function(){
    				getList();
    				layer.closeAll();
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
function deleteit(uuid){
	$.ajax({
        url: '../store/companyBankcard/delete',
        type: 'post',
        data: {
        	'uuid': uuid
        }
    }).done(function(r) {
    		if (r.status == "SUCCESS") {
    			layer.msg('操作成功', {
    				time: 1000 
    			}, function(){
    				getList();
    				layer.closeAll();
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
function getList(){
	$.ajax({
        url: '../store/companyBankcard/list',
        type: 'get',
        async:false,
        data: {
        	'pageSize':pageSize,
        	'pageNum':pageNum
        }
    }).done(function(r) {
		if (r.status == "SUCCESS") {
			var tableHtml = '<tr>'+
				'<th width="100px" class="text-left">所属银行</th>'+
				'<th width="150px" class="text-center">持卡人</th>'+
				'<th width="150px" class="text-center">银行卡号</th>'+
				'<th width="200px" class="text-center">开户地区</th>'+
				'<th width="100px" class="text-center">开户支行</th>'+
				'<th width="80px" class="text-center">状态</th>'+
				'<th width="100px" class="text-center">操作</th>'+
				'</tr>';
			for(var i=0;i<r.data.length;i++){
				var processHtml = '';
				if(r.data[i].status == 'normal'){
					processHtml = '<a class="closeBtn color-red mr-5">停用</a>'
				}else if(r.data[i].status == 'disable'){
					processHtml = '<a class="openBtn color-green mr-5">启用</a>'
				}
				processHtml = processHtml + '<a class="deleteBtn color-blue">解绑</a>';
				tableHtml += '<tr>'
					+'<td class="text-left">'+r.data[i].bankName+'</td>'
					+'<td class="text-center">'+r.data[i].holder+'</td>'
					+'<td class="text-center">'+r.data[i].accountNum+'</td>'
					+'<td class="text-center">'+r.data[i].area+'</td>'
					+'<td class="text-center">'+r.data[i].branchBank+'</td>'
					+'<td class="text-center">'+statusObj[r.data[i].status]+'</td>'
					+'<td class="text-center" data-id="'+r.data[i].uuid+'">'+processHtml+'</td>'
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
			$("table").html(tableHtml);
			$(".openBtn").click(function(){
				var dataUuid = $(this).parent().attr("data-id");
				layer.confirm('是否确认启用该银行卡',{
					btnAlign: 'c',
					yes: function(index){
						changeStatus(dataUuid,'normal');
					}
				});
				return false;
			});
			$(".closeBtn").click(function(){
				var dataUuid = $(this).parent().attr("data-id");
				layer.confirm('是否确认停用该银行卡',{
					btnAlign: 'c',
					yes: function(index){
						changeStatus(dataUuid,'disable');
					}
				});
				return false;
			});
			$(".deleteBtn").click(function(){
				var dataUuid = $(this).parent().attr("data-id");
				layer.confirm('是否解绑该银行卡',{
					btnAlign: 'c',
					yes: function(index){
						deleteit(dataUuid);
					}
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