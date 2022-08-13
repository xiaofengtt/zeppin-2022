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
<script src="../js/url.min.js"></script>

</head>
<body>
	<div class="ifrcnt container" style="width:auto;">
		<div class="bd">
			<div id="ProjectTableContainer"></div>
		</div>

	</div>
	<script type="text/javascript">
			$(document).ready(function() {
				var pid = url("?pid");
				$('#ProjectTableContainer')
						.jtable(
								{
									title : '材料上传情况',
									messages : zhCN, //Lozalize
									paging : true, //Enable paging
									pageSize : 10, //Set page size (default: 10)
									sorting : false, //Enable sorting
									defaultSorting : 'organization ASC', //Set default sorting
									dialogShowEffect : 'drop',

									actions : {
										listAction : '../admin/trainingUnitProjectApply_getDocumentUpdateInfo.action?id='+pid ,

									},
									fields : {
										projectApplyReport : {
											title : '申报书',
											width : '13%',
											list : true
										},
										startMessageReport : {
											title : '开班通知',
											width : '13%',
											list : true
										},
										workReport : {
											title : '工作简报',
											width : '13%',
											list : true
										},
										proformanceReport : {
											title : '绩效报告',
											width : '13%',
											list : true
										},
										curriculumDocument : {
											title : '课程表',
											width : '13%',
											list : true
										},
										projectPlan : {
											title : '实施方案',
											width : '13%',
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
