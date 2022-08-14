var uuid = (url('?uuid') != null) ? url('?uuid') : '';
var pagesize = (url('?pagesize') != null) ? url('?pagesize') : 50;
var pagenum = (url('?pagenum') != null) ? url('?pagenum') : 1;
var explanationEdit = null;
$(function(){
	$(".goPrePage").click(function(){
		window.location.href='capitalPlatformList.html?pagesize='+pagesize+'&pagenum='+pagenum
	});
	if(uuid != ''){
		$("select[name=type]").attr("disabled",true);
		$("select[name=transType]").attr("disabled",true);
	}
})

layui.use(['form', 'layedit', 'laydate','upload','element'], function(){
	var form = layui.form
	,layer = layui.layer
	,layedit = layui.layedit
	,laydate = layui.laydate
	,$ = layui.jquery
	,upload = layui.upload
	,element = layui.element;
	
	form.on('select(type)',function(data){
		$("select[name=type]").val(data.value)
	})
	
	form.on('select(transType)',function(data){
		$("select[name=transType]").val(data.value)
	})
  
	//初始赋值
	if(uuid!=""){
		$.ajax({
	        url: '../back/capitalPlatform/get',
	        type: 'get',
	        async:false,
	        data:{
	        	'uuid':uuid
	        }
	    }).done(function(r) {
	    	if (r.status == "SUCCESS") {
    			form.val('first',r.data);
    			if(r.data.isRecommend==true){
	   				$("input[name=isRecommend][value='true']").prop("checked",true);
	   			}else{
	   				$("input[name=isRecommend][value='false']").prop("checked",true);
	   			}
    			if(r.data.isRandomAmount==true){
	   				$("input[name=isRandomAmount][value='true']").prop("checked",true);
	   			}else{
	   				$("input[name=isRandomAmount][value='false']").prop("checked",true);
	   			}
    			if(r.data.isUniqueAmount==true){
	   				$("input[name=isUniqueAmount][value='true']").prop("checked",true);
	   			}else{
	   				$("input[name=isUniqueAmount][value='false']").prop("checked",true);
	   			}
    			form.render();
    			$('#explanation').val(r.data.explanation);
    			explanationEdit = layedit.build('explanation', {
    				 tool: ['strong','italic','underline','del','|','left','center','right','link','unlink','face']
				});
    			$('#logoUpload').attr('src','..' + r.data.logoUrl);
    			$('#explanImgUpload').attr('src','..' + r.data.explanImgUrl);
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
	}else{
		explanationEdit = layedit.build('explanation', {
			 tool: ['strong','italic','underline','del','|','left','center','right','link','unlink','face']
		});
	}
	upload.render({
		elem: '#uploadLogo',
		url: '../back/resource/add',
		done: function(res){
			$('input[name="logo"]').val(res.data.uuid);
			$('#logoUpload').attr('src','..' + res.data.url);
			$("#logoImages").show();
		}
	});
	upload.render({
		elem: '#uploadExplanImg',
		url: '../back/resource/add',
		done: function(res){
			$('input[name="explanImg"]').val(res.data.uuid);
			$('#explanImgUpload').attr('src','..' + res.data.url);
			$("#explanImgImages").show();
		}
	});
	$(".lay-submit").click(function(){
		$("[name=explanation]").val(layedit.getContent(explanationEdit))
		if($("input[name=name]").val().replace(/(^\s*)|(\s*$)/g, "")==""){
			layer.msg("请填写渠道名称");
			return false;
		}
		if($("select[name=transType]").val() == undefined || $("select[name=transType]").val()==""){
			layer.msg("请选择支付方式");
			return false;
		}
		if($("select[name=type]").val() == undefined || $("input[name=type]").val()==""){
			layer.msg("请选择支付类型");
			return false;
		}
		if($("input[name=min]").val().replace(/(^\s*)|(\s*$)/g, "")==""){
			layer.msg("请填写最小充值额度");
			return false;
		}
		if($("input[name=max]").val().replace(/(^\s*)|(\s*$)/g, "")==""){
			layer.msg("请填写最大充值额度");
			return false;
		}
		if($("input[name=logo]").val()==""){
			layer.msg("请选上传logo");
			return false;
		}
		if($("input[name=explanImg]").val()==""){
			layer.msg("请选上传说明图");
			return false;
		}
		if($("input[name=explanation]").val().replace(/(^\s*)|(\s*$)/g, "")==""){
			layer.msg("请填写用户须知");
			return false;
		}
		if($("textarea[name=remark]").val().replace(/(^\s*)|(\s*$)/g, "")==""){
			layer.msg("请填写描述");
			return false;
		}
		if($("input[name=sort]").val().replace(/(^\s*)|(\s*$)/g, "")==""){
			layer.msg("请填写排序参数");
			return false;
		}
		if(uuid!=""){
			$.ajax({
		        url: '../back/capitalPlatform/edit',
		        type: 'post',
		        async:false,
		        data: $("form").serialize()+"&uuid="+uuid
		    }).done(function(r) {
	    		if (r.status == "SUCCESS") {
	    			layer.msg('编辑成功', {
	    				time: 1000 
	    			}, function(){
	    				window.location.href='capitalPlatformList.html?pagesize='+pagesize+'&pagenum='+pagenum
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
		        url: '../back/capitalPlatform/add',
		        type: 'post',
		        async:false,
		        data: $("form").serialize()
		    }).done(function(r) {
		    		if (r.status == "SUCCESS") {
		    			layer.msg('添加成功', {
		    				time: 1000 
		    			}, function(){
		    				window.location.href='capitalPlatformList.html?pagesize='+pagesize+'&pagenum=1'
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
