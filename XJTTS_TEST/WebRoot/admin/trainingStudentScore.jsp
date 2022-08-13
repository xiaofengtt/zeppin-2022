<%@page import="org.apache.struts2.components.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:action name="head_init" namespace="/admin" executeResult="true"
	ignoreContextParams="false">
</s:action>
<s:action name="left_init" namespace="/admin" executeResult="true">
	<s:param name="leftName">承训单位学员成绩管理</s:param>
</s:action>
<div class="main">
<div class="tablewrap">
<div class="cui-menu" style="width:100%">
	<form id="aduTaskTeacher" class="form-horizontal"  action="../admin/trainingStudentOpt_outputScore.action">
		<input type="hidden" name="id" value="<s:property value="projectApply.id"></s:property>" >
				<div class="form-group">
					<label class="col-sm-2 control-label" style="width:60px" for="">搜索</label>
					<div class="col-sm-6" style="width:130px">
						<select class="form-control" style="width:115px" id="searchFor">
							<option value="classes" selected="selected">按班级</option>
							<option value="name">按姓名</option>
							<option value="idcard">按身份证号</option>
						</select>
					</div>
					<div class="col-sm-6" style="width:400px" id="searchInfo">
						<select class="form-control" name="classes" style="width:300px">
							<option value="0">全部</option>
							<option value="1">一班</option>
							<option value="2">二班</option>
							<option value="3">三班</option>
							<option value="4">四班</option>
							<option value="5">五班</option>
							<option value="6">六班</option>
							<option value="7">七班</option>
							<option value="8">八班</option>
							<option value="9">九班</option>
							<option value="10">十班</option>
						</select>
					</div>
				<button class="btn btn-primary" id="findRecord" type="button">查询</button>&nbsp&nbsp&nbsp
				<button type="submit" id="output" class="btn btn-primary">导出学员成绩</button>&nbsp&nbsp&nbsp
				<a class="ifrmbox" href="../admin/trainingStudentOpt_inputInit.action" data-fancybox-type="iframe" >
				<button type="button" id="output" class="btn btn-primary">上传学员成绩</button></a>
				</div>	
		</form>				
	</div>

		<div id="ProjectTableContainer">
		
		</div>

		<script type="text/javascript">
		$(function() {//添加按钮
			$(".btn-create,.ifrmbox").colorbox({
				iframe : true,
				width : "860px",
				height : "400px",
				opacity : '0.5',
				overlayClose : false,
				escKey : true
			});
		})
		
			$(document)
					.ready(
							function() {
								$('#ProjectTableContainer')
										.jtable(
												{
													title : '<a href="../admin/trainingStudentOpt_initPage.action">承训单位学员成绩管理 :</a> <s:date name="projectApply.trainingStarttime" format="yyyy"/> > <s:property value="projectApply.project.name"></s:property> > <s:property value="projectApply.trainingSubject.name"></s:property>',
													messages : zhCN, //Lozalize
													paging : true, //Enable paging
													pageSize : 10, //Set page size (default: 10)
													sorting : true, //Enable sorting
													defaultSorting : 'year DASC', //Set default sorting
													dialogShowEffect : 'drop',
													selecting: true, //Enable selecting
													selectOnRowClick : false,
										            multiselect: true, //Allow multiple selecting
										            //selectingCheckboxes: true, //Show checkboxes on first column

													actions : {
														listAction : '../admin/trainingStudentOpt_getScoreList.action?id=<s:property value="projectApply.id"/>'

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
															width : '8%',
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
														
														
														organazation : {
															title : '单位',
															list : true,
															sorting:false
														},
														classHoure : {
															title : '获得学时',
															list : true,
															sorting:false,
															width: '7%'
														},
														score : {
															title : '成绩',
															list : true,
															sorting:false,
															width: '4%'
														},
														classes : {
															title : '班级',
															list : true,
															sorting:false,
															width: '4%'
														},
														trainingStatus : {
															title : '状态',
															list : true,
															width: '5%'

														},
														customedit : {
															title : '操作',
															width : '10%',
															sorting : false,
															edit : false,
															create : false,
															visibility : 'fixed',
															display : function(
																	data) {
																if(data.record.trainingStatus == '未报到'){
																	var html = '<a  href="javascript:alert(\'未报到学员不能进行此操作\')">评定/修改成绩</a>  <a href="javascript:alert(\'未报到学员不能进行此操作\')">结业</a>';
																}else{
																	var html = '<a target="_blank" href="../paper/paper_view.action?pid='+data.record.pid+'&sid='+data.record.sid+'&tc='+data.record.tc+'&type=2&valuator='+data.record.tid+'&id='+data.record.ttid+'"">评定/修改成绩</a>  <a class="ifrbox" data-fancybox-type="iframe" href="../admin/trainingStudentOpt_completeInit.action?id='
																	+ data.record.id
																	+ '">结业</a>';
																}
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
										
									//点击查询按钮事件
									$('#findRecord').click(function (e) {
										var search = $('#searchFor').children('option:selected').val();
											if(search == "classes"){
								        	   $('#ProjectTableContainer').jtable('load', {
									            	classes:$('select[name="classes"]').val()

									            });
												return false;
								           }else if(search == "name"){
								        	   var searchInfo = $('input[name="name"]').val();
								        	   //验证姓名是否合法
// 								        	   var regex = /^[\u4e00-\u9fa5]+[\.]{0,1}[\·]{0,1}[\u4e00-\u9fa5]+$/;
								        	   var regex = /^[\S]{0,}$/;//验证姓名是否包含空白字符
								        	   if(!regex.test(searchInfo)){
								        		   alert("您输入的姓名格式不正确,请重新输入!");
								        		   return false;
								        	   }
								        	   
								        	   $('#ProjectTableContainer').jtable('load', {
									            	name:searchInfo

									            });
												return false;
								           }else if(search == "idcard"){
								        	   var searchInfo = $('input[name="idcard"]').val();
								        	   //验证身份证号是否合法
// 								        	   var regex = /^(^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$)|(^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[Xx])$)$/;
 											   var regex = /^[\S]{0,}$/;//验证身份证号是否包含空白字符
								        	   var month = searchInfo.substr(10,2);
								        	   var day = searchInfo.substr(12,2);
								        	   
								        	   if(!regex.test(searchInfo)||month>12||day>31){
								        	   		alert('请输入正确的身份证号码！');
								        	   		return false;
								        	   }
								        	   
								        	   $('#ProjectTableContainer').jtable('load', {
								        		   idCard:searchInfo

									            });
												return false;
								           }
								      });	

									  //变动查询条件
									  $('#searchFor').change(function(){
										  var search = $(this).children('option:selected').val();
// 										  alert(search);
										  if(search == "name"){
											  $('#searchInfo').empty();
											  $('#searchInfo').append('<input class="form-control input_font" type="text" name="name" style="width:300px" />');
										  }else if(search == "idcard"){
											  $('#searchInfo').empty();
											  $('#searchInfo').append('<input class="form-control input_font" type="text" name="idcard" style="width:300px" />');
										  }else if(search == "classes"){
											  $('#searchInfo').empty();
											  $('#searchInfo').append('<select class="form-control" name="classes" style="width:300px">'
													  +'<option value="0">全部</option>'
													  +'<option value="1">一班</option>'
													  +'<option value="2">二班</option>'
													  +'<option value="3">三班</option>'
													  +'<option value="4">四班</option>'
													  +'<option value="5">五班</option>'
													  +'<option value="6">六班</option>'
													  +'<option value="7">七班</option>'
													  +'<option value="8">八班</option>'
													  +'<option value="9">九班</option>'
													  +'<option value="10">十班</option></select>');
										  }
									  });
										
							});
			
							
							function checkin (e) {
							
								var status = $(e).val(),
								id = $(e).parents('tr').attr('data-record-key');
								$.get('../admin/trainingStudentOpt_changeStudentStatus.action?id='+ id + '&status=' + status,function(data){
									
								})
									
							}
	
			
			
			
		</script>





	</div>
</div>


<jsp:include page="foot.jsp"></jsp:include>