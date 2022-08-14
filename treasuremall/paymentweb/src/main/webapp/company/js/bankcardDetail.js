/**
 * 修改密码
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
			layer.msg("请填写持卡人！");
			return false;
		}
		if($("input[name=accountNum]").val()==""){
			layer.msg("请填写账户号码！");
			return false;
		}
		if($("select[name=bank]").val()==""){
			layer.msg("请选择所属银行！");
			return false;
		}
		if($("input[name=area]").val()==""){
			layer.msg("请填写开户地区！");
			return false;
		}
		if($("input[name=branchBank]").val()==""){
			layer.msg("请填写开户支行！");
			return false;
		}
		$.ajax({
	        url: '../store/companyBankcard/add',
	        type: 'post',
	        async:false,
	        data: $("form").serialize()
	    }).done(function(r) {
	    		if (r.status == "SUCCESS") {
	    			layer.msg('绑定成功', {
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
			var tableHtml = '<option value="">请选择</option>';
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