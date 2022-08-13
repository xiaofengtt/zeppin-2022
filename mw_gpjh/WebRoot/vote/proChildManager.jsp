<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>子项目管理</title>
<link rel="stylesheet" type="text/css" media="screen"
	href="css/jquery-ui.min.css" />
<link rel="stylesheet" type="text/css" media="screen"
	href="css/ui.jqgrid.css" />
<script src="js/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="js/i18n/grid.locale-cn.js" type="text/javascript"></script>
<script src="js/jquery.jqGrid.min.js" type="text/javascript"></script>

<script src="js/jquery-ui-1.10.3.custom.min.js" type="text/javascript">
	<script src="js/jquery-ui-i18n.js" type="application/x-javascript">
</script>
<script src="js/jquery.ui.datepicker-zh-CN.js"
	type="application/x-javascript"></script>
</script>
<script src="js/proChildManager.js" type="text/javascript"></script>

<script type="text/javascript">
	var userid = '<s:property value="#parameters.userid" />';

	$(function() {
		function getProgamesParent() {
			$.get('project_getParents.action', function(r) {
				if (r.length > 0) {
					var opht = "";
					for (var i = 0; i < r.length; i++) {
						var ht = '<option value="'+r[i].id+'">' + r[i].name	+ '</option>';
						opht += ht;
					}

					$('#addProjectParent').html(opht);
					$('#ediProjectParent').html(opht);
				}

			});
		}
		getProgamesParent();

	})
</script>

</head>

<body>
	<div class="container">
		<div class="content">
			<div class="list">
				<table id="list2">
				</table>
				<div id="pager2"></div>
			</div>

			<div id="addDiv" style="display:none;">
				<div>
					<label>项目名称*:</label> <input id="addProjectNameText"
						style="width:333px;" />
				</div>
				<div>
					<label>项目编号*:</label> <input id="addProjectCode"
						style="width:333px;" />
				</div>
				<div style="display:none;">
					<label>所属年度*:</label> <input id="addProjectYear"
						style="width:333px;" />
				</div>
				<div>
					<label>所属项目*:</label> <select id="addProjectParent"
						style="width:333px;"></select>
				</div>
			</div>
			<div id="ediDiv" style="display:none;">
				<div>
					<label>项目名称*:</label> <input id="ediProjectNameText"
						style="width:333px;" />
				</div>
				<div>
					<label>项目编号*:</label> <input id="ediProjectCode"
						style="width:333px;" />
				</div>
				<div style="display:none;">
					<label>所属年度*:</label> <input id="ediProjectYear"
						style="width:333px;" />
				</div>
				<div>
					<label>项目类型*:</label> <select id="ediProjectParent"
						style="width:333px;"></select>
				</div>
			</div>

		</div>
	</div>
</body>
</html>