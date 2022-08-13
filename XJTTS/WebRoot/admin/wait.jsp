<%@page import="org.apache.struts2.components.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:action name="head_init" namespace="/admin" executeResult="true"
	ignoreContextParams="false">
</s:action>
<s:action name="left_init" namespace="/admin" executeResult="true">
	<s:param name="leftName">教师基本信息审核管理</s:param>
</s:action>
<div align='center'><img alt="waitting" src="../img/00002.png"> </div>
<jsp:include page="foot.jsp"></jsp:include>