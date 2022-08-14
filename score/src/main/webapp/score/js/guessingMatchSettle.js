var flagSubmit = true;
var uuid = (url('?uuid') != null) ? url('?uuid') : '';
var hasVictory = false;//有没有胜负平记录
var hasScore = false;//有没有比分记录
var transform = {
	"won":"胜",
	"drawn":"平",
	"lost":"负"
}

$(function(){
	getGuessingDetail();
});

//获取竞猜比赛信息
function getGuessingDetail(){
	$.ajax({
      url: '../back/guessingMatchOdds/get',
      type: 'get',
      async:false,
      traditional:true,
      data: {
    	  "guessingMatch":uuid
      }
  }).done(function(r) {
  		if (r.status == "SUCCESS") {
  			$(".matchPlayed").html(r.data.matchDetail.hometeamName+"&nbsp;VS&nbsp;"+r.data.matchDetail.awayteamName);
  			$(".matchGame").html(r.data.matchDetail.categoryName+r.data.matchDetail.roundName);
  			$(".matchTime").html(formatDate(r.data.matchDetail.time));
  			//循环列表
  			for(var i=0;i<r.data.oddsDetail.length;i++){
  				if(r.data.oddsDetail[i].type=="victory"){
	  				hasVictory = true;
	  				$(".winorloseBox .guessBox-position").show();
  					var ulHtml = "";
  					var objVictory={},newArrVictory=[];
  					r.data.oddsDetail[i].oddsList.forEach(function(item,suffix){
  				        if(!objVictory[item.spread]){
  				            var arr=[];
  				            arr.push(item);
  				            newArrVictory.push(arr);
  				            objVictory[item.spread]=item;
  				        }else{
  				        	newArrVictory.forEach(function(value,index){
  				                if(value[0].spread==item.spread){
  				                    value.push(item)
  				                }
  				            })
  				        }
  				    })
  				    for(var j=0;j<newArrVictory.length;j++){
  				    	var str = {};
  				    	for(var l=0;l<newArrVictory[j].length;l++){
  				    		str[newArrVictory[j][l].result]=newArrVictory[j][l].uuid
  				    	}
  				    	if(newArrVictory[j][0].spread!="0"){
  				    		ulHtml+='<li>让('+newArrVictory[j][0].spread+
  				    		')&nbsp;&nbsp;<div class="scoreBtn" data-uuid="'+str.won
  				    		+'">胜</div><div class="scoreBtn '+(str.drawn?'':'click-notAllowed')+'" data-uuid="'+str.drawn+'">平</div>'
  				    		+'<div class="scoreBtn" data-uuid="'+str.lost+'">负</div>';
  				    	}else{
  				    		ulHtml+='<li>平(&nbsp;'+newArrVictory[j][0].spread
  				    		+'&nbsp;)&nbsp;&nbsp;<div class="scoreBtn" data-uuid="'+str.won+'">胜</div><div class="scoreBtn '
  				    		+(str.drawn?'':'click-notAllowed')+'" data-uuid="'+str.drawn+'">平</div>'
  				    		+'<div class="scoreBtn" data-uuid="'+str.lost+'">负</div>';
  				    	}
  				    }
  					$(".winorloseBox ul").html(ulHtml).attr("data-uuid",r.data.oddsDetail[i].uuid).parents(".content-detail").show();
  					$(".winorloseBox ul .scoreBtn").click(function(){						
  						var flag = $(this).attr("data-uuid");
  						if(flag!="undefined"){
  							$(this).addClass("light").siblings().removeClass("light");
  						}
  					})
  					
  				}
  				if(r.data.oddsDetail[i].type=="score"){
	  				hasScore = true;
	  				$(".scoreBox .guessBox-position").show();
  					$(".scoreBox ul").attr("data-uuid",r.data.oddsDetail[i].uuid);
  					var strs = {};
  					for(var l=0;l<r.data.oddsDetail[i].oddsList.length;l++){
				    	strs[r.data.oddsDetail[i].oddsList[l].result]=r.data.oddsDetail[i].oddsList[l].uuid
				    }
  					var li = '<li><div class="scoreTitle">主胜</div>'+
  					'<div class="scoreBtn" data-uuid="'+strs['1:0']+'"><label>1:0</label></div>'+
  					'<div class="scoreBtn" data-uuid="'+strs['2:0']+'"><label>2:0</label></div>'+
  					'<div class="scoreBtn" data-uuid="'+strs['2:1']+'"><label>2:1</label></div>'+
  					'<div class="scoreBtn" data-uuid="'+strs['3:0']+'"><label>3:0</label></div>'+
  					'<div class="scoreBtn" data-uuid="'+strs['3:1']+'"><label>3:1</label></div>'+
  					'<div class="scoreBtn" data-uuid="'+strs['3:2']+'"><label>3:2</label></div>'+
  					'<div class="scoreBtn" data-uuid="'+strs['4:0']+'"><label>4:0</label></div>'+
  					'<div class="scoreBtn" data-uuid="'+strs['4:1']+'"><label>4:1</label></div>'+
  					'<div class="scoreBtn" data-uuid="'+strs['4:2']+'"><label>4:2</label></div>'+
  					'<div class="scoreBtn" data-uuid="'+strs['5:0']+'"><label>5:0</label></div>'+
  					'<div class="scoreBtn" data-uuid="'+strs['5:1']+'"><label>5:1</label></div>'+
  					'<div class="scoreBtn" data-uuid="'+strs['5:2']+'"><label>5:2</label></div>'+
  					'<div class="scoreBtn" data-uuid="'+strs['otherWon']+'"><label>其他胜</label></div>'+
  					'<br/><div class="scoreTitle">平</div>'+
  					'<div class="scoreBtn" data-uuid="'+strs['0:0']+'"><label>0:0</label></div>'+
  					'<div class="scoreBtn" data-uuid="'+strs['1:1']+'"><label>1:1</label></div>'+
  					'<div class="scoreBtn" data-uuid="'+strs['2:2']+'"><label>2:2</label></div>'+
  					'<div class="scoreBtn" data-uuid="'+strs['3:3']+'"><label>3:3</label></div>'+
  					'<div class="scoreBtn" data-uuid="'+strs['otherDrawn']+'"><label>其他平</label></div>'+
  					'<br/><div class="scoreTitle">主负</div>'+
  					'<div class="scoreBtn" data-uuid="'+strs['0:1']+'"><label>0:1</label></div>'+
  					'<div class="scoreBtn" data-uuid="'+strs['0:2']+'"><label>0:2</label></div>'+
  					'<div class="scoreBtn" data-uuid="'+strs['1:2']+'"><label>1:2</label></div>'+
  					'<div class="scoreBtn" data-uuid="'+strs['0:3']+'"><label>0:3</label></div>'+
  					'<div class="scoreBtn" data-uuid="'+strs['1:3']+'"><label>1:3</label></div>'+
  					'<div class="scoreBtn" data-uuid="'+strs['2:3']+'"><label>2:3</label></div>'+
  					'<div class="scoreBtn" data-uuid="'+strs['0:4']+'"><label>0:4</label></div>'+
  					'<div class="scoreBtn" data-uuid="'+strs['1:4']+'"><label>1:4</label></div>'+
  					'<div class="scoreBtn" data-uuid="'+strs['2:4']+'"><label>2:4</label></div>'+
  					'<div class="scoreBtn" data-uuid="'+strs['0:5']+'"><label>0:5</label></div>'+
  					'<div class="scoreBtn" data-uuid="'+strs['1:5']+'"><label>1:5</label></div>'+
  					'<div class="scoreBtn" data-uuid="'+strs['2:5']+'"><label>2:5</label></div>'+
  					'<div class="scoreBtn" data-uuid="'+strs['otherLost']+'"><label>其他负</label></div>'+
  					'</li>';
  					$(".scoreBox ul").html(li).parents(".content-box").show();;
  					
  					$(".scoreBox ul .scoreBtn").click(function(){
  						var flag = $(this).attr("data-selected");
  						if(flag){
  							$(this).removeAttr("data-selected");
  							$(this).removeClass("light");
  						}else{
  							$(".scoreBox ul .scoreBtn").removeAttr("data-selected");
  							$(".scoreBox ul .scoreBtn").removeClass("light");
  							$(this).attr("data-selected",true)
  							$(this).addClass("light");
  						}
  					})
  				}				
  			}
  		} else {
  			
  		}
  }).fail(function(r) {
      
  });   	
}

$(".submit-btn").click(function(){
	layer.confirm('请确认录入结果正确无误！', {
		btn: ['确定','取消'] //按钮
	}, function(){
		var rightArray = [];
		var hasWin = true;
		$(".winorloseBox ul li").each(function(item,value){
			if($(this).find("div").hasClass("light")){
				rightArray.push($(this).find("div.light").attr("data-uuid"));
			}else{
				//li里没有选中div
				hasWin = false;
			}
		});
		if(!hasWin){
			layer.closeAll();
			layer.msg("竞猜胜平负有结果未选择");
			return false;
		}
		if($(".scoreBox ul").attr("data-uuid")){
			var rightScore = $(".scoreBox ul div[data-selected=true]").attr("data-uuid");
			if(rightScore){
				rightArray.push(rightScore);
			}else{
				layer.msg("竞猜比分有结果未选择");
				return false;
			}
		}
		$.ajax({
	        url: '../back/guessingMatch/finish',
	        type: 'post',
	        async:false,
	        traditional:true,
	        data: {
	           "uuid":uuid,
	           "rightArray": rightArray
	        }
	    }).done(function(r){
	    	if (r.status == "SUCCESS") {
	  			layer.msg("结算成功！", {
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
	    });
	}, function(){
	  layer.closeAll();
	});
});
