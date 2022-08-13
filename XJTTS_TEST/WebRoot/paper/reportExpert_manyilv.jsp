<%@page import="org.apache.struts2.components.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:action name="head_init" namespace="/admin" executeResult="true"
	ignoreContextParams="false">
</s:action>
<s:action name="left_init" namespace="/admin" executeResult="true">
	<s:param name="leftName"></s:param>
</s:action>

<script src="../js/jquery-1.9.1.min.js"></script>
<script src="../js/highcharts.js"></script>
<script src="../js/data.js"></script>

<div class="main">
	<div class="listwrap">
		<div class="list_bar">
			<s:property value="psq.title" />
		</div>
		<br>

		<div class="nav">
			<ul class="nav nav-tabs">
				<li><a
					href="../paper/reportExpert_getReportExpertData.action?paperid=<s:property value="#parameters.paperid"/>">原始数据</a></li>
				<li><a
					href="../paper/reportExpert_getReportExpertSummary.action?paperid=<s:property value="#parameters.paperid"/>">汇总统计</a></li>
				<li class="active"><a href="#">完成率分析</a></li>
			</ul>
		</div>

		<div class="report-fliter">
			<form class="form-horizontal" role="form"
				action="../paper/reportExpert_getReportExpertManyilv.action">
				<div class="row clearfix">
					<div class="col-md-6">
					
					<div class="form-group">
							<label class="col-sm-2 control-label" for="">项目</label>
							<div class="col-sm-6">
								<select class="form-control" name="pid">
									<option value="0">全部</option>
									<s:iterator value="tprojects" id="tp">
										<option value='<s:property value="key" />'
											<s:if test="key==tproject"> selected </s:if>><s:property
												value="value" /></option>
									</s:iterator>
								</select>
							</div>
						</div>
					
						<div class="form-group">
							<label class="col-sm-2 control-label" for="">学科</label>
							<div class="col-sm-6">
								<select class="form-control" name="stid">
									<option value="0">全部</option>
									<s:iterator value="trainingSubject" id="ts">
										<option value='<s:property value="key" />'
											<s:if test="key==tsubject"> selected </s:if>><s:property
												value="value" /></option>
									</s:iterator>
								</select>
							</div>
						</div>


					</div>

					<div class="col-md-6">


						<div class="form-group">
							<label class="col-sm-2 control-label" style="min-width:90px" for="">承训单位</label>
							<div class="col-sm-6">
								<select class="form-control" name="tcid">
									<option value="0">全部</option>

									<s:iterator value="trainingCollege" id="ts">
										<option value='<s:property value="key" />'
											<s:if test="key==tcollege"> selected </s:if>><s:property
												value="value" /></option>
									</s:iterator>

								</select>
							</div>
						</div>

					</div>


				</div>

				<div class="row actionbar">
					<input name="paperid" type="hidden"
						value='<s:property value="#parameters.paperid"/>'>
					<div class="text-center">
						<button class="btn btn-primary" id="findRecord" type="submit">查询</button>
					</div>
				</div>
			</form>

		</div>

		<div class="list-content clearfix">
			<div class="report_title">
				
			</div>

			<div class="survey_result_qs">
				<div class="no-result" style="display: none;">
					<p class="no-result-tip">请至少显示一道问题以查看结果分析</p>
				</div>

				<s:if test="manyiList.size() > 0">

					<div class="result-content">
						<div class="chart-table  clearfix" style="border:0;padding:0;">
							<h2 class="clearfix"
								style="color:#2084ca;margin-left:10px;margin-top:10px;margin-bottom:20px;">
								<span class="fl">此评审问卷总体完成率： <s:property
										value="avgCountScore" />
								</span>
							</h2>

							<table class="table table-bordered table-hover">
								<thead>
									<tr>
										<th>编号</th>
										<th>项目</th>
										<th>培训学科</th>
										<th>承训机构</th>
										<th>计划<br>人数
										</th>
										<th>完成<br>人数
										</th>
										<th>评审<br>完成比
										</th>
									</tr>
								</thead>
								<tbody>
									<s:set name="flag" value="1" />
									<s:iterator value="manyiList" id="question">
										<tr>
											<td><s:property value="#flag" /></td>
											<td><s:property value="#question[1]" /></td>
											<td><s:property value="#question[3]" /></td>
											<td><s:property value="#question[5]" /></td>
											<td><s:property value="#question[6]" /></td>
											<td><s:property value="#question[7]" /></td>
											<td><s:property value="#question[8]" /></td>
										</tr>
										<s:set name="flag" value="#flag+1" />
									</s:iterator>
								</tbody>
							</table>
						</div>

					</div>

				</s:if>

				<!--end result-content-->

			</div>

		</div>


	</div>
	<!--end listwrap-->

</div>
<!--end main-->

<jsp:include page="../admin/foot.jsp"></jsp:include>