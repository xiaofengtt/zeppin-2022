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
	        url: '../back/winningRate/get',
	        type: 'get',
	        async:false,
	        data:{
	        	'uuid':uuid
	        }
	    }).done(function(r) {
	    		if (r.status == "SUCCESS") {
	    			 form.val('first',r.data);
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
	if($("input[name='goodsPriceMin']").val().replace(/(^\s*)|(\s*$)/g, "")==""){
		layer.msg("请填写最小奖品价值");
		return false;
	}
	if($("input[name='winningRate']").val().replace(/(^\s*)|(\s*$)/g, "")==""){
		layer.msg("请填写中奖概率");
		return false;
	}
	if($("input[name='goodsPriceMax']").val().replace(/(^\s*)|(\s*$)/g, "")!="" &&
		Number($("input[name='goodsPriceMin']").val().replace(/(^\s*)|(\s*$)/g, ""))>Number($("input[name='goodsPriceMax']").val().replace(/(^\s*)|(\s*$)/g, ""))){
		layer.msg("最小奖品价值不能大于最大奖品价值");
		return false;
	}
	if(Number($("input[name='winningRate']").val().replace(/(^\s*)|(\s*$)/g, ""))>100){
		layer.msg("中奖概率不能大于100");
		return false;
	}
	if(uuid!=""){
		$.ajax({
	        url: '../back/winningRate/edit',
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
	        url: '../back/winningRate/add',
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
