<%@page import="org.apache.struts2.components.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:action name="head_init" namespace="/admin" executeResult="true"
	ignoreContextParams="false">
</s:action>
<s:action name="left_init" namespace="/admin" executeResult="true">
	<s:param name="leftName">学员报到及成绩管理</s:param>
</s:action>
<div class="main">

	<div class="tablewrap">

		<div class="cui-menu">
			
			<button type="button" id="batchcheckin" class="btn btn-primary">批量报到</button>
			<div class="dropdown" style="display:inline-block">
			  <button  type="button" data-toggle="dropdown" class="btn btn-primary">
			  	  批量分班
			   <span class="caret"></span>
			  </button>
			  <ul id="batchclass" class="dropdown-menu" role="menu">
				  <li><a data-class="1" href="#">一班</a></li>
				  <li><a data-class="2" href="#">二班</a></li>
				  <li><a data-class="3" href="#">三班</a></li>
				  <li><a data-class="4" href="#">四班</a></li>
				  <li><a data-class="5" href="#">五班</a></li>
				  <li><a data-class="6" href="#">六班</a></li>
				  <li><a data-class="7" href="#">七班</a></li>
				  <li><a data-class="8" href="#">八班</a></li>
				  <li><a data-class="9" href="#">九班</a></li>
				  <li><a data-class="10" href="#">十班</a></li>
				
			  </ul>
			</div>
		</div>
		<div class="hidden">
			<input type="hide" name="checkinids" value="">
		</div>
		<div id="ProjectTableContainer">
		
		</div>

		<script type="text/javascript">
			$(document)
					.ready(
							function() {
								$('#ProjectTableContainer')
										.jtable(
												{
													title : '<a href="../admin/trainingStudentOpt_initPage.action">承训单位学员报到管理 :</a> <s:date name="projectApply.trainingStarttime" format="yyyy"/> > <s:property value="projectApply.project.name"></s:property> > <s:property value="projectApply.trainingSubject.name"></s:property>',
													messages : zhCN, //Lozalize
													paging : true, //Enable paging
													pageSize : 10, //Set page size (default: 10)
													sorting : true, //Enable sorting
													defaultSorting : 'year DASC', //Set default sorting
													dialogShowEffect : 'drop',
													selecting: true, //Enable selecting
													selectOnRowClick : false,
										            multiselect: true, //Allow multiple selecting
										            selectingCheckboxes: true, //Show checkboxes on first column

													actions : {
														listAction : '../admin/trainingStudentOpt_getRegistList.action?id=<s:property value="projectApply.id"></s:property>'

													},
													fields : {
														id : {
															title : 'ID',
															key : true,
															sorting:false,
															list:false
														},
														name : {
															title : '姓名',
															width : '9%',
															list : true,
															sorting:false
														},
														idCard : {
															title : '身份证',
															list : true,
															sorting:false
															
															
														},
														phone : {
															title : '电话',
															width : '7%',
															list : true,
															sorting:false
														},
														
														sex : {
															title : '性别',
															list : true,
															sorting:false,
															width: '5%'
														},
														age : {
															title : '年龄',
															list : true,
															sorting:false,
															width: '5%'
														},
														jobTitle : {
															title : '职务',
															list : true,
															sorting:false
														},
														organazation : {
															title : '单位',
															list : true,
															sorting:false
														},
														REGISTTIME : {
															title : '报到时间',
															list : true,
															width: '9%'
														},
														TRAINING_STATUS : {
															title : '状态',
															list : true,
															width: '7%'
														},
														classes : {
															title : '班级',
															list : true,
															sorting:false,
															width: '5%'
														},
														customedit : {
															title : '操作',
															width : '13%',
															sorting : false,
															edit : false,
															create : false,
															visibility : 'fixed',
															display : function(
																	data) {
																var html = '<select onchange="checkin(this)" class="form-control" name="status" id="status"><option value=-1>请选择</option><option value=2>已报到</option><option value=1>未报到</option><option value=0>异动</option></select>';
																return html;
															}
														}
													},
													columnResizable : false,
													
													recordsLoaded :function (data) {
														$(".ifrbox").colorbox({
															iframe : true,
															width : "860px",
															height: "550px",
															opacity : '0.5',
															overlayClose : false,
															escKey : true
														});
														
													
													},
													//Register to selectionChanged event to hanlde events
										            selectionChanged: function () {
										               
										                var $selectedRows = $('#ProjectTableContainer').jtable('selectedRows');
										                if ($selectedRows.length > 0) {
										                   var record = [];
										                    $selectedRows.each(function () {
										                        record.push( $(this).data('record').id ); 
										                    });
															$('input[name="checkinids"]').val(record.join(','))
															
										                }
										            }

												});
									$('#ProjectTableContainer').jtable('load');
										
								
										
							});
			
							
							function checkin (e) {
								var th = $(e),
								status = th.val(),
								id = th.parents('tr').attr('data-record-key');
								$.get('../admin/trainingStudentOpt_changeStudentStatus.action?id='+ id + '&status=' + status,function(data){
									if(data.Result == 'OK') {
										location.reload();
									}else {
										alert(data.Message);
									}
								})
									
							}
							
							$('#batchcheckin').click(function(){//批量审核
							 	var $selectedRows = $('#ProjectTableContainer').jtable('selectedRows');
								if ($selectedRows.length > 0) {
									var id = $('input[name="checkinids"]').val();
									$.get('../admin/trainingStudentOpt_changeStudentStatus.action?ids='+ id +'&status=2', function(data){
										if(data.Result == 'OK') {
											//window.location.href = window.location.href;
											location.reload();
										}
									})
								}else {
				                    alert('至少选择一条数据');
									return;
								}
								
							});
							$('#batchclass li a').click(function(){//批量分班
							 	var $selectedRows = $('#ProjectTableContainer').jtable('selectedRows'),classes = $(this).data('class');
								if ($selectedRows.length > 0) {
									var id = $('input[name="checkinids"]').val();
									$.get('../admin/trainingStudentOpt_editClasses.action?ids='+ id + '&classes='+classes, function(data){
										if(data.Result == 'OK') {
											location.reload();
										}
									})
								}else {
				                    alert('至少选择一条数据');
									return;
								}
								
							})
							
							
			
			
			
		</script>





	</div>
</div>


<jsp:include page="foot.jsp"></jsp:include>