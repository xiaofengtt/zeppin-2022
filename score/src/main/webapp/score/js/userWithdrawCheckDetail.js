var uuid = (url('?uuid') != null) ? url('?uuid') : '';
var pageType = (url('?type') != null) ? url('?type') : '0';
var flagSubmit = true;

$(function(){
	if(pageType == '1'){
		$(".submit-btn").show();
		$(".nopass-btn").show();
	}else{
		$("#reason").attr('disabled',true);
		$(".infoDiv").show();
	}
	
	getDetail();
});

//待审核
function getDetail(){
	$.ajax({
        url: '../back/userWithdraw/get',
        type: 'get',
        async:false,
        data: {
            "uuid":uuid
        }
    }).done(function(r) {
    		if (r.status == "SUCCESS") {
    			var typeCN = "";//类型
    			if(r.data.type=="apply"){
    				typeCN = "申请";
					$("#operateReasonDiv").hide();
					$("#proofDiv").hide();
				}else if(r.data.type=="confirm"){
					typeCN = "确认";
					$("#operateReasonDiv").hide();
				}else if(r.data.type=="rollback"){
					typeCN = "退回";
					$("#capitalAccountDiv").hide();
					$("#proofDiv").hide();
				}else if(r.data.type=="delete"){
					typeCN = "删除";
					$("#capitalAccountDiv").hide();
					$("#proofDiv").hide();
				}
    			$("#type").val(typeCN);
    			$("#creatorName").val(r.data.creatorName);
    			$("#frontUserMobile").val(r.data.value.frontUserMobile);//手机号
    			$("#frontUserName").val(r.data.value.frontUserName);
    			$("#pay").val(moneyFormat(r.data.value.pay));
    			$("#poundage").val(moneyFormat(r.data.value.poundage));
    			$("#bankName").val(r.data.value.frontUserBankcardBankName);
    			$("#accountNumber").val(r.data.value.frontUserBankcardAccountNumber);
    			$("#submittime").val(formatDate(r.data.submittime));
    			$("#capitalAccountName").val(r.data.value.capitalAccountName);
    			$("#operateReason").val(r.data.value.transData);
    			if(r.data.proofUrl){
    				$('#imageShow').attr('src', '..' + r.data.proofUrl).show()
    			}
    			if(pageType != '1'){
    				$("#reason").val(r.data.reason)
    				$("#checkerName").val(r.data.checkerName)
    			}
    			$.ajax({
    		        url: '../back/user/get',
    		        type: 'get',
    		        async:false,
    		        data: {
    		            "uuid":r.data.value.frontUser
    		        }
    		    }).done(function(re) {
    		    	if (re.status == "SUCCESS") {
    		    		$("#balanceLock").val(moneyFormat(re.data.balanceLock));
    		    	} else {
    					if(re.errorCode=="302"){
    						layer.msg(re.message, {
    				            time: 2000
    				        },function(){
    				        	window.location.href="login.html";
    				        });
    					}else{
    						layer.msg(re.message);
    					}
    				}
    		    }).fail(function(r) {
    		        layer.msg("error", {
    		            time: 2000
    		        },function(){
    		        	
    		        });
    		    }); 
    		} else {
    			if(r.errorCode=="302"){
    				layer.msg(r.message, {
    		            time: 2000
    		        },function(){
    		        	window.location.href="login.html";
    		        });
    			}else{
    				layer.msg(r.message);
    			}
    		}
    }).fail(function(r) {
        layer.msg("error", {
            time: 2000
        },function(){
        	
        });
    });   	
}

//通过
$(".submit-btn").click(function(){
	layer.confirm('确定审核通过吗？', {
		  btn: ['确定','取消'] //按钮
		}, function(){
			if(flagSubmit == false) {
				return false;
			}
			flagSubmit = false;
			setTimeout(function() {
				flagSubmit = true;
			}, 3000);
			$.ajax({
		        url: '../back/userWithdraw/check',
		        type: 'post',
		        async:false,
		        data: {
					"uuid":uuid,
					"status":"checked",
					"reason":$("#reason").val().replace(/(^\s*)|(\s*$)/g, "")
		        }
		    }).done(function(r) {
	    		if (r.status == "SUCCESS") {
	    			layer.msg("审核成功！", {
			            time: 2000
			        },function(){
			        	window.location.href=document.referrer;
			        });
	    				            
        		} else {
        			if(r.errorCode=="302"){
        				layer.msg(r.message, {
        		            time: 2000
        		        },function(){
        		        	window.location.href="login.html";
        		        });
        			}else{
        				layer.msg(r.message);
        			}
        		}
	    }).fail(function(r) {
	        layer.msg("error", {
	            time: 2000
	        },function(){
	        	window.location.href=window.location.href;
	        });
	    });   	
	}, function(){
	  layer.closeAll();
	});
});

//不通过
$(".nopass-btn").click(function(){
	layer.confirm('确定审核不通过吗？', {
		  btn: ['确定','取消'] //按钮
		}, function(){
			if(flagSubmit == false) {
				return false;
			}
			flagSubmit = false;
			setTimeout(function() {
				flagSubmit = true;
			}, 3000);
			$.ajax({
		        url: '../back/userWithdraw/check',
		        type: 'post',
		        async:false,
		        data: {
		        	"uuid":uuid,
			        "status":"nopass",
			        "reason":$("#reason").val().replace(/(^\s*)|(\s*$)/g, "")
		        }
		    }).done(function(r) {
	    		if (r.status == "SUCCESS") {
	    			layer.msg("审核成功！", {
			            time: 2000
			        },function(){
			        	window.location.href=document.referrer;
			        });
	    				            
        		} else {
        			if(r.errorCode=="302"){
        				layer.msg(r.message, {
        		            time: 2000
        		        },function(){
        		        	window.location.href="login.html";
        		        });
        			}else{
        				layer.msg(r.message);
        			}
        		}
	    }).fail(function(r) {
	        layer.msg("error", {
	            time: 2000
	        },function(){
	        	window.location.href=window.location.href;
	        });
	    });   	
	}, function(){
	  layer.closeAll();
	});
});
