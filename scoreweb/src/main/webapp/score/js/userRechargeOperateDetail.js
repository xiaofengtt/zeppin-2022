var uuid = (url('?uuid') != null) ? url('?uuid') : '';
var pageType = (url('?type') != null) ? url('?type') : '0';
var flagSubmit = true;
var type = '';
var capitalPlatform = ''
var capitalAccount = ''

$(function(){
	if(pageType == '1'){
		$(".submit-btn").show();
	}
	getDetail();
});

//待审核
function getDetail(){
	$.ajax({
        url: '../back/userRecharge/get',
        type: 'get',
        async:true,
        data: {
            "uuid":uuid
        }
    }).done(function(r) {
    		if (r.status == "SUCCESS") {
    			type = r.data.type;
    			var typeCN = "";//类型
				if(r.data.type=="add"){
					typeCN = "录入";
				}else if(r.data.type=="confirm"){
					typeCN = "确认";
				}
				if(pageType == '1'){
					$("#resourceId").uploadFile({
	    		        id: "1",
	    		        url: "../back/resource/add",
	    		        allowedTypes: "png,jpg,jpeg",
	    		        maxFileSize: 1024 * 1024 * 1024 * 2,
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
					if(type == 'add'){
						$("#income").attr('disabled',false);
						$("#poundage").attr('disabled',false);
						$("#transData").attr('disabled',false);
						$("#capitalPlatformName").hide();
						$("#capitalAccountName").hide();
						capitalPlatform=r.data.value.capitalPlatform;
						capitalAccount=r.data.value.capitalAccount;
						getPlatformList();
					}else{
						$("#capitalPlatform").hide();
						$("#capitalAccount").hide();
		    			$("#capitalPlatformName").val(r.data.value.capitalPlatformName);
						$("#capitalAccountName").val(r.data.value.capitalAccountName);
					}
				}else{
					$("#capitalPlatform").hide();
					$("#capitalAccount").hide();
					$("#capitalPlatformName").val(r.data.value.capitalPlatformName);
					$("#capitalAccountName").val(r.data.value.capitalAccountName);
				}
    			$("#type").val(typeCN);
    			$("#checkerName").val(r.data.checkerName);
    			$("#reason").val(r.data.reason);
    			$("#frontUserMobile").val(r.data.value.frontUserMobile);
    			$("#income").val(moneyFormat(r.data.value.income));
    			$("#poundage").val(moneyFormat(r.data.value.poundage));
    			$("#transData").val(r.data.value.transData);
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
			if(type == 'add'){
				$.ajax({
			        url: '../back/userRecharge/edit',
			        type: 'post',
			        async:false,
			        data: {
			           "uuid":uuid,
			           "capitalAccount":$("#capitalAccount").val(),
			           "fee":$("#income").val().replace(/[^\d.]/g,""),
			           "poundage":$("#poundage").val().replace(/[^\d.]/g,""),
			           "transData":$("#transData").val(),
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
			}else if(type=='confirm'){
				$.ajax({
			        url: '../back/userRecharge/resubmit',
			        type: 'post',
			        async:false,
			        data: {
			           "uuid":uuid,
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
			}  	
	}, function(){
	  layer.closeAll();
	});
});

//渠道列表
function getPlatformList(){
	$.ajax({
        url: '../back/capitalPlatform/list',
        type: 'get',
        async:true
    }).done(function(r) {
    		if (r.status == "SUCCESS") {
    			var optionHtml = ""
    			for(var i in r.data){
    				optionHtml = optionHtml + '<option value="'+r.data[i].uuid+'">'+r.data[i].name+'</option>'
    			}
    			$("#capitalPlatform").html(optionHtml);
    			$("#capitalPlatform").val(capitalPlatform);
    			getAccountList();
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

//渠道账户列表
function getAccountList(){
	$.ajax({
        url: '../back/capitalAccount/list',
        type: 'get',
        async:false,
        data: {
            "capitalPlatform":$("#capitalPlatform").val()
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