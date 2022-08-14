var uuid = (url('?uuid') != null) ? url('?uuid') : '';
var pagesize = (url('?pagesize') != null) ? url('?pagesize') : 50;
var pagenum = (url('?pagenum') != null) ? url('?pagenum') : 1;
var goodsSelect;
var goodsTypeSelect;

$(function(){
	$(".goPrePage").click(function(){
		window.location.href='noticeList.html?pagesize='+pagesize+'&pagenum='+pagenum
	});
})

layui.use(['form', 'laydate', 'element'], function(){
	var form = layui.form
	,layer = layui.layer
	,laydate = layui.laydate
	,$ = layui.jquery
	,element = layui.element;
	
	laydate.render({
		elem: '#staticStarttime',
		type: 'datetime',
		format: 'yyyy-MM-dd HH:mm:ss',
		theme: '#3D99FF'
	});
	//初始赋值
  if(uuid!=""){
		$.ajax({
	        url: '../back/noticePublic/get',
	        type: 'get',
	        async:false,
	        data:{
	        	'uuid':uuid
	        }
	    }).done(function(r) {
	    		if (r.status == "SUCCESS") {
	    			 form.val('first',r.data);
	    			 $("#staticStarttime").val(formatDate(r.data.onlinetime));
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
		if($("input[name=title]").val().replace(/(^\s*)|(\s*$)/g, "")==""){
			layer.msg("请填写公告标题");
			return false;
		}
		if($("textarea[name=details]").val().replace(/(^\s*)|(\s*$)/g, "")==""){
			layer.msg("请填写公告内容");
			return false;
		}
		if($("input[name=onlinetime]").val().replace(/(^\s*)|(\s*$)/g, "")==""){
			layer.msg("请设置上线时间");
			return false;
		}
		if(uuid!=""){
			$.ajax({
		        url: '../back/noticePublic/edit',
		        type: 'post',
		        async:false,
		        data: $("form").serialize()+"&uuid="+uuid
		    }).done(function(r) {
	    		if (r.status == "SUCCESS") {
	    			layer.msg('编辑成功', {
	    				time: 1000 
	    			}, function(){
	    				window.location.href='noticeList.html?pagesize='+pagesize+'&pagenum='+pagenum
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
		        url: '../back/noticePublic/add',
		        type: 'post',
		        async:false,
		        data: $("form").serialize()
		    }).done(function(r) {
	    		if (r.status == "SUCCESS") {
	    			layer.msg('添加成功', {
	    				time: 1000 
	    			}, function(){
	    				window.location.href='noticeList.html?pagesize='+pagesize+'&pagenum='+pagenum
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
