<%@page import="org.apache.struts2.components.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:action name="head_init" namespace="/admin" executeResult="true"
	ignoreContextParams="false">
</s:action>
<s:action name="left_init" namespace="/admin" executeResult="true">
	<s:param name="leftName">项目评审</s:param>
</s:action>
<div class="main">
	<div class="tablewrap">
		<div id="ProjectTableContainer"></div>
		<script type="text/javascript">
			$(document).ready(function() {
				$('#ProjectTableContainer')
						.jtable(
								{
									title : '<a href="../admin/projectReview_initPage.action">项目评审</a> ',
									messages : zhCN, //Lozalize
									paging : true, //Enable paging
									pageSize : 10, //Set page size (default: 10)
									defaultSorting : 'id ASC', //Set default sorting
									dialogShowEffect : 'drop',

									actions : {
										listAction : '../admin/projectReview_getProjectList.action'
									},
									fields : {
										paid : {
											title : 'ID',
											key : true,
											width : '5%'
										},
										year : {
											title : '年份',
											width : '4%',
											inputClass : 'test',
											visibility : 'fixed'
										},
										project : {
											title : '项目',
											width : '10%',
											inputClass : 'test',
											visibility : 'fixed'
										},
										subject : {
											title : '学科',
											width : '10%',
											inputClass : 'test',
											visibility : 'fixed'
										},
										training : {
											title : '承训院校',
											width : '10%',
											inputClass : 'test',
											visibility : 'fixed'
										},
										startTime : {
											title : '培训开始时间',
											width : '9%',
											inputClass : 'test',
											visibility : 'fixed'
										},
										endTime : {
											title : '培训结束时间',
											width : '9%',
											inputClass : 'test',
											visibility : 'fixed'
										},
										classHour : {
											title : '学时',
											width : '4%',
											inputClass : 'test',
											visibility : 'fixed'
										},
										report:{
											title : '申报书',
											width : '5%',
											visibility : 'fixed',
											display : function(data){
												if (data.record.path!=""){
												var html='<a href="..'+ data.record.path +'">下载</a>'
												}else{
													html='<a>无</a>'
												}												
												return html;
											}
										},
										score : {
											title : '评分',
											width : '4%',
											inputClass : 'test',
											visibility : 'fixed'
										},
										customedit : {
											title : '操作',
											width : '4%',
											sorting : false,
											edit : false,
											create : false,
											visibility : 'fixed',
											display : function(data) {
												var html = '<a target="_blank" href="../paper/paper_view.action?pid='
														+ data.record.pid +'&sid=' + data.record.sid +'&tc=' + data.record.tc +'&type=' 
														+ data.record.type +'&id=' + data.record.id +'&valuator=' + data.record.valuator 
														+ '">评分</a>';
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