<%@page import="org.apache.struts2.components.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>


<s:action name="head_init" namespace="/admin" executeResult="true"
	ignoreContextParams="false">
</s:action>
<s:action name="left_init" namespace="/admin" executeResult="true">
	<s:param name="leftName">学段设置</s:param>
</s:action>

<!--main-container-part-->
<div class="main">

	<div class="tablewrap">

		<div class="cui-menu">
			<button class="btn btn-primary btn-create" type="button">+
				新建学段</button>
		</div>
		

		<div id="KemuTableContainer"></div>


		<script type="text/javascript">
			$(document)
					.ready(
							function() {
								$('#KemuTableContainer')
										.jtable(
												{
													title : '学段设置',
													messages : zhCN, //Lozalize
													paging : true, //Enable paging
													pageSize : 10, //Set page size (default: 10)
													sorting : true, //Enable sorting
													defaultSorting : 'id ASC', //Set default sorting
													dialogShowEffect : 'drop',
													addRecordButton : $('.btn-create'),
													actions : {
														listAction : '../admin/grade_List.action?type=all',
														updateAction : '../admin/grade_Edit.action',
														deleteAction : '../admin/grade_Delete.action',
														createAction : '../admin/grade_Add.action'
													},
													fields : {
														id : {
															title : 'ID',
															key : true,
															width : '5%',
														},
														name : {
															title : '名称',
															width : '85%',
															visibility : 'fixed'
														},
														isSchool : {
															title : '教师可选',
															options : {
																'0' : '是',
																'1' : '否'
															},
															width : '10%'
														}
														
													},
													columnResizable : false,
													deleteConfirmation : function(
															data) {
														data.deleteConfirmMessage = '确定要删除 <b style="color:#d9534f">'
																+ data.record.name
																+ '</b> 这行数据?';
													}
												});

								$('#KemuTableContainer').jtable('load');
								
								
								
								//搜索
						        $('#searchOrg').click(function (e) {   
								  	e.preventDefault();
						            $('#KemuTableContainer').jtable('load', {
										stype:$('input[name="stype"]:checked').val(),
						                q: $('#searchheader').val()
						               
						            });
									return false
						        });
								
								
							});
		</script>

	</div>
</div>

<jsp:include page="foot.jsp"></jsp:include>