/**
 * Created by thanosYao on 2015/7/22.
 */
$(document).ready(function(){
    $("#header").load("header.html");
    $("#footer").load("footer.html");
});
$(function(){
	basicInfo();
	attachment();
	reply(id,1);
	$(".delete").click(function(){
		var mids;
		if(types=="1"){
			mids=mid;
		}else if(types=="2"){
			mids=id;
		}	
		layer.confirm('您确定要删除吗？', {
			  btn: ['确定','取消'] //按钮
			}, function(){
				$.get('../teacher/tinfo_deletemail.action?id='+ mids+'&type='+types, function(r) {
					if (r.Result == 'OK') {
						layer.msg('删除成功！', {
		        			  icon: 1,
		        			  time: 2000 
		        			}, function(){
		        				if(types=="1"){
		        					window.location.href="mailListForTeacher.html";
		        				}else if(types=="2"){
		        					window.location.href="mailListForTeacher.html";
		        				}		        				
		        		});   
						
					} else {
						layer.confirm(r.Message, {
		        			btn : [ '确定' ]
		        		//按钮
		        		}, function() {
		        			layer.closeAll();
		        		});
					}
				})
			}, function(){
			  layer.closeAll();
			});
	});
});
var id = (url('?id') != null) ? url('?id') : '';
var types= url('?type');
var mid= url('?mid');
//获取基本信息
function basicInfo(){
	$.ajax({
        type: "GET",
        url: "../teacher/tinfo_getInfo.action",
        data: {id:id},
        dataType: "text",
        async: true,
        success: function (json) {
        	var data = (new Function("", "return " + json.split("&&&")[0]))();
        	if(data.Result=='OK'){
        		var type;
        		$(".title").html(data.Records.title);
        		$(".contents").html(data.Records.content);
        		$(".inscribe").html(data.Records.inscription);
        		$(".data").html(data.Records.createtime);
        		if(data.Records.type==1){
    				type='站内信';
    			}else if(data.Records.type==2){
    				type='公告';
    			}else if(data.Records.type==3){
    				type='申请';
    			}
        		$(".letterType").html(type);
        	}else{
        		layer.confirm(data.Message, {
    				btn : [ '确定' ]
    			//按钮
    			}, function() {
    				layer.closeAll();
    			});
        	}
        }
	});
}
//附件
function attachment(){
	$.ajax({
        type: "GET",
        url: "../teacher/tinfo_getAttachmentList.action",
        data: {id:id},
        dataType: "text",
        async: true,
        success: function (json) {
        	var data = (new Function("", "return " + json.split("&&&")[0]))();
        	if(data.Result=="OK"){
        		if(data.Records.length==0){
        			$(".attachmentDiv").css("display","none");
        		}else{
        			var attachmentStr="";//附件
    				for(var j=0;j<data.Records.length;j++){
    					attachmentStr+="<p><b>"+(Number(j)+1)+".</b><span>"+data.Records[j].name+"</span><a href='../"
    					+data.Records[j].path+"' target='_blank'>下载</a></p>";
    				}
    				$(".attachmentDiv").css("display","block").append(attachmentStr);
    				$(".attachmentDiv p").hover(function(){
    					$(this).find("a").css("display","inline-block");
    				},function(){
    					$(this).find("a").css("display","none");
    				});
        		}
        		
        	}
        }
	});
}

//回复列表
function reply(id,page){
	$.ajax({
        type: "GET",
        url: "../teacher/tinfo_getReplaList.action",
        data: {id:id,status:1,page:page},
        dataType: "text",
        async: true,
        success: function (data) {
        	var json = (new Function("", "return " + data.split("&&&")[0]))();
        	var str="";
        	if(json.Result=="OK"){
        		if(json.TotalCount==0){
        			str="<p class='text-center' style='color:#999;font-size:16px;line-height:50px;margin-bottom:0;'>暂无回复内容</p>";
        		}else{
        			for(var i=0;i<json.Records.length;i++){
        				str+='<div class="avatars"><img class="img-circle" src="../img/applyMan.png" alt="头像"></div><div class="contentDivs">'+''+
            			'<p class="identitys">'+json.Records[i].organization+'-'+json.Records[i].creator+'</p><div class="dataBox">'
            			+''+'<a class="deleteChild showAll">显示全部</a><span>'
            			+json.Records[i].createtime+'</span></div>'+''+
            			'<div class="clear"></div><p class="applyContent clamp">'+json.Records[i].content+'</p></div>';
        			}
        			str+='<div id="paginations" style="float: right;margin-right:10px;" class="pull-right pagination-sm"></div><div class="clear"></div></div>';
        			
        		}
        		$(".reply .replayDiv").html(str);
        		var totalPage = Math
    			.ceil(json.TotalCount /5);
    			if(totalPage==0){
    				totalPage=1;
    			}
    			var options = {
    				currentPage : page,
    				totalPages : totalPage,
    				shouldShowPage : function(type, page,
    						current) {
    					switch (type) {
    					default:
    						return true;
    					}
    				},
    				onPageClicked : function(e, originalEvent,
    						type, page) {
    					reply(id,page);
    				}
    	
    			}
    			$('#paginations').bootstrapPaginator(options);
        	}
        }
	}).done(function(){
		$(".contentDivs").width($(".repliedItems").width()-$(".avatars").outerWidth(true)-10+"px");
		$(this).find(".repliedItems .contentDivs:last").css("border-bottom","none");
		$(".repliedItems .contentDivs").hover(function(){
			$(".deleteChild").css("display","none");
			$(this).find(".deleteChild").css("display","inline-block");
		},function(){
			$(".deleteChild").css("display","none");
		});
		$(".showAll").click(function(){
			if($(this).parent().parent().find(".applyContent").hasClass("clamp")){
				$(this).html("点击收起");
				$(this).parent().parent().find(".applyContent").removeClass("clamp");
				$(this).parent().parent().find(".applyInscription").css("display","block");
			}else{
				$(this).html("显示全部");
				$(this).parent().parent().find(".applyContent").addClass("clamp");
				$(this).parent().parent().find(".applyInscription").css("display","none");
			}
		});
	});
}

//删除
function doDelete(obj,ids,type,isadd) {
	if(type==3){
		if(isadd==true){
			layer.confirm('您确定要删除吗？', {
				  btn: ['确定','取消'] //按钮
				}, function(){
					$.get('../teacher/tinfo_deletemail.action?id='+ ids+'&type='+type, function(r) {
						if (r.Result == 'OK') {
							layer.msg('删除成功！', {
			        			  icon: 1,
			        			  time: 2000 
			        			}, function(){
			        				reply(id,1);
			        		});   
							
						} else {
							layer.confirm(r.Message, {
			        			btn : [ '确定' ]
			        		//按钮
			        		}, function() {
			        			layer.closeAll();
			        		});
						}
					})
				}, function(){
				  layer.closeAll();
				});
		}else{
			layer.confirm('此条回复不能删除', {
    			btn : [ '确定' ]
    		//按钮
    		}, function() {
    			layer.closeAll();
    		});
		}
	}
}


