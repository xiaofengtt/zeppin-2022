<%@page import="org.apache.struts2.components.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:action name="head_init" namespace="/admin" executeResult="true"
	ignoreContextParams="false">
</s:action>
<s:action name="left_init" namespace="/admin" executeResult="true">
	<s:param name="leftName">教师基本信息大数据</s:param>
</s:action>
<link rel="stylesheet" href="../css/iframe.css">
<link href="../css/statistics.css" rel="stylesheet">
<style type="text/css">
.statistics .titleDiv p.title{width: 33.3%;}
.statistics .numberDiv p.number{width: 33.3%;}
.tableThead{margin-top:0;}
.companylocation{width:400px;height:auto;box-shadow:inset 0 1px 1px rgba(0, 0, 0, .075);border-radius:4px;}
.listSub{width:400px;overflow-x:auto;}
.selectDiv{float:left;width:600px;}
.col-md-6{margin:auto;margin-bottom:20px;}
h4{font-size:16px;}
.btn-myself{margin-left:20px;}
</style>
<script src="../js/jsrender.min.js"></script>
<script src="../js/highcharts.js"></script>
<script src="../js/data.js"></script>
<script src="../js/iframe.js"></script>
<script type="text/javascript" src="../js/layer-v2.2/layer/layer.js"></script>

<div class="main">
	<div class="listwrap">
		<div class="loadingDiv"></div>
		<div class="conta">
			<h3 class="text-center">教师基本信息大数据</h3>
			<div class="row">
				<div class="selectDiv">
					<label>选择地区：</label>
					<div class="controls">
						<select class="form-control" id="organization"></select>
					</div>
					
					<div class="clear"></div>
				</div>	
				<a class="btn btn-primary btn-myself" onclick="areaIsNull();">查询</a>
				<div class="clear"></div>
				
				<div class="contentsDiv">
					<div class="statistics">
						<div class="titleDiv">
							<p class="title">总教师人数</p>
							<p class="title">去年同期</p>
							<p class="title">同比变动</p>
							<div class="clear"></div>
						</div>
						<div class="numberDiv">
							<p class="number">
								<span class="total"></span>
								名
							</p>
							<p class="number">
								<span class="passengers"></span>
								名
							</p>
							<p class="number">
								<span class="proportion"></span>
								%
							</p>						
							<div class="clear"></div>
						</div>
						
					</div>
					<div class="btnDiv">
						<a class="light" onclick="getBasicInfo();">基本情况统计</a>
						<a onclick="getTeachInfo();">教学信息统计</a>
						<a onclick="getAddressInfo();">分地区统计</a>
					</div>
					<div class="loadingSmalldiv"></div>
					<!-- 基本信息 -->
						<div class="div0 div">
							<div>					
								<h4>一、在岗教师的性别构成</h4>
								<div class="col-md-6">
									<table class="table tableThead">
										<tr><th width="50%">性别</th><th width="50%">教师数</th></tr>
									</table>
								<div class="dv-table" name="Sex">
									<table class="table-striped text-center table-sex">
										
									</table>
								</div>
								<table class="text-center totalTr">
									<tr class=""><td width="50%">总计</td><td width="50%" class="totalSex">0</td></tr>
								</table>
								</div>
								<table id="datatable_Sex" style="display:none;">
									<tr><th class="option">性别</th><th class="option-data">教师数</th></tr>
								</table>
								<div class="col-md-6 pieChart">
									<p class="title text-center">在岗教师性别构成饼状图</p>
									<div id="chart-column_Sex" name="Sex" style="height: 433px;"></div>
								</div>
								<div class="clear"></div>
							</div>
							<div>
								<h4>二、在岗教师的年龄构成</h4>
								<div class="col-md-6">
									<table class="table tableThead">
									<tr><th width="50%">年龄</th><th width="50%">教师数</th></tr>
								</table>
								<div class="dv-table" name="Age">
									<table class="table-striped text-center table-age">
										
									</table>
								</div>
								<table class="text-center totalTr">
									<tr class=""><td width="50%">总计</td><td width="50%" class="totalAge">0</td></tr>
								</table>
								</div>
								<div class="col-md-6 pieChart">
									<p class="title text-center">在岗教师年龄饼状图</p>
									<div id="chart-column_Age" style="width: 300px;height: 433px;"></div>
								</div>
								<div class="clear"></div>
							</div>
							<div>
								<h4>三、在岗教师的教龄构成</h4>
								<div class="col-md-6">
									<table class="table tableThead">
									<tr><th width="50%">教龄</th><th width=50%">教师数</th></tr>
								</table>
								<div class="dv-table" name="TeachingAge">
									<table class="table-striped text-center table-teachingAge">
										
									</table>
								</div>
								<table class="text-center totalTr">
									<tr class=""><td width="50%">总计</td><td width="50%" class="totalteachingAge">0</td></tr>
								</table>
								</div>
								<div class="col-md-6 pieChart">
									<p class="title text-center">在岗教师教龄饼状图</p>
									<div id="chart-column_TeachingAge" style="width: 99%;height: 433px;"></div>
								</div>
								<div class="clear"></div>
							</div>
							<div>
								<h4>四、在岗教师的学校举办者类型构成</h4>
								<div class="col-md-6">
									<table class="table tableThead">
									<tr><th width="30%">学校类别</th><th width="30%">学校数</th><th width="40%">教师数</th></tr>
								</table>
								<div class="dv-table" name="SchoolType">
									<table class="table-striped text-center table-schoolType">
										
									</table>
								</div>
								<table class="text-center totalTr">
									<tr class=""><td width="30%">总计</td><td width="30%" class="totalschoolTypesch">0</td>
										<td width="40%" class="totalschoolTypetea">0</td></tr>
								</table>
								</div>
								<div class="col-md-6 pieChart">
									<p class="title text-center">在岗教师的学校举办者类型构成饼状图</p>
									<div id="chart-column_SchoolType" style="width: 99%;height: 433px;"></div>
								</div>
								<div class="clear"></div>
							</div>
							<div>
								<h4>五、在岗教师的职称构成</h4>
								<div class="col-md-6">
									<table class="table tableThead tableTheadJobTitle">
										<tr><th width="50%">职称</th><th width="50%">教师数</th></tr>
									</table>
									<table class="table tableThead tableTheadJobTitleFix">
										<tr><th width="50%">职称</th><th width="50%">教师数</th></tr>
									</table>
									<div class="dv-table dv-tableJobTitle" name="JobTitle">
										<table class="table-striped text-center table-jobTitle">
											
										</table>
									</div>
									<table class="text-center totalTr totalTrJobTitle">
										<tr class=""><td width="50%">总计</td><td width="50%" class="totaljobTitle">0</td></tr>
									</table>
								</div>
								<div class="col-md-6 pieChart">
									<p class="title text-center">在岗教师的职称构成饼状图</p>
									<div id="chart-column_JobTitle" style="width: 99%;height: 433px;"></div>
								</div>
								<div class="clear"></div>
							</div>
							<div>
								<h4>六、在岗教师的政治面貌构成</h4>
								<div class="col-md-6">
									<table class="table tableThead">
									<tr><th width="50%">政治面貌</th><th width="50%">教师数</th></tr>
								</table>
								<div class="dv-table" name="Politice">
									<table class="table-striped text-center table-politice">
										
									</table>
								</div>
								<table class="text-center totalTr">
									<tr class=""><td width="50%">总计</td><td width="50%" class="totalpolitice">0</td></tr>
								</table>
								</div>
								<div class="col-md-6 pieChart">
									<p class="title text-center">在岗教师的政治构成饼状图</p>
									<div id="chart-column_Politice" style="width: 99%;height: 433px;"></div>
								</div>
								<div class="clear"></div>
							</div>
						
						</div>
					<!--教学信息统计 -->
					<div class="div1 div">
						<div>					
							<h4>一、双语教师构成</h4>
							<div class="col-md-6">
								<table class="table tableThead">
									<tr><th width="50%">教学语言</th><th width="50%">教师数</th></tr>
								</table>
							<div class="dv-table" name="MutiLanguage">
								<table class="table-striped text-center table-mutiLanguage">
									
								</table>
							</div>
							<table class="text-center totalTr">
								<tr class=""><td width="50%">总计</td><td width="50%" class="totalMutiLanguage">0</td></tr>
							</table>
							</div>
							<div class="col-md-6 pieChart">
								<p class="title text-center">双语教师构成饼状图</p>
								<div id="chart-column_MutiLanguage" name="MutiLanguage" style="width: 99%;height: 433px;"></div>
							</div>
							<div class="clear"></div>
						</div>
						<div>
							<h4>二、教师主要教学语言构成</h4>
							<div class="col-md-6">
								<table class="table tableThead tableTheadTeachingLanguage">
									<tr><th width="50%">教学语言</th><th width="50%">教师数</th></tr>
								</table>
								<table class="table tableThead tableTheadTeachingLanguageFix">
									<tr><th width="50%">教学语言</th><th width="50%">教师数</th></tr>
								</table>
								<div class="dv-table dv-tableTeachingLanguage" name="TeachingLanguage">
									<table class="table-striped text-center table-teachingLanguage">
										
									</table>
								</div>
								<table class="text-center totalTr">
									<tr class=""><td width="50%">总计</td><td width="50%" class="totalTeachingLanguage">0</td></tr>
								</table>
							</div>
							<div class="col-md-6 pieChart">
								<p class="title text-center">教师主要教学语言构成饼状图</p>
								<div id="chart-column_TeachingLanguage" style="width: 99%;height: 433px;"></div>
							</div>
							<div class="clear"></div>
						</div>
						<div>
							<h4>三、教师主要教学学段构成</h4>
							<div class="col-md-6">
								<table class="table tableThead">
								<tr><th width="50%">学段</th><th width="50%">教师数</th></tr>
							</table>
							<div class="dv-table" name="TeachingGrade">
								<table class="table-striped text-center table-teachingGrade">
									
								</table>
							</div>
							<table class="text-center totalTr">
								<tr class=""><td width="50%">总计</td><td width="50%" class="totalteachingGrade">0</td></tr>
							</table>
							</div>
							<div class="col-md-6 pieChart">
								<p class="title text-center">教师主要教学学段构成饼状图</p>
								<div id="chart-column_TeachingGrade" style="width: 99%;height: 433px;"></div>
							</div>
							<div class="clear"></div>
						</div>
						<div>
							<h4>四、教师主要教学学科构成</h4>
							<div class="col-md-6">
								<table class="table tableThead tableTheadTeachingSubject">
									<tr><th width="60%">学科</th><th width="40%">教师数</th></tr>
								</table>
								<table class="table tableThead tableTheadTeachingSubjectFix">
									<tr><th width="60%">学科</th><th width="40%">教师数</th></tr>
								</table>
								<div class="dv-table dv-tableTeachingSubject" name="TeachingSubject">
									<table class="table-striped text-center table-teachingSubject">
										
									</table>
								</div>
								<table class="text-center totalTr totalTrTeachingSubject">
									<tr class=""><td width="60%">总计</td><td width="40%" class="totalteachingSubject">0</td></tr>
								</table>
							</div>
							<div class="col-md-6 pieChart">
								<p class="title text-center">教师主要教学学科构成饼状图</p>
								<div id="chart-column_TeachingSubject" style="width: 99%;height: 433px;"></div>
							</div>
							<div class="clear"></div>
						</div>	
					</div>
					<!-- 分地区统计 -->
					<div class="div2 div">
						<div>					
							<h4>一、教师所在地区类型</h4>
							<div class="col-md-6">
								<table class="table tableThead">
									<tr><th width="50%">地区类型</th><th width="50%">教师数</th></tr>
								</table>
							<div class="dv-table" name="TeacherAddressType">
								<table class="table-striped text-center table-teacherAddressType">
									
								</table>
							</div>
							<table class="text-center totalTr">
								<tr class=""><td width="50%">总计</td><td width="50%" class="totalteacherAddressType">0</td></tr>
							</table>
							</div>
							<div class="col-md-6 pieChart">
								<p class="title text-center">教师所在地区类型饼状图</p>
								<div id="chart-column_TeacherAddressType" name="Sex" style="width: 99%;height: 433px;"></div>
							</div>
							<div class="clear"></div>
						</div>
						<div>
							<h4>二、教师所在地区构成</h4>
							<div class="col-md-6">
								<table class="table tableThead tableTheadTeacherAddress">
									<tr><th width="60%">下属单位</th><th width="40%">教师数</th></tr>
								</table>
								<table class="table tableThead tableTheadTeacherAddressFix">
									<tr><th width="60%">下属单位</th><th width="40%">教师数</th></tr>
								</table>
								<div class="dv-table dv-tableTeacherAddress">
									<table class="table-striped text-center table-teacherAddress">
										
									</table>
								</div>
								<table class="text-center totalTr totalTrTeacherAddress">
									<tr class=""><td width="60%">总计</td><td width="40%" class="totalteacherAddress">0</td></tr>
								</table>
							</div>
							<div class="col-md-6 pieChart">
								<p class="title text-center">教师所在地区构成柱状图</p>
								<div id="containerline" style="width: 99%;min-height: 433px;"></div>
							</div>
							<div class="clear"></div>
						</div>
					</div>
					
					<!-- 分地区统计结束 -->
				</div>
			</div>
		</div>
	</div>
</div>
<script src="../js/statisticsteacherTrainingBasicInfo.js"></script>

<jsp:include page="foot.jsp"></jsp:include>