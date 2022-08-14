var uuid = (url('?uuid') != null) ? url('?uuid') : '';
var size = (url('?pagesize') != null) ? url('?pagesize') : '';
var num = (url('?pagenum') != null) ? url('?pagenum') : '';
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
	        url: '../back/robot/get',
	        type: 'get',
	        async:false,
	        data:{
	        	'uuid':uuid
	        }
	    }).done(function(r) {
	    		if (r.status == "SUCCESS") {
	    			$("input[name='mobile']").attr("disabled",true)
	    			 form.val('first',r.data);
	    			 $("input[name='pwdstr']").val(r.data.password);
	    			 $("input[name='ipstr']").val(r.data.ip);
	    			 $('#demo1').attr('src','..' + r.data.imageURL);
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
  upload.render({
	    elem: '#uploadFile'
	    ,url: '../back/resource/add'
	    ,done: function(res){
	    	$('input[name="image"]').val(res.data.uuid);
	    	$('#demo1').attr('src','..' + res.data.url); 
	    }
	  });
  
});
$(".lay-submit").click(function(){
	var reg = /^((\d)|([1-9]\d)|(1\d{2})|((2[0-4]\d)|(25[0-5])))(\.((\d)|([1-9]\d)|(1\d{2})|((2[0-4]\d)|(25[0-5])))){3}$/;
	var ipstr = $("input[name='ipstr']").val().replace(/(^\s*)|(\s*$)/g, "");
	if($("input[name='mobile']").val().replace(/(^\s*)|(\s*$)/g, "")==""){
		layer.msg("请填写手机号");
		return false;
	}
	if(ipstr!=""&&!reg.test(ipstr)){
		layer.msg("请填写正确的ip地址");
		return false;
	}
	if(uuid!=""){
		$.ajax({
	        url: '../back/robot/edit',
	        type: 'post',
	        async:false,
	        data: $("form").serialize()+"&uuid="+uuid
	    }).done(function(r) {
	    		if (r.status == "SUCCESS") {
	    			layer.msg('编辑成功', {
	    				time: 1000 
	    			}, function(){
	    				window.location.href = 'robotList.html?pagesize='+size+'&pagenum='+num;
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
	        url: '../back/robot/add',
	        type: 'post',
	        async:false,
	        data: $("form").serialize()
	    }).done(function(r) {
	    		if (r.status == "SUCCESS") {
	    			layer.msg('添加成功', {
	    				time: 1000 
	    			}, function(){
	    				window.location.href = 'robotList.html';
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
