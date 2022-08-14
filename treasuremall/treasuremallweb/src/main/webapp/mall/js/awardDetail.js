var pagesize = (url('?pagesize') != null) ? url('?pagesize') : 50;
var pagenum = (url('?pagenum') != null) ? url('?pagenum') : 1;
var index = "0";
$(function(){
	$(".award-name").val(JSON.parse(window.localStorage.getItem("data")).name);
	$(".award-mobile").val(JSON.parse(window.localStorage.getItem("data")).mobile);
	$(".award-code").val(JSON.parse(window.localStorage.getItem("data")).code);
	$(".award-address").val(JSON.parse(window.localStorage.getItem("data")).address);
})

layui.use(['form', 'layedit', 'laydate','upload','element'], function(){
  var form = layui.form
  ,layer = layui.layer
  ,layedit = layui.layedit
  ,laydate = layui.laydate
  ,$ = layui.jquery
  ,upload = layui.upload
  ,element = layui.element;
  form.on('radio', function(data){
	  index = data.value;
  });
});
$(".lay-submit").click(function(){
	if($("select[name='company']").val().replace(/(^\s*)|(\s*$)/g, "")==""){
		layer.msg("请选择快递公司");
		return false;
	}
	if($("input[name='expressNumber']").val().replace(/(^\s*)|(\s*$)/g, "")==""){
		layer.msg("请输入快递单号");
		return false;
	}
	$.ajax({
        url: '../back/winningInfo/receive',
        type: 'post',
        traditional: true,
        async:false,
        data: {
        	'uuid':window.localStorage.getItem("uuid"),
        	'company':$("select[name='company']").val(),
        	'expressNumber':$("input[name='expressNumber']").val().replace(/(^\s*)|(\s*$)/g, "")
        }
    }).done(function(r) {
    		if (r.status == "SUCCESS") {
    			layer.msg('操作成功', {
    				time: 1000 
    			}, function(){
    				window.localStorage.removeItem("uuid")
    				window.localStorage.removeItem("data")
    				var index = parent.layer.getFrameIndex(window.name); 
    				window.parent.getList(pagesize,pagenum);
//    				parent.location.reload();
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
