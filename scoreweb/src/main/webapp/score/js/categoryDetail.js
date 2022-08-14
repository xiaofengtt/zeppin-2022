var uuid = (url('?uuid') != null) ? url('?uuid') : '';
var flagSubmit = true;

$(function(){
	getDetail();
});
//文章详情
function getDetail(){
	$.ajax({
        url: '../back/category/get',
        type: 'get',
        async:false,
        data: {
            "uuid":uuid
        }
    }).done(function(r) {
    		if (r.status == "SUCCESS") {
    			var imageUrl = '';
    			$("#name").val(r.data.name);//名称
    			$("#shortname").val(r.data.shortname);//英文名称
    			$("#istag").val(r.data.istag? "true":"false");//是否作为标签
    			$("#status").val(r.data.status);//状态
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
        	
        });
    });   	
}

//编辑保存
$(".submit-btn").click(function(){
	layer.confirm('确定要保存吗？', {
		  btn: ['确定','取消'] //按钮
		}, function(){
			if(flagSubmit == false) {
				return false;
			}
			flagSubmit = false;
			setTimeout(function() {
				flagSubmit = true;
			}, 3000);
			$.ajax({
		        url: '../back/category/edit',
		        type: 'post',
		        async:false,
		        data: {
		           "uuid":uuid,
		           "name":$("#name").val().replace(/(^\s*)|(\s*$)/g, ""),
		           "shortname":$("#shortname").val().replace(/(^\s*)|(\s*$)/g, ""),
		           "istag":$("#istag").val(),
		           "status":$("#status").val()
		        }
		    }).done(function(r) {
	    		if (r.status == "SUCCESS") {
	    			layer.msg("保存成功！", {
			            time: 2000
			        },function(){
			        	window.location.href=document.referrer;
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
	}, function(){
	  layer.closeAll();
	});
});
