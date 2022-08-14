/**
 * 
 */
layui.use(['form','element'], function(){
	var form = layui.form
	,layer = layui.layer
	,$ = layui.jquery
	,element = layui.element;
	
	$(".lay-submit").click(function(){
		if($("input[name=password]").val()==""){
			layer.msg("Please fill in the old pwd");
			return false;
		}
		if($("input[name=newPassword]").val()==""){
			layer.msg("Please fill in the new pwd");
			return false;
		}
		if($("input[name=confirmPassword]").val()==""){
			layer.msg("Please fill in the confirm pwd");
			return false;
		}
		if($("input[name=newPassword]").val() != $("input[name=confirmPassword]").val()){
			layer.msg("The two new password entries are inconsistent");
			return false;
		}
		$.ajax({
	        url: '../store/companyAdmin/password',
	        type: 'post',
	        async:false,
	        data: $("form").serialize()
	    }).done(function(r) {
	    		if (r.status == "SUCCESS") {
	    			layer.msg('Successful', {
	    				time: 1000 
	    			}, function(){
	    				var index = parent.layer.getFrameIndex(window.name);
	    				parent.layer.close(index);
	    			}); 
	    		} else {
	    			if(r.errorCode=="302"){
	    				layer.msg(r.message, {
	    		            time: 2000
	    		        },function(){
	    		        	parent.location.href="login.html";
	    		        });
	    			}else{
	    				layer.msg(r.message);
	    			}
	    		}
	    }).fail(function(r) {
	        layer.msg("error", {
	            time: 2000
	        });
	    }); 
		return false;
	})
});