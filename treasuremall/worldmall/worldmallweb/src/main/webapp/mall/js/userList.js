/**
 * 
 */
var pageNum = 1,
    pageSize = 50;
var flagSubmit = true;
var datas = {};
var sortType = '';//列表sort
var levelObj = {
		'registered':'注册用户',
		'recharged':'充值用户',
		'VIP':'VIP用户',
		'demo':'测试用户'
}

$(function(){	
	getList();
	channelList();
	$(".lay-export").click(function(){
		exportList();
	});
});
$(".lay-submit").click(function(){	
	getList();
	return false;
});
layui.use(['form', 'layedit', 'laydate','upload','element'], function(){
	  var form = layui.form
	  ,layer = layui.layer
	  ,layedit = layui.layedit
	  ,laydate = layui.laydate
	  ,$ = layui.jquery
	  ,upload = layui.upload
	  ,element = layui.element;	  
	  //日期
	  laydate.render({
	    elem: '.createtime'
	    ,type: 'datetime'
	    ,range: '_'
		,theme: '#3D99FF'
	  });
});
//渠道列表
function channelList(sort){
	$.ajax({
        url: '../back/channel/list',
        type: 'get',
        async:false,
        data: {
        	'pageSize':'100000',
        	'pageNum':pageNum
        }
    }).done(function(r) {
    		if (r.status == "SUCCESS") {
    			var tableHtml = '<option value="">请选择</option>';
				for(var i=0;i<r.data.length;i++){
					tableHtml += '<option value="'+r.data[i].uuid+'">'+r.data[i].title+'</option>';
				}
				$("select[name='channel']").html(tableHtml);
    		} 
    })
}
//list
function getList(sort){
	datas = $("form").serializeObject();
	datas['pageSize'] = pageSize;
	datas['pageNum'] = pageNum;
	datas['totalRecharge'] = $(".totalRechargeStart").val().replace(/(^\s*)|(\s*$)/g, "")+'_'+$(".totalRechargeEnd").val().replace(/(^\s*)|(\s*$)/g, "");
	datas['totalWinning'] = $(".totalWinningStart").val().replace(/(^\s*)|(\s*$)/g, "")+'_'+$(".totalWinningEnd").val().replace(/(^\s*)|(\s*$)/g, "");
	datas['totalWithdraw'] = $(".totalWithdrawStart").val().replace(/(^\s*)|(\s*$)/g, "")+'_'+$(".totalWithdrawEnd").val().replace(/(^\s*)|(\s*$)/g, "");
	datas['sort'] = sortType;
	datas['type'] = 'normal';
	$.ajax({
        url: '../back/user/list',
        type: 'get',
        async:false,
        data: datas
    }).done(function(r) {
    		if (r.status == "SUCCESS") {
    			var tableHtml = '<tr>'+
								'<th width="120px" class="text-center">ID</th>'+
								'<th width="200px" class="text-center">头像/昵称</th>'+
								'<th width="100px" class="text-center">手机号</th>'+
								'<th width="100px" class="text-center">真实姓名</th>'+
								'<th width="100px" class="text-center">地区</th>'+
								'<th width="100px" class="text-center">用户级别</th>'+
								'<th width="120px" class="text-center">充值总额/美元'+
								'<span class="layui-table-sort layui-inline" lay-sort="'
								+(sortType=="total_recharge asc"?"asc":(sortType=="total_recharge desc"?"desc":""))+'">'+
								'<i class="layui-edge layui-table-sort-asc" title="asc" attr-sort="total_recharge asc"></i>'+
								'<i class="layui-edge layui-table-sort-desc" title="desc" attr-sort="total_recharge desc"></i></span></th>'+
								'<th width="120px" class="text-center">提现总额/美元'+
								'<span class="layui-table-sort layui-inline" lay-sort="'
								+(sortType=="total_withdraw asc"?"asc":(sortType=="total_withdraw desc"?"desc":""))+'">'+
								'<i class="layui-edge layui-table-sort-asc" title="asc" attr-sort="total_withdraw asc"></i>'+
								'<i class="layui-edge layui-table-sort-desc" title="desc" attr-sort="total_withdraw desc"></i></span></th>'+
								'<th width="150px" class="text-center">领取实物总额/美元'+
								'<span class="layui-table-sort layui-inline" lay-sort="'
								+(sortType=="total_delivery asc"?"asc":(sortType=="total_delivery desc"?"desc":""))+'">'+
								'<i class="layui-edge layui-table-sort-asc" title="asc" attr-sort="total_delivery asc"></i>'+
								'<i class="layui-edge layui-table-sort-desc" title="desc" attr-sort="total_delivery desc"></i></span></th>'+
								'<th width="140px" class="text-center">用户获利/美元</th>'+
								'<th width="100px" class="text-center">投注总额'+
								'<span class="layui-table-sort layui-inline" lay-sort="'
								+(sortType=="total_payment asc"?"asc":(sortType=="total_payment desc"?"desc":""))+'">'+
								'<i class="layui-edge layui-table-sort-asc" title="asc" attr-sort="total_payment asc"></i>'+
								'<i class="layui-edge layui-table-sort-desc" title="desc" attr-sort="total_payment desc"></i></span></th>'+
								'<th width="100px" class="text-center">中奖总额'+
								'<span class="layui-table-sort layui-inline" lay-sort="'
								+(sortType=="total_winning asc"?"asc":(sortType=="total_winning desc"?"desc":""))+'">'+
								'<i class="layui-edge layui-table-sort-asc" title="asc" attr-sort="total_winning asc"></i>'+
								'<i class="layui-edge layui-table-sort-desc" title="desc" attr-sort="total_winning desc"></i></span></th>'+
								'<th width="100px" class="text-center">投注次数'+
								'<span class="layui-table-sort layui-inline" lay-sort="'
								+(sortType=="payment_times asc"?"asc":(sortType=="payment_times desc"?"desc":""))+'">'+
								'<i class="layui-edge layui-table-sort-asc" title="asc" attr-sort="payment_times asc"></i>'+
								'<i class="layui-edge layui-table-sort-desc" title="desc" attr-sort="payment_times desc"></i></span></th>'+
								'<th width="100px" class="text-center">中奖次数'+
								'<span class="layui-table-sort layui-inline" lay-sort="'
								+(sortType=="winning_times asc"?"asc":(sortType=="winning_times desc"?"desc":""))+'">'+
								'<i class="layui-edge layui-table-sort-asc" title="asc" attr-sort="winning_times asc"></i>'+
								'<i class="layui-edge layui-table-sort-desc" title="desc" attr-sort="winning_times desc"></i></span></th>'+
								'<th width="100px" class="text-center">账户余额'+
								'<span class="layui-table-sort layui-inline" lay-sort="'
								+(sortType=="balance asc"?"asc":(sortType=="balance desc"?"desc":""))+'">'+
								'<i class="layui-edge layui-table-sort-asc" title="asc" attr-sort="balance asc"></i>'+
								'<i class="layui-edge layui-table-sort-desc" title="desc" attr-sort="balance desc"></i></span></th>'+
								'<th width="100px" class="text-center">注册渠道</th>'+
								'<th width="150px" class="text-center">注册时间</th>'+
								'<th width="150px" class="text-center">上次上线时间</th>'+
								'<th width="100px" class="text-center">操作</th>'+
							'</tr>';
				for(var i=0;i<r.data.length;i++){
	    			var imgUrl = r.data[i].imageURL?(".."+r.data[i].imageURL):'../image/img-defaultAvatar.png';
	    			var profit = (r.data[i].balance*100+r.data[i].balanceLock*100+r.data[i].totalWithdraw*100+r.data[i].totalDelivery*100-r.data[i].totalRecharge*100)/100
					var classColor = ''
					if(profit>0){
						classColor = 'color-red'
					}else if(profit<0){
						classColor = 'color-green'
					}
	    			tableHtml += '<tr data-url="userDetail.html?uuid='+r.data[i].uuid+
					'"><td class="text-center">'+r.data[i].showId+'</td><td class="text-center showDetail"><div style="width:40%;float:left;text-align:right;"><img src="'+
					imgUrl+'" class="touxiang" /></div><div style="width:60%;float:left; text-align:left;padding-top:10px;"><p>'
					+r.data[i].nickname+'</p></div>'+
					'</td><td class="text-center">'+
					r.data[i].mobile+'</td><td class="text-center">'
					+(r.data[i].realname?r.data[i].realname:"-")+'</td><td class="text-center">'
					+(r.data[i].area?r.data[i].area:"-")+'</td><td class="text-center">'
					+levelObj[r.data[i].level]+'</td><td class="text-center">'
					+(r.data[i].totalRecharge).toFixed(2)+'</td><td class="text-center">'
					+(r.data[i].totalWithdraw).toFixed(2)+'</td><td class="text-center">'
					+(r.data[i].totalDelivery).toFixed(2)+'</td><td class="text-center '+classColor+'">'
					+profit.toFixed(2)+'</td><td class="text-center">'
					+(r.data[i].totalPayment).toFixed(2)+'</td><td class="text-center">'
					+(r.data[i].totalWinning).toFixed(2)+'</td><td class="text-center">'
					+r.data[i].paymentTimes+'</td><td class="text-center">'
					+r.data[i].winningTimes+'</td><td class="text-center">'
					+(r.data[i].balance).toFixed(2)+'</td><td class="text-center">'
					+(r.data[i].registerChannelCN?r.data[i].registerChannelCN:"-")+'</td><td class="text-center">'
					+formatDate(r.data[i].createtime)+'</td><td class="text-center">'
					+formatDate(r.data[i].lastonline)+'</td><td class="text-center showDetail">'
					+'<a>明细</a></td></tr>'; 
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
					tableHtml += '<tr><td colspan="20" align="center">暂无相关数据</td></tr>';
				}
				$("table").html(tableHtml);
				$("td.showDetail").click(function(){
					layer.open({
						type: 2, 
						title:false, 
						area: ['90%', '90%'],
						content: [$(this).parent().attr("data-url")+'?pagesize='+pageSize+'&pagenum='+pageNum] 
					});
				});
				$("th i").click(function(){
					$(this).parent().attr("lay-sort",$(this).attr("title"));
					sortType = $(this).attr("attr-sort");
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
//导出
function exportList(){
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
	var data = $("form").serialize()+"&totalRecharge="+$(".totalRechargeStart").val().replace(/(^\s*)|(\s*$)/g, "")+
				'_'+$(".totalRechargeEnd").val().replace(/(^\s*)|(\s*$)/g, "")+"&totalWinning="+$(".totalWinningStart").val().replace(/(^\s*)|(\s*$)/g, "")
				+'_'+$(".totalWinningEnd").val().replace(/(^\s*)|(\s*$)/g, "")+"&totalWithdraw="+$(".totalWithdrawStart").val().replace(/(^\s*)|(\s*$)/g, "")
				+'_'+$(".totalWithdrawEnd").val().replace(/(^\s*)|(\s*$)/g, "")+"&type=normal";
	var link = document.createElement('a');
	link.setAttribute("download", "");
	link.href = "../back/user/export?"+data;
	link.click();
//	layer.close(layerIndex)
	return false;
}


