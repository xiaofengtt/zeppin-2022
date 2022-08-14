/**
 * 
 */
var uuid = (url('?uuid') != null) ? url('?uuid') : '';
var company = (url('?company') != null) ? url('?company') : '';
var channel = (url('?channel') != null) ? url('?channel') : '';
var poundageType = 'static';
var channelAccountSelect;

$(function(){
	$('input[name=company]').val(company);
	if(uuid != ''){
		$("select[name=channel]").attr("disabled",true);
	}
	getCompany();
	channelList();
	
	$("input[name=poundage]").blur(function(){
		var poundage = Number($("input[name=poundage]").val());
		$("input[name=poundage]").val(poundage.toFixed(2));
	});
	
	$("input[name=max]").blur(function(){
		var max = Number($("input[name=max]").val());
		$("input[name=max]").val(max.toFixed(2));
	});
	
	$("input[name=min]").blur(function(){
		var min = Number($("input[name=min]").val());
		$("input[name=min]").val(min.toFixed(2));
	});

})
layui.use(['form','element'], function(){
	var form = layui.form
	,layer = layui.layer
	,$ = layui.jquery
	,element = layui.element;
	
	form.on('select(channel)',function(data){
		$("select[name=channel]").val(data.value)
		
		$.ajax({
		    url: '../system/channelAccount/list',
		    type: 'get',
		    async: true,
		    data:{
		    	'channel': data.value,
		    	'pageSize':1000,
		    	'pageNum':1
		    }
		}).done(function(r) {
			if (r.status == "SUCCESS") {
				var channelAccountDataArr = r.data;
				var channelAccountDataList = []
				for(var i=0;i<channelAccountDataArr.length;i++){
					var channelAccountData = {
						'name': channelAccountDataArr[i].name,
						'value': channelAccountDataArr[i].uuid
					}
					channelAccountDataList[i] = channelAccountData;
				}
				channelAccountSelect = xmSelect.render({
					el: '#channelAccountDiv', 
					data: channelAccountDataList
				})
			}
		})
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
	
	$.ajax({
	    url: '../system/channelAccount/list',
	    type: 'get',
	    async: true,
	    data:{
	    	'channel': channel,
	    	'pageSize':1000,
	    	'pageNum':1
	    }
	}).done(function(r) {
		if (r.status == "SUCCESS") {
			var channelAccountDataArr = r.data;
			
			//初始赋值
			if(uuid!=""){
				//编辑
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
			    		datas['max'] = (r.data['max']/100).toFixed(2)
			    		datas['min'] = (r.data['min']/100).toFixed(2)
		    			form.val('first',datas);
			    		
			    		var channelAccountSelectList = r.data.companyChannelAccountList;
			    		var channelAccountDataList = []
						for(var i=0;i<channelAccountDataArr.length;i++){
							var flagSelect = false;
							for(var channelAccountSelectIndex in channelAccountSelectList){
								if(channelAccountSelectList[channelAccountSelectIndex] == channelAccountDataArr[i].uuid){
									flagSelect = true;
									break;
								}
							}
							var channelAccountData = {
								'name': channelAccountDataArr[i].name,
								'value': channelAccountDataArr[i].uuid
							}
							if(flagSelect){
								channelAccountData['selected'] = true
							}
							channelAccountDataList[i] = channelAccountData;
						}
						channelAccountSelect = xmSelect.render({
							el: '#channelAccountDiv', 
							data: channelAccountDataList
						})
						
		    			if(r.data.poundage!=null){
		    				$("input[name=poundageType][value='static']").attr("checked", true);
		        			$("input[name=poundageType][value='rate']").attr("checked", false);
		    				$("input[name=poundage]").val((r.data.poundage/100).toFixed(2));
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
		    		        	parent.location.href="login.html";
		    		        });
		    			}else{
		    				layer.msg(r.message);
		    			}
		    		}
			    }).fail(function(r) {
			        
			    }); 
			}else{
				//添加
				//商品类型
				var channelAccountDataList = []
				for(var i=0;i<channelAccountDataArr.length;i++){
					var channelAccountData = {
						'name': channelAccountDataArr[i].name,
						'value': channelAccountDataArr[i].uuid
					}
					channelAccountDataList[i] = channelAccountData;
				}
				channelAccountSelect = xmSelect.render({
					el: '#channelAccountDiv', 
					data: channelAccountDataList
				})
			}
		}
	})
	
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
		
		var channelAccountArr = channelAccountSelect.getValue();
		var channelAccountData = [];
		for(var i=0;i<channelAccountArr.length;i++){
			channelAccountData.push(channelAccountArr[i].value);
		}
		
		var poundage, poundageRate;
		if($("input[name=poundage]").val() != ""){
			poundage = $("input[name=poundage]").val() * 100;
		}else{
			poundageRate = $("input[name=poundageRate]").val();
		}
		
		if(uuid!=""){
			$.ajax({
		        url: '../system/companyChannel/edit',
		        type: 'post',
		        async:false,
		        traditional: true,
		        data: {
		        	'uuid': uuid,
		        	'company': company,
		        	'channel': $("select[name=channel]").val(),
		        	'max': $("input[name=max]").val() * 100,
		        	'min': $("input[name=min]").val() * 100,
		        	'channelAccount': uniq(channelAccountData),
		        	'status': $("input[name=status]").val(),
		        	'poundage': poundage,
		        	'poundageRate' :poundageRate
		        }
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
		        url: '../system/companyChannel/add',
		        type: 'post',
		        async:false,
		        traditional: true,
		        data: {
		        	'company': company,
		        	'channel': $("select[name=channel]").val(),
		        	'max': $("input[name=max]").val() * 100,
		        	'min': $("input[name=min]").val() * 100,
		        	'channelAccount': uniq(channelAccountData),
		        	'status': $("input[name=status]").val(),
		        	'poundage': poundage,
		        	'poundageRate' :poundageRate
		        }
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