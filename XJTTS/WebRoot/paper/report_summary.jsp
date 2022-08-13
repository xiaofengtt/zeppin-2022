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
					href="../paper/report_getReportData.action?paperid=<s:property value="#parameters.paperid"/>">原始数据</a></li>
				<li><a
					href="../paper/report_getReportPaper.action?paperid=<s:property value="#parameters.paperid"/>">每题统计</a></li>
				<li class="active"><a href="#">汇总统计</a></li>
				<li><a
					href="../paper/report_getReportContrast.action?paperid=<s:property value="#parameters.paperid"/>">对比统计</a></li>
				<li><a
					href="../paper/report_getReportManyilv.action?paperid=<s:property value="#parameters.paperid"/>">完成率分析</a></li>
			</ul>


		</div>

		<div class="report-fliter">
			<form class="form-horizontal" role="form"
				action="../paper/report_getReportSummary.action">
				<div class="row clearfix">
					<div class="col-md-6">
						<div class="form-group">
							<label class="col-sm-2 control-label" for="">项目</label>
							<div class="col-sm-6">
								<select class="form-control" id="pid" name="pid">
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
								<select class="form-control" id="stid" name="stid">
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
								<select class="form-control" id="tcid" name="tcid">
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
						<a target="_blank" class="btn btn-primary" id="output" >导出统计结果</a>
					</div>
				</div>
			</form>

		</div>

		<div class="list-content clearfix">
			<div class="report_title"></div>
			<div class="survey_result_qs">
				<div class="no-result" style="display: none;">
					<p class="no-result-tip">请至少显示一道问题以查看结果分析</p>
				</div>

				<s:if test="summaryList.size() > 0">

					<div class="result-content">
						<div class="chart-table clearfix">
							<h2>
								<span class="fl">此问卷总体满意度： <s:property
										value="avgCountScore" />
								</span>
							</h2>

							<table class="table table-bordered table-hover">
								<thead>
									<tr>
										<th>编号</th>
										<th>项目</th>
										<th>培训科目</th>
										<th>承训机构</th>
										<th>总体满意度</th>

									</tr>
								</thead>
								<tbody>
									<s:set name="flag" value="1" />
									<s:iterator value="summaryList" id="question">
										<tr>
											<td><s:property value="#flag" /></td>
											<td><s:property value="#question[0]" /></td>
											<td><s:property value="#question[1]" /></td>
											<td><s:property value="#question[2]" /></td>
											<td><s:property value="#question[3]" /></td>
										</tr>
										<s:set name="flag" value="#flag+1" />
									</s:iterator>
								</tbody>
							</table>
						</div>

					</div>

				</s:if>

				<!--end result-content-->
		<script>
			$(function() {
		        $('#output').click(function (e) {
		            
		            var paperid =url('?paperid');
		            var pid = document.getElementById("pid").options[document.getElementById("pid").selectedIndex].value;
		            var tcid = document.getElementById("tcid").options[document.getElementById("tcid").selectedIndex].value;
		            var stid =  document.getElementById("stid").options[document.getElementById("stid").selectedIndex].value;
		            
		            var http = '../paper/report_outputSummaryResult.action?paperid='+paperid+'&pid='+pid+'&tcid='+tcid+'&stid='+stid;
		            
		            $(this).attr("href",http);
					
		        });
			});
		</script>
			</div>

		</div>


	</div>
	<!--end listwrap-->

</div>
<!--end main-->

<jsp:include page="../admin/foot.jsp"></jsp:include>