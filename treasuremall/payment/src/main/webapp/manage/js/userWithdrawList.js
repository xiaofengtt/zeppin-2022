/**
 * 用户提现审核管理
 */
var pageNum = 1,
    pageSize = 50;
var statusObj = {
	'normal':'待处理',
	'checking':'待审核',
	'checked':'已审核',
	'fail':'处理失败',
	'close':'已关闭',
	'success':'已完成'
}
var transDataObj = {
	'10':{'title':'标题','bank':'所属银行','bankcard':'银行卡号','holder':'持卡人姓名'}
}
$(function(){
	companyList();
	channelList();
	getList();	
});
layui.use(['table', 'layer','laydate','element'], function(){
	var form = layui.form
		,layer = layui.layer
		,laydate = layui.laydate
		,$ = layui.jquery
		,element = layui.element;
	
	//日期
	laydate.render({
		elem: '.createtime'
		,type: 'datetime'
		,range: '-'
	});
});
//商户列表
function companyList(){
	$.ajax({
        url: '../system/company/list',
        type: 'get',
        async:false,
        data: {
        	'pageSize':'1000',
        	'pageNum':1
        }
    }).done(function(r) {
		if (r.status == "SUCCESS") {
			var tableHtml = '<option value="">全部</option>';
			for(var i=0;i<r.data.length;i++){
				tableHtml += '<option value="'+r.data[i].uuid+'">'+r.data[i].name+'</option>';
			}
			$("select[name='company']").html(tableHtml);
		} 
    })
}
//渠道列表
function channelList(){
	$.ajax({
        url: '../system/channel/list',
        type: 'get',
        async:false,
        data: {
        	'type':'withdraw',
        	'pageSize':'1000',
        	'pageNum':1
        }
    }).done(function(r) {
		if (r.status == "SUCCESS") {
			var tableHtml = '<option value="">全部</option>';
			for(var i=0;i<r.data.length;i++){
				tableHtml += '<option value="'+r.data[i].uuid+'">'+r.data[i].name+'</option>';
			}
			$("select[name='channel']").html(tableHtml);
		} 
    })
}
function setFail(uuid,reason){
	$.ajax({
        url: '../system/userWithdraw/fail',
        type: 'post',
        data: {
        	'uuid': uuid,
        	'reason': reason
        }
    }).done(function(r) {
    		if (r.status == "SUCCESS") {
    			layer.msg('处理成功', {
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
	if($('select[name=company]').val() != ''){
		datas['company'] = $('select[name=company]').val();
	}
	if($('select[name=channel]').val() != ''){
		datas['channel'] = $('select[name=channel]').val();
	}
	if($('select[name=status]').val() != ''){
		datas['status'] = $('select[name=status]').val();
	}
	if($('input[name=orderNum]').val() != ''){
		datas['orderNum'] = $('input[name=orderNum]').val();
	}
	if($('input[name=companyOrderNum]').val() != ''){
		datas['companyOrderNum'] = $('input[name=companyOrderNum]').val();
	}
	if($('input[name=createtime]').val() != ''){
		var ctimes = $('input[name=createtime]').val().split(" - ");
		datas['starttime'] = ctimes[0];
		datas['endtime'] = ctimes[1];
	}
	$.ajax({
        url: '../system/userWithdraw/list',
        type: 'get',
        async:false,
        data: datas
    }).done(function(r) {
		if (r.status == "SUCCESS") {
			var tableHtml = '<tr>'+
				'<th width="200px" class="text-left">平台订单号</th>'+
				'<th width="150px" class="text-center">商户</th>'+
				'<th width="200px" class="text-center">商户订单号</th>'+
				'<th width="100px" class="text-center">提现渠道</th>'+
				'<th width="150px" class="text-center">渠道账户</th>'+
				'<th width="100px" class="text-center">提现总额</th>'+
				'<th width="100px" class="text-center">手续费</th>'+
				'<th width="100px" class="text-center">实际金额</th>'+
				'<th width="120px" class="text-center">商户当前余额</th>'+
				'<th width="120px" class="text-center">商户当前冻结</th>'+
				'<th width="220px" class="text-left">提现信息</th>'+
				'<th width="100px" class="text-center">下单时间</th>'+
				'<th width="100px" class="text-center">处理时间</th>'+
				'<th width="80px" class="text-center">状态</th>'+
				'<th width="200px" class="text-center">操作</th>'+
				'</tr>';
			for(var i=0;i<r.data.length;i++){
				var processHtml = '';
				if(r.data[i].status == 'checking'){
					processHtml = '<a class="checkBtn mr-5">审核</a> <a class="failBtn mr-5">设为失败</a>'
				}else if(r.data[i].status == 'checked'){
					processHtml = '<a class="successBtn mr-5">设为成功</a> <a class="failBtn mr-5">设为失败</a>'
				}
				var transData = '';
				var transDataMap = transDataObj[r.data[i].channelCode];
				for(var key in transDataMap){
					transData = transData + transDataMap[key] + '：' + (r.data[i].transDataMap[key] == null ? "" : r.data[i].transDataMap[key]) + '</br>'; 
				}
				
				tableHtml += '<tr>'
					+'<td class="text-center">'+r.data[i].orderNum+'</td>'
					+'<td class="text-center">'+r.data[i].companyName+'</td>'
					+'<td class="text-center">'+r.data[i].companyOrderNum+'</td>'
					+'<td class="text-center">'+r.data[i].channelName+'</td>'
					+'<td class="text-center">'+(r.data[i].channelAccountName == null ? '未分配' : r.data[i].channelAccountName)+'</td>'
					+'<td class="text-center">'+(r.data[i].totalAmount/100).toFixed(2)+'</td>'
					+'<td class="text-center">'+(r.data[i].poundage/100).toFixed(2)+'</td>'
					+'<td class="text-center">'+(r.data[i].amount/100).toFixed(2)+'</td>'
					+'<td class="text-center">'+(r.data[i].companyBalance/100).toFixed(2)+'</td>'
					+'<td class="text-center">'+(r.data[i].companyBalanceLock/100).toFixed(2)+'</td>'
					+'<td class="text-left">'+transData+'</td>'
					+'<td class="text-center">'+formatDate(r.data[i].createtime)+'</td>'
					+'<td class="text-center">'+(r.data[i].operattime == null ? '' : formatDate(r.data[i].operattime))+'</td>'
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
				tableHtml += '<tr><td colspan="15" align="center">暂无相关数据</td></tr>';
			}
			$("table").html(tableHtml);
			$(".checkBtn").click(function(){
				var dataUuid = $(this).parent().attr("data-id");
				layer.open({
					type: 2, 
					title:false,
					area: ['800px', '600px'],
					content:['userWithdrawProcess.html?type=check&uuid='+dataUuid,'no']
				}); 
				return false;
			});
			$(".successBtn").click(function(){
				var dataUuid = $(this).parent().attr("data-id");
				layer.open({
					type: 2, 
					title:false,
					area: ['800px', '600px'],
					content:['userWithdrawProcess.html?type=success&uuid='+dataUuid,'no']
				}); 
				return false;
			});
			$(".failBtn").click(function(){
				var dataUuid = $(this).parent().attr("data-id");
				layer.open({
					type: 2, 
					title:false,
					area: ['800px', '600px'],
					content:['userWithdrawProcess.html?type=fail&uuid='+dataUuid,'no']
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
function exportFile(){
	var company = $('select[name=company]').val();
	var channel = $('select[name=channel]').val();
	var status = $('select[name=status]').val();
	var orderNum = $('input[name=orderNum]').val();
	var companyOrderNum = $('input[name=companyOrderNum]').val();
	var starttime = '';
	var endtime ='';
	if($('#createtime').val() != ''){
		var ctimes = $('#createtime').val().split("-");
		starttime = ctims[0].trim();
		endtime = ctims[1].trim();
	}
	window.open('../system/userWithdraw/export?status='+status+'&company='+company+'&channel='+channel+'&orderNum='+orderNum+
			'&starttime='+starttime+'&endtime='+endtime);
}