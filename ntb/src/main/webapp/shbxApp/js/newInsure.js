var oneFlag = false;
var userid;
var first = url("?firatFlag") != null ? url("?firatFlag") : "";//订单id
$(function(){
	if(first!=""){
		getUserInfo();
	}
	getJfyeData();
	getCjgzsj();
	getCbrsf();
	getXl();
	getMz();
	jsFwfYf();
});

//第一步
//监听input变化 
$('.firstInfo input').bind('input propertychange', function() {
	changeInput();
});
function changeInput(){
	var cbrxm = $("input.cbrxm").val().replace(/(^\s*)|(\s*$)/g,"");
	var sjh = $("input.sjh").val().replace(/(^\s*)|(\s*$)/g,"");
	var sfzh = $("input.sfzh").val().replace(/(^\s*)|(\s*$)/g,"");
	var hkszd = $("input.hkszd").val().replace(/(^\s*)|(\s*$)/g,"");
	var hkxz = $("input.hkxz").val().replace(/(^\s*)|(\s*$)/g,"");
	if(cbrxm!=""&&sjh!=""&&sfzh!=""&&hkszd!=""&&hkxz!=""){
		$(".stepBtnOne").addClass("light");
		oneFlag = true;
	}else{
		$(".stepBtnOne").removeClass("light");
		oneFlag = false;
	}
}
$(".stepBtnOne").click(function(){
	var cbrxm = $("input.cbrxm").val().replace(/(^\s*)|(\s*$)/g,"");
	var sjh = $("input.sjh").val().replace(/(^\s*)|(\s*$)/g,"");
	var sfzh = $("input.sfzh").val().replace(/(^\s*)|(\s*$)/g,"");
	var hkszd = $("input.hkszd").val().replace(/(^\s*)|(\s*$)/g,"");
	var hkxz = $("input.hkxz").val().replace(/(^\s*)|(\s*$)/g,"");
	var email = $("input.yx").val().replace(/(^\s*)|(\s*$)/g,"");
	var education = $("input.xl").val().replace(/(^\s*)|(\s*$)/g,"");
	var worktime = $("input.cjgzsj").val().replace(/(^\s*)|(\s*$)/g,"");
	var nationality = $("input.mz").val().replace(/(^\s*)|(\s*$)/g,"");
	var duty = $("input.grsf").val().replace(/(^\s*)|(\s*$)/g,"");
	if(oneFlag){
		if(!checkPhone(sjh)){
			layerIn("请填写正确的手机号");
			return false;
		}
		if(!isIDnumber.test(sfzh)){
			layerIn("请填写正确的身份证号");
			return false;
		}
		if(email!=""&&!checkEmail(email)){
			layerIn("邮箱格式不正确");
			return false;
		}
		$.ajax({
			type: "get",
			url: "../rest/shbxWeb/insured/add",
			async: true,
			data: {
				'name':cbrxm,
				'idcard':sfzh,
				'mobile':sjh,
				'householdtype':hkxz,
				'householdarea':$("#cbdq").val(),
				'email':email,
				'education':education,
				'worktime':$("#cjgzsj").val(),
				'nationality':nationality,
				'duty':duty
				
			},
			beforeSend:function(){
				loadingIn();
			}
		})
		.done(function(r) {
			loadingOut();
			if(r.status == "SUCCESS") {
				$(".firstInfo").hide();
				$(".secondInfo").show();
				$(".stepOne").addClass("ok");
				$(".spanOne").addClass("ok");
				$(".stepTwo").addClass("light");
				$(".spanTwo").addClass("color-green");
				userid=r.data;
				_czc.push(["_trackEvent", "缴社保", "第一步"]);
			}else {
				if(r.status == "ERROR"&&r.errorCode=="302"){
					location.replace("login.html");
				}else{
					if(r.message=="参保人已存在！"){
						$(".firstInfo").hide();
						$(".secondInfo").show();
						$(".stepOne").addClass("ok");
						$(".spanOne").addClass("ok");
						$(".stepTwo").addClass("light");
						$(".spanTwo").addClass("color-green");
						userid=r.data;
						_czc.push(["_trackEvent", "缴社保", "第一步"]);
					}else{
						layerIn(r.message);	
					}			
				}						
			}
		})
		.fail(function() {
			loadingOut();
			layerIn("服务器错误");
		});
		
	}else{
		
		if(cbrxm==''){
			layer.msg("请填写参保人姓名");
			return false;
		}else if(sjh == ''){
			layer.msg("请填写参保人手机号");
			return false;
		}else if(sfzh == ''){
			layer.msg("请填写参保人身份证号");
			return false;
		}else if(hkszd == ''){
			layer.msg("请选择户口所在地");
			return false;
		}else if(hkxz == ''){
			layer.msg("请选择户口性质");
			return false;
		}
	}
});

//第二步
var isCustom = false;//是否自定义社保基数
var sqWages; //社保基数
var ylWages;//养老、失业社保基数
var gjjWages; //公积金
var stepOneFlag =  false;//缴费月份flag
var stepTwoFlag =  false;//社保基数和公积金基数flag
var stepThreeFlag =  false;//服务费flag

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
function changeInputYf(){
	var jfyf = $("input.jfyf").val().replace(/(^\s*)|(\s*$)/g,"");
	if(jfyf !=""){
		stepOneFlag = true;
	}else{
		stepOneFlag = false;
	}
	surePay();
}
//监听input变化 
$('.secondInfo input').bind('input propertychange', function() {
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
			$(".stepTwoBox").show();
			$(".wrap").hide();
			$(".totalAmount").hide();			
		});
		changeTotal();
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
			$(".stepTwoBox").show();
			$(".wrap").hide();
			$(".totalAmount").hide();			
		});
		changeTotal();
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
$(".stepTwoBox .bigSure").click(function(){
	$(".stepTwoBox").hide();
	$(".wrap").show();
	$(".totalAmount.secondInfo").show();
});
//合计金额
function changeTotal(){
	var jfje = $("input.jfje").val().replace(/(^\s*)|(\s*$)/g,"");
	var fwf = $("input.fwf").val().replace(/(^\s*)|(\s*$)/g,"");
	if(jfje!=""&&fwf!=""){
		$(".amoutLeft span").html((Number($("input.jfje").val())*Number($(".fwf").attr("datalength"))+Number( $("input.fwf").attr("dataValue"))).toFixed(2));
	}
}
//提交订单是否可点
function surePay(){
	if(stepOneFlag&&stepTwoFlag&&stepThreeFlag){
		$(".secondInfo .surebtn").addClass("light");
		
	}else{
		$(".secondInfo .surebtn").removeClass("light");
	}
}
$(".secondInfo .surebtn").click(function(){
	if($(this).hasClass("light")){
		$(".firstInfo").hide();
		$(".secondInfo").hide();
		$(".threeInfo").show();
		$(".stepTwo,.spanTwo").addClass("ok");
		$(".stepThree").addClass("light");
		$(".spanThree").addClass("color-green");
		//确认信息
		$(".sureName").html($(".cbrxm").val().replace(/(^\s*)|(\s*$)/g,"")!=""?$(".cbrxm").val().replace(/(^\s*)|(\s*$)/g,""):"未填写");
		$(".sureTel").html($(".sjh").val().replace(/(^\s*)|(\s*$)/g,"")!=""?$(".sjh").val().replace(/(^\s*)|(\s*$)/g,""):"未填写");
		$(".sureIdnumber").html($(".sfzh").val().replace(/(^\s*)|(\s*$)/g,"")!=""?$(".sfzh").val().replace(/(^\s*)|(\s*$)/g,""):"未填写");
		$(".sureHkszd").html($(".hkszd").val().replace(/(^\s*)|(\s*$)/g,"")!=""?$(".hkszd").val().replace(/(^\s*)|(\s*$)/g,""):"未填写");
		$(".sureHkxz").html($(".hkxz").val().replace(/(^\s*)|(\s*$)/g,"")!=""?$(".hkxz").val().replace(/(^\s*)|(\s*$)/g,""):"未填写");
		$(".sureNation").html($(".mz").val().replace(/(^\s*)|(\s*$)/g,"")!=""?$(".mz").val().replace(/(^\s*)|(\s*$)/g,""):"未填写");
		$(".sureEducation").html($(".xl").val().replace(/(^\s*)|(\s*$)/g,"")!=""?$(".xl").val().replace(/(^\s*)|(\s*$)/g,""):"未填写");
		$(".sureWorkTime").html($(".cjgzsj").val().replace(/(^\s*)|(\s*$)/g,"")!=""?$(".cjgzsj").val().replace(/(^\s*)|(\s*$)/g,""):"未填写");
		$(".sureEmail").html($(".yx").val().replace(/(^\s*)|(\s*$)/g,"")!=""?$(".yx").val().replace(/(^\s*)|(\s*$)/g,""):"未填写");
		$(".sureCompany").html($(".gsmc").val().replace(/(^\s*)|(\s*$)/g,"")!=""?$(".gsmc").val().replace(/(^\s*)|(\s*$)/g,""):"未填写");
		$(".sureCbdq").html($(".cbdq").val().replace(/(^\s*)|(\s*$)/g,"")!=""?$(".cbdq").val().replace(/(^\s*)|(\s*$)/g,""):"未填写");
		if($(".radioLabel.light").attr("data-value")=="1"){
			$(".gsmcLi").hide();
		}
		$(".sureCblx").html($(".radioLabel.light").attr("data-cn")!=""?$(".radioLabel.light").attr("data-cn"):"未填写");
		$(".sureJfyf").html($(".jfyf").val().replace(/(^\s*)|(\s*$)/g,"")!=""?$(".jfyf").val().replace(/(^\s*)|(\s*$)/g,""):"未填写");
		$(".sureSbjs").html($(".sbjs").val().replace(/(^\s*)|(\s*$)/g,"")!=""?$(".sbjs").val().replace(/(^\s*)|(\s*$)/g,""):"未填写");
		if($(".info-gjj .radioLabel.light").attr("data-value")=="2"){
			$(".gjjjsLi").hide();
		}
		$(".sureGjjjs").html($(".gjjjs").val().replace(/(^\s*)|(\s*$)/g,"")!=""?$(".gjjjs").val().replace(/(^\s*)|(\s*$)/g,""):"未填写");
		$(".sureJnje").html($(".jfje").val().replace(/(^\s*)|(\s*$)/g,"")!=""?$(".jfje").val().replace(/(^\s*)|(\s*$)/g,""):"未填写");
		$(".sureFwf").html($(".fwf").val().replace(/(^\s*)|(\s*$)/g,"")!=""?$(".fwf").val().replace(/(^\s*)|(\s*$)/g,""):"未填写");
		
		//把值存到cookie里
		localStorage.setItem("starttime",$("#jfyf").val());//缴费月份
		localStorage.setItem("insuredType",$(".info-cblx .radioLabel.light").attr("data"));//参保类型
		localStorage.setItem("sourceCompany",$(".gsmc").val().replace(/(^\s*)|(\s*$)/g,""));//原公司名称
		localStorage.setItem("duration",$(".fwf").attr("datalength"));//参保时长
		localStorage.setItem("housingFund",$("input.gjjjs").val().replace(/(^\s*)|(\s*$)/g,""));//公积金基数
		localStorage.setItem("cardinalNumber",$("input.sbjs").val().replace(/(^\s*)|(\s*$)/g,""));//社保基数	
		localStorage.setItem("benefits",$(".jfje").val());//代缴金额
		localStorage.setItem("serviceFee",$("input.fwf").attr("dataValue"));//服务费
		localStorage.setItem("gjj",$(".info-gjj .radioLabel.light").attr("data-value"));//是否缴纳公积金
		localStorage.setItem("price",(Number($("input.jfje").val())*Number($(".fwf").attr("datalength"))+Number($("input.fwf").attr("dataValue"))).toFixed(2));//合计金额
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
//返回第一步
$(".stepOne,.spanOne").click(function(){
	if($(this).hasClass("ok")){
		$(".firstInfo").show();
		$(".secondInfo").hide();
		$(".threeInfo").hide();
		$(".stepTwo,.spanTwo").removeClass("light");
		$(".stepThree,.spanThree").removeClass("light");
		$(".stepOne,.stepTwo").removeClass("ok");
		$(".spanTwo,.spanThree").removeClass("color-green");
	}	
});
//返回第二步
$(".stepTwo,.spanTwo").click(function(){
	if($(this).hasClass("ok")){
		$(".firstInfo").hide();
		$(".secondInfo").show();
		$(".threeInfo").hide();
		$(".stepThree,.spanThree").removeClass("light");
		$(".stepTwo,.spanTwo").removeClass("ok").addClass("light");
		$(".spanTwo").addClass("color-green");
		$(".spanThree").removeClass("color-green");
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
$(".threeInfo .surebtn").click(function(){
	location.replace("paySuccess.html?userid="+userid);
});

//第一次填写前三项 获取用户信息
function getUserInfo(){
	$.ajax({
		type: "get",
		url: "../rest/shbxWeb/user/get",
		async: true,
		data: {
			
		},
		beforeSend:function(){
			loadingIn();
		}
	})
	.done(function(r) {
		loadingOut();
		if(r.status == "SUCCESS") {
			$(".cbrxm").val(r.data.realnameFull);
			$(".sjh").val(r.data.phoneFull);
			$(".sfzh").val(r.data.idcardFull);
				
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
		
	});
}
function getJfyeData(){
	var Data = new Date();
	var Year = Data.getFullYear(); //获取完整的年份
	var Day = new Date().getDate();
	var Month = Data.getMonth(); //月份
	if(Day>15){
		Month = new Date().getMonth()+1;
	}
	for(var i=0;i<10;i++){
		var dataList ={};
    	dataList.text = Year+i;
    	dataList.value = Year+i;   	
    	data14.push(dataList); 
	}
	for(var i=Month;i<12;i++){
		var dataList ={};
    	dataList.text = ("0"+(i+1)).substring(("0"+(i+1)).length-2,("0"+(i+1)).length);
    	dataList.value = ("0"+(i+1)).substring(("0"+(i+1)).length-2,("0"+(i+1)).length);   	
    	data15.push(dataList); 
	}
	data16 = data15;
	var picker7 = new Picker({
		title: '请选择缴费月份',
	    data: [data14,data16]
	});

	picker7.on('picker.select', function (selectedVal, selectedIndex) {
	    $("#picker7").find("input").val(data14[selectedIndex[0]].text+'-'+data16[selectedIndex[1]].text);
	    $("#jfyf").val(data14[selectedIndex[0]].value+'-'+data16[selectedIndex[1]].value+'-01');
	    changeInputYf();
	    jsFwfYf();
	});

	picker7.on('picker.change', function (index, selectedIndex) {
		if(index=="0"){
			var Year =new Date().getFullYear();
			if(data14[selectedIndex].value==Year){
				data16=data15;				
			}else{
				data16=data2;				
			}
			picker7.refillColumn('1', data16);
		}	
	});

	picker7.on('picker.valuechange', function (selectedVal, selectedIndex) {
	    console.log(selectedVal);
	});
	 
	if(picker7El){
		picker7El.addEventListener('click', function () {
		    picker7.show();
		});
	}
}
//收起
$(".takeUp").click(function(){
	$(this).hide();
	$(".takeDown").show();
	$(".insuredUserBottom").hide();
});
//展开
$(".takeDown").click(function(){
	$(this).hide();
	$(".takeUp").show();
	$(".insuredUserBottom").show();
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
$(".sbjs").focus(function(){
	$(".totalAmount.secondInfo").hide();
});
$(".sbjs").blur(function(){
	$(".totalAmount.secondInfo").show();
});