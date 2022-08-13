<%@page import="org.apache.struts2.components.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<script src="../js/highcharts.js"></script>
<script src="../js/data.js"></script>
<script src="../js/jsrender.min.js"></script>

<s:action name="head_init" namespace="/admin" executeResult="true"
	ignoreContextParams="false">
</s:action>
<s:action name="left_init" namespace="/admin" executeResult="true"
	ignoreContextParams="true">
</s:action>
<link rel="stylesheet" href="../css/iframe.css">
<div class="main">
	<table class="table table-bordered" style="text-align: center;">
		<tr>
			<td class="left"><span class="text-primary">姓名：<s:property
						value="teacher.name" /></span></td>
			<td class="left"><span class="text-primary">性别：<s:property
						value="teacherEx.sexString" /></span></td>
			<td class="left"><span class="text-primary">身份证号：<s:property
						value="teacher.idcard" /></span></td>
			<td class="left"><span class="text-primary">教龄：<s:property
						value="teacherEx.teachingAge" /></span></td>
			<td class="left"><span class="text-primary">教师状态：<s:property
						value="teacherEx.statusString" /></span></td>
			<td class="left"><span class="text-primary">编制：<s:property
						value="teacherEx.authorized" /></span></td>
			<td class="left"><span class="text-primary">职称：<s:property
						value="teacher.jobTitle.name" /></span></td>
			<td class="left"><span class="text-primary">职务：<s:property
						value="teacher.jobDuty.name" /></span></td>
			<td class="left"><span class="text-primary">所在学校：<s:property
						value="teacher.organization.name" /></span></td>
		</tr>
		<tr>
			<td class="left"><span class="text-primary">联系电话：<s:property
						value="teacher.mobile" /></span></td>
			<td class="left"><span class="text-primary">民族：<s:property
						value="teacher.ethnic.name" /></span></td>
			<td class="left"><span class="text-primary">政治面貌：<s:property
						value="teacher.politics.name" /></span></td>
			<td class="left"><span class="text-primary">主要教学学科：<s:property
						value="teacherEx.mainTeachingCourse.subject.name" /></span></td>
			<td class="left"><span class="text-primary">主要教学学段：<s:property
						value="teacherEx.mainTeachingClass.grade.name" /></span></td>
			<td class="left"><span class="text-primary">主要教学语言：<s:property
						value="teacherEx.mainTeachingLanguage.language.name" /></span></td>
		</tr>
		<tr>
			<td class="left"><span class="text-primary">最高学历：<s:property
						value="teacher.eductionBackground.name" /></span></td>
			<td class="left"><span class="text-primary">汉语言水平：<s:property
						value="teacher.chineseLanguageLevel.name" /></span></td>
			<td class="left"><span class="text-primary">是否双语教学：<s:property
						value="teacherEx.isMultiLanguage" /></span></td>

				<td class="left"><span class="text-primary">头像：<s:property
							value="teacher.headPicPath" /></span></td>

		</tr>

	</table>

	<!-- 遍历数据 -->
	<s:iterator value="ttRecordsHash" id="pros">
		<table id="<s:property value="key" />" class="table table-bordered"
			style="text-align: center;">
			<tr>
				<td class="left"><span class="text-primary">时间：<s:property
							value="value[1]" /></span></td>
				<td class="left"><span class="text-primary">内容：<s:property
							value="value[0]" /></span></td>
				<td class="left"><span class="text-primary">是否合格：<s:property
							value="value[2]" /></span></td>
				<td class="left"><span class="text-primary">承训单位：<s:property
							value="value[10]" /></span></td>
				<td class="left"><span class="text-primary">培训方式：<s:property
							value="value[4]" /></span></td>
				<td class="left"><span class="text-primary">培训状态：<s:property
							value="value[2]" /></span></td>
				<td class="left"><span class="text-primary">培训成绩：<s:property
							value="value[3]" /></span></td>
				<td class="left"><span class="text-primary">证书编号：<s:property
							value="value[11]" /></span></td>
				<td class="left"><span class="text-primary">获取集中培训学时：<s:property
							value="value[5]" /></span></td>
				<td class="left"><span class="text-primary">获取信息技术培训学时：<s:property
							value="value[6]" /></span></td>
				<td class="left"><span class="text-primary">获取区域特色培训学时：<s:property
							value="value[7]" /></span></td>
				<td class="left"><span class="text-primary">获取校本培训学时：<s:property
							value="value[8]" /></span></td>
				<td class="left"><span class="text-primary">不合格原因：<s:property
							value="value[9]" /></span></td>

			</tr>

		</table>
	</s:iterator>

	<s:iterator value="cycleHash" id="">
		<table id="<s:property value="key" />" class="table table-bordered"
			style="text-align: center;">
			<tr>
				<td class="left"><span class="text-primary">周期名称：<s:property
							value="value[2]" /></span></td>
				<td class="left"><span class="text-primary">周期开始年份：<s:property
							value="value[0]" /></span></td>
				<td class="left"><span class="text-primary">周期结束年份：<s:property
							value="value[1]" /></span></td>
			</tr>

		</table>
	</s:iterator>

	<s:iterator value="classHoursHashMap" id="">
		<table id="<s:property value="key" />" class="table table-bordered"
			style="text-align: center;">
			<tr>
				<td class="left"><span class="text-primary">集中培训：<s:property
							value="value[0]" /></span></td>
				<td class="left"><span class="text-primary">信息技术：<s:property
							value="value[1]" /></span></td>
				<td class="left"><span class="text-primary">区域：<s:property
							value="value[2]" /></span></td>
				<td class="left"><span class="text-primary">集中培训：<s:property
							value="value[3]" /></span></td>
				<td class="left"><span class="text-primary">信息技术：<s:property
							value="value[4]" /></span></td>
				<td class="left"><span class="text-primary">区域：<s:property
							value="value[5]" /></span></td>
				<td class="left"><span class="text-primary">信息技术：<s:property
							value="value[6]" /></span></td>
				<td class="left"><span class="text-primary">区域：<s:property
							value="value[7]" /></span></td>
			</tr>

		</table>

		<table id="datatable" style="display: block; width: 60%;">
			<thead>
				<tr>
					<th>认订标准</th>
					<th>已认定学时</th>
					<th>1</th>
					<th>2</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td class="left"><span class="text-primary"><s:property
								value="value[0]" /></span></td>
					<td class="left"><span class="text-primary"><s:property
								value="value[1]" /></span></td>
					<td class="left"><span class="text-primary"><s:property
								value="value[2]" /></span></td>
					<td class="left"><span class="text-primary"><s:property
								value="value[3]" /></span></td>
				</tr>
				<tr>
					<td class="left"><span class="text-primary"><s:property
								value="value[4]" /></span></td>
					<td class="left"><span class="text-primary"><s:property
								value="value[5]" /></span></td>
					<td class="left"><span class="text-primary"><s:property
								value="value[6]" /></span></td>
					<td class="left"><span class="text-primary"><s:property
								value="value[7]" /></span></td>
				</tr>
			</tbody>
		</table>
	</s:iterator>

	<div id="aa" style="width: 70%; height: 400px; margin: 0 auto"></div>
</div>

<script>
	function getGraph() {
		//曲线图 
		$('#aa').highcharts(
				{
					data : {
						table : 'datatable'
					},
					chart : {
						type : 'column'
					},
					title : {
						text : 'Data extracted from a HTML table in the page'
					},
					yAxis : {
						allowDecimals : false,
						title : {
							text : 'Units'
						}
					},
					tooltip : {
						formatter : function() {
							return '<b>' + this.series.name + '</b><br/>'
									+ this.point.y + ' '
									+ this.point.name.toLowerCase();
						}
					}
				});
	}

	
</script>