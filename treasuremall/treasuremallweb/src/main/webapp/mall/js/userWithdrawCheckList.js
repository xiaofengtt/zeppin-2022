/**
 * 用户提现审核管理
 */
var pageNum = (url('?pagenum') != null) ? url('?pagenum') : 1,
    pageSize = (url('?pagesize') != null) ? url('?pagesize') : 50;
var flagSubmit = true;
function getStatus(status){
	switch (status){
		case 'normal':
			return '申请中'
		case 'checking':
			return '处理中'
		case 'checked':
			return '已提现'
		case 'cancel':
			return '已取消'
		case 'close':
			return '已关闭'
		case 'fail':
			return '失败'
		default:
			return''
	}
}
$(function(){
	getList();	
});
function cancel(uuid,status,remark){
	$.ajax({
        url: '../back/userWithdraw/cancel',
        type: 'post',
        data: {
        	'uuid': uuid,
        	'status': status,
        	'remark': remark
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
	if($('select[name=status]').val() != ''){
		datas['status'] = $('select[name=status]').val();
	}
	if($('input[name=orderNum]').val() != ''){
		datas['orderNum'] = $('input[name=orderNum]').val();
	}
	if($('input[name=frontUserShowId]').val() != ''){
		datas['frontUserShowId'] = $('input[name=frontUserShowId]').val();
	}
	if($('#starttime').val() != ''){
		datas['starttime'] = $('#starttime').val();
	}
	if($('#endtime').val() != ''){
		datas['endtime'] = $('#endtime').val();
	}
	$.ajax({
        url: '../back/userWithdraw/list',
        type: 'get',
        async:false,
        data: datas
    }).done(function(r) {
		if (r.status == "SUCCESS") {
			var tableHtml = '<tr>'+
				'<th width="50px" class="text-center">序号</th>'+
				'<th width="90px" class="text-center">订单编号</th>'+
				'<th width="100px" class="text-center">用户</th>'+
				'<th width="100px" class="text-center">提现金额/元</th>'+
				'<th width="80px" class="text-center">手续费/元</th>'+
				'<th width="100px" class="text-center">到账金额/元</th>'+
				'<th width="200px" class="text-center">用户提现信息</th>'+
				'<th width="150px" class="text-center">用户统计</th>'+
				'<th width="60px" class="text-center">处理人</th>'+
				'<th width="90px" class="text-center">处理时间</th>'+
				'<th width="90px" class="text-center">提现时间</th>'+
				'<th width="60px" class="text-center">状态</th>'+
				'<th width="80px" class="text-center">用户余额</th>'+
				'<th width="80px" class="text-center">操作</th>'+
				'</tr>';
			for(var i=0;i<r.data.length;i++){
				var categoryHtml = '';
				var registerDay = formatDateDay(new Date().getTime()-r.data[i].registertime);
				var classColor = "";
				if(r.data[i].status == 'fail'){
					categoryHtml = '<a class="cancelBtn">提现失败</a>'
				}else if(r.data[i].status == 'normal'){
					categoryHtml = '<a class="handleBtn">审核</a>'
				}
				if(r.data[i].status=="normal"){
					classColor = "color-green"
				}else if(r.data[i].status=="checked"){
					classColor = "color-blue"
				}else if(r.data[i].status=="cancel"){
					classColor = ""
				}else if(r.data[i].status=="close"||r.data[i].status=="fail"){
					classColor = ""
				}else{
					classColor = "color-blue"
				}
				tableHtml += '<tr data-showId="'+r.data[i].frontUser+'">'
					+'<td class="text-center">'+(pageSize*(pageNum-1)+i+1)+'</td>'
					+'<td class="text-center" style="word-break:break-all">'+r.data[i].orderNum+'</td>'
					+'<td class="text-left showDetail">ID:'+r.data[i].frontUserShowId+'</br>昵称：'+r.data[i].frontUserNickname+'</td>'
					+'<td class="text-center">'+(r.data[i].amount).toFixed(2)+'</td>'
					+'<td class="text-center">'+(r.data[i].poundage).toFixed(2)+'</td>'
					+'<td class="text-center">'+(r.data[i].actualAmount).toFixed(2)+'</td>'
					+'<td class="text-left">姓名：'+r.data[i].frontUserAccountHolder+'</br>卡号：'+r.data[i].frontUserAccountNumber+'</br>开户行：'+(r.data[i].frontUserBankName==null?'':r.data[i].frontUserBankName)+'</td>'
					+'<td class="text-left"><p class="'+(registerDay<7?"color-red":"")+'">注册天数:'+registerDay+'天</p>'
					+'<p class="'+(r.data[i].totalPayment<100?"color-red":"")+'">参与量：'+r.data[i].totalPayment+'金币</p>'
					+'<p class="'+(r.data[i].totalRecharge<r.data[i].totalWithdraw+r.data[i].frontUserBalance+r.data[i].totalDelivery?"color-red":"")+'">充值量：'+(r.data[i].totalRecharge).toFixed(2)+'元</p>'
					+'<p class="'+(r.data[i].totalPayment<r.data[i].totalWithdraw*1.5?"color-red":"")+'">提现量：'+(r.data[i].totalWithdraw).toFixed(2)+'元</p></td>'
					+'<td class="text-center">'+(r.data[i].operatorName==null?'':r.data[i].operatorName)+'</td>'
					+'<td class="text-center">'+(r.data[i].operattime==null?'':formatDate(r.data[i].operattime))+'</td>'
					+'<td class="text-center">'+formatDate(r.data[i].createtime)+'</td>'
					+'<td class="text-center '+classColor+'">'+getStatus(r.data[i].status)+'</td>'
					+'<td class="text-center">'+(r.data[i].frontUserBalance+r.data[i].frontUserBalanceLock).toFixed(2)+'</td>'
					+'<td class="text-center" data-id="'+r.data[i].uuid+'">'+categoryHtml+'</td>'
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
			    $(".ui-paging-container .ui-paging-toolbar").append("<b class='paging-total'>共"+r.totalResultCount+"条</b>")
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
				tableHtml += '<tr><td colspan="12" align="center">暂无相关数据</td></tr>';
			}
			$("table").html(tableHtml);
			$(".cancelBtn").click(function(){
				var dataUuid = $(this).parent().attr("data-id");
				layer.prompt({
					title: '取消提现订单',
					formType: 0,
					btnAlign: 'c',
					yes: function(value, index, elem){
						cancel(dataUuid,'cancel',$(".layui-layer-input").val());
					}
				});
				return false;
			});
			$(".handleBtn").click(function(){
				$.ajax({
			        url: '../back/userWithdraw/get',
			        type: 'get',
			        async:false,
			        data: {
			        	'uuid':$(this).parent().attr("data-id")
			        }
			    }).done(function(r) {
			    	if (r.status == "SUCCESS") {
			    		window.localStorage.setItem("userWithdraw",JSON.stringify(r.data));
			    		layer.open({
							type: 2, 
							title:false, 
							area: ['600px', '530px'],
							content: ['userWithdrawCheckDetail.html'] 
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
			    })
			});
			$(".showDetail").click(function(){
				layer.open({
					type: 2, 
					title:false, 
					area: ['90%', '90%'],
					content: ['userDetail.html?uuid='+$(this).parent().attr("data-showId")] 
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
	var layerIndex = layer.open({
		type: 3
	});
	if(flagSubmit == false) {
		return false;
	}
	flagSubmit = false;
	setTimeout(function() {
		flagSubmit = true;
	}, 3000);
	var status = $('select[name=status]').val();
	var orderNum = $('input[name=orderNum]').val()
	var frontUserShowId = $('input[name=frontUserShowId]').val();
	var starttime = $('#starttime').val();
	var endtime = $('#endtime').val();
	var link = document.createElement('a');
	link.setAttribute("download", "");
	link.href = '../back/userWithdraw/export?status='+status+'&orderNum='+orderNum+'&frontUserShowId='+frontUserShowId+
				'&starttime='+starttime+'&endtime='+endtime;
	link.click();
	layer.close(layerIndex)
	return false;
//	window.open('../back/userWithdraw/export?status='+status+'&orderNum='+orderNum+'&frontUserShowId='+frontUserShowId+
//			'&starttime='+starttime+'&endtime='+endtime);
}
layui.use(['table', 'layer','laydate','element'], function(){
	var form = layui.form
		,layer = layui.layer
		,layedit = layui.layedit
		,laydate = layui.laydate
		,$ = layui.jquery
		,element = layui.element;
	
	laydate.render({
		elem: '#starttime',
		type: 'datetime',
		theme: '#3D99FF'
	});
	laydate.render({
		elem: '#endtime',
		type: 'datetime',
		theme: '#3D99FF'
	});
});