
var ue = UE.getEditor('editor');

jQuery(window).load(function() {
	$(".attachmentBox").addClass("col-sm-10").removeAttr("style").css({"width":$(".form-group").width()*0.83333-30+
		"px","margin-left":"15px","padding":"0"});
	$(".ajax-upload-dragdrop").css("width","100%");
});
$(window).resize(function(){
	$(".attachmentBox").addClass("col-sm-10").removeAttr("style").css({"width":$(".form-group").width()*0.83333-30+
		"px","margin-left":"15px","padding":"0"});
	$(".ajax-upload-dragdrop").css("width","100%");
});
$(".firLevel li").click(function(){
	if($(this).children("img").attr("src")=="../img/rarrow-youhui.png"){
		$(this).children("ul").css("display","block");
		$(this).children("img").attr("src","../img/rarrow-xiahui.png");
		return false;
	}else{
		$(this).children("ul").css("display","none");
		$(this).children("img").attr("src","../img/rarrow-youhui.png");
		return false;
	}
	
});

$(".addressee").click(function(){
	if($(this).attr("name")=="no"){
		var name=$(this).children("a").html()+";";
		$(this).attr("name","yes");
		$("#recipient").val($("#recipient").val()+name);
	}else{
		
	}
});

$("span.tag").click(function(){
	$(this).addClass("light").siblings().removeClass("light");
	if($(this).html()=="申请"){
//		$(".firLevel,.contactDiv,.tip").css("display","none");
//		$(".mtcDiv").html('<div class="mtcs"><b id="1-26124">自治区教育厅管理员</b></div>');
		$("ul.firLevel li:eq(0)").css("display","block").addClass("light").css({"border-bottom-color":"#e6e6e6","background":"#fafafa"});
		$("ul.firLevel li:eq(3),ul.firLevel li:eq(1),ul.firLevel li:eq(2)").css("display","none");
		$("ul.firLevel li").css("width","100%");
		$("#admin select").select2();
		$.ajax({
	        type: "GET",
	        url: "../admin/newmail_getApplyConnectionListInfo.action",
	        data: {type:"3"},
	        dataType: "text",
	        async: true,
	        success: function (data) {
	        	var json = (new Function("", "return " + data.split("&&&")[0]))();
	        	if(json.Result=="OK"){
	        		$(".col-md-3").css("display","none");
	        		$(".adminLabel").css("display","block").html("管理员：");
	        		$("#admin select").html("").css("display","block");
	        		$(".select2-container").css("display","block");
	        		$("#admin .span3").css("display","none");
	        		var str='<option number="1" value="0" search="全部">全部</option>';
	        		$("#adminNext1,#adminNext2").css("display","none");
	        		for(var i=0;i<json.Records.length;i++){
	        			str+='<option value="'+json.Records[i].id+'" search="'+json.Records[i].organization+
	        			'-'+json.Records[i].name+'">'+json.Records[i].organization+
	        			'-'+json.Records[i].name+'</option>';
	        		}
	        		$("#admin select").html(str);
	        		$("#admin select").attr("number","1");
	        		$("#admin").find(".select2-chosen").html($("#admin select").find("option:eq(0)").html());
	        	}else{
	        		$("#admin select").html('<option number="1" value="0" search="全部">全部</option>');
	        		$("#admin select").attr("number","1");
	        	}
	        }
	    });
	}else{
		$(".firLevel,.contactDiv,.tip").css("display","block");
		$(".mtcDiv").html('');
		if($(this).html()!="站内信"){
			$('#admin select,#adminNext1 select,#adminNext2 select').select2();
			
		}
		admin();
		
		$("ul.firLevel li:eq(0)").addClass("light")
		.css({"border-bottom-color":"#fff","background":"#fff"}).siblings().removeClass("light");
		$("#adminNext1,#adminNext2").css("display","none");
		$.ajax({
	        type: "GET",
	        url: "../admin/newmail_getInfoInit.action",
	        data: {id:id,method:"add"},
	        dataType: "text",
	        async: true,
	        success: function (data) {
	        	var json = (new Function("", "return " + data.split("&&&")[0]))();
	        	if(json.Result=="OK"){
	        		contact(json.Records.role,json.Records.level);
	        	}else{
	        		layer.confirm('参数错误', {
	        			btn : [ '确定' ]
	        		//按钮
	        		}, function() {
	        			layer.closeAll();
	        		});
	        	}
	        }
		});
	}
});
$(function(){
	var number=$("ul.firLevel li").length;
	$("ul.firLevel li").css("width",100/number+"%");
	if($("span.light").html()!="站内信"){
		$('#admin select,#adminNext1 select,#adminNext2 select').select2();
		admin();
		$("ul.firLevel li:eq(0)").addClass("light").siblings().removeClass("light");
		$("#adminNext1,#adminNext2").css("display","none");
	}
	
	if(id!=''){
		edit();
	}else{
		$.ajax({
	        type: "GET",
	        url: "../admin/newmail_getInfoInit.action",
	        data: {id:id,method:"add"},
	        dataType: "text",
	        async: true,
	        success: function (data) {
	        	var json = (new Function("", "return " + data.split("&&&")[0]))();
	        	if(json.Result=="OK"){
	        		contact(json.Records.role,json.Records.level);
	        	}else{
	        		layer.confirm('参数错误', {
	        			btn : [ '确定' ]
	        		//按钮
	        		}, function() {
	        			layer.closeAll();
	        		});
	        	}
	        }
		});
	}
});
$("ul.firLevel li").click(function(){
	var number=$(this).index();
	$("ul.firLevel li:eq(0)").css({"background":"#fafafa","border-bottom-color":"#e6e6e6"});
	$(this).addClass("light").siblings().removeClass("light");
});

//筛选的select
function addFilter(selectId){	
		var content=$("#admin").find(".span4 .select2-chosen").html();
		var number=$("#admin select option").attr("number");
		var id=$("#admin").find("select").val();
		$(selectId).find(".tip").css("display","none");
		if(content=="全部"){
			if(number=="1"){
				content="全部管理员";
			}else if(number=="2"){
				content="全部承训单位";
			}else if(number=="4"){
				content="全部评审专家";
			}else if(number=="3"){
				content="全部培训项目";
			}
		}
		if(number=="3"){
			var id2=$("#adminNext1").find("select").val();
			var id3=$("#adminNext2").find("select").val();
			var content2=$("#adminNext1").find(".select2-chosen").html();
			var content3=$("#adminNext2").find(".select2-chosen").html();
			if(content2=="全部"){
				content2="全部学科";
			}
			if(content3=="全部"){
				content3="全部承训单位";
			}
			$(selectId).find(".mtcDiv").append('<div class="mtcs"><b id="'+number+'-'+id+'-'+id2+'-'+id3+'">'+content+
			'-'+content2+'-'+content3+'</b><span class="close" onclick="deleteSingle(this)">x</span></div>');
		}else{
			if($("span.light").html()=="站内信"){
				var ids=$("#organization2").val();
				if(ids!=""){
					var contents=$(".span3 .select2-chosen").html();
					var light=$("ul.firLevel li.light a").html();
					if(light=="管理员"){
						$(selectId).find(".mtcDiv").append('<div class="mtcs"><b id="1-'+ids+'">'+contents+
						'</b><span class="close" onclick="deleteSingle(this)">x</span></div>');
					}else if(light=="承训单位"){
						$(selectId).find(".mtcDiv").append('<div class="mtcs"><b id="2-'+ids+'">'+contents+
						'</b><span class="close" onclick="deleteSingle(this)">x</span></div>');
					}else if(light=="教师"){
						$(selectId).find(".mtcDiv").append('<div class="mtcs"><b id="3-'+ids+'">'+contents+
						'</b><span class="close" onclick="deleteSingle(this)">x</span></div>');
					}else if(light=="评审专家"){
						$(selectId).find(".mtcDiv").append('<div class="mtcs"><b id="4-'+ids+'">'+contents+
						'</b><span class="close" onclick="deleteSingle(this)">x</span></div>');
					}
					
				}else{
					if($(".mtcDiv").html().replace(/(^\s*)|(\s*$)/g, "")==""){
						$(selectId).find(".tip").css("display","block");
					}
				}
			}else{
				$(selectId).find(".mtcDiv").append('<div class="mtcs"><b id="'+number+'-'+id+'">'+content+
				'</b><span class="close" onclick="deleteSingle(this)">x</span></div>');
			}
			
		}
		
		
}
//删除多选的
function deleteSingle(obj){
	$(obj).parents(".mtcs").remove();
	if($("#recipient .mtcDiv").html().replace(/(^\s*)|(\s*$)/g, "")==""){
		$("#recipient").find(".tip").css("display","block");
	}else{
		$("#recipient").find(".tip").css("display","none");
	}
}
var id = (url('?id') != null) ? url('?id') : '';
//获取转发信息
function edit(){
	$.ajax({
        type: "GET",
        url: "../admin/newmail_getInfoInit.action",
        data: {id:id,method:"edit"},
        dataType: "text",
        async: true,
        success: function (data) {
        	var json = (new Function("", "return " + data.split("&&&")[0]))();
        	if(json.Result=="OK"){
        		$("span.tag").each(function(index,value){
        			if(Number($(this).attr("value"))==json.Records.type){
        				$(this).addClass("light").siblings().removeClass("light");
        			}
        		});
        		if(json.Records.type==3){
        			$(".firLevel,.contactDiv,.tip").css("display","none");
        			$(".mtcDiv").html('<div class="mtcs"><b id="1-26124">自治区教育厅管理员</b></div>');
        		}else{
        			$(".firLevel,.contactDiv,.tip").css("display","block");
        			$(".mtcDiv").html('');
        		}
        		$("#title").val(json.Records.title);
        		ue.addListener("ready", function () {
        			ue.setContent(json.Records.content);
    	        });
        		$("#inscription").val(json.Records.inscription);
        		if(json.Records.attachment.length==0){
        			$("#forwardDiv").css("display","none");
        		}else{
    				var attachmentStr="";//附件
    				for(var j=0;j<json.Records.attachment.length;j++){
    					attachmentStr+="<p><b id='"+json.Records.attachment[j].id+"'>"+(Number(j)+1)+".</b><span>"
    					+json.Records.attachment[j].name+"</span><a onclick='deleteAttachment(this)'>删除</a></p>";
    				}
    				$("#forwardDiv").css("display","block").find(".col-sm-10 div").html(attachmentStr);
    				$("#forwardDiv p").hover(function(){
    					$(this).find("a").css("display","inline-block");
    				},function(){
    					$(this).find("a").css("display","none");
    				})
        		}
        		contact(json.Records.role,json.Records.level);
        	}else{
        		layer.confirm('参数错误', {
        			btn : [ '确定' ]
        		//按钮
        		}, function() {
        			layer.closeAll();
        		});
        	}
        }
	});
}
//删除转发附件
function deleteAttachment(obj){
	$(obj).parent().remove();
}
//联系人
function contact(role,level){
	if(role==1){
		admin();
		$("ul.firLevel li:eq(0)").addClass("light").siblings().removeClass("light");
		if(level==1){
			$(".tag1,.tag2").css("display","inline-block");
			$(".tag3").css("display","none");
			$("ul.firLevel li:eq(0),ul.firLevel li:eq(1),ul.firLevel li:eq(2),ul.firLevel li:eq(3)").css("display","block");
			$("ul.firLevel li").css("width","25%");
		}else if(level==2||level==3||level==4){
			$(".tag1,.tag2,.tag3").css("display","inline-block");
			$("ul.firLevel li:eq(0),ul.firLevel li:eq(1),ul.firLevel li:eq(2)").css("display","block");
			$("ul.firLevel li:eq(3)").css("display","none");
			$("ul.firLevel li").css("width","33.3333%");
		}
	}else if(role==2){
		teacher();
		$(".tag1,.tag3").css("display","inline-block");
		$(".tag2").css("display","none");
		$("ul.firLevel li:eq(0),ul.firLevel li:eq(1)").css("display","none");
		$("ul.firLevel li:eq(2),ul.firLevel li:eq(3)").css("display","block");
		$("ul.firLevel li").css("width","50%");
		$("ul.firLevel li:eq(2)").addClass("light").siblings().removeClass("light");
		if(id==''){
			$(".tag1").addClass("light").siblings().removeClass("light");
		}
	}
}

//发送信息
$("#form").submit(function(){
	var address="";
	$("#recipient .mtcs").each(function(i,obj){
		address+=$(this).find("b").attr("id")+",";
	});
	address=address.substring(0,address.length-1);
	if(address==""){
		layer.confirm('请添加收件人', {
			btn : [ '确定' ]
		//按钮
		}, function() {
			layer.closeAll();
		});
	}else if($("#title").val()==""){
		layer.confirm('标题不能为空', {
			btn : [ '确定' ]
		//按钮
		}, function() {
			layer.closeAll();
		});
	}else{
		var title=$("#title").val();
		var content = ue.getContent();;
		var tag=$("span.light").attr("value");
		var inscription=$("#inscription").val();
		var attachment="";
		if($("#forwardDiv").css("display")=="block"){
			$("#forwardDiv p").each(function(index,value){
				attachment+=$(this).find("b").attr("id")+",";
			});
			attachment=attachment.substring(0,attachment.length-1);
		}
		$("button[type='submit']").attr("disabled","disabled");
		$.ajax({
	        type: "POST",
	        url: "../admin/newmail_addMail.action",
	        data: {type:tag,content:content,inscription:inscription,attachment:attachment,title:title,address:address},
	        dataType: "text",
	        async: true,
	        success: function (data) {
	        	var json = (new Function("", "return " + data.split("&&&")[0]))();
	        	if(json.Result=="OK"){
	        		$("button[type='submit']").removeAttr("disabled");
	        		layer.msg('发送成功', {
	        			  icon: 1,
	        			  time: 2000 
	        			}, function(){
	        				window.location.href="mailListForSending.jsp";
	        			});   
	        	}else{
	        		$("button[type='submit']").removeAttr("disabled");
	        		layer.confirm(json.Message, {
	        			btn : [ '确定' ]
	        		//按钮
	        		}, function() {
	        			layer.closeAll();
	        		});
	        	}
	        }
		});
	}
	return false;
});
//管理员联系人
function admin(){
	if($("span.light").html()=="站内信"){
		$(".col-md-3").css("display","none");
		$(".adminLabel").css("display","block").html("管理员：");
		$("#admin .span4").css("display","none");
		
		$("#organization2").select2({
		    placeholder: "请输入联系人单位、名称或手机号",
		    minimumInputLength: 0,
			quietMillis : 1000,
			allowClear : true,
		    ajax: { 
		        url: "../admin/newmail_getConnectionInfo.action",
		        dataType: 'json',
		        data: function (term, page) {
		            return {
		            	type:1,
		            	role:1,
		                search: term, // search term
		                page_limit: 10
		            };
		        },
		        results: function (data, page) {
		            return {results: data.Records};
					
		        }
		    },
			
			initSelection: function (element, callback) {
                element = $(element);
            	  var data = {id: element.val(), DisplayText: element.attr('data-name')};
			    callback(data);
            },
		    formatResult: movieFormatResult, 
		   formatSelection: movieFormatSelection, 
			dropdownCssClass: "bigdrop",
		    escapeMarkup: function (m) { return m; } 
		})
		function movieFormatResult(Options) {
			var html = Options.DisplayText;
			return html;
			
		}
		function movieFormatSelection(Options) {
			return Options.DisplayText;
		}
		$(".select2-chosen").attr("placeholder","请输入联系人单位、名称或手机号");
		$(".select2-container .select2-choice abbr").css("display","none");
		$("#organization2").val('');
	}else{
		$.ajax({
	        type: "GET",
	        url: "../admin/newmail_getNextOrganization.action",
	        data: {},
	        dataType: "text",
	        async: true,
	        success: function (data) {
	        	var json = (new Function("", "return " + data.split("&&&")[0]))();
	        	if(json.Result=="OK"){
	        		$(".col-md-3").css("display","none");
	        		$(".adminLabel").css("display","block").html("管理员：");
	        		$("#admin select").html("").css("display","block");
	        		$(".select2-container").css("display","block");
	        		$("#admin .span3").css("display","none");
	        		var str='<option number="1" value="0" search="全部">全部</option>';
	        		$("#adminNext1,#adminNext2").css("display","none");
	        		for(var i=0;i<json.Records.length;i++){
	        			str+='<option value="'+json.Records[i].id+'" search="'+json.Records[i].name+'">'+json.Records[i].name+'</option>';
	        		}
	        		$("#admin select").html(str);
	        		$("#admin select").attr("number","1");
	        		$("#admin").find(".select2-chosen").html($("#admin select").find("option:eq(0)").html());
	        	}else{
	        		$("#admin select").html('<option number="1" value="0" search="全部">全部</option>');
	        		$("#admin select").attr("number","1");
	        	}
	        	
	        }
		});
	}
}
//承训单位
function training(){
	if($("span.light").html()=="站内信"){
		$(".col-md-3").css("display","none");
		$(".adminLabel").css("display","block").html("承训单位：");
		$("#admin .span4").css("display","none");
		
		$("#organization2").select2({
		    placeholder: "请输入联系人单位、名称或手机号",
		    minimumInputLength: 0,
			quietMillis : 1000,
			allowClear : true,
		    ajax: { 
		        url: "../admin/newmail_getConnectionInfo.action",
		        dataType: 'json',
		        data: function (term, page) {
		            return {
		            	type:1,
		            	role:2,
		                search: term, // search term
		                page_limit: 10
		            };
		        },
		        results: function (data, page) {
		            return {results: data.Records};
					
		        }
		    },
			
			initSelection: function (element, callback) {
                element = $(element);
            	  var data = {id: element.val(), DisplayText: element.attr('data-name')};
			    callback(data);
            },
		    formatResult: movieFormatResult, 
		   formatSelection: movieFormatSelection, 
			dropdownCssClass: "bigdrop",
		    escapeMarkup: function (m) { return m; } 
		})
		function movieFormatResult(Options) {
			var html = Options.DisplayText;
			return html;
			
		}
		function movieFormatSelection(Options) {
			return Options.DisplayText;
		}
		$(".select2-chosen").attr("placeholder","请输入联系人单位、名称或手机号");
		$(".select2-container .select2-choice abbr").css("display","none");
		$("#organization2").val('');
	}else{
		$.ajax({
	        type: "GET",
	        url: "../admin/newmail_searchTrainCollege.action",
	        data: {},
	        dataType: "text",
	        async: true,
	        success: function (data) {
	        	var json = (new Function("", "return " + data.split("&&&")[0]))();
	        	if(json.Result=="OK"){
	        		$(".col-md-3").css("display","none")
	        		$(".adminLabel").css("display","block").html("承训单位：");
	        		$("#admin select").html("").css("display","block");;
	        		$(".select2-container").css("display","block");
	        		$("#admin .span3").css("display","none");
	        		var str='<option number="2" value="0" search="全部">全部</option>';
	        		$("#adminNext1,#adminNext2").css("display","none");
	        		for(var i=0;i<json.Records.length;i++){
	        			str+='<option number="2" value="'+json.Records[i].id+'" search="'+json.Records[i].name+'">'+json.Records[i].name+'</option>';
	        		}
	        		$("#admin select").html(str);
	        		$("#admin").find(".select2-chosen").html($("#admin select").find("option:eq(0)").html());
	        	}else{
	        		$("#admin select").html('<option number="2" value="0" search="全部">全部</option>');
	        		$("#admin select").attr("number","2");
	        	}
	        	
	        }
		});
	}
}
//教师
function teacher(){
	if($("span.light").html()=="站内信"){
		$(".col-md-3").css("display","none");
		$(".adminLabel").css("display","block").html("教师：");
		$("#admin .span4").css("display","none");
		$("#organization2").select2({
		    placeholder: "请输入联系人单位、名称或手机号",
		    minimumInputLength: 0,
			quietMillis : 1000,
			allowClear : true,
		    ajax: { 
		        url: "../admin/newmail_getConnectionInfo.action",
		        dataType: 'json',
		        data: function (term, page) {
		            return {
		            	type:1,
		            	role:3,
		                search: term, // search term
		                page_limit: 10
		            };
		        },
		        results: function (data, page) {
		            return {results: data.Records};
					
		        }
		    },
			
			initSelection: function (element, callback) {
                element = $(element);
            	  var data = {id: element.val(), DisplayText: element.attr('data-name')};
			    callback(data);
            },
		    formatResult: movieFormatResult, 
		   formatSelection: movieFormatSelection, 
			dropdownCssClass: "bigdrop",
		    escapeMarkup: function (m) { return m; } 
		})
		function movieFormatResult(Options) {
			var html = Options.DisplayText;
			return html;
			
		}
		function movieFormatSelection(Options) {
			return Options.DisplayText;
		}
		$(".select2-chosen").attr("placeholder","请输入联系人单位、名称或手机号");
		$(".select2-container .select2-choice abbr").css("display","none");
		$("#organization2").val('');
	}else{
		$.ajax({
	        type: "GET",
	        url: "../admin/newmail_getTeacherInfo.action",
	        data: {},
	        dataType: "text",
	        async: true,
	        success: function (data) {
	        	var json = (new Function("", "return " + data.split("&&&")[0]))();
	        	if(json.Result=="OK"){
	        		$(".col-md-3").css("display","block")
	        		$(".adminLabel").css("display","block").html("培训项目：");
	        		$("#admin select").html("").css("display","block");;
	        		$(".select2-container").css("display","block");
	        		$("#admin .span3").css("display","none");
	        		var str='<option number="3" value="0" search="全部">全部</option>';
	        		$("#adminNext1,#adminNext2").css("display","block").find("select").html(str);
	        		for(var i=0;i<json.Records.length;i++){
	        			str+='<option number="3" value="'+json.Records[i].id+'" search="'+json.Records[i].name+'">'+json.Records[i].name+'</option>';
	        		}
	        		$("#admin select").html(str);
	        		$("#admin").find(".select2-chosen").html($("#admin select").find("option:eq(0)").html());
	        		$("#adminNext1").find(".select2-chosen").html($("#adminNext1 select").find("option:eq(0)").html());
	        		$("#adminNext2").find(".select2-chosen").html($("#adminNext2 select").find("option:eq(0)").html());
	        	}else{
	        		$("#admin select").html('<option number="3" value="0" search="全部">全部</option>');
	        		$("#admin select").attr("number","3");
	        	}
	        	
	        }
		});
	}
}
//专家
function expert(){
	if($("span.light").html()=="站内信"){
		$(".col-md-3").css("display","none");
		$(".adminLabel").css("display","block").html("评审专家：");
		$("#admin .span4").css("display","none");
		$("#organization2").select2({
		    placeholder: "请输入联系人单位、名称或手机号",
		    minimumInputLength: 0,
			quietMillis : 1000,
			allowClear : true,
		    ajax: { 
		        url: "../admin/newmail_getConnectionInfo.action",
		        dataType: 'json',
		        data: function (term, page) {
		            return {
		            	type:1,
		            	role:4,
		                search: term, // search term
		                page_limit: 10
		            };
		        },
		        results: function (data, page) {
		            return {results: data.Records};
					
		        }
		    },
			
			initSelection: function (element, callback) {
                element = $(element);
            	  var data = {id: element.val(), DisplayText: element.attr('data-name')};
			    callback(data);
            },
		    formatResult: movieFormatResult, 
		   formatSelection: movieFormatSelection, 
			dropdownCssClass: "bigdrop",
		    escapeMarkup: function (m) { return m; } 
		})
		function movieFormatResult(Options) {
			var html = Options.DisplayText;
			return html;
			
		}
		function movieFormatSelection(Options) {
			return Options.DisplayText;
		}
		$(".select2-chosen").attr("placeholder","请输入联系人单位、名称或手机号");
		$(".select2-container .select2-choice abbr").css("display","none");
		$("#organization2").val('');
	}else{
		$.ajax({
	        type: "GET",
	        url: "../admin/newmail_getExpertManage.action",
	        data: {},
	        dataType: "text",
	        async: true,
	        success: function (data) {
	        	var json = (new Function("", "return " + data.split("&&&")[0]))();
	        	if(json.Result=="OK"){
	        		$(".col-md-3").css("display","none")
	        		$(".adminLabel").css("display","block").html("评审专家：");
	        		$("#admin select").html("").css("display","block");;
	        		$(".select2-container").css("display","block");
	        		$("#admin .span3").css("display","none");
	        		var str='<option number="4" value="0" search="全部">全部</option>';
	        		$("#adminNext1,#adminNext2").css("display","none");
	        		for(var i=0;i<json.Records.length;i++){
	        			str+='<option number="4" value="'+json.Records[i].id+'" search="'+json.Records[i].name+'">'+json.Records[i].name+'</option>';
	        		}
	        		$("#admin select").html(str);
	        		$("#admin").find(".select2-chosen").html($("#admin select").find("option:eq(0)").html());
	        	}else{
	        		$("#admin select").html('<option number="4" value="0" search="全部">全部</option>');
	        		$("#adminNext1 select").html('<option number="4" value="0" search="全部">全部</option>');
	        		$("#adminNext2 select").html('<option number="4" value="0" search="全部">全部</option>');
	        		$("#admin select").attr("number","4");
	        	}
	        	
	        }
		});
	}
}
$("#admin select").change(function(){
	var id1=$("#admin select").val();
	$.ajax({
        type: "GET",
        url: "../admin/ttRecord_getAssignTaskOrganization.action",
        data: {projectId:id1},
        dataType: "text",
        async: true,
        success: function (data) {
        	var json = (new Function("", "return " + data.split("&&&")[0]))();
        		$("#adminNext1 select").html("");
        		var str="";
        		for(var i=0;i<json.subjects.length;i++){
        			str+='<option value="'+json.subjects[i].id+'" search="'+json.subjects[i].name+'">'+json.subjects[i].name+'</option>';
        		}
        		$("#adminNext1 select").html(str);
        		$("#adminNext1").find(".select2-chosen").html($("#adminNext1 select").find("option:eq(0)").html());
        	
        }
	});
});
$("#adminNext1 select").change(function(){
	var id1=$("#admin select").val();
	if(id1!="0"){
		$.ajax({
	        type: "GET",
	        url: "../admin/ttRecord_getAssignTaskOrganization.action",
	        data: {projectId:id1},
	        dataType: "text",
	        async: true,
	        success: function (data) {
	        	var json = (new Function("", "return " + data.split("&&&")[0]))();
	        		$("#adminNext2 select").html("");
	        		var str='';
	        		for(var i=0;i<json.trainingUnits.length;i++){
	        			str+='<option value="'+json.trainingUnits[i].id+'" search="'+json.trainingUnits[i].name+'">'+json.trainingUnits[i].name+'</option>';
	        		}
	        		$("#adminNext2 select").html(str);
	        		$("#adminNext2").find(".select2-chosen").html($("#adminNext2 select").find("option:eq(0)").html());
	        	
	        }
		});
	}
});
$(".return").click(function(){
	history.go(-1);
});




