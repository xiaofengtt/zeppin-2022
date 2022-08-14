var layerIndex;
var resultData;
var resultScoreUUid;//记录比分各个id
var listDrawnArray=[],listWonArray=[],listLostArray=[],listDataArray=[];
var oddChange = "";
$(function(){
	getGuessingMatchOddList();
	getBetSum();
});
function getGuessingMatchOddList(){
	$.ajax({
      url: '../back/guessingMatchOdds/controlList',
      type: 'get',
      async:false,
      traditional:true,
      data: {
    	  
      }
  }).done(function(r) {
  		if (r.status == "SUCCESS") {
  			var html = '<div class="list-head"><div class="list-child1 text-center">场次</div><div class="list-child2 text-center">胜平负</div>'+
  			'<div class="list-child3 text-center">比分</div></div>'
  			for(var i=0;i<r.data.length;i++){
  				var html6="",html7="",html8="",html9="",htmlBottom="";
  				//循环oddsDetail区分比分和胜负平
  				for(var j=0;j<r.data[i].oddsDetail.length;j++){
  					if(r.data[i].oddsDetail[j].type=="victory"){//胜负平类型
  						var objVictory={},newArrVictory=[];
  	  					r.data[i].oddsDetail[j].oddsList.forEach(function(item,suffix){//把所有胜负平同一个比赛放到同一个数组里
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
  				    	html6="",html7="",html8="",html9="";
  				    	var strUuid = {};
  	  				    for(var m=0;m<newArrVictory.length;m++){
	  				    	var str = {};
	  				    	var drawn,drawnCN,drawnUuid;
	  				    	for(var l=0;l<newArrVictory[m].length;l++){
	  				    		str[newArrVictory[m][l].result]=newArrVictory[m][l].odds.toFixed(2);
	  				    		strUuid[newArrVictory[m][l].result] = newArrVictory[m][l].uuid;
	  				    	}
	  				    	if(!str.drawn){
	  				    		drawn = "";
	  				    		drawnCN = "-";
	  				    		drawnUuid = "";
	  				    	}else{
	  				    		drawn = str.drawn;
	  				    		drawnCN = str.drawn;
	  				    		drawnUuid = strUuid.drawn;
	  				    	}
	  				    	var spreadCN = newArrVictory[m][0].spread==0?"平(&nbsp;"+newArrVictory[m][0].spread+"&nbsp;)":"让("+newArrVictory[m][0].spread+")";
	  				    	html6+="<div class='controlOdds' data-result='"+JSON.stringify(strUuid)+"'><p data-spread='"
	  				    	+newArrVictory[m][0].spread+"' data-won='"+str.won+"' data-drawn='"
	  				    	+drawn+"' data-lost='"+str.lost+"'>"+spreadCN+"</p><p style='margin-bottom:10px;'>投注金额</p>"+
	  				    	"<div class='oddsChangeBox'><h6>赔率指数变化</h6><ul></ul></div></div>";
	  				    	html7+="<p data-result='"+JSON.stringify(strUuid)+"' data-spread='"
	  				    	+newArrVictory[m][0].spread+"' data-won='"+str.won+"' data-drawn='"
	  				    	+drawn+"' data-lost='"+str.lost+"' id='"+strUuid.won+"'>"+str.won+"</p><p class='color-red' id='"
	  				    	+newArrVictory[m][0].guessingMatchType+newArrVictory[m][0].spread+"won' style='margin-bottom:10px;'>0</p>";
	  				    	html8+="<p data-result='"+JSON.stringify(strUuid)+"' data-spread='"
	  				    	+newArrVictory[m][0].spread+"' data-won='"+str.won+"' data-drawn='"
	  				    	+drawn+"' data-lost='"+str.lost+"' id='"+drawnUuid+"'>"+drawnCN+"</p><p class='color-red' id='"
	  				    	+newArrVictory[m][0].guessingMatchType+newArrVictory[m][0].spread+"drawn' style='margin-bottom:10px;'>0</p>";
	  				    	html9+="<p data-result='"+JSON.stringify(strUuid)+"' data-spread='"
	  				    	+newArrVictory[m][0].spread+"' data-won='"+str.won+"' data-drawn='"
	  				    	+drawn+"' data-lost='"+str.lost+"' id='"+strUuid.lost+"'>"+str.lost+"</p><p class='color-red' id='"
	  				    	+newArrVictory[m][0].guessingMatchType+newArrVictory[m][0].spread+"lost' style='margin-bottom:10px;'>0</p>";
	  				    }
  					}else{
  						var strs = {};
  						var strUUidScore = {};
  				    	for(var l=0;l<r.data[i].oddsDetail[j].oddsList.length;l++){
  				    		strs[r.data[i].oddsDetail[j].oddsList[l].result]=r.data[i].oddsDetail[j].oddsList[l].odds.toFixed(2);
  				    		strUUidScore[r.data[i].oddsDetail[j].oddsList[l].result]=r.data[i].oddsDetail[j].oddsList[l].uuid;
  				    	}
  						htmlBottom+='<div class="guessScoreInfo"><label>比分</label><span>赔率</span><p>投注金额</p></div>'+
  						'<div><label>1:0</label><span data-id="1:0" id="'+strUUidScore['1:0']+'">'+strs['1:0']+'</span><p class="color-red" id="'+r.data[i].oddsDetail[j].oddsList[0].guessingMatchType+'1-0">0</p></div>'+
  	  					'<div><label>2:0</label><span data-id="2:0" id="'+strUUidScore['2:0']+'">'+strs['2:0']+'</span><p class="color-red" id="'+r.data[i].oddsDetail[j].oddsList[0].guessingMatchType+'2-0">0</p></div>'+
  	  					'<div><label>2:1</label><span data-id="2:1" id="'+strUUidScore['2:1']+'">'+strs['2:1']+'</span><p class="color-red" id="'+r.data[i].oddsDetail[j].oddsList[0].guessingMatchType+'2-1">0</p></div>'+
  	  					'<div><label>3:0</label><span data-id="3:0" id="'+strUUidScore['3:0']+'">'+strs['3:0']+'</span><p class="color-red" id="'+r.data[i].oddsDetail[j].oddsList[0].guessingMatchType+'3-0">0</p></div>'+
  	  					'<div><label>3:1</label><span data-id="3:1" id="'+strUUidScore['3:1']+'">'+strs['3:1']+'</span><p class="color-red" id="'+r.data[i].oddsDetail[j].oddsList[0].guessingMatchType+'3-1">0</p></div>'+
  	  					'<div><label>3:2</label><span data-id="3:2" id="'+strUUidScore['3:2']+'">'+strs['3:2']+'</span><p class="color-red" id="'+r.data[i].oddsDetail[j].oddsList[0].guessingMatchType+'3-2">0</p></div>'+
  	  					'<div><label>4:0</label><span data-id="4:0" id="'+strUUidScore['4:0']+'">'+strs['4:0']+'</span><p class="color-red" id="'+r.data[i].oddsDetail[j].oddsList[0].guessingMatchType+'4-0">0</p></div>'+
  	  					'<div><label>4:1</label><span data-id="4:1" id="'+strUUidScore['4:1']+'">'+strs['4:1']+'</span><p class="color-red" id="'+r.data[i].oddsDetail[j].oddsList[0].guessingMatchType+'4-1">0</p></div>'+
  	  					'<div><label>4:2</label><span data-id="4:2" id="'+strUUidScore['4:2']+'">'+strs['4:2']+'</span><p class="color-red" id="'+r.data[i].oddsDetail[j].oddsList[0].guessingMatchType+'4-2">0</p></div>'+
  	  					'<div><label>5:0</label><span data-id="5:0" id="'+strUUidScore['5:0']+'">'+strs['5:0']+'</span><p class="color-red" id="'+r.data[i].oddsDetail[j].oddsList[0].guessingMatchType+'5-0">0</p></div>'+
  	  					'<div><label>5:1</label><span data-id="5:1" id="'+strUUidScore['5:1']+'">'+strs['5:1']+'</span><p class="color-red" id="'+r.data[i].oddsDetail[j].oddsList[0].guessingMatchType+'5-1">0</p></div>'+
  	  					'<div><label>5:2</label><span data-id="5:2" id="'+strUUidScore['5:2']+'">'+strs['5:2']+'</span><p class="color-red" id="'+r.data[i].oddsDetail[j].oddsList[0].guessingMatchType+'5-2">0</p></div>'+
  	  					'<div><label>胜其他</label><span data-id="otherWon" id="'+strUUidScore['otherWon']+'">'+strs['otherWon']+'</span><p class="color-red" id="'+r.data[i].oddsDetail[j].oddsList[0].guessingMatchType+'otherWon">0</p></div>'+
  	  					'<br/><div class="guessScoreInfo"><label>比分</label><span>赔率</span><p>投注金额</p>'+
  	  					'</div><div><label>0:0</label><span data-id="0:0" id="'+strUUidScore['0:0']+'">'+strs['0:0']+'</span><p class="color-red" id="'+r.data[i].oddsDetail[j].oddsList[0].guessingMatchType+'0:0">0</p></div>'+
  	  					'<div><label>1:1</label><span data-id="1:1" id="'+strUUidScore['1:1']+'">'+strs['1:1']+'</span><p class="color-red" id="'+r.data[i].oddsDetail[j].oddsList[0].guessingMatchType+'1-1">0</p></div>'+
  	  					'<div><label>2:2</label><span data-id="2:2" id="'+strUUidScore['2:2']+'">'+strs['2:2']+'</span><p class="color-red" id="'+r.data[i].oddsDetail[j].oddsList[0].guessingMatchType+'2-2">0</p></div>'+	
  	  					'<div><label>3:3</label><span data-id="3:3" id="'+strUUidScore['3:3']+'">'+strs['3:3']+'</span><p class="color-red" id="'+r.data[i].oddsDetail[j].oddsList[0].guessingMatchType+'3-3">0</p></div>'+
  	  					'<div><label>平其他</label><span data-id="otherDrawn" id="'+strUUidScore['otherDrawn']+'">'+strs['otherDrawn']+'</span><p id="'+r.data[i].oddsDetail[j].oddsList[0].guessingMatchType+'otherDrawn">0</p></div>'+
  	  					'<br/><div class="guessScoreInfo"><label>比分</label><span>赔率</span><p>投注金额</p>'+
  	  					'</div><div><label>0:1</label><span data-id="0:1" id="'+strUUidScore['0:1']+'">'+strs['0:1']+'</span><p class="color-red" id="'+r.data[i].oddsDetail[j].oddsList[0].guessingMatchType+'0-1">0</p></div>'+
  	  					'<div><label>0:2</label><span data-id="0:2" id="'+strUUidScore['0:2']+'">'+strs['0:2']+'</span><p class="color-red" id="'+r.data[i].oddsDetail[j].oddsList[0].guessingMatchType+'0-2">0</p></div>'+
  	  					'<div><label>1:2</label><span data-id="1:2" id="'+strUUidScore['1:2']+'">'+strs['1:2']+'</span><p class="color-red" id="'+r.data[i].oddsDetail[j].oddsList[0].guessingMatchType+'1-2">0</p></div>'+
  	  					'<div><label>0:3</label><span data-id="0:3" id="'+strUUidScore['0:3']+'">'+strs['0:3']+'</span><p class="color-red" id="'+r.data[i].oddsDetail[j].oddsList[0].guessingMatchType+'0-3">0</p></div>'+
  	  					'<div><label>1:3</label><span data-id="1:3" id="'+strUUidScore['1:3']+'">'+strs['1:3']+'</span><p class="color-red" id="'+r.data[i].oddsDetail[j].oddsList[0].guessingMatchType+'1-3">0</p></div>'+
  	  					'<div><label>2:3</label><span data-id="2:3" id="'+strUUidScore['2:3']+'">'+strs['2:3']+'</span><p class="color-red" id="'+r.data[i].oddsDetail[j].oddsList[0].guessingMatchType+'2-3">0</p></div>'+
  	  					'<div><label>0:4</label><span data-id="0:4" id="'+strUUidScore['0:4']+'">'+strs['0:4']+'</span><p class="color-red" id="'+r.data[i].oddsDetail[j].oddsList[0].guessingMatchType+'0:4">0</p></div>'+
  	  					'<div><label>1:4</label><span data-id="1:4" id="'+strUUidScore['1:4']+'">'+strs['1:4']+'</span><p class="color-red" id="'+r.data[i].oddsDetail[j].oddsList[0].guessingMatchType+'1-4">0</p></div>'+
  	  					'<div><label>2:4</label><span data-id="2:4" id="'+strUUidScore['2:4']+'">'+strs['2:4']+'</span><p class="color-red" id="'+r.data[i].oddsDetail[j].oddsList[0].guessingMatchType+'2-4">0</p></div>'+
  	  					'<div><label>0:5</label><span data-id="0:5" id="'+strUUidScore['0:5']+'">'+strs['0:5']+'</span><p class="color-red" id="'+r.data[i].oddsDetail[j].oddsList[0].guessingMatchType+'0-5">0</p></div>'+
  	  					'<div><label>1:5</label><span data-id="1:5" id="'+strUUidScore['1:5']+'">'+strs['1:5']+'</span><p class="color-red" id="'+r.data[i].oddsDetail[j].oddsList[0].guessingMatchType+'1-5">0</p></div>'+
  	  					'<div><label>2:5</label><span data-id="2:5" id="'+strUUidScore['2:5']+'">'+strs['2:5']+'</span><p class="color-red" id="'+r.data[i].oddsDetail[j].oddsList[0].guessingMatchType+'2-5">0</p></div>'+
  	  					'<div><label>负其他</label><span data-id="otherLost" id="'+strUUidScore['otherLost']+'">'+strs['otherLost']+'</span><p class="color-red" id="'+r.data[i].oddsDetail[j].oddsList[0].guessingMatchType+'otherLost">0</p></div>';
  					}	
  				} 	
  				html += '<div class="list-item" data-uuid="'+r.data[i].uuid+'"><div class="list-child1"><div><b>赛事:</b><span class="child1-value">'
  				+r.data[i].matchDetail.categoryName+r.data[i].matchDetail.roundName+'</span></div><div><b>主场:</b><span class="child1-value">'+r.data[i].matchDetail.hometeamName
  				+'</span></div><div><b>客场:</b><span class="child1-value">'+r.data[i].matchDetail.awayteamName+'</span></div><div><b>时间:</b><span class="child1-value">'+formatDates(r.data[i].matchDetail.time)
  				+'</div></div><div class="list-child2 text-center"><div class="list-child6 text-center">'+html6+'</div><div class="list-child7 text-center oddsChange" data-array="listWonArray">'+html7
  				+'</div><div class="list-child8 text-center oddsChange" data-array="listDrawnArray">'+html8+'</div>'
  				+'<div class="list-child9 text-center oddsChange" data-array="listLostArray">'+html9+'</div></div>'+				
  				'<div class="list-item-bottom list-child3" data-data=\''+JSON.stringify(strs)+'\' data-id=\''+JSON.stringify(strUUidScore)+'\'>'
  				+htmlBottom+'</div><div class="clear"></div></div>'
  			}
  			$(".matchOddList").html(html);
  			$(".controlOdds").hover(function(){
  				if($(this).attr("data-result")){
	  				listDrawnArray=[],listWonArray=[],listLostArray=[],listDataArray=[],oddChange = "";
	  				for(var key in JSON.parse($(this).attr("data-result"))){
	  					$.ajax({
		  			      url: '../back/guessingMatchOdds/historyList',
		  			      type: 'get',
		  			      async:false,
		  			      traditional:true,
		  			      data: {
		  			    	  "uuid":JSON.parse($(this).attr("data-result"))[key]
			  			      }
			  			  }).done(function(r) {
				  				if(r.status=="SUCCESS"){
				  					for(var i=0;i<r.data.length;i++){
				  						if(key=="won"){
				  							listWonArray.push(r.data[i].odds);
				  							listDataArray.push(r.data[i].createtime);
				  						}else if(key=="drawn"){
				  							listDrawnArray.push(r.data[i].odds);
				  						}else{
				  							listLostArray.push(r.data[i].odds);
				  						}
				  					}
				  				}
			  			  })
	  				}
  				}
  				//处理赔率变化
  				var listOddArray = [];
  				for(var i=0;i<listWonArray.length;i++){
  					listOddArray[i]=new Array();
  					listOddArray[i][0]= listDataArray[i];
  					listOddArray[i][1]= listWonArray[i];
  					listOddArray[i][2]= listDrawnArray[i];
  					listOddArray[i][3]= listLostArray[i];
  				}
  				for(var j=0;j<listOddArray.length;j++){
  					var DrawnCN = listOddArray[j][2];
  					if(!listOddArray[j][2]){
  						DrawnCN = "-"
  					}
					var class1 = "oddEqual",class2 ="oddEqual",class3 ="oddEqual";
  					if(j!=listOddArray.length-1){
  						if(listOddArray[j][1]&&listOddArray[j][1]>listOddArray[j+1][1]){
  							class1 = "oddUp";
  						}else if(listOddArray[j][1]&&listOddArray[j][1]==listOddArray[j+1][1]){
  							class1 = "oddEqual";
  						}else{
  							class1 = "oddDown";
  						}
  						if(listOddArray[j][2]){
  							if(listOddArray[j][2]>listOddArray[j+1][2]){
  	  							class2 = "oddUp";
  	  						}else if(listOddArray[j][2]==listOddArray[j+1][2]){
  	  							class2 = "oddEqual";
  	  						}else{
  	  							class2 = "oddDown";
  	  						}
  						}
  						if(listOddArray[j][3]>listOddArray[j+1][3]){
  							class3 = "oddUp";
  						}else if(listOddArray[j][3]==listOddArray[j+1][3]){
  							class3 = "oddEqual";
  						}else{
  							class3 = "oddDown";
  						}
  						oddChange+='<li><span>'+formatDates(listOddArray[j][0]).substring(0,formatDates(listOddArray[j][0]).length-3)
  						+'</span><span>胜:<b class="'+class1+'">'+listOddArray[j][1]
  						+'</b><img src="img/icon-'+class1+'.png"/></span><span>平:<b class="'+class2+'">'+DrawnCN
  						+'</b><img src="img/icon-'+class2+'.png"/></span><span>负:<b class="'+class3+'">'+listOddArray[j][3]
  						+'</b><img src="img/icon-'+class3+'.png"/></span></li>';
  					}else{
  						oddChange+='<li><span>'+formatDates(listOddArray[j][0]).substring(0,formatDates(listOddArray[j][0]).length-3)+'</span><span>胜:<b>'+listOddArray[j][1]
  						+'</b><img src="img/icon-'+class1+'.png"/></span><span>平:<b>'+DrawnCN
  						+'</b><img src="img/icon-'+class2+'.png"/></span><span>负:<b>'+listOddArray[j][3]
  						+'</b><img src="img/icon-'+class3+'.png"/></span></li>';
  					}
  				}
  				$(this).find(".oddsChangeBox ul").html(oddChange).show();
  				$(this).find(".oddsChangeBox").show();
  			},function(){
  				$(this).find(".oddsChangeBox").hide();
  			});
  			$(".list-child7 p,.list-child8 p,.list-child9 p").click(function(){
  				if($(this).attr("data-spread")){

  				var resultUuid = JSON.parse($(this).attr("data-result"));
  				var spread = $(this).attr("data-spread");
  				var won = $(this).attr("data-won");
  				var drawn = $(this).attr("data-drawn");
  				var lost = $(this).attr("data-lost");
  				layerIndex=layer.open({
  				  type: 1,
  				  title: ["竞猜胜负平","text-align:center"],
  				  closeBtn: 1,
  				  shadeClose: true,
  				  area:['500px'],
  				  content: '<div class="guessWinorLose"><div class="form-group"><label>让球：</label><input class="form-control spreadInput" disabled value="'+spread+
  				  '"/><div class="clear"></div></div>'+
  				  '<div class="form-group"><label>赔率：</label><span>胜</span><input class="form-control input1" value="'+won+'" /><span>平</span><input value="'
  				  +drawn+'" class="form-control input2" /><span>负</span><input class="form-control input3" value="'+lost+'" /><div class="clear"></div></div>'+
  				  '<div class="btn-group"><a class="cancle-btn" onclick="layer.closeAll();">取消</a><a class="sure-btn" onclick="addWinorLose(\''+resultUuid.won+'\',\''+resultUuid.drawn+'\',\''
  				  +resultUuid.lost+'\',\''+won+'\',\''+drawn+'\',\''+lost+'\')">保存</a></div></div>'
  				});		
  				}
  			});
  			$(".list-item-bottom").click(function(){
  				resultData = JSON.parse($(this).attr("data-data"));
  				resultScoreUUid = JSON.parse($(this).attr("data-id"));
  				layerIndex = layer.open({
  				  type: 1,
  				  title: ["竞猜比分","text-align:center"],
  				  closeBtn: 1,
  				  shadeClose: true,
  				  area:['850px'],
  				  content: '<div class="guessScore">'+
  				  '<div class="form-group"><label>主胜:</label><div class="guessScoreContent"><span>1:0</span><input class="form-control input1-0" data-class="'+resultScoreUUid['1:0']+'" value="'+resultData['1:0']
  				  +'" data-old="'+resultData['1:0']+'" onblur="value=moneyFormats(value)" /><span>2:0</span><input data-old="'+resultData['2:0']+'" value="'+resultData['2:0']
  				  +'" data-class="'+resultScoreUUid['2:0']+'" class="form-control input2-0" onblur="value=moneyFormats(value)" /><span>2:1</span><input class="form-control input2-1" data-old="'
  				  +resultData['2:1']+'" data-class="'+resultScoreUUid['2:1']+'" value="'+resultData['2:1']
  				  +'" onblur="value=moneyFormats(value)"/><span>3:0</span><input class="form-control input3-0" data-old="'+resultData['3:0']+'" value="'
  				  +resultData['3:0']+'" data-class="'+resultScoreUUid['3:0']+'" onblur="value=moneyFormats(value)" />'+
  				  '<span>3:1</span><input class="form-control input3-1" data-old="'+resultData['3:1']+'" data-class="'+resultScoreUUid['3:1']+'" value="'+resultData['3:1']+
  				  '" onblur="value=moneyFormats(value)" /><span>3:2</span><input class="form-control input3-2" data-old="'+resultData['3:2']+'" data-class="'+resultScoreUUid['3:2']
  				+'" value="'+resultData['3:2']+'" onblur="value=moneyFormats(value)" /><span>4:0</span><input class="form-control input4-0" data-old="'
  				+resultData['4:0']+'" data-class="'+resultScoreUUid['4:0']+'" value="'
  				  +resultData['4:0']+'" onblur="value=moneyFormats(value)" />'+
  				  '<span>4:1</span><input class="form-control input4-1" data-class="'+resultScoreUUid['4:1']+'" data-old="'+resultData['4:1']+'" value="'+resultData['4:1']+
  				  '" onblur="value=moneyFormats(value)" /><span>4:2</span><input class="form-control input4-2" data-class="'+resultScoreUUid['4:2']+'" data-old="'+resultData['4:2']+'" value="'+
  				  resultData['4:2']+'" onblur="value=moneyFormats(value)" />'+
  				  '<span>5:0</span><input class="form-control input5-0" data-class="'+resultScoreUUid['5:0']+'" data-old="'+resultData['5:0']+'" value="'+resultData['5:0']+
  				  '" onblur="value=moneyFormats(value)" /><span>5:1</span><input class="form-control input5-1" data-class="'+resultScoreUUid['5:1']+'" data-old="'+resultData['5:1']+'" value="'
  				  +resultData['5:1']+'" onblur="value=moneyFormats(value)" /><span>5:2</span><input class="form-control input5-2" data-old="'+resultData['5:2']
  				+'" value="'+resultData['5:2']+'" data-class="'+resultScoreUUid['5:2']+'" onblur="value=moneyFormats(value)" />'+
  				  '<span>胜其他</span><input class="form-control inputOtherWon" value="'+resultData['otherWon']+'" data-old="'+resultData['otherWon']+
  				  '" data-class="'+resultScoreUUid['otherWon']+'" onblur="value=moneyFormats(value)" /><div class="clear"></div></div><div class="clear">'+
  				  '</div></div><div class="form-group"><label>平:</label><div class="guessScoreContent"><span>0:0</span><input class="form-control input0-0" value="'
  				  +resultData['0:0']+'" data-class="'+resultScoreUUid['0:0']+'" data-old="'+resultData['0:0']+'" onblur="value=moneyFormats(value)" />'+
  				  '<span>1:1</span><input class="form-control input1-1" data-class="'+resultScoreUUid['1:1']+'" value="'+resultData['1:1']+'" data-old="'+resultData['1:1']+
  				  '" onblur="value=moneyFormats(value)" /><span>2:2</span><input class="form-control input2-2" data-old="'+resultData['2:2']+'" value="'+
  				  resultData['2:2']+'" data-class="'+resultScoreUUid['2:2']+'" onblur="value=moneyFormats(value)" /><span>3:3</span><input class="form-control input3-3" value="'+resultData['3:3']+
  				  '" data-class="'+resultScoreUUid['3:3']+'" data-old="'+resultData['3:3']+'" onblur="value=moneyFormats(value)" />'+
  				  '<span>平其他</span><input class="form-control inputOtherDrawn" data-class="'+resultScoreUUid['otherDrawn']+'" data-old="'+resultData['otherDrawn']+'" value="'+resultData['otherDrawn']
  				+'" onblur="value=moneyFormats(value)" /><div class="clear"></div></div><div class="clear"></div>'+
  			  '</div><div class="form-group"><label>主负:</label><div class="guessScoreContent"><span>0:1</span><input class="form-control input0-1" data-class="'+resultScoreUUid['0:1']+'" value="'+
  				  resultData['0:1']+'" data-old="'+resultData['0:1']+'" onblur="value=moneyFormats(value)" />'+
  				  '<span>0:2</span><input class="form-control input0-2" data-class="'+resultScoreUUid['0:2']+'" data-old="'+resultData['0:2']+'" value="'+resultData['0:2']
  				+'" onblur="value=moneyFormats(value)" /><span>1:2</span><input class="form-control input1-2" data-class="'+resultScoreUUid['1:2']+'" data-old="'+resultData['1:2']+'" value="'+
  				  resultData['1:2']+'" onblur="value=moneyFormats(value)" /><span>0:3</span><input class="form-control input0-3" data-class="'+resultScoreUUid['0:3']+'" data-old="'+resultData['0:3']
  				+'" value="'+resultData['0:3']+'" onblur="value=moneyFormats(value)" />'+
  				  '<span>1:3</span><input class="form-control input1-3" data-class="'+resultScoreUUid['1:3']+'" data-old="'+resultData['1:3']+'" value="'+resultData['1:3']+
  				  '" onblur="value=moneyFormats(value)" /><span>2:3</span><input class="form-control input2-3" data-class="'+resultScoreUUid['2:3']+'" data-old="'+resultData['2:3']+'" value="'+
  				  resultData['2:3']+'" onblur="value=moneyFormats(value)" /><span>0:4</span><input class="form-control input0-4" data-class="'+resultScoreUUid['0:4']+'" data-old="'+resultData['0:4']
  				+'" value="'+resultData['0:4']+'" onblur="value=moneyFormats(value)" />'+
  				  '<span>1:4</span><input class="form-control input1-4" data-class="'+resultScoreUUid['1:4']+'" data-old="'+resultData['1:4']+'" value="'+resultData['1:4']
  				+'" onblur="value=moneyFormats(value)" /><span>2:4</span><input class="form-control input2-4" data-class="'+resultScoreUUid['2:4']+'" data-old="'+resultData['2:4']+'" value="'+
  				  resultData['2:4']+'" onblur="value=moneyFormats(value)" /><span>0:5</span><input class="form-control input0-5" data-class="'+resultScoreUUid['0:5']+'" data-old="'+resultData['0:5']
  				+'" value="'+resultData['0:5']+'" onblur="value=moneyFormats(value)" />'+
  				  '<span>1:5</span><input class="form-control input1-5" data-class="'+resultScoreUUid['1:5']+'" data-old="'+resultData['1:5']+'" value="'+resultData['1:5']
  				+'" onblur="value=moneyFormats(value)" /><span>2:5</span><input class="form-control input2-5" data-class="'+resultScoreUUid['2:5']+'" data-old="'+resultData['2:5']+'" value="'+
  				  resultData['2:5']+'" onblur="value=moneyFormats(value)" /><span>负其他</span><input class="form-control inputOtherLost" data-class="'+resultScoreUUid['otherLost']+'" data-old="'
  				  +resultData['otherLost']+'" value="'+resultData['otherLost']
  				  +'" onblur="value=moneyFormats(value)" /><div class="clear"></div></div><div class="clear"></div></div>'+
  				  '<div class="btn-group"><a class="cancle-btn" onclick="layer.closeAll();">取消</a><a class="sure-btn" onclick="addScore()">保存</a></div></div>'
  				});
  			});
  		}
  })
}

function addWinorLose(won,drawn,lost,one,two,three){
	if(one==$(".guessWinorLose .input1").val().replace(/^\s+|\s+$/g, '')&&two==$(".guessWinorLose .input2").val().replace(/^\s+|\s+$/g, '')
			&&three==$(".guessWinorLose .input3").val().replace(/^\s+|\s+$/g, '')){
		layer.msg("没有修改操作");
		layer.close(layerIndex);
		return false;
	}
	var data;
	if(drawn!="undefined"){
		data = [won+'@_@'+$(".guessWinorLose .input1").val(),
	            drawn+'@_@'+$(".guessWinorLose .input2").val(),
	            lost+'@_@'+$(".guessWinorLose .input3").val()];
	}else{
		data = [won+'@_@'+$(".guessWinorLose .input1").val(),
	            lost+'@_@'+$(".guessWinorLose .input3").val()];
	}
	$.ajax({
	      url: '../back/guessingMatchOdds/update',
	      type: 'post',
	      async:false,
	      traditional:true,
	      data: {
	    	  "datas":data
		      }
	  }).done(function(r) {
		  if(r.status=="SUCCESS"){
			  layer.msg("修改成功！", {
		            time: 2000
		        },function(){
//		        	$("#"+won).html($(".guessWinorLose .input1").val());
//		        	if(drawn){
//		        		$("#"+drawn).html($(".guessWinorLose .input2").val());
//		        	}
//		        	$("#"+lost).html($(".guessWinorLose .input3").val());
					layer.close(layerIndex);
					window.location.href=window.location.href;
					return false;
		        });
		  }
	  })

}
function addScore(){
	var hasEdit = false;//是否有修改
	$(".guessScore input").each(function(item,value){
		if(moneyFormats($(this).attr("data-old"))!=moneyFormats($(this).val())){
			hasEdit = true;
			return false;
		}
	});
	if(hasEdit){
		var data=[];
		$(".guessScore input").each(function(item,value){
			data.push($(this).attr("data-class")+'@_@'+$(this).val().replace(/^\s+|\s+$/g, ''));
		});
		$.ajax({
		      url: '../back/guessingMatchOdds/update',
		      type: 'post',
		      async:false,
		      traditional:true,
		      data: {
		    	  "datas":data
			      }
		  }).done(function(r) {
			  if(r.status=="SUCCESS"){
				  layer.msg("修改成功！", {
			            time: 2000
			        },function(){
//			        	$(".guessScore input").each(function(item,value){
//			        		$("#"+$(this).attr("data-class")).html($(this).val().replace(/^\s+|\s+$/g, ''));
//			    		});
						layer.close(layerIndex);
						window.location.href=window.location.href;
						return false;
			        });
			  }
		  })
	}else{
		layer.msg("没有修改操作");
		layer.close(layerIndex);
	}
}
//获取投注额
function getBetSum(){
	$.ajax({
	      url: '../back/guessingMatchOdds/getBetSum',
	      type: 'get',
	      async:false,
	      traditional:true,
	      data: {
	    	  
		      }
	  }).done(function(r) {
		  if(r.status=="SUCCESS"){
			  for(var i=0;i<r.data.length;i++){
				if(r.data[i].spread){
					$("#"+r.data[i].guessingMatchType+r.data[i].spread+r.data[i].result).html(r.data[i].sum);
				}else{
					$("#"+r.data[i].guessingMatchType+r.data[i].result.replace(':','-')).html(r.data[i].sum);
				}  
			  }
		  }
	  })
}


