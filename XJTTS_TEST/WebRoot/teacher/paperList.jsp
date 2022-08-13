<%@page import="org.apache.struts2.components.Include"%>
<%@page import="cn.zeppin.action.sso.UserSession"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>个人信息能力列表</title>

<link rel="stylesheet" href="../css/bootstrap.css">
<!--[if lt IE 9]>
  <script src="../js/html5shiv.js"></script>
  <script src="../js/respond.min.js"></script>
<![endif]-->
<link rel="stylesheet" href="../css/app.css">
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css">
<link href="../css/metro/blue/jtable.css" rel="stylesheet"
	type="text/css">
<link href="../css/colorbox.css" rel="stylesheet" type="text/css">
<script src="../js/jquery-1.9.1.min.js"></script>
<script src="../js/jquery-ui.js"></script>
<script src="../js/jquery.jtable.js"></script>
<script src="../js/jquery.jtable.zh-CN.js"></script>
<script src="../js/jquery.colorbox.js"></script>
<script src="../js/bootstrap-paginator.min.js"></script>
<script src="../js/bootstrap.js"></script>
<script src="../js/url.min.js"></script>
<script src="../js/app.js"></script>
<script type="text/javascript" src="../js/Chart.js"></script>
<script src="../js/excanvas.js" type="text/javascript"></script>

<script src="../js/jsrender.min.js"></script>
<script src="../js/underscore-min.js"></script>
<link rel="stylesheet" href="../css/ui-dialog.css">
<script src="../js/dialog-min.js"></script>

</head>
<body>

	<jsp:include page="head.jsp"></jsp:include>
	<div id="container" class="container-fluid">
	<jsp:include page="left.jsp"></jsp:include>

		<div class="main">
		
			<div class="cui-menu2">
				<a class="btn-search btn btn-primary" href="../teacher/hsdTest_getTrainingRecords.action" >刷新</a>
			</div>
			
			<div id="list-content" class="list-content clearfix">
				<s:if test="%{onSubmitList.size()>0}">
					<s:iterator value="onSubmitList" id="ttr" status="st" >
				
					<div id="hsd_<s:property value='id' />" class="list-item">
						
						<table class="table table-bordered">
							<tbody>
								<tr>
									<td width="auto"><span class="text-primary">编号：</span><span class="text-danger"><s:property value="#st.count"/></span></td>
									<td width="auto"><span class="text-primary">项目名称：</span><span class="text-danger"><s:property value='projectName' /></span></td>
									<td width="400px"><span class="text-primary">培训学科：</span><span class="text-danger"><s:property value='subjectName' /></span></td>
									<td width="200px"><span class="text-primary">培训单位：</span><span class="text-danger"><s:property value='trainingCollege' /></span></td>
									<td>
									<s:if test="%{isRecords}">
										<span class="text-primary">已评价</span>
									</s:if>
									<s:else>
										<a target="_blank" href="../paper/paper_choosePaper.action?id=<s:property value="id" />">未评价</a>
									</s:else>
									</td>
									
								</tr>
							</tbody>
						</table>
						
					</div>
					
				</s:iterator>
				</s:if>
				<s:else>
					<p style="height:100px;text-align:center;line-height:100px;font-size:20px;">没有问卷调查</p>
				</s:else>
			
			</div>


		</div>


	</div>
<jsp:include page="foot.jsp"></jsp:include>
</body>
</html>