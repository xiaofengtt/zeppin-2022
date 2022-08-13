<%@page import="org.apache.struts2.components.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:action name="head_init" namespace="/admin" executeResult="true"
	ignoreContextParams="false">
</s:action>
<s:action name="left_init" namespace="/admin" executeResult="true">
	<s:param name="leftName">站内信内容</s:param>
</s:action>
<div class="main">

	<div class="listwrap">
		<div class="list_bar"><a style="margin-left: 20px" href='../admin/mail_getInboxListInit.action'>收件箱 </a> &nbsp >> &nbsp 查看信息</div>
		<div class="container">
	    	<div align="center" class="col-md-12"><h3><b><s:property value="mailInformation.title"/></b></h3></div>
			<div align="center" class="col-md-12" style="color: gray;"><s:property value="timeString"/></div>
			<div align="center" class="col-md-12" style="margin-top: 20px;text-align: left;text-indent:2em"><s:property value="mailInformation.text"/></div>
			<div align="right" class="col-md-12" style="margin-top: 20px;padding-right: 80px"><s:property value="mailInformation.inscription"/></div>
    	</div>
		
	</div>
</div>


<jsp:include page="foot.jsp"></jsp:include>