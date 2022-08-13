<%@page import="org.apache.struts2.components.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:action name="head_init" namespace="/admin" executeResult="true"
	ignoreContextParams="false">
</s:action>
<s:action name="left_init" namespace="/admin" executeResult="true">
	<s:param name="leftName">贫困地区教师培训统计</s:param>
</s:action>
<link href="../css/statistics.css" rel="stylesheet">

<script src="../js/jsrender.min.js"></script>
<script src="../js/highcharts.js"></script>
<script src="../js/data.js"></script>
<script type="text/javascript" src="../js/layer-v2.2/layer/layer.js"></script>
<div class="main" onclick="closeSelect()">

	<div class="listwrap">
		<div class="loadingDiv"></div>
		<div class="conta">
			<h3 class="text-center">贫困地区教师培训统计</h3>
			<div class="row">
				<div class="col-md-5" style="margin-top:0;">
					<label>培训年份：</label>
					<div class="controls">
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
						</div>
					</div>
					<div class="clear"></div>
				</div>
				<div class="col-md-5" style="margin-top:0;">
					<label>培训项目：</label>
					<div class="controls">
						<div id="select1">
							<p class="choose">请选择</p>
							<div class="options" id="projectName" value="">
								<p onclick="closeSelect()"><input type="checkbox" class="checkbox1" id="all" value="全选"/>
								<label for="all">全选</label><span class="clear"></span></p>
							</div>		
						</div>
						<input type="hidden" class="parentInput" name="parent" value="">
						<%-- <select class="form-control" id="projectName">
							<option value="">全部</option>
						</select> --%>
					</div>
					<div class="clear"></div>
				</div>	
				<div class="col-md-2 text-center" style="">
					<a class="btn btn-primary btn-myself" onclick="getInfo();">查询</a>
				</div>
				<div class="clear"></div>
				
				<div class="contentsDiv">
					<div class="statistics">
						<div class="titleDiv">
							<p class="title">总参训人次</p>
							<p class="title">贫困地区参训人次</p>
							<p class="title">贫困地区教师参训人次占比</p>
							<p class="title">贫困地区教师培训覆盖率</p>
							<div class="clear"></div>
						</div>
						
						<div class="numberDiv">
							<p class="number">
								<span class="total"></span>
								人次
							</p>
							<p class="number">
								<span class="passengers"></span>
								人次
							</p>
							<p class="number">
								<span class="proportion"></span>
								%
							</p>
							<p class="number">
								<span class="coverage"></span>
								%
							</p>
							<div class="clear"></div>
						</div>
					</div>
					<div class="pieChart col-md-6" style="float:left;">
							<p class="title text-center">贫困地区培训人次饼状图</p>
							<div id="containerrate" style="width: 99%;height: 433px;"></div>
						</div>
						<div class="pieChart col-md-6" style="float: right;">
							<p class="title text-center">贫困地区教师培训覆盖率饼状图</p>
							<div id="containerpie" style="width: 99%;height: 433px;"></div>
						</div>
						<div class="clear"></div>
						<div class="pieChart col-md-12">
							<p class="title text-center">贫困地区教师培训人次历年变化曲线图</p>
							<div id="containerline" style="width: 100%;height: 433px;"></div>
						</div>	
					<div class="offsetTop"></div>
					<table class="table tableThead">
						<tr><th class="extend" width="25%">下属单位</th><th width="15%">总教师数</th><th width="10%">贫困地区教师数</th><th width="10%">总参训人次</th>
							<th width="10%">贫困地区参训人次</th><th width="10%">贫困地区培训人数</th><th width="10%">贫困地区参训人次占比</th>
							<th width="10%">贫困地区教师培训覆盖率</th></tr>
					</table>
					<table class="table tableThead tableTheadFix">
						<tr><th class="extend" width="25%">下属单位</th><th width="15%">总教师数</th><th width="10%">贫困地区教师数</th><th width="10%">总参训人次</th>
							<th width="10%">贫困地区参训人次</th><th width="10%">贫困地区培训人数</th><th width="10%">贫困地区参训人次占比</th>
							<th width="10%">贫困地区教师培训覆盖率</th></tr>
					</table>
					<div class="dv-table">
						<table class="table-striped text-center">
						
						</table>
					</div>
					<table class="text-center totalTr">
						<tr class=""><td class="extend" width="15%">总计</td><td width="15%">200,000,00</td><td width="15%">3,000,000,00</td>
							
					</table>
							
				</div>
			</div>
			
			
		</div>
	</div>
</div>
		
<script src="../js/statisticsteacherTrainingPoor.js"></script>			

<jsp:include page="foot.jsp"></jsp:include>	