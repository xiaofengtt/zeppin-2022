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

	$(function() {
		$.post("adu_getOProjects.action", function(r) {

			var ohml = "";
			for ( var i = 0; i < r.length; i++) {
				var id = r[i].id;
				var name = r[i].name;

				var tm = "<option value=\""+id+"\" >" + name + "</option>"
				ohml += tm;
			}

			$('#projectId').html(ohml);

		});

		$.post("adu_getSubjects.action", function(r) {

			var ohml = "";
			for ( var i = 0; i < r.length; i++) {
				var id = r[i].id;
				var name = r[i].name;

				var tm = "<option value=\""+id+"\" >" + name + "</option>"
				ohml += tm;
			}

			$('#subjectId').html(ohml);

		});

		$.post("adu_getUnits.action", function(r) {

			var ohml = "";
			for ( var i = 0; i < r.length; i++) {
				var id = r[i].id;
				var name = r[i].name;

				var tm = "<option value=\""+id+"\" >" + name + "</option>"
				ohml += tm;
			}

			$('#unitId').html(ohml);

		});

	});

	function selectSubject(id) {
		$.post("adu_getChildProjects.action", {
			"projectId" : id
		}, function(r) {

			var ohml = "";
			for ( var i = 0; i < r.length; i++) {
				var id = r[i].id;
				var name = r[i].name;

				var tm = "<option value=\""+id+"\" >" + name + "</option>"
				ohml += tm;
			}

			$('#subprojectId').html(ohml);

		});
	}
</script>

</head>

<body>

	<form action="trainee_trainUploadFileOld.action" method="post"
		enctype="multipart/form-data">
		<table>
			<tr>
				<td>省 份:</td>
				<td><%=session.getAttribute("provinceName").toString()%></td>
			</tr>
			<tr>
				<td>所属项目:</td>
				<td><select id="projectId" name="projectId"
					onchange="selectSubject(this.value)"></select></td>
			</tr>
			<tr>
				<td>子项目:</td>
				<td><select id="subprojectId" name="subprojectId"></select></td>
			</tr>
			<tr>
				<td>培训单位:</td>
				<td><select id="unitId" name="unitId"></select></td>
			</tr>
			<tr>
				<td>培训学科:</td>
				<td><select id="subjectId" name="subjectId"></select></td>
			</tr>
			<tr>
				<td>学员EXCEL模板:</td>
				<td><input type="file" name="fileupload" /></td>
				<td><a href="/mw_gpjh/studentModel.xls" >模板下载</a></td>
			</tr>
			<tr>
				<td></td>
				<td><input type="submit" value="上传" /></td>
			</tr>
		</table>
	</form>


</body>
</html>
