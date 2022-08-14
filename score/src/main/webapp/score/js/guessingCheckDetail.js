var flagSubmit = true;
var uuid = (url('?uuid') != null) ? url('?uuid') : '';
var transform = {
		"won":"胜",
		"drawn":"平",
		"lost":"负"
	}
$(function(){
	getUserBetDetail();
});
//获取订单详情
function getUserBetDetail(){
	$.ajax({
	      url: '../back/userBet/get',
	      type: 'get',
	      async:false,
	      traditional:true,
	      data: {
	    	  "uuid":uuid
	      }
	  }).done(function(r) {
	  		if (r.status == "SUCCESS") {
	  			var html = "";
	  			if(r.data.status!="normal"){
	  				$(".publish-succcess,.publish-refuse").hide();
	  			}
	  			if(r.data.type=="victory"){
	  				$(".matchGame").html("竞猜胜负平");
	  				for(var i=0;i<r.data.detailList.length;i++){
		  				var dan = r.data.detailList[i].flagCorrect==true?'胆':'-';
		  				var spread = r.data.detailList[i].spread=="0"?"":"("+r.data.detailList[i].spread+")";
		  				var content = r.data.detailList[i].spread=="0"?transform[r.data.detailList[i].result]:"让"+transform[r.data.detailList[i].result];
		  				if(i==0){
		  					html += '<tr><td>'+formatDate(r.data.detailList[i].time)+'</td><td class="text-right">'+r.data.detailList[i].hometeamName
		  					+spread+'</td><td class="text-center">VS</td><td>'
		  						+r.data.detailList[i].awayteamName+'</td><td class="text-center">'+content+'('+r.data.detailList[i].odds.toFixed(2)+')'
		  					+'</td><td>'+dan+'</td></tr>';
		  				}else{
		  					html += '<tr><td>'+formatDate(r.data.detailList[i].time)+'</td><td class="text-right">'+r.data.detailList[i].hometeamName+spread
		  					+'</td><td class="text-center">VS</td><td>'+r.data.detailList[i].awayteamName+'</td><td class="text-center">'
		  					+content+'('+r.data.detailList[i].odds.toFixed(2)+')'
		  					+'</td><td>'+dan+'</td></tr>';
		  				}
		  				$(".matchMoney").html(r.data.price.toFixed(2)+"元");
	  					$(".matchTime").html(formatDate(r.data.createtime));
		  			}
	  			}else{
	  				$(".matchGame").html("竞猜比分");
	  				for(var i=0;i<r.data.detailList.length;i++){
		  				var dan = r.data.detailList[i].flagCorrect==true?'胆':'-';
		  				var content = r.data.detailList[i].result;
		  				if(i==0){
		  					html += '<tr><td class="text-center">'+formatDate(r.data.detailList[i].time)+'</td><td class="text-right">'+r.data.detailList[i].hometeamName
		  					+'</td><td class="text-center">VS</td><td>'+r.data.detailList[i].awayteamName+'</td><td class="text-center">'
		  					+content+'('+r.data.detailList[i].odds+')'
		  					+'</td><td>'+dan+'</td></tr>';		  		
		  				}else{
		  					html += '<tr><td class="text-center">'+formatDate(r.data.detailList[i].time)+'</td><td class="text-right">'+r.data.detailList[i].hometeamName
		  					+'</td><td class="text-center">VS</td><td>'+r.data.detailList[i].awayteamName+'</td><td class="text-center">'
		  					+content+'('+r.data.detailList[i].odds+')'
		  					+'</td><td>'+dan+'</td></tr>';		  		
		  				}
	  					$(".matchMoney").html(r.data.price+"元");
	  					$(".matchTime").html(formatDate(r.data.createtime));
		  			}
	  			}
//	  			计算几串几
	  			var betInfo = "";
	  			Object.keys(r.data.betMap).forEach(function(key){
	  				betInfo += '单关,'+r.data.betMap[key]+"倍&nbsp;;&nbsp;";
//	  				betInfo += key+'串1，2注，'+r.data.betMap[key]+"倍；";
	  			});
	  			$(".matchType").html(betInfo);
	  			
	  			if(r.data.detailList.length==0){
	  				html = '<tr><td colspan="6">暂无相关内容</td></tr>'
	  			}
	  			$("table").append(html);
	  		}
	  }).fail(function(r) {
	      
	  });
}
//审核通过
$(".publish-succcess").click(function(){
	layer.confirm('确定审核通过吗？', {
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
		      url: '../back/userBet/confirm',
		      type: 'post',
		      async:false,
		      data: {
		    	  "uuid": uuid
		      }
		}).done(function(r) {
	  		if (r.status == "SUCCESS") {
	  			layer.msg("审核成功！", {
		            time: 2000
		        },function(){
    	        	window.location.href=document.referrer;
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
	}, function(){
		layer.closeAll();
	});
});
//审核不通过
$(".publish-refuse").click(function(){
	layer.confirm('确定审核不通过吗？', {
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
		      url: '../back/userBet/refund',
		      type: 'post',
		      async:false,
		      data: {
		    	  "uuid": uuid
		      }
		}).done(function(r) {
	  		if (r.status == "SUCCESS") {
	  			layer.msg("操作成功！", {
		            time: 2000
		        },function(){
    	        	window.location.href=document.referrer;
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
	}, function(){
		layer.closeAll();
	});
});