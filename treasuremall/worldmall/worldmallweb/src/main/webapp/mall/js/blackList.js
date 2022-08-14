/**
 * 黑名单
 */
var pageNum = (url('?pagenum') != null) ? url('?pagenum') : 1,
	pageSize = (url('?pagesize') != null) ? url('?pagesize') : 50;
var flagSubmit = true;
var sortType = '';
var levelObj = {
		'registered':'注册用户',
		'recharged':'充值用户',
		'VIP':'VIP用户',
		'demo':'测试用户'
}
$(function(){
	getList();	
});
$(".lay-submit").click(function(){	
	pageNum = 1;
	getList();
	return false;
});
function getList(size,num){
	$.ajax({
        url: '../back/user/blacklist',
        type: 'get',
        async:false,
        data: {
        	'pageSize':size?size:pageSize,
            'pageNum':num?num:pageNum,
            'showid':$("input[name='showid']").val().replace(/(^\s*)|(\s*$)/g, ""),
            'nickname':$("input[name='nickname']").val().replace(/(^\s*)|(\s*$)/g, "")
        }
    }).done(function(r) {
    		if (r.status == "SUCCESS") {
				var tableHtml = '<tr>'+
								'<th width="120px" class="text-center">ID</th>'+
								'<th width="200px" class="text-center">头像/昵称</th>'+
								'<th width="100px" class="text-center">手机号</th>'+
								'<th width="100px" class="text-center">真实姓名</th>'+
								'<th width="100px" class="text-center">地区</th>'+
								'<th width="100px" class="text-center">用户级别</th>'+
								'<th width="120px" class="text-center">充值总额/美元</th>'+
								'<th width="120px" class="text-center">提现总额/美元</th>'+
								'<th width="150px" class="text-center">领取实物总额/美元</th>'+
								'<th width="140px" class="text-center">用户获利/美元</th>'+
								'<th width="100px" class="text-center">投注总额</th>'+
								'<th width="100px" class="text-center">中奖总额</th>'+
								'<th width="100px" class="text-center">投注次数</th>'+
								'<th width="100px" class="text-center">中奖次数</th>'+
								'<th width="100px" class="text-center">账户余额</th>'+
								'<th width="100px" class="text-center">注册渠道</th>'+
								'<th width="150px" class="text-center">注册时间</th>'+
								'<th width="150px" class="text-center">上次上线时间</th>'+
								'<th width="150px" class="text-center">原因</th>'+
								'<th width="150px" class="text-center">添加时间</th>'+
								'<th width="100px" class="text-center">操作</th>'+
							'</tr>';
				for(var i=0;i<r.data.length;i++){
					var imgUrl = r.data[i].frontUserVO.imageURL?(".."+r.data[i].frontUserVO.imageURL):'../image/img-defaultAvatar.png';
					var categoryHtml = '<a class="deleteBtn">移除</a>';//分类
					var profit = (r.data[i].frontUserVO.balance*100+r.data[i].frontUserVO.balanceLock*100+r.data[i].frontUserVO.totalWithdraw*100+r.data[i].frontUserVO.totalDelivery*100-r.data[i].frontUserVO.totalRecharge*100)/100
					var classColor = ''
					if(profit>0){
						classColor = 'color-red'
					}else if(profit<0){
						classColor = 'color-green'
					}
					tableHtml += '<tr data-url="userDetail.html?uuid='+r.data[i].frontUser+
					'"><td class="text-center">'+r.data[i].showId+'</td><td class="text-center showDetail"><div style="width:40%;float:left;text-align:right;"><img src="'+
					imgUrl+'" class="touxiang" /></div><div style="width:60%;float:left; text-align:left;padding-top:5px;"><p>'
					+r.data[i].nickname+'</p></div>'+
					'</td><td class="text-center">'+
					r.data[i].frontUserVO.mobile+'</td><td class="text-center">'
					+(r.data[i].frontUserVO.realname?r.data[i].frontUserVO.realname:"-")+'</td><td class="text-center">'
					+(r.data[i].frontUserVO.area?r.data[i].frontUserVO.area:"-")+'</td><td class="text-center">'
					+levelObj[r.data[i].frontUserVO.level]+'</td><td class="text-center">'
					+(r.data[i].frontUserVO.totalRecharge).toFixed(2)+'</td><td class="text-center">'
					+(r.data[i].frontUserVO.totalWithdraw).toFixed(2)+'</td><td class="text-center">'
					+(r.data[i].frontUserVO.totalDelivery).toFixed(2)+'</td><td class="text-center '+classColor+'">'
					+profit.toFixed(2)+'</td><td class="text-center">'
					+(r.data[i].frontUserVO.totalPayment).toFixed(2)+'</td><td class="text-center">'
					+(r.data[i].frontUserVO.totalWinning).toFixed(2)+'</td><td class="text-center">'
					+r.data[i].frontUserVO.paymentTimes+'</td><td class="text-center">'
					+r.data[i].frontUserVO.winningTimes+'</td><td class="text-center">'
					+(r.data[i].frontUserVO.balance).toFixed(2)+'</td><td class="text-center">'
					+(r.data[i].frontUserVO.registerChannelCN?r.data[i].frontUserVO.registerChannelCN:"-")+'</td><td class="text-center">'
					+formatDate(r.data[i].frontUserVO.createtime)+'</td><td class="text-center">'
					+formatDate(r.data[i].frontUserVO.lastonline)+'</td><td class="text-center">'+r.data[i].reason+
					'</td><td class="text-center">'+formatDate(r.data[i].createtime)+'</td><td data-id="'+r.data[i].uuid+
					'" class="text-center">'+categoryHtml+'</td></tr>'; 
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
					tableHtml += '<tr><td colspan="22" align="center">暂无相关数据</td></tr>';
				}
				$("table").html(tableHtml);

				$(".deleteBtn").click(function(){
					deleteList($(this).parent().attr("data-id"));
					return false;
				});
				$("td.showDetail").click(function(){
					layer.open({
						type: 2, 
						title:false, 
						area: ['90%', '90%'],
						content: [$(this).parent().attr("data-url")+'?pagesize='+pageSize+'&pagenum='+pageNum] 
					});
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
function deleteList(uuid){
	layer.confirm('确定要移除吗？', {
		btn: ['确定','取消'] //按钮
	}, function(){		
		if(flagSubmit == false) {
			return false;
		}
		flagSubmit = false;
		setTimeout(function() {
			flagSubmit = true;
		}, 3000);
	$.ajax({
        url: '../back/user/blackdrop',
        type: 'post',
        async:false,
        data: {
        	'uuid':uuid
        }
    }).done(function(r) {
    		if (r.status == "SUCCESS") {
    			layer.msg("移除成功", {
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
    })
	}, function(){
		layer.closeAll();
	});
}
layui.use(['form', 'element'], function(){
	var form = layui.form
	,layer = layui.layer
	,$ = layui.jquery
	,element = layui.element;
})