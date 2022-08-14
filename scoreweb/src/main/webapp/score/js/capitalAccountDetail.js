var uuid = (url('?uuid') != null) ? url('?uuid') : '';
var capitalPlatform = (url('?capitalPlatform') != null) ? url('?capitalPlatform') : '';
var flagSubmit = true;

$(function(){
	if(uuid!=""){
		$(".add-btn").hide();
		getDetail();
	}else{
		$(".submit-btn").hide();
		getPlatform();
	}
});

//渠道账号详情
function getDetail(){
	$.ajax({
        url: '../back/capitalAccount/get',
        type: 'get',
        async:false,
        data: {
            "uuid":uuid
        }
    }).done(function(r) {
    		if (r.status == "SUCCESS") {
    			$("#capitalPlatform").val(r.data.capitalPlatform);
    			$("#capitalPlatformName").val(r.data.capitalPlatformName);
    			$("#name").val(r.data.name);
    			$("#accountNum").val(r.data.accountNum);
    			$("#poundageRate").val(r.data.poundageRate);
    			$("#min").val(moneyFormat(r.data.min));
    			$("#max").val(moneyFormat(r.data.max));
    			$("#sort").val(r.data.sort);
    			$("#remark").val(r.data.remark);
    			$("#status").val(r.data.status);
    			$("#privateKey").val(r.data.data.privateKey);
    			$("#accountName").val(r.data.data.accountName);
				$("#bankName").val(r.data.data.bankName);
				$("#branchBank").val(r.data.data.branchBank);
    			var type = r.data.capitalPlatformType
    			switch (type){
    			case "company_bankcard":
    				$("#privateKeyDiv").hide();
					break
				case "company_alipay":
					$("#accountNameDiv").hide();
    				$("#bankNameDiv").hide();
    				$("#branchBankDiv").hide();
					break
				case "personal_bankcard":
					$("#privateKeyDiv").hide();
					$("#branchBankDiv").hide();
					break
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

//渠道详情
function getPlatform(){
	$.ajax({
        url: '../back/capitalPlatform/get',
        type: 'get',
        async: true,
        data: {
            "uuid":capitalPlatform
        }
    }).done(function(r) {
    		if (r.status == "SUCCESS") {
    			var imageUrl = '';
    			$("#capitalPlatform").val(r.data.uuid);
    			$("#capitalPlatformName").val(r.data.name);
    			var type = r.data.type;
    			switch (type){
    			case "company_bankcard":
    				$("#privateKeyDiv").hide();
					break
				case "company_alipay":
					$("#accountNameDiv").hide();
    				$("#bankNameDiv").hide();
    				$("#branchBankDiv").hide();
					break
				case "personal_bankcard":
					$("#privateKeyDiv").hide();
					$("#branchBankDiv").hide();
					break
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

//编辑保存
$(".submit-btn").click(function(){
	layer.confirm('确定要修改吗？', {
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
		        url: '../back/capitalAccount/edit',
		        type: 'post',
		        async:false,
		        data: {
		        	"uuid":uuid,
		           	"name":$("#name").val().replace(/(^\s*)|(\s*$)/g, ""),
		           	"accountNum":$("#accountNum").val().replace(/(^\s*)|(\s*$)/g, ""),
					"poundageRate":$("#poundageRate").val().replace(/(^\s*)|(\s*$)/g, ""),
					"min":$("#min").val().replace(/[^\d.]/g,""),
					"max":$("#max").val().replace(/[^\d.]/g,""),
					"privateKey":$("#privateKey").val().replace(/(^\s*)|(\s*$)/g, ""),
					"accountName":$("#accountName").val().replace(/(^\s*)|(\s*$)/g, ""),
					"bankName":$("#bankName").val().replace(/(^\s*)|(\s*$)/g, ""),
					"branchBank":$("#branchBank").val().replace(/(^\s*)|(\s*$)/g, ""),
					"sort":$("#sort").val().replace(/(^\s*)|(\s*$)/g, ""),
					"remark":$("#remark").val().replace(/(^\s*)|(\s*$)/g, ""),
					"status":$("#status").val()
		        }
		    }).done(function(r) {
	    		if (r.status == "SUCCESS") {
	    			layer.msg("修改成功！", {
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

//新增按钮
$(".add-btn").click(function(){
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
		        url: '../back/capitalAccount/add',
		        type: 'post',
		        async:false,
		        data: {
					"capitalPlatform":$("#capitalPlatform").val(),
					"name":$("#name").val().replace(/(^\s*)|(\s*$)/g, ""),
					"accountNum":$("#accountNum").val().replace(/(^\s*)|(\s*$)/g, ""),
					"poundageRate":$("#poundageRate").val().replace(/(^\s*)|(\s*$)/g, ""),
					"min":$("#min").val().replace(/[^\d.]/g,""),
					"max":$("#max").val().replace(/[^\d.]/g,""),
					"privateKey":$("#privateKey").val().replace(/(^\s*)|(\s*$)/g, ""),
					"accountName":$("#accountName").val().replace(/(^\s*)|(\s*$)/g, ""),
					"bankName":$("#bankName").val().replace(/(^\s*)|(\s*$)/g, ""),
					"branchBank":$("#branchBank").val().replace(/(^\s*)|(\s*$)/g, ""),
					"sort":$("#sort").val().replace(/(^\s*)|(\s*$)/g, ""),
					"remark":$("#remark").val().replace(/(^\s*)|(\s*$)/g, ""),
					"status":$("#status").val()
		        }
		    }).done(function(r) {
	    		if (r.status == "SUCCESS") {
	    			layer.msg("保存成功！", {
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