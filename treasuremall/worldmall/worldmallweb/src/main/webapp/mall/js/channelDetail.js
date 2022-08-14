var uuid = (url('?uuid') != null) ? url('?uuid') : '';
var pagesize = (url('?pagesize') != null) ? url('?pagesize') : 50;
var pagenum = (url('?pagenum') != null) ? url('?pagenum') : 1;
$(function(){
	
})
layui.use(['form', 'layedit', 'laydate','upload','element'], function(){
  var form = layui.form
  ,layer = layui.layer
  ,layedit = layui.layedit
  ,laydate = layui.laydate
  ,$ = layui.jquery
  ,upload = layui.upload
  ,element = layui.element;

  //自定义验证规则
  form.verify({
    
  });
  
  //日期
  laydate.render({
    elem: '#test1'
    ,type: 'datetime'
	,theme: '#3D99FF'
  });
  
  //初始赋值
  if(uuid!=""){
		$.ajax({
	        url: '../back/channel/get',
	        type: 'get',
	        async:false,
	        data:{
	        	'uuid':uuid
	        }
	    }).done(function(r) {
	    		if (r.status == "SUCCESS") {
	    			 form.val('first',r.data);
	    			 if(r.data.isDefault==true){
	 	   				 $("input[name=isDefault][value='true']").prop("checked",true);
	 	   			 }else{
	 	   				 $("input[name=isDefault][value='false']").prop("checked",true);
	 	   			 }
	     			 form.render();
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
  
});
$(".lay-submit").click(function(){
	if($("input[name='title']").val().replace(/(^\s*)|(\s*$)/g, "")==""){
		layer.msg("请填写渠道名称");
		return false;
	}
	if($("input[name='channelId']").val().replace(/(^\s*)|(\s*$)/g, "")==""){
		layer.msg("请填写渠道ID");
		return false;
	}
	if(uuid!=""){
		$.ajax({
	        url: '../back/channel/edit',
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
	        url: '../back/channel/add',
	        type: 'post',
	        async:false,
	        data: $("form").serialize()
	    }).done(function(r) {
	    		if (r.status == "SUCCESS") {
	    			layer.msg('添加成功', {
	    				time: 1000 
	    			}, function(){
	    				var index = parent.layer.getFrameIndex(window.name); 
	    				window.parent.getList(pagesize,'1');
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
	}
	return false;
})
