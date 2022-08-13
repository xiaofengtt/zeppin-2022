<%@page import="org.apache.struts2.components.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:action name="head_init" namespace="/admin" executeResult="true"
	ignoreContextParams="false">
</s:action>
<s:action name="left_init" namespace="/admin" executeResult="true">
	<s:param name="leftName">组织架构设置</s:param>
</s:action>

<div class="main">

	<div class="tablewrap">
		<div class="cui-menu"><button class="btn btn-primary btn-create" type="button">+ 添加组织架构</button>
		<a class="btn-search" data-fancybox-type="iframe"  href="../admin/organization_initSearchPage.action"><button class="btn btn-primary" type="button" >组织架构查询</button></a>
		</div>
		
		
				<input id="pid" type="hidden" value="<s:property value="parentId" />">
				<input id="ptitle" type="hidden" value="<s:property value="navi" />">
				<input id="organizationLevelString" type="hidden" value="<s:property value="organizationLevelStr" />">
			
				<div id="OrgTableContainer"></div>
				
				<script type="text/javascript">
				    $(document).ready(function () {
				    	var oraganizationLevelString = eval($('#organizationLevelString').val());
				        $('#OrgTableContainer').jtable({
							title:'<a href="../admin/organization_initPage.action">组织架构设置</a> ' + $('#ptitle').val(),
							 messages: zhCN, //Lozalize
							paging: true, //Enable paging
							pageSize: 10, //Set page size (default: 10)
							sorting: true, //Enable sorting
							defaultSorting: 'id ASC', //Set default sorting
							dialogShowEffect :'drop',
							addRecordButton : $('.btn-create'),
				            actions: {
				                listAction: '../admin/organization_getOrganizationList.action?pid=' + $('#pid').val(),
				                updateAction: '../admin/organization_opOrganization.action?method=edit',
				                deleteAction: '../admin/organization_opOrganization.action?method=delete',
								 createAction: '../admin/organization_opOrganization.action?method=add&pid=' + $('#pid').val()
				            },
				            fields: {
				                id: {
									title: 'ID',
				                    key: true,
									width:'5%'
				                },
				                name: {
				                    title: '单位名称',
				                    width: '20%',
									visibility : 'fixed'
				                },
								shortName:{
				                    title: '简称',
									list : false
				                },
								adress:{
				                    title: '地址',
									list : false
				                },
				                organizationLevel: {
				                    title: '部门级别',
									options: oraganizationLevelString
				                },
				                area: {
				                    title: '直辖市/省/自治区',
									width:'15%',
				                    options: '../base/area_getArea.action?provinceId=0',
				                    list: true
				                },
				                area1: {
				                    title: '地区/市/州',
				                    dependsOn: 'area', 
				                    options: function (data) {
				                        return '../base/area_getArea.action?cityId=' + data.dependedValues.area;
				                    },
				                    list: false
				                },
				                area2: {
				                    title: '县/区/市',
				                    width: '10%',
				                    dependsOn: 'area1', 
				                    options: function (data) {
				                        return '../base/area_getArea.action?countyId=' + data.dependedValues.area1;
				                    },
				                    list:false
				                },
				                
				                contacts: {
				                    title: '联系人',
									width: '10%'
				                    
				                },
				                phone: {
				                    title: '联系电话',
									width: '10%'
				                    
				                },
				                fax: {
				                    title: '办公电话',
									width: '14%'
				                    
				                },
								
				                status: {
				                    title: '状态',
									options: { '1': '正常', '2': '停用' },
				                    width: '10%'
				                },
								isschool: {
									title: '机构性质',
				                   options: { '0': '教育管理部门', '1': '教师派出学校' },
								    defaultValue: '0',
									list:false
				                },
								grade: {
									title:'学段',
									dependsOn : 'isschool',
				                    options:function(data) {
										if (data.source != 'list') {
											var thisbox = data.form.find('select[name="grade"]');
											if(data.dependedValues['isschool'] == 0) {
												thisbox.parents(".jtable-input-field-container").first().hide();
											}else {
												thisbox.parents(".jtable-input-field-container").first().show();
											}	
										}
										return  '../admin/grade_getGrade.action'; 
									},
				                   
									list:false
				                },
								
								addviewchild: {
				                    title: '操作',
				                    width: '10%',
				                    sorting: false,
				                    edit: false,
				                    create: false,
				                    display: function (data) {
										if(data.record.isschool == 0) {
											var html ='<a href="../admin/organization_initPage.action?pid=' + data.record.id +'">管理下级部门</a>';
										}else {
											var html = '';
										}
										return html;
									}
									
								}
				            },
							columnResizable : false,
							deleteConfirmation: function(data) {
							  data.deleteConfirmMessage = '确定要删除 <b style="color:#d9534f">' + data.record.name + '</b> 这行数据?';
							},
							formCreated: function (e, data) {
					
								if(data.record.isschool == 0) {
									 data.form.find('select[name="grade"]').parents(".jtable-input-field-container").first().hide();
								}
							    
							}
				        });
						
						$('#OrgTableContainer').jtable('load');
						
						
						
				    });
				    $(function() {//添加按钮
						$(".btn-search").colorbox({
							iframe : true,
							width : "450px",
							height : "400px",
							opacity : '0.5',
							overlayClose : false,
							escKey : true
						});
						
					
					})
				</script>
			

	</div>
</div>


<jsp:include page="foot.jsp"></jsp:include>