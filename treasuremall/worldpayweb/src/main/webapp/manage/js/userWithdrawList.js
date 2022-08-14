/**
 * 
 */
var pageNum = 1,
    pageSize = 50;
var statusObj = {
	'normal':'pending',
	'checking':'checking',
	'checked':'checked',
	'fail':'fail',
	'close':'close',
	'success':'success',
}
var transDataObj = {
	'10':{'bank':'bank','bankcard':'bankcard','holder':'cardholder'}
}
$(function(){
	companyList();
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
			var tableHtml = '<option value="">all</option>';
			for(var i=0;i<r.data.length;i++){
				tableHtml += '<option value="'+r.data[i].uuid+'">'+r.data[i].name+'</option>';
			}
			$("select[name='company']").html(tableHtml);
		} 
    })
}
function getList(){
	var datas = {
        	'pageSize':pageSize,
        	'pageNum':pageNum
        }
	if($('select[name=company]').val() != ''){
		datas['company'] = $('select[name=company]').val();
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
				'<th width="180px" class="text-left">OrderNum</th>'+
				'<th width="140px" class="text-center">Store</th>'+
				'<th width="150px" class="text-left">Store Order Number</th>'+
				'<th width="100px" class="text-center">Channel</th>'+
				'<th width="150px" class="text-center">Channel name</th>'+
				'<th width="70px" class="text-center">Currency</th>'+
				'<th width="100px" class="text-center">TotalAmount</th>'+
				'<th width="80px" class="text-center">Poundage</th>'+
				'<th width="90px" class="text-center">Amount</th>'+
				'<th width="180px" class="text-left">TransInfo</th>'+
				'<th width="140px" class="text-center">Order time</th>'+
				'<th width="60px" class="text-center">Status</th>'+
				'<th width="180px" class="text-center">Operation</th>'+
				'</tr>';
			for(var i=0;i<r.data.length;i++){
				var processHtml = '<a class="detailBtn mr-5">check</a>';
				if(r.data[i].status == 'checking'){
					processHtml = processHtml + '<a class="checkBtn mr-5">review</a> <a class="failBtn">set_failed</a>'
				}else if(r.data[i].status == 'checked'){
					processHtml = processHtml + '<a class="successBtn mr-5">set_success</a> <a class="failBtn">set_failed</a>'
				}else if(r.data[i].status == 'fail'){
					processHtml = processHtml + '<a class="checkBtn">review</a>'
				}
				var transData = '';
				var transDataMap = transDataObj[r.data[i].channelCode];
				for(var key in transDataMap){
					transData = transData + transDataMap[key] + '：' + stringValue(r.data[i].transDataMap[key])+ ' '; 
				}
				
				tableHtml += '<tr>'
					+'<td class="text-left">'+r.data[i].orderNum+'</td>'
					+'<td class="text-center">'+r.data[i].companyName+'</td>'
					+'<td class="text-left">'+r.data[i].companyOrderNum+'</td>'
					+'<td class="text-center">'+r.data[i].channelName+'</td>'
					+'<td class="text-center">'+stringValue(r.data[i].channelAccountName,'unallocated')+'</td>'
					+'<td class="text-center">'+r.data[i].currency+'</td>'
					+'<td class="text-center">'+(r.data[i].totalAmount/100).toFixed(2)+'</td>'
					+'<td class="text-center">'+(r.data[i].poundage/100).toFixed(2)+'</td>'
					+'<td class="text-center">'+(r.data[i].amount/100).toFixed(2)+'</td>'
					+'<td class="text-left"><div class="table-more"><b class="singleLine">'+transData+'</b><p class="moreBtn"></p></div></td>'
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
				tableHtml += '<tr><td colspan="13" align="center">No relevant data</td></tr>';
			}
			$("table").html(tableHtml);
			$(".detailBtn").click(function(){
				var dataUuid = $(this).parent().attr("data-id");
				layer.open({
					type: 2, 
					title:false,
					area: ['800px', '600px'],
					content:['userWithdrawProcess.html?type=detail&uuid='+dataUuid,'no']
				}); 
				return false;
			});
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
			$(".moreBtn").parent().parent().click(function(){
				if($(this).find("b").hasClass("singleLine")){
					$(this).find("b").removeClass("singleLine");
					$(this).find("p").removeClass("rotate1").addClass("rotate");
				}else{
					$(this).find("b").addClass("singleLine");
					$(this).find("p").removeClass("rotate").addClass("rotate1");
				}
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
	var status = $('select[name=status]').val();
	var orderNum = $('input[name=orderNum]').val();
	var companyOrderNum = $('input[name=companyOrderNum]').val();
	var starttime = '';
	var endtime ='';
	if($('input[name=createtime]').val() != ''){
		var ctimes = $('#createtime').val().split("-");
		starttime = ctims[0].trim();
		endtime = ctims[1].trim();
	}
	window.open('../system/userWithdraw/export?status='+status+'&company='+company+
			'&orderNum='+orderNum+'&companyOrderNum='+companyOrderNum+'&starttime='+starttime+'&endtime='+endtime);
}