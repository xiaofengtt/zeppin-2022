<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="/entity/function/css/style.css" rel="stylesheet" type="text/css">
<title>生殖健康咨询师培训课堂</title>
</head>
<FRAMESET border=0 frameSpacing=0 rows=50,* frameBorder=0>
  <frame name="top" scrolling="no" noresize src="/entity/function/lore/store_question_add.jsp?lore_id=<s:property value='lore_id'/>&fatherDir_id=<s:property value='fatherDir_id'/>&course_id=<s:property value='course_id'/>">
  <frame name="ctcontent" scrolling="auto"  src="/entity/function/lore/store_question_single.jsp?lore_id=<s:property value='lore_id'/>&fatherDir_id=<s:property value='fatherDir_id'/>&course_id=<s:property value='course_id'/>&type=<s:property value='type'/>">
  <noframes>
  <body>

  <p>此网页使用了框架，但您的浏览器不支持框架。</p>

  </body>
  </noframes>
</frameset>

</html>