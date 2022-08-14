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
        url: '../back/userRecharge/get',
        type: 'get',
        async:false,
        data: {
            "uuid":uuid
        }
    }).done(function(r) {
    		if (r.status == "SUCCESS") {
    			var type = "";//类型
				if(r.data.type=="add"){
					type = "录入";
				}else if(r.data.type=="confirm"){
					type = "确认";
				}
    			$("#type").val(type);
    			$("#creatorName").val(r.data.creatorName);
    			$("#frontUserMobile").val(r.data.value.frontUserMobile);
    			$("#income").val(moneyFormat(r.data.value.income));
    			$("#capitalPlatformName").val(r.data.value.capitalPlatformName);
    			$("#poundage").val(moneyFormat(r.data.value.poundage));
    			$("#capitalAccountName").val(r.data.value.capitalAccountName);
    			$("#transData").val(r.data.value.transData);
    			$("#submittime").val(formatDate(r.data.submittime));
    			if(r.data.proofUrl){
    				$('#imageShow').attr('src', '..' + r.data.proofUrl).show()
    			}
    			if(pageType != '1'){
    				$("#reason").val(r.data.reason)
    				$("#checkerName").val(r.data.checkerName)
    			}
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
		        url: '../back/userRecharge/check',
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
		        url: '../back/userRecharge/check',
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
