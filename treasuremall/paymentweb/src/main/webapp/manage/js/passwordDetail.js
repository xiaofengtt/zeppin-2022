/**
 * 修改密码
 */
layui.use(['form','element'], function(){
	var form = layui.form
	,layer = layui.layer
	,$ = layui.jquery
	,element = layui.element;
	
	$(".lay-submit").click(function(){
		if($("input[name=password]").val()==""){
			layer.msg("请填写原密码");
			return false;
		}
		if($("input[name=newPassword]").val()==""){
			layer.msg("请填写新密码");
			return false;
		}
		if($("input[name=confirmPassword]").val()==""){
			layer.msg("请确认新密码");
			return false;
		}
		if($("input[name=newPassword]").val() != $("input[name=confirmPassword]").val()){
			layer.msg("两次新密码输入不一致");
			return false;
		}
		$.ajax({
	        url: '../system/admin/password',
	        type: 'post',
	        async:false,
	        data: $("form").serialize()
	    }).done(function(r) {
	    		if (r.status == "SUCCESS") {
	    			layer.msg('修改成功', {
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