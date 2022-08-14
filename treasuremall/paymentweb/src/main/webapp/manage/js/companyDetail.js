/**
 * 商户编辑
 */
var uuid = (url('?uuid') != null) ? url('?uuid') : '';

$(function(){
	
})
layui.use(['form','element'], function(){
	var form = layui.form
	,layer = layui.layer
	,$ = layui.jquery
	,element = layui.element;
	
	$("input[name=rechargePoundage]").blur(function(){
		var rechargePoundage = Number($("input[name=rechargePoundage]").val());
		$("input[name=rechargePoundage]").val(rechargePoundage.toFixed(2));
	});
	
	$("input[name=withdrawPoundage]").blur(function(){
		var withdrawPoundage = Number($("input[name=withdrawPoundage]").val());
		$("input[name=withdrawPoundage]").val(withdrawPoundage.toFixed(2));
	});
	
	form.on('radio(rechargePoundageType)',function(data){
		rechargePoundageType = data.value;
		if(rechargePoundageType == 'static') { 
			$("input[name=rechargePoundage]").attr('disabled',false);
			$("input[name=rechargePoundageRate]").attr('disabled',true).val('');
		} else if(rechargePoundageType == 'rate') { 
			$("input[name=rechargePoundage]").attr('disabled',true).val('');
			$("input[name=rechargePoundageRate]").attr('disabled',false);
		} 
	})
	
	form.on('radio(withdrawPoundageType)',function(data){
		withdrawPoundageType = data.value;
		if(withdrawPoundageType == 'static') { 
			$("input[name=withdrawPoundage]").attr('disabled',false);
			$("input[name=withdrawPoundageRate]").attr('disabled',true).val('');
		} else if(withdrawPoundageType == 'rate') { 
			$("input[name=withdrawPoundage]").attr('disabled',true).val('');
			$("input[name=withdrawPoundageRate]").attr('disabled',false);
		} 
	})
	
	//初始赋值
	if(uuid!=""){
		$.ajax({
	        url: '../system/company/get',
	        type: 'get',
	        async:false,
	        data:{
	        	'uuid':uuid
	        }
	    }).done(function(r) {
	    	if (r.status == "SUCCESS") {
    			form.val('first',r.data);
    			if(r.data.rechargePoundage!=null){
    				$("input[name=rechargePoundageType][value='static']").attr("checked", true);
        			$("input[name=rechargePoundageType][value='rate']").attr("checked", false);
    				$("input[name=rechargePoundage]").val((r.data.rechargePoundage/100).toFixed(2));
    			}else if(r.data.rechargePoundageRate!=null){
    				rechargePoundageType = 'rate';
    				$("input[name=rechargePoundage]").attr('disabled',true);
    				$("input[name=rechargePoundageRate]").attr('disabled',false);
    				$("input[name=rechargePoundageType][value='static']").attr("checked", false);
        			$("input[name=rechargePoundageType][value='rate']").attr("checked", true);
    				$("input[name=rechargePoundageRate]").val(r.data.withdrawPoundageRate);
    			}
    			if(r.data.withdrawPoundage!=null){
    				$("input[name=withdrawPoundageType][value='static']").attr("checked", true);
        			$("input[name=withdrawPoundageType][value='rate']").attr("checked", false);
    				$("input[name=withdrawPoundage]").val((r.data.withdrawPoundage/100).toFixed(2));
    			}else if(r.data.withdrawPoundageRate!=null){
    				withdrawPoundageType = 'rate';
    				$("input[name=withdrawPoundage]").attr('disabled',true);
    				$("input[name=withdrawPoundageRate]").attr('disabled',false);
    				$("input[name=withdrawPoundageType][value='static']").attr("checked", false);
        			$("input[name=withdrawPoundageType][value='rate']").attr("checked", true);
    				$("input[name=withdrawPoundageRate]").val(r.data.withdrawPoundageRate);
    			}
    			form.render();
    		} else {
    			if(r.errorCode=="302"){
    				layer.msg(r.message, {
    		            time: 2000
    		        },function(){
    		        	parent.location.href="login.html";
    		        });
    			}else{
    				layer.msg(r.message);
    			}
    		}
	    }).fail(function(r) {
	        
	    }); 
	}
	
	$(".lay-submit").click(function(){
		if($("input[name=name]").val()==""){
			layer.msg("请填写账户名称");
			return false;
		}
		if($("input[name=whiteUrl]").val()==""){
			layer.msg("请填写白名单");
			return false;
		}
		if(!isNumber($("input[name=rechargePoundage]").val()) && !isNumber($("input[name=rechargePoundageRate]").val())){
			layer.msg("请填写注资手续费");
			return false;
		}
		if(!isNumber($("input[name=withdrawPoundage]").val()) && !isNumber($("input[name=withdrawPoundageRate]").val())){
			layer.msg("请填写提现手续费");
			return false;
		}
		var dataMap = {};
		dataMap['name'] = $("input[name=name]").val()
		dataMap['whiteUrl'] = $("input[name=whiteUrl]").val()
		if($("textarea[name=companyPrivate]").val() != ""){
			dataMap['companyPrivate'] = $("textarea[name=companyPrivate]").val();
		}
		if($("textarea[name=companyPublic]").val() != ""){
			dataMap['companyPublic'] = $("textarea[name=companyPublic]").val();
		}
		if($("input[name=rechargePoundage]").val() != ""){
			dataMap['rechargePoundage'] = $("input[name=rechargePoundage]").val() * 100;
		}else{
			dataMap['rechargePoundageRate'] = $("input[name=rechargePoundageRate]").val();
		}
		if($("input[name=withdrawPoundage]").val() != ""){
			dataMap['withdrawPoundage'] = $("input[name=withdrawPoundage]").val() * 100;
		}else{
			dataMap['withdrawPoundageRate'] = $("input[name=withdrawPoundageRate]").val();
		}
		dataMap['status'] = $("input[name=status]").val()
		
		if(uuid!=""){
			dataMap['uuid'] = uuid;
			$.ajax({
		        url: '../system/company/edit',
		        type: 'post',
		        async:false,
		        data: dataMap
		    }).done(function(r) {
	    		if (r.status == "SUCCESS") {
	    			layer.msg('编辑成功', {
	    				time: 1000 
	    			}, function(){
	    				var index = parent.layer.getFrameIndex(window.name); 
	    				parent.getList();
	    				parent.layer.close(index);
	    			}); 
	    		} else {
	    			if(r.errorCode=="302"){
	    				layer.msg(r.message, {
	    		            time: 2000
	    		        },function(){
	    		        	parent.location.href="login.html";
	    		        });
	    			}else{
	    				layer.msg(r.message);
	    			}
	    		}
		    }).fail(function(r) {
		        layer.msg("error", {
		            time: 2000
		        });
		    }); 
		}else{			
			$.ajax({
		        url: '../system/company/add',
		        type: 'post',
		        async:false,
		        data: dataMap
		    }).done(function(r) {
		    		if (r.status == "SUCCESS") {
		    			layer.msg('添加成功', {
		    				time: 1000 
		    			}, function(){
		    				var index = parent.layer.getFrameIndex(window.name); 
		    				parent.getList();
		    				parent.layer.close(index);
		    			}); 
		    		} else {
		    			if(r.errorCode=="302"){
		    				layer.msg(r.message, {
		    		            time: 2000
		    		        },function(){
		    		        	parent.location.href="login.html";
		    		        });
		    			}else{
		    				layer.msg(r.message);
		    			}
		    		}
		    }).fail(function(r) {
		        layer.msg("error", {
		            time: 2000
		        });
		    }); 
		}
		return false;
	})
});