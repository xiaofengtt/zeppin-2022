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

		<div class="nav">
			<ul class="nav nav-tabs">

				<li>
					<a href="../paper/reportExpert_getReportExpertData.action?type=<s:property value="#parameters.type"/>&paperid=<s:property value="#parameters.paperid"/>">原始数据</a>
				</li>
				<li class="active"><a href="#">每题统计</a></li>
				<li>
					<a href="../paper/reportExpert_getReportExpertSummary.action?type=<s:property value="#parameters.type"/>&paperid=<s:property value="#parameters.paperid"/>">汇总统计</a>
				</li>
				<li>
					<a href="../paper/reportExpert_getReportExpertManyilv.action?type=<s:property value="#parameters.type"/>&paperid=<s:property value="#parameters.paperid"/>">完成率分析</a>
				</li>

			</ul>
		</div>

		<div class="report-fliter">
			<form class="form-horizontal" role="form"
				action="../paper/reportExpert_getReportExpertPaper.action">
				<input type="hidden" name="type" value="<s:property value="#parameters.type"/>">
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
						<button class="btn btn-primary" id="" type="submit">查询</button>&nbsp&nbsp&nbsp
						<a target="_blank" class="btn btn-primary" id="output" >导出统计结果</a>
						
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
													<td class="tl"><s:property value="#answer.name" />'</td>
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
						plotOptions: {
				            column: {
				                pointPadding: 0.4,
				                borderWidth: 1
				            }
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
		        $('#output').click(function (e) {
		            
		            var paperid =url('?paperid');
		            var pid = document.getElementById("pid").options[document.getElementById("pid").selectedIndex].value;
		            var tcid = document.getElementById("tcid").options[document.getElementById("tcid").selectedIndex].value;
		            var stid =  document.getElementById("stid").options[document.getElementById("stid").selectedIndex].value;
		            
		            var http = '../paper/reportExpert_outputExpertPaperResult.action?paperid='+paperid+'&pid='+pid+'&tcid='+tcid+'&stid='+stid;
		            
		            $(this).attr("href",http);
					
		        });
			});
		</script>
		<!--end list-content clearfix-->


	</div>
	<!--end listwrap-->

</div>
<!--end main-->

<jsp:include page="../admin/foot.jsp"></jsp:include>