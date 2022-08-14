/**
 * 商户流水
 */
var pageNum = 1,
    pageSize = 50;
var typeObj = {
	'user_recharge':'用户充值',
	'user_withdraw':'用户提现',
	'company_recharge':'商户注资',
	'company_withdraw':'商户结算'
}
var company = (url('?company') != null) ? url('?company') : '';

$(function(){
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
//渠道列表
function channelList(){
	$.ajax({
        url: '../system/channel/list',
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
			$("select[name='channel']").html(tableHtml);
		} 
    })
}

function getList(){
	var datas = {
			'company':company,
        	'pageSize':pageSize,
        	'pageNum':pageNum
        }
	if($('select[name=channel]').val() != ''){
		datas['channel'] = $('select[name=channel]').val();
	}
	if($('select[name=type]').val() != ''){
		datas['type'] = $('select[name=type]').val();
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
        url: '../system/companyAccountHistory/list',
        type: 'get',
        async:false,
        data: datas
    }).done(function(r) {
		if (r.status == "SUCCESS") {
			var tableHtml = '<tr>'+
				'<th width="120px" class="text-left">交易类型</th>'+
				'<th width="200px" class="text-center">平台订单号</th>'+
				'<th width="150px" class="text-center">商户</th>'+
				'<th width="200px" class="text-center">商户订单号</th>'+
				'<th width="100px" class="text-center">渠道</th>'+
				'<th width="150px" class="text-center">渠道账户</th>'+
				'<th width="100px" class="text-center">交易总额</th>'+
				'<th width="60px" class="text-center">手续费</th>'+
				'<th width="100px" class="text-center">商户余额</th>'+
				'<th width="150px" class="text-center">交易时间</th>'+
				'<th width="100px" class="text-center">操作</th>'+
				'</tr>';
			for(var i=0;i<r.data.length;i++){
				var processHtml = '';
				
				
				tableHtml += '<tr>'
					+'<td class="text-left">'+typeObj[r.data[i].type]+'</td>'
					+'<td class="text-center">'+r.data[i].orderNum+'</td>'
					+'<td class="text-center">'+r.data[i].companyName+'</td>'
					+'<td class="text-center">'+stringValue(r.data[i].companyOrderNum)+'</td>'
					+'<td class="text-center">'+stringValue(r.data[i].channelName)+'</td>'
					+'<td class="text-center">'+stringValue(r.data[i].channelAccountName)+'</td>'
					+'<td class="text-center">'+(r.data[i].amount/100).toFixed(2)+'</td>'
					+'<td class="text-center">'+(r.data[i].poundage/100).toFixed(2)+'</td>'
					+'<td class="text-center">'+(r.data[i].balance/100).toFixed(2)+'</td>'
					+'<td class="text-center">'+formatDate(r.data[i].createtime)+'</td>'
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
	var channel = $('select[name=channel]').val();
	var type = $('select[name=type]').val();
	var orderNum = $('input[name=orderNum]').val();
	var companyOrderNum = $('input[name=companyOrderNum]').val();
	var starttime = '';
	var endtime ='';
	if($('input[name=createtime]').val() != ''){
		var ctimes = $('#createtime').val().split("-");
		starttime = ctims[0].trim();
		endtime = ctims[1].trim();
	}
	window.open('../system/companyAccountHistory/export?type='+type+'&company='+company+'&channel='+channel+
			'&orderNum='+orderNum+'&companyOrderNum='+companyOrderNum+'&starttime='+starttime+'&endtime='+endtime);
}