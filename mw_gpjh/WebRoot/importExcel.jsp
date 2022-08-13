<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title></title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<script type="text/javascript" src="js/vendor/jquery-1.9.1.min.js"></script>
<script type="text/javascript">
	$(function() {
		$.post("doExcel_getProvince.action", function(r) {

			var ohml = "";
			for ( var i = 0; i < r.length; i++) {
				var id = r[i].id;
				var name = r[i].name;

				var tm = "<option value=\""+id+"\" >" + name + "</option>"
				ohml += tm;
			}

			$('#selProvince').html(ohml);

		});
	});
</script>

</head>

<body>

	<form action="doExcel_importExcel.action" method="post"
		enctype="multipart/form-data">
		<table>
			<tr>
				<td>省份:</td>
				<td><select id="selProvince" name="proviceId">
						
				</select></td>
			</tr>
			<tr>
				<td>文件:</td>
				<td><input type="file" name="upfile" /></td>
			</tr>
			<tr>
				<td></td>
				<td><input type="submit" value="导入" /></td>
			</tr>
		</table>
	</form>

</body>
</html>
