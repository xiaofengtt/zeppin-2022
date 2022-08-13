<%@page import="org.apache.struts2.components.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:action name="head_init" namespace="/admin" executeResult="true"
	ignoreContextParams="false">
</s:action>
<s:action name="left_init" namespace="/admin" executeResult="true">
	<s:param name="leftName">收件箱列表</s:param>
</s:action>
<div class="main">

	<div class="tablewrap">
		
		<div class="cui-menu">
		</div>
		
		<div id="searchbox" class="searchbox">
			<form class="search search_form" id="searchForm" action="#" method="POST">
				<fieldset>
					<label for="searchheader" class="placeholder overlabel">模糊搜索</label>
					<input autocomplete="off" id="searchheader" value="" type="text" name="q">
					<input type="hidden" name="" value="">
					<div class="show_dropdown">
						<div class="dropdown" style="display: block;">
							<ul>
								<li><label for="type_title"></label><input type="radio" id="type_title" name="stype" value="content" checked="checked">
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

		<div id="ProjectTableContainer"></div>

		<script type="text/javascript">
			$(document).ready(function() {
				$('#ProjectTableContainer')
						.jtable(
								{
									title : '收件箱列表',
									messages : zhCN, //Lozalize
									paging : true, //Enable paging
									pageSize : 10, //Set page size (default: 10)
									sorting : true, //Enable sorting
									defaultSorting : 'id ASC', //Set default sorting
									dialogShowEffect : 'drop',

									actions : {
										listAction : '../admin/mail_getInboxListForProjectAdmin.action',
										deleteAction : '../admin/mail_changeStatus.action?method=input'
									},
									fields : {
										id : {
											title : '',
											key : true,
											width : '0%',
											sorting:false,
											list:false
										},
										index : {
											title : '序号',
											width : '5%',
											list : true,
											sorting:false
										},
										title : {
											title : '标题',
											width : 'auto',
											inputClass : 'test',
											visibility : 'fixed'
										},
										creator : {
											title : '发件人',
											width : 'auto',
											list : true,
											sorting:false
										},
										creattime : {
											title : '',
											width : 'auto',
											list : true,
											sorting:false
										},
										customedit : {
											title : '操作',
											width : '6%',
											sorting : false,
											edit : false,
											create : false,
											visibility : 'fixed',
											display : function(data) {
												var html = '<a class="jtable-cmd-btn" href="../admin/mail_getInboxMailInformationInit.action?id='
														+ data.record.id
														+ '">查看</a>';
												return html;
											}
										
										},
									},
									columnResizable : false,
									deleteConfirmation : function(data) {
										data.deleteConfirmMessage = '确定要删除这条信息？';
									}

								});

						$('#ProjectTableContainer').jtable('load');
						
						//搜索
				        $('#searchForm').submit(function (e) {
				            e.preventDefault();
				            $('#ProjectTableContainer').jtable('load', {
				                content: $('#searchheader').val()
				               
				            });
							return false;
				        });
							
								
			});
					
			$(function() {//添加按钮
				
			
			})
		</script>





	</div>
</div>


<jsp:include page="foot.jsp"></jsp:include>