/**
 * 渠道账户管理
 */
var pageNum = 1,
    pageSize = 50;
var statusObj = {
	'normal':'正常',
	'disable':'停用',
	'suspend':'暂停'
}
var typeObj = {
	'recharge':'充值',
	'withdraw':'提现'
}
$(function(){
	channelList();
	getList();	
	$(".addBtn").click(function(){
		layer.open({
		  type: 2, 
		  title:false,
		  area: ['800px', '90%'],
		  content:['channelAccountDetail.html']
		}); 
	});
});
layui.use(['table', 'layer','element'], function(){
	var form = layui.form
		,layer = layui.layer
		,$ = layui.jquery
		,element = layui.element;
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
			var channel = stringValue(url('?channel'));
			
			var tableHtml = '<option value="">全部</option>';
			for(var i=0;i<r.data.length;i++){
				if(channel == r.data[i].uuid){
					tableHtml += '<option value="'+r.data[i].uuid+'" selected>'+r.data[i].name+'</option>';
				}else{
					tableHtml += '<option value="'+r.data[i].uuid+'">'+r.data[i].name+'</option>';
				}
			}
			$("select[name='channel']").html(tableHtml);
		} 
    })
}
function changeStatus(uuid,status){
	$.ajax({
        url: '../system/channelAccount/changeStatus',
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
	if($('select[name=type]').val() != ''){
		datas['type'] = $('select[name=type]').val();
	}
	if($('select[name=channel]').val() != ''){
		datas['channel'] = $('select[name=channel]').val();
	}
	$.ajax({
        url: '../system/channelAccount/list',
        type: 'get',
        async:false,
        data: datas
    }).done(function(r) {
		if (r.status == "SUCCESS") {
			var tableHtml = '<tr>'+
				'<th width="80px" class="text-left">类型</th>'+
				'<th width="120px" class="text-center">渠道名称</th>'+
				'<th width="200px" class="text-center">名称</th>'+
				'<th width="150px" class="text-center">账号</th>'+
				'<th width="120px" class="text-center">交易金额</th>'+
				'<th width="80px" class="text-center">手续费</th>'+
				'<th width="120px" class="text-center">单次限额</th>'+
				'<th width="100px" class="text-center">每日限额</th>'+
				'<th width="120px" class="text-center">总限额</th>'+
				'<th width="180px" class="text-center">转发地址</th>'+
				'<th width="150px" class="text-center">创建时间</th>'+
				'<th width="80px" class="text-center">状态</th>'+
				'<th width="100px" class="text-center">操作</th>'+
				'</tr>';
			for(var i=0;i<r.data.length;i++){
				var processHtml = '<a class="editBtn mr-5">修改</a>';
				if(r.data[i].status == 'normal'){
					processHtml = processHtml + '<a class="closeBtn mr-5">停用</a>'
				}else if(r.data[i].status == 'disable' ||r.data[i].status == 'suspend'){
					processHtml = processHtml + '<a class="openBtn mr-5">启用</a>'
				}
				
				
				tableHtml += '<tr>'
					+'<td class="text-left">'+typeObj[r.data[i].type]+'</td>'
					+'<td class="text-center">'+r.data[i].channelName+'</td>'
					+'<td class="text-center">'+r.data[i].name+'</td>'
					+'<td class="text-center">'+r.data[i].accountNum+'</td>'
					+'<td class="text-center">'+Math.abs(r.data[i].balance/100).toFixed(2)+'</td>'
					+'<td class="text-center">'+(r.data[i].poundage == null ? (r.data[i].poundageRate*100+'%') : ((r.data[i].poundage/100).toFixed(2) + '元'))+'</td>'
					+'<td class="text-center">'+(r.data[i].min/100).toFixed(2) +'-'+(r.data[i].max/100).toFixed(2) +'</td>'
					+'<td class="text-center">'+(r.data[i].dailyMax/100).toFixed(2)+'</td>'
					+'<td class="text-center">'+(r.data[i].totalMax/100).toFixed(2)+'</td>'
					+'<td class="text-center">'+r.data[i].transferUrl+'</td>'
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
				tableHtml += '<tr><td colspan="13" align="center">暂无相关数据</td></tr>';
			}
			$("table").html(tableHtml);
			$(".openBtn").click(function(){
				var dataUuid = $(this).parent().attr("data-id");
				layer.confirm('是否确认启用该账号',{
					btnAlign: 'c',
					yes: function(index){
						changeStatus(dataUuid,'normal');
					}
				});
				return false;
			});
			$(".closeBtn").click(function(){
				var dataUuid = $(this).parent().attr("data-id");
				layer.confirm('是否确认停用该账号',{
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
				  area: ['800px', '90%'],
				  content:['channelAccountDetail.html?uuid='+dataUuid]
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
function showPhotos(src) {
	layer.photos({
		shade: [0.3, '#000'],
		photos: { "data": [{"src": window.location.protocol + '//' + window.location.host + src}] }
	});
}