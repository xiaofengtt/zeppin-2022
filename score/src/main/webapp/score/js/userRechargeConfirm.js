var uuid = (url('?uuid') != null) ? url('?uuid') : '';
var flagSubmit = true;

$(function(){
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
	getDetail();
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
    			$("#income").val(moneyFormat(r.data.income));
    			$("#capitalPlatformName").val(r.data.capitalPlatformName);
    			$("#poundage").val(moneyFormat(r.data.poundage));
    			$("#capitalAccountName").val(r.data.capitalAccountName);
    			$("#transData").val(r.data.transData);
    			$("#creatTime").val(formatDate(r.data.createtime));
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
		        url: '../back/userRecharge/confirm',
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
	}, function(){
	  layer.closeAll();
	});
});
