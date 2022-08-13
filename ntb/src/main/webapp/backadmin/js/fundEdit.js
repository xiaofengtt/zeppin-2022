var uuid = (url('?uuid') != null) ? url('?uuid') : '';
$(function(){
	
	
	$.get('../rest/backadmin/fund/get?uuid='+uuid,function(r) {
		if(r.status =='SUCCESS') {
			if(r.data.status == 'published'){
				$(".edit").remove();
				$(".sureBtn").remove();
			}
			$('#titlename').html(r.data.name);
			$('#shortnames').html(r.data.shortname);
			$('#shortname').val(r.data.shortname);
			$('#names').html(r.data.name);
			$('#name').val(r.data.name);
			$('#scodess').val(r.data.scode);
			$('#scodes').html(r.data.scode);
			
			$('#seriess').html(r.data.series);
			$('#series').val(r.data.series);
			$('#shortnames').html(r.data.shortname);
			$('#shortname').val(r.data.shortname);
			$('#types').html(r.data.typeCN);
			$('#type').val(r.data.type);
			$('#setuptimes').html(r.data.setuptimeCN);
			$('#setuptime').val(r.data.setuptimeCN);
			$('#flagStructureds').html(r.data.flagStructuredCN);
			$('#flagStructured').val(r.data.flagStructured+'');//数据类型问题
			$('#gps').html(r.data.gp);
			$('#gp').val(r.data.gp);
			$('#custodians').html(r.data.custodianCN);
			$('#custodian').val(r.data.custodian);
			$('#structuredTypes').html(r.data.structuredTypeCN);
			$('#structuredType').val(r.data.structuredType);
			$('#structuredRemarks').html(r.data.structuredRemark);
			$('#structuredRemark').val(r.data.structuredRemark);
			$('#styles').html(r.data.styleCN);
			$('#style').val(r.data.style);
			$('#riskLevels').html(r.data.riskLevel);
			$('#riskLevel').val(r.data.riskLevel);
			$('#creditLevels').html(r.data.creditLevelCN);
			$('#creditLevel').val(r.data.creditLevel);
			$('#performanceLevels').html(r.data.performanceLevel);
			$('#performanceLevel').val(r.data.performanceLevel);
			$('#planingScales').html(r.data.planingScale);
			$('#planingScale').val(r.data.planingScale);
			$('#collectStarttimes').html(r.data.collectStarttimeCN);
			$('#collectStarttime').val(r.data.collectStarttimeCN);
			$('#collectEndtimes').html(r.data.collectEndtimeCN);
			$('#collectEndtime').val(r.data.collectEndtimeCN);
			$('#purchaseStarttimes').html(r.data.purchaseStarttimeCN);
			$('#purchaseStarttime').val(r.data.purchaseStarttimeCN);
			$('#purchaseEndtimes').html(r.data.purchaseEndtimeCN);
			$('#purchaseEndtime').val(r.data.purchaseEndtimeCN);
			$('#goals').html(r.data.goal);
			$('#goal').val(r.data.goal);
			$('#investIdeas').html(r.data.investIdea);
			$('#investIdea').val(r.data.investIdea);
			$('#investScopes').html(r.data.investScope);
			$('#investScope').val(r.data.investScope);
			$('#investStaregys').html(r.data.investStaregy);
			$('#investStaregy').val(r.data.investStaregy);
			$('#investStandards').html(r.data.investStandard);
			$('#investStandard').val(r.data.investStandard);
			$('#revenueFeatures').html(r.data.revenueFeature);
			$('#revenueFeature').val(r.data.revenueFeature);
			$('#riskManagements').html(r.data.riskManagement);
			$('#riskManagement').val(r.data.riskManagement);
			
			$('#statuss').html(r.data.statusName);
			$('#status').val(r.data.status);
			
		
			$('#currencyTypes').html(r.data.currencyTypeName);
			$('#currencyType').val(r.data.currencyType);
			$('#targetAnnualizedReturnRates').html(r.data.targetAnnualizedReturnRate);
			$('#targetAnnualizedReturnRate').val(r.data.targetAnnualizedReturnRate);
			$('#minAnnualizedReturnRates').html(r.data.minAnnualizedReturnRate);
			$('#minAnnualizedReturnRate').val(r.data.minAnnualizedReturnRate);
			$('#minInvestAmounts').html(r.data.minInvestAmount);
			$('#minInvestAmount').val(r.data.minInvestAmount);
			$('#minInvestAmountAdds').html(r.data.minInvestAmountAdd);
			$('#minInvestAmountAdd').val(r.data.minInvestAmountAdd);
			$('#maxInvestAmounts').html(r.data.maxInvestAmount);
			$('#maxInvestAmount').val(r.data.maxInvestAmount);
			$('#subscribeFees').html(r.data.subscribeFee);
			$('#subscribeFee').val(r.data.subscribeFee);
			$('#purchaseFees').html(r.data.purchaseFee);
			$('#purchaseFee').val(r.data.purchaseFee);
			$('#redemingFees').html(r.data.redemingFee);
			$('#redemingFee').val(r.data.redemingFee);
			$('#managementFees').html(r.data.managementFee);
			$('#managementFee').val(r.data.managementFee);
			$('#custodyFees').html(r.data.custodyFee);
			$('#custodyFee').val(r.data.custodyFee);
			$('#totalAmounts').html(r.data.totalAmount);
			$('#totalAmount').val(r.data.totalAmount);
			$('#terms').html(r.data.term);
			$('#term').val(r.data.term);
			$('#recordDates').html(r.data.recordDateCH);
			$('#recordDate').val(r.data.recordDate);
			$('#valueDates').html(r.data.valueDateCH);
			$('#valueDate').val(r.data.valueDate);
			$('#maturityDates').html(r.data.maturityDateCH);
			$('#maturityDate').val(r.data.maturityDate);
			$('#flagPurchases').html(r.data.flagPurchaseCH);
			$('#flagPurchase').val(r.data.flagPurchase);
			$('#flagRedemptions').html(r.data.flagRedemptionCH);
			$('#flagRedemption').val(r.data.flagRedemption);
			
			
			
			
			
			$('#flagCloseends').html(r.data.flagCloseendCH);
			$('#flagCloseend').val(r.data.flagCloseend);
			$('#guaranteeStatuss').html(r.data.guaranteeStatusName);
			$('#guaranteeStatus').val(r.data.guaranteeStatus);
			$('#areas').html(r.data.areaName);
			$('#area').val(r.data.area);
			$('#remarks').html(r.data.remark);
			$('#remark').val(r.data.remark);
			$('#netWorths').html(r.data.netWorth);
			$('#netWorth').val(r.data.netWorth);
			if(r.data.document == ''){
				$("#imageShow").attr('href','').html(r.data.documentName).show();
			}else{
				$('input[name="document"]').val(r.data.document);
				$("#imageShow").attr('href','..'+r.data.documentURL).html(r.data.documentName+'.'+r.data.documentType).show();
				$(".ajax-upload-dragdrop").css("display","none");
			}
		} else {
			layer.msg(r.message, {
				time: 2000 
			})
		}
	}).done(function(){
	})
	
	$(".datepicker").click(function(){
		laydate({
            start: laydate.now(0, "YYYY/MM/DD hh:mm"),
            istime: false,
            istoday: false,
            format: 'YYYY-MM-DD'
        });
	});
	
	$(".edit").click(function(){
		$(this).parent().find(".content-item-info").css("display","none");
		$(this).parent().find(".content-item-edit").css("display","block");
		$(this).css("display","none").siblings(".save").css("display","block");
		$(".inputGroup").each(function(){
			var width=$(this).width()-150;
			$(this).find("input").width(width/2+"px");
		});
	});
	$(".save").click(function(){
		$(this).parent().find(".content-item-info").css("display","block");
		$(this).parent().find(".content-item-edit").css("display","none");
		$(this).css("display","none").siblings(".edit").css("display","block");
	});
	$(".col-md-12 .content-items").each(function(){
		$(this).width($(".col-md-12 .form-group").width()-$(this).siblings("label").width());
	});
	
	$(".edits").click(function(){
		$(this).parent().find(".content-item span").css("display","none");
		$(this).parent().find("label").css("float","none");
		$(this).parent().find(".content-item input,.content-item select,.content-item textarea,.inputGroup span").css("display","inline-block");
		$(this).css("display","none").siblings(".save").css("display","block");
	});
	$(".saves").click(function(){
		$(this).parent().find(".content-item span").css("display","block");
		$(this).parent().find("label").css("float","left");
		$(this).parent().find(".content-item input,.content-item select,.content-item textarea,.inputGroup span").css("display","none");
		$(this).css("display","none").siblings(".edit").css("display","block");
	});
	$(".raiseEdit").click(function(){
		$(".buyRateEdit,.applyRateEdit").css("display","inline-table");
		$(".buyRate,.applyRate").css("display","none");
	});
	$(".raiseSave").click(function(){
		$(".buyRateEdit,.applyRateEdit").css("display","none");
		$(".buyRate,.applyRate").css("display","inline-table");
	});
});
function rateDel(obj){
	$(obj).parent().parent().remove();
}
function rateAdd(obj){
	$(obj).parent().parent().parent().append('<tr><td><input value="" />万元≤金额＜<input value="" />万元</td><td><input value="" />%</td>'
	+'<td><input value="" />%</td><td><a class="rateDel" onclick="rateDel(this)">删除</a><a class="rateAdd" onclick="rateAdd(this)">添加</a></td></tr>');
}

$('#allSave').submit(function() {
	var name=$("#name").val().replace(/(^\s*)|(\s*$)/g, "");
	var shortname=$("#shortname").val().replace(/(^\s*)|(\s*$)/g, "");
	var scode=$("#scode").val().replace(/(^\s*)|(\s*$)/g, "");
	var setuptime=$("#setuptime").val();
	var planingScale=$("#planingScale").val().replace(/(^\s*)|(\s*$)/g, "");
	var actualScale=$("#actualScale").val().replace(/(^\s*)|(\s*$)/g, "");
	var collectStarttime=$("#collectStarttime").val().replace(/(^\s*)|(\s*$)/g, "");
	var collectEndtime=$("#collectEndtime").val().replace(/(^\s*)|(\s*$)/g, "");
	var purchaseStarttime=$("#purchaseStarttime").val().replace(/(^\s*)|(\s*$)/g, "");
	var purchaseEndtime=$("#purchaseEndtime").val().replace(/(^\s*)|(\s*$)/g, "");
	
	if(name==""){
		layer.msg('基金全称不能为空', {
			time: 2000 
		});   
	}else if(shortname==""){
		layer.msg('基金简称不能为空', {
			time: 2000 
		});
	}else if(scode==""){
		layer.msg('基金编号不能为空', {
			time: 2000 
		});
	}else if(setuptime==""){
		layer.msg('请选择成立日期', {
			time: 2000 
		});
	}else if(planingScale==""){
		layer.msg('总募集规模不能为空', {
			time: 2000 
		});
	}else if(actualScale==""){
		layer.msg('最新规模不能为空', {
			time: 2000 
		});
	}else if(collectStarttime==""){
		layer.msg('请选择募集起始日', {
			time: 2000 
		});
	}else if(collectEndtime==""){
		layer.msg('请选择募集终止日', {
			time: 2000 
		});
	}else if(purchaseStarttime==""){
		layer.msg('请选择日常申购起始日', {
			time: 2000 
		});
	}else if(purchaseEndtime==""){
		layer.msg('请选择日常申购终止日', {
			time: 2000 
		});
	}else{
		var str = $(this).serialize();
		$.post('../rest/backadmin/fund/edit?step=1',str, function(data) {
			if (data.status == "SUCCESS") {
				layer.msg('添加成功', {
					time: 1000 
				}, function(){
					window.parent.getStatusList();
					window.parent.getList();
					parent.$.colorbox.close()
				}); 
			} else {
				layer.msg(data.message, {
					time: 2000 
				})
			}
		}) 
	}
	return false;
});


$(window).resize(function(){
	$(".col-md-12 .content-items").each(function(){
		$(this).width($(".col-md-12 .form-group").width()-$(this).siblings("label").width());
	});
});
//修改第一步
$("#formsubmit1").submit(function(){
	var str = $(this).serialize();
	$.post('../rest/backadmin/fund/edit?step=1&uuid='+uuid+'&planingScale='+Number($("#planingScale").val())+'.00',str, function(data) {
		if (data.status == "SUCCESS") {
			layer.msg('保存成功', {
				time: 1000 
			}, function(){
				
			}); 
		} else {
			layer.msg(data.message, {
				time: 2000 
			})
		}
	}) 
	return false;
});




