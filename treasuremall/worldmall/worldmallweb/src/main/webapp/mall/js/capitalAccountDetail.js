var uuid = (url('?uuid') != null) ? url('?uuid') : '';
var pagesize = (url('?pagesize') != null) ? url('?pagesize') : 50;
var pagenum = (url('?pagenum') != null) ? url('?pagenum') : 1;
var datasMap = {};

$(function(){
	$(".goPrePage").click(function(){
		window.location.href='capitalAccountList.html?pagesize='+pagesize+'&pagenum='+pagenum
	});
	if(uuid != ''){
		$("select[name=capitalPlatform]").attr("disabled",true);
	}
})

layui.use(['form', 'element','upload'], function(){
	var form = layui.form
	,layer = layui.layer
	,$ = layui.jquery
	,upload = layui.upload
	,element = layui.element;
	
	form.on('select(capitalPlatform)',function(data){
		$("select[name=capitalPlatform]").val(data.value)
	})
	$.ajax({
        url: '../back/capitalPlatform/list',
        type: 'get',
        async:false,
        data: {
        	'pageSize':1000,
        	'pageNum':1,
        	'sort':'sort',
        	'status': 'normal'
        }
    }).done(function(r) {
    	if (r.status == "SUCCESS") {
    		var optionHtml = '<option value="">请选择</option>'
    		for(var i=0;i<r.data.length;i++){
    			optionHtml = optionHtml + '<option value="'+r.data[i].uuid+'">'+r.data[i].name+'</option>';
    		}
    		$('select[name=capitalPlatform]').html(optionHtml)
    		form.render('select');
    		//初始赋值
    		if(uuid!=""){
    			$.ajax({
    		        url: '../back/capitalAccount/get',
    		        type: 'get',
    		        async:false,
    		        data:{
    		        	'uuid':uuid
    		        }
    		    }).done(function(r) {
    		    	if (r.status == "SUCCESS") {
    	    			form.val('first',r.data);
    	    			if(r.data.frontUserGroup){
    	    				frontUserArr = r.data.frontUserGroup.split(",");
	   	   	    			 $("input[name='frontUserGroups']").each(function(i,value){
	   	   	    				 var value = $(this).val();
	   	   		    			 for(var i=0;i<frontUserArr.length;i++){		    				 
	   	   	    					 if (frontUserArr[i] == value) {
	   	   	    			                $(this).next('div').click()
	   	   	    			          }
	   	   		    			 }
	   	   	    			 })
    	    			}
    	    			$('#logoUpload').attr('src','..' + r.data.logoUrl);
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
    			elem: '#uploadLogo',
    			url: '../back/resource/add',
    			done: function(res){
    				$('input[name="logo"]').val(res.data.uuid);
    				$('#logoUpload').attr('src','..' + res.data.url);
    				$("#logoImages").show();
    			}
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
        
    });
	$(".lay-submit").click(function(){
		if($("select[name=capitalPlatform]").val() == undefined || $("select[name=capitalPlatform]").val()==""){
			layer.msg("请选择所属渠道");
			return false;
		}
		if($("input[name=name]").val().replace(/(^\s*)|(\s*$)/g, "")==""){
			layer.msg("请填写账户名称");
			return false;
		}
		if($("input[name=accountNum]").val().replace(/(^\s*)|(\s*$)/g, "")==""){
			layer.msg("请填写账户账号");
			return false;
		}
		if($("input[name=logo]").val()==""){
			layer.msg("请选上传logo");
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
		if($("input[name=dailyMax]").val().replace(/(^\s*)|(\s*$)/g, "")==""){
			layer.msg("请填写每日限额");
			return false;
		}
		if($("input[name=totalMax]").val().replace(/(^\s*)|(\s*$)/g, "")==""){
			layer.msg("请填写总限额");
			return false;
		}
		if($("input[name=remark]").val().replace(/(^\s*)|(\s*$)/g, "")==""){
			layer.msg("请填写备注");
			return false;
		}
		if($("input[name=sort]").val().replace(/(^\s*)|(\s*$)/g, "")==""){
			layer.msg("请填写排序参数");
			return false;
		}
		var datas = $("form").serializeObject()
		if(datas['poundageRate'].replace(/(^\s*)|(\s*$)/g, "") == ''){
			delete datas.poundageRate;
		}
		if(datas['frontUserStatus'].replace(/(^\s*)|(\s*$)/g, "") == ''){
			delete datas.frontUserStatus;
		}
		if(datas['userPreReceive'].replace(/(^\s*)|(\s*$)/g, "") == ''){
			delete datas.userPreReceive;
		}
		var classid = [];
		$(".layui-form-checkbox").each(function(){
			var _taht = $(this);
			if(_taht.is(".layui-form-checked")){ 
				classid.push(_taht.prev("input").val()); 
			}
		});
		datas['frontUserGroup']=classid
		if(uuid!=""){
			datas['uuid'] = uuid;
			$.ajax({
		        url: '../back/capitalAccount/edit',
		        type: 'post',
		        async:false,
		        traditional : true,
		        data: datas
		    }).done(function(r) {
	    		if (r.status == "SUCCESS") {
	    			layer.msg('编辑成功', {
	    				time: 1000 
	    			}, function(){
	    				window.location.href='capitalAccountList.html?pagesize='+pagesize+'&pagenum='+pagenum
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
		        url: '../back/capitalAccount/add',
		        type: 'post',
		        async:false,
		        traditional : true,
		        data: datas
		    }).done(function(r) {
		    		if (r.status == "SUCCESS") {
		    			layer.msg('添加成功', {
		    				time: 1000 
		    			}, function(){
		    				window.location.href='capitalAccountList.html?pagesize='+pagesize+'&pagenum=1'
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
