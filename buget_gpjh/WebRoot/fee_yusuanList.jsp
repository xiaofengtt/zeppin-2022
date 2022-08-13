<%@ page contentType="text/html; charset=utf-8" language="java"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>预算列表</title>
<link rel="stylesheet" type="text/css" media="screen" href="css/jquery-ui.min.css" />
<link rel="stylesheet" type="text/css" media="screen" href="css/ui.jqgrid.css" />
<script src="js/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="js/i18n/grid.locale-cn.js" type="text/javascript"></script>
<script src="js/jquery.jqGrid.min.js" type="text/javascript"></script>

<script src="js/jquery-ui-1.10.3.custom.min.js" type="text/javascript">
</script>
<script src="js/jquery-ui-i18n.js" type="application/x-javascript"></script>
<script src="js/jquery.ui.datepicker-zh-CN.js" 	type="application/x-javascript"></script>
<script type="text/javascript" src="js/yusuan.js" ></script>

<script >
	var role = '<s:property value="#parameters.role" />';
	var userid = '<s:property value="#parameters.userid" />';
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

		</div>
	</div>
</body>
</html>