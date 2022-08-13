<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>上传学员EXCEL</title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width">

<script type="text/javascript" src="js/vendor/jquery-1.9.1.min.js"></script>
<script type="text/javascript">



</script>

</head>

<body>

	<form action="trainee_historyUploadFile.action" method="post"
		enctype="multipart/form-data">
		<table>
			<tr>
				<td>省 份:</td>
				<td><%=session.getAttribute("provinceName").toString()%></td>
			</tr>
			<tr>
				<td>所属项目:</td>
				<td>
					<select id="projectId" name="projectId">
						<option value="“国培计划（2010）”中西部项目（置换脱产）">“国培计划（2010）”中西部项目（置换脱产）</option>
						<option value="“国培计划（2010）”中西部项目（短期集中）">“国培计划（2010）”中西部项目（短期集中）</option>
						<option value="“国培计划（2011）”中西部项目（置换脱产）">“国培计划（2011）”中西部项目（置换脱产）</option>
						<option value="“国培计划（2011）”中西部项目（短期集中）">“国培计划（2011）”中西部项目（短期集中）</option>
						<option value="“国培计划（2011）”幼师国培项目（置换脱产）">“国培计划（2011）”幼师国培项目（置换脱产）</option>
						<option value="“国培计划（2011）”幼师国培项目（短期集中）">“国培计划（2011）”幼师国培项目（短期集中）</option>
						<option value="“国培计划（2011）”幼师国培项目（转岗教师）">“国培计划（2011）”幼师国培项目（转岗教师）</option>
						
						<option value="“国培计划（2012）”中西部项目（置换脱产）">“国培计划（2012）”中西部项目（置换脱产）</option>
						<option value="“国培计划（2012）”中西部项目（短期集中）">“国培计划（2012）”中西部项目（短期集中）</option>
						<option value="“国培计划（2012）”幼师国培项目（置换脱产）">“国培计划（2012）”幼师国培项目（置换脱产）</option>
						<option value="“国培计划（2012）”幼师国培项目（短期集中）">“国培计划（2012）”幼师国培项目（短期集中）</option>
						<option value="“国培计划（2012）”幼师国培项目（转岗教师）">“国培计划（2012）”幼师国培项目（转岗教师）</option>
						
					</select>
				</td>
			</tr>
			<tr>
				<td>学员EXCEL模板:</td>
				<td><input type="file" name="fileupload" /></td>
				<td><a href="/mw_gpjh/导入历史记录模板.xls" >模板下载</a></td>
			</tr>
			<tr>
				<td></td>
				<td><input type="submit" value="上传" /></td>
			</tr>
		</table>
	</form>


</body>
</html>
