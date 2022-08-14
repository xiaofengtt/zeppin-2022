var index = "0";
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
  form.on('radio', function(data){
	  index = data.value;
  });
});
$(".lay-submit").click(function(){
	if(index=="0"){
		if($(".addCoin").val().replace(/(^\s*)|(\s*$)/g, "")==""){
			layer.msg("请填写添加金币数目");
			return false;
		}
		$.ajax({
	        url: '../back/robot/goldadd',
	        type: 'post',
	        traditional: true,
	        async:false,
	        data: {
	        	'uuids':JSON.parse(window.localStorage.getItem("uuid")).split(","),
	        	'fee':$(".addCoin").val().replace(/(^\s*)|(\s*$)/g, "")
	        }
	    }).done(function(r) {
	    		if (r.status == "SUCCESS") {
	    			layer.msg('添加成功', {
	    				time: 1000 
	    			}, function(){
	    				window.localStorage.removeItem("uuid")
	    				var index = parent.layer.getFrameIndex(window.name); 
	    				window.parent.getList(size,num);
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
		if($(".subCoin").val().replace(/(^\s*)|(\s*$)/g, "")==""){
			layer.msg("请填写扣减金币数目");
			return false;
		}
		$.ajax({
	        url: '../back/robot/goldsub',
	        type: 'post',
	        async:false,
	        traditional: true,
	        data: {
	        	'uuids':JSON.parse(window.localStorage.getItem("uuid")).split(","),
	        	'fee':$(".subCoin").val().replace(/(^\s*)|(\s*$)/g, "")
	        }
	    }).done(function(r) {
	    		if (r.status == "SUCCESS") {
	    			layer.msg('扣减成功', {
	    				time: 1000 
	    			}, function(){
	    				window.localStorage.removeItem("uuid")
	    				var index = parent.layer.getFrameIndex(window.name); 
	    				window.parent.getList(size,num);
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
