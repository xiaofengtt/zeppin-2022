<%@page import="org.apache.struts2.components.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:action name="head_init" namespace="/admin" executeResult="true"
	ignoreContextParams="false">
</s:action>
<s:action name="left_init" namespace="/admin" executeResult="true">
	<s:param name="leftName"></s:param>
</s:action>

<div class="main ">
	<div class="report_detail">
		<div class="list_bar">调查结果</div>

		<div class="ctt_bd">

			<div class="que_meta">
				<table class="table">
					<tbody>
						<tr>
							<td><b>所属项目：</b>
							<s:property value="submit.project.name" /></td>
							<td><b>承训单位：</b>
							<s:property value="submit.trainingCollege.name" /></td>
							<td><b>学科：</b>
							<s:property value="submit.trainingSubject.name" /></td>
						</tr>
						<tr>
							<td><b>登录码：</b>
							<s:property value="submit.uuid" /></td>
							<td><b>教师姓名：</b>
							<s:property value="teacher.name" /></td>
							<td><b>提交时间：</b>
							<s:date name="submit.createtime" format="yyyy-MM-dd HH:mm:ss" /></td>
						</tr>
					</tbody>
				</table>
			</div>
			<h1>问卷填写结果</h1>
			<ul class="que_results">

				<s:set name="flag" value="1" />
				<s:set name="questionId" value="-1" />
				<s:iterator value="submit.results" id="result">

					<s:if test="#questionId==-1">
						<li>第<s:property value="#flag" />题：<s:property
								value="#result.question.name" escape="false" /><br> <s:if
								test="#result.question.type == 6">
								<strong>调查结果：</strong>
								<span style="color:red"><s:property
											value="#result.content" escape="false" /></span>
							</s:if> <s:elseif test="#result.question.type == 7"></s:elseif> <s:else>
								<strong>调查结果：</strong>
								<s:property value="#result.answer.inx" />: <s:property
									value="#result.answer.name" escape="false" />
							</s:else>
					</s:if>
					<s:else>

						<s:if test="#questionId == #result.question.id">
							<s:property value="#result.answer.inx" />: <s:property
								value="#result.answer.name" escape="false" />
						</s:if>
						<s:else>
							</li>
							<s:set name="flag" value="#flag+1" />
							<li>第<s:property value="#flag" />题：<s:property
									value="#result.question.name" escape="false" /><br> <s:if
									test="#result.question.type == 6">
									<strong>调查结果：</strong>
									<span style="color:red"><s:property
											value="#result.content" /></span>
								</s:if> <s:elseif test="#result.question.type == 7"></s:elseif> <s:else>
									<strong>调查结果：</strong>
									<s:property value="#result.answer.inx" />: <s:property
										value="#result.answer.name" escape="false" />
								</s:else>
						</s:else>

					</s:else>


					<s:set name="questionId" value="#result.question.id" />

				</s:iterator>
			
				</li>
			</ul>
		</div>
	</div>
</div>

<jsp:include page="../admin/foot.jsp"></jsp:include>
