<%@page import="org.apache.struts2.components.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:action name="head_init" namespace="/admin" executeResult="true"
	ignoreContextParams="false">
</s:action>
<s:action name="left_init" namespace="/admin" executeResult="true">
	<s:param name="leftName">项目类型设置</s:param>
</s:action>

<div class="main">

	<div class="tablewrap">

		<div class="cui-menu">
			<button class="btn btn-primary btn-create" type="button">+
				添加项目类型</button>
		</div>

		<input id="pid" type="hidden" value="<s:property value="parentId" />">
		<input id="ptitle" type="hidden" value="<s:property value="navi" />">
		<input id="level" type="hidden" value="<s:property value="level" />">
		<input id="projectLevelStr" type="hidden"
			value="<s:property value="projectLevelString" />">
		<input id="areaStr" type="hidden" value="<s:property value="areaString" />">

		<div id="ProjectTableContainer"></div>

		<script type="text/javascript">
			$(document)
					.ready(
							
							function() {
							
							var projectLevelString = eval($('#projectLevelStr').val());
							var areaString = eval($('#areaStr').val());
							
								$('#ProjectTableContainer')
										.jtable(
												{
													title : '<a href="../admin/projectType_initPage.action">项目类型设置</a> ' + $('#ptitle').val(),
													messages : zhCN, //Lozalize
													paging : true, //Enable paging
													pageSize : 10, //Set page size (default: 10)
													sorting : true, //Enable sorting
													defaultSorting : 'id ASC', //Set default sorting
													dialogShowEffect : 'drop',
													addRecordButton : $('.btn-create'),
													actions : {
														listAction : '../admin/projectType_getProjectTypeList.action?pid=' + $('#pid').val(),
														updateAction : '../admin/projectType_opProjectType.action?method=edit',
														deleteAction : '../admin/projectType_opProjectType.action?method=delete',
														createAction : '../admin/projectType_opProjectType.action?method=add&pid=' + $('#pid').val()
													},
													fields : {
														id : {
															title : 'ID',
															key : true,
															width:'5%'
														},
														name : {
															title : '名称',
															width : '30%',
															inputClass : 'test',
															visibility : 'fixed'
														},
														shortname : {
															title : '简称',
															width : '10%'
														},
														projectLevel : {
															title : '项目级别',
															options : projectLevelString,
															width : '8%'
														},
														area : {
															title : '所属地区',
															options : areaString,
															list : false
														},
														
														level : {
															title : '层级',
															options : 
															[
														      {
														         "DisplayText":$('#level').val() + '级',
														         "Value":$('#level').val()
														      }    
															],
															width : '6%'
														},
														
														status : {
															title : '状态',
															options : {
																'1' : '正常',
																'2' : '停用'
															},
															sorting: false,
															width : '5%'
														},
														addviewchild: {
										                    title: '操作',
										                    width: '10%',
										                    sorting: false,
										                    edit: false,
										                    create: false,
															visibility : 'fixed',
										                    display: function (data) {
																var html ='<a href="../admin/projectType_initPage.action?pid=' + data.record.id +'">管理子项目</a>';
																return html;
															}
														}
													},
													columnResizable : false,
													formCreated: function (e, data) {
													    if (data.formType == "edit") {
													        data.form.find('#Edit-area').prop('disabled', true);
													    }
													},
													deleteConfirmation: function(data) {
													  data.deleteConfirmMessage = '确定要删除 <b style="color:#d9534f">' + data.record.name + '</b> 这行数据?';
													}
												});

								$('#ProjectTableContainer').jtable('load');
							});
		</script>





	</div>
</div>


<jsp:include page="foot.jsp"></jsp:include>