<%@page import="org.apache.struts2.components.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>


<s:action name="head_init" namespace="/admin" executeResult="true"
	ignoreContextParams="false">
</s:action>
<s:action name="left_init" namespace="/admin" executeResult="true">
	<s:param name="leftName">学科设置</s:param>
</s:action>

<!--main-container-part-->
<div class="main">

	<div class="tablewrap">

		<div class="cui-menu">
			<button class="btn btn-primary btn-create" type="button">+
				新建学科</button>
		</div>
		<div id="searchbox" class="searchbox" style="top:15px;">
			<form class="search search_form" action="/cloudadmin/" method="get">
				<fieldset>
					<label for="searchheader" class="placeholder overlabel">学科名称</label>
					<input autocomplete="off" id="searchheader" value="" type="text" name="q">
					<input type="hidden" name="" value="">
					<div class="show_dropdown">
						<div class="dropdown" style="display: block;">
							<ul>
								<li><label for="type_title">按学科名称</label><input type="radio" name="stype" value="name" checked="checked">
								</li>
								
							</ul>
							<span class="bl"></span>
							<span class="br"></span>
						</div>
					</div>
					<button type="submit" id="searchOrg" class="search-button">Search</button>
				</fieldset>
			</form>
			
		</div>

		<div id="KemuTableContainer"></div>


		<script type="text/javascript">
			$(document)
					.ready(
							function() {
								$('#KemuTableContainer')
										.jtable(
												{
													title : '学科设置',
													messages : zhCN, //Lozalize
													paging : true, //Enable paging
													pageSize : 10, //Set page size (default: 10)
													sorting : true, //Enable sorting
													defaultSorting : 'id ASC', //Set default sorting
													dialogShowEffect : 'drop',
													addRecordButton : $('.btn-create'),
													actions : {
														listAction : '../admin/subject_List.action?type=all',
														updateAction : '../admin/subject_Edit.action',
														deleteAction : '../admin/subject_Delete.action',
														createAction : '../admin/subject_Add.action'
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