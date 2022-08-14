$(document).ready(function(){
	var windowWidth = $(window).width(),
		windowHeight = $(window).height(),
		targetWidth = 1186;
	//loading高度
	$(".loadingDiv").height(windowHeight-133+"px");
	//企业列表 显示隐藏
	$(".bottom").parent().click(function(event){
		if($(".company-list").css("display") == "none"){
			$(".company-list").css({"display":"block"});
		}else{
			$(".company-list").css({"display":"none"});
		}
		event.stopPropagation();
	});
	
	
	if(windowWidth < targetWidth){
		$(".main-left").css({"height":windowHeight + "px"});
	}
	$(window).resize(function(){
		windowWidth = $(window).width();
		if(windowWidth < targetWidth){
			$(".main-left").css({"height":windowHeight + "px"});
		}
		$(".loadingDiv").height($(window).height()-133+"px");
	});
	
	$(document).click(function(){
		if(windowWidth < targetWidth){
			$(".main-left").css({"left":"-230px"});
			$("body").css({"overflow":"auto"});
		}
		$(".company-list").css({"display":"none"});
	});
	$(".main-left,.company-list").click(function(event){
		event.stopPropagation();
	});
	
	$("#click").click(function(event){
		$(".main-left").css({"left":"0px"});
		$("body").css({"overflow":"hidden"});
		event.stopPropagation();
	});
})
//校验非空
function checkNonempty(str){
	if(str.replace(/(^\s*)|(\s*$)/g,"")==""){
		return false;
	}else{
		return true;
	}
}
//校验固话
function checkLandline(str){
	var isPhone = /^[-0-9]+$/; 
	if(str.replace(/(^\s*)|(\s*$)/g,"")!=""){
		if(isPhone.test(str.replace(/(^\s*)|(\s*$)/g,""))){
			return true;
		}else{
			return false;
		}
	}else{
		return false;
	}	
}
//校验手机号
function checkPhone(str){
	var isPhone = /^1(3|4|5|6|7|8|9)\d{9}$/;//手机号正则式	
	if(isPhone.test(str.replace(/(^\s*)|(\s*$)/g,""))){
		return true;
	}else{
		return false;
	}
}
//校验数字
function checkNumber(str){
	var isNumber = /^[0-9]*$/;
	if(isNumber.test(str.replace(/(^\s*)|(\s*$)/g,""))){
		return true;
	}else{
		return false;
	}
}
//验证邮箱
function checkEmail(str){
	var isEmail = /^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$/g;
	if(isEmail.test(str.replace(/(^\s*)|(\s*$)/g,""))){
		return true;
	}else{
		return false;
	}
}
//验证身份证号
function checkIDnumber(str){
	var isIDnumber = /^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/;
	if(isIDnumber.test(str.replace(/(^\s*)|(\s*$)/g,""))){
		return true;
	}else{
		return false;
	}
}
//校验银行卡号
function checkBankNum(str){
	var isBankNum = /^\d{12,21}$/;
	if(isBankNum.test(str.replace(/(^\s*)|(\s*$)/g,""))){
		return true;
	}else{
		return false;
	}
	
}

//校验注册密码
function checkRegistPassword(str){
	var isPassword = /((?=.*[a-z])(?=.*\d)|(?=[a-z])(?=.*[#@!~%^&*])|(?=.*\d)(?=.*[#@!~%^&*]))[a-z\d#@!~%^&*]{8,20}/i
	if(isPassword.test(str.replace(/(^\s*)|(\s*$)/g,""))){
		return true;
	}else{
		return false;
	}
	
}

//校验金钱
function checkMoney(str){
	var reg = /(^[1-9]([0-9]+)?(\.[0-9]{0})?$)/;
	if(reg.test(str.replace(/(^\s*)|(\s*$)/g,""))){
		return true;
	}else{
		return false;
	}
}
//纳税人识别号
function checkbillingNumber(str){
	var reg = /^[A-Za-z0-9]+$/;
	if(reg.test(str.replace(/(^\s*)|(\s*$)/g,""))){
		return true;
	}else{
		return false;
	}
}
function getTimetimeStamp(timeStamp) {
    var time = "";
    var now = new Date(timeStamp); //创建Date对象
    var year = now.getFullYear(); //获取年份
    var month = now.getMonth(); //获取月份
    var date = now.getDate(); //获取日期
    var hour = now.getHours(); //获取小时
    var minu = now.getMinutes(); //获取分钟
    var sec = now.getSeconds(); //获取秒钟
    month = month + 1;
    if (hour < 10) {
        hour = "0" + hour;
    }
    if (minu < 10) {
        minu = "0" + minu;
    }
    if (sec < 10) {
        sec = "0" + sec;
    }
    time = year + "-" + month + "-" + date + " " + hour + ":" + minu + ":" + sec;
    return time;
}
