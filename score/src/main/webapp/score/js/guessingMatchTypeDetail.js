var flagSubmit = true;
var uuid = (url('?uuid') != null) ? url('?uuid') : '';
var hasVictory = false;//有没有胜负平记录
var hasScore = false;//有没有比分记录
var scoreFlag = true;//增加比分赔率是否填全
var statusScore = true;//比分状态
var statusVictory = true;//胜平负状态
var transform = {
	"won":"胜",
	"drawn":"平",
	"lost":"负"
}
$(function(){
	getGuessingDetail();
	 $(".valueDate,.valueDate-score").click(function() {
        laydate({
            start: laydate.now(0, "YYYY/MM/DD hh:mm:00"),
            istime: true,
            istoday: false,
            format: 'YYYY-MM-DD hh:mm:00'
        });
    });
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
//  			循环列表
  			for(var i=0;i<r.data.oddsDetail.length;i++){
  				if(r.data.oddsDetail[i].type=="victory"){
	  				hasVictory = true;
  					if(r.data.oddsDetail[i].status!="normal"){
  						$(".winorloseBox .guessBox-position").hide();
  						statusScore = false;
  					}else{
  						$(".winorloseBox .guessBox-position").show();
  						statusScore = true;
  					}
  					$(".winorloseBox .editBox .valueDate").val(formatDate(r.data.oddsDetail[i].endtime));
  					$(".winorloseBox .showBox .valueDates").html(formatDate(r.data.oddsDetail[i].endtime));
  					$(".winorloseBox .editBox .maxMoney").val(r.data.oddsDetail[i].maxMoney);
  					$(".winorloseBox .showBox .maxMoneys").html(r.data.oddsDetail[i].maxMoney+"倍");
  					if(!r.data.oddsDetail[i].flagSingle){
  						$(".winorloseBox .editBox input[name='radio']").eq(0).removeAttr("checked");
  						$(".winorloseBox .editBox input[name='radio']").eq(1).attr("checked","checked");
  						$(".winorloseBox .editBox input[name='radio']").eq(1).click();
  					}  					
  					$(".winorloseBox .showBox .valueRadio").html(r.data.oddsDetail[i].flagSingle==true?"是":"否");
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
  				    	var strIsright = {};
  				    	var drawn,drawnCN;
  				    	for(var l=0;l<newArrVictory[j].length;l++){
  				    		str[newArrVictory[j][l].result]=newArrVictory[j][l].odds.toFixed(2);
  				    		strIsright[newArrVictory[j][l].result]=newArrVictory[j][l].isRight;
  				    	}
  				    	if(str.drawn){
  				    		drawn = str.drawn;
  				    		drawnCN = str.drawn;
  				    	}else{
  				    		drawn = "";
  				    		drawnCN = "-"
  				    	}
  				    	if(newArrVictory[j][0].spread!="0"){
  				    		ulHtml+='<li attr-spread="'+newArrVictory[j][0].spread+'" attr-won="'+str.won+
  				    		'" attr-drawn="'+drawn+'" attr-lost="'+str.lost+'">让('+newArrVictory[j][0].spread+')，<span class="'
  				    		+(strIsright.won?'isrightLight':'')+'">胜'+str.won+'</span>，<span class="'
  		  				    		+(strIsright.drawn?'isrightLight':'')+'">平'
  				    		+drawnCN+'</span>，<span class="'
  				    		+(strIsright.lost?'isrightLight':'')+'">负'+str.lost+'</span>&nbsp;&nbsp;<a onclick="layeropen(\''
  				    		+newArrVictory[j][0].spread+'\',\''+str.won+'\',\''+drawn+'\',\''+str.lost+'\','+j+')">修改</a><a onclick="deleteLi(this)">删除</a></li>'
  				    	}else{
  				    		ulHtml+='<li attr-spread="'+newArrVictory[j][0].spread+'" attr-won="'+str.won+
  				    		'" attr-drawn="'+drawn+'" attr-lost="'+str.lost+'">让(&nbsp;'+newArrVictory[j][0].spread+')，<span class="'
  				    		+(strIsright.won?'isrightLight':'')+'">胜'+str.won+'</span>，<span class="'
	  				    		+(strIsright.drawn?'isrightLight':'')+'">平'
  				    		+drawnCN+'</span>，<span class="'
  				    		+(strIsright.lost?'isrightLight':'')+'">负'+str.lost+'&nbsp;&nbsp;<a onclick="layeropen(\''
  				    		+newArrVictory[j][0].spread+'\',\''+str.won+'\',\''+drawn+'\',\''+str.lost+'\','+j+')">修改</a><a onclick="deleteLi(this)">删除</a></li>'
  				    	}
  				    }
  					$(".winorloseBox ul").html(ulHtml).attr("data-uuid",r.data.oddsDetail[i].uuid);
  				}
  				if(r.data.oddsDetail[i].type=="score"){
	  				hasScore = true;
  					if(r.data.oddsDetail[i].status!="normal"){
  						$(".scoreBox .guessBox-position").hide();
  						statusVictory = false;
  					}else{
  						$(".scoreBox .guessBox-position").show();
  						statusVictory = true;
  					}
  					$(".scoreBox .editBox .valueDate-score").val(formatDate(r.data.oddsDetail[i].endtime));
  					$(".scoreBox .showBox .valueDates-score").html(formatDate(r.data.oddsDetail[i].endtime));
  					$(".scoreBox .editBox .maxMoney-score").val(r.data.oddsDetail[i].maxMoney);
  					$(".scoreBox .showBox .maxMoneys-score").html(r.data.oddsDetail[i].maxMoney+"倍");
  					if(!r.data.oddsDetail[i].flagSingle){
  						$(".scoreBox .editBox input[name='radios']").eq(0).removeAttr("checked");
  						$(".scoreBox .editBox input[name='radios']").eq(1).attr("checked","checked");
  						$(".scoreBox .editBox input[name='radios']").eq(1).click();
  					}  					
  					$(".scoreBox .showBox .valueRadio-score").html(r.data.oddsDetail[i].flagSingle==true?"是":"否");
  					$(".scoreBox ul").attr("data-uuid",r.data.oddsDetail[i].uuid);
  					var strs = {};
  					var strsIsright = {};
  					for(var l=0;l<r.data.oddsDetail[i].oddsList.length;l++){
				    	strs[r.data.oddsDetail[i].oddsList[l].result]=r.data.oddsDetail[i].oddsList[l].odds.toFixed(2);
				    	strsIsright[r.data.oddsDetail[i].oddsList[l].result]=r.data.oddsDetail[i].oddsList[l].isRight;
				    }
  					var li = '<li><div class="scoreTitle">主胜</div><div><label>比分</label><b>赔率</b></div>'+
  					'<div class="'+(strsIsright['1:0']?'isrightLight':'')+'"><label>1:0</label><span data-id="1:0">'+strs['1:0']+'</span></div>'+
  					'<div class="'+(strsIsright['2:0']?'isrightLight':'')+'"><label>2:0</label><span data-id="2:0">'+strs['2:0']+'</span></div>'+
  					'<div class="'+(strsIsright['2:1']?'isrightLight':'')+'"><label>2:1</label><span data-id="2:1">'+strs['2:1']+'</span></div>'+
  					'<div class="'+(strsIsright['3:0']?'isrightLight':'')+'"><label>3:0</label><span data-id="3:0">'+strs['3:0']+'</span></div>'+
  					'<div class="'+(strsIsright['3:1']?'isrightLight':'')+'"><label>3:1</label><span data-id="3:1">'+strs['3:1']+'</span></div>'+
  					'<div class="'+(strsIsright['3:2']?'isrightLight':'')+'"><label>3:2</label><span data-id="3:2">'+strs['3:2']+'</span></div>'+
  					'<div class="'+(strsIsright['4:0']?'isrightLight':'')+'"><label>4:0</label><span data-id="4:0">'+strs['4:0']+'</span></div>'+
  					'<div class="'+(strsIsright['4:1']?'isrightLight':'')+'"><label>4:1</label><span data-id="4:1">'+strs['4:1']+'</span></div>'+
  					'<div class="'+(strsIsright['4:2']?'isrightLight':'')+'"><label>4:2</label><span data-id="4:2">'+strs['4:2']+'</span></div>'+
  					'<div class="'+(strsIsright['5:0']?'isrightLight':'')+'"><label>5:0</label><span data-id="5:0">'+strs['5:0']+'</span></div>'+
  					'<div class="'+(strsIsright['5:1']?'isrightLight':'')+'"><label>5:1</label><span data-id="5:1">'+strs['5:1']+'</span></div>'+
  					'<div class="'+(strsIsright['5:2']?'isrightLight':'')+'"><label>5:2</label><span data-id="5:2">'+strs['5:2']+'</span></div>'+
  					'<div class="'+(strsIsright['otherWon']?'isrightLight':'')+'"><label>胜其他</label><span data-id="otherWon">'+strs['otherWon']+'</span></div>'+
  					'<br/><div class="scoreTitle">平</div><div><label>比分</label><b>赔率</b></div>'+
  					'<div class="'+(strsIsright['0:0']?'isrightLight':'')+'"><label>0:0</label><span data-id="0:0">'+strs['0:0']+'</span></div>'+
  					'<div class="'+(strsIsright['1:1']?'isrightLight':'')+'"><label>1:1</label><span data-id="1:1">'+strs['1:1']+'</span></div>'+
  					'<div class="'+(strsIsright['2:2']?'isrightLight':'')+'"><label>2:2</label><span data-id="2:2">'+strs['2:2']+'</span></div>'+	
  					'<div class="'+(strsIsright['3:3']?'isrightLight':'')+'"><label>3:3</label><span data-id="3:3">'+strs['3:3']+'</span></div>'+
  					'<div class="'+(strsIsright['otherDrawn']?'isrightLight':'')+'"><label>平其他</label><span data-id="otherDrawn">'+strs['otherDrawn']+'</span></div>'+
  					'<br/><div class="scoreTitle">主负</div><div><label>比分</label><b>赔率</b></div>'+
  					'<div class="'+(strsIsright['0:1']?'isrightLight':'')+'"><label>0:1</label><span data-id="0:1">'+strs['0:1']+'</span></div>'+
  					'<div class="'+(strsIsright['0:2']?'isrightLight':'')+'"><label>0:2</label><span data-id="0:2">'+strs['0:2']+'</span></div>'+
  					'<div class="'+(strsIsright['1:2']?'isrightLight':'')+'"><label>1:2</label><span data-id="1:2">'+strs['1:2']+'</span></div>'+
  					'<div class="'+(strsIsright['0:3']?'isrightLight':'')+'"><label>0:3</label><span data-id="0:3">'+strs['0:3']+'</span></div>'+
  					'<div class="'+(strsIsright['1:3']?'isrightLight':'')+'"><label>1:3</label><span data-id="1:3">'+strs['1:3']+'</span></div>'+
  					'<div class="'+(strsIsright['2:3']?'isrightLight':'')+'"><label>2:3</label><span data-id="2:3">'+strs['2:3']+'</span></div>'+
  					'<div class="'+(strsIsright['0:4']?'isrightLight':'')+'"><label>0:4</label><span data-id="0:4">'+strs['0:4']+'</span></div>'+
  					'<div class="'+(strsIsright['1:4']?'isrightLight':'')+'"><label>1:4</label><span data-id="1:4">'+strs['1:4']+'</span></div>'+
  					'<div class="'+(strsIsright['2:4']?'isrightLight':'')+'"><label>2:4</label><span data-id="2:4">'+strs['2:4']+'</span></div>'+
  					'<div class="'+(strsIsright['0:5']?'isrightLight':'')+'"><label>0:5</label><span data-id="0:5">'+strs['0:5']+'</span></div>'+
  					'<div class="'+(strsIsright['1:5']?'isrightLight':'')+'"><label>1:5</label><span data-id="1:5">'+strs['1:5']+'</span></div>'+
  					'<div class="'+(strsIsright['2:5']?'isrightLight':'')+'"><label>2:5</label><span data-id="2:5">'+strs['2:5']+'</span></div>'+
  					'<div class="'+(strsIsright['otherLost']?'isrightLight':'')+'"><label>负其他</label><span data-id="otherLost">'+strs['otherLost']+'</span></div>'+
  					'</li>';
  					$(".scoreBox ul").html(li);
  					$(".scoreBox ul li").click(function(){
  						if($(".scoreBox .guessSave").css("display")!="none"){
  							layeropenScore(strs);
  						}		
  					});
  				}				
  			}
  		} else {
  			
  		}
  }).fail(function(r) {
      
  });   	
}
$(".winorloseBox .guessEdit").click(function(){
	$(this).hide();
	$(".winorloseBox .showBox,.winorloseBox .guessPublish").hide();
	$(this).parent().find(".guessSave").css("display","inline-block");
	$(".winorloseBox .guessAdd,.winorloseBox .guessCancle").css("display","inline-block");
	$(".winorloseBox .editBox").show();
	$(".winorloseBox ul a").css("display","inline-block");
});
$(".scoreBox .guessEdit").click(function(){
	$(this).hide();
	$(".scoreBox .showBox,.scoreBox .guessPublish").hide();
	$(this).parent().find(".guessSave").css("display","inline-block");
	if($(".scoreBox ul").html().replace(/^\s+|\s+$/g, '')==""){
		$(".scoreBox .guessAdd").css("display","inline-block");
	}
	$(".scoreBox .editBox").show();
	$(".scoreBox .guessCancle").css("display","inline-block");
});
$(".winorloseBox .guessCancle").click(function(){
	$(this).hide();
	$(this).parent().parent().find(".editBox,ul a,.guessSave,.guessAdd").hide();
	$(this).parent().parent().find(".showBox,.guessEdit").show();
	if(statusScore){
		$(".winorloseBox .guessPublish").css("display","inline-block");
	}
});
$(".scoreBox .guessCancle").click(function(){
	$(this).hide();
	$(this).parent().parent().find(".editBox,ul a,.guessSave,.guessAdd").hide();
	$(this).parent().parent().find(".showBox,.guessEdit").show();
	if(statusVictory){
		$(".scoreBox .guessPublish").css("display","inline-block");
	}
});
function layeropen(spread,won,drawn,lost,position){
	layer.open({
	  type: 1,
	  title: ["竞猜胜负平","text-align:center"],
	  closeBtn: 1,
	  shadeClose: true,
	  area:['500px'],
	  content: '<div class="guessWinorLose"><div class="form-group"><label>让球：</label><input class="form-control spreadInput" value="'+spread+
	  '" onblur="value=value.replace(/[^(\-?)\d.+]/ig,\'\')"/><div class="clear"></div></div>'+
	  '<div class="form-group"><label>赔率：</label><span>胜</span><input class="form-control input1" value="'+won
	  +'" onblur="value=moneyFormats(value)" /><span>平</span><input value="'
	  +drawn+'" onblur="value=moneyFormats(value)" class="form-control input2" /><span>负</span><input class="form-control input3" value="'+
	  lost+'" onblur="value=moneyFormats(value)" /><div class="clear"></div></div>'+
	  '<div class="btn-group"><a class="cancle-btn" onclick="layer.closeAll();">取消</a><a class="sure-btn" onclick="addWinorLose('+position+')">保存</a></div></div>'
	});
}

function layeropenScore(str){
	var input1 = !str['1:0']?"":str['1:0'];
	var input2 = !str['2:0']?"":str['2:0'];
	var input3 = !str['2:1']?"":str['2:1'];
	var input4 = !str['3:0']?"":str['3:0'];
	var input5 = !str['3:1']?"":str['3:1'];
	var input6 = !str['3:2']?"":str['3:2'];
	var input7 = !str['4:0']?"":str['4:0'];
	var input8 = !str['4:1']?"":str['4:1'];
	var input9 = !str['4:2']?"":str['4:2'];
	var input10 = !str['5:0']?"":str['5:0'];
	var input11 = !str['5:1']?"":str['5:1'];
	var input12 = !str['5:2']?"":str['5:2'];
	var input13 = !str['otherWon']?"":str['otherWon'];
	var input14 = !str['0:0']?"":str['0:0'];
	var input15 = !str['1:1']?"":str['1:1'];
	var input16 = !str['2:2']?"":str['2:2'];
	var input17 = !str['3:3']?"":str['3:3'];
	var input18 = !str['otherDrawn']?"":str['otherDrawn'];
	var input19 = !str['0:1']?"":str['0:1'];
	var input20 = !str['0:2']?"":str['0:2'];
	var input21 = !str['1:2']?"":str['1:2'];
	var input22 = !str['0:3']?"":str['0:3'];
	var input23 = !str['1:3']?"":str['1:3'];
	var input24 = !str['2:3']?"":str['2:3'];
	var input25 = !str['0:4']?"":str['0:4'];
	var input26 = !str['1:4']?"":str['1:4'];
	var input27 = !str['2:4']?"":str['2:4'];
	var input28 = !str['0:5']?"":str['0:5'];
	var input29 = !str['1:5']?"":str['1:5'];
	var input30 = !str['2:5']?"":str['2:5'];
	var input31 = !str['otherLost']?"":str['otherLost'];
	layer.open({
		  type: 1,
		  title: ["竞猜比分","text-align:center"],
		  closeBtn: 1,
		  shadeClose: true,
		  area:['850px'],
		  content: '<div class="guessScore">'+
		  '<div class="form-group"><label>主胜:</label><div class="guessScoreContent"><span>1:0</span><input class="form-control input1-0" value="'+input1
		  +'" onblur="value=moneyFormats(value)" /><span>2:0</span><input value="'+input2
		  +'" class="form-control input2-0" onblur="value=moneyFormats(value)" /><span>2:1</span><input class="form-control input2-1" value="'+input3
		  +'" onblur="value=moneyFormats(value)"/><span>3:0</span><input class="form-control input3-0" value="'+input4+'" onblur="value=moneyFormats(value)" />'+
		  '<span>3:1</span><input class="form-control input3-1" value="'+input5+'" onblur="value=moneyFormats(value)" /><span>3:2</span><input class="form-control input3-2" value="'+input6
		  +'" onblur="value=moneyFormats(value)" /><span>4:0</span><input class="form-control input4-0" value="'+input7+'" onblur="value=moneyFormats(value)" />'+
		  '<span>4:1</span><input class="form-control input4-1" value="'+input8+'" onblur="value=moneyFormats(value)" /><span>4:2</span><input class="form-control input4-2" value="'+
		  input9+'" onblur="value=moneyFormats(value)" />'+
		  '<span>5:0</span><input class="form-control input5-0" value="'+input10+'" onblur="value=moneyFormats(value)" /><span>5:1</span><input class="form-control input5-1" value="'
		  +input11+'" onblur="value=moneyFormats(value)" /><span>5:2</span><input class="form-control input5-2" value="'+input12+'" onblur="value=moneyFormats(value)" />'+
		  '<span>胜其他</span><input class="form-control inputOtherWon" value="'+input13+'" onblur="value=moneyFormats(value)" /><div class="clear"></div></div><div class="clear">'+
		  '</div></div><div class="form-group"><label>平:</label><div class="guessScoreContent"><span>0:0</span><input class="form-control input0-0" value="'
		  +input14+'" onblur="value=moneyFormats(value)" />'+
		  '<span>1:1</span><input class="form-control input1-1" value="'+input15+'" onblur="value=moneyFormats(value)" /><span>2:2</span><input class="form-control input2-2" value="'+
		  input16+'" onblur="value=moneyFormats(value)" /><span>3:3</span><input class="form-control input3-3" value="'+input17+'" onblur="value=moneyFormats(value)" />'+
		  '<span>平其他</span><input class="form-control inputOtherDrawn" value="'+input18+'" onblur="value=moneyFormats(value)" /><div class="clear"></div></div><div class="clear"></div>'+
		  '</div><div class="form-group"><label>主负:</label><div class="guessScoreContent"><span>0:1</span><input class="form-control input0-1" value="'+
		  input19+'" onblur="value=moneyFormats(value)" />'+
		  '<span>0:2</span><input class="form-control input0-2" value="'+input20+'" onblur="value=moneyFormats(value)" /><span>1:2</span><input class="form-control input1-2" value="'+
		  input21+'" onblur="value=moneyFormats(value)" /><span>0:3</span><input class="form-control input0-3" value="'+input22+'" onblur="value=moneyFormats(value)" />'+
		  '<span>1:3</span><input class="form-control input1-3" value="'+input23+'" onblur="value=moneyFormats(value)" /><span>2:3</span><input class="form-control input2-3" value="'+
		  input24+'" onblur="value=moneyFormats(value)" /><span>0:4</span><input class="form-control input0-4" value="'+input25+'" onblur="value=moneyFormats(value)" />'+
		  '<span>1:4</span><input class="form-control input1-4" value="'+input26+'" onblur="value=moneyFormats(value)" /><span>2:4</span><input class="form-control input2-4" value="'+
		  input27+'" onblur="value=moneyFormats(value)" /><span>0:5</span><input class="form-control input0-5" value="'+input28+'" onblur="value=moneyFormats(value)" />'+
		  '<span>1:5</span><input class="form-control input1-5" value="'+input29+'" onblur="value=moneyFormats(value)" /><span>2:5</span><input class="form-control input2-5" value="'+
		  input30+'" onblur="value=moneyFormats(value)" /><span>负其他</span><input class="form-control inputOtherLost" value="'+input31
		  +'" onblur="value=moneyFormats(value)" /><div class="clear"></div></div><div class="clear"></div></div>'+
		  '<div class="btn-group"><a class="cancle-btn" onclick="layer.closeAll();">取消</a><a class="sure-btn" onclick="addScore()">保存</a></div></div>'
		});
}
//增加比分
function addScore(){
	$(".guessScore input").each(function(item,value){
		if($(this).val()==""){
			layer.msg("请填写完整赔率");
			scoreFlag = false;
			return;
		}else{
			scoreFlag = true;
		}
	});
	if(!scoreFlag){
		return false;
	}
	var input1 = $(".input1-0").val()==""?"0":$(".input1-0").val();
	var input2 = $(".input2-0").val()==""?"0":$(".input2-0").val();
	var input3 = $(".input2-1").val()==""?"0":$(".input2-1").val();
	var input4 = $(".input3-0").val()==""?"0":$(".input3-0").val();
	var input5 = $(".input3-1").val()==""?"0":$(".input3-1").val();
	var input6 = $(".input3-2").val()==""?"0":$(".input3-2").val();
	var input7 = $(".input4-0").val()==""?"0":$(".input4-0").val();
	var input8 = $(".input4-1").val()==""?"0":$(".input4-1").val();
	var input9 = $(".input4-2").val()==""?"0":$(".input4-2").val();
	var input10 = $(".input5-0").val()==""?"0":$(".input5-0").val();
	var input11 = $(".input5-1").val()==""?"0":$(".input5-1").val();
	var input12 = $(".input5-2").val()==""?"0":$(".input5-2").val();
	var input13 = $(".inputOtherWon").val()==""?"0":$(".inputOtherWon").val();
	var input14 = $(".input0-0").val()==""?"0":$(".input0-0").val();
	var input15 = $(".input1-1").val()==""?"0":$(".input1-1").val();
	var input16 = $(".input2-2").val()==""?"0":$(".input2-2").val();
	var input17 = $(".input3-3").val()==""?"0":$(".input3-3").val();
	var input18 = $(".inputOtherDrawn").val()==""?"0":$(".inputOtherDrawn").val();
	var input19 = $(".input0-1").val()==""?"0":$(".input0-1").val();
	var input20 = $(".input0-2").val()==""?"0":$(".input0-2").val();
	var input21 = $(".input1-2").val()==""?"0":$(".input1-2").val();
	var input22 = $(".input0-3").val()==""?"0":$(".input0-3").val();
	var input23 = $(".input1-3").val()==""?"0":$(".input1-3").val();
	var input24 = $(".input2-3").val()==""?"0":$(".input2-3").val();
	var input25 = $(".input0-4").val()==""?"0":$(".input0-4").val();
	var input26 = $(".input1-4").val()==""?"0":$(".input1-4").val();
	var input27 = $(".input2-4").val()==""?"0":$(".input2-4").val();
	var input28 = $(".input0-5").val()==""?"0":$(".input0-5").val();
	var input29 = $(".input1-5").val()==""?"0":$(".input1-5").val();
	var input30 = $(".input2-5").val()==""?"0":$(".input2-5").val();
	var input31 = $(".inputOtherLost").val()==""?"0":$(".inputOtherLost").val();
	var li = '<li><div class="scoreTitle">主胜</div><div><label>比分</label><b>赔率</b></div></div><div><label>1:0</label><span data-id="1:0">'+input1+'</span></div>'+
			'<div><label>2:0</label><span data-id="2:0">'+input2+'</span></div>'+
			'<div><label>2:1</label><span data-id="2:1">'+input3+'</span></div>'+
			'<div><label>3:0</label><span data-id="3:0">'+input4+'</span></div>'+
			'<div><label>3:1</label><span data-id="3:1">'+input5+'</span></div>'+
			'<div><label>3:2</label><span data-id="3:2">'+input6+'</span></div>'+
			'<div><label>4:0</label><span data-id="4:0">'+input7+'</span></div>'+
			'<div><label>4:1</label><span data-id="4:1">'+input8+'</span></div>'+
			'<div><label>4:2</label><span data-id="4:2">'+input9+'</span></div>'+
			'<div><label>5:0</label><span data-id="5:0">'+input10+'</span></div>'+
			'<div><label>5:1</label><span data-id="5:1">'+input11+'</span></div>'+
			'<div><label>5:2</label><span data-id="5:2">'+input12+'</span></div>'+
			'<div><label>胜其他</label><span data-id="otherWon">'+input13+'</span></div>'+
			'<br/><div class="scoreTitle">平</div><div><label>比分</label><b>赔率</b></div><div><label>0:0</label><span data-id="0:0">'+input14+'</span></div>'+
			'<div><label>1:1</label><span data-id="1:1">'+input15+'</span></div>'+
			'<div><label>2:2</label><span data-id="2:2">'+input16+'</span></div>'+	
			'<div><label>3:3</label><span data-id="3:3">'+input17+'</span></div>'+
			'<div><label>平其他</label><span data-id="otherDrawn">'+input18+'</span></div>'+
			'<br/><div class="scoreTitle">主负</div><div><label>比分</label><b>赔率</b></div><div><label>0:1</label><span data-id="0:1">'+input19+'</span></div>'+
			'<div><label>0:2</label><span data-id="0:2">'+input20+'</span></div>'+
			'<div><label>1:2</label><span data-id="1:2">'+input21+'</span></div>'+
			'<div><label>0:3</label><span data-id="0:3">'+input22+'</span></div>'+
			'<div><label>1:3</label><span data-id="1:3">'+input23+'</span></div>'+
			'<div><label>2:3</label><span data-id="2:3">'+input24+'</span></div>'+
			'<div><label>0:4</label><span data-id="0:4">'+input25+'</span></div>'+
			'<div><label>1:4</label><span data-id="1:4">'+input26+'</span></div>'+
			'<div><label>2:4</label><span data-id="2:4">'+input27+'</span></div>'+
			'<div><label>0:5</label><span data-id="0:5">'+input28+'</span></div>'+
			'<div><label>1:5</label><span data-id="1:5">'+input29+'</span></div>'+
			'<div><label>2:5</label><span data-id="2:5">'+input30+'</span></div>'+
			'<div><label>负其他</label><span data-id="otherLost">'+input31+'</span></div>'+
			'</li>';
	$(".scoreBox ul").html(li);
	var str = {};
	str['1:0']=input1;
    str['2:0']=input2;
    str['2:1']=input3;
    str['3:0']=input4;
    str['3:1']=input5;
    str['3:2']=input6;
    str['4:0']=input7;
    str['4:1']=input8;
    str['4:2']=input9;
    str['5:0']=input10;
    str['5:1']=input11;
    str['5:2']=input12;
    str['otherWon']=input13;
    str['0:0']=input14;
    str['1:1']=input15;
    str['2:2']=input16;
    str['3:3']=input17;
    str['otherDrawn']=input18;
    str['0:1']=input19;
    str['0:2']=input20;
    str['1:2']=input21;
    str['0:3']=input22;
    str['1:3']=input23;
    str['2:3']=input24;
    str['0:4']=input25;
    str['1:4']=input26;
    str['2:4']=input27;
    str['0:5']=input28;
    str['1:5']=input29;
    str['2:5']=input30;
    str['otherLost'] = input31;
	$(".scoreBox ul li").click(function(){
		if($(".scoreBox .guessSave").css("display")!="none"){
			layeropenScore(str);
		}		
	});
	layer.closeAll();
}
//增加一条胜负平竞猜
function addWinorLose(position){
	if($(".guessWinorLose .spreadInput").val()==""){
		layer.msg("请填写让球数");
		return false;
	}
	if($(".guessWinorLose .input1").val()==""||$(".guessWinorLose .input3").val()==""){
		layer.msg("请填写完整赔率");
		return false;
	}
	var inputDrawn = $(".guessWinorLose .input2").val()==""?"-":$(".guessWinorLose .input2").val();
	if(position!=undefined){
		if($(".guessWinorLose .spreadInput").val()!="0"){
			$(".winorloseBox ul li:eq("+position+")").html('让('+$(".guessWinorLose .spreadInput").val()+')，胜'
								+$(".guessWinorLose .input1").val()+'，平'
	  				    		+inputDrawn+'，负'+$(".guessWinorLose .input3").val()+'&nbsp;&nbsp;<a onclick="layeropen(\''+
	  				    		$(".guessWinorLose .spreadInput").val()+'\',\''+$(".guessWinorLose .input1").val()+'\',\''
	  				    	  +$(".guessWinorLose .input2").val()+'\',\''+$(".guessWinorLose .input3").val()+'\','+position+')">修改</a><a onclick="deleteLi(this)">删除</a>');		
		}else{
			$(".winorloseBox ul li:eq("+position+")").html('平(&nbsp;0&nbsp;)，胜'+$(".guessWinorLose .input1").val()+'，平'
			    		+inputDrawn+'，负'+$(".guessWinorLose .input3").val()+'&nbsp;&nbsp;<a onclick="layeropen(\''
			    		+$(".guessWinorLose .spreadInput").val()+'\',\''+$(".guessWinorLose .input1").val()+'\',\''
			    		  +$(".guessWinorLose .input2").val()+'\',\''+$(".guessWinorLose .input3").val()+'\','+position+')">修改</a><a onclick="deleteLi(this)">删除</a>');
		}
		$(".winorloseBox ul li:eq("+position+")").attr("attr-spread",$(".guessWinorLose .spreadInput").val());
		$(".winorloseBox ul li:eq("+position+")").attr("attr-won",$(".guessWinorLose .input1").val());
		$(".winorloseBox ul li:eq("+position+")").attr("attr-drawn",$(".guessWinorLose .input2").val());
		$(".winorloseBox ul li:eq("+position+")").attr("attr-lost",$(".guessWinorLose .input3").val());
		$(".winorloseBox ul a").css("display","inline-block");
	}else{
		if($(".guessWinorLose .spreadInput").val()!="0"){
			$(".winorloseBox ul").append('<li attr-spread="'+$(".guessWinorLose .spreadInput").val()+'" attr-won="'
					+$(".guessWinorLose .input1").val()+'" attr-drawn="'+$(".guessWinorLose .input2").val()+
					'" attr-lost="'+$(".guessWinorLose .input3").val()+'">让('+$(".guessWinorLose .spreadInput").val()+')，胜'
								+$(".guessWinorLose .input1").val()+'，平'
	  				    		+inputDrawn+'，负'+$(".guessWinorLose .input3").val()+'&nbsp;&nbsp;<a onclick="layeropen(\''+
	  				    		$(".guessWinorLose .spreadInput").val()+'\',\''+$(".guessWinorLose .input1").val()+'\',\''
	  				    	  +$(".guessWinorLose .input2").val()+'\',\''+$(".guessWinorLose .input3").val()+'\','+$(".winorloseBox ul li").length+')">修改</a><a onclick="deleteLi(this)">删除</a></li>');		
		}else{
			$(".winorloseBox ul").append('<li attr-spread="'+$(".guessWinorLose .spreadInput").val()+'" attr-won="'
					+$(".guessWinorLose .input1").val()+'" attr-drawn="'+$(".guessWinorLose .input2").val()+
					'" attr-lost="'+$(".guessWinorLose .input3").val()+'">平(&nbsp;0&nbsp;)，胜'+$(".guessWinorLose .input1").val()+'，平'
			    		+inputDrawn+'，负'+$(".guessWinorLose .input3").val()+'&nbsp;&nbsp;<a onclick="layeropen(\''
			    		+$(".guessWinorLose .spreadInput").val()+'\',\''+$(".guessWinorLose .input1").val()+'\',\''
			    		  +$(".guessWinorLose .input2").val()+'\',\''+$(".guessWinorLose .input3").val()+'\','+$(".winorloseBox ul li").length+')">修改</a><a onclick="deleteLi(this)">删除</a></li>');
		}
		$(".winorloseBox ul a").css("display","inline-block");
	}
	
	layer.closeAll();
}
//保存编辑
function winorloseSave(type){
	var odds = [];
	var url = "";
	var data = {};
	if(type=="victory"){
		if($(".valueDate").val()==""){
			layer.msg("请填写竞猜时间");
			return false;
		}
		if($(".maxMoney").val()==""){
			layer.msg("请填写最大投倍数");
			return false;
		}
		for(var i=0;i<$(".winorloseBox ul li").length;i++){
			odds.push($(".winorloseBox ul li:eq("+i+")").attr("attr-spread")+'@_@won@_@'+$(".winorloseBox ul li:eq("+i+")").attr("attr-won"));
			if($(".winorloseBox ul li:eq("+i+")").attr("attr-drawn")!=""){
				odds.push($(".winorloseBox ul li:eq("+i+")").attr("attr-spread")+'@_@drawn@_@'+$(".winorloseBox ul li:eq("+i+")").attr("attr-drawn"));
			}
			odds.push($(".winorloseBox ul li:eq("+i+")").attr("attr-spread")+'@_@lost@_@'+$(".winorloseBox ul li:eq("+i+")").attr("attr-lost"));
		}
		if(odds.length==0){
			layer.msg("请填写赔率");
			return false;
		}
		$(".winorloseBox .guessSave").hide();
		$(".winorloseBox .guessAdd").hide();
		$(".winorloseBox .guessCancle").hide();
		$(".winorloseBox .guessEdit").css("display","inline-block");
		$(".winorloseBox .guessPublish").css("display","inline-block");
		$(".winorloseBox .showBox").show();
		$(".winorloseBox .editBox").hide();
		$(".winorloseBox ul a").hide();
		if(hasVictory){
			url = '../back/guessingMatchOdds/editType';
			data = {
				  "uuid":$(".winorloseBox ul").attr("data-uuid"),
		    	  "oddsType":"asia",
		    	  "type":type,
		    	  "maxMoney":$(".maxMoney").val().replace(/[^\d]/g,""),
		    	  "flagSingle":$("input[name='radio']:checked").val(),
		    	  "betEndtime":$(".valueDate").val(),
		    	  "odds":odds	    
			}
		}else{
			url = '../back/guessingMatchOdds/addType';
			data = {
					  "guessingMatch":uuid,
			    	  "oddsType":"asia",
			    	  "type":type,
			    	  "maxMoney":$(".maxMoney").val().replace(/[^\d]/g,""),
			    	  "flagSingle":$("input[name='radio']:checked").val(),
			    	  "betEndtime":$(".valueDate").val(),
			    	  "odds":odds	    
				}
		}		
	}else{
		odds = [];
		if($(".valueDate-score").val()==""){
			layer.msg("请填写竞猜时间");
			return false;
		}
		if($(".maxMoney-score").val()==""){
			layer.msg("请填写最大投倍数");
			return false;
		}
		$(".scoreBox ul span").each(function(item,value){
			odds.push('@_@'+$(this).attr("data-id")+'@_@'+$(this).html());
		});
		if(odds.length==0){
			layer.msg("请填写赔率");
			return false;
		}
		$(".scoreBox .guessSave").hide();
		$(".scoreBox .guessAdd").hide();
		$(".scoreBox .guessCancle").hide();
		$(".scoreBox .guessEdit").css("display","inline-block");
		$(".scoreBox .guessPublish").css("display","inline-block");
		$(".scoreBox .showBox").show();
		$(".scoreBox .editBox").hide();
		if(hasScore){
			url = '../back/guessingMatchOdds/editType';
			data = {
					  "uuid":$(".scoreBox ul").attr("data-uuid"),
			    	  "oddsType":"asia",
			    	  "type":type,
			    	  "maxMoney":$(".maxMoney-score").val().replace(/[^\d]/g,""),
			    	  "flagSingle":$("input[name='radios']:checked").val(),
			    	  "betEndtime":$(".valueDate-score").val(),
			    	  "odds":odds	    
				}
		}else{
			url = '../back/guessingMatchOdds/addType';
			data = {
					  "guessingMatch":uuid,
			    	  "oddsType":"asia",
			    	  "type":type,
			    	  "maxMoney":$(".maxMoney-score").val().replace(/[^\d]/g,""),
			    	  "flagSingle":$("input[name='radios']:checked").val(),
			    	  "betEndtime":$(".valueDate-score").val(),
			    	  "odds":odds	    
				}
		}
	}
	
	$.ajax({
	      url: url,
	      type: 'post',
	      async:false,
	      traditional:true,
	      data: data
	  }).done(function(r) {
	  		if (r.status == "SUCCESS") {
	  			layer.msg("保存成功");	  			
	  			getGuessingDetail();
	  		} else {
	  			layer.msg(r.message);
	  			if(type=="victory"){
	  				$(".winorloseBox .guessSave").css("display","inline-block");
	  				$(".winorloseBox .guessAdd").css("display","inline-block");
	  				$(".winorloseBox .guessCancle").css("display","inline-block");
	  				$(".winorloseBox .guessEdit").hide();
	  				$(".winorloseBox .guessPublish").hide();
	  				$(".winorloseBox .showBox").hide();
	  				$(".winorloseBox .editBox").show();
	  				$(".winorloseBox ul a").css("display","inline-block");
	  			}else{
	  				$(".scoreBox .guessSave").css("display","inline-block");
	  				if($(".scoreBox ul").html().replace(/^\s+|\s+$/g, '')==""){
	  					$(".scoreBox .guessAdd").css("display","inline-block");
	  				}
	  				$(".scoreBox .guessCancle").css("display","inline-block");
	  				$(".scoreBox .guessEdit").hide();
	  				$(".scoreBox .guessPublish").hide();
	  				$(".scoreBox .showBox").hide();
	  				$(".scoreBox .editBox").show();				
	  			}
	  			
	  		}
	  }).fail(function(r) {
	      
	  });   	
}
//删除条数
function deleteLi(obj){
	$(obj).parent().remove();
}
//发布
$(".guessPublish").click(function(){
	var publishUuid = $(this).parent().parent().find("ul").attr("data-uuid");
	if(publishUuid){
	layer.confirm('确定发布这场比赛吗？', {
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
		      url: '../back/guessingMatchOdds/publishType',
		      type: 'post',
		      async:false,
		      data: {
		    	  "uuid": publishUuid
		      }
		}).done(function(r) {
	  		if (r.status == "SUCCESS") {
	  			layer.msg("发布成功！", {
		            time: 2000
		        },function(){
  		        	window.location.href=window.location.href;
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
	}else{
		layer.msg("暂无可发布内容");
	}
});
