var uuid = (url('?uuid') != null) ? url('?uuid') : '';
var pagesize = (url('?pagesize') != null) ? url('?pagesize') : 50;
var pagenum = (url('?pagenum') != null) ? url('?pagenum') : 1;
var frontUserArr = [];
$(function(){
	$(".goPrePage").click(function(){
		window.location.href='bannerList.html?pagesize='+pagesize+'&pagenum='+pagenum
	});
	$(".skipLink").click(function(){
		window.location.href='bannerSecondList.html?type='+url('?type')+'&pagesize='+pagesize+'&pagenum='+pagenum
	})
	$("select[name='type']").val(url('?type'))
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
    elem: '#endtime'
    ,type: 'datetime'
    ,range: true
    ,format: 'yyyy-MM-dd HH:mm:ss'
	,theme: '#3D99FF'
  });
  
  //初始赋值
  if(uuid!=""){
		$.ajax({
	        url: '../back/banner/get',
	        type: 'get',
	        async:false,
	        data:{
	        	'uuid':uuid
	        }
	    }).done(function(r) {
	    		if (r.status == "SUCCESS") {
	    			 form.val('first',r.data);
	    			 $('#demo1').attr('src','..' + r.data.imageUrl);
	    			 $(".layui-upload-list").show();
	    			 $("#endtime").val(formatDate(r.data.starttime)+' - '+formatDate(r.data.endtime));
	    			 if(r.data.frontUserLevel){
	    				 frontUserArr = r.data.frontUserLevel.split(",");
		    			 $("input[name='frontuserlevel']").each(function(i,value){
		    				 var value = $(this).val();
			    			 for(var i=0;i<frontUserArr.length;i++){		    				 
		    					 if (frontUserArr[i] == value) {
		    			                $(this).next('div').click()
		    			          }
			    			 }
		    			 })
	    			 } 
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
	    	$(".layui-upload-list").show();
	    }
	  });
  
});
$(".lay-submit").click(function(){
	if($("input[name='title']").val().replace(/(^\s*)|(\s*$)/g, "")==""){
		layer.msg("请填写中文名");
		return false;
	}
	if($("input[name='code']").val().replace(/(^\s*)|(\s*$)/g, "")==""){
		layer.msg("请填写英文名");
		return false;
	}
	if($("input[name='url']").val().replace(/(^\s*)|(\s*$)/g, "")==""){
		layer.msg("请填写跳转地址");
		return false;
	}
	if($("input[name='image']").val().replace(/(^\s*)|(\s*$)/g, "")==""){
		layer.msg("请上传图片");
		return false;
	}
	if($("#endtime").val().replace(/(^\s*)|(\s*$)/g, "")==""){
		layer.msg("请选择有效期");
		return false;
	}
	if(uuid!=""){
		$.ajax({
	        url: '../back/banner/edit',
	        type: 'post',
	        async:false,
	        data: $("form").serialize()+"&uuid="+uuid+"&endtime="+$("#endtime").val().substring(0,19)+'_'+$("#endtime").val().substring(22)
	    }).done(function(r) {
	    		if (r.status == "SUCCESS") {
	    			layer.msg('编辑成功', {
	    				time: 1000 
	    			}, function(){
	    				window.location.href='bannerSecondList.html?type='+url('?type')+'&pagesize='+pagesize+'&pagenum='+pagenum
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
	        url: '../back/banner/add',
	        type: 'post',
	        async:false,
	        data: $("form").serialize()+"&endtime="+$("#endtime").val().substring(0,19)+'_'+$("#endtime").val().substring(22)
	    }).done(function(r) {
	    		if (r.status == "SUCCESS") {
	    			layer.msg('添加成功', {
	    				time: 1000 
	    			}, function(){
	    				window.location.href='bannerSecondList.html?type='+url('?type')+'&pagesize='+pagesize+'&pagenum=1'
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
