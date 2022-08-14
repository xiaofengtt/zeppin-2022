/**
 * 机器人管理
 */
var pageNum = 1,
    pageSize = 50;
function getStatus(status){
	switch (status){
		case 'normal':
			return '待审核'
		case 'checking':
			return '处理中'
		case 'checked':
			return '已完成'
		case 'cancel':
			return '已取消'
		case 'close':
			return '已关闭'
		default:
			return''
	}
}
$(function(){
	getList();	
});
function processSetting(uuid,status,remark){
	$.ajax({
        url: '../back/userRecharge/statusSetting',
        type: 'post',
        data: {
        	'uuid': uuid,
        	'status': status,
        	'remark': remark
        }
    }).done(function(r) {
    		if (r.status == "SUCCESS") {
    			layer.msg('手工处理成功', {
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
        url: '../back/userRecharge/list',
        type: 'get',
        async:false,
        data: datas
    }).done(function(r) {
		if (r.status == "SUCCESS") {
			var tableHtml = '<tr>'+
				'<th width="5%" class="text-center">序号</th>'+
				'<th width="8%" class="text-center">订单编号</th>'+
				'<th width="8%" class="text-center">用户</th>'+
				'<th width="8%" class="text-center">充值金额/美元</th>'+
				'<th width="12%" class="text-center">充值信息</th>'+
				'<th width="12%" class="text-center">备注</th>'+
				'<th width="6%" class="text-center">充值金币</th>'+
				'<th width="8%" class="text-center">处理时间</th>'+
				'<th width="8%" class="text-center">充值时间</th>'+
				'<th width="5%" class="text-center">状态</th>'+
				'<th width="6%" class="text-center">用户余额</th>'+
				'<th class="text-center">操作</th>'+
				'</tr>';
			for(var i=0;i<r.data.length;i++){
				var categoryHtml = '';
				if(r.data[i].status == 'normal' || r.data[i].status == 'checking'){
					categoryHtml = '<a class="processBtn mr-5">手工处理</a>'
				}
				tableHtml += '<tr data-showId="'+r.data[i].frontUser+'">'
					+'<td class="text-center">'+(pageSize*(pageNum-1)+i+1)+'</td>'
					+'<td class="text-center">'+r.data[i].orderNum+'</td>'
					+'<td class="text-left showDetail">ID:'+r.data[i].frontUserShowId+'</br>昵称：'+r.data[i].frontUserNickname+'</td>'
					+'<td class="text-center">'+(r.data[i].amount).toFixed(2)+'</td>'
					+'<td class="text-left">支付类型：'+r.data[i].capitalPlatform+'</br>渠道：'+r.data[i].accountName+'</br>账号：'+(r.data[i].accountNum==null?'':r.data[i].accountNum)+'</td>'
					+'<td class="text-center">'+r.data[i].remark+'</td>'
					+'<td class="text-center">'+(r.data[i].increaseDAmount).toFixed(2)+'</td>'
					+'<td class="text-center">'+(r.data[i].operattime==null?'':formatDate(r.data[i].operattime))+'</td>'
					+'<td class="text-center">'+formatDate(r.data[i].createtime)+'</td>'
					+'<td class="text-center">'+getStatus(r.data[i].status)+'</td>'
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
			$(".processBtn").click(function(){
				var dataUuid = $(this).parent().attr("data-id");
				layer.prompt({
					title: '手工处理',
					formType: 0,
					btnAlign: 'c',
					btn: ['充值成功','充值失败'],
					yes: function(value, index, elem){
						processSetting(dataUuid,'checked',$(".layui-layer-input").val());
					},
					btn2: function(value, index, elem){
						processSetting(dataUuid,'cancel',$(".layui-layer-input").val());
					}
				});
				return false;
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
	var status = $('select[name=status]').val();
	var orderNum = $('input[name=orderNum]').val()
	var frontUserShowId = $('input[name=frontUserShowId]').val();
	var starttime = $('#starttime').val();
	var endtime = $('#endtime').val();
	window.open('../back/userRecharge/export?status='+status+'&orderNum='+orderNum+'&frontUserShowId='+frontUserShowId+
			'&starttime='+starttime+'&endtime='+endtime);
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