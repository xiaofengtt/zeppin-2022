/**
 * 
 */
var company = (url('?company') != null) ? url('?company') : '';
var pageNum = 1,
    pageSize = 50;
var statusObj = {
	'normal':'normal',
	'disable':'disable',
}
var typeObj = {
	'recharge':'recharge',
	'withdraw':'withdraw'
}
$(function(){
	getList();	
	$('.addBtn').click(function(){
		layer.open({
		  type: 2, 
		  title:false,
		  area: ['600px', '500px'],
		  content:['companyChannelDetail.html?company='+company]
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
        url: '../system/companyChannel/changeStatus',
        type: 'post',
        data: {
        	'uuid': uuid,
        	'status': status
        }
    }).done(function(r) {
    		if (r.status == "SUCCESS") {
    			layer.msg('Successful', {
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
	if($('select[name=type]').val() != ''){
		datas['type'] = $('select[name=type]').val();
	}
	$.ajax({
        url: '../system/companyChannel/list',
        type: 'get',
        async:false,
        data: datas
    }).done(function(r) {
		if (r.status == "SUCCESS") {
			var tableHtml = '<tr>'+
				'<th width="100px" class="text-left">Type</th>'+
				'<th width="150px" class="text-center">Store</th>'+
				'<th width="150px" class="text-center">Channel Name</th>'+
				'<th width="200px" class="text-center">Channel ID</th>'+
				'<th width="80px" class="text-center">Currency</th>'+
				'<th width="100px" class="text-center">Poundage</th>'+
				'<th width="120px" class="text-center">Single Limit</th>'+
				'<th width="100px" class="text-center">Balance</th>'+
				'<th width="100px" class="text-center">Balance Lock</th>'+
				'<th width="150px" class="text-center">Createtime</th>'+
				'<th width="80px" class="text-center">Status</th>'+
				'<th width="100px" class="text-center">Operation</th>'+
				'</tr>';
			for(var i=0;i<r.data.length;i++){
				var processHtml = '';
				if(r.data[i].status == 'normal'){
					processHtml = '<a class="closeBtn mr-5">close</a> <a class="editBtn">edit</a>'
				}else if(r.data[i].status == 'disable'){
					processHtml = '<a class="openBtn mr-5">open</a> <a class="editBtn">edit</a>'
				}
				
				tableHtml += '<tr>'
					+'<td class="text-left">'+typeObj[r.data[i].type]+'</td>'
					+'<td class="text-center">'+r.data[i].companyName+'</td>'
					+'<td class="text-center">'+r.data[i].channelName+'</td>'
					+'<td class="text-center">'+r.data[i].uuid+'</td>'
					+'<td class="text-center">'+r.data[i].currency+'</td>'
					+'<td class="text-center">'+(r.data[i].poundageRate == null ? ((r.data[i].poundage/100).toFixed(2)) : (r.data[i].poundageRate*100+'%'))+'</td>'
					+'<td class="text-center">'+(r.data[i].min/100).toFixed(2) +'-'+(r.data[i].max/100).toFixed(2) +'</td>'
					+'<td class="text-center">'+(r.data[i].balance/100).toFixed(2)+'</td>'
					+'<td class="text-center">'+(r.data[i].balanceLock/100).toFixed(2)+'</td>'
					+'<td class="text-center">'+formatDate(r.data[i].createtime)+'</td>'
					+'<td class="text-center">'+statusObj[r.data[i].status]+'</td>'
					+'<td class="text-center" data-id="'+r.data[i].uuid+'" data-channel="'+r.data[i].channel+'">'+processHtml+'</td>'
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
				tableHtml += '<tr><td colspan="11" align="center">No relevant data</td></tr>';
			}
			$("table").html(tableHtml);
			$(".openBtn").click(function(){
				var dataUuid = $(this).parent().attr("data-id");
				layer.confirm('Are you sure to start the channel',{
					btnAlign: 'c',
					title:'information',
					btn: ['Confirm', 'Cancel'],
					yes: function(index){
						changeStatus(dataUuid,'normal');
					}
				});
				return false;
			});
			$(".closeBtn").click(function(){
				var dataUuid = $(this).parent().attr("data-id");
				layer.confirm('Are you sure to stop the channel',{
					btnAlign: 'c',
					title:'information',
					btn: ['Confirm', 'Cancel'],
					yes: function(index){
						changeStatus(dataUuid,'disable');
					}
				});
				return false;
			});
			$(".editBtn").click(function(){
				var dataUuid = $(this).parent().attr("data-id");
				var dataChannel = $(this).parent().attr("data-channel");
				layer.open({
					  type: 2, 
					  title:false,
					  area: ['600px', '500px'],
					  content:['companyChannelDetail.html?company='+company+'&uuid='+dataUuid+'&channel='+dataChannel]
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