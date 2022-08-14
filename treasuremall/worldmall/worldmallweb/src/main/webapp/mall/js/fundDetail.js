var uuid = (url('?uuid') != null) ? url('?uuid') : '';
var pagesize = (url('?pagesize') != null) ? url('?pagesize') : 50;
var pagenum = (url('?pagenum') != null) ? url('?pagenum') : 1;
layui.use(['form', 'layedit', 'laydate','upload','element'], function(){
	var form = layui.form
	,layer = layui.layer
	,layedit = layui.layedit
	,laydate = layui.laydate
	,$ = layui.jquery
	,upload = layui.upload
	,element = layui.element;
	form.on('select(country)',function(data){
      	$("input[name='countryCode']").val($(data.elem).find("option:selected").attr("data-value"))
    });
	form.on('select(versionChannel)',function(data){
		var info = $(data.elem).find("option:selected").attr("data-value");
	  	$("input[name='channel']").val(info.substring(0,info.lastIndexOf('-')))
		$("input[name='version']").val(info.substring(info.lastIndexOf('-')+1))
	});

})	
	$(function(){
		countryList();
		versionList();
		if(uuid!=""){
			$.ajax({
				url: '../back/control/get',
				type: 'get',
				async:false,
				data:{
					'uuid':uuid
				}
			}).done(function(r) {
					if (r.status == "SUCCESS") {
						 $("select[name='country']").val(r.data.internationalInfo)
						 $("select[name='versionChannel']").val(r.data.version)
						 $("input[name='countryCode']").val(r.data.code)
						 $("input[name='channel']").val(r.data.channel)
						 $("input[name='version']").val(r.data.versionName)
						 if(r.data.flagWithdraw==true){
						 	$("input[name=isDefault][value='true']").prop("checked",true);
						 }else{
						 	$("input[name=isDefault][value='false']").prop("checked",true);
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
	})
	// 获取国家列表
	function countryList(){
		$.ajax({
		    url: '../back/country/list',
		    type: 'get',
		    async:false,
		    data:{
				'status':'normal',
		    	'pageSize':'10000',
		    	'pageNum':'1'
		    }
		}).done(function(r) {
				if (r.status == "SUCCESS") {
					var html = '<option value="">全部</option>';
					for(var i = 0;i<r.data.length;i++){
						html += '<option data-value="'+r.data[i].code+'" value="'+r.data[i].uuid+'">'+ r.data[i].name+'</option>';
					}
					$("select[name='country']").html(html);
				} else {
					
				}
		}).fail(function(r) {
		    
		}); 
	}
	// 获取渠道版本列表
	function versionList(){
		$.ajax({
		    url: '../back/version/list',
		    type: 'get',
		    async:false,
		    data:{
				'status':'normal',
		    	'pageSize':'10000',
		    	'pageNum':'1'
		    }
		}).done(function(r) {
				if (r.status == "SUCCESS") {
					var html = '<option value="">全部</option>';
					for(var i = 0;i<r.data.length;i++){
						html += '<option data-value="'+r.data[i].channel+'-'+r.data[i].name+'" value="'+r.data[i].uuid+'">'+r.data[i].channel+'-'+r.data[i].name+'</option>';
					}
					$("select[name='versionChannel']").html(html);
				} else {
					
				}
		}).fail(function(r) {
		    
		}); 
	}

$(".lay-submit").click(function(){
	if($("select[name='country']").val().replace(/(^\s*)|(\s*$)/g, "")==""){
		layer.msg("请选择国家");
		return false;
	}
	if($("select[name='versionChannel']").val().replace(/(^\s*)|(\s*$)/g, "")==""){
		layer.msg("请选择渠道版本");
		return false;
	}
	if(uuid!=""){
		$.ajax({
	        url: '../back/control/edit',
	        type: 'post',
	        async:false,
	        data: {
				'internationalInfo':$("select[name='country']").val(),
				'version':$("select[name='versionChannel']").val(),
				'flagWithdraw':$('input[name="isDefault"]:checked').val(),
				'uuid':uuid
			}
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
	        url: '../back/control/add',
	        type: 'post',
	        async:false,
	        data: {
				'internationalInfo':$("select[name='country']").val(),
				'version':$("select[name='versionChannel']").val(),
				'flagWithdraw':$('input[name="isDefault"]:checked').val()
			}
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
