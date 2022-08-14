/**
 * 
 */
layui.use(['form','element'], function(){
	var form = layui.form
	,layer = layui.layer
	,$ = layui.jquery
	,element = layui.element;
	
	getCompany()
	getCompanyChanenlList()
	getBankcardList()
	
	form.on('select(companyChannel)',function(data){
		$("select[name=companyChannel]").val(data.value);
		var balance =$('select[name=companyChannel] option[value='+data.value+']').attr('balance');
		$('input[name=balance]').val(balance);
	})
	
	form.on('select(companyBankcard)',function(data){
		$("select[name=companyBankcard]").val(data.value);
	})
	
	$("input[name=totalAmount]").blur(function(){
		var totalAmout = Number($("input[name=totalAmount]").val());
		$("input[name=totalAmount]").val(totalAmout.toFixed(2));
		countAmount();
	});
	
	$(".lay-submit").click(function(){
		if($("select[name=companyChannel]").val() == ''){
			layer.msg("Please select the channel");
			return false;
		}
		
		if($("select[name=companyBankcard]").val() == ''){
			layer.msg("Please select the bankcard");
			return false;
		}
		
		if(!isNumber($("input[name=totalAmount]").val())){
			layer.msg("Please fill in the withdrawal");
			return false;
		}
		
		var dataMap = {};
		dataMap['companyChannel'] = $("select[name=companyChannel]").val();
		dataMap['companyBankcard'] = $("select[name=companyBankcard]").val();
		dataMap['totalAmount'] = $("input[name=totalAmount]").val() * 100;
		dataMap['data'] = $("textarea[name=data]").val();
		
		$.ajax({
	        url: '../store/companyTrade/withdraw',
	        type: 'post',
	        async:false,
	        data: dataMap
	    }).done(function(r) {
    		if (r.status == "SUCCESS") {
    			layer.msg('Successful', {
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
	

	function getCompany(){
		$.ajax({
	        url: '../store/company/get',
	        type: 'get',
	        async:false,
	        data: {}
	    }).done(function(r) {
			if (r.status == "SUCCESS") {
				if(r.data.withdrawPoundage != null){
					$('input[name=poundage]').val((r.data.withdrawPoundage/100).toFixed(2));
				}else if(r.data.withdrawPoundageRate != null){
					$('input[name=poundageRate]').val(r.data.withdrawPoundageRate);
				}else{
					$('input[name=poundage]').val('0.00');
				}
			} 
	    })
	}

	function getCompanyChanenlList(){
		$.ajax({
	        url: '../store/companyChannel/list',
	        type: 'get',
	        async:false,
	        data: {
	        	'type':'withdraw',
	        	'pageSize':1000,
	        	'pageNum':1
	        }
	    }).done(function(r) {
			if (r.status == "SUCCESS") {
				var tableHtml = '<option value="">please select</option>';
				for(var i=0;i<r.data.length;i++){
					tableHtml += '<option balance="'+(r.data[i].balance/100).toFixed(2)+'" value="'+r.data[i].uuid+'">'+r.data[i].uuid +'</option>';
				}
				$("select[name='companyChannel']").html(tableHtml);
				form.render();
			} 
	    })
	}

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
				var tableHtml = '<option value="">please select</option>';
				for(var i=0;i<r.data.length;i++){
					tableHtml += '<option value="'+r.data[i].uuid+'">'+r.data[i].bank + r.data[i].accountNum +'</option>';
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