<%@page import="org.apache.struts2.components.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:action name="head_init" namespace="/admin" executeResult="true"
	ignoreContextParams="false">
</s:action>
<s:action name="left_init" namespace="/admin" executeResult="true">
	<s:param name="leftName">教师培训大数据</s:param>
</s:action>
<link href="../css/statistics.css" rel="stylesheet">
<script src="../js/jsrender.min.js"></script>
<script src="../js/highcharts.js"></script>
<script src="../js/data.js"></script>
<script type="text/javascript" src="../js/layer-v2.2/layer/layer.js"></script>
<style>
.col-md-12{margin-top:20px;}
</style>

<style>

</style>

<div class="main">
	<div class="listwrap">
		<div class="loadingDiv"></div>
		<div class="conta">
			<h3 class="text-center">教师培训大数据</h3>
			<div class="row">
				<div class="col-md-8" style="margin-top:0; min-width:600px;">
					<label>培训年份：</label>
					<div class="controls" style="min-width:350px;margin-left:0;float:left;">
						<div class="select-group">
							<select class="form-control" id="startTime">
								
								<s:iterator value="searchYear" id="yt">
								<option value='<s:property value="#yt" />' <s:if test="#yt==selectYear"> selected </s:if> ><s:property
										value="#yt" /></option>
							</s:iterator>
							</select>
							<span class="select-group-span">至</span>
							<select class="form-control" id="endTime">
								
								<s:iterator value="searchYear" id="yt">
								<option value='<s:property value="#yt" />' <s:if test="#yt==selectYear"> selected </s:if> ><s:property
										value="#yt" /></option>
							</s:iterator>
							</select>
							<div class="clear"></div>
						</div>
					</div>
					<a class="btn btn-primary btn-myself" onclick="getYearInfo('1');" style="float:left;">查询</a>
					<div class="clear"></div>
				</div>
				<div class="clear"></div>
				
				<div class="contentsDiv">
					<div class="statistics">
						<div class="titleDiv">
							<p class="title">总参训人次</p>
							<p class="title">总参训教师人数</p>
							<p class="title">总结业人次</p>
							<p class="title">总覆盖率</p>
							<div class="clear"></div>
						</div>
						
						<div class="numberDiv">
							<p class="number">
								<span class="total"></span>
								人次
							</p>
							<p class="number">
								<span class="passengers"></span>
								名
							</p>
							<p class="number">
								<span class="proportion"></span>
								人次
							</p>
							<p class="number">
								<span class="coverage"></span>
								%
							</p>
							<div class="clear"></div>
						</div>
					</div>
					<div class="clear"></div>										
					<div class="btnDiv">
						<a class="light" onclick="getYearInfo();">分年统计</a>
						<a onclick="getLevelInfo();">分类统计</a>
						<a onclick="getSubjectInfo();">分学段统计</a>
						<a onclick="getGradeInfo();">分学科统计</a>
						<a onclick="getOrganizationInfo();">分地区统计</a>
					</div>
					<div class="loadingSmalldiv"></div>
					<div class="div">
						<table class="table tableThead">
							
						</table>
						<div class="dv-table">
							<table class="table-striped text-center table-hover">
		
							</table>
						</div>
						<table class="text-center totalTr">
		 
						</table>
						<div class="pieChart col-md-12">
							<p class="title text-center">参训人次曲线图</p>
							<div id="containerline" style="width: 99%;min-height: 433px;"></div>
						</div>	
					</div>		
				</div>
			</div>
			
			
		</div>
	</div>
</div>

<script src="../js/statisticsteacherTrainingBigdata.js"></script>
<jsp:include page="foot.jsp"></jsp:include>