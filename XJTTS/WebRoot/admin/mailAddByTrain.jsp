<%@page import="org.apache.struts2.components.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:action name="head_init" namespace="/admin" executeResult="true"
	ignoreContextParams="false">
</s:action>
<s:action name="left_init" namespace="/admin" executeResult="true">
	<s:param name="leftName">管理员发送站内信</s:param>
</s:action>
<div class="main">
<div class="ifrcnt container">
		<div class="hd">
			<h3>编辑信息</h3>
		</div>
		<div class="bd">
			<div class="alert alert-danger"
				style="display: none; margin: 0 15px 15px;"></div>
			<form id="formsubmit" class="form-horizontal" role="form" action="#" method="post">
				<div class="clearfix">
					<div id="HtmlTpl" class="span10">
					
						<div class="control-group">
							<label class="control-label" for="">收件人角色</label>
							<div class="controls">
								<select name="" onchange="changeSource(this)">
									<option value="0">请选择</option>
									<option value="1">项目管理员</option>
									<option value="2">承训单位</option>
									<option value="3">评审专家</option>
									<option value="4">培训教师</option>
								</select>
							</div>
						</div>
						
						<div class="control-group">
							<label class="control-label" for="">素材</label>
							<div class="controls">
								<div id="resourceId"><span class="btn btn-primary btn-create">&nbsp;上传素材&nbsp;&nbsp;</span>
								</div>
								<img id="uploadImage" width="120px" >
							</div>
						</div>
						
						<div class="control-group">
							<label class="control-label" for="">标题</label>
							<div class="controls">
								<input type="text" id="" name="title" value="">
							</div>
						</div>
						
						<div class="control-group">
							<label class="control-label" for="">民族</label>
							<div class="controls">
								<select id="nationalSelect" name="national">
									
								</select>
							</div>
						</div>

						<div class="control-group">
							<label class="control-label" for="">分类</label>
							<div class="controls">
								<div class="companylocation" style="z-index: 1;">
									<span class="clId" style="height: 22px;" id="calId"
										onclick="getCategory();changeCategorybtn($(this));"></span>
									<div id="calListBox" class="listCate">
										<div id="calList" class="list_sub sm_icon">
											<div id="cabido"></div>
										</div>
									</div>
								</div>
								<input type="hidden" id="category" name="category" value="">
							</div>
						</div>
						
						<div class="control-group">
							<label class="control-label" for="">自定义属性</label>
							<div class="controls">
								属性名 <input type="text" id="newCustomTagName" style="width:90px;margin-right:10px" value="">
								属性值 <input type="text" id="newCustomTagValue" style="width:90px;margin-right:10px" value="">
								<button type="button" onclick="addCustomTag()" class="btn btn-primary">添加属性</button>
								<div id="customTagsShow" style="margin-top:10px;">
								</div>
								<div class="hidden" id="customTagsInput">
								</div>
							</div>
						</div>
						
						<div class="control-group">
							<label class="control-label" for="">标签</label>
							<div class="controls">
								<input type="text" id="newTag" value=""> <button type="button" onclick="addTag()" class="btn btn-primary">添加标签</button>
								<div id="tagsShow" style="margin-top:10px;">
								</div>
								<div class="hidden" id="tagsInput">
								</div>
							</div>
						</div>
						
						<div class="control-group">
							<label class="control-label" for="">描述</label>
							<div class="controls">
								<textarea id="" name="comment"></textarea>
							</div>
						</div>
						
						<div class="control-group">
							<label class="control-label" for="">寓意</label>
							<div class="controls">
								<textarea id="" name="meaning"></textarea>
							</div>
						</div>
						
						<div class="control-group">
							<label class="control-label" for="">来源</label>
							<div class="controls">
								<select name="source" onchange="changeSource(this)">
									<option value="1">其他</option>
									<option value="2">来自互联网</option>
									<option value="3">现场拍照</option>
									<option value="4">出版物扫描</option>
								</select>
							</div>
						</div>
						
						<div class="control-group">
							<label class="control-label" for="">是否为实物载体</label>
							<div class="controls">
								<select name="isObject">
									<option value="0">否</option>
									<option value="1">是</option>
								</select>
							</div>
						</div>
						
						<div class="control-group" id="sourcePath" style="display:none">
							<label class="control-label" id="sourceLabel" for=""></label>
							<div class="controls">
								<input type="text" id="" name="sourcePath" value="">
							</div>
						</div>
						
						<div class="control-group" id="sourceTime" style="display:none">
							<label class="control-label" for="">采集时间</label>
							<div class="controls">
								<input type="text" id="" name="sourceTime" value="">
							</div>
						</div>
						
						<div class="control-group">
							<label class="control-label" for="">是否置顶</label>
							<div class="controls">
								<select name="eminent">
									<option value="0">否</option>
									<option value="1">是</option>
								</select>
							</div>
						</div>
						
						<div class="control-group">
							<label class="control-label" for="">状态</label>
							<div class="controls">
								<select name="status">
									<option value="1">未审核</option>
									<option value="2">审核通过</option>
									<option value="3">审核未通过</option>
								</select>
							</div>
						</div>
					</div>
				</div>

				<div class="row actionbar">
					<div class="offset7">
						<button class="btn btn-primary" type="submit">确定</button>
						<button id="colorboxcancel" onclick="parent.$.colorbox.close()"
							class="btn btn-default" type="button">取消</button>
					</div>
				</div>
			</form>
		</div>

	</div>
</div>
<script>
		var tagIndex = 0;
		var customTagIndex = 0;
		function addTag(){
			var newTag =$('#newTag').val()
			if(newTag == ''){
				alert('标签不能为空');
			}else{
				if ($('input[name="tag"]').length > 0) {// 避免添加相同的标签
					var rightid_array = [];

					$('input[name="tag"]').each(function() {
						rightid_array.push($(this).val());
					});
					
					if ($.inArray(newTag, rightid_array) > -1) {
						alert('已存在该标签');
						return;
					}
				}
				$('#tagsShow').append('<div class="mtc">' + newTag + ' <button type="button" data-id="' + tagIndex + '" onclick="deleteTag(this)" class="qwrong"></button></div>');
				$('#tagsInput').append('<input type="hidden" id="tagId_' + tagIndex + '" name="tag" value="' + newTag + '">');
				tagIndex++;
			}
		}
		function addCustomTag(){
			var newCustomTagName =$('#newCustomTagName').val();
			var newCustomTagValue =$('#newCustomTagValue').val();
			var newCustomTag = newCustomTagName + '_' + newCustomTagValue;
			if(newCustomTagName == ''){
				alert('属性名不能为空');
			}else if(newCustomTagValue == ''){
				alert('属性值不能为空');
			}else{
				if ($('input[name="customTag"]').length > 0) {// 避免添加相同的标签
					var rightid_array = [];

					$('input[name="customTag"]').each(function() {
						rightid_array.push($(this).val());
					});
					
					if ($.inArray(newCustomTag, rightid_array) > -1) {
						alert('已存在该标签');
						return;
					}
				}
				$('#customTagsShow').append('<div class="mtc">' + newCustomTagName +':' + newCustomTagValue + ' <button type="button" data-id="' + customTagIndex + '" onclick="deleteCustomTag(this)" class="qwrong"></button></div>');
				$('#customTagsInput').append('<input type="hidden" id="customTagId_' + customTagIndex + '" name="customTag" value="' + newCustomTag + '">');
				customTagIndex++;
			}
		}
		function deleteTag(t){
			$(t).parent().remove();
			$('#tagId_' + $(t).attr('data-id')).remove();
		}
		
		function deleteCustomTag(t){
			$(t).parent().remove();
			$('#customTagId_' + $(t).attr('data-id')).remove();
		}
		
		function changeSource(t){
			var source = $(t).val();
			if(source == 1){
				$('#sourcePath').css('display','none');
				$('#sourceTime').css('display','none');
			}else if(source == 2){
				$('#sourceLabel').html("链接地址");
				$('#sourcePath').css('display','block');
				$('#sourceTime').css('display','none');
			}else if(source == 3){
				$('#sourceLabel').html("采集地点");
				$('#sourcePath').css('display','block');
				$('#sourceTime').css('display','block');
			}else if(source == 4){
				$('#sourceLabel').html("出版物名称");
				$('#sourcePath').css('display','block');
				$('#sourceTime').css('display','none');
			}
		}
		
		$(function() {
			$('#formsubmit').submit(function() {
				var str = $(this).serialize();
				$.get('../resourceEdit?' + str, function(data) {
					if (data.Status == "success") {
						window.top.location.reload(true);
					} else {
						$('.alert-danger').html(data.Message).show();
					}
				})
				return false;
			});

			//项目类型 helper 函数
			$(document).on("click", function(e) {
				if (!$(e.target).parents().is('.ufc')) {
					$('.uul').hide();
				}
				if(!$(e.target).parent().is('#bido') && !$(e.target).is('#clId') && !$(e.target).parent().is('.listnode')){
					$('#clListBox').hide();
				}
			});
			
			$.get('../nationalList',function(r){
				var nationalHtml ="";
				for ( var i = 0, l = r.Records.length; i < l; i++ ) {
					nationalHtml+='<option value="'+r.Records[i].id+'">'+r.Records[i].name+'</option>';
				}
				$('#nationalSelect').html(nationalHtml);
			});
		});
		
		$("#resourceId").uploadFile({
			url:"../resourceAdd?type=1",
			allowedTypes:"jpg,png,jpeg,bmp,tiff",
			maxFileSize:1024*1024*5,
			fileName:"applyReportBook",
			maxFileCount : 1,
			dragDropStr: "",
			extErrorStr:"文件格式不正确，请上传jpg类型的图片",
			showStatusAfterSuccess:false,
			showDelete : true,
			deletelStr : '删除',
			showAbort:false,
			onSuccess:function(files,data,xhr)
			{
				if($('input[name="id"]').length > 0) {
					$('input[name="id"]').val(data.Records.id);
				}else {
					$("#resourceId").append('<input type="hidden" name="id" value="' + data.Records.id + '">');
				}
				$('#uploadImage').attr('src',data.Records.url);
			},
			deleteCallback: function (data, pd) {
	         	$.get("../resourceDelete?id="+$('input[name="id"]').val(),function (resp,textStatus, jqXHR) {
	                if(resp.Status=='success')  
						 $("#resourceId").find('input[name="id"]').remove();
					else 
						alert(resp.Message);
	             });
			}
		});
		
		function getCategory(bid,bname) {
			var cUrl = '../categoryList'
			bid = (typeof (bid) == 'undefined') ? '' : bid;
			bname = (typeof (bname) == 'undefined') ? '' : bname+'>';
			var e = (bid) ? $('#cabido' + bid) : $('#cabido');
			cUrl += (bid) ? '?pagesize=10000&parent=' + bid : '?pagesize=10000&category=&level=1';
			if (bid)
				e.css('display') == 'none' ? e.show() : e.hide();
			$.getJSON(
				cUrl,
				function(data) {
					var cLis = '';
					if (data.Records.length > 0) {
						$.each(
							data.Records,
							function(i, c) {
								emClass = (c.haschild) ? ' class="c"' : '';
								emClick = (c.haschild) ? ' onclick="getCategory(\'' + c.id+'\',\''+bname+c.name + '\');changeIcon($(this))"' : '';
								aClick = ' onclick="setCategory(\'' + c.id + '\', \'' +bname + c.name + '\')"';
								cLis += '<div class="listnode" id="' + c.id + '"><em' + emClass + emClick + '></em><a href="javascript:void(0)" ' + aClick + '>'
										+ c.name + '</a><div id="cabido' + c.id	+ '" class="cSub" style="display:none">加载中...</div></div>';
							});
					}
					e.html(cLis)
				});
		}

		function setCategory(id, name) {
			$('#cabido').html('');
			$('#calId').html(name);
			$('#category').val(id);
			$('.listCate').hide();
		}
		
		function changeCategorybtn(e) {
			e.next('.listCate').toggle();
		}
		
	</script>

<jsp:include page="foot.jsp"></jsp:include>