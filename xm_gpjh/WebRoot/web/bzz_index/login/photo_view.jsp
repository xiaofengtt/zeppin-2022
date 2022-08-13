<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>图片浏览</title>
  </head>
  
  <body style="padding:0px;margin:0px;">
    <img src=<s:property value="photoLink"/> width="300" height="400" onerror="this.src='/error/d4.jpg'"/>
  </body>
</html>
