var billid = url("?billid") != null ? url("?billid") : "";//订单id
$(function(){
	getOrderDetails();
});
//收起
$(".takeUp").click(function(){
	$(this).hide();
	$(".takeDown").show();
	$(".insuredBottom").hide();
});
//展开
$(".takeDown").click(function(){
	$(this).hide();
	$(".takeUp").show();
	$(".insuredBottom").show();
});
//订单详情
function getOrderDetails(){
	$.ajax({
		type: "get",
		url: "../rest/shbxWeb/user/getBillInfo",
		async: true,
		data: {
			'billid':billid
		},
		beforeSend:function(){
			loadingIn();
		}
	})
	.done(function(r) {
		loadingOut();
		if(r.status == "SUCCESS") {
			$(".insuredInfo").addClass(r.data.status);
			if(r.data.status=="normal"){
				$(".status").html("在缴");
			}else{
				$(".status").html(r.data.statusCN);
			}
			//参保人信息
			$(".insuredTop p span").html(r.data.orderNumber);//订单号
			$(".insuredName").html(r.data.shbxInsuredName);//参保人姓名
			$(".insureTel").html(r.data.shbxInsuredMobile);//参保人手机号
			
			$(".insuredIdnumber").html(r.data.shbxInsuredIdcard!=""?r.data.shbxInsuredIdcard:"未填写");//参保人身份证号
			$(".insuredHkszd").html(r.data.shbxInsuredHouseholdareaCN!=""?r.data.shbxInsuredHouseholdareaCN:"未填写");//参保人户口所在地
			$(".insuredHkxz").html(r.data.shbxInsuredHouseholdtype!=""?r.data.shbxInsuredHouseholdtype:"未填写");//参保人户口性质
			$(".insuredNation").html(r.data.shbxInsuredNationality!=""?r.data.shbxInsuredNationality:"未填写");//参保人民族
			$(".insuredEducation").html(r.data.shbxInsuredEducation!=""?r.data.shbxInsuredEducation:"未填写");//参保人学历
			$(".insuredWorkTime").html(r.data.worktimeCN!=""?r.data.worktimeCN:"未填写");//参保人参加工作时间
			$(".insuredEmail").html(r.data.shbxInsuredEmail!=""?r.data.shbxInsuredEmail:"未填写");//参保人邮箱
			if(r.data.insuredType!="changein"){
				$(".info-gsmc").hide();
			}
			$(".insuredCompany").html(r.data.sourceCompany!=""?r.data.sourceCompany:"未填写");//参保人公司
			if(r.data.housingFundCN=="0.00"){
				$(".info-gjjjs").hide();
			}
			//订单信息
			$(".cblx").html(r.data.insuredTypeCN);//参保类型
			$(".jfyf").html(r.data.starttime);//参保月份
			$(".jnsc").html(r.data.duration+"个月("+r.data.durationCN+")");//缴纳时长
			$(".sbjs").html(r.data.cardinalNumberCN);//社保基数
			$(".gjjjs").html(r.data.housingFundCN);//公积金基数
			$(".jfje").html(r.data.totalAmountCN);//缴费金额
			$(".fwf").html("(含服务费"+r.data.serviceFeeCN+")");//服务费
			$(".xdsj").html(r.data.createtimeCN);//下单时间
			
			$(".socialSecurityResult").click(function(){
				window.location.href="payList.html?cardinalNumber="+r.data.cardinalNumber+"&housingFund="+r.data.housingFund;				
			});
			
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



