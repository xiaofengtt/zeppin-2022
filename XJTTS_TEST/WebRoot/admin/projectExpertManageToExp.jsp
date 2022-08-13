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
											title : '操作',
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
			});
		</script>





	</div>
</div>


<jsp:include page="foot.jsp"></jsp:include>