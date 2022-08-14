/**
 * 
 */
var uuid = (url('?uuid') != null) ? url('?uuid') : '';
var pagesize = (url('?pagesize') != null) ? url('?pagesize') : 50;
var pagenum = (url('?pagenum') != null) ? url('?pagenum') : 1;
var user = false;
$(function(){
	$(".goPrePage").click(function(){
		window.location.href='userVoucherList.html?pagesize='+pagesize+'&pagenum='+pagenum;
		localStorage.clear();
		return false
	});
	$(".searchVoucherBtn").click(function(){
		layer.open({
			type: 2, 
			title:false, 
			area: ['80%', '80%'],
			content: ['userVoucherChoose.html','no'] 
		});
		return false;
	});
})
function getValue(){
	$("input[name='vouchers']").val(window.localStorage.getItem("userVoucherName"));
	$("input[name='vouchersId']").val(window.localStorage.getItem("userVoucher"));
}
$(".searchBtn").click(function(){
	var frontUserShowId = $('input[name=frontUserShowId]').val()
	if(frontUserShowId == ''){
		layer.msg("请填写用户ID");
		return false;
	}
	$.ajax({
        url: '../back/user/list',
        type: 'get',
        async:false,
        data: {
        	'showid': frontUserShowId
        }
    }).done(function(r) {
    	if (r.status == "SUCCESS") {
			if(r.data.length > 0){
				$('input[name=frontUserNickname]').val(r.data[0].nickname)
				$('input[name=frontUser]').val(r.data[0].uuid)
				$('input[name=accountBalance]').val(r.data[0].balance.toFixed(2))
				if(r.data[0].imageURL != null){
					$('#userImg').attr('src','..' + r.data[0].imageURL);
				}else{
					$('#userImg').attr('src','../image/img-defaultAvatar.png');
				}
				user = true;
			}else{
				user = false;
				layer.msg('用户ID不存在！', {
					time: 1000 
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
    })
    return false;
})
layui.use(['form'], function(){
	var form = layui.form
	$(".lay-submit").click(function(){
		if($('input[name=frontUserShowId]').val() == ''){
			layer.msg("请填写用户ID");
			return false;
		}else if(!user){
			layer.msg("请填写正确的用户ID");
			return false;
		}else if($("input[name='vouchersId']").val()==""){
			layer.msg("请选择金券");
			return false;
		}
		$.ajax({
	        url: '../back/userVoucher/add',
	        type: 'post',
	        async:false,
	        traditional: true,
	        data: {
	        	'frontUser':$('input[name=frontUser]').val(),
	        	'vouchers':$("input[name='vouchersId']").val().split(",")
	        }
	    }).done(function(r) {
	    		if (r.status == "SUCCESS") {
	    			layer.msg('操作成功', {
	    				time: 1000 
	    			}, function(){
	    				window.location.href='userVoucherList.html?pagesize='+pagesize+'&pagenum=1';
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
		localStorage.clear();
		return false;
	})
})