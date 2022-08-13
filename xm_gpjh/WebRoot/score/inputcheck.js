/**
 * 获取光标所在的字符位置
 * @param obj 要处理的输入框
 * @author 靳希坤
 */
function getPosition(obj){
	//alert(obj.tagName);
	var result = 0;
	if(obj.selectionStart){ //非IE浏览器
		result = obj.selectionStart
	}else{ //IE
		var rng;
		if(obj.tagName == "TEXTAREA"){ //如果是文本域
			rng = event.srcElement.createTextRange();
			rng.moveToPoint(event.x,event.y);
		}else{ //输入框
			rng = document.selection.createRange();
		}
		rng.moveStart("character",-event.srcElement.value.length);
		result = rng.text.length;
	}
	return result;
}
/**
 * 返回在光标位置添加char后的结果
 * @param obj 要处理的输入框
 * @author 靳希坤
 */
function getValue(obj,char){
	if(window.document.selection.createRange().text.length>0){
		window.document.selection.clear();
	}
	var pos = getPosition(obj);
	return obj.value.substr(0,pos)+char+obj.value.substr(pos,obj.value.length);
}
/**
 * 检验登分时的字符输入
 * @param oinput 要处理的输入框
 * @author 靳希坤
 */
function checkkey(oinput){
	var k = window.event.keyCode;
	var id = oinput.id;
	var num = id.substr(4);
	if(k>=48&&k<=57){//判断是不是数字
		window.document.getElementById("ss1_"+num).selectedIndex=0;
		var v = getValue(oinput,k-48);
		if(!/^\d+([\.])?\d?$/.test(v)){
			alert("小数点后面只能有一位数字");
			window.event.keyCode=0;
		}else if(v>100){
			alert("对不起，您输入的数据有问题！");
			window.event.keyCode=0;
		}
	}else if(k==46){//判断是不是小数点
		var re = /^\d+([\.])?\d?$/;			 	
		if(!re.test(getValue(oinput,'.'))){
			alert("对不起，您输入的有问题！");
			window.event.keyCode=0;
		}
	}else if(k==47){//判断是不是"/"即缺考
		window.document.getElementById("ss1_"+num).selectedIndex=1;
		oinput.value=0;
		window.event.keyCode=0;
	}else if(k==42){//判断是不是"*"即违纪
		window.document.getElementById("ss1_"+num).selectedIndex=2;
		oinput.value=0;
		window.event.keyCode=0;
	}else if(k==45){//判断是不是"-"即作弊
		window.document.getElementById("ss1_"+num).selectedIndex=3;
		oinput.value=0;
		window.event.keyCode=0;
	}else if(k==43){//判断是不是"+"即正常
		window.document.getElementById("ss1_"+num).selectedIndex=0;
		window.event.keyCode=0;
	}else if(k==13){//点击回车
		var nextnum = 1 + parseInt(num);
		var next = window.document.getElementById("sc1_"+nextnum);
		if(next==null||next==undefined){
			window.document.getElementById("mysubmit").focus();
		}else{
			next.focus();
			next.select();
		}
		window.event.keyCode=0;
	}else{//其他字符报错
		alert("分数只能由数字和小数点构成");
		window.event.keyCode=0;
	}
}
/*
 * 与他人登分比较
 *
 * @author 靳希坤
 */
function checkab(num){
	var sc1 = document.getElementById('sc1_'+num).value;
	var sc2 = document.getElementById('sc2_'+num).value;
	var ss1 = document.getElementById('ss1_'+num).value;
	var ss2 = document.getElementById('ss2_'+num).value;			
	if(sc1!=null&&sc1.length>0){
		if(sc2!=null&&sc2.length>0){
			if(sc1!=sc2){
				alert("录入分数与另外的帐户录入的分数不一致！请查证");
			}else if(ss1!=ss2){
				alert("录入分数状态与另外的帐户录入的分数状态不一致！请查证");
			}
		}
	}
}

/*
 * 改变状态时分数随之改变
 *
 * @author 靳希坤
 */
function changestatus(oSelect,num){
	if(oSelect.selectedIndex != 0){
		document.getElementById("sc1_"+num).value="0";
	}
}
