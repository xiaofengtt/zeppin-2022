
layui.use(['form','element'], function(){
	var form = layui.form
	,layer = layui.layer
	,$ = layui.jquery
	,element = layui.element;
		
	$(".lay-submit").click(function(){
		var password = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$/
		if($("input[name='oldPwd']").val().replace(/(^\s*)|(\s*$)/g, "")==""){
			layer.msg("请填写原密码");
			return false;
		}
		if($("input[name='password']").val().replace(/(^\s*)|(\s*$)/g, "")==""){
			layer.msg("请填写新密码");
			return false;
		}
		if($("input[name='passwordNew']").val().replace(/(^\s*)|(\s*$)/g, "")==""){
			layer.msg("请填写确认密码");
			return false;
		}
		if (!password.test($("input[name='password']").val().replace(/(^\s*)|(\s*$)/g, ""))){
			layer.msg("请输入8-16位密码，需包含字母及数字");
			return false;
		}
		if($("input[name=password]").val().replace(/(^\s*)|(\s*$)/g, "") != $("input[name=passwordNew]").val().replace(/(^\s*)|(\s*$)/g, "")){
			layer.msg("两次密码输入不一致");
			return false;
		}
			$.ajax({
		        url: '../back/admin/resetPwd',
		        type: 'post',
		        async:false,
		        data: {
		        	'oldPwd':$("input[name=oldPwd]").val().replace(/(^\s*)|(\s*$)/g, ""),
		        	'password':$("input[name=passwordNew]").val().replace(/(^\s*)|(\s*$)/g, "")
		        }
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
		
		return false;
	})
});