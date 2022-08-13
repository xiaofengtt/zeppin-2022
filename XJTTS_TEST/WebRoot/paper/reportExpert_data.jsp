<%@page import="org.apache.struts2.components.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:action name="head_init" namespace="/admin" executeResult="true"
	ignoreContextParams="false">
</s:action>
<s:action name="left_init" namespace="/admin" executeResult="true">
	<s:param name="leftName"></s:param>
</s:action>

<!--<script src="../js/jquery-1.9.1.min.js"></script>
<script src="../js/highcharts.js"></script>
<script src="../js/data.js"></script>-->

<div class="main">
	<div class="listwrap">
		<div class="list_bar">
			<s:property value="psq.title" />
		</div>
		<br>

		<div class="nav">
			<ul class="nav nav-tabs clearfix">
				<li class="active"><a href="#">原始数据</a></li>
				<li><a
					href="../paper/reportExpert_getReportExpertSummary.action?paperid=<s:property value="#parameters.paperid"/>">汇总统计</a></li>
				<li><a
					href="../paper/reportExpert_getReportExpertManyilv.action?paperid=<s:property value="#parameters.paperid"/>">完成率分析</a></li>

			</ul>
		</div>

		<div class="report-fliter">
			<form class="form-horizontal" role="form"
				action="../paper/reportExpert_getReportExpertData.action">
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

				<s:if test="dataList.size() > 0">

					<div class="">
						<div class="chart-table clearfix">
							<table class="table table-bordered table-hover">
								<thead>
									<tr>
										<th width="133px">提交时间</th>
										<th width="92px">评审专家</th>
										<th width="312px">项目</th>
										<th width="106px">承训单位</th>
										<th width="92px">学科</th>
										<th width="82px">IP</th>
										<th width="40px">详细</th>

									</tr>
								</thead>
								<tbody>
									<s:set name="flag" value="1" />
									<s:iterator value="dataList" id="submit" status="status">
										<tr>
											<td><s:date name="createtime"
													format="yyyy-MM-dd HH:mm:ss" /></td>
											<td><s:property value="%{expertList[#status.index].getName()}" /></td>

											<td><s:property value="project.getName()" /></td>
											<td><s:property value="trainingCollege.getName()" /></td>

											<td><s:property value="trainingSubject.getName()" /></td>

											<td><s:property value="ip" /></td>
											<td><a
												href="../paper/reportExpert_detail.action?sub=<s:property value="id"/>&pid=<s:property value="#submit.psq.id"/>"
												target="_blank">查看</a></td>
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

		<div id="pagination" style="float:right;" class="pagination">
			<ul>
				<li><a title="Go to first page">首页</a></li>
				<li><a title="Go to previous page">&lt;&lt;</a></li>
				<li class="active"><a title="Current page is 1">1</a></li>
				<li><a title="Go to next page">&gt;&gt;</a></li>
				<li><a title="Go to last page">末页</a></li>
			</ul>
		</div>

	</div>
	<!--end listwrap-->

</div>
<!--end main-->

<script type="text/javascript">
	var paperid = url('?paperid');
	var page = url('?page');
	var stid = url('?stid');
	var tcid = url('?tcid');
	var pid = url('?pid');
	$.getJSON('../paper/reportExpert_getReportDataPageJson.action?', {
		paperid : paperid,
		page : page,
		pid : pid,
		stid : stid,
		tcid : tcid
	}, function(r) {
		var options = {
			currentPage : r.currentPage,
			totalPages : r.totalPage,
			shouldShowPage : function(type, page, current) {
				switch (type) {
				default:
					return true;
				}
			},
			onPageClicked : function(e, originalEvent, type, page) {
				window.location = updateURLParameter(url(), 'page', page);
			}

		}
		$('#pagination').bootstrapPaginator(options);
	})
</script>

<jsp:include page="../admin/foot.jsp"></jsp:include>