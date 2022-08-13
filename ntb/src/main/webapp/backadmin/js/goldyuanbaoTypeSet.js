$(".addType").click(function(){
	var width=$(window).width()-364+"px";
	layer.open({
	  type: 1,
	  title: false,
	  shadeClose: true,
	  shade: 0,
	  scrollbar: true,
	  offset:['191px','335px'],
	  area: [width, 'auto'],
	  content: '<div class="layerOpen"><p class="text-left title">添加类型</p><a class="closeBtn"></a><div class="contents"><div class="form-group"><label class="col-md-4">名称：</label><div class="col-md-6"><input type="text" id="name" class="form-control"/></div><div class="clear"></div></div><div class="form-group"><label class="col-md-4">描述：</label><div class="col-md-6"><textarea id="describe" class="form-control" rows="11"></textarea></div><div class="clear"></div></div><div class="btnGroup text-center"><a class="btn sureBtn" onclick="submitData()">确定</a><a class="btn cancleBtn">取消</a></div><div class="clear"></div></div>' 
	}); 
	$(".layui-layer-close").css("display","none");
	$(".cancleBtn,.closeBtn").click(function(){
		layer.closeAll();
	});
});
$(".deleteBtn").click(function(){
	layer.titleConfirm('确认要删除吗?', "删除确认", function(index){
	  layer.close(index);
	});       
});
//提交
function submitData(){
	var name=$("#name").val().replace(/(^\s*)|(\s*$)/g, "");
	var describe=$("#describe").val().replace(/(^\s*)|(\s*$)/g, "");
	if(name==""){ 
		layer.msg('名称不能为空', {
			time: 2000 
		});
	}else if(describe==""){
		layer.msg('描述不能为空', {
			time: 2000 
		});
	}else{
		layer.msg('添加成功', {
		  time: 2000 
		}, function(){
			layer.closeAll();
		});   
	}
}
