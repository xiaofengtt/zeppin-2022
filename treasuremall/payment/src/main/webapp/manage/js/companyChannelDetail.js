/**
 * 渠道编辑
 */
var uuid = (url('?uuid') != null) ? url('?uuid') : '';
var company = (url('?company') != null) ? url('?company') : '';
var poundageType = 'static';
$(function(){
	$('input[name=company]').val(company);
	if(uuid != ''){
		$("select[name=channel]").attr("disabled",true);
	}
	getCompany();
	channelList();
})
layui.use(['form','element'], function(){
	var form = layui.form
	,layer = layui.layer
	,$ = layui.jquery
	,element = layui.element;
	
	form.on('select(channel)',function(data){
		$("select[name=channel]").val(data.value)
	})
	
	form.on('radio(poundageType)',function(data){
		poundageType = data.value;
		if(poundageType == 'static') { 
			$("input[name=poundage]").attr('disabled',false);
			$("input[name=poundageRate]").attr('disabled',true).val('');
		} else if(poundageType == 'rate') { 
			$("input[name=poundage]").attr('disabled',true).val('');
			$("input[name=poundageRate]").attr('disabled',false);
		} 
	})
	
	//初始赋值
	if(uuid!=""){
		$.ajax({
	        url: '../system/companyChannel/get',
	        type: 'get',
	        async:false,
	        data:{
	        	'uuid':uuid
	        }
	    }).done(function(r) {
	    	if (r.status == "SUCCESS") {
	    		var datas = r.data;
	    		datas['max'] = r.data['max']/100
	    		datas['min'] = r.data['min']/100
    			form.val('first',datas);
    			if(r.data.poundage!=null){
    				$("input[name=poundageType][value='static']").attr("checked", true);
        			$("input[name=poundageType][value='rate']").attr("checked", false);
    				$("input[name=poundage]").val(r.data.poundage/100);
    			}else if(r.data.poundageRate!=null){
    				poundageType = 'rate';
    				$("input[name=poundage]").attr('disabled',true);
    				$("input[name=poundageRate]").attr('disabled',false);
    				$("input[name=poundageType][value='static']").attr("checked", false);
        			$("input[name=poundageType][value='rate']").attr("checked", true);
    				$("input[name=poundageRate]").val(r.data.poundageRate);
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
	
	$(".lay-submit").click(function(){
		if($("select[name=channel]").val() == undefined || $("select[name=channel]").val()==""){
			layer.msg("请选择渠道");
			return false;
		}
		if(!isNumber($("input[name=min]").val())){
			layer.msg("请填写单次最小限额");
			return false;
		}
		if(!isNumber($("input[name=max]").val())){
			layer.msg("请填写单次最大限额");
			return false;
		}
		if(!isNumber($("input[name=poundage]").val()) && !isNumber($("input[name=poundageRate]").val())){
			layer.msg("请填写手续费");
			return false;
		}
		
		var dataMap = {};
		dataMap['company'] = company;
		dataMap['channel'] = $("select[name=channel]").val()
		dataMap['max'] = $("input[name=max]").val() * 100;
		dataMap['min'] = $("input[name=min]").val() * 100;
		if($("input[name=poundage]").val() != ""){
			dataMap['poundage'] = $("input[name=poundage]").val() * 100;
		}else{
			dataMap['poundageRate'] = $("input[name=poundageRate]").val();
		}
		dataMap['status'] = $("input[name=status]").val()
		
		if(uuid!=""){
			dataMap['uuid'] = uuid;
			$.ajax({
		        url: '../system/companyChannel/edit',
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
		        url: '../system/companyChannel/add',
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
//获取商户
function getCompany(){
	$.ajax({
        url: '../system/company/get',
        type: 'get',
        async:false,
        data: {
        	'uuid': company
        }
    }).done(function(r) {
    	if (r.status == "SUCCESS") {
    		$("input[name='companyName']").val(r.data.name);
    	}
    })
}
//渠道列表
function channelList(){
	$.ajax({
        url: '../system/channel/list',
        type: 'get',
        async:false,
        data: {
        	'pageSize':'1000',
        	'pageNum':1
        }
    }).done(function(r) {
		if (r.status == "SUCCESS") {
			
			var tableHtml = '<option value="">请选择</option>';
			for(var i=0;i<r.data.length;i++){
				tableHtml += '<option value="'+r.data[i].uuid+'">'+r.data[i].name+'</option>';
			}
			$("select[name='channel']").html(tableHtml);
		} 
    })
}