<%@page import="org.apache.struts2.components.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>


<s:action name="head_init" namespace="/admin" executeResult="true"
	ignoreContextParams="false">
</s:action>
<s:action name="left_init" namespace="/admin" executeResult="true">
	<s:param name="leftName">承训单位设置</s:param>
</s:action>

<!--main-container-part-->
<div class="main">

	<div class="tablewrap">

		<div class="cui-menu">
			<button class="btn btn-primary btn-create" type="button">+
				新建承训单位</button>
		</div>
		
		<div id="searchbox" class="searchbox">
			<form class="search search_form" action="/cloudadmin/" method="get">
				<fieldset>
					<label for="searchheader" class="placeholder overlabel">单位名称</label>
					<input autocomplete="off" id="searchheader" value="" type="text" name="q">
					<input type="hidden" name="" value="">
					<div class="show_dropdown">
						<div class="dropdown" style="display: block;">
							<ul>
								<li><label for="type_title">按单位名称</label><input type="radio" name="stype" value="name" checked="checked">
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

		<input id="areaStr" type="hidden"
			value="<s:property value="areaString" />">

		<div id="KemuTableContainer"></div>


		<script type="text/javascript">
			$(document)
					.ready(
							function() {
								var areaString = eval($('#areaStr').val());
								$('#KemuTableContainer')
										.jtable(
												{
													title : '承训单位设置',
													messages : zhCN, //Lozalize
													paging : true, //Enable paging
													pageSize : 10, //Set page size (default: 10)
													sorting : true, //Enable sorting
													defaultSorting : 'id ASC', //Set default sorting
													dialogShowEffect : 'drop',
													addRecordButton : $('.btn-create'),
													actions : {
														listAction : '../admin/trainingCollege_getTrainingCollegeList.action?type=all',
														updateAction : '../admin/trainingCollege_opTrainingCollege.action?method=edit',
														deleteAction : '../admin/trainingCollege_opTrainingCollege.action?method=delete',
														createAction : '../admin/trainingCollege_opTrainingCollege.action?method=add'
													},
													fields : {
														id : {
															title : 'ID',
															key : true,
															width : '5%',
														},
														name : {
															title : '单位名称',
															width : '15%',
															visibility : 'fixed'
														},
														shortName : {
															title : '单位简称',
															width : '15%',
															visibility : 'fixed'
														},
														projectLevel : {
															title : '可承训项目级别',
															options : {
																'1' : '国家级',
																'2' : '自治区级',
																'3' : '地/市/州级',
																'4' : '县/区/市级',
																'5' : '校本级'
															},
															width : '15%'
														},
														area : {
															title : '地区',
															options : areaString
														},
														contacts : {
															title : '联系人',
															width : '10%'

														},
														phone : {
															title : '联系电话',
															width : '10%'

														},
														adress : {
															title : '地址',
															width : '10%',
															list : false

														},
														fax : {
															title : '传真',
															width : '10%',
															list : false

														},
														status : {
															title : '状态',
															options : {
																'1' : '正常',
																'2' : '停用'
															},
															width : '5%'
														},
														trainURL : {
															title : '远程培训地址链接',
															width : '10%',
															list : false
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