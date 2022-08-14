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
		if(status!=''&&status!='fail'){
			$(".layui-button").hide()
		}
		topicList();
		if(uuid!=""){
			$.ajax({
				url: '../back/topicMessage/get',
				type: 'get',
				async:false,
				data:{
					'uuid':uuid
				}
			}).done(function(r) {
					if (r.status == "SUCCESS") {
						 $("select[name='topic']").val(r.data.noticeTopic)
						 $("input[name='title']").val(r.data.title)
						 $("textarea[name='content']").val(r.data.content)
						 if(status=='fail'){
						 	$("input[name=isDefault][value='true']").prop("checked",true);
						 }else{
						 	$("input[name=isDefault][value='false']").prop("checked",true);
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
			}).fail(function(r) {
				
			}); 
		}
	})
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
					$("select[name='topic']").html(html);
				} else {
					
				}
		}).fail(function(r) {
		    
		}); 
	}

$(".lay-submit").click(function(){
	if($("select[name='topic']").val()==""){
		layer.msg("请选择主题");
		return false;
	}
	if($("input[name='title']").val().replace(/(^\s*)|(\s*$)/g, "")==""){
		layer.msg("请填写标题内容");
		return false;
	}
	if($("textarea[name='content']").val().replace(/(^\s*)|(\s*$)/g, "")==""){
		layer.msg("请填写推送内容");
		return false;
	}
	var data = {
		'topic':$("select[name='topic']").val(),
		'title':$("input[name='title']").val().replace(/(^\s*)|(\s*$)/g, ""),
		'content':$("textarea[name='content']").val().replace(/(^\s*)|(\s*$)/g, "")
	}
	if(uuid!=""){
		data['uuid'] = uuid 	
	}
	$.ajax({
		url: '../back/topicMessage/send',
		type: 'post',
		async:false,
		data: data
	}).done(function(r) {
			if (r.status == "SUCCESS") {
				layer.msg('推送成功', {
					time: 1000 
				}, function(){
					var index = parent.layer.getFrameIndex(window.name); 
					window.parent.getList(pagesize,pagenum);
//	    				parent.location.reload();
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
function setLength(obj,maxlength,id){
    var num=maxlength-obj.value.length;
    var leng=id;
    if(num<0){
        num=0;
    }
    document.getElementById(leng).innerHTML=num+"/500";
}