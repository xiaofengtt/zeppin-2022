<%@page import="org.apache.struts2.components.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:action name="head_init" namespace="/admin" executeResult="true"
	ignoreContextParams="false">
</s:action>
<s:action name="left_init" namespace="/admin" executeResult="true">
	<s:param name="leftName">查看上级管理员</s:param>
</s:action>
<style type="text/css">
div.jtable-main-container table.jtable{margin-top:15px;}
</style>
<div class="main">

	<div class="tablewrap">
		
		<div id="searchbox" class="searchbox" style="top:15px;">
			<form class="search search_form" id="searchForm" action="#" method="POST">
				<fieldset>
					<label for="searchheader" class="placeholder overlabel">姓名</label>
					<input autocomplete="off" id="searchheader" value="" type="text" name="q">
					<input type="hidden" name="" value="">
					<div class="show_dropdown">
						<div class="dropdown" style="display: block;">
							<ul>
								<li><label for="type_title">按姓名</label><input type="radio" id="type_title" name="stype" value="name" checked="checked">
								</li>
								<li><label for="type_organization">按工作单位</label><input type="radio" id="type_organization" name="stype" value="organization">
								</li>
								<li><label for="type_idcard">按身份证号</label><input type="radio" id="type_idcard" name="stype" value="idcard">
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
									title : '<a href="../admin/projectAdmin_higherAdmin.action">查看上级管理员</a> ',
									messages : zhCN, //Lozalize
									paging : true, //Enable paging
									pageSize : 10, //Set page size (default: 10)
									sorting : true, //Enable sorting
									defaultSorting : 'id ASC', //Set default sorting
									dialogShowEffect : 'drop',

									actions : {
										listAction : '../admin/projectAdmin_getHigherAdminList.action',
									},
									fields : {
										id : {
											title : 'ID',
											key : true,
											width : '5%'
										},
										name : {
											title : '姓名',
											width : '10%',
											inputClass : 'test',
											visibility : 'fixed'
										},
										sex : {
											title : '性别',
											width : '6%',
											options : {
												'0' : '无',
												'1' : '男',
												'2' : '女'
											}
										},
										ethnic : {
											title : '民族',
											width : '6%'
										},
										organization : {
											title : '工作单位',
											width : '13%',
											list : true
										},
										department : {
											title : '工作部门',
											width : '13%',
											list : true
										},
										jobDuty : {
											title : '工作职务',
											width : '11%',
											list : true
										},
										phone : {
											title : '办公电话',
											width : '5%',
											list : true
										},
										mobile : {
											title : '手机',
											width : '5%',
											list : true
										}
										
									},
									columnResizable : false,
									recordsLoaded :function (data) {
										$(".ifrbox").colorbox({
											iframe : true,
											width : "860px",
											height: "620px",
											opacity : '0.5',
											overlayClose : false,
											escKey : true
										});
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
		</script>





	</div>
</div>


<jsp:include page="foot.jsp"></jsp:include>