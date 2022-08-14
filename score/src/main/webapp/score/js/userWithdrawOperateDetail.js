var uuid = (url('?uuid') != null) ? url('?uuid') : '';
var pageType = (url('?type') != null) ? url('?type') : '0';
var flagSubmit = true;
var type = '';
var capitalAccount = '';
var autoFlag = '';

$(function(){
	if(pageType == '1'){
		$(".submit-btn").show();
	}
	getDetail();
});

//待审核
function getDetail(){
	$.ajax({
        url: '../back/userWithdraw/get',
        type: 'get',
        async:true,
        data: {
            "uuid":uuid
        }
    }).done(function(r) {
    		if (r.status == "SUCCESS") {
    			capitalAccount = r.data.value.capitalAccount
    			type = r.data.type;
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
				if(pageType == '1'){
					if(type == 'confirm'){
						$("#capitalAccountName").hide();
						autoFlag = 'false';
						getAccountList()
						$("#resourceId").uploadFile({
		    		        id: "1",
		    		        url: "../back/resource/add",
		    		        allowedTypes: "png,jpg,jpeg",
		    		        maxFileSize: 1024 * 1024 * 2,
		    		        fileName: "file",
		    		        maxFileCount: 100,
		    		        dragDropStr: "",
		    		        extErrorStr: "文件格式不正确，请上传指定类型的文件",
		    		        multiple: false,
		    		        showDone: false,
		    		        showDelete: true,
		    		        deletelStr: '删除',
		    		        doneStr: "完成",
		    		        showAbort: false,
		    		        showStatusAfterSuccess: false,
		    		        maxFileCountErrorStr: "文件数量过多，请先删除。",
		    		        onSuccess: function(files, data, xhr) {
		    		            $('input[name="document"]').val(data.data.uuid);
		    		            $('#imageShow').attr('src', '..' + data.data.url).show().unbind().click(function(){
		    		            	$(".ajax-file-upload").find("input[type='file']").eq(0).click();
		    		            });
		    		            $(".coverTips").html("修改凭证").css("color","#fff");
		    		        }
		    		    });
					}else if(type == 'apply') {
						$("#capitalAccountName").hide();
						autoFlag = 'true';
						getAccountList()
					}else if(type == 'rollback'){
						$("#operateReason").attr('disabled',false);
					}
				}else{
					$("#capitalAccount").hide();
				}
    			$("#type").val(typeCN);
    			$("#checkerName").val(r.data.checkerName);
    			$("#reason").val(r.data.reason);
    			$("#frontUserMobile").val(r.data.value.frontUserMobile);
    			$("#frontUserName").val(r.data.value.frontUserName);
    			$("#pay").val(moneyFormat(r.data.value.pay));
    			$("#poundage").val(moneyFormat(r.data.value.poundage));
    			$("#bankName").val(r.data.value.frontUserBankcardBankName);
    			$("#accountNumber").val(r.data.value.frontUserBankcardAccountNumber);
    			$("#capitalAccountName").val(r.data.value.capitalAccountName);
    			$("#operateReason").val(r.data.value.transData);
    			if(r.data.proofUrl){
    				if(pageType == '1'){
	    				$('input[name="document"]').val(r.data.proof);
	    				$(".ajax-upload-dragdrop").show();
	                    $('#imageShow').attr('src', '..' + r.data.proofUrl).css("cursor","pointer").show().unbind().click(function(){
	                    	$(".ajax-file-upload").find("input[type='file']").eq(0).click();
	                    });
	                    $(".coverTips").html("修改凭证").css("color","#fff");
    				}else{
    					$('#imageShow').attr('src', '..' + r.data.proofUrl).css("cursor","pointer").show()
    					$(".coverTips").html("")
    				}
    			}else{
    				if(pageType != '1'){
    					$(".coverTips").html("")
    				}
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

//提交
$(".submit-btn").click(function(){
	layer.confirm('确定重新提交吗？', {
		  btn: ['确定','取消'] //按钮
		}, function(){
			if(flagSubmit == false) {
				return false;
			}
			flagSubmit = false;
			setTimeout(function() {
				flagSubmit = true;
			}, 3000);
			if(type == 'apply'){
				$.ajax({
			        url: '../back/userWithdraw/reapply',
			        type: 'post',
			        async:false,
			        data: {
			           "uuid":uuid,
			           "capitalAccount":$("#capitalAccount").val()
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
			}else if(type=='confirm'){
				$.ajax({
			        url: '../back/userWithdraw/resubmit',
			        type: 'post',
			        async:false,
			        data: {
			           "uuid":uuid,
			           "capitalAccount":$("#capitalAccount").val(),
			           "proof":$("#document").val()
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
			}else if(type=='rollback'){
				$.ajax({
			        url: '../back/userWithdraw/rerollback',
			        type: 'post',
			        async:false,
			        data: {
			           "uuid":uuid,
			           "reason":$("#operateReason").val().replace(/(^\s*)|(\s*$)/g, "")
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
			}else if(type=='delete'){
				$.ajax({
			        url: '../back/userWithdraw/redelete',
			        type: 'post',
			        async:false,
			        data: {
			           "uuid":uuid,
			           "reason":$("#operateReason").val().replace(/(^\s*)|(\s*$)/g, "")
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
			}
	}, function(){
	  layer.closeAll();
	});
});

//渠道账户列表
function getAccountList(){
	$.ajax({
        url: '../back/capitalAccount/list',
        type: 'get',
        async:false,
        data: {
            "transType" : "withdraw",
            "status": "normal",
            "auto":autoFlag,
        }
    }).done(function(r) {
    		if (r.status == "SUCCESS") {
    			var optionHtml = "<option>请选择</option>"
    			for(var i in r.data){
    				optionHtml = optionHtml + '<option value="'+r.data[i].uuid+'">'+r.data[i].name+'</option>'
    			}
    			$("#capitalAccount").html(optionHtml);
    			if(capitalAccount != ''){
    				$("#capitalAccount").val(capitalAccount)
    				capitalAccount = '';
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