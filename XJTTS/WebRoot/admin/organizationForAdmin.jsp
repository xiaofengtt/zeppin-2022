<%@page import="org.apache.struts2.components.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:action name="head_init" namespace="/admin" executeResult="true"
	ignoreContextParams="false">
</s:action>
<s:action name="left_init" namespace="/admin" executeResult="true">
	<s:param name="leftName">组织架构管理</s:param>
</s:action>
<style>
div.jtable-main-container > table.jtable{margin-top:15px;}
div.jtable-main-container > table.jtable > thead th{text-align:center;}
div.jtable-main-container > table.jtable > tbody > tr.jtable-data-row > td{text-align:center;}
div.jtable-main-container > table.jtable > thead th.jtable-column-header div.jtable-column-header-container{height:auto;}
</style>
<div class="main">
<div class="iconDiv">
<s:if test="orgId==0">
	<a class="btn btnMyself btn-create">
		<span><img src="../img/kaishexiangmu.png" alt="icon">
			<b>添加</b>
		</span>
		<p>
			<img src="../img/lanse.png" alt="蓝色三角">
			<b>添加组织架构</b>
		</p>
	</a>
</s:if>
</div>
	<div class="tablewrap">
<%-- 		<div class="cui-menu"><s:if test="orgId==0"><button class="btn btn-primary btn-create" type="button">添加组织架构</button></s:if> --%>
<!-- 		<a class="btn-search" data-fancybox-type="iframe"  href="../admin/organization_initSearchPage.action"><button class="btn btn-primary" type="button" >组织架构查询</button></a> -->
<!-- 		</div> -->
		
		
				<input id="pid" type="hidden" value="<s:property value="parentId" />">
				<input id="oid" type="hidden" value="<s:property value="orgId" />">
				<input id="ptitle" type="hidden" value="<s:property value="navi" />">
				<input id="organizationLevelString" type="hidden" value="<s:property value="organizationLevelStr" />">
			
				<div id="OrgTableContainer"></div>
				
				<script type="text/javascript">
				    $(document).ready(function () {
				    	var oraganizationLevelString = eval($('#organizationLevelString').val());
				        $('#OrgTableContainer').jtable({
							title:'<a href="../admin/organization_initPageForAdmin.action">组织架构设置</a> ' + $('#ptitle').val(),
							 messages: zhCN, //Lozalize
							paging: true, //Enable paging
							pageSize: 10, //Set page size (default: 10)
							sorting: true, //Enable sorting
							defaultSorting: 'id ASC', //Set default sorting
							dialogShowEffect :'drop',
							addRecordButton : $('.btn-create'),
				            actions: {
				                listAction: '../admin/organization_getOrganizationListForAdmin.action?pid=' + $('#pid').val(),
				                updateAction: '../admin/organization_opOrganization.action?method=edit',
				                deleteAction: '../admin/organization_opOrganization.action?method=delete',
								 createAction: '../admin/organization_opOrganization.action?method=add&pid=' + $('#pid').val()
				            },
				            fields: {
				            	id: {
									title: 'ID',
				                    key: true,
									width:'8%'
				                },
				                otherscode: {
				                    title: '标识码',
				                    width: '8%',
									visibility : 'fixed',
									list: true,
									edit: false
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
				                    edit:false,
									options: oraganizationLevelString
				                },
				                isschool: {
									title: '机构性质',
				                   options: { '0': '教育管理部门', '1': '教师派出学校' },
								    defaultValue: '0'
				                },
								grade: {
									title:'学校类型',
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
										return  '../admin/organization_getGrade.action'; 
									}
				                },
				                organizerName: {
				                    title: '举办者类型',
				                    width: '20%',
				                    dependsOn : 'isschool',
									visibility : 'fixed',
									options: function(data) {
										if (data.source != 'list') {
											var thisbox = data.form.find('select[name="organizerName"]');
											if(data.dependedValues['isschool'] == 0) {
												thisbox.parents(".jtable-input-field-container").first().hide();
											}else {
												thisbox.parents(".jtable-input-field-container").first().show();
											}	
										}
										return  '../admin/organization_getOrganizerName.action'; 
									},
									list: false
				                },
				                dictype: {//{ '0': '无', '1': '公办', '2' : '民办' }
				                    title: '办别',
				                    width: '20%',
				                    dependsOn : 'isschool',
									visibility : 'fixed',
									options: function(data) {
										if (data.source != 'list') {
											var thisbox = data.form.find('select[name="dictype"]');
											if(data.dependedValues['isschool'] == 0) {
												thisbox.parents(".jtable-input-field-container").first().hide();
											}else {
												thisbox.parents(".jtable-input-field-container").first().show();
											}	
										}
										return { '0': '无', '1': '公办', '2' : '民办' }; 
									},
									list: false
				                },
				                ftype: {//{ '0': '无', '1': '城市', '2' : '农村' }
				                    title: '城乡分类',
				                    width: '20%',
				                    dependsOn : 'isschool',
									visibility : 'fixed',
									options: function(data) {
										if (data.source != 'list') {
											var thisbox = data.form.find('select[name="ftype"]');
											if(data.dependedValues['isschool'] == 0) {
												thisbox.parents(".jtable-input-field-container").first().hide();
											}else {
												thisbox.parents(".jtable-input-field-container").first().show();
											}	
										}
										return { '0': '无', '1': '主城区', '2' : '城乡结合区', '3' : '镇中心区', '4' : '镇乡结合区', '5' : '特殊区域', '6' : '乡中心区', '7' : '村庄', '8' : '乡村' }; 
									},
									list: false
				                },
				                isPoor: {//{ '0': '否', '1': '是' }
				                    title: '是否为集中连片特困地区县',
				                    dependsOn : 'organizationLevel',
									options: function(data) {
										if (data.source != 'list') {
											var thisbox = data.form.find('select[name="isPoor"]');
											if(data.dependedValues['organizationLevel'] != 3) {
												thisbox.parents(".jtable-input-field-container").first().hide();
											}else {
												thisbox.parents(".jtable-input-field-container").first().show();
											}	
										}
										return { '0': '否', '1': '是' }; 
									},
				                    width: '10%',
				                    list: false
				                },
				                isCountryPoor: {
				                    title: '是否为国家级贫困县',
				                    dependsOn : 'organizationLevel',
									options: function(data) {
										if (data.source != 'list') {
											var thisbox = data.form.find('select[name="isCountryPoor"]');
											if(data.dependedValues['organizationLevel'] != 3) {
												thisbox.parents(".jtable-input-field-container").first().hide();
											}else {
												thisbox.parents(".jtable-input-field-container").first().show();
											}	
										}
										return { '0': '否', '1': '是' }; 
									},
				                    width: '10%',
				                    list: false
				                },
// 				                attribute: {
// 				                    title: '学校地域属性',
// 				                    dependsOn : 'isschool',
// 				                    options: function(data) {
// 				                    	if (data.source != 'list') {
// 											var thisbox = data.form.find('select[name="attribute"]');
// 											if(data.dependedValues['isschool'] == 0) {
// 												thisbox.parents(".jtable-input-field-container").first().hide();
// 											}else {
// 												thisbox.parents(".jtable-input-field-container").first().show();
// 											}	
// 										}
// 				                    	return "../admin/organization_getAttribute.action";
// 				                    },
// 				                    width: '10%',
// 				                    list: false
// 				                },
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
				                area3: {
				                    title: '镇/乡',
				                    width: '10%',
				                    dependsOn: 'area2', 
				                    options: function (data) {
				                        return '../base/area_getArea.action?townId=' + data.dependedValues.area2;
				                    },
				                    list:false
				                },
				                area4: {
				                    title: '村',
				                    width: '10%',
				                    dependsOn: 'area3', 
				                    options: function (data) {
				                        return '../base/area_getArea.action?villageId=' + data.dependedValues.area3;
				                    },
				                    list:false
				                },
				                
				                contacts: {
				                    title: '联系人',
				                    list:false,
									width: '10%'
				                    
				                },
				                phone: {
				                    title: '联系电话',
				                    list:false,
									width: '10%'
				                    
				                },
				                fax: {
				                    title: '办公电话',
				                    list:false,
									width: '14%'
				                    
				                },
				                
								
				                status: {
				                    title: '状态',
									options: { '1': '正常', '2': '停用' },
				                    width: '10%'
				                },
								
								addviewchild: {
				                    title: '操作',
				                    width: '10%',
				                    sorting: false,
				                    edit: false,
				                    create: false,
				                    display: function (data) {
										if(data.record.isschool == 0) {
											var html ='<a href="../admin/organization_initPageForAdmin.action?pid=' + data.record.id +'&org=1">管理下级部门</a>';
										}else {
											if(data.record.isMerge == 1){
												var html = '<a class="ifrmbox" href="organization_mergeInit.action?id='+data.record.id+'" data-fancybox-type="iframe">合并学校</a>&nbsp&nbsp';
											}
											if(data.record.isTransfer == 1){
												html += '<a class="ifrmbox2" href="organization_transferInit.action?id='+data.record.id+'" data-fancybox-type="iframe">学校迁移</a>';
											}
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
					
								var thisbox = data.form.find('select[name="isschool"]');
								if(thisbox.val() == 0) {
									 data.form.find('select[name="grade"]').parents(".jtable-input-field-container").first().hide();
									 data.form.find('select[name="organizerName"]').parents(".jtable-input-field-container").first().hide();
									 data.form.find('select[name="dictype"]').parents(".jtable-input-field-container").first().hide();
									 data.form.find('select[name="ftype"]').parents(".jtable-input-field-container").first().hide();
// 									 data.form.find('select[name="attribute"]').parents(".jtable-input-field-container").first().hide();
								}
								
								var thisbox1 = data.form.find('select[name="organizationLevel"]');
								if(thisbox1.val() != 3) {
									 data.form.find('select[name="isPoor"]').parents(".jtable-input-field-container").first().hide();
									 data.form.find('select[name="isCountryPoor"]').parents(".jtable-input-field-container").first().hide();
								}
							    
							},
							recordsLoaded :function (data) {
								$(".ifrmbox").colorbox({
									iframe : true,
									width : "860px",
									height: "250px",
									opacity : '0.5',
									overlayClose : false,
									escKey : true
								});
								$(".ifrmbox2").colorbox({
									iframe : true,
									width : "860px",
									height: "350px",
									opacity : '0.5',
									overlayClose : false,
									escKey : true
								})
							}
				        });
						
						$('#OrgTableContainer').jtable('load');
						
						
						
				    });
				    $(function() {//添加按钮
						$(".btn-search").colorbox({
							iframe : true,
							width : "650px",
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