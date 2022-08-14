/**
 * 
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
		for(var key in dataMap){
			dataHtml = '<div class="layui-form-item"><label class="layui-form-label">'+dataMap[key]
			+'</label><div class="layui-input-block"><input type="text" name="'+key
			+'" autocomplete="off" class="layui-input"></div></div>';
			datasHtml = datasHtml + dataHtml;
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
				$('#uploadNormal').html("change code");
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
				+'<div class="item-top"><label>number</label><input type="number" name="" class="layui-input quota-money"></div>'
				+'<div class="item-top"><label>code</label><div class="layui-upload">'
				+'<button type="button" class="layui-btn" id="quota-upload">upload code</button>'
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
			    ,url: '../system/resource/add' 
			    ,size: 1024
			    ,accept: 'images'
				,exts: 'jpg|jpeg|png'
			    ,before: function(obj){
			    }
			    ,done: function(res){
			    	$('.'+uploadClass+' input[name="image"]').val(res.data.uuid);
			    	$('.'+uploadClass+' .goodsCover').attr('data-url',res.data.url);
			    	$('.'+uploadClass+' .goodsCover').attr('src','..' + res.data.url); 
			    	$('.'+uploadClass+' button').html("change code");
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
			layer.msg("Please select channel");
			return false;
		}
		if($("input[name=name]").val()==""){
			layer.msg("Please fill in the channel name");
			return false;
		}
		if($("input[name=accountNum]").val()==""){
			layer.msg("Please fill in the account");
			return false;
		}
		
		if($("select[name=channel]").val() == "9298fcd6-36ee-41b2-8c99-31a7334d93dc" && $('.channel-datas input[name="normalCode"]').val()==""){
			layer.msg("Please upload the normal code");
			return false;
		}
		for(var i=0;i<$(".quota-item").length;i++){
			if($(".quota-item:eq("+i+")").find(".quota-money").val()==""||$(".quota-item:eq("+i+")").find(".quota-money").val()=="0.00"){
				layer.msg("Quota code has the option of 0 amount");
				return false;
			}else if($(".quota-item:eq("+i+")").find("input[name='image']").val()==""){
				layer.msg("Quota code has the option of not uploading collection code");
				return false;
			}
		}
		if($("input[name=transferUrl]").val()==""){
			layer.msg("Please fill in the TransferUrl");
			return false;
		}
		if(!isNumber($("input[name=min]").val())){
			layer.msg("Please fill in the SingleMin");
			return false;
		}
		if(!isNumber($("input[name=max]").val())){
			layer.msg("Please fill in the SingleMax");
			return false;
		}
		if(!isNumber($("input[name=dailyMax]").val())){
			layer.msg("Please fill in the DailyMax");
			return false;
		}
		if(!isNumber($("input[name=totalMax]").val())){
			layer.msg("Please fill in the TotalMax");
			return false;
		}
		if(!isNumber($("input[name=poundage]").val()) && !isNumber($("input[name=poundageRate]").val())){
			layer.msg("Please fill in the rate");
			return false;
		}
		
		var datas = {};
		$(".channel-datas input").each(function(){
			datas[$(this).attr('name')] = $(this).val();
	    });
		
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
	    			layer.msg('Successful', {
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
		    			layer.msg('Successful', {
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
				var tableHtml = '<option value="">please select</option>';
				for(var i=0;i<r.data.length;i++){
					datasMap[r.data[i].uuid] = r.data[i].dataMap;
					tableHtml += '<option value="'+r.data[i].uuid+'">'+r.data[i].name+'</option>';
				}
				$("select[name='channel']").html(tableHtml);
				form.render();
			} 
	    })
	}
	
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
				form.render();
				$(".quota-add").click(function(){
					quotaAdd()
				})
				function quotaAdd(){
					var html = '<div class="quota-item quota-item'+itemIndex+'">'
						+'<a class="delete-item"></a>'
						+'<div class="item-top"><label>money</label><input type="number" name="" class="layui-input quota-money"></div>'
						+'<div class="item-top"><label>code</label><div class="layui-upload">'
						+'<button type="button" class="layui-btn" id="quota-upload">upload code</button>'
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
					    ,url: '../system/resource/add' 
					    ,size:1024
					    ,accept: 'images'
						,exts: 'jpg|jpeg|png'
					    ,before: function(obj){
					    }
					    ,done: function(res){
					    	$('.'+uploadClass+' input[name="image"]').val(res.data.uuid);
					    	$('.'+uploadClass+' .goodsCover').attr('data-url',res.data.url);
					    	$('.'+uploadClass+' .goodsCover').attr('src','..' + res.data.url); 
					    	$('.'+uploadClass+' button').html("upload code");
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


