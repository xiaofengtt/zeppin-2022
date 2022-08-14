var isCustom = false;//是否自定义社保基数
var sqWages; //社保基数
var ylWages;//养老、失业社保基数
var gjjWages; //公积金
var stepOneFlag =  false;//缴费月份flag
var stepTwoFlag =  false;//社保基数和公积金基数flag
var stepThreeFlag =  false;//服务费flag
var userid = url("?userid") != null ? url("?userid") : "";//用户id
var edit = url("?edit") != null ? url("?edit") : "";//是否从编辑页跳转过来

$(function(){
	getUserInfo();
	getJfyeData();
	jsFwfYf();
	if(edit!=""){
		//参保类型
		if(localStorage.getItem("insuredType")=="new"){
			$(".radioLabel.newType").addClass("light");
			$(".info-gsmc").hide();
		}else{
			$(".radioLabel.changeinType").addClass("light").siblings().removeClass("light");
			$(".info-gsmc").show();
		}
		//公司名称
		$(".gsmc").val(localStorage.getItem("sourceCompany"));
		//缴费开始月份
		$(".jfyf").val(localStorage.getItem("starttime").substring(0,7));
		$("#jfyf").val(localStorage.getItem("starttime"));
		//社保基数
		$(".sbjs").val(localStorage.getItem("cardinalNumber"));
		//缴纳公积金
		if(localStorage.getItem("gjj")=="1"){
			$(".radioLabel.jngjj").addClass("light").siblings().removeClass("light");
			$(".info-gjjjs").show();
		}else{
			$(".radioLabel.bjngjj").addClass("light").siblings().removeClass("light");
			$(".info-gjjjs").hide();
		}
		//公积金基数
		$(".gjjjs").val(localStorage.getItem("housingFund"));
		//缴纳金额
		$(".jfje").val(localStorage.getItem("benefits"));
		//服务费
		$(".fwf").val(localStorage.getItem("fwfValue"));
		if(localStorage.getItem("fwfValue")!==""){
			stepThreeFlag = true;
		}
		//合计金额
		$(".amoutLeft span").html(localStorage.getItem("price"));
		if(localStorage.getItem("shbbtn")=="true"){
			$(".shbbtn").addClass("light");
			$(".sbjs").attr("disabled","disabled");
		}
		if(localStorage.getItem("gjjbtn")=="true"){
			$(".gjjbtn").addClass("light");
			$(".gjjjs").attr("disabled","disabled");
		}
		if(localStorage.getItem("surebtn")=="true"){
			$(".surebtn").addClass("light");
		}
		changeInput();
		changeInputs();
	}else{
		localStorage.clear();
	}
	$(".wrap").show();
});
$(".editBtn").click(function(){
//	点击编辑时把页面信息存下
	localStorage.setItem("starttime",$("#jfyf").val());//缴费月份
	localStorage.setItem("insuredType",$(".info-cblx .radioLabel.light").attr("data"));//参保类型
	localStorage.setItem("sourceCompany",$(".gsmc").val().replace(/(^\s*)|(\s*$)/g,""));//原公司名称
	localStorage.setItem("duration",$(".fwf").attr("datalength")?$(".fwf").attr("datalength"):'');//参保时长
	localStorage.setItem("housingFund",$("input.gjjjs").val().replace(/(^\s*)|(\s*$)/g,""));//公积金基数
	localStorage.setItem("cardinalNumber",$("input.sbjs").val().replace(/(^\s*)|(\s*$)/g,""));//社保基数	
	localStorage.setItem("benefits",$(".jfje").val());//代缴金额
	localStorage.setItem("serviceFee",$("input.fwf").attr("dataValue")?$("input.fwf").attr("dataValue"):'');//服务费
	localStorage.setItem("gjj",$(".info-gjj .radioLabel.light").attr("data-value"));//是否缴纳公积金
	var price = (Number($("input.jfje").val())*Number($(".fwf").attr("datalength"))+Number($("input.fwf").attr("dataValue"))).toFixed(2)
	localStorage.setItem("price",$("input.fwf").attr("dataValue")?price:'');//合计金额
	localStorage.setItem("fwfValue",$("input.fwf").val());//服务费
	localStorage.setItem("shbbtn",$(".shbbtn").hasClass("light"));//社保按钮
	localStorage.setItem("gjjbtn",$(".gjjbtn").hasClass("light"));//公积金按钮
	localStorage.setItem("surebtn",$(".surebtn").hasClass("light"));//提交按钮
	
	location.replace("insuredInfoEdit.html?userid="+userid);
});
//收起
$(".takeUp").click(function(){
	$(this).hide();
	$(".takeDown").show();
	$(".insuredBottom").hide();
	$(".editBtn").hide();
});
//展开
$(".takeDown").click(function(){
	$(this).hide();
	$(".takeUp").show();
	$(".insuredBottom").show();
	$(".editBtn").show();
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
	changeInputs();
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
	changeInputs();
}
$("#picker6").click(function(){
	$(".layerDiv").show();
});
//layer 确定
$(".layerDiv .confirm-hook").click(function(){
	$(".layerDiv").hide();
	$(".fwf").val($(".layerInner li.light").attr("data"));
	$(".fwf").attr("dataValue",$(".layerInner li.light").attr("data-value"));
	$(".fwf").attr("datalength",$(".layerInner li.light").attr("data-length"));
	//changeTotal
	changeTotal();
	stepThreeFlag = true;
	surePay();
});
$(".layerInner li,.radioLabel").click(function(){
	$(this).addClass("light").siblings().removeClass("light");
});

//缴费月份
function changeInput(){
	var jfyf = $("input.jfyf").val().replace(/(^\s*)|(\s*$)/g,"");
	if(jfyf !=""){
		stepOneFlag = true;
	}else{
		stepOneFlag = false;
	}
	surePay();
}
//监听input变化 
$('input').bind('input propertychange', function() {
	changeInputs();
});
//社保基数和公积金基数
function changeInputs(){
	var sbjs = $("input.sbjs").val().replace(/(^\s*)|(\s*$)/g,"");
	var gjjjs = $("input.gjjjs").val().replace(/(^\s*)|(\s*$)/g,"");
	var gjj = $(".info-gjj .radioLabel.light").attr("data-value");
	var gsmc = $("input.gsmc").val().replace(/(^\s*)|(\s*$)/g,"");
	var jflx = $(".info-cblx .radioLabel.light").attr("data-value");
	if(sbjs!=""&&gjjjs!=""&&gjj=="1"){
		//计算缴费明细
		calculateDetail();
		$(".fymx").show();
		$(".info-jfje").click(function(){
			$(".stepTwo").show();
			$(".fixedBtn").show();
			$(".stepOne").hide();
			$(".totalAmount").hide();			
		});
		if(edit==""){
			changeTotal();
		}
		if((gsmc!=""&&jflx=="2")||jflx=="1"){
			stepTwoFlag = true;
		}else{
			stepTwoFlag = false;
		}
	}else if(sbjs!=""&&gjj=="2"){
		//计算缴费明细
		calculateDetail();
		$(".fymx").show();
		$(".info-jfje").click(function(){
			$(".stepTwo").show();
			$(".fixedBtn").show();
			$(".stepOne").hide();
			$(".totalAmount").hide();			
		});
		if(edit==""){
			changeTotal();
		}
		if((gsmc!=""&&jflx=="2")||jflx=="1"){
			stepTwoFlag = true;
		}else{
			stepTwoFlag = false;
		}
	}else{
		$(".amoutLeft span").html("0.00");
		$(".jfje").val("0.00");
		$(".fymx").hide();
		$(".info-jfje").unbind();
		stepTwoFlag = false;
	}
	surePay();
}
//添加正则校验
var reg = /^[1-9][0-9]*(\.\d{1,2})?$/;
var re = new RegExp(reg);
//计算明细
function calculateDetail(){
	var sbjs = $("input.sbjs").val().replace(/(^\s*)|(\s*$)/g,"");
	var gjjjs = $("input.gjjjs").val().replace(/(^\s*)|(\s*$)/g,"");
	var gjj = $(".info-gjj .radioLabel.light").attr("data-value");
    socialSecurityResult(sbjs,gjjjs,$("#hkxz").val(),gjj);
}

//明细确定按钮
$(".fixedBtn").click(function(){
	$(".stepTwo").hide();
	$(".fixedBtn").hide();
	$(".stepOne").show();
	$(".totalAmount").show();
});
//合计金额
function changeTotal(){
	var jfje = $("input.jfje").val().replace(/(^\s*)|(\s*$)/g,"");
	var fwf = $("input.fwf").val().replace(/(^\s*)|(\s*$)/g,"");
	if(jfje!=""&&fwf!=""){
		$(".amoutLeft span").html((Number($("input.jfje").val())*Number($(".fwf").attr("datalength"))+Number($("input.fwf").attr("dataValue"))).toFixed(2));
		$(".orderamount").html(Number($("input.jfje").val())*Number($(".fwf").attr("datalength"))+Number( $("input.fwf").attr("dataValue"))+"元");
	}
}
//确认支付是否可点
function surePay(){
	if(stepOneFlag&&stepTwoFlag&&stepThreeFlag){
		$(".totalAmount .surebtn").addClass("light");
	}else{
		$(".totalAmount .surebtn").removeClass("light");
	}
}
//获取用户信息
function getUserInfo(){
	$.ajax({
		type: "get",
		url: "../rest/shbxWeb/insured/get",
		async: true,
		data: {
			'uuid':userid			
		},
		beforeSend:function(){
			loadingIn();
		}
	})
	.done(function(r) {
		loadingOut();
		if(r.status == "SUCCESS") {
			//参保人信息
			$(".insuredName").html(r.data.name!=""?r.data.name:"未填写");//参保人姓名
			$(".insureTel").html(r.data.mobile!=""?r.data.mobile:"未填写");//参保人手机号
			
			$(".insuredIdnumber").html(r.data.idcard!=""?r.data.idcard:"未填写");//参保人身份证号
			$(".insuredHkszd").html(r.data.householdareaName!=""?r.data.householdareaName:"未填写");//参保人户口所在地
			$(".insuredHkxz").html(r.data.householdtype!=""?r.data.householdtype:"未填写");//参保人户口性质
			$(".insuredNation").html(r.data.nationality!=""?r.data.nationality:"未填写");//参保人民族
			$(".insuredEducation").html(r.data.education!=""?r.data.education:"未填写");//参保人学历
			$(".insuredWorkTime").html(r.data.worktimeCN!=""&&r.data.worktimeCN?r.data.worktimeCN.substring(0,7):"未填写");//参保人参加工作时间
			$(".insuredEmail").html(r.data.email!=""?r.data.email:"未填写");//参保人邮箱
			$(".insuredDuty").html(r.data.duty!=""?r.data.duty:"未填写");//参保人身份
		}else {
			if(r.status == "ERROR"&&r.errorCode=="302"){
				location.replace("login.html");
			}else{
				layerIn(r.message);				
			}						
		}
	})
	.fail(function() {
		loadingOut();
		layerIn("服务器错误");
	});
}

//公积金
$(".info-gjj label").click(function(){
	var value = $(this).attr("data-value");
	if(value=="1"){
		$(".info-gjjjs").show();
	}else{
		$(".info-gjjjs").hide();
		$(".sureGjjjs").html("0.00");
	}
	changeInputs();
	return false;
});

//确认支付
$(".surebtn").click(function(){
	if($(this).hasClass("light")){
		//把值存到cookie里
		localStorage.setItem("starttime",$("#jfyf").val());//缴费月份
		localStorage.setItem("insuredType",$(".info-cblx .radioLabel.light").attr("data"));//参保类型
		localStorage.setItem("sourceCompany",$(".gsmc").val().replace(/(^\s*)|(\s*$)/g,""));//原公司名称
		localStorage.setItem("duration",localStorage.getItem("duration")?localStorage.getItem("duration"):$(".fwf").attr("datalength"));//参保时长
		localStorage.setItem("housingFund",$("input.gjjjs").val().replace(/(^\s*)|(\s*$)/g,""));//公积金基数
		localStorage.setItem("cardinalNumber",$("input.sbjs").val().replace(/(^\s*)|(\s*$)/g,""));//社保基数	
		localStorage.setItem("benefits",$(".jfje").val());//代缴金额
		localStorage.setItem("serviceFee",localStorage.getItem("serviceFee")?localStorage.getItem("serviceFee"):$("input.fwf").attr("dataValue"));//服务费
		localStorage.setItem("gjj",$(".info-gjj .radioLabel.light").attr("data-value"));//是否缴纳公积金
		var price = (Number($("input.jfje").val())*Number($(".fwf").attr("datalength"))+Number($("input.fwf").attr("dataValue"))).toFixed(2)
		localStorage.setItem("price",localStorage.getItem("price")?localStorage.getItem("price"):price);//合计金额
		location.replace("paySuccess.html?userid="+userid);
		
	}else{
		var cblx = $(".info-cblx .radioLabel.light").attr("data-value");
		var gsmc = $("input.gsmc").val().replace(/(^\s*)|(\s*$)/g,"");
		var jfyf = $("input.jfyf").val().replace(/(^\s*)|(\s*$)/g,"");
		var sbjs = $("input.sbjs").val().replace(/(^\s*)|(\s*$)/g,"");
		var gjjjs = $("input.gjjjs").val().replace(/(^\s*)|(\s*$)/g,"");
		var gjj = $(".info-gjj .radioLabel.light").attr("data-value");
		
		if(gsmc==""&&cblx=="2"){
			layerIn("请填写公司名称");
			return false;
		}else if(jfyf==""){
			layerIn("请选择缴费开始月份");
			return false;
		}else if(sbjs==""){
			layerIn("请填写社保基数");
			return false;
		}else if(gjjjs==""&&gjj=="1"){
			layerIn("请填写公积金基数");
			return false;
		}else{
			layerIn("请选择服务费");
			return false;
		}
	}
});
//参保类型
$(".info-cblx label").click(function(){
	var value = $(this).attr("data-value");
	if(value=="1"){
		$(".info-gsmc").hide();
		$(".gsmcLi").hide();
	}else{
		$(".info-gsmc").show();
		$(".gsmcLi").show();
	}
	changeInputs();
});
$(".layerInner .cancel-hook").click(function(){
	if($(".info-fwf .fwf").attr("datavalue")){
		for(var i=0;i<$(".layerInner ul li").length;i++){
			if($(".info-fwf .fwf").attr("datavalue")==$(".layerInner ul li:eq("+i+")").attr("data-value")){
				$(".layerInner ul li:eq("+i+")").addClass("light").siblings().removeClass("light");
				return;
			}
		}
	}
});