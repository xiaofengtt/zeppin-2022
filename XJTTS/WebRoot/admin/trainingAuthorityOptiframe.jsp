<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<link rel="stylesheet" href="../css/bootstrap2.css">
<link rel="stylesheet" href="../css/iframe.css">
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" >
<link href="../css/metro/blue/jtable.css" rel="stylesheet" type="text/css" >
<link href="../css/colorbox.css" rel="stylesheet" type="text/css">
<script src="../js/jquery-1.9.1.min.js"></script>
<script src="../js/jquery.colorbox.js"></script>
<script src="../js/iframe.js"></script>
<script src="../js/jquery-ui.js"></script>
<script src="../js/jquery.jtable.js"></script>
<script src="../js/jquery.jtable.zh-CN.js"></script>
<script src="../js/bootstrap-paginator.min.js"></script>
<script src="../js/bootstrap.js"></script>
<% 
	String projectApply = request.getParameter("id");
%>
</head>
<body>
	<div class="ifrcnt container">
		<div class="bd">
			<div id="ProjectTableContainer"></div>
		</div>

	</div>
	<script type="text/javascript">
			$(document).ready(function() {
				$('#ProjectTableContainer')
						.jtable(
								{
									title : '承训单位管理员列表',
									messages : zhCN, //Lozalize
									paging : true, //Enable paging
									pageSize : 10, //Set page size (default: 10)
									sorting : false, //Enable sorting
									defaultSorting : 'organization ASC', //Set default sorting
									dialogShowEffect : 'drop',

									actions : {
										listAction : '../admin/trainingStudentOpt_getTrainingAdminList.action?id=' + <%=projectApply%>,

									},
									fields : {
										organization : {
											title : '工作单位',
											width : '13%',
											list : true
										},
										name : {
											title : '姓名',
											width : '10%',
											inputClass : 'test',
											visibility : 'fixed'
										},
										sex : {
											title : '性别',
											width : '6%',
											options : {
												'0' : '无',
												'1' : '男',
												'2' : '女'
											}
										},
										ethnic : {
											title : '民族',
											width : '6%'
										},
										department : {
											title : '工作部门',
											width : '13%',
											list : true
										},
										jobDuty : {
											title : '工作职务',
											width : '11%',
											list : true
										},
										phone : {
											title : '办公电话',
											width : '8%',
											list : true
										},
										mobile : {
											title : '手机',
											width : '5%',
											list : true
										}
										
									},
									columnResizable : false
								});

						$('#ProjectTableContainer').jtable('load');	
						$("table").css("margin-top","0px");
			});
		</script>
<body>
</html>
