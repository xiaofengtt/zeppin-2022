/**
 * 商户交易审核管理
 */
var pageNum = 1,
    pageSize = 50;
var statusObj = {
	'checking':'待审核',
	'checked':'已审核',
	'fail':'处理失败',
	'close':'已关闭',
	'success':'已完成'
}
var typeObj = {
	'recharge':'注资',
	'withdraw':'提现'
}
$(function(){
	getList();	
	$(".addBtn").click(function(){
		layer.open({
		  type: 2, 
		  title:false,
		  area: ['600px', '550px'],
		  content:['companyTradeDetail.html']
		}); 
	});
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

function getList(){
	var datas = {
        	'pageSize':pageSize,
        	'pageNum':pageNum
        }
	if($('select[name=status]').val() != ''){
		datas['status'] = $('select[name=status]').val();
	}
	if($('input[name=orderNum]').val() != ''){
		datas['orderNum'] = $('input[name=orderNum]').val();
	}
	if($('input[name=createtime]').val() != ''){
		var ctimes = $('input[name=createtime]').val().split(" - ");
		datas['starttime'] = ctimes[0];
		datas['endtime'] = ctimes[1];
	}
	$.ajax({
        url: '../store/companyTrade/list',
        type: 'get',
        async:false,
        data: datas
    }).done(function(r) {
		if (r.status == "SUCCESS") {
			var tableHtml = '<tr>'+
				'<th width="100px" class="text-left">交易类型</th>'+
				'<th width="200px" class="text-center">平台订单号</th>'+
				'<th width="100px" class="text-center">交易总额</th>'+
				'<th width="80px" class="text-center">手续费</th>'+
				'<th width="100px" class="text-center">实际金额</th>'+
				'<th width="200px" class="text-left">商户银行卡</th>'+
				'<th width="220px" class="text-left">交易信息</th>'+
				'<th width="150px" class="text-center">下单时间</th>'+
				'<th width="150px" class="text-center">处理时间</th>'+
				'<th width="80px" class="text-center">状态</th>'+
				'<th width="150px" class="text-center">操作</th>'+
				'</tr>';
			for(var i=0;i<r.data.length;i++){
				var processHtml = '<a class="detailBtn color-blue mr-5">查看</a>';
				if(r.data[i].status == 'checking'){
					processHtml = processHtml + '<a class="closeBtn color-red">关闭订单</a>'
				}else if(r.data[i].status == 'fail'){
					processHtml = processHtml + '<a class="closeBtn color-red mr-5">关闭订单</a> <a class="retryBtn color-green">重试</a>'
				}
				var bankcardHtml = '';
				if(r.data[i].companyBankcard != null){
					bankcardHtml = r.data[i].companyBankcardBankName + ' 尾号：' + getEnd(r.data[i].companyBankcardNum,4)
				}
				tableHtml += '<tr>'
					+'<td class="text-left">'+typeObj[r.data[i].type]+'</td>'
					+'<td class="text-center">'+r.data[i].orderNum+'</td>'
					+'<td class="text-center">'+(r.data[i].totalAmount/100).toFixed(2)+'</td>'
					+'<td class="text-center">'+(r.data[i].poundage/100).toFixed(2)+'</td>'
					+'<td class="text-center">'+(r.data[i].amount/100).toFixed(2)+'</td>'
					+'<td class="text-left">'+ bankcardHtml +'</td>'
					+'<td class="text-left"><div class="table-more"><b class="singleLine">'+r.data[i].data+'</b><p class="moreBtn"></p></div></td>'
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
				tableHtml += '<tr><td colspan="11" align="center">暂无相关数据</td></tr>';
			}
			$("table").html(tableHtml);
			$(".detailBtn").click(function(){
				var dataUuid = $(this).parent().attr("data-id");
				layer.open({
					type: 2, 
					title:false,
					area: ['800px', '600px'],
					content:['tradeProcess.html?type=detail&uuid='+dataUuid]
				}); 
				return false;
			});
			$(".closeBtn").click(function(){
				var dataUuid = $(this).parent().attr("data-id");
				layer.open({
					type: 2, 
					title:false,
					area: ['800px', '600px'],
					content:['tradeProcess.html?type=close&uuid='+dataUuid]
				}); 
				return false;
			});
			$(".retryBtn").click(function(){
				var dataUuid = $(this).parent().attr("data-id");
				layer.open({
					type: 2, 
					title:false,
					area: ['800px', '550px'],
					content:['tradeProcess.html?type=retry&uuid='+dataUuid,'no']
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
	var status = $('select[name=status]').val();
	var orderNum = $('input[name=orderNum]').val();
	var starttime = '';
	var endtime ='';
	if($('input[name=createtime]').val() != ''){
		var ctimes = $('input[name=createtime]').val().split(" - ");
		starttime = ctimes[0];
		endtime = ctimes[1];
	}
	window.open('../store/companyTrade/export?status='+status+'&orderNum='+orderNum+
			'&starttime='+starttime+'&endtime='+endtime);
}