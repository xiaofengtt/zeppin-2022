/**
 * 充值渠道
 */
var pageNum = (url('?pagenum') != null) ? url('?pagenum') : 1,
	pageSize = (url('?pagesize') != null) ? url('?pagesize') : 50;
var flagSubmit = true;
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
	'union':'Payduoduo支付',
	'worldpay':'Worldpay支付',
	'paypal':'PayPal',
	'stripe':'Stripe',
	'credit':'信用卡支付'
}
var transTypeObj = {
	'company_bankcard':'企业银行卡',
	'company_alipay':'企业支付宝',
	'company_wechat':'企业微信',
	'personal_bankcard':'个人银行卡',
	'alipay_code':'支付宝扫码',
	'wechat_code':'微信扫码',
	'alipay_bankcard':'支付宝转银行卡',
	'paypal':'PayPal',
	'stripe':'Stripe',
	'credit':'信用卡'
}
$(function(){
	$(".goPrePage").click(function(){
		window.location.href='capitalPlatformDetail.html?pagesize='+pageSize+'&pagenum='+pageNum
	});
	getList();
});
function getList(){
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
			var tableHtml = '<tr>'+
				'<th width="5%" class="text-center">序号</th>'+
				'<th width="15%" class="text-center">支付名称</th>'+
				'<th width="10%" class="text-center">支付方式</th>'+
				'<th width="10%" class="text-center">支付类型</th>'+
				'<th width="15%" class="text-center">充值额度/美元</th>'+
				'<th width="5%" class="text-center">推荐</th>'+
				'<th width="8%" class="text-center">额度随机</th>'+
				'<th width="5%" class="text-center">排序</th>'+
				'<th width="8%" class="text-center">状态</th>'+
				'<th width="10%" class="text-center">操作</th>'+
				'</tr>';
			for(var i=0;i<r.data.length;i++){
				var categoryHtml = '<a class="editBtn mr-5">编辑</a><a class="deleteBtn">删除</a>';//分类
				var classColor = "";
				if(r.data[i].status=="normal"){
					classColor = "color-blue"
				}else if(r.data[i].status=="disable"){
					classColor = "color-red"
				}
				tableHtml += '<tr>'
					+'<td class="text-center">'+(pageSize*(pageNum-1)+i+1)+'</td>'
					+'<td class="text-center">'+r.data[i].name+'</td>'
					+'<td class="text-center">'+typeObj[r.data[i].type]+'</td>'
					+'<td class="text-center">'+transTypeObj[r.data[i].transType]+'</td>'
					+'<td class="text-center">'+(r.data[i].min).toFixed(2)+'-'+(r.data[i].max).toFixed(2)+'</td>'
					+'<td class="text-center">'+(r.data[i].isRecommend==true?'是':'否')+'</td>'
					+'<td class="text-center">'+(r.data[i].isRandomAmount==true?'是':'否')+'</td>'
//					+'<td class="text-center"><input type="number" value="'+r.data[i].sort+'" class="layui-input" ></td>'
					+'<td class="text-center">'+r.data[i].sort+'</td>'
					+'<td class="text-center '+classColor+'">'+statusObj[r.data[i].status]+'</td>'
					+'<td class="text-center" data-id="'+r.data[i].uuid+'" data-item="'
					+encodeURI(JSON.stringify(r.data[i]))+'">'+categoryHtml+'</td>'
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
				tableHtml += '<tr><td colspan="10" align="center">暂无相关数据</td></tr>';
			}
			$("table").html(tableHtml);
			$(".editBtn").click(function(){
				window.location.href = 'capitalPlatformDetail.html?uuid='+$(this).parent().attr("data-id")+'&pagesize='+pageSize+'&pagenum='+pageNum;
				return false;
			});
			$(".deleteBtn").click(function(){
				deleteData($(this).parent().attr("data-id"));
				return false;
			});
			$(".updateBtn").click(function(){
				updateData($(this).parent().attr("data-item"),$(this).parent().parent().find("input").val());
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
		if(flagSubmit == false) {
			return false;
		}
		flagSubmit = false;
		setTimeout(function() {
			flagSubmit = true;
		}, 3000);
    	$.ajax({
            url: '../back/capitalPlatform/changeStatus',
            type: 'post',
            async:false,
            data: {
            	'uuid':uuid,
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
function updateData(item,value){
	var data = JSON.parse(decodeURI(item));
	data.sort = value;
	$.ajax({
        url: '../back/capitalPlatform/edit',
        type: 'post',
        async:false,
        data: data
    }).done(function(r) {
		if (r.status == "SUCCESS") {
			layer.msg('修改成功', {
				time: 1000 
			}, function(){
				getList();
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
layui.use(['form', 'element'], function(){
	var form = layui.form
	,layer = layui.layer
	,$ = layui.jquery
	,element = layui.element;
})