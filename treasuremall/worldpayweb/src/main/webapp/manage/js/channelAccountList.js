/**
 * 
 */
var pageNum = 1,
    pageSize = 50;
var statusObj = {
	'normal':'normal',
	'disable':'disable',
	'suspend':'suspend'
}
var typeObj = {
	'recharge':'recharge',
	'withdraw':'withdraw'
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
			
			var tableHtml = '<option value="">all</option>';
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
    			layer.msg('Successful', {
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
				'<th width="90px" class="text-left">Type</th>'+
				'<th width="120px" class="text-center">Channel</th>'+
				'<th width="160px" class="text-center">Name</th>'+
				'<th width="150px" class="text-center">Account</th>'+
				'<th width="80px" class="text-center">Currency</th>'+
				'<th width="100px" class="text-center">TotalAmount</th>'+
				'<th width="80px" class="text-center">Poundage</th>'+
				'<th width="120px" class="text-center">SingleLimit</th>'+
				'<th width="100px" class="text-center">DailyMax</th>'+
				'<th width="120px" class="text-center">TotalMax</th>'+
				'<th width="180px" class="text-center">TransferUrl</th>'+
				'<th width="150px" class="text-center">Createtime</th>'+
				'<th width="70px" class="text-center">Status</th>'+
				'<th width="100px" class="text-center">Operation</th>'+
				'</tr>';
			for(var i=0;i<r.data.length;i++){
				var processHtml = '<a class="editBtn mr-5">edit</a>';
				if(r.data[i].status == 'normal'){
					processHtml = processHtml + '<a class="closeBtn mr-5">close</a>'
				}else if(r.data[i].status == 'disable' ||r.data[i].status == 'suspend'){
					processHtml = processHtml + '<a class="openBtn mr-5">open</a>'
				}
				
				
				tableHtml += '<tr>'
					+'<td class="text-left">'+typeObj[r.data[i].type]+'</td>'
					+'<td class="text-center">'+r.data[i].channelName+'</td>'
					+'<td class="text-center">'+r.data[i].name+'</td>'
					+'<td class="text-center">'+r.data[i].accountNum+'</td>'
					+'<td class="text-center">'+r.data[i].currency+'</td>'
					+'<td class="text-center">'+Math.abs(r.data[i].balance/100).toFixed(2)+'</td>'
					+'<td class="text-center">'+(r.data[i].poundage == null ? (r.data[i].poundageRate*100+'%') : ((r.data[i].poundage/100).toFixed(2)))+'</td>'
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
				tableHtml += '<tr><td colspan="14" align="center">No relevant data</td></tr>';
			}
			$("table").html(tableHtml);
			$(".openBtn").click(function(){
				var dataUuid = $(this).parent().attr("data-id");
				layer.confirm('Are you sure to start this account',{
					btnAlign: 'c',
					title:'information',
					btn: ['Confirm', 'Cancel'],
					yes: function(index){
						changeStatus(dataUuid,'normal');
					}
				});
				return false;
			});
			$(".closeBtn").click(function(){
				var dataUuid = $(this).parent().attr("data-id");
				layer.confirm('Are you sure you want to stop this account',{
					btnAlign: 'c',
					title:'information',
					btn: ['Confirm', 'Cancel'],
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