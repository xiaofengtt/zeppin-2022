/**
 * 商户管理
 */
var pageNum = 1,
    pageSize = 50;
var statusObj = {
	'normal':'正常',
	'disable':'停用'
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
				'<th width="150px" class="text-left">商户编码</th>'+
				'<th width="150px" class="text-center">商户名称</th>'+
				'<th width="80px" class="text-center">渠道管理</th>'+
				'<th width="80px" class="text-center">管理员管理</th>'+
				'<th width="80px" class="text-center">商户流水</th>'+
				'<th width="80px" class="text-center">商户银行卡</th>'+
				'<th width="80px" class="text-center">财务统计</th>'+
				'<th width="150px" class="text-center">商户可用余额</th>'+
				'<th width="150px" class="text-center">商户冻结余额</th>'+
				'<th width="80px" class="text-center">注资手续费</th>'+
				'<th width="80px" class="text-center">提现手续费</th>'+
				'<th width="150px" class="text-center">开设时间</th>'+
				'<th width="80px" class="text-center">状态</th>'+
				'<th width="120px" class="text-center">操作</th>'+
				'</tr>';
			for(var i=0;i<r.data.length;i++){
				var processHtml = '<a class="tradeBtn mr-5">注资提现</a> <a class="editBtn mr-5">修改</a>';
				if(r.data[i].status == 'normal'){
					processHtml = processHtml + '<a class="closeBtn mr-5">停用</a>'
				}else if(r.data[i].status == 'disable'){
					processHtml = processHtml + '<a class="openBtn mr-5">启用</a>'
				}
				
				tableHtml += '<tr>'
					+'<td class="text-left">'+r.data[i].code+'</td>'
					+'<td class="text-center">'+r.data[i].name+'</td>'
					+'<td class="text-center"><a href="companyChannelList.html?company='+r.data[i].uuid+'">管理</a></td>'
					+'<td class="text-center"><a href="companyAdminList.html?company='+r.data[i].uuid+'">管理</a></td>'
					+'<td class="text-center"><a href="companyHistoryList.html?company='+r.data[i].uuid+'">查看</a></td>'
					+'<td class="text-center"><a href="companyBankcardList.html?company='+r.data[i].uuid+'">查看</a></td>'
					+'<td class="text-center"><a href="statisticsCompanyDaily.html?uuid='+r.data[i].uuid+'">查看</a></td>'
					+'<td class="text-center">'+(r.data[i].balance/100).toFixed(2)+'</td>'
					+'<td class="text-center">'+(r.data[i].balanceLock/100).toFixed(2)+'</td>'
					+'<td class="text-center">'+(r.data[i].rechargePoundageRate == null ? ((r.data[i].rechargePoundage/100).toFixed(2) + '元') : (r.data[i].rechargePoundageRate*100+'%'))+'</td>'
					+'<td class="text-center">'+(r.data[i].withdrawPoundageRate == null ? ((r.data[i].withdrawPoundage/100).toFixed(2) + '元') : (r.data[i].withdrawPoundageRate*100+'%'))+'</td>'
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
				tableHtml += '<tr><td colspan="11" align="center">暂无相关数据</td></tr>';
			}
			$("table").html(tableHtml);
			$(".openBtn").click(function(){
				var dataUuid = $(this).parent().attr("data-id");
				layer.confirm('是否确认启用该商户',{
					btnAlign: 'c',
					yes: function(index){
						changeStatus(dataUuid,'normal');
					}
				});
				return false;
			});
			$(".closeBtn").click(function(){
				var dataUuid = $(this).parent().attr("data-id");
				layer.confirm('是否确认停用该商户',{
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
				  area: ['600px', '600px'],
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