/**
 * 商户渠道编辑
 */
var uuid = (url('?uuid') != null) ? url('?uuid') : '';
var company = (url('?company') != null) ? url('?company') : '';

$(function(){
	$('input[name=company]').val(company);
	if(uuid != ''){
		$("#pwdDiv").hide();
	}
	getCompany();
})
layui.use(['form','element'], function(){
	var form = layui.form
	,layer = layui.layer
	,$ = layui.jquery
	,element = layui.element;
	
	//初始赋值
	if(uuid!=""){
		$.ajax({
	        url: '../system/companyAdmin/get',
	        type: 'get',
	        async:false,
	        data:{
	        	'uuid':uuid
	        }
	    }).done(function(r) {
	    	if (r.status == "SUCCESS") {
    			form.val('first',r.data);
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
	        
	    }); 
	}
	
	$(".lay-submit").click(function(){
		if($("input[name=mobile]").val()==''){
			layer.msg("请填写手机号码");
			return false;
		}
		if($("input[name=username]").val()==''){
			layer.msg("请填写用户名");
			return false;
		}
		
		var dataMap = $("form").serializeObject();
		if(uuid!=""){
			dataMap['uuid'] = uuid;
			$.ajax({
		        url: '../system/companyAdmin/edit',
		        type: 'post',
		        async:false,
		        data: dataMap
		    }).done(function(r) {
	    		if (r.status == "SUCCESS") {
	    			layer.msg('编辑成功', {
	    				time: 1000 
	    			}, function(){
	    				var index = parent.layer.getFrameIndex(window.name); 
	    				parent.getList();
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
		}else{			
			if($("input[name=password]").val()==""){
				layer.msg("请填写密码");
				return false;
			}
			if($("input[name=confirmPassword]").val()==""){
				layer.msg("请确认密码");
				return false;
			}
			if($("input[name=password]").val() != $("input[name=confirmPassword]").val()){
				layer.msg("两次密码输入不一致");
				return false;
			}
			dataMap['password'] = $("input[name=password]").val()
			$.ajax({
		        url: '../system/companyAdmin/add',
		        type: 'post',
		        async:false,
		        data: dataMap
		    }).done(function(r) {
		    		if (r.status == "SUCCESS") {
		    			layer.msg('添加成功', {
		    				time: 1000 
		    			}, function(){
		    				var index = parent.layer.getFrameIndex(window.name); 
		    				parent.getList();
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
		}
		return false;
	})
});
//获取商户
function getCompany(){
	$.ajax({
        url: '../system/company/get',
        type: 'get',
        async:false,
        data: {
        	'uuid': company
        }
    }).done(function(r) {
    	if (r.status == "SUCCESS") {
    		$("input[name='companyName']").val(r.data.name);
    	}
    })
}