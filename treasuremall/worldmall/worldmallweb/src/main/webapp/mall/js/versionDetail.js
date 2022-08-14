//var channelArray = ['appStore','tencent','baidu','huawei','xiaomi','meizu','oppo','vivo','zhushou360','google','samsung'];
var typeArray = ['iOS','Android'];
var flagSubmit = true;
var uuid = (url('?uuid') != null) ? url('?uuid') : '';
var pagesize = (url('?pagesize') != null) ? url('?pagesize') : 50;
var pagenum = (url('?pagenum') != null) ? url('?pagenum') : 1;
$(function(){
	$(".goPrePage").click(function(){
		window.location.href='versionList.html?pagesize='+pagesize+'&pagenum='+pagenum
	});
//	var channeloption = '';
//	for(var i=0;i<channelArray.length;i++){
//		channeloption += '<option value="'+channelArray[i]+'">'+channelArray[i]+'</option>'; 
//	}
//	$("#channelSelect").html(channeloption);
//	var typeoption = '';
//	for(var i=0;i<typeArray.length;i++){
//		typeoption += '<option value="'+typeArray[i]+'">'+typeArray[i]+'</option>'; 
//	}
//	$("#typeSelect").html(typeoption);
//	if(uuid!=''){
//		getVersionDetail();
//		$("#displayname").attr("disabled",true);
//		$("#bundleid").attr("disabled",true);
//		$("#typeSelect").attr("disabled",true);
//		$("#channelSelect").attr("disabled",true);
//		$("#name").attr("disabled",true);
//		$("#code").attr("disabled",true);
//	}
});

layui.use(['form', 'laydate', 'element'], function(){
	var form = layui.form
	,layer = layui.layer
	,laydate = layui.laydate
	,$ = layui.jquery
	,element = layui.element;
	
//	var typeoption = '';
//	for(var i=0;i<typeArray.length;i++){
//		typeoption += '<option value="'+typeArray[i]+'">'+typeArray[i]+'</option>'; 
//	}
//	$("#typeSelect").html(typeoption);
	//初始赋值
  if(uuid!=""){
		$.ajax({
	        url: '../back/version/get',
	        type: 'get',
	        async:false,
	        data:{
	        	'uuid':uuid
	        }
	    }).done(function(r) {
	  		if (r.status == "SUCCESS") {
	  			form.val('first',r.data);
	  		}else{
	  			layer.msg(r.message);
	  		}

			$("#displayname").attr("disabled",true);
			$("#bundleid").attr("disabled",true);
			$("#typeSelect").attr("disabled",true);
			$("#channelSelect").attr("disabled",true);
			$("#name").attr("disabled",true);
			$("#code").attr("disabled",true);
	  }).fail(function(r) {
	      
	  });
	}
	
	$(".lay-submit").click(function(){
		var map = $("form").serializeObject();
		var flag = map['flag'] == '1';
		map['flag'] = flag;
		var flagupdate = map['flagupdate'] == '1';
		map['flagupdate'] = flagupdate;
		var flagcompel = map['flagcompel'] == '1';
		map['flagcompel'] = flagcompel;
//		var type = '',data = {};
		var displayname = $("#displayname").val().replace(/(^\s*)|(\s*$)/g, "");
		var bundleid = $("#bundleid").val().replace(/(^\s*)|(\s*$)/g, "");
		var typeSelect = $("#typeSelect").val().replace(/(^\s*)|(\s*$)/g, "");
		var channelSelect = $("#channelSelect").val().replace(/(^\s*)|(\s*$)/g, "");
		var name = $("#name").val().replace(/(^\s*)|(\s*$)/g, "");
		var code = $("#code").val().replace(/(^\s*)|(\s*$)/g, "");
//		var mainurl = $("#mainurl").val().replace(/(^\s*)|(\s*$)/g, "");
//		var tempurl = $("#tempurl").val().replace(/(^\s*)|(\s*$)/g, "");
//		var downloadurl = $("#downloadurl").val().replace(/(^\s*)|(\s*$)/g, "");
		var adverturl = $("#adverturl").val().replace(/(^\s*)|(\s*$)/g, "");
//		var status = $("#status").val().replace(/(^\s*)|(\s*$)/g, "");
//		var flagupdate = $("#flagupdate").val().replace(/(^\s*)|(\s*$)/g, "")==="false" ? false : true;
//		var flagcompel = $("#flagcompel").val().replace(/(^\s*)|(\s*$)/g, "")==="false" ? false : true;
		if(uuid!=''){
			map['uuid'] = uuid;
			if(adverturl==''){
				layer.msg("主包地址不能为空");
				return false;
			}
			type = '../back/version/edit';
			data = map
		}else{
			if(displayname==''){
				layer.msg("应用名称不能为空");
				return false;
			}
			if(bundleid==''){
				layer.msg("包名不能为空");
				return false;
			}
			if(name==''){
				layer.msg("版本号不能为空");
				return false;
			}
			if(code==''){
				layer.msg("版本编号不能为空");
				return false;
			}
			if(adverturl==''){
				layer.msg("主包地址不能为空");
				return false;
			}
			type = '../back/version/add';
			data = map
		}
		if(flagSubmit == false) {
			return false;
		}
		flagSubmit = false;
		setTimeout(function() {
			flagSubmit = true;
		}, 3000);
		$.ajax({
		      url: type,
		      type: 'post',
		      async:false,
		      data: data
		}).done(function(r) {
	  		if (r.status == "SUCCESS") {
	  			layer.msg("操作成功！", {
		            time: 2000
		        },function(){
		        	window.location.href='versionList.html?pagesize='+pagesize+'&pagenum='+pagenum;
		        })
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
});
