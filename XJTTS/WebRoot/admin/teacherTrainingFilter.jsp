<%@page import="org.apache.struts2.components.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:action name="head_init" namespace="/admin" executeResult="true"
	ignoreContextParams="false">
</s:action>
<s:action name="left_init" namespace="/admin" executeResult="true">
	<s:param name="leftName">自定义筛查器管理</s:param>
</s:action>
<script type="text/javascript" src="../js/layer-v2.2/layer/layer.js"></script>
<link href="../css/teacherTrainingFilter.css" rel="stylesheet">
<div class="main">
	<div class="list_bar" style="height:58px;">自定义筛查器管理</div>
	<div class="iconDiv">
		<a class="btn btnMyself btn-screen">
			<span><img src="../img/kaishexiangmu.png" alt="icon">
				<b>添加</b>
			</span>
			<p>
				<img src="../img/lanse.png" alt="蓝色三角">
				<b>添加筛查器</b>
			</p>
		</a>
	</div>
	<div class="container">
		<table class="tableHead table-bordered table">
			<tr><th class="col-md-2 no-border"></th><th class="col-md-6 text-center">筛查器名称</th><th class="col-md-1 text-center">状态</th>
				<th class="col-md-3 text-center">操作</th></tr>
		</table>
		<div class="tableDiv">
		
		</div>
		<div id="pagination" style="float: right;" class="pull-right"></div>
	</div>

</div>
<script src="../js/teacherTrainingFilter.js"></script>
<jsp:include page="foot.jsp"></jsp:include>