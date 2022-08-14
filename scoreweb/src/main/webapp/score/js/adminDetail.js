var uuid = (url('?uuid') != null) ? url('?uuid') : '';
var flagSubmit = true;

$(function(){
	if(uuid!=""){
		$(".passwordBox").hide();
		getDetail();
	}else{
		$("#username").removeAttr("disabled")
	}
	
});
//管理员详情
function getDetail(){
	$.ajax({
        url: '../back/admin/get',
        type: 'get',
        async:false,
        data: {
            "uuid":uuid
        }
    }).done(function(r) {
    		if (r.status == "SUCCESS") {
    			var imageUrl = '';
    			$("#username").val(r.data.username);//名称
    			$("#realname").val(r.data.realname);//英文名称
    			$("#role").val(r.data.role);//角色
    			$("#status").val(r.data.status);//状态
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
			        url: '../back/admin/update',
			        type: 'post',
			        async:false,
			        data: {
			           "uuid":uuid,
			           "realname":$("#realname").val().replace(/(^\s*)|(\s*$)/g, ""),
			           "role":$("#role").val(),
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
		        url: '../back/admin/add',
		        type: 'post',
		        async:false,
		        data: {
		           "username":$("#username").val().replace(/(^\s*)|(\s*$)/g, ""),
		           "password":$("#password").val().replace(/(^\s*)|(\s*$)/g, ""),
		           "realname":$("#realname").val().replace(/(^\s*)|(\s*$)/g, ""),
		           "role":$("#role").val(),
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
