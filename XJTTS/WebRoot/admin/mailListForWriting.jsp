<%@page import="org.apache.struts2.components.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:action name="head_init" namespace="/admin" executeResult="true"
	ignoreContextParams="false">
</s:action>
<s:action name="left_init" namespace="/admin" executeResult="true">
	<s:param name="leftName">发送编辑</s:param>
</s:action>
<link href="../css/select2.css" rel="stylesheet" />
<script src="../js/select2.js"></script>
<script src="../js/select2_locale_zh-CN.js"></script>
<script type="text/javascript" charset="utf-8" src="../js/utf8-jsp/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="../js/utf8-jsp/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="../js/utf8-jsp/lang/zh-cn/zh-cn.js"></script>
<script src="../js/upload/jquery.uploadfile.min.js"></script>
<link href="../css/iframe.css" rel="stylesheet">
<link rel="stylesheet" href="../css/uploadfile.css">
<link href="../css/mainList.css" rel="stylesheet">
<script type="text/javascript" src="../js/layer-v2.2/layer/layer.js"></script>
<script type="text/javascript" src="../js/map.js"></script>

<!-- 发送编辑 -->
<div class="main">
	<div class="list_bar" style="height:58px;">编辑信息</div>
	<div class="iconDiv">
		<a class="btn btnMyself return"> <span><img
				src="../img/return.png" alt="icon"> <b>返回</b> </span>
			<p>
				<img src="../img/lanse.png" alt="蓝色三角"> <b>返回上一页</b>
			</p>
		</a>
	</div>
	<div class="content">
		<form id="form">
		<div class="col-md-7">
			<div class="form-group">
				<label class="col-sm-2 text-right">信息类型：</label>
				<div class="col-sm-10">
					<span class="tag text-center light tag1" value="1">站内信</span><span class="tag text-center tag2" value="2">公告</span>
					<span class="tag text-center tag3" value="3">申请</span>
				</div>
				<div class="clear"></div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 text-right">收件人：</label>
				<div class="col-sm-10">
					<div id="recipient"><span class="tip">请从联系人中添加收件人</span><div class="mtcDiv"></div>
						
					</div>

				</div>
				<div class="clear"></div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 text-right">标题：</label>
				<div class="col-sm-10">
					<input class="form-control" id="title"/>
				</div>
				<div class="clear"></div>
			</div>
			
			<div class="form-group">
				<label class="col-sm-2 text-right">内容：</label>
				<div class="col-sm-10">
					<textarea id="editor" name="content" type="text/plain" style="width:100%;height:300px;"></textarea>
				</div>
				<div class="clear"></div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 text-right">附件：</label>
				<div class="attachmentBox">
					<div id="applydoc" class="">&nbsp;上传附件&nbsp;&nbsp;</div>
				</div>
				
								
					<script>
						$("#applydoc").uploadFile({
							url:"../base/fileUpload_upload.action",
							allowedTypes:"jpg,jpeg,png,txt,docx,xls,xlsx，zip,rar,",
							maxFileSize:1024*1024*10,
							fileName:"attachment",
							maxFileCount : 10,
							dragDropStr: "<span style='display:block;'><b style='font-weight:normal;color:#ccc;font-size:14px;"+''
							+"text-align:left !important;'>(上传附件最多10份，大小不超过10M)</b></span>",
							extErrorStr:"文件格式不正确，请上传word文档类型的文件",
							showDone:false,
							showDelete : false,
							deletelStr : '删除',
							doneStr: "完成",
							showAbort:false
						});
						$(".ajax-upload-dragdrop").addClass("col-sm-10").removeAttr("style");
					</script>
					<div class="clear"></div>
				</div>
			<div class="form-group" id="forwardDiv">
				<label class="col-sm-2 text-right">转发附件：</label>
				<div class="col-sm-10">
					<div style="padding:4px 12px;border:1px solid #ccc;border-radius:4px;"></div>
				</div>
				<div class="clear"></div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 text-right">落款：</label>
				<div class="col-sm-10">
					<input class="form-control" id="inscription"/>
				</div>
				<div class="clear"></div>
			</div>
			<div class="text-center">
				<button type="submit" class="contentSure btn btn-primary btn-myself">发送</button>
			</div>
		</div>
		</form>
		<div class="col-md-1 circleRight">
		
		</div>
		<div class="col-md-4 contacts">
			<h5>联系人</h5>
			<ul class="firLevel">
				<li class="light" onclick="admin();"><a>管理员</a></li>
				<li onclick="training();"><a>承训单位</a></li>
				<li onclick="teacher();"><a>教师</a></li>
				<li onclick="expert();"><a>评审专家</a></li>
				<div class="clear"></div>
			</ul>
			<div class="contactDiv">
				<div class="form-group contactDiv0">
					<div class="form-group">
						<label class="col-md-3 adminLabel text-right">培训项目：</label>
						<div class="col-md-9" id="admin">
							<select class="form-control span4">
								
							</select>
							<input name="organization2" data-val="true"  id="organization2" type="text" class="span3" >
						</div>
						<div class="clear"></div>
					</div>
					<div class="form-group">
						<label class="col-md-3 text-right">培训学科：</label>
						<div class="col-md-9" id="adminNext1" style="display:none;">
	
							<select class="form-control">
							
							</select>
						</div>
						<div class="clear"></div>
					</div>
					<div class="form-group">
						<label class="col-md-3 text-right">承训单位：</label>
						<div class="col-md-9" id="adminNext2" style="display:none;">
	
							<select class="form-control">
							
							</select>
						</div>
						<div class="clear"></div>
					</div>
					
					<div class="text-center">
						<a class="addBtn btn btn-primary btn-myself" onclick="addFilter('#recipient')">添加</a>
					</div>
					<div class="clear"></div>
				</div>			
			</div>
		</div>
		<div class="clear"></div>
	</div>
</div>

<script src="../js/mailListForWriting.js"></script>
<jsp:include page="foot.jsp"></jsp:include>