<%@page import="org.apache.struts2.components.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:action name="head_init" namespace="/admin" executeResult="true"
	ignoreContextParams="false">
</s:action>
<s:action name="left_init" namespace="/admin" executeResult="true">
	<s:param name="leftName">评审专家管理</s:param>
</s:action>
<div class="main">

	<div class="tablewrap">

		<div class="cui-menu">
			<a class="btn btn-primary btn-create" data-fancybox-type="iframe"
				href="../admin/projectExpertMana_initPage.action">+ 添加评审专家</a>
		</div>
		
		<div id="searchbox" class="searchbox" style="top:15px;">
			<form class="search search_form" id="searchForm" action="#" method="POST">
				<fieldset>
					<label for="searchheader" class="placeholder overlabel">姓名</label>
					<input autocomplete="off" id="searchheader" value="" type="text" name="q">
					<input type="hidden" name="" value="">
					<div class="show_dropdown">
						<div class="dropdown" style="display: block;">
							<ul>
								<li><label for="type_title">按姓名</label><input type="radio" id="type_title" name="stype" value="name" checked="checked"></li>
								<li><label for="type_idcard">按身份证号</label><input type="radio" id="type_idcard" name="stype" value="idcard"></li>
								
							</ul>
							<span class="bl"></span>
							<span class="br"></span>
						</div>
					</div>
					<button type="submit" id="searchBtn" class="search-button">Search</button>
				</fieldset>
			</form>
			
		</div>

		<div id="ProjectTableContainer"></div>

		<script type="text/javascript">
			$(document).ready(function() {
				$('#ProjectTableContainer')
						.jtable(
								{
									title : '<a href="../admin/projectExpertManage_initPage.action">评审专家管理</a> ',
									messages : zhCN, //Lozalize
									paging : true, //Enable paging
									pageSize : 10, //Set page size (default: 10)
									sorting : true, //Enable sorting
									defaultSorting : 'id ASC', //Set default sorting
									dialogShowEffect : 'drop',

									actions : {
										listAction : '../admin/projectExpertManage_getExpertList.action',

										deleteAction : '../admin/projectExpertManage_opProjectExpert?method=delete'

									},
									fields : {
										id : {
											title : 'ID',
											key : true,
											width : '5%'
										},
										name : {
											title : '姓名',
											width : '7%',
											inputClass : 'test',
											visibility : 'fixed'
										},
										sex : {
											title : '性别',
											width : '7%',
											options : {
												'0' : '无',
												'1' : '男',
												'2' : '女'
											}
										},
										ethnic : {
											title : '民族',
											width : '7%'
										},
										organization : {
											title : '工作单位',
											width : '10%',
											list : true
										},
										jobDuty : {
											title : '工作职务',
											width : '10%',
											list : true
										},
										teach : {
											title : '教学专长',
											width : '11%',
											list : true
										},
										research : {
											title : '研究专长',
											width : '11%',
											list : true
										},
										mobile : {
											title : '手机',
											width : '8%',
											list : true
										},
										status : {
											title : '状态',
											options : {
												'1' : '正常',
												'2' : '停用'
											},
											sorting : false,
											width : '5%'
										},
										customedit : {
											width : '5%',
											sorting : false,
											edit : false,
											create : false,
											visibility : 'fixed',
											display : function(
													data) {
												var html = '<a class="ifrbox jtable-cmd-btn" data-fancybox-type="iframe" href="../admin/projectExpertMana_initPage.action?id='
														+ data.record.id
														+ '">编辑</a>';
												return html;
											}
										},
										passwordedit : {
											title : '操作',
											width : '6%',
											sorting : false,
											visibility : 'fixed',
											display : function(data) {
												var html = '<a href="../admin/projectExpertManage_opPassword.action?id='
														+ data.record.id
														+ '">重置密码</a>';
												return html;
											}
										
										}
										
									},
									columnResizable : false,
									deleteConfirmation : function(data) {
										data.deleteConfirmMessage = '确定要停用 <b style="color:#d9534f">'
												+ data.record.name
												+ '</b> 评审专家身份?';
										$('#DeleteDialogButton').html('停用');
									},
									recordsLoaded :function (data) {
										$(".ifrbox").colorbox({
											iframe : true,
											width : "860px",
											height: "620px",
											opacity : '0.5',
											overlayClose : false,
											escKey : true
										});
										
										//删除按钮改成停用
									    $('#'+data.target.id).find('.jtable-delete-command-button').each(function(){
											$(this).attr('title','停用');
											$(this).find('span').text('停用');
										})  
										
									}

								});

						$('#ProjectTableContainer').jtable('load');
						
						//搜索
				        $('#searchForm').submit(function (e) {
				            e.preventDefault();
				            $('#ProjectTableContainer').jtable('load', {
								stype:$('input[name="stype"]:checked').val(),
				                q: $('#searchheader').val()
				               
				            });
							return false;
				        });
							
								
			});
					
			$(function() {//添加按钮
				$(".btn-create").colorbox({
					iframe : true,
					width : "860px",
					height : "620px",
					opacity : '0.5',
					overlayClose : false,
					escKey : true
				});
				
			
			})
		</script>





	</div>
</div>


<jsp:include page="foot.jsp"></jsp:include>