$(document).ready(function(){
	var uuid = url("?uuid");
	get();
	function get(){
		$.ajax({
			type:"get",
			url:api + "/employee/getBillInfo",
			async:true,
			data:{
				"token":token(),
				"uuid":uuid
			},
			beforeSend:function(){
				loadingIn();
			}
		})
		.done(function(r){
			if(r.status == "SUCCESS"){
				var html = '';
				//收
				if(r.data.priceflag){
					html='<div class="top">'
						+'<i class="bg-red">'
							+'<span class="fuckWechat">收</span>'	
						+'</i>'
						+'<span>'+r.data.typeCN+'</span>'
						+'<p class="color-red">+'+r.data.price+'</p>'
						+'<b class="color-'+r.data.status+'">'+r.data.statusCN+'</b>'
					+'</div>'
					+'<div class="msg">'
					+'<div class="msg-item">'
						+'<p>本次余额</p>'
						+'<span class="value" id="accountBalance">'+r.data.accountBalanceCN+'</span>'
					+'</div>'
					+'<div class="msg-item">'
						+'<p>交易时间</p>'
						+'<span class="value">'+r.data.createtimeCN+'</span>'
					+'</div>'
					+'<div class="msg-item">'
						+'<p>订单号</p>'
						+'<span class="value">'+r.data.orderNum+'</span>'
					+'</div>'
					+'<div class="msg-item">'
						+'<p>备注</p>'
						+'<span class="value">'+r.data.remark+'</span>'
					+'</div>'
				+'</div>';			
				}else{//支
					var account='';
					//提现
					if(r.data.type=="withdraw"){
						account = r.data.bankName+'(尾号'+r.data.bankcard+')';
						$(".right-first b,.right-second b").html(r.data.createtimeCN);
						
						if(r.data.status=="SUCCESS"){//交易成功
							$(".right-second p").html("银行处理中");
							$(".right-third p").html("到账成功");
							if(r.data.processCreatetimeCN){
								$(".right-third b").html(r.data.processCreatetimeCN);
							}else{
								$(".right-third b").html(r.data.createtimeCN);
							}							
							$(".progressLeft div").addClass("light");
						}else if(r.data.status=="FAIL"){//交易失败
							$(".right-second p").html("交易失败");
							$(".right-third p").html("到账成功").css("color","rgba(44, 44, 44, 0.4)");
							$(".right-second b").html(r.data.processCreatetimeCN);
						}else if(r.data.status=="TRANSACTING"){//交易中
							$(".right-second p").html("银行处理中");
							$(".right-third p").html("到账成功").css("color","rgba(44, 44, 44, 0.4)");
						}
						$(".progressDiv").show();
					}else if(r.data.type=="cur_buy"){//活期盈转入
						account = '平台余额';
					}else if(r.data.type=="buy"){
						account = '平台余额';
					}
					html='<div class="top">'
						+'<i class="bg-green">'
						+'<span class="fuckWechat">支</span>'
						+'</i>'
						+'<span>'+r.data.typeCN+'</span>'
						+'<p class="color-green">-'+r.data.price+'</p>'
						+'<b class="color-'+r.data.status+'">'+r.data.statusCN+'</b>'
						+'</div>'
						+'<div class="msg">'
						+'<div class="msg-item">'
						+'<p>本次余额</p>'
						+'<span class="value" id="accountBalance">'+r.data.accountBalanceCN+'</span>'
						+'</div>'
						+'<div class="msg-item">'
						+'<p>交易账户</p>'
						+'<span class="value">'+account+'</span>'
						+'</div>'
						+'<div class="msg-item">'
						+'<p>订单号</p>'
						+'<span class="value">'+r.data.orderNum+'</span>'
						+'</div>'
						+'<div class="msg-item">'
						+'<p>备注</p>'
						+'<span class="value">'+r.data.remark+'</span>'
						+'</div>'
						+'</div>';
					
				}
				$(".main").prepend(html);
				if(/android/.test(ua)){
					$(".fuckWechat").css({
						"height":"0.2rem"
					});	
				}
				$("#accountBalance").html(formatNum(r.data.accountBalanceCN));
			}else{
				if(r.status != "ERROR"){
					layerIn(r.message);
				}else{
					isWechat();
				}
			}
			loadingOut();
		})
		.fail(function(){
			loadingOut();
			layerIn("服务器错误");
		});
	}
});
