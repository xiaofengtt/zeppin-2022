/**
 * 充值账户
 */
var pageNum = (url('?pagenum') != null) ? url('?pagenum') : 1,
	pageSize = (url('?pagesize') != null) ? url('?pagesize') : 50;
var statusObj = {
	'normal':'启用',
	'disable':'停用'
}
var typeObj = {
	'alipay':'支付宝',
	'wechat':'微信支付',
	'reapal':'融宝支付',
	'acicpay':'兴达支付',
	'jinzun':'金樽支付',
	'union':'Payduoduo支付'
}
var transTypeObj = {
	'company_bankcard':'企业银行卡',
	'company_alipay':'企业支付宝',
	'company_wechat':'企业微信',
	'personal_bankcard':'个人银行卡',
	'alipay_code':'支付宝扫码',
	'wechat_code':'微信扫码',
	'alipay_bankcard':'支付宝转银行卡'
}
var frontUserGroupObj = {
	'registered':'注册',
	'recharged':'充值',
	'VIP':'vip',
	'demo':'测试'
}
$(function(){
	$(".goPrePage").click(function(){
		window.location.href='capitalAccountDetail.html?pagesize='+pageSize+'&pagenum='+pageNum
	});
	getPlatformList();
	getList();
});
function getPlatformList(){
	$.ajax({
        url: '../back/capitalPlatform/list',
        type: 'get',
        async:false,
        data: {
        	'pageSize':pageSize,
        	'pageNum':pageNum,
        	'sort':'sort'
        }
    }).done(function(r) {
    	if (r.status == "SUCCESS") {
    		var optionHtml = '<option value="">全部</option>'
    		for(var i=0;i<r.data.length;i++){
    			optionHtml = optionHtml + '<option value="'+r.data[i].uuid+'">'+r.data[i].name+'</option>';
    		}
    		$('select[name=capitalPlatformList]').html(optionHtml)
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
function getList(capitalPlatform){
	var datas = {
    	'pageSize':pageSize,
    	'pageNum':pageNum,
    	'sort':'sort'
    }
	if(capitalPlatform != undefined && capitalPlatform != ''){
		datas['capitalPlatform'] = capitalPlatform;
	}
	$.ajax({
        url: '../back/capitalAccount/list',
        type: 'get',
        async:false,
        data: datas
    }).done(function(r) {
		if (r.status == "SUCCESS") {
			var tableHtml = '<tr>'+
				'<th width="5%" class="text-center">序号</th>'+
				'<th width="8%" class="text-center">支付方式</th>'+
				'<th width="10%" class="text-center">支付类型</th>'+
				'<th width="13%" class="text-center">名称</th>'+
				'<th width="12%" class="text-center">充值限额/元</th>'+
				'<th width="10%" class="text-center">每日限额</th>'+
				'<th width="10%" class="text-center">总限额</th>'+
				'<th width="5%" class="text-center">排序</th>'+
				'<th width="5%" class="text-center">状态</th>'+
				'<th width="12%" class="text-center">用户状态</th>'+
				'<th width="10%" class="text-center">操作</th>'+
				'</tr>';
			for(var i=0;i<r.data.length;i++){
				var categoryHtml = '<a class="editBtn mr-5">编辑</a><a class="deleteBtn">删除</a>';//分类
				var classColor = "";
				var frontUserGroup = "";
				if(r.data[i].status=="normal"){
					classColor = "color-blue"
				}else if(r.data[i].status=="disable"){
					classColor = "color-red"
				}
				if(r.data[i].frontUserGroup){
					var groupArr = r.data[i].frontUserGroup.split(",")
					var length = 0;
					for(var key in frontUserGroupObj){
						if(length%2==0){
							if(groupArr.indexOf(key)>-1){
								frontUserGroup += '<p><b class="color-blue mr-5">'+frontUserGroupObj[key]+':启用</b>'
							}else{
								frontUserGroup += '<p><b class="color-red mr-5">'+frontUserGroupObj[key]+':停用</b>'
							}
						}else{
							if(groupArr.indexOf(key)>-1){
								frontUserGroup += '<b class="color-blue">'+frontUserGroupObj[key]+':启用</b></p>'
							}else{
								frontUserGroup += '<b class="color-red">'+frontUserGroupObj[key]+':停用</b></p>'
							}
						}
						length ++ 
					}
				}else{
					frontUserGroup = '<p><b class="color-blue mr-5">注册:启用</b>'+
									 '<b class="color-blue">充值:启用</b></p>'+
									 '<p><b class="color-blue mr-5">VIP:启用</b>'+
									 '<b class="color-blue">测试:启用</b></p>'
				}
				tableHtml += '<tr>'
					+'<td class="text-center">'+(pageSize*(pageNum-1)+i+1)+'</td>'
					+'<td class="text-center">'+typeObj[r.data[i].type]+'</td>'
					+'<td class="text-center">'+transTypeObj[r.data[i].transType]+'</td>'
					+'<td class="text-center">'+r.data[i].name+'</td>'
					+'<td class="text-center">'+(r.data[i].min).toFixed(2)+'-'+(r.data[i].max).toFixed(2)+'</td>'
					+'<td class="text-center">'+(r.data[i].dailyMax).toFixed(2)+'</td>'
					+'<td class="text-center">'+(r.data[i].totalMax).toFixed(2)+'</td>'
					+'<td class="text-center">'+r.data[i].sort+'</td>'
					+'<td class="text-center '+classColor+'">'+statusObj[r.data[i].status]+'</td>'
					+'<td class="text-center">'+frontUserGroup+'</td>'
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
				tableHtml += '<tr><td colspan="11" align="center">暂无相关数据</td></tr>';
			}
			$("table").html(tableHtml);
			$(".editBtn").click(function(){
				window.location.href = 'capitalAccountDetail.html?uuid='+$(this).parent().attr("data-id")+'&pagesize='+pageSize+'&pagenum='+pageNum;
				return false;
			});
			$(".deleteBtn").click(function(){
				deleteData($(this).parent().attr("data-id"));
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
//删除
function deleteData(uuid){
	layer.confirm('是否确认删除该条数据？',function(index, layero){
    	$.ajax({
            url: '../back/capitalAccount/changeStatus',
            type: 'post',
            async:false,
            data: {
            	'uuid': uuid,
            	'status': 'delete'
            }
        }).done(function(r) {
    		if (r.status == "SUCCESS") {
    			layer.msg("删除成功", {
    	            time: 2000
    	        },function(){
    	        	getList();	
    	        });
    		}else {
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
    		layer.close(index);
        })
    },function(){
    	
    });
}
layui.use(['table','form', 'layer','element'], function(){
	var form = layui.form
		,layer = layui.layer
		,$ = layui.jquery
		,element = layui.element;
	form.on('select(capitalPlatformList)',function(data){
		pageNum = 1;
		getList(data.value);
	})
});