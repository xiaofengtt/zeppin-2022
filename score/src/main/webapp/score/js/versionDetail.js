var channelArray = ['appStore','tencent','baidu','huawei','xiaomi','meizu','oppo','vivo','zhushou360','google','samsung'];
var typeArray = ['iOS','Android'];
var pageNum = 1,
pageSize = 20;
var flagSubmit = true;
var uuid = (url('?uuid') != null) ? url('?uuid') : '';
$(function(){
	var channeloption = '';
	for(var i=0;i<channelArray.length;i++){
		channeloption += '<option value="'+channelArray[i]+'">'+channelArray[i]+'</option>'; 
	}
	$("#channelSelect").html(channeloption);
	var typeoption = '';
	for(var i=0;i<typeArray.length;i++){
		typeoption += '<option value="'+typeArray[i]+'">'+typeArray[i]+'</option>'; 
	}
	$("#typeSelect").html(typeoption);
	if(uuid!=''){
		getVersionDetail();
		$("#displayname").attr("disabled",true);
		$("#bundleid").attr("disabled",true);
		$("#typeSelect").attr("disabled",true);
		$("#channelSelect").attr("disabled",true);
		$("#name").attr("disabled",true);
		$("#code").attr("disabled",true);
	}
});

//获取详情
function getVersionDetail(){
	$.ajax({
	      url: '../back/version/get',
	      type: 'get',
	      async:false,
	      traditional:true,
	      data: {
	    	  "uuid":uuid
	      }
	  }).done(function(r) {
	  		if (r.status == "SUCCESS") {
	  			$("#displayname").val(r.data.displayname);
	  			$("#bundleid").val(r.data.bundleid);
	  			$("#typeSelect").val(r.data.type);
	  			$("#channelSelect").val(r.data.channel);
	  			$("#name").val(r.data.name);
	  			$("#code").val(r.data.code);
	  			$("#mainurl").val(r.data.mainurl);
	  			$("#tempurl").val(r.data.tempurl);
	  			$("#downloadurl").val(r.data.downloadurl);
	  			$("#adverturl").val(r.data.adverturl);
	  			$("#flagSelect").val(r.data.flag==true?'true':'false');
	  			$("#status").val(r.data.status);
	  			$("#flagupdate").val(r.data.flagupdate==true?'true':'false');
	  			$("#flagcompel").val(r.data.flagcompel==true?'true':'false');
	  			
	  		}else{
	  			layer.msg(r.message);
	  		}
	  }).fail(function(r) {
	      
	  });
}
//点击确定
$(".sure-btn").click(function(){
	var type = '',data = {};
	var displayname = $("#displayname").val().replace(/(^\s*)|(\s*$)/g, "");
	var bundleid = $("#bundleid").val().replace(/(^\s*)|(\s*$)/g, "");
	var typeSelect = $("#typeSelect").val().replace(/(^\s*)|(\s*$)/g, "");
	var channelSelect = $("#channelSelect").val().replace(/(^\s*)|(\s*$)/g, "");
	var name = $("#name").val().replace(/(^\s*)|(\s*$)/g, "");
	var code = $("#code").val().replace(/(^\s*)|(\s*$)/g, "");
	var mainurl = $("#mainurl").val().replace(/(^\s*)|(\s*$)/g, "");
	var tempurl = $("#tempurl").val().replace(/(^\s*)|(\s*$)/g, "");
	var downloadurl = $("#downloadurl").val().replace(/(^\s*)|(\s*$)/g, "");
	var adverturl = $("#adverturl").val().replace(/(^\s*)|(\s*$)/g, "");
	var flagSelect = $("#flagSelect").val().replace(/(^\s*)|(\s*$)/g, "")==="false" ? false : true;
	var status = $("#status").val().replace(/(^\s*)|(\s*$)/g, "");
	var flagupdate = $("#flagupdate").val().replace(/(^\s*)|(\s*$)/g, "")==="false" ? false : true;
	var flagcompel = $("#flagcompel").val().replace(/(^\s*)|(\s*$)/g, "")==="false" ? false : true;
	if(uuid!=''){
		if(adverturl==''){
			layer.msg("主包地址不能为空");
			return false;
		}
		type = '../back/version/edit';
		data = {
				'uuid':uuid,
				'mainurl':mainurl,
				'tempurl':tempurl,
				'downloadurl':downloadurl,
				'adverturl':adverturl,
				'flag':flagSelect,
				'status':status,
				'flagupdate':flagupdate,
				'flagcompel':flagcompel
		}
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
		data = {
				'type':typeSelect,
				'bundleid':bundleid,
				'displayname':displayname,
				'channel':channelSelect,
				'name':name,
				'code':code,
				'mainurl':mainurl,
				'tempurl':tempurl,
				'downloadurl':downloadurl,
				'adverturl':adverturl,
				'flag':flagSelect,
				'status':status,
				'flagupdate':flagupdate,
				'flagcompel':flagcompel
		}
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
	        	window.location.href=document.referrer;
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
});