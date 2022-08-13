<%@page import="org.apache.struts2.components.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:action name="head_init" namespace="/admin" executeResult="true"
	ignoreContextParams="false">
</s:action>
<s:action name="left_init" namespace="/admin" executeResult="true">
	<s:param name="leftName"></s:param>
</s:action>

<script src="../js/highcharts.js"></script>
<script src="../js/data.js"></script>

<div class="main">
	<div class="listwrap">
		<div class="list_bar">
			<s:property value="psq.title" />
		</div>
		<br>
			<a target="_blank" class="btn btn-primary" href="../paper/report_outputPaperResult.action?paperid=<s:property value="psq.id" />&pid=<s:property value="projectApply.project.id" />&tcid=<s:property value="projectApply.trainingCollege.id" />&stid=<s:property value="projectApply.trainingSubject.id" />">导出统计结果</a>
		</div>
		<div class="list-content clearfix">
			<div class="report_title">
			</div>
			<div class="survey_result_qs">
				<div class="no-result" style="display: none;">
					<p class="no-result-tip">请至少显示一道问题以查看结果分析</p>
				</div>

				<s:iterator value="psq.questions" id="question">
					<s:if
						test="%{#question.type != 0 && #question.type!=6 && #question.type!=7 }">

						<div class="result-content">
							<h2 class="question-title">
								<b><s:property value="#question.inx" /></b>
								<s:property value="#question.name" escape="false" />
							</h2>

							<div class="chart-content">
								<div class="chart-table">
									<table id="datatable_<s:property value="#question.id" />"
										style="display:none">
										<thead>
											<tr>
												<th class="option">选项</th>
												<th class="option-data">选择人数</th>
											</tr>
										</thead>
										<tbody>
											<s:iterator value="#question.answers" id="answer">
												<tr>
													<td class="tl"><s:property value="#answer.name" /></td>
													<s:set name="count"
														value="paperHashMap.get(#question.id + '_' + #answer.id)[0]" />

													<s:if test="#count==null">
														<s:set name="count" value="0" />
													</s:if>
													<td class="tr"><s:property value="#count" /></td>
											</s:iterator>

										</tbody>
									</table>
									<table 
										style="margin-left:20px;"
										class="table table-bordered table-hover">
										<thead>
											<tr>
												<th class="option">选项</th>
												<th class="option-data">选择人数</th>
												<th class="option-data">百分比</th>
											</tr>
										</thead>
										<tbody>
											<s:iterator value="#question.answers" id="answer">
												<tr>
													<td class="tl"><s:property value="#answer.name" /></td>
													<s:set name="count"
														value="paperHashMap.get(#question.id + '_' + #answer.id)[0]" />

													<s:if test="#count==null">
														<s:set name="count" value="0" />
													</s:if>
													<td class="tr"><s:property value="#count" /></td>
													
													<s:set name="percent"
														value="paperHashMap.get(#question.id + '_' + #answer.id)[1]" />

													<s:if test="#percent==null">
														<s:set name="percent" value="0.0" />
													</s:if>
													<td class="tr"><s:property value="#percent" />%</td>
												</tr>
											</s:iterator>

										</tbody>
									</table>
								</div>
								<div class="clearfix">
									<div class="chart-column col-md-6"
										id="chart-column_<s:property value="#question.id" />"
										data-id="<s:property value="#question.id" />"
										style="height:300px;"></div>
									<div class="chart-pie  col-md-6"
										id="chart-pie_<s:property value="#question.id" />"
										data-id="<s:property value="#question.id" />"
										style="height:300px;"></div>
								</div>
							</div>
						</div>
					</s:if>

				</s:iterator>

				<!--end result-content-->

		</div>
		<script>
			$(function() {
				$('.chart-column').each(function() {
					var id = $(this).attr('data-id');
					$('#chart-column_' + id).highcharts({
						data : {
							table : document.getElementById('datatable_' + id)
						},
						legend : {
							enabled : false
						},
						chart : {
							type : 'column'
						},
						title : {
							text : ''
						},
						yAxis : {
							allowDecimals : false,
							title : {
								text : '选择人数',
								style : {
									"fontSize" : "12px",
									"color" : '#5691f0'
								}
							}
						}
					});

					$('#chart-pie_' + id).highcharts({
						data : {
							table : document.getElementById('datatable_' + id)
						},
						chart : {
							type : 'pie'
						},

						title : {
							text : '选择人数',
							align : 'center',
							verticalAlign : 'middle',
							y : 5,
							style : {
								"fontSize" : "16px",
								"color" : '#5691f0'
							}
						},
						yAxis : {
							allowDecimals : false,
							title : {
								text : '选择人数'
							}
						},
						series : [ {
							name : 'Browsers',
							title : '',
							innerSize : '50%',
							showInLegend : false,
							dataLabels : {
								distance : -40,
								style : {
									fontWeight : "bold",
									color : "#fff",
									textShadow : "0px 1px 2px gray"
								}
							}

						} ]
					});

				});
			});
		</script>
		<!--end list-content clearfix-->


	</div>
	<!--end listwrap-->

</div>
<!--end main-->

<jsp:include page="../admin/foot.jsp"></jsp:include>