var type = url('?type');
var size = (url('?pagesize') != null) ? url('?pagesize') : 50;
var num = (url('?pagenum') != null) ? url('?pagenum') : 1;
$(function(){
	
})
layui.use(['form','element'], function(){
  var form = layui.form
  ,layer = layui.layer
  ,$ = layui.jquery
  ,element = layui.element;
  
  
});

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
    		console.log(r.data)
			if(r.data.length > 0){
				$('input[name=frontUserNickname]').val(r.data[0].nickname)
				$('input[name=frontUser]').val(r.data[0].uuid)
				$('input[name=accountBalance]').val(r.data[0].balance.toFixed(2))
				if(r.data[0].imageURL != null){
					$('#userImg').attr('src','..' + r.data[0].imageURL);
				}else{
					$('#userImg').attr('src','../image/img-defaultAvatar.png');
				}
			}else{
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

$(".lay-submit").click(function(){
	if($("input[name=frontUser]").val()==""){
		layer.msg("请确认对象用户");
		return false;
	}
	if($("input[name=dAmount]").val().replace(/(^\s*)|(\s*$)/g, "")==""){
		layer.msg("请确认操作金币数量");
		return false;
	}
	if($("input[name=confirmAmount]").val().replace(/(^\s*)|(\s*$)/g, "")==""){
		layer.msg("请确认操作金币数量");
		return false;
	}
	if($("input[name=dAmount]").val().replace(/(^\s*)|(\s*$)/g, "")!=$("input[name=confirmAmount]").val().replace(/(^\s*)|(\s*$)/g, "")){
		layer.msg("两次输入的操作金币数量不相等");
		return false;
	}
	$.ajax({
        url: '../back/adminOffsetOrder/add',
        type: 'post',
        async:false,
        data: {
        	'frontUser': $("input[name=frontUser]").val(),
        	'type': type,
        	'dAmount': $("input[name='dAmount']").val().replace(/(^\s*)|(\s*$)/g, ""),
        	'remark': $("textarea[name='remark']").val()
        }
    }).done(function(r) {
		if (r.status == "SUCCESS") {
			layer.msg('添加成功', {
				time: 1000 
			}, function(){
				var index = parent.layer.getFrameIndex(window.name); 
				window.parent.getList(size,num);
//				parent.location.reload();
				parent.layer.close(index);
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
	return false;
})
