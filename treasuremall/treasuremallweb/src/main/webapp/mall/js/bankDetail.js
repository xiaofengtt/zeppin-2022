var uuid = (url('?uuid') != null) ? url('?uuid') : '';
var pagesize = (url('?pagesize') != null) ? url('?pagesize') : 50;
var pagenum = (url('?pagenum') != null) ? url('?pagenum') : 1;
$(function(){
})
layui.use(['form','element','upload'], function(){
	var form = layui.form
	,layer = layui.layer
	,$ = layui.jquery
	,upload = layui.upload
	,element = layui.element;
	
	upload.render({
		elem: '#uploadFile'
		,url: '../back/resource/add'
		,done: function(res){
			$('input[name="logo"]').val(res.data.uuid);
			$('#demo1').attr('src','..' + res.data.url); 
		}
	});
	
	//初始赋值
	if(uuid!=""){
		$.ajax({
	        url: '../back/bank/get',
	        type: 'get',
	        async:false,
	        data:{
	        	'uuid':uuid
	        }
	    }).done(function(r) {
	    	if (r.status == "SUCCESS") {
    			form.val('first',r.data);
    			$('#demo1').attr('src','..' + r.data.logoUrl);
    			$(".layui-upload-list").show();
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
		if($("input[name=name]").val().replace(/(^\s*)|(\s*$)/g, "")==""){
			layer.msg("请填写名称");
			return false;
		}
		if($("input[name=shortName]").val().replace(/(^\s*)|(\s*$)/g, "")==""){
			layer.msg("请填写简称");
			return false;
		}
		if($("input[name=logo]").val() == undefined || $("input[name=logo]").val()==""){
			layer.msg("请上传logo");
			return false;
		}
		
		if(uuid!=""){
			$.ajax({
		        url: '../back/bank/edit',
		        type: 'post',
		        async:false,
		        data: $("form").serialize()+"&uuid="+uuid
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
		        url: '../back/bank/add',
		        type: 'post',
		        async:false,
		        data: $("form").serialize()
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