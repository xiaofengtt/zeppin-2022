/**
 * 渠道账户
 */
var uuid = (url('?uuid') != null) ? url('?uuid') : '';
var poundageType = 'static';
var editData;
var datasMap = {};
var itemIndex = 0;
var uploadClass = '';
$(function(){
	if(uuid != ''){
		$("select[name=type]").attr("disabled",true);
		$("select[name=channel]").attr("disabled",true);
	}
	
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

	$("input[name=dailyMax]").blur(function(){
		var dailyMax = Number($("input[name=dailyMax]").val());
		$("input[name=dailyMax]").val(dailyMax.toFixed(2));
	});

	$("input[name=totalMax]").blur(function(){
		var totalMax = Number($("input[name=totalMax]").val());
		$("input[name=totalMax]").val(totalMax.toFixed(2));
	});
})
layui.use(['form','upload','element'], function(){
	var form = layui.form
	,layer = layui.layer
	,upload = layui.upload
	,$ = layui.jquery
	,element = layui.element;
	
	form.on('select(type)',function(data){
		$("select[name=type]").val(data.value);
		channelList(data.value);
	})
	
	form.on('select(channel)',function(data){
		$("select[name=channel]").val(data.value)
		var dataMap = datasMap[data.value];
		var datasHtml = '';
		if("a4ac649b-43f3-4f85-888c-dd85bbca25a6" == data.value || "9298fcd6-36ee-41b2-8c99-31a7334d93dc" == data.value){
			datasHtml = '<div class="layui-form-item"><label class="layui-form-label">通用收款码</label>'
				+'<div class="layui-input-block" style="font-size:0;display:flex"><div class="layui-upload"><button type="button" class="layui-btn" id="uploadNormal">上传收款码</button>'
				+'</div><input type="hidden" name="normalCode"/>'
				+'<a id="normalImages" title="点击查看大图" data-url="" onclick="showImage(this)" style="display:none">'
				+'<img style="width:128px;margin-left:20px;" src="" class="uploadNormalImg" /></a>'
				+'</div></div>';
			datasHtml += '<div class="layui-form-item"><label class="layui-form-label">定额收款码</label>'
				+'<div class="layui-input-block"><div class="quota-add"><p>添加其他定额</p></div><div class="clear"></div></div>'
				+'</div>';
		}else{
			for(var key in dataMap){
				dataHtml = '<div class="layui-form-item"><label class="layui-form-label">'+dataMap[key]
				+'</label><div class="layui-input-block"><input type="text" name="'+key
				+'" autocomplete="off" class="layui-input"></div></div>';
				datasHtml = datasHtml + dataHtml;
			}
		}
		$(".channel-datas").html(datasHtml);
		upload.render({
			elem: '#uploadNormal',
			url: '../system/resource/add',
			size:1024,
			accept: 'images',
			exts: 'jpg|jpeg|png',
			done: function(res){
				$('input[name="normalCode"]').val(res.data.uuid);
				$('#normalImages').attr('data-url',res.data.url).show();
				$('#normalImages img').attr('src','..' + res.data.url);
				$('#uploadNormal').html("更换收款码");
			}
		});
		form.render();
		$(".quota-add").click(function(){
			quotaAdd()
		})
		//定额收款码添加item
		function quotaAdd(){
			var html = '<div class="quota-item quota-item'+itemIndex+'">'
				+'<a class="delete-item"></a>'
				+'<div class="item-top"><label>金额</label><input type="number" name="" class="layui-input quota-money"></div>'
				+'<div class="item-top"><label>收款码</label><div class="layui-upload">'
				+'<button type="button" class="layui-btn" id="quota-upload">上传收款码</button>'
				+'<input type="hidden" name="image" value="" ></div></div>'
				+'<img class="goodsCover" src="" data-url="" onclick="showImage(this)" />'
				+'</div>';
			$(".quota-add").before(html)
			addUpload(itemIndex)
			$(".layui-upload button").click(function(){
				uploadClass = $(this).parent().parent().parent().attr('class')
				uploadClass = uploadClass.substring(uploadClass.indexOf(' ')+1)
			})
			$(".delete-item").click(function(){
				$(this).parent().remove()
			})
			$(".quota-money").on("input", function () {
				var obj = $(this).val()
				obj = obj.replace(/[^\d.]/g,"");  
				obj = obj.replace(/\.{2,}/g,".");   
				obj = obj.replace(".","$#$").replace(/\./g,"").replace("$#$","."); 
				obj = obj.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3');
			    if(obj.indexOf(".")< 0 && obj !=""){
			        obj= parseFloat(obj); 
			    } 
			    $(this).val(obj)
	        });
			inputBlur();
			itemIndex ++;
		}
		function inputBlur(){
			for(var i=0;i<$(".quota-item").length;i++){
				$(".quota-item:eq("+i+")").find(".quota-money").blur(function(){
					var poundage = Number($(this).val());
					$(this).val(poundage.toFixed(2));
				});
			}
		}
		function addUpload(index){
			upload.render({
			    elem: '.quota-item'+index+' #quota-upload'
			    ,url: '../system/resource/add' //改成您自己的上传接口
			    ,size: 1024
			    ,accept: 'images'
				,exts: 'jpg|jpeg|png'
			    ,before: function(obj){
			      //预读本地文件示例，不支持ie8
			    }
			    ,done: function(res){
			    	$('.'+uploadClass+' input[name="image"]').val(res.data.uuid);
			    	$('.'+uploadClass+' .goodsCover').attr('data-url',res.data.url);
			    	$('.'+uploadClass+' .goodsCover').attr('src','..' + res.data.url); 
			    	$('.'+uploadClass+' button').html("更换收款码");
			    }
			});
		}
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
	        url: '../system/channelAccount/get',
	        type: 'get',
	        async:false,
	        data:{
	        	'uuid':uuid
	        }
	    }).done(function(r) {
	    	if (r.status == "SUCCESS") {
	    		var datas = r.data;
	    		editData = r.data.dataMap;
	    		datas['max'] = (r.data['max']/100).toFixed(2)
	    		datas['min'] = (r.data['min']/100).toFixed(2)
	    		datas['dailyMax'] = (r.data['dailyMax']/100).toFixed(2)
	    		datas['totalMax'] = (r.data['totalMax']/100).toFixed(2)
    			form.val('first',datas);
	    		$("select[name=channel]").html('<option value="'+r.data.channel+'" checked>'+r.data.channelName+'</option>');
	    		getChannel(r.data.channel, r.data.dataMap);
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
	}
	
	$(".lay-submit").click(function(){
		if($("select[name=channel]").val() == undefined || $("select[name=channel]").val()==""){
			layer.msg("请选择渠道");
			return false;
		}
		if($("input[name=name]").val()==""){
			layer.msg("请填写账户名称");
			return false;
		}
		if($("input[name=accountNum]").val()==""){
			layer.msg("请填写账户号码");
			return false;
		}
		if($("select[name=channel]").val() == "a4ac649b-43f3-4f85-888c-dd85bbca25a6" && $('.channel-datas input[name="normalCode"]').val()==""){
			layer.msg("请上传通用收款码");
			return false;
		}
		if($("select[name=channel]").val() == "9298fcd6-36ee-41b2-8c99-31a7334d93dc" && $('.channel-datas input[name="normalCode"]').val()==""){
			layer.msg("请上传通用收款码");
			return false;
		}
		for(var i=0;i<$(".quota-item").length;i++){
			if($(".quota-item:eq("+i+")").find(".quota-money").val()==""||$(".quota-item:eq("+i+")").find(".quota-money").val()=="0.00"){
				layer.msg("定额收款码有金额为0的选项");
				return false;
			}else if($(".quota-item:eq("+i+")").find("input[name='image']").val()==""){
				layer.msg("定额收款码有未上传收款码的选项");
				return false;
			}
		}
		if($("input[name=transferUrl]").val()==""){
			layer.msg("请填写转发地址");
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
		if(!isNumber($("input[name=dailyMax]").val())){
			layer.msg("请填写每日限额");
			return false;
		}
		if(!isNumber($("input[name=totalMax]").val())){
			layer.msg("请填写总限额");
			return false;
		}
		if(!isNumber($("input[name=poundage]").val()) && !isNumber($("input[name=poundageRate]").val())){
			layer.msg("请填写手续费");
			return false;
		}
		
		var datas = {};
		if("a4ac649b-43f3-4f85-888c-dd85bbca25a6" == $("select[name=channel]").val() || "9298fcd6-36ee-41b2-8c99-31a7334d93dc" == $("select[name=channel]").val()){
			var normalData = $('.channel-datas input[name="normalCode"]').val();
			var fiexdObj = {}
			datas['normal'] = normalData
			for(var i=0;i<$(".quota-item").length;i++){
				fiexdObj[""+$(".quota-item:eq("+i+")").find(".quota-money").val()+""] = $(".quota-item:eq("+i+")").find("input[name='image']").val()
			}
			datas['fixed'] = fiexdObj
		}else{
			$(".channel-datas input").each(function(){
				datas[$(this).attr('name')] = $(this).val();
		    });
		}
		var dataMap = {};
		dataMap['channel'] = $("select[name=channel]").val()
		dataMap['name'] = $("input[name=name]").val()
		dataMap['accountNum'] = $("input[name=accountNum]").val()
		dataMap['data'] = JSON.stringify(datas);
		dataMap['transferUrl'] = $("input[name=transferUrl]").val()
		dataMap['max'] = $("input[name=max]").val() * 100;
		dataMap['min'] = $("input[name=min]").val() * 100;
		dataMap['dailyMax'] = $("input[name=dailyMax]").val() * 100;
		dataMap['totalMax'] = $("input[name=totalMax]").val() * 100;
		if($("input[name=poundage]").val() != ""){
			dataMap['poundage'] = $("input[name=poundage]").val() * 100;
		}else{
			dataMap['poundageRate'] = $("input[name=poundageRate]").val();
		}
		dataMap['status'] = $("input[name=status]").val()
		
		if(uuid!=""){
			dataMap['uuid'] = uuid;
			$.ajax({
		        url: '../system/channelAccount/edit',
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
		        url: '../system/channelAccount/add',
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
	
	//渠道列表
	function channelList(type){
		$.ajax({
	        url: '../system/channel/list',
	        type: 'get',
	        async:false,
	        data: {
	        	'type': type,
	        	'pageSize':'1000',
	        	'pageNum':1
	        }
	    }).done(function(r) {
			if (r.status == "SUCCESS") {
				datasMap = {};
				var tableHtml = '<option value="">请选择</option>';
				for(var i=0;i<r.data.length;i++){
					datasMap[r.data[i].uuid] = r.data[i].dataMap;
					tableHtml += '<option value="'+r.data[i].uuid+'">'+r.data[i].name+'</option>';
				}
				$("select[name='channel']").html(tableHtml);
				form.render();
			} 
	    })
	}
	
	//渠道列表
	function getChannel(uuid, datas){
		$.ajax({
	        url: '../system/channel/get',
	        type: 'get',
	        async:false,
	        data: {
	        	'uuid': uuid
	        }
	    }).done(function(r) {
			if (r.status == "SUCCESS") {
				datasMap[r.data.uuid] = r.data.dataMap;
				var dataMap = datasMap[r.data.uuid];
				var datasHtml = '';
				if("a4ac649b-43f3-4f85-888c-dd85bbca25a6" == r.data.uuid || "9298fcd6-36ee-41b2-8c99-31a7334d93dc" == r.data.uuid){
					var str = editData.normal;
					var html = '';
					datasHtml = '<div class="layui-form-item"><label class="layui-form-label">通用收款码</label>'
						+'<div class="layui-input-block" style="font-size:0;display:flex;"><div class="layui-upload"><button type="button" class="layui-btn" id="uploadNormal">更换收款码</button></div>'
						+'<a id="normalImages" title="点击查看大图" data-url="'+str.substring(str.indexOf('@')+1)+'" onclick="showImage(this)" style="display:inline-block">'
						+'<img style="width:128px;margin-left:20px;" src="'+str.substring(str.indexOf('@')+1)+'" class="uploadNormalImg" /></a>'
						+'<input type="hidden" name="normalCode" value="'+str.substring(0,str.indexOf('@'))+'" /></div></div>';
					for(var key in editData.fixed){
						html += '<div class="quota-item quota-item'+itemIndex+'">'
						+'<a class="delete-item"></a>'
						+'<div class="item-top"><label>金额</label><input type="number" name="" class="layui-input quota-money" value="'+key+'"></div>'
						+'<div class="item-top"><label>收款码</label><div class="layui-upload">'
						+'<button type="button" class="layui-btn" id="quota-upload">更换收款码</button>'
						+'<input type="hidden" name="image" value="'+editData.fixed[key].substring(0,editData.fixed[key].indexOf('@'))+'" ></div></div>'
						+'<img class="goodsCover" src="'+editData.fixed[key].substring(editData.fixed[key].indexOf('@')+1)
						+'" data-url="'+editData.fixed[key].substring(editData.fixed[key].indexOf('@')+1)+'" onclick="showImage(this)" />'
						+'</div>';
						itemIndex ++;
					}
					datasHtml += '<div class="layui-form-item"><label class="layui-form-label">定额收款码</label>'
						+'<div class="layui-input-block">'+html+'<div class="quota-add"><p>添加其他定额</p></div><div class="clear"></div></div>'
						+'</div>';
					$(".channel-datas").html(datasHtml);
//					$('input[name=normalCode]').val(datas['normal']);
					upload.render({
						elem: '#uploadNormal',
						url: '../system/resource/add',
						size:1024,
						accept: 'images',
						exts: 'jpg|jpeg|png',
						done: function(res){
							$('input[name="normalCode"]').val(res.data.uuid);
							$('#normalImages').attr('data-url',res.data.url).show();
							$('#normalImages img').attr('src','..' + res.data.url);
							$('#uploadNormal').html("更换收款码");
						}
					});
					for(var i=0;i<$(".quota-item").length;i++){
						addUpload(i)
					}
					$(".layui-upload button").click(function(){
						uploadClass = $(this).parent().parent().parent().attr('class')
						uploadClass = uploadClass.substring(uploadClass.indexOf(' ')+1)
					})
					$(".delete-item").click(function(){
						$(this).parent().remove()
					})
					$(".quota-money").on("input", function () {
						var obj = $(this).val()
						obj = obj.replace(/[^\d.]/g,"");  
						obj = obj.replace(/\.{2,}/g,".");   
						obj = obj.replace(".","$#$").replace(/\./g,"").replace("$#$","."); 
						obj = obj.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3');
					    if(obj.indexOf(".")< 0 && obj !=""){
					        obj= parseFloat(obj); 
					    } 
					    $(this).val(obj)
			        });
					inputBlur();
				}else{
					for(var key in dataMap){
						dataHtml = '<div class="layui-form-item"><label class="layui-form-label">'+dataMap[key]
						+'</label><div class="layui-input-block"><input type="text" name="'+key
						+'" autocomplete="off" class="layui-input"></div></div>';
						datasHtml = datasHtml + dataHtml;
						$(".channel-datas").html(datasHtml);
						for(var key in datas){
							$('input[name='+key+']').val(datas[key]);
						}
					}
				}
				form.render();
				$(".quota-add").click(function(){
					quotaAdd()
				})
				//定额收款码添加item
				function quotaAdd(){
					var html = '<div class="quota-item quota-item'+itemIndex+'">'
						+'<a class="delete-item"></a>'
						+'<div class="item-top"><label>金额</label><input type="number" name="" class="layui-input quota-money"></div>'
						+'<div class="item-top"><label>收款码</label><div class="layui-upload">'
						+'<button type="button" class="layui-btn" id="quota-upload">上传收款码</button>'
						+'<input type="hidden" name="image" value="" ></div></div>'
						+'<img class="goodsCover" src="" data-url="" onclick="showImage(this)" />'
						+'</div>';
					$(".quota-add").before(html)
					addUpload(itemIndex)
					$(".layui-upload button").click(function(){
						uploadClass = $(this).parent().parent().parent().attr('class')
						uploadClass = uploadClass.substring(uploadClass.indexOf(' ')+1)
					})
					$(".delete-item").click(function(){
						$(this).parent().remove()
					})
					$(".quota-money").on("input", function () {
						var obj = $(this).val()
						obj = obj.replace(/[^\d.]/g,"");  
						obj = obj.replace(/\.{2,}/g,".");   
						obj = obj.replace(".","$#$").replace(/\./g,"").replace("$#$","."); 
						obj = obj.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3');
					    if(obj.indexOf(".")< 0 && obj !=""){
					        obj= parseFloat(obj); 
					    } 
					    $(this).val(obj)
			        });
					inputBlur();
					itemIndex ++;
				}
				function inputBlur(){
					for(var i=0;i<$(".quota-item").length;i++){
						$(".quota-item:eq("+i+")").find(".quota-money").blur(function(){
							var poundage = Number($(this).val());
							$(this).val(poundage.toFixed(2));
						});
					}
				}
				function addUpload(index){
					upload.render({
					    elem: '.quota-item'+index+' #quota-upload'
					    ,url: '../system/resource/add' //改成您自己的上传接口
					    ,size:1024
					    ,accept: 'images'
						,exts: 'jpg|jpeg|png'
					    ,before: function(obj){
					      //预读本地文件示例，不支持ie8
					    }
					    ,done: function(res){
					    	$('.'+uploadClass+' input[name="image"]').val(res.data.uuid);
					    	$('.'+uploadClass+' .goodsCover').attr('data-url',res.data.url);
					    	$('.'+uploadClass+' .goodsCover').attr('src','..' + res.data.url); 
					    	$('.'+uploadClass+' button').html("更换收款码");
					    }
					});
				}
			} 
	    })
	}
});

function showImage(obj){
	var src = $(obj).attr("data-url")
	if(src){
		parent.showPhotos(src);
	}
}


