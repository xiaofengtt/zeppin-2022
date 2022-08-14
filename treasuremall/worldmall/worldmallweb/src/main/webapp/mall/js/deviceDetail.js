var nickname = (url('?nickname') != null) ? url('?nickname') : '';
var device = (url('?device') != null) ? url('?device') : '';
var pagesize = (url('?pagesize') != null) ? url('?pagesize') : 50;
var pagenum = (url('?pagenum') != null) ? url('?pagenum') : 1;
layui.use(['form', 'layedit', 'laydate','upload','element'], function(){
	var form = layui.form
	,layer = layui.layer
	,layedit = layui.layedit
	,laydate = layui.laydate
	,$ = layui.jquery
	,upload = layui.upload
	,element = layui.element;
})	
	$(function(){
		if(nickname!=""){
			$(".nickname").show()
			$("input[name='nickname']").val(nickname)
		}else{
			$(".nickname").hide()
		}
		if(device!=""){
			$(".device").show()
			$("input[name='device']").val(device)
		}else{
			$(".device").hide()
		}
	})

$(".lay-submit").click(function(){
	if($("input[name='title']").val().replace(/(^\s*)|(\s*$)/g, "")==""){
		layer.msg("请填写标题内容");
		return false;
	}
	if($("textarea[name='content']").val().replace(/(^\s*)|(\s*$)/g, "")==""){
		layer.msg("请填写推送内容");
		return false;
	}
	$.ajax({
		url: '../back/device/send',
		type: 'post',
		async:false,
		data: {
			'uuids':JSON.parse(window.localStorage.getItem("deviceUuid")),
			'title':$("input[name='title']").val().replace(/(^\s*)|(\s*$)/g, ""),
			'content':$("textarea[name='content']").val().replace(/(^\s*)|(\s*$)/g, "")
		}
	}).done(function(r) {
			if (r.status == "SUCCESS") {
				layer.msg('发送成功', {
					time: 1000 
				}, function(){
					var index = parent.layer.getFrameIndex(window.name); 
					window.parent.getList(pagesize,pagenum);
//	    				parent.location.reload();
					parent.layer.close(index);
					window.localStorage.removeItem("deviceUuid")
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
function setLength(obj,maxlength,id){
    var num=maxlength-obj.value.length;
    var leng=id;
    if(num<0){
        num=0;
    }
    document.getElementById(leng).innerHTML=num+"/500";
}
