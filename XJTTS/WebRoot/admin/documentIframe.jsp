<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<title>在线预览</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<link rel="stylesheet" href="../css/bootstrap2.css">
<link rel="stylesheet" href="../css/iframe.css">
<link href="../css/bootstrap-switch.min.css" rel="stylesheet">
<script src="../js/jquery-1.9.1.min.js"></script>
<script src="../js/jquery.colorbox.js"></script>
<script src="../js/iframe.js"></script>
<script src="../js/bootstrap-switch.min.js"></script>
<script src="../js/url.min.js"></script>

</head>
<body>
	<script>
		$(function() {
			id=url('?id');
			var obj = $("#"+id,window.parent.document);
			var cUrl = obj.attr('data-url');
			$.getJSON('../base/fileUpload_checkDoc.action?url='+encodeURIComponent(cUrl), function(ret) {
				if (ret.Result == 'OK') {
					window.location.href="../generic/web/viewer.html?file="+encodeURIComponent(ret.docurl);
					
				} else {
					alert('失败,' + ret.Message);
					parent.$.colorbox.close();
				}
			})
		})

	</script>
<body>
</html>
