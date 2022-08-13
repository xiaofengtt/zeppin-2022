<%@page import="org.apache.struts2.components.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>


<s:action name="head_init" namespace="/admin" executeResult="true"
	ignoreContextParams="false">
</s:action>
<s:action name="left_init" namespace="/admin" executeResult="true">
	<s:param name="leftName">职务设置</s:param>
</s:action>

<!--main-container-part-->
<div class="main">

	<div class="tablewrap">

		<div class="cui-menu">
			<button class="btn btn-primary btn-create" type="button">+
				新建职务</button>
		</div>
		

		<div id="KemuTableContainer"></div>


		<script type="text/javascript">
			$(document)
					.ready(
							function() {
								$('#KemuTableContainer')
										.jtable(
												{
													title : '职务设置',
													messages : zhCN, //Lozalize
													paging : true, //Enable paging
													pageSize : 10, //Set page size (default: 10)
													sorting : true, //Enable sorting
													defaultSorting : 'id ASC', //Set default sorting
													dialogShowEffect : 'drop',
													addRecordButton : $('.btn-create'),
													actions : {
														listAction : '../admin/jobDuty_List.action?type=all',
														updateAction : '../admin/jobDuty_Edit.action',
														deleteAction : '../admin/jobDuty_Delete.action',
														createAction : '../admin/jobDuty_Add.action'
													},
													fields : {
														id : {
															title : 'ID',
															key : true,
															width : '5%',
														},
														name : {
															title : '名称',
															width : '95%',
															visibility : 'fixed'
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