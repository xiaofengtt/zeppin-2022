/**
 * Administrator edit
 */
var uuid = (url('?uuid') != null) ? url('?uuid') : '';
$(function(){
	if(uuid != ''){
		$("input[name=username]").attr("disabled",true);
		$("input[name=password]").attr("disabled",true);
	}
})
layui.use(['form','element'], function(){
	var form = layui.form
	,layer = layui.layer
	,$ = layui.jquery
	,element = layui.element;
  
	form.on('select(role)',function(data){
		$("select[name=role]").val(data.value)
	})
	
	//Initial assignment
	if(uuid!=""){
		$.ajax({
	        url: '../system/admin/get',
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
		if($("input[name=username]").val().replace(/(^\s*)|(\s*$)/g, "")==""){
			layer.msg("Please fill in the username");
			return false;
		}
		if($("input[name=realname]").val().replace(/(^\s*)|(\s*$)/g, "")==""){
			layer.msg("Please fill in your realname");
			return false;
		}
		if($("select[name=role]").val() == undefined || $("input[name=type]").val()==""){
			layer.msg("Please select a role");
			return false;
		}
		
		if(uuid!=""){
			$.ajax({
		        url: '../system/admin/edit',
		        type: 'post',
		        async:false,
		        data: $("form").serialize()+"&uuid="+uuid
		    }).done(function(r) {
	    		if (r.status == "SUCCESS") {
	    			layer.msg('Edit succeeded', {
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
			if($("input[name=password]").val().replace(/(^\s*)|(\s*$)/g, "")==""){
				layer.msg("Please fill in the password");
				return false;
			}
			$.ajax({
		        url: '../system/admin/add',
		        type: 'post',
		        async:false,
		        data: $("form").serialize()
		    }).done(function(r) {
		    		if (r.status == "SUCCESS") {
		    			layer.msg('Added successfully', {
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