/**
 * 商户注资
 */
layui.use(['form','element'], function(){
	var form = layui.form
	,layer = layui.layer
	,$ = layui.jquery
	,element = layui.element;
	
	getCompany()
	getBankcardList()
	
	form.on('select(companyBankcard)',function(data){
		$("select[name=companyBankcard]").val(data.value);
	})
	
	$("input[name=totalAmount]").blur(function(){
		var totalAmout = Number($("input[name=totalAmount]").val());
		$("input[name=totalAmount]").val(totalAmout.toFixed(2));
		countAmount();
	});
	
	$(".lay-submit").click(function(){
		if($("select[name=companyBankcard]").val() == ''){
			layer.msg("请选择银行卡");
			return false;
		}
		
		if(!isNumber($("input[name=totalAmount]").val())){
			layer.msg("请填写交易总额");
			return false;
		}
		
		var dataMap = {};
		dataMap['companyBankcard'] = $("select[name=companyBankcard]").val();
		dataMap['totalAmount'] = $("input[name=totalAmount]").val() * 100;
		dataMap['data'] = $("textarea[name=data]").val();
		
		$.ajax({
	        url: '../store/companyTrade/recharge',
	        type: 'post',
	        async:false,
	        data: dataMap
	    }).done(function(r) {
    		if (r.status == "SUCCESS") {
    			layer.msg('添加成功', {
    				time: 1000 
    			}, function(){
    				var index = parent.layer.getFrameIndex(window.name); 
    				parent.getCompany();
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
	
	//获取商户
	function getCompany(){
		$.ajax({
	        url: '../store/company/get',
	        type: 'get',
	        async:false,
	        data: {}
	    }).done(function(r) {
			if (r.status == "SUCCESS") {
				$('input[name=balance]').val((r.data.balance/100).toFixed(2));
				if(r.data.rechargePoundage != null){
					$('input[name=poundage]').val((r.data.rechargePoundage/100).toFixed(2));
				}else if(r.data.rechargePoundageRate != null){
					$('input[name=poundageRate]').val(r.data.rechargePoundageRate);
				}else{
					$('input[name=poundage]').val('0.00');
				}
			} 
	    })
	}
	//获取银行卡
	function getBankcardList(){
		$.ajax({
	        url: '../store/companyBankcard/list',
	        type: 'get',
	        async:false,
	        data: {
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
	function countAmount(){
		if(isNumber($("input[name=totalAmount]").val())){
			var totalAmount = $("input[name=totalAmount]").val();
			var poundage = $('input[name=poundage]').val();
			if(isNumber($('input[name=poundageRate]').val())){
				poundage = (totalAmount * $('input[name=poundageRate]').val()).toFixed(2)
				$("input[name=poundage]").val(poundage);
			}
			var amount = totalAmount - poundage;
			$("input[name=amount]").val(amount.toFixed(2));
		}
	}
});