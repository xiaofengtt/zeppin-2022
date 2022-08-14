var uuid = (url('?uuid') != null) ? url('?uuid') : '';
var flagSubmit = true;

$(function(){
	getDetail();
	getFrontUser();
});

//流水信息
function getDetail(){
	$.ajax({
        url: '../back/userHistory/get',
        type: 'get',
        async:false,
        data: {
            "uuid":uuid
        }
    }).done(function(r) {
		if (r.status == "SUCCESS") {
			$("#frontUserMobile").val(r.data.frontUserMobile);//手机号
			$("#frontUserName").val(r.data.frontUserName);
			$("#pay").val(moneyFormat(r.data.pay));
			$("#poundage").val(moneyFormat(r.data.poundage));
			$("#bankName").val(r.data.frontUserBankcardBankName);
			$("#accountNumber").val(r.data.frontUserBankcardAccountNumber);
			$("#creatTime").val(formatDate(r.data.createtime));
			$.ajax({
		        url: '../back/user/get',
		        type: 'get',
		        async:false,
		        data: {
		            "uuid":r.data.frontUser
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

//提交
$(".submit-btn").click(function(){
	layer.confirm('确定要提交吗？', {
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
		        url: '../back/userWithdraw/delete',
		        type: 'post',
		        async:false,
		        data: {
		           "uuid":uuid,
		           "reason":$("#reason").val().replace(/(^\s*)|(\s*$)/g, "")
		        }
		    }).done(function(r) {
	    		if (r.status == "SUCCESS") {
	    			layer.msg("提交成功！", {
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
