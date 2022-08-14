var uuid = (url('?uuid') != null) ? url('?uuid') : '';
var size = (url('?pagesize') != null) ? url('?pagesize') : '';
var num = (url('?pagenum') != null) ? url('?pagenum') : '';
var pageNum = 1,
pageSize = 50;
var flagSubmit = true;
var showId = '';
var type = {
		'normal':'未领奖',
		'gold':'金币',
		'entity':'实物'
}
var userHistoryType={
        'system_add': '+',
        'system_sub': '-',
        'system_give': '+',
        'user_recharge': '+',
        'user_withdraw': '-',
        'user_payment': '-',
        'user_exchange': '+',
        'user_scorelottery': '+',
        'user_buyfree': '+',
        'user_checkin': '+',
        'user_delivery':'-',
        'user_recommend':'+'
}
$(function(){
	getList();
	pageNum = 1;
	getGoldCoins();
	$(".voucherTab li").click(function(){
		pageNum = 1;
		$(this).addClass("light").siblings().removeClass("light");
		 voucherlist($(this).attr("data-value"));
	});
	$(".userLevel").change(function(){
		changeLevel($(this).val())
	})
})
function getStatus(status){
	switch (status){
		case 'normal':
			return '待审核'
		case 'checking':
			return '审核中'
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

//获取用户详情
function getList(){
	$.ajax({
        url: '../back/user/get',
        type: 'get',
        async:false,
        data: {
        	'uuid':uuid
        }
    }).done(function(r) {
    		if (r.status == "SUCCESS") {
    			var profit = (r.data.balance*100+r.data.balanceLock*100+r.data.totalWithdraw*100+r.data.totalDelivery*100-r.data.totalRecharge*100)/100
				var classColor = ''
				if(profit>0){
					classColor = 'color-red'
				}else if(profit<0){
					classColor = 'color-green'
				}
    			$(".userLevel").val(r.data.level);
    			$(".setBlack").css('display','block')
    			if(r.data.status=="normal"){
    				$(".setNormal").hide()
    				$(".setHigh").css('display','block')
    				$(".left .high").hide()
    			}else if(r.data.status=="highrisk"){
    				$(".setNormal").css('display','block')
    				$(".setHigh").hide()
    				$(".left .high").show()
    			}
    			if(r.data.status=="blacklist"){
    				$(".setHigh,.setNormal,.setBlack").hide()
    			}
    			if(r.data.level=="VIP"){
    				$(".left .vip").show()
    			}else{
    				$(".left .vip").hide()
    			}
    			var imgUrl = r.data.imageURL?(".."+r.data.imageURL):'../image/img-defaultAvatar.png';
    			$(".coverImg").attr("src",imgUrl);
    			$(".realname").html(r.data.realname);	
    			$(".nickname").html(r.data.nickname);
    			$(".idcard").html(r.data.idcard);
    			$(".balance").html(r.data.balance);
    			$(".showId").html(r.data.showId);
    			showId = r.data.showId;
    			$(".createtime").html(formatDate(r.data.createtime));
    			$(".scoreBalance").html(r.data.scoreBalance)
    			$(".totalRecharge").html((r.data.totalRecharge).toFixed(2));
    			$(".totalWithdraw").html((r.data.totalWithdraw).toFixed(2));
    			$(".totalDelivery").html(r.data.totalDelivery);
    			$(".totalPayment").html(r.data.totalPayment);
    			$(".totalWinning").html(r.data.totalWinning);
    			$(".paymentTimes").html(r.data.paymentTimes);		
    			$(".winningTimes").html(r.data.winningTimes);	
    			$(".totalProfit").html(profit.toFixed(2)).addClass(classColor);	
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
layui.use('element', function(){
  var element = layui.element;  
  element.on('tab(docDemoTabBrief)', function(elem){
	  pageNum = 1;
	  if(elem.index==0){
		  getGoldCoins();
	  }else if(elem.index==1){
		  getAttendanceRecord();
	  }else if(elem.index==2){
		  winlist();
	  }else if(elem.index==3){
		  addresslist();
	  }else if(elem.index==4){
		  rechargelist();
	  }else if(elem.index==5){
		  withdrawlist();
	  }else if(elem.index==6){
		  voucherlist($(".voucherTab li.light").attr("data-value"));
	  }
  });
});
//金币明细
function getGoldCoins(){
	$.ajax({
        url: '../back/userHistory/list',
        type: 'get',
        async:false,
        data: {
        	'pageSize':pageSize,
        	'pageNum':pageNum,
        	'frontUser':uuid
        }
    }).done(function(r) {
    		if (r.status == "SUCCESS") {
    			var tableHtml = '<tr>'+
									'<th width="5%" class="text-center">序号</th>'+
									'<th width="18%" class="text-center">项目</th>'+
									'<th width="10%" class="text-center">额度</th>'+
									'<th width="10%" class="text-center">结余</th>'+
									'<th width="20%" class="text-center">时间</th>'+
									'<th width="15%" class="text-center">原因</th>'+
									'<th width="17%" class="text-center">订单号</th>'+
									'<th width="15%" class="text-center">备注</th>'+
								'</tr>';
					for(var i=0;i<r.data.length;i++){
						tableHtml += '<tr><td class="text-center">'+
						(pageSize*(pageNum-1)+i+1)+'</td><td class="text-center">'
						+(r.data[i].title)+'</td><td class="text-center">'
						+userHistoryType[r.data[i].orderType]+(r.data[i].dAmount?r.data[i].dAmount.toFixed(2):'-')+'</td><td class="text-center">'
						+(r.data[i].balanceAfter?r.data[i].balanceAfter.toFixed(2):'-')+'</td><td class="text-center">'
						+formatDate(r.data[i].createtime)+'</td><td class="text-center">'
						+r.data[i].reason+'</td><td class="text-center">'
						+r.data[i].orderNum+'</td><td class="text-center">'
						+r.data[i].remark+'</td></tr>'; 
					}
					if (r.totalPageCount!=0) {
					    $('#pageTool0').Paging({
					        prevTpl: "<",
					        nextTpl: ">",
					        pagesize: pageSize,
					        count: r.totalResultCount,
					        current: pageNum,
					        toolbar: true,
					        pageSizeList: [50, 200, 1000],
					        callback: function(page, size, count) {
					            pageNum = page;
					            getGoldCoins();
					            flag = false;
					            document.body.scrollTop = document.documentElement.scrollTop = 0;
					        }
					    });
					    $("#pageTool0 .ui-paging-container .ui-paging-toolbar").append("<b class='paging-total'>共"+r.totalResultCount+"条</b>")
					    $("#pageTool0").show().find(".ui-paging-container:last").siblings().remove();
					}else{
						$("#pageTool0").hide();
					}
					$(".ui-select-pagesize").unbind("change").change(function() {
					    pageSize = $(this).val();
					    pageNum = 1;
					    getGoldCoins();
					});
					if(r.data.length==0){
						tableHtml += '<tr><td colspan="8" align="center">暂无相关数据</td></tr>';
					}
					$(".goldCoins table").html(tableHtml);
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
//参与记录
function getAttendanceRecord(){
	$.ajax({
        url: '../back/userHistory/partakelist',
        type: 'get',
        async:false,
        data: {
        	'pageSize':pageSize,
        	'pageNum':pageNum,
        	'frontUser':uuid
        }
    }).done(function(r) {
    		if (r.status == "SUCCESS") {
    			var tableHtml = '<tr>'+
									'<th width="5%" class="text-center">序号</th>'+
									'<th width="23%" class="text-center">商品信息</th>'+
									'<th width="10%" class="text-center">订单号</th>'+
									'<th width="10%" class="text-center">投注金币</th>'+
									'<th width="10%" class="text-center">参与次数</th>'+
									'<th width="10%" class="text-center">代金券</th>'+
									'<th width="10%" class="text-center">实际支付金币</th>'+
//									'<th width="10%" class="text-center">包尾订单</th>'+
									'<th width="15%" class="text-center">时间</th>'+
									'<th width="10%" class="text-center">促销信息</th>'+
									'<th width="10%" class="text-center">中奖状态</th>'+
								'</tr>';
					for(var i=0;i<r.data.length;i++){
						tableHtml += '<tr><td class="text-center">'+
						(pageSize*(pageNum-1)+i+1)+'</td><td class="text-center"><div style="width:40%;float:left;"><img src="..'+
						r.data[i].cover+'"/></div><div style="width:60%;float:left; text-align:left;"><p>'
						+r.data[i].shortTitle+'</p><p>总份额：'
						+r.data[i].shares+'份</p><p>商品编码：'
						+r.data[i].code+'</p></div></td><td class="text-center" style="word-break: break-all;">'
						+r.data[i].orderNum+'</td><td class="text-center">'
						+(r.data[i].totalDAmount?r.data[i].totalDAmount.toFixed(2):'-')+'</td><td class="text-center">'
						+r.data[i].buyCount+'</td><td class="text-center" style="position:relative;">'
						+r.data[i].voucherDAmount+'<p class="voucherTitle" style="position:absolute;width:100%;text-align:center;background:#eee;display:none;z-index:1;">'
						+(r.data[i].isVoucherUsed?r.data[i].voucherTitle:"")+'</p></td><td class="text-center">'
						+(r.data[i].actualDAmount?r.data[i].actualDAmount.toFixed(2):'-')+'</td><td class="text-center">'
//						+(r.data[i].isVoucherUsed?"是":"否")+'</td><td class="text-center">'
						+formatDate(r.data[i].createtime)+'</td><td class="text-center">'
						+(r.data[i].promotionTitle?r.data[i].promotionTitle:'-')+'</td><td class="text-center">'
						+(r.data[i].isLucky?"<span class='color-red'>中奖</span>":"未中奖")+'</td></tr>'; 
					}
					if (r.totalPageCount!=0) {
					    $('#pageTool1').Paging({
					        prevTpl: "<",
					        nextTpl: ">",
					        pagesize: pageSize,
					        count: r.totalResultCount,
					        current: pageNum,
					        toolbar: true,
					        pageSizeList: [50, 200, 1000],
					        callback: function(page, size, count) {
					            pageNum = page;
					            getAttendanceRecord();
					            flag = false;
					            document.body.scrollTop = document.documentElement.scrollTop = 0;
					        }
					    });
					    $("#pageTool1 .ui-paging-container .ui-paging-toolbar").append("<b class='paging-total'>共"+r.totalResultCount+"条</b>")
					    $("#pageTool1").show().find(".ui-paging-container:last").siblings().remove();
					}else{
						$("#pageTool1").hide();
					}
					$(".ui-select-pagesize").unbind("change").change(function() {
					    pageSize = $(this).val();
					    pageNum = 1;
					    getAttendanceRecord();
					});
					if(r.data.length==0){
						tableHtml += '<tr><td colspan="10" align="center">暂无相关数据</td></tr>';
					}
					$(".attendanceRecord table").html(tableHtml);
					$("tr").hover(function(){
						$(this).find(".voucherTitle").show();
					},function(){
						$(this).find(".voucherTitle").hide();
					})
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
//中奖记录
function winlist(){
	$.ajax({
        url: '../back/userHistory/winlist',
        type: 'get',
        async:false,
        data: {
        	'pageSize':pageSize,
        	'pageNum':pageNum,
        	'frontUser':uuid
        }
    }).done(function(r) {
    		if (r.status == "SUCCESS") {
    			var tableHtml = '<tr>'+
									'<th width="8%" class="text-center">商品ID</th>'+
									'<th width="22%" class="text-center">商品信息</th>'+
									'<th width="15%" class="text-center">领奖信息</th>'+
									'<th width="5%" class="text-center">期号</th>'+
									'<th width="10%" class="text-center">商品价格</th>'+
									'<th width="10%" class="text-center">参与次数</th>'+
									'<th width="10%" class="text-center">领奖方式</th>'+
									'<th width="10%" class="text-center">中奖时间</th>'+
									'<th width="10%" class="text-center">操作</th>'+
								'</tr>';
					for(var i=0;i<r.data.length;i++){
						tableHtml += '<tr><td class="text-center">'+
						r.data[i].code+'</td><td class="text-center"><div style="width:35%;float:left;"><img src="..'+
						r.data[i].cover+'"/></div><div style="width:65%;float:left; text-align:left;"><p>'
						+r.data[i].shortTitle+'</p><p>总份额：'
						+r.data[i].shares+'份</p><p>商品编码：'
						+r.data[i].code+'</p></div></td><td class="text-center"><p>时间：'
						+(r.data[i].createtime?formatDate(r.data[i].createtime):"-")+'</p><p>IP：'
						+(r.data[i].ip?r.data[i].ip:'-')+'</p></td><td class="text-center">'
						+(r.data[i].issueNum?r.data[i].issueNum:'-')+'</td><td class="text-center">'
						+(r.data[i].price?r.data[i].price.toFixed(2):'-')+'</td><td class="text-center">'
						+r.data[i].buyCount+'</td><td class="text-center">'
						+type[r.data[i].type]+'</td><td class="text-center">'
						+formatDate(r.data[i].winningTime)+'</td><td class="text-center" style="position:relative;"><a data-id="'
						+r.data[i].goodsIssue+'" class="lookDetail">查看明细</a>'
						+'<div class="lookDetailBox" style="position:absolute;width:700px;max-height:200px;display:none;top:0;right:100px;z-index:9;">'
						+'<table class="table-list mt-15"></table></div></td></tr>'; 
					}
					if (r.totalPageCount!=0) {
					    $('#pageTool2').Paging({
					        prevTpl: "<",
					        nextTpl: ">",
					        pagesize: pageSize,
					        count: r.totalResultCount,
					        current: pageNum,
					        toolbar: true,
					        pageSizeList: [50, 200, 1000],
					        callback: function(page, size, count) {
					            pageNum = page;
					            winlist();
					            flag = false;
					            document.body.scrollTop = document.documentElement.scrollTop = 0;
					        }
					    });
					    $("#pageTool2 .ui-paging-container .ui-paging-toolbar").append("<b class='paging-total'>共"+r.totalResultCount+"条</b>")
					    $("#pageTool2").show().find(".ui-paging-container:last").siblings().remove();
					}else{
						$("#pageTool2").hide();
					}
					$(".ui-select-pagesize").unbind("change").change(function() {
					    pageSize = $(this).val();
					    pageNum = 1;
					    winlist();
					});
					if(r.data.length==0){
						tableHtml += '<tr><td colspan="9" align="center">暂无相关数据</td></tr>';
					}
					$(".winningRecord table").html(tableHtml);
					$(".lookDetail").hover(function(){
						lookDetail($(this).attr("data-id"));
						$(this).parent().find(".lookDetailBox").show();
					},function(){
						$(this).parent().find(".lookDetailBox").hide();
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
//查看明细
function lookDetail(id){
	$.ajax({
        url: '../back/userPayment/list',
        type: 'get',
        async:false,
        data: {
        	'goodsIssue':id,
        	'pageNum':1,
        	'pageSize':'10000',
        	'showId':showId
        }
    }).done(function(r) {
    		if (r.status == "SUCCESS") {
    			var tableHtml = '<tr>'+
								'<th width="15%" class="text-center">投注记录ID</th>'+
								'<th width="10%" class="text-center">投注量</th>'+
								'<th width="15%" class="text-center">投注金币</th>'+
								'<th width="10%" class="text-center">红包投注量</th>'+
								'<th width="25%" class="text-center">投注时间</th>'+
							'</tr>';
				for(var i=0;i<r.data.length;i++){
					tableHtml += '<tr><td class="text-center">'+r.data[i].frontUserShowId
					+'</td><td class="text-center">'
					+(r.data[i].totalDAmount?r.data[i].totalDAmount.toFixed(2):'-')+'</td><td class="text-center">'
					+(r.data[i].actualDAmount?r.data[i].actualDAmount.toFixed(2):'-')+'</td><td class="text-center">'
					+(r.data[i].voucherDAmount?r.data[i].voucherDAmount.toFixed(2):'-')+'</td><td class="text-center">'
					+formatDate(r.data[i].createtime)+'</td></tr>'; 
				}
				if(r.data.length==0){
					tableHtml += '<tr><td colspan="7" align="center">暂无相关数据</td></tr>';
				}
				$(".lookDetailBox table").html(tableHtml);

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
//收货地址
function addresslist(){
	$.ajax({
        url: '../back/user/addresslist',
        type: 'get',
        async:false,
        data: {
        	'pageSize':pageSize,
        	'pageNum':pageNum,
        	'uuid':uuid
        }
    }).done(function(r) {
    		if (r.status == "SUCCESS") {
    			var tableHtml = '<tr>'+
									'<th width="15%" class="text-center">收货人姓名</th>'+
									'<th width="20%" class="text-center">收货人电话</th>'+
									'<th width="" class="text-center">详细地址</th>'+
									'<th width="20%" class="text-center">创建时间</th>'+
								'</tr>';
					for(var i=0;i<r.data.length;i++){
						var address = '';
						if(r.data[i].areaNameList != null){
							for(var j=0; j<r.data[i].areaNameList.length; j++){
								address+=r.data[i].areaNameList[j]
							}
						}
						address+=r.data[i].address;
						tableHtml += '<tr><td class="text-center">'+
						r.data[i].receiver+'</td><td class="text-center">'
						+r.data[i].phone+'</td><td class="text-center">'
						+address+'</td><td class="text-center">'
						+formatDate(r.data[i].createtime)+'</td></tr>'; 
					}
					if (r.totalPageCount!=0) {
					    $('#pageTool3').Paging({
					        prevTpl: "<",
					        nextTpl: ">",
					        pagesize: pageSize,
					        count: r.totalResultCount,
					        current: pageNum,
					        toolbar: true,
					        pageSizeList: [50, 200, 1000],
					        callback: function(page, size, count) {
					            pageNum = page;
					            addresslist();
					            flag = false;
					            document.body.scrollTop = document.documentElement.scrollTop = 0;
					        }
					    });
					    $("#pageTool3 .ui-paging-container .ui-paging-toolbar").append("<b class='paging-total'>共"+r.totalResultCount+"条</b>")
					    $("#pageTool3").show().find(".ui-paging-container:last").siblings().remove();
					}else{
						$("#pageTool3").hide();
					}
					$(".ui-select-pagesize").unbind("change").change(function() {
					    pageSize = $(this).val();
					    pageNum = 1;
					    addresslist();
					});
					if(r.data.length==0){
						tableHtml += '<tr><td colspan="4" align="center">暂无相关数据</td></tr>';
					}
					$(".receivingAddress table").html(tableHtml);
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
//充值记录
function rechargelist(){
	$.ajax({
        url: '../back/userHistory/rechargelist',
        type: 'get',
        async:false,
        data: {
        	'pageSize':pageSize,
        	'pageNum':pageNum,
        	'frontUser':uuid
        }
    }).done(function(r) {
    		if (r.status == "SUCCESS") {
    			var tableHtml = '<tr>'+
									'<th width="7%" class="text-center">序号</th>'+
									'<th width="10%" class="text-center">订单号</th>'+
									'<th width="10%" class="text-center">充值金额/元</th>'+
									'<th width="20%" class="text-center">充入信息</th>'+
									'<th width="12%" class="text-center">备注</th>'+
									'<th width="8%" class="text-center">充值金币</th>'+
									'<th width="10%" class="text-center">充值时间</th>'+
									'<th width="8%" class="text-center">状态</th>'+
								'</tr>';
					for(var i=0;i<r.data.length;i++){
						var classColor = "";
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
						tableHtml += '<tr><td class="text-center">'+
						(pageSize*(pageNum-1)+i+1)+'</td><td class="text-center">'
						+r.data[i].orderNum+'</td><td class="text-center">'
						+(r.data[i].amount).toFixed(2)+'</td><td class="text-left"><p>支付类型：'
						+r.data[i].capitalPlatform+'</p><p>渠道：'+r.data[i].accountName
						+'</p><p>账号：'+(r.data[i].accountNum==null?'':r.data[i].accountNum)
						+'</p></td><td class="text-center">'+
						r.data[i].remark+'</td><td class="text-center">'
						+(r.data[i].increaseDAmount).toFixed(2)+'</td><td class="text-center">'
						+formatDate(r.data[i].createtime)+'</td><td class="text-center '+classColor+'">'
						+getStatus(r.data[i].status)+'</td></tr>'; 
					}
					if (r.totalPageCount!=0) {
					    $('#pageTool4').Paging({
					        prevTpl: "<",
					        nextTpl: ">",
					        pagesize: pageSize,
					        count: r.totalResultCount,
					        current: pageNum,
					        toolbar: true,
					        pageSizeList: [50, 200, 1000],
					        callback: function(page, size, count) {
					            pageNum = page;
					            rechargelist();
					            flag = false;
					            document.body.scrollTop = document.documentElement.scrollTop = 0;
					        }
					    });
					    $("#pageTool4 .ui-paging-container .ui-paging-toolbar").append("<b class='paging-total'>共"+r.totalResultCount+"条</b>")
					    $("#pageTool4").show().find(".ui-paging-container:last").siblings().remove();
					}else{
						$("#pageTool4").hide();
					}
					$(".ui-select-pagesize").unbind("change").change(function() {
					    pageSize = $(this).val();
					    pageNum = 1;
					    rechargelist();
					});
					if(r.data.length==0){
						tableHtml += '<tr><td colspan="8" align="center">暂无相关数据</td></tr>';
					}
					$(".rechargeRecord table").html(tableHtml);
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
//提现记录
function withdrawlist(){
	$.ajax({
        url: '../back/userHistory/withdrawlist',
        type: 'get',
        async:false,
        data: {
        	'pageSize':pageSize,
        	'pageNum':pageNum,
        	'frontUser':uuid
        }
    }).done(function(r) {
    		if (r.status == "SUCCESS") {
    			var tableHtml = '<tr>'+
									'<th width="7%" class="text-center">序号</th>'+
									'<th width="10%" class="text-center">订单号</th>'+
									'<th width="12%" class="text-center">提现金额/元(不含手续费)</th>'+
									'<th width="8%" class="text-center">手续费/元</th>'+
									'<th width="15%" class="text-center">用户提现信息</th>'+
									'<th width="15%" class="text-center">打款信息</th>'+
									'<th width="15%" class="text-center">处理信息</th>'+
									'<th width="8%" class="text-center">状态</th>'+
								'</tr>';
					for(var i=0;i<r.data.length;i++){
						var classColor = "";
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
						tableHtml += '<tr><td class="text-center">'+
						(pageSize*(pageNum-1)+i+1)+'</td><td class="text-center">'
						+r.data[i].orderNum+'</td><td class="text-center">'
						+(r.data[i].actualAmount).toFixed(2)+'</td><td class="text-center">'
						+(r.data[i].poundage).toFixed(2)+'</td><td class="text-left"><p>姓名：'
						+r.data[i].frontUserAccountHolder+'</p><p>卡号：'
						+r.data[i].frontUserAccountNumber+'</p><p>开户行：'
						+(r.data[i].frontUserBankName==null?'':r.data[i].frontUserBankName)
						+'</p></td><td class="text-center">'
						+r.data[i].transData+'</td><td class="text-center"><p>处理人：'
						+(r.data[i].operatorName?r.data[i].operatorName:'-')+'</p><p>'
						+(r.data[i].operattime?formatDate(r.data[i].operattime):'-')+'</p></td><td class="text-center '+classColor+'">'
						+getStatus(r.data[i].status)+'</td></tr>'; 
					}
					if (r.totalPageCount!=0) {
					    $('#pageTool5').Paging({
					        prevTpl: "<",
					        nextTpl: ">",
					        pagesize: pageSize,
					        count: r.totalResultCount,
					        current: pageNum,
					        toolbar: true,
					        pageSizeList: [50, 200, 1000],
					        callback: function(page, size, count) {
					            pageNum = page;
					            withdrawlist();
					            flag = false;
					            document.body.scrollTop = document.documentElement.scrollTop = 0;
					        }
					    });
					    $("#pageTool5 .ui-paging-container .ui-paging-toolbar").append("<b class='paging-total'>共"+r.totalResultCount+"条</b>")
					    $("#pageTool5").show().find(".ui-paging-container:last").siblings().remove();
					}else{
						$("#pageTool5").hide();
					}
					$(".ui-select-pagesize").unbind("change").change(function() {
					    pageSize = $(this).val();
					    pageNum = 1;
					    withdrawlist();
					});
					if(r.data.length==0){
						tableHtml += '<tr><td colspan="8" align="center">暂无相关数据</td></tr>';
					}
					$(".withdrawalsRecord table").html(tableHtml);
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
//红包
function voucherlist(status){
	$.ajax({
        url: '../back/userHistory/voucherlist',
        type: 'get',
        async:false,
        data: {
        	'pageSize':pageSize,
        	'pageNum':pageNum,
        	'status':status,
        	'frontUser':uuid
        }
    }).done(function(r) {
    		if (r.status == "SUCCESS") {
    			var tableHtml = '<tr>'+
									'<th width="8%" class="text-center">序号</th>'+
									'<th width="20%" class="text-center">名称</th>'+
									'<th width="12%" class="text-center">总额</th>'+
									'<th width="15%" class="text-center">额度限制</th>'+
									'<th width="15%" class="text-center">生效时间</th>'+
									'<th width="15%" class="text-center">过期时间</th>'+
									'<th width="15%" class="text-center">发放时间</th>'+
								'</tr>';
					for(var i=0;i<r.data.length;i++){
						tableHtml += '<tr><td class="text-center">'+
						(pageSize*(pageNum-1)+i+1)+'</td><td class="text-center">'
						+r.data[i].title+'</td><td class="text-center">'
						+r.data[i].dAmount+'</td><td class="text-center">'
						+r.data[i].payMin+'</td><td class="text-center">'+
						(r.data[i].starttime?formatDate(r.data[i].starttime):'-')+'</td><td class="text-center">'
						+(r.data[i].endtime?formatDate(r.data[i].endtime):'长期')+'</td><td class="text-center">'
						+formatDate(r.data[i].createtime)+'</td></tr>'; 
					}
					
					if (r.totalPageCount!=0) {
					    $('#pageTool6').Paging({
					        prevTpl: "<",
					        nextTpl: ">",
					        pagesize: pageSize,
					        count: r.totalResultCount,
					        current: pageNum,
					        toolbar: true,
					        pageSizeList: [50, 200, 1000],
					        callback: function(page, size, count) {
					            pageNum = page;
					            voucherlist(status);
					            flag = false;
					            document.body.scrollTop = document.documentElement.scrollTop = 0;
					        }
					    });
					    $("#pageTool6 .ui-paging-container .ui-paging-toolbar").append("<b class='paging-total'>共"+r.totalResultCount+"条</b>")
					    $("#pageTool6").show().find(".ui-paging-container:last").siblings().remove();
					}else{
						$("#pageTool6").hide();
					}
					$(".ui-select-pagesize").unbind("change").change(function() {
					    pageSize = $(this).val();
					    pageNum = 1;
					    withdrawlist();
					});
					if(r.data.length==0){
						tableHtml += '<tr><td colspan="7" align="center">暂无相关数据</td></tr>';
					}
					$(".redEnvelopes table").html(tableHtml);
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
//加入黑名单
function addBlack(){
	layer.prompt({title: '加入黑名单', formType: 0,}, function(pass, index){
		  if($(".layui-layer-input").val().replace(/(^\s*)|(\s*$)/g, "")==""){
				layer.msg("请填写原因");
				return false;
			}
			$.ajax({
		        url: '../back/user/blackadd',
		        type: 'post',
		        async:false,
		        data: {
		        	'uuid':uuid,
		        	'reason':$(".layui-layer-input").val().replace(/(^\s*)|(\s*$)/g, "")
		        }
			}).done(function(r) {
	    		if (r.status == "SUCCESS") {
	    			layer.msg('操作成功', {
	    				time: 1000 
	    			}, function(){
	    				var index = parent.layer.getFrameIndex(window.name); 
	    				window.parent.getList(size,num);
//	    				parent.location.reload();
	    				parent.layer.close(index);
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
	});
}
//设为高危
function setHigh(status){
	layer.confirm('确定要执行操作吗？', {
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
        url: '../back/user/changeStatus',
        type: 'post',
        async:false,
        data: {
        	'uuid':uuid,
        	'status':status
        }
    }).done(function(r) {
    		if (r.status == "SUCCESS") {
	        	getList();
    			layer.msg("设定成功", {
		            time: 2000
		        },function(){	
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
//更改用户等级
function changeLevel(status){
	$.ajax({
        url: '../back/user/group',
        type: 'post',
        async:false,
        data: {
        	'uuid':uuid,
        	'level':status
        }
    }).done(function(r) {
    		if (r.status == "SUCCESS") {
    			getList();
    			var index = parent.layer.getFrameIndex(window.name); 
				window.parent.getList(size,num);
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
}
layui.use(['form'], function(){
	 var form = layui.form
	 form.on('select(userLevel)',function(data){
		 changeLevel(data.value)
	 })
})

