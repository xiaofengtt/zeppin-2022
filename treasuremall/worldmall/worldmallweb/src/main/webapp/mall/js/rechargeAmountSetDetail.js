var uuid = (url('?uuid') != null) ? url('?uuid') : '';
var pagesize = (url('?pagesize') != null) ? url('?pagesize') : 50;
var pagenum = (url('?pagenum') != null) ? url('?pagenum') : 1;
$(function(){
})
layui.use(['form','element'], function(){
	var form = layui.form
	,layer = layui.layer
	,$ = layui.jquery
	,element = layui.element;
	
	//初始赋值
	if(uuid!=""){
		$.ajax({
	        url: '../back/amountSet/get',
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
    		        	window.location.href="login.html";
    		        });
    			}else{
    				layer.msg(r.message);
    			}
    		}
	    }).fail(function(r) {
	        
	    }); 
	}
	
	$(".lay-submit").click(function(){
		if($("input[name=amount]").val().replace(/(^\s*)|(\s*$)/g, "")==""){
			layer.msg("请填写充值金额");
			return false;
		}
		if($("input[name=dAmount]").val().replace(/(^\s*)|(\s*$)/g, "")==""){
			layer.msg("请填写到账金币");
			return false;
		}
		if($("input[name=sort]").val().replace(/(^\s*)|(\s*$)/g, "")==""){
			layer.msg("请填写排序");
			return false;
		}
		var map = $("form").serializeObject();
		var isPreferential = map['isPreferential'] == '1';
		map['isPreferential'] = isPreferential;
		if(uuid!=""){
			map['uuid'] = uuid;
			$.ajax({
		        url: '../back/amountSet/edit',
		        type: 'post',
		        async:false,
		        data: map
		    }).done(function(r) {
	    		if (r.status == "SUCCESS") {
	    			layer.msg('编辑成功', {
	    				time: 1000 
	    			}, function(){
	    				var index = parent.layer.getFrameIndex(window.name); 
	    				window.parent.getList(pagesize,pagenum);
//	    				parent.location.reload();
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
		}else{	
			$.ajax({
		        url: '../back/amountSet/add',
		        type: 'post',
		        async:false,
		        data: map
		    }).done(function(r) {
		    		if (r.status == "SUCCESS") {
		    			layer.msg('添加成功', {
		    				time: 1000 
		    			}, function(){
		    				var index = parent.layer.getFrameIndex(window.name); 
		    				window.parent.getList(pagesize,pagenum);
//		    				parent.location.reload();
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
		}
		return false;
	})
});