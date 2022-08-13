<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>标题文档</title>
<link href="../css/css.css" rel="stylesheet" type="text/css">
</head>
<body leftmargin="0" topmargin="0" bgcolor="#F9F9F9">
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#F9F9F9">
  <tr>
    <td><iframe src="/entity/studyZone/courseResources_previewTestpaperLeft.action?paper_id=<s:property value='#parameters.paper_id[0]'/>" frameborder="0" width="100%" height="100%" scrolling="auto" allowtransparency="true"></iframe></td>
  </tr>
</table>
</body>
</html>