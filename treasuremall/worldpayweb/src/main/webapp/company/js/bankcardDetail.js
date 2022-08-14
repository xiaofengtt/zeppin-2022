/**
 * 
 */

$(function(){
	getBankList();
});

layui.use(['form','element'], function(){
	var form = layui.form
	,layer = layui.layer
	,$ = layui.jquery
	,element = layui.element;
	
	$(".lay-submit").click(function(){
		if($("input[name=holder]").val()==""){
			layer.msg("Please fill in the cardholder！");
			return false;
		}
		if($("input[name=accountNum]").val()==""){
			layer.msg("Please fill in the AccountNum！");
			return false;
		}
		if($("select[name=bank]").val()==""){
			layer.msg("Please select the bank！");
			return false;
		}
		if($("input[name=area]").val()==""){
			layer.msg("Please fill in the area！");
			return false;
		}
		if($("input[name=branchBank]").val()==""){
			layer.msg("Please fill in the branch！");
			return false;
		}
		$.ajax({
	        url: '../store/companyBankcard/add',
	        type: 'post',
	        async:false,
	        data: $("form").serialize()
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
		return false;
	})
});

function getBankList(){
	$.ajax({
        url: '../store/bank/list',
        type: 'get',
        async:false,
        data: {
        	'status':'normal',
        	'pageSize':1000,
        	'pageNum':1
        }
    }).done(function(r) {
    	if (r.status == "SUCCESS") {
			var tableHtml = '<option value="">please select</option>';
			for(var i=0;i<r.data.length;i++){
				tableHtml += '<option value="'+r.data[i].uuid+'">'+r.data[i].name+'</option>';
			}
			$("select[name='bank']").html(tableHtml);
		}
    }).fail(function(r) {
        layer.msg("error", {
            time: 2000
        });
    });   	
}