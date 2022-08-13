<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>栏目列表</title>

<link href="../assets/css/style.css" rel="stylesheet" type="text/css" />
<link href="../assets/css/themes/light.css" rel="stylesheet" type="text/css" />
<link href="../assets/css/style-responsive.css" rel="stylesheet" type="text/css" />
<link href="../assets/css/colorbox.css" rel="stylesheet" type="text/css">
<link href="../assets/css/jquery-ui.css" rel="stylesheet" type="text/css" >
<link href="../assets/css/metro/blue/jtable.css" rel="stylesheet" type="text/css" >
<link href="../assets/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="../assets/css/columLists.css" type="text/css"/>
<link rel="stylesheet" href="../assets/css/main.css" type="text/css"/>

<script src="../assets/plugins/jquery-1.10.2.min.js" type="text/javascript"></script>
<script src="../assets/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script src="../assets/plugins/jquery.cokie.min.js" type="text/javascript"></script>
<script src="../assets/scripts/app.js" type="text/javascript"></script>
<script type="text/javascript" src="../app/js/index.js"></script>
<script src="../assets/plugins/jquery-ui.js"></script>
<script src="../assets/plugins/jquery.jtable.js"></script>
<script src="../assets/plugins/jquery.jtable.zh-CN.js"></script>
<script src="../assets/plugins/url.min.js"></script>
<script src="../assets/plugins/jquery.colorbox.js"></script>
</head>
<body>

<!--head-->
<jsp:include page="header.jsp"></jsp:include>
		<!--head end-->
		<div class="clearfix"></div>
		<!--left-->
			<div class="page-container">
            <!-- BEGIN SIDEBAR -->
            <jsp:include page="left_admin.jsp"></jsp:include>
            <!-- END SIDEBAR -->
            <!-- BEGIN CONTENT -->
            <div class="page-content-wrapper">
                <div class="page-content">
                    <!-- BEGIN SAMPLE PORTLET CONFIGURATION MODAL FORM-->
                    <div class="modal fade" id="portlet-config" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                                    <h4 class="modal-title">Modal title</h4>
                                </div>
                                <div class="modal-body">
                                    Widget settings form goes here
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn blue">
                                        Save changes
                                    </button>
                                    <button type="button" class="btn default" data-dismiss="modal">
                                        Close
                                    </button>
                                </div>
                            </div>
                            <!-- /.modal-content -->
                        </div>
                        <!-- /.modal-dialog -->
                    </div>
                    <!-- /.modal -->
                    <!-- END SAMPLE PORTLET CONFIGURATION MODAL FORM-->
                    <!-- BEGIN STYLE CUSTOMIZER -->
                    <!-- <div class="theme-panel hidden-xs hidden-sm">
                        <div class="toggler"></div>
                        <div class="toggler-close"></div>
                        <div class="theme-options">
                            <div class="theme-option theme-colors clearfix">
                                <span> 主题颜色 </span>
                                <ul>
                                    <li class="color-black current color-default" data-style="#333438"></li>
                                    <li class="color-blue" data-style="blue"></li>
                                    <li class="color-brown" data-style="brown"></li>
                                    <li class="color-purple" data-style="purple"></li>
                                    <li class="color-grey" data-style="grey"></li>
                                    <li class="color-white color-light" data-style="#fff"></li>
                                </ul>
                            </div>
                        </div>
                    </div> -->
                    <!-- END STYLE CUSTOMIZER -->
					
					<!--content-->
						
						<div class="content">
<!-- 							<button class="btn">新增栏目</button> -->
<!-- 							<hr> -->

<!-- 							<table class="table"> -->
<!-- 								<tr><th width="20%">序号</th><th width="15%">栏目名称</th><th width="15%">状态</th><th width="40%">操作</th></tr> -->
<!-- 								<tr><td><label>1</label></td><td>新品发布</td><td><label>正常</label></td><td><a class="look">查看下级栏目</a><a>编辑</a><a>删除</a></td></tr> -->
<!-- 								<tr><td><label>2</label></td><td>新品发布</td><td><label>正常</label></td><td><a class="look">查看下级栏目</a><a>编辑</a><a>删除</a></td></tr> -->
<!-- 								<tr><td><label>3</label></td><td>新品发布</td><td><label>正常</label></td><td><a class="look">查看下级栏目</a><a>编辑</a><a>删除</a></td></tr> -->
<!-- 								<tr><td><label>4</label></td><td>新品发布</td><td><label class="disable">已停用</label></td><td><a class="look">查看下级栏目</a><a>编辑</a><a>删除</a></td></tr> -->
<!-- 							</table> -->
							
							<div class="cui-menu">
								<button class="btn-create btn" type="button">
									添加栏目
								</button>
							</div>
							
							<div id="TableContainer"></div>
							<script type="text/javascript">
								$(document).ready(
									function() {
										var parent = (url('?parent') != null) ? url('?parent') : '';
										var level = (url('?level') != null) ? '' : 1; 
										var navarr = []; 
										$.get('../rest/category/loadNav?parent='+parent,function(r) {
											if(r.status =='success') {
												for ( var i = 0, l = r.data.length; i < l; i++ ) {
												    var navhtml = ' &gt; <a href="../views/categoryList.jsp?parent='+ r.data[i].id +'&level='+r.data[i].level +'">'+ r.data[i].name +'</a>';
													navarr.push(navhtml);
										
												}
												
											}
											
										}).done(function(){
											$('#TableContainer')
												.jtable(
														{
															title : '<a href="../views/categoryList.jsp">栏目管理</a> '+ navarr.join(''),
															messages : zhCN, //Lozalize
															paging : true, //Enable paging
															pageSize : 10, //Set page size (default: 10)
															pageSizes : [ 10, 20, 30, 40, 50 ],
															sorting : true, //Enable sorting
															defaultSorting : 'scode ASC', //Set default sorting
															dialogShowEffect : 'drop',
															addRecordButton : $('.btn-create'),
															actions : {
																listAction : '../rest/category/all?parent='+ parent +'&level='+level,
																updateAction : '../rest/category/edit',
																deleteAction :'../rest/category/remove',
																createAction : '../rest/category/add?parent='+ parent
															},
															fields : {
																id : {
																	title : '',
																	key : true,
																	width:'0%',
																	list:false
																},
																scode : {
																	title : '序号',
																	edit: false,
												                    create: false,
																	width:'5%'
																},
																name : {
																	title : '栏目名称',
																	width: '8%',
																	optionsSorting : 'value'
																	
																},
																status: {
																	title: '状态',
																	options : {
																		'normal' : '正常',
																		'stopped' : '停用'
																		
																	},
																	defaultValue : 'normal'
																	
																	
																},
																addviewchild: {
												                    title: '操作',
												                    width: '10%',
												                    sorting: false,
												                    edit: false,
												                    create: false,
												                    display: function (data) {
																			var html ='<a href="../views/categoryList.jsp?parent=' + data.record.id+'&level='+data.record.level +'">管理下级栏目</a>';
																		return html;
																	}
									
																}
					
														
															},
															
															columnResizable : false,
															deleteConfirmation: function(data) {
															  data.deleteConfirmMessage = '确定要停用个栏目?';
// 															  $('#DeleteDialogButton').html('停用');
															},
															formClosed : function(event,data) {
																
															},
															recordsLoaded :function (data) {
																//删除按钮改成停用
// 															    $('#'+data.target.id).find('.jtable-delete-command-button').each(function(){
// 																	$(this).attr('title','停用');
// 																	$(this).find('span').text('停用');
// 																})  
										
															}
															
														});

										$('#TableContainer').jtable('load');
		
										
									});
								})
				</script>
						</div>
					
					<!--content end-->

                </div>
            </div>
            <!-- END CONTENT -->
        </div>
		<!--left end-->
		<jsp:include page="footer.jsp"></jsp:include>


</body>
</html>