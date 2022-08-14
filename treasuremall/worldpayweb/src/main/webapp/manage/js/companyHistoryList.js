/**
 * 
 */
var pageNum = 1,
    pageSize = 50;
var typeObj = {
	'user_recharge':'user recharge',
	'user_withdraw':'user withdraw',
	'company_recharge':'store recharge',
	'company_withdraw':'store withdraw'
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
	
	laydate.render({
		elem: '.createtime'
		,type: 'datetime'
		,range: '-'
		,lang: 'en'
	});
});

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
			var tableHtml = '<option value="">all</option>';
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
				'<th width="120px" class="text-left">Type</th>'+
				'<th width="200px" class="text-center">OrderNum</th>'+
				'<th width="150px" class="text-center">Store</th>'+
				'<th width="200px" class="text-center">Store Order Number</th>'+
				'<th width="100px" class="text-center">Channel</th>'+
				'<th width="150px" class="text-center">Channel account</th>'+
				'<th width="70px" class="text-center">Currency</th>'+
				'<th width="100px" class="text-center">Amount</th>'+
				'<th width="80px" class="text-center">Poundage</th>'+
				'<th width="100px" class="text-center">Balance</th>'+
				'<th width="150px" class="text-center">Time</th>'+
				'<th width="100px" class="text-center">Operation</th>'+
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
					+'<td class="text-center">'+r.data[i].currency+'</td>'
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
				tableHtml += '<tr><td colspan="12" align="center">No relevant data</td></tr>';
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