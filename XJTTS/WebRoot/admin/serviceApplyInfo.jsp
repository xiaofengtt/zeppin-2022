<%@page import="org.apache.struts2.components.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:action name="head_init" namespace="/admin" executeResult="true"
	ignoreContextParams="false">
</s:action>
<s:action name="left_init" namespace="/admin" executeResult="true"
	ignoreContextParams="true">
</s:action>
<script type="text/javascript" src="../js/layer-v2.2/layer/layer.js"></script>
<link rel="stylesheet" href="../css/iframe.css">
<style>
.list_bar{height:58px;}
#reprtTask{display:none;}
.table-bordered{border:none;}
.list-item-bd{border-color:#e6e6e6;}
table tr{background:#fff;}
td.left{text-align:left;padding:8px 30px !important;font-size:15px;}
.text-primary{color:#333333 !important;}
.table-bordered > thead > tr > th, .table-bordered > tbody > tr > th, .table-bordered > tfoot > tr > th, 
.table-bordered > thead > tr > td, .table-bordered > tbody > tr > td, .table-bordered > tfoot > tr > td{border:none;}
td.right{font-size:15px;text-align:right;padding-right:20px !important;}
td.right div.reply{display:inline;margin:0 11px;color:#808080 !important;cursor:pointer;}
td.right div.reply:hover{color:#418bca !important;text-decoration: underline; }
td.right a{margin:0 11px;color:#808080 !important;cursor:pointer;font-size:15px;}
td.right a:hover{color:#418bca !important;}
.replycontent{color:#1a1a1a;font-size:14px;line-height:22px;margin:15px 0 20px 0;padding:0px 30px;max-height:44px;display:
 -webkit-box;-webkit-box-orient: vertical;-webkit-line-clamp: 2;word-break: break-all;overflow: hidden;text-indent:2em;}
.col-xs-6{color:#999999;padding:0 30px;line-height:20px;font-size:14px;}
td.right div.reply:hover td{color:#808080;}
td.right div.reply:hover th{color:#808080;}
.scorelist{position:absolute;right:80px;width:600px;display:none;z-index:999;text-align:left;cursor:auto;}
.scorelist .replycontent{text-indent:0em;padding:8px;}
.scorelist .col-xs-12{color:#999999;padding:0 0px;line-height:22px;margin:0;font-size:15px;}
.scorelist .bd{padding:9px 14px;}

.Applicant{font-size:14px;color:#c2c2c2;float:right;}
.applyTime{font-size:14px;color:#bfbfbf;width:150px;float:right;margin-right:30px;}
.list-item-bd{border:none;border-bottom:1px solid #e6e6e6;}
.col-xs-12 a{font-size:14px;margin:10px;cursor:pointer;}
.col-xs-12 a.btn-create{color:#418bca;}
.col-xs-12 a.delbtn{color:#808080;margin-right:30px;}

/* ??????????????? */

.clear{clear:both;}
.label{position:absolute;top:41px;right:53px;z-index:2;}
.label span{margin:3px;display:inline-block;color:#333;font-size:14px;height:30px;line-height:30px;padding:0 7px;background-color:#dae7f5;}
.applyDiv{border:1px solid #e6e6e6;position:relative;padding-bottom:20px;margin-bottom:10px;}
.light{border-color:#418bca;}
.avatar{padding:34px 23px 0 29px;float:left;}
.avatar img{width:48px;}
.contentDiv{padding-top:30px;float:left;}
.contentDiv h5,.identity{font-size:18px;color:#333;font-family:"??????";margin-bottom:7px;}
.applyType{color:#4489ce;font-size:16px;line-height:40px;}
.applyContent{color:#333;font-size:14px;line-height:24px;}
.clamp{display: -webkit-box;-webkit-box-orient: vertical;-webkit-line-clamp: 2;word-break: break-all;overflow: hidden;}
.applyInscription{display:none;}
.button{float:left;}
.button a{color:#808080;font-size:14px;line-height:30px;display:inline-block;padding-right:25px;cursor:pointer;}
a.replied{background:url(../img/rarrow.png) 63px center no-repeat;}
a.answer{background:url(../img/rarrow.png) 35px center no-repeat;}
a.delete{padding-right:10px;}
.dataBox{float:right;color:#bfbfbf;padding-top:10px;line-height:25px;}
.artboard{display:block;top:1px;left:11px;position:relative;}
.alreadyReply{display:none;}
.repliedDiv{border:1px solid #d0d0d0;}
.avatars{padding:9px;float:left;}
.avatars img{width:25px;}
.contentDivs{float:left;border-bottom:1px solid #e6e6e6;padding-bottom:20px;}
.identitys{color:#bfbfbf;font-size:14px;line-height:25px;padding-top:10px;float:left;}
.contentDivs .applyContent{}

.artboards{display:block;top:1px;left:73px;position:relative;}
.answerDiv{border:1px solid #d0d0d0;padding:20px 16px 10px 20px;}
.answerDiv input{width:100%;height:32px;background:#fafafa;border:1px solid #d9d9d9;font-size:14px;color:#333333;}
.answerDiv .operation{text-align:right;padding:8px 0 0px 0;}
.answerDiv .operation a{margin:0 10px;cursor:pointer;font-size:14px;color:#333333;}
.answerDiv .operation a.sendBtn{color:#418bca;}
.response{display: none;}
.deleteChild{color:#bfbfbf;margin-right:10px;cursor:pointer;display:none;}
.deleteChild:hover{color:#666;}
.searchbox{top:15px;}
.iconDiv{right:220px;}

</style>
<div class="main">
	<div class="tablewrap">	
		<div id="searchbox" class="searchbox">
			<form class="search search_form" id="searchForm" action="#" method="POST">
				<fieldset>
					<label for="searchheader" class="placeholder overlabel">????????????</label>
					<input autocomplete="off" id="searchheader" value="" type="text" name="q">
					<input type="hidden" name="" value="">
					<div class="show_dropdown">
						<div class="dropdown" style="display: block;">
							<ul>
								<li><label for="type_title"></label><input type="radio" id="type_title" name="stype" value="content" 
									checked="checked">
								</li>
							</ul>
							<span class="bl"></span>
							<span class="br"></span>
						</div>
					</div>
					<button type="submit" id="searchBtn" class="search-button">Search</button>
				</fieldset>
			</form>		
		</div>
	</div>
	<div class="list_bar" style="height:58px;"></div>
	<div class="iconDiv">
		<a class="btn btnMyself btn-create"
			data-fancybox-type="iframe"
			href="../admin/serviceApply_addServiceApplyInit.action?val="> <span><img
				src="../img/kaishexiangmu.png" alt="icon"> <b>??????</b> </span>
			<p>
				<img src="../img/lanse.png" alt="????????????"> <b>????????????</b>
			</p>
		</a>

	</div>
	<div class="cui-menu2" style="position: relative">
	</div>
	<div id="list-content" class="list-content clearfix">
	</div>
	<div id="pagination" style="float: right;" class="pull-right"></div>
</div>

<script>
$(window).resize(function(){
	$(".contentDiv").width($(".applyDiv").width()-$(".avatar").outerWidth(true)-50+"px");
	$(".contentDivs").width($(".contentDiv").width()-$(".avatars").outerWidth(true)-10+"px");
})
	$(function() {
		getTable();
	});
	
	function doDelete(id,type,isadd) {
		if(type==3){
			if(isadd==true){
				layer.confirm('????????????????????????', {
					  btn: ['??????','??????'] //??????
					}, function(){
						$.get('../admin/newmail_delete.action?id='+ id+'&type='+type, function(r) {
							if (r.Result == 'OK') {
								layer.msg('???????????????', {
				        			  icon: 1,
				        			  time: 2000 
				        			}, function(){
				        				$(obj).parent().parent().parent().parent().parent().parent().find(".replied").click();
				        		});   
								
							} else {
								layer.confirm(r.Message, {
				        			btn : [ '??????' ]
				        		//??????
				        		}, function() {
				        			layer.closeAll();
				        		});
							}
						})
					}, function(){
					  layer.closeAll();
					});
			}else{
				layer.confirm('????????????????????????', {
        			btn : [ '??????' ]
        		//??????
        		}, function() {
        			layer.closeAll();
        		});
			}
		}else{
			layer.confirm('????????????????????????', {
			  btn: ['??????','??????'] //??????
			}, function(){
				$.get('../admin/newmail_delete.action?id='+ id+'&type='+type, function(r) {
					if (r.Result == 'OK') {
						layer.msg('???????????????', {
		        			  icon: 1,
		        			  time: 2000 
		        			}, function(){
		        				window.location.href = window.location.href;			        				
		        		});   
						
					} else {
						layer.confirm(r.Message, {
		        			btn : [ '??????' ]
		        		//??????
		        		}, function() {
		        			layer.closeAll();
		        		});
					}
				})
			}, function(){
			  layer.closeAll();
			});
		}
	}
	

	$(function() {
		var type = url('?type');
		var status = $('.stateDiv a.light').attr("value");
	})
	$("#searchBtn").click(function(){
		getTable();
		return false;
	});
	var page = (url('?page') != null) ? url('?page') : '1';	
//????????????
function getTable(){
	var content=$("#searchheader").val().replace(/(^\s*)|(\s*$)/g, "");
	$.ajax({
        type: "GET",
        url: "../admin/newmail_getInboxList.action",
        data: {content:content,page:page,status:0},
        dataType: "text",
        async: true,
        success: function (json) {
        	var data = (new Function("", "return " + json.split("&&&")[0]))();
        	if(data.Result=='OK'){
        		var str="";
        		var type;
        		for(var i=0;i<data.Records.length;i++){
        			if(data.Records[i].type==1){
        				type='?????????';
        			}else if(data.Records[i].type==2){
        				type='??????';
        			}else if(data.Records[i].type==3){
        				type='??????';
        			}
        			str+='<div class="applyDiv"><div class="label"><span>'+type+
        			'</span></div><div class="avatar"><img class="img-circle" src="../img/applyMan.png" alt="??????">'+''+
        			'</div><div class="contentDiv"><h5>'+data.Records[i].creator+
        			'</h5><p class="identity">'+data.Records[i].organization+
        			'</p><p class="applyType">'+data.Records[i].title+
        			'</p><p class="applyContent clamp">'+data.Records[i].content+
        			'</p><p class="applyInscription text-right">'+data.Records[i].inscription+
        			'</p><div class="button"><div><div><div><div><a class="replied" name='+data.Records[i].id+'>????????????</a>'+''+
        			'<a class="answer">??????</a><a class="delete" onclick="doDelete('+data.Records[i].id+',1)">??????</a>'+''+
        			'<a class="showAll">????????????</a></div></div></div></div></div><div class="dataBox">'+data.Records[i].createtime+'</div>'+''+
        			'<div class="clear"></div><div class="alreadyReply"><img class="artboard" src="../img/artboard_03.png" />'+''+
        			'<div class="repliedDiv"><div class="repliedItems"><div class="answerDiv" style="border:none"><input class="answerInputs" value=""/>'+''+
        			'<div class="operation"><a class="sendBtn" onclick="answer(this,'+data.Records[i].mid+',\'answerInputs\')">??????</a>'+''+
        			'<a class="cancelBtn">??????</a></div></div><div class="replayDiv"></div></div></div></div><div class="response">'+''+
        			'<img class="artboards" src="../img/artboard_03.png" /><div class="answerDiv"><div><div><input class="answerInput" value=""/>'+''+
        			'<div class="operation"><a class="sendBtn" onclick="answer(this,'+data.Records[i].mid+',\'answerInput\')">??????</a>'+''+
        			'<a class="cancelBtn">??????</a></div></div></div></div></div></div><div class="clear"></div></div>';
        		}
        		$("#list-content").html(str);
        		var totalPage = Math
    			.ceil(data.TotalCount / 10);
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
    					window.location = updateURLParameter(
    							url(), 'page', page);
    				}
    	
    			}
    			$('#pagination').bootstrapPaginator(options);
        	}
        }
	}).done(function(){
		$(".contentDiv").width($(".applyDiv").width()-$(".avatar").outerWidth(true)-50+"px");
		$(".showAll").click(function(){
			if($(this).parent().parent().find(".applyContent").hasClass("clamp")){
				$(this).html("????????????");
				$(this).parent().parent().find(".applyContent").removeClass("clamp");
				$(this).parent().parent().find(".applyInscription").css("display","block");
			}else{
				$(this).html("????????????");
				$(this).parent().parent().find(".applyContent").addClass("clamp");
				$(this).parent().parent().find(".applyInscription").css("display","none");
			}
		});
		$(".replied").click(function(){
			$(this).css({"background":"url(../img/rarrow-active.png) 63px center no-repeat","color":"#4489ce"});
			$(this).parent().parent().parent().parent().parent().parent().find(".alreadyReply").css("display","block");
			$(".contentDivs").width($(".contentDiv").width()-$(".avatars").outerWidth(true)-10+"px");
			$(this).parent().parent().parent().parent().parent().parent().find(".response").css("display","none");
			$(this).siblings(".answer").css({"background":"url(../img/rarrow.png) 35px center no-repeat","color":"#808080"});
			reply(this,$(this).attr("name"),1);
		});
		$(".answer").click(function(){
			$(this).css({"background":"url(../img/rarrow-active.png) 35px center no-repeat","color":"#4489ce"});
			$(this).parent().parent().parent().parent().parent().parent().find(".response").css("display","block");		
			$(this).parent().parent().parent().parent().parent().parent().find(".alreadyReply").css("display","none");
			$(this).siblings(".replied").css({"background":"url(../img/rarrow.png) 63px center no-repeat","color":"#808080"});
		});
	});	
}
//????????????
function reply(obj,id,page){
	$.ajax({
        type: "GET",
        url: "../admin/newmail_getReplaList.action",
        data: {id:id,status:1,page:page},
        dataType: "text",
        async: true,
        success: function (data) {
        	var json = (new Function("", "return " + data.split("&&&")[0]))();
        	var str="";
        	if(json.Result=="OK"){
        		if(json.TotalCount==0){
        			str="<p class='text-center' style='color:#999;font-size:16px;line-height:50px;margin-bottom:0;'>??????????????????</p>";
        		}else{
        			for(var i=0;i<json.Records.length;i++){
        				str+='<div class="avatars"><img class="img-circle" src="../img/applyMan.png" alt="??????"></div><div class="contentDivs">'+''+
            			'<p class="identitys">'+json.Records[i].organization+'-'+json.Records[i].creator+'</p><div class="dataBox">'
            			+''+'<a class="deleteChild showAll">????????????</a><a class="deleteChild" onclick="doDelete('
            					+json.Records[i].id+',3,'+json.Records[i].isadd+')">??????</a><span>'
            			+json.Records[i].createtime+'</span></div>'+''+
            			'<div class="clear"></div><p class="applyContent clamp">'+json.Records[i].content+'</p></div>';
        			}
        			str+='<div id="paginations" style="float: right;margin-right:10px;" class="pull-right pagination-sm"></div><div class="clear"></div></div>';
        			
        		}
        		$(obj).parent().parent().parent().parent().parent().parent().find(".replayDiv").html(str);
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
    					reply(obj,id,page);
    				}
    	
    			}
    			$('#paginations').bootstrapPaginator(options);
        	}
        }
	}).done(function(){
		$(".contentDivs").width($(".contentDiv").width()-$(".avatars").outerWidth(true)-10+"px");
		$(obj).parent().parent().find(".repliedItems .contentDivs:last").css("border-bottom","none");
		$(obj).parent().parent().parent().parent().parent().parent().find(".repliedItems .contentDivs").hover(function(){
			$(".deleteChild").css("display","none");
			$(this).find(".deleteChild").css("display","inline-block");
		},function(){
			$(".deleteChild").css("display","none");
		});
		$(".showAll").click(function(){
			if($(this).parent().parent().find(".applyContent").hasClass("clamp")){
				$(this).html("????????????");
				$(this).parent().parent().find(".applyContent").removeClass("clamp");
				$(this).parent().parent().find(".applyInscription").css("display","block");
			}else{
				$(this).html("????????????");
				$(this).parent().parent().find(".applyContent").addClass("clamp");
				$(this).parent().parent().find(".applyInscription").css("display","none");
			}
		});
	});
}
//????????????
function answer(obj,id,className){
	var content=$(obj).parent().parent().find("."+className).val().replace(/(^\s*)|(\s*$)/g, "");
	if(content==""){
		layer.confirm('????????????????????????', {
			btn : [ '??????' ]
		//??????
		}, function() {
			layer.closeAll();
		});
		return false;
	}else{
		$.ajax({
	        type: "GET",
	        url: "../admin/newmail_addReplay.action",
	        data: {id:id,content:content},
	        dataType: "text",
	        async: true,
	        success: function (data) {
	        	var json = (new Function("", "return " + data.split("&&&")[0]))();
	        	if(json.Result=="OK"){
	        		layer.msg('???????????????', {
	        			  icon: 1,
	        			  time: 2000 
	        			}, function(){
	        				$(".answerInputs,.answerInput").val("");
	        				$(obj).parent().parent().parent().parent().parent().parent().find(".replied")
	        				.css({"background":"url(../img/rarrow-active.png) 63px center no-repeat","color":"#4489ce"});
	        				$(obj).parent().parent().parent().parent().parent().parent().find(".alreadyReply").css("display","block");
	        				$(".contentDivs").width($(".contentDiv").width()-$(".avatars").outerWidth(true)-10+"px");
	        				$(".response").css("display","none");
	        				$(".answer").css({"background":"url(../img/rarrow.png) 35px center no-repeat","color":"#808080"});
	        				reply(obj,id,1);
	        		});   
	        	}else{
	        		layer.confirm(json.Message, {
	        			btn : [ '??????' ]
	        		//??????
	        		}, function() {
	        			layer.closeAll();
	        		});
	        		return false;
	        	}
	        }
	    });
	}
	
}



</script>
<jsp:include page="foot.jsp"></jsp:include>
