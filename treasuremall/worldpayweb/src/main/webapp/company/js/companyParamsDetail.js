/**
 * 
 */

$(function(){
	getDetail();
});

layui.use(['form','element'], function(){
	var form = layui.form
	,layer = layui.layer
	,$ = layui.jquery
	,element = layui.element;
	
	$(".lay-submit").click(function(){
		if($("input[name=whiteUrl]").val()==""){
			layer.msg("Please fill in the white list address, unlimited please fill in *！");
			return false;
		}
		$.ajax({
	        url: '../store/company/edit',
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

function getDetail(){
	$.ajax({
        url: '../store/company/get',
        type: 'get',
        async:false,
        data: {}
    }).done(function(r) {
		if (r.status == "SUCCESS") {
			$('input[name=whiteUrl]').val(r.data.whiteUrl);
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