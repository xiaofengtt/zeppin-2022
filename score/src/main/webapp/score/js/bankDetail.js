var uuid = (url('?uuid') != null) ? url('?uuid') : '';
var flagSubmit = true;

$(function(){
	if(uuid!=""){
		getDetail();
	}
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
            $('#imageShow').attr('src', '..' + data.data.url).css("cursor","pointer").show().unbind().click(function(){
            	$(".ajax-file-upload").find("input[type='file']").eq(0).click();
            });
            $(".ajax-upload-dragdrop").css("display", "block");
            $(".coverTips").html("修改图标").css("color","#fff");
        }
    });
});
//银行详情
function getDetail(){
	$.ajax({
        url: '../back/bank/get',
        type: 'get',
        async:false,
        data: {
            "uuid":uuid
        }
    }).done(function(r) {
    		if (r.status == "SUCCESS") {
    			var imageUrl = '';
    			$("#name").val(r.data.name);//名称
    			$("#shortName").val(r.data.shortName);//英文名称
    			$("#status").val(r.data.status);//状态
    			if(r.data.logoUrl){
    				$('input[name="document"]').val(r.data.logo);
    				$(".ajax-upload-dragdrop").show();
                    $('#imageShow').attr('src', '..' + r.data.logoUrl).css("cursor","pointer").show().unbind().click(function(){
                    	$(".ajax-file-upload").find("input[type='file']").eq(0).click();
                    });
                    $(".coverTips").html("修改图标").css("color","#fff");
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
	layer.confirm('确定要保存吗？', {
		  btn: ['确定','取消'] //按钮
		}, function(){
			if(flagSubmit == false) {
				return false;
			}
			flagSubmit = false;
			setTimeout(function() {
				flagSubmit = true;
			}, 3000);
			if(uuid!=""){
				$.ajax({
			        url: '../back/bank/edit',
			        type: 'post',
			        async:false,
			        data: {
			           "uuid":uuid,
			           "name":$("#name").val().replace(/(^\s*)|(\s*$)/g, ""),
			           "shortName":$("#shortName").val().replace(/(^\s*)|(\s*$)/g, ""),
			           "logo":$("#document").val(),
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
		}else{
			$.ajax({
		        url: '../back/bank/add',
		        type: 'post',
		        async:false,
		        data: {
		        	"uuid":uuid,
		        	"name":$("#name").val().replace(/(^\s*)|(\s*$)/g, ""),
		        	"shortName":$("#shortName").val().replace(/(^\s*)|(\s*$)/g, ""),
		        	"logo":$("#document").val(),
		        	"status":$("#status").val()
		        }
		    }).done(function(r) {
	    		if (r.status == "SUCCESS") {
	    			layer.msg("添加成功！", {
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
