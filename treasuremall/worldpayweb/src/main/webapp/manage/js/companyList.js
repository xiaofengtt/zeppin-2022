/**
 * 
 */
var pageNum = 1,
    pageSize = 50;
var statusObj = {
	'normal':'normal',
	'disable':'disable'
}
$(function(){
	getList();	
	$(".addBtn").click(function(){
		layer.open({
		  type: 2, 
		  title:false,
		  area: ['800px', '600px'],
		  content:['companyDetail.html']
		}); 
	});
});
layui.use(['table', 'layer','element'], function(){
	var form = layui.form
		,layer = layui.layer
		,$ = layui.jquery
		,element = layui.element;
});
function changeStatus(uuid,status){
	$.ajax({
        url: '../system/company/changeStatus',
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
    	'pageNum':pageNum
    }
	if($('input[name=name]').val() != ''){
		datas['name'] = $('input[name=name]').val();
	}
	if($('input[name=code]').val() != ''){
		datas['code'] = $('input[name=code]').val();
	}
	
	$.ajax({
        url: '../system/company/list',
        type: 'get',
        async:false,
        data: datas
    }).done(function(r) {
		if (r.status == "SUCCESS") {
			var tableHtml = '<tr>'+
				'<th width="120px" class="text-left">Code</th>'+
				'<th width="150px" class="text-center">Store</th>'+
				'<th width="80px" class="text-center">Channel</th>'+
				'<th width="80px" class="text-center">Admin</th>'+
				'<th width="80px" class="text-center">Business</th>'+
				'<th width="80px" class="text-center">BankCard</th>'+
				'<th width="80px" class="text-center">Statistics</th>'+
				'<th width="120px" class="text-center">Recharge poundage</th>'+
				'<th width="120px" class="text-center">Withdraw poundage</th>'+
				'<th width="120px" class="text-center">Createtime</th>'+
				'<th width="80px" class="text-center">Status</th>'+
				'<th width="120px" class="text-center">Operation</th>'+
				'</tr>';
			for(var i=0;i<r.data.length;i++){
				var processHtml = '<a class="tradeBtn mr-5">trade</a> <a class="editBtn mr-5">edit</a>';
				if(r.data[i].status == 'normal'){
					processHtml = processHtml + '<a class="closeBtn">close</a>'
				}else if(r.data[i].status == 'disable'){
					processHtml = processHtml + '<a class="openBtn">open</a>'
				}
				
				tableHtml += '<tr>'
					+'<td class="text-left">'+r.data[i].code+'</td>'
					+'<td class="text-center">'+r.data[i].name+'</td>'
					+'<td class="text-center"><a href="companyChannelList.html?company='+r.data[i].uuid+'">manage</a></td>'
					+'<td class="text-center"><a href="companyAdminList.html?company='+r.data[i].uuid+'">manage</a></td>'
					+'<td class="text-center"><a href="companyHistoryList.html?company='+r.data[i].uuid+'">check</a></td>'
					+'<td class="text-center"><a href="companyBankcardList.html?company='+r.data[i].uuid+'">check</a></td>'
					+'<td class="text-center"><a href="statisticsCompanyDaily.html?uuid='+r.data[i].uuid+'">check</a></td>'
					+'<td class="text-center">'+(r.data[i].rechargePoundageRate == null ? ((r.data[i].rechargePoundage/100).toFixed(2)) : (r.data[i].rechargePoundageRate*100+'%'))+'</td>'
					+'<td class="text-center">'+(r.data[i].withdrawPoundageRate == null ? ((r.data[i].withdrawPoundage/100).toFixed(2)) : (r.data[i].withdrawPoundageRate*100+'%'))+'</td>'
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
				tableHtml += '<tr><td colspan="9" align="center">No relevant data</td></tr>';
			}
			$("table").html(tableHtml);
			$(".openBtn").click(function(){
				var dataUuid = $(this).parent().attr("data-id");
				layer.confirm('Are you sure to start the store',{
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
				layer.confirm('Are you sure to stop the store',{
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
				layer.open({
				  type: 2, 
				  title:false,
				  area: ['800px', '600px'],
				  content:['companyDetail.html?uuid='+dataUuid]
				}); 
				return false;
			});
			$(".tradeBtn").click(function(){
				var dataUuid = $(this).parent().attr("data-id");
				layer.open({
				  type: 2, 
				  title:false,
				  area: ['800px', '660px'],
				  content:['companyTradeDetail.html?uuid='+dataUuid]
				}); 
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