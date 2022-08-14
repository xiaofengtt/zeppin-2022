/**
 * 商户交易
 */
var company = url('?uuid');

layui.use(['form','element'], function(){
	var form = layui.form
	,layer = layui.layer
	,$ = layui.jquery
	,element = layui.element;
	
	getCompany();
	getBankcardList();
	
	form.on('select(companyBankcard)',function(data){
		$("select[name=companyBankcard]").val(data.value);
	})
	$("input[name=totalAmount]").blur(function(){
		var totalAmout = Number($("input[name=totalAmount]").val());
		$("input[name=totalAmount]").val(totalAmout.toFixed(2));
		countAmount();
	});
	
	form.on('select(type)',function(data){
		$("select[name=type]").val(data.value);
		if(data.value=="recharge"){
			$('#accountTitle').html("实际充值")
		}else{
			$('#accountTitle').html("实际打款")
		}
		$("input[name=totalAmount]").val('');
		$("input[name=amount]").val('');
		if($("select[name=type]").val() == 'withdraw'){
			$('input[name=poundage]').val(isNumber($('input[name=withdrawPoundageRate]').val()) ? '' : $('input[name=withdrawPoundageStatic]').val());
		}else{
			$('input[name=poundage]').val(isNumber($('input[name=rechargePoundageRate]').val()) ? '' : $('input[name=rechargePoundageStatic]').val());
		}
	})
	
	$(".lay-submit").click(function(){
		if($("select[name=type]").val()==""){
			layer.msg("请选择交易类型");
			return false;
		}
		if(!isNumber($("input[name=totalAmount]").val())){
			layer.msg("请填写交易总额");
			return false;
		}
		if($("select[name=type]").val()=="withdraw" && Number($("input[name=totalAmount]").val()) > Number($("input[name=balance]").val())){
			layer.msg("账户余额不足");
			return false;
		}
		var dataMap = {};
		dataMap['type'] = $("select[name=type]").val();
		dataMap['company'] = company;
		dataMap['companyBankcard'] = $("select[name=companyBankcard]").val();
		dataMap['totalAmount'] = $("input[name=totalAmount]").val() * 100;
		dataMap['data'] = $("textarea[name=data]").val();
		
		$.ajax({
	        url: '../system/companyTrade/add',
	        type: 'post',
	        async:false,
	        data: dataMap
	    }).done(function(r) {
    		if (r.status == "SUCCESS") {
    			layer.msg('添加成功', {
    				time: 1000 
    			}, function(){
    				var index = parent.layer.getFrameIndex(window.name); 
    				parent.getList();
    				parent.layer.close(index);
    			}); 
    		} else {
    			if(r.errorCode=="302"){
    				layer.msg(r.message, {
    		            time: 2000
    		        },function(){
    		        	parent.location.href="login.html";
    		        });
    			}else{
    				layer.msg(r.message);
    			}
    		}
	    }).fail(function(r) {
	        layer.msg("error", {
	            time: 2000
	        });
	    }); 
		return false;
	})
	//获取银行卡
	function getBankcardList(){
		$.ajax({
	        url: '../system/companyBankcard/list',
	        type: 'get',
	        async:false,
	        data: {
	        	'company':company,
	        	'status':'normal',
	        	'pageSize':1000,
	        	'pageNum':1
	        }
	    }).done(function(r) {
			if (r.status == "SUCCESS") {
				var tableHtml = '<option value="">请选择</option>';
				for(var i=0;i<r.data.length;i++){
					tableHtml += '<option value="'+r.data[i].uuid+'">'+r.data[i].bankName + r.data[i].accountNum +'</option>';
				}
				$("select[name='companyBankcard']").html(tableHtml);
				form.render();
			} 
	    })
	}
	//获取商户
	function getCompany(){
		$.ajax({
	        url: '../system/company/get',
	        type: 'get',
	        async:false,
	        data: {
	        	'uuid':company
	        }
	    }).done(function(r) {
			if (r.status == "SUCCESS") {
				$('input[name=company]').val(r.data.name);
				$('input[name=balance]').val((r.data.balance/100).toFixed(2));
				if(r.data.withdrawPoundage != null){
					$('input[name=withdrawPoundageStatic]').val((r.data.withdrawPoundage/100).toFixed(2));
				}else if(r.data.withdrawPoundageRate != null){
					$('input[name=withdrawPoundageRate]').val(r.data.withdrawPoundageRate);
				}else{
					$('input[name=withdrawPoundageStatic]').val('0.00');
				}
				if(r.data.rechargePoundage != null){
					$('input[name=rechargePoundageStatic]').val((r.data.rechargePoundage/100).toFixed(2));
				}else if(r.data.rechargePoundageRate != null){
					$('input[name=rechargePoundageRate]').val(r.data.rechargePoundageRate);
				}else{
					$('input[name=rechargePoundageStatic]').val('0.00');
				}
				if($("select[name=type]").val() == 'withdraw'){
					$('input[name=poundage]').val(isNumber($('input[name=withdrawPoundageRate]').val()) ? '' : $('input[name=withdrawPoundageStatic]').val());
				}else{
					$('input[name=poundage]').val(isNumber($('input[name=rechargePoundageRate]').val()) ? '' : $('input[name=rechargePoundageStatic]').val());
				}
				
			} 
	    })
	}
	
	function countAmount(){
		if($("select[name=type]").val() == 'withdraw'){
			if(isNumber($("input[name=totalAmount]").val())){
				var totalAmount = $("input[name=totalAmount]").val();
				var poundage = $('input[name=poundage]').val();
				if(isNumber($('input[name=withdrawPoundageRate]').val())){
					poundage = (totalAmount * $('input[name=withdrawPoundageRate]').val()).toFixed(2)
					$("input[name=poundage]").val(poundage);
				}
				var amount = totalAmount - poundage;
				$("input[name=amount]").val(amount.toFixed(2));
			}
		}else{
			if(isNumber($("input[name=totalAmount]").val())){
				var totalAmount = $("input[name=totalAmount]").val();
				var poundage = $('input[name=poundage]').val();
				if(isNumber($('input[name=rechargePoundageRate]').val())){
					poundage = (totalAmount * $('input[name=rechargePoundageRate]').val()).toFixed(2)
					$("input[name=poundage]").val(poundage);
				}
				var amount = totalAmount - poundage;
				$("input[name=amount]").val(amount.toFixed(2));
			}
		}
	}
});