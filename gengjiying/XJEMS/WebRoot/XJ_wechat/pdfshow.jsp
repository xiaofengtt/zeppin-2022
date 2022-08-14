<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title></title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

</head>

<body leftmargin="0" topmargin="0" class="scllbar">
	

	<script src="../js/jquery-1.11.1.js"></script>
	<script src="../js/jquery.cookie.js"></script>
	<script src="../js/jquery-ui.js"></script>
	<script src="../js/url.min.js"></script>
	<script src="../js/jquery.colorbox.js"></script>
	<script type="text/javascript" src="../js/query.js"></script>
	<script type="text/javascript">
		//window.navigate('<s:property value="filePath"/>');
		var id = url('?id') == null ? '' : url('?id');
		window.location.href ='../weixin/weixinToPdf?id=' + id;
		/* $(document).ready(function() {
			var id = url('?id') == null ? '' : url('?id');
			var mUrl = '../weixin/weixinToPdf?id=' + id;
			$.get(mUrl, function(r) {
				if (r.Status == 'success') {
					window.location.href = "file://" + r.Records;
				}
			}).done(function(r) {
			});
		}) */
	</script>
</body>
</html>

