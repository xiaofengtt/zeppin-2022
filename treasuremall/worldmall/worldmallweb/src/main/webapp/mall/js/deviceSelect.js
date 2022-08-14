var uuid = (url('?uuid') != null) ? url('?uuid') : '';
var status = (url('?status') != null) ? url('?status') : '';
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
		topicList()
	})
	// 获取主题列表
	function topicList(){
		$.ajax({
		    url: '../back/topic/list',
		    type: 'get',
		    async:false,
		    data:{
				'status':'normal',
		    	'pageSize':'10000',
		    	'pageNum':'1'
		    }
		}).done(function(r) {
				if (r.status == "SUCCESS") {
					var html = '<option value="">请选择</option>';
					for(var i = 0;i<r.data.length;i++){
						html += '<option value="'+r.data[i].uuid+'">'+ r.data[i].displayName+'</option>';
					}
					$("select[name='country']").html(html);
				} else {
					
				}
		}).fail(function(r) {
		    
		}); 
	}

$(".lay-submit").click(function(){
	if($("select[name='country']").val().replace(/(^\s*)|(\s*$)/g, "")==""){
		layer.msg("请选择主题");
		return false;
	}
	$.ajax({
		url: '../back/device/bind',
		type: 'post',
		async:false,
		data: {
			'uuids':JSON.parse(window.localStorage.getItem("deviceUuid")),
			'topic':$("select[name='country']").val()
		}
	}).done(function(r) {
			if (r.status == "SUCCESS") {
				layer.msg('绑定成功', {
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
