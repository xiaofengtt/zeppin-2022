var uuid = (url('?uuid') != null) ? url('?uuid') : '';
var pagesize = (url('?pagesize') != null) ? url('?pagesize') : 50;
var pagenum = (url('?pagenum') != null) ? url('?pagenum') : 1;
var goodsSelect;
var goodsTypeSelect;

$(function(){
	
})

layui.use(['form', 'laydate', 'element'], function(){
	var form = layui.form
	,layer = layui.layer
	,laydate = layui.laydate
	,$ = layui.jquery
	,element = layui.element;
	
	laydate.render({
		elem: '#staticStarttime',
		type: 'datetime',
		theme: '#3D99FF'
	});
	laydate.render({
		elem: '#staticEndtime',
		type: 'datetime',
		theme: '#3D99FF'
	});
	
	$.ajax({
	    url: '../back/goodsType/list',
	    type: 'get',
	    async: true,
	    data:{
	    	'pageSize':1000,
	    	'pageNum':1
	    }
	}).done(function(r) {
		var goodsTypeDataArr = r.data;
		$.ajax({
		    url: '../back/goods/list',
		    type: 'get',
		    async: true,
		    data:{
		    	'pageSize':1000,
		    	'pageNum':1
		    }
		}).done(function(r) {
			if (r.status == "SUCCESS") {
				//初始赋值
				if(uuid!=""){
					//编辑
					var goodsDataArr = r.data;
					$.ajax({
				        url: '../back/voucher/get',
				        type: 'get',
				        async:false,
				        data:{
				        	'uuid':uuid
				        }
				    }).done(function(r) {
				    	if (r.status == "SUCCESS") {
			    			form.val('first',r.data);
			    			if(r.data.starttime!=null){
			    				if(r.data.starttime.length > 10){
			    					$("input[name=staticStarttime]").val(formatDate(r.data.starttime));
			    				}else{
			    					$("input[name=dynamicStarttime]").val(r.data.starttime);
			    				}
			    			}
			    			if(r.data.endtime!=null){
			    				if(r.data.endtime.length > 10){
			    					$("input[name=staticEndtime]").val(formatDate(r.data.endtime));
			    				}else{
			    					$("input[name=dynamicEndtime]").val(r.data.endtime);
			    				}
			    			}
			    			var goodsSelectList = [];
			    			if(r.data.goods != null){
			    				goodsSelectList = r.data.goods.split(',');
							}
			    			var goodsDataList = []
							for(var i=0;i<goodsDataArr.length;i++){
								var flagSelect = false;
								for(var goodsSelectIndex in goodsSelectList){
									if(goodsSelectList[goodsSelectIndex] == goodsDataArr[i].uuid){
										flagSelect = true;
										break;
									}
								}
								var goodsData = {
									'name': goodsDataArr[i].name,
									'value': goodsDataArr[i].uuid
								}
								if(flagSelect){
									goodsData['selected'] = true
								}
								goodsDataList[i] = goodsData;
							}
							goodsSelect = xmSelect.render({
								el: '#goodsDiv', 
								data: goodsDataList
							})
							var goodsTypeSelectList = []
							if(r.data.goodsType != null){
								goodsTypeSelectList = r.data.goodsType.split(',');
							}
			    			var goodsTypeDataList = []
							for(var i=0;i<goodsTypeDataArr.length;i++){
								var flagSelect = false;
								for(var goodsTypeSelectIndex in goodsTypeSelectList){
									if(goodsTypeSelectList[goodsTypeSelectIndex] == goodsTypeDataArr[i].code){
										flagSelect = true;
										break;
									}
								}
								var goodsTypeData = {
									'name': goodsTypeDataArr[i].name,
									'value': goodsTypeDataArr[i].code
								}
								if(flagSelect){
									goodsTypeData['selected'] = true
								}
								goodsTypeDataList[i] = goodsTypeData;
							}
							goodsTypeSelect = xmSelect.render({
								el: '#goodsTypeDiv', 
								data: goodsTypeDataList
							})
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
				}else{
					//添加
					//商品类型
					var goodsTypeDataList = []
					for(var i=0;i<goodsTypeDataArr.length;i++){
						var goodsTypeData = {
							'name': goodsTypeDataArr[i].name,
							'value': goodsTypeDataArr[i].code
						}
						goodsTypeDataList[i] = goodsTypeData;
					}
					goodsTypeSelect = xmSelect.render({
						el: '#goodsTypeDiv', 
						data: goodsTypeDataList
					})
					//商品
					var goodsDataList = []
					for(var i=0;i<r.data.length;i++){
						var goodsData = {
							'name': r.data[i].name,
							'value': r.data[i].uuid
						}
						goodsDataList[i] = goodsData;
					}
					goodsSelect = xmSelect.render({
						el: '#goodsDiv', 
						data: goodsDataList
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
	}).fail(function(r) {
	    
	});
	
	
	$(".lay-submit").click(function(){
		if($("input[name=title]").val().replace(/(^\s*)|(\s*$)/g, "")==""){
			layer.msg("请填写金券名称");
			return false;
		}
		if($("input[name=dAmount]").val().replace(/(^\s*)|(\s*$)/g, "")==""){
			layer.msg("请填写金券面值");
			return false;
		}
		if($("input[name=payMin]").val().replace(/(^\s*)|(\s*$)/g, "")==""){
			layer.msg("请填写使用最低限额");
			return false;
		}
		
		//有效期限
		var starttime='', endtime='';
		var staticStarttime = $("input[name=staticStarttime]").val().replace(/(^\s*)|(\s*$)/g, "");
		var staticEndtime = $("input[name=staticEndtime]").val().replace(/(^\s*)|(\s*$)/g, "");
		var dynamicStarttime = $("input[name=dynamicStarttime]").val().replace(/(^\s*)|(\s*$)/g, "");
		var dynamicEndtime = $("input[name=dynamicEndtime]").val().replace(/(^\s*)|(\s*$)/g, "");
		if(staticStarttime != '' && dynamicStarttime != ''){
			layer.msg("开始时间只能选择一种有效期");
			return false;
		}
		if(staticEndtime != '' && dynamicEndtime != ''){
			layer.msg("结束时间只能选择一种有效期");
			return false;
		}
		if(staticStarttime != ''){
			starttime = staticStarttime;
		}
		if(dynamicStarttime != ''){
			starttime = dynamicStarttime;
		}
		if(staticEndtime != ''){
			endtime = staticEndtime;
		}
		if(dynamicEndtime != ''){
			endtime = dynamicEndtime;
		}
		
		if(staticStarttime != '' && staticEndtime != '' && staticStarttime > staticEndtime){
			layer.msg("开始时间不能大于结束时间");
			return false;
		}
		if(dynamicStarttime!='' && !$.isNumeric(dynamicStarttime)){
			layer.msg("开始时间格式错误");
			return false;
		}
		if(dynamicEndtime!='' && !$.isNumeric(dynamicEndtime)){
			layer.msg("结束时间格式错误");
			return false;
		}
		if($.isNumeric(dynamicStarttime) && $.isNumeric(dynamicEndtime) && parseInt(dynamicStarttime) > parseInt(dynamicEndtime)){
			layer.msg("开始时间不能大于结束时间");
			return false;
		}
		
		//商品类型限制
		var goodsTypeArr = goodsTypeSelect.getValue();
		var goodsTypeData = ''
		for(var i=0;i<goodsTypeArr.length;i++){
			goodsTypeData = goodsTypeData + goodsTypeArr[i].value + ','
		}
		if(goodsTypeData != ''){
			goodsTypeData = goodsTypeData.substring(0,goodsTypeData.length-1);
		}
		
		//商品限制
		var goodsArr = goodsSelect.getValue();
		var goodsData = ''
		for(var i=0;i<goodsArr.length;i++){
			goodsData = goodsData + goodsArr[i].value + ','
		}
		if(goodsData != ''){
			goodsData = goodsData.substring(0,goodsData.length-1);
		}
		
		var dataObj = {
			'title': $("input[name=title]").val().replace(/(^\s*)|(\s*$)/g, ""),
			'dAmount': $("input[name=dAmount]").val().replace(/(^\s*)|(\s*$)/g, ""),
			'payMin': $("input[name=payMin]").val().replace(/(^\s*)|(\s*$)/g, ""),
			'discription': $("textarea[name=discription]").val().replace(/(^\s*)|(\s*$)/g, "")
		};
		if(starttime!=''){
			dataObj['starttime'] = starttime
		}
		if(endtime!=''){
			dataObj['endtime'] = endtime
		}
		if(goodsTypeData!=''){
			dataObj['goodsType'] = goodsTypeData
		}
		if(goodsData!=''){
			dataObj['goods'] = goodsData
		}
		if(uuid!=""){
			dataObj['uuid'] = uuid;
			$.ajax({
		        url: '../back/voucher/edit',
		        type: 'post',
		        async:false,
		        data: dataObj
		    }).done(function(r) {
	    		if (r.status == "SUCCESS") {
	    			layer.msg('编辑成功', {
	    				time: 1000 
	    			}, function(){
	    				window.location.href='voucherList.html?pagesize='+pagesize+'&pagenum='+pagenum
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
		        url: '../back/voucher/add',
		        type: 'post',
		        async:false,
		        data: dataObj
		    }).done(function(r) {
	    		if (r.status == "SUCCESS") {
	    			layer.msg('添加成功', {
	    				time: 1000 
	    			}, function(){
	    				window.location.href='voucherList.html?pagesize='+pagesize+'&pagenum=1'
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
