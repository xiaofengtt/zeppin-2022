/**
 * 
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
			layer.msg("Please fill in the mobile");
			return false;
		}
		if($("input[name=username]").val()==''){
			layer.msg("Please fill in the username");
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
	    			layer.msg('Successful', {
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
				layer.msg("Please fill in the password");
				return false;
			}
			if($("input[name=confirmPassword]").val()==""){
				layer.msg("Please fill in the confirm");
				return false;
			}
			if($("input[name=password]").val() != $("input[name=confirmPassword]").val()){
				layer.msg("The two password inputs are inconsistent");
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
		    			layer.msg('Successful', {
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