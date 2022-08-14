var key = (url('?key') != null) ? url('?key') : '';
var type = (url('?type') != null) ? url('?type') : '';
var paramValue = (url('?value') != null) ? decodeURI(url('?value')) : '';
var arrayStr,index;
var flagSubmit = true;
layui.use(['form', 'layedit', 'element','layedit'], function(){
	  var form = layui.form
	  ,layer = layui.layer
	  ,$ = layui.jquery
	  ,element = layui.element
	  ,layedit = layui.layedit; 
$(function(){
	$("."+type).show();
	if(type=="currency"||type=="boolean"||type=="numberic"||type=="string"){
		$("."+type+" .numberInput").val(paramValue);
	}else if(type=="primarykey"){
		var option="";
		if(key == 'withdraw_default_account'){
			$.ajax({
		        url: '../back/capitalAccount/list',
		        type: 'get',
		        async:false,
		        traditional: "true",
		        data: {
		        	"transType":"withdraw",
		            "status":"normal"
		        }
		    }).done(function(r) {
		    	if (r.status == "SUCCESS") {
		    		for(var i=0;i<r.data.length;i++){
		    			option += "<option value='"+r.data[i].uuid+"'>"+r.data[i].name+"</option>";
		    		}
		    		
		    	}
		    });
		}
		$("."+type+" .numberInput").html(option);
		$("."+type+" .numberInput").val(paramValue);
	}else if(type=="map"){
		arrayStr = paramValue.split('<br/>');
		var html = '';
		for(var i=0;i<arrayStr.length-1;i++){
			html += '<div class="layui-form-item">'+
					'<label class="layui-form-label">参数值：</label>'+
					'<div class="layui-input-inline">'+
						'<input class="layui-input keyInput" value="'+arrayStr[i].substring(0,arrayStr[i].indexOf("："))
						+'"/><span>:</span><input class="layui-input valueInput" value="'+arrayStr[i].substring(arrayStr[i].indexOf("：")+1,arrayStr[i].length)
						+'"/><b class="addItem">+</b><b class="removeItem">-</b>'+
					'</div>'+
					'<div class="clear"></div>'+
				'</div>';
		}
		$("."+type).html(html);	
		$(".addItem").unbind().click(function(){
			addItem(this);
		})
		$(".removeItem").unbind().click(function(){
			removeItem(this);
		})
	}else if(type=="richtext"){
		$("#richtext").val(new Base64().decode(window.localStorage.getItem('paramValue')));
		index = layedit.build('richtext',{
			height: 180 
		});
	}
	window.localStorage.removeItem('paramValue')
	
});
function addItem(obj){
	$("."+type).append('<div class="layui-form-item">'+
			'<label class="layui-form-label">参数值：</label>'+
			'<div class="layui-input-inline">'+
				'<input class="layui-input keyInput" value=""/><span>:</span><input class="layui-input valueInput" value="'
			+'"/><b class="addItem">+</b><b class="removeItem">-</b>'+
			'</div>'+
			'</div>');
	$(".addItem").unbind().click(function(){
		addItem(this);
	})
	$(".removeItem").unbind().click(function(){
		removeItem(this);
	})
}
function removeItem(obj){
	$(obj).parent().parent().remove();
	if($(".map").html()==""){
		$("."+type).html('<div class="form-group col-xs-12 ">'+
				'<label class="col-xs-2 text-right">参数值：</label>'+
				'<div class="content-items col-xs-10">'+
					'<input class="form-control keyInput" value=""/><span>:</span><input class="form-control valueInput" value="'
				+'"/><b class="addItem">+</b><b class="removeItem">-</b>'+
				'</div>'+
				'<div class="clear"></div>'+
				'</div>');
		$(".addItem").unbind().click(function(){
			addItem(this);
		})
		$(".removeItem").unbind().click(function(){
			removeItem(this);
		})
	}
}

$(".submit-btn").click(function(){	
	parent.layer.confirm('确定提交修改吗？', {
		  btn: ['确定','取消'] //按钮
		}, function(){
			if(flagSubmit == false) {
				return false;
			}
			flagSubmit = false;
			setTimeout(function() {
				flagSubmit = true;
			}, 3000);
			var values;
			var array = [];
			if(type=="currency"){
				values = $("."+type+" .numberInput").val().replace(/[^\d.]/g,"")
			}else if(type=="numberic"||type=="string"||type=="primarykey"){
				values = $("."+type+" .numberInput").val();
			}else if(type=="map"){
				$(".map .layui-form-item").each(function(key,value){
					array[key] = $(this).find(".keyInput").val()+'@_@'+$(this).find(".valueInput").val();
				});
				values = array;
			}else if(type=="boolean"){
				values = ($("."+type+" .numberInput").val()).toString();
			}else if(type=="richtext"){
				values =new Base64().encode(layedit.getContent(index))
			}
			
			$.ajax({
		        url: '../back/param/edit',
		        type: 'get',
		        async:false,
		        traditional: "true",
		        data: {
		            "paramKey":key,
		            "paramValues":values
		        }
		    }).done(function(r) {
		    		if (r.status == "SUCCESS") {
		    			parent.layer.closeAll();
		    			parent.getList();
		    		} else {
		    			if(r.errorCode=="302"){
		    				layer.msg(r.message, {
		    		            time: 2000
		    		        },function(){
		    		        	parent.window.location.href="login.html";
		    		        });
		    			}else{
		    				parent.layer.msg(r.message);
		    			}
		    		}
		    }).fail(function(r) {
		        layer.msg("error", {
		            time: 2000
		        },function(){
		        	
		        });
		    }); 
		}, function(){
			  layer.closeAll();
			});
});
})