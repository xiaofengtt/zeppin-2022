var key = (url('?key') != null) ? url('?key') : '';
var type = (url('?type') != null) ? url('?type') : '';
var paramValue = (url('?value') != null) ? decodeURI(url('?value')) : '';
var arrayStr;
var flagSubmit = true;
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
			html += '<div class="form-group col-xs-12 ">'+
					'<label class="col-xs-2 text-right">参数值：</label>'+
					'<div class="content-items col-xs-10">'+
						'<input class="form-control keyInput" value="'+arrayStr[i].substring(0,arrayStr[i].indexOf("："))
						+'"/><span>:</span><input class="form-control valueInput" value="'+arrayStr[i].substring(arrayStr[i].indexOf("：")+1,arrayStr[i].length)
						+'"/><b class="addItem" onclick="addItem(this)">+</b><b class="removeItem" onclick="removeItem(this)">-</b>'+
					'</div>'+
					'<div class="clear"></div>'+
				'</div>';
		}
		$("."+type).html(html);			
	}
});
function addItem(obj){
	$("."+type).append('<div class="form-group col-xs-12 ">'+
			'<label class="col-xs-2 text-right">参数值：</label>'+
			'<div class="content-items col-xs-10">'+
				'<input class="form-control keyInput" value=""/><span>:</span><input class="form-control valueInput" value="'
			+'"/><b class="addItem" onclick="addItem(this)">+</b><b class="removeItem" onclick="removeItem(this)">-</b>'+
			'</div>'+
			'<div class="clear"></div>'+
			'</div>');
}
function removeItem(obj){
	$(obj).parent().parent().remove();
	if($(".map").html()==""){
		$("."+type).html('<div class="form-group col-xs-12 ">'+
				'<label class="col-xs-2 text-right">参数值：</label>'+
				'<div class="content-items col-xs-10">'+
					'<input class="form-control keyInput" value=""/><span>:</span><input class="form-control valueInput" value="'
				+'"/><b class="addItem" onclick="addItem(this)">+</b><b class="removeItem" onclick="removeItem(this)">-</b>'+
				'</div>'+
				'<div class="clear"></div>'+
				'</div>');
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
				$(".map .form-group").each(function(key,value){
					array[key] = $(this).find(".keyInput").val()+'@_@'+$(this).find(".valueInput").val();
				});
				values = array;
			}else if(type=="boolean"){
				values = ($("."+type+" .numberInput").val()).toString();
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