var clearFlag = false;//清除按钮标识
var calculateFlag = false;//计算按钮标识
var isCustom = false;//是否自定义社保基数
var sqWages; //社保基数
var ylWages;//养老、失业社保基数
var gjjWages; //公积金
$(function(){
	$(".layerDiv").height(windowHeight);
	getHkArea("", "","1","true");
	getHkxz();
});
//监听input变化 
$('input').bind('input propertychange', function() {
	changeInput();
});
function changeInput(){  
	var hkszd = $("input.hkszd").val().replace(/(^\s*)|(\s*$)/g,"");
	var hkxz = $("input.hkxz").val().replace(/(^\s*)|(\s*$)/g,"");
	var sbjs = $("input.sbjs").val().replace(/(^\s*)|(\s*$)/g,"");
	var gjjjs = $("input.gjjjs").val().replace(/(^\s*)|(\s*$)/g,"");
	var gjj = $(".info-gjj .radioLabel.light").attr("data-value");
	if(hkszd!=""||hkxz!=""||sbjs!=""||gjjjs!=""||gjj!="2"){
		$(".clearBtn").addClass("light");
		clearFlag = true;
	}else{
		$(".clearBtn").removeClass("light");
		clearFlag = false;
	}
	if(hkszd!=""&&hkxz!=""&&sbjs!=""){
		if(gjj=="2"){
			$(".calculationBtn").addClass("light");
			calculateFlag = true;
		}else if(gjj=="1"&&gjjjs!=""){
			$(".calculationBtn").addClass("light");
			calculateFlag = true;
		}else if(gjjjs==""&&gjj!="2"){
			$(".calculationBtn").removeClass("light");
			calculateFlag = false;
		}
		
	}else{
		$(".calculationBtn").removeClass("light");
		calculateFlag = false;
	}
	
}

//点击清除按钮
$(".clearBtn").click(function(){
	if(clearFlag){
		$(".info-item input").val("");
		$(".jfcs").val("北京");
		clearFlag = false;
		$(this).removeClass("light");
		$(".jsButton").removeClass("light");
		isCustom = false;
		calculateFlag = false;
		$(".calculationBtn").removeClass("light");
		$(".sbjs").removeAttr("disabled");
		$(".gjjjs").removeAttr("disabled");
		$(".info-gjj").find(".radioLabel:eq(1)").click();
	}
});
//添加正则校验
var reg = /^[1-9][0-9]*(\.\d{1,2})?$/;
var re = new RegExp(reg);
//点击计算按钮
$(".calculationBtn").click(function(){	
	var hkszd = $("input.hkszd").val().replace(/(^\s*)|(\s*$)/g,"");
	var hkxz = $("input.hkxz").val().replace(/(^\s*)|(\s*$)/g,"");
	var sbjs = $("input.sbjs").val().replace(/(^\s*)|(\s*$)/g,"");
	var gjjjs = $("input.gjjjs").val().replace(/(^\s*)|(\s*$)/g,"");
	var gjj = $(".info-gjj .radioLabel.light").attr("data-value");
	if(calculateFlag){
		socialSecurityResult(sbjs,gjjjs,$("#hkxz").val(),gjj);
		_czc.push(["_trackEvent", "算社保", "计算"]);
	}else{
		if(hkszd==""){
			layerIn("请选择户口所在地");
			return false;
		} else if(hkxz==""){
			layerIn("请选择户口性质");
			return false;
		} else if(sbjs==""){
			layerIn("请输入社保基数");
			return false;
		} else if(gjjjs==""&&gjj=="1"){
			layerIn("请输入公积金基数");
			return false;
		} 
	}
});


//
//$("#js_yc2").blur(function(){
//	var value = $("#js_yc2").val().replace(/(^\s*)|(\s*$)/g,"");
//	if(value!=""){
//		if(value<3387){
//			value = 3387+".00";
//		}else if(value>23118){
//			value = 23118+".00";
//		}else{
//			value = value;
//		}
//		$("#js_yc2").val(value);
//	}
//	
//});
//$("#js_yc").blur(function(){
//	var value = $("#js_yc").val().replace(/(^\s*)|(\s*$)/g,"");
//	if(value!=""){
//		if(value<2273){
//			value = 2273+".00";
//		}else if(value>23118){
//			value = 23118+".00";
//		}else{
//			value = value;
//		}
//		$("#js_yc").val(value);
//	}
//	
//});
//社保基数按钮选择
function sbjs(obj){
	if($(obj).hasClass("light")){
		$(obj).removeClass("light");
		$("#js_yc2").removeAttr("disabled").val("");
		isCustom = false;
	}else{
		$(obj).addClass("light");
		$("#js_yc2").val("3387.00");
        $("#js_yc2").attr("disabled","disabled");
        isCustom = true;
	}
	changeInput();
}
//公积金基数按钮选择
function sbjn(obj){
	if($(obj).hasClass("light")){
		$(obj).removeClass("light");
		$("#js_yc").removeAttr("disabled").val("");
	}else{
		$(obj).addClass("light");
		$("#js_yc").val("2273.00");
        $("#js_yc").attr("disabled","disabled");
	}
	changeInput();
}
//公积金
$(".info-gjj label").click(function(){
	$(this).addClass("light").siblings().removeClass("light");
	var value = $(this).attr("data-value");
	if(value=="1"){
		$(".info-gjjjs").show();
	}else{
		$(".info-gjjjs").hide();
	}
	changeInput();
	return false;
});
