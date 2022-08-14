/**
 * 用户充值审核管理
 */
var pageNum = 1,
    pageSize = 50;
var statusObj = {
	'normal':'未付款',
	'checking':'待审核',
	'checked':'已审核',
	'fail':'处理失败',
	'close':'已关闭',
	'success':'已完成'
}
var channelUuid = (url('?channel') != null) ? url('?channel') : 'a4ac649b-43f3-4f85-888c-dd85bbca25a6';
var transDataObj = {
	'01':{'title':'标题'},
	'02':{'orderid':'支付宝订单号'},
	'03':{'title':'标题'},
	'04':{'orderid':'转账单号'},
	'05':{'remark':'备注内容'},
	'06':{'remark':'备注内容'},
	'07':{'holder':'转账人'}
}
$(function(){
	companyList();
	getTypeList();
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

$('#channelTab li').click(function(){
	channelUuid = $(this).attr('data-uuid');
	getTypeList()
	getList()
})

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
//待审核列表
function getTypeList(){
	$.ajax({
        url: '../system/userRecharge/typeList',
        type: 'get',
        async:false,
        data: {
        	'pageSize':'1000',
        	'pageNum':1
        }
    }).done(function(r) {
		if (r.status == "SUCCESS") {
			for(var i in r.data){
				$('#' + r.data[i].status + 'Count').html(r.data[i].count)
			}
		} 
    })
}

function getList(){
	var datas = {
			'channel':channelUuid,
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
	if($('input[name=transData]').val() != ''){
		datas['transData'] = $('input[name=transData]').val();
	}
	if($('input[name=createtime]').val() != ''){
		var ctimes = $('input[name=createtime]').val().split(" - ");
		datas['starttime'] = ctimes[0];
		datas['endtime'] = ctimes[1];
	}
	$.ajax({
        url: '../system/userRecharge/list',
        type: 'get',
        async:false,
        data: datas
    }).done(function(r) {
		if (r.status == "SUCCESS") {
			var tableHtml = '<tr>'+
				'<th width="200px" class="text-left">平台订单号</th>'+
				'<th width="150px" class="text-center">商户</th>'+
				'<th width="200px" class="text-center">商户订单号</th>'+
				'<th width="150px" class="text-center">渠道账户</th>'+
				'<th width="100px" class="text-center">充值总额</th>'+
				'<th width="80px" class="text-center">手续费</th>'+
				'<th width="100px" class="text-center">实际金额</th>'+
				'<th width="200px" class="text-left">充值信息</th>'+
				'<th width="150px" class="text-center">下单时间</th>'+
				'<th width="80px" class="text-center">状态</th>'+
				'<th width="150px" class="text-center">操作</th>'+
				'</tr>';
			for(var i=0;i<r.data.length;i++){
				var processHtml = '<a class="detailBtn mr-5">查看</a>';
				if('a4ac649b-43f3-4f85-888c-dd85bbca25a6' == channelUuid || '9298fcd6-36ee-41b2-8c99-31a7334d93dc' == channelUuid 
						|| 'd013e88e-de4b-4633-bd39-91a946137e9a' == channelUuid || 'f2f128f4-7ed6-421e-bad0-9ad9ed874e3a' == channelUuid
						|| 'ab0f3c59-669e-4950-8cdf-9087ed298c23' == channelUuid){
					if(r.data[i].status == 'normal' || r.data[i].status == 'checking' || r.data[i].status == 'fail' || r.data[i].status == 'close'){
						processHtml = processHtml + '<a class="successBtn mr-5">设为成功</a>'
					}
					if(r.data[i].status == 'normal' || r.data[i].status == 'checking' || r.data[i].status == 'fail'){
						processHtml = processHtml + '<a class="closeBtn mr-5">关闭订单</a>'
					}
				}else{
					if(r.data[i].status == 'normal' || r.data[i].status == 'checking' || r.data[i].status == 'fail'){
						processHtml = processHtml + '<a class="successBtn mr-5">设为成功</a> <a class="failBtn mr-5">设为失败</a>'
					}
				}
				var transData = '';
				var transDataMap = transDataObj[r.data[i].channelCode];
				for(var key in transDataMap){
					transData = transData + transDataMap[key] + '：' + stringValue(r.data[i].transDataMap[key]) + '</br>'; 
				}
				
				tableHtml += '<tr>'
					+'<td class="text-center">'+r.data[i].orderNum+'</td>'
					+'<td class="text-center">'+r.data[i].companyName+'</td>'
					+'<td class="text-center">'+r.data[i].companyOrderNum+'</td>'
					+'<td class="text-center">'+stringValue(r.data[i].channelAccountName,"未分配")+'</td>'
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
				tableHtml += '<tr><td colspan="11" align="center">暂无相关数据</td></tr>';
			}
			$("table").html(tableHtml);
			$(".detailBtn").click(function(){
				var dataUuid = $(this).parent().attr("data-id");
				layer.open({
					type: 2, 
					title:false,
					area: ['800px', '600px'],
					content:['userRechargeProcess.html?type=detail&uuid='+dataUuid,'no']
				}); 
				return false;
			});
			$(".successBtn").click(function(){
				var dataUuid = $(this).parent().attr("data-id");
				layer.open({
					type: 2, 
					title:false,
					area: ['800px', '600px'],
					content:['userRechargeProcess.html?type=success&uuid='+dataUuid,'no']
				}); 
				return false;
			});
			$(".failBtn").click(function(){
				var dataUuid = $(this).parent().attr("data-id");
				layer.open({
					type: 2, 
					title:false,
					area: ['800px', '600px'],
					content:['userRechargeProcess.html?type=fail&uuid='+dataUuid,'no']
				}); 
				return false;
			});
			$(".closeBtn").click(function(){
				var dataUuid = $(this).parent().attr("data-id");
				layer.open({
					type: 2, 
					title:false,
					area: ['800px', '600px'],
					content:['userRechargeProcess.html?type=close&uuid='+dataUuid,'no']
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
	window.open('../system/userRecharge/export?status='+status+'&company='+company+'&channel='+channelUuid+
			'&orderNum='+orderNum+'&companyOrderNum='+companyOrderNum+'&starttime='+starttime+'&endtime='+endtime);
}