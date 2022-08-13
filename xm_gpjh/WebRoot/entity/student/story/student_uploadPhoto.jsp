<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>单个照片上传</title>
<link href="../manager/pub/images/layout.css" rel="stylesheet" type="text/css" />
</head>
<body>
请上传以学生身份证号命名的 .bmp /.jpg .gif 文件
<s:form action="" method="POST" enctype="multipart/form-data">
        <s:file name="photoUpload" label="文件" />
        <s:submit value="上传"/>
</s:form>


</body>
</html>