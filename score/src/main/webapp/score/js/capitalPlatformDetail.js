var uuid = (url('?uuid') != null) ? url('?uuid') : '';
var flagSubmit = true;

$(function(){
	if(uuid!=""){
		$(".add-btn").hide();
		$("#type").attr("disabled","disabled")
		$("#transType").attr("disabled","disabled")
		getDetail();
	}else{
		$(".submit-btn").hide();
	}
});

//渠道详情
function getDetail(){
	$.ajax({
        url: '../back/capitalPlatform/get',
        type: 'get',
        async:false,
        data: {
            "uuid":uuid
        }
    }).done(function(r) {
    		if (r.status == "SUCCESS") {
    			$("#name").val(r.data.name);
    			$("#transType").val(r.data.transType);
    			$("#type").val(r.data.type);
    			$("#sort").val(r.data.sort);
    			$("#remark").val(r.data.remark);
    			$("#status").val(r.data.status);
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
		        url: '../back/capitalPlatform/edit',
		        type: 'post',
		        async:false,
		        data: {
		           "uuid":uuid,
		           "name":$("#name").val().replace(/(^\s*)|(\s*$)/g, ""),
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
		        url: '../back/capitalPlatform/add',
		        type: 'post',
		        async:false,
		        data: {
					"name":$("#name").val().replace(/(^\s*)|(\s*$)/g, ""),
					"transType":$("#transType").val(),
					"type":$("#type").val(),
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