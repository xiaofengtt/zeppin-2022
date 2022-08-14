/**
 * 商户管理员管理
 */
var company = (url('?company') != null) ? url('?company') : '';
var pageNum = 1,
    pageSize = 50;
var statusObj = {
	'normal':'正常',
	'disable':'停用',
}
$(function(){
	getList();	
	$('.addBtn').click(function(){
		layer.open({
		  type: 2, 
		  title:false,
		  area: ['600px', '600px'],
		  content:['companyAdminDetail.html?company='+company]
		}); 
	})
});
layui.use(['table', 'layer','element'], function(){
	var form = layui.form
		,layer = layui.layer
		,$ = layui.jquery
		,element = layui.element;
});
function changeStatus(uuid,status){
	$.ajax({
        url: '../system/companyAdmin/changeStatus',
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
function getList(){
	var datas = {
        	'pageSize':pageSize,
        	'pageNum':pageNum,
        	'company':company
        }
	$.ajax({
        url: '../system/companyAdmin/list',
        type: 'get',
        async:false,
        data: datas
    }).done(function(r) {
		if (r.status == "SUCCESS") {
			var tableHtml = '<tr>'+
				'<th width="150px" class="text-left">手机号码</th>'+
				'<th width="150px" class="text-center">用户名</th>'+
				'<th width="200px" class="text-center">邮箱</th>'+
				'<th width="150px" class="text-center">创建时间</th>'+
				'<th width="100px" class="text-center">状态</th>'+
				'<th width="100px" class="text-center">操作</th>'+
				'</tr>';
			for(var i=0;i<r.data.length;i++){
				var processHtml = '';
				if(r.data[i].status == 'normal'){
					processHtml = '<a class="closeBtn mr-5">停用</a> <a class="editBtn mr-5">编辑</a>'
				}else if(r.data[i].status == 'disable'){
					processHtml = '<a class="openBtn mr-5">启用</a> <a class="editBtn mr-5">编辑</a>'
				}
				
				tableHtml += '<tr>'
					+'<td class="text-left">'+r.data[i].mobile+'</td>'
					+'<td class="text-center">'+r.data[i].username+'</td>'
					+'<td class="text-center">'+stringValue(r.data[i].email)+'</td>'
					+'<td class="text-center">'+formatDate(r.data[i].createtime)+'</td>'
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
				tableHtml += '<tr><td colspan="6" align="center">暂无相关数据</td></tr>';
			}
			$("table").html(tableHtml);
			$(".openBtn").click(function(){
				var dataUuid = $(this).parent().attr("data-id");
				layer.confirm('是否确认启用该商户管理员',{
					btnAlign: 'c',
					yes: function(index){
						changeStatus(dataUuid,'normal');
					}
				});
				return false;
			});
			$(".closeBtn").click(function(){
				var dataUuid = $(this).parent().attr("data-id");
				layer.confirm('是否确认停用该商户管理员',{
					btnAlign: 'c',
					yes: function(index){
						changeStatus(dataUuid,'disable');
					}
				});
				return false;
			});
			$(".editBtn").click(function(){
				var dataUuid = $(this).parent().attr("data-id");
				layer.open({
					  type: 2, 
					  title:false,
					  area: ['600px', '600px'],
					  content:['companyAdminDetail.html?company='+company+'&uuid='+dataUuid]
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